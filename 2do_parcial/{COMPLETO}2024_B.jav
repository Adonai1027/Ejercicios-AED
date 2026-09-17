// PARCIAL 2024 - AED
// Ejercicio 1: Actualización Secuencial (Maestro CLIENTES con Detalle MOVIMIENTOS)
// Ejercicio 2: Proceso Estadístico (Matriz de Sucursales x Categoría + Archivo Indexado SUCURSALES)

// ============================================================================
// EJERCICIO N° 1: ACTUALIZACIÓN DEL ARCHIVO CLIENTES
// ============================================================================

Accion ActualizacionClientes Es
   Ambiente
      formato_fecha = Registro
         aaaa: N(4)
         mm: 1..12
         dd: 1..31
      FR

      clave_cliente = Registro
         id_sucursal: 1..15
         id_cliente: N(8)
      FR

      cliente = Registro
         clave: clave_cliente
         nombre_y_apellido: AN(50)
         saldo: REAL
         fecha_alta: formato_fecha
         fecha_baja: formato_fecha
      FR

      arch_mae, mae_act: archivo de cliente ordenado por clave
      reg_mae, reg_act, aux: cliente

      movimiento = Registro
         clave: clave_cliente
         cod_movimiento: 0..99
         nombre_y_apellido: AN(50)
         fecha_movimiento: formato_fecha
         monto: REAL
         detalle: AN(100)
         categoria: 1..6
         tipo: ('I', 'E') // 'I' = Ingreso, 'E' = Egreso
      FR

      arch_mov: archivo de movimiento ordenado por clave y cod_movimiento
      reg_mov: movimiento

      cant_clientes_nuevos: ENTERO

      Procedimiento LeerMae() Es
         Leer(arch_mae, reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.clave.id_sucursal := HV
            reg_mae.clave.id_cliente := HV
         FSi
      FP

      Procedimiento LeerMov() Es
         Leer(arch_mov, reg_mov)
         Si FDA(arch_mov) Entonces
            reg_mov.clave.id_sucursal := HV
            reg_mov.clave.id_cliente := HV
         FSi
      FP

      Procedimiento TratarTransaccion() Es
         Si aux.fecha_baja.aaaa <> 0 Entonces
            Escribir('ERROR: Movimiento registrado para cliente dado de baja: ', aux.clave.id_cliente)
         Sino
            Si reg_mov.tipo = 'I' Entonces
               aux.saldo := aux.saldo + reg_mov.monto
            Sino
               Si reg_mov.tipo = 'E' Entonces
                  aux.saldo := aux.saldo - reg_mov.monto
               Sino
                  Escribir('ERROR: Tipo de movimiento invalido (', reg_mov.tipo, ') para cliente: ', aux.clave.id_cliente)
               FSi
            FSi
         FSi
      FP

      Procedimiento ProcesarMovimientoIgual() Es
         Segun reg_mov.cod_movimiento Hacer
            = 0:
               Escribir('ERROR: Alta invalida. El cliente ', reg_mov.clave.id_cliente, ' ya existe en la sucursal ', reg_mov.clave.id_sucursal)
            = 99:
               aux.fecha_baja := reg_mov.fecha_movimiento
            de otro modo:
               TratarTransaccion()
         FSegun
      FP

   Proceso
      ABRIR E/(arch_mae); LeerMae()
      ABRIR E/(arch_mov); LeerMov()
      ABRIR /S(mae_act)

      cant_clientes_nuevos := 0

      Mientras (reg_mae.clave.id_sucursal <> HV) O (reg_mov.clave.id_sucursal <> HV) Hacer
         Si reg_mae.clave < reg_mov.clave Entonces
            // Cliente sin movimientos en el mes
            reg_act := reg_mae
            Grabar(mae_act, reg_act)
            LeerMae()
         Sino
            Si reg_mae.clave = reg_mov.clave Entonces
               // Cliente existente con movimientos
               aux := reg_mae
               Mientras reg_mae.clave = reg_mov.clave Hacer
                  ProcesarMovimientoIgual()
                  LeerMov()
               FMientras
               reg_act := aux
               Grabar(mae_act, reg_act)
               LeerMae()
            Sino
               // reg_mae.clave > reg_mov.clave -> Posible Alta de Cliente
               Si reg_mov.cod_movimiento = 0 Entonces
                  cant_clientes_nuevos := cant_clientes_nuevos + 1
                  
                  // Inicialización del nuevo cliente
                  aux.clave := reg_mov.clave
                  aux.nombre_y_apellido := reg_mov.nombre_y_apellido
                  aux.saldo := 0
                  aux.fecha_alta := reg_mov.fecha_movimiento
                  aux.fecha_baja.aaaa := 0 // Fecha nula / Sin baja
                  
                  LeerMov()
                  
                  // Procesar movimientos adicionales en el mismo mes para la nueva alta
                  Mientras aux.clave = reg_mov.clave Hacer
                     ProcesarMovimientoIgual()
                     LeerMov()
                  FMientras
                  
                  reg_act := aux
                  Grabar(mae_act, reg_act)
               Sino
                  Escribir('ERROR: Movimiento invalido (cod ', reg_mov.cod_movimiento, ') para cliente inexistente: ', reg_mov.clave.id_cliente)
                  LeerMov()
               FSi
            FSi
         FSi
      FMientras

      Escribir('=== RESUMEN DE ACTUALIZACION ===')
      Escribir('Cantidad total de clientes nuevos agregados: ', cant_clientes_nuevos)

      CERRAR(arch_mae)
      CERRAR(arch_mov)
      CERRAR(mae_act)
FIN_ACCIÓN


// ============================================================================
// EJERCICIO N° 2: INFORME ESTADÍSTICO DE CARTERA DE CLIENTES
// ============================================================================

Accion InformeCarteraClientes Es
   Ambiente
      formato_fecha = Registro
         aaaa: N(4)
         mm: 1..12
         dd: 1..31
      FR

      clave_cliente = Registro
         id_sucursal: 1..15
         id_cliente: N(8)
      FR

      cliente = Registro
         clave: clave_cliente
         nombre_y_apellido: AN(50)
         saldo: REAL
         fecha_alta: formato_fecha
         fecha_baja: formato_fecha
      FR

      arch_cli: archivo de cliente ordenado por clave
      reg_cli: cliente

      sucursal = Registro
         id_sucursal: 1..15
         nombre_sucursal: AN(50)
         direccion: AN(100)
         localidad: AN(50)
      FR

      arch_suc: archivo de sucursal indexado por id_sucursal
      reg_suc: sucursal

      // Matriz de 16 filas x 4 columnas
      // Filas 1..15: Sucursales, Fila 16: Totales por Categoría
      // Cols 1..3: Categorías (1-Estándar, 2-Oro, 3-Diamante), Col 4: Totales por Sucursal
      cuadro: Arreglo[1..16, 1..4] de ENTERO
      nombres_sucursales: Arreglo[1..15] de AN(50)

      i, j, cat: ENTERO

   Proceso
      ABRIR E/(arch_cli)
      ABRIR E/(arch_suc)

      // 1. Cargar nombres de las 15 sucursales desde el archivo indexado
      Para i := 1 Hasta 15 Hacer
         reg_suc.id_sucursal := i
         Leer(arch_suc, reg_suc)
         Si EXISTE Entonces
            nombres_sucursales[i] := reg_suc.nombre_sucursal
         Sino
            nombres_sucursales[i] := 'Sucursal Desconocida'
         FSi
      FPara

      // 2. Inicializar Matriz en ceros
      Para i := 1 Hasta 16 Hacer
         Para j := 1 Hasta 4 Hacer
            cuadro[i, j] := 0
         FPara
      FPara

      // 3. Procesar el archivo de CLIENTES
      Leer(arch_cli, reg_cli)
      Mientras NFDA(arch_cli) Hacer
         // Filtrar clientes no dados de baja
         Si reg_cli.fecha_baja.aaaa = 0 Entonces
            i := reg_cli.clave.id_sucursal

            // Determinar Categoría según el Saldo
            Si reg_cli.saldo < 100000 Entonces
               cat := 1 // Estándar
            Sino
               Si reg_cli.saldo < 1500000 Entonces
                  cat := 2 // Oro
               Sino
                  cat := 3 // Diamante
               FSi
            FSi

            // Acumular en la matriz y en las celdas de totales
            cuadro[i, cat] := cuadro[i, cat] + 1         // Celda sucursal/categoría
            cuadro[i, 4] := cuadro[i, 4] + 1             // Total sucursal
            cuadro[16, cat] := cuadro[16, cat] + 1       // Total categoría
            cuadro[16, 4] := cuadro[16, 4] + 1           // Total general
         FSi

         Leer(arch_cli, reg_cli)
      FMientras

      // 4. Emisión del Informe Estadístico por Pantalla
      Escribir('========================================================================================')
      Escribir('                        INFORME DE CARTERA DE CLIENTES                                  ')
      Escribir('========================================================================================')
      Escribir('Sucursal                     | Categoria Estandar | Categoria Oro | Categoria Diamante | Totales x Suc')
      Escribir('----------------------------------------------------------------------------------------')

      Para i := 1 Hasta 15 Hacer
         Escribir(nombres_sucursales[i], ' | ', cuadro[i, 1], ' | ', cuadro[i, 2], ' | ', cuadro[i, 3], ' | ', cuadro[i, 4])
      FPara

      Escribir('----------------------------------------------------------------------------------------')
      Escribir('Totales x Categoria          | ', cuadro[16, 1], ' | ', cuadro[16, 2], ' | ', cuadro[16, 3], ' | ', cuadro[16, 4])
      Escribir('========================================================================================')

      CERRAR(arch_cli)
      CERRAR(arch_suc)
FIN_ACCIÓN

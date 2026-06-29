// Consigna: Escribir una acción que solicite al usuario el ingreso de 10 números enteros. El algoritmo debe calcular el promedio de los números pares e informar el resultado por pantalla.
Accion promedio Es 
Ambiente 
   numero, acum_pares, cuantos_pares, i: ENTERO 
   promedio: REAL 
Proceso
   acum_pares := 0 
   cuantos_pares := 0

   Para i:=1 hasta 10 Hacer 
      Escribir("Ingrese un número entero: ") 
      Leer(numero) 

      Si numero MOD 2 = 0 Entonces 
         acum_pares := acum_pares + numero 
         cuantos_pares := cuantos_pares + 1 
      FS
   FP 

   Si cuantos_pares > 0 Entonces 
      promedio := acum_pares / cuantos_pares 
      Escribir("El promedio de los números pares es: ", promedio)
   Sino 
      Escribir("No se ingresaron números pares.")     
   FS
FinAccion

// Consigna Propuesta: Se tiene una secuencia de caracteres (entrada) que contiene una oración terminada en punto ("."). Contar cuántas veces aparece la letra "a" (minúscula o mayúscula) en toda la secuencia.

Accion contar_a Es 
AMBIENTE 
   sec: SECUENCIA DE CARACTERES 
   v: CARACTER 
   cont: ENTERO 
PROCESO
   ARR(sec);AVZ(sec,v);
   cont := 0
   Mientras NFDS(sec) Hacer 
      Mientras v <> '.' Hacer 
         si v = 'a' O v = 'A' Entonces 
            cont := cont + 1
         FIN_SI
         AVZ(sec,v)
      FIN_MIENTRAS 
      AVZ(sec,v) //avanzo el punto
   FIN_MIENTRAS
   Escribir("La letra 'a' aparece ", cont, " veces en la secuencia.")
FinAccion

// Consigna: se tiene una secuencia de caracteres con las ventas del día. La secuencia tiene este formato: CCVCCV...# (Donde C es el código de categoría de producto - 'A', 'B' o 'C' - y V es el valor de la venta - un dígito del '0' al '9'). La secuencia está ordenada por categoría. Se pide informar el total vendido por cada categoría.
//AA0BB1CC2#
ACCIÓN Ventas_Por_Categoria ES
   AMBIENTE
      sec: SECUENCIA DE CARACTER
      v, cat_anterior: CARACTER
      total_cat: ENTERO
   PROCESO
      ARR(sec)
      AVZ(sec, v)

      MIENTRAS v <> '#' HACER
         cat_anterior := v  // Resguardo la categoría actual
         total_cat := 0     // Limpio el acumulador para ESTA categoría

         // Ciclo de la misma categoría
         MIENTRAS (v <> '#') Y (v = cat_anterior) HACER
               AVZ(sec, v) // Salto la categoría 
               AVZ(sec, v) // Leo el valor de la venta
               
               total_cat := total_cat + VALOR(v)
               
               AVZ(sec, v) // Salto el valor para ir a la siguiente Categoría
         FIN_MIENTRAS

         ESCRIBIR("Total de la categoría ", cat_anterior, " es: ", total_cat)
      FIN_MIENTRAS
FIN_ACCIÓN

//Intentá definir el AMBIENTE para un archivo físico llamado ventas.dat que contenga registros con:cod_cat: cadena de caracteres.importe: numérico real.

Accion archivos Es 
   AMBIENTE
      ventas: REGISTRO
         cod_cat: AN(10)
         importe: REAL
      FIN_REGISTRO 
      arch: ARCHIVO de ventas
      reg: ventas

//parcial 2024 corte de control

Accion ejercicio2 Es
   AMBIENTE
      fechas = Registro 
         aaaa: entero 
         mm: 1..12 
         dd: 1..31
      FR

      peaje = Registro 
         clave = Registro 
            fecha: fechas 
            categoria: ('Auto','Camion','Moto')
            patente: AN(10)
         FR
         cant_pases: ENTERO
      FR 

      salida = Registro 
         fecha: fechas
         categoria: ('Auto','Camion','Moto')
         patente: AN(10)
      FR

      arch: archivo de peaje ordenado por clave
      reg: peaje
      sarch: archivo de salida
      sreg: salida   

      resg_anio, resg_mes, resg_dia: ENTERO

      //punto a
      total_anio, total_mes, total_dia, total_gral: ENTERO
      //punto b
      fecha_usu: fechas

      Procedimiento CorteDia() Es 
         Escribir('La cantidad total de vehiculos que pasaron mas de dos veces por dia es: ',total_dia)
         total_mes:= total_mes + total_dia
         total_dia:=0
         resg_dia:=reg.clave.fecha.dd
      FP
      
      Procedimiento CorteMes() Es 
         CorteDia()
         Escribir('La cantidad total de vehiculos que pasaron mas de dos veces por dia, en el mes es:',total_mes)
         total_anio:= total_anio + total_mes
         total_mes:=0
         resg_mes:=reg.clave.fecha.mm
      FP

      Procedimiento CorteAnio() Es 
         CorteMes()
         Escribir('La cantidad total de vehiculos que pasaron mas de dos veces por dia, en el anio es:',total_anio)
         total_gral:= total_gral + total_anio
         total_anio:=0
         resg_anio:=reg.clave.fecha.aaaa
      FP

   Proceso
      ABRIR E/(arch);Leer(arch,reg);
      ABRIR /S(sarch)

      resg_anio:=reg.clave.fecha.aaaa
      resg_mes:=reg.clave.fecha.mm
      resg_dia:=reg.clave.fecha.dd

      total_anio:=0;total_mes:=0;total_dia:=0;total_gral:=0;

      //punto b
      Escribir('Ingrese una anio: ');Leer(fecha_usu.aaaa);
      Escribir('Ingrese un mes: ');Leer(fecha_usu.mm);
      Escribir('Ingrese un dia: ');Leer(fecha_usu.dd);

      Mientras NFDA(arch) Hacer
         Si reg.clave.fecha.aaaa <> resg_anio Entonces
            CorteAnio()
         Sino 
            Si reg.clave.fecha.mm <> resg_mes Entonces
               CorteMes()
            Sino 
               Si reg.clave.fecha.dd <> resg_dia Entonces
                  CorteDia()
               FSi
            FSi
         FSi

         //punto a
         Si reg.cant_pases > 2 Entonces
            total_dia:= total_dia + 1
         FSi

         //punto b
         Si (reg.clave.fecha.aaaa = fecha_usu.aaaa) Y (reg.clave.fecha.mm = fecha_usu.mm) Y (reg.clave.fecha.dd = fecha_usu.dd)Entonces
            sreg.fecha:=reg.clave.fecha
            sreg.categoria:=reg.clave.categoria
            sreg.patente:=reg.clave.patente
            Grabar(sarch,sreg)
         FSi

         Leer(arch,reg)
      FM

      //cierres finales
      CorteAnio()
      Escribir('El total general de pases es:',total_gral)

      CERRAR(arch)
      CERRAR(sarch)
FinAccion

//parcial 2025 secuencia

Accion fifa Es 
   Ambiente
      sec1,sec2: SECUENCIA DE CARACTERES
      v1,v2: CARACTER
      salida: SECUENCIA DE CARACTERES

      goles,cantJugadores,cantPorConf, decena, unidad, roja, asistencia, m1,m2,m3,m4, totalMinutos: ENTERO

   Proceso
      ARR(sec1);AVZ(sec1,v1);
      ARR(sec2);AVZ(sec2,v2);
      CREAR(salida);
      Mientras NFDS(sec1) y NFDS(sec2) Hacer
         cantPorConf:=0;
         Mientras v1 <> '$' Hacer
            
            Mientras v1 <> '-' Hacer
               AVZ(sec1,v1)
            FM
            AVZ(sec1,v1) //parado en el primer caracter del nombre de equipo

            cantJugadores:=0;
            //guardo el nombre del equipo en la salida porque se asume que al menos 1 jugador cumple con la condicion
            Mientras v1 <> '-' Hacer
               Grabar(salida,v1)
               AVZ(sec1,v1)
            FM
            GRABAR(salida,'/')
            AVZ(sec1,v1) //parado en el nombre del jugador

            Mientras v1 <> '%' Hacer
               Mientras v1 <> '#' Hacer
                  //obtengo goles
                  decena:= VALOR(v2)*10
                  AVZ(sec2,v2)
                  unidad:= VALOR(v2)
                  goles:= decena+unidad
                  AVZ(sec2,v2)
                  //obtengo asistencias
                  decena:= VALOR(v2)*10
                  AVZ(sec2,v2)
                  unidad:= VALOR(v2)
                  asistencia:=decena+unidad
                  AVZ(sec2,v2)
                  //estoy en el inicio de los minutos
                  m1:=VALOR(v2)*1000;AVZ(sec2,v2)
                  m2:=VALOR(v2)*100;AVZ(sec2,v2)
                  m3:=VALOR(v2)*10;AVZ(sec2,v2)
                  m4:=VALOR(v2);AVZ(sec2,v2)
                  totalMinutos:=m1+m2+m3+m4
                  Si totalMinutos >= 1000 Entonces
                     cantPorConf:=cantPorConf+1 //ej3
                  FS
                  //estoy en amarilla
                  AVZ(sec2,v2)
                  //estoy en roja
                  roja:=VALOR(v2)
                  AVZ(sec2,v2) //estoy en la marca
                  AVZ(sec2,v2) //me quedo en el siguiente jugador
                  //01 01 0270 0 0|#|-

                  //ej1
                  Si (goles >= (3*(asistencia+roja))) Entonces
                     Mientras v1<>'*'Hacer
                        GRABAR(salida,v1)
                        AVZ(sec1,v1)
                     FM
                     GRABAR(salida,'_')
                     Mientras v1 <>'#' Hacer
                        AVZ(sec1,v1)
                     FM 
                  Sino
                     Mientras v1<>'#' Hacer
                        AVZ(sec1,v1)
                     FM
                  FS
                  cantJugadores:=cantJugadores+1
               FM
               AVZ(sec1,v1) //otro jugador o equipo
            FM
            AVZ(sec1,v1) //parado en el nombre del siguiente equipo o posible confederación
            GRABAR(salida,'%')
            //ej2
            Escribir('La cantidad de jugadores del equipo actual es de: ',cantJugadores)
         FM
         //ej3
         Escribir('La cantidad de jugadores que jugaron mas de 1000 minutos en la confederacion actual es: ',cantPorConf)
      FM
      CERRAR(sec1);CERRAR(sec2);CERRAR(salida)
FIN_ACCIÓN
//salida:= Real Madrid/Luka Modric_%
// 01 01 0270 0 0|#|0403031210|#|0201024500#0501030010|#|0600028001|#|*|
// UEFA-RealMadrid-LukaModric*3801#ViniciusJr*2402#JudeBellingham*2103#%ManchesterCity-KevinDeBruyne*3304#ErlingHaaland*2405#PhilFoden*2306#  |%|equipo  |$|confederacion


//=================2do parcial========================
Accion gimnasio Es 
   Ambiente
      socios=REGISTRO   
         dni:N(8)
         estado:('Activo', 'Moroso')
      FR 
      arch_mae, mae_act: archivo de socios ordenado por dni 
      reg_mae, reg_act, aux: socios 

      pagos=REGISTRO
         dni:N(8)
      FR 
      arch_mov: archivo de pagos ordenado por dni 
      reg_mov: pagos 

      Procedimiento LeerMae() Es
         Leer(arch_mae,reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.dni:=HV
         FS
      FP
      Procedimiento LeerMov() Es 
         Si FDA(arch_mov) Entonces
            reg_mov.dni:=HV
         FS 
      FP 

      cantidad:Arreglo[1..2] de N(1)

   Proceso 
      ABRIR E/(arch_mae);LeerMae()
      ABRIR E/(arch_mov);LeerMov()
      ABRIR /S(mae_actualizado)

      Para i:=1 Hasta 2 Hacer 
         cantidad[i]:=0
      FP

      Mientra reg_mae.dni<>HV O reg_mov.dni<>HV Hacer
         Si reg_mae.dni < reg_mov.dni Entonces  
            reg_act:=reg_mae

            Si reg_act.estado = 'Activo' Entonces
               cantidad[1] := cantidad[1] + 1
            Sino
               cantidad[2] := cantidad[2] + 1
            FS

            Grabar(mae_act,reg_act)
            LeerMae()
         Sino
            Si reg_mae.dni = reg_mov.dni Entonces
               aux:=reg_mae
               Mientras reg_mae.dni = reg_mov.dni Hacer  
                  aux.estado:='Activo'
                  LeerMov()
               FM 
               reg_act:=aux
               
               Si reg_act.estado = 'Activo' Entonces
                  cantidad[1] := cantidad[1] + 1
               Sino
                  cantidad[2] := cantidad[2] + 1
               FS

               Grabar(mae_act,reg_act)
               LeerMae()
            Sino
               LeerMov()
               Escribir('ERROR')
            FS 
         FS 
      FM
      Escribir('Total activos: ',cantidad[1])
      Escribir('Total morosos: ',cantidad[2])
      CERRAR(mae_act); CERRAR(arch_mae); CERRAR(arch_mov)
FIN_ACCIÓN

// parcial 2025 actualizacion

Accion primerejercicio Es 
   Ambiente 
      formato_fecha=Registro
         aaaa: N(4)
         mm: 1..12
         dd: 1..31
      FR 

      formato_clave=Registro
         tipo_equipo: AN(30)
         nro_equipo: N(2)
      FR

      equipo=Registro 
         clave=formato_clave
         fecha_adquisicion: formato_fecha
         fecha_ult_mantenim: formato_fecha
         horas_de_uso: 0..23
         disponibilidad: ('Si', 'No')
      FR 

      arch_mae, mae_act: archivo de equipo ordenado por clave 
      reg_mae, reg_act,aux: equipo

      novedades=Registro 
         clave=formato_clave
         tipo_novedad: 1..3
         fecha_novedad: formato_fecha
         hora_inicio: 0..23
         hora_fin: 0..23
         nro_circuito: 1..6
         id_usuario: N(4)
      FR 
      arch_mov: archivo de novedades ordenado por clave
      reg_mov: novedades 

      Procedimiento LeerMae() Es 
         Leer(arch_mae,reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.clave:=HV
         FS
      FP 

      Procedimiento LeerMov() Es 
         Leer(arch_mov,reg_mov)
         Si FDA(arch_mov) Entonces  
            reg_mov.clave:=HV 
         FS 
      FP 

      //ej2
      cant_equipo, horas_usu: ENTERO 
      //ej3
      cant_baja:ENTERO 

      Procedimiento Procesos_Iguales() Es 
         Segun reg_mov.tipo_novedad Hacer 
            =1: Escribir('ALTA')
            =2: aux.horas_de_uso:=aux.horas_de_uso+diff(reg_mov.hora_inicio,reg_mov.hora_fin)
            =3: aux.disponibilidad:= 'No'; cant_baja:=cant_baja+1
         FS
      FP 

      //ej2
      Procedimiento Estadistica() Es
         Si (aux.fecha_ult_mantenim.mm = 1 O aux.fecha_ult_mantenim.mm = 2) Y (aux.horas_de_uso > horas_usu) Entonces
            cant_equipo := cant_equipo + 1
         FS
      FP

   Proceso
      ABRIR E/(arch_mae); LeerMae()
      ABRIR E/(arch_mov); LeerMov()
      ABRIR /S(mae_act)

      cant_equipo:=0;cant_baja:=0

      Escribir('Ingrese las horas de uso: '); Leer(horas_usu)

      Mientras reg_mae.clave<>HV O reg_mov.clave<>HV Hacer 
         Si reg_mae.clave < reg_mov.clave Entonces 
            reg_act:=reg_mae
            Estadistica()
            Grabar(mae_act, reg_act)
            LeerMae()
         Sino 
            //movimientos
            Si reg_mae.clave = reg_mov.clave Entonces 
               aux:=reg_mae
               Mientras reg_mae.clave = reg_mov.clave Hacer 
                  Procesos_Iguales()
                  LeerMov()
               FM
               Estadistica()
               reg_act:=aux
               Grabar(mae_act,reg_act)
               LeerMae()
            Sino
               //caso donde mae > mov o sea, hay alta
               Si reg_mov.tipo_novedad=1 Entonces
                  aux.clave := reg_mov.clave 
                  aux.fecha_adquisicion:=reg_mov.fecha_novedad
                  aux.fecha_ult_mantenim:=reg_mov.fecha_novedad
                  aux.horas_de_uso:=diff(reg_mov.hora_inicio,reg_mov.hora_fin)
                  aux.disponibilidad:='Si'
                  LeerMov()
                  Mientras aux.clave=reg_mov.clave Hacer 
                     Procesos_Iguales()
                     LeerMov()
                  FM
                  Estadistica() 
                  reg_act:=aux 
                  Grabar(mae_act,reg_act)
               Sino 
                  Escribir('ERROR: PRESTAMOS Y BAJAS NO POSIBLES')
               FS
               LeerMov()
            FS 
         FS 
      FM 
      Escribir('Cantidad de equipo en enero y febrero: ',cant_equipo)
      Escribir('La cantidad de equipos que se dieron de baja es de: ',cant_baja)
      CERRAR(arch_mae)
      CERRAR(arch_mov)
      CERRAR(mae_act)
FIN_ACCIÓN

// procesos estadistico (pertenece arriba)
Accion pestadistico(costos: Arreglo[1..6] de Real) Es 
   Ambiente 
      formato_fecha=Registro
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      novedades=Registro 
         tipo_equipo: AN(30)
         nro_equipo: N(2)
         tipo_novedad: 1..3
         fecha_novedad: formato_fecha
         hora_inicio: 0..23
         hora_fin: 0..23
         nro_circuito: 1..6
         id_usuario: N(4)
      FR
      arch: archivo de novedades ordenado por tipo_equipo y nro_equipo
      reg: novedades

      usuario=Registro 
         id_usuario: N(4)
         dni:N(8)
         sexo: ('m','f')
         apellido_y_nombre: AN(255)
         lugar_de_origen:1..4
         edad:N(2)
      FR 
      arch_index: archivo de usuario indexado por id_usuario
      reg_index: usuario

      A:Arreglo[1..7,1..5] de Real 
      i,j: Entero 
      importe_recaudado:REAL
      monto_usu:REAL

      Funcion lugar(n:ENTERO):AN Es 
         Segun n Hacer 
            =1:'Chaco'
            =2:'Otra Provincia'
            =3:'País limitrofe'
            =4:'Otro Pais'
         FS 
      FS 
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)
      ABRIR E/(arch_index);

      Para i=1 hasta 7 Hacer 
         Para j=1 hasta 5 Hacer 
            A[i,j]:=0
         FP 
      FP 

      Escribir('Ingrese un monto: ');Leer(monto_usu)

      Mientras NFDA(arch) Hacer 
         Si reg.tipo_novedad = 2 Entonces 
            reg_index.id_usuario := reg.id_usuario
            Leer(arch_index, reg_index)
            Si EXISTE Entonces   
               i := reg.nro_circuito
               j := reg_index.lugar_de_origen

               importe_recaudado := diff(reg.hora_inicio, reg.hora_fin) * costos[i]
               // cargo matriz
               A[i, j] := A[i, j] + importe_recaudado 
               A[i, 5] := A[i, 5] + importe_recaudado // Total por circuito
               A[7, j] := A[7, j] + importe_recaudado // Total por origen
               A[7, 5] := A[7, 5] + importe_recaudado // Total General (opcional pero recomendado)
            Sino 
               Escribir('ERROR. NO EXISTE EL USUARIO: ', reg.id_usuario)
            FS 
         FS 
         Leer(arch, reg)
      FM 

      // --- INFORMES ---
      Para i := 1 hasta 6 Hacer 
         Escribir('Circuito N°: ', i, ' - Total Recaudado: $', A[i, 5])
         Para j := 1 hasta 4 Hacer  
            Escribir('  Origen: ', lugar(j), ' - Importe: $', A[i, j])
         FP 
      FP 
      Para j := 1 hasta 4 Hacer 
         Escribir('El total para ', lugar(j), ' fue: $', A[7, j])
         // ITEM B: Se compara el TOTAL del origen contra el monto ingresado
         Si A[7, j] > monto_usu Entonces 
            Escribir(lugar(j), ' superó el monto de $', monto_usu)
         FS
      FP
      Cerrar(arch);CERRAR(arch_ind)
FIN_ACCIÓN
      


//parcial 2025 indexado

Accion indexado Es 
   Ambiente 
      formato_fecha=Registro
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      formato_clave=Registro 
         cod_lib:N(8)
         cod_ejemplar:N(10)
      FR 

      ejemplares=Registro
         clave:formato_clave
         disponible:('Si','No')
         estado:('Bien','Reparacion','Baja')
         fecha:formato_fecha
      FR 
      arch_mae,mae_act: archivo de ejemplares ordenado por clave 
      reg_mae,reg_act,aux: ejemplares

      novedades=Registro 
         clave: formato_clave
         tipo_novedad: 1..5
         fecha_novedad: formato_fecha
         id_usuario: N(8)
      FR 
      arch_mov: archivo de novedades ordenado por clave 
      reg_mov: novedades 

      libros=Registro
         cod_lib: N(8)
         titulo: AN(60)
         ISBN: N(13)
         descripcion: AN(255)
         tipo_libro: ('A','D','L','N')
         cant_dias_max: N(2)
      FR 
      arch_index: archivo de libros indexado por cod_lib
      reg_index: libros 

      Procedimiento LeerMae() Es 
         Leer(arch_mae,reg_mae)
         Si FDA(arch_mae) Entonces 
            reg_mae.clave:=HV
         FS 
      FP 

      Procedimiento LeerMov() Es 
         Leer(arch_mov, reg_mov)
         Si FDA(arch_mov) Entonces
            reg_mov.clave:=HV 
         FS 
      FP 

      cant_dia:ENTERO
      cant_nuevo:ENTERO //ej_b

      Procedimiento Procesos_Iguales() Es 
         Segun reg_mov.tipo_nov Hacer 
            =1:
               cant_nuevo:=cant_nuevo+1 //ej_b
               aux.disponible:='Si' 
               aux.fecha:=' '
            =2: 
               reg_index.cod_lib:=reg_mov.clave.cod_lib
               Leer(arch_index,reg_index)
               Si EXISTE Entonces
                  aux.disponible:='No'
                  cant_dia:=sumar_dias(aux.fecha,reg_index.cant_dias_max)
                  aux.fecha:= reg_mov.fecha_novedad + cant_dia
               Sino 
                  Escribir('ERROR: REFERENCIA INEXISTENTE')
               FS
            =3:
               aux.disponible:='Si'
               aux.fecha:=' '
            =4:
               aux.disponible:='No'
               aux.estado:='Reparacion'
               aux.fecha:=reg_mov.fecha_novedad
            =5:
               aux.disponible:='No'
               aux.estado:='Baja'
               aux.fecha:=reg_mov.fecha_novedad
         FS 
      FP
   Proceso 
      ABRIR E/(arch_mae);LeerMae()
      ABRIR E/(arch_mov);LeerMov()
      ABRIR E/(arch_index)
      ABRIR /S(mae_act)

      cant_nuevo:=0

      Mientras reg_mae.clave<>HV O reg_mov.clave<>HV Hacer 
         Si reg_mae.clave < reg_mov.clave Entonces 
            reg_act:=reg_mae
            Grabar(mae_act,reg_act)
            LeerMae()
         Sino 
            //mae=mov
            Si reg_mae.clave=reg_mov.clave Entonces
               aux:=reg_mae
               Mientras reg_mae.clave=reg_mov.clave Hacer 
                  Procesos_Iguales()
                  LeerMov()
               FM 
               reg_act:=aux 
               Grabar(mae_act,reg_act)
               LeerMae()
            Sino 
            //altas mae>mov 
               reg_index.cod_lib:=reg_mov.clave.cod_lib
               Leer(arch_index,reg_index)
               Si EXISTE Entonces
                  //alta
                  Si reg_mov.tipo_novedad=1 Entonces
                     aux.clave:=reg_mov.clave
                     aux.disponible:='Si'
                     aux.estado:='Bien'
                     aux.fecha:=' '
                     cant_nuevo:=cant_nuevo+1 //ej_b
                     LeerMov() // siempre después del alta
                     Mientras aux.clave=reg_mov.clave Hacer 
                        Procesos_Iguales()
                        LeerMov()
                     FM 
                     reg_act:=aux 
                     Grabar(mae_act,reg_act)
                  Sino
                     Escribir('ERROR. NO ES POSIBLE DAR PRESTAMOS, DEVOLUCION, MANTENIMIENTO NI BAJAS')
                     LeerMov() //acá también
                  FS
               Sino 
                  Escribir('ERROR. REFERENCIAS INEXISTENTES')
               FS
               LeerMov()
            FS
         FS
      FM
      Escribir('La cantidad de ejemplares nuevos son: ',cant_nuevo)
      CERRAR(arch_mae)
      CERRAR(arch_mov)
      CERRAR(arch_index)
      CERRAR(mae_act)
FIN_ACCIÓN

// proceso estadistico 2025 (pertenece al ejercicio de arriba)

Accion pestadistico Es 
   Ambiente 
      formato_fecha=Registro  
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      novedades=Registro 
         cod_lib: N(8)
         cod_ejemplar:N(10)
         tipo_novedad: 1..5
         fecha_novedad: formato_fecha
         id_usuario: N(5)
      FR 
      arch: archivo de novedades ordenado por cod_lib, cod_ejemplar, tipo_novedad y fecha_novedad
      reg: novedades

      formato_arr=Registro
         cantidad:ENTERO
      FR 

      arr:Arreglo[1..6,1..7] de formato_arr

      Funcion NombreMes(n:ENTERO):AN Es 
         Segun n Hacer 
            =1: 'Enero'
            =2: 'Febrero'
            =3: 'Marzo'
            =4: 'Abril'
            =5: 'Mayo'
            =6: 'Junio'
         FS
      FS
   
      mayor_pres,mayor_mes:ENTERO 

   PROCESO 
      ABRIR E/(arch);Leer(arch,reg)

      //INICIALIZO EL ARREGLO 
      Para i:=1 hasta 6 Hacer 
         Para j:=1 hasta 7 Hacer 
            arr[i,j].cantidad:=0
         FP
      FP 

      //cargo el arreglo
      Mientras NFDA(arch) Hacer 
         Si (reg.fecha_novedad.mm >= 1 Y reg.fecha_novedad.mm <= 6) Y (reg.fecha_novedad.aaaa=2025) Entonces
            i:=reg.tipo_novedad
            j:=reg.fecha_novedad.mm

            arr[i,j].cantidad:=arr[i,j].cantidad+1
            arr[i,7].cantidad:=arr[i,7].cantidad+1
            arr[6,j].cantidad:=arr[6,j].cantidad+1
            arr[6,7].cantidad:=arr[6,7].cantidad+1
         FS
         Leer(arch,reg)
      FM 

      //recorro solo los 5 tipos y los 6 meses
      Para i:=1 hasta 5 Hacer
         //ej1
         Escribir('Para el movimiento: ',i)
         Para j:=1 hasta 6 Hacer
            //ej1
            Escribir('En el mes: ',nombreMes(j), ' Cantidad: ', arr[i,j].cantidad)
         FP
         //ej2 
         Escribir('Total por tipo: ',arr[i,7].cantidad)
      FP
      //ej2
      Para j:=1 hasta 6 Hacer 
         Escribir('En el mes: ', nombreMes(j), ' el total fue: ',arr[6,j].cantidad)
      FP 
      Escribir('Total general: ',arr[6,7].cantidad)

      //ej b
      mayor_pres:=LV
      mayor_mes:=0

      Para j:=1 hasta 6 Hacer 
         Si arr[2,j].cantidad > mayor_pres Entonces 
            mayor_pres:=arr[2,j].cantidad
            mayor_mes:=j
         FS 
      FP 
      Si mayor_mes>0 Entonces
         Escribir('El mes con mayor cantidad de prestamos fue: ', nombreMes(mayor_mes),' con: ',mayor_pres)
      Sino 
         Escribir('No se registraron prestamos en el semestre')
      FS
      CERRAR(arch)
FIN_ACCIÓN

//================== tercer parcial =====================
// Lista Simplemente Enlazada (Simple) Es una cadena unidireccional donde cada nodo conoce solo al siguiente.
Ambiente
   NODO = Registro
      dato: Entero
      prox: Puntero a NODO
   FinRegistro
   prim, p, q, a: Puntero a NODO
// Algoritmo de Inserción Ordenada (Descendente): Este algoritmo busca el hueco correcto para que la lista siempre esté organizada.
Proceso
   Nuevo(q)           // Creo el nuevo nodo
   *q.dato := valor   // Le asigno el dato
   a := nil           // Puntero anterior
   p := prim          // Puntero actual

   // Recorrido para encontrar la posición
   Mientras (p <> nil) y (*q.dato < *p.dato) Hacer 
      a := p 
      p := *p.prox   // Avanzo
   FinMientras

   Si a = nil Entonces   // Caso: Insertar al principio
      *q.prox := prim 
      prim := q 
   sino                  // Caso: Insertar en el medio o final
      *a.prox := q 
      *q.prox := p 
   FinSi

// Lista Doblemente Enlazada (Doble)
// Aquí cada nodo tiene "dos brazos": uno que agarra al de adelante (prox) y otro al de atrás (ant).

Ambiente
   NODO_D = Registro
      dato: Entero
      ant: Puntero a NODO_D
      prox: Puntero a NODO_D
   FinRegistro
   primd, ult, d, q: Puntero a NODO_D
//q significa siempre NUEVO
//Es el puntero que apunta al nodo que acabas de crear con la instrucción Nuevo(q). Imaginalo como un vagon nuevo que todavía está suelto y quieres enganchar a la formación.

//p o a veces d es para recorrer la lista (como AVZ). Se mueve de nodo en nodo (d := *d.prox) buscando en qué lugar debe ir q.

//a pero en simple, en doble ya existe, es el ANTERIOR, guarda la info del nodo que está atrás de p. En doblemente tenemos el ant del nodo y se accede *d.ant

//prim o primd es la entrada, es el puntero que apunta siempre al primer nodo de la lista entonces nunca se usa este para desplazarse en las listas porque si se pierde, se pierde toda la lista completa

//ult o ultd es la salida, puntero que apunta al ultimo nodo. se usa mucho en dobles para insertar al final o recorrer hacia atrás

// Algoritmo de Inserción Ordenada: Es más complejo porque hay que conectar más "brazos".
Proceso
   Si primd = nil Entonces  // Lista vacía
      primd := q 
      ult := q 
      *q.prox := nil 
      *q.ant := nil
   Sino 
      d := primd
      Mientras (d <> nil) y (*q.dato < *d.dato) Hacer
         d := *d.prox
      FinMientras

      Si d = primd Entonces // Insertar al principio
         primd := q
         *q.prox := d 
         *d.ant := q
         *q.ant := nil 
      Sino
         Si d = nil Entonces // Insertar al final
               *q.prox := nil
               *q.ant := ult 
               *ult.prox := q
               ult := q
         Sino                // Insertar en el medio
               *q.prox := d 
               *q.ant := *d.ant 
               *(*d.ant).prox := q // El anterior de d ahora apunta al nuevo
               *d.ant := q
         FinSi
      FinSi
   FinSi

// Lista Circular
// Es como una lista simple, pero el último nodo no apunta a nil, sino que vuelve al primero, cerrando un círculo.

// Diferencia clave en el Recorrido: El ciclo no termina cuando llegamos a nil, sino cuando el puntero "siguiente" vuelve a ser el inicio.
Ambiente
   NODO = Registro
      dato: Entero
      prox: Puntero a NODO
   FinRegistro
   prim, p, q, a: Puntero a NODO
Proceso
   p := prim
   Mientras (*p.prox <> prim) Hacer
      // Procesar datos (*p.dato)
      p := *p.prox
   FinMientras

//ej 4.6 github
Accion ej46github Es 
   Ambiente 
      formato_fecha=Registro 
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      clientes=Registro
         nombre:AN(255)
         nro_mesa:N(3)
         total_consumido:REAL 
         fecha_atencion: formato_fecha
      FR 
      arch: archivo de clientes ordenado por nombre 
      reg: clientes

      NODO = REGISTRO 
         dato: clientes 
         prox: PUNTERO A NODO 
      FR 
      prim,p,a,q: PUNTERO A NODO 

      nombre:AN
      monto,opc:ENTERO

   Proceso 

      Repetir
         Escribir('Eliga una opcion: 1- Añadir Cliente 2- Registrar consumo / Realizar cobro y Eliminar'); Leer(opc)
         
         Segun opc Hacer 
            =1:
               //agrego un nodo nuevo
               Nuevo(q)
               //cargar los valores a ese nodo
               Escribir('Nombre del cliente: '); Leer(*q.dato.nombre)
               Escribir('Asignar numero de mesa: '); Leer(*q.dato.nro_mesa)
               *q.dato.total_consumido:=0
               Escribir('Ingrese la fecha: '); Leer(*q.dato.fecha_atencion)

               a:=nil //el nodo anterior apunta a nil inicialmente
               p:= prim //me paro en el nodo inicial

               //busqueda
               Mientras (p<>NIL) Y (*q.dato.nombre>*p.dato.nombre) Hacer 
                  a:=p 
                  p:=*p.prox 
               FM 

               Si a=NIL Entonces 
                  //nuevo cliente es el primero alfabeticamente
                  *q.prox:=prim
                  prim:=q
               Sino 
                  *a.prox:= q
                  *q.prox:= p
               FS
            =2:
               Escribir('Ingresar nombre: '); Leer(nombre)
               Escribir('Ingrese monto: '); Leer(monto)

               p:=prim 
               a:=NIL //siempre
               Mientras (p<>NIL) Y (*p.dato.nombre<>nombre) Hacer
                  a:=p //siempre guardar el anterior
                  p:=*p.prox
               FM
               //salgo de acá y p está en el nombre que buscamos

               Si p<>NIL Entonces   
                  *p.dato.total_consumido:= *p.dato.total_consumido+monto
                  Escribir('Cliente: ',*p.dato.nombre)
                  Escribir('Fecha: ',*p.dato.fecha_atencion)
                  Escribir('Mesa: ', *p.dato.nro_mesa)
                  Escribir('Total: ',*p.dato.total_consumido)
                  //eliminacion
                  Si a=NIL Entonces 
                     prim:=*p.prox //primero de la lista
                  Sino
                     *a.prox:= *p.prox 
                  FS 
                  Disponer(p)
               Sino 
                  Escribir('No existe ese cliente')
               FS
            Otro caso: Escribir('ERROR')
         FS 
         Escribir('Para salir, pulse 3')
         Leer(opc)
      Hasta Que opc=3
FinAccion

//ej 4.12 github
Accion ej412(prim,ult:PUNTERO A NODO) Es 
   Ambiente
      DATOS_PEDIDO = Registro
         nombre: AN(50)
         direccion: AN(100)
         telefono: AN(20)
         total: Real
         estado: ('P','E') // 'P' (Pendiente) o 'E' (Enviado)
      FR

      NODO = Registro
         dato: DATOS_PEDIDO
         ant, prox: Puntero a NODO
      FinRegistro  

      p,q: PUNTERO A NODO

      nombre_buscado:AN 
      opc:ENTERO

      Procedimiento CrearNodo() Es 
         Nuevo(q)
         Escribir('NOMBRE: ');Leer(*q.dato.nombre)
         Escribir('DIRECCION: ');Leer(*q.dato.direccion)
         Escribir('TELEFONO: ');Leer(*q.dato.telefono)
         *q.dato.total:=0
         *q.dato.estado:='P'
      FP 
      Procedimiento CargaOrdenadaDoble() Es 
         //VACIA
         Si prim=NIL Entonces 
            prim:= q
            ult:= q
            *q.prox:=NIL 
            *q.ant:=NIL 
         Sino 
            p:=prim
            Mientras (p<>NIL) y (*q.dato.nombre > *p.dato.nombre) Hacer
               p:=*p.prox
            FM 
            //INCIO
            Si p = prim Entonces 
               *q.prox:=p
               *q.ant:=NIL
               *p.ant:=q
               prim:=q
            Sino 
               //FINAL
               Si p=NIL Entonces 
                  *q.prox:=NIL 
                  *q.ant:=ult 
                  *ult.prox:=q
                  ult:=q
               Sino 
                  //MEDIO
                  *q.prox:=*p
                  *q.ant:=*p.ant
                  *(*p.ant).prox:=q 
                  *p.ant:=q 
               FS 
            FS 
         FS 
      FP

      Procedimiento Eliminar() Es 
         Si p=prim Entonces 
            prim:=*p.prox 
            Si prim <> NIL Entonces 
               *prim.ant:=NIL 
            FS 
         Sino
            Si p=ult Entonces 
               ult:=*p.ant 
               *ult.prox:=NIL 
            Sino 
               *(*p.ant).prox:= *p.prox 
               *(*p.prox).ant:= *p.ant
            FS 
         FS 
         Disponer(p) 
      FP
      
      Procedimiento Busqueda() Es 
         Escribir('Ingrese el nombre a buscar: ');Leer(nombre_buscado)
         p:=prim 
         Mientras (p<>NIL) Y (*p.dato.nombre <> nombre_buscado) Hacer 
            p:=*p.prox
         FM

         Si p <> NIL Entonces 
            Si *p.dato.estado='P' Entonces
               *p.dato.estado:='E'
               Escribir('Pedido enviado a: ',*p.dato.direccion)
            Sino 
               Escribir('Ya fue enviado')
               Escribir('Desea eliminar? Pulse 0');Leer(opc)
               Si opc=0 Entonces
                  Eliminar()
               FS
            FS
         Sino 
            Escribir('Cliente no encontrado') 
         FS
      FP

   Proceso 
      Repetir 
         Escribir('Seleccione una opcion: ');Leer(opc)
         Segun opc Hacer 
            =1:
               CrearNodo()
               CargaOrdenadaDoble()
            =2: 
               Busqueda()
            Otro: 
               Escribir('ERROR')
         FS 

         Escribir('Desea Salir? Pulse 3');Leer(opc)
      Hasta Que opc=3 
FIN_ACCIÓN


//recursividad 
Funcion SumarVector(V: ARREGLO, i: ENTERO, N: ENTERO) : ENTERO Es
Proceso
   Si i > N Entonces
      SumarVector:=0
   Sino
      SumarVector:=V[i]+SumarVector(V,i+1,N)
   FS
FP

Funcion MaximoVector(V:ARREGLO, i:ENTERO, N:ENTERO):ENTERO Es
   max_resto: ENTERO
   Si i = N Entonces
      // CASO BASE: Llegamos al último casillero. 
      // El máximo de un solo número es el número mismo.
      MaximoVector := V[i]
   Sino
      // PASO RECURSIVO: 
      // 1. Llamamos a la función para que busque el máximo en el resto del vector
      max_resto := MaximoVector(V,i+1,N)

      // 2. Comparamos nuestro valor actual con el máximo que vino del resto
      Si V[i] > max_resto Entonces
         MaximoVector := V[i]
      Sino
         MaximoVector := max_resto
      FS
   FS
FF

//Hacer una función que sume todos los valores de una lista simple de números.
Funcion SumarLista(p: PUNTERO A NODO): ENTERO Es
   Si p = NIL Entonces
      SumarLista:=0
   Sino
      SumarLista:=*p.valor + SumarLista(*p.prox)
   FS
FF

Funcion ExisteDNI(p: PUNTERO A NODO, dni_buscado: N(8)) : BOOLEANO Es
   Si p = NIL Entonces
      ExisteDNI := FALSO
   Sino
      Si *p.dni = dni_buscado Entonces
         ExisteDNI := VERDADERO
      Sino
         ExisteDNI := ExisteDNI(*p.prox, dni_buscado)
      FS
   FS
FF 

//Imprimir los nombres de una lista simple en orden inverso (del último al primero)
Procedimiento Inverso(p:PUNTERO A NODO) Es 
   Si p<>NIL Entonces 
      Inverso(*p.prox)
      Escribir('Nombre: ',*p.nombre)
   FS 
FF

//Conteo Condicional
//Escribir una función recursiva que cuente cuántas swifties de la lista tienen más de 18 años (suponiendo que el nodo tiene un campo edad).
Funcion ContarMayores(p: PUNTERO A NODO) : ENTERO Es
   Si p = NIL Entonces
      ContarMayores:=0
   Sino
      Si *p.edad > 18 Entonces
         ContarMayores:=1+ContarMayores(*p.prox)
      Sino
         ContarMayores:=ContarMayores(*p.prox)
      FS
   FS
FF

//Encontrar el nombre de la Swiftie con mayor edad en la lista
Funcion MayorEdad(p:PUNTERO A NODO):PUNTERO A NODO Es
   aux:PUNTERO A NODO
   Si *p.prox=NIL Entonces 
      MayorEdad:=p
   Sino
      aux:=MayorEdad(*p.prox) 
      
      Si *p.edad > *aux.edad Entonces
         MayorEdad:=p
      Sino 
         MayorEdad:=aux
      FS 
   FS 
FF

//guia trabajos practico

//Ejercicio 5.1.1  Calcular el factorial de un número positivo n. Tener en cuenta la definición matemática de n!

Funcion Factorial(n:Entero):Entero Es 
   Si n=1 Entonces
      Factorial:=1 
   Sino 
      Factorial:=n*Factorial(n-1)
   FS 
FF

//5.1.2 Dado un número n  como parámetro de entrada, calcular el n-ésimo número de la serie de Fibonacci. Tener en cuenta la siguiente definición

Funcion Fib(n:ENTERO):ENTERO Es 
   Si n=1 o n=2 Entonces   
      Fib:=1
   Sino 
      Fib:=Fib(n-1)+Fib(n-2)
   FS 
FS 

// 5.1.3 Dados dos números a y b Calcule la potencia  usando sólo multiplicaciones sucesivas.
Funcion Pot(a,b:ENTERO):ENTERO 
   Si b=0 Entonces 
      Pot:=1 
   Sino 
      Si b=1 Entonces
         Pot:=a 
      Sino 
         Pot:=a*Pot(a,b-1)
      FS 
   FS 
FS 

//Ejercicio 5.1.4¶
// Construir un algoritmo recursivo que permita determinar si los dígitos de un número n dado son todos pares.

Funcion DigPar(n:ENTERO):BOOLEANO Es 
   digito:= n MOD 10
   Si n<10 Entonces
      DigPar:=(digito MOD 2 = 0 )
   Sino
      Si (digito MOD 2 <> 0) Entonces
         DigPar:=FALSO 
      Sino 
         DigPar:= DigPar(n DIV 10)
      FS 
   FS 
FS

Procedimiento EliminarDoble() Es 
   aux:=p 
   Si prim=ult Entonces
      prim=:NIL 
      ult:=NIL 
   Sino 
      Si p=prim Entonces
         prim:=*p.prox 
         *prim.ant:=NIL 
      Sino 
         Si *p.prox=NIL Entonces
            ult:=*p.ant
            *ult.prox:=NIL 
         Sino 
            *(*p.ant).prox:=*p.prox 
            *(*p.prox).ant:=*p.ant 
         FS 
      FS 
   FS 
   p:=*p.prox 
   Disponer(aux)
FP
//ubicar ult en su lugar
ult:=prim 
Mientras *ult.prox<>NIL Hacer 
   ult:=*ult.prox 
FM 

p:=prim 
Mientras p<>NIL Hacer 
   Si NO(Cumple()) Entonces
      CargarListaX()
      EliminarDoble()
   Sino 
      p:=*p.prox 
   FS 
FM 
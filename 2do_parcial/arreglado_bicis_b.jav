Accion gestion_ecobici Es
Ambiente
   fecha = Registro
      anio: N(4)
      mes: N(2)
      dia: N(2)
   FR

   clave_bici = Registro
      nro_serie: ENTERO
      modelo: AN(30)
   FR

   Bicicleta = Registro
      clave: clave_bici
      fecha_adquisicion: AN(10)
      fecha_ult_mantenimiento: AN(10)
   FR

   Novedad = Registro
      clave: clave_bici
      tipo_novedad: 1..4 // 1: Alta, 2: Prestamo, 3: Mantenimiento, 4: Baja
      fecha_novedad: AN(10)
      hora_inicio: AN(5)
      hora_fin: AN(5)
      circuito_nro: 1..6
      id_usuario: ENTERO
   FR

   archbici, archsal: Archivo Secuencial de Bicicleta ordenado por clave
   archnove: Archivo Secuencial de Novedad ordenado por clave

   regbici, regsal, aux: Bicicleta
   regnove: Novedad

   // Filas: 1 a 6 (Circuitos) | Columnas: 1 (Intensivo > 6hs) y 2 (Recreativo <= 6hs)
   Matriz_Costos: Arreglo [1..6, 1..2] de REAL
   circuito_buscado: ENTERO
   cant_prestamos: ENTERO
   total_recaudado: REAL
   horas_uso, precio_hora, costo_viaje: REAL
   es_baja: LOGICO
   clave_error: clave_bici

   Procedimiento LeerMae() Es
      Leer(archbici, regbici)
      Si FDA(archbici) Entonces
         regbici.clave.nro_serie := HV
         regbici.clave.modelo := "ZZZZ"
      FS 
   FP 

   Procedimiento LeerMov() Es
      Leer(archnove, regnove)
      Si FDA(archnove) Entonces
         regnove.clave.nro_serie := HV
         regnove.clave.modelo := "ZZZZ"
      FS 
   FP 

   // Procesa novedades para una bicicleta existente (Maestro = Novedad)
   Procedimiento ProcesarNovedadExistente() Es
      Segun regnove.tipo_novedad Hacer
         =1: // ALTA sobre registro existente -> ERROR
            Escribir("ERROR: La bicicleta Nro Serie ", regnove.clave.nro_serie, " Modelo ", regnove.clave.modelo, " ya existe. No se puede dar de alta nuevamente.")
         
         =2: // PRESTAMO -> Estadisticas
            horas_uso := diff_horas(regnove.hora_inicio, regnove.hora_fin)
            Si horas_uso > 6 Entonces
               precio_hora := Matriz_Costos[regnove.circuito_nro, 1]
               costo_viaje := 1500 + (precio_hora * horas_uso)
            Sino
               precio_hora := Matriz_Costos[regnove.circuito_nro, 2]
               costo_viaje := 1000 + (precio_hora * horas_uso)
            FS

            Si regnove.circuito_nro = circuito_buscado Entonces
               total_recaudado := total_recaudado + costo_viaje
               cant_prestamos := cant_prestamos + 1
            FS

         =3: // MANTENIMIENTO -> Actualiza fecha de ultimo mantenimiento
            aux.fecha_ult_mantenimiento := regnove.fecha_novedad

         =4: // BAJA -> Marca la bicicleta para eliminarla
            es_baja := VERDADERO
      FS
   FP

Proceso
   Abrir E/(archbici)
   Abrir E/(archnove)
   Abrir /S(archsal)

   LeerMae()
   LeerMov()

   Escribir("Ingrese el número de circuito a consultar (1 a 6): ")
   Leer(circuito_buscado)

   total_recaudado := 0
   cant_prestamos := 0

   Mientras (regbici.clave.nro_serie <> HV) O (regnove.clave.nro_serie <> HV) Hacer
      
      // CASO 1: LA CLAVE DEL MAESTRO ES MENOR (Bicicleta sin novedades)
      Si (regbici.clave.nro_serie < regnove.clave.nro_serie) O 
         (regbici.clave.nro_serie = regnove.clave.nro_serie Y regbici.clave.modelo < regnove.clave.modelo) Entonces
         
         regsal := regbici
         Grabar(archsal, regsal)
         LeerMae()

      Sino
         // CASO 2: LAS CLAVES SON IGUALES (Bicicleta existente con novedades)
         Si (regbici.clave.nro_serie = regnove.clave.nro_serie Y regbici.clave.modelo = regnove.clave.modelo) Entonces
            aux := regbici
            es_baja := FALSO

            Mientras (regbici.clave.nro_serie = regnove.clave.nro_serie Y regbici.clave.modelo = regnove.clave.modelo) Hacer
               ProcesarNovedadExistente()
               LeerMov()
            FM

            // Si no sufrio baja fisica, se graba en el maestro actualizado
            Si NO es_baja Entonces
               regsal := aux
               Grabar(archsal, regsal)
            FS

            LeerMae()

         Sino
            // CASO 3: LA CLAVE DEL MAESTRO ES MAYOR (Novedad para bicicleta que no esta en el Maestro)
            Si regnove.tipo_novedad = 1 Entonces
               // ALTA DE NUEVA BICICLETA
               aux.clave := regnove.clave
               aux.fecha_adquisicion := regnove.fecha_novedad
               aux.fecha_ult_mantenimiento := " " // Se inicializa vacia segun consigna
               es_baja := FALSO
               
               LeerMov() // Avanza la novedad del alta

               // Procesa si hay mas novedades consecutivas para la bici recien dada de alta
               Mientras (aux.clave.nro_serie = regnove.clave.nro_serie Y aux.clave.modelo = regnove.clave.modelo) Hacer
                  ProcesarNovedadExistente()
                  LeerMov()
               FM

               Si NO es_baja Entonces
                  regsal := aux
                  Grabar(archsal, regsal)
               FS
            Sino
               // ERROR: Intento de Prestamo, Mantenimiento o Baja sobre bicicleta inexistente
               Escribir("ERROR: Novedad tipo ", regnove.tipo_novedad, " invalida para bicicleta inexistente Nro Serie ", regnove.clave.nro_serie)
               
               clave_error := regnove.clave
               // Se descartan todas las novedades erroneas para esa misma clave inexistente
               Mientras (regnove.clave.nro_serie = clave_error.nro_serie Y regnove.clave.modelo = clave_error.modelo) Hacer
                  LeerMov()
               FM
            FS
         FS
      FS
   FM

   Escribir("--- ESTADISTICAS DEL CIRCUITO ", circuito_buscado, " ---")
   Escribir("Cantidad total de prestamos: ", cant_prestamos)
   Escribir("Total recaudado: $", total_recaudado)

   Cerrar(archbici)
   Cerrar(archnove)
   Cerrar(archsal)
FinAccion

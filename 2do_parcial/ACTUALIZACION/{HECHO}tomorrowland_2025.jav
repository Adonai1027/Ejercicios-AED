Accion ActualizacionTomorrowland(puestos_habilitados: Arreglo[1..500] de LOGICO) Es
   Ambiente
      PULSERA = Registro
         DNI: N(8)
         N_pulsera: N(8)
         Credito_total: REAL
         credito_bonos: REAL
      FR
      arch_mae, mae_act: archivo de PULSERA ordenado por DNI
      reg_mae, reg_act, aux: PULSERA

      ASISTENTE = Registro
         DNI: N(8)
         Nombre: AN(100)
         Telefono: AN(30)
         Pais: AN(50)
      FR
      arch_mov: archivo de RECARGAS ordenado por DNI
      reg_mov: RECARGAS

      RECARGAS = Registro
         N_operacion: N(12)
         DNI: N(8)
         id_puesto: 1..500
         monto: ENTERO
         alta: LOGICO
      FR
      // Archivo Indexado
      arch_asistente: archivo de ASISTENTE Indexado por DNI
      reg_asistente: ASISTENTE

      // Variables estadísticas e incisos
      cant_bonos_otorgados: ENTERO
      max_credito, bono: REAL
      max_dni: N(8)
      max_nombre: AN(100)

      // ----------------------------------------------------------------------
      // Función para determinar si la suma de los dígitos de un entero es par
      // (Definida dentro del Ambiente mediante ciclo interativo)
      // ----------------------------------------------------------------------
      Funcion es_suma_digitos_par(m: ENTERO) : LOGICO Es
         Ambiente
            suma, digito, num: ENTERO
         Proceso
            suma := 0
            num := m
            Mientras num > 0 Hacer
               digito := num MOD 10 //1234 mod 10 me devuelve el último digito, el 4
               suma := suma + digito
               num := num DIV 10 //1234 div 10 me devuelve 123
            FinMientras
            
            Si (suma MOD 2) = 0 Entonces
               es_suma_digitos_par := VERDADERO
            Sino
               es_suma_digitos_par := FALSO
            FinSi
      FinFuncion

      // ----------------------------------------------------------------------
      // Subprogramas de Lectura y Procesamiento
      // ----------------------------------------------------------------------
      Procedimiento LeerMae() Es
         Leer(arch_mae, reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.DNI := HV
         FinSi
      FP

      Procedimiento LeerMov() Es
         Leer(arch_mov, reg_mov)
         Si FDA(arch_mov) Entonces
            reg_mov.DNI := HV
         FinSi
      FP

      // Procesa una recarga individual acumulando saldo y bono sobre 'aux'
      Procedimiento ProcesarRecarga() Es
         // Condición para otorgar bono del 30% (tope €300)
         // Uso el arreglo proporcionado por el escenario/precargado, le paso como parametro el id_puesto para que retorne un valor booleano
         Si puestos_habilitados[reg_mov.id_puesto] O es_suma_digitos_par(reg_mov.monto) Entonces
            bono := reg_mov.monto * 0.30
            Si bono > 300 Entonces
               bono := 300
            FinSi
            //item 3a: cantidad total de bonos otorgados
            cant_bonos_otorgados := cant_bonos_otorgados + 1
         Sino
            bono := 0
         FinSi

         // Acumular saldo total cargado y bonos
         aux.Credito_total := aux.Credito_total + reg_mov.monto + bono
         aux.credito_bonos := aux.credito_bonos + bono
      FP

      // Evalúa si el crédito total acumulado supera el máximo registrado
      //item 3b: DNI y Nombre de la persona con mayor crédito total
      Procedimiento EvaluarMaximo() Es
         Si aux.Credito_total > max_credito Entonces
            max_credito := aux.Credito_total
            max_dni := aux.DNI
         FinSi
      FP

   Proceso
      Abrir E/(arch_mae)
      Abrir E/(arch_mov)
      Abrir /S(mae_act)
      Abrir E/S(arch_asistente)

      LeerMae()
      LeerMov()

      cant_bonos_otorgados := 0
      max_credito := -1
      max_dni := 0

      Mientras (reg_mae.DNI <> HV) O (reg_mov.DNI <> HV) Hacer
         
         // CASO A: MAESTRO MENOR (Pulsera sin recargas en este periodo)
         Si reg_mae.DNI < reg_mov.DNI Entonces
            //item 3b: DNI y Nombre de la persona con mayor crédito total
            aux := reg_mae
            EvaluarMaximo()
            
            reg_act := aux
            Grabar(mae_act, reg_act)
            LeerMae()

         Sino
            // CASO B: CLAVES IGUALES (Pulsera existente con recargas)
            Si reg_mae.DNI = reg_mov.DNI Entonces
               aux := reg_mae

               Mientras reg_mae.DNI = reg_mov.DNI Hacer
                  ProcesarRecarga()
                  LeerMov()
               FinMientras

               //item 3b: DNI y Nombre de la persona con mayor crédito total
               EvaluarMaximo()

               reg_act := aux
               Grabar(mae_act, reg_act)
               LeerMae()

            Sino
               // CASO C: MAESTRO MAYOR (DNI no existe en PULSERA -> Alta de Pulsera)
               aux.DNI := reg_mov.DNI
               aux.N_pulsera := obtener_nro_pulsera()
               aux.Credito_total := 0
               aux.credito_bonos := 0

               Mientras aux.DNI = reg_mov.DNI Hacer
                  ProcesarRecarga()
                  LeerMov()
               FinMientras

               //item 3b: DNI y Nombre de la persona con mayor crédito total
               EvaluarMaximo()

               reg_act := aux
               Grabar(mae_act, reg_act)
            FinSi
         FinSi
      FinMientras

      // ----------------------------------------------------------------------
      // SALIDAS / INFORMES SOLICITADOS
      // ----------------------------------------------------------------------

      // 3.a) Cantidad total de bonos otorgados
      Escribir("Cantidad total de bonos otorgados: ", cant_bonos_otorgados)

      // 3.b) DNI y Nombre de la persona con mayor crédito total
      Si max_dni <> 0 Entonces
         // Acceso directo por clave al archivo Indexado ASISTENTE
         reg_asistente.DNI := max_dni
         Leer(arch_asistente, reg_asistente)
         
         Si Existe Entonces
            max_nombre := reg_asistente.Nombre
         Sino
            max_nombre := "Nombre no encontrado en archivo de asistentes"
         FinSi

         Escribir("--- ASISTENTE CON MAYOR CRÉDITO TOTAL ---")
         Escribir("DNI: ", max_dni)
         Escribir("Nombre: ", max_nombre)
         Escribir("Crédito Total: €", max_credito)
      Sino
         Escribir("No se registraron datos de pulseras.")
      FinSi

      Cerrar(arch_mae)
      Cerrar(mae_act)
      Cerrar(arch_mov)
      Cerrar(arch_asistente)
FinAccion

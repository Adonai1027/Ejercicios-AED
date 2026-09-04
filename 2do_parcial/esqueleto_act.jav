Proceso
   // 1. ABRIR ARCHIVOS
   Abrir E/(arch_mae)
   Abrir E/(arch_mov)
   Abrir /S(mae_act)  // Archivo Maestro Actualizado (el de salida)
   
   // 2. PRIMERA LECTURA DE AMBOS ARCHIVOS
   LeerMae() // Lee arch_mae y si es Fin De Archivo pone reg_mae.clave = HV (High Value)
   LeerMov() // Lee arch_mov y si es Fin De Archivo pone reg_mov.clave = HV

   // 3. CICLO PRINCIPAL
   Mientras reg_mae.clave <> HV o reg_mov.clave <> HV Hacer
      
      // CASO A: LA CLAVE DEL MAESTRO ES MENOR
      Si reg_mae.clave < reg_mov.clave Entonces
         // Significa que este registro del maestro NO tiene novedades.
         // Se pasa tal cual al nuevo archivo actualizado.
         reg_act := reg_mae
         Grabar(mae_act, reg_act)
         
         // Se lee el siguiente del maestro
         LeerMae()
         
      Sino
         
         // CASO B: LAS CLAVES SON IGUALES (MODIFICACIONES Y BAJAS)
         Si reg_mae.clave = reg_mov.clave Entonces
            // Hay novedades para un registro que SÍ EXISTE.
            aux := reg_mae // Se carga el registro a una variable auxiliar para modificarlo
            
            // Ciclo interno por si hay más de 1 movimiento para la misma clave
            Mientras reg_mae.clave = reg_mov.clave Hacer
               // ----- AQUÍ VAN LAS MODIFICACIONES Y BAJAS -----
               // Ej: Según reg_mov.tipo Hacer ... 
               // (Si es una BAJA FÍSICA no se grabaría al final, pero si es 
               // BAJA LÓGICA, solo se cambia un campo Estado a "Baja").
               // ------------------------------------------------
               
               LeerMov() // Se lee el siguiente movimiento
            FM 
            
            // Una vez procesados todos sus movimientos, se graba el registro modificado
            reg_act := aux
            Grabar(mae_act, reg_act)
            
            // Avanza el maestro porque ya lo terminamos de actualizar
            LeerMae()
            
         Sino 
            
            // CASO C: LA CLAVE DEL MAESTRO ES MAYOR (ALTAS)
            // reg_mae.clave > reg_mov.clave
            // Significa que vino un movimiento para un registro que NO existe en el maestro.
            
            // ----- AQUÍ VAN LAS ALTAS -----
            // Por lo general, se valida que el movimiento sea un código de "Alta" o "Ingreso"
            Si reg_mov.tipo = 'Alta' Entonces
                aux.clave := reg_mov.clave
                // Se inicializan los datos del nuevo registro
                // aux.campo := ...
                
                LeerMov() // Se avanza el movimiento
                
                // Si este registro nuevo tuviera múltiples movimientos el mismo día (ej: lo dan de alta y lo prestan el mismo día)
                Mientras aux.clave = reg_mov.clave Hacer
                   // Procesar modificaciones sobre esta nueva alta
                   LeerMov()
                FM
                
                // Finalmente se graba el registro nuevo
                reg_act := aux
                Grabar(mae_act, reg_act)
                
            Sino
                // Si la clave no existía en el maestro y el tipo NO era "Alta" -> ES UN ERROR
                Escribir("ERROR: Movimiento inválido sobre registro inexistente")
                
                // Descartamos todos los movimientos erróneos de esa clave inexistente
                aux.clave := reg_mov.clave
                Mientras aux.clave = reg_mov.clave Hacer
                   LeerMov()
                FM
            FS 
            
         FS 
      FS
   FM 
   
   // 4. CERRAR ARCHIVOS
   Cerrar(arch_mae)
   Cerrar(arch_mov)
   Cerrar(mae_act)
FinAccion

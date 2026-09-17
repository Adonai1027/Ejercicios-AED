Accion biblioteca Es 
   Ambiente 
      formato_clave=Registro
         Cod_lib:N(8)
         Cod_ejemplar:N(10)
      FR 
      //maestro 
      ejemplares=Registro
         clave:formato_clave
         Descripcion:AN(255)
         Tipo_libro:('A','D','L','N')
         Disponible:('Si','No') 
         Estado:AN(10) // "Bien", "Reparacion", "Baja"
         Fecha:AN(10) // "aaaa/mm/dd" o en blanco
      FR 
      arch_mae,mae_act:archivo de ejemplares ordenado por clave 
      reg_mae,reg_act,aux:ejemplares
      //movimientos
      novedades=Registro
         clave:formato_clave
         Tipo_novedad:(1,2,3,4,5)
         Fecha_novedad:AN(10)
         Id_usuario:N(5)
      FR 
      arch_mov:archivo de novedades ordenado por clave 
      reg_mov:novedades

      usuarios=Registro
         Id_usuario:N(5)
         Nombre:AN(50)
         Edad:ENTERO
         Tipo_usuario:('E','D','P')
      FR 
      arch_usu:archivo de usuarios indexado por Id_usuario 
      reg_usu:usuarios
      //b) Informar cuántos ejemplares se encuentran actualmente prestados.
      cant_prestados:ENTERO

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

      Procedimiento tratar_procesos_iguales() Es 
         Segun reg_mov.Tipo_novedad Hacer
            =1: // Ingreso sobre ejemplar existente (Error) //ES UNA ALTA=INGRESO
               Escribir('ERROR: Novedad de Ingreso sobre un ejemplar ya existente.')
            =2: // Prestamo
               //acceder al archivo indexado
               reg_usu.Id_usuario:=reg_mov.Id_usuario
               Leer(arch_usu,reg_usu)
               Si EXISTE Entonces
                  //Disponible = “No”,
                  aux.Disponible:='No'
                  Segun reg_usu.Tipo_usuario Hacer
                     //sumar_dias(fecha, dias)
                     ='E': aux.Fecha:=sumar_dias(reg_mov.Fecha_novedad, 10) //estudiante
                     ='D': aux.Fecha:=sumar_dias(reg_mov.Fecha_novedad, 15) //docente
                     ='P': aux.Fecha:=sumar_dias(reg_mov.Fecha_novedad, 7) //publico gral
                  FS
               Sino
                  Escribir('ERROR: El usuario con ID ', reg_mov.Id_usuario, ' no existe. No se puede realizar el prestamo.')
               FS
            =3: // Devolucion
               //Disponible = “Si”, Fecha = en blanco.
               aux.Disponible:='Si'
               aux.Fecha:=''
            =4: // Mantenimiento
               aux.Disponible:='No'
               aux.Estado:='Reparacion'
               aux.Fecha:=reg_mov.Fecha_novedad
            =5: // Baja
               aux.Disponible:='No'
               aux.Estado:='Baja'
               aux.Fecha:=reg_mov.Fecha_novedad
         FS 
      FP

   Proceso 
      ABRIR E/(arch_mae);LeerMae()
      ABRIR E/(arch_mov);LeerMov()
      ABRIR E/(arch_usu)
      ABRIR /S(mae_act)

      cant_prestados:=0

      Mientras reg_mae.clave <> HV o reg_mov.clave <> HV Hacer
         Si reg_mae.clave < reg_mov.clave Entonces
            reg_act:=reg_mae
            Grabar(mae_act,reg_act)
            //item b
            Si reg_act.Disponible='No' y reg_act.Estado='Bien' Entonces
               cant_prestados:=cant_prestados+1
            FS
            
            LeerMae()
         Sino
            Si reg_mae.clave = reg_mov.clave Entonces
               aux:=reg_mae
               Mientras reg_mae.clave = reg_mov.clave Hacer
                  tratar_procesos_iguales() 
                  LeerMov()
               FM 
               
               reg_act:=aux
               Grabar(mae_act,reg_act)
               //item b
               Si reg_act.Disponible='No' y reg_act.Estado='Bien' Entonces
                  cant_prestados:=cant_prestados+1
               FS
               
               LeerMae()
            Sino 
               // reg_mae.clave > reg_mov.clave (Ejemplar nuevo o error)
               aux.clave:=reg_mov.clave
               LeerMov()
               Si reg_mov.Tipo_novedad = 1 Entonces
                  // Inicializamos con el alta
                  //Nuevo ejemplar: Disponible = “Si”, Fecha = en blanco.
                  aux.Disponible:='Si'
                  aux.Fecha:=''
                  aux.Estado:='Bien'
                  
                  LeerMov()
                  // Procesamos posibles novedades subsecuentes sobre este nuevo ejemplar
                  Mientras aux.clave = reg_mov.clave Hacer
                     Procesar_Novedad()  
                     LeerMov()
                  FM 
                  
                  reg_act:=aux
                  Grabar(mae_act,reg_act)
                  
                  Si reg_act.Disponible='No' y reg_act.Estado='Bien' Entonces
                     cant_prestados:=cant_prestados+1
                  FS
               Sino 
                  Escribir('ERROR: Ejemplar inexistente y el movimiento no es un Ingreso. Clave: ', aux.clave)
               FS 
            FS 
         FS
      FM 
      
      Escribir('La cantidad de ejemplares que se encuentran actualmente prestados es: ', cant_prestados)
      
      Cerrar(arch_mae);Cerrar(arch_mov);Cerrar(arch_usu);Cerrar(mae_act) 
FinAccion

Accion ej1 Es
   Ambiente
      formato_fecha=Registro  
         anio:ENTERO 
         mes:1..12
         dia:1..31
      FR 

      formato_clave=Registro
         nro_serie:N(5)
         modelo:AN(50)
      FR

      bicicletas=Registro
         clave:formato_clave
         fecha_adq:formato_fecha
         fecha_ult_mantenimiento:formato_fecha
         disponibilidad:LOGICO
      FR 
      arch_mae,mae_act:archivo de bicicletas ordenado por clave 
      reg_mae,reg_act,aux:bicicletas

      novedades=Registro
         clave:formato_clave
         tipo_nov:1..3
         fecha_novedad:formato_fecha
         hora_inicio:00..23
         hora_fin:00..23
         circuito_nro:N(5)
         id_usuario:N(5)
      FR
      arch_mov:archivo de novedades ordenado por clave, tipo_nov,fecha_novedad
      reg_mov:novedades

      usuarios=Registro
         id_usuario:N(5)
         dni:N(8)
         sexo:('M','F')
         apynom:AN(60)
         domicilio:AN(60)
         localidad:AN(60)
         provincia:AN(60)
         edad:ENTERO
      FR 
      arch_index:archivo de usuarios indexado por id_usuario
      reg_index:usuarios

      cant_prest_m,cant_prest_f:ENTERO

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

      Procedimiento Procesos_Iguales() Es 
         Si reg_mov.tipo_nov=1 Entonces
            Escribir('ERROR')
         Sino 
            Si reg_mov.tipo_nov=2 Entonces
               reg_index.id_usuario:=reg_mov.id_usuario
               Leer(arch_index,reg_index)
               Si EXISTE Entonces
                  Si reg_index.sexo='F' Entonces
                     cant_prest_f:=cant_prest_f+1
                  Sino
                     cant_prest_m:=cant_prest_m+1
                  FS 
               Sino
                  Escribir('NO EXISTE')
               FS 
            Sino
               aux.disponibilidad:=Falso
               aux.fecha_ult_mantenimiento:=reg_mov.fecha_novedad
            FS 
         FS
      FP
   Proceso 
      ABRIR E/(arch_mae);LeerMae()
      ABRIR E/(arch_mov);LeerMov()
      ABRIR E/(arch_index);
      ABRIR /S(mae_act)

      cant_prest_f:=0
      cant_prest_m:=0

      Mientras reg_mae.clave<>HV o reg_mov.clave<>HV Hacer
         Si reg_mae.clave<reg_mov.clave Entonces
            reg_act:=reg_mae
            Grabar(mae_act,reg_act)
            LeerMae()
         Sino
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
               //alta
               Si reg_mov.tipo_nov = 1 Entonces
                  aux.nro_serie:=reg_mov.nro_serie
                  aux.modelo:=reg_mov.modelo
                  aux.fecha_adq:=reg_mov.fecha_novedad
                  aux.fecha_ult_mantenimiento:='';
                  aux.disponibilidad:=Verdadero
                  LeerMov()
                  Mientras reg_mae.clave=reg_mov.clave Hacer
                     Procesos_Iguales()
                     LeerMov()
                  FM
                  reg_act:=aux
                  Grabar(mae_act,reg_act)
               Sino 
                  Escribir('ERROR: MODIFICACIONES Y BAJAS NO POSIBLE')
               FS
               LeerMov()
            FS
         FS
      FM 
      Escribir('ITEM_B')
      Escribir('La cantidad total de prestamos por el sexo masculino es:',cant_prest_m)
      Escribir('La cantidad total de prestamos por el sexo femenino es:',cant_prest_f)
      Cerrar(arch_mae);Cerrar(arch_mov);Cerrar(arch_index);Cerrar(mae_act)  
FinAccion
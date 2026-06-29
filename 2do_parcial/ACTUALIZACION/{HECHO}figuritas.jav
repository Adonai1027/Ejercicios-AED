Accion ej1 Es 
   Ambiente
      formato_fecha=Registro
         anio:ENTERO 
         mes:1..12
         dia:1..31
      FR 

      album=Registro
         cod_figurita:N(5)
         cantidad:N(2)
         permitir_dup:('Si','No')
      FR
      arch_mae,mae_act:archivo de album ordenado por cod_figurita
      reg_mae,aux:album

      intercambios=Registro 
         cod_figurita:N(5)
         cod_amigo:N(5)
         fecha_solicitud:formato_fecha
      FR 
      arch_mov:archivo de intercambios ordenado por cod_figurita,cod_amigo
      reg_mov:intercambios

      supera:LOGICO

      cant_figuritas_dup:ENTERO 

      Procedimiento LeerMae() Es
         Leer(arch_mae,reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.cod_figurita:=HV
         FSi
      FP 
      Procedimiento LeerMov() Es
         Leer(arch_mov,reg_mov)
         Si FDA(arch_mov) Entonces
            reg_mov.cod_figurita:=HV
         FSi
      FP 
   Proceso
      ABRIR E/(arch_mae);LeerMae()
      ABRIR E/(arch_mov);LeerMov()
      ABRIR /S(mae_act)

      Mientras (reg_mae.cod_figurita<>HV) O (reg_mov.cod_figurita<>HV) Hacer
         Si (reg_mae.cod_figurita<reg_mov.cod_figurita) Entonces
            Grabar(mae_act,reg_mae)
            LeerMae()
         Sino 
            Si reg_mae.cod_figurita=reg_mov.cod_figurita Entonces
               aux:=reg_mae
               Mientras reg_mae.cod_figurita=reg_mov.cod_figurita Hacer
                  Si reg_mae.permitir_dup = 'Si' Entonces
                     cant_figuritas_dup:=cant_figuritas_dup+1
                     aux.cantidad:=aux.cantidad+1
                  FSi
                  LeerMov()
               FM
               reg_mae_act:=aux 
               Grabar(mae_act,reg_mae_act)
               LeerMae()
            Sino
               supera:=diff_dias(7,reg_mov.fecha_solicitud)
               Si NO supera Entonces
                  aux.cantidad:=1
                  aux.permitir_dup:='No'
                  LeerMov()
                  Mientras reg_mae.cod_figurita=reg_mov.cod_figurita Hacer
                     Si reg_mae.permitir_dup = 'Si' Entonces
                        cant_figuritas_dup:=cant_figuritas_dup+1
                        aux.cantidad:=aux.cantidad+1
                     FSi
                     LeerMov()
                  FM
                  reg_mae_act:=aux
                  Grabar(mae_act,reg_mae_act)
               Sino
                  Escribir('ERROR')
               FSi
            FSi
         FSi
      FM
      Escribir('La cantidad de figuritas duplicadas que fueron admitidas son:'cant_figuritas_dup)
      Cerrar(arch_mae);Cerrar(mae_act);Cerrar(arch_mov)
FinAccion



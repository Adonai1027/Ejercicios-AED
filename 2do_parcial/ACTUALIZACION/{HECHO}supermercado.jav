Accion supermercado Es 
   Ambiente 
      formato_clave=Registro
         producto_id:N(5)
      FR 

      stock=Registro
         clave:formato_clave
         stock:ENTERO 
      FR 
      arch_mae,mae_act:archivo de stock ordenado por clave 
      reg_mae,reg_act,aux:stock

      movimientos_diarios=Registro
         clave:formato_clave
         cliente_id:N(5)
         tipo:('C','D')
         cantidad:ENTERO 
         precio_unitario:REAL 
         total:REAL 
         tipo_envio:('Domicilio','Sucursal')
      FR 
      arch_mov:archivo de movimientos_diarios ordenado por clave 
      reg_mov:movimientos_diarios

      productos=Registro
         clave:formato_clave
         nombre:AN(50)
         descripcion:AN(50)
         rubro:('Limpieza','Carniceria','Verduleria','Bazar','Panaderia')
      FR 
      arch_index:archivo de productos indexado por clave 
      reg_index:productos

      cant_no_vendida,cant_retiro_suc:ENTERO

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
         Si aux.stock >= reg_mov.cantidad Entonces
            Segun reg_mov.tipo Hacer
               ='C': aux.stock:=aux.stock-1
               ='D': aux.stock:=aux.stock+1
            FS 
            Si reg_mov.tipo_envio='Sucursal' Entonces
               cant_retiro_suc:=cant_retiro_suc+reg_mov.cantidad
            FS
         Sino
            //falta stock
            cant_no_vendida:=cant_no_vendida+1
            Escribir('Nombre del producto que no se pudo vender por falta de stock:',reg_index.nombre)
            Escribir('Nombre del rubro del producto que no se pudo vender por falta de stock:',reg_index.rubro)
         FS 
      FP
   Proceso 
      ABRIR E/(arch_mae);LeerMae()
      ABRIR E/(arch_mov);LeerMov()
      ABRIR E/(arch_index)
      ABRIR /S(mae_act)

      cant_no_vendida:=0
      cant_retiro_suc:=0

      Mientras reg_mae.clave <> HV o reg_mov.clave<>HV Hacer
         reg_index.clave:=aux.clave
         Leer(arch_index,reg_index)
         Si EXISTE Entonces  
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
                  aux.clave:=reg_mov.clave
                  LeerMov()
                  Mientras reg_mae.clave=reg_mov.clave Hacer
                     Procesos_Iguales()  
                     LeerMov()
                  FM 
                  reg_act:=aux
                  Grabar(mae_act,reg_act)
               FS 
            FS 
         Sino 
            Escribir('ERROR. NO EXISTE EL PRODUCTO')
         FS
      FM 
      Escribir('La cantidad de productos que no se han podido vender por falta de stock es:'cant_no_vendida)
      Escribir('La cantidad de productos que deberan retirarse en sucursal son:',cant_retiro_suc)
      Cerrar(arch_mae);Cerrar(arch_mov);Cerrar(arch_index);Cerrar(mae_act) 
FinAccion      


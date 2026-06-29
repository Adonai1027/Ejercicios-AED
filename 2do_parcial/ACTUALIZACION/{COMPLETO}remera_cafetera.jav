Accion ej1(jugador_nom:Arreglo[1..27] de AN(50)) Es 
   Ambiente
      productos=Registro
         id_producto:N(5)
         nombre:AN(50)
         descripcion:AN(50)
         categoria:('C','R','G','S')
         nuevo_lanz:('Si','No')
         porc_desc:REAL
         stock:ENTERO
      FR 
      arch_mae,mae_act:archivo de productos ordenado por id_producto
      reg_mae,reg_act,aux:productos

      preventas=Registro
         id_producto:N(5)
         id_cliente:N(5)
         cantidad:ENTERO
         es_personalizado:LOGICO
         nro_jugador:1..26
         nombre_jugador:AN(50)
         talle:('S','M','L','XL','XXL')
      FR   
      arch_mov:archivo de preventas ordenado por id_cliente, id_producto
      reg_mov:preventas

      //i1
      max_ventas,max_jugador:ENTERO
      //i2
      cant_prod,cantidad_faltante:ENTERO
      //i3
      min_solicitado,total_solicitado,contador_camiseta,contador_short,contador_gorro,contador_remera:ENTERO
      //final
      imp_tot:REAL

      ventas_jugador:Arreglo[1..27] De ENTERO

      i:ENTERO

      Procedimiento LeerMae() Es
         Leer(arch_mae,reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.id_producto:=HV
         FS
      FP
      Procedimiento LeerMov() Es 
         Si FDA(arch_mov) Entonces
            reg_mov.id_producto:=HV
         FS 
      FP 

      Procedimiento Procesos_Iguales() Es 
         //item (2)
         Si reg_mov.cantidad>aux.stock Entonces
            cantidad_faltante:=reg_mov.cantidad-aux.stock //se prevee que haya mas cantidad que stock, sino sería negativo el resultado
         Sino
            Si aux.stock=0 Entonces
               Escribir('ERROR. NO HAY STOCK.')
            FS 
         FS
         // item (3)
         Segun aux.categoria Hacer
            ='C': contador_camiseta:=contador_camiseta+1
                  ventas_jugador[reg_mov.nro_jugador]:=ventas_jugador[reg_mov.nro_jugador]+reg_mov.cantidad 
            ='R': contador_remera:=contador_remera+1
            ='G': contador_gorro:=contador_gorro+1
            ='S': contador_short:=contador_short+1
         FS
      FP

   Proceso
      Abrir E/(arch_mae);LeerMae()
      Abrir E/(arch_mov);LeerMov()
      Abrir /S(mae_actualizado)

      max_ventas:=LW
      max_jugador:=0
      //item (2)
      cantidad_faltante:=0
      cantidad_solicitada:=HV
      //item (3)
      min_solicitado:=HV
      contador_camiseta:=0
      contador_short:=0
      contador_gorro:=0
      contador_remera:=0
      //final
      imp_tot:=0

      Para i:=1 hasta 27 Hacer
         ventas_jugador[i]:=0
      FP 

      Mientras (reg_mae.id_producto<>HV) O (reg_mov.id_producto<>HV) Hacer
         Si reg_mae.id_producto<reg_mov.id_producto Entonces
            reg_act:=reg_mae
            Grabar(mae_act,reg_act)
            LeerMae()
         Sino
            Si reg_mae.id_producto=reg_mov.id_producto Entonces
               aux:=reg_mae
               Mientras reg_mae.id_producto=reg_mov.id_producto Hacer
                  Procesos_Iguales()
                  LeerMov()
               FM
               reg_act:=aux
               Grabar(mae_act,reg_act)
               LeerMae()
            Sino
               aux:=reg_mae
               cantidad_solicitada:=cantidad_solicitada+reg_mov.cantidad
               LeerMov()
               Mientras reg_mae.id_producto=reg_mov.id_producto Hacer
                  Procesos_Iguales()
                  LeerMov()
               FM
               reg_act:=aux
               Grabar(mae_act,reg_act)
            FS 
         FS 
      FM 
      //i1
      Para i:=1 hasta 27 Hacer
         Si ventas_jugador[i]>max_ventas Entonces
            max_ventas:=ventas_jugador[i] //cantidad=30
            max_jugador:=i //posicion=5
         FS 
      FP
      Escribir('El nombre del jugador que vendio mas camisetas fue:',jugador_nom[max_jugador],'con un total de camiseta vendidas:',max_ventas)

      //i2
      Si contador_camiseta < cantidad_solicitada Entonces
         cantidad_solicitada:=contador_camiseta
         categoria_menos_solicitada:='Camiseta'
      FS 
      Si contador_remera < cantidad_solicitada Entonces
         cantidad_solicitada:=contador_remera
         categoria_menos_solicitada:='Remera'
      FS
      Si contador_gorro < cantidad_solicitada Entonces
         cantidad_solicitada:=contador_gorro
         categoria_menos_solicitada:='Gorros'
      FS
      Si contador_short < cantidad_solicitada Entonces
         cantidad_solicitada:=contador_short
         categoria_menos_solicitada:='Short'
      FS

      Escribir('La cantidad de productos faltantes es:',cantidad_faltante)
      Escribir('EL PRODUCTO NO EXISTE. La cantidad pedida del mismo fue:',cantidad_solicitada)
      //i3
      Escribir('La categoría menos solicitada es:',categoria_menos_solicitada,',con un total de:',cantidad_solicitada);
      Cerrar(arch_mae);Cerrar(mae_act);Cerrar(arch_mov)
FinAccion

Accion ej2 Es 
   Ambiente
      info=Registro
         codsuc:1..10
         codprom:0..5
         codprod:AN(7)
         cant:N(4)
      FR 
      arch:archivo de info
      reg:info

      formato_arr=Registro
         ventas:REAL 
         importe:REAL 
      FR 

      A:Arreglo[1..11,1..6] de formato_arr

      mayor_imp,promedio,mayor_ventas:REAL
      sucursal_mayor,mayor_imp,promocion_mayor,mejor_sucursal:ENTERO

   Proceso
      ABRIR E/(arch);Leer(arch,reg)
      //inicializo a 0 las celdas
      Para i:=1 hasta 11 Hacer
         Para j:=1 hasta 6 Hacer
            A[i,j].ventas:=0
            A[i,j].importe:=0
         FP 
      FP 
      //cargo el arreglo
      Mientras NFDA(arch) Hacer
         i:=reg.codsuc
         j:=reg.codprod
         //no tengo en cuenta la columna del codprom=0, ya que la consigna me dice que solamente se implementaron 5 promociones
         Si j>0 Entonces
            A[i,j].importe:=A[i,j].importe+getImporte(reg.codprod)
            A[i,6].importe:=A[i,6].importe+getImporte(reg.codprod)
            A[11,j].importe:=A[11,j].importe+getImporte(reg.codprod)
            //cada reg es una venta
            A[i,j].ventas:=A[i,j].ventas+1
         FS
         Leer(arch,reg)
      FM
      //resuelvo las consignas
      mayor_imp:=0
      mayor_ventas:=0
      sucursal_mayor:=0
      promocion_mayor:=0

      Para j:=1 hasta 6 Hacer //promocion
         Para i:=1 hasta 10 Hacer //sucursal
            //item_A
            Si A[i,j].ventas>0 Entonces
               promedio:=A[i,j].importe/A[i,j].ventas
               Escribir('En la sucursal:',i,',el promedio de importe fue:',promedio)
            FS 
            //item_C
            Si sucursal_mayor<A[i,j].ventas Entonces
               sucursal_mayor:=A[i,j].ventas
               mejor_sucursal:=i
            FS
         FP       
         //item_C
         Escribir('La sucursal con mayor nro de ventas fue:',mejor_sucursal,'con un total de:',sucursal_mayor) 
         //item_B
         Si A[11,j].importe>mayor_imp Entonces
            mayor_imp:=A[i,j].importe
            promocion_mayor:=j 
         FS 
      FP
      //itemB
      Escribir('La promocion:',j,'obtuvo el mayor importe siendo:',mayor_imp)
      cerrar(arch_suc)
FinAccion
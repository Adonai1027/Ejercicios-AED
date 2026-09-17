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
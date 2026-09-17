Accion finalej1 Es   
   Ambiente
      sec:SECUENCIA DE CARACTERES
      v:CARACTER 
      //ITEM 1
      cant_tot,grad_con_exp:ENTERO 
      porc:REAL 
      rol,lenguaje,rta:CARACTER
      i,j:ENTERO
      //item 2
      mas_elegido,leng_eleg:ENTERO 
      //ITEM 2 4 5
      //fila extra porque pide totales por algo
      M_tot:ARREGLO[1..4,1..4] DE ENTERO
      //ITEM 3
      M_exp:ARREGLO[1..3,1..4] DE ENTERO
      menos_elegido:ENTERO
      //item 4
      menor_rol,menor_rta:ENTERO 
      //item 5
      leng_mayor,leng_menor:ENTERO

      Procedimiento AsignarRol() Es 
         Segun rol Hacer
            'E': i:=1
            'P': i:=2
            'D': i:=3 
         FS 
      FP 

      Procedimiento AsignarLeng() Es 
         Segun lenguaje Hacer 
            'P': j:=1
            'C': j:=2
            'J': j:=3
            'G': j:=4
         FS 
      FP 

      Funcion ConvLeng(n:ENTERO):AN Es 
         Segun n Hacer
            1:ConvLeng:='Python'
            2:ConvLeng:='C'
            3:ConvLeng:='JAVA'
            4:ConvLeng:='GO'
         FS 
      FF 
      Funcion ConvRol(n:ENTERO):AN Es 
         Segun n Hacer
            1: ConvRol:='Estudiante'
            2: ConvRol:='Graduado'
            3: ConvRol:='Docente'
         FS 
      FF
   Proceso 
      ARR(sec);AVZ(sec,v)
      cant_tot:=0
      grad_con_exp:=0

      //inicializar en 0 matrices
      Para i:=1 Hasta 4 Hacer 
         Para j:=1 hasta 4 Hacer 
            M_tot[i,j]:=0
         FP 
      FP 
      Para i:=1 Hasta 3 Hacer 
         Para j:=1 hasta 4 Hacer 
            M_exp[i,j]:=0
         FP 
      FP

      //adonai-Estudiante Avanzado/Python/SI/...
      Mientras NFDS(sec) Hacer 
         Mientras v<>'-' Hacer 
            AVZ(sec,v)
         FM 
         cant_tot:=cant_tot+1
         AVZ(sec,v) //estoy en la inicial del rol

         rol:=v
         AsignarRol()

         Mientras v<>'/' Hacer 
            AVZ(sec,v)
         FM
         AVZ(sec,v) //estoy en la inicial de lenguaje

         lenguaje:=v 
         AsignarLeng()

         M_tot[i,j]:=M_tot[i,j]+1
         M_tot[4,j]:=M_tot[4,j]+1

         Mientras v<>'/' Hacer
            AVZ(sec,v)
         FM 
         AVZ(sec,v) //estoy en RTA

         rta:=v

         //ITEM 3
         Si (rol='D') Y (rta='S') Entonces 
            M_exp[i,j]:=M_exp[i,j]+1
         FS 
         //item 1
         Si rol='P' Y rta='S' Entonces
            grad_con_exp:=grad_con_exp+1
         FS

         AVZ(sec,v) //avanzo al i del si o al o de no
         AVZ(sec,v) //estoy en la primer letra del nombre O FDS
      FM

      //cantidad personas participantes / ITEM 1
      Escribir('La cantidad de personas participantes es: ',cant_tot)
      porc:=((grad_con_exp*100)/cant_tot)
      Escribir('El porcentaje que representa las personas graduadas con exp es de: %',porc)

      //lenguaje elegido / ITEM 2
      mas_elegido:=LV
      
      Para j:=1 Hasta 4 Hacer 
         Si M_tot[1,j] > mas_elegido Entonces   
            mas_elegido:=M_tot[1,j]
            leng_eleg:=j 
         FS 
      FP 

      Escribir('El lenguaje mas elegido entre estudiantes es: ',ConvLeng(leng_eleg),' con un total de: ',mas_elegido)

      //ITEM 3
      menos_elegido:=HV 
      Para j:=1 hasta 4 Hacer
         Si M_exp[3,j] < menos_elegido Entonces
            menos_elegido:=M_exp[3,j]
            leng_eleg:=j 
         FS 
      FP 
      Escribir('El lenguaje menos elegido entre docentes con exp es: ',ConvLeng(leng_eleg),' con un total de: ',menos_elegido)

      //item 4
      menor_rta:=HV
      Para i:=1 hasta 3 Hacer
         Para j:=1 hasta 4 Hacer
            Si M_tot[i,j]<menor_rta Entonces
               menor_rta:=M_tot[i,j]
               menor_rol:=i
               leng_eleg:=j
            FS 
         FP 
      FP 

      Escribir('Menor cantidad de respuestas en el rol: ',ConvRol(menor_rol),' y en el lenguaje: ',ConvLeng(leng_eleg),' con el total de: ',menor_rta)

      //item 5
      leng_menor:=HV 
      leng_mayor:=LV
      Para j:=1 hasta 4 Hacer
         Si M_tot[4,j]<leng_menor Entonces
            leng_eleg:=j
            leng_menor:=M_tot[4,j]
         FS 
      FP 
      Escribir('El lenguaje menos elegido es: ',ConvLeng(leng_eleg),' con un total de: ',leng_menor)

      Para j:=1 hasta 4 Hacer
         Si M_tot[4,j] > leng_mayor Entonces
            leng_mayor:=M_tot[4,j]
            leng_eleg:=j 
         FS 
      FP 
      Escribir('El lenguaje mas elegido es: ',ConvLeng(leng_eleg),' con un total de: ',leng_mayor)

      Cerrar(sec)
FIN_ACCIÓN

Accion ej2 Es 
   Ambiente
      x,y,i,j:ENTERO

      Funcion SumaDivImpar(n:ENTERO):ENTERO Es 
         suma,k:ENTERO 
         suma:=0
         //aca excluyo al propio numero, ya que ningún divisor propio será mayor a la mitad del número, excepto el mismo número
         Para k:=1 Hasta (n DIV 2) Hacer
            //Un divisor propio de un número n es cualquier número entero positivo que divide a n sin dejar resto
            //Si n mod d = 0, entonces d es divisor.

            //veo si es divisor
            Si (n MOD k = 0) Entonces
               //veo si es impar
               Si (k MOD 2 <> 0) Entonces
                  suma:=suma+k
               FS 
            FS
         FP
         //retorno
         SumaDivImpar:=suma
      FF
      Funcion SumaDivPar(n:ENTERO):ENTERO Es 
         suma,k:ENTERO 
         suma:=0
         //aca excluyo al propio numero, ya que ningún divisor propio será mayor a la mitad del número, excepto el mismo número
         Para k:=1 Hasta (n DIV 2) Hacer
            //Un divisor propio de un número n es cualquier número entero positivo que divide a n sin dejar resto
            //Si n mod d = 0, entonces d es divisor.

            //veo si es divisor
            Si (n MOD k = 0) Entonces
               //veo si es par
               Si (k MOD 2 = 0) Entonces
                  suma:=suma+k
               FS 
            FS
         FP
         //retorno
         SumaDivPar:=suma
      FF
   Proceso 
      Escribir('Ingrese el inicio: ');Leer(x)
      Escribir('Ingrese el tope: ');Leer(y)

      Para i:=x Hasta y Hacer 
         //me evito pares repetidos o invertidos
         Para j:=i+1 Hasta y Hacer  
            //miro si son guardianes
            Si (SumaDivImpar(i)=j) Y ((SumaDivPar(j)=i)) Entonces
               Escribir('GUARDIANES: (',i',',j')')
            Sino 
               Si (SumaDivImpar(j)=i) Y ((SumaDivPar(i)=j)) Entonces 
                  Escribir('GUARDIANES: (',j',',i')')
               FS 
            FS 
         FP 
      FP 
FIN_ACCIÓN

Accion ej3 Es 
   Ambiente 
      supermercado=Registro 
         cod_suc:N(5)
         anio_vta:2001...2024
         total:N(6,2)
         cant_fact:N(4)
      FR 
      arch:archivo de supermercado ordenado por cod_suc
      reg:supermercado

      total_anio, monto_max_anio, tot_suc, tot_gral, max_suc, acum_calif:REAL // Usar REAL para dinero
      reporte_anios: ALFANUMERICO // Para guardar los totales de años y mostrarlos solo si califica

      //item 2
      max_anio_suc:ENTERO

      //item 4
      cant_ticket_suc,acum_tickets_dest:ENTERO
      califica:BOOLEANO

      //1
      id_suc:ENTERO 
      //4
      cont_tick:ENTERO 

      cont_calif,cont_calif:ENTERO
      
      resg_anio,resg_suc,tot_suc:ENTERO 

      Procedimiento CorteAnio() Es
         reporte_anios:=reporte_anios,' Anio: ', resg_anio,' Total: ', total_anio
         //2
         Si total_anio>monto_max_anio Entonces 
            monto_max_anio:=total_anio
            max_anio_suc:=resg_anio
         FS 
         tot_suc:=tot_suc+total_anio
         total_anio:=0
         resg_anio:=reg.anio_vta
      FP 


      Procedimiento CorteSuc() Es
         CorteAnio()
         
         //4 y 5
         Si califica Entonces
            Escribir('Sucursal: ',resg_suc,' Total facturado: ',tot_suc)
            //item 2 
            Escribir('Anio mayor facturacion: ',max_anio_suc)
            Escribir('Tickets: ',cant_ticket_suc)

            acum_tickets_dest:=acum_tickets_dest+cant_ticket_suc
            //promedio general de venta
            cont_calif:=cont_calif+1
            acum_calif:=acum_calif+tot_suc
            
            //toda la cadena
            //item 1
            Si tot_suc>max_suc Entonces
               max_suc:=tot_suc
               id_suc:=resg_suc
            FS 
         FS
         //3 
         tot_gral:=tot_gral+tot_suc 
         //este corte
         tot_suc:=0
         cant_ticket_suc:=0
         //corte anterior
         monto_max_anio:=0
         reporte_anios:=''
         califica:=FALSO
         resg_suc:=reg.cod_suc
      FP
   Proceso
      ABRIR E/(arch);Leer(arch,reg)
      tot_gral:=0
      acum_suc:=0
      tot_suc:=0
      total_anio:=0
      cant_ticket_suc:=0
      cont_tick:=0 

      resg_anio:=reg.anio_vta
      resg_suc:=reg.cod_suc

      Mientras NFDA(arch) Hacer
         Si resg_suc<>reg.cod_suc Entonces
            CorteSuc()
         Sino 
            Si resg_anio<>reg.anio_vta Entonces
               CorteAnio()
            FS 
         FS 
         //total anio
         total_anio:=total_anio+reg.total
         //item 4 de toda la cadena
         cont_tick:=cont_tick+reg.cant_fact

         //item 4 y 5 de cada sucursal
         Si reg.total>1000000 Entonces
            califica:=VERDADERO
            cant_ticket_suc:= cant_ticket_suc + reg.cant_fact
         FS 

         Leer(arch,reg)
      FM 
      CorteSuc()
      Cerrar(arch)

      Escribir('La cantidad de tickets de la sucursal destacada es de: ',cant_ticket_suc)

      Escribir('==TODA LA CADENA==')

      Escribir('Sucursal con mayor facturacion: ',id_suc)

      Escribir('PROMEDIO: ',(acum_calif/cont_calif))

      Escribir('TOTAL GENERAL: ',tot_gral)

      Escribir('Porcentaje de tickets destacados: ',((acum_tickets_dest*100)/cont_tick))
      
FIN_ACCIÓN











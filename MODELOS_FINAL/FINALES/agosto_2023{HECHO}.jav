Accion ej1 Es 
   Ambiente
      reg_ta=Registro 
         ndpto:N(6)
         desc:AN(200)
         estado:('D','A','R')
         nivel:1..5
      FR 
      arch_dptos: archivo de dpto indexado por ndpto
      reg_d: reg_ta
      
      regis_d=Registro
         ndpto:N(6)
         due:N(8)
         nombre:AN(20)
         alquilado:N(4)
         reservas:N(4)
         cancelados:N(4)
      FR 
      arch_duenio: archivo de regis_d indexado por ndpto y duenio 
      reg_due: regis_d

      valido=('A','R','C')
      num_dpto:ENTERO 
      cod_mov:CARACTER 

      cont_correcta:ENTERO
      cont_dpto_A:ENTERO 

      //filas son niveles, columna correcto o incorrecto
      A:ARREGLO[1..5,1..2] DE ENTERO 
      i,j:ENTERO
      exito:BOOLEANO

   Proceso 
      ABRIR E/S(dptos)
      ABRIR E/S(duenio)

      cont_correcta:=0
      cont_inco:=0
      cont_dpto_A:=0

      Para i:=1 hasta 5 Hacer
         Para j:=1 hasta 2 Hacer 
            A[i,j]:=0
         FP 
      FP

      Escribir('Ingrese el numero del dpto y codigo de movimiento (A,R,C)');Leer(num_dpto,cod_mov)

      Mientras (cod_mov EN valido) Hacer
         //busco dpto primero
         reg_d.ndpto:=num_dpto
         Leer(arch_dptos,reg_d)
         Si NO EXISTE Entonces
            Escribir('ERROR. NO EXISTE DPTO')
            //no sumo en la matriz porque no hay niveles
         Sino 
            //existe dpto, entonces busco el duenio ahora
            reg_due.ndpto:=num_dpto
            Leer(arch_duenio,reg_due)
            exito:=FALSO 

            Si EXISTE Entonces 
               Segun cod_mov Hacer
                  'A':
                     Si (reg_d.estado='D' O reg_d.estado='R') Entonces
                        reg_d.estado:='A'
                        cont_dpto_A:=cont_dpto_A+1
                        //trato el arch dueño
                        reg_due.alquilado:=reg_due.alquilado+1
                        exito:=VERDADERO 
                     FS 
                  'R':
                     Si (reg_d.estado='D' Y reg_d.nivel>3) Entonces
                        reg_d.estado:='R'
                        //trato el arch dueño
                        reg_due.reservas:=reg_due.reservas+1
                        exito:=VERDADERO 
                     FS 
                  'C':  
                     Si (reg_d.estado='R' O reg_d.estado='A') Entonces
                        reg_d.estado:='D'
                        Si reg_due.cancelados>50 Entonces
                           reg_d.nivel:=reg_d.nivel-2
                           Si reg_d.nivel<1 Entonces 
                              reg_d.nivel:=1
                           FS 
                        FS
                        reg_due.cancelados:=reg_due.cancelados+1
                        exito:=VERDADERO  
                     FS 
               FS 
               
               Si exito Entonces
                  ReGrabar(arch_dptos,reg_d)
                  ReGrabar(arch_duenio,reg_due)
                  A[reg_d.nivel,1]:=A[reg_d.nivel,1]+1
                  cont_correcta:=cont_correcta+1
               Sino
                  A[reg_d.nivel,2]:=A[reg_d.nivel,2]+1
               FS
            FS
         Sino 
            //no existe dueño
            //no aclara la consigna pero lo considero obvio
            Escribir('Ingrese numero de dpto');Leer(reg_due.ndpto)
            Escribir('Ingrese dni de dueño');Leer(reg_due.duenio)
            Escribir('Ingrese nombre de dueño');Leer(reg_due.nombre)
            reg_due.alquilado:=0
            reg_due.reservas:=0
            reg_due.cancelados:=0
            Grabar(arch_duenio,reg_due)
            A[reg_d.nivel,2]:=A[reg_d.nivel,2]+1
         FS
         Escribir('Ingrese el numero del dpto y codigo de movimiento (A,R,C). Para salir, pulse otra cosa');Leer(num_dpto,cod_mov)
      FM 

      Para i:=1 Hasta 5 Hacer 
         Escribir('TRANSACCIONES CORRECTAS EN EL NIVEL: ',i,' SON: ',A[i,1])
      FP 
      Para i:=1 Hasta 5 Hacer 
         Escribir('TRANSACCIONES INCORRECTAS EN EL NIVEL: ',i,' SON: ',A[i,2])
      FP

      Escribir('PORCENTAJE',((cont_dpto_A*100)/cont_correcta))

      CERRAR(arch_duenio);CERRAR(arch_dptos)
FinAccion

Accion ej2 Es
   Ambiente
      sec:SECUENCIA DE CARACTERES 
      v:CARACTER 

      mascota=Registro
         tipo_doc:N(1)
         numero_doc:AN(8)
         nombre_masc:AN(30)
         direccion:AN(40)
      FR 
      arch_sal:archivo de mascota
      reg_sal:mascota

      tipo_aux:AN
      cont_reg,i:ENTERO 

   Proceso 
      ARR(sec);AVZ(sec,v)
      ABRIR/S(arch_sal)

      cont_reg:=0
      Mientras v<>'!' Hacer
         reg_sal.nombre_masc:=''
         Mientras v<>'*' Hacer 
            reg_sal.nombre_masc:=reg_sal.nombre_masc+v
            AVZ(sec,v)
         FM
         AVZ(sec,v) //estoy parado en la primer letra del tipo
         tipo_aux:=''
         Para i:=1 Hasta 3 Hacer 
            tipo_aux:=tipo_aux+v
            AVZ(sec,v)
         FP
         //parado en el primer caracter del numero 
         Segun tipo_aux Hacer 
            'DNI': reg_sal.tipo_doc:=0
            'LCE': reg_sal.tipo_doc:=1
            'LEN': reg_sal.tipo_doc:=2
            Otro Caso: reg_sal.tipo_doc:=3
         FS 
         
         reg_sal.numero_doc:=''
         Mientras v<>'*' Hacer 
            reg_sal.numero_doc:=reg_sal.numero_doc+v
            AVZ(sec,v)
         FM
         AVZ(sec,v) //parado en el primer caracter de direccion

         reg_sal.direccion:=''
         Mientras v<>'?' Hacer 
            reg_sal.direccion:=reg_sal.direccion+v
            AVZ(sec,v)
         FM
         AVZ(sec,v) //posible fin (!) o primer caracter de nombre

         Grabar(arch_sal,reg_sal)
         cont_reg:=cont_reg+1
      FM 
      Escribir('Se grabaron: ',cont_reg,' registros de salidas')
      Cerrar(arch_sal)
      Cerrar(sec)
FINACCION

Accion ej3(A:ARREGLO[1..10] DE ENTERO) Es 
   Ambiente
      Funcion Comprobar(V:ARREGLO[1..10] DE ENTERO,i:ENTERO):BOOLEANO Es 
         Si i>10 Entonces
            Comprobar:=FALSO 
         Sino 
            Si V[i]=i Entonces
               Comprobar:=VERDADERO 
            Sino
               Si V[i]<i Entonces
                  Comprobar:=FALSO 
               Sino
                  Comprobar:=Comprobar(V,i+1)
               FS 
            FS 
         FS 
      FF 
   Proceso  
      Si Comprobar(A,1) Entonces
         Escribir('COINCIDE')
      Sino 
         Escribir('NO COINCIDEN')
      FS 
FinAccion





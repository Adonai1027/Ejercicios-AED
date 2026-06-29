//2024
Accion parcial2024(prims:PUNTERO A NODO_S, primc:PUNTERO A NODO_C) Es 
   Ambiente
      formato_fecha=Registro
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR

      NODO_S=Registro
         id_usuario:N(3)
         fecha_opinion: formato_fecha
         calif: 1..5
         servicio_calif: 1..20 
         prox:PUNTERO A NODO_S
      FR 
      ps,qs,ants: PUNTERO A NODO_S 

      usuarios=Registro
         id_usuario:N(3)
         apynom: AN(255)
         dni: N(8)
         direccion: AN(255)
         correo: AN(255)
         cat: ('Sin Cat','Silver','Gold')
      FR 
      arch_ind: archivo de usuarios indexado por id_usuario
      reg_ind: usuarios 

      NODO_C=Registro
         cod_dto:N(6)
         porc_dto:REAL 
         prox: PUNTERO A NODO_C
      FR
      pc:PUNTERO A NODO_C

      NODO_D=Registro 
         id_usuario:N(3)
         apynom:AN(255)
         correo:AN(255)
         cant_op:ENTERO 
         prom_op:REAL 
         dto: REAL 
         prox,ant: PUNTERO A NODO_D
      FR 
      pd,qd: PUNTERO A NODO_D

      resg_fec:formato_fecha
      resg_usu:ENTERO
      resg_serv:ENTERO

      cont_op,cont_calif:ENTERO 
      

      Procedimiento OpinionReciente() Es 
         resg_usu:=*ps.id_usuario
         resg_fec:=*ps.fecha_op
         cont_op:=0
         cont_calif:=0
         Mientras (ps<>NIL) Y (resg_usu = *ps.id_usuario) Hacer

            Si *ps.fecha_opinion > resg_fec Entonces 
               resg_fec:= *ps.fecha_opinion
               resg_serv:= *ps.serv_calif
            FS 

            cont_op:=cont_op+1
            cont_calif:=cont_calif + *ps.calif

            ps:=*ps.prox 
         FM 
      FP 

      Procedimiento CrearNodo() Es 
         NUEVO(qd)
         *qd.id_usuario:=*ps.id_usuario
         *qd.apynom:=reg_ind.apynom
         *qd.correo:=reg_ind.correo 
         *qd.cant_op:=cont_op
         *qd.prom_op:=(cont_calif/cont_op)
         *qd.dto:=*pc.porc_dto
         pc:=*pc.prox //siguiente en la lista circular
      FP 

      Procedimiento CargaOrdenada() Es 
         Si primd=NIL Entonces
            primd:=qd 
            ultd:=qd 
            *qd.prox:=NIL 
            *qd.ant:=NIL 
         Sino 
            pd:=primd 
            Mientras (pd<>NIL) Y (*pd.prom_op<*qd.prom_op) Hacer 
               pd:=*pd.prox 
            FM 

            Si pd=primd Entonces 
               *qd.prox:=pd 
               *qd.ant:= NIL 
               *pd.ant:=qd 
               primd:=qd 
            Sino 
               Si pd=NIL Entonces
                  *ultd.prox:=qd
                  *qd.ant:=ultd 
                  *qd.prox:=NIL 
                  ultd:=qd 
               Sino 
                  *qd.prox:=pd 
                  *qd.ant:=*pd.ant 
                  *(*pd.ant).prox:=qd 
                  *pd.ant:=qd 
               FS 
            FS 
         FS 
      FP 
   Proceso 
      ABRIR E/(arch_ind)

      ps:=prims
      pc:=prim_c

      primd:=NIL 
      ultd:=NIL 

      Mientras ps<>NIL Hacer 
         reg_ind.id_usuario:= *ps.id_usuario
         Leer(arch_ind,reg_ind)
         Si EXISTE Entonces
            OpinionReciente()
            Si bonificacion(reg_ind.categoria,resg_serv) Entonces
               CrearNodo()
               CargaOrdenada()
            FS 
         FS
      FM

      //mayor prom (ultimas 5 posiciones de la lista)
      pd:=ultd 
      Para i:=1 Hasta 5 Hacer
         Si pd<>NIL Entonces 
            Escribir('ID: ',*pd.id_usuario,'| Nombre: ',*pd.apynom)
            pd:=*pd.ant 
         FS
      FP 

      //menor prom (primeras 5 posiciones de la lista)
      pd:=primd 
      Para i:=1 Hasta 5 Hacer 
         Si pd<>NIL Entonces
            Escribir('ID: ',*pd.id_usuario,'| Nombre: ',*pd.apynom)
            pd:=*pd.prox 
         FS
      FP

      Cerrar(arch_ind)
FINACCION

//recursividad
Accion ej2_2024(primd,ultd:PUNTERO A NODO_D) Es  
   Ambiente
      NODO_D=Registro
         id_compra:ENTERO 
         fecha_compra=Registro 
            aaaa:N(4)
            mm:1..12
            dd:1..31
         FR 
         metodo_pago:('EF','TC','TD')
         nro_tarj:ARREGLO[1..16] DE ENTERO 
         monto:REAL 
         prox,ant:PUNTERO A NODO_D
      FR 
      pd,aux:PUNTERO A NODO_D

      Funcion Validar(V:ARREGLO[1..16] DE ENTERO,i:ENTERO):ENTERO Es 
         valor_actual,sum_dig:ENTERO
         //termino el recorrido del vector
         Si i=0 Entonces 
            Validar:=0
         Sino 
            Si (i MOD 2 <> 0) Entonces
               valor_actual:= V[i]*2
               //ocupa dos digitos 
               Si valor_actual>9 Entonces
                  sum_dig:= (valor_actual MOD 10) + (valor_actual DIV 10)
               Sino 
                  sum_dig:=valor_actual
               FS 
            Sino 
               sum_dig:=V[i]
            FS
            Validar:=sum_dig+Validar(V,i-1)
            // El uso de sum_dig + Validar(V, i - 1) es la forma correcta de arrastrar la suma en la pila de recursividad.
         FS 
      FF

      Procedimiento EliminarDoble() Es 
         aux:=pd
         Si primd=ultd Entonces 
            primd:=NIL 
            ultd:=NIL 
         Sino 
            Si pd=primd Entonces 
               primd:=*pd.prox
               *primd.ant:=NIL 
            Sino 
               Si pd=ultd Entonces 
                  ultd:=*pd.ant
                  *ultd.prox:=NIL 
               Sino 
                  *(*pd.prox).ant:=*pd.ant
                  *(*pd.ant).prox:=*pd.prox 
               FS 
            FS 
         FS 
         pd:=*pd.prox 
         Disponer(aux)
      FP

      suma:ENTERO
   Proceso 
      pd:=primd

      Mientras pd<>NIL Hacer 
         Si (*pd.metodo_pago='TC') Entonces
            suma:=Validar(*pd.nro_tarj,16) 
            Si (suma MOD 10 = 0) Entonces
               //valido, se deja
               pd:=*pd.prox 
            Sino 
               //invalido, se borra
               EliminarDoble()
            FS 
         Sino 
            //es efectivo, modo o lo que sea, AVZ
            pd:=*pd.prox 
         FS
      FM 
FIN_ACCIÓN





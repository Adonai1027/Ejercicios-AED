//parcial 2023 A 

Accion swiftA(prims: PUNTERO A NODO_S, A:ARREGLO[1..12,9..11] DE ENTEROS) ES 
   Ambiente
      formato_fecha=Registro 
         aaaa:N(4)
         mm:1..12
         dd:1..31 
      FR 

      NODO_S=Registro 
         dni:N(8)
         nro_fila:ENTERO
         fecha_fila: formato_fecha
         fecha_entrada: formato_fecha
         prox: PUNTERO A NODO_S
      FR 
      ps,ants: PUNTERO A NODO_S

      NODO_D=Registro 
         fecha:formato_fecha
         cantidad_asis:ENTERO 
         cod:AN
         dni:N(8)
         prox,ant: PUNTERO A NODO_D
      FR
      pd,qd:PUNTERO A NODO_D

      cod_enc:AN
      cod_swift:AN 
      mayor_cant:ENTERO
      mayor_fecha:formato_fecha

      i,j:ENTERO

      Procedimiento Encriptado() Es 
         i:= *ps.fecha_fila.mm 
         j:= *ps.fecha_entrada.dd 

         cod_enc:=A[i,j]
         cod_swift:=swiftieEncriptada(*ps.nro_fila,cod_enc)
      FP 

      Procedimiento CrearNodoFecha() Es 
         NUEVO(qd)
         *qd.fecha:=*ps.fecha_entrada
         *qd.cant:=1
         *qd.cod:=''
         *qd.dni:=0
      FP 
      Procedimiento CrearNodoInfo() Es 
         NUEVO(qd)
         *qd.fecha:=0
         *qd.cant:=''
         *qd.cod:=cod_swift
         *qd.dni:=*ps.dni
      FP

      Procedimiento CargaOrdenada() Es 
         Si primd=NIL Entonces 
            primd:=qd 
            ultd:=qd 
            *qd.prox:=NIL 
            *qd.ant:=NIL 
         Sino 
            pd:=primd 
            Mientras pd<>NIL Y *pd.fecha< *qd.fecha Hacer 
               pd:=*pd.prox 
            FM 

            Si pd=primd Entonces 
               *qd.prox:=pd 
               *qd.ant:=NIL 
               *pd.ant:=qd 
               primd:=qd 
            Sino 
               Si pd=NIL Entonces
                  *ult.prox:=qd 
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

      Procedimiento Buscar() Es 
         pd:=primd 
         Mientras pd<>NIL Y (*pd.fecha<>*ps.fecha_entrada) Hacer 
            pd:=*pd.prox 
         FM 
         //salgo del mientras y estoy en NIL o no se encontro la fecha, así que la creo 
         Si pd=NIL Entonces 
            CrearNodoFecha()
            CargaOrdenada()
            pd:=qd 
         Sino 
            *pd.cant:=*pd.cant+1
         FS 

         CrearNodoInfo()
         *qd.prox:=*pd.prox 
         *qd.ant:=pd 
         Si *pd.prox<>NIL Entonces 
            *(*pd.prox).ant:=qd 
         FS 
         *pd.prox:=qd 
      FP 

      Procedimiento Eliminar() Es  
         Si ps=prims Entonces   
            prims:=*ps.prox 
            ps:=prims
         Sino 
            *ant.prox:=*p.prox
            ps:=*ps.prox
         FS  
         Disponer(ps) 
      FP

   Proceso 
      primd:=NIL 
      ultd:=NIL 

      ps:=prims 
      ants:=NIL
      Mientras ps <> NIL Hacer 
         Encriptado()
         Buscar()
         Eliminar()
         //el Eliminar() avanza a la siguiente swiftie en la lista simple 
      FM 

      mayor_cant:=LV 
      pd:=primd 
      Mientras pd<>NIL Hacer 
         Si *pd.fecha<>0 Entonces
            Si *pd.cantidad_asis > mayor_cant Entonces 
               mayor_cant:=*pd.cantidad_asis
               mayor_fecha:=*pd.fecha 
            FS
         FS 
         pd:=*pd.prox //avanzo la lista doble 
      FM 
      Escribir('La fecha: ',mayor_fecha,' fue la fecha con mayor cantida de swifties: ',mayor_cant)
FIN_ACCIÓN

//recursividad
Accion ej2_2023(primc: PUNTERO A NODO_C) Es 
   Ambiente
      NODO_C=Registro 
         valor:ENTERO 
         prox:PUNTERO A NODO_C
      FR 
      pc,antc,aux:PUNTERO A NODO_C

      NODO_S=Registro 
         valor:ENTERO 
         prox:PUNTERO A NODO_S
      FR 
      ps,prims,qs:PUNTERO A NODO_S
      //no uso ant porque aca la lista solo guarda valores, no se recorre mas adelante ni nada

      Funcion Patron(n:ENTERO):BOOLEANO Es 
         Si n=10 Entonces 
            Patron:=VERDADERO 
         Sino 
            Si n<10 O (n MOD 100 <> 10) Entonces
               Patron:=FALSO 
            Sino 
               Patron:=Patron(n DIV 100) 
            FS 
         FS 
      FS 

      Procedimiento CargarSimple() Es 
         Si prims=NIL Entonces 
            prims:=qs
            ps:=prims 
            *qs.prox:=NIL  
         Sino 
            *ps.prox:=qs 
            *qs.prox:=NIL 
            ps:=qs 
         FS 
      FP 

      Procedimiento EliminarCircular() Es
         aux:=pc
         
         Si (pc=*pc.prox) Entonces  
         // Es el único elemento de la lista 
            primc:=NIL
            pc:=NIL
         Sino       
         // Lista con más de un elemento
            Si (primc=pc) Entonces  
            //Hay que eliminar el primer elto. Actualizar el puntero del último elemento, buscandolo
               antc:= primc
               Mientras (*antc.prox <> primc) Hacer
                  antc:= *antc.prox
               FM
               *antc.prox:= *pc.prox
               primc:= *pc.prox
            Sino
               *antc.prox:= *pc.prox
            FS
            pc:=*pc.prox //avanzamos si o si, ya que el que dispongo es el aux, sino el pc se me pierde
         FS
         DISPONER(aux)
      FP 

      vuelta_completa:BOOLEANO

   Proceso 
      prims:=NIL
      
      pc:=primc
      antc:=nil 

      vuelta_completa:=FALSO 
      Si primc=NIL 
         Mientras NO vuelta_completa Hacer
            Si (*pc.prox=primc) Entonces
               vuelta_completa:=VERDADERO 
               //y aca salgo del mientras 
            FS  
            Si NO Patron(*pc.valor) Entonces
               //para entrar, patron debe devolver falso
               NUEVO(qs)
               *qs.valor:=*pc.valor
               CargarSimple()
               EliminarCircular()
               //si después de eliminar, quedo vacia la LC
               Si primc=NIL Entonces
                  vuelta_completa:=VERDADERO 
                  //y aca salgo del mientras 
               FS 
            Sino
               antc:=pc  
               pc:=*pc.prox
            FS 
         FM
      FS 
      //mostrar la lista circular como quedo
FIN_ACCIÓN
         
      
         







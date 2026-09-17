Accion ej1 Es 
   Ambiente
      formato_fecha=Registro  
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR
      
      formato_clave=Registro
         cod_repartidor:N(5)
         cod_prod:N(5)
      FR 

      ventas=Registro 
         clave: formato_clave
         cantidad:N(5)
         precio:REAL
         fecha:formato_fecha
      FR 
      arch: archivo secuencial ordenado por clave 
      reg:ventas 

      comisiones=Registro 
         clave:formato_clave
         comision:N(5)
      FR 
      arch_ind: archivo indexado por clave 
      reg_ind: comisiones 
      //ranking de repartidores
      NODO=Registro 
         id:ENTERO
         total_comisiones:ENTERO 
         prox:PUNTERO A NODO 
      FR 
      p,q,ant,prim:PUNTERO A NODO

      //cortes
      resg_repartidor:ENTERO 
      resg_producto:ENTERO 
      total_producto,total_repartidor,com_pagar,com_actual:REAL 
      Procedimiento CorteProducto() Es
         //busco en el indexado
         reg_ind.clave.cod_repartidor:=resg_repartidor
         reg_ind.clave.cod_prod:=resg_producto
         Leer(arch_ind,reg_ind)
         Si EXISTE Entonces 
            com_actual:=reg_ind.comision
         Sino 
            com_actual:=com_pagar
         FS 
         //total siguiente nivel
         total_repartidor:=total_repartidor+(total_producto*precio_aux*com_actual)
         //reseteo
         total_producto:=0
         resg_producto:=reg.clave.cod_prod
      FP 
      Procedimiento CorteRepartidor() Es 
         CorteProducto()
         Si total_repartidor>10000 Entonces
            total_repartidor:=total_repartidor*1.10
         FS 
         //listado
         Escribir('Repartidor: ',resg_repartidor,' Total: ',total_repartidor)
         //ranking
         InsertarOrdenadoDecreciente(prim,resg_repartidor,total_repartidor)
         //reseteo
         total_repartidor:=0
         resg_repartidor:=reg.clave.cod_repartidor
      FP
      //ranking 
      Procedimiento InsertarOrdenadoDecreciente(cabeza:PUNTERO A NODO, repartidor:ENTERO, totales:REAL) Es 
         Nuevo(q)
         *q.id:=repartidor
         *q.total_comisiones:=totales 
         Si cabeza=NIL Entonces 
            cabeza:=q 
            *q.prox:=NIL 
         Sino 
            p:=cabeza 
            ant:=NIL 
            Mientras p<>NIL Y (*p.total_comisiones>*q.total_comisiones) Hacer 
               ant:=p 
               p:=*p.prox 
            FM 
            Si p=cabeza Entonces
               cabeza:=q 
            Sino 
               *ant.prox:=q 
            FS 
            *q.prox:=p 
         FS 
      FP 
   Proceso
      ABRIR E/(arch);Leer(arch,reg)
      ABRIR E/(arch_ind)

      total_repartidor:=0
      total_producto:=0
      precio_aux:=0
      resg_producto:=reg.clave.cod_prod
      resg_repartidor:=reg.clave.cod_repartidor
      prim:=NIL 
      
      Escribir('Ingrese una comision generica: ');Leer(com_pagar)

      Mientras NFDA(arch) Hacer 
         Si resg_repartidor<>reg.clave.cod_repartidor Entonces
            CorteRepartidor()
         Sino 
            Si resg_producto<>reg.clave.cod_prod Entonces
               CorteProducto()
            FS 
         FS 
         Si reg.fecha.aaaa=2026 Y reg.fecha.mm=2 Entonces
            precio_aux:=reg.precio 
            total_producto:=total_producto+reg.cantidad 
         FS 
         Leer(arch,reg)
      FM 
      CorteRepartidor()
      //mostrar ranking
      p:=prim 
      Mientras p<>NIL Hacer
         Escribir('ID REPARTIDOR: ',*p.id)
         Escribir('TOTAL COMISIONES: ',*p.total_comisiones)
         p:=*p.prox 
      FM 
      Cerrar(arch);Cerrar(arch_ind)
FinAccion


Accion ej2 Es 
   Ambiente
      sec:SECUENCIA DE CARACTER
      v:CARACTER  
      
      NODO=Registro
         texto_claro:AN(255)
         texto_cifrado:AN(255)
         prox:PUNTERO A NODO 
      FR 
      p,q,prim:PUNTERO A NODO 

      Funcion Concatenar(var:CARACTER, ventana:CARACTER):AN Es
         var:=var+ventana 
         Concatenar:=var
      FF 

      Funcion Conv_Num(c:CARACTER):ENTERO Es 
         Segun c Hacer
            'A':  Posicion:=0
            'B':  Posicion:=1
            'C':  Posicion:=2
            'D':  Posicion:=3
            'E':  Posicion:=4
            'F':  Posicion:=5
            'G':  Posicion:=6
            'H':  Posicion:=7
            'I':  Posicion:=8
            'J':  Posicion:=9
            'K':  Posicion:=10
            'L':  Posicion:=11
            'M':  Posicion:=12
            'N':  Posicion:=13
            'O':  Posicion:=14
            'P':  Posicion:=15
            'Q':  Posicion:=16
            'R':  Posicion:=17
            'S':  Posicion:=18
            'T':  Posicion:=19
            'U':  Posicion:=20
            'V':  Posicion:=21
            'W':  Posicion:=22
            'X':  Posicion:=23
            'Y':  Posicion:=24
            'Z':  Posicion:=25
         FS 
      FF 
      Funcion Conv_Letra(n:ENTERO):CARACTER Es 
         Segun c Hacer
            1: Conv_Letra:='A'
            2: Conv_Letra:='B'
            3: Conv_Letra:='C'
            4: Conv_Letra:='D'
            5: Conv_Letra:='E'
            6: Conv_Letra:='F'
            7: Conv_Letra:='G'
            8: Conv_Letra:='H'
            9: Conv_Letra:='I'
            10: Conv_Letra:='J'
            11: Conv_Letra:='K'
            12: Conv_Letra:='L'
            13: Conv_Letra:='M'
            14: Conv_Letra:='N'
            15: Conv_Letra:='O'
            16: Conv_Letra:='P'
            17: Conv_Letra:='Q'
            18: Conv_Letra:='R'
            19: Conv_Letra:='S'
            20: Conv_Letra:='T'
            21: Conv_Letra:='U'
            22: Conv_Letra:='V'
            23: Conv_Letra:='W'
            24: Conv_Letra:='X'
            25: Conv_Letra:='Y'
            26: Conv_Letra:='Z'
         FS 
      FF

      Funcion Descrifrar(c:CARACTER,clave:ENTERO):CARACTER Es 
         nueva_pos:ENTERO 

         nueva_pos:=((Conv_Num(c)-1-clave+26) MOD 26)

         Descrifrar:= Conv_Letra(nueva_pos)
      FF 

      palabra_og,palabra_desc,caracter_des:CARACTER 
      k:ENTERO
   Proceso 
      ARR(sec);AVZ(sec,v)
      prim:=NIL
      //BUBRVF BM BNBOFDFS
      Escribir('Ingrese el corrimiento: ');Leer(k)

      Mientras NFDS(sec) Hacer 
         palabra_desc:=''
         palabra_og:=''

         //controlo los blancos
         Mientras v='' Hacer
            AVZ(sec,v)
         FM

         Mientras (NFDS(sec)) Y (v<>'') Hacer
            //en esta variable voy guardando el texo original (sin descrifrar)
            palabra_og:=Concatenar(palabra_og,v)
            
            //en esta variable voy descrifrando caracter a caracter
            caracter_des:=Descrifrar(v,k)

            //en esta variable, al salir del mientras, tendre la palabra descifrada, luego a la lista
            palabra_desc:=Concatenar(palabra_desc,caracter_des)

            AVZ(sec,v)
         FM
         Si palabra_og<>' ' Entonces
            //cargo en la lista esta palabra
            NUEVO(q)
            *q.texto_cifrado:=palabra_og
            *q.texto_claro:=palabra_desc
            *q.prox:=NIL
            //carga simple
            Si prim=NIL Entonces 
               prim:=q
            Sino 
               *p.prox:=q
            FS 
            p:=q
         FS
      FM 
      //mostrar lista
      p:=prim 
      Mientras p<>NIL Hacer
         Escribir('Texto en claro: ',*p.texto_claro)
         Escribir('Texto Cifrado: ',*p.texto_cifrado)
         Escribir('Clave K= ',k)
         p:=*p.prox 
      FM 
      Cerrar(sec)
FinAccion


Accion ej2 Es 
   Ambiente
      sec:SECUENCIA DE CARACTER
      v:CARACTER  
      
      NODO=Registro
         lista_hija:PUNTERO A NODOC
         prox:PUNTERO A NODO 
      FR 
      p,q,prim:PUNTERO A NODO 

      NODOC=Registro 
         dato_claro:CARACTER 
         dato_cifrado:CARACTER
         prox:PUNTERO A NODOC 
      FR 
      pc,qc,primc:PUNTERO A NODOC

      Funcion Conv_Num(c:CARACTER):ENTERO Es 
         Segun c Hacer
            'A':  Posicion:=0
            'B':  Posicion:=1
            'C':  Posicion:=2
            'D':  Posicion:=3
            'E':  Posicion:=4
            'F':  Posicion:=5
            'G':  Posicion:=6
            'H':  Posicion:=7
            'I':  Posicion:=8
            'J':  Posicion:=9
            'K':  Posicion:=10
            'L':  Posicion:=11
            'M':  Posicion:=12
            'N':  Posicion:=13
            'O':  Posicion:=14
            'P':  Posicion:=15
            'Q':  Posicion:=16
            'R':  Posicion:=17
            'S':  Posicion:=18
            'T':  Posicion:=19
            'U':  Posicion:=20
            'V':  Posicion:=21
            'W':  Posicion:=22
            'X':  Posicion:=23
            'Y':  Posicion:=24
            'Z':  Posicion:=25
         FS 
      FF 
      Funcion Conv_Letra(n:ENTERO):CARACTER Es 
         Segun c Hacer
            0: Conv_Letra:='A'
            1: Conv_Letra:='B'
            2: Conv_Letra:='C'
            3: Conv_Letra:='D'
            4: Conv_Letra:='E'
            5: Conv_Letra:='F'
            6: Conv_Letra:='G'
            7: Conv_Letra:='H'
            8: Conv_Letra:='I'
            9: Conv_Letra:='J'
            10: Conv_Letra:='K'
            11: Conv_Letra:='L'
            12: Conv_Letra:='M'
            13: Conv_Letra:='N'
            14: Conv_Letra:='O'
            15: Conv_Letra:='P'
            16: Conv_Letra:='Q'
            17: Conv_Letra:='R'
            18: Conv_Letra:='S'
            19: Conv_Letra:='T'
            20: Conv_Letra:='U'
            21: Conv_Letra:='V'
            22: Conv_Letra:='W'
            23: Conv_Letra:='X'
            24: Conv_Letra:='Y'
            25: Conv_Letra:='Z'
         FS 
      FF

      Funcion Descrifrar(c:CARACTER,clave:ENTERO):CARACTER Es 
         nueva_pos:ENTERO 

         nueva_pos:=((Conv_Num(c)-clave+26) MOD 26)

         Descrifrar:= Conv_Letra(nueva_pos)
      FF 

      k:ENTERO
   Proceso 
      ARR(sec);AVZ(sec,v)
      prim:=NIL
      
      //BUBRVF BM BNBOFDFS
      Escribir('Ingrese el corrimiento: ');Leer(k)

      Mientras NFDS(sec) Hacer
         primc:=NIL 
         //controlo los blancos
         Mientras v='' Hacer
            AVZ(sec,v)
         FM

         Mientras (NFDS(sec)) Y (v<>'') Hacer
            NUEVO(qc)
            *qc.dato_cifrado:=v 
            *qc.dato_claro:=Descrifrar(v,k)
            //carga encolada
            Si primc=NIL Entonces
               primc:=qc 
               *qc.prox:=primc
               pc:=qc
            Sino 
               *pc.prox:=qc
               *qc.prox:=primc 
               pc:=qc 
            FS 
            AVZ(sec,v)
         FM
         //cargo en la lista esta palabra
         NUEVO(q)
         *q.lista_hija:=primc
         //carga encolada
         Si prim=NIL Entonces 
            prim:=q
            *q.prox:=NIL 
            p:=q
         Sino 
            *p.prox:=q
            *q.prox:=NIL
            p:=q 
         FS 
      FM
      //mostrar lista de palabras
      Escribir('Clave K= ',k)
      Escribir('Texto en claro: ')
      p:=prim 
      Mientras p<>NIL Hacer
         pc:=*p.lista_hija
         Repetir
            Escribir(*pc.dato_claro)
            pc:=*pc.prox 
         Hasta Que (pc=*p.lista_hija)
         p:=*p.prox 
      FM 
      //mostrar lista de palabras
      Escribir('Texto Cifrado: ')
      p:=prim 
      Mientras p<>NIL Hacer
         pc:=*p.lista_hija
         Repetir
            Escribir(*pc.dato_cifrado)
            pc:=*pc.prox 
         Hasta Que (pc=*p.lista_hija)
         p:=*p.prox 
      FM
      Cerrar(sec)
FinAccion
Accion ej1 Es 
   Ambiente 
      sec,sal_riesgo,sal_no_riesgo:secuencia de caracteres 
      v:caracter 

      NodoHijo=Registro
         dato:CARACTER
         prox:puntero a NodoHijo
      FR 
      p1,prim1,ant1,q1:puntero A NodoHijo

      NodoPadre=Registro
         informacion:puntero a NodoHijo
         prox: puntero a NodoPadre
      FR 
      p2,q2,prim2,ant2:puntero a NodoPadre

      Funcion nombreEnvio(n:entero):AN Es 
         Segun n hacer 
            1:nombreEnvio:='Pendiente'
            .
            .
            .
            4:nombreEnvio:='Devuelto'
         FS 
      FF 
      Funcion Convertir(c:caracter):entero Es 
         Segun c Hacer 
            '0': Convertir:=0
            .
            .
            .
            '9': Convertir:=9
         FS 
      FF 
      Procedimiento cargaEncolada1() Es    
         Si prim1=nil Entonces
            prim1:=q1 
            *q1.prox:=nil 
            p1:=q1 
         Sino 
            *p1.prox:=q1 
            *q1.prox:=nil 
            p1:=q1 
         FS
      FP
      Procedimiento cargaEncolada2() Es    
         Si prim2=nil Entonces
            prim2:=q2 
            *q2.prox:=nil 
            p2:=q2 
         Sino 
            *p2.prox:=q2 
            *q2.prox:=nil 
            p2:=q2 
         FS
      FP
      Funcion convertirFilas(c:caracter):entero Es 
         Segun c hacer 
            'P':convertirFilas:=1
            .
            .
            .
            'D':convertirFilas:=4
         FS 
      FF 
      matriz=Registro
         riesgoso,no_riesgoso:ENTERO 
      FR 

      M:arreglo[1..5,1..3] de matriz

      i,j:entero
      pesaje:N(4,2)
      resg_cod:AN(10)
   Proceso 
      
      ARR(sec);avz(sec,v)
      crear(sal_riesgo);crear(sal_no_riesgo)

      

      para i:=1 hasta 5 hacer 
         para j:=1 hasta 3 hacer 
            m[i,j].riesgoso:=0
            m[i,j].no_riesgoso:=0
         FP 
      FP 

      Mientras v<>'@' Hacer 
         Mientras v<>'#' Hacer 
            prim2:=nil 

            resg_cod:=''
            prim1:=nil 
            Mientras v<>'/' Hacer 
               nuevo(q1)
               *q1.dato:=v
               cargaEncolada1()
               resg_cod:=CONCATENAR(resg_cod,v)
               AVZ(sec,v)
            FM 

            nuevo(q2)
            *q2.lista_hija:=prim1
            cargaEncolada2()
            AVZ(sec,v)

            prim1:=nil 
            para i:=1 hasta 4 hacer 
               nuevo(q1)
               *q1.dato:=v
               cargaEncolada1()
               AVZ(sec,v)
            FP 

            nuevo(q2)
            *q2.lista_hija:=prim1
            cargaEncolada2()

            prim1:=nil 
            Mientras v<>'/' Hacer 
               nuevo(q1)
               *q1.dato:=v
               cargaEncolada1()
               AVZ(sec,v)
            FM 

            nuevo(q2)
            *q2.lista_hija:=prim1
            cargaEncolada2()
            AVZ(sec,v)

            prim1:=nil 
            pesaje:=0.00
            Mientras v<>'/' Hacer
               pesaje:=CONCATENAR(pesaje,Convertir(v))
               nuevo(q1)
               *q1.dato:=v
               cargaEncolada1()
               AVZ(sec,v)
            FM 
            nuevo(q2)
            *q2.lista_hija:=prim1
            cargaEncolada2()
            AVZ(sec,v)

            prim1:=nil
            nuevo(q1)
            *q1.dato:=v
            cargaEncolada1()

            nuevo(q2)
            *q2.lista_hija:=prim1
            cargaEncolada2()

            //ITEM 2 PUNTO B
            Si NO (EsEnvioRiesgoso(resg_cod,pesaje)) Entonces
               i:=convertirFilas(v)
               m[i,2].no_riesgoso:=m[i,2].no_riesgoso+1 
               m[5,2].no_riesgoso:=m[5,2].no_riesgoso+1

               m[i,3].no_riesgoso:=m[i,3].no_riesgoso+1
               m[5,3].no_riesgoso:=m[5,3].no_riesgoso+1
            FS 

            Si (v='P' o v='T') Y EsEnvioRiesgoso(resg_cod,pesaje) Entonces
               p2:=prim2 
               Mientras p2<>NIL Hacer 
                  p1:=*p2.lista_hija
                  MIentras p1<>NIL Hacer 
                     Grabar(sal_riesgo,*p1.dato)
                     p1:=*p1.prox 
                  FM 
                  Grabar(sal_riesgo,'/')
                  p2:=*p2.prox 
               FM 
               Grabar(sal_riesgo,'#')

               i:=convertirFilas(v)
               m[i,1].riesgoso:=m[i,1].riesgoso+1
               m[5,1].riesgoso:=m[5,1].riesgoso+1

               m[5,3].riesgoso:=m[5,3].riesgoso+1
            Sino 
               Si v='D' Y NO(EsEnvioRiesgoso(resg_cod,pesaje)) Entonces
                  p2:=prim2 
                  Mientras p2<>NIL Hacer 
                     p1:=*p2.lista_hija
                     MIentras p1<>NIL Hacer 
                        Grabar(sal_no_riesgo,*p1.dato)
                        p1:=*p1.prox 
                     FM 
                     Grabar(sal_riesgo,'/')
                     p2:=*p2.prox 
                  FM 
                  Grabar(sal_no_riesgo,'#')
               FS   
            FS 
            AVZ(sec,v) //estoy en #
         FM 
         AVZ(sec,v) //estoy en la siguiente suc o fds
      FM 
      Grabar(sal_no_riesgo,'@')
      Grabar(sal_riesgo,'@')

      //punto b - item i
      Escribir('Porcentaje :',((m[5,1].riesgoso*100)/m[5,3].riesgoso),'%')
      //punto b - item ii
      Para i:=1 Hasta 4 hacer 
         Escribir('Para el estado de envio: ',nombreEnvio(i))
         Escribir('Cantidad de envios no riesgosos: ',m[i,2].no_riesgoso)
      FP 
      cerrar(sec)
      cerrar(sal_no_riesgo)
      cerrar(sal_riesgo)
FINACCION

// coddeenvio/0000/direccion/1000.00/P#@
//codigo envio / codigo suc origen / direccion destino / peso / estado envio # @
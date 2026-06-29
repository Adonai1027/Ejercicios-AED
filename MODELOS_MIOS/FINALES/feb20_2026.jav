Accion ej1 Es 
   Ambiente    
      sec:SECUENCIA DE CARACTER 
      v:CARACTER 

      NODO1=Registro 
         dato:AN(3)
         prox:PUNTERO A NODO1
      FR
      p1,q1,prim1:PUNTERO A NODO1 

      NODO2=Registro 
         lista_hija:PUNTERO A NODO1 
         nivel,id:ENTERO 
         prox:PUNTERO A NODO2 
      FR 
      p2,q2,ant2:PUNTERO A NODO2 
      prim2:ARREGLO[1..10] DE PUNTERO A NODO2

      Funcion ConvertirNumero(c:CARACTER):ENTERO Es 
         Segun c Hacer 
            '0': ConvertirNumero:=0
            '1': ConvertirNumero:=1
            '2': ConvertirNumero:=2
            '3': ConvertirNumero:=3
            '4': ConvertirNumero:=4
            '5': ConvertirNumero:=5
            '6': ConvertirNumero:=6
            '7': ConvertirNumero:=7
            '8': ConvertirNumero:=8
            '9': ConvertirNumero:=9
         FS 
      FF
      Funcion ConvVocal(c:CARACTER):ENTERO Es 
         'a': ConvVocal:=1
         'e': ConvVocal:=2
         'i': ConvVocal:=3
         'o': ConvVocal:=4
         'u': ConvVocal:=5
         'A': ConvVocal:=1
         'E': ConvVocal:=2
         'I': ConvVocal:=3
         'O': ConvVocal:=4
         'U': ConvVocal:=5
      FF 
      Funcion ReconvVocal(n:ENTERO):CARACTER Es 
         1: ConvVocal:='a'
         2: ConvVocal:='e'
         3: ConvVocal:='i'
         4: ConvVocal:='o'
         5: ConvVocal:='u'
      FF
      Procedimiento CargaOrd(prim,q:PUNTERO A NODO2) Es 
         Si prim=NIL Entonces
            prim:=q 
            *q.prox:=NIL 
         Sino 
            p2:=prim 
            ant2:=NIL 
            Mientras p2<>NIL Y (*p2.nivel>*q.nivel Y *p2.id>*q.id) Hacer 
               ant2:=p2 
               p2:=*p2.prox 
            FM 
            Si p2=prim Entonces
               prim:=q 
               *q.prox:=p2 
            Sino 
               *ant2.prox:=q 
               *q.prox:=p2 
            FS 
         FS 
      FP 
      vocal:('a','e','i','o','u')
      numero:('0','1','2','3','4','5','6','7','8','9')
      i,correctas,erroneas,indice_nivel:ENTERO
   Proceso 
      ARR(sec);AVZ(sec,v)

      correctas:=0;erroneas:=0

      Para i:=1 Hasta 10 Hacer 
         prim2[i]:=NIL 
      FP 

      Mientras NFDS(sec) Hacer 
         prim1:=NIL 

         Nuevo(q2)
         *q2.id:=0
         Para i:=1 Hasta 6 Hacer 
            *q2.id:=CONCATENAR(*q2.id,ConvertirNumero(v))
            AVZ(sec,v)
         FP 
         //parado en el primer caracter de firma
         Para i:=1 Hasta 7 Hacer 
            AVZ(sec,v)
         FP 
         //parado en el nivel
         indice_nivel:=ConvertirNumero(v)
         *q2.id:=indice_nivel
         AVZ(sec,v)
         //parado en carga datos textuales
         Mientras v<>'-' O v<>'+' Hacer 
            Nuevo(q1)
            *q1.dato:=v 
            //carga encolada
            Si prim1=NIL 
               prim1:=q1 
               *q1.prox:=NIL 
               p1:=q1
            Sino 
               *p1.prox:=q1 
               *q1.prox:=NIL 
               p1:=q1 
            FS 
            AVZ(sec,v)
         FM 
         segun numerocubo hacer 
            0: conv:='0'
            .
            .
            .
            729: conv:='729'
         p1:=prim1
         Si v='-' Hacer 
            Mientras p1<>NIL Hacer 
               Si *p1.dato EN numero Entonces
                  Si (ConvertirNumero(*p1.dato) MOD 2 <> 0) Entonces

                     *p1.dato:=(ConvertirNumero(*p1.dato)**3)
                  Sino 
                     *p1.dato:=(ConvertirNumero(*p1.dato)*2)
                  FS 
                  correctas:=correctas+1 
               Sino 
                  //comparacion a nivel ascii, si no se encuentra en este rango es porque no es ni consonante ni vocal, son simbolos extraños
                  Si (*p1.dato<='a' Y *p1.dato>='z') O (*p1.dato<='A' Y *p1.dato>='Z') Entonces
                     erroneas:=erroneas+1
                     *p1.dato:='!'
                  FS 
               FS 
               p1:=*p1.prox 
            FM 
         Sino 
            Mientras p1<>NIL 
               Si v EN vocal Entonces
                  //utilizo aritmetica modular
                  /*
                  pide las dos anteriores entonces se resta 2 y se suma la cantidad de vocales para evitar numeros negativos
                  1a-->o4
                  2e-->u5
                  3i-->a1
                  4o-->e2
                  5u-->i3
                  supongamos que en la v esta la a
                  (1 - 2 + 5) mod 5
                  (4 mod 5)
                  4
                  */
                  *p1.dato:=ReconvVocal(((ConvVocal(*p1.dato) - 2 + 5) MOD 5))
                  correctas:=correctas+1
               Sino 
                  Si (*p1.dato>='a' Y *p1.dato<='z') O (*p1.dato>='A' Y *p1.dato<='Z') Entonces
                     *p1.dato:=ASCII(*p1.dato)
                     correctas:=correctas+1
                  Sino 
                     erroneas:=erroneas+1 
                     *p1.dato:='!'
                  FS 
               FS 
               p1:=*p1.prox 
            FM 
         FS
         //cargo en la lista  padre
         *q2.lista_hija:=prim1
         CargaOrd(prim2[indice_nivel],q2)
         //siguiente rafaga de datos o FDS
         AVZ(sec,v)
      FM
      //INFORME
      Escribir('PORCENTAJE',((erroneas*100)/correctas))
      //SISTEMA DE CLASIFICACION
      Si erroneas>(correctas*1.10) Entonces
         Escribir('SISTEMA COMPROMETIDO')
      Sino 
         Si erroneas=correctas Entonces
            Escribir('SISTEMA ALERTA')
         Sino 
            Si erroneas<(correctas DIV 2) Entonces
               Escribir('SISTEMA PROTEGIDO')
            FS 
         FS 
      FS 
      //LISTADO COMPLETO
      Para i:=1 Hasta 10 Hacer
         p2:=prim2[i] 
         Escribir('Viendo el listado del nivel de criticidad: ',i-1)
         Mientras p2<>NIL Hacer 
            Escribir('CARGA TEXTUAL TRANSFORMADA: ')
            p1:=prim1 
            Mientras p1<>NIL Hacer 
               Escribir(*p1.dato)
               p1:=*p1.prox 
            FM 
            p2:=*p2.prox
         FM 
      FP 

      Cerrar(sec)
FINACCION

      







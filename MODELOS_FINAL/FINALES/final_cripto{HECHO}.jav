Accion ej1 Es 
   Ambiente
      sec:SECUENCIA DE CARACTER 
      v:CARACTER 

      NODO_CAR=Registro
         dato:AN 
         prox:PUNTERO A NODO_CAR
      FR 
      p1,q1,prim1,aux:PUNTERO A NODO_CAR

      NODO_PAL=Registro
         lista_hija:PUNTERO A NODO_CAR
         prox:PUNTERO A NODO_PAL
      FR 
      p2,q2,prim2:PUNTERO A NODO_PAL

      cont_nodos_pal,normales,erroneas,total_car:ENTERO
      
      Funcion ConvertirNumero(c:CARACTER):ENTERO Es 
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
      Funcion PosActual(c:CARACTER):ENTERO Es 
         'a': PosActual:=0
         'e': PosActual:=1
         'i': PosActual:=2
         'o': PosActual:=3
         'u': PosActual:=4
         'A': PosActual:=0
         'E': PosActual:=1
         'I': PosActual:=2
         'O': PosActual:=3
         'U': PosActual:=4
      FS
      Funcion PosConv(n:ENTERO):CARACTER Es 
         0: PosConv:='a'
         1: PosConv:='e'
         2: PosConv:='i'
         3: PosConv:='o'
         4: PosConv:='u'
      FS

      vocales:('a','e','i','o','u','A','E','I','O','U')
      numeros:('0','1','2','3','4','5','6','7','8','9')
      nuevaPos:CARACTER 
   Proceso 
      ARR(sec);AVZ(sec,v)
      prim2:=NIL 
      normales:=0
      erroneas:=0
      cont_nodos_pal:=0
      total_car:=0

      Mientras NFDS(sec) Hacer
         prim1:=NIL
         Mientras NFDS(sec) Y v<>' ' Hacer 
            NUEVO(q1)
            total_car:=total_car+1
            Si v EN vocales Entonces 
               normales:=normales+1
               nuevaPos:=((PosActual(v) - 2 + 5) MOD 5)
               *q1.dato:=PosConv(nuevaPos)
            Sino 
               Si v EN numeros Entonces
                  normales:=normales+1
                  Si (ConvertirNumero(v) MOD 2 = 0) Entonces 
                     *q1.dato:=(ConvertirNumero(v)*2)
                  Sino 
                     *q1.dato:=(ConvertirNumero(v)**3)
                  FS 
               Sino
                  //consonante
                  Si (v>='a' Y v<='z') O (v>='A' Y v<='Z') Entonces
                     normales:=normales+1
                     *q1.dato:=1
                  Sino 
                     //simbolo extraño
                     erroneas:=erroneas+1
                     *q1.dato:='@'
                  FS 
               FS 
            FS 
            //carga encolada
            Si prim1=NIL Entonces
               prim1:=q1 
               *q1.prox:=NIL 
               p1:=q1 
            Sino 
               *p1.prox:=q1 
               *q1.prox:=NIL 
               p1:=q1 
            FS 
            AVZ(sec,v) //avanzo el char
         FM 
         //nodo palabra
         NUEVO(q2)  
         *q2.lista_hija:=prim1
         //carga encolada
         Si prim2=NIL Entonces
            prim2:=q2 
            *q2.prox:=NIL 
            p2:=q2 
         Sino 
            *p2.prox:=q2 
            *q2.prox:=NIL 
            p2:=q2 
         FS 
         //avanzo el blanco o llegue al fds
         AVZ(sec,v) 
      FM 
      //recorro lista de palabras
      p2:=prim2 
      Mientras p2<>NIL Hacer
         //MUESTRO MENSAJE ENCRIPTADO
         p1:=*p2.lista_hija
         Escribir('MENSAJE ENCRIPTADO: ')
         Mientras p1<>NIL Hacer 
            Escribir(*p1.dato)
            p1:=*p1.prox
         FM
         //CUENTO PALABRAS Y AVANZO A LA SIGUIENTE PALABRA
         cont_nodos_pal:=cont_nodos_pal+1
         p2:=*p2.prox
      FM 
      Escribir('CANTIDAD DE PALABRAS: ',cont_nodos_pal)
      //porcentaje
      Si total_car>0 Entonces
         Escribir('PORCENTAJE CARACTERES INVALIDOS: '((erroneas*100)/total_car))
      FS 
      //CATALOGO
      Si erroneas>(normales*1.10) Entonces
         Escribir('CORRUPTO')
      Sino 
         Si erroneas=normales Entonces
            Escribir('ALERTA')
         Sino 
            Si erroneas<(normales DIV 2) Entonces
               Escribir('CURADO')
            FS 
         FS 
      FS 
      Cerrar(sec)
FINACCION

Accion ej2 Es
   Ambiente
      transacciones=Registro 
         dpto:N(6)
         origen:1..3
         nivel:1..5
         valida:('SI','NO')
      FR 
      arch:archivo secuencial de transacciones
      reg:transacciones

      matriz=Registro 
         validas,general:ENTERO 
      FR 

      M[1..4,1..6] DE matriz 
      i,j:ENTERO 

      resgNivel,resgOg,menorCant,mayorCant:ENTERO 
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)

      Para i:=1 Hasta 4 Hacer 
         Para j:=1 Hasta 6 Hacer 
            M[i,j].validas:=0
            M[i,j].general:=0
         FP 
      FP 

      Mientras NFDA(arch) Hacer
         i:=reg.origen
         j:=reg.nivel 

         Si reg.valida='SI' Entonces
            M[i,j].validas:=M[i,j].validas+1
            M[4,j].validas:=M[4,j].validas+1
            M[i,6].validas:=M[i,6].validas+1
            M[4,6].validas:=M[4,6].validas+1
         FS 
         M[i,j].general:=M[i,j].general+1
         M[4,j].general:=M[4,j].general+1
         M[i,6].general:=M[i,6].general+1
         M[4,6].general:=M[4,6].general+1
         Leer(arch,reg)
      FM 
      //A
      menorCant:=HV
      Para i:=1 Hasta 3 Hacer 
         Para j:=1 Hasta 5 Hacer 
            Si M[i,j].general>0 Entonces
               Si M[i,j].general<menorCant Entonces
                  resgOg:=i
                  resgNivel:=j 
                  menorCant:=M[i,j].general
               FS 
            FS 
         FP 
      FP 
      Escribir('NIVEL: ',resgNivel,' ORIGEN: ',resgOg,' REPRESENTAN LA MENOR CANTIDAD DE TRANSACCIONES')

      //B
      mayorCant:=LV
      Para j:=1 Hasta 5 Hacer 
         //C
         Escribir('NIVEL: ',j,' TOTAL: ',M[4,j].general)
         Si M[4,j].general>mayorCant Entonces
            mayorCant:=M[4,j]
            resgNivel:=j 
         FS 
      FP 
      Escribir('NIVEL: ',resgNivel,' TIENE MAYOR CANTIDAD DE TRANSACCIONES')

      //D
      Si M[4,6].general>0 Entonces
         Escribir('PORCENTAJE DE VALIDAS: ',(((M[4,6].validas)*100)/M[4,6].general))
      FS 

      //E
      menorCant:=HV
      Para i:=1 Hasta 3 Hacer 
         Si M[i,6].validas>0 Entonces
            Si M[i,6].validas<menorCant Entonces
               menorCant:=M[i,6].validas 
               resgOg:=i 
            FS 
         FS 
      FP 
      Escribir('ORIGEN: ',resgOg,' MENOR CANTIDAD DE TRANSACCIONES VALIDAS')

      Cerrar(arch)
FINACCION

Accion ej3 Hacer 
   Ambiente 
      Funcion ConvertirMinuscula(c:CARACTER):CARACTER Es 
         Segun c Hacer 
            'A': ConvertirMinuscula:='a'
            'B': ConvertirMinuscula:='b'
            'C': ConvertirMinuscula:='c'
            'D': ConvertirMinuscula:='d'
            'E': ConvertirMinuscula:='e'
            'F': ConvertirMinuscula:='f'
            'G': ConvertirMinuscula:='g'
            'H': ConvertirMinuscula:='h'
            'I': ConvertirMinuscula:='i'
            'J': ConvertirMinuscula:='j'
            'K': ConvertirMinuscula:='k'
            'L': ConvertirMinuscula:='l'
            'M': ConvertirMinuscula:='m'
            'N': ConvertirMinuscula:='n'
            'Ñ': ConvertirMinuscula:='ñ'
            'O': ConvertirMinuscula:='o'
            'P': ConvertirMinuscula:='p'
            'Q': ConvertirMinuscula:='q'
            'R': ConvertirMinuscula:='r'
            'S': ConvertirMinuscula:='s'
            'T': ConvertirMinuscula:='t'
            'U': ConvertirMinuscula:='u'
            'V': ConvertirMinuscula:='v'
            'W': ConvertirMinuscula:='w'
            'X': ConvertirMinuscula:='x'
            'Y': ConvertirMinuscula:='y'
            'Z': ConvertirMinuscula:='z'
         Otro Caso (ConvertirMinuscula:=c)
      FF

      Funcion Palindrome(arr:ARREGLO[1..16] DE CARACTER,i,f:ENTERO):BOOLEANO Es 
         Si i>=f Entonces
            Palindrome:=VERDADERO
         Sino 
            Si arr[i] <> arr[f] Entonces
               Palindrome:=FALSO
            Sino
               Palindrome:=Palindrome(arr,i+1,f-1)
            FS 
         FS 
      FP
      i,j,cant_leida,opc:ENTERO 
      A,B:ARREGLO[1..16] DE CARACTER
   Proceso 
      i:=1
      opc:=0
      Escribir('Escriba una frase de no más de 16 caracteres. Los espacios cuentan como caracter tambien')
      Mientras opc<>1 Y i<=16 Hacer 
         Leer(A[i])
         cant_leida:=i
         Escribir('Para salir pulse 1. Para continuar otra tecla');Leer(opc)
         i:=i+1
      FM
      //limpieza: quitar blancos y mandar todo a minuscula
      j:=1
      Para i:=1 Hasta cant_leida Hacer 
         //MUESTRO LA FRASE
         Escribir(A[i])
         //BORRO ESPACIOS
         Si A[i]<>' ' Entonces 
            B[j]:=ConvertirMinuscula(A[i])
            j:=j+1
         FS 
      FP
      Si Palindrome(B,1,j-1) Entonces
         Escribir('ES PALINDROME')
      Sino 
         Escribir('NO ES PALINDROME')
      FS 
FINACCION

      
      



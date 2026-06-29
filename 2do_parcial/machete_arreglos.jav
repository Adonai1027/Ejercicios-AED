//metodo ordenamiento
//1 seleccion directa 
min,aux:ENTERO 
Para i:=1 Hasta n-1 Hacer 
   aux:=V[i]
   min:=i
   Para j:=(i+1) Hasta n Hacer
      Si min>V[j] Entonces 
         min:=j
         aux:=V[j]
      FS 
   FP
   V[min]:=V[i]
   V[i]:=aux
FP 
//2 insercion directa
Para i:=2 Hasta n Hacer 
   aux:=V[i]
   j:=i-1
   Mientras (aux<V[j]) Y (j>0) Hacer
      V[j+1]:=V[j]
      j:=j-1
   FM 
   V[j+1]:=aux 
FP 
//3 intercambio directo o burbuja
bandera:=FALSO 
Mientras No bandera Hacer 
   bandera:=VERDADERO
   Para i:=1 Hasta (n-1) Hacer
      Si V[i]>V[i+1] Entonces
         aux:=V[i]
         V[i]:=V[i+1]
         V[i+1]:=aux 
         bandera:=FALSO 
      FS 
   FP 
FM 

//metodo de busquedas
//desordenado
//lineal
Leer(elem_buscado)
exito:=FALSO 
Para i:=1 Hasta n Hacer 
   Si V[i]=elem_buscado Entonces 
      exito:=VERDADERO
      Escribir('Se encontro en la posicion:',i)
   FS 
FP 
Si No exito Entonces
   Escribir('No se encontro')
FS 

//lineal con centinela
Leer(elem_buscado)
i:=1
Mientras (V[i]<>elem_buscado) Y (i<n) Hacer 
   i:=i+1
FM 
Si V[i]=elem_buscado Entonces
   Escribir('Encontrado')
Sino 
   Escribir('No encontrado')
FS 

//ordenado
//lineal con centinela
Leer(elem_buscado)
i:=1
//ordenado de menor a mayor
Mientras (V[i]>elem_buscado) Y (i<n) Hacer 
   i:=i+1
FM 
Si V[i]=elem_buscado Entonces
   Escribir('Encontrado')
Sino 
   Escribir('No encontrado')
FS 
//binaria
Leer(elem_buscado)
izq:=1
der:=n
medio:=(izq+der) DIV 2
Mientras (V[medio]<>elem_buscado) Y (izq<der) Hacer
   Si V[medio]>elem_buscado Entonces
      der:=medio-1
   Sino 
      izq:=medio+1
   FS 
   medio:=(izq+der) DIV 2
FM 
Si V[medio]=elem_buscado Entonces
   Escribir('Elemento encontrado en: ',medio)
Sino 
   Escribir('No encontrado')
FS 


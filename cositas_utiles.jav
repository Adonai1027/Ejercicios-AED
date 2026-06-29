//numeros amigos
Accion ej Es 
   Ambiente
      i,j,x,y,suma_i:ENTERO 
      Funcion SumDivPropios(n:ENTERO):ENTERO 
         suma,i:ENTERO 
         suma:=0
         Para i:=1 Hasta (n DIV 2) Hacer  
            Si (n MOD i = 0) Entonces
               suma:=suma+i 
            FS 
         FP
         SumDivPropios:=suma  
      FF 

   Proceso 
      Escribir('Ingrese el rango: ');Leer(x,y)
      Para i:=x Hasta y Hacer 
         suma_i:=SumDivPropios(i)
         Para j:=i+1 Hasta y Hacer
            Si suma_i=j Y SumDivPropios(j)=i Entonces
               Escribir('NUMEROS AMIGOS: ',i,' ',j)
            FS  
         FP 
      FP 
FIN_ACCIÓN

//extraer numeros de una secuencia sin concatenar + contar su longitud
numero:=0
longitud_numero:=0
Mientras v<>MARCA Hacer 
   numero:=(numero*10)+ConvertirNumero(v)
   longitud_numero:=longitud_numero+1
   AVZ(sec,v)
FM 

//capicua
Capicua(numero,longitud_numero-1) //le paso restando 1 porque es la cantidad de digito que voy a extraer dentro de la función
Funcion Capicua(num,indice:ENTERO):BOOLEANO Es 
   primer_dig,ultimo_dig:ENTERO 
   Si indice<=0 Entonces
      Capicua:=VERDADERO
   Sino  
      //extraigo extremos
      primer_dig:=TRUNC(num / (10**indice))
      ultimo_dig:=(num MOD 10)
      Si primer_dig<>ultimo_dig Entonces
         Capicua:=FALSO 
      Sino
         num:=(num MOD (10**indice)) //saco el primer digito
         num:=TRUNC(num/10) //saco el ultimo digito
         //resto 2 porque saque 2 digitos
         Capicua:=Capicua(num,indice-2)
      FS 
   FS
FF

//FUNCION PARA SABER SI ES PRIMO + MOSTRAR EN UN RANGO DADO POR EL USUARIO LOS NUMEROS PRIMOS
Funcion EsPrimo(n:ENTERO):BOOLEANO Es 
   i:ENTERO 
   primo:BOOLEANO 
   Si n<2 Entonces
      EsPrimo:=FALSO 
   Sino 
      primo:=VERDADERO 
      i:=2
      Mientras i<=(n DIV 2) Y primo Hacer
         Si (n MOD i = 0) Entonces
            primo:=FALSO 
         Sino 
            i:=i+1
         FS 
      FM
      EsPrimo:=primo 
   FS 
FF
Para i:=x Hasta y Hacer 
   Si EsPrimo(i) Entonces 
      Escribir(i)
   FS 
FP 

//NUMEROS NARCISISTAS + FORMA PARA CONSEGUIR CANTIDAD DE DIGITOS
num:=153
aux:=num
Si aux=0 Entonces
   cantidad_digitos:=1 
Sino 
   cantidad_digitos:=0
   Mientras aux>0 Hacer 
      aux:=aux DIV 10  //ELIMINO EL ULTIMO DIGITO: 153 --> 15
      cantidad_digitos:=cantidad_digitos+1
   FM 
FS 
suma:=0
aux:=num
Mientras aux>0 Hacer 
   digito:=aux MOD 10 //OBTENGO EL ULTIMO DIGITO: 153 --> 3
   suma:=suma+(digito**cantidad_digitos)
   aux:=aux DIV 10 //ELIMINO EL ÚLTIMO DIGITO: 153 --> 15
FM 
Si num=suma Entonces
   Escribir('NARCISISTA')
Sino 
   Escribir('NO ES NARCISISTA')
FS

//DADO UN NUMERO INGRESADO POR EL USUARIO, DEVOLVER DE FORMA INVERTIDA
num:=123
aux:=num
Escribir(num)
Mientras num>0 Hacer 
   aux:=aux MOD 10 
   Escribir(aux)
   num:=num DIV 10
FM


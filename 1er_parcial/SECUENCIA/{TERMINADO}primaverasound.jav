0301|123456|T|3|#|0345|234567|E|4|#|...
123456|unsuario+|33254787|Juan|.|27895614|Melisa|.|36257489|Pedro|?...
123456|#+?|654321|rolfiix+|33254787|Juan|.|27895614|Melisa|.|36257489|Pedro|?...
Accion primaverasound Es
   Ambiente
      sec1,sec2,salida:SECUENCIA DE CARACTERES
      v1,v2:CARACTER 
      cant_distinta,cant_entradas,compras:ENTERO
      esTarjeta:LOGICO

      Funcion CONVERTIR(c:CARACTER):ENTERO Es
         Segun (c) Hacer            
            "1": CONVERTIR:= 1
            "2": CONVERTIR:= 2
            "3": CONVERTIR:= 3
            "4": CONVERTIR:= 4
            "5": CONVERTIR:= 5
         FS
      FF
   Proceso
      ARR(sec1);AVZ(sec1,v1)
      ARR(sec2);AVZ(sec2,v2)
      Crear(salida)

      cant_distinta:=0

      Mientras NFDS(sec1) Y NFDS(sec2) Hacer
         //trato sec1
         Mientras v1 <> '#' Hacer 
            cant_entradas:=0
            Para i:=1 hasta 10 Hacer 
               AVZ(sec1,v1)
            FP 
            Si v1 = 'T' Entonces
               esTarjeta:=Verdadero
            Sino 
               esTarjeta:=Falso
            FS 
            AVZ(sec1,v1) //estoy parado en la cant de entradas
            cant_entradas:=CONVERTIR(v1)
            AVZ(sec1,v1) //estoy en la marca
         FM
         AVZ(sec1,v1) //estoy parado en una siguiente fila

         //trato sec2
         Mientras v2 <> '?' Hacer 
            compras:=0
            Para i:=1 hasta 6 Hacer 
               AVZ(sec2,v2)
            FP 
            Si v2 <> '#' Entonces
               Si esTarjeta Entonces
                  Mientras v2 <>'+' Entonces
                     Grabar(salida,v2)
                     AVZ(sec2,v2)
                  FM 
                  Grabar(salida,'+')
                  AVZ(sec2,v2) //estoy en el dni
                  Mientras v2 <> '?' Hacer
                     compras:=compras+1
                     Para i:=1 hasta 8 Hacer
                        Grabar(salida,v2)
                        AVZ(sec2,v2)
                     FP                     
                     Mientras v2 <> '.' Y v2 <>'?' Hacer
                        AVZ(sec2,v2)
                     FM   
                     Si v2 <>'?' Entonces
                        Grabar(salida,'+')
                        AVZ(sec2,v2)
                     Sino 
                        Grabar(salida,'#')   
                     FS    
                  FM
               Sino
                  Mientras v2 <> '+' Hacer
                     AVZ(sec2,v2) 
                  FM
                  AVZ(sec2,v2) //avanzo el usuario
                  Mientras v2 <>'?' Hacer 
                     Mientras v2 <> '.' Y v2 <>'?' Hacer                        
                        AVZ(sec2,v2)
                     FM
                     compras:=compras+1
                     Si v2 <>'?' Entonces
                        AVZ(sec2,v2)
                     FS 
                  FM        
               FS 
            Sino 
               //abandono el usu
               Mientras v2 <> '?' Hacer
                  AVZ(sec2,v2)
               FM
            FS               
         FM
         Si compras <> cant_entradas Entonces
            cant_distinta:=cant_distinta+1
         FS
         AVZ(sec2,v2) //estoy en una siguiente compra
      FM  
      Escribir('La cantidad de usuarios que compraron entradas distintas a las declaradas son:'cant_distinta)
      Cerrar(sec1);Cerrar(sec2);Cerrar(salida)
FINACCION      
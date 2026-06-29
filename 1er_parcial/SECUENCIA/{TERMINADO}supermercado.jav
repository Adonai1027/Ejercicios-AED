12345|L|789|Detergente Magistral||&||23456|F|078|Jamon Iberico||&|| [...] FDS
2710TS20|2208CD21|0101TS22||#||2710TS20|2208CD21|0101TS22||#||[...] FDS
Accion supermercado Es
    Ambiente
        sec1,sec2,salida:SECUENCIA DE CARACTERES
        v1,v2:CARACTER 
        mes1,mes2,mes_usuario1,mes_usuario2,cant_suc,cant_dom,total,stock:ENTERO
        Funcion CONVERTIR(c:CARACTER):ENTERO Es
            Segun (c) Hacer
                "0": CONVERTIR:= 0
                "1": CONVERTIR:= 1
                "2": CONVERTIR:= 2
                "3": CONVERTIR:= 3
                "4": CONVERTIR:= 4
                "5": CONVERTIR:= 5
                "6": CONVERTIR:= 6
                "7": CONVERTIR:= 7
                "8": CONVERTIR:= 8
                "9": CONVERTIR:= 9
            FS
        FF
    Proceso
        ARR(sec1);AVZ(sec1,v1)
        ARR(sec2);AVZ(sec2,v2)
        Crear(salida)

        Escribir('Ingrese el primer digito del mes:');Leer(mes_usuario1)
        Escribir('Ingrese el segundo digito del mes:');Leer(mes_usuario2)

        stock:=0
        mes1:=0
        mes2:=0

        Mientras NFDS(sec1) Y NFDS(sec2) Hacer
            
            Mientras v1 <>'&' Hacer
                Para i:=1 Hasta 6 Hacer
                    AVZ(sec1,v1)
                FP

                stock:=stock+(CONVERTIR(v1)*100)
                AVZ(sec1,v1)
                stock:=stock+(CONVERTIR(v1)*10)
                AVZ(sec1,v1)
                stock:=stock+CONVERTIR(v1)
                AVZ(sec1,v1) //estoy en nombre art

                cant_suc:=0
                cant_dom:=0

                Mientras v2 <>'#' Hacer
                    AVZ(sec2,v2);AVZ(sec2,v2) //avanzo el dd

                    mes1:=CONVERTIR(v2);AVZ(sec2,v2);mes2:=CONVERTIR(v2)

                    AVZ(sec2,v2);AVZ(sec2,v2) //me deja en FE

                    Segun v2 Hacer
                        'S':cant_suc:=cant_suc+1
                        'D':cant_dom:=cant_dom+1
                    FS
                    Para i:=1 Hasta 3 Hacer
                        AVZ(sec2,v2)
                    FP //estoy en el siguiente dia    
                FM
                AVZ(sec2,v2) //pase a otra venta

                //item1    
                total:=stock-cant_suc
                Si ((mes_usuario1=mes1) Y (mes_usuario2=mes2)) Entonces
                    Escribir('NOMBRE DEL ARTICULO:')
                    Mientras v1<>'&'Hacer
                        Escribir(sec1,v1)
                        AVZ(sec1,v1)
                    FM
                    Escribir('CANTIDAD ENTREGADO SUC:',cant_suc)
                    Escribir('CANTIDAD ENTREGADO DOM:',cant_dom)
                Sino    
                    Si total = 0 Entonces
                        Mientras v1<>'&'Hacer
                            Grabar(salida,v1)
                            AVZ(sec1,v1)
                        FM
                    Sino
                        Mientras v1<>'&'Hacer
                            AVZ(sec1,v1)
                        FM
                    FS  
                FS      
            FM
            AVZ(sec1,v1) //paso al siguiente codart
        FM
        Cerrar(sec1);Cerrar(sec2);Cerrar(salida)
FinAccion    
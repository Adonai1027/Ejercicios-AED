Resistencia&025|Barranqueras&126|...
3|2950|4|9864||1
Accion ParcialISI_C Es 
    Ambiente    
        sec1,salida:secuencia de caracteres 
        v1: caracter 
        sec2: secuencia de enteros 
        v2,total_efectivo,cantidad_ticket: entero
        porcentaje: real
    Proceso
        ARR(sec1);AVZ(sec1,v1)
        ARR(sec2);AVZ(sec2,v2)
        Crear(salida)

        porcentaje:=0
        total_efectivo:=0
        cantidad_ticket:=0

        Mientras NFDS(sec1) Y NFDS(sec2) Hacer
            Si v2 = 1 Entonces
                total_efectivo:=total_efectivo+1 //item 1 y 2  
                Mientras v1 <> '&' Hacer 
                    Grabar(salida,v1)
                    AVZ(sec1,v1)
                FM 
                AVZ(sec1,v1) //estoy en el primer digito de la cantidad de tickets
                cantidad_ticket:=(cantidad_ticket+(ConvertiraNumero(v1)*100)) //0
                AVZ(sec1,v1)
                cantidad_ticket:=(cantidad_ticket+(ConvertiraNumero(v1)*10)) //20
                AVZ(sec1,v1)
                cantidad_ticket:=(cantidad_ticket+(ConvertiraNumero(v1))) //25
                AVZ(sec1,v1) //me posiciono en la siguiente sucursal

                Para i:=1 Hasta cantidad_ticket Hacer
                    Para j:=1 Hasta 4 Hacer
                        AVZ(sec2,v2)
                    FP    
                    Si v2 = 1 Entonces 
                        total_efectivo:=total_efectivo+1
                    FS        
                FP
                Grabar(salida,total_efectivo)
            Sino 
                Mientras v1 <>'&' Hacer 
                    Grabar(salida,v1)
                    AVZ(sec1,v1)
                FM 
                AVZ(sec1,v1) //estoy en el primer digito de la cantidad de tickets
                cantidad_ticket:=(cantidad_ticket+(ConvertiraNumero(v1)*100)) //100
                AVZ(sec1,v1)
                cantidad_ticket:=(cantidad_ticket+(ConvertiraNumero(v1)*10)) //120
                AVZ(sec1,v1)
                cantidad_ticket:=(cantidad_ticket+(ConvertiraNumero(v1))) //126
                AVZ(sec1,v1) //me posiciono en la siguiente sucursal 
                //Estoy en la siguiente sucursal ya que el metodo de pago no es por efectivo y no cumple ningun item
                Para i:=1 Hasta cantidad_ticket Hacer
                    Para j:=1 Hasta 4 Hacer
                        AVZ(sec2,v2)
                    FP
                    Si v2 = 1 Entonces
                       total_efectivo:=total_efectivo + 1
                    FS   
                FP 
                Grabar(salida,total_efectivo)
            FS
        FM
        porcentaje:=((total_efectivo/cantidad_ticket)*100)
        Escribir('El total de ventas en efectivo sobre la cantidad de tickets es:',porcentaje)
        Cerrar()
FinAccion         

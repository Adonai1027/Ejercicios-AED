Accion peaje Es 
    Ambiente 
        sec,salida:SECUENCIA DE CARACTERES
        v,categoria_usuario,EsFeriado,categoria:CARACTER 
        cantidad_recaudada,cont_cat1,cont_cat2,cont_cat3,cont_cat4,cont_cat5,total_vehiculos:ENTERO
        bandera,EsFeriado:LOGICO
    Proceso
        ARR(sec);AVZ(sec,v);CREAR(salida)
        Escribir('Ingrese la categoria[1..5]:');Leer(categoria_usuario);

        total_vehiculos:=0

        Mientras NFDS(sec) Hacer
            cantidad_recaudada:=0 //se resetea cada dia

            Para i:= 1 hasta 2 Hacer
                AVZ(sec,v)
            FP

            Si v = 'S' Entonces
                EsFeriado:=Verdadero
            Sino
                EsFeriado:=Falso 
            FS

            AVZ(sec,v) //estoy en categoria

            Mientras v <> '!' Hacer 
                cantidad_recaudada:=cantidad_recaudada+monto_peaje(v)

                categoria:=v 

                Si categoria = categoria_usuario Entonces
                    bandera:=Verdadero
                Sino
                    bandera:=Falso
                FS        

                total_vehiculos := total_vehiculos + 1

                Segun categoria Hacer
                    '1':cont_cat1:=cont_cat1+1
                    '2':cont_cat2:=cont_cat2+1
                    '3':cont_cat3:=cont_cat3+1
                    '4':cont_cat4:=cont_cat4+1
                    '5':cont_cat5:=cont_cat5+1
                FS 
                AVZ(sec,v) //estoy en hhmm

                Si (bandera Y EsFeriado) Entonces
                    Para i:=1 Hasta 11 Hacer 
                        Grabar(salida,v)
                        AVZ(sec,v)
                    FP
                    Grabar(salida,'#')
                Sino 
                    Para i:=1 Hasta 11 Hacer 
                        AVZ(sec,v)
                    FP
                FS
            FM 
            Escribir('Al final del dia se recaudo:',cantidad_recaudada)  
            AVZ(sec,v) //avanzo al dia siguiente   
        FM  

        Si total_vehiculos > 0 Entonces 
            Escribir('El porcentaje de vehiculos de la categoria 1 es:',((cont_cat1/total_vehiculos)*100)) 
            Escribir('El porcentaje de vehiculos de la categoria 2 es:',((cont_cat2/total_vehiculos)*100))
            Escribir('El porcentaje de vehiculos de la categoria 3 es:',((cont_cat3/total_vehiculos)*100))
            Escribir('El porcentaje de vehiculos de la categoria 4 es:',((cont_cat1/total_vehiculos)*100))
            Escribir('El porcentaje de vehiculos de la categoria 5 es:',((cont_cat1/total_vehiculos)*100))
        FS
        Cerrar(sec);Cerrar(salida)
FinAccion    

Accion cortepeaje Es
    Ambiente 
        PEAJE = Registro
            clave = Registro
                aaaa: entero
                mm: 1..12
                aa: 1..31
                categoria: 1..5
                patente: AN(7)
            FR 
            cantidad_pases: N(5)
        FR 

        arch: archivo secuencial de Peaje ordenado por clave
        reg: registro de peaje 

        salida = Registro 
            aaaa: entero
            mm: 1..12
            dd: 1..31 
            cantidad_pases: N(5) 
        FR 

        s_arch: archivo de salida 
        s_reg: registro de salida 

        totalDia,totalAnio,totalMes,totalGral,mayorPasesAnio,anioConMayorPases: entero 
        resgAnio:entero
        resgMes: 1..12
        resgDia: 1..31

        Procedimiento CorteDia() Es 
            Escribir('El total de vehiculos que pasaron por dia es:',totalDia)
            totalMes:= totalMes + totalDia
            s_reg.aaaa:=resgAnio
            s_reg.mm:=resgMes
            s_reg.dd:=resgDia
            s_reg:=reg.cantidad_pases
            Grabar(s_arch,s_reg)
            totalDia:=0
            resgDia:=reg.clave.dd
        FP
        
        Procedimiento CorteMes() Es 
            CorteDia()
            Escribir('El total de vehiculos que pasaron por mes es:',totalMes)
            totalAnio:= totalAnio + totalMes
            totalMes:=0
            resgMes:=reg.clave.mm
        FP 
        Procedimiento CorteAnio() Es 
            CorteMes()
            Escribir('El total de vehiculos que pasaron por anio es:',totalAnio)
            totalGral:= totalGral + totalAnio
            Si totalAnio > mayorPasesAnio Entonces 
                mayorPasesAnio := totalAnio;
                anioConMayorPases := resgAnio;
            FS 
            totalAnio:=0
            resgAnio:=reg.clave.aaaa
        FP
    Proceso 
        Abrir E/(arch);Leer(arch,reg);Abrir /S(arch);

        resgAnio:=reg.clave.aaaa
        resgMes:=reg.clave.mm
        resgDia:=reg.clave.dd

        totalMes:=0
        totalAnio:=0  
        mayorPasesAnio:=0 
        anioConMayorPases:=0 

        Mientras NFDA(arch) Entonces
            Si resgAnio <> reg.clave.aaaa Entonces 
                CorteAnio()
            Sino 
                Si resgMes <> reg.clave.mm Entonces
                    CorteMes()
                Sino 
                    Si resgDia <> reg.clave.dd Entonces
                        CorteDia()
                    FS 
                FS         
            FS 
            mayorPasesAnio:=mayorPasesAnio+1  
        FM 
        CorteAnio()                   
        Escribir('La cantidad total de vehiculos que pasaron es:',totalGral)
        Escribir('El anio en que hubo mayor cantidad de pases es:',anioConMayorPases,', con:',mayorPasesAnio)
        Cerrar(arch);Cerrar(salida)
FinAccion        


12|S|1|1551|ABC123D|2|1615|ABC321E!|13|N|3|1001|ABC213F|5|2323|ABC555D!...



Accion simulacro2023 Es 
    Ambiente
        sec1,sec2,salida:SECUENCIA DE CARACTERES
        v1,v2,genero:CARACTER 
        suma_canciones,cantidad_cancion,cont_playlist,total_genero:ENTERO 
        cont_rock,cont_pop,cont_electronica,cont_folk:ENTERO 

        FUNCION CONVERTIR(c:caracter):entero ES
            SEGUN (c) HACER
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
            FINSEGUN
        FINFUNCION
    Proceso
        ARR(sec1);AVZ(sec1,v1)
        ARR(sec2);AVZ(sec2,v2)
        Crear(salida)

        cont_rock:=0
        cont_pop:=0
        cont_electronica:=0
        cont_folk:=0

        total_genero:=0
        cont_playlist:=0
        cantidad_cancion:=0
        suma_canciones:=0

        Mientras NFDS(sec1) y NFDS(sec2) Hacer 
            Mientras v1 <> '?' Hacer
                genero:=v1
                total_genero:=total_genero+1 //item 1
                cont_playlist:=cont_playlist+1 //item 2
                Segun genero Hacer 
                    'R':cont_rock:=cont_rock+1
                    'P':cont_pop:=cont_pop+1
                    'E':cont_electronica:=cont_electronica+1
                    'F':cont_folk:=cont_folk+1
                FS            
                //item 4
                Si genero = 'R' Entonces
                    Grabar(salida,v1)
                    Grabar(salida,'+')
                    AVZ(sec1,v1);AVZ(sec1,v1) //avz el + y me posiciono en el nombplay
                    Mientras v1 <> '+' Hacer
                        Grabar(salida,v1)
                        AVZ(sec1,v1)
                    FM 
                    Grabar(salida,'+')
                    AVZ(sec1,v1) //estoy en el usuario
                    Mientras v1 <> '+' Hacer
                        AVZ(sec1,v1)
                    FM
                    AVZ(sec1,v1) //estoy en [a]ammdd 
                    //barro aammdd+hhmm+
                    Para i:=1 Hasta 12 Hacer
                        AVZ(sec1,v1)
                    FP 
                    cantidad_cancion:= (cantidad_cancion+(CONVERTIR(v1)*100))
                    AVZ(sec1,v1)
                    cantidad_cancion:= (cantidad_cancion+(CONVERTIR(v1)*10))
                    AVZ(sec1,v1)
                    cantidad_cancion:= (cantidad_cancion+CONVERTIR(v1))
                    AVZ(sec1,v1) //estoy en ?
                    Mientras v2 <>'/' Hacer
                        Mientras v2 <>'#' Hacer
                            Grabar(salida,v2)
                            AVZ(sec2,v2)
                        FM 
                        Grabar(salida,'+')
                        AVZ(sec2,v2) //estoy en [m]mss
                        Para i:=1 Hasta 10 Hacer 
                            AVZ(sec2,v2) //limpio el mmss#aaaa#
                        FP
                        Mientras v2 <>'#' y v2 <> '/' Hacer
                            Grabar(salida,v2)
                            AVZ(sec2,v2)
                        FM 
                        //control de bucle y evitar poner '+#'
                        Si v2 <> '/' Entonces
                            AVZ(sec2,v2)
                            Grabar(salida,'+') 
                        Sino
                            Grabar(salida,'#')  
                        FS      
                    FM
                    AVZ(sec2,v2) //me posiciono en el siguiente nombre de la siguiente playlist
                Sino 
                    AVZ(sec1,v1);AVZ(sec1,v1) //avz el + y me posiciono en el nombplay
                    Mientras v1 <> '+' Hacer
                        AVZ(sec1,v1)
                    FM
                    AVZ(sec1,v1) //estoy en el usuario
                    Mientras v1 <> '+' Hacer
                        AVZ(sec1,v1)
                    FM
                    AVZ(sec1,v1) //estoy en el aammdd
                    Para i:=1 Hasta 12 Hacer
                        AVZ(sec1,v1)
                    FP 
                    cantidad_cancion:= (cantidad_cancion+(CONVERTIR(v1)*100))
                    AVZ(sec1,v1)
                    cantidad_cancion:= (cantidad_cancion+(CONVERTIR(v1)*10))
                    AVZ(sec1,v1)
                    cantidad_cancion:= (cantidad_cancion+CONVERTIR(v1))
                    AVZ(sec1,v1) //estoy en ?
                FS
                suma_canciones:= suma_canciones+cantidad_cancion   
            FM
            AVZ(sec1,v1) //avanzo a la siguiente playlist
        FM
        //item 1
        Escribir('El porcentaje de playlist del genero electronica es:',((cont_electronica/total_genero)*100))
        Escribir('El porcentaje de playlist del genero pop es:',((cont_pop/total_genero)*100))
        Escribir('El porcentaje de playlist del genero folklore es:',((cont_folk/total_genero)*100))
        Escribir('El porcentaje de playlist del genero rock es:',((cont_rock/total_genero)*100))
        //item 2
        Escribir('El promedio de canciones de la playlist es:',(suma_canciones/cont_playlist))
        Cerrar(sec1);Cerrar(sec2);Cerrar(salida)
FinAccion
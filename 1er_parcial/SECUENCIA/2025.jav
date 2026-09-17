//parcial 2025 secuencia

Accion fifa Es 
   Ambiente
      sec1,sec2: SECUENCIA DE CARACTERES
      v1,v2: CARACTER
      salida: SECUENCIA DE CARACTERES

      goles,cantJugadores,cantPorConf, decena, unidad, roja, asistencia, m1,m2,m3,m4, totalMinutos: ENTERO

   Proceso
      ARR(sec1);AVZ(sec1,v1);
      ARR(sec2);AVZ(sec2,v2);
      CREAR(salida);
      Mientras NFDS(sec1) y NFDS(sec2) Hacer
         cantPorConf:=0;
         Mientras v1 <> '$' Hacer
            
            Mientras v1 <> '-' Hacer
               AVZ(sec1,v1)
            FM
            AVZ(sec1,v1) //parado en el primer caracter del nombre de equipo

            cantJugadores:=0;
            //guardo el nombre del equipo en la salida porque se asume que al menos 1 jugador cumple con la condicion
            Mientras v1 <> '-' Hacer
               Grabar(salida,v1)
               AVZ(sec1,v1)
            FM
            GRABAR(salida,'/')
            AVZ(sec1,v1) //parado en el nombre del jugador

            Mientras v1 <> '%' Hacer
               Mientras v1 <> '#' Hacer
                  //obtengo goles
                  decena:= VALOR(v2)*10
                  AVZ(sec2,v2)
                  unidad:= VALOR(v2)
                  goles:= decena+unidad
                  AVZ(sec2,v2)
                  //obtengo asistencias
                  decena:= VALOR(v2)*10
                  AVZ(sec2,v2)
                  unidad:= VALOR(v2)
                  asistencia:=decena+unidad
                  AVZ(sec2,v2)
                  //estoy en el inicio de los minutos
                  m1:=VALOR(v2)*1000;AVZ(sec2,v2)
                  m2:=VALOR(v2)*100;AVZ(sec2,v2)
                  m3:=VALOR(v2)*10;AVZ(sec2,v2)
                  m4:=VALOR(v2);AVZ(sec2,v2)
                  totalMinutos:=m1+m2+m3+m4
                  Si totalMinutos >= 1000 Entonces
                     cantPorConf:=cantPorConf+1 //ej3
                  FS
                  //estoy en amarilla
                  AVZ(sec2,v2)
                  //estoy en roja
                  roja:=VALOR(v2)
                  AVZ(sec2,v2) //estoy en la marca
                  AVZ(sec2,v2) //me quedo en el siguiente jugador
                  //01 01 0270 0 0|#|-

                  //ej1
                  Si (goles >= (3*(asistencia+roja))) Entonces
                     Mientras v1<>'*'Hacer
                        GRABAR(salida,v1)
                        AVZ(sec1,v1)
                     FM
                     GRABAR(salida,'_')
                     Mientras v1 <>'#' Hacer
                        AVZ(sec1,v1)
                     FM 
                  Sino
                     Mientras v1<>'#' Hacer
                        AVZ(sec1,v1)
                     FM
                  FS
                  cantJugadores:=cantJugadores+1
               FM
               AVZ(sec1,v1) //otro jugador o equipo
            FM
            AVZ(sec1,v1) //parado en el nombre del siguiente equipo o posible confederación
            GRABAR(salida,'%')
            //ej2
            Escribir('La cantidad de jugadores del equipo actual es de: ',cantJugadores)
         FM
         //ej3
         Escribir('La cantidad de jugadores que jugaron mas de 1000 minutos en la confederacion actual es: ',cantPorConf)
      FM
      CERRAR(sec1);CERRAR(sec2);CERRAR(salida)
FIN_ACCIÓN
//salida:= Real Madrid/Luka Modric_%
// 01 01 0270 0 0|#|0403031210|#|0201024500#0501030010|#|0600028001|#|*|
// UEFA-RealMadrid-LukaModric*3801#ViniciusJr*2402#JudeBellingham*2103#%ManchesterCity-KevinDeBruyne*3304#ErlingHaaland*2405#PhilFoden*2306#  |%|equipo  |$|confederacion
Accion bioquimico Es
  Ambiente
    sec,salida:SECUENCIA DE CARACTERES
    v:CARACTER
    protocolo:LOGICO
    total_estudios,estudioA,estudioE,estudioI:ENTERO
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
    ARR(sec);AVZ(sec,v)
    Crear(salida)
    Mientras v<>'*' Hacer
      cantidad_estudios:=0
      Si v = 'A' Entonces
        protocolo:=Verdadero
      Sino 
        protocolo:=Falso
      FS 
      Para i:=1 hasta 5 Hacer
        AVZ(sec,v)
      FP
      Mientras v <>',' Hacer
        AVZ(sec,v)
      FP
      AVZ(sec,v) //estoy en el primer digito de la cant
      cantidad_estudios:=cantidad_estudios+(CONVERTIR(v)*10)
      AVZ(sec,v)
      cantidad_estudios:=cantidad_estudios+CONVERTIR(v)
      AVZ(sec,v) //estoy en el primer caracter de los codigos
      Para i:=1 hasta cantidad_estudios Hacer
        Segun v Hacer
          'A':estudioA:=estudioA+1
          'E':estudioE:=estudioE+1
          'I':estudioI:=estudioI+1
        FS 
        //item1  
        Si v='E' y protocolo Entonces
          Para i:=1 Hasta 4 Hacer
            Grabar(salida,v)
            AVZ(sec,v)
          FP
        Sino
          Para i:=1 Hasta 4 Hacer
            AVZ(sec,v)
          FP
        FS 
      FP
    FM 
    //item2
    Escribir('El total recaudado por el estudio A es:$'estudioA*300) 
    Escribir('El total recaudado por el estudio E es:$'estudioE*420)
    Escribir('El total recaudado por el estudio I es:$'estudioI*670)
    //item3
    Escribir('El porcentaje de estudios de tipo A es:',(estudioA/(estudioA+estudioE+estudioI))*100,'%')
    Cerrar(sec);Cerrar(salida)
FinAccion    
|A2462|Reina|,|03|A123|E345|E333||P2342|Rey|,|01|E888*    


  
  
Accion batallaNaval Es 
   Ambiente 
      tipo_jugador = Registro 
         tablero = ARREGLO[1..10,1..10] DE ENTERO 
         estadoBarco : ARREGLO[1..6] de ENTERO 
         barcoHundido:ENTERO   
         tiros_realizados:ENTERO   
      FR 

      jugadores: ARREGLO[1..5] de tipo_jugador
      i,fila,col,idBarco:ENTERO 
      tiro,minTiro,ganador:ENTERO 
      estanHundidos:BOOLEANO 

      Procedimiento InicializarJugadores() Es
         j, f, c, k: ENTERO
         Para j:=1 Hasta 5 Hacer
            jugadores[j].barcoHundido := 0
            jugadores[j].tiros_realizados := 0
            
            // 3 barcos de 1 casilla (IDs del 1 al 3)
            Para k:=1 Hasta 3 Hacer
               jugadores[j].estadoBarco[k] := 1
            FP
            
            // 2 barcos de 2 casillas (IDs 4 y 5)
            Para k:=4 Hasta 5 Hacer
               jugadores[j].estadoBarco[k] := 2
            FP
            
            // 1 barco de 4 casillas (ID 6)
            jugadores[j].estadoBarco[6] := 4
            
            // Tablero vacio (llenamos de agua = 0)
            Para f:=1 Hasta 10 Hacer
               Para c:=1 Hasta 10 Hacer
                  jugadores[j].tablero[f, c] := 0
               FP
            FP
            
            // *NOTA*: Aca se asumiria la llamada a una rutina externa 'UbicarBarcos(j)'
            // que posiciona los barcos colocando su ID correspondiente en las celdas elegidas.
         FP
      FP
   Proceso
      InicializarJugadores()
      // Se asume que los barcos ya estan ubicados en el tablero a esta altura.
      
      // Ciclo principal donde cada uno de los 5 jugadores realiza su partida
      Para i:=1 Hasta 5 Hacer
         Escribir('--- Turno del jugador ', i, ' ---')
         tiro := 1
         todos_hundidos := FALSO
         
         Mientras (tiro <= 25) Y (NO todos_hundidos) Hacer
            Escribir('Tiro numero: ', tiro)
            Escribir('Ingrese coordenada Fila (1 al 10): ')
            Leer(fila)
            Escribir('Ingrese coordenada Columna (1 al 10): ')
            Leer(col)
            
            // Validamos que las coordenadas esten dentro del rango del tablero
            Si (fila >= 1) Y (fila <= 10) Y (col >= 1) Y (col <= 10) Entonces
               idBarco := jugadores[i].tablero[fila, col]
               
               Si idBarco = 0 Entonces
                  Escribir('AGUA')
               Sino
                  Si idBarco = -1 Entonces 
                     Escribir('Ya habias atacado esta posicion, pierdes el impacto.')
                  Sino
                     // IMPACTO: Disminuimos las casillas vivas del barco atacado
                     jugadores[i].estadoBarco[idBarco] := jugadores[i].estadoBarco[idBarco] - 1
                     jugadores[i].tablero[fila, col] := -1 // Marcamos la casilla como impactada
                     
                     // Verificamos si era la ultima casilla que le quedaba al barco
                     Si jugadores[i].estadoBarco[idBarco] = 0 Entonces
                        Escribir('HUNDIMIENTO')
                        jugadores[i].barcoHundido := jugadores[i].barcoHundido + 1
                     Sino
                        Escribir('IMPACTO')
                     FS
                  FS
               FS
               
               jugadores[i].tiros_realizados := tiro
               
               // Condicion de corte anticipado si rompio todos
               Si jugadores[i].barcoHundido = 6 Entonces
                  todos_hundidos := VERDADERO
               FS
               
               tiro := tiro + 1
            Sino
               Escribir('Coordenadas invalidas. Desperdiciaste un tiro.')
               jugadores[i].tiros_realizados := tiro
               tiro := tiro + 1
            FS
         FM
      FP
      
      // Una vez terminadas las 5 partidas, buscamos al ganador
      minTiro := HIGHVALUE
      ganador := 0
      
      Para i:=1 Hasta 5 Hacer
         // Requisito para ganar: Debe haber hundido los 6 barcos
         Si jugadores[i].barcoHundido = 6 Entonces
            // Nos quedamos con el que lo hizo en la menor cantidad de tiros
            Si jugadores[i].tiros_realizados < min_tiros Entonces
               min_tiros := jugadores[i].tiros_realizados
               ganador := i
            FS
         FS
      FP
      
      // Emitir el resultado final
      Si ganador <> 0 Entonces
         Escribir('EL GANADOR ES EL JUGADOR: ', ganador)
         Escribir('Logro hundir todos los barcos en ', min_tiros, ' tiros.')
      Sino
         Escribir('Ningun jugador logro hundir todos los barcos.')
      FS
FIN_ACCIÓN
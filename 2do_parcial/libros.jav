Accion ejercicio Es 
   Ambiente
      libros=Registro
         nro:entero 
         titulo:AN 
         autor:AN 
         cant_hojas:entero 
      FR 

      V:arreglo[1..200] de libros 
      i:entero 

      autor_buscado:an

      //item 2
      posicion: entero 

      //item 3
      mayor_volumen: entero 

   Proceso 
   //se asume que el arreglo de 200 posiciones esta cargado
      autor_buscado:='Nicklaus Wirth'
      izq:=1
      der:=200
      medio:=(izq+der) DIV 2
      Mientras (V[medio].autor<>autor_buscado) Y (izq<der) Hacer 
         Si v[medio].autor > autor_buscado Entonces
            der:=medio-1
         Sino 
            izq:=medio+1 
         FS
         medio:=(izq+der) DIV 2
      FM 

      Si v[medio].autor = autor_buscado Entonces
         //item 1
         Esc('Libros de :',autor_buscado, ':')
         //item 3
         mayor_volumen:=-1
         titulo_mayor_volumen:=''
         i:=1
         Mientras (i <= 200) Y (v[i].autor=autor_buscado) Hacer 
            //item 1: muestro los titulos encontrados para ese autor
            Escribir(v[i].titulo)

            //item 3: mayor volumen
            Si v[i].cant_hojas>mayor_volumen Entonces 
               mayor_volumen:=v[i].cant_hojas 
               titulo_mayor_volumen:=v[i].titulo
            FS 
            i:=i+1
         FM
         //item 3: informar
         Escribir('El libro de mayor volumen de paginas fue: ',titulo_mayor_volumen)
         Escribir('Con un total de paginas de: ',mayor_volumen)
      Sino 
         Escribir('No se encontro libros de: ',autor_buscado)
      FS 

      titulo_buscado:='ALGORITMOS + ESTRUCTURAS DE DATOS = PROGRAMA'
      i:=1
      Mientras (v[i].titulo<>titulo_buscado) Y (i<200) Hacer 
         i:=i+1
      FM 


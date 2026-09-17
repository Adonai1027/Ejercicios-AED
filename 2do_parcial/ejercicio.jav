// Asumimos el arreglo LIBRO ya cargado de 1 a 200
// =======================================================
// PREMISAS 1 y 3 (Búsqueda Binaria por AUTOR - ordenado)
// =======================================================
autor_buscado:='Nicklaus Wirth'
izq:=1
der:=200
medio:=(izq+der) DIV 2
Mientras (LIBRO[medio].AUTOR<>autor_buscado) Y (izq<der) Hacer
   Si LIBRO[medio].AUTOR > autor_buscado Entonces
      der:=medio-1
   Sino 
      izq:=medio+1
   FS 
   medio:=(izq+der) DIV 2
FM 

Si LIBRO[medio].AUTOR=autor_buscado Entonces
   // Retrocedemos para ubicarnos en el PRIMER libro de este autor
   Mientras (medio>1) Y (LIBRO[medio-1].AUTOR=autor_buscado) Hacer
      medio:=medio-1
   FM
   
   max_hojas:=-1
   titulo_max:=''
   Escribir('Libros de ', autor_buscado, ':')
   
   // Recorrer todos los libros de este autor (Corte de control simple)
   Mientras (medio<=200) Y (LIBRO[medio].AUTOR=autor_buscado) Hacer
      // Premisa 1: Mostrar título
      Escribir(LIBRO[medio].TITULO)
      
      // Premisa 3: Buscar mayor cantidad de hojas
      Si LIBRO[medio].CANT_HOJAS > max_hojas Entonces
         max_hojas:=LIBRO[medio].CANT_HOJAS
         titulo_max:=LIBRO[medio].TITULO
      FS
      
      medio:=medio+1
   FM
   Escribir('El libro de mayor volumen es: ', titulo_max)
Sino 
   Escribir('No se encontraron libros de ', autor_buscado)
FS 

// =======================================================
// PREMISA 2 (Búsqueda Lineal por TÍTULO - desordenado)
// =======================================================
titulo_buscado:='Algoritmos + Estructuras de Datos=Programa'
i:=1
Mientras (LIBRO[i].TITULO<>titulo_buscado) Y (i<200) Hacer 
   i:=i+1
FM 

Si LIBRO[i].TITULO=titulo_buscado Entonces
   Escribir('El libro esta en la posicion: ', i)
Sino 
   Escribir('El libro no fue encontrado.')
FS

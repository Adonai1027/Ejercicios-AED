Accion e2f1 Es 
   Ambiente 
      sec1,sec2,salida:SECUENCIA DE CARACTERES
      v1,v2,categoria_usuario,pat1,pat2,pat3,pat4,pat5:CARACTER 
      cantidad_recaudada,cont_cat1,cont_cat2,cont_cat3,cont_cat4,cont_cat5,total_vehiculos,precio_cat1,precio_cat2,precio_cat3,precio_cat4,precio_cat5:ENTERO
      EsFeriado,categoria3,TieneDesc:LOGICO
      descuento:REAL
   Proceso
      ARR(sec1);ARR(sec2);AVZ(sec1,v1);AVZ(sec2,v2);CREAR(salida)

      cont_cat1:=0;cont_cat2:=0;cont_cat3:=0;cont_cat4:=0;cont_cat5:=0
      precio_cat1:=900;precio_cat2:=1800;precio_cat3:=2700;precio_cat4:=3400;precio_cat5:=4500

      Mientras NDFS(sec1) y NDFS(sec2) Hacer
         cantidad_recaudada:=0 //se resetea cada dia

         //27102003 S > 28102003 N >
         //1 S 1030 A1B2C > 2 N 1031 A1B2C > -

         //avanzo la fecha hasta el feriado en la sec1
         Para i:=1 Hasta 8 Hacer 
            AVZ(sec1,v1)
         FP
         //estoy en si es feriado o no
         Si v1 = 'S' Entonces
            EsFeriado:=Verdadero
         Sino
            EsFeriado:=Falso
         FS
         AVZ(sec1,v1) //me quedo en la siguiente fecha

         Mientras v2 <> '-' Hacer

            categoria3:=Falso

            Si EsFeriado Entonces
            
               Segun v2 Hacer
                  '1':cont_cat1:=cont_cat1+1
                  '2':cont_cat2:=cont_cat2+1
                  '3':cont_cat3:=cont_cat3+1;categoria3:=Verdadero
                  '4':cont_cat4:=cont_cat4+1
                  '5':cont_cat5:=cont_cat5+1
               FS
               
               AVZ(sec2,v2) //avanzo al descuento

               Si v2 = 'S' Entonces
                  TieneDesc:=Verdadero 
               Sino
                  TieneDesc:=Falso
               FS

               AVZ(sec2,v2) //estoy en hhmm
               
               Si categoria3 y TieneDesc Entonces
                  Para i:=1 hasta 4 Hacer
                     Grabar(salida,v2)
                     AVZ(sec2,v2)
                  FP
                  Grabar(salida,v2)
                  pat1:=v2
                  AVZ(sec2,v2)
                  Grabar(salida,v2)
                  pat2:=v2
                  AVZ(sec2,v2)
                  Grabar(salida,v2)
                  pat3:=v2
                  AVZ(sec2,v2)
                  Grabar(salida,v2)
                  pat4:=v2
                  AVZ(sec2,v2)
                  Grabar(salida,v2)
                  pat5:=v2
                  AVZ(sec2,v2)

                  Grabar(salida,'-')

                  descuento:=ObtenerDescuento(pat1,pat2,pat3,pat4,pat5)
                  cantidad_recaudada:=cantidad_recaudada+(precio_cat3-(precio_cat3*descuento))
               Sino
               //1 S |1|030 A1B2C > 2 N 1031 A1B2C > -
                  Para i:=1 hasta 4 Hacer
                     AVZ(sec2,v2)
                  FP

                  pat1:=v2
                  AVZ(sec2,v2)
                  
                  pat2:=v2
                  AVZ(sec2,v2)
                  
                  pat3:=v2
                  AVZ(sec2,v2)
                  
                  pat4:=v2
                  AVZ(sec2,v2)
                  
                  pat5:=v2
                  AVZ(sec2,v2)

               FS
                  AVZ(sec2,v2) //estoy en la siguiente categoria del vehiculo

      FM
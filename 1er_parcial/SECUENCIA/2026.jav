//legajo|apellido y nombre#|cant mat aprob|cant finales| 

//cod materia|nota
//rendi 3 finales entonces 

Accion 2026 Es 
   Ambiente 
      sec1: sec caracter
      sec2: sec entero
      v1:caracter
      v2:entero
      salida: sec caracter

      cant_desap,cant_promo,total_gral,decena,unidad,cantidadRendido,valor,i:entero 

   Proceso 
      ARR(sec1);AVZ(sec1,v1)
      ARR(sec2);AVZ(sec2,v2)
      crear(salida)
      //inicializo el total de examenes rendidos en 2025
      total_gral:=0
      //inicializo el total de examenes promocionados con 8 o mas
      contador:=0

      Mientras NFDS(sec1) Y NFDS(sec2) Hacer
      //27909|pereztournadonai#|07|03
         Mientras v1<>'#' Hacer 
            Grabar(v1,salida)
            avz(sec1,v1)
         FM 
         
         Grabar(v1,salida) //grabo el #
         AVZ(sec1,v1)
         //estoy en 0
         AVZ(sec1,v1);AVZ(sec1,v1)
         //estoy en 0 
         decena:=ConvNum(v1)*10
         avz(sec1,v1)
         unidad:=ConvNum(v1)

         cantidadRendido:=decena+unidad
         //para el total general
         total_gral:=total_gral+cantidadRendido

         AVZ(sec1,v1) //alumno nuevo

         //trato la 2da sec
         cant_desap:=0
         Para i:=1 Hasta cantidadRendido Hacer
            //avanzo el cod mat
            AVZ(sec2,v2)
            
            Si v2 < 6 Entonces
               cant_desap:=cant_desap+1
            Sino 
               Si v2>=8 Entonces
                  cant_promo:=cant_promo+1
               FS 
            FS
            avz(sec2,v2) //siguiente codigo
         FP 
         //quedo parado en el codigo de la materia del siguiente alumno
         Si cant_desap<10 Entonces
            Grabar('0',salida)
            GRABAR(ConvertiraCaracter(cant_desap),salida)
         Sino 
            GRABAR(ConvertiraCaracter(cant_desap DIV 10),salida)
            GRABAR(ConvertiraCaracter(cant_desap MOD 10),salida)
         FS
         //legajo|apynom#|cant finales desaprobado
      FM
      Cerrar(sec1)
      Cerrar(sec2)
      Cerrar(salida)
      //INFORMO EN PANTALLA EL PORCENTAJE
      Si total_gral>0 entonces
         Escribir('El porcentaje de examenes promocionados (nota >= 8) sobre el total general es: '(contador / total_gral)*100)
      Sino 
         Escribir('No se registraron examenes')
      FS
FinAccion






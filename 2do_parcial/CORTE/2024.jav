//Intentá definir el AMBIENTE para un archivo físico llamado ventas.dat que contenga registros con:cod_cat: cadena de caracteres.importe: numérico real.

Accion archivos Es 
   AMBIENTE
      ventas: REGISTRO
         cod_cat: AN(10)
         importe: REAL
      FIN_REGISTRO 
      arch: ARCHIVO de ventas
      reg: ventas

//parcial 2024 corte de control

Accion ejercicio2 Es
   AMBIENTE
      fechas = Registro 
         aaaa: entero 
         mm: 1..12 
         dd: 1..31
      FR

      peaje = Registro 
         clave = Registro 
            fecha: fechas 
            categoria: ('Auto','Camion','Moto')
            patente: AN(10)
         FR
         cant_pases: ENTERO
      FR 

      salida = Registro 
         fecha: fechas
         categoria: ('Auto','Camion','Moto')
         patente: AN(10)
      FR

      arch: archivo de peaje ordenado por clave
      reg: peaje
      sarch: archivo de salida
      sreg: salida   

      resg_anio, resg_mes, resg_dia: ENTERO

      //punto a
      total_anio, total_mes, total_dia, total_gral: ENTERO
      //punto b
      fecha_usu: fechas

      Procedimiento CorteDia() Es 
         Escribir('La cantidad total de vehiculos que pasaron mas de dos veces por dia es: ',total_dia)
         total_mes:= total_mes + total_dia
         total_dia:=0
         resg_dia:=reg.clave.fecha.dd
      FP
      
      Procedimiento CorteMes() Es 
         CorteDia()
         Escribir('La cantidad total de vehiculos que pasaron mas de dos veces por dia, en el mes es:',total_mes)
         total_anio:= total_anio + total_mes
         total_mes:=0
         resg_mes:=reg.clave.fecha.mm
      FP

      Procedimiento CorteAnio() Es 
         CorteMes()
         Escribir('La cantidad total de vehiculos que pasaron mas de dos veces por dia, en el anio es:',total_anio)
         total_gral:= total_gral + total_anio
         total_anio:=0
         resg_anio:=reg.clave.fecha.aaaa
      FP

   Proceso
      ABRIR E/(arch);Leer(arch,reg);
      ABRIR /S(sarch)

      resg_anio:=reg.clave.fecha.aaaa
      resg_mes:=reg.clave.fecha.mm
      resg_dia:=reg.clave.fecha.dd

      total_anio:=0;total_mes:=0;total_dia:=0;total_gral:=0;

      //punto b
      Escribir('Ingrese una anio: ');Leer(fecha_usu.aaaa);
      Escribir('Ingrese un mes: ');Leer(fecha_usu.mm);
      Escribir('Ingrese un dia: ');Leer(fecha_usu.dd);

      Mientras NFDA(arch) Hacer
         Si reg.clave.fecha.aaaa <> resg_anio Entonces
            CorteAnio()
         Sino 
            Si reg.clave.fecha.mm <> resg_mes Entonces
               CorteMes()
            Sino 
               Si reg.clave.fecha.dd <> resg_dia Entonces
                  CorteDia()
               FSi
            FSi
         FSi

         //punto a
         Si reg.cant_pases > 2 Entonces
            total_dia:= total_dia + 1
         FSi

         //punto b
         Si (reg.clave.fecha.aaaa = fecha_usu.aaaa) Y (reg.clave.fecha.mm = fecha_usu.mm) Y (reg.clave.fecha.dd = fecha_usu.dd)Entonces
            sreg.fecha:=reg.clave.fecha
            sreg.categoria:=reg.clave.categoria
            sreg.patente:=reg.clave.patente
            Grabar(sarch,sreg)
         FSi

         Leer(arch,reg)
      FM

      //cierres finales
      CorteAnio()
      Escribir('El total general de pases es:',total_gral)

      CERRAR(arch)
      CERRAR(sarch)
FinAccion
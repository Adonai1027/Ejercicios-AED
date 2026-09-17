ambiente
fecha = registro
  anio:N(4)
  mes:N(2)
  dia:N(2)
finregistro

Bicicleta = registro
 clavem = registro
   nro_serie: Entero
   modelo: Alfanumerico
  finregistro
  fecha_adquisicion: fecha 
  fecha_ult_mantenimiento: fecha  
finregistro
Novedad = registro
 clavem = registro
   nro_serie: Entero
   modelo: Alfanumerico
  finregistro
 tipo_novedad:(1..4)
 fecha_novedad: Alfanumerico
 hora_inicio: Alfanumerico 
 hora_fin: Alfanumerico
 circuito_nro: (1..6)
 id_usuario: Entero
finregistro
    
archbici,archsal: Archivo ordenado por clave 
archnove: Archivo ordenado por clavem
Regbici,regsal: Bicicleta
regnove: Novedad
  // Filas: 1 a 6 (Circuitos) | Columnas: 1 (Intensivo) y 2 (Recreativo)
Matriz_Costos: Arreglo [1..6, 1..2] de Real
circuito_buscado: Entero
cant_prestamos: Entero
total_recaudado: Real
horas_uso: Entero
costo_viaje: Real
 
procedimiento leermae es
  leer(archbici,Regbici)
  si fda(archbici) entonces
    regbici.clave:=HW
  finsi 
finprocedimiento 

procedimiento leermov es
  leer(archnove,regnove)
  si fda(archnove) entonces
    regnove.clavem:=HW
  finsi 
finprocedimiento 

procedimiento procesomovi es  
  si regnove.tipo_novedad = 1 entonces
   escribir("error no se puede dar de alta porque ya existe")
  sino 
    si regnove.tipo_novedad = 2 entonces 
     horas_uso := diff_horas(regnove.hora_inicio, regnove.hora_fin)
     Si horas_uso > 6 entonces
       precio_hora := Matriz_Costos[regnove.circuito_nro, 1]
       costo_viaje := 1500 + (precio_hora * horas_uso)
     Sino
       precio_hora := Matriz_Costos[regnove.circuito_nro, 2]
       costo_viaje := 1000 + (precio_hora * horas_uso)
     FinSi
     Si regnove.circuito_nro = circuito_buscado entonces
       total_recaudado := total_recaudado + costo_viaje
       cant_prestamos := cant_prestamos + 1
     FinSi
    sino 
     si regnove.tipo_novedad = 3 entonces
       aux.fecha_ult_mantenimiento:=regnove.fecha_novedad
      sino 
        // baja fisica 
      finsi 
    finsi
  finsi
finprocedimiento
 
proceso 
abrirE/(archbici);abrirE/(archnove)
abrir/S(archsal)
leermae
leermov
escribir("ingrese circuito")
leer(circuito_buscado)
total_recaudado:=0
cant_prestamos:=0

mientras (regbici.clave <> HW) o (regnove.clavem <> HW) hacer 
  si regbici.clave < regnove.clavem entonces
    regsal:=regbici
    escribir(archsal,regsal)
    leermae
  sino 
    si  regbici.clave = regnove.clavem  entonces
      aux:=regbici
      mientras  regbici.clave = regnove.clavem hacer 
        procesomovi
        leermov
      finmientras 
      regsal:=aux
      escribir(archsal,regsal)
      leermae
    sino 
      si regbici.clave > regnove.clavem entonces
       si regnove.tipo_novedad = 1 entonces
         aux.clave:=regnove.clavem
         aux.fecha_adquisicion:=regnove.fecha_novedad
         aux.fecha_ult_mantenimiento:= "   "
         leermov
          mientras regbici.clave = regnove.clavem hacer
            procesomovi
           leermov
          finmientras
          regsal := aux
          escribir(archsal, regsal)
        sino
          escribir("Error")
          aux_clave := regnove.clavem
          mientras regbici.clave = regnove.clavem hacer
            leermov
          finmientras
        finsi 
      finsi 
    finsi 
  finsi 
finmientras

escribir(total_recaudado;cant_prestamos)
cerrar(archbici)
cerrar(archnove)
cerrar(archsal)
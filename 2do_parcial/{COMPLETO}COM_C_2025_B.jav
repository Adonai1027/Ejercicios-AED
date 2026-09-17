// parcial 2025 actualizacion

Accion primerejercicio Es 
   Ambiente 
      formato_fecha=Registro
         aaaa: N(4)
         mm: 1..12
         dd: 1..31
      FR 

      formato_clave=Registro
         tipo_equipo: AN(30)
         nro_equipo: N(2)
      FR

      equipo=Registro 
         clave=formato_clave
         fecha_adquisicion: formato_fecha
         fecha_ult_mantenim: formato_fecha
         horas_de_uso: 0..23
         disponibilidad: ('Si', 'No')
      FR 

      arch_mae, mae_act: archivo de equipo ordenado por clave 
      reg_mae, reg_act,aux: equipo

      novedades=Registro 
         clave=formato_clave
         tipo_novedad: 1..3
         fecha_novedad: formato_fecha
         hora_inicio: 0..23
         hora_fin: 0..23
         nro_circuito: 1..6
         id_usuario: N(4)
      FR 
      arch_mov: archivo de novedades ordenado por clave
      reg_mov: novedades 

      Procedimiento LeerMae() Es 
         Leer(arch_mae,reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.clave:=HV
         FS
      FP 

      Procedimiento LeerMov() Es 
         Leer(arch_mov,reg_mov)
         Si FDA(arch_mov) Entonces  
            reg_mov.clave:=HV 
         FS 
      FP 

      //ej2
      cant_equipo, horas_usu: ENTERO 
      //ej3
      cant_baja:ENTERO 

      Procedimiento Procesos_Iguales() Es 
         Segun reg_mov.tipo_novedad Hacer 
            =1: Escribir('ALTA')
            =2: aux.horas_de_uso:=aux.horas_de_uso+diff(reg_mov.hora_inicio,reg_mov.hora_fin)
            =3: aux.disponibilidad:= 'No'; cant_baja:=cant_baja+1
         FS
      FP 

      //ej2
      Procedimiento Estadistica() Es
         Si (aux.fecha_ult_mantenim.mm = 1 O aux.fecha_ult_mantenim.mm = 2) Y (aux.horas_de_uso > horas_usu) Entonces
            cant_equipo := cant_equipo + 1
         FS
      FP

   Proceso
      ABRIR E/(arch_mae); LeerMae()
      ABRIR E/(arch_mov); LeerMov()
      ABRIR /S(mae_act)

      cant_equipo:=0;cant_baja:=0

      Escribir('Ingrese las horas de uso: '); Leer(horas_usu)

      Mientras reg_mae.clave<>HV O reg_mov.clave<>HV Hacer 
         Si reg_mae.clave < reg_mov.clave Entonces 
            reg_act:=reg_mae
            Estadistica()
            Grabar(mae_act, reg_act)
            LeerMae()
         Sino 
            //movimientos
            Si reg_mae.clave = reg_mov.clave Entonces 
               aux:=reg_mae
               Mientras reg_mae.clave = reg_mov.clave Hacer 
                  Procesos_Iguales()
                  LeerMov()
               FM
               Estadistica()
               reg_act:=aux
               Grabar(mae_act,reg_act)
               LeerMae()
            Sino
               //caso donde mae > mov o sea, hay alta
               Si reg_mov.tipo_novedad=1 Entonces
                  aux.clave := reg_mov.clave 
                  aux.fecha_adquisicion:=reg_mov.fecha_novedad
                  aux.fecha_ult_mantenim:=reg_mov.fecha_novedad
                  aux.horas_de_uso:=diff(reg_mov.hora_inicio,reg_mov.hora_fin)
                  aux.disponibilidad:='Si'
                  LeerMov()
                  Mientras aux.clave=reg_mov.clave Hacer 
                     Procesos_Iguales()
                     LeerMov()
                  FM
                  Estadistica() 
                  reg_act:=aux 
                  Grabar(mae_act,reg_act)
               Sino 
                  Escribir('ERROR: PRESTAMOS Y BAJAS NO POSIBLES')
               FS
               LeerMov()
            FS 
         FS 
      FM 
      Escribir('Cantidad de equipo en enero y febrero: ',cant_equipo)
      Escribir('La cantidad de equipos que se dieron de baja es de: ',cant_baja)
      CERRAR(arch_mae)
      CERRAR(arch_mov)
      CERRAR(mae_act)
FIN_ACCIÓN

// procesos estadistico (pertenece arriba)
Accion pestadistico(costos: Arreglo[1..6] de Real) Es 
   Ambiente 
      formato_fecha=Registro
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      novedades=Registro 
         tipo_equipo: AN(30)
         nro_equipo: N(2)
         tipo_novedad: 1..3
         fecha_novedad: formato_fecha
         hora_inicio: 0..23
         hora_fin: 0..23
         nro_circuito: 1..6
         id_usuario: N(4)
      FR
      arch: archivo de novedades ordenado por tipo_equipo y nro_equipo
      reg: novedades

      usuario=Registro 
         id_usuario: N(4)
         dni:N(8)
         sexo: ('m','f')
         apellido_y_nombre: AN(255)
         lugar_de_origen:1..4
         edad:N(2)
      FR 
      arch_index: archivo de usuario indexado por id_usuario
      reg_index: usuario

      A:Arreglo[1..7,1..5] de Real 
      i,j: Entero 
      importe_recaudado:REAL
      monto_usu:REAL

      Funcion lugar(n:ENTERO):AN Es 
         Segun n Hacer 
            =1:'Chaco'
            =2:'Otra Provincia'
            =3:'País limitrofe'
            =4:'Otro Pais'
         FS 
      FS 
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)
      ABRIR E/(arch_index);

      Para i=1 hasta 7 Hacer 
         Para j=1 hasta 5 Hacer 
            A[i,j]:=0
         FP 
      FP 

      Escribir('Ingrese un monto: ');Leer(monto_usu)

      Mientras NFDA(arch) Hacer 
         Si reg.tipo_novedad = 2 Entonces 
            reg_index.id_usuario := reg.id_usuario
            Leer(arch_index, reg_index)
            Si EXISTE Entonces   
               i := reg.nro_circuito
               j := reg_index.lugar_de_origen

               importe_recaudado := diff(reg.hora_inicio, reg.hora_fin) * costos[i]
               // cargo matriz
               A[i, j] := A[i, j] + importe_recaudado 
               A[i, 5] := A[i, 5] + importe_recaudado // Total por circuito
               A[7, j] := A[7, j] + importe_recaudado // Total por origen
               A[7, 5] := A[7, 5] + importe_recaudado // Total General (opcional pero recomendado)
            Sino 
               Escribir('ERROR. NO EXISTE EL USUARIO: ', reg.id_usuario)
            FS 
         FS 
         Leer(arch, reg)
      FM 

      // --- INFORMES ---
      Para i := 1 hasta 6 Hacer 
         Escribir('Circuito N°: ', i, ' - Total Recaudado: $', A[i, 5])
         Para j := 1 hasta 4 Hacer  
            Escribir('  Origen: ', lugar(j), ' - Importe: $', A[i, j])
         FP 
      FP 
      Para j := 1 hasta 4 Hacer 
         Escribir('El total para ', lugar(j), ' fue: $', A[7, j])
         // ITEM B: Se compara el TOTAL del origen contra el monto ingresado
         Si A[7, j] > monto_usu Entonces 
            Escribir(lugar(j), ' superó el monto de $', monto_usu)
         FS
      FP
      Cerrar(arch);CERRAR(arch_ind)
FIN_ACCIÓN
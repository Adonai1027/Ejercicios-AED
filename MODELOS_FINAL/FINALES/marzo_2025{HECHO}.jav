Accion ej1 Es 
   Ambiente 
      resultados=Registro  
         nro_estudiante:N(6)
         escuela:1..12
         materia:1..3 //1M 2L 3C
         calificacion:N(2)
         fecha=Registro 
            aaaa:N(4)
            mm:1..12
            dd:1..31
         FR 
         valido:('S','N')
      FR 
      arch:archivo secuencial de resultados
      reg:resultados

      matriz=Registro 
         acum_notas:N(3)
         acum_alumnos:N(3)
      FR 
      i,j:ENTERO
      M:ARREGLO[1..13,1..4] DE matriz
      cant_sup,resg_mejor,resg_peor,peor,mejor:ENTERO
      
      prom_actual,p_m,p_c,p_l,prom_escuela_gral,promedio_nacional,matematica_nacional,lengua_nacional,ciencia_nacional:REAL
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)

      Para i:=1 Hasta 13 Hacer 
         Para j:=1 Hasta 4 Hacer 
            M[i,j].acum_alumnos:=0
            M[i,j].acum_notas:=0
         FP 
      FP 

      Mientras NFDA(arch) Hacer 
         i:=reg.escuela
         j:=reg.materia
         Si reg.valido='S' Entonces 
            M[i,j].acum_notas:=M[i,j].acum_notas+reg.calificacion
            M[13,j].acum_notas:=M[13,j].acum_notas+reg.calificacion
            M[i,4].acum_notas:=M[i,4].acum_notas+reg.calificacion
            M[13,4].acum_notas:=M[13,4].acum_notas+reg.calificacion

            M[i,j].acum_alumnos:=M[i,j].acum_alumnos+1
            M[13,j].acum_alumnos:=M[13,j].acum_alumnos+1
            M[i,4].acum_alumnos:=M[i,4].acum_alumnos+1
            M[13,4].acum_alumnos:=M[13,4].acum_alumnos+1
         FS 
         Leer(arch,reg)
      FM 
      Si M[13,4].acum_alumnos>0 Entonces
         promedio_nacional:=(M[13,4].acum_notas / M[13,4].acum_alumnos)
         matematica_nacional:=(M[13,1].acum_notas / M[13,1].acum_alumnos)
         lengua_nacional:=(M[13,2].acum_notas / M[13,2].acum_alumnos)
         ciencia_nacional:=(M[13,3].acum_notas / M[13,3].acum_alumnos)
      FS
      //recorro la matriz de la escuela (la de alumnos)
      Para i:=1 Hasta 12 Hacer 
         Escribir('RESULTADOS PARA EL COLEGIO NUMERO: ',i)
         //ITEM 1
         p_m:=(M[i,1].acum_notas / M[i,1].acum_alumnos)
         p_l:=(M[i,2].acum_notas / M[i,2].acum_alumnos)
         p_c:=(M[i,3].acum_notas / M[i,3].acum_alumnos)
         prom_escuela_gral:=(M[i,4].acum_notas / M[i,4].acum_alumnos)

         cant_sup:=0
         Si p_m>matematica_nacional Entonces
            Escribir('SUPERA EN MATEMATICAS')
            cant_sup:=cant_sup+1
         FS 
         Si p_l>lengua_nacional Entonces
            Escribir('SUPERA EN LENGUA')
            cant_sup:=cant_sup+1
         FS 
         Si p_c>ciencia_nacional Entonces
            Escribir('SUPERA EN CIENCIAS')
            cant_sup:=cant_sup+1
         FS 

         //ITEM 2
         Si prom_escuela_gral>promedio_nacional Entonces
            Escribir('Esta escuela supera el desempeño nacional')
         FS 

         //ITEM 3
         mejor:=LV
         peor:=HV 
         Para j:=1 Hasta 3 Hacer 
            prom_actual:=(M[i,j].acum_notas / M[i,j].acum_alumnos)
            Si prom_actual>mejor Entonces
               mejor:=prom_actual
               resg_mejor:=j
            FS 
            Si prom_actual<peor Entonces
               peor:=prom_actual
               resg_peor:=j
            FS 
         FP 
         Escribir('Mejor materia: ',resg_mejor,' Peor: ',resg_peor)

         //item 4
         Si cant_sup>=2 Entonces
            Escribir('La escuela supera el promedio nacional en al menos dos de las tres materias')
         FS
      FP 

      Cerrar(arch)
FinAccion
      
Accion ej2(D:ARREGLO[1..99] DE AN, T:ARREGLO[1..9] DE AN) Es 
   Ambiente 
      evento=Registro   
         clave=Registro
            pais:N(2)
            amenaza:N(1)
         FR 
         intentos:N(4)
         ip_origen:AN(15)
         ip_destino:AN(15)
         nivel:('B','M','A')
         fecha=Registro 
            aaaa:N(4)
            mm:1..12
            dd:1..31
         FR 
         hora:N(2)
      FR 
      arch:archivo secuencial de evento ordenado por clave 
      reg:evento

      total_amenaza,total_pais,resgPais,resgAmenaza:ENTERO
      //I2
      resgIP:AN
      mayorIP:ENTERO 
      //I3
      ataq_alto:ENTEROS 
      //i4 
      auxPais,mayorIntentos:ENTERO
      //i5
      A:ARREGLO[1..9] DE ENTERO
      i,auxAmenaza,masFrecuente:ENTERO
   
      Procedimiento CorteAmenaza() Es 
         Escribir('Para la amenaza: ',resgAmenaza,' El total de intentos fue: ',total_amenaza)
         //I2
         Escribir('La direccion de ip de origen con mayor cantidad de intentos fue: ',resgIP)
         total_pais:=total_pais+total_amenaza
         mayorIP:=LV
         total_amenaza:=0
         resgAmenaza:=reg.clave.amenaza 
      FP 
      Procedimiento CortePais() Es 
         CorteAmenaza()
         Escribir('Para el pais: ',resgPais,' el total fue de: ',total_pais)
         //i3
         Si total_pais>0 Entonces
            Escribir('Porcentaje de ataques altos: ',((ataq_alto*100)/total_pais),'%')
         FS
         //i4
         Si total_pais>mayorIntentos Entonces
            mayorIntentos:=total_pais
            auxPais:=resgPais
         FS 
         total_pais:=0
         ataq_alto:=0
         resgPais:=reg.clave.pais 
      FP 
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)

      total_pais:=0
      total_amenaza:=0
      ataq_alto:=0
      mayorIntentos:=LV 
      mayorIP:=LV 
      masFrecuente:=LV
      Para i:=1 Hasta 9 Hacer 
         A[i]:=0
      FP 

      resgPais:=reg.clave.pais 
      resgAmenaza:=reg.clave.amenaza
      Mientras NFDA(arch) Hacer 
         Si resgPais<>reg.clave.pais Entonces
            CortePais()
         Sino 
            Si resgAmenaza<>reg.clave.amenaza Entonces 
               CorteAmenaza()
            FS 
         FS 
         //i1
         total_amenaza:=total_amenaza+reg.intentos
         //i2
         Si reg.intentos>mayorIP Entonces
            mayorIP:=reg.intentos
            resgIP:=reg.ip_origen
         FS
         //i3
         Si reg.nivel='A' Entonces
            ataq_alto:=ataq_alto+1
         FS 
         //i5
         A[reg.clave.amenaza]:=A[reg.clave.amenaza]+reg.intentos

         Leer(arch,reg)
      FM 
      CortePais()
      //i4
      Escribir('El país con más intentos de ataques en TODO el proceso fue: ', D[auxPais])
      //i5
      Para i:=1 Hasta 9 Hacer
         Si A[i]>masFrecuente Entonces
            masFrecuente:=A[i]
            auxAmenaza:=i
         FS
      FP 
      Escribir('El tipo de amenaza mas frecuente a nivel global es: ',T[auxAmenaza])
      Cerrar(arch)
FinAccion

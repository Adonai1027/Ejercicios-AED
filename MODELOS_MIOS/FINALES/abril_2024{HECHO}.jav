Accion ej1 Es 
   Ambiente 
      sec:SECUENCIA DE CARACTER
      v:CARACTER

      NODO=Registro
         codpack:AN(50)
         cant_a,cant_d:ENTERO
         prox:PUNTERO A NODO 
      FR
      p,q,ant,prim:PUNTERO A NODO 

      acum_edad,cont_reg, i, cont_premium,cont_nula:ENTERO 
      edad1, edad2:CARACTER 
      resg_codpack:AN
   Proceso 
      ARR(sec);AVZ(sec,v)
      prim:=NIL 
      acum_edad:=0;cont_reg:=0;cont_premium:=0;cont_nula:=0;
      Mientras NFDS(sec) Hacer
         Mientras v<>'/' Y NFDS(sec) Hacer 
            Si v='P' Entonces 
               cont_premium:=cont_premium+1
            FS 
            AVZ(sec,v)

            Para i:=1 Hasta 6 Hacer 
               AVZ(sec,v)
            FP 
            
            edad1:=v; AVZ(sec,v);
            edad2:=v; AVZ(sec,v);

            //guardar codpack
            resg_codpack:=''
            Mientras v<>'-' Hacer
               resg_codpack:=resg_codpack+v
               AVZ(sec,v)
            FM 
            AVZ(sec,v)

            Si v='N' Entonces 
               acum_edad:=acum_edad+AGE(edad1,edad2)
               cont_nula:=cont_nula+1
            FS  

            //busco cod pack
            p:=prim
            ant:=NIL
            Mientras p<>NIL Y (*p.codpack<>resg_codpack) Hacer
               ant:=p 
               p:=*p.prox 
            FM 
            //ya esta cargado el codpack en la lista, actualizo contadores
            Si p<>NIL Entonces
               Segun v Hacer 
                  'A': *p.cant_a:=*p.cant_a+1
                  'D': *p.cant_d:=*p.cant_d+1
               FS 
            Sino
               //nuevo codpack
               NUEVO(q)
               *q.codpack:=resg_codpack
               Segun v Hacer 
                  'A': *q.cant_a:=1
                  'D': *q.cant_d:=1
               FS
               //carga encolada    
               Si prim=NIL Entonces 
                  prim:=q
                  *q.prox:=NIL
                  p:=q
               Sino 
                  *ant.prox:=q
                  *q.prox:=NIL
                  p:=q 
               FS 
               AVZ(sec,v)
            FS
         FM
         cont_reg:=cont_reg+1
      FM
      //punto 1 y 3
      p:=prim 
      Mientras p<>NIL Hacer
         Si (*p.cant_a>*p.cant_d) 
            Escribir('El pack: ',*p.codpack,' tiene mayor preferencias A que D')
         FS 
         Escribir('Cantidad de preferencias A: ',*p.cant_a)
         Escribir('Cantidad de preferencias D: ',*p.cant_d)
         p:=*p.prox 
      FM 

      //punto 2
      Si cont_nula>0 Entonces
         Escribir('Promedio de edad con preferencia nula: ',(acum_edad/cont_nula))
      FS

      //punto 4
      Si cont_reg>0 Entonces
         Escribir('Porcentaje: ',((cont_premium*100)/cont_reg))
      FS

      Cerrar(sec)
FINACCION

Accion ej2 Es 
   Ambiente
      control=Registro 
         mes_visita:1..12
         nro_empleado:N(6)
         raz_soc:AN(15)
         cat:1..3
         habilitado:('SI','NO')
         capacidad:N(3)
      FR 
      arch:archivo secuencial de control ordenado por mes_visita y nro_empleado
      reg:control 

      i,j:ENTERO 
      M:ARREGLO[1..12,1..3] DE ENTERO 

      cant_max,cant_no_hab,resg_mes,menor,resg_cat,cant_total:ENTERO  
   Proceso
      ABRIR E/(arch);Leer(arch,reg)

      cant_max:=0;cant_no_hab:=0;cant_total:=0;
      Para i:=1 Hasta 12 Hacer 
         Para j:=1 Hasta 3 Hacer 
            M[i,j]:=0
         FP 
      FP 

      Mientras NFDA(arch) Hacer 
         i:=reg.mes_visita
         j:=reg.cat 
         Si reg.habilitado='SI' Entonces
            M[i,j]:=M[i,j]+1
         Sino 
            cant_no_hab:=cant_no_hab+1
         FS 
         cant_total:=cant_total+1

         Si reg.capacidad>=0 Y reg.capacidad<=100 Entonces
            cant_max:=cant_max+1
         FS 

         Leer(arch,reg)
      FM 
      menor:=HV
      Para i:=1 Hasta 12 Hacer 
         Escribir('Para el mes: ',i)
         Para j:=1 hasta 3 Hacer 
            Escribir('Para la categoria: ',j,' la cantidad fue: ',M[i,j])
            Si (M[i,j]>0) Entonces
               Si (M[i,j]<menor) Entonces
                  menor:=M[i,j]
                  resg_cat:=j
                  resg_mes:=i
               FS 
            FS
         FP 
      FP 

      Escribir('Cantidad de restaurantes cuya capacidad no supera los 100: ',cant_max)

      Escribir('MES: ',resg_mes,' CATEGORIA: ',resg_cat,' tiene la menor cantidad de habilitaciones')

      Si cant_total>0 Entonces
         Escribir('PORCENTAJE: ',((cant_no_hab*100)/cant_total))
      FS 

      Cerrar(arch)
FINACCION





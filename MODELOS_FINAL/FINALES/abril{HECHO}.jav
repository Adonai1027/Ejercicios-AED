Accion ej1(N:ARREGLO[1..39] DE AN) Es 
   Ambiente 
      sec:SECUENCIA DE CARACTER 
      v:CARACTER

      NODO=Registro 
         id:N(5)
         titulo:AN(255)
         num_area:N(2)
         prox:PUNTERO A NODO 
      FR 
      p,q,ant:PUNTERO A NODO
      //tipo de proyecto
      prim:ARREGLO[1..3] DE PUNTERO A NODO 
      //arreglo de areas con mayores aplicacion
      A:ARREGLO[1..39] DE ENTERO
      mayor:ENTERO 
      area_nombre:AN 

      id_str,titulo_str,area_sin_proy:AN 
      aux_tipo,i,aux_area:ENTERO 

      Funcion ConvertirTipo(c:CARACTER):ENTERO Es 
         Segun c Hacer 
            '0': ConvertirTipo:=1
            '1': ConvertirTipo:=2
            '2': ConvertirTipo:=3
         FS 
      FF 

      Funcion Conv_Area(c:CARACTER):ENTERO Es 
         Segun c Hacer
         '0': Conv_Area:=0
         '1': Conv_Area:=1
         '2': Conv_Area:=2
         '3': Conv_Area:=3
         '4': Conv_Area:=4
         '5': Conv_Area:=5
         '6': Conv_Area:=6
         '7': Conv_Area:=7
         '8': Conv_Area:=8
         '9': Conv_Area:=9
         FS 
      FF 

      Procedimiento InsertarOrdenado(prim_tipo:PUNTERO A NODO, qs:PUNTERO A NODO) Es 
         Si prim_tipo=NIL Entonces 
            prim_tipo:=qs 
            *qs.prox:=NIL 
         Sino 
            p:=prim_tipo 
            ant:=NIL 
            Mientras (p<>NIL) Y (*p.num_area<*qs.num_area) Hacer 
               ant:=p 
               p:=*p.prox 
            FM 
            Si p=prim_tipo Entonces
               prim_tipo:=qs 
            Sino 
               *ant.prox:=qs 
            FS 
            *qs.prox:=p 
         FS 
      FP 
   Proceso 
      ARR(sec);AVZ(sec,v)

      Para i:=1 hasta 3 Hacer
         prim[i]:=NIL //vector de punteros
      FP 
      Para i:=1 hasta 39 Hacer 
         A[i]:=0 //vector de cantidades de areas
      FP

      Mientras NFDS(sec) Hacer
         id_str:=''
         Para i:=1 Hasta 5 Hacer 
            id_str:=CONCATENAR(id_str,v)
            AVZ(sec,v)
         FP 
         //estoy parado en el caracter de titulo
         titulo_str:=''
         Mientras v<>'&' Hacer 
            titulo_str:=CONCATENAR(titulo_str,v)
            AVZ(sec,v)
         FM 
         AVZ(sec,v) //parado en el primer digito de area 
         //hago este ajuste porque el vector empieza en 1 y no en 01 02 etc
         Si v='0' Entonces
            AVZ(sec,v)
            //voy al segundo digito
         FS
         aux_area:=Conv_Area(v)*10
         AVZ(sec,v)
         aux_area:=aux_area+Conv_Area(v)

         AVZ(sec,v) //estoy en el digito tipo
         aux_tipo:=ConvertirTipo(v)

         AVZ(sec,v) //estoy en estado
         Si v='V' Entonces
            //cargo todo en la lista
            NUEVO(q)
            *q.id:=id_str
            *q.titulo:=tipo_str
            *q.num_area:=aux_area
            //sumo 1 para el vector area
            A[aux_area]:=A[aux_area]+1
            //CARGA ORDENADA
            InsertarOrdenado(prim[aux_tipo],q)
         FS 
         //si no esta vigente, no hago nada y salteo al siguiente proyecto
         AVZ(sec,v)
      FM 
      //proceso estadistico
      mayor:=LV
      Para i:=1 hasta 39 Hacer
         Si A[i]>mayor Entonces
            mayor:=A[i]
            area_nombre:=N[i]
         FS 
         Si A[i]=0 Entonces   
            Escribir('Area sin proyectos: ',N[i])
         FS 
      FP 
      Escribir('El area: ',area_nombre,' tiene mayor aplicacion de proyectos con un total de: ',mayor)

      Para i:=1 hasta 3 Hacer 
         p:=prim[i]
         Escribir('ESTAS VIENDO EL TIPO DE PROYECTO: ',i-1)
         Mientras p<>NIL Hacer
            Escribir('ID DE PROYECTO: ',*p.id)
            Escribir('TITULO: ',*p.titulo)
            Escribir('AREA: ',*p.num_area)
            p:=*p.prox 
         FM 
      FP 
      Cerrar(sec)
FINACCION

Accion ej2 Es 
   Ambiente
      x,y,i,j:ENTERO 
      Funcion SumDivPropios(n:entero):ENTERO Es 
         suma,i:ENTERO 
         suma:=0
         Para i:=1 Hasta (n DIV 2) Hacer 
            Si (n MOD i = 0) Entonces 
               suma:=suma+i 
            FS 
         FP 
         SumDivPropios:=suma 
      FF 
      /*
      RECURSIVO
      Funcion SumDivPropios(n,i:entero):ENTERO Es 
         Si i>(n DIV 2) Entonces
            SumDivPropios:=0
         Sino 
            Si (n MOD i = 0) Entonces 
               SumDivPropios:=i+SumDivPropios(n,i+1)
            Sino 
               SumDivPropios:=SumDivPropios(n,i+1)
            FS 
         FS
      FF
      */ 
      suma_i,suma_j:ENTERO
   Proceso
      Escribir('Ingrese un rango');Leer(x,y)

      Para i:=x Hasta y Hacer
         suma_i:=SumDivPropios(i)
         Para j:=i+1 Hasta y Hacer
            suma_j:=SumDivPropios(j)
            Si (suma_i=(j+1)) Y (suma_j=(i+1)) Entonces
               Escribir(i,' y ',j,' son prometidos')
            FS 
         FP 
      FP 
FINACCION

Accion ej3(A:ARREGLO[1..30] DE AN(30)) Es 
   Ambiente 
      formato_fecha=Registro 
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      equipos=Registro
         departamento:AN(30)
         id_equipo:N(6)
         tipo_equipo:AN(20)
         responsable:AN(30)
         fecha_adquisicion:formato_fecha
         fecha_ult_mant:formato_fecha
         estado:('FUNCIONANDO','CON FALLAS')
         cod_problema:N(2)
      FR 
      arch_ind:archivo indexado por departamento y id_equipo
      reg_ind:equipos

      nombre_dpto,descripcion_problema:AN
      equip_id:N(6)
      cod_prob_usu:N(2)
      opc:ENTERO 
   Proceso 
      ABRIR E/S(arch_ind)
      opc:=0
      Mientras opc<>1 Hacer
         Escribir('Ingrese el nombre del departamento y el id del equipo que desea verificar: ');Leer(nombre_dpto,equip_id) 

         reg_ind.departamento:=nombre_dpto
         reg_ind.id_equipo:=equip_id
         Leer(arch_ind,reg_ind)

         Si EXISTE Entonces
            //DETALLANDO LAS FALLAS
            Si reg_ind.estado='CON FALLAS' Entonces
               descripcion_problema:=A[reg_ind.cod_problema]
            Sino 
               descripcion_problema:='NINGUNO'
            FS 
            
            //FICHA IMPRESA
            Escribir('FECHA PROCESO: 11/2/26')
            Escribir('DEPTO: ',nombre_dpto)
            Escribir('ID EQUIPO: ',equip_id)
            Escribir('ESTADO: ',reg_ind.estado)
            Escribir('PROBLEMA ',descripcion_problema)

            //ACCIONES CORRECTIVAS
            Si reg_ind.estado='CON FALLAS' Entonces
               Escribir('INGRESE CODIGO PROBLEMA');Leer(cod_prob_usu)
               descripcion_problema:=A[cod_prob_usu]

               reg_ind.cod_problema:=cod_prob_usu
               reg_ind.fecha_ult_mant.dd:=11
               reg_ind.fecha_ult_mant.mm:=2
               reg_ind.fecha_ult_mant.aaaa:=2026
               Leer(arch_ind,reg_ind)
               REESCRIBIR(arch_ind,reg_ind)
            FS
         Sino 
            Escribir('ERROR')
         FS 
         Escribir('PARA SALIR PULSE 1 - PARA CONTINUAR CUALQUIER OTRO BOTON: ');Leer(opc)
      FM 
      CERRAR(arch_ind)
FINACCION
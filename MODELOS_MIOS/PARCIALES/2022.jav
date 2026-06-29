//parcial 2022 
Accion parcial2022(prim_c: NODO A RULETA) Es 
   Ambiente
      formato_fecha=Registro
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      compras=Registro 
         fecha_compra:formato_fecha
         DNI_cliente:N(8)
         Cantidad_art:N(3)
         Importe_total:REAL 
      FR 
      arch: archivo secuencial de compras ordenado por fecha_compra
      reg:compras 

      socios=Registro 
         DNI_cliente:(8)
         apellido_y_nombre:AN(255)
         fecha_adhesion:formato_fecha
         categoria:('Gold','Black', 'Silver')
      FR 
      arch_ind: archivo de socios indexado por DNI_cliente
      reg_index: socios 

      //NODO DE LISTA DE SALIDA DOBLE
      NODO_D=Registro 
         apellido_y_nombre: AN(255)
         chances_tot:ENTERO
         prox,ant:PUNTERO A NODO_D
      FR 
      prim_d,ult_d,pd,qd: PUNTERO A NODO_D

      //lista circular que simula ruleta
      pc:PUNTERO A RULETA

      //valor para la funcion Tirar()
      valor:ENTERO 
      //chance extra
      compra:ENTERO

      Procedimiento Buscar_Crear_Insertar() Es 
         pd:=prim_d
         //El detalle: qd todavía no existe (no le hiciste Nuevo(qd)) al momento de buscar.
         // La solución: es comparar con el reg index
         Mientras (pd<>NIL) Y (*pd.apellido_y_nombre<>reg_index.apellido_y_nombre) Hacer 
            pd:=*pd.prox 
         FM
         //cargado en la lista doble
         Si (pd<>NIL) Entonces
            compra:=TRUNC(reg.Importe_total/100)
            //ruleta
            Si reg_index.categoria='Black' Entonces
               valor:=Tirar()
               Para i:=1 Hasta valor Hacer 
                  pc:=*pc.prox 
               FP
               *pd.chances_tot:=*pd.chances_tot + (compra + *pc.valor)
            Sino 
               *pd.chances_tot:=*pd.chances_tot+compra
            FS
         Sino 
            //cliente nuevo, no estaba en la lista dob
            Nuevo(qd)
            *qd.apellido_y_nombre:=reg_index.apellido_y_nombre
            *qd.chances_tot:=5
            compra:=TRUNC(reg.Importe_total/100)
            
            Si reg_index.categoria='Black' Entonces
               valor:=Tirar()
               Para i:=1 Hasta valor Hacer 
                  pc:=*pc.prox 
               FP
               *qd.chances_tot:=*qd.chances_tot + (compra + *pc.valor)
            Sino 
               *qd.chances_tot:=*qd.chances_tot+compra
            FS
            CargaSociosOrdenada()
         FS
      FP

      Procedimiento CargaSociosOrdenada() Es 
         //vacia
         Si prim_d=NIL Entonces
            prim_d:=qd 
            ult_d:=qd 
            *qd.prox:=NIL 
            *qd.ant:=NIL 
         Sino 
            //buscar lugar 
            pd:=prim_d
            Mientras (pd<>NIL) Y (*pd.apellido_y_nombre<*qd.apellido_y_nombre) Hacer 
               pd:=*pd.prox 
            FM 
            //Poner en el inicio
            Si pd=prim_d Entonces
               *qd.prox:=pd 
               *qd.ant:=NIL 
               *pd.ant:=qd 
               prim_d:=qd
            Sino 
               //final 
               Si pd=NIL Entonces
                  *ult_d.prox:=qd 
                  *qd.ant:=ult_d
                  *qd.prox:=NIL 
                  ult_d:=qd 
               Sino 
                  //medio 
                  *qd.prox:=pd 
                  *qd.ant:=*pd.ant 
                  *(*pd.ant).prox:=qd 
                  *pd.ant:=qd 
               FS 
            FS 
         FS 
      FP 
   Proceso
      //doble 
      prim_d:=NIL 
      ult_d:=NIL 
      //circular
      pc:=prim_c

      ABRIR E/(arch);Leer(arch,reg)
      ABRIR E/(arch_ind)

      Mientras NFDA(arch) Hacer 
         reg_index.DNI_cliente:=reg.DNI_cliente
         Leer(arch_ind,reg_index)
         Si EXISTE Entonces
            Buscar_Crear_Insertar()
         FS 
         Leer(arch,reg) 
      FM
      CERRAR(arch);CERRAR(arch_ind)
FIN_ACCIÓN

Accion EJ2(prim: PUNTERO A NODO) Es 
   Ambiente
      NODO=Registro
         dni:N(8)
         apynom:AN(255)
         edad:N(2)
         nro_cama:ENTERO 
         nro_hab:ENTERO 
         temp:Arreglo[1..4] DE REAL 
         prox:PUNTERO A NODO 
      FR 
      p,ant,aux:PUNTERO A NODO 

      Funcion CumpleTemperatura(V:ARREGLO[1..4] DE REAL,i:ENTERO):BOOLEANO Es 
         Si V[i]>36.5 Entonces 
            CumpleTemperatura:=VERDADERO 
         Sino 
            Si i=5 Entonces
               CumpleTemperatura:=FALSO 
            Sino 
               CumpleTemperatura:=CumpleTemperatura(V,i+1)
            FS 
         FS 
      FS 

      Procedimiento Eliminar() Es  
         Si p=prim Entonces   
            prim:=*p.prox 
            p:=prim
         Sino 
            *ant.prox:=*p.prox
            p:=*p.prox
         FS  
         Disponer(aux) 
      FP 
   Proceso 
      p:=prim 
      ant:=NIL 
      Mientras p<>NIL Hacer 
         Si NO CumpleTemperatura(*p.temp,1) Entonces 
            //AVANZAMOS AMBOS PUNTEROS. Porque la consigna dice que los pacientes que 'no' deben cumplir. Y en este si están los que cumplen, es decir, no nos interesan
            ant:=p
            p:=*p.prox
         Sino 
            Escribir('NOMBRE Y APELLIDO: ',*p.apynom)
            aux:=p
            Eliminar() 
         FS 
      FM 
FIN_ACCIÓN



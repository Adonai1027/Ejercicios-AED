// parcial 2025 tema a
Accion p2025A Es 
   Ambiente
      formato_fecha=Registro 
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      inscripciones=Registro 
         id_equipo:N(2)
         nombre_equipo: AN(255)
         fecha_insc:formato_fecha
         tec_usadas:ARREGLO[1..5] de ENTERO
      FR 
      arch:archivo de inscripciones ordenado por id_equipo
      reg:inscripciones

      puntajes=Registro 
         id_equipo:N(2)
         puntaje_por_criterio:ARREGLO[1..3] DE ENTERO 
         puntaje_lider:1..10 
      FR 
      arch_ind: archivo de puntajes indexado por id_equipo
      reg_index: puntajes 

      NODO_D=REGISTRO 
         nombre_equipo: AN(255)
         total: ENTERO
         puntaje_lider:1..10 
         prox,ant:PUNTERO A NODO_D
      FR 
      primd,ultd,pd,qd: PUNTERO A NODO_D
   
      //ej1
      criterio,tecnologia,puntaje_final:ENTERO
      //ej2
      equipo_tec,cantidad_eq:ENTERO
      //ej3
      ganador:ENTERO 

      Procedimiento Calculo() Es
         tecnologia:=0 //reseteo
         Para i:=1 Hasta 5 Hacer
            //1 si se uso la tecnologia 
            Si reg.tec_usadas[i]=1 Hacer 
               //para la lista doble
               tecnologia:=tecnologia+1
               //ej2
               equipo_tec:=equipo_tec+1
            FS
         FP
         criterio:=reg_index.puntaje_por_criterio[1]+reg_index.puntaje_por_criterio[2]+reg_index.puntaje_por_criterio[3]
      FP

      Procedimiento CrearNodo() Es 
         NUEVO(qd)
         *qd.nombre_equipo:=reg.nombre_equipo
         *qd.puntaje_lider:=reg_index.puntaje_lider
         Calculo()
         *qd.total:=criterio+tecnologia
      FP

      Procedimiento CargaAsc() Es
         //VACIA
         Si primd=NIL Entonces 
            primd:= qd
            ultd:= qd
            *qd.prox:=NIL 
            *qd.ant:=NIL 
         Sino 
            pd:=primd
            Mientras (pd<>NIL) Y ((*pd.total < *qd.total) O (*pd.total=*qd.total Y *pd.puntaje_lider<*qd.puntaje_lider)) Hacer
               pd:=*pd.prox
            FM 
            //INCIO
            Si pd = primd Entonces 
               *qd.prox:=pd
               *qd.ant:=NIL
               *pd.ant:=qd
               primd:=qd
            Sino 
               //FINAL
               Si pd=NIL Entonces 
                  *ultd.prox:=qd
                  *qd.ant:=ultd
                  *qd.prox:= NIL 
                  ultd:= qd 
               Sino 
                  //MEDIO
                  *qd.prox:=pd 
                  *qd.ant:=*pd.ant 
                  *(*pd.ant).prox:=qd  
                  *pd.ant:=qd 
               FS 
            FS 
         FS 
      FP
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)
      ABRIR E/(arch_ind)

      cantida_eq:=0
      equipo_tec:=0
      criterio:=0
      tecnologia:=0

      primd:=NIL
      ultd:=NIL

      Mientras NFDA(arch) Hacer 
         reg_index.id_equipo:=reg.id_equipo
         Leer(arch_ind,reg_index)
         //id equipo se inscribe una vez, entonces, no hay necesidad de BUSCAR
         Si EXISTE Entonces
            CrearNodo()
            CargaAsc()
            //ej2
            Si equipo_tec>=3 Entonces 
               cantida_eq:=cantida_eq+1
            FS 
            equipo_tec:=0 //reseteo
         FS 

         Leer(arch,reg)
      FM

      //ej2
      Escribir('La cantida de equipos que usaron 3 o mas tecnologias son: ',cantidad_eq)

      //ej3
      ganador:=1
      pd:=ultd
      Mientras pd<>NIL Y ganador<=3 Hacer 
         Escribir('Puesto: ',ganador,'| Nombre: ',*pd.nombre_equipo,'| Puntaje: ',*pd.total,'| Puntaje Lider: ',*pd.puntaje_lider)
         pd:=*pd.ant //marcha atras 
         ganador:=ganador+1
      FM 
      CERRAR(arch);CERRAR(arch_ind)
FINACCION

//recursividad
Accion recur2025(prim1:PUNTERO A NODO1) Es 
   Ambiente 
      NODO1=Registro 
         valor:ENTERO 
         prox:PUNTERO A NODO1 
      FR 
      p1,ant1,aux:PUNTERO A NODO1

      NODO2=Registro
         valor:ENTERO 
         prox:PUNTERO A NODO2 
      FR 
      p2,q2,prim2:PUNTERO A NODO2 

      Funcion Cumple(n:ENTERO):BOOLEANO Es 
         a,b,aux:ENTERO 
         //comparo con 10,porque antes del 10 están los numeros del 0 al 9 y son de un solo digito
         Si n<10 Entonces
            Cumple:=VERDADERO
         Sino 
            a:= n MOD 10 
            aux:=n DIV 10 
            b:= aux MOD 10
            Si a<=b Entonces
               Cumple:=FALSO 
            Sino 
               Cumple:=Cumple(n DIV 10)
            FS 
         FS
      FF

      Procedimiento CargarSimple() Es 
         Si prim2=NIL Entonces 
            prim2:=q2
            p2:=prim2 
            *q2.prox:=NIL  
         Sino 
            *p2.prox:=q2 
            *q2.prox:=NIL 
            p2:=q2 
         FS 
      FP

      Procedimiento Eliminar() Es
         aux:=p1 
         Si p1=prim1 Entonces 
            prim1:=*p1.prox 
         Sino 
            *ant1.prox:=*p1.prox 
         FS 
         p1:=*p1.prox
         Disponer(aux)
      FP 

   Proceso 
      p1:=prim1 
      ant1:=NIL 

      prim2:=NIL 

      Mientras (p1<>NIL) Hacer 
         Si NO Cumple(*p1.valor) Entonces
            NUEVO(q2)
            *q2.valor:=*p1.valor
            CargarSimple()
            Eliminar() 
         Sino 
            ant1:=p1 
            p1:=*p1.prox 
         FS 
      FM
      //pide generar la lista simple de salida con los numeros que se eliminaron de la primer lista, no pide orden, no pide mostrar, termina aca el ejercicio
FIN_ACCIÓN
            




Accion ej1(N:ARREGLO[1..10] DE AN) Es 
   Ambiente 
      formato_fecha=Registro
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      eventos=Registro 
         codigo:N(4)
         titulo:AN(30)
         costo:N(4,2)
         cod_cat:1..10
         segmento:0..2
         fecha_ev:formato_fecha
      FR 
      arch:archivo secuencial de eventos 
      reg:eventos

      NODO=Registro
         fecha:formato_fecha
         costos_ev:N(4,2)
         nombre_ev:AN(30)
         seg_ev:AN(10)
         prox:PUNTERO A NODO 
      FR
      p,q,prim,ant:PUNTERO A NODO 

      Funcion ConvSeg(n:ENTERO):AN Es 
         Segun n Hacer 
            0: ConvSeg:='MATINAL'
            1: ConvSeg:='TARDE'
            2: ConvSeg:='NOCHE'
         FS 
      FF 

      Procedimiento CargaOrdenada() Es 
         Si prim=NIL Entonces 
            prim:=q 
            *q.prox:=NIL 
         Sino 
            p:=prim 
            ant:=NIL 
            Mientras p<>NIL Y *p.costos_ev>*q.costos_ev Hacer 
               ant:=p 
               p:=*p.prox 
            FM 
            Si p=prim Entonces
               prim:=q 
               *q.prox:=p 
            Sino 
               *ant.prox:=q
               *q.prox:=p 
            FS 
         FS 
      FP

      matriz=Registro 
         cant:ENTERO 
         costos_acum:N(4,2)
      FR
      i,j:ENTERO 
      
      M:ARREGLO[1..11,1..4] DE matriz

      cantidad_A,cant_usu,menor,mayor,resg_cat:ENTERO
      total_inv:N(4,2)
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)

      Para i:=1 Hasta 11 Hacer 
         Para j:=1 Hasta 4 Hacer 
            M[i,j].cant:=0
            M[i,j].costos_acum:=0
         FP 
      FP 

      prim:=NIL 
      cantidad_A:=0

      Escribir('Ingrese el costo: ');Leer(cant_usu)
      Mientras NFDA(arch) Hacer 
         i:=reg.cod_cat
         j:=reg.segmento+1 //0->1 1->2 2->3 
         //procesando en otra estructura
         Si cant_usu<reg.costo Entonces
            NUEVO(q)
            *q.fecha:=reg.fecha_ev
            *q.costos_ev:=reg.costo
            *q.nombre_ev:=N[reg.cod_cat]
            *q.seg_ev:=ConvSeg(reg.segmento)
            CargaOrdenada()
         FS 

         M[i,j].cant:=M[i,j].cant+1
         M[i,j].costos_acum:=M[i,j].costos_acum+reg.costo

         M[11,j].cant:=M[11,j].cant+1
         M[11,j].costos_acum:=M[11,j].costos_acum+reg.costo

         M[i,4].cant:=M[i,4].cant+1
         M[i,4].costos_acum:=M[i,4].costos_acum+reg.costo

         Si reg.segmento=0 Y N[reg.cod_cat]='Deportivo' Entonces
            cantidad_A:=cantidad_A+1
         FS 

         Leer(arch,reg)
      FM 

      //PUNTO B Y C
      menor:=HV
      mayor:=LV
      Para i:=1 Hasta 10 Hacer 
         Si M[i,4].cant<menor Entonces
            menor:=M[i,4].cant
            total_inv:=M[i,4].costos_acum
         FS 
         Si M[i,3].cant>mayor Entonces
            mayor:=M[i,3].cant
            resg_cat:i 
         FS 
      FP 

      Escribir('Cantidad de eventos en el segmento matinal de la categoria deportivo: ',cantidad_A)

      Escribir('Total invertido: ',total_inv)

      Escribir('Porcentaje que representa la categoria: ',N[resg_cat],' %',((mayor*100)/M[11,3]))
      
      Cerrar(arch)
FINACCION

//aeiou+hola111h+aeiou222a+aa@cc/#9a+...+
Accion ej2 Es 
   Ambiente 
      sec:SECUENCIA DE CARACTER 
      v:CARACTER

      NODO_CARACTER=Registro
         dato:CARACTER 
         prox:PUNTERO A NODO_CARACTER
      FR 
      prim_temp,p_temp,q_char,aux,p_char:PUNTERO A NODO_CARACTER

      NODO_CONT=Registro
         lista_hija:PUNTERO A NODO_CARACTER
         longitud:ENTERO 
         prox:PUNTERO A NODO_CONT
      FR 
      pc,qc,primc,antc:PUNTERO A NODO_CONT

      //contadores de niveles
      A:ARREGLO[1..4] de Entero 
      
      i,menor,resg_nivel,contador_longitud,cont_rep,cont_tot,:ENTERO 
      vocal:('A','E','I','O','U','a','e','i','o','u')
      numeros:('0','1','2','3','4','5','6','7','8','9')
      simbolos:('/','#','$','@')
      cont_voc,cont_cons,cont_num,cont_simb:ENTERO
      //para repeticion
      tiene_rep:BOOLEANO 
      cont_seguidos:ENTERO 
      char_ant:CARACTER 

      //Como no podés usar un simple < entre dos palabras (porque no son strings, son cadenas de nodos), tenés que comparar eslabón por eslabón.
      Funcion EsMenor(p1,p2:PUNTERO A NODO_CARACTER):BOOLEANO Es 
         iguales:BOOLEANO 
         iguales:=VERDADERO

         Mientras p1<>NIL Y p2<>NIL Y iguales Hacer 
            Si *p1.dato = *p2.dato Entonces
               p1:=*p1.prox 
               p2:=*p2.prox 
            Sino 
               iguales:=FALSO 
            FS 
         FM 

         Si p1<>NIL Y p2<>NIL Entonces
            EsMenor:=(*p1.dato<*p2.dato)
         Sino 
            Si p1=NIL Y p2<>NIL Entonces
               EsMenor:=VERDADERO
            Sino 
               EsMenor:=FALSO 
            FS 
         FS 
      FF

      Procedimiento Extraer() Es 
         NUEVO(q_char)
         *q_char.dato:=v
         Si prim_temp=NIL Entonces
            prim_temp:=q_char
            *q_char.prox:=NIL
            p_temp:=q_char
         Sino
            *p_temp.prox:=q_char
            *q_char.prox:=NIL
            p_temp:=q_char
         FS 
      FP 

      Procedimiento CargaOrdenada() Es 
         Si primc=NIL Entonces
            primc:=qc 
            *qc.prox:=NIL 
         Sino 
            pc:=primc 
            antc:=NIL 
            Mientras pc<>NIL Y EsMenor(*pc.lista_hija,*qc.lista_hija) Hacer 
               antc:=pc 
               pc:=*pc.prox 
            FM 
            Si pc=primc Entonces
               primc:=qc 
               *qc.prox:=pc 
            Sino 
               *antc.prox:=qc 
               *qc.prox:=pc 
            FS 
         FS 
      FP
      
      Funcion queEs(c:CARACTER):ENTERO Es  
         Si c EN vocal Entonces 
            queEs:=1
         Sino 
            Si c EN numeros Entonces
               queEs:=3
            Sino 
               Si c EN simbolos Entonces
                  queEs:=4
               Sino 
                  queEs:=2 //consonante
               FS 
            FS 
         FS 
      FF 

      Procedimiento Composicion() Es 
         tipo:=queEs(*p_char.dato)
         Segun tipo Hacer
            1: cont_voc:=cont_voc+1
            2: cont_cons:=cont_cons+1
            3: cont_num:=cont_num+1
            4: cont_simb:=cont_simb+1
         FS 
      FP 

      Procedimiento Repeticion() Es 
         Si (*p_char.dato = char_ant) Entonces
            cont_seguidos:=cont_seguidos+1
            Si cont_seguidos>3 Entonces
               tiene_rep:=VERDADERO 
            FS 
         Sino 
            char_ant:=*p_char.dato 
            cont_seguidos:=1 
         FS 
      FP 
   Proceso 
      ARR(sec);AVZ(sec,v)

      Para i:=1 Hasta 4 Hacer 
         A[i]:=0
      FP 
      
      cont_rep:=0
      cont_tot:=0

      Mientras NFDS(sec) Hacer 
         prim_temp:=NIL
         contador_longitud:=0
         
         Mientras v<>'+' Hacer 
            Extraer()
            contador_longitud:=contador_longitud+1
            AVZ(sec,v)
         FM 
         AVZ(sec,v)

         Si *p_temp.dato = *prim_temp.dato Entonces
            //coinciden el inicio y el final
            NUEVO(qc)
            *qc.lista_hija:=prim_temp
            *qc.longitud:=contador_longitud
            //carga ordenada alfabeticamente
            CargaOrdenada()
            cont_tot:=cont_tot+1
         Sino 
            p_temp:=prim_temp
            Mientras p_temp<>NIL Hacer 
               aux:=p_temp
               p_temp:=*p_temp.prox 
               Disponer(aux)
            FM 
         FS 
      FM 

      //recorro las contraseñas
      pc:=primc 
      Mientras pc<>NIL Hacer
         //reseteo
         cont_voc:=0;cont_cons:=0;cont_num:=0;cont_simb:=0;
         //deteccion de repeticion
         tiene_rep:=FALSO 
         cont_seguidos:=1
         char_ant:='/' //es imposible que sea el primer caracter este porque en el filtro nos aseguramos que sean caracteres iguales tanto al inicio como al final

         //recorro la lista de caracteres
         p_char:=*pc.lista_hija
         Escribir('CONTRASEÑA: ')
         Mientras p_char<>NIL Hacer
            //clasifico los caracteres
            Composicion() 
            //muestro las contraseñas
            Escribir(*p_char.dato)
            //para el porcentaje de repeticion
            Repeticion()
            //avanzo siguiente caracter
            p_char:=*p_char.prox 
         FM 
         //salto de linea para mostrar bien las contraseñas
         Escribir(' ') 
         //si se repite, sumo uno para el porcentaje
         Si tiene_rep Entonces 
            cont_rep:=cont_rep+1
         FS 

         //nivel 1
         //sin numeros y solo vocales O consonantes || con numeros y sin vocales y consonantes
         Si (cont_num=0 Y (cont_voc = 0 O cont_cons = 0)) O (cont_num > 0 Y cont_voc = 0 Y cont_cons = 0) Y (*pc.longitud<6) Entonces   
            A[1]:=A[1]+1
         Sino
            //NIVEL 2 
            Si (*pc.longitud>=6 Y *pc.longitud<=8) Y cont_simb=0 Entonces
               A[2]:=A[2]+1
            Sino 
               //NIVEL 3
               Si (*pc.longitud>=8 Y *pc.longitud<=10) Y (cont_voc>cont_cons) Y cont_simb=0 Entonces
                  A[3]:=A[3]+1
               Sino 
                  //NIVEL 4
                  A[4]:=A[4]+1
               FS 
            FS 
         FS
         pc:=*pc.prox 
      FM 

      menor:=HV 
      Para i:=1 Hasta 4 Hacer 
         Si A[i]<menor Entonces
            menor:=A[i]
            resg_nivel:=i 
         FS 
      FP 

      Escribir('El nivel con menor cantidad de contraseñas es: ',resg_nivel)

      Si cont_tot>0 Entonces
         Escribir('Porcentaje: %',((cont_rep*100)/cont_tot))
      FS 

      CERRAR(sec)
FINACCION



Accion ej1 Es 
   Ambiente
      sec:SECUENCIA DE CARACTERES
      v:CARACTER 

      NODO=Registro 
         nro_historia:AN(4)
         nombre:AN(255)
         especialidad:10..29
         cobertura:0..9
         prox:PUNTERO A NODO 
      FR 
      p,q,ant:PUNTERO A NODO 
      P:ARREGLO[1..3] DE PUNTERO A NODO
      aux_dia:ENTERO
      //una fila y col extra para los totales
      A:ARREGLO[10..30,0..10] DE ENTERO 

      opc:CARACTER
      i,j:ENTERO 
      opc_usu_historia,opc_dia:AN 

      //para la matriz
      cantidad_menor,menos_cob:ENTERO 
      cantidad_mayor,mas_esp:ENTERO

      esp_aux:AN

      Funcion ConvertirDia(c:CARACTER):ENTERO Es 
         Segun c Hacer 
            'L':ConvertirDia:=1
            'M':ConvertirDia:=2
            'V':ConvertirDia:=3
         FS 
      FF
      Funcion ConvertirNumero(palabra:AN):ENTERO Es 
         Segun palabra Hacer 
            '0':ConvertirNumero:=0
            '1':ConvertirNumero:=1
            '2':ConvertirNumero:=2
            '3':ConvertirNumero:=3
            '4':ConvertirNumero:=4
            '5':ConvertirNumero:=5
            '6':ConvertirNumero:=6
            '7':ConvertirNumero:=7
            '8':ConvertirNumero:=8
            '9':ConvertirNumero:=9
            '10':ConvertirNumero:=10
            '11':ConvertirNumero:=11
            '12':ConvertirNumero:=12
            '13':ConvertirNumero:=13
            '14':ConvertirNumero:=14
            '15':ConvertirNumero:=15
            '16':ConvertirNumero:=16
            '17':ConvertirNumero:=17
            '18':ConvertirNumero:=18
            '19':ConvertirNumero:=19
            '20':ConvertirNumero:=20
            '21':ConvertirNumero:=21
            '22':ConvertirNumero:=22
            '23':ConvertirNumero:=23
            '24':ConvertirNumero:=24
            '25':ConvertirNumero:=25
            '26':ConvertirNumero:=26
            '27':ConvertirNumero:=27
            '28':ConvertirNumero:=28
            '29':ConvertirNumero:=29
         FS 
      FF

      Procedimiento CargaOrdenadaSimple(prim:PUNTERO A NODO, nuevoNodo:PUNTERO A NODO) Es 
         // Caso 1: La lista está vacía
         Si prim = NIL Entonces
            prim := nuevoNodo
            *nuevoNodo.prox := NIL
         Sino
            // Busco el lugar para la inserción
            p := prim
            ant := NIL
            Mientras (p <> NIL) y (*p.nro_historia < *nuevoNodo.nro_historia) Hacer
               ant := p
               p := *p.prox
            FM
            // Caso 2: El nuevo es el más chico (p quedó en prim)
            Si p = prim Entonces
               prim := nuevoNodo
            Sino 
               // Casos 3 y 4: Medio o Final
               *ant.prox := nuevoNodo
            FS
            // 5. El enganche final
            *nuevoNodo.prox := p
         FS
      FP

      Procedimiento Opcion_A_Cargar() Es 
         Mientras NFDS(sec) Hacer
            NUEVO(q)
            *q.nro_historia:=''
            Para i:=1 Hasta 4 Hacer
               *q.nro_historia:=CONCATENAR(*q.nro_historia,v)
               AVZ(sec,v)
            FP 
            //estoy parado en el primer caracter de nombre
            *q.nombre:=''
            Mientras v<>'-' Hacer
               *q.nombre:=CONCATENAR(*q.nombre,v)
               AVZ(sec,v)
            FM 
            AVZ(sec,v) //estoy en el primer digito de especialidad
            esp_aux:=''
            Para i:=1 Hasta 2 Hacer 
               esp_aux:=CONCATENAR(esp_aux,v)
               AVZ(sec,v)
            FP 
            //estoy en el digito de cobertura
            *q.especialidad:=ConvertirNumero(esp_aux)
            *q.cobertura:=ConvertirNumero(v)

            A[*q.especialidad,*q.cobertura]:=A[*q.especialidad,*q.cobertura]+1
            //cargo total por cobertura
            A[30,*q.cobertura]:=A[30,*q.cobertura]+1
            //cargo total por especialidad
            A[*q.especialidad,10]:=A[*q.especialidad,10]+1

            AVZ(sec,v) //me paro en el dia
            aux_dia:=ConvertirDia(v)
            CargaOrdenadaSimple(P[aux_dia],q) 
            AVZ(sec,v) //me paro en el primer digito de historia clinica o FDS
         FM
      FP 

      Procedimiento Opcion_B_Buscar() Es 
         Escribir('Ingrese el Numero de historia clinica del paciente que desea buscar: ');Leer(opc_usu_historia)
         Escribir('Ingrese el dia (L,M,V)');Leer(opc_dia)

         Escribir('MIRANDO DIA: ',opc_dia )
         aux_dia:=ConvertirDia(opc_dia)
         p:=P[aux_dia]
         //algoritmo busqueda lista simple 
         ant:=NIL 
         Mientras (p<>NIL) Y (*p.historia<opc_usu_historia) Hacer 
            ant:=p 
            p:=*p.prox 
         FM 

         Si opc_usu_historia=*p.nro_historia Entonces
            Escribir('Nombre: ',*p.nombre)
            Escribir('Especialidad: ',*p.especialidad)
         Sino 
            Escribir('NO EXISTE')
         FS 
      FP

      Procedimiento Opcion_C_Informar() Es
         cantidad_mayor:=LV
         Para i:=10 Hasta 30 Hacer
            Escribir('Para la especialidad: ',i,' el total es de: ',A[i,10])

            //ITEM 3
            Si A[i,10]>cantidad_mayor Entonces
               cantidad_mayor:=A[i,10]
               mas_esp:=i 
            FS 
         FP 

         cantidad_menor:=HV
         Para j:=0 Hasta 10 Hacer
            Escribir('Para la cobertura: ',j, 'el total es de: ',A[30,j])

            //item 2
            Si A[30,j]<cantidad_menor Entonces
               cantidad_menor:=A[30,j]
               menos_cob:=i 
            FS 
         FP 
      FP 
   Proceso 
      ARR(sec);AVZ(sec,v)

      primLunes:=NIL
      primMartes:=NIL
      primViernes:=NIL
      
      Para i:=1 Hasta 30 Hacer
         Para j:= Hasta 10 Hacer
            A[i,j]:=0
         FP 
      FP 

      Escribir('BIENVENIDO AL MENU DE | CARGA | BUSQUEDA | INFORME |')
      Escribir('LAS OPCIONES SON A|B|C');Leer(opc)

      Mientras opc<>'S' Hacer 
         Segun opc Hacer 
            'A': Opcion_A_Cargar()
            'B': Opcion_B_Buscar()
            'C': Opcion_C_Informar()
         FS 
         Escribir('Para salir, escriba S. Para continuar, seleccione A|B|C')
      FM 
      Cerrar(sec) 
FINACCION

Accion ej2 Es 
   Ambiente
      participantes=Registro  
         pais:AN(255)
         equipo:AN(255)
         atlteta:AN(255)
         disciplina:('100M','200M','4X100','4X400')
         tiempo_seg:N(4)
         descalificado:('SI','NO')
      FR 
      arch: archivo secuencial de participantes ordenado por pais y equipo 
      reg: participantes

      tot_gral,tot_equipo,tot_pais,contador_des,tiempo_acum:ENTERO
      resg_equipo,resg_pais:AN

      salida=Registro
         pais:AN(255)
         tiempo_total:N(4)
      FR 
      arch_sal:archivo secuencial de salida 
      reg_sal:salida 

      Procedimiento CorteEquipo() Es 
         Escribir('Equipo:',resg_equipo)
         Escribir('Total tiempo:',tot_equipo)
         //item2
         Escribir('Cantidad descalificados:',contador_des)
         //item3
         Si (tiempo_acum>0) Y (tiempo_acum<160) Entonces
            reg_sal.pais:=resg_pais
            reg_sal.tiempo_total:=tiempo_acum 
            Grabar(arch_sal,reg_sal)
         FS
         tot_pais:=tot_pais+tot_equipo
         tot_equipo:=0
         contador_des:=0 //item2
         tiempo_acum:=0 //item3
         resg_equipo:=reg.equipo
      FP 
      Procedimiento CortePais() Es 
         CorteEquipo()
         Escribir('Pais:',resg_pais)
         Escribir('Total tiempo:',tot_pais)
         tot_gral:=tot_gral+tot_pais
         tot_pais:=0
         resg_pais:=reg.pais 
      FP 
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)
      ABRIR /S(arch_sal)

      tot_gral:=0
      tot_equipo:=0
      tot_pais:=0
      contador_des:=0
      tiempo_acum:=0
      resg_equipo:=''
      resg_pais:=''

      resg_equipo:=reg.equipo
      resg_pais:=reg.pais

      Mientras NFDA(arch) Hacer
         Si resg_pais<>reg.pais Entonces
            CortePais()
         Sino 
            Si resg_equipo<>reg.equipo Entonces
               CorteEquipo()
            FS 
         FS 

         //no descalificados
         Si reg.descalificado='NO' Entonces
            tot_equipo:=tot_equipo+reg.tiempo_seg
            Si (reg.disciplina='4X100' O reg.disciplina='4X400') Entonces
               tiempo_acum:=tiempo_acum+reg.tiempo_seg
            FS 
         Sino  
            //item 2
            contador_des:=contador_des+1
         FS 
         Leer(arch,reg)
      FM 
      CortePais()
      Escribir('Total general:',tot_gral)
      Cerrar(arch);Cerrar(arch_sal)
FINACCION

Accion ej3 Es 
   Ambiente
      a,b,cant_total,i:ENTERO 
      
      Funcion SumaDivPropios(n:ENTERO):ENTERO Es 
         suma,k:ENTERO 
         suma:=0
         //aca excluyo al propio numero, ya que ningún divisor propio será mayor a la mitad del número, excepto el mismo número
         Para k:=1 Hasta (n DIV 2) Hacer
            //Un divisor propio de un número n es cualquier número entero positivo que divide a 'n' sin dejar resto
            //Si n mod k = 0, entonces k es divisor.
            //veo si es divisor
            Si (n MOD k = 0) Entonces
               //es divisor entonces sumo sus divisores propios
               suma:=suma+k
            FS 
         FP 
         SumaDivPropios:=suma 
      FF 
   Proceso
      cant_total:=0
      Escribir('Ingrese el inicio y el tope');Leer(a,b)

      Mientras (a<=0 O b<=0) O (a>=b) Hacer 
         Escribir('ERROR. REINGRESE')
         Leer(a,b)
      FM 

      Para i:=a Hasta b Hacer 
         Si (SumaDivPropios(i)=(i-1)) Entonces
            Escribir(i,' ES CASI PERFECTO')
            cant_total:=cant_total+1
         FS  
      FP

      Escribir('Cantidad total de numeros casi perfectos:',cant_total)
FINACCION

//solución con CONCATENACION
Accion ej1 Es 
Ambiente
    sec,sec1,sec2:SECUENCIA DE CARACTER 
    v:CARACTER 

    Funcion Concatenar(c:CARACTER,ventana:CARACTER):AN Es
        c:=c+v 
        Concatenar:=c 
    FF 

    Funcion Convertir_Tipo(c:CARACTER):ENTERO Es 
        Segun c Hacer 
        'A': Convertir_Tipo:=1
        'B': Convertir_Tipo:=2
        'C': Convertir_Tipo:=3
        'D': Convertir_Tipo:=4
        FS 
    FF 

    Funcion Conv_Num(c:CARACTER):ENTERO Es 
        Segun c Hacer
        '1': Conv_Num:=1
        '2': Conv_Num:=2
        '3': Conv_Num:=3
        '4': Conv_Num:=4
        '5': Conv_Num:=5
        '6': Conv_Num:=6
        '7': Conv_Num:=7
        '8': Conv_Num:=8
        '9': Conv_Num:=9
        FS 
    FF

    Funcion Reconvertir_Tipo(n:ENTERO):CARACTER Es 
        1: Reconvertir_Tipo:='A'
        2: Reconvertir_Tipo:='B'
        3: Reconvertir_Tipo:='C'
        4: Reconvertir_Tipo:='D'
    FF

    tipo_veh:ARREGLO[1..4] DE ENTERO 

    cantz1,cant_tot_z:ENTERO 
    cantS,cant_tot_I:ENTERO 
    i:ENTERO
    patente_str,zona_str,inf_aux,tipo_str,tiempo_str:AN
    tiempo_circ:ENTERO
    indx_tipo:ENTERO
    mayor:ENTERO
Proceso 
    ARR(sec);AVZ(sec,v)
    CREAR(sec1);CREAR(sec2)

    cant_tot_z:=0
    cant_tot_I:=0
    cantS:=0
    cantz1:=0

    Para i:=1 Hasta 4 Hacer 
        tipo_veh[i]:=0
    FP 

    Mientras v<>'*' Hacer 
        Mientras v<>'+' Hacer 
        patente_str:=''
        Mientras v<>'-' Hacer
            patente_str:=Concatenar(patente_str,v)
            AVZ(sec,v)
        FM 
        AVZ(sec,v) //salteo el -

        tipo_str:=''
        tipo_str:=Concatenar(tipo_str,v)

        //proc est
        indx_tipo:=Convertir_Tipo(v)
        tipo_veh[indx_tipo]:=tipo_veh[indx_tipo]+1

        AVZ(sec,v) //tiempo circ

        tiempo_str:=''
        tiempo_str:=Concatenar(tiempo_str,v)
        
        tiempo_circ:=Conv_Num(v)

        AVZ(sec,v) //estoy en infraccion
        Si v='S' Entonces 
            cantS:=cantS+1
        FS 
        cant_tot_I:=cant_tot_I+1

        inf_aux:=v 

        AVZ(sec,v) //zona

        zona_str:=''
        Para i:=1 Hasta 2 Hacer 
            zona_str:=Concatenar(zona_str,v)
            AVZ(sec,v)
        FP 

        Si zona_str='Z1' Entonces
            cantz1:=cantz1+1
        FS 
        cant_tot_z:=cant_tot_z+1
        FM 
        //dos secuencias 
        Si zona_str='Z1' Entonces
        GRABAR(sec1,patente_str)
        GRABAR(sec1,'-')
        GRABAR(sec1,tipo_str)
        GRABAR(sec1,tiempo_str)
        GRABAR(sec1,inf_aux)
        GRABAR(sec1,zona_str)
        GRABAR(sec1,'+')
        FS 
        Si inf_aux='S' Y tiempo_circ>2 Entonces
        GRABAR(sec2,patente_str)
        GRABAR(sec2,'-')
        GRABAR(sec2,tipo_str)
        GRABAR(sec2,tiempo_str)
        GRABAR(sec2,inf_aux)
        GRABAR(sec2,zona_str)
        GRABAR(sec2,'+')
        FS 
        AVZ(sec,v)
    FM 
    GRABAR(sec1,'*');GRABAR(sec2,'*')

    Escribir('Cantidad vehiculos que circularon en zona z1: ',cantz1,' Porcentaje: ',((cantz1*100)/cant_tot_z))
    Escribir('Cantidad vehiculos que cometieron infraccion : ',cantS,' Porcentaje: ',((cantS*100)/cant_tot_I))

    mayor:=LV
    Para i:=1 Hasta 4 Hacer 
        Si tipo_veh[i]>mayor Entonces
        mayor:=tipo_veh[i]
        tipo_str:=Reconvertir_Tipo(i)
        FS 
    FP 
    Escribir('El tipo de vehiculo que mas circulo en la ciudad es: ',tipo_str)
    
    Cerrar(sec);Cerrar(sec1);Cerrar(sec2)
FINACCION

//SOLUCIÓN CON ARREGLOS
Accion arreglos Es 
Ambiente
    sec,sec1,sec2:SECUENCIA DE CARACTER 
    v:CARACTER 

    A:ARREGLO[1..20] DE CARACTER
    i,largo:ENTERO
    cantz1,cant_tot_z:ENTERO 
    cantS,cant_tot_I:ENTERO 
    patente_str,zona_str,inf_aux,tipo_str,tiempo_str:AN
    fin_patente,zona_num:ENTERO
    tiempo_circ:ENTERO
    indx_tipo:ENTERO
    mayor:ENTERO

    Funcion Convertir_Tipo(c:CARACTER):ENTERO Es 
        Segun c Hacer 
        'A': Convertir_Tipo:=1
        'B': Convertir_Tipo:=2
        'C': Convertir_Tipo:=3
        'D': Convertir_Tipo:=4
        FS 
    FF 
    Funcion Reconvertir_Tipo(n:ENTERO):CARACTER Es 
        1: Reconvertir_Tipo:='A'
        2: Reconvertir_Tipo:='B'
        3: Reconvertir_Tipo:='C'
        4: Reconvertir_Tipo:='D'
    FF

    Funcion Conv_Num(c:CARACTER):ENTERO Es 
        Segun c Hacer
        '1': Conv_Num:=1
        '2': Conv_Num:=2
        '3': Conv_Num:=3
        '4': Conv_Num:=4
        '5': Conv_Num:=5
        '6': Conv_Num:=6
        '7': Conv_Num:=7
        '8': Conv_Num:=8
        '9': Conv_Num:=9
        FS 
    FF

    tipo_veh:ARREGLO[1..4] DE ENTERO 
    
Proceso 
    ARR(sec);AVZ(sec,v)
    CREAR(sec1);CREAR(sec2)

    cant_tot_z:=0
    cant_tot_I:=0
    cantS:=0
    cantz1:=0
    
    Para i:=1 Hasta 4 Hacer 
        tipo_veh[i]:=0
    FP 

    Mientras v<>'*' Hacer 
        largo:=0
        //cargo todo la secuencia hasta + incluido
        Repetir 
            largo:=largo+1
            A[largo]:=v 
            AVZ(sec,v)
        Hasta Que (A[largo]='+')
        //al salir estoy parado en la marca o siguiente patente

        //miro la secuencia guardada en el arreglo
        fin_patente:=1
        Mientras A[fin_patente]<>'-' Hacer
            fin_patente:=fin_patente+1 
        FM 
        tipo_str:=A[fin_patente+1] //avanzo el guion y me paro en tipo y lo guardo
        indx_tipo:=Convertir_Tipo(tipo_str)
        tipo_veh[indx_tipo]:=tipo_veh[indx_tipo]+1

        tiempo_str:=A[fin_patente+2] //me posiciono, desde el guion, en el tiempo circ y lo guardo
        inf_aux:=A[fin_patente+3] //guardo la infraccion
        cant_tot_I:=cant_tot_I+1
        zona_str:=A[largo-2] //zona son los dos ultimos digitos antes del +
        zona_num:=A[largo-1]
        cant_tot_z:=cant_tot_z+1
        
        Si zona_str='Z' Y zona_num='1' Entonces 
            Para i:=1 Hasta largo Hacer 
                Grabar(sec1,A[i])
            FP 
            cantz1:=cantz1+1
        FS 
        tiempo_circ:=Conv_Num(tiempo_str)
        Si inf_aux='S' Y tiempo_circ>2 Entonces
            Para i:=1 Hasta largo Hacer 
                Grabar(sec2,A[i])
            FP    
            cantS:=cantS+1
        FS 
    FM 
    GRABAR(sec1,'*');GRABAR(sec2,'*')

    Escribir('Cantidad vehiculos que circularon en zona z1: ',cantz1,' Porcentaje: ',((cantz1*100)/cant_tot_z))
    Escribir('Cantidad vehiculos que cometieron infraccion : ',cantS,' Porcentaje: ',((cantS*100)/cant_tot_I))

    mayor:=LV
    Para i:=1 Hasta 4 Hacer 
        Si tipo_veh[i]>mayor Entonces
        mayor:=tipo_veh[i]
        tipo_str:=Reconvertir_Tipo(i)
        FS 
    FP 
    Escribir('El tipo de vehiculo que mas circulo en la ciudad es: ',tipo_str)
    
    Cerrar(sec);Cerrar(sec1);Cerrar(sec2)
FINACCION

//SOLUCIÓN CON LISTAS
Accion listas Es 
Ambiente
    sec,sec1,sec2:SECUENCIA DE CARACTER 
    v:CARACTER 

    NODO=Registro 
        dato:CARACTER 
        prox:PUNTERO A NODO 
    FR 
    p,q,prim,aux:PUNTERO A NODO 

    Procedimiento CargaEncolada(REF cabeza,qs:PUNTERO A NODO, c:CARACTER) Es 
        Nuevo(qs)
        *qs.dato:=c 
        Si cabeza=NIL Entonces
            cabeza:=qs
            p:=cabeza
            *qs.prox:=NIL
        Sino 
            *p.prox:=qs
            *qs.prox:=NIL
            p:=qs 
        FS 
    FP 

    i:ENTERO
    cantz1,cant_tot_z,cantS,cant_tot_I:ENTERO 
    zona_str,inf_aux,tipo_str:AN
    zona_num,tiempo_circ,indx_tipo:ENTERO
    mayor:ENTERO
    aux_v:CARACTER

    Funcion Convertir_Tipo(c:CARACTER):ENTERO Es 
        Segun c Hacer 
        'A': Convertir_Tipo:=1
        'B': Convertir_Tipo:=2
        'C': Convertir_Tipo:=3
        'D': Convertir_Tipo:=4
        FS 
    FF 
    Funcion Reconvertir_Tipo(n:ENTERO):CARACTER Es 
        1: Reconvertir_Tipo:='A'
        2: Reconvertir_Tipo:='B'
        3: Reconvertir_Tipo:='C'
        4: Reconvertir_Tipo:='D'
    FF

    Funcion Conv_Num(c:CARACTER):ENTERO Es 
        Segun c Hacer
        '1': Conv_Num:=1
        '2': Conv_Num:=2
        '3': Conv_Num:=3
        '4': Conv_Num:=4
        '5': Conv_Num:=5
        '6': Conv_Num:=6
        '7': Conv_Num:=7
        '8': Conv_Num:=8
        '9': Conv_Num:=9
        FS 
    FF

    tipo_veh:ARREGLO[1..4] DE ENTERO 
    
Proceso 
    ARR(sec);AVZ(sec,v)
    CREAR(sec1);CREAR(sec2)

    cant_tot_z:=0
    cant_tot_I:=0
    cantS:=0
    cantz1:=0
    
    Para i:=1 Hasta 4 Hacer 
        tipo_veh[i]:=0
    FP 


    Mientras v<>'*' Hacer 
        prim:=NIL 
        //cargo todo la secuencia hasta + incluido
        Repetir     
            aux_v:=v
            CargaEncolada(prim,q,v)
            AVZ(sec,v)
        Hasta Que (aux_v='+')
        //al salir estoy parado en siguiente patente o en la marca

        p:=prim
        Mientras (*p.dato<>'-') Hacer 
            p:=*p.prox 
        FM 
        p:=*p.prox
        indx_tipo:=Convertir_Tipo(*p.dato)
        tipo_veh[indx_tipo]:=tipo_veh[indx_tipo]+1

        p:=*p.prox

        tiempo_circ:=Conv_Num(*p.dato)

        p:=*p.prox 

        inf_aux:=*p.dato 
        cant_tot_I:=cant_tot_I+1

        p:=*p.prox 

        zona_str:=*p.dato
        p:=*p.prox
        zona_num:=*p.dato
        cant_tot_z:=cant_tot_z+1
        
        Si (zona_str='Z') Y (zona_num='1') Entonces
            p:=prim  
            Mientras p<>NIL Hacer
                Grabar(sec1,*p.dato)
                p:=*p.prox 
            FM
            cantz1:=cantz1+1
        FS 
        
        Si (inf_aux='S') Y (tiempo_circ>2) Entonces
            p:=prim 
            Mientras p<>NIL Hacer
                Grabar(sec2,*p.dato)
                p:=*p.prox 
            FM
            cantS:=cantS+1
        FS 

        //liberar memoria para el siguiente auto
        p:=prim 
        Mientras p<>NIL Hacer
            aux:=p
            p:=*p.prox 
            Disponer(aux)
        FM 

    FM 
    GRABAR(sec1,'*');GRABAR(sec2,'*')

    Escribir('Cantidad vehiculos que circularon en zona z1: ',cantz1,' Porcentaje: ',((cantz1*100)/cant_tot_z))
    Escribir('Cantidad vehiculos que cometieron infraccion : ',cantS,' Porcentaje: ',((cantS*100)/cant_tot_I))

    mayor:=LV
    Para i:=1 Hasta 4 Hacer 
        Si tipo_veh[i]>mayor Entonces
        mayor:=tipo_veh[i]
        tipo_str:=Reconvertir_Tipo(i)
        FS 
    FP 
    Escribir('El tipo de vehiculo que mas circulo en la ciudad es: ',tipo_str)
    
    Cerrar(sec);Cerrar(sec1);Cerrar(sec2)
FINACCION


Accion ej2 Es 
Ambiente
    formato_fecha=Registro 
        aaaa:N(4)
        mm:1..12
        dd:1..31
    FR 

    notas=Registro
        asignatura:N(3)
        dni:N(8)
        evaluacion:1..4
        fecha:formato_fecha
        nota:1..10
    FR 
    arch:archivo secuencial de notas 
    reg:notas 

    M:ARREGLO[1..4,1] DE ENTEROS 
    i,j:ENTERO
    promedio_nota:REAL
    cont_1,cont_2,cont_3,cont_4:ENTERO
Proceso 
    ABRIR E/(arch);Leer(arch,reg)

    Para i:=1 Hasta 4 Hacer 
        Para j:=1 Hasta 1 Hacer 
            M[i,j]:=0
        FP 
    FP 
    promedio_nota:=0
    Mientras NFDA(arch) Hacer 
        Si reg.asignatura=674 Entonces
            Segun reg.evaluacion Hacer 
                1:cont_1:=reg.nota 
                2:cont_2:=reg.nota
                3:cont_3:=reg.nota
                4:cont_4:=reg.nota
            FS 
            promedio_nota:=(cont_1+cont_2+cont_3+cont_4)/4

            Si promedio_nota>=8 Entonces
                M[1,1]:=M[1,1]+1
            Sino 
                Si promedio_nota>=6 Entonces
                    M[2,1]:=M[2,1]+1
                Sino 
                    M[3,1]:=M[3,1]+1
                FS 
            FS 
            M[4,1]:=M[4,1]+1
        FS 
        Leer(arch,reg)
    FM 
    Escribir('Porcentaje que aprobaron directo: ',((M[1,1]*100)/M[4,1]))
    Escribir('Porcentaje que regularizaron: ',((M[2,1]*100)/M[4,1]))
    Escribir('Cantidad que quedaron libre: ',M[3,1])
    CERRAR(arch)
FINACCION



Accion AED (prims:Puntero a NODO_S) Es
 Ambiente
    NODO_S = Registro
        leg
        ayp 
        com 
        notas:Arreglo[1..5]
        prox:Puntero a NODO_S
    FinRegistro
    p:Puntero a NODO_S

    NODO_D = Registro
        nro_curso
        cantidad
        legajo
        ant:Puntero a NODO_D
        prox:Puntero a NODO_D
    FinRegistro
    primd,ult,d,q:Puntero a NODO_D

    prom:real
    mayor_aula:entero
    EsAlumnoLibre(),AsignarAula()
 Proceso 
    primd:=nil 
    ult:=nil 
    d:=nil 
    Para i:=1 hasta 6 Hacer //NODOS Cabeceras
        Nuevo(q)
        *q.nro_curso:=i 
        *q.cantidad:=0
        *q.legajo:=0
        Segun i Hacer
            =1: *q.prox:=d  //Primer NODO
                *q.ant:=nil 
                *d.ant:=q 
                primd:=q 

            =6: *q.prox:=nil //Ultimo NODO
                *q.ant:=ult 
                *ult.prox:=q 
                ult:=q

            Otro:*q.ant:=*d.ant //NODO Intermedio
                 *q.prox:=d 
                 *d.ant:=q 
                 *(d.ant).prox:=q
        FinSegun 
    FinPara

    p:=prims
    a:=nil 
    Mientras p <> nil Hacer 
        Si EsAlumnoLibre(*p.legajo) = Falso Entonces
            Mientras (AsignarAula() <> *d.nro_curso) y (*primd.legajo <> 0) Hacer
                d:=*d.prox 
            FinMientras
            *d.cantidad:=*d.cantidad + 1
            tot:=tot + 1
            Nuevo(q)
            *q.nro_curso:=AsignarAula()
            *q.cantidad:=1 
            *q.legajo:=*p.legajo
            d:=primd
            Si *d.nro_curso <> 6 Entonces 
                *q.prox:=d 
                *q.ant:=*d.ant 
                *d.ant:=q 
                *(d.ant).prox:=q 
            sino 
                *q.prox:=nil 
                *q.ant:=ult 
                *ult.prox:=q 
                ult:=q
            FinSi
            p:=prims 
            Mientras (p <> nil ) y (*p.legajo <> *d.legajo) Hacer
                a:=p 
                p:=*p.prox 
            FinMientras 
            Si (a = nil) Entonces
                Prim = *p.prox
            sino
                *a.prox:= *p.prox
            FinSi
            z:=p //sabemos que si entra en el condicional, es un alumno que se elimina 
            Disponer(z)
        FinSi
        p:=*p.prox 
    FinMientras 

    d:=primd
    mayor_aula:=LV
    Mientras primd <> nil Hacer 
        Mientras *primd.legajo <> 0 Hacer
            d:=*d.prox 
        FinMientras
        Si mayor_aula < *d.cantidad Entonces 
            mayor_aula:=*d.cursoNro
            cant_mayor:=*d.cantidad
        FinSi
        d:=*d.prox 
    FinMientras 
    Esc('El promedio de alumno por aula es de:',tot/6)
    Esc('El aula con mayor cantidad de alumnos es',mayor_aula,'con un total de',cant_mayor)
FinAccion
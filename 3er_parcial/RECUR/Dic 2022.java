Accion jugadores(prim:puntero a nodo) es
    Ambiente
        NODO = Registro
            pais:
            edad:(a:arreglo[1..26] de enteros)
            grupo: 
            puntos:
            Ta:
            Tr:
            prox:Puntero a NODO
        FinRegistro
        p, a:Puntero a NODO

        grupo,posc,i:entero
        nombre_pais, equipoMayor, equipoMenor:AN;
        prom, may_prom, men_prom: Real

        Funcion promedio(a:arreglo[1..26] de enteros,suma,posc:Entero):Real
            Si posc = 26 entonces 
                promedio:=(a[posc]+suma)/26
            sino 
                promedio:= promedio(A,suma+A[posc],posc + 1)
            FinSi
        FinFuncion
    
    Proceso
        may_prom:=LV 
        men_prom:=HV
        Para i:=1 hasta 32 hacer
            Escribir('Ingrese nombre del grupo y pais del sorteo')
            Leer(grupo)
            Leer(nombre_pais)
            p:=prim
            a:= nil;

            Mientras (*p.pais <> nombre_pais) entonces
                a:= p;
                p:= *p.prox;
            FinMientras

            *p.grupo:= grupo;
            prom:=promedio(*p.edad,0,1)
            Si (may_prom < prom) entonces
                equipoMayor:= *p.pais
                may_prom:=prom
            Fsi 
            Si (men_prom > prom) entonces
                equipoMenor:= *p.pais;
                men_prom:=prom
            FinSi
        FinPara
        Escribir('El equipo con mayor promedio fue de',equipoMayor)
        Escribir('El equipo con menor promedio fue de',equipoMenor)

        //reordeno la lista
        p:=prim 
        Mientras (p <> nil) hacer
            Si (*p.grupo > *(*p.grupo).prox) entonces
                Si (p=prim) entonces
                    prim:=*p.prox
                    *p.prox:=*prim.prox
                    *prim.prox:=p 
                sino 
                    *a.prox:=*p.prox 
                    *p.prox:=*(*p.prox).prox
                    *(*a.prox).prox:=p 
                FinSi
                p:=prim 
            sino 
                a:=p 
                p:=*p.prox 
            FinSi
        FinMientras
FinAccion
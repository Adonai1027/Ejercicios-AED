Accion Recursividad (prim:Puntero a NODO_C) Es
 Ambiente
    NODO_C = Registro
        dato:Entero
        prox:Puntero a NODO
    FinRegistro
    pc,a,q:Puntero a NODO

    NODO_S = Registro
        patron:entero
        prox:Puntero a NODO_S
    FinRegistro
    prim,p:Puntero a NODO_S

    Funcion bin(x:entero):booleano Es
        Si (x < 100) Entonces //se verifica el posible caso base
            Si (x MOD 10) = 0 Entonces
                bin:=true
            sino
                bin:=false
            FinSi
        sino
            Si (x MOD 100) = 10 Entonces //caso recursivo
                bin:=bin(x DIV 100)
            sino
                bin:=false
            FinSi
        FinSi
    FinFuncion
 Proceso
    pc:=prim 
    p:=prim 
    a:=nil
    Mientras (pc.prox <> prim) Hacer
        Si bin(pc.dato = false) Entonces //verifico que no cumplen
            //procedimiento insertar para lista simple
            Nuevo(q)
            q.patron:=pc.dato
            Mientras (p <> nil) y (p.patron <q.patron) Hacer
                a:=p
                p:=*p.prox
            FinMientras
            Si a=nil Entonces
                *q.prox:=prim
                prim:=q 
            sino
                *q.prox:=p
                *a.prox:=q 
            FinSi
        sino
            //eliminacion para lista circular

        FinSi
    FinMientras
FinAccion

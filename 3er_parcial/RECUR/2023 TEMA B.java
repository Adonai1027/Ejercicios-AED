Accion Recursividad (primc:Puntero a NODO_C) Es
 Ambiente
    NODO_C = Registro
        cod
        prox:Puntero a NODO
    FinRegistro
    c:Puntero a NODO

    NODO_D = Registro
        dato
        ant:Puntero a NODO_D
        prox:Puntero a NODO_D
    FinRegistro
    primd,d,Q,ult:Puntero a NODO_D

    Funcion Multiplo3 (cod,cant:entero):booleano
        Si cod=0 entonces
            Si (cant MOD 3) = 0 entonces
                Multiplo3:=true
            Sino
                Multiplo3:=false
            FinSi
        sino
            Si (cod MOD 10) = 0 entonces
                Multiplo3:=Multiplo3 ((cod DIV 10),cant + 1);
            sino
                Multiplo3:=Multiplo3((cod DIV 10),cant);
            FinSi
        FinSi
    FinFuncion
 Proceso
    c:=primc
    cod:=0
    Mientras (*c.prox <> primc) Hacer
        //carga en lista doble
        cod:=*c.cod
        Si Multiplo3(cod,0) = false entonces
            Nuevo(Q)
            *Q.dato:= *c.cod
            //carga encolada
            Si primd = nil entonces
                primd:=q 
                ult:=q 
                *q.prox:=nil 
                *q.ant:=nil 
            sino 
                *q.prox:=ult 
                *q.ant:=nil 
                *ult.ant:=q 
                ult:=q 
            FinSi
        FinSi
        c:=*c.prox
    FinMientras
    Mientras (d <> ult) Hacer
        Escribir(*d.dato)
        d:=*d.prox
    FinMientras
FinAccion
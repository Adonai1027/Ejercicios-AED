Accion 2021 prim:Puntero a NODO Es 
 Ambiente
    NODO = Registro
        msj_bin
        prox:Puntero a NODO
    FinRegistro
    p:Puntero a NODO
    prims,ps,as,qs:Puntero a NODO

    Funcion Deteccion(nro_bin,cant:Entero):Alfanumerico
     Ambiente 
        aux:entero 
     Proceso
        Si (nro_bin < 10) Entonces //caso base
            Si nro_bin = 1 Entonces
                aux:=cant + 1
            FinSi
            Si (aux MOD 2) <> 0 Entonces //es impar
                Deteccion:='Error'
            sino //es par
                Deteccion:='Sin Error'
            FinSi 
        sino 
            Si (nro_bin MOD 10) = 1 Entonces
                Deteccion:=Deteccion(nro_bin DIV 10,cant + 1)
            sino 
                Deteccion:=Deteccion(nro_bin DIV 10,cant)
            FinSi   
        FinSi 
    FinFuncion 
 Proceso 
    prims:=nil 
    p:=prim 
    Mientras p <> nil Hacer 
        Si Deteccion(*p.msj_bin,0) = 'Sin Error' Entonces
            Nuevo(q) 
            *q.msj_bin:=*p.msj_bin
            *q.prox:=prims
            prims:=q
        FinSi 
        p:=*p.prox
    FinMientras 
FinAccion
Accion NumerosBinarios 2021 (prim:puntero a nodo) es
    Ambiente
        Nodo=registro
            codigo:entero
            prox:puntero a nodo
        FR
        p,q,primsalida:puntero a nodo
        unos:booleano
        Funcion ParidadUnos(nro,cant):booleano
            Si nro=0 entonces //caso base
                Si cant MOD 2=0 entonces
                    unos:=falso
                Sino
                    unos:=verdadero
                FinSi
            Sino //caso recursivo
                Si nro MOD 10=1 entonces
                    unos:=unos((nro DIV 10),cant + 1);
                Sino
                    unos:=unos((nro DIV 10), cant); //no incremento la cantidad
                FinSi
            FinSi
        FinFuncion

    Proceso
        p:=prim
        primsalida:=nil
        
        Mientras (p <> nil)hacer
            Si ParidadUnos(*p.codigo,0) entonces //caso verdadero
                Nuevo (q);
                *q.codigo:=*p.codigo
                *q.prox:=primsalida
                primsalida:=q
            FinSi
            p:=*p.prox
        FinMientras

        q:=primsalida
        Mientras (q<>nil)hacer
            Esc("Nodo que contiene error, su mensaje es:",*q.codigo);
            q:=*q.prox
        FinMientras
FinAccion
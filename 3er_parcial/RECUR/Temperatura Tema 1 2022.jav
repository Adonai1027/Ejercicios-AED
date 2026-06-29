Accion Temperaturas Tema 1(prim:puntero a Nodo) es
    Ambiente

        Nodo=Registro
            DNI:N(8);
            Apyn:AN(30);
            Edad:N(3);
            Nro_cama: N(3);
            Nro_habitacion: N(3);
            Temperaturas: arreglo [1...4] de real;
            prox:puntero a Nodo
        FR

        p,a:puntero a nodo

        Funcion TemperaturasMenores (i:entero, prom:real, A:arreglo de [1...4] de real): booleano
            //caso base
            Si cont=4 entonces //ya se realizaron todas las mediciones
                Si (prom / 4) > 36,5 entonces
                    TemperaturasMenores:= falso
                Sino
                    TemperaturasMenores:=verdadero
                FinSi
            Sino 
                //caso recursivo
                TemperaturasMenores:=TemperaturasMenores(i +1,prom + A[i], A);
            FinSi
        FinFuncion


    Proceso
        p:=prim
        a:=nil

        Mientras p<>nil hacer
            a:=p
            Si no TemperaturasMenores(0,0,*p.Temperaturas) entonces
                Esc("Paciente:",*p.Apyn);
                *a.prox:=*p.prox
                Disponer(p);
            FinSi
            p:=*p.prox
        FinMientras

FinAccion
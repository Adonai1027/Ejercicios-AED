Accion Presion Arterial 2023 (prim:puntero a Nodo)es
    Ambiente
        sistolica,diastolica:arreglo [1...4] de enteros;

        Nodo=registro
            DNI:N(8);
            ApyN:AN(50);
            Nro_cama:N(3);
            Nro_habitacion:N(2);
            PA_sistolica: sistolica;
            PA_diastolica:diastolica;
            prox:puntero a Nodo
        FR

        p,q,a:puntero a Nodo
        PresionNormal:booleano

        Funcion Presion (promsis,promdias:real, A,B:arreglo [1...4] de real, i:entero):booleano;
            Si i=4 entonces //caso base cuando ya recorri todas las mediciones
                Si (promdias <+ (promsis-promdias)/3>) < 120 y ((promdias + (promsis-promdias)/3) > 80) entonces
                    PresionNormal:=verdadero;
                Sino
                    PresionNormal:=falso
            Sino
                PresionNormal:=Presion(promsis + A[i],promdias + B[i], A,B, i + 1);
            FinSi
        FinFuncion
    Proceso
        i:=1
        promsis:=0
        promdias:=0
        p:=prim
        Mientras (p<>nil) hacer
            Si Presion(promsis,promdias,*p.PA_sistolica,*p.PA_diastolica,i) entonces
                //dar de baja el paciente
                a:=p
                p:=*p.prox
                *a.prox:=*p.prox
                Disponer(p);
            Sino
                p:=*p.prox
            FinSi
        FinMientras

FinAccion
                
                




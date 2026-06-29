Accion MULTIPLOS DE 3 (prim:puntero a Circular) es
    Ambiente
        Circular=Registro
            Binario:entero;
            prox:puntero a Circular
        FR

        p:puntero a Circular

        Doble=registro
            Binario_no_multiplos:entero;
            ant:puntero a Doble
            prox:puntero a Doble;
        FR

        primd,ultd,pd:puntero a Doble

        Funcion MultiploDe3 (num,cant:entero):booleano
            Si num=0 entonces
                Si cant MOD 3=0 entonces
                    MultiploDe3:=VERDADERO
                Sino
                    MultiploDe3:=FALSO;
                FinSi
            Sino
                Si num MOD 10=0 entonces
                    MultiploDe3:=MultiploDe3 ((num DIV 10),cant + 1);
                Sino
                    MultiploDe3:=MultiploDe3((num DIV 10),cant);
                FinSi
            FinSi
        FinFuncion


    Proceso
        p:=prim
        primd:=nil

        Mientras p<>nil hacer
            Si no MultiploDe3(*p.Binario,0) entonces
                NUEVO (qd);
                *qd.Binario_no_multiplos:=*p.Binario;
                Si pd=nil entonces
                    *qd.prox:=nil
                    *qd.ant:=nil
                    primd:=qd
                    ultd:=qd
                Sino
                    pd:=primd
                    Mientras (pd <> nil) y (*pd.Binario_no_multiplos *qd.Binario_no_multiplos) hacer
                        pd:=*pd.prox
                    FinMientras
                    Si pd=prim entonces
                        *qd.prox:=nil
                        *qd.ant:=pd
                        *pd.prox:=qd
                        ultd:=qd
                    Sino
                        Si pd = nil entonces //ultima posicion
                            *qd.prox:=nil
                            *qd.ant:=pd
                            *ultd.prox:=qd
                            ultd:=qd
                        Sino
                            *qd.prox:=pd
                            *qd.ant:=*pd.ant
                            *(*pd.ant).prox:=qd
                            *pd.ant:=qd
                        FinSi
                    FinSi
                FinSi
            FinSi
            p:=*p.prox
        FinMientras
        //MUESTRO LA LISTA

        Mientras pd<>nil hacer
            Esc(*pd.Binario_no_multiplos);
            pd:=*pd.prox
        FinMientras
FinAccion
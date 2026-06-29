Accion Taylor Tema A(prims:puntero a Simple, M:arreglo [1...12,1...31] de enteros);
    Ambiente
        Fecha=registro
            MM:1...12;
            DD:1...31;
        FR
        Simple=registro
            DNI:N(8);
            NumeroFila:N(5);
            Fecha_fila:Fecha;
            Fecha_entrada:Fecha;
            prox:puntero a Simple
        FR

        p:puntero a Simple

        Doble=registro
            Fecha_recital:Fecha;
            Cantidad_asistentes:entero
            Cod:AN(30);
            DNI:N(8);
            ant,prox:puntero a doble
        FR

        primd,ultd,pd,qd,n1,n2,n3:puntero a Doble

        i,j:entero
        mayor:entero
        fecha_mayor:fecha;
        encriptacion:entero;
        fecha9, fecha10, fecha11: entero

    Proceso
        
        p:=prim
        primd:=nil
        ultd:=nil
        Para i:=1 a 3 hacer //creo los nodos cabeceras con las 3 fechas posibles (9,10,11)
            Nuevo(qd);
            *qd.Fecha_recital.DD:=8 + i;
            *qd.Fecha_recital.MM:=11
            *qd.Cantidad_asistentes:=0
            *qd.Cod:= nil
            *qd.DNI:=nil

            Si i=1 entonces //primer nodo
                *qd.prox:=nil
                *qd.ant:=nil
                primd:=qd
                ultd:=qd
                n1:=qd
            Sino
                pd:=primd
                Mientras pd<>nil y (*pd.prox < *qd.prox) hacer
                    pd:=*pd.prox
                FinMientras
                Si pd=nil
                    *qd.ant:=pd
                    *qd.prox:=nil
                    *pd.prox:=qd
                    ultd:=qd
                    n2:=qd
                Sino //insercion 2do lugar
                    *qd.prox:=nil
                    *qd.ant:pd
                    *pd.ant:=qd
                    *(*pd.ant).prox:=qd
                    n3:=qd
                FinSi
            FinSi
        FinPara

        fecha9, fecha10, fecha11:=0

        Mientras p<>nil hacer
            //busco el codigo de encriptacion
            Para i:=1 a 31 hacer
                Para j:=1 a 12 hacer
                    Si i=*p.Fecha_fila y j=*p.Fecha_entrada entonces
                        encriptacion:= M[i,j];
                FinPara
            FinPara

            pd:=primd

            Mientras (*pd.Fecha_recital <> *p.Fecha_entrada) hacer
                pd:=*pd.prox
            FinMientras
            Nuevo(qd)
            *qd.Fecha_recital:=nil
            *qd.Cantidad_asistentes:=nil
            *qd.Cod:=SwiftieEncriptada(*p.NumeroFila,encriptacion);
            *qd.DNI:=*p.DNI
            Segun *p.Fecha_entrada hacer
                =9/11:
                    *pd.Cantidad_asistentes:= *pd.Cantidad_asistentes + 1;
                    Si *pd.prox = n2 entonces //el siguiente nodo es la proxima fecha de recital
                        *qd.prox:=n2
                        *qd.ant:=n1
                        *(*n2.ant).prox:=qd
                        *pd.prox:=qd
                    Sino
                        //ya hay otras swifties cargadas en la fecha del recital
                        *qd.prox:=n2
                        *qd.ant:=pd
                        *
                        
            FinGun

            
            
            

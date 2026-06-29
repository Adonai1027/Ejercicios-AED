Accion Taylor Tema B(primd:puntero a Doble) es
    Ambiente
        Doble=Registro
            Fecha_recital:Fecha;
            Cant_asist:N(10);
            Cod:AN(30);
            DNI:N(8);
            ant,prox:puntero a doble
        FR

        pd, qd, rd,ultd:puntero a doble;

        Simple=Registro
            lugar: N(5);
            DNI:N(8);
            prox:puntero a Simple
        FR

        prim, p, q:Puntero a Simple

        fecha_usuario:fecha;
        cant_ingresaron:entero;

    Proceso
        Esc("Ingrese fecha");
        Leer(fecha_usuario)
        pd:=primd
        prim:=nil
        cant_ingresaron:=0
        Mientras pd<>nil hacer
            Si *pd.fecha <> nil entonces 
                rd:=pd
            Sino
                Si SwiftieHabilitada(*pd.Cod, *pd.DNI) entonces //puede ingresar
                    *rd.Cant_asist:=*rd.Cant_asist - 1;
                    //verifico si ingreso en la fecha del usuario
                    Si (*rd.Fecha_recital=fecha_usuario) entonces
                        cant_ingresaron:=cant_ingresaron + 1;
                    FinSi
                    //borro de la lista doble
                   
                    Si pd = ultd entonces
                        (pd.Ant).Prox:= Nil 
                        ultd:= *pd.Ant
                    sino 
                        (pd.Prox).Ant:= *pd.Ant
                        (pd.Ant).Prox:= *pd.Prox
                    FinSi  
                    DISPONER(pd)
                Sino
                    Nuevo(q); //creo nodo en la lista simple
                    *q.lugar:=DesencriptarLugarEnLaFila(*pd.cod)
                    *q.DNI:=*pd.DNI;
                    
                    Si p=nil
                        *q.prox:=nil
                        prim:=q
                    Sino
                        p:=prim
                        a:=nil
                        Mientras (p<>nil) y (*p.lugar < *q.lugar) hacer
                            a:p
                            p:=*p.prox
                        FinMientras

                        Si a=nil entonces //en el primer lugar
                            *q.prox:=prim
                            prim:=q
                        Sino //lugar intermedio
                            *q.prox:=p
                            *a.prox:=q 
                        FinSi
                    FinSi
                FinSi
            FinSi
            pd:=*pd.prox
        FinMientras

        Esc("La cantidad de swifties que asistieron en la fecha:",fecha_usuario,"es de:",cant_ingresaron);

FinAccion

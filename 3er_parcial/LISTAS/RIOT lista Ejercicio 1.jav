Accion RIOT (prims:puntero a Simple) es
    Ambiente
        Cliente=registro
            NombreCliente:AN(45);
            Localizacion:("LAN","EUW","LAS");
            Nivel:1...500;
        FR
        arch_clientes:Archivo de Cliente;
        reg_cli:Cliente;

        Servidores=Registro
            Servidor:N(10);
            Localizacion:("LAN","EUW","LAS");
            Estado:("Libre","En Mantenimiento");
            CantUsuarios:0..10;
            Latencia:1..1500;
            prox:puntero a Servidores
        FR

        ps,qs:puntero a Servidores

        SimpleSalida=registro
            NombreCliente:AN(45);
            Servidor:N(10);
            Localizacion:("LAN","EUW","LAS");
            Nivel:1...500;
            prox:puntero a SimpleSalida
        FR

        primsal,psal,qsal:puntero a SimpleSalida;

        DobleSalida=Registro
            Servidor:N(10);
            Latencia:1...500;
            ant:puntero a DobleSalida;
            prox:puntero a DobleSalida;
        FR

        primd,pd,qd,ultd:Puntero a DobleSalida;


    Proceso
        Abrir E/(arch_clientes);
        Leer(arch_clientes);

        ps:=prims
        primsal:=nil

        Mientras NFDA(arch_clientes) hacer
            Mientras ps<>nil hacer
                Si *ps.Estado<>"En mantenimiento" y *ps.CantUsuarios<10 entonces
                    Nuevo(qsal);
                    *qsal.NombreCliente:=reg_cli.NombreCliente;
                    *qsal.Servidor:=*ps.Servidor;
                    *qsal.Localizacion:=*ps.Localizacion;
                    *qsal.Nivel:=reg_cli.Nivel;
                    *qsal.prox:=primsal
                    primsal:=qsal
                Sino
                    ps:=*ps.prox
                FinSi
            FinMientras

            Leer(arch_clientes,reg_cli);
        FinMientras

        psal:=primsal

        //muestro los clientes
        Mientras psal <> nil hacer
            Esc("Cliente:",*psal.NombreCliente);
            psal:=*psal.prox
        FinMientras

        //Cargo lista doble

        ps:=prims
        primd:=nil
        ultd:=nil

        Mientras ps<>nil hacer
            Si (*ps.Estado=Libre) y (*ps.CantUsuarios<10) entonces
                Nuevo(qd);
                *qd.Servidor:=*ps.Servidor;
                *qd.Latencia:=*ps.Latencia;

                Si primd=nil entonces
                    *qd.prox:=nil
                    *qd.ant:=nil
                    primd:=qd
                    ultd:=qd
                Sino
                    pd:=primd
                    Mientras (pd<>nil) y (*pd.Latencia < *qd.Latencia) hacer
                        pd:=*pd.prox
                    FinMientras
                    
                    Si pd=primd entonces
                        *qd.prox:=ps
                        *qd.ant:=nil
                        *pd.ant:=qd
                        primd:=qd
                    Sino
                        Si pd=nil entonces
                            *qd.prox:=nil
                            *qd.ant:=ultd
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
            ps:=*ps.prox
        FinMientras
        
        CERRAR(arch_clientes);

FinAccion

        
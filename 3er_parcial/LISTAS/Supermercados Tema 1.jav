Accion Supermercados Tema 1(prim:puntero a Circular) es
    Ambiente
        Compras=Registro
            Fecha_compra:Fecha
            DNI:N(8);
            Cantidad_articulos:N(3);
            Importe_total:N(10);
        FR

        arch_compras:Archivo SECUENCIAL de Compras ordenado por Fecha_compra;
        reg_com:Compras;

        Clientes=Registro
            DNI:N(8);
            Apyn:AN(30);
            Fecha_adhesion:Fecha
            Categoria:
        FR

        arch_clientes:Archivo de Clientes INDEXADO por DNI;
        reg_cli:Clientes;

        Circular=Registro
            Chances_extra:1..55;
            prox:puntero a Circular
        FR

        p,ult_pos:puntero a Circular

        Doble=Registro
            Apyn:AN(30);
            Chances_total:N(5);
            ant,prox:puntero a Doble
        FR

        primd,ultd, pd,qd:puntero a Doble;

    Proceso
        Abrir E/(arch_compras);
        Leer(arch_compras);
        Abrir /S(arch_clientes);

        primd:=nil
        ultd:=nil
        p:=prim
        Mientras NFDA(arch_compras) hacer
            chances_por_compra:=0
            reg_cli.DNI:=reg_com.DNI;
            Leer(arch_clientes,reg_cli);
            Si EXISTE entonces
                //busco en la lista doble si existe
                pd:=primd
                Mientras (pd <> nil) o (*pd.Apyn<>reg_cli.Apyn) hacer
                    *pd:=*pd.prox
                FinMientras
                Si pd=nil entonces
                    //creo un nuevo nodo en la lista doble y lo inserto
                    Nuevo(qd);
                    *qd.ApyN:=reg_cli.Apyn;
                    *qd.Chances_total:=5;
                    Si primd=nil entonces
                        *qd.prox:=nil
                        *qd.ant:=nil
                        primd:=qd
                        ultd:=qd
                    Sino
                        pd:=primd
                        //carga ordenada
                        Mientras (pd<>nil) y (*pd.Apyn < *qd.Apyn) hacer
                            pd:=*pd.prox
                        FinMientras
                        Si pd=primd entonces
                            *qd.prox:=pd
                            *qd.ant:=nil
                            *p.ant:=qd
                            primd:=qd
                        Sino
                            Si (*pd.Apyn > *qd.Apyn) entonces
                                *qd.prox:=pd;
                                *qd.ant:=*pd.ant;
                                *(*pd.ant).prox:=qd
                                *pd.ant:=qd
                            Sino
                                //si pd=nil
                                *qd.prox:=nil
                                *qd.ant:=ultd
                                *ult.prox:=qd
                                ultd:=qd
                            FinSi
                        FinSi
                Sino
                    //actualizo los datos porque el nombre ya existe en la lista doble
                
                    //sumo cantidad de chances por compras
                    *pd.Chances_extra:=*pd.Chances_extra + (reg_cli.Importe_total DIV 100);
                    
                    chances_black:=0
                    
                    Si reg_cli.Categoria="Black" entonces
                        Para i:=1 a Tirar() hacer
                            p:=*p.prox
                        FinPara
                    FinSi
                    
                    *pd.Chances_total:=*pd.Chances_total + *p.chances_black;
               FinSi
            FinSi
        FinMientras
        CERRAR(arch_compras);
        CERRAR(arch_clientes);

FinAccion
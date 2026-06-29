Accion Cadena Supermercado 2022(primd:puntero a Doble) es
    Ambiente
        Compras=Registro
            Fecha_compra:Fecha;
            DNI_cliente:N(8);
            Cantidad_art:N(3);
            Importe: N(10);
        FinRegistro

        arch_compras:Archivo SECUENCIAL de Compras ordenado por Fecha_compra;
        reg_c:Compras;

        Socios=Registro
            DNI_cliente:N(8);
            ApyN:AN(50);
            Fecha_adhesion:Fecha;
            Categoria:
        FinRegistro

        arch_socios:Archivo de Socios INDEXADO por DNI_cliente;
        reg_s:Socios;

        Simple=Registro
            DNI_cliente:N(8);
            Cantidad_compras_real:N(3);
            Importe_total:N(10);
            prox:puntero a Simple
        FR

        prims,ps,qs,as:puntero a Simple

        Doble=Registro
            Importe_min:N(5);
            Importe_max:N(15);
            Descuento:0,01...0,99;
            Cupo_disp: booleano;
            Rubro_desc:1...9;
            prox:puntero a Doble
            ant:puntero  a Doble
        FR

        pd,qd,ad:puntero a Doble
    
    Proceso
        prims:=nil
        as:=nil

        Abrir E/(arch_compras);
        Leer(arch_compras);
        Abrir E/(arch_socios);

        Mientras NFDA(arch_compras) hacer
            reg_s.DNI_cliente:=reg_c.DNI_cliente;
            Leer(arch_socios,reg_s);

            Si NO EXISTE entonces //NO ES SOCIO (punto A)
                ps:=prim
                Mientras (ps <> nil) y (*ps.DNI_cliente <> reg_c.DNI_cliente) hacer
                    ps:=*ps.prox
                FinMientras
                Si ps <> nil entonces //se encontro el DNI en la lista
                    *qs.Cantidad_compras_real:=*qs.Cantidad_compras_real + 1;
                    *qs.Importe_total:= *qs.Importe_total + reg_c.Importe_total;
                Sino
                    Nuevo(qs); //carga ordenada por DNI
                    *qs.DNI_cliente:=reg_c.DNI_cliente;
                    *qs.Cantidad_compras_real:=1
                    *qs.Importe_total:= reg_c.Importe_total;
                    
                    ps:=prims
                    Mientras (ps <> nil) y (*ps.DNI_cliente < *qs.DNI_cliente) hacer
                        as:=ps
                        ps:=*ps.prox
                    FinMientras
                    
                    Si (as=nil) entonces
                        *qs.prox:=prims
                        prims:=qs
                    Sino
                        *qs.prox:=ps
                        *a.prox:=qs
                    FinSi
                FinSi
            FinSi

            ImporteCompra:=reg_c.Importe_total

            pd:=primd
            Mientras (pd<>nil) hacer
                Si (*pd.Importe_min < ImporteCompra) y (*pd.Importe_max > ImporteCompra) entonces
                    //está en el rango de importe
                    Si *pd.Cupo_disp = VERDADERO entonces //verifico que el cupo este disp
                        Esc("El porcentaje de descuento es:", *pd.descuento * 100);
                        Esc("El rubro es:",*pd.Rubro_desc);
                    Sino
                        Esc("Cupo no disponible");
                    FinSi
                FinSi
                pd:=*pd.prox
            FinMientras
            Si pd=nil entonces
                Esc("No le corresponde descuento");
            FinSi

        Leer(arch_compras,reg_c);

        FinMientras

FinAccion

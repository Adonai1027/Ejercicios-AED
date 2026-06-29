Accion Actualizacion Supermercados es
    Ambiente
        STOCK=REGISTRO
            ProductoID:N(4);
            Stock:N(10);
        FR
        
        arch_mae,arch_sal:Archivo SECUENCIAL de STOCK ordenado por ProductoID;
        reg_mae,reg_sal,aux:STOCK;

        MOVIMIENTOS_DIARIOS=REGISTRO
            ProductoID:N(4);
            ClienteID: N(4);
            Tipo:("C","D");
            Cantidad:N(5);
            PrecioUnitario:N(5,2);
            Total:N(6,2);
            TipoEnvio:("D","S");
        FR

        arch_mov:Archivo SECUENCIAL de MOVIMIENTOS_DIARIOS ordenado por ProductoID;
        reg_mov:ProductoID;

        PRODUCTOS=REGISTRO
            ProductoID:N(4);
            nombre:AN(20);
            Descripcion:AN(50);
            Rubro:("L","C","V","B","P");
        FR
        arch_ind:Archivo de PRODUCTOS INDEXADO por ProductoID;
        reg_ind:PRODUCTOS;

        cant_no_vendidos:entero
        cant_prod_suc:entero

        Procedimiento Leer_Mae () es
            Leer(arch_mae,reg_mae);
            Si FDA(arch_mae) entonces
                reg_mae.ProductoID:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov () es
            Leer(arch_mov,reg_mov);
            Si FDA(arch_mov) entonces
                reg_mae.ProductoID:=HV
            FinSi
        FinProcedimiento

    Proceso
        Abrir E/(arch_mae);
        Abrir E/(arch_mov);
        Abrir /S(arch_sal);
        Abrir E/(arch_ind);
        Leer_mae();
        Leer_mov();

        Mientras(reg_mae.ProductoID <> HV) o (reg_mov.ProductoID<> Hv) hacer
            reg_ind.ProductoID:=reg_mae.ProductoID
            Leer(arch_ind,reg_ind);
            Si EXISTE entonces
                Si reg_mae.ProductoID < reg_mov.ProductoID entonces
                    reg_sal:=reg_mae
                    Grabar(arch_sal,reg_sal);
                    Leer_Mae();
                Sino
                    Si reg_mae.ProductoID = reg_mov.ProductoID entonces
                        aux:=reg_mae
                        Mientras (aux.ProductoID=reg_mov.ProductoID) hacer
                            Segun reg_mov.Tipo hacer
                                ="C": 
                                    Si reg_mov.cantidad <= aux.stock entonces
                                        aux.stock:=aux.stock - reg_mov.cantidad;
                                        Si reg_mov.TipoEnvio = "S" entonces
                                            cant_prod_suc:=cant_prod_suc + reg_mov.cantidad;
                                        FinSi
                                    Sino
                                        Esc("Error, no se puede concretar la venta");
                                        cant_no_vendidos:=cant_no_vendidos + reg_mov.cantidad;
                                        Esc("Nombre:",reg_ind.nombre,"Rubro:",reg_ind.Rubro);
                                        FinSi
                                    FinSi
                                ="D": aux.stock:=aux.stock + reg_mov.cantidad;
                                        Esc("Devolucion realizada con éxito");
                            FinSegun
                            Leer_Mov();
                        FinMientras
                        reg_sal:=aux
                        Grabar(arch_sal,reg_sal);
                        Leer_Mae();
                    Sino
                        //mae mayor
                        Esc("Error,el producto no es comercializado por el Supermercado");
                    FinSi
                FinSi
            Sino
               Esc("El Producto no es comercializado por el supermercado");
        FinMientras

        Esc("La cantidad de productos retiro en sucursal es de:",cant_prod_suc);
        
        CERRAR(arch_mae);
        CERRAR(arch_mov);
        CERRAR(arch_ind);
        CERRAR(arch_sal);
    
FinAccion



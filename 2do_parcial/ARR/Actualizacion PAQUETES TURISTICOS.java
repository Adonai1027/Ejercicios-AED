Accion TURISMO RESISTENCIA es
    Ambiente
        CLIENTES=REGISTRO
            Nro_cliente:N(4);
            apellido_nombre:AN(50);
            DNI:N(8);
            id_paquete:N(5);
            Estado:("SALDADO","SALDO A FAVOR","DEUDOR");
            Categoria:("SIMPLE","PLATA","ORO","DIAMANTE");
            Puntos:N(2);
            Fecha_baja:Fecha;
        FR

        arch_mae,arch_sal:Archivo SECUENCIAL de CLIENTES ordenado por Nro_cliente;
        reg_mae, reg_sal, aux:CLIENTES;

        NOVEDADES=REGISTRO
            Nro_cliente:N(4);
            Nro_novedad:0...999;
            apellido_nombre:AN(50);
            DNI:N(8);
            id_paquete:N(5); 
            Fecha_novedad:Fecha;
            Monto:N(4,2);
        FR

        arch_mov:Archivo SECUENCIAL de NOVEDADES ordenado por nro_cliente,Nro_novedad;
        reg_mov:NOVEDADES;

        PAQUETES_TURISTICOS=REGISTRO
            id_paquete:N(5); 
            Nombre: AN(30);
            Costo:N(3,2);
            Destino: AN(30);
        FR

        arch_ind:Archivo de PAQUETES_TURISTICOS indexado por id_paquete;
        reg_ind:PAQUETES_TURISTICOS;

        cantidad_baja: entero
        monto_total_reintegro: real;
        tot_gen,tot_simple, tot_playa, tot_oro, tot_diamante:enteros

        Procedimiento Leer_Mae() es
            leer(arch_mae,reg_mae);
            SI FDA(arch_mae) entonces
                reg_mae.nro_cliente:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov() es
            leer(arch_mov,reg_mov);
            SI FDA(arch_mov) entonces
                 reg_mov.nro_cliente:=HV
            FinSi
        FinProcedimiento

        Procedimiento DEUDOR() es
            Segun aux.categoria hacer
                ="SIMPLE": 
                    aux.saldo:=aux.saldo - reg_mov.monto;
                    tot_simple:=tot_simple + 1;
                ="PLATA": 
                    aux.saldo:=aux.saldo - reg_mov.monto*0,9;
                    aux.Puntos:=aux.Puntos + 10;
                    tot_plata:tot_plata + 1;
                ="ORO": 
                    aux.saldo:=aux.saldo - reg_mov.monto*0,85;
                    aux.Puntos:=aux.Puntos + 20;
                    tot_oro:=tot_oro + 1;
                ="DIAMANTE":
                    aux.saldo:=aux.saldo - reg_mov.monto*0,8
                    aux.Puntos:=aux.Puntos + 30; 
                    tot_diamante:=tot_diamante + 1;
            FinSegun
        FinProcedimiento

        Procedimiento SALDADO_FAVOR() es
            Segun aux.categoria hacer
                ="SIMPLE": 
                    aux.saldo:=aux.saldo + reg_mov.monto;
                    tot_simple:=tot_simple + 1;
                ="PLATA": 
                    aux.saldo:=aux.saldo + reg_mov.monto*0,9;
                    aux.Puntos:=aux.Puntos + 10;
                    tot_plata:tot_plata + 1;
                ="ORO": 
                    aux.saldo:=aux.saldo + (reg_mov.monto + reg_mov.monto*0,85);
                    aux.Puntos:=aux.Puntos + 20;
                    tot_oro:=tot_oro + 1;
                ="DIAMANTE":
                    aux.saldo:=aux.saldo + reg_mov.monto*0,8
                    aux.Puntos:=aux.Puntos + 30; 
                    tot_diamante:=tot_diamante + 1;
            FinSegun
            monto_total_reintegro:=monto_total_reintegro + aux.saldo;
        FinProcedimiento

        Procedimiento Modificacion () es
            Mientras (aux.nro_cliente=reg_mov.nro_cliente) hacer
                tot_gen:=tot_gen + 1;
                Segun reg_mov.Nro_novedad hacer
                    =0: Esc("Error, alta inválida");
                    <=998:
                        Si aux.Estado="DEUDOR" entonces
                            DEUDOR();
                        Sino
                            //estaba en saldado o saldo a favor y llego un nuevo pago
                            SALDADO_FAVOR();
                        FinSi
                    =999: 
                        aux.Fecha_baja:=reg_mov.Fecha_novedad;
                        aux.Estado:="SALDO A FAVOR";
                        SALDADO_FAVOR();
                        cantidad_baja:=cantidad_baja + 1;
                FinSegun
                Leer_Mov();
            FinMientras
        FinProcedimiento


    Proceso
        Abrir E/(arch_mae);
        Abrir E/(arch_mov);
        Abrir /S(arch_sal);
        Leer_Mae();
        Leer_Mov();

        cantidad_baja:=0
        monto_total_reintegro:=0
        tot_gen:=0
        tot_simple:=0
        tot_plata:=0
        tot_oro:=0
        tot_diamante:=0

        Mientras (reg_mae.nro_cliente<> HV ) o (reg_mov.nro_cliente <> HV) hacer
            Si reg_mae.nro_cliente < reg_mov.nro_cliente entonces
                reg_sal:=reg_mae
                Grabar(arch_sal,reg_sal);
                Leer_Mae();
            Sino
                Si reg_mae.nro_cliente = reg_mov.nro_cliente entonces
                    aux:=reg_mae
                    Modificacion();
                    Leer_Mov();
                    reg_sal:=aux
                    Grabar(arch_sal,reg_sal);
                    Leer_Mae();
                Sino
                    Si reg_mov.Nro_novedad = 0 entonces
                        aux.nro_cliente:=reg_mov.,nro_cliente;
                        aux.apellido_nombre:=reg_mov.apellido_nombre;
                        aux.DNI:=reg_mov.DNI;
                        aux.id_paquete:=reg_mov.id_paquete;
                        aux.saldo:=reg_mov.monto;
                        aux.Estado:="DEUDOR";
                        aux.categoria:="SIMPLE";
                        aux.Puntos:=0;
                        aux.Fecha_baja:=" ";

                        Leer_mov();
                        Modificacion();
                        reg_sal:=aux
                        Grabar(arch_sal,reg_sal);
                    Sino
                        Esc("Error, no existe en el maestro");
        FinMientras

        Esc("El porcentaje para la categoria SIMPLE es de:", (tot_simple/tot_gen)*100);
        Esc("El porcentaje para la categoria PLATA es de:", (tot_plata/tot_gen)*100);
        Esc("El porcentaje para la categoria ORO es de:", (tot_oro/tot_gen)*100);
        Esc("El porcentaje para la categoria DIAMANTE es de:", (tot_diamante/tot_gen)*100);

        Esc("La cantidad de clientes dado de baja es de:", cantidad_baja);
        Esc("El monto total de reintegro de la empresa es de:$",monto_total_reintegro);

        CERRAR(arch_mae);
        CERRAR(arch_mov);
        CERRAR(arch_sal);
FinAccion
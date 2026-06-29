Accion MercadoLibre es
    Ambiente
        VENTAS=REGISTRO
            CLAVE=REGISTRO
                nu_tran:N(8);
                nu_cliente:N(5);
                nu_vendedor:N(5);
                Fecha_tr:Fecha;
            FR
            costo_tr:N(7,2);
            Ubicacion:AN(50);
            Puntuacion:N(1);
            Opinion:N(1);
        FR

        arch_mae,arch_sal:Archivo SECUENCIAL de VENTAS ordenado por CLAVE;
        reg_mae,reg_sal,aux:VENTAS;

        TRANSACCIONES=REGISTRO
            CLAVE=REGISTRO
                nu_tran:N(8);
                nu_cliente:N(5);
                nu_vendedor:N(5);
                Fecha_tr:Fecha;
            FR
            costo_tr:N(7,2);
            Ubicacion:AN(50);
            Puntuacion:N(1);
            Opinion:N(1);
        FR
        arch_mov:Archivo SECUENCIAL de TRANSACCIONES ordenado por CLAVE;
        reg_mov:TRANSACCIONES;

        VENDEDORES=REGISTRO
            nu_vendedor:N(5);
            ApYn:AN(50);
            Puntuacion:N(1,1);
            Cant_ventas:N(7);
            Opiniones:N(1,1);
            Tipo_mercadolider:AN(1);
        FR

        arch_ind:Archivo de VENDEDORES indexado por nu_vendedor;
        reg_ind:VENDEDORES;

        Procedimiento Leer_Mae () es
            Leer(arch_mae,reg_mae);
            Si FDA(arch_mae) entonces
                reg_mae.CLAVE:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov () es
            Leer(arch_mov,reg_mov);
            Si FDA(arch_mov) entonces
                reg_mae.CLAVE:=HV
            FinSi
        FinProcedimiento
        promedio_puntuaciones:real
        promedio_opiniones:real
        cant_ventas:entero

        Procedimiento Tipo_mercadolider() es
            Segun reg_ind.cant_ventas hacer
                    <100:reg_ind.Tipo_mercadolider:="S";
                    >100:reg_ind.Tipo_mercadolider:="P";
                    >500: reg_ind.Tipo_mercadolider:="G";
             FinSegun
        FinProcedimiento

    Proceso 
        Abrir E/(arch_mae);
        Abrir E/(arch_mov);
        Abrir /S(arch_sal);
        Abrir E/S(arch_ind);
        Leer_Mae();
        Leer_Mov();

        Mientras (reg_mae.CLAVE<>HV) o (reg_mov.CLAVE<>HV) entonces
            reg_ind.nu_vendedor:=reg_mov.CLAVE.nu_vendedor;
            leer(reg_ind,arch_ind);
            Si EXISTE entonces
                Si reg_mae.CLAVE < reg_mov.CLAVE entonces
                    reg_sal:=reg_mae
                    Grabar(arch_sal,reg_sal);
                    Leer_Mae();
                Sino
                    Si reg_mae.CLAVE = reg_mov.CLAVE entonces
                        aux:=reg_mae
                        total_opiniones:=0
                        total_puntuacion:=0
                        cont:=0
                        Mientras (reg_mov.CLAVE=aux.CLAVE) entonces
                            bandera_baja:=Falso
                            Si (reg_mov.CLAVE.nu_cliente = " ") y (reg_mov.CLAVE.Fecha_tr= " ") entonces
                                bandera_baja:=verdadero
                            Sino
                                total_opiniones:=total_opiniones + reg_mov.Opinion:
                                total_puntuacion:=total_puntuacion + reg_mov.Puntuacion;
                                cont:=cont + 1; //sirve para ventas, puntuciones y opiniones
                            FinSi
                            Leer_Mov()
                        FinMientras
                        Si NO bandera_baja entonces
                                reg_sal:=aux;
                                Grabar(arch_sal,reg_sal);
                                promedio_opiniones:=(total_opiniones/cont);
                                promedio_puntuaciones:=(total_puntuacion/cont);
                                reg_ind.Puntuacion:=(reg_ind.Puntuacion + promedio_puntuaciones)/2;;
                                reg_ind.Opiniones:=(reg_ind.opiniones + promedio_opiniones)/2; //dos promedios y saco el nuevo promedio
                                reg_ind.cant_ventas:=reg_ind.cant_ventas + cont;
                                Tipo_mercadolider();
                                Regrabar(arch_ind,reg_ind);
                        FinSi
                        Leer_Mae();
                    Sino
                        //mae mayor
                        Si (reg_mov.CLAVE.nu_cliente <> " ") y (reg_mov.CLAVE.Fecha_tr <> " ") entonces
                            Mientras (aux.CLAVE=reg_mov.CLAVE) y ((reg_mov.CLAVE.nu_cliente <> " ") y (reg_mov.CLAVE.Fecha_tr <> " ")) hacer
                                //actualizo VENTAS
                                aux.CLAVE.nu_tran:=reg_mov.CLAVE.nu_tran;
                                aux.CLAVE.nu_cliente:=reg_mov.CLAVE.nu_cliente;
                                aux.CLAVE.nu_vendedor:=reg_mov.CLAVE.nu_vendedor;
                                aux.CLAVE.Fecha_tr:=reg_mov.CLAVE.Fecha_tr;

                                Tipo_mercadolider();
                                bandera_baja:=Falso
                                Si (reg_mov.CLAVE.nu_cliente = " ") y (reg_mov.CLAVE.Fecha_tr= " ") entonces
                                    bandera_baja:=verdadero
                                FinSi
                            FinMientras
                            Si NO bandera_baja entonces
                                Grabar
                        Sino
                            Esc("Error, no se puede dar de baja algo que no existe");
                        

            Sino
                Esc("Error");
        FinMientras
        
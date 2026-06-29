Accion ACTUALIZACION FIGURITAS TEMA A es
    Ambiente
        ALBUM=REGISTRO
            Cod_figurita:N(5);
            Cantidad:N(2);
            Permite_duplicados:("SI","NO");
        FR

        arch_mae, arch_sal:Archivo secuencial de ALBUM ordenado por Cod_figurita;
        reg_mae,reg_sal,aux:ALBUM;

        INTERCAMBIOS=REGISTRO
            Cod_figurita:N(5);
            COD_amigo:N(5);
            Fecha_solicitud:Fecha;
        FR

        arch_mov:Archivo SECUENCIAL de INTERCAMBIOS ordenado por Cod_figurita,COD_amigo;
        reg_mov:INTERCAMBIOS;

        Procedimiento Leer_Mae () es
            Leer(arch_mae,reg_mae);
            Si FDA(arch_mae) entonces
                reg_mae.Cod_figurita:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov () es
            Leer(arch_mov,reg_mov);
            Si FDA(arch_mov) entonces
                reg_mae.Cod_figurita:=HV
            FinSi
        FinProcedimiento

    Proceso
    Abrir E/(arch_mae);
    Abrir E/(arch_mov);
    Leer_Mae();
    Leer_Mov();
    Abrir /S(arch_sal);

    Mientras (reg_mae.Cod_figurita<>HV) o (reg_mov.Cod_figurita<>HV) hacer
        Si reg_mae.Cod_figurita < reg_mov.Cod_figurita entonces
            reg_sal:=reg_mae
            Grabar(arch_sal,reg_sal);
            Leer_Mae();
        Sino 
            Si reg_mae.Cod_figurita=reg_mov.Cod_figurita entonces
                aux:=reg_mae
                Mientras (aux.Cod_figurita=reg_mae.Cod_figurita) hacer
                    Si aux.Permite_duplicados="SI" entonces
                        aux.cantidad:=aux.cantidad + 1;
                    Sino
                        Esc("No se permiten duplicados");
                    FinSi
                    Leer_Mov();
                FinMientras
                reg_sal:=aux
                Grabar(arch_sal,reg_sal);
                Leer_Mae();
            Sino
                //mae mayor es un alta 
                Si diff_fecha(reg_mov.Fecha_solicitud,FechaActual()) < 7 Entonces
                    aux.Cod_figurita:=reg_mov.Cod_figurita;
                    aux.cantidad:=1;
                    aux.Permite_duplicados:="NO";
                    Leer_mov();
                    Mientras (aux.Cod_figurita=reg_mov.Cod_figurita) hacer
                        Si aux.Permite_duplicados="SI" entonces
                            aux.cantidad:=aux.cantidad + 1;
                        Sino
                            Esc("No se permiten duplicados");
                        FinSi
                        Leer_Mov();
                    FinMientras
                    reg_sal:=aux
                    Grabar(arch_sal,reg_sal);
                Sino
                    Esc("Error, la fecha expiró");
                    Leer_mov()
                FinSi
    FinMientras

    CERRAR(arch_mae);
    CERRAR(arch_mov);
FinAccion
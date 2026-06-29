Accion METEREOLOGICO (A:arreglo [1...25] de alfanumericos) es
    Ambiente
        PRECIPITACIONES_ANUALES=REGISTRO
            PluviometroId:N(4);
            FechaRegistro:fecha;
            Precipitaciones:N(3);
            EstadoPluviometro:("AC","MA");
        FR

        arch_mae,arch_sal:Archivo SECUENCIAL de PRECIPITACIONES_ANUALES ordenado por PluviometroId y FechaRegistro;
        reg_mae,reg_sal,aux:PRECIPITACIONES_ANUALES;

        MEDICIONES=REGISTRO
            PluviometroId:N(4);
            FechaRegistro:fecha;
            HoraRegistro:0...23;
            Precipitaciones:N(3);
            EstadoPluviometro:("AC","MA");
        FR

        arch_mov:Archivo de MEDICIONES ordenado por PluviometroId,FechaRegistro;
        reg_mov:MEDICIONES;

        PLUVIOMETROS=REGISTRO
            PluviometroId:N(4);
            Modelo:N(4);
            Descripcion:AN(50);
            Departamento:1...25;
        FR

        arch_ind:Archivo de PLUVIOMETROS indexado por PluviometroId;
        reg_ind:PLUVIOMETROS;

        Procedimiento Leer_Mae () es
            Leer(arch_mae,reg_mae);
            Si FDA(arch_mae) entonces
                reg_mae.PluviometroId:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov () es
            Leer(arch_mov,reg_mov);
            Si FDA(arch_mov) entonces
                reg_mae.PluviometroId:=HV
            FinSi
        FinProcedimiento

        Procedimiento Modificacion() es
            Mientras (aux.PluviometroId=reg_mov.PluviometroId) hacer
                            aux.FechaRegistro:=reg_mov.FechaRegistro;
                            aux.Precipitaciones:=aux.Precipitaciones+ reg_mov.Precipitaciones;
                            aux.EstadoPluviometro:=reg_mov.EstadoPluviometro;
                        Si reg_mov.EstadoPluviometro="MA" entonces
                            cant_plv_mant:=cant_plv_mant + 1;
                            reg_ind.PluviometroId:=reg_mov.PluviometroId;
                            Leer(arch_ind,reg_ind);
                            Si EXISTE Entonces
                                Esc("ID PLUVIOMETRO:",reg_ind.PluviometroId);
                                Esc("Nombre del Departamento:",A[reg_ind.Departamento]);
                            Sino
                                Esc("Error, no existe el pluviometro");
                            FinSi
                        FinSi
                        Leer_Mov();
                    FinMientras
        FinProcedimiento

        cant_plv_mant:entero

    Proceso
        Abrir E/(arch_mae);
        Abrir E/(arch_mov);
        Abrir /S(arch_sal);
        Leer_mae();
        Leer_Mov();

        Mientras (reg_mae.PluviometroId <> HV) o (reg_mov.PluviometroId <> HV) hacer
            Si reg_mae.PluviometroId < reg_mov.PluviometroId entonces
                reg_sal:=reg_mae
                Grabar(arch_sal,reg_sal);
                Leer_Mae();
            Sino
                Si reg_mae.PluviometroId = reg_mov.PluviometroId entonces
                    aux:=reg_mae
                    Modificacion();
                    reg_sal:=aux
                    Grabar(arch_sal,reg_sal);
                    Leer_Mae();
                Sino
                    //mae mayor
                    Si reg_mov.EstadoPluviometro="AC" entonces
                        aux.PluviometroId:=reg_mov.PluviometroId;
                        aux.FechaRegistro:=reg_mov.FechaRegistro;
                        aux.EstadoPluviometro:=reg_mov.EstadoPluviometro;
                        aux.Precipitaciones:=reg_mov.Precipitaciones;
                        Leer_Mov();
                        Modificacion();
                        reg_sal:=aux
                        Grabar(arch_sal,reg_sal);
                    Sino
                        Esc("Error, el pluviometro está en mantenimiento");
                        Leer_Mov();
                    FinSi
        FinMientras

        Esc("La cantidad de pluviometros en mantenimiento");
        CERRAR(arch_mae);
        CERRAR(arch_mov);
        CERRAR(arch_ind);

FinAccion
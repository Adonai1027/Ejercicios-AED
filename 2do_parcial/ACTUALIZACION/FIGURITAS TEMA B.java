Accion FIGURITAS TEMA B es
    Ambiente
        Fecha=Registro
            aa:n(4);
            mm:1...12;
            dd:1...31;
        FR

        ALBUM=Registro
            Cod_figurita:N(5);
            Cantidad:N(2);
            Permite_duplicados: ("SI","NO");
        FR
        arch_mae,arch_sal: Archivo SECUENCIAL DE ALBUM ordenado por Cod_figurita;
        reg_mae,reg_sal,aux:ALBUM;

        INTERCAMBIOS=Registro
            Cod_figurita: N(5);
            Cod_amigo:N(5);
            Fecha_solicitud:Fecha;
        FR
        arch_mov:Archivo Secuencial de INTERCAMBIOS ordenado por Cod_figurita, Cod_amigo;
        reg_mov:INTERCAMBIOS;

        Procedimiento Leer_Mae() es
            Leer(arch_mae,reg_mae);
            SI FDA(arch_mae) entonces
                reg_album.Cod_figurita:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov() es
            Leer(arch_mov,reg_mov);
            SI FDA(arch_mov) entonces
                reg_mov.Cod_figurita:=HV
            FinSi
        FinProcedimiento

        cant_duplicadas:entero;

        Proceso
            Abrir E(arch_mae);
            Abrir E/(arch_mov);
            Abrir /S(arch_sal);
            Leer_Mae();
            Leer_Mov();
            cant_duplicadas:=0

            Mientras (reg_mae.Cod_figurita <> HV) o (reg_mov.Cod_figurita <> HV ) hacer
                Si reg_mae.Cod_figurita < reg_mov.Cod_figurita entonces
                    reg_sal:=reg_mae;
                    Grabar(arch_sal,reg_sal);
                    Leer_Mae();
                Sino
                    Si reg_mae.Cod_figurita = reg_mov.Cod_figurita entonces
                        aux:=reg_mae
                        Mientras aux.Cod_figurita=reg_mov.Cod_figurita hacer
                            Si (aux.Permite_duplicados="SI") entonces
                                aux.Cantidad:=aux.Cantidad + 1;
                                cant_duplicadas:=cant_duplicadas + 1;
                            FinSi
                            Leer_Mov();
                        FinMientras
                        reg_sal:=aux;
                        Grabar(arch_sal, reg_sal);
                        Leer_Mae();
                    Sino
                            Si diff_horas(reg_mov.Fecha_solicitud,7)=FALSO entonces 
                                aux.Cod_figurita:=reg_mov.Cod_figurita;
                                aux.Cantidad:=1;
                                aux.Permite_duplicados:="NO";
                                Leer_Mov();
                                Mientras (aux.cod_figurita=reg_mov.cod_figurita) hacer
                                     Si (aux.permitir_duplicados = 'si') Entonces
                                        figuritas_duplicadas:= figuritas_duplicadas + 1
                                        aux.cantidad:= aux.cantidad + 1
                                    FinSi
                                    Leer_Mov();
                                FinMientras
                                reg_sal:=aux
                                Grabar(arch_sal,reg_sal);
                            Sino
                                Esc("Error, la fecha expiró");
                                Leer_Mov();
                            FinSi
                        FinSi
            FinMientras

            Esc("La cantidad de figuritas duplicadas admitidas fue de:", cant_duplicadas,"figuritas");
            CERRAR(arch_mae);
            CERRAR(arch_sal);
            CERRAR(arch_mov);

FinAccion



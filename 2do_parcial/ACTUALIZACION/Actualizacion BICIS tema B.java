Accion (A:arreglo[1...6,1..2] de enteros) es
    Ambiente
        BICICLETAS=Registro
            Clave=Registro
                Nro_serie:N(3);
                Modelo:N(4);
            FR
            Fecha_adquisicion:Fecha;
            Fecha_ult_mant:Fecha;
        FR

        arch_mae, arch_sal:Archivo de BICICLETAS ordenado por Clave;
        reg_mae,reg_sal,aux:BICICLETAS;

        NOVEDADES=Registro
            Clave=Registro
                Nro_serie:N(3);
                Modelo:N(4);
                Tipo_nov:1...4;
            FR
            Fecha_novedad: fecha;
            hora_inicio:0...23;
            hora_fin:0...23;
            circuito_nro: 1...6;
            id_usuario: N(5);
        FR
        total_prestamos,circuito_usuario,tot_recaudado:entero;
    
        arch_mov:Archivo Secuencial de NOVEDADES ordenado por Clave;
        reg_mov:NOVEDADES;

        Procedimiento Leer_Mae () es
            Leer(arch_mae,reg_mae);
            Si FDA(arch_mae) entonces
                reg_mae.Clave:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov () es
            Leer(arch_mov,reg_mov);
            Si FDA(arch_mov) entonces
                reg_mae.Clave:=HV
            FinSi
        FinProcedimiento

        Procedimiento Prestamo() es
            Si reg_mov.circuito_nro = circuito_usuario entonces
                total_prestamos:=total_prestamos + 1;
                i:=circuito_usuario
                Para j:=1 a 2 hacer
                    tot_recaudado:=tot_recaudado + A[i,j];
                FinPara
            FinSi
        FinProcedimiento

        Procedimiento Modif() es
            Mientras (aux.clave=reg_mov.clave) hacer
                Segun reg_mov.Tipo_nov hacer
                    =1:Esc("Error,alta inválida");
                    =2:Prestamo();
                    =3:aux.Fecha_ult_mant:=reg_mov.Fecha_ult_mant;
                    =4:bandera_baja:=verdadero
                FinSegun
                Leer_Mov();
            FinMientras
        FinProcedimiento

        bandera_baja:booleano
    Proceso
        Abrir E/(arch_mae);
        Abrir E/(arch_mov);
        Abrir /S(arch_sal);
        Leer_Mae();
        Leer_Mov();
        total_prestamos:=0
        tot_recaudado:=0
        Esc("Ingrese circuito:");
        Leer(circuito_usu);
        Mientras (reg_mae.CLAVE <> HV)O (reg_mov.CLAVE<> HV) entonces
            Si reg_mae.clave < reg_mov.clave entonces
                reg_sal:=reg_mae
                Grabar(arch_sal,reg_sal);
                Leer_mae();
            Sino
                Si reg_mae.clave = reg_mov.clave entonces
                    aux:=reg_mae
                    bandera_baja:=Falso
                    Modif();
                    Si no bandera_baja entonces //baja fisica
                        reg_sal:=aux
                        Grabar(arch_sal,reg_sal);
                    FinSi
                    Leer_Mae();
                Sino
                    //mae mayor (alta)
                    Si reg_mov.Tipo_nov=1 entonces
                        aux.clave.Nro_serie:=reg_mov.clave.Nro_serie;
                        aux.clave.modelo:=reg_mov.clave.modelo;
                        aux.Fecha_adquisicion:=reg_mov.Fecha_novedad;
                        aux.Fecha_ult_mant:="";
                        Leer_Mov();
                        bandera_baja:=Falso
                        Modif();
                        Si no bandera_baja entonces //baja fisica
                            reg_sal:=aux
                            Grabar(arch_sal,reg_sal);
                        FinSi
                    Sino
                        Esc("Baja,préstamo o modificacion no posible");
                    FinSi
                    Leer_Mov();
                FinSi
        FinMientras
        Esc("El total de prestamos para el circuito:",circuito_usuario,"es de :",total_prestamos,
        "con un monto total de:",tot_recaudado);

        CERRAR(arch_mae);
        CERRAR(arch_mov);
        CERRAR(arch_sal);
FinAccion
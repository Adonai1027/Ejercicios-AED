Accion 2023 tema A es
    Ambiente
        BICICLETAS=Registro
            Clave=Registro
                Nro_serie:N(3);
                Modelo:N(4);
            FR
            Fecha_adquisicion:Fecha;
            Fecha_ult_mant:Fecha;
            Disponibilidad:booleano
        FR

        arch_mae, arch_sal:Archivo de BICICLETAS ordenado por Clave;
        reg_mae,reg_sal,aux:BICICLETAS;

        NOVEDADES=Registro
            Clave=Registro
                Nro_serie:N(3);
                Modelo:N(4);
                Tipo_nov:1...3;
            FR
            Fecha_novedad: fecha;
            hora_inicio:0...23;
            hora_fin:0...23;
            circuito_nro: 1...6;
            id_usuario: N(5);
        FR

        arch_mov:Archivo Secuencial de NOVEDADES ordenado por Clave;
        reg_mov:NOVEDADES;

        USUARIOS=Registro
            Id_usuario:N(5);
            DNI:N(8);
            Sexo:("M","F");
            ApyN:AN(50);
            Domicilio:AN(40);
            Localidad:AN(30);
            Provincia:AN(30);
            Edad:N(2);
        FR

        arch_usuarios:Archivo de USUARIOS indexado por id_usuario;
        reg_usu:USUARIOS;

        totmujeres, tothombres:entero

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
            reg_usu.id_usuario:=reg_nov.id_usuario
            leer(arch_usuarios,reg_usu);
            Si EXISTE entonces
                Si reg_nov.Tipo_nov=2 entonces
                    Si reg_usu.Sexo="M" entonces
                        tothombres:=tot_hombres + 1;
                    Sino
                        totmujeres:=totmujeres + 1;
                    FinSi
                FinSi
            Sino
                Esc("El usuario no contrató el servicio");
            FinSi
        FinProcedimiento


        Procedimiento Mantenimiento() es
            aux.Disponibilidad:=FALSO
            aux.Fecha_ult_mant:=reg_nov.Fecha_novedad;
        FinProcedimiento

        Procedimiento Alta() es
            aux.Nro_serie:=reg_nov.Nro_serie;
            aux.modelo:=reg_nov.modelo;
            aux.Fecha_adquisicion:=reg_nov.Fecha_novedad;
            aux.Fecha_ult_mant:=" ";
            aux.Disponibilidad:=Verdadero; 
        FinProcedimiento

        Proceso
            Abrir E/(arch_mae);
            Abrir E/(arch_nov);
            Abrir /S(arch_sal);
            Abrir E/(arch_usuarios);
            Leer_Mae();
            Leer_Mov();

            tothombres:=0
            totmujeres:=0

            Mientras (reg_mae.clave <> HV) o (reg_mov.clave<>HV) hacer
                Si reg_mae.clave < reg_mov.clave entonces
                    reg_sal:=reg_mae
                    Grabar(arch_sal,reg_sal);
                    Leer_Mae();
                Sino
                    Si reg_mae.clave = reg_mov.clave entonces
                        aux:=reg_mae
                        Mientras reg_nov.clave=aux.clave hacer
                            Según reg_nov.Tipo_nov hacer
                                =1:Esc("Error, alta invalida");
                                =2:Prestamo();
                                =3:Mantenimiento();
                            FinSegun
                            Leer_Mov();
                        FinMientras
                        reg_sal:=aux
                        Grabar(arch_sal,reg_sal);
                        Leer_Mae();
                    Sino
                        Segun reg_nov.Tipo_nov hacer
                            =1: Alta();
                                Leer_Mov();
                                Mientras (aux.clave=reg_nov.clave) hacer
                                    Según reg_nov.Tipo_nov hacer
                                        =1:Esc("Error, alta invalida");
                                        =2:Prestamo();
                                        =3:Mantenimiento();
                                    FinSegun
                                    Leer_Mov();     
                                FinMientras
                                reg_sal:=aux
                                Grabar(arch_sal,reg_sal);
                            =2:Esc("Error, préstamo inválido");  //primero prestamo es un error
                            =3:Esc("Error, mantenimiento inválido"); //primero mantenimiento es un error
                        FinSegun
                        Leer_Mov();
            FinMientras

            Esc("El total de préstamos a mujeres es:",totmujeres,"y a hombres es de:",tothombres);
            CERRAR(arch_mae);
            CERRAR(arch_nov);
            CERRAR(arch_usuarios);
FinAccion


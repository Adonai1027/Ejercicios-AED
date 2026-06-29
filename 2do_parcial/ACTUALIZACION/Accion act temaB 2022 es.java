Accion act temaB 2022 es
    Ambiente
        Fecha=Registro
            AA:N(4);
            MM:1...12;
            DD:1...31;
        FR
        BICICLETAS=Registro
            Nro_serie: N(5);
            modelo: N(4);
            Fecha_ad: Fecha
            fecha_ult_mant:Fecha;
            Disponibilidad:booleano;
        FR

        arch_mae, arch_sal: Archivo Secuencial de BICICLETAS ordenado por Nro_serie y modelo;
        mae, aux, sal: BICICLETAS;

        NOVEDADES=Registro
            Nro_serie:N(5);
            Modelo:N(4);
            Tipo_nov: 1..3;
            Fecha_nov:Fecha;
            Hora_inicio:0...23;
            Hora_fin:0...23;
            Circuito_nro:1...6;
            Id_usuario:N(6);
        FR

        arch_mov:Archivo Secuencial de NOVEDADES ordenado por Nro_serie, modelo, Tipo_nov y Fecha_nov;
        mov:NOVEDADES;

        USUARIOS=Registro
            Id_usuario:N(6);
            DNI:N(8);
            Sexo:("M","F");
            AyN: AN(30);
            Domicilio:AN(50);
            Localidad: AN(20);
            Provincia: AN(30);
            Edad: N(2);
        FR

        arch_usu:Archivo de USUARIOS INDEXADO por Id_usuario;
        usu:USUARIOS;
        cant_prestamos: entero;
        circuito_usuario:1...6;

        Procedimiento Leer_Mae() es
            Leer(arch_mae,mae);
            Si FDA (arch_mae) entonces
                mae.Nro_serie:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov() es
            Leer(arch_mov,mov);
            Si FDA (arch_mov) entonces
                mov.Nro_serie:=HV
            FinSi
        FinProcedimiento

        Procedimiento Prestamo() es
            usu.Id_usuario:=nov.Id_usuario;
            Leer(arch_usu,usu);
            Si EXISTE entonces
                
            Sino
                Esc("Error, el usuario no contrató el servicio");
            FinSi
        FinProcedimiento

        Procedimiento Mantenimiento () es
                aux.Disponibilidad:=FALSO;
                aux.fecha_ult_mant:= nov.Fecha_no
        FinProcedimiento

        Procedimiento LOTES() es
            aux:mae
            Mientras (mae.Nro_serie=nov.Nro_serie) hacer
                Según nov.Tipo_nov hacer
                    ="1": Esc("Error, no se puede dar de alta una bici inexistente");
                    ="2": Prestamo();
                    ="3": Mantenimiento ();
                FinSegun
                Leer_Mov();
            FinMientras
            sal:=aux
            Grabar(arch_sal,sal);
            Leer_Mae();
        FinProcedimiento

        Procedimiento Alta () es
            aux.Nro_serie:=nov.Nro_serie;
            aux.modelo:=nov.modelo;
            aux.Fecha_ad:=nov.Fecha_nov;
            aux.fecha_ult_mant:= null;
            aux.Disponibilidad:=Verdadero
            Grabar(arch_sal,reg_sal)
            Leer_Mov();
        FinProcedimiento

        Proceso
            Abrir E/(arch_mae);
            Abrir E/(arch_mov);
            Abrir /S(arch_sal);
            Abrir E/(arch_usu);
            Leer_Mae();
            Leer_Mov();
            cont_fem:=0
            cont_masc:=0
            
            Mientras (mae.Nro_serie <> HV) ^ (nov.Nro_serie <> HV) hacer
                Si mae.Nro_serie < nov.Nro_serie entonces
                    sal:=mae
                    Grabar(arch_sal,sal);
                    Leer_Mae();
                Sino
                    Si mae.Nro_serie = nov.Nro_serie entonces
                        Segun nov.Tipo_nov hacer
                            ="1": ESC("ERROR, no se peude dar de alta porque ya existe");
                            ="2": LOTES();
                            ="3": LOTES();
                        FinSegun
                    Sino
                        //mae>mov

                        Segun nov.Tipo_nov hacer
                            ="1": Alta(), LOTES();
                            ="2": ESC("Error, no puede existir un prestamo de algo que no existe");
                            ="3": ESC("Error, no puede existir mantenimiento ni prestamo de algo inexistente");
                        FinSegun
            FinMientras
            
            Esc("El total de préstamos realizados a mujeres es:", cont_fem);
            Esc("El total de préstamos realizadoas a hombres es:",cont_masc);
            CERRAR(arch_mae);
            CERRAR(arch_mov);
            CERRAR(arch_sal);
            CERRAR(arch_usu);
FinAccion
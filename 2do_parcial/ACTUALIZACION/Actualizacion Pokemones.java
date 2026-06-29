Accion (A:arreglo [1...151] de alfanumerico) es
    Ambiente
        CAPTURAS=Registro
            Clave=Registro
                Cod_Region:N(4);
                Cod_Usuario:N(10);
            FR
            Cod_pokemon: 1...151;
            Puntos_de_exp:N(5);
            Fecha_captura:Fecha;
            Estado_pok:("E","I","D");
            Estado_Usuario:("A","S","B");
        FR

        arch_mov:Archivo SECUENCIAL de CAPTURAS Clave;
        reg_mov:CAPTURAS

        USUARIOS=Registro
            Clave=Registro
                Cod_Region:N(4);
                Cod_Usuario:N(10);
            FR
            Correo:AN(50);
            Experiencia:N(7);
        FR

        arch_mae,arch_sal:Archivo Secuencial de USUARIOS ordenado por Cod_Region y Cod_Usuario;
        reg_mae,reg_sal,aux:USUARIOS;
        P:arreglo[1...151] de enteros
        i,cant_usuarios,mayor:entero
        bandera_baja:booleano
        nombre_pok:alfanumerico

        Procedimiento Exp()es
            Según reg_mov.Estado_pok hacer
                ="E":= aux.Experiencia:=aux.Experiencia + reg_mov.Puntos_de_exp*2;
                ="I":=aux.Experiencia:=aux.Experiencia + reg_mov.Puntos_de_exp;
                ="D":=aux.Experiencia:=aux.Experiencia + reg_mov.Puntos_de_exp;
                cant_usuarios:=cant_usuarios + 1;
                P[reg_mov.Cod_pokemon]:=P[reg_mov.Cod_pokemon] + 1;
            FinSegun
        FinProcedimiento

        Procedimiento Baja()es
            Si reg_mov.Estado_Usuario="S" entonces
                bandera_baja:=Verdadero
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mae() es
            Leer(arch_mae,reg_mae);
            SI FDA(arch_mae) entonces
                reg_mae.Clave:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov() es
            Leer(arch_mov,reg_mov);
            SI FDA(arch_mov) entonces
                reg_mov.Clave:=HV
            FinSi
        FinProcedimiento

    Proceso
        Abrir E/(arch_mov);
        Abrir E/(arch_mae);
        Abrir /S (arch_sal);
        Leer_Mae();
        Leer_Mov();

        cant_usuarios:=0
        Mientras (reg_mae.clave <> HV) o (reg_mov.clave<>HV) hacer
            Si reg_mae.clave < reg_mov.clave
                reg_sal:=reg_mae
                Grabar(arch_sal,reg_sal);
                Leer_Mae();
            Sino
                Si reg_mae.clave=reg_mov.Clave entonces
                    aux:=reg_mae
                    bandera_baja:=FALSO
                    Mientras (aux.clave=reg_mov.clave) hacer
                        Baja();
                        Exp();
                        Leer_Mov();
                    FinMientras
                    Si no bandera_baja entonces
                        reg_sal:=aux
                        Grabar(arch_sal,reg_sal);
                    FinSi
                    Leer_Mae();
                Sino
                    //mov mayor es un alta
                    Si reg_mov.Estado_Usuario = "A" entonces
                        aux.Cod_Region:=reg_mov.Cod_Region;
                        aux.Cod_Usuario:=reg_mov.Cod_Usuario;
                        aux.Correo:=" ";
                        Exp();
                        Leer_Mov();
                        bandera_baja:=FALSO
                        Mientras (aux.clave=reg_mov.clave) hacer
                            Baja();
                            Exp();
                            Leer_Mov();
                        FinMientras
                        Si no bandera_baja entonces
                            reg_sal:=aux
                            Grabar(arch_sal,reg_sal);
                        FinSi
                    Sino   
                        Esc("Error, no se permite actualizacion");
                        Leer_Mov();
                    FinSi
            FinSi
        FinMientras

        mayor:=LV
        Para i:=1 a 151 hacer
            Si P[i] > mayor entonces
                mayor:=P[i];
                nombre_pok:=i;
            FinSi
        FinPara

        Esc("El nombre del Pokemon en estado descansando con mayor cantidad de usuarios es:",A[nombre_pok]
        "Con un total de:",cant_usuarios,"usuarios");

        CERRAR(arch_mae);
        CERRAR(arch_mov);
FinAccion



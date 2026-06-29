Accion (P:arreglo[1...6,1...2] de enteros) es
    Ambiente
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
        arch_ind:Archivo de USUARIOS indexado por id_usuario;
        reg_ind:USUARIOS;

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

        arch_nov:Archivo Secuencial de NOVEDADES ordenado por Clave;
        reg_nov:NOVEDADES;

        A:arreglo [1...4,1...2] de Enteros;
        i,j:entero
        tipo_paseo:entero
        etario_max, resg_etario:entero

    Proceso
        Abrir E/(arch_nov);
        Abrir E/(arch_ind);
        Para i:=1 a 4 hacer
            Para i:=1 a 2 hacer
                A[i,j]:=0
            FinPara
        FinPara

        Mientras NFDA(arch_nov) hacer
            reg_ind.id_usuario:=reg_nov.id_usuario
            leer(arch_ind,reg_ind);
            Si EXISTE entonces
                tipo_paseo:=diff_horas(reg_ind.hora_inicio,reg_ind.hora_fin);
                Si tipo_paseo > 6 entonces
                    j:=1
                Sino
                    j:=2
                FinSi

                i:=reg_ind.Edad
                Según i hacer
                    <18: i:=1
                    >18: i:=2
                    >35: i:=3
                    >75: i:=4   
                FinSegun

                A[i,j]:=A[i,j] + 1;
            Sino
                ESC("No se puede realizar préstamo");
            FinSi
        FinMientras

        etario_max:=LV
        Para i:=1 a 4 hacer
            Esc("Para el grupo etario:",i);
            Para j:=1 a 2 hacer
                Esc("Tipo de Paseo:",j,"El total de prestamos es:",A[i,j]);
                Si j=2 entonces
                    Si A[i,2] > etario_max entonces //consigna B
                        etario_max:=A[i,2];
                        resg_etario:=i
                    FinSi
                FinSi
            FinPara
        FinPara

        Esc("El rango etario que mas paseos recreativos realizó es:",resg_etario);
        Esc("El total de prestamos tipo intensivo, rango mayores de 75 es de:",A[4,1]);

        CERRAR(arch_nov);
        CERRAR(arch_ind);

FinAccion




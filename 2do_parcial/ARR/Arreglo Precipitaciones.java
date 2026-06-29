Accion ARREGLO PRECIPITACIONES (X:arreglo [1...25] de alfanumericos) es
    Ambiente
        PRECIPITACIONES_ANUALES=REGISTRO
            PluviometroId:N(4);
            FechaRegistro:fecha;
            Precipitaciones:N(3);
            EstadoPluviometro:("AC","MA");
        FR
        arch_prec:Archivo de PRECIPITACIONES_ANUALES ordenado por PluviometroId;
        reg_prec:PRECIPITACIONES_ANUALES

        PLUVIOMETROS=REGISTRO
            PluviometroId:N(4);
            Modelo:N(4);
            Descripcion:AN(50);
            Departamento:1...25;
        FR

        arch_ind:Archivo de PLUVIOMETROS indexado por PluviometroId;
        reg_ind:PLUVIOMETROS;

        A:arreglo[1..26,1...12] de enteros
        i,j:entero

        mes_mayor, max:entero //consigna A

        mes_menor:min:entero //consigna C

    Proceso
        Para i:=1 a 26 hacer
            Para j:=1 a 12 hacer
                A[i,j]:=0
            Finpara
        Finpara

        Abrir E/(arch_prec);
        Leer(arch_prec,reg_prec);

        Mientras NFDA(arch_prec) hacer
            reg_ind.PluviometroId:=reg_prec.PluviometroId;
            Leer(arch_ind,reg_ind);
            SI EXISTE entonces
                i:=reg_ind.Departamento;
                j:=reg_prec.FechaRegistro.mm;

                A[i,j]:=A[i,j] + reg_prec.Precipitaciones;
                A[26,j]:=A[26,j] + reg_prec.Precipitaciones;: //consigna A
            Sino
                Esc("Error");
            FinSi
            Leer(arch_prec,reg_prec);
        FinMientras

        max:=LV
        min:=HV
        Para i:=1 a 25 hacer //recorro deptos
            Para j:=1 a 12 hacer //recorro meses
                Si A[i,j] > 350 entonces
                    Esc("El departamento numero:",i,"Nombre:",X[i],"Mes:",j,"superó los 350 mm caídos");
                FinSi
                Si A[26,j] > max entonces //consigna A
                    max:=A[26,j];
                    mes_mayor:=j
                FinSi
                Si A[i,j] < min entonces //consigna C
                    min:A[i,j];
                    mes_menor:=j;
                FinSi
            Finpara
        Finpara

        CERRAR(arch_prec);
        CERRAR(arch_ind);

FinAccion




Accion (M:arreglo[1..10] de alfanumerico) es
    Ambiente
        VIAJES=REGISTRO
            Fecha:fecha;
            Origen:N(1);
            Destino:N(1);
            Millas_compradas:N(10);
        FR
        Formato=Registro
            Millas_compradas:N(20);
            Cant_compras:N(3);
        FR

        arch_viajes:Archivo de VIAJES;
        reg_viajes:VIAJES

        A:arreglo[1..11,1..4] de Formato;
        i,j:entero
        resg_cuatri_millas,min_cuatri_millas:entero //consigna A
        resg_origen_millas, max_origen_millas:entero //consigna B
        resg_origen_compras,resg_cuatri_compras, min:entero //consigna C

    Proceso
        Abrir E/(arch_viajes);
        Leer(arch_viajes,reg_viajes);

        Para i:=1 a 11 hacer
            Para j:=1 a 5 hacer
                A[i,j].Cant_compras:=0
                A[i,j].Millas_compradas:=0
            FinPara
        FinPara

        Mientras NFDA(arch_viajes) hacer
            i:=reg_viajes.Destino;
            Segun reg_viajes.fecha.mm hacer
                <5: j:=1
                <9: j:=2
                <=12: j:=3
            FinSegun
            A[i,j].Cant_compras:=A[i,1].Cant_compras + 1; //CONSIGNA C
            A[11,j].Millas_compradas:=A[11,1].Millas_compradas + reg_viajes.Millas_compradas; //consigna A
            A[i,4].Millas_compradas:=A[i,4].Millas_compradas + reg_viajes.Millas_compradas;

            Leer(arch_viajes,reg_viajes);
        FinMientras
        
        min_cuatri_millas:=HV
        min:=HV
        max_origen_millas:=LV

        Para i:=1 a 10 hacer
            Para j:=1 a 3 hacer
                Si A[11,j] < min_cuatri_millas entonces
                    min_cuatri_millas:=A[11,j];
                    resg_cuatri_millas:=j; //consigna A
                FinSi

                Si A[i,j] < min entonces //consigna C
                    min:=A[i,j];
                    resg_origen_compras:=i;
                    resg_cuatri_compras:=j;
                FinSi
            FinPara
            Si A[i,4] >  max_origen_millas entonces //consigna B
                max_origen_millas:=A[i,4];
                resg_origen_millas:=i;
            FinSi
        FinPara


        Esc("Cuatrimestre con menor cantidad de millas compradas es el:",resg_cuatri_millas,"con un total de:",min_cuatri_millas);
        Esc("Origen con mayor cantidad de millas compradas es el:",M[resg_origen_millas],"con un total de:",max_origen_millas);
        Esc("El origen:",M[resg_origen_compras],"Cuatrimestre:",resg_cuatri_compras,"registró la menor cantidad de compras,
        con un total de:",min,"compras");

        CERRAR(arch_viajes);

FinAccion

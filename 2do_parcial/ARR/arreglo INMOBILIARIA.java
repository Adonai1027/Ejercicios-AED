Accion INMOBILIARIA es
    Ambiente
        INMOBILIARIA=REGISTRO
            Departamento:N(6);
            ORIGEN:1...3;
            Nivel:1...5;
            VALIDA:("SI","NO");
        FR

        arch:Archivo de INMOBILIARIA;
        reg:INMOBILIARIA;

        A:arreglo[1...4,1...6] de enteros;
        i,j:entero;

        resg_niv,resg_origen,menor:entero; //a
        mayor, nivel_mayor: entero; //b
        porcentaje:real //d
        origen_menor, menor_trans:entero //e

    Proceso
        Abrir E/(arch);
        Leer(arch,reg);

        Para i:=1 a 4 hacer
            Para j:=1 a 6 hacer
                A[i,j]:=0
            Finpara
        Finpara

        Mientras NFDA(arch) hacer
            Si reg.VALIDA="SI" entonces
                i:=reg.ORIGEN;
                j:=reg.Nivel;

                A[i,j]:=A[i,j] + 1;
                A[4,j]:=A[4,j] + 1; //consigna B
                A[i,6]:=A[i,6] + 1; //consigna E
                A[4,6]:=A[4,6] + 1; //consigna C total
            sino
                Esc("Transaccion inválida");
            FinSi
        FinMientras

        menor:=HV
        mayor:=LV
        menor_trans:=HV

        
            Para j:=1 a  5 hacer //nivel
                Para i:=1 a 3 hacer //origen
                    Si A[i,6] < menor_trans entonces
                        menor_trans:=A[i,6];
                        origen_menor:=i;
                    FinSi
                Finpara
                Si A[i,j] < menor entonces //consigna A
                    menor:=A[i,j];
                    resg_niv:=j;
                    resg_origen:=i;
                FinSi
                Si A[4,j] > mayor entonces //consigna B
                    mayor:=A[4,j];
                    nivel_mayor:=j;
                FinSi
                porcentaje:=(A[4,j]/A[4,6])*100
                Esc("El porcentaje de transacciones del nivel:",j,
                "sobre el total es de:",porcentaje); //consigna C
            Finpara


            Para j:=1 a 5 hacer
                Si A[4,j] > (mayor*1.1) entonces
                    Esc("El nivel:",j,"supera en un 10% al mayor");
            Finpara

            Esc("El nivel:",resg_niv,"origen:",resg_origen,"presentó la menor cantidad de transacciones");
            Esc("El nivel con mayor cantidad de trans es:",nivel_mayor);
            Esc("El origen con menor cant transacciones validas es:",origen_menor);

            cerrar(arch);

FinAccion


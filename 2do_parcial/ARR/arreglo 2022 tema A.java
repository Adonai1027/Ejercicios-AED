Accion (P:arreglo[1..6,1..2] de entero) es
    Ambiente
        novedades=Registro

        FR
        Formato=Registro
            Cant_prest:N(3);
            Monto:N(10);
        FR
        A:arreglo[1...7,1...3] de Formato
        i,j:entero
        cant_paseos:entero //consigna d
        nro_circuito_mayor, mayor:entero //consigna b
        tot_usu, tipo_paseo_usu, circuito_usu:entero //consigna c

    Proceso
        //inicializo el arreglo
        Para i:=1 a 7 hacer
            Para j:=1 a 3 hacer
                A[i,j]:=0
            FinPara
        FinPara
        
        //cargo el arreglo
        Mientras NFDA(arch_nov) hacer
            i:=reg_nov.circuito_nro;
            Si reg_nov.Tipo_nov =2 entonces
                dif:= diff_horas(reg_nov.hora_inicio, reg_nov.hora_fin);
                Si dif > 6 entonces
                    j:=1
                     A[i,j].Monto:= A[i,j].monto + 1500 + P[i,j] ;
                     A[i,3].Monto:=A[i,3].monto + 1500 + P[i,j];
                     A[7,j].Monto:=A[7,j].Monto + 1500 + P[i,j]; //total monto por tipo de paseo
                     A[7,3].Monto:=A[7,3].Monto + 1500 + P[i,j];
                Sino
                    j:=2
                    A[i,j].Monto:= A[i,j].monto +1000 + P[i,j] ;
                    A[i,3].Monto:=A[i,3].monto + 1000 + P[i,j];
                    A[7,j].Monto:=A[7,j].Monto + 1000 + P[i,j]; //total monto por tipo de paseo
                    A[7,3].Monto:=A[7,3].Monto + 1000 + P[i,j];
                FinSi
                A[i,j].Cant_prest:= A[i,j].Cant_prest + 1;
                A[i,3].Cant_prest:=A[i,3].Cant_prest + 1; //consigna A //total prestamos por circuito
                A[7,j].Cant_prest:=A[7,j].Cant_prest + 1; //total prestamo por tipo de paseo
                A[7,3].Cant_prest:=A[7,3].Cant_prest + 1;

            FinSi
            Leer(arch_nov,reg_nov);
        FinMientras
        Esc("Ingrese tipo paseo y circuito");
        Leer(tipo_paseo_usu);
        Leer(circuito_usu);
        mayor:=0
        Para i:=1 a 6 hacer
                Esc("Para el circuito:", i);
                Para j:=1 a 2 hacer
                    Esc("Tipo de Paseo:",j,"La cantidad de prestamos es:",A[i,j].Cant_prest,"y monto:",A[i,j].Monto); //CONSIGNA A
                FinPara
                Si mayor < A[i,3] entonces
                    mayor:=A[i,3];
                    nro_circuito_mayor:=i;
                FinSi
        FinPara
        Esc("El circuito con mayor cantidad de paseos es:",nro_circuito_mayor,"con un total de:",mayor);
        Esc("El importe total recaudado es:", A[7,3].Monto, "y la cantidad de paseos realizados es de:",A[7,3].Cant_prest);
        Esc("El total recaudado para el circuito y paseo del usuario es:",A[circuito_usu,tipo_paseo_usu].importe);
        
       CERRAR(arch_nov);

FinAccion 
        

            
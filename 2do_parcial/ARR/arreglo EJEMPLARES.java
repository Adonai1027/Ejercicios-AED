Accion EJEMPLARES es
    Ambiente
        EJEMPLARES=REGISTRO
            ID_EJEMPLAR:N(8);
            ID_LIBRO:AN(30);
            Sucursal:1...5;
            Digital:("SI","NO");
            Disponible:booleano
        FR

        arch_ejem:Archivo de EJEMPLARES ordenado por ID_EJEMPLAR,id-ID_LIBRO;
        reg_ej:EJEMPLARES;

        A:arreglo[1...6,1..3,1..3] de enteros;
        i,j,k:entero

        Funcion Disponible(x:entero):booleano
            Segun x hacer
                =1:=Disponible:=VERDADERO;
                =2:=Disponible:=FALSO;
            FinSegun
        finFuncion

        Funcion DIGITAL(x:entero):alfanumerico
            Segun x hacer
                =1:=DIGITAL:="SI";
                =2:=DIGITAL:="NO";
            FinSegun
        finFuncion

        mayor_suc,resg_suc:entero //consigna 2
    Proceso
        Abrir E/(arch_ejem);
        Leer(arch_ejem,reg_ej);

        Para i:=1 a 6 hacer
            Para j:=1 a 3 hacer
                Para k:=1 a 3 hacer
                    A[i,j,k]:=0
                Finpara
            Finpara
        Finpara

        Mientras NFDA(arch_ejem) hacer
            i:=reg_ej.Sucursal;
            Si reg_ej.Digital="SI" Entonces
                j:=1
            Sino
                j:=2
            FinSi
            Si reg_ej.Disponible=Verdadero entonces
                k:=1
            Sino
                k:=2
            FinSi

            A[i,j,k]:=A[i,j,k] + 1
            A[i,3,k]:=A[i,3,3] + 1;
            A[6,j,3]:=A[6,j,k] + 1;

            Leer(arch_ejem,reg_ej);

        FinMientras
        mayor_suc:=LV
        Para i:=1 a 5 hacer
            Esc("Para la sucursal:",i);
            Para j:=1 a 2 hacer
                Eac("Digital:",DIGITAL(j));
                Para k:=1 a 2 hacer
                        Esc("Cantidad de ejemplares:",Disponible(k),"es de:",A[i,j,k]); //consigna A
                Finpara
            Finpara
            Si A[i,3,1]>mayor_suc entonces //consigna 2
                mayor_suc:=A[i,3,1];
                resg_suc:i;
            FinSi
        FinPara

        Esc("El total de libros digitales es de:",A[6,1,3]); //consigna 3
        Esc("El total de libros fisicos es de:",A[6,2,3]); //consigna 3

        Esc("En la sucursal 2 hay:",A[2,3,3],"ejemplares"); //consigna 4

        CERRAR(arch_ejem);
FinAccion

        
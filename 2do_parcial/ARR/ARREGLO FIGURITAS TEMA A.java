Accion ARREGLO FIGURITAS TEMA A es
    ALBUM=REGISTRO
            Cod_usuario:N(5);
            Cod_figurita:n(5)
            Cantidad:N(2);
            Tipo:("D","C","V");
    FR

    arch_album:Archivo de ALBUM;
    reg_album:ALBUM;

    FIGURITAS=REGISTRO
        Cod_figurita:N(5);
        Jugador_nombre:AN(50);
        Equipo_id:1...32;
        Equipo_nombre: AN(50);
        Altura:N(3);
        Peso:N(3);
        Posicion:N(2);
    FR

    arch_ind:Archivo de FIGURITAS indexado por Cod_figurita;
    reg_ind:FIGURITAS;

    A:arreglo [1..32,1..4] de entero;
    i,j:entero

    equipo_mayor, max:entero //consigna 1
    porcentaje:real // consigna 2

    Funcion ConvertirTipo(x:alfanumerico): entero
        Segun x hacer
            ="D":=ConvertirTipo:=1;
            ="C":=ConvertirTipo:=2;
            ="V":=ConvertirTipo:=3;
        FinSegun
    finFuncion

    Funcion Convertir_ent (x:entero): alfanumerico
        Segun x hacer
            =1:=ConvertirTipo:="D";
            =2:=ConvertirTipo:="C";
            =3:=ConvertirTipo:="V";
        FinSegun
    finFuncion

Proceso
    Abrir E/(arch_album);
    Leer(arch_album);

        //inicializo la matriz
    Para i:=1 a 33 hacer
        Para j:=1 a 4 hacer
            A[i,j]:=0
        Finpara
    Finpara

    //cargo la matriz
    Mientras NFDA(arch_album) hacer
        reg_ind.Cod_figurita:=reg_album.Cod_figurita;
        Leer(arch_ind,reg_ind);
        Si EXISTE entonces
            i:=reg_ind.Equipo_id;
            j:=ConvertirTipo(reg_album.Tipo);
            A[i,j]:=A[i,j] + reg_album.cantidad;
            A[i,4]:=A[i,4] + reg_album.cantidad; //consigna A
            A[33,j];:=A[33,j] + reg_album.Cantidad;
            A[33,4]:=A[33,4] + reg_album.cantidad; //para el porcentaje de cada equipo sobre el total
        Sino
            Esc("Error");
        FinSi
        Leer(arch_album,reg_album);
    FinMientras


    //muestro las consignas correspondientes
    max:=LV
    Para i:=1 a 32 hacer
        Esc("Para el equipo:",i);
        Para j:=1 a 3 hacer
            Esc("Tipo de album:",Convertir_ent(j),"el total de figuritas es de:",A[i,j]);
        Finpara
        Si A[i,4] > max entonces //consigna A
            max:=A[i,4];
            equipo_mayor:=i;
        FinSi
        porcentaje:=(A[i,4]/A[33,4])*100;
        Esc("El porcentaje del equipo es:",porcentaje); //consigna B
    Finpara

    Esc("El codigo del equipo que mas figuritas tiene en su album es:",equipo_mayor,"con una cantidad de:",max);

    CERRAR(arch_album);
    CERRAR(arch_ind);

FinAccion



Accion arreglo 2022 isi C tema B es
    Ambiente
        ALBUM=Registro
            cod_usuario:N(5);
            Cod_figurita:N(5);
            Cantidad:N(2);
            Tipo:{"D","C","V"};
        FR

        AMIGOS=Registro
            cod_usuario:N(5);
            Apellido: AN(20);
            Nombre: AN(20);
            Celular: N(10);
        FR

        arch_album:Archivo de ALBUM ordenado por cod_usuario;
        arch_amigos: Archivo de AMIGOS Indexado por cod_usuario;
        reg_album:ALBUM;
        reg_amigos:AMIGOS;
        cant_mayor, mayor:entero;
        A:arreglo [1...11,1...4] de enteros;
        i,j:entero
        porcentaje:real
    Proceso
        Para i:=1 a 11 hacer
            Para j:=1 a 4 hacer
                A[i,j]:=0
            FinPara
        FinPara

        Mientras NFDA(arch_album) hacer
                i:=reg_album.cod_usuario;
                Segun reg_album.Tipo hacer
                    ="D": j:=1
                    ="C":j:=2
                    ="V":j:=3
                FinSegun

                A[i,j]:=A[i,j] + reg_album.cantidad;
                A[11,j]:=A[11,j] + reg_album.cantidad;
                A[i,4]:=A[i,4] + reg_album.cantidad;
                A[11,4]:=A[11,4] + reg_album.cantidad;

                Leer(arch_album,reg_album);
        FinMientras

        cant_mayor:=LV
        
        Para i:=1 a 10 hacer
            Esc("Para el usuario:",i);
            Para j:=1 a 3 hacer
                ESC("Tipo:", j,"El total es:"A[i,j]);
            FinPara
            Si A[i,4] > cant_mayor entonces
                cant_mayor:=A[i,4];
                mayor:=i;
            FinSi
            porcentaje:=(A[i,4]/A[11,4])*100
            Esc("El porcentaje de figuritas del usuario:",i, "es:",porcentaje);
        FinPara

        reg_amigos.cod_usuario:=mayor
        Leer(arch_amigos,reg_amigos);
        Si EXISTE
            Esc("El nombre del usuario que mas figuritas coleccionó es:",reg_amigos.nombre,
            "y su apellido es:",reg_amigos.apellido);
        Sino
            Esc("Error");
        FinSi
        Cerrar(arch_album);
        Cerrar(arch_amigos);
FinAccion
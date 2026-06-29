ACCION 3.28(V:arreglo[A...F]de enteros) es
    Ambiente
        TERRENOS=REGISTRO
            Nro_terreno:N(4);
            Zona:A...F;
            Ubicacion:1...10;
            Superficie:N(3); //en metros cuadrados
        FR
        arch:Archivo de TERRENOS;
        reg:TERRENOS;
        A:arreglo [A...G,1...11] de enteros
        i,j:entero
        coeficiente=10
    Proceso
        Abrir E/(arch);
        Leer(arch,reg);

        Para i:=A a G hacer
            Para j:=1 a 11 hacer
                A[i,j]:=0
            Finpara
        Finpara

        Mientras NFDA(arch) hacer
            i:=reg.Zona
            j:=reg.Ubicacion

            A[i,j]:=A[i,j] + (reg.Superficie*V[i]*coeficiente);
            A[i,11]:=   A[i,11]  + (reg.Superficie*V[i]*coeficiente);
            A[G,j]:=  A[G,j] + (reg.Superficie*V[i]*coeficiente);

            Leer(arch,reg);
        FinMientras

        Para i:=A a F hacer
            Esc("Para la zona:",i);
            Para j:=1 a 10 hacer
                Esc("Ubicacion:",j,"El valor del terreno es:",A[i,j]);
                Esc("El total de la ubicacion es:",A[G,j]);
            Finpara
            Esc("El total de la zona es:",A[i,11]);
        Finpara

        CERRAR(arch);
FinAccion


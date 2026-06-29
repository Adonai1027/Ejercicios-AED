Accion (R:arreglo [1...31] de alfanumerico,C:arreglo[1...16]de alfanumerico) Papers es

    Ambiente
        Papers=Registro
            DNI:N(8);
            ApyN:AN(50);
            Email:AN(50);
            Regional:1...31;
            Categoria: 1...16;
            Titulo_trabajo:AN(30);
        FR
        arch_papers:Archivo de Papers;
        reg_papers:Papers
        A:arreglo[1...32,1...17] de enteros;
        i,j:entero
        mayor:entero
        nombre_mayor:alfanumerico
    Proceso
        Para i:=1 a 32 hacer
            Para j:=1 a 17 hacer
                A[i,j]:=0
            FinPara
        FinPara

        Abrir E/(arch_papers); 
        Leer(arch_papers,reg_papers);

        Mientras NFDA(arch_papers) hacer
            i:=reg_papers.Regional;
            j:=reg_papers.Categoria;
            A[i,j]:=A[i,j] + 1;
            A[32,j]:=A[32,j] + 1; //total categoria
            A[i,17]:=A[i,17] + 1; //total regional
            Leer(arch_papers,reg_papers);
        FinMientras
        mayor:=0
        Para i:=1 a 31 hacer
            Esc("Para la regional:",i,"nombre:",R[i],"el total es:",A[i,17])
            Para j:=1 a 16 hacer
                Esc("Categoría:",i,"nombre:",C[i],"El total es de:",A[32,j],"papers");
            FinPara
            Si A[i,17] > mayor entonces
                mayor:=A[i,j];
            FinSi
        FinPara
        
        Para i:=1 a 31 hacer
            Si A[i,17]=mayor entonces
                Esc("Regional con mas Papers:",R[i]);
            FinSi
        FinPara

        CERRAR(arch_papers);
        
FinAccion
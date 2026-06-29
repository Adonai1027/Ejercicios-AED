Accion 3.11 es
    Ambiente
        Videos=Registro
            Titulo: AN(40);
            Director: AN(30);
            Categoria: 1...5;            
            Can_personas: N(9);
            Alquilado: ("SI","NO")
        FR
        arch_videos: Archivo de Videos ordenado por Titulo;
        reg_videos: Videos;
        A: arreglo [1...100] de Videos;
        i:entero;
        cat_usu: 1...5;
        Proceso

            Esc("Ingrese cat");
            Leer(cat_usu);
            Esc("Peliculas");
        Para i:=1 a 100 hacer
            Si cat_usu=b A[i].Categoria entonces
                Esc(A[i].Titulo);
            FinSi
        FinPara

FinAccion

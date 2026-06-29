Accion ARREGLO RECLAMOS es
    Ambiente
        EJEMPLARES=Registro
            ID_ejemplar:N(8);
            ID_libro: AN(30);
            Sucursal: 1...5;
            Digital: ("S","N");
            Disponible: booleano;
        FR
        arch_ejemplares:Archivo de EJEMPLARES ordenado por ID_ejemplar, ID_libro;
        reg_ejemplar: EJEMPLARES;

        A: arreglo [1...3,1...3,1...6] de enteros; //disponibilidad, dig/fisico, sucursal
        i,j,k:entero;

        sucursal_mayor, mayor: entero

        Funcion ConvertirDigital (x:alfanumerico): entero es
            Según (x) hacer
                ="S": ConvertirDigital:=1;
                ="N": ConvertirDigital:=2;
            FinSegun
        FinFunción

        Funcion MostrarForma (x:entero) : alfanumerico es
            Segun (x) hacer
                =1:MostrarForma:="Digital";
                =2:MostrarForma:="Físico";
            FinSegun
        FinFunción

        Funcion ConvertirDisponiblidad (x:booleano): entero es
            Segun (x) hacer
                =Verdadero:ConvertirDisponiblidad:=1;
                =Falso: ConvertirDisponiblidad:=2;
            FinFunción
        FinFunción

        Funcion MostrarDisponiblidad (x:entero): alfanumerico es
            Segun (x) hacer
                =1:MostrarDisponiblidad:="Disponible";
                =2:MostrarDisponiblidad:="No Disponible";
            FinSegun
        FinFunción

    Proceso

        //PRIMERO DEBO CARGAR EL ARREGLO 

        Para k:=1 a 6 hacer
            Para j:=1 a 3 hacer
                Para i:=1 a 3 hacer
                    A[i,j,k]:=0
                FinPara
            FinPara
        FinPara

        //RECORRO EL ARCHIVO Y CARGO EN EL ARREGLO
        Abrir E/(arch_ejemplares);
        Leer(arch_ejemplares,reg_ejemplar);

        Mientras NFDA(arch_ejemplares) hacer

            i:= ConvertirDisponiblidad(reg_ejemplar.Disponible);
            j:=ConvertirDigital(reg_ejemplar.Digital);
            k:=reg_ejemplar.sucursal;

            A[i,j,k]:= A[i,j,k] + 1; // se ocupa siempre
            A[3,j,k]:= A[3,j,k] + 1; //consigna 1 discriminando si es dig o no;
            A[3,j,6]:= [3,j,6] + 1; //consigna 3;
            A[i,3,k]:=  A[i,3,k] + 1; //consigna 1 sin discriminar dig/fis;

            Leer(arch_ejemplares,reg_ejemplar);

        FinMientras

        //recorro el arreglo y muestro totales

        mayor:=0
        Para k:=1 a 5 hacer
            Esc("Para la sucursal:", k);
            Para j:=1 a 3 hacer
                Esc("Forma:",MostrarForma(j));
                Para i:=1 a 3 hacer
                    Esc("DIsponiblidad:",MostrarDisponiblidad(i), "el total es:"A[i,j,k];);

                    Si A[1,3,k] > mayor entonces
                        mayor:=A[1,3,k];
                        sucursal_mayor:=k
                    FinSi

                FinPara
            FinPara
        FinPara

        Esc("La sucursal con mayor cantidad de ejemplares disponibles es la:", sucursal_mayor);
        Esc("El total de libros digitales es:", A[3,1,6], "y el total de físicos es:", A[3,2,6]);
        Esc("La cantidad de ejemplares de la sucursal 2 es:",A[3,3,2]);

        Cerrar(arch_ejemplares);

FinAccion
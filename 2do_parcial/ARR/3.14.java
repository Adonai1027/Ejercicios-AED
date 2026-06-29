Accion 3.14 es
    Ambiente    
        Desfile=Registro
            Nombre:AN(30);
            Altura:N(3); //en cm
        FR
        n=100;
        A:arreglo [1...n] de Desfile;
        i,j,x:entero;
    Proceso
         //inicializo el arreglo
        Para i:=1 a n hacer
            A[i]:=0
        FinPara

        //cargo el arreglo

        Para i:=1 a n hacer
            Esc("Ingrese nombre");
            Leer(A[i].Nombre);
            Esc("Ingrese altura de el/la modelo en cm");
            Leer(A[i].altura);
        FinPara

         //ordeno el arreglo de menor a mayor e imprimo
        Esc("Nombre Modelo","Altura")
         Para i:=2 a n hacer
            nombre:=a[i].nombre;
            x:=a[i].altura;
            j:=i-1;
            Mientras (j > 0) y (x < a[j].altura ) hacer
                a[j + 1].altura:=a[j].altura;
                j:=j-1;
            FinMientras
            a[j+1].nombre:=nombre;
            a[j+1].altura:=x;
            Esc(A[j+1].nombre,A[j+1].altura); //imprimo nombre y altura;
         FinPara

FinAccion



Accion 3.13 es
    Ambiente

        Alumno=Registro
            Legajo: N(5);
            Materias_aprob: N(2);
        FR
        n=100
        A: arreglo [1..n] de Alumno;
        i,j,max,x,leg:entero;

        
        

    Proceso
        Para i:= 1 a n-1 hacer
            x:=a[i].Materias_aprob;
            legajo:=a[i].Materias_aprob;
            max:=i;
            Para j:= i+1 a n hacer
                Si (x<a[j].Materias_aprob) entonces
                    max:=j
                    x:=a[j].Materias_aprob;
                    leg:=a[j].legajo;
                FinSi
            FinPara
            a[max].Materias_aprob:=a[i].Materias_aprob;
            a[max].legajo:=a[i].legajo                                                 :=a[i].Materias_aprob;
            a[i].Materias_aprob:=x;
            a[i].Legajo:=leg;
        FinPara
    


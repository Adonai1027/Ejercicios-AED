Accion 3.21 (M: arreglo[1...6,1...6] de enteros;) es
    Ambiente

        i,j: entero;
    Proceso
        Para i:= 1 a 5 hacer
            Para j:= 1 a 5 hacer
                M[6,j]:=M[6,j] + M[i,j];
                M[i,6]:=M[i,6] + M[i,j;];
                M[6,6]:=M[6,6] + M[i,j];
            FinPara
        FinPara
FinAccion  
    
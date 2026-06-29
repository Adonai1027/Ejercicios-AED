Accion 3.4 (A: arreglo [1...50] de enteros) es
    Ambiente
        S: arreglo [1...50] de enteros;
        i,cont:entero;

    Proceso
        Para i:= 1 a 50 hacer
            Si A[i] MOD 3 = 0 entonces
                cont:=cont + 1;
            Sino
                S[í]:=A[i];
                A[i]:=A[i]* 3; //lo multiplico por 3 para que sea múltiplo de 3;
            FinSi
        FinPara
        Esc("La cantidad de numeros que cumplieron la condicion es:",cont);
FinAccion


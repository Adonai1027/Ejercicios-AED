Accion 3.2 es
    Ambiente
        A: arreglo [1...100] de enteros;
        i,numero: entero
        mayor,menor,pos_mayor, pos_menor cant_pares, suma_pares: entero;
        Proceso
           //consigna A carga de un arreglo  
            Para i:= 1 a 100 hacer
                A[i]:=0;
            FinPara

            Para i:=1 a 100 hacer
                Esc("Ingrese número");
                Leer (numero);
                A[i]:=numero;
            FinPara

                //consigna B y C recorro el arreglo
                mayor:=0
                menor:=1000000
                cant_pares:=0
                suma_pares:=0
            Para i:=1 a 100 hacer
                Si A[i] > mayor entonces
                    mayor:= A[i];
                    pos_mayor:=i
                Sino
                    Si A[i] < menor entonces
                        menor:=A[i];
                        pos_menor:=i
                    FinSi
                FinSi
                Si A[i] MOD 2=0 entonces
                    cant_pares:=cant_pares + 1;
                    suma_pares:=suma_pares + A[i];
                FinSi
            FinPara

            Esc("El numero mayor es:", mayor, "y se encuentra en la posición", pos_mayor);
            Esc("El numero menor es:",menor,"y se encuentra en la posición:",pos_menor);
            Esc("La cantidad de numeros pares es:",cant_pares,"y la suma de estos numeros es:",suma_pares);
FinAccion

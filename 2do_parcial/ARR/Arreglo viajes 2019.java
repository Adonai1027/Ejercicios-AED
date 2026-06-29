Accion (V:arreglo [1...23] de alfanumericos)es
    Ambiente
        Fecha=Registro
            AA:N(4);
            MM:1..12;
            DD:1...31;
        FR
        Viajes=Registro
            DNI:N(8);
            Fecha:Fecha;
            Provincia_Destino:N(2);
            Monto_Crédito:N(10);
        FR

        Formato=Registro
            Cant_viajes: N(10);
            Monto_crédito:N(20);
        FR

        arch_viajes:Archivo de viajes ordenado por DNI;
        reg_viajes:viajes;

        A:arreglo [1..23,1...13] de Formato;
        i,j:entero
        destino_mas_turistas, mayorturistas:entero
        destino_mayor, mayorcredito:entero


        Proceso
            //inicializo el arreglo
            Para i:=1 a 23 hacer
                Para j:=1 a 13 hacer
                    A[i,j].Cant_viajes:=0
                    A[i,j].Monto_Crédito:=0
                FinPara
            FinPara
            //cargo el arreglo
            Mientras NFDA(arch_viajes) hacer
                i:=reg_viajes.Provincia_Destino; // 1,2..,23
                j:=reg_viajes.fecha.mm; //1,2,...12

                A[i,j].Cant_viajes:= A[i,j].Cant_viajes + 1;
                A[i,j].Monto_Crédito:= A[i,j].Monto_Crédito + reg_viajes.Monto_Crédito;
                A[i,13].Cant_viajes:=A[i,13].Cant_viajes + 1;
                A[i,13].Monto_Crédito:=A[i,13].Monto_Crédito + reg_viajes.Monto_Crédito;
        
                Leer(arch_viajes,reg_viajes);
            FinMientras
            mayorturistas:=0
            mayorcredito:=0
            Para i:=1 a 23 hacer
                Esc("Para la provincia:",V[i]) //punto A
                Para j:=1 a 12 hacer
                    Esc("Para el mes:",ConvertirMes(j),"el total de viajes es de:",A[i,j].Cant_viajes,"con un monto total de",A[i,j].Monto_Crédito); //punto A
                    //punto B
                    Si mayorcredito < A[i,j].Monto_Crédito entonces
                        mayorcredito:=A[i,j].Monto_Crédito
                        destino_monto_max:=V[i];
                    FinSi
                FinPara
                Si destino_mas_turistas < A[i,13].Cant_viajes entonces //punto C
                    destino_mas_turistas:= A[i,13].Cant_viajes
                    destino_mayor:=V[i];
                FinSi
                Esc("El promedio de crédito de la provincia",V[i], "es de:",(A[i,13].Monto_Crédito/A[i,13].Cant_viajes)) //Punto D
            FinPara
            Esc("El destino con mas turistas fue:",destino_mayor, "con un total de:",destino_mas_turistas,"viajes"); //Punto C
            Esc("El destino con mayor monto otorgado,discriminado por destino y mes:",destino_monto_max);

            Cerrar(arch_viajes);
FinAccion
            

            
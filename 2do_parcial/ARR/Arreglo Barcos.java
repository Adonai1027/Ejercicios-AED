Accion barcos es
    Ambiente
        Fecha=Registro
            AA:N(4);
            MM:1...12;
            DD:1...31;
        FR
        TOTALES=REGISTRO
            Barco:1...3;
            Fecha:fecha;
            Obra:1...5;
            Espectadores:N(3);
            Total:N(5,2);
        FR

        arch_totales:Archivo de TOTALES;
        reg_tot:TOTALES;

        Formato_arreglo=Registro
            Espectadores:N(10);
            Total_recaudado:N(5,2);
        FR

        A:arreglo[1..3,1...13,1..5] de Formato_arreglo
        i,j,k:entero;
        obra_mayor,resg_mes,resg_barco, esp_max:entero  //consigna A
        mes_min, mes_menor:entero //consigna B
        barco_min,barco_menor:entero

        Proceso
            Abrir E/(arch_totales);
            Leer(arch_totales,reg_tot);

            Para k:=1 a 5 hacer
                Para j:=1 a 13 hacer
                    Para i:=1 a 3 hacer
                        A[i,j,k].Espectadores:=0
                        A[i,j,k].Total_recaudado:=0
                    FinPara
                FinPara
            FinPara

            Mientras NFDA(arch_totales) hacer
                i:=reg_tot.Barco;
                j:=reg_tot.fecha.mm;
                k:=reg_tot.Obra;

                A[i,j,k].Espectadores:=A[i,j].Espectadores + reg_tot.Espectadores;
                A[i,j,k].Total_recaudado:=A[i,j].Total_recaudado + reg_tot.Total;
                A[i,13,k].Total_recaudado:=A[i,13,k].Total_recaudado + reg_tot.total;

                Leer(arch_totales,reg_tot);
            FinMientras
            esp_max:=0 //consigna A
            Para k:=1 a 5 hacer
                barco_min:=HV //consigna C
                Para i:=1 a 3 hacer
                    mes_min:=HV
                    Para j:=1 a 12 hacer
                        Si A[i,j,k].Total_recaudado < mes_min entonces
                            mes_min:=A[i,j,k];
                            mes_menor:=j;
                        FinSi
                        Si A[i,j,k].Espectadores > esp_max entonces
                            esp_max:=A[i,j,k].Espectadores;
                            resg_mes:=j;
                            resg_barco:=i;
                            obra_mayor:=k;
                        FinSi
                    FinPara
                    Esc("Barco:",i,"El mes que recaudó menos es:",mes_menor); //consigna B
                    Si A[i,13,k].Total_recaudado < barco_min entonces
                        barco_min:=A[i,13,k].Total_recaudado;
                        barco_menor:=i;
                    FinSi
                FinPara
                Esc("Para la obra:",k,"El barco que recaudó menos fue:",barco_menor); //consigna C
            FinPara
            Esc("La obra con mayor cantidad de espectadores fue la:",obra_mayor,"ocurrió en el mes:",resg_mes,"barco:",resg_barco);

            Cerrar(arch_totales,reg_tot);
FinAccion

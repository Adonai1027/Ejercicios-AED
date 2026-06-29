Accion 3.23 (Pacientes: arreglo [1..50,1...7,1...4] de real) es
    Ambiente
        promedio: arreglo [1..50,1..7] de real
        i,j,k: entero
        //CONSIGNA 1
        temp_max, temp_min: real;
        dia_max, dia_min: entero;
        lect_max, lect_min: real;
        //CONSIGNA 2
        sum_temp,prom_dia: real: real
        
        Función ConvertirDia(x:entero):alfanumerico es
            Segun x hacer
                =1: ConvertirDia:="Lunes";
                .... .
                ..... ..
                ... 
                ..
            FinSegun
        FinFunción

        Proceso
        //inicializo el arreglo de la consigna 2
            Para i:=1 a 0 hacer
                Para j:= 1 a 7 hacer
                    promedio[i,j]:=0
                FinPara
            FinPara

            Esc("Número de paciente - Temp Max / Dia / Lectura - Temp Min /Dia/Lectura");

            Para i:=1 a 50 hacer
                //en el numero de paciente asigno la temp max y min

                temp_max:=Pacientes[i,1,1];
                temp_min:=Pacientes[i,1,1];
                Para j:=1 a 7 hacer
                    sum_temp:=0
                    Para k:=1 a 4 hacer
                        
                        Si Paciente[i,j,k] > temp_max entonces
                            temp_max:=Pacientes[i,j,k];
                            lect_max:=k
                            dia_max:=j
                        FinSi
                        Si Paciente[i,j,k] < temp_min entonces
                            temp_min:=Pacientes[i,j,k];
                            lect_min:=k
                            dia_min:=j
                        FinSi

                        //sumo para el promedio por dia
                        sum_temp:=sum_temp + Pacientes[i,j,k];
                        promedio[i,j]:=promedio[i,j] + sum_temp; 
                    FinPara
                    
                    promedio[i,j]:=promedio[i,j] / 4; //calculo el promedio del dia

                    Esc("Paciente numero:", i, "Temp Max:", temp_max, "Dia:", ConvertirDia(dia_max),"Lectura Nro:",lect_max,"-"
                    ,"Temp Min:", temp_min,"Dia:", ConvertirDia(dia_min),"Lectura Nro:", lect_min)
                FinPara

            FinPara
FinAccion
Accion Mundial Recursividad (prim:puntero a Nodo) es
    Ambiente
        Nodo=Registro
            Pais:AN(30);
            Edad:arreglo [1...26] de enteros;
            Grupo:A...H;
            Puntos:N(2);
            Amarillas:n(2);
            Rojas:N(2);
            prox:puntero a Nodo
        FR

        p,a:puntero a Nodo

        pais_usuario:alfanumerico;
        grupo_usuario:A...H;
        menor_promedio_edad,mayor_promedio_edad:entero
        equipo_menor, equipo_mayor:alfanumerico

        Funcion Promedio(i,suma:entero, A:arreglo [1...26] de enteros):real;
            Si i=26 entonces
                Promedio:=Promedio(i,suma,A) / i
            Sino
                Promedio:=Promedio(i+1,suma + A[i],A);
            FinSi
        FinFuncion

    Proceso
        menor_promedio_edad:=HV
        mayor_promedio_edad:LV
        
        
        Para i:=1 a 32 hacer
            suma:=0
            Esc("Ingrese numero de grupo");
            Leer(grupo_usuario);
            Esc("Ingrese nombre del pais");
            Leer(pais_usuario);
            
            p:=prim
            Mientras (p<>nil) y (*p.pais<>pais_usuario) hacer
                p:=*p.prox
            FinMientras

            Si p=nil entonces
                Esc("Grupo no encontrado");
            Sino
                *p.grupo:=grupo_usuario
                Promedio_equipo:=Promedio(1,suma,Edad)
                
                Si Promedio_equipo > mayor_promedio_edad entonces
                    mayor_promedio_edad:= Promedio(1,suma,Edad);
                    equipo_mayor:=*p.pais;
                Sino
                    Si Promedio_equipo < menor_promedio_edad entonces
                        menor_promedio_edad:=Promedio_equipo;
                        equipo_menor:=*p.pais;
                    FinSi
                FinSi
            FinSi
        FinPara

        //reordeno la lista
        p:=prim
        a:=nil
        q:=*prim.prox

        Mientras (p<>nil) y (*p.grupo < *q.grupo) hacer
            a:=p
            p:=*p.prox
        FinMientras
        Si (*p.grupo > *q.grupo) entonces
            *p.prox:=*q.prox
            *q.prox:=p //intercambio de lugar
        FinSi
            
        Esc("El equipo con mayor promedio de edad es:",equipo_mayor);
        Esc("El equipo con menor promedio de edad es:",equipo_menor);
    
FinAccion



    
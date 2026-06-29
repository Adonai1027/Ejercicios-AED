Accion fila 3 1o sept es
    Ambiente
        Fecha=Registro
            AA:N(4);
            mm: 1...12;
            dd:1...31;
        FR
        PROYECTOS=Registro
            ID_PROYECTO. N(4);
            Nombre: AN(20);
            Lider: AN(30);
            Fecha_inicio: Fecha
            Duracion_meses: N(2)
        FR
        arch_proy: Arcgivo de PROYECTOS;
        reg_proy: PROYECTOS

        Presupuestos=Registro
            ID_PROYECTO
            Presupuesto_total: N(10);
            Presupuesto_dispo: N(10);
        FR

        arch_pres: Archivo de Presupuestos indexado por ID_PROYECTO;
        reg_pres:Presupuestos

        A: arreglo [1..40] de PROYECTOS
        S: arreglo [1...40] de entero;
        i,s:entero
        lider_usu: alfanumerico;
        cant_proyect:entero
        mayor_duracion: entero
        proy_mayor_duracion: alfanumerico
        total_presupuesto: entero
        Proceso
            Esc("Ingrese lider");
            Leer(lider_usu);
            Esc("Proyectos liderados por",lider_usu);
            cant_proyect:=0
            mayor_duracion:=0
            total_presupuesto:=o
            s:=1
            Para i:= 1 a 40 hacer
                reg_pres.ID_PROYECTO:= A[i].ID_PROYECTO;
                Si existe entonces
                    Si A[i].lider = lider_usu entonces
                        Esc(A[i].nombre);
                        cant_proyect:=cant_proyect + 1;
                    FinSi
                    Si A[i].Duracion_meses > mayor_duracion entonces
                        mayor_duracion:=A[i].Duracion_meses;
                        proy_mayor_duracion:=A[i].nombre;
                    FinSi
                    Si A[i].Fecha_inicio.mm = fecha_sistema.mm() entonces
                        total_presupuesto:=total_presupuesto + reg_pres.Presupuesto_dispo;
                    FinSi
                    Si (A[i].Duracion_meses > 6) y (reg_pres.Presupuesto_total * 0,20 > reg_pres.Presupuesto_dispo) entonces
                        S[s]:=A[i].ID_PROYECTO;
                        s:=s + 1;
                    FinSi
            FinPara

            Esc("La cantidad de proyectos a cargo del", lider_usu," es de:", cant_proyect);
            Esc("El proyecto con mayor duracion es:", proy_mayor_duracion);
            Esc("El presupuesto disponible de los proyectos iniciados este mes es:", total_presupuesto);
            CERRAR(arch_pres);
FinAccion
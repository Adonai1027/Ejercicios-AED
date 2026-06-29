Accion fila 1 10 sept (C:arreglo [1...50] de alfanumerico) es
    Ambiente
        NOTAS_AED= Registro
            Legajo
            nota_p_parc
            not_rec
            comision
        FR

        arch_notas: Archivo de NOTAS_AED ordenado po Legajo;
        reg_notas:NOTAS_AED;

        SYSACAD=Registro
            Legajo
            AyN
            DNI
            Fecha_ingreso
            Carrera
            Codigo_materia: 1...50;
        FR
        arch_sysa: Archivo de SYSACAD idexado por legajo,
        reg_sysa: SYSACAD;
        A: arreglo[1...150] de alfanumerico;
        c,i:entero //para los arreglos
        nombre_materia: alfanumerico;
        Proceso
            Abrir E/(arch_notas);
            Abrir E/(arch_sysa);
            Leer(arch_notas, reg_notas);
            Esc("Legajo","AyN","Fecha de Ingreso", "Nombre Ultima materia aprobada");
            i:=1
            Mientras NFDA(arch_notas) hacer
                reg_sysa.legajo:=reg_notas.Legajo;
                Leer(arch_sysa,reg_sysa);
                
                Si existe entonces
                    Si (reg_notas.comision="D") y ((reg_notas.nota_p_parc >=6) o (reg_notas.not_rec >=6)) entonces
                        A[i]:=reg_sysa.AyN;
                        i:=i + 1;
                    FinSi
                    Si (reg_notas.not_rec < 6) entonces
                        Para c:=1 a 50 hacer
                            Si c=reg_sysa.Codigo_materia entonces
                                nombre_materia:=C[c];
                            FinSi
                        FinPara
                        Esc(reg_sysa.Legajo,reg_sysa.AyN, reg_sysa.Fecha_ingreso, nombre_materia);
                    FinSi
                Sino    
                    Esc("El alumno no está inscripto en el SYSACAD");
                FinSi
            FinMientras
            Cerrar(arch_notas);
            Cerrar(arch_sysa);
FinAccion

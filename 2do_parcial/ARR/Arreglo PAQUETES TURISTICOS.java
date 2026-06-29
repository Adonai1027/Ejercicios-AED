Accion PAQUETES_TURISTICOS es
    Ambiente
        Fecha=Registro
            AA:n(4);
            MM:1...12;
            dd:1...31;
        FR
        
        CLIENTES=REGISTRO
            Nro_cliente:N(4);
            apellido_nombre:AN(50);
            DNI:N(8);
            id_paquete:1...12;
            Estado:("SALDADO","SALDO A FAVOR","DEUDOR");
            Categoria:("SIMPLE","PLATA","ORO","DIAMANTE");
            Puntos:N(2);
            Fecha_baja:Fecha;
        FR

        arch_clientes:Archivo de CLIENTES;
        reg_cli:CLIENTES;

        PAQUETES_TURISTICOS=REGISTRO
            id_paquete:N(5); 
            Nombre: AN(30);
            Costo:N(3,2);
            Destino: AN(30);
        FR

        arch_ind:Archivo de PAQUETES_TURISTICOS indexado por id_paquete;
        reg_ind:PAQUETES_TURISTICOS;

        A:arreglo [1...13,1..5] de enteros
        i,j,:entero
        mayor_paquete, max: entero

        Funcion ConvertirCategoria(x:alfanumerico):entero
            Segun x hacer
                ="SIMPLE": ConvertirCategoria:=1;
                ="PLATA": ConvertirCategoria:=2;
                ="ORO": ConvertirCategoria:=3;
                ="DIAMANTE": ConvertirCategoria:=4;
            FinSegun
        FinFuncion

        Funcion ConvertirEstado(x:alfanumerico) : entero
            Segun x hacer
                ="SALDADO":ConvertirEstado:=1
                ="SALDO A FAVOR":=ConvertirEstado:=2
            FinSegun
        FinFuncion

    Proceso
        Abrir E/(arch_clientes);
        Leer(arch_clientes,reg_cli);
        
        Para i:=1 a 13 hacer
            Para j:=1 a 5 hacer
                Para k:=1 a 3 hacer
                    A[i,j]:=0
                FinPara
            FinPara
        FinPara

        Mientras NFDA(arch_clientes) hacer
            reg_ind.id_paquete:=reg_cli.id_paquete;
            leer(arch_ind,reg_ind);
            Si EXISTE entonces
                Si reg_cli.Estado <> "DEUDOR" entonces
                    i:=reg_cli.id_paquete;
                    j:=ConvertirCategoria(reg_cli.categoria);
                    A[i,j]:=A[i,j] + 1; //discriminado por categoria y paquete
                    A[13,5]:=A[13,5] + 1; //consigna B
                    A[i,5]:=A[i,5] + 1; //consigna C
                Sino
                    Esc("ERROR, ES DEUDOR");
                FinSi
            Sino
                Esc("Error");
            FinSi
            Leer(arch_clientes,reg_cli);
         //paquete con mas ventas consigna C
        FinMientras


        
        max:=LV
        Para i:=1 a 12 Hacer
            Esc("Para el paquete:",i,"Nombre:",A[reg_ind.nombre]);
            Para j:=1 a 4 Hacer
                Esc("Categoría:",j,"La cantidad es:",A[i,j]); //consigna A
            FinPara
            Si A[i,5] > max entonces
                max:=A[i,5];
                mayor_paquete:=i;
            FinSi
        FinPara
        Esc("Cantidad total de paquetes saldados:",A[13,5]); //consigna B
        reg_ind.id_paquete:=mayor_paquete
        Leer(arch_ind,reg_ind);
        Esc('El paquete con mas ventas es',reg_ind.nombre,'con un total de',max);

        CERRAR(arch_ind);
        CERRAR(arch_clientes);
FinAccion

    
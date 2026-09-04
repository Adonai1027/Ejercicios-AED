Accion estadisticas_biblioteca es
    Ambiente
        formato_clave=Registro
            Cod_lib:N(8);
            Cod_ejemplar:N(10);
        FR
        
        EJEMPLARES=Registro
            clave:formato_clave;
            Descripcion:AN(255);
            Tipo_libro:("A","D","L","N");
            Disponible:("Si","No");
            Estado:AN(10); // "Bien", "Reparacion", "Baja"
            Fecha:fecha;
        FR
        arch_mae:Archivo Secuencial de EJEMPLARES ordenado por clave;
        reg_mae:EJEMPLARES;

        A:arreglo [1...4,1...12] de Enteros;
        f, c: entero;
        tot_fila, tot_col, tot_gen: entero;
        mes_buscado, min_cant, resg_tipo: entero;
        nombre_tipo: AN(15);
        
        // Se asume que existe la funcion extraer_mes(fecha) que retorna el mes (1 a 12)
    Proceso
        // Inicializar matriz en 0
        Para f:=1 a 4 hacer
            Para c:=1 a 12 hacer
                A[f,c]:=0;
            FinPara
        FinPara

        Abrir E/(arch_mae);
        leer(arch_mae, reg_mae);

        //llenado inicial del arreglo
        Mientras NFDA(arch_mae) hacer
            Si reg_mae.Estado="Reparacion" o reg_mae.Estado="Baja" entonces
                Segun reg_mae.Tipo_libro hacer
                    "A": f:=1
                    "D": f:=2
                    "L": f:=3
                    "N": f:=4
                FinSegun
                
                c:=extraer_mes(reg_mae.Fecha);
                
                A[f,c]:=A[f,c] + 1;
            FinSi
            
            leer(arch_mae, reg_mae);
        FinMientras
        
        // a) 1. y 2. Mostrar cantidades discriminadas, y totales por fila, columna y general
        tot_gen:=0;
        
        Esc("Cantidades por tipo de libro y mes (con totales por tipo):");
        Para f:=1 a 4 hacer
            Segun f hacer
                1: nombre_tipo:="Autoayuda"
                2: nombre_tipo:="Didacticos"
                3: nombre_tipo:="Literatura"
                4: nombre_tipo:="No ficcion"
            FinSegun
            
            Esc("Tipo de libro: ", nombre_tipo);
            tot_fila:=0;
            
            Para c:=1 a 12 hacer
                Esc("Mes ", c, ": ", A[f,c]);
                tot_fila:=tot_fila + A[f,c];
            FinPara
            
            Esc("Total de libros tipo ", nombre_tipo, " en Reparacion/Baja: ", tot_fila);
            tot_gen:=tot_gen + tot_fila;
        FinPara
        
        Esc("Totales por mes:");
        Para c:=1 a 12 hacer
            tot_col:=0;
            Para f:=1 a 4 hacer
                tot_col:=tot_col + A[f,c];
            FinPara
            Esc("Total del mes ", c, ": ", tot_col);
        FinPara
        
        Esc("Total general de libros en Reparacion o Baja: ", tot_gen);
        
        // b) Tipo de libro con menor cantidad en un mes ingresado por el usuario
        Esc("Ingrese el mes (1-12) para buscar el tipo de libro con menor cantidad: ");
        leer(mes_buscado); // Simula el ingreso del usuario por pantalla
        
        min_cant:=HV; // Valor inicial muy alto (HV)
        resg_tipo:=0;
        
        Para f:=1 a 4 hacer
            Si A[f, mes_buscado] < min_cant entonces
                min_cant:=A[f, mes_buscado];
                resg_tipo:=f;
            FinSi
        FinPara
        
        Segun resg_tipo hacer
            1: nombre_tipo:="Autoayuda"
            2: nombre_tipo:="Didacticos"
            3: nombre_tipo:="Literatura"
            4: nombre_tipo:="No ficcion"
        FinSegun
        
        Esc("Para el mes ", mes_buscado, " el tipo de libro con menor cantidad fue: ", nombre_tipo, " con ", min_cant, " ejemplares.");

        Cerrar(arch_mae);
FinAccion

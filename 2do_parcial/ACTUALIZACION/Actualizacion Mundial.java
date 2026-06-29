Accion(A:arreglo [1...26] de alfanumerico) es
    Ambiente
        PRODUCTO=REGISTRO
            Id_prod:N(4);
            Nombre:AN(20);
            Descripcion:AN(50);
            Categoria:("C","R","G","S");
            Nuevo_lanzamiento:("SI","NO");
            Porc_desc:real;
            Stock:N(3);
        FR

        arch_mae,arch_sal:Archivo SECUENCIAL de PRODUCTO ordenado por Id_prod;
        reg_mae,reg_sal,aux:PRODUCTO;

        PREVENTA=REGISTRO
            Id_prod:n(4);
            id_cliente:n(5);
            cantidad:N(2);
            es_personalizado:("SI","NO");
            nro_jugador:1...26;
            nombre_jug:AN(30);
            Talle:("S","M","L","XL","XXL");
        FR
        X:arreglo [1...26] de Enteros;
        M:arreglo[1...4] de enteros;
        i,cant_prod:entero
        mas_camisetas:entero
        arch_mov:Archivo de PREVENTA ordenado por Id_prod,id_cliente;
        reg_mov:PREVENTA;
        nombre_jugador,cat_menor:alfanumerico

         Procedimiento Leer_Mae () es
            Leer(arch_mae,reg_mae);
            Si FDA(arch_mae) entonces
                reg_mae.Id_prod:=HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov () es
            Leer(arch_mov,reg_mov);
            Si FDA(arch_mov) entonces
                reg_mae.Id_prod=HV
            FinSi
        FinProcedimiento

        Procedimiento Productos_Iguales() es
            Si aux.stock < reg_mov.cantidad Entonces
                aux.stock:=aux.stock - reg_mov.cantidad;
                cant_prod:=cant_prod + (reg_mov.cantidad-reg_mae.stock);
                Esc("El pedido no se pudo concretar por falta de stock");
            FinSi
        FinProcedimiento
        bandera_baja:booleano

        Procedimiento Categoria() es
            Segun aux.Categoria hacer
                ="C":M[1]:=M[1] + reg_mov.cantidad;
                x[reg_mov.nro_jugador]:=x[reg_mov.nro_jugador] + reg_mov.cantidad;
                ="R":M[2]:=M[2] + reg_mov.cantidad;
                ="G":M[3]:=M[3] + reg_mov.cantidad;
                ="S":M[4]:=M[4]+ reg_mov.cantidad;
            FinSegun
        FinProcedimiento


    
    Proceso
        Abrir E/(arch_mae);
        Abrir E/(arch_mov);
        Abrir /S(arch_sal);
        Leer_Mae();
        Leer_Mov();

        cant_prod:=0
        importe:

        Para i:=1 a 26 hacer
            X[i]:=0
        FinPara
        Para i:=1 a 4 hacer
            M[i]:=0
        FinPara

        Mientras (reg_mae.Id_prod <> HV) o (reg_mov.Id_prod <> HV) hacer
            Si reg_mae.Id_prod < reg_mov.Id_prod entonces
                reg_sal:=reg_mae
                Grabar(arch_sal,reg_sal);
                Leer_Mae();
            Sino
                Si (reg_mae.Id_prod=reg_mov.Id_prod) entonces
                    aux:=reg_mae
                    bandera_baja:=Falso
                    Mientras(aux.Id_prod=reg_mov.Id_prod) hacer
                        Si aux.stock > 0 Entonces
                            Productos_Iguales();
                            importe:=importe + reg_mov.cantidad*aux.Porc_desc;
                        Sino
                            bandera_baja:=verdadero
                            Esc("Error, no hay stock");
                        FinSi
                        Categoria(); //se hace siempre ya que los solicitados no depende de si hay o no stock
                        Leer_Mov();
                    FinMientras
                    Si no bandera_baja Entonces
                        reg_sal:=aux
                        Grabar(arch_sal,reg_sal);
                    FinSi
                Sino
                    //mae mayor que el mov es un error
                    Esc("Error, el producto solicitado no existe");
                    cant_prod:=cant_prod + reg_mov.cantidad //contabilizo toda la cantidad pedida
                    Leer_Mov();
            FinSi
        FinMientras
        mas_camisetas:=0
        Para i:=1 a 26 hacer
            Si x[i]>mas_camisetas entonces
                mas_camisetas:=x[i];
                nombre_jugador:=A[i];
            FinSi
        FinPara
        menos_solicitada:=HV
        Para i:=1 a 4 hacer
            Si M[i] < menos_solicitada entonces
                menos_solicitada:=M[i];
                cat_menor:=i
            FinSi
        FinPara

        Esc("El nombre del jugador que mas vendió es:",nombre_jugador);
        Esc("La cantidad de productos que no se pudieron procesar por falta de stock es:",cant_prod);
        Esc("El nombre de la categoria menos solicitada es",cat_menor);
    
        Cerrar(arch_mae);
        Cerrar(arch_mov);
        Cerrar(arch_sal);
FinAccion





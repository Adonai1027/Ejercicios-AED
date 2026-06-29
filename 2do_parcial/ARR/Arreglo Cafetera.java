Accion cafetera es
    Ambiente
        Promociones=Registro
            CodSuc:1...10;
            CodPromocion:0...5;
            CodProd:AN(7);
            Cant:N(4);
        FR
        arch_promociones:Archivo de Promociones;
        reg_promo:Promociones

        Formato_arreglo=Registro
            Importe:N(20);
            Cant_ventas:N(4);
        FR

        A:arreglo [1..11,1...6] de Formato_arreglo;
        i,j:entero
        promedio:real
        resg_suc, suc_mayor:entero
        resg_promo, promo_mayor:entero

    Proceso
        Abrir E/(arch_promociones);
        Leer(arch_promociones,reg_promo);

        Para i:=1 a 11 hacer
            Para j:=1 a 6 hacer
                A[i,j].Cant_ventas:=0
                A[i,j].importe:=0
            FinPara
        FinPara

        Mientras NFDA(arch_promociones) hacer
            Si reg_promo.CodPromocion <> 0 entonces
                i:= reg_promo.CodSuc; //sucursal
                j:=reg_promo.CodPromocion; //promociones

                A[i,j].Cant_ventas:=A[i,j].Cant_ventas + reg_promo.cant;
                A[i,6].Cant_ventas:=A[i,6].Cant_ventas + reg_promo.cant;
                A[i,6].importe:=A[i,6].importe + getImporte(reg_promo.CodProd)*reg_promo.cant;
                A[11,j].importe:=A[i,j].importe + getImporte(reg_promo.CodProd)*reg_promo.cant;
                
            Sino
                Esc("La venta se realizó sin promociones");
            FinSi
            Leer(arch_promociones,reg_promo);
        FinMientras
            suc_mayor:=LV
            promo_mayor:=LV
            Para j:=1 a 5 hacer
                Para i:=1 a 10 hacer
                promedio:=A[i,6].importe/A[i,6].Cant_ventas
                Esc("El importe promedio sobre el total de ventas de la sucursal:",i,"fue de:",promedio); //consigna A
                Si A[i,j].Cant_ventas > suc_mayor entonces
                    suc_mayor:=A[i,j];
                    resg_suc:=i
                FinSi
                FinPara
                Esc("La promocion:",j,"obtuvo mejores resultados en la sucursal:",resg_suc); //consigna C
                Si A[11,j].importe > promo_mayor entonces
                    promo_mayor:=A[11,j];
                    resg_promo:=j
                FinSi
            FinPara

            Esc("La promocion que mayor importe recaudó es:",resg_promo);
            Cerrar(arch_promociones);

FinAccion

        
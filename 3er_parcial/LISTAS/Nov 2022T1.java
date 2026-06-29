Accion Lista Es
 Ambiente
    Compras = Registro
        fecha_compra
        dni_cliente
        cant_art
        importe_total
    FinRegistro
    arch_compras
    reg

    Fidelizacion = Registro
        dni_cliente
        ayp
        fecha_adhesion
        categoria
    FinRegistro
    arch_fide
    index

    NODO_D = Registro
        ayp
        chances_total

        prox:Puntero a NODO
        ant:Puntero a NODO
    FinRegistro
    primd,pd,ult,q:Puntero a NODO_D

    NODO_C = Registro
        chance 
        prox
    FinRegistro
    primc, pc:Puntero a NODO_C

    num_aleatorio:Entero

    Procedimiento chances() Es
        *pd.chances_total:=*pd.chances_total + ((reg.importe_total) DIV 100)
        Si (index.categoria) = 'Black' Entonces
            num_aleatorio:=Tirar(x)
            Para i:=1 hasta num_aleatorio Hacer
                pc:=*pc.prox
            FinPara
            *pd.chances_total:=*pd.chances_total + *pc.chance
        FinSi 
    FinProcedimiento
 Proceso
    abrir e/(arch_compras);leer(arch_compras,reg)
    abrir e/(arch_fide)
    primd:=nil 
    ult:=nil 
    pc:=primc
    Mientras NFDA(arch_compras) Hacer
        index.dni_cliente:=reg.dni_cliente
        Leer(arch_fide, index)
        Si Existe Entonces
            pd:=primd
            Mientras (pd <> nil) y (*pd.ayp <> reg.ayp) Hacer
                pd:=*pd.prox
            FinMientras
            Si primd = nil Entonces //Primera vez
                Nuevo(q)
                *q.ayp:=index.ayp
                *q.chances_total:=chances() + 5
                *q.prox:=nil
                *q.ant:=nil
                primd:=q
                ult:=q 
            sino 
                //Metodo de ordenamiento
                pd:=primd
                Mientras (pd <> nil) y (*pd.ayp < index.ayp) Hacer
                    pd:=*pd.prox
                FinMientras
                Si pd = primd entonces 
					*Q.Prox:= primd 
					*Q.Ant:= Nil
					*pd.Ant:= Q
					primd:= Q
				sino 
					Si pd = Nil entonces 
						*Q.Prox:= Nil 
						*Q.Ant:= Ult
						*Ult.Prox:= Q
						Ult:= Q
					Sino 
						*Q.Prox:= pd
						*Q.Ant:= *pd.Ant
						*(*pd.Ant).Prox:= Q
						*pd.Ant:= Q
				    FinSi 
                FinSi
                *pd.chances_total:=*pd.chances_total + chances()
            FinSi  
        FinSi 
        Leer(arch_compras, reg)
    FinMientras
    cerrar(arch_compras)
    cerrar(arch_fide)
FinAccion
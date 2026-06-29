Accion Lista (prim_d:Puntero a NODO_D) Es
 Ambiente
    Sec = Registro
        Fecha_compra
        dni_cliente
        cant_articulos
        importe_total
    FinRegistro
    arch:Archivo Secuencial ordenado por Fecha_compra
    reg:Sec

    Index = Registro
        dni_cliente
        ayp
        fecha_adhesion
        cat 
    FinRegistro
    arch_index: Archivo indexado por dni_cliente
    index: Index

    NODO_S = Registro
        dni_cliente
        cant_compras
        importe_total

        prox:Puntero a NODO_S
    FinRegistro
    prim,p,a:Puntero a NODO_S

    NODO_D = Registro
        importe_minimo
        importe_maximo
        desc
        cupo
        rubro

        prox:Puntero a NODO_D
        ant:Puntero a NODO_D
    FinRegistro
    prim_d, d,ult:Puntero a NODO_D
    importe_aux
 Proceso
    abrir e/(arch);leer(arch,reg)
    abrir e/(arch_index)
    a:=nil 
    prim:=nil 
    d:=prim_d
    Mientras NFDA(arch) Hacer
        //Punto a)
        index.dni_cliente:= reg.dni_cliente
        Leer(arch_index, index)
        Si NO Existe Entonces
            p:=prim 
            Mientras (p <> nil) y (*p.dni_cliente <> reg.dni_cliente) Hacer
                p:=*p.prox
            FinMientras
            Si (p=nil) Entonces //NUEVO SOCIO
                Nuevo(q)
                *q.dni_cliente:= reg.dni_cliente
                *q.cant_compras:=1
                *q.importe_total:= reg.importe_total
                p:=prim
                Mientras (p<>nil) y (*p.dni_cliente < reg.dni_cliente) Hacer
                    a:=p
                    p:=*p.prox
                FinMientras
                Si (a=nil) Entonces
                    *q.prox:=prim
                    prim:=q
                sino
                    *q.prox:=p 
                    *a.prox:=q
                FinSi
            sino 
                *p.cant_compras:=*p.cant_compras + 1
                *p.importe_total:=*p.importe_total + reg.importe_total
            FinSi
        FinSi 

        //Punto b)
        importe_aux:=reg.importe_total
        d:=prim_d
        Mientras (d <> nil) Hacer
            Si (*d.importe_minimo < importe_aux) y (*d.importe_maximo > importe_aux) Entonces
                Si (*d.cupo = True) Entonces
                    Esc('Cupon de Descuento:',*d.desc*100)
                    Esc('El rubro al que corresponde es',*d.rubro)
                sino 
                    Esc('Cupo no disponible')
                FinSi
            sino
                Esc('Descuento Rechazado')
            FinSi
            d:=*d.prox
        FinMientras
        Leer(arch,reg)
    FinMientras
    cerrar(arch)
    cerrar(arch_index)
FinAccion
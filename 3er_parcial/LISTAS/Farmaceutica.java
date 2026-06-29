Accion Farmaceutica prim:Puntero a NODO Es 
 Ambiente
    NODO = Registro
        nro_cliente
        nro_pedido
        precio_pedido
        tipoPedido
        prox:Puntero a NODO
    FinRegistro
    p:Puntero a NODO

    CLIENTES = Registro
        nro_cliente
        dni 
        domicilio 
        dinero_deuda
        deudor
        baja
    FinRegistro
    arch_index
    index 

    NODO_S1 = Registro //clientes deudores
        nro_cliente
        total_pedido
        prox:Puntero a NODO_S1
    FinRegistro
    prim1,p1,q1,a1:Puntero a NODO_S1

    NODO_S2 = Registro//clientes regulares
        nro_cliente
        total_pedido
        prox:Puntero NODO_S2
    FinRegistro
    prim2,p2,q2,a2:Puntero NODO_S2


 Proceso    
    abrir e/s (arch_index)
    p:=prim 
    prim1:=nil 
    prim2:=nil 
    a1:=nil 
    a2:=nil 
    Mientras p <> nil Hacer
        index.nro_cliente:=*p.nro_cliente
        Leer(arch_index,index)
        Si Existe Entonces
            Si index.baja = true Entonces
                index.baja:= false
            FinSi
            totPedido:=0
            resg_nro:=*p.nro_cliente
            Mientras resg_nro = *p.nro_cliente Hacer
                //acumulo el total de pedidos
                totPedido:=totPedido + *p.precio_pedido

                Si *p.tipoPedido = R Entonces
                    index.dinero_deuda:=index.dinero_deuda + *p.precio_pedido
                FinSi
                p:=*p.prox 
            FinMientras
            Si dinero_deuda > 0 Entonces
                //creo lista para los regulares
                Nuevo(q2)
                *q2.nro_cliente:=*p.nro_cliente
                *q2.total_pedido:=totPedido
                Si prim2 = nil Entonces
                    prim2:=q2 
                sino 
                    *a.prox:=q2 
                FinSi
                a:=q2 

                index.deudor:='no'
            sino 
                //creo lista para los deudores
                Nuevo(q1)
                *q1.nro_cliente:=*p.nro_cliente
                *q1.total_pedido:=totPedido
                Si prim1 = nil Entonces
                    prim1:=q1 
                sino 
                    *a.prox:=q1 
                FinSi
                a:=q1 
            FinSi
            Regrabar(arch_index)
        FinSi 
        p:=*p.prox 
    FinMientras
    cerrar(arch_index)
FinAccion 
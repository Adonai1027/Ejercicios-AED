Accion Extraordinario de Numerico Es 
 Ambiente
    CODIGO = Registro
        cod 
        precio 
        desc 
    FinRegistro
    arch_index
    index 

    CAJA1 = Registro
        compra
        prox:Puntero a CAJA1
    FinRegistro
    c1:Puntero a CAJA1

    CAJA2 = Registro
        efectivo
        prox:Puntero a CAJA2
    FinRegistro
    c2:Puntero a CAJA2

    CAJA3 = Registro
        transferencia
        cupones
        prox:Puntero a CAJA3
    FinRegistro
    c3:Puntero a CAJA3

    NODO = Registro
        nro_carrito
        nro_articulos
        Forma_pago
        prox:Puntero a NODO
    FinRegistro
    prim,p,q,a:Puntero a NODO

    opcion:{'Agregar','Atender'}
    nro_carrito,nro_articulos:Entero 
    Forma_pago:{'E','T'} //efectivo o transferencia
    AtenderCliente(),ObtenerSigArticulo()


    tot_c1_TD,tot_c2_TD,tot_c3_TD,tot_clientes:entero
    tot_c1_TC,tot_c2_TC,tot_c3_TC:entero
 Proceso
    Esc('Agregar o  Atender');leer(opcion)
    prim:=nil 
    *c1.prox:=nil
    *c2.prox:=nil
    *c3.prox:=nil 
    Si opcion = 'Agregar' Entonces 
        Esc('Ingrese el nro de carrito, la cantidad de articulos y la forma de pago')
        leer(nro_carrito);leer(nro_articulos);leer(Forma_pago)
        Si nro_articulos < 20 Entonces 
            //carga encolada simple
            Nuevo(q)
            *q.nro_carrito:=nro_carrito
            *q.nro_articulos:=nro_articulos
            *q.Forma_pago:=Forma_pago
            *q.prox:=c1  
            Si prim = nil Entonces
                Esc('La cola esta vacia')
                prim:=q 
            sino 
                *a.prox:=q
            FinSi
            a:=q
        sino 
            Si Forma_pago = 'E' Entonces

            sino 

            FinSi
        
        FinSi
    sino 

    FinSi 

FinAccion
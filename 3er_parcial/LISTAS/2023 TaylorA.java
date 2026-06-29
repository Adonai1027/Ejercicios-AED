Accion TEMA A(prim:Puntero a NODO_S) Es
 Ambiente
    NODO_S = Regsistro
        dni
        nro_fila
        fecha_fila
        fecha_entrada

        prox:Puntero a NODO_S
    FinRegistro
    p,q,a:Puntero a NODO_S

    NODO_D = Regsistro
        fecha 
        cant 
        cod 
        dni 

        prox:Puntero a NODO_D
        ant:Puntero a NODO_D
    FinRegistro
    primd,pd,ult:Puntero a NODO_D
    
    a:Arreglo[1..12,1...31] de Enteros
    i,j:Enteros
    fecha_mayor:Enteros
    cod_encriptado:AN 
    swiftieEncriptado()
 Proceso
    primd:=nil 
    ult:=nil 
    //Cargo los nodos fechas
    Para i:=1 hasta 3 Hacer
        Nuevo(q)
        Segun i Hacer 
            =1:*q.fecha.dd:=9
               resg_nodo1:=q 
            =2:*q.fecha.dd:=10
                resg_nodo2:=q
            =3:*q.fecha.dd:=11
                resg_nodo3:=q
        FinSegun
        *q.fecha.mm:=11
        *q.fecha.aa:=2023
        *q.cant:=0
        *q.cod:=' '
        *q.dni:=' '

        Si primd = nil Entonces
            primd:=q
            ult:=q
            *q.ant:=nil 
            *q.prox:=nil 
        sino 
            *q.prox:=nil 
            *q.ant:=ult 
            *ult.prox:=q 
            ult:=q
        FinSi
    FinPara

    p:=prim
    d:=primd
    a:=nil
    cant_mayor:=LV
    Mientras p <> nil Entonces 
        cod:=swiftieEncriptado(*p.nro_fila,a[*p.fecha_fila.mm,*p.fecha_entrada.dd])
        
        Nuevo(q)
        *q.fecha:= ' '
        *q.cod:=cod 
        *q.dni:=*p.dni 
        Si (*p.fecha_entrada.dd <> 11) Entonces // 9//11 y 10/11 cargo de manera iguales
            *q.ant:=*d.ant 
            *q.prox:=d
            *(d.ant).prox:=q
            *d.ant:=q
            Si (*p.fecha_entrada.dd = 9) Entonces
                *resg_nodo1.cant:=*resg_nodo1.cant + 1
            sino 
                *resg_nodo2.cant:=*resg_nodo2.cant + 1
            FinSi
        sino
            // 11/11 cargo como nodo ultimo
            *q.ant:=ult 
            *q.prox:=nil
            *ult.prox:=q
            ult:=q 
            *resg_nodo3.cant:=*resg_nodo3.cant + 1 
        FinSi 
        
        //eliminacion de lista simple
        Si p = prim Entonces 
            prim:=*p.prox 
        sino 
            *a.prox:=*p.prox
        FinSi 
        z:=p 
        DISPONER(z)

        p:=*p.prox
    FinMientras
    Si cant_mayor < *resg_nodo1.cant Entonces 
        cant_mayor:=*resg_nodo1.cant
        fecha_mayor:=*resg_nodo1.fecha
    FinSi
    Si cant_mayor < *resg_nodo2.cant Entonces 
        cant_mayor:=*resg_nodo2.cant
        fecha_mayor:=*resg_nodo2.fecha
    FinSi
    Si cant_mayor < *resg_nodo2.cant Entonces 
        cant_mayor:=*resg_nodo2.cant
        fecha_mayor:=*resg_nodo2.fecha
    FinSi
    Escribir('La fecha en la que hubo mayor cantidad de entradas fue,'fecha_mayor,'con un total de,',cant_mayor)
FinAccion
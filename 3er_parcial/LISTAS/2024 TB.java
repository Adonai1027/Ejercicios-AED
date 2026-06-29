Accion 2024TB (prim:Puntero a NODO_S),(primc:Puntero a NODO_C) Es
 Ambiente
    NODO_S = Registro
        id_usu
        fecha_op
        calif
        serv_calif
        prox:Puntero a NODO_S
    FinRegistro
    p:Puntero a NODO_S

    USUARIOS = Registro
        usuario 
        ayp 
        dni 
        direc
        correo
        categoria
    FinRegistro
    arch_index
    index 

    NODO_C = Registro
        cod_desc 
        porc_desc
        prox:Puntero a NODO_C
    FinRegistro
    c:Puntero a NODO_C

    NODO_D = Registro
        id_usu
        ayp 
        correo
        cant_op
        prom_op
        desc 
        ant:Puntero a NODO_D
        prox:Puntero a NODO_D
    FinRegistro
    primd,ult,d,q:Puntero a NODO_D

    resg_fechaOp:fecha 
    resg_servCalif,cant_calif,cant_op,i,resg_usu:Entero
    
 Proceso
    abrir e/(arch_index)
    p:=prim 
    c:=primc
    primd:=nil 
    ult:nil 
    Mientras p <> nil Hacer
        index.usuario:=*p.id_usu
        Leer(arch_index,index)
        Si Existe Entonces
            resg_usu:=*p.id_usu
            resg_fechaOp:=*p.fecha_op
            cant_op:=0
            cant_calif:=0
            Mientras resg_usu = *p.id_usu Hacer
                Si *p.fecha_op < resg_fechaOp Hacer
                    resg_fechaOp:=*p.fecha_op
                    resg_servCalif:=*p.serv_calif
                FinSi
                cant_op:=cant_op + 1
                cant_calif:=cant_calif + *p.calif
                p:=*p.prox 
            FinMientras 

            Si bonificacion(index.categoria,resg_servCalif) Entonces
                Nuevo(q)
                *q.id_usu:=*p.id_usu
                *q.ayp:=index.ayp
                *q.correo:=index.correo
                *q.cant_op:=cant_op
                *q.prom_op:=cant_calif/cant_op
                *q.desc:=*c.porc_desc
                c:=*c.prox 
                Si primd = nil Entonces
                    primd:=q 
                    ult:=q 
                    *q.prox:=nil 
                    *q.ant:=nil
                sino 
                    d:=primd
                    Mientras (d <> nil) y (*q.prom_op < *d.prom_op) Hacer
                        d:=*d.prox
                    FinMientras
                    Si d = primd Entonces
                        primd:=q
                        *q.prox:=d 
                        *d.ant:=q
                        *q.ant:=nil 
                    sino
                        Si d = nil Entonces
                            *q.prox:=nil
                            *q.ant:=ult 
                            *ult.prox:=q
                            ult:=q
                        sino 
                            *q.prox:=d 
                            *q.ant:=*d.ant 
                            *(d.ant).prox:=q
                            *d.ant:=q
                        FinSi
                    FinSi
                FinSi
            sino 
                Esc('No esta disponible el descuento')
            FinSi
        sino 
            Esc('No existe el usuario')
        FinSi 
        p:=*p.prox
    FinMientras

    d:=primd
    Para i:=1 hasta 10 Hacer
        Esc(*d.id_usu,*d.ayp,*d.correo)
        d:=*d.prox 
    FinPara

    d:=ult 
    Para i:=1 hasta 10 Hacer
        Esc(*d.id_usu,*d.ayp,*d.correo)
        d:=*d.ant 
    FinMientras

    Cerrar(arch_index)
FinAccion
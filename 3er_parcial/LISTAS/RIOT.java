Accion Listas (primc:Puntero a NODO_C) Es
 Ambiente
    CLIENTE = Registro
        nombre_cliente
        localizacion
        nivel
    FinRegistro
    arch_clie:
    reg_clie

    NODO_C = Registro
        servidor
        localizacion
        estado
        cantUsuarios
        latencia

        prox:Puntero a NODO_C
    FinRegistro
    c:Puntero a NODO_C

    NODO_S = Registro
        nombre_cliente
        servidor
        localizacion
        nivel
        
        prox:Puntero a NODO_S
    FinRegistro
    prims,p,q,a:Puntero a NODO_S

    NODO_D = Registro
        latencia 
        serv_libre
        cantUsuarios

        ant:Puntero a NODO_D
        prox:Puntero a NODO_D
    FinRegistro
    primd,d,ult:Puntero a NODO_D

 Proceso
    abrir e/(arch_clie);leer(arch_clie,reg_clie)
    c:=primc
    prims:=nil
    a:=nil 
    Mientras NFDA(arch_clie) Hacer
        Si (*c.localizacion = reg_clie.localizacion) Entonces //busco que sean de la misma loc
            Si (*c.estado = 'Libre') y (*c.cantUsuarios < 11) Entonces
                //carga encolada
                Nuevo(q) 
                *q.nombre_cliente:=reg_clie.nombre_cliente
                *q.servidor:=*c.servidor
                *q.localizacion:=*c.localizacion
                *q.nivel:=reg_clie.nivel
                *q.prims:=nil 
                Si (prims = nil) Entonces
                    prims:=q 
                sino
                    *a.prox:=q 
                FinSi
                a:=q
            FinSi 
            c:=*c.prox
        FinSi

        leer(arch_clie,reg_clie)
    FinMientras
    c:=primc
    primd:=nil 
    Mientras (*c.prox <> primc) Hacer
        Si (*c.estado = 'Libre') y (*c.cantUsuarios < 10) Entonces
            //carga ordenada doble
            Nuevo(q2) 
            *q2.latencia:=*c.latencia
            *q2.serv_libre:=*c.servidor
            *q2.cantUsuarios:=*c.cantUsuarios
            Si primd = nil Entonces
                primd:=q2
                ult:=q2 
                *q2.prox:=nil 
                *q2.ant:=nil 
            sino 
                d:=primd
                Mientras (d <> nil) y (*q2.latencia < *c.latencia) Hacer
                    d:=*d.prox
                FinMientras
                Si d = primd Entonces
                    *q2.prox:=p
                    *q2.ant:=NIL
                    *p.ant:=q2 
                    primd:=q2
                sino
                    Si d = nil Entonces
                        *q2.prox:=nil 
                        *q2.ant:=ult 
                        *ult.prox:=q2 
                        ult:=q2
                    sino 
                        *q.prox:=p 
                        *q.ant:=p.ant
                        *(p.ant).prox:=q2 
                        *p.ant:=q2 
                    FinSi
                FinSi
            FinSi
        FinSi
        c:=*c.prox
    FinMientras
    Cerrar(CLIENTE)
FinAccion
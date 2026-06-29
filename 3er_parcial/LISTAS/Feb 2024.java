Accion 2024Recu (primc:Puntero a NODO_C)Es 
 Ambiente
    FECHA = Registro
        aa 
        mm 
        dd 
    FinRegistro

    CORREO = Registro
        mail
        msj_corrupto
        fecha_ult:FECHA
    FinRegistro
    arch:Archivo secuencial de CORREO ord por mail
    reg:CORREO

    INDEXADO = Registro
        mail
        msj 
        corrupto:
    FinRegistro
    arch_index:Archivo indexado por mail
    index:INDEXADO

    NODO_C = Registro
        mail //peligroso
        prox:Puntero a NODO_C
    FinRegistro
    c:Puntero a NODO_C

    NODO_S = Registro
        mail 
        msj_corrupto
        prox:=Puntero a NODO_S
    FinRegistro
    prims,q,p,a:Puntero a NODO_S
    tot_cuentas,cont_pel:Entero 
    peligroso:Booleano

 Proceso
    abrir e/(arch);leer(arch,reg)
    abrir e/(arch_index)
    c:=primc
    prims:=nil 
    cont_pel:=0
    tot_cuentas:=0
    Mientras NFDA(arch) Hacer
        index.mail:=reg.mail
        Leer(arch_index,index)
        Si EXISTE Entonces
            tot_cuentas:=tot_cuentas + 1
            Si index.corrupto = 'SI' Entonces
                c:=primc
                Mientras (*c.prox <> prim) y (reg.mail <> *c.mail) Hacer //busco si el mail es peligroso
                    c:=*c.prox
                FinMientras
                peligroso:=Falso
                Si (reg.mail = *c.mail) Entonces
                    cont_pel:=cont_pel + 1
                    peligroso:=Verdadero
                FinSi
                Mientras (reg.mail = index.mail) Hacer
                    reg.msj_corrupto:=reg.msj_corrupto + 1

                    leer(arch_index,index)
                FinMientras
                Si (peligroso = Verdadero) Entonces
                    reg.msj_corrupto:=reg.msj_corrupto * 10
                FinSi
                Grabar(arch,reg)
                //carga ordenada lista simple descendente
                a:=nil 
                Nuevo(q)
                *q.mail:=reg.mail 
                *q.msj_corrupto:=reg.msj_corrupto
                p:=prims
                Mientras (p<>nil) y (*q.msj_corrupto < *p.msj_corrupto) Hacer 
                    a:=p 
                    p:=*p.prox 
                FinMientras
                Si a = nil Entonces
                    *q.prox:=prims 
                    prims:=q 
                sino 
                    *a.prox:=q 
                    *q.prox:=p 
                FinSi
            FinSi
        FinSi 
        Leer(arch,reg)
    FinMientras
    Esc('Ranking Generado')
    p:=prims 
    Mientras p <> nil Hacer
        Esc('MAIL',*p.mail,'Cantidad de Mensajes Corruptos:',*p.msj_corrupto)
        p:=*p.prox 
    FinMientras
    Esc('El porcentaje de cuentas que son Peligrosas es de',(cont_pel/tot_cuentas) * 100)
    Cerrar(arch)
FinAccion
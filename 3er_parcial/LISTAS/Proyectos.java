Accion Proyectos (prim:Puntero a NODO_S) Es
 Ambiente
    PROYECTOS = Registro
        codigo
        nombre
        tipo
        cant_errores
    FinRegistro
    arch:
    reg:PROYECTOS

    NODO_S = Registro
        desc
        estado
        prox:Puntero a NODO_S
    FinRegistro
    p:Puntero a NODO_S

    NODO_D1 = Registro //estado resuelto
        codigo
        nombre
        ant:
        prox 
    FinRegistro
    primd1,d1,q1,ult1:Puntero a NODO_D1

    NODO_D2 = Registro //estado resuelto o proceso
        codigo
        nombre
        porc_error //por este campo se ordena
        ant:
        prox 
    FinRegistro
    primd2,d2,q2,ult2:Puntero a NODO_D2

    proy_no_resueltos,cant:entero
    porc:=real 
 Proceso
    abrir e/(arch);leer(arch,reg)
    p:=prim
    primd1:=nil
    ult1:=nil 
    primd2:=nil 
    ult2:=nil 
    Mientras NFDA(arch) Hacer
        Para i:=1 hasta (reg.cant_errores) Hacer
            bandera:=falso
            Si *p.estado = 'R' Entonces
                bandera:=true 
                cant:=cant + 1
            sino 
                Si *p.estado = 'O' Entonces
                    cant:=cant + 1
                sino
                    proy_no_resueltos:=proy_no_resueltos + 1
                FinSi
            FinSi 
            p:=*p.prox
        FinPara
        porc:=(cant/reg.cant_errores)*100 
        Si bandera Entonces //primer lista doble
            //carga encolada lista doble
            Nuevo(q1)
            *q1.codigo:=reg.codigo
            *q1.nombre:=reg.nombre
            Si primd1 = nil Entonces
                primd1:=q1
                ult1:=q1 
                *q1.prox:=nil
                *q1.ant:=nil
            sino 
                *q1.ant:=nil 
                *q1.prox:=ult
                *ult.ant:=q1  
                ult1:=q1
            FinSi   
        sino 
            Si porc > 49 Entonces //carga ordenada segunda lista
                Nuevo(q2)
                *q2.codigo:=reg.codigo
                *q2.nombre:=reg.nombre
                *q2.porc_error:=porc
                Si primd2=nil Entonces
                    primd2:=q2 
                    ult2:=q2 
                    *q2.prox:=nil 
                    *q2.ant:=nil 
                sino 
                    d2:=primd2 
                    Mientras (d2 <> nil) y (*q2.porc_error < *d2.porc_error) Hacer
                        d2:=*d2.prox
                    FinMientras
                    Si d2 = primd2 Entonces
                        primd2:=q2 
                        *q.prox:=d2 
                        *q.ant:=nil 
                        *d2.ant:=q2 
                    sino 
                        Si d2 = nil Entonces
                            *q2.prox:=nil 
                            *q2.ant:=d2 
                            *ult2.prox:=q2
                            ult2:=q2 
                        sino 
                            *q2.prox:=d2 
                            *q2.ant:=*d2.ant 
                            *(d2.ant).prox:=q2 
                            *d2.ant:=q2
                        FinSi
                    FinSi
                FinSi
            FinSi
        FinSi
        
        cant:=0
        Leer(arch,reg)
    FinMientras
    Esc('La cantidad de proyectos no resueltos fue de:',proy_no_resueltos)
    Cerrar(arch)
FinAccion
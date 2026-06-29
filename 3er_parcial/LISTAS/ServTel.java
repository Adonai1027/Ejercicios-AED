Accion serviciostelefonicos primc:Puntero a NODO_C Es
 Ambiente
    Numeros = Registro
        nro_cel
        dni_cliente
    FinRegistro
    arch:Archivo secuencial de Numeros
    reg:Numeros

    NODO_C = Registro 
        iden_regalo
        cant_disp
        prox:Puntero a NODO_C
    FinRegistro 
    c:Puntero a NODO_C

    NODO_S = Registro
        dni_cliente
        iden_regalo
        prox:Puntero a NODO_S
    FinRegistro
    prim,p,q,a:Puntero a NODO_S

    NODO_D1 = Registro
        iden_regalo
        cant_disp
        ant,prox:Puntero a NODO_D1
    FinRegistro
    primd1,d1,ult1,q1:Puntero a NODO_D1

    NODO_D2 = Registro
        dni_cliente
        ant,prox:Puntero a NODO_D2
    FinRegistro
    primd2,d2,ult2,q2:Puntero a NODO_D2
 Proceso
    abrir e/(arch);leer(arch,reg)
    c:=primc
    prim:=nil 
    resg:=reg.dni_cliente
    a:=nil 
    primd1:=nil 
    primd2:=nil 
    Mientras NFDA(arch) Hacer 
        Si resg <> reg.dni_cliente Entonces
            //carga lista simple encolada
            Nuevo(q) 
            *q.dni_cliente:=reg.dni_cliente
            *q.iden_regalo:=*c.iden_regalo
            *q.prox:=nil 
            Si prim = nil Entonces
                prim:=q 
            sino 
                *a.prox:=q
            FinSi
            a:=q 
        
            //trato las dos listas dobles
            Si reg.cant_disp > 0 Entonces //primer lista doble
                //carga encolada lista doble
                Nuevo(q1)
                *q1.iden_regalo:=*c.iden_regalo
                *q1.cant_disp:=*c.cant_disp
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
            sino //segunda lista doble
                //carga encolada lista doble
                Nuevo(q2)
                *q2.dni_cliente:=reg.dni_cliente
                Si primd2 = nil Entonces
                    primd2:=q2
                    ult2:=q2 
                    *q2.prox:=nil
                    *q2.ant:=nil
                sino 
                    *q2.ant:=nil 
                    *q2.prox:=ult
                    *ult.ant:=q2  
                    ult2:=q2
                FinSi 
            FinSi 
        FinSi        


        resg:=reg.dni_cliente
        avz(arch,reg)
        Si *c.prox <> primc Entonces
            c:=*c.prox 
        FinSi 
    FinMientras
FinAccion
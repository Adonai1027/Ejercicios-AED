Accion TEMA B (primd:Puntero a NODO_D) Es
 Ambiente
    NODO_D = Registro
        fecha
        cant
        cod
        dni

        prox:Puntero a NODO_D
        ant:Puntero a NODO_D
    FinRegistro
    d,resg_nodo,ult:Puntero a NODO_D

    NODO_S = Registro
        nro_fila
        cod 
        dni
    FinRegistro
    prim,p,a:Puntero a NODO_S
    cant_swifties:entero
    fecha_det:fecha
    habilitacion:booleano

 Proceso
    d:primd
    prim:=nil 
    Esc('Ingrese la fecha determinda'); Leer(fecha_det)
    cant_swifties:=0
    Mientras (d <> nil) Hacer 
        //Saco el primer nodo en blanco
        Si (*d.fecha <> ' ') Entonces
            resg_nodo:=d
        sino    
            habilitacion:=swiftieHabilitada(*d.cod,*d.dni)
            Si (habilitacion) Entonces //ingresaron
                //eliminacion lista doble
                Si d = Ult entonces
                    ult:=*d.ant
                    *ult.prox:=nil  
                sino 
                    *(*d.Prox).Ant:= *d.Ant
                    *(*d.Ant).Prox:= *d.Prox
                FinSi 
                aux:=d 
                d:=*d.ant 
                DISPONER(aux)
                *resg_nodo.cant:=*resg_nodo.cant - 1
                Si *d.fecha = fecha_det Entonces
                    cant_swifties:=cant_swifties + 1
                FinSi
            sino 
                nro_lugar_fila:=DesencriptarLugarEnLaFila(*d.cod)
                a=nil 
                Nuevo(q)
                *q.nro_fila:=nro_lugar_fila
                *q.cod:=*d.cod
                *q.dni:=*d.dni
                p:=prim
                
                Mientras (p<>nil) y (*q.nro_fila > *p.nro_lugar_fila) Hacer
                    a:=p 
                    p:=*p.prox
                FinMientras
                Si (a = nil) Entonces
                    *q.prox:=prim
                    prim:=q
                sino 
                    *q.prox:=p 
                    *a.prox:=q
                FinSi
            FinSi 
        FinSi
        d:=*d.prox
    FinMientras
    Escribir('La cantidad de swifties que ingresaron en una fecha determinada son de',cant_swifties)
FinAccion
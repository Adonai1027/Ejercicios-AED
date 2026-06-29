Accion equipos (prim:Puntero a NODO_S) Es 
 Ambiente
    NODO_S = Registro
        pais:
        edad:(a:arreglo[1..26] de enteros)
        grupo: 
        puntos:
        Ta:
        Tr:
        prox:Puntero a NODO
    FinRegistro
    p,a:Puntero a NODO_S

    PARTIDOS = Registro
        id_partido
        equipo_l
        equipo_v
    FinRegistro
    arch:Archivo secuencial de PARTIDOS
    reg:PARTIDOS

    RESULTADOS = Registro
        id_partido
        cant_gol_e_l
        cant_gol_e_v
        tr 
        ta 
    FinRegistro
    arch_index:Archivo indexado por id_partido
    index:RESULTADOS

    NODO_D = Registro
        equipos
        cant_puntos
        ant,prox:Puntero a NODO_D
    FinRegistro
    primd,d,ult,q:Punt
    bandera:boolean
    mayor_cant:entero 
 Proceso
    abrir e/(arch);leer(arch,reg)
    abrir e/(arch_index)
    p:=prim 
    primd:=nil 
    ult:=nil 
    Mientras NFDA(arch) Hacer
        index.id_partido:=reg.id_partido
        leer(arch_index,index)
        Si EXISTE Entonces
            Si (index.cant_gol_e_l > index.cant_gol_e_v) Entonces //gana local +3
                p:=prim 
                Mientras (p <> nil) y (*p.pais <> reg.equipo_l) Hacer 
                    p:=*p.prox 
                FinMientras
                *p.puntos:=*p.puntos + 3
                *p.tr:=*p.tr + index.tr 
                *p.dif_gol:=*p.dif_gol + (index.cant_gol_e_l - index.cant_gol_e_v)
            sino 
                Si (index.cant_gol_e_l < index.cant_gol_e_v) Entonces //gana visitante +3
                    p:=prim 
                    Mientras (p <> nil) y (*p.pais <> reg.equipo_v) Hacer 
                        p:=*p.prox 
                    FinMientras 
                    *p.puntos:=*p.puntos + 3
                    *p.tr:=*p.tr + index.tr 
                    *p.dif_gol:=*p.dif_gol + (index.cant_gol_e_v - index.cant_gol_e_l)
                sino //queda en empate +1
                    p:=prim 
                    Mientras (p <> nil) y (*p.pais <> reg.equipo_l) Hacer //equipo local
                        p:=*p.prox 
                    FinMientras
                    *p.puntos:=*p.puntos + 1
                    *p.tr:=*p.tr + index.tr  

                    p:=prim 
                    Mientras (p <> nil) y (*p.pais <> reg.equipo_v) Hacer //equipo visitante
                        p:=*p.prox 
                    FinMientras
                    *p.puntos:=*p.puntos + 1
                    *p.tr:=*p.tr + index.tr  
                FinSi
            FinSi
        FinSi 
    FinMientras

    p:=prim 
    Mientras p <> nil Hacer
        resg:=p
        bandera:=falso
        Si (*p.puntos) > 4 Entonces
            bandera:=true
        sino 
            Si (*p.puntos) > 4 y (*p.dif_gol > 1) Entonces
                bandera:=true 
            sino 
                Si (*p.puntos) > 3 Entonces
                    resg:=prim
                    Mientras (resg <> nil) y (bandera = falso)  Hacer
                        Si *resg.puntos = 9 Entonces
                            bandera:=true 
                        FinSi
                        resg:=*resg.prox
                    FinMientras
                FinSi
            FinSi
        FinSi

        //carga de lista doble
        Si bandera Entonces
            Nuevo(q)
            *q.equipo:=*p.pais
            *q.cant_puntos:=*p.puntos
            Si primd = nil Entonces
                primd:=q
                ult:=q 
                *q.prox:=nil 
                *q.ant:=nil 
            sino 
                d:=primd
                Mientras (d <> nil) y (*q.cant_puntos < *d.cant_puntos)
                    d:=*d.prox
                FinMientras
                Si d = primd Entonces
                    primd:=q 
                    *q.ant:=nil 
                    *q.prox:=d 
                    *d.ant:=q 
                sino 
                    Si d = nil Entonces
                        *q.prox:=nil 
                        *q.ant:=ult
                        *ult.prox:=q 
                        ult:=q
                    sino 
                        *d.ant:=q 
                        *(q.ant).prox:=q 
                        *q.ant:=*d.ant 
                        *q.prox:=d 
                    FinSi
                FinSi
            FinSi
        FinSi   

        p:=*p.prox 
    FinMientras

    p:=prim 
    mayor_cant:=0
    Mientras p <> nil Hacer 
        Si *p.tr > mayor_cant Entonces 
            resg_equipo:=*p.pais 
            mayor_cant:=*p.tr 
        FinSi 
        p:=*p.prox
    FinMientras
    Esc('El equipo con mayor cantidad de tarjetas rojas fue',resg_equipo,'con un total de,'mayor_cant)

    d:=primd
    Mientras d <> nil Hacer
        Esc('Los equipos que pasaron a la siguiente fase fueron',*d.equipo)
        d:=*d.prox 
    FinMientras

    Cerrar(arch)
    Cerrar(arch_index)

FinAccion
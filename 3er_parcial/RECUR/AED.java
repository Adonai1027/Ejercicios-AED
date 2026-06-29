Accion recursividad (prime:Puntero a NODO) Es
 Ambiente
    NODO = Registro
        legajo
        ayp 
        com
        notas:Arreglo[1..5] de Entero

        prox:Puntero a NODO
    FinRegistro
    pe:Puntero a NODO
    prims,ps,qs,as:Puntero a NODO

    Funcion Aprobado(notas:Arreglo[1..5],posc,cant:entero):boolean
     Ambiente
        aux:Entero 
     Proceso  
        Si (posc = 1) Entonces
            aux:=cant  //2
            Si (notas[1] > 5) Entonces
                aux:=cant + 1 //2 + 1
            finsi
            Si aux > 2 Entonces
                Aprobado:=true 
            sino 
                Aprobado:=false
            FinSi
        sino 
            Si notas[posc] > 5 Entonces
                Aprobado:=Aprobado[notas,posc-1,cant + 1]
            sino 
                Aprobado:=Aprobado[notas,posc-1,cant]
            FinSi
        FinSi   
    FinFuncion
 Proceso
    p:=prim
    prims:=nil 
    Mientras pe <> nil Entonces
        Si Aprobado(*p.notas,5,0)=true Entonces
            as:=nil 
            Nuevo(qs)
            *qs.legajo:=*pe.legajo
            *qs.ayp:=*pe.ayp
            *qs.com:=*pe.com 
            *qs.notas:=*pe.notas
            Si prims = nil entonces 
                *qs.prox:= nil 
                prims:= qs 
            Si No 
                ps:= prims 
                Mientras ps <> nil y *qs.legajo < *pe.Legajo hacer 
                    as:= ps 
                    ps:= *ps.prox 
                Fin Mientras 

                Si as = nil entonces 
                    *qs.prox:= prims
                    prims:= qs 
                Sino 
                    *qs.prox:= ps 
                    *as.prox qs 
                Fin Si  
            Fin Si  
        FinSi 
        pe:=*pe.prox
    FinMientras
FinAccion
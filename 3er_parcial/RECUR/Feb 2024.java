Accion 2024Recu (primd:Puntero a NODO_D) Es
 Ambiente
    NODO_D = Registro 
        num_aleatorio
        ant:Puntero a NODO_D
        prox:Puntero a NODO_D
    FinRegistro
    ult,d,z:Puntero a NODO_D
    
    Funcion PERFECTOS(num,divisor,suma:Entero):Booleano
        Si divisor = 1 Entonces 
            Si suma + 1 = num Entonces
                PERFECTOS:=True
            sino 
                PERFECTOS:=False 
            FinSi 
        sino 
            Si (num MOD divisor) = 0 Entonces
                PERFECTOS:=PERFECTOS(num,divisor - 1,suma + divisor)
            sino 
                PERFECTOS:=PERFECTOS(num,divisor - 1,suma)
            FinSi
        FinSi
    FinFuncion
 Proceso
    d:=primd
    Mientras d <> nil Hacer 
        Si PERFECTOS(*d.num_aleatorio,*d,num_aleatorio-1,0) = True Entonces    
            Si d = primd Entonces
                primd:=*d.prox 
                *primd.ant:=nil 
            sino 
                Si d = ult Entonces
                    ult:=*d.ant 
                    *ult.prox:=nil 
                sino 
                    *(*d.ant).prox:=*d.prox 
                    *(*d.prox).ant:=*d.ant
                FinSi
            FinSi
            z:=d 
            Disponer(z) 
        FinSi
        d:=*d.prox 
    FinMientras
FinAccion
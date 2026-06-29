Accion RECU_B (prim:Puntero a NODO) Es
 Ambiente
    NODO = Registro
        id_compra
        fecha_compra
        metodo_pago
        nro_tarjeta
        monto
        ant:Puntero a NODO
        prox:Puntero a NODO
    FinRegistro
    ult,p,z:Puntero a NODO

    Funcion validar_tarjeta(a:Arreglo[1..20],posc,suma:Entero):boolean Es 
     Ambiente 
        aux:=Entero
     Proceso
        Si posc = 1 Entonces //caso base
            aux:=a[posc] * 2 //9*2 = 18
            Si aux > 9 Entonces
                aux:= (aux DIV 10) + (aux MOD 10) //18 --> 1 + 8 = 9
            FinSi
            aux:=aux + suma 
            Si (aux MOD 5) = 0 Entonces
                validar_tarjeta:=true 
            sino 
                validar_tarjeta:=false
            FinSi
        sino //caso recursivo 
            Si (posc MOD 2) <> 0 Entonces //solo posiciones impares
                aux:= a[posc] * 2 //7*2 = 14
                Si aux > 9 Entonces
                    aux:=(aux MOD 10) + (aux DIV 10) //14 --> 1 + 4 = 5
                FinSi
            FinSi
            validar_tarjeta:=validar_tarjeta(a,posc-1,suma + aux) 
        FinSi 
    FinFuncion
 Proceso
    p:=prim 
    Mientras p <> nil Entonces 
        Si *p.metodo_pago = 'TC' Entonces
            Si validar_tarjeta(*p.nro_tarjeta[1..20],20,0) = Falso Entonces
                //eliminar nodo 
                Si prim = ult Entonces
                    prim:=nil 
                    ult:=nil 
                sino 
                    Si p = prim Entonces
                        prim:=*p.prox 
                        *prim.ant:=nil 
                    sino 
                        Si p = ult Entonces
                            ult:=*p.ant 
                            *ult.prox:=nil 
                        sino 
                            *p.ant:=*p.prox 
                            *(*p.prox).ant:=*p.ant
                        FinSi
                    FinSi
                FinSi
                z:=p
                Disponer(z) 
            FinSi
        FinSi
        p:=*p.prox 
    FinMientras 
FinAccion
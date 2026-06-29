Accion nov2022 (prim:puntero a NODO) Es 
 Ambiente
    NODO = Registro
        dni
        nyp
        nro_cama
        nro_hab
        pa_sistolica:(A: Arreglo [1..4] de Entero)
        pa_diastolica:(A: Arreglo [1..4] de Entero)

        prox:puntero a NODO
    FinRegistro
    p,a:puntero a NODO

    Funcion PromPresion ( A: Arreglo [1..4] de Entero,Pos,Suma: Entero): Real 
		Si Pos = 4 entonces 
			PromPresion:= (A[Pos]+Suma)/4
		sino 
			PromPresion:= PromPresion(A,Pos+1,Suma+A[Pos])
		FinSi 	
	FinFuncion
	aux1,aux2:Real
 Proceso
    aux1:=0
    aux2:=0
    p:=prim 
    Mientras (p <> nil) Hacer 
        aux1:=PromPresion(*p.pa_sistolica,1,0)
        aux2:=PromPresion(*p.pa_diastolica,1,0)
        Si (aux1 < 120) y (aux2 < 80) entonces
            Si p = prim entonces
                prim:=nil
            sino 
                *a.prox:=*p.prox
            FinSi
            z:=p 
            p:=*p.prox
            Disponer(z)
        sino 
            a:=p
            p:=*p.prox
        FinSi 
    FinMientras 
FinAccion
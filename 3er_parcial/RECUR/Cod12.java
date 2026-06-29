parcial
	ambiente
		nodo:registro
			cod:entero
            prox:Puntero a nodoP
		fin_registro
        prim,p:Puntero a nodoP

		Funcion Primos(n,divisor: enteros): booleano
            Si (n <> divisor) y (divisor <> 1) enrtonces 
                Si (n MOD divisor) = 0 entonces 
                    Primos:= false  
                Si No 
                    Primos:= Primos(n,divisor-1) 
                Fin Si 
            Si No 
                Si divisor = 1 entonces 
                    Primos:=true  
                sino 
                    Primos:= Primos(n,divisor - 1)
                Fin Si 
            Fin Si 
        Fin Funcion

		procedimiento verificarCod(x:entero) Es
			N:=(x div 10^k-1) mod 10
            divisor:=n 
			band:=primo(N,divisor)
			si band="verdadero" entonces
				esc(x)
			sino
				esc("numero no primo")
			fin_si
		fin_procedimiento
		
	proceso
		p:=prim
		esc("ingrese un valor k de posicion del 1 al 12")
		repetir	
			verificarCod(*p.cod)
			p:=*p.prox
		hasta *p.prox=prim
	fin_proceso
fin_accion
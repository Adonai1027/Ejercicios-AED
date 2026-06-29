
Accion Recuperatorio_2022_Ejer1 (Prim: puntero a Nodo) ES

Ambiente

	Nodo=Registro
		pais: AN(20)
		edad_jugadores: arreglo[1...26] de enteros
		grupo: caracter
		puntos: N(2)
		T_amarillas: N(2)
		T_rojas: N(2)
		prox: puntero a Nodo
	Fin Registro

	P,A,Q,ant_Q: puntero a Nodo

	i: entero
	menor_prom_edad,mayor_prom_edad: entero
	equip_menor_edad,equip_mayor_edad: alfanumerico 

	Funcion Promedio(Arr: arreglo[1...26] de enteros, j: entero): entero ES

		Si (j = 26) entonces

			Promedio:= Arr[j] div 26
		SiNo
			Promedio:= (Arr[j] div 26) + Promedio(Arr[],j + 1)
		Fin Si
	Fin Funcion

Proceso
	menor_prom_edad:= HV
	mayor_prom_edad:= LV

	Para (i:= 1 hasta 32) hacer
		Esc("Ingrese el pais del equipo. A continuacion, ingrese el grupo al que pertenece")
		Leer(aux_pais)
		Leer(aux_grupo)

		P:= Prim
		Mientras (P <> Nill) y (*P.pais <> aux_pais) hacer
			A:= P
			P:= *P.prox
		Fin Mientras

		Si (P = Nill) entonces
			Esc("ERROR!! Pais no encontrado")
		SiNo
			*P.grupo:= aux_grupo
			aux_promedio:= Promedio(edad_jugadores,1)

			Si (aux_promedio < menor_prom_edad) entonces
				menor_prom_edad:= aux_promedio
				equip_menor_edad:= *P.pais
			Fin Si

			Si (aux_promedio > mayor_prom_edad) entonces
				mayor_prom_edad:= aux_promedio
				equip_mayor_edad:= *P.pais
			Fin Si
		Fin Si
	Fin Para

	// ORDENAR
	
	ant_Q:= Prim		//nodo anterior a Q
	Q:= *Prim.prox		//Q es el nodo a ordenar

	Mientras (Q <> Nill) hacer

		P:= Prim

		Mientras (P <> Nill) y (*P.grupo < *Q.grupo) hacer
			A:= P
			P:= *P.prox
		Fin Mientras

		*ant_Q.prox:= *Q.prox	//re-enlaza punteros anteriores y posteriores a Q
		*Q.prox:= P 			//ordena Q con su siguiente

		//ordena Q con su anterior
		Si (P = Prim) entonces
			Prim:= Q
		SiNo		
			*A.prox:= Q
		Fin Si

		Q:= *(*ant_Q.prox).prox		//ahora nos colocamos en el elemento que le SEGUIA a Q
	Fin Mientras 

	Esc("El equipo con mayor edad promedio es: ",equip_mayor_edad)
	Esc("El equipo con menor edad promedio es: ",equip_menor_edad)
Fin Accion 
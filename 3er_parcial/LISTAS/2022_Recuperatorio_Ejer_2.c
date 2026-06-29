Accion Recuperatorio_2022_Ejer2 (Prim: puntero a Nodo) ES

Ambiente
	
	Partidos=Registro
		ID_partido: AN(5)
		Equip_loc: AN(20)
		Equip_vis: AN(20)
	Fin Registro

	Resultado=Registro
		ID_partido: AN(5)
		goles_local: N(2)
		goles_visit: N(2)
		T_amarillas: N(2)
		T_rojas: N(2)
	Fin Registro

	Nodo=Registro
		dato=Registro
			pais: AN(20)
			grupo: caracter
			puntos: N(2)
			goles_a_favor: N(2)
			goles_en_contra: N(2)
			T_amarillas: N(2)
			T_rojas: N(2)
		Fin Registro
		prox: puntero a Nodo
	Fin Registro

	Nodo_D=Registro
		dato=Registro
			pais: AN(20)
			grupo: caracter
			puntos: N(2)
			goles_a_favor: N(2)
			goles_en_contra: N(2)
			T_amarillas: N(2)
			T_rojas: N(2)
		Fin Registro
		ant,prox: puntero a Nodo_D
	Fin Registro

	Arch_Part:
	reg_part:
	Arch_Res:
	reg_res:

	P: puntero a Nodo
	New_Prim, Ult, Q, R: puntero a Nodo_D

	mas_T_rojas: entero
	equip_T_rojas: alfanumerico
	copiar_a_Lista_Dbl: booleano

Proceso
	Abrir E/(Arch_Part)
	Leer(Arch_Part,reg_part)
	Abrir E/(Arch_Res)

	Mientras NFDA(Arch_Part) hacer

		reg_res.ID_partido:= reg_part.ID_partido
		Leer(Arch_Res,reg_res)

		Si (existe) entonces

			P:= Prim
			Mientras (P <> Nill) y (*P.dato.pais <> reg_part.Equip_loc) hacer
				P:= *P.prox
			Fin Mientras

			*P.dato.goles_a_favor:= *P.dato.goles_a_favor + reg_res.goles_local
			*P.dato.goles_en_contra:= *P.dato.goles_en_contra + reg_res.goles_visit
			*P.dato.T_amarillas:= *P.dato.T_amarillas + reg_res.T_amarillas
			*P.dato.T_rojas:= *P.dato.T_rojas + reg_res.T_rojas

			Si (reg_res.goles_local > reg_res.goles_visit) entonces

				*P.dato.puntos:= *P.dato.puntos + 3
			SiNo
				Si (reg_res.goles_local = reg_res.goles_visit) entonces

					*P.dato.puntos:= *P.dato.puntos + 1
				Fin Si
			Fin Si
		Fin Si

		Leer(Arch_Part,reg_part)
	Fin Mientras

	New_Prim:= Nill
	Ult:= Nill
	P:= Prim
	mas_T_rojas:= LV

	Mientras (P <> Nill) hacer

		copiar_a_Lista_Dbl:= Falso

		Si (*P.dato.puntos > 4) entonces
			copiar_a_Lista_Dbl:= Verdadero
		SiNo
			Si (*P.dato.puntos = 4) y ((*P.dato.goles_a_favor - *P.dato.goles_en_contra) > 2) entonces
				copiar_a_Lista_Dbl:= Verdadero
			Fin Si
		Fin Si

		Si (copiar_a_Lista_Dbl) entonces
			Nuevo(Q)
			*Q.dato:= *P.dato

			Esc(*P.dato.pais," paso a la siquiente fase del mundial")

			Si (New_Prim = Nill) entonces
				*Q.prox:= Nill
				*Q.ant:= Nill
				New_Prim:= Q
				Ult:= Q
			SiNo
				R:= New_Prim
				Mientras (R <> Nill) y (*R.dato.puntos < *Q.dato.puntos) hacer
					R:= *R.prox
				Fin Mientras

				Si (R = New_Prim) entonces
					*Q.prox:= New_Prim
					*Q.ant:= Nill
					*R.ant:= Q
					New_Prim:= Q
				SiNo
					Si (R = Ult) entonces
						*Q.prox:= Nill
						*Q.ant:= Ult
						*R.prox:= Q
						Ult:= Q
					SiNo
						*Q.prox:= R
						*Q.ant:= *R.ant
						*(R.ant).prox:= Q
						*R.ant:= Q
					Fin Si
				Fin Si
			Fin Si
		Fin Si

		Si (*P.dato.T_rojas > mas_T_rojas) entonces
			mas_T_rojas:= *P.dato.T_rojas
			equip_T_rojas:= *P.dato.Partidos
		Fin Si

		P:= *P.prox
	Fin Mientras

	Esc("El equipo con mas tarjetas rojas es: ",equip_T_rojas)

	Cerrar(Arch_Part)
	Cerrar(Arch_Res)
Fin Accion 
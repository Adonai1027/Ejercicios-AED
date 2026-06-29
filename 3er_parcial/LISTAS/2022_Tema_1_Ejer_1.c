
Accion Tema_1_Ejer_1_2022 (Prim_circ: puntero a Nodo_circ) ES

Ambiente
	
	Compras=Registro
		Fecha_compra: N(8)
		DNI: N(8)
		Cant_artc: N(2)
		Importe: N(6,2)
	Fin Registro

	Fidel=Registro
		DNI: N(8)
		AyN: AN(30)
		Fecha_adhesion: N(8)
		Categoria: AN(20)
	Fin Registro

	Nodo=Registro
		AyN: AN(30)
		Chances_total: N(1)
		ant, prox: puntero a Nodo
	Fin Registro

	Nodo_circ=Registro
		Chances_extra: N(2)
		prox: puntero a Nodo_circ
	Fin Registro

	Arch_C: archivo secuencial de Compras ordenado por Fecha_compra
	reg_C: Compras
	Arch_F: archivo de Fidel indexado por DNI
	reg_F: Fidel

	Prim, Ult, P, Q: puntero a Nodo
	C: puntero a Nodo_circ

Proceso
	Abrir E/(Arch_C)
	Leer(Arch_C,reg_C)
	Abrir E/(Arch_F)

	Prim:= 0
	Ult:= 0
	C:= Prim_circ

	Mientras NFDA(Arch_C) hacer

		reg_F.DNI:= reg_C.DNI
		Leer(Arch_F,reg_F)

		Si (existe) entonces

			Si (Prim = Nill) entonces
				Nuevo(P)
				*P.AyN:= reg_F.AyN
				*P.Chances_total:= 5

				*P.ant:= Nill
				*P.prox:= Nill
				Prim:= P
				Ult:= P 
			SiNo
				Q:= Prim
				Mientras (Q <> Nill) y (*Q.AyN < reg_F.AyN) hacer
					Q:= *Q.prox
				Fin Mientras

				Si (*Q.AyN = reg_F.AyN) entonces

					*Q.Chances_total:= *Q.Chances_total + (reg_C.Importe div 100)
					
					Si (reg_F.Categoria = "Black") entonces

						vueltas:= Tirar()
						cont:= 0

						Mientras (cont <> vueltas) hacer
							C:= *C.prox
							cont:= cont + 1
						Fin Mientras

						*Q.Chances_total:= *Q.Chances_total + *C.Chances_extra
					Fin Si
				SiNo
					Nuevo(P)
					*P.AyN:= reg_F.AyN
					*P.Chances_total:= 5

					Si (Q = Prim) entonces
						*P.ant:= Nill
						*P.prox:= Prim
						*Prim.ant:= P
						Prim:= P 
					SiNo
						Si (Q = Nill) entonces
							*P.prox:= Nill
							*P.ant:= Ult
							*Ult.prox:= P
							Ult:= P
						SiNo
							*P.prox:= Q
							*P.ant:= *Q.ant
							*(*Q.ant).prox:= P
							*Q.ant:= P
						Fin Si
					Fin Si
				Fin Si
			Fin Si		
		Fin Si

		Leer(Arch_C,reg_C)
	Fin Mientras

	Cerrar(Arch_C)
	Cerrar(Arch_F)
Fin Accion 
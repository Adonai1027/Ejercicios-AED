
Accion Tema2_Ejer1_2022 (Prim,Ult: puntero a Nodo) ES

Ambiente

	Compras=Registro
		Fecha_comp: N(8)
		DNI: N(8)
		cant_articulos: N(3)
		Importe: N(6,2)
	Fin Registro

	Socios=Registro
		DNI: N(8)
		AyN: N(30)
		Fecha_adhesion: N(8)
		Categoria: AN(10)
	Fin Registro

	Nodo=Registro
		min: N(6,2)
		max: N(6,2)
		descuento: (0,01...0,99)
		cupos_disp: entero
		rubro_desc: (1...9)
		ant,prox: puntero a Nodo
	Fin Registro

	New_Nodo=Registro
		DNI: N(8)
		cant_compr: N(3)
		imp_total: N(6,2)
		prox: puntero a New_Nodo
	Fin Registro

	Arch_Comp: archivo secuencial de compras ordenado por Fecha_comp
	reg_comp: Compras
	Arch_Soc: archivo de Socios indexado por DNI
	reg_soc: Socios

	P: puntero a Nodo
	New_Prim, Q, R, A: puntero a New_Nodo

Proceso
	Abrir E/(Arch_Comp)
	Leer(Arch_Comp,reg_comp)
	Abrir E/(Arch_Soc)

	New_Prim:= Nill

	Mientras NFDA(Arch_Comp) hacer
		reg_soc.DNI:= reg_comp.DNI
		Leer(Arch_Soc,reg_soc)

		Si no(existe) entonces
			Si (New_Prim = Nill) entonces	//Lista vacia
				Nuevo(Q)
				*Q.DNI:= reg_comp.DNI
				*Q.cant_compr:= reg_comp.cant_articulos
				*Q.imp_total:= reg_comp.Importe
				*Q.prox:= New_Prim
				New_Prim:= Q
			SiNo
				R:= New_Prim
				Mientras (R <> Nill) y (*R.DNI < reg_comp.DNI) hacer
					A:= R
					R:= *R.prox
				Fin Mientras

				Si (*R.DNI = reg_comp.DNI) entonces		//Actualizo
					*R.cant_compr:= *R.cant_compr + reg_comp.cant_articulos
					*R.imp_total:= *R.imp_total + reg_comp.Importe
				SiNo
					Nuevo(Q)
					*Q.DNI:= reg_comp.DNI
					*Q.cant_compr:= reg_comp.cant_articulos
					*Q.imp_total:= reg_comp.Importe

					//Carga Ordenada
					Si (R = New_Prim) entonces
						*Q.prox:= R
						New_Prim:= Q
					SiNo
						Si (R = Nill) entonces
							*Q.prox:= Nill
							*A.prox:= Q
						SiNo
							*Q.prox:= R
							*A.prox:= Q
						Fin Si
					Fin Si
				Fin Si
			Fin Si
		Fin Si

		P:= Prim

		Mientras (P <> Nill) y ((*P.min > reg_comp.Importe) o (*P.max < reg_comp.Importe)) hacer
			P:= *P.prox
		Fin Mientras

		Si (P = Nill) entonces
			Esc("La compra no tiene descuentos. No se encuentra en rangos de descuentos")
		SiNo
			Si (*P.cupos_disp = 0) entonces
				Esc("No hay cupos de descuentos disponibles ")
			SiNo
				Esc("El porcentaje de descuento es: ",*P.desc * 100,"%")
				Esc("Correspondiente al rubro: ",*P.rubro_desc)

				*P.cupos_disp:= *P.cupos_disp - 1
			Fin Si
		Fin Si

		Leer(Arch_Comp,reg_comp)
	Fin Mientras

	Cerrar(Arch_Comp)
	Cerrar(Arch_Soc)
Fin Accion 
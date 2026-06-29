Accion modelo de parcial 1 actualizacion (arr_libro: arreglo [1...100] de enteros) es

Ambiente

	socios = registro

		DNI: N (8)
		Ape_Nom: AN(30)
		Edad: (18..80) 
		Ciudad :AN(30) 
		Deudor: {"Si", "No"} 
		Cantidad_de_Prestamos: N(3)

	FinRegistro

	arch_socios: archivo de socios indexado por DNI
	reg_socios: socios

	libros = registro

		ID: (1..100)
		Titulo: AN(30)
		Genero: N(2)
		Disponible: booleano // revisar si puedo definir asi

	FinRegistro

	arch_libros: archivo de libros indexado por ID
	reg_libros: libros

	prestamos = registro

		ID_prestamo N(8) 
		DNI_socio N(8) 
		ID_libro N(8) 
		Fecha_prestamo: fecha 
		Fecha_devolucion: fecha 
		Devolucion: AN (2) // (Si-No) 

	FinRegistro

	arch_prestamos: archivo de prestamos indexado por ID_prestamo
	reg_prestamos: prestamos

	i: entero // ID libros

	total_cant_prestamos, total_socios_alta: entero

Proceso

	Abrir E/S(arch_socios)
	Abrir E/S(arch_libros)
	Abrir /S(arch_prestamos)
	// revisar cuales son de entrada y salida

	total_cant_prestamos, total_socios_alta:= 0

	Mientras NFDA (arch_libro) hacer

		// pido datos al usuario
		Escribir("Ingresar dni socio")
		Leer(dni_socio)

		Escribir("Ingresar titulo del libro que quiere llevar prestado")
		Leer(titulo_libro)

		// busco ID libro
		Para i=1 a 100 hacer

			Si arr_libro[i] = titulo_libro entonces

				ID_libro:= i
				i:= 100 // para que termine automaticamente de recorrer mi arreglo

			FinSi

		FinPara

		reg_libros.ID:= ID_libro

		Leer (arch_libro, reg_libro)

		Si existe entonces // busco si existe el libro

			Si reg_libros.Disponible = verdadero entonces // veo si esta disponible

				reg_libros.Disponible:= Falso // pq lo da a prestamo - consigna 1
				Regrabar(arch_libros, reg_libros)

				reg_socios.Cantidad_de_Prestamos:= reg_socios.Cantidad_de_Prestamos + 1 // consigna 1
				Regrabar(arch_socios, reg_socios)
		
				// consigna 4
				reg_prestamo.Fecha_prestamo:= fecha_sistema() // fecha actual
				Si (reg_socios.Cantidad_de_Prestamos) > 10 y (reg_socios.Deudor = "No") entonces

					reg_prestamo.Fecha_devolucion:= reg_prestamo.Fecha_prestamo + 20
					Grabar(arch_prestamos, reg_prestamos)

				sino

					reg_prestamo.Fecha_devolucion:= reg_prestamo.Fecha_prestamo + 14
					Grabar(arch_prestamos, reg_prestamos)

				FinSi


				reg_socios.DNI:= dni_socio // dato que le pide al usuario

				Leer (arch_socios , reg_socios)

				Si no existe entonces // busco sino existe el socio para agregarlo en ese caso - consigna 2

					Escribir("Ingresar DNI, nombre y apellido, edad, ciudad, deudor.")
					Leer(DNI)
					Leer(Ape_Nom)
					Leer(Edad)
					Leer(Ciudad)
					Leer(Deudor)

					reg_socios.DNI:= DNI
					reg_socios.Ape_Nom:= Ape_Nom
					reg_socios.Edad:= Edad
					reg_socios.Ciudad:= Ciudad
					reg_socios.Deudor:= Deudor
					Grabar (arch_socios, reg_socios) // alta al cliente

					total_socios_alta:= total_socios_alta + 1 // consigna 5

				FinSi

			sino

				Escribir("El libro no esta disponible") // consigna 3

			FinSi

		sino // libro no existe 

			Escribir("El libro no existe") // consigna 3

		FinSi

		total_cant_prestamos:= total_cant_prestamos + 1 

		Leer (arch_libros, reg_libros)

	FinMientras

	Escribir("El total de prestamos realizados es: ", total_cant_prestamos) // consigna 5

	Escribir("El total de socios dados de alta es: ", total_socios_alta) // consigna 5

	Cerrar (arch_socios)
	Cerrar (arch_libro)
	Cerrar (arch_prestamos)

FinAccion
Accion modelo de parcial 2 actualizacion (A: arreglo [1...20] de enteros) es

Ambiente

	Fecha = registro
		dd: 1...31
		mm: 1...12
		aaaa: 1...2023
	finregistro

	Reclamos = registro

		CodRecl: N(10)
		FecRecl: Fecha
		MailCliente: AN(20)
		Urgencia: AN(1)
		Detalle: AN(100)
		Region: N(2)

	FinRegistro

	arch_reclamos: archivo de Reclamos ordenado por Region y CodRecl
	reg_reclamos: Reclamos

	Reporte = registro

		Region: N(2)
		UltFecRec: Fecha
		UrgAlta: N(6)
		UrgMedia: N(6)
		UrgBaja: N(6)
		NueAud: {"S"-"N"}

	FinRegistro

	arch_reporte: archivo de Reporte indexado por Region
	reg_reporte: Reporte

	i: entero // codRegion

	total_auditorias, total_nueva_auditoria: entero

Proceso

	Abrir E/(arch_reclamos)
	Abrir E/S(arch_reporte)

	Leer (arch_reclamos) // pq es secuencial

	total_auditorias, total_nueva_auditoria:= 0

	Mientras NFDA (arch_reclamos) hacer

		// busco codRegion

		Para i=1 a 20 hacer

			total_auditorias:= total_auditorias + A[i]

		FinPara

		reg_reporte.Region:= reg_reclamos.Region

		Leer (arch_reporte, reg_reporte)

		Si existe entonces

			Segun reg_reclamos.Urgencia hacer // consigna 1

				="A": reg_reporte.UrgAlta:= reg_reclamos.Urgencia
				="M": reg_reporte.UrgMedia:= reg_reclamos.Urgencia 
				="B": reg_reporte.UrgBaja:= reg_reclamos.Urgencia
				Grabar(arch_reporte, reg_reporte)

			FinSegun

			// consigna 1
			reg_reporte.Region:= reg_reclamos.Region
			reg_reporte.UltFecRec:= reg_reclamos.FecRecl
			Grabar(arch_reporte, reg_reporte)

			// lo que pide el texto
			Si (total_auditorias < 10) y (reg_reporte.UrgAlta > reg_reporte.UrgBaja * 2) entonces

				Escribir("Requiera auditoria")

				reg_reporte.NueAud:= "S"
				total_nueva_auditoria:= total_nueva_auditoria + 1

			sino

				Escribir("No requiere auditoria")
			
			FinSi

		FinSi

	FinMientras

	Escribir("Se solicito una nueva auditoria ", total_nueva_auditoria, " veces") // consigna 2

	Cerrar (arch_reclamos)
	Cerrar (arch_reporte)
	
FinAccion
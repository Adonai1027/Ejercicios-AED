Accion PRESTAMOS (A:arreglo[1...100] de enteros) es
    Ambiente
        SOCIOS=Registro
            DNI:N(8);
            AyN:AN(30);
            Edad:18...80;
            Ciudad: AN(30);
            Deudor: ("SI","NO");
            Cantidad_de_Prestamos: N(2);
        FR

        arch_socios: Archivo de SOCIOS Indexado por DNI;
        reg_socios: SOCIOS;

        LIBROS=Registro
            ID:1...10;
            Titulo: AN(30);
            Genero: N(2);
            Disponible: booleano;
        FR

        arch_libros: Archivo de LIBROS Indexado por ID;
        reg_libros:LIBROS;

        PRESTAMOS=Registro
            ID_prestamo: N(8);
            DNI_socio: N(8);
            ID_libro: N(8);
            Fecha_prestamo: Fecha;
            Fecha_devolucion: Fecha;
            Devolucion: ("SI","NO");
        FR

        arch_prestamos: Archivo de PRESTAMOS Indexado por ID_prestamo;
        reg_prestamo: PRESTAMOS;

        i:entero //para recorrer el arreglo
        rta: ("SI","NO");
        dni_usuario: N(8);
        titulo_usuario: AN(30); 
        total_prestamos_realizados: entero;
        total_socios_alta: entero;
        resg_ID_libro:N(8);

        Proceso
            Abrir E/S(arch_socios);
            Abrir E/S(arch_libros);
            Abrir /S(arch_prestamos);

            Esc("Quiere iniciar el sistema de carga interactiva?");
            Leer(rta);

            Mientras rta ="SI" hacer
                Esc("Ingrese DNI");
                Leer(dni_usuario);
                Esc("Ingrese título del libro");
                Leer(titulo_usuario);

                Para i:=1 a 100 hacer
                    Si A[i]=titulo_usuario entonces
                        resg_ID_libro:=i
                        i:=100 //asi deja de recorrer pq ya encontramos lo que buscabamos
                FinPara

                reg_libros.ID_libro:=resg_ID_libro;
                Leer(arch_libros,reg_libros);
                Si existe entonces
                    reg_socios.DNI:=dni_usuario
                    Leer(arch_socios,reg_socios);
                    Si existe entonces
                        Si reg_libros.Disponible=Verdadero entonces
                            reg_libros.Disponible:= Falso; //lo doy de préstamo;
                            Regrabar(arch_libros, reg_libros);
                            reg_socios.Cantidad_de_Prestamos:=reg_socios.Cantidad_de_Prestamos + 1;
                            Regrabar(arch_socios,reg_socios);

                            reg_prestamo.Fecha_prestamo:=Fecha_sistema();

                            Si (reg_socios.Cantidad_de_Prestamos >9) ^ (reg_socios.Deudor="NO") entonces
                                reg_prestamo.Fecha_devolucion:=Fecha_sistema() + 20;
                            Sino
                                reg_prestamo.Fecha_devolucion:=Fecha_sistema() + 14;
                            FinSi
                            reg_prestamo.DNI_socio:=dni_usuario;
                            reg_prestamo.ID_libro:=resg_ID_libro;
                            reg_prestamo.Devolucion:="NO";
                            Grabar(arch_prestamos,reg_prestamo);
                            total_prestamos_realizados:=total_prestamos_realizados + 1;
                        Sino
                            Esc("El libro no está disponible"); //no se puede realizar prestamos
                        FinSi
                    Sino
                        //el socio no existe pero existe el libro
                        Si reg_libros.disponible=Verdadero entonces
                            Esc("Ingrese Apellido y Nombre");
                            Leer(reg_socios.AyN);
                            Esc("Ingrese edad");
                            Leer(reg_socios.Edad);
                            Esc("Ingrese ciudad");
                            Leer(reg_socios.ciudad);
                            reg_socios.DNI:=dni_usuario;
                            reg_socios.Deudor:="SI";
                            reg_socios.Cantidad_de_Prestamos:=1
                            total_socios_alta:=total_socios_alta + 1;
                            total_prestamos_realizados:=total_prestamos_realizados + 1;
                            Grabar(arch_socios, reg_socios);

                            //tambien debo guardar el prestamo en el archivo de prestamos
                            reg_prestamo.DNI_socio:dni_usuario;
                            reg_prestamo.ID_libro:=resg_ID_libro;
                            reg_prestamo.Fecha_prestamo:=Fecha_sistema();

                            Si (reg_socios.Cantidad_de_Prestamos >9) ^ (reg_socios.Deudor="NO") entonces
                                reg_prestamo.Fecha_devolucion:=Fecha_sistema() + 20;
                            Sino
                                reg_prestamo.Fecha_devolucion:=Fecha_sistema() + 14;
                            FinSi
                            reg_prestamo.Devolucion:="NO";
                            Grabar(arch_prestamos,reg_prestamo);
                        Sino
                            Esc("El libro no está disponible");
                            //en este caso considero que si el libro no está disponible, no debería dar de alta al usuario
                            //ya que no se llevará ningun libro 
                        FinSi
                    FinSi

                Sino
                        Esc("El libro no está disponible");
                FinSi

                 Esc("Desea continuar con la carga interactiva?");
                Leer(rta);
            FinMientras

            Esc("El total de prestamos realizados es:", total_prestamos_realizados);
            Esc("El total de socios dados de alta es:", total_socios_alta);

            Cerrar(arch_socios);
            Cerrar(arch_libros);
            Cerrar(arch_prestamos);

FinAccion

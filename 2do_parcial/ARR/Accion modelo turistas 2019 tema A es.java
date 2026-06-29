Accion modelo turistas 2019 tema A es
    Ambiente
        Fecha=Registro
            AA:N(4);
            MM:1...12;
            DD:1...31;
        FR

        Tarjeta=Registro
            DNI:N(8);
            Num_cuenta:N(8);
            Credito:N(10);
            Ult_carga:Fecha;
        FR

        arch_tarjeta:Archivo de Tarjeta indexado por DNI;
        reg_tarj:Tarjeta;

        Turista=Registro
            DNI:N(8);
            Nombre: AN(100);
            Fecha_nacimmiento:Fecha;
            Telefono:AN(100);
        FR

        arch_turista:Archivo de turista indexado por DNI;
        reg_turista:Turista;

        Facturas=Registro
            Clave=Registro
                NRO:N(10);
                DNI:N(8);
            FR
            id_servicio:n(10);
            Monto:N(2);
            Fecha_carga:Fecha;
        FR

        arch_fac:Archivo de facturas ordenado por Clave;
        reg_fac:Facturas;

        A:arreglo [1...200] de enteros
        i,totfact:entero


        Procedimiento Reintegro() es
                Si (reg_fac.Fecha_carga.AA = 2022) y ((reg_fac.Fecha_carga.MM >=8 y reg_fac.Fecha_carga.MM<=9) o (reg_fac.Fecha_carga.MM = 9 y reg_fac.carga.DD <=15)) entonces
                            Si A[i]=1 entonces
                                Si reg_fac.Monto > 200000 entonces
                                    reg_tarj.Credito:=100000
                                Sino
                                    reg_tarj.Credito:=reg_fac.Monto/2; //el 50%
                                FinSi
                                reg_tarj.Ult_carga:=reg_fac.Fecha_carga;
                            Sino
                                Esc("El servicio no está habilitado");
                            FinSi
                        Sino
                            Esc("Fecha inválida");
                            totfact:=tot_fact + 1;
                        FinSi
                    Sino
                        Esc("Fecha inválida");
                        totfact:=tot_fact + 1;
                    FinSi
        FinProcedimiento


    Proceso
        Abrir E/(arch_fac);
        Abrir E/S(arch_tarjeta);
        Abrir E/(arch_turista);
        totfact:=0

            Mientras NFDA(arch_fac) hacer
                Para i:=1 a 200 hacer
                    bandera:=falso
                    Si reg_fact.id_servicio=i entonces
                        bandera:=Verdadero
                    FinSi
                FinPara
                Si bandera entonces
                    reg_turista.DNI:=reg_fac.DNI
                    Leer(arch_turista,reg_turista);
                    Si EXISTE entonces
                        reg_tarj.DNI:=reg_fac.DNI
                        Leer(arch_tarjeta,reg_tarj);
                        Si EXISTE entonces
                            Reintegro();
                            Regrabar(arch_tarjeta,reg_tarj);
                        Sino
                            //no existe la tarjeta
                            reg_tarj.Num_cuenta:=obtener_nrocuenta();
                            reg_tarj.DNI:=reg_fac.DNI;
                            Reintegro();
                            Grabar(arch_tarjeta,reg_tarj);
                        FinSi
                    Sino
                    //no existe el turista
                        Esc("Ingrese nombre");
                        Leer(reg_turista.nombre);
                        Esc("Ingrese fecha de nacimiento");
                        Leer(reg_turista.Fecha_nacimmiento);
                        Esc("Ingrese telefono");
                        Leer(reg_turista.telefono);
                        //doy de alta al turista
                    FinSi
                Sino
                    Esc("El servicio no está inscripto");
                FinSi
                Leer(arch_fac,reg_fac);
            FinMientras  
            Esc("El total de facturas con fecha fuera de plazo es de:",totfact,"facturas");
            Cerrar(arch_fac);
            Cerrar(arch_turista);
            Cerrar(arch_tarjeta);
FinAccion

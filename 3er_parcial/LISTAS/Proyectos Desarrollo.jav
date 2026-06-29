Accion Proyectos Desarrollo (prim:puntero a Nodo) es
    Ambiente
        Proyectos=registro
            Codigo:N(10);
            Nombre:AN(60);
            Tipo:("D,W");
            Cant_errores:N(3);
        FR

        arch_proy:Archioovo de Proyectos ordenado por Codigo;
        reg_pr:Proyectos;

        Nodo=registro
            Descripcion:AN(250);
            Estado:("N","O","P");
            prox:puntero a nodo
        FR

        p:puntero a Nodo

        FormatoDoble=Registro
            Codigo:N(10);
            Nombre:AN(60);
            porc_res:real
            porc_en_proc:real
            ant,prox:puntero a FormatoDoble
        FR

        primd1, pd1,qd1,ultd1:puntero a FormatoDoble;
        primd2, pd2,qd2,ultd2:puntero a FormatoDoble;
        i,cantnr, cantr,cantp:entero //resuelto, no resuelto y en proceso
        cant_proy:entero
    Proceso
        Abrir E/(arch_proy);
        Leer(arch_proy,reg_pr);
        p:=prim
        primd1:=nil
        ultd1:=nil
        primd2:=nil
        ultd2:=nil

        Mientras NFDA(arch_proy) hacer
            cantnr:=0
            cantr:=0
            cantp:=0

            Para i:=1 a (reg_pr.Cant_errores) hacer
                Según *p.Estado hacer
                    ="N":cantnr:=cantnr + 1;
                    ="O":cantp:=cantp + 1;
                    ="R":cantr:=cantr + 1;
                FinSegún
                Si cantnr=reg_pr.Cant_errores entonces //consigna cantidad de proyectos con todos sus errores en NO RESUELTO
                    cant_proy:=cant_proy + 1;
                FinSi
                p:=*p.prox
            finPara

            Si ((cantr)/(reg_pr.Cant_errores))*100= 100 entonces
                //guardo la lista de proyectos con todos sus errores resueltos
                Nuevo(qd1);
                *qd1.Codigo:=reg_pr.Codigo;
                *qd1.Nombre:=reg_pr.Nombre;
                *qd1.porc_res:=((cantr)/reg_pr.Cant_errores)*100
                *qd1.porc_en_proc:=((cantp)/reg_pr.Cant_errores)*100

                Si primd1 = nil entonces
                    *qd1.prox:=nil
                    *qd1.ant:=nil
                    primd1:=qd1
                    ultd1:=qd1
                Sino
                    pd1:=primd1
                    Mientras *pd1.prox <> nil hacer
                        pd1:=*pd1.prox
                    FinMientras
                    //estoy en la ultima posicion siempre
                    *qd1.prox:=nil
                    *qd1.ant:=pd1
                    ultd1:=qd1
                    *pd1.prox:=qd1
                FinSi

            Sino
                Si ((cantr)/reg_pr.Cant_errores*100 > 50) o ((cantp)/reg_pr.Cant_errores*100 > 50) entonces
                    Nuevo(qd2);
                    *qd2.Codigo:=reg_pr.Codigo;
                    *qd2.Nombre:=reg_pr.Nombre;
                    Si primd2=nil entonces
                        *qd2.prox:=nil
                        *qd2.ant:=nil
                        primd2:=qd2
                        ultd2:=qd2
                    Sino
                        pd2:=primd2
                        Mientras (pd2 <> nil) ((*pd2.porc_res < *qd2.porc_res) o (*pd2.porc_en_proc < *qd2.porc_en_proc))hacer
                            pd2:=*pd2.prox
                        FinMientras

                        Si pd2=primd2 entonces
                            *qd2.prox:=pd2
                            *qd2.ant:=nil
                            primd2:=qd2
                            *pd2.ant:=qd2
                        Sino
                            Si pd2=nil entonces
                                *qd2.prox:=nil
                                *qd2.ant:=ultd2
                                *(*ultd2.ant).prox:=qd2
                                ultd2:=qd2
                            Sino
                                *qd2.prox:=pd2
                                *qd2.ant:=*pd2.ant
                                *(*pd2.ant).prox:=qd2
                                *pd2.ant:=qd2
                            FinSi
                        FinSi
                    FinSi
                FinSi
            FinSi

            Leer(arch_proy,reg_pr);              

        FinMientras

        Esc("La cantidad de proyectos con todos sus errores en NO RESUELTO es de:", cant_proy);

        CERRAR(arch_proy);

FinAccion

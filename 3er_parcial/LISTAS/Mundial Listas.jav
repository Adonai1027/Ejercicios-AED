Accion Mundial 2022 (prim:puntero a Simple) es
    Ambiente
        Simple=Registro
            Pais:AN(30);
            Edad:arreglo [1...26] de enteros;
            Grupo: A...H;
            Diferencia_goles:N(3); //agrego para contar los goles antes de crear la lista doble
            Puntos:N(2);
            Amarillas:N(2);
            Rojas:N(2);
            prox:puntero a Simple
        FR

        Partidos=Registro
            ID_partido:N(5);
            Equipo_Local: AN(30);
            Equipo_visitante:AN(30);
        FR

        arch_partidos:Archivo SECUENCIAL de Partidos ordenado por ID_partido;
        reg_partidos:Partidos;

        Resultados=Registro
          ID_partido:N(5);
          Cant_goles_local:N(2);
          Cant_goles_visitante:N(2);
          Rojas:N(2);
          Amarillas:N(2);
        FR

        arch_resul:Archivo de Resultados INDEXADO por ID_partido;
        reg_resul:Resultados;

        Doble=Registro
            Pais: AN(30);
            Puntos_obtenidos:N(2);
            ant,prox:puntero a Doble
        FR

        primd, ultd, pd, qd: puntero a Doble
        equipo_mayor:alfanumerico
        tarjetas_mayor:entero
        copiar_a_doble:booleano

    Proceso
        Abrir E/(arch_partidos);
        Leer(arch_partidos,reg_partidos);
        Abrir E/(arch_resul);

        Mientras NFDA (arch_partidos) hacer

            reg_resul.ID_partido:=reg_partidos.ID_partido;
            Leer(arch_resul,reg_resul);

            Si EXISTE entonces
                p:=prim
                Mientras (p<>nil) y ((*p.Pais <> reg_partidos.Equipo_Local) o (*p.Pais <> reg_partidos.Equipo_visitante)) hacer
                    p:=*p.prox
                FinMientras

                //verifico si debo sumar los goles de visitante o de local

                Si (*p.pais=reg_partidos.Equipo_Local) entonces
                    *p.Cant_goles:=*p.Cant_goles + reg_resul.Cant_goles_local;

                    Si reg_resul.Cant_goles_local > reg_resul.Cant_goles_visitante entonces
                        *p.Puntos:= *p.Puntos + 3;
                    Sino
                        Si (reg_resul.Cant_goles_local = reg_resul.Cant_goles_visitante) entonces
                            *p.puntos:=*p.puntos + 1;
                        FinSi
                    FinSi
                Sino
                    *p.Cant_goles:=*p.Cant_goles + reg_resul.Cant_goles_visitante;

                    Si (reg_resul.Cant_goles_local < reg_resul.Cant_goles_visitante) entonces
                        *p.Puntos:= *p.Puntos + 3;
                    Sino
                        Si (reg_resul.Cant_goles_local = reg_resul.Cant_goles_visitante) entonces
                            *p.puntos:=*p.puntos + 1;
                        FinSi
                    FinSi
                    
                FinSi
                //cargo los otros datos  que no dependen de la condicion
                *p.Diferencia_goles:=*p.Diferencia_goles + (reg_resul.Cant_goles_local-reg_resul.Cant_goles_visitante);
                *p.Amarillas:=*p.Amarillas + reg_resul.Amarillas;
                *p.Rojas:=*p.Rojas + reg_resul.Rojas;

            FinSi

            Leer(arch_partidos,reg_partidos);
        FinMientras

        primd:=nil
        ultd:=nil
        p:=prim
        
        tarjetas_mayor:=LV

        Mientras p<>nil hacer
            copiar_a_doble:=falso

            Si (*p.Puntos > 4) entonces
                copiar_a_doble:=VERDADERO
            Sino
                Si (*p.Puntos = 4) y (*p.Diferencia_goles > 2) entonces
                    copiar_a_doble:=VERDADERO;
                Sino
                    Si (*p.puntos=3) entonces
                        q:=prim
                        Mientras (q<>nil) hacer
                            Si (*p.Grupo=*q.grupo) y (*q.Puntos = 9) entonces
                                copiar_a_doble:=VERDADERO
                            FinSi
                            q:=*q.prox
                        FinMientras
                    FinSi
                FinSi
            FinSi

            Si copiar_a_doble entonces
                Nuevo(qd);
                *qd.pais:=*p.pais;
                *qd.Puntos_obtenidos:=*p.Puntos;
                
                Esc("El pais:", *qd.pais,"avanzó de fase de grupos");

                //lo cargo en la lista doble

                Si primd = nil entonces
                    *qd.prox:=nil
                    *qd.ant:=nil
                    primd:=qd
                    ultd:=qd
                Sino
                    pd:=primd
                    Mientras (pd <> nil) y (*pd.Puntos_obtenidos < *qd.Puntos_obtenidos)arch_resul
                        pd:=*pd.prox
                    FinMientras

                    Si pd=primd entonces
                        *qd.prox:=nil
                        *qd.ant:=pd
                        *pd.prox:=qd
                        ultd:=qd
                    Sino
                        Si pd=nil entonces
                            *qd.prox:=nil
                            *qd.ant:=pd
                            *ultd.prox:=qd
                            ultd:=qd
                        Sino
                            *qd.prox:=pd
                            *qd.ant:=*pd.ant
                            *(*p.ant).prox:=qd
                            *pd.ant:=qd
                        FinSi
                    FinSi
                FinSi
            Sino
                Esc("El equipo no avanzó de fase de grupos");
            FinSi

            Si *p.Rojas > tarjetas_mayor entonces
                tarjetas_mayor:=*p.Rojas
                equipo_mayor:=*p.pais
            FinSi

            p:=*p.prox
        FinMientras

        CERRAR(arch_partidos);
        CERRAR(arch_resul);

        Esc("El equipo con mas tarjetas rojas fue:",equipo_mayor);
    
FinAccion








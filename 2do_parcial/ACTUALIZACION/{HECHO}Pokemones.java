// ============================================================================
// RECUPERATORIO PARCIAL 2 - DICIEMBRE 2022
// Tema: Actualización Secuencial (USUARIOS vs CAPTURAS) + Arreglo Pokémon
// Cátedra: Algoritmos y Estructuras de Datos (AED)
// ============================================================================

Accion ActualizacionPokemones(A: arreglo [1..151] de ALFANUMERICO) Es
    Ambiente
        clave_usuario = Registro
            Cod_Region: N(4)
            Cod_Usuario: N(10)
        FR

        CAPTURAS = Registro
            Clave: clave_usuario
            Cod_pokemon: 1..151
            Puntos_de_exp: N(5)
            Fecha_captura: ALFANUMERICO
            Estado_pok: ("E", "I", "D") // E: Entrenándose, I: Incubándose, D: Descansando
            Estado_Usuario: ("A", "S", "B") // A: Activo, S: Suspendido, B: Baneado
        FR

        arch_mov: Archivo SECUENCIAL de CAPTURAS ordenado por Clave
        reg_mov: CAPTURAS

        USUARIOS = Registro
            Clave: clave_usuario
            Correo: AN(50)
            Experiencia: N(7)
        FR

        arch_mae, arch_sal: Archivo SECUENCIAL de USUARIOS ordenado por Clave
        reg_mae, reg_sal, aux: USUARIOS

        // Arreglo contador para los 151 pokémones en estado "Descansando"
        P: arreglo [1..151] de ENTERO
        i, pos_max, mayor: ENTERO
        bandera_baja: BOLEANO

        // Subprograma para sumar puntos de experiencia y registrar estado Descansando
        Procedimiento Exp() Es
            Segun reg_mov.Estado_pok Hacer
                ="E": aux.Experiencia := aux.Experiencia + (reg_mov.Puntos_de_exp * 2)
                ="I": aux.Experiencia := aux.Experiencia + reg_mov.Puntos_de_exp
                ="D": aux.Experiencia := aux.Experiencia + reg_mov.Puntos_de_exp
                      // Se contabiliza para el Pokémon en estado Descansando
                      P[reg_mov.Cod_pokemon] := P[reg_mov.Cod_pokemon] + 1
            FinSegun
        FinProcedimiento

        // Subprograma para verificar si el usuario debe ser dado de baja
        Procedimiento Baja() Es
            Si reg_mov.Estado_Usuario = "S" Entonces
                bandera_baja := VERDADERO
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mae() Es
            Leer(arch_mae, reg_mae)
            Si FDA(arch_mae) Entonces
                reg_mae.Clave.Cod_Region := HV
                reg_mae.Clave.Cod_Usuario := HV
            FinSi
        FinProcedimiento

        Procedimiento Leer_Mov() Es
            Leer(arch_mov, reg_mov)
            Si FDA(arch_mov) Entonces
                reg_mov.Clave.Cod_Region := HV
                reg_mov.Clave.Cod_Usuario := HV
            FinSi
        FinProcedimiento

    Proceso
        Abrir E/(arch_mov)
        Abrir E/(arch_mae)
        Abrir /S(arch_sal)

        Leer_Mae()
        Leer_Mov()

        // Inicializar arreglo de contadores por pokémon
        Para i := 1 a 151 Hacer
            P[i] := 0
        FinPara

        Mientras (reg_mae.Clave.Cod_Region <> HV) O (reg_mov.Clave.Cod_Region <> HV) Hacer
            
            // CASO 1: MAESTRO MENOR (Usuario sin nuevas capturas)
            Si reg_mae.Clave < reg_mov.Clave Entonces
                
                reg_sal := reg_mae
                Grabar(arch_sal, reg_sal)
                Leer_Mae()

            Sino
                // CASO 2: CLAVES IGUALES (Usuario existente con capturas)
                Si reg_mae.Clave = reg_mov.Clave Entonces
                    aux := reg_mae
                    bandera_baja := FALSO

                    Mientras aux.Clave = reg_mov.Clave Hacer
                        Baja()
                        Exp()
                        Leer_Mov()
                    FinMientras

                    // Si no fue dado de baja física por estar Suspendido, se graba en el maestro actualizado
                    Si NO bandera_baja Entonces
                        reg_sal := aux
                        Grabar(arch_sal, reg_sal)
                    FinSi

                    Leer_Mae()

                Sino
                    // CASO 3: MAESTRO MAYOR (Usuario no existe en USUARIOS)
                    Si reg_mov.Estado_Usuario = "A" Entonces
                        // ALTA de nuevo usuario activo
                        aux.Clave := reg_mov.Clave
                        aux.Correo := " "
                        aux.Experiencia := 0
                        bandera_baja := FALSO

                        Mientras aux.Clave = reg_mov.Clave Hacer
                            Baja()
                            Exp()
                            Leer_Mov()
                        FinMientras

                        Si NO bandera_baja Entonces
                            reg_sal := aux
                            Grabar(arch_sal, reg_sal)
                        FinSi
                    Sino   
                        // Si no era Activo, no se puede dar de alta -> Error
                        Escribir("ERROR: Intento de registro o actualización para usuario inexistente no activo.")
                        Leer_Mov()
                    FinSi
                FinSi
            FinSi
        FinMientras

        // ----------------------------------------------------------------------
        // INCISO 4: Pokémon en estado "Descansando" con mayor cantidad de usuarios
        // ----------------------------------------------------------------------
        mayor := LV
        pos_max := 1

        Para i := 1 a 151 Hacer
            Si P[i] > mayor Entonces
                mayor := P[i]
                pos_max := i
            FinSi
        FinPara

        Escribir("El Pokémon en estado 'Descansando' con mayor cantidad de usuarios es: ", A[pos_max])
        Escribir("Cantidad total de usuarios/capturas que lo poseen en descanso: ", mayor)

        CERRAR(arch_mae)
        CERRAR(arch_mov)
        CERRAR(arch_sal)
FinAccion

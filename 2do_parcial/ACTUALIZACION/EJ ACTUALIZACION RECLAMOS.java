Accion ACTUALIZACION Es (A:arreglo[1...20] de Enteros)
 Ambiente
    FECHA=REGISTRO
        AA:N(4)
        MM:N(2)
        DD:N(2)
    FR    
    RECLAMOS=REGISTRO
        CodRecl:N(10)
        FechaRecl:FECHA
        MailCliente:AN(20)
        Urgencia:AN(1)
        Detalle:AN(100)
        Region:N(2)
    FR
    ArchRecl:Archivo Ordena por Region y CodRecl
    reg_recl:RECLAMOS

    REPORTE=REGISTRO
        Region:N(2)
        UltFecRec:FECHA
        UrgA:N(6)
        UrgM:N(6)
        UrgB:N(6)
        NueAud:('S','N')
    FR
    ArchRepor:Archivo Indexado por Region
    reg_report:REPORTE

    i,tot_aud,Nue_Aud,resg_region:Enteros
Proceso
    Abrir E/(ArchRecl);Leer(ArchRecl,reg_recl)
    Abrir E/S(ArchRepor)
    tot_aud,Nue_Aud:=0
    Mientras NFDA(ArchRecl) Hacer
        //BUSCO TOTAL AUDITORIAS SOLICITADAS
        Para i:=1 a 20
            tot_aud:=tot_aud + A[i]
        FP
        //INDEXO
        reg_report.Region:=reg_recl.Region
        Leer(ArchRepor,reg_report)

        Si existe Entonces
            //CONSIGNA 1
            Segun reg_recl.Urgencia Hacer
                ='A':reg_report.UrgA:=reg_report.UrgA + reg_recl.Urgencia
                ='M':reg_report.UrgM:=reg_report.UrgM + reg_recl.Urgencia
                ='B':reg_report.UrgB:=reg_report.UrgB + reg_recl.Urgencia
            FSEGUN
            reg_report.UltFecRec:=reg_recl.FechaRecl
            Grabar(ArchRepor,reg_report)
            //CONSIGNA 2
            Si tot_aud < 10 Entonces    
                Si reg_report.UrgA > (reg_report.UrgB*2) Entonces
                    Si reg_report.NueAud = 'N' Entonces
                        reg_report.NueAud:='S'
                        Nue_Aud:=Nue_Aud + 1
                        Grabar(ArchRepor,reg_report)
                    FS
                Sino
                Escribir('No se cumple la necesidad de llamar a auditoria')
                FS
            FS
        FS
    FM
    Escribir('Las veces que se solicito una nueva auditoria es de'Nue_Aud)
    CERRAR(ArchRecl)
    CERRAR(ArchRepor)
Fin Accion
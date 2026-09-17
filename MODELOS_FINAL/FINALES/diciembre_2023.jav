Accion ej2 Es 
   Ambiente
      tickets=Registro
         clave=Registro 
            site:AN(10)
            area:AN(5)
            version:('W','M')
         FR
         integrantes:3..6
         ticket_resueltos:N(3)
         estado:('F','E')
      FR 
      arch:archivo secuencial de tickets ordenado por clave 
      reg:tickets

      salida=Registro 
         site:AN(10)
         total_tick:ENTERO 
      FR 
      arch_s:archivo secuencial de salida 
      reg_s:salida

      tot_site,tot_area,tot_version,tot_gral,resgSite,resgArea,resgVersion:ENTERO

      Procedimiento CorteVersion() Es  
         Escribir('La version: ',resgVersion,' tiene un total de: ',tot_version)
         tot_area:=tot_area+tot_version
         tot_version:=0
         resgVersion:=reg.clave.version
      FP 
      Procedimiento CorteArea() Es 
         Escribir('El area: ',resgArea,' tiene un total de: ',tot_area)
         tot_site:=tot_site+tot_area
         tot_area:=0
         resgArea:=reg.clave.area
      FP 
      Procedimiento CorteSite() Es 
         Escribir('El site: ',resgSite,' tiene un total de: ',tot_site)
         reg_s:=resgSite
         reg_s:=tot_site
         Grabar(arch_s,reg_s)
         tot_gral:=tot_gral+tot_site
         tot_site:=0
         resgSite:=reg.clave.site 
      FP 
      Procedimiento TratarReg() Es 
         Si reg.integrantes>4 Y reg.estado='E' Entonces 
            tot_version:=tot_version+reg.ticket_resueltos
         FS 
      FP 
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)
      ABRIR /S(arch_s)

      tot_site:=0
      tot_area:=0
      tot_version:=0
      tot_gral:=0
      resgVersion:=reg.clave.version
      resgArea:=reg.clave.area
      resgSite:=reg.clave.site
      Mientras NFDA(arch) Hacer 
         Si resgSite<>reg.clave.site Entonces
            CorteSite()
         Sino 
            Si resgArea<>reg.clave.area Entonces
               CorteArea()
            Sino 
               Si resgVersion<>reg.clave.version Entonces
                  CorteVersion()
               FS 
            FS 
         FS 
         TratarReg()
         Leer(arch,reg)
      FM 
      CorteSite()
      Escribir('Total general: ',tot_gral)
      Cerrar(arch);Cerrar(arch_s)
FINACCION
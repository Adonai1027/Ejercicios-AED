FRM|M|22711|PedroGonzales|#|FRM|F|19245|Monica|+|FRC|M|27909|Adonai|#|...
67|20||50|30||70|20||...

Accion jubilar Es 
    Ambiente
        sec1,salida:SECUENCIA DE CARACTERES
        v1,regional_usuario,resg_regional:CARACTER
        sec2:SECUENCIA DE ENTEROS 
        v2,cant_jub,cant_nojub:ENTERO
        esRegional,puedeJubilarse:LOGICO
    Proceso
        ARR(sec1);AVZ(sec1,v1)
        ARR(sec2);AVZ(sec2,v2)
        CREAR(salida)

        Escribir('Ingrese la inicial de la provincia de la regional:');Leer(regional_usuario)

        Mientras NFDS(sec1) Y NFDS(sec2) Hacer
            //reseteo contadores ya que estoy en una nueva regional
            cant_jub:=0
            cant_nojub:=0
            //trato sec1
            Mientras v1 <> '+' Hacer    
                //avanzo FR ya que lo unico que cambia es el ultimo caracter
                Para i:=1 Hasta 2 Hacer
                    AVZ(sec1,v1)
                FP

                resg_regional:=v1

                Si resg_regional = regional_usuario Entonces
                    esRegional:=Verdadero
                Sino
                    esRegional:=Falso
                FS 

                AVZ(sec1,v1) //estoy en sexo     

                AVZ(sec1,v1) //estoy en el legajo

                //trato sec2
                Si v2>=65 Y v2<=70 Entonces
                    AVZ(sec2,v2)
                    puedeJubilarse:=Falso
                    Si v2 >= 30 Entonces
                        puedeJubilarse:=Verdadero
                    FS 
                Sino
                    Si v2>=60 Y v2<=70 Entonces
                        AVZ(sec2,v2)
                        puedeJubilarse:=Falso
                        Si v2>=30 Entonces
                            puedeJubilarse:=Verdadero
                        FS 
                    Sino 
                        Si v2<60 Entonces 
                            puedeJubilarse:=Falso
                            AVZ(sec2,v2)
                        FS
                    FS         
                FS

                Si puedeJubilarse Entonces
                    cant_jub:=cant_jub+1
                Sino 
                    cant_nojub:=cant_nojub+1
                FS 

                AVZ(sec2,v2) //estoy en la siguiente edad

                //trato la sec1
                Si puedeJubilarse Y esRegional Entonces
                    Mientras v1 <>'#' Y v1<>'+' Hacer
                        Grabar(sec1,v1)
                        AVZ(sec1,v1)
                    FM 
                Sino
                    Mientras v1 <>'#' Y v1<>'+' Hacer
                        AVZ(sec1,v1)
                    FM  
                FS
                
                //controlo para no recorrer mal la secuencia
                Si v1 <>'+' Entonces
                    AVZ(sec1,v1) //avanzo '#'
                FS    
            FM 
            Escribir('En la regional:',resg_regional,', la cantidad de docentes que pueden jubilarse son:',cant_jub)
            Escribir('En la regional:',resg_regional,', la cantidad de docentes que no pueden jubilarse son:',cant_nojub) 

            AVZ(sec1,v1) //estoy en otra regional                             
        FM 
        Cerrar(sec1);Cerrar(sec2);Cerrar(salida)
FinAccion        


        






Accion recorrido Es 
   Ambiente
      NODO=Registro
         dato:ENTERO 
         izq,der:PUNTERO A NODO 
      FR 

      Procedimiento EnOrden(p: PUNTERO A NODO) Es 
         Si p<>NIL Entonces
            EnOrden(*p.izq)
            Escribir(*p.dato)
            EnOrden(*p.der)
         FS 
      FP 

      Procedimiento PreOrden(p: PUNTERO A NODO) Es 
         Si p<>NIL Entonces
            Escribir(*p.dato)
            PreOrden(*p.izq)
            PreOrden(*p.der)
         FS 
      FP
      
      Procedimiento PostOrden(p: PUNTERO A NODO) Es 
         Si p<>NIL Entonces
            PostOrden(*p.izq)
            PostOrden(*p.der)
            Escribir(*p.dato)
         FS 
      FP
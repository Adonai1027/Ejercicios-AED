//MACHETE DE TODOS LOS ALGORITMOS DE TODAS LAS LISTAS

// PARA TODAS LAS LISTAS 
Procedimiento CrearNodo() Es 
   Nuevo(q)
   *q.valor:=123
   *q.prox:=NIL 
   //doble
   *q.ant:=NIL
FP 
//Lista dobles. Lista circulares simple y dobles
Procedimiento Busqueda() Es 
   p:=prim 
   Mientras (*p.prox<>NIL) Y (*p.valor<>valorBuscado) Hacer 
      p:=*p.prox 
   FM 
   Si *p.valor=valorBuscado Entonces
      Escribir('ENCONTRADO')
   Sino 
      Escribir('NO ENCONTRADO')
   FS 
FP

Accion LS(prim: PUNTERO A NODO) Es 
   Ambiente 
      NODO = Registro 
         valor:ENTERO 
         prox: PUNTERO A NODO 
      FR 
      p,q,ant: PUNTERO A NODO

      Procedimiento Busqueda() Es 
         p:=prim 
         ant:=NIL
         Mientras (*p.prox<>NIL) Y (*p.dato<>datoBuscado) Hacer
            ant:=p 
            p:=*p.prox 
         FM 
         Si *p.dato=datoBuscado Entonces
            Escribir('ENCONTRADO')
         Sino 
            Escribir('NO ENCONTRADO')
         FS 
      FP

      Procedimiento CargaOrdenadaSimple() Es 
         // Caso 1: La lista está vacía
         Si prim = NIL Entonces
            prim := q
            *q.prox := NIL
         Sino
            // Busco el lugar para la inserción
            p := prim
            ant := NIL
            Mientras (p <> NIL) y (*p.valor < valor) Hacer
               ant := p
               p := *p.prox
            FM
            // Caso 2: El nuevo es el más chico (p quedó en prim)
            Si p = prim Entonces
               *q.prox:=prim
               prim := q
            Sino 
               // Casos 3 y 4: Medio o Final
               *q.prox := p
               *ant.prox := q
            FS
            // 5. El enganche final
            
         FS
      FP

      Procedimiento EliminarSimple() Es 
         Si prim=NIL Entonces
            Escribir('ERROR')
         Sino 
            Escribir('Ingrese el valor a eliminar'); Leer(valorBuscado)
            p:=prim 
            ant:=NIL 

            Mientras (p<>NIL) Y (*p.valor<valorBuscado) Hacer 
               ant:=p 
               p:=*p.prox 
            FM

            Si p=NIL Entonces
               Escribir('NO SE ENCONTRO')
            Sino 
               Si p=prim Entonces   
                  prim:=*p.prox 
               Sino 
                  *ant.prox:=*p.prox 
               FS  
               Disponer(p) 
            FS 
         FS 
      FP
FINACCION

Accion LD(prim,ult: PUNTERO A NODO) Es 
   Ambiente
      NODO = Registro
         valor:ENTERO 
         prox,ant: PUNTERO A NODO
      FR 
      p,q: PUNTERO A NODO 
   
      Procedimiento CargaOrdenadaDoble() Es 
        //VACIA
         Si prim=NIL Entonces 
            prim:= q
            ult:= q
            *q.prox:=NIL 
            *q.ant:=NIL 
         Sino 
            p:=prim
            Mientras (p<>NIL) y (*p.valor < *q.valor) Hacer
               p:=*p.prox
            FM 
            //INCIO
            Si p = prim Entonces 
               *q.prox:=p
               *q.ant:=NIL
               *p.ant:=q
               prim:=q
            Sino 
               //FINAL
               Si p=NIL Entonces 
                  *ult.prox:=q 
                  *q.ant:=ult 
                  *q.prox:= NIL 
                  ult:= q 
               Sino 
                  //MEDIO
                  *q.prox:=p 
                  *q.ant:=*p.ant 
                  *(*p.ant).prox:=q  
                  *p.ant:=q 
               FS 
            FS 
         FS 
      FP
      Procedimiento Eliminar() Es 
         Si prim=NIL Entonces 
            Escribir('ERROR. LISTA VACIA')
         Sino
            //busco que eliminar
            Escribir('Ingrese el valor a eliminar'); Leer(valorBuscado)
            p:=prim
            Mientras (p<>NIL) Y (*p.valor <> valorBuscado) Hacer 
               p:=*p.prox 
            FM 
            Si p=NIL Entonces
               Escribir('No se encontro el valor')
            Sino 
               //un solo nodo
               Si prim=ult Entonces
                  prim:=NIL 
                  ult:=NIL 
               Sino
                  //inicio
                  Si p=prim Entonces 
                     prim:=*p.prox
                     *prim.ant:=NIL
                  Sino
                     //final
                     Si p=ult Entonces 
                        ult:=*p.ant
                        *ult.prox:=NIL 
                     Sino 
                        //medio
                        *(*p.ant).prox:= *p.prox 
                        *(*p.prox).ant:= *p.ant
                     FS 
                  FS 
                  Disponer(p) 
               FS 
            FS
         FS
      FP
FINACCION

Accion LCS(prim: PUNTERO A NODO) Es 
   Ambiente
   NODO = Registro
      dato: Entero // Aquí va la info (DNI, Nombre, etc.)
      prox: Puntero a NODO
   FR
   p,q,ant,ult: Puntero a NODO
   
   Procedimiento CargaOrdenada() Es 
      Si prim = NIL Entonces
         // CASO 1: Lista vacía
         prim:=q
         ult:=q
         *q.prox := prim 
      Sino
         // CASO 2: Hay que buscar el lugar
         p:=prim
         ant:=NIL
         Mientras (*p.prox <> prim) Y (*p.dato<datoBuscado) Hacer
            ant:=p 
            p:=*p.prox
         FM
         // CASO A: El nuevo es el menor (va al inicio)
         Si (p=prim) Y (*p.dato>=*q.dato) Entonces
            *q.prox := prim
            prim := q 
            *ult.prox := q // El último ahora apunta al nuevo prim
         Sino
            Si (*p.prox=prim) Y (*p.dato<*q.dato) Entonces
            // CASO B: El nuevo va en el final
               *p.prox:=q 
               *q.prox:=prim 
               ult:=q //el nuevo nodo es el último ahora
            Sino
               //CASO C: Va en el medio
               *ant.prox := q
               *q.prox := p // Si p es prim, se cierra el círculo automáticamente
            FS
         FS
      FS
   FP

   Procedimiento Eliminar() Es 
      Si prim=NIL Entonces
         Escribir('ERROR')
      Sino 
         p:=prim 
         Mientras (*p.prox<>prim) Hacer 
            p:=*p.prox 
         FM 
         ult:=p 

         Escribir('ingrese el dato a eliminar'); Leer(datoBuscado)

         p:=prim 
         ant:=NIL 

         Mientras (*p.prox<>prim) Y (*p.dato<>datoBuscado) Hacer
            ant:=p 
            p:=*p.prox
         FM

         Si *p.dato<>datoBuscado Entonces
            Escribir('ERROR')
         Sino 
            //unico nodo
            Si prim=ult Entonces 
               prim:=NIL 
               ult:=NIL 
            Sino 
               //primer nodo
               Si p=prim Entonces 
                  prim:=*p.prox
                  *ult.prox:=*p.prox //el ultimo, apunta al nuevo primero 
               Sino 
                  //último nodo
                  Si *p.prox=prim Entonces   
                     *ant.prox:=prim
                     ult:=ant 
                  Sino 
                     //nodo del medio 
                     *ant.prox:=*p.prox 
                  FS 
               FS 
            FS
            DISPONER(p)
         FS
      FS
   FP 
FINACCION

Accion LCD(prim,ult: PUNTERO A NODO) Es 
   Ambiente
   NODO = Registro
      dato: Entero // Aquí va la info (DNI, Nombre, etc.)
      prox,ant: Puntero a NODO
   FR
   p,q,ant,ult: Puntero a NODO
   
   Procedimiento CargaOrdenadaDoble() Es 
      Si prim = NIL Entonces
         // CASO 1: Lista vacía
         prim:=q
         ult:=q
         *q.prox:=q
         *q.ant:=q 
      Sino
         // CASO 2: Hay que buscar el lugar
         p:=prim
         Mientras (p<>ult) Y (*p.dato<datoBuscado) Hacer
            p:=*p.prox
         FM
         // CASO A: El nuevo es el menor (va al inicio)
         Si (p=prim) Y (*p.dato>=*q.dato) Entonces
            *q.prox:=p
            *q.ant:=ult
            *p.ant:=q 
            prim := q 
            *ult.prox := q
         Sino
            Si (p=ult) Y (*p.dato<*q.dato) Entonces
            // CASO B: El nuevo va en el final 
               *ult.prox:=q 
               *q.prox:=prim
               *q.ant:=ult 
               ult:=q
               *prim.ult:=q
            Sino
               //CASO C: Va en el medio
               *q.ant:=*p.ant 
               *q.prox:=p 
               *(*p.ant).prox:=q 
               *p.ant:=q
            FS
         FS
      FS
   FP

   Procedimiento EliminarDoble() Es 
      Si prim=NIL Entonces
         Escribir('ERROR')
      Sino 
         Escribir('ingrese el dato a eliminar'); Leer(datoBuscado)
         //busco el dato a eliminar
         p:=prim 
         Mientras (p<>NIL) Y (*p.dato<>datoBuscado) Hacer 
            p:=*p.prox 
         FM 

         Si p=NIL Entonces
            Escribir('NO SE ENCONTRO EL VALOR')
         Sino  
            //unico nodo
            Si prim=ult Entonces 
               prim:=NIL 
               ult:=NIL 
            Sino 
               //primer nodo
               Si p=prim Entonces 
                  prim:=*p.prox
                  *ult.prox:=*p.prox //el ultimo, apunta al nuevo primero 
               Sino 
                  //último nodo
                  Si (p=prim) Y (*p.dato>=datoBuscado) Entonces   
                     *(*p.prox).ant:=ult 
                     prim:=*p.prox 
                     *ult.prox:=*p.prox 
                  Sino 
                     Si (p=ult) Y (*p.dato<datoBuscado)
                        *(*p.ant).prox:=prim 
                        ult:=*p.ant 
                        *prim.prox:=*p.ant 
                     Sino 
                        *(*p.prox).ant:=*p.ant 
                        *(*p.ant).prox:=*p.prox
                     FS 
                  FS 
               FS 
               DISPONER(p)
            FS
         FS
      FS
   FP


//carga encolada
prim:=NIL 
p:=NIL 
Mientras NFDS(sec) Hacer
   Nuevo(q)
   *q.dato:=v 
   *q.prox:=NIL 
   Si prim=NIL Entonces
      prim:=q
      *q.prox:=prim 
   Sino 
      *p.prox:=q 
   FS 
   *q.prox:=NIL 
   p:=q 
   AVZ(sec,v)
FM

prim:=NIL 
Mientras NFDS(sec) Hacer
   NUEVO(q)
   *q.dato:=v
   *q.prox:=NIL

   Si prim=NIL Entonces 
      prim:=q 
      *q.prox:=NIL 
   Sino 
      p:=prim
      ant:=NIL
      Mientras (p<>NIL) Y (*p.dato<*q.dato) Hacer 
         ant:=p 
         p:=*p.prox 
      FM 
      Si p = prim Entonces
         prim := q
      Sino 
         // Casos 3 y 4: Medio o Final
         *ant.prox := q
      FS
      *q.prox := p
   FS 
   AVZ(sec,v)
FM

aux:=p
Si p=prim Entonces
   prim:=*p.prox 
Sino 
   *ant.prox:=*p.prox 
FS 
p:=*p.prox 
Disponer(aux)

p:=prim 
Mientras p<>NIL Hacer
   p:=*p.prox
FM

p:=ult 
Mientras p<>NIL Hacer
   p:=*p.ant 
FM 

Nuevo(q)
*q.dato:=dato
//lista vacia 
Si prim=NIL Entonces
   prim:=q
   ult:=q 
   *q.prox:=NIL 
   *q.ant:=NIL 
Sino 
   p:=prim 
   Mientras (p<>NIL) Y (*p.dato<*q.dato) Hacer 
      p:=*p.prox 
   FM
   //antes del cabecera
   Si p=prim Entonces
      prim:=q 
      *q.prox:=p 
      *p.ant:=q 
      *q.ant:=NIL 
   Sino
      //final de la lista 
      Si p=NIL Entonces
         *q.prox:=NIL 
         *q.ant:=ult 
         *ult.prox:=q 
         ult:=q 
      Sino 
         //medio 
         *q.prox:=p 
         *q.ant:=*p.ant 
         *(*p.ant).prox:=q 
         *p.ant:=q 
      FS 
   FS 
FS 

Leer(dato)
p:=prim
Mientras (p<>NIL) Y (*p.dato<>dato) Hacer
   p:=*p.prox 
FM 
aux:=p
//unico nodo
Si prim=ult Entonces
   prim:=NIL 
   ult:=NIL 
Sino
   //primer nodo
   Si p=prim Entonces
      prim:=*p.prox 
      *prim.ant:=NIL 
   Sino 
      //ultimo nodo
      Si p=ult Entonces
         ult:=*p.ant 
         *ult.prox:=NIL 
      Sino 
         //nodo del medio
         *(*p.ant).prox:=*p.prox 
         *(*p.prox).ant:=*p.ant 
      FS 
   FS 
FS 
p:=*p.prox 
Disponer(aux)

p:=prim 
Mientras p<>prim Hacer
   Escribir('Mostrando nodo: ',*p.valor)
   p:=*p.prox 
FM 
Escribir('Mostrando último nodo: ',*p.valor)


Nuevo(q)
*q.dato := valor 
Si prim = NIL Entonces
   *q.prox:= q 
   prim:= q 
Sino
   // CASO A: Es menor al primero (Cambia el inicio)
   Si (*q.dato < *prim.dato) Entonces
      p := prim
      Mientras (*p.prox <> prim) Hacer // Busco el último para cerrar el círculo
         p:=*p.prox
      FM
      *q.prox:= prim
      *p.prox:= q
      prim:= q
   Sino
      // CASO B: En el medio o al final
      p := prim
      ant := NIL
      // El bucle busca el lugar o el final de la vuelta
      Mientras (*p.prox <> prim) Y (*p.dato < *q.dato) Hacer
         ant:= p
         p:= *p.prox
      FM
      // Verifico si el bucle terminó por valor o por llegar al final
      Si (*p.dato >= *q.dato) Entonces
         // Inserción en el medio
         *ant.prox:= q
         *q.prox:= p
      Sino
         // Inserción al final (p es el último nodo)
         *p.prox:= q
         *q.prox:= prim
      FS
   FS
FS

aux:=p
Si (p=*p.prox) Entonces  
// Es el único elemento de la lista 
   prim:=NIL
   p:=NIL
Sino       
// Lista con más de un elemento
   Si (prim=p) Entonces  
   //Hay que eliminar el primer elto. Actualizar el puntero del último elemento, buscandolo
      ant:= prim
      Mientras (*ant.prox <> prim) Hacer
         ant:= *ant.prox
      FM
      *ant.prox:= *p.prox
      prim:= *p.prox
   Sino
      *ant.prox:= *p.prox
   FS
   p:=*p.prox //avanzamos si o si, ya que el que dispongo es el aux, sino el pc se me pierde
FS
DISPONER(aux)
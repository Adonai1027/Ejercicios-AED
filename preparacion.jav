// Consigna: Escribir una acción que solicite al usuario el ingreso de 10 números enteros. El algoritmo debe calcular el promedio de los números pares e informar el resultado por pantalla.
Accion promedio Es 
Ambiente 
   numero, acum_pares, cuantos_pares, i: ENTERO 
   promedio: REAL 
Proceso
   acum_pares := 0 
   cuantos_pares := 0

   Para i:=1 hasta 10 Hacer 
      Escribir("Ingrese un número entero: ") 
      Leer(numero) 

      Si numero MOD 2 = 0 Entonces 
         acum_pares := acum_pares + numero 
         cuantos_pares := cuantos_pares + 1 
      FS
   FP 

   Si cuantos_pares > 0 Entonces 
      promedio := acum_pares / cuantos_pares 
      Escribir("El promedio de los números pares es: ", promedio)
   Sino 
      Escribir("No se ingresaron números pares.")     
   FS
FinAccion

// Consigna Propuesta: Se tiene una secuencia de caracteres (entrada) que contiene una oración terminada en punto ("."). Contar cuántas veces aparece la letra "a" (minúscula o mayúscula) en toda la secuencia.

Accion contar_a Es 
AMBIENTE 
   sec: SECUENCIA DE CARACTERES 
   v: CARACTER 
   cont: ENTERO 
PROCESO
   ARR(sec);AVZ(sec,v);
   cont := 0
   Mientras NFDS(sec) Hacer 
      Mientras v <> '.' Hacer 
         si v = 'a' O v = 'A' Entonces 
            cont := cont + 1
         FIN_SI
         AVZ(sec,v)
      FIN_MIENTRAS 
      AVZ(sec,v) //avanzo el punto
   FIN_MIENTRAS
   Escribir("La letra 'a' aparece ", cont, " veces en la secuencia.")
FinAccion

// Consigna: se tiene una secuencia de caracteres con las ventas del día. La secuencia tiene este formato: CCVCCV...# (Donde C es el código de categoría de producto - 'A', 'B' o 'C' - y V es el valor de la venta - un dígito del '0' al '9'). La secuencia está ordenada por categoría. Se pide informar el total vendido por cada categoría.
//AA0BB1CC2#
ACCIÓN Ventas_Por_Categoria ES
   AMBIENTE
      sec: SECUENCIA DE CARACTER
      v, cat_anterior: CARACTER
      total_cat: ENTERO
   PROCESO
      ARR(sec)
      AVZ(sec, v)

      MIENTRAS v <> '#' HACER
         cat_anterior := v  // Resguardo la categoría actual
         total_cat := 0     // Limpio el acumulador para ESTA categoría

         // Ciclo de la misma categoría
         MIENTRAS (v <> '#') Y (v = cat_anterior) HACER
               AVZ(sec, v) // Salto la categoría 
               AVZ(sec, v) // Leo el valor de la venta
               
               total_cat := total_cat + VALOR(v)
               
               AVZ(sec, v) // Salto el valor para ir a la siguiente Categoría
         FIN_MIENTRAS

         ESCRIBIR("Total de la categoría ", cat_anterior, " es: ", total_cat)
      FIN_MIENTRAS
FIN_ACCIÓN






//=================2do parcial========================
Accion gimnasio Es 
   Ambiente
      socios=REGISTRO   
         dni:N(8)
         estado:('Activo', 'Moroso')
      FR 
      arch_mae, mae_act: archivo de socios ordenado por dni 
      reg_mae, reg_act, aux: socios 

      pagos=REGISTRO
         dni:N(8)
      FR 
      arch_mov: archivo de pagos ordenado por dni 
      reg_mov: pagos 

      Procedimiento LeerMae() Es
         Leer(arch_mae,reg_mae)
         Si FDA(arch_mae) Entonces
            reg_mae.dni:=HV
         FS
      FP
      Procedimiento LeerMov() Es 
         Si FDA(arch_mov) Entonces
            reg_mov.dni:=HV
         FS 
      FP 

      cantidad:Arreglo[1..2] de N(1)

   Proceso 
      ABRIR E/(arch_mae);LeerMae()
      ABRIR E/(arch_mov);LeerMov()
      ABRIR /S(mae_actualizado)

      Para i:=1 Hasta 2 Hacer 
         cantidad[i]:=0
      FP

      Mientra reg_mae.dni<>HV O reg_mov.dni<>HV Hacer
         Si reg_mae.dni < reg_mov.dni Entonces  
            reg_act:=reg_mae

            Si reg_act.estado = 'Activo' Entonces
               cantidad[1] := cantidad[1] + 1
            Sino
               cantidad[2] := cantidad[2] + 1
            FS

            Grabar(mae_act,reg_act)
            LeerMae()
         Sino
            Si reg_mae.dni = reg_mov.dni Entonces
               aux:=reg_mae
               Mientras reg_mae.dni = reg_mov.dni Hacer  
                  aux.estado:='Activo'
                  LeerMov()
               FM 
               reg_act:=aux
               
               Si reg_act.estado = 'Activo' Entonces
                  cantidad[1] := cantidad[1] + 1
               Sino
                  cantidad[2] := cantidad[2] + 1
               FS

               Grabar(mae_act,reg_act)
               LeerMae()
            Sino
               LeerMov()
               Escribir('ERROR')
            FS 
         FS 
      FM
      Escribir('Total activos: ',cantidad[1])
      Escribir('Total morosos: ',cantidad[2])
      CERRAR(mae_act); CERRAR(arch_mae); CERRAR(arch_mov)
FIN_ACCIÓN


      




//================== tercer parcial =====================
// Lista Simplemente Enlazada (Simple) Es una cadena unidireccional donde cada nodo conoce solo al siguiente.
Ambiente
   NODO = Registro
      dato: Entero
      prox: Puntero a NODO
   FinRegistro
   prim, p, q, a: Puntero a NODO
// Algoritmo de Inserción Ordenada (Descendente): Este algoritmo busca el hueco correcto para que la lista siempre esté organizada.
Proceso
   Nuevo(q)           // Creo el nuevo nodo
   *q.dato := valor   // Le asigno el dato
   a := nil           // Puntero anterior
   p := prim          // Puntero actual

   // Recorrido para encontrar la posición
   Mientras (p <> nil) y (*q.dato < *p.dato) Hacer 
      a := p 
      p := *p.prox   // Avanzo
   FinMientras

   Si a = nil Entonces   // Caso: Insertar al principio
      *q.prox := prim 
      prim := q 
   sino                  // Caso: Insertar en el medio o final
      *a.prox := q 
      *q.prox := p 
   FinSi

// Lista Doblemente Enlazada (Doble)
// Aquí cada nodo tiene "dos brazos": uno que agarra al de adelante (prox) y otro al de atrás (ant).

Ambiente
   NODO_D = Registro
      dato: Entero
      ant: Puntero a NODO_D
      prox: Puntero a NODO_D
   FinRegistro
   primd, ult, d, q: Puntero a NODO_D
//q significa siempre NUEVO
//Es el puntero que apunta al nodo que acabas de crear con la instrucción Nuevo(q). Imaginalo como un vagon nuevo que todavía está suelto y quieres enganchar a la formación.

//p o a veces d es para recorrer la lista (como AVZ). Se mueve de nodo en nodo (d := *d.prox) buscando en qué lugar debe ir q.

//a pero en simple, en doble ya existe, es el ANTERIOR, guarda la info del nodo que está atrás de p. En doblemente tenemos el ant del nodo y se accede *d.ant

//prim o primd es la entrada, es el puntero que apunta siempre al primer nodo de la lista entonces nunca se usa este para desplazarse en las listas porque si se pierde, se pierde toda la lista completa

//ult o ultd es la salida, puntero que apunta al ultimo nodo. se usa mucho en dobles para insertar al final o recorrer hacia atrás

// Algoritmo de Inserción Ordenada: Es más complejo porque hay que conectar más "brazos".
Proceso
   Si primd = nil Entonces  // Lista vacía
      primd := q 
      ult := q 
      *q.prox := nil 
      *q.ant := nil
   Sino 
      d := primd
      Mientras (d <> nil) y (*q.dato < *d.dato) Hacer
         d := *d.prox
      FinMientras

      Si d = primd Entonces // Insertar al principio
         primd := q
         *q.prox := d 
         *d.ant := q
         *q.ant := nil 
      Sino
         Si d = nil Entonces // Insertar al final
               *q.prox := nil
               *q.ant := ult 
               *ult.prox := q
               ult := q
         Sino                // Insertar en el medio
               *q.prox := d 
               *q.ant := *d.ant 
               *(*d.ant).prox := q // El anterior de d ahora apunta al nuevo
               *d.ant := q
         FinSi
      FinSi
   FinSi

// Lista Circular
// Es como una lista simple, pero el último nodo no apunta a nil, sino que vuelve al primero, cerrando un círculo.

// Diferencia clave en el Recorrido: El ciclo no termina cuando llegamos a nil, sino cuando el puntero "siguiente" vuelve a ser el inicio.
Ambiente
   NODO = Registro
      dato: Entero
      prox: Puntero a NODO
   FinRegistro
   prim, p, q, a: Puntero a NODO
Proceso
   p := prim
   Mientras (*p.prox <> prim) Hacer
      // Procesar datos (*p.dato)
      p := *p.prox
   FinMientras

//ej 4.6 github
Accion ej46github Es 
   Ambiente 
      formato_fecha=Registro 
         aaaa:N(4)
         mm:1..12
         dd:1..31
      FR 

      clientes=Registro
         nombre:AN(255)
         nro_mesa:N(3)
         total_consumido:REAL 
         fecha_atencion: formato_fecha
      FR 
      arch: archivo de clientes ordenado por nombre 
      reg: clientes

      NODO = REGISTRO 
         dato: clientes 
         prox: PUNTERO A NODO 
      FR 
      prim,p,a,q: PUNTERO A NODO 

      nombre:AN
      monto,opc:ENTERO

   Proceso 

      Repetir
         Escribir('Eliga una opcion: 1- Añadir Cliente 2- Registrar consumo / Realizar cobro y Eliminar'); Leer(opc)
         
         Segun opc Hacer 
            =1:
               //agrego un nodo nuevo
               Nuevo(q)
               //cargar los valores a ese nodo
               Escribir('Nombre del cliente: '); Leer(*q.dato.nombre)
               Escribir('Asignar numero de mesa: '); Leer(*q.dato.nro_mesa)
               *q.dato.total_consumido:=0
               Escribir('Ingrese la fecha: '); Leer(*q.dato.fecha_atencion)

               a:=nil //el nodo anterior apunta a nil inicialmente
               p:= prim //me paro en el nodo inicial

               //busqueda
               Mientras (p<>NIL) Y (*q.dato.nombre>*p.dato.nombre) Hacer 
                  a:=p 
                  p:=*p.prox 
               FM 

               Si a=NIL Entonces 
                  //nuevo cliente es el primero alfabeticamente
                  *q.prox:=prim
                  prim:=q
               Sino 
                  *a.prox:= q
                  *q.prox:= p
               FS
            =2:
               Escribir('Ingresar nombre: '); Leer(nombre)
               Escribir('Ingrese monto: '); Leer(monto)

               p:=prim 
               a:=NIL //siempre
               Mientras (p<>NIL) Y (*p.dato.nombre<>nombre) Hacer
                  a:=p //siempre guardar el anterior
                  p:=*p.prox
               FM
               //salgo de acá y p está en el nombre que buscamos

               Si p<>NIL Entonces   
                  *p.dato.total_consumido:= *p.dato.total_consumido+monto
                  Escribir('Cliente: ',*p.dato.nombre)
                  Escribir('Fecha: ',*p.dato.fecha_atencion)
                  Escribir('Mesa: ', *p.dato.nro_mesa)
                  Escribir('Total: ',*p.dato.total_consumido)
                  //eliminacion
                  Si a=NIL Entonces 
                     prim:=*p.prox //primero de la lista
                  Sino
                     *a.prox:= *p.prox 
                  FS 
                  Disponer(p)
               Sino 
                  Escribir('No existe ese cliente')
               FS
            Otro caso: Escribir('ERROR')
         FS 
         Escribir('Para salir, pulse 3')
         Leer(opc)
      Hasta Que opc=3
FinAccion

//ej 4.12 github
Accion ej412(prim,ult:PUNTERO A NODO) Es 
   Ambiente
      DATOS_PEDIDO = Registro
         nombre: AN(50)
         direccion: AN(100)
         telefono: AN(20)
         total: Real
         estado: ('P','E') // 'P' (Pendiente) o 'E' (Enviado)
      FR

      NODO = Registro
         dato: DATOS_PEDIDO
         ant, prox: Puntero a NODO
      FinRegistro  

      p,q: PUNTERO A NODO

      nombre_buscado:AN 
      opc:ENTERO

      Procedimiento CrearNodo() Es 
         Nuevo(q)
         Escribir('NOMBRE: ');Leer(*q.dato.nombre)
         Escribir('DIRECCION: ');Leer(*q.dato.direccion)
         Escribir('TELEFONO: ');Leer(*q.dato.telefono)
         *q.dato.total:=0
         *q.dato.estado:='P'
      FP 
      Procedimiento CargaOrdenadaDoble() Es 
         //VACIA
         Si prim=NIL Entonces 
            prim:= q
            ult:= q
            *q.prox:=NIL 
            *q.ant:=NIL 
         Sino 
            p:=prim
            Mientras (p<>NIL) y (*q.dato.nombre > *p.dato.nombre) Hacer
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
                  *q.prox:=NIL 
                  *q.ant:=ult 
                  *ult.prox:=q
                  ult:=q
               Sino 
                  //MEDIO
                  *q.prox:=*p
                  *q.ant:=*p.ant
                  *(*p.ant).prox:=q 
                  *p.ant:=q 
               FS 
            FS 
         FS 
      FP

      Procedimiento Eliminar() Es 
         Si p=prim Entonces 
            prim:=*p.prox 
            Si prim <> NIL Entonces 
               *prim.ant:=NIL 
            FS 
         Sino
            Si p=ult Entonces 
               ult:=*p.ant 
               *ult.prox:=NIL 
            Sino 
               *(*p.ant).prox:= *p.prox 
               *(*p.prox).ant:= *p.ant
            FS 
         FS 
         Disponer(p) 
      FP
      
      Procedimiento Busqueda() Es 
         Escribir('Ingrese el nombre a buscar: ');Leer(nombre_buscado)
         p:=prim 
         Mientras (p<>NIL) Y (*p.dato.nombre <> nombre_buscado) Hacer 
            p:=*p.prox
         FM

         Si p <> NIL Entonces 
            Si *p.dato.estado='P' Entonces
               *p.dato.estado:='E'
               Escribir('Pedido enviado a: ',*p.dato.direccion)
            Sino 
               Escribir('Ya fue enviado')
               Escribir('Desea eliminar? Pulse 0');Leer(opc)
               Si opc=0 Entonces
                  Eliminar()
               FS
            FS
         Sino 
            Escribir('Cliente no encontrado') 
         FS
      FP

   Proceso 
      Repetir 
         Escribir('Seleccione una opcion: ');Leer(opc)
         Segun opc Hacer 
            =1:
               CrearNodo()
               CargaOrdenadaDoble()
            =2: 
               Busqueda()
            Otro: 
               Escribir('ERROR')
         FS 

         Escribir('Desea Salir? Pulse 3');Leer(opc)
      Hasta Que opc=3 
FIN_ACCIÓN


//recursividad 
Funcion SumarVector(V: ARREGLO, i: ENTERO, N: ENTERO) : ENTERO Es
Proceso
   Si i > N Entonces
      SumarVector:=0
   Sino
      SumarVector:=V[i]+SumarVector(V,i+1,N)
   FS
FP

Funcion MaximoVector(V:ARREGLO, i:ENTERO, N:ENTERO):ENTERO Es
   max_resto: ENTERO
   Si i = N Entonces
      // CASO BASE: Llegamos al último casillero. 
      // El máximo de un solo número es el número mismo.
      MaximoVector := V[i]
   Sino
      // PASO RECURSIVO: 
      // 1. Llamamos a la función para que busque el máximo en el resto del vector
      max_resto := MaximoVector(V,i+1,N)

      // 2. Comparamos nuestro valor actual con el máximo que vino del resto
      Si V[i] > max_resto Entonces
         MaximoVector := V[i]
      Sino
         MaximoVector := max_resto
      FS
   FS
FF

//Hacer una función que sume todos los valores de una lista simple de números.
Funcion SumarLista(p: PUNTERO A NODO): ENTERO Es
   Si p = NIL Entonces
      SumarLista:=0
   Sino
      SumarLista:=*p.valor + SumarLista(*p.prox)
   FS
FF

Funcion ExisteDNI(p: PUNTERO A NODO, dni_buscado: N(8)) : BOOLEANO Es
   Si p = NIL Entonces
      ExisteDNI := FALSO
   Sino
      Si *p.dni = dni_buscado Entonces
         ExisteDNI := VERDADERO
      Sino
         ExisteDNI := ExisteDNI(*p.prox, dni_buscado)
      FS
   FS
FF 

//Imprimir los nombres de una lista simple en orden inverso (del último al primero)
Procedimiento Inverso(p:PUNTERO A NODO) Es 
   Si p<>NIL Entonces 
      Inverso(*p.prox)
      Escribir('Nombre: ',*p.nombre)
   FS 
FF

//Conteo Condicional
//Escribir una función recursiva que cuente cuántas swifties de la lista tienen más de 18 años (suponiendo que el nodo tiene un campo edad).
Funcion ContarMayores(p: PUNTERO A NODO) : ENTERO Es
   Si p = NIL Entonces
      ContarMayores:=0
   Sino
      Si *p.edad > 18 Entonces
         ContarMayores:=1+ContarMayores(*p.prox)
      Sino
         ContarMayores:=ContarMayores(*p.prox)
      FS
   FS
FF

//Encontrar el nombre de la Swiftie con mayor edad en la lista
Funcion MayorEdad(p:PUNTERO A NODO):PUNTERO A NODO Es
   aux:PUNTERO A NODO
   Si *p.prox=NIL Entonces 
      MayorEdad:=p
   Sino
      aux:=MayorEdad(*p.prox) 
      
      Si *p.edad > *aux.edad Entonces
         MayorEdad:=p
      Sino 
         MayorEdad:=aux
      FS 
   FS 
FF

//guia trabajos practico

//Ejercicio 5.1.1  Calcular el factorial de un número positivo n. Tener en cuenta la definición matemática de n!

Funcion Factorial(n:Entero):Entero Es 
   Si n=1 Entonces
      Factorial:=1 
   Sino 
      Factorial:=n*Factorial(n-1)
   FS 
FF

//5.1.2 Dado un número n  como parámetro de entrada, calcular el n-ésimo número de la serie de Fibonacci. Tener en cuenta la siguiente definición

Funcion Fib(n:ENTERO):ENTERO Es 
   Si n=1 o n=2 Entonces   
      Fib:=1
   Sino 
      Fib:=Fib(n-1)+Fib(n-2)
   FS 
FS 

// 5.1.3 Dados dos números a y b Calcule la potencia  usando sólo multiplicaciones sucesivas.
Funcion Pot(a,b:ENTERO):ENTERO 
   Si b=0 Entonces 
      Pot:=1 
   Sino 
      Si b=1 Entonces
         Pot:=a 
      Sino 
         Pot:=a*Pot(a,b-1)
      FS 
   FS 
FS 

//Ejercicio 5.1.4¶
// Construir un algoritmo recursivo que permita determinar si los dígitos de un número n dado son todos pares.

Funcion DigPar(n:ENTERO):BOOLEANO Es 
   digito:= n MOD 10
   Si n<10 Entonces
      DigPar:=(digito MOD 2 = 0 )
   Sino
      Si (digito MOD 2 <> 0) Entonces
         DigPar:=FALSO 
      Sino 
         DigPar:= DigPar(n DIV 10)
      FS 
   FS 
FS

Procedimiento EliminarDoble() Es 
   aux:=p 
   Si prim=ult Entonces
      prim=:NIL 
      ult:=NIL 
   Sino 
      Si p=prim Entonces
         prim:=*p.prox 
         *prim.ant:=NIL 
      Sino 
         Si *p.prox=NIL Entonces
            ult:=*p.ant
            *ult.prox:=NIL 
         Sino 
            *(*p.ant).prox:=*p.prox 
            *(*p.prox).ant:=*p.ant 
         FS 
      FS 
   FS 
   p:=*p.prox 
   Disponer(aux)
FP
//ubicar ult en su lugar
ult:=prim 
Mientras *ult.prox<>NIL Hacer 
   ult:=*ult.prox 
FM 

p:=prim 
Mientras p<>NIL Hacer 
   Si NO(Cumple()) Entonces
      CargarListaX()
      EliminarDoble()
   Sino 
      p:=*p.prox 
   FS 
FM 
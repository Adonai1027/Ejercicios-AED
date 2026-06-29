Accion Recursividad (prim:Puntero a NODO) Es
 Ambiente
    NODO = Registro
        dni_paciente
        nya
        edad
        nro_cama
        nro_habitacion
        temperatura: (a:Arreglo[1..4] de Real)
    FinRegistro
    p,a,q:Puntero a NODO
    posc:Entero
    Funcion temp(temperatura[posc],posc,suma:Entero):Real
        Si posc = 4 Entonces
            temp:=(temperatura[posc]+suma)/4
        sino 
            temp:=temp(temperatura,posc + 1,suma + temperatura[posc])
        FinSi
    FinFuncion  
 Proceso
    p:=prim
    a:=nil 
    Mientras p <> nil Hacer
        Si temp(*p.temperatura,1,0) < 36,5 Entonces    
            Escribir('Nombre y Apellido',*p.nya)
            Si a = nil Entonces
                prim:=*p.prox
            sino 
                *a.prox:=*p.prox
            FinSi
            z:=p
            p:=*p.prox
            Disponer(z)
        sino 
            a:=p
            p:=*p.prox
        FinSi 
    FinMientras
FinAccion
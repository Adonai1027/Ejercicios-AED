Ejercicio Mundiañ
Accion  (prim:Puntero a NODO)
ambiente
 NODO=registro
     pais:AN(40)
     A:arreglo [1..26] de entero
     grupo:N(1)
     puntos:N(1)
     taram:N(2)
     tarroj:N(2)
     Prox:puntero a NODOS
  Finregistro
 PARTIDOS=registro
   ID_partido:
   equipo_local:
   equipo_visitante
   prox:puntero a NODO
 Finregistro
 EQUIPOS=registro
   equipo:AN(30)
   golafavor:N(2)
   golencontra:N(2)
  Finregistro
 RESULTADOS=registro
   ID_partido
   Cantidad_goles_local:N(2)
   Cantidad_goles_visitante:N(2)
   tarjeta_roja
   tarjeta_amarilla
 Finregistro
 NODOD=registro
   equipo:AN(40)
   tarjeta_roja:N(2)
   prox:Puntero a NODOD
   ant:puntero a NODOD
 Finregistro
 p,q:puntero a NODO
 pd,qd,primd,ultd:puntero a NODOD
 arch_res:archivo de RESULTADOS indexado por ID_partido
 reg:RESULTADOS
 arch_partidos:archivo de PARTIDOS
 reg_part:PARTIDOS
 difgoles,tarj,il,iv,i:entero
 equipomayroj:alfanumerico
 A:arreglo [1..32] de EQUIPOS
 B:booleano
Proceso
  p:=prim
  para i=1 a 32 hacer
    A[i].equipo:=p.equipo
    A[i].grupo:=p.grupo
    A[i].golafavor:=0
    A[i].golencontra:=0
    p:=p*.prox
  finpara
   ABRIR E/S (arch_res)
   p:=prim
   primd=nill  
   ultd:=nill
   ABRIR S/(arch_partidos)
   LEER (arch_partidos,reg_part)
   p:=prim
   q:=prim
   mientras NDFA (arch_partidos) hacer
     
    mientras p<> nill y p*.equipo<>reg_part.equipo_local hacer
     p:=p*.prox
    finmientras
     mientras q<> nill y p*.equipo<>reg_part.equipo_visitante hacer
      q:=q*.Prox
     finmientras
    reg.ID_partido:=reg_part.ID_partido
    leer (reg,arch_res)
    SI EXISTE entonces
      para i=1 a 32 hacer 
        si reg_part.equipo_local = p*.equipo_local entonces
          il:=i
        Finsi
        si reg_part.equipo_visitante = p*.equipo_visitante entonces
          iv:=i
        Finsi
      finpara
      A[il].golafavor:=A[il].golafavor+reg.Cantidad_goles_local
      A[il].golencontra:=A[il].golencontra+reg.Cantidad_goles_visitante
      A[iv].golafavor:=A[iv].golafavor+reg.Cantidad_goles_visitante
      A[iv].golencontra:=A[iv].golencontra+reg.Cantidad_goles_local
      p.tarroj:=p.tarroj+reg.tarjeta_roja_local
      p.taram:=p.taram+reg.tarjeta_roja_visitante
      q.tarroj:=q.tarroj+reg.tarjeta_roja_visitante
      q.taram:=q.taram+reg.tarjeta_amarilla_visitante
      segun reg.Cantidad_goles_local hacer
        >reg.Cantidad_goles_visitante:p.puntos:=p.puntos+3
        =reg.Cantidad_goles_visitante:p.puntos:=p.puntos+1
                                      q*.puntos:=q.puntos+1
        <reg.Cantidad_goles_visitante:q.puntos:=q.puntos+3
      finsegun
    Finsi
    leer (arch_partidos,reg_part)
   finmientras
   p:=prim
   mientras p<>nill hacer
     si p.puntos > 4 entonces
       B:=V
      SINO
        si p.puntos=3 entonces
           para i=1 a 32 hacer
             si p.grupo=A[i].grupo y p.equipo<>A[i].equipo entonces
               q:=prim
               mientras q<>nill y q.equipo<> A[i].equipo entonces
                 q:=q.prox
                finmientras
                si q.equipo=A[i].equipo y q.puntos>9 entonces
                  B:=V
                Finsi
              Finsi
              Finsi
            finpara

        SINO
           Si p.puntos=4 entonces
            para i=1 a 32 hacer
                si p.equipo=a[i].equipo entonces
                  difgoles:=A[i].golafavor-A[i].golencontra
                   si difgoles>2 entonces
                     B:=V
                   Finsi
                Finsi
            finpara
           Finsi
         Finsi
      Finsi  
        Si b=V entonces
          CARGA_ENCOLADA Y ORDENADA DOBLE()
        Finsi
      p:=p*.prox
    finmientras
    tarj:=0
    Mientras p<> nill hacer
      si tarj<p.tarroj hacer
        tarj:=p.tarroj
        equipomayroj:=p.equipo
      Finsi
    finmientras
    MOSTRAR EQUIPOS
    MOSTRAR EQUIPO CON MAYOR CANT tarjeTAS
finaccion
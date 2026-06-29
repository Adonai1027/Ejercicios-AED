Accion ej1 Es 
   Ambiente 
      viajes=Registro 
         nro_viaje:N(3)
         ciu_origen,ciu_destino:1..5
         plazas_dispo:N(2)
      FR 
      arch:archivo secuencial de viajes 
      reg:viajes 

      NODO=Registro 
         nro:N(3)
         og,dest:1..5
         cap_max:ENTERO
         vip,eco:ENTERO 
         vendido_total:ENTERO 
         prox:PUNTERO A NODO 
      FR 
      p,q,prim:PUNTERO A NODO 

      A:ARREGLO[1..2] DE ENTERO 

      resg_nro,mayor,cont_boleto,cant_vuelos_vacios,opc,i,cant_vuelos:ENTERO 
      categoria,og_usu,dest_usu:ENTERO 
   Proceso 
      ABRIR E/(arch);Leer(arch,reg)

      prim:=NIL 
      cont_boleto:=0
      cant_vuelos_vacios:=0
      opc:=0
      Para i:=1 Hasta 2 Hacer 
         A[i]:=0
      FP 

      Mientras NFDA(arch) Hacer 
         NUEVO(q)
         *q.nro:=reg.nro_viaje
         *q.og:=reg.ciu_origen
         *q.dest:=reg.ciu_destino
         *q.cap_max:=reg.plazas_dispo
         *q.vip:=(reg.plazas_dispo*0.30)
         *q.eco:=(reg.plazas_dispo*0.70)
         *q.vendido_total:=0
         //encolada
         Si prim=NIL Entonces
            prim:=q 
            *q.prox:=NIL 
            p:=q 
         Sino 
            *p.prox:=q
            *q.prox:=NIL 
            p:=q 
         FS 
         cant_vuelos:=cant_vuelos+1
         Leer(arch,reg)
      FM 

      Mientras opc<>1 Hacer 
         Escribir('Ingrese el origen, destino y categoria(|1 VIP|2 ECO|): ');Leer(og_usu,dest_usu,categoria)

         //buscamos viaje
         p:=prim 
         Mientras p<>NIL Y (*p.og<>og_usu O *p.dest<>dest_usu) Hacer 
            p:=*p.prox 
         FM 
         //exito, encontramos un viaje
         Si p<>NIL Entonces
            Si (*p.og=og_usu Y *p.dest=dest_usu) Entonces
               Segun categoria Hacer 
                  1: 
                     Si (*p.vip>0) Entonces 
                        *p.vip:=*p.vip-1
                        *p.vendido_total:=*p.vendido_total+1
                        A[1]:=A[1]+1
                        cont_boleto:=cont_boleto+1
                     Sino 
                        Escribir('RECHAZADO')
                     FS
                  2: 
                     Si (*p.eco>0) Entonces
                        *p.eco:=*p.eco-1
                        *p.vendido_total:=*p.vendido_total+1
                        A[2]:=A[2]+1
                        cont_boleto:=cont_boleto+1
                     Sino 
                        Escribir('RECHAZADO')
                     FS 
               FS 
            FS 
         Sino
            Escribir('NO SE ENCONTRO EL VIAJE')
         FS
         Escribir('PARA SALIR PULSE 1. PARA CONTINUAR 0');Leer(opc)
      FM
      //1
      Escribir('Cantidad de vip: ',A[1],' Cantidad de economicos: ',A[2])
      p:=prim 
      mayor:=LV
      Mientras p<>NIL Hacer 
         //2
         Si *p.vendido_total>mayor Entonces
            mayor:=*p.vendido_total
            resg_nro:=*p.nro
         FS 
         //3
         Si *p.vendido_total<(*p.cap_max*0.5) Entonces
            cant_vuelos_vacios:=cant_vuelos_vacios+1
         FS
         p:=*p.prox 
      FM 
      //2
      Escribir('EL VIAJE: ',resg_nro,' FUE EL QUE TUVO MAS BOLETOS VENDIDOS')
      //3
      Escribir('PORCENTAJE DE VUELOS: ',((cant_vuelos_vacios*100)/cant_vuelos))
      //4
      Escribir('Total boletos vendidos: ',cont_boleto)
      Cerrar(arch)
FINACCION

Accion ej2 Es 
   Ambiente
      sec:SECUENCIA DE CARACTERES 
      v:CARACTER 
      datos=Registro 
         nro_patente:AN(6)
         chofer:AN(60)
      FR 
      arch:archivo secuencial de datos 
      reg:datos 

      NODO=Registro 
         cantidad_capicua:ENTERO 
         patente:AN(6)
         nombre:AN(60)
         prox:PUNTERO A NODO 
      FR 
      p,q,prim,ant:PUNTERO A NODO 

      Funcion ConvertirNum(c:CARACTER):ENTERO Es 
         Segun c Hacer 
            '0': ConvertirNum:=0
            '1': ConvertirNum:=1
            '2': ConvertirNum:=2
            '3': ConvertirNum:=3
            '4': ConvertirNum:=4
            '5': ConvertirNum:=5
            '6': ConvertirNum:=6
            '7': ConvertirNum:=7
            '8': ConvertirNum:=8
            '9': ConvertirNum:=9
         FS 
      FF 

      Funcion Capicua(num,indice:ENTERO):BOOLEANO Es 
         primer_dig,ultimo_dig:ENTERO 
         Si indice<=0 Entonces
            Capicua:=VERDADERO
         Sino  
            //extraigo extremos
            primer_dig:=TRUNC(num / (10**indice))
            ultimo_dig:=(num MOD 10)
            Si primer_dig<>ultimo_dig Entonces
               Capicua:=FALSO 
            Sino
               num:=(num MOD (10**indice)) //saco el primer digito
               num:=TRUNC(num/10) //saco el ultimo digito
               //resto 2 porque saque 2 digitos
               Capicua:=Capicua(num,indice-2)
            FS 
         FS
      FF
      n,i,top:ENTERO 
   Proceso
      ARR(sec);ABRIR E/(arch)
      AVZ(sec,v);Leer(arch,reg)
      prim:=NIL
      Mientras NFDA(arch) Y v<>'*' Hacer
         i:=0
         n:=0
         Mientras v<>'+' Hacer 
            n:=(n*10)+ConvertirNum(v)
            i:=i+1
            AVZ(sec,v)
         FM 
         //func capicua
         Si Capicua(n,i-1) Entonces
            //creo el nodo
            NUEVO(q)
            *q.cantidad_capicua:=n 
            *q.patente:=reg.nro_patente
            *q.nombre:=reg.chofer
            //carga ordenada
            Si prim=NIL Entonces
               prim:=q 
               *q.prox:=NIL 
            Sino 
               p:=prim 
               ant:=NIL 
               //DESCENDENTE 10-9-8-...-1
               Mientras p<>NIL Y (*p.cantidad_capicua>*q.cantidad_capicua) Hacer 
                  ant:=p 
                  p:=*p.prox 
               FM 
               Si p=prim Entonces
                  prim:=q 
                  *q.prox:=p 
               Sino
                  *ant.prox:=q 
                  *q.prox:=p  
               FS 
            FS
         FS 
         AVZ(sec,v)
         Leer(arch,reg)
      FM 
      p:=prim 
      top:=10
      Mientras p<>NIL Y top<>0 Hacer
         Escribir('TOP: ',top)
         Escribir('CANTIDAD ENTREGAS: ',*p.cantidad_capicua)
         Escribir('NOMBRE: ',*p.nombre)
         Escribir('PATENTE: ',*p.patente)
         top:=top-1
         p:=*p.prox 
      FM 
      Cerrar(sec);Cerrar(arch)
FINACCION


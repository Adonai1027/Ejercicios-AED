# 📚 Algoritmos y Estructuras de Datos (AED) - UTN FRRE

Bienvenido al repositorio de **Algoritmos y Estructuras de Datos (AED)** para la carrera de Ingeniería en Sistemas de Información (ISI) en la Universidad Tecnológica Nacional - Facultad Regional Resistencia (UTN FRRE).

Este repositorio contiene ejercicios resueltos, modelos de examen de parciales y finales, machetes con estructuras base, guías teóricas y plantillas en el pseudocódigo formal de la cátedra.

---

## 🏷️ Nomenclatura y Confiabilidad de los Archivos

Para facilitar la navegación y saber la confiabilidad de cada solución, los archivos siguen esta convención de nombres:

* **`{COMPLETO}`**: Indica que el archivo tiene **resueltos la totalidad de los ejercicios / consignas** del parcial o modelo.
* **`{HECHO}`**: Indica que el archivo tiene resuelto **al menos 1 de los ejercicios** o consignas principales del examen.
* **Archivos CON llaves `{...}`**: Son ejercicios **revisados y resueltos más recientemente**, con mayor rigor algorítmico y máxima confiabilidad.
* **Archivos SIN llaves `{}`**: Fueron resueltos originalmente **durante la cursada inicial**. *(Tomar con pinzas: se hicieron mientras se aprendía el tema y pueden contener inconsistencias menores o aproximaciones de aprendizaje)*. También por otras personas o durante la resolución de la clase dadas por profesores. En fin puede ser cualquier cosa.

---

## 📂 Organización del Repositorio

El repositorio está organizado por instancias de evaluación y temáticas:

```text
AED/
├── 📁 1er_parcial/
│   └── 📁 SECUENCIA/           # Algoritmos sobre secuencias de caracteres y valores
├── 📁 2do_parcial/
│   ├── 📁 ACTUALIZACION/       # Apareo / Actualización Secuencial (Maestro vs Novedades)
│   ├── 📁 CORTE/               # Algoritmos de Corte de Control en archivos
│   ├── 📁 ARR/                 # Arreglos (Vectores, Matrices, Tablas de Búsqueda)
│   ├── 📄 esqueleto_act.jav    # Plantilla / Esqueleto base de Actualización Secuencial
│   └── 📄 machete_arreglos.jav # Resumen y sintaxis rápida para arreglos y matrices
├── 📁 3er_parcial/
│   ├── 📁 RECUR/               # Algoritmos recursivos
│   ├── 📁 LISTAS/              # Listas simples, dobles y punteros
│   ├── 📄 arboles.jav          # Conceptos y algoritmos de Árboles
│   └── 📄 machete_listas.jav   # Guía rápida para manejo de listas y memoria dinámica
├── 📁 MODELOS_FINAL/
│   ├── 📁 FINALES/             # Resoluciones y parciales de finales
│   └── 📄 *.jpeg / *.jpg       # Modelos de exámenes finales de distintas fechas (Julio, Agosto, Dic, etc.)
├── 📁 TEORIA/                  # Material teórico conceptual de la materia
├── 📄 cositas_utiles.jav       # Snippets, trucos y sintaxis clave de AED
└── 📄 preparacion.jav          # Ejercicios integradores de repaso
```

---

## 💻 Sintaxis y Convenciones de Pseudocódigo AED

Todas las resoluciones siguen el pseudocódigo formal de la cátedra de AED de la UTN FRRE:

* **Estructura General**: `Accion Nombre Es ... Ambiente ... Proceso ... FinAccion`.
* **Subprogramas**: Definidos dentro del `Ambiente` (`Procedimiento` / `FP`, `Funcion` / `FinFuncion`).
* **Delimitadores de Bloque**:
  * `Si ... Sino ... FS` (o `FinSi`)
  * `Mientras ... FM` (o `FinMientras`)
  * `Para ... FP` (o `FinPara`)
  * `Segun ... FinSegun`
* **Centinelas de Fin de Archivo (FDA)**:
  * `HV` (*High Value*): Asignado a las claves para indicar el fin de procesamiento.
  * `LW` (*Low Value*): Usado para inicialización de máximos.
* **Archivos**:
  * Secuenciales: `archivo de REGISTRO ordenado por clave`
  * Indexados: `archivo de REGISTRO Indexado por clave`

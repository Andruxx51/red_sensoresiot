# Guía paso a paso — Semana 2

## ¿Dónde viven los datos?

### Proyecto integrador: Red de Sensores IoT

**Espacio académico:** Estructuras de Datos  
**Programa:** Ingeniería de Datos e Inteligencia Artificial — II semestre

### Contenidos

- 2.1 Concepto de estructura de datos y TAD.
- 2.2 Arreglos unidimensionales.
- 2.3 Matrices.
- 2.4 Recorrer, insertar, actualizar, buscar y eliminar.

---

# 1. Dónde quedamos

Durante la Semana 1 construimos la primera capa de nuestro sistema: una **ingesta confiable de datos**.

Aprendimos a:

- Modularizar el código.
- Detectar errores.
- Manejar excepciones.
- Validar entradas.
- Rechazar datos incorrectos.
- Evitar que una lectura defectuosa detenga todo el programa.

Pero existe un problema.

Nuestro programa recibe una lectura:

```text
EST-001,2026-09-07 08:00,18.5,75.2,32.4
```

La procesa y la muestra:

```text
Estación: EST-001
Temperatura: 18.5
```

¿Y después qué?

Cuando el programa termina:

```text
Memoria
   │
   └── Datos perdidos
```

Nadie puede preguntarle posteriormente:

> ¿Cuál fue la temperatura de EST-004 a las 3:00 p. m.?

Porque el programa ya no tiene la información.

Por eso esta semana aparece una nueva necesidad:

> **Los datos ya llegan limpios. ¿Dónde viven ahora y qué podemos preguntarles?**

---

# 2. El nuevo problema: almacenar

Nuestra red ahora genera muchas lecturas.

El archivo utilizado en esta semana contiene:

```text
211 filas
9 estaciones
24 horas
```

Esto representa un cambio importante frente a las primeras pruebas del proyecto.

Ya no tenemos unas pocas lecturas:

```text
Lectura 1
Lectura 2
Lectura 3
...
```

Ahora tenemos:

```text
211 lecturas
```

Necesitamos una estructura que permita:

- Almacenar datos.
- Recorrerlos.
- Acceder a una posición.
- Buscar información.
- Insertar nuevos datos.
- Actualizar datos.
- Eliminar datos.
- Conocer cuántos elementos existen.

Aquí aparecen las **estructuras de datos**.

---

# 3. ¿Qué es una estructura de datos?

Una estructura de datos es una forma organizada de almacenar y manipular información para poder realizar determinadas operaciones sobre ella.

Podemos imaginarlo así:

```text
                 DATOS
                   │
                   ▼
          ┌─────────────────┐
          │ Estructura      │
          │ de datos        │
          └─────────────────┘
                   │
        ┌──────────┼──────────┐
        ▼          ▼          ▼
      Buscar    Insertar   Eliminar
```

La elección de la estructura no es un detalle menor.

Una estructura puede ser muy eficiente para una operación y poco eficiente para otra.

Por ejemplo:

```text
Arreglo
   │
   ├── Excelente → acceso por posición
   │
   ├── Bueno → recorrer
   │
   ├── Costoso → insertar en medio
   │
   ├── Costoso → eliminar en medio
   │
   └── Limitado → crecer
```

Por eso no basta con preguntar:

> ¿Qué estructura puedo utilizar?

La pregunta correcta es:

> **¿Qué operaciones necesita realizar nuestro sistema y con qué frecuencia?**

---

# 4. El concepto central: TAD

Uno de los conceptos fundamentales de esta semana es:

> **Tipo Abstracto de Dato — TAD**

La definición puede parecer complicada inicialmente, así que utilizaremos una analogía.

---

## 4.1 La máquina expendedora

Imagina una máquina expendedora.

Tú sabes que puedes:

```text
Introducir dinero
      ↓
Seleccionar producto
      ↓
Recibir producto
```

Pero no sabes cómo funciona internamente.

No sabes si tiene:

- Un resorte.
- Un brazo mecánico.
- Un motor.
- Un mecanismo hidráulico.
- Un pequeño robot.

Y realmente no importa.

Lo importante es que la máquina cumple lo que promete.

---

## 4.2 Contrato e implementación

Podemos separar entonces dos conceptos:

```text
             MÁQUINA
                │
       ┌────────┴────────┐
       │                 │
       ▼                 ▼
   CONTRATO         IMPLEMENTACIÓN
       │                 │
       │                 │
       ▼                 ▼
Lo que promete       Cómo funciona
```

En un TAD ocurre exactamente lo mismo.

El **contrato** define qué operaciones ofrece la estructura.

La **implementación** determina cómo se almacenan internamente los datos.

---

# 5. TAD aplicado a nuestra Red de Sensores

Nuestro sistema puede definir un repositorio de lecturas.

El contrato podría ofrecer:

```java
agregar(lectura);
obtener(posicion);
buscarPorEstacion(id);
actualizar(posicion, lectura);
eliminar(posicion);
tamano();
```

Pero el contrato no necesita decir:

```text
"Internamente utilizo un arreglo"
```

Podríamos tener:

```text
Repositorio
     │
     ├── RepositorioLecturas
     │       └── arreglo
     │
     ├── RepositorioEnlazado
     │       └── lista enlazada
     │
     └── RepositorioHash
             └── tabla hash
```

El resto del programa debería preocuparse por **qué puede hacer**, no por **cómo está implementado**.

---

# 6. ¿Por qué importa el TAD?

Esta decisión será fundamental para el resto del semestre.

Hoy podemos tener:

```text
Repositorio
      │
      ▼
Arreglo
```

Más adelante podremos cambiarlo:

```text
Repositorio
      │
      ▼
Lista enlazada
```

Y posteriormente:

```text
Repositorio
      │
      ▼
Tabla hash
```

Si el contrato está bien diseñado:

```text
              REPOSITORIO
                   │
        ┌──────────┴──────────┐
        ▼                     ▼
 Implementación A       Implementación B
    Arreglo              Lista enlazada
        │                     │
        └──────────┬──────────┘
                   ▼
          Mismo contrato
```

El resto del sistema puede continuar funcionando.

> **El contrato es lo que prometemos. La implementación es cómo cumplimos esa promesa.**

---

# 7. Encapsulamiento: proteger la implementación

Supongamos que creamos:

```java
public class RepositorioLecturas {

    public LecturaSensor[] lecturas;
    public int cantidad;
}
```

Esto parece sencillo.

Pero tenemos un problema.

Cualquier parte del programa podría hacer:

```java
repositorio.lecturas = null;
```

O:

```java
repositorio.cantidad = 999999;
```

Ahora el repositorio puede quedar en un estado inconsistente.

Por eso debemos utilizar encapsulamiento:

```java
public class RepositorioLecturas {

    private LecturaSensor[] lecturas;
    private int cantidad;

}
```

Ahora el estado interno está protegido.

El usuario del repositorio deberá utilizar las operaciones definidas por el contrato.

---

# 8. Primera estructura: arreglo unidimensional

La primera estructura que estudiaremos es el **arreglo**.

Un arreglo permite almacenar varios elementos del mismo tipo.

Por ejemplo:

```java
int[] temperaturas = new int[5];
```

Podemos representarlo:

```text
Índice:

   0      1      2      3      4
┌──────┬──────┬──────┬──────┬──────┐
│  18  │  21  │  19  │  23  │  20  │
└──────┴──────┴──────┴──────┴──────┘
```

Cada posición tiene un índice.

En Java, los índices comienzan en:

```text
0
```

Por eso, un arreglo de tamaño 5 tiene índices:

```text
0
1
2
3
4
```

---

# 9. Acceso por posición

Podemos acceder directamente a una posición:

```java
System.out.println(temperaturas[2]);
```

Resultado:

```text
19
```

También podemos modificarla:

```java
temperaturas[2] = 25;
```

Ahora:

```text
   0      1      2      3      4
┌──────┬──────┬──────┬──────┬──────┐
│  18  │  21  │  25  │  23  │  20  │
└──────┴──────┴──────┴──────┴──────┘
```

Esta es una de las principales ventajas de un arreglo:

> **Podemos acceder directamente a una posición.**

---

# 10. Arreglo de objetos

Nuestro sistema no almacenará solamente números.

Queremos almacenar objetos `LecturaSensor`.

Podemos crear:

```java
LecturaSensor[] lecturas =
        new LecturaSensor[10];
```

Visualmente:

```text
Índice

   0       1       2       3       4
┌───────┬───────┬───────┬───────┬───────┐
│ null  │ null  │ null  │ null  │ null  │
└───────┴───────┴───────┴───────┴───────┘
```

Inicialmente las posiciones contienen:

```java
null
```

Podemos almacenar una lectura:

```java
LecturaSensor lectura =
        new LecturaSensor(
                "EST-001",
                "2026-09-07 08:00",
                18.5,
                75.2,
                32.4
        );

lecturas[0] = lectura;
```

Ahora:

```text
   0          1       2       3
┌──────────┬───────┬───────┬───────┐
│Lectura  │ null  │ null  │ null  │
│ EST-001 │       │       │       │
└──────────┴───────┴───────┴───────┘
```

---

# 11. Tamaño vs. cantidad de elementos

Este concepto es fundamental.

Tenemos:

```java
LecturaSensor[] lecturas =
        new LecturaSensor[10];
```

El arreglo tiene:

```text
capacidad = 10
```

Pero podemos tener:

```text
cantidad = 3
```

Por ejemplo:

```text
Capacidad: 10

   0       1       2       3       4       5 ...
┌───────┬───────┬───────┬───────┬───────┬───────┐
│ dato  │ dato  │ dato  │ null  │ null  │ null  │
└───────┴───────┴───────┴───────┴───────┴───────┘
    ▲       ▲       ▲
    └───────┴───────┘
       3 elementos
```

Por eso debemos diferenciar:

```text
length  → capacidad física del arreglo

cantidad → número real de elementos almacenados
```

No debemos confundirlos.

---

# 12. Crear nuestro primer repositorio

Crearemos:

```java
public class RepositorioLecturas {

    private LecturaSensor[] lecturas;
    private int cantidad;

}
```

El constructor:

```java
public RepositorioLecturas(int capacidadInicial) {

    lecturas =
            new LecturaSensor[capacidadInicial];

    cantidad = 0;
}
```

Podemos crear:

```java
RepositorioLecturas repositorio =
        new RepositorioLecturas(10);
```

---

# 13. Operación `agregar()`

Necesitamos incorporar nuevas lecturas.

Una primera versión podría ser:

```java
public boolean agregar(
        LecturaSensor lectura) {

    if (cantidad >= lecturas.length) {
        return false;
    }

    lecturas[cantidad] = lectura;
    cantidad++;

    return true;
}
```

La lógica es:

```text
¿Hay espacio?
     │
   ┌─┴─┐
  Sí   No
  │     │
  ▼     ▼
Guardar  false
  │
  ▼
cantidad++
  │
  ▼
true
```

---

# 14. El problema del techo

Supongamos:

```java
new RepositorioLecturas(10);
```

Y el archivo contiene:

```text
201 lecturas válidas
```

Después de almacenar las primeras 10:

```text
0  1  2  3  4  5  6  7  8  9
●  ●  ●  ●  ●  ●  ●  ●  ●  ●
```

El arreglo está lleno.

La siguiente lectura intenta entrar:

```text
Lectura 11
    │
    ▼
¿Hay espacio?
    │
    ▼
   NO
```

Si simplemente hacemos:

```java
return false;
```

el programa puede continuar sin almacenar la lectura.

Esto puede producir un problema silencioso.

---

# 15. ¿Dónde están las otras 191 lecturas?

El archivo tiene:

```text
211 filas
```

Se descartan:

```text
10 registros
```

por problemas de formato o rango.

Entonces deberían quedar:

```text
211 - 10 = 201
```

lecturas válidas.

Pero el programa almacena:

```text
10
```

¿Por qué?

Porque el arreglo tiene capacidad fija.

El programa puede mostrar:

```text
Lecturas almacenadas: 10
```

sin generar una excepción.

Esto es particularmente peligroso porque:

> **El número es correcto respecto al arreglo, pero incorrecto respecto al problema que estamos resolviendo.**

---

# 16. Redimensionamiento de arreglos

Un arreglo de Java no puede crecer directamente.

Si tenemos:

```java
LecturaSensor[] lecturas =
        new LecturaSensor[10];
```

no podemos convertirlo mágicamente en:

```text
new LecturaSensor[20]
```

Debemos crear otro arreglo.

El proceso es:

```text
Arreglo viejo
     │
     ▼
Crear arreglo nuevo
     │
     ▼
Copiar elementos
     │
     ▼
Reemplazar referencia
     │
     ▼
Continuar utilizando el nuevo arreglo
```

---

# 17. Implementar `redimensionar()`

Podemos comenzar con:

```java
private void redimensionar() {

    LecturaSensor[] nuevo =
            new LecturaSensor[lecturas.length * 2];

    for (int i = 0; i < cantidad; i++) {

        nuevo[i] = lecturas[i];
    }

    lecturas = nuevo;
}
```

Ahora `agregar()` puede utilizarlo:

```java
public boolean agregar(
        LecturaSensor lectura) {

    if (cantidad >= lecturas.length) {
        redimensionar();
    }

    lecturas[cantidad] = lectura;
    cantidad++;

    return true;
}
```

---

# 18. ¿Por qué duplicar?

Podemos preguntarnos:

> ¿Por qué multiplicar por 2?

Podríamos hacer:

```text
10 → 11 → 12 → 13 → 14 ...
```

Pero cada crecimiento obliga a copiar los elementos existentes.

Otra estrategia sería:

```text
10 → 20 → 40 → 80 → 160 → 320
```

Aquí hacemos menos redimensionamientos.

Por eso esta semana realizaremos un experimento.

---

# 19. Experimento: medir las copias

No debemos decidir únicamente por intuición.

Vamos a medir.

Crearemos un contador:

```java
private int copiasRealizadas;
```

Durante la copia:

```java
copiasRealizadas++;

nuevo[i] = lecturas[i];
```

También podemos contar las redimensiones:

```java
private int redimensionamientos;
```

Y:

```java
redimensionamientos++;
```

Al final podremos mostrar:

```text
Copias realizadas: ...
Redimensionamientos: ...
```

---

# 20. Comparar dos estrategias

Para 211 elementos y una capacidad inicial de 10:

### Estrategia A — Crecer de uno en uno

```text
10 → 11 → 12 → 13 → ...
```

Cada crecimiento requiere copiar nuevamente los elementos existentes.

### Estrategia B — Duplicar

```text
10 → 20 → 40 → 80 → 160 → 320
```

Hay menos redimensionamientos.

La actividad no busca que simplemente memorices cuál estrategia es mejor.

Busca que puedas responder:

> **¿Qué evidencia tenemos para afirmar que una estrategia es más eficiente que otra?**

---

# 21. Operación `obtener()`

Ahora necesitamos recuperar una lectura por posición.

```java
public LecturaSensor obtener(int posicion) {

    if (posicion < 0 ||
            posicion >= cantidad) {

        return null;
    }

    return lecturas[posicion];
}
```

Podemos utilizar:

```java
LecturaSensor lectura =
        repositorio.obtener(5);
```

La operación debe considerar posiciones inválidas.

Por ejemplo:

```java
repositorio.obtener(-1);
```

o:

```java
repositorio.obtener(500);
```

No son posiciones válidas.

---

# 22. Operación `tamano()`

Necesitamos saber cuántos elementos tiene el repositorio.

```java
public int tamano() {
    return cantidad;
}
```

No debemos hacer:

```java
return lecturas.length;
```

porque:

```text
length = capacidad
```

mientras:

```text
cantidad = elementos reales
```

Ejemplo:

```text
Capacidad = 320
Cantidad  = 201
```

Entonces:

```java
tamano()
```

debe devolver:

```text
201
```

no:

```text
320
```

---

# 23. Operación `buscarPorEstacion()`

Ahora aparece una operación muy útil para nuestro sistema.

Queremos preguntar:

> ¿Existe una lectura de la estación `EST-004`?

Podemos recorrer el arreglo:

```java
public LecturaSensor buscarPorEstacion(
        String idEstacion) {

    for (int i = 0; i < cantidad; i++) {

        if (lecturas[i]
                .getIdEstacion()
                .equals(idEstacion)) {

            return lecturas[i];
        }
    }

    return null;
}
```

Uso:

```java
LecturaSensor lectura =
        repositorio.buscarPorEstacion("EST-004");
```

Si existe:

```text
Lectura encontrada
```

Si no existe:

```text
null
```

---

# 24. ¿Cuántas comparaciones hacemos?

Aquí aparece una pregunta importante:

> ¿Cuánto trabajo debe realizar el programa para encontrar una lectura?

Si tenemos:

```text
10 lecturas
```

podemos recorrer como máximo:

```text
10
```

posiciones.

Pero si tenemos:

```text
8.000 estaciones
```

podríamos necesitar:

```text
8.000 comparaciones
```

en el peor caso.

Por ahora utilizaremos el recorrido secuencial.

En la siguiente semana analizaremos cómo mejorar esta búsqueda.

---

# 25. Operación `actualizar()`

También necesitamos modificar una lectura.

Por ejemplo:

```java
public boolean actualizar(
        int posicion,
        LecturaSensor nueva) {

    if (posicion < 0 ||
            posicion >= cantidad) {

        return false;
    }

    lecturas[posicion] = nueva;

    return true;
}
```

Aquí aparece nuevamente la validación.

No debemos permitir:

```java
actualizar(-1, lectura);
```

ni:

```java
actualizar(500, lectura);
```

---

# 26. Operación `eliminar()`

Ahora queremos eliminar una lectura.

Una primera solución podría parecer sencilla:

```java
public void eliminar(int posicion) {

    lecturas[posicion] = null;
}
```

Pero esta implementación tiene un problema.

Supongamos:

```text
   0       1       2       3       4
┌───────┬───────┬───────┬───────┬───────┐
│  A    │  B    │  C    │  D    │  E    │
└───────┴───────┴───────┴───────┴───────┘
```

Eliminamos la posición 2:

```text
   0       1       2       3       4
┌───────┬───────┬───────┬───────┬───────┐
│  A    │  B    │ null  │  D    │  E    │
└───────┴───────┴───────┴───────┴───────┘
```

Ahora tenemos un hueco.

Pero:

```java
cantidad
```

podría seguir indicando:

```text
5
```

aunque realmente tenemos:

```text
4 lecturas
```

El repositorio empieza a mentir sobre sí mismo.

---

# 27. Primera estrategia: compactar

Podemos mover los elementos posteriores hacia la izquierda.

Antes:

```text
A B C D E
```

Eliminamos `C`:

```text
A B _ D E
```

Compactamos:

```text
A B D E _
```

Y reducimos:

```java
cantidad--;
```

Una implementación podría ser:

```java
public boolean eliminar(int posicion) {

    if (posicion < 0 ||
            posicion >= cantidad) {

        return false;
    }

    for (int i = posicion;
         i < cantidad - 1;
         i++) {

        lecturas[i] = lecturas[i + 1];
    }

    lecturas[cantidad - 1] = null;

    cantidad--;

    return true;
}
```

Observa cuidadosamente esta línea:

```java
lecturas[cantidad - 1] = null;
```

Es importante.

Si no anulamos la última posición, podríamos conservar una referencia duplicada.

---

# 28. Segunda estrategia: marcar el hueco

Existe otra posibilidad.

En lugar de mover elementos:

```text
A B C D E
    ↓
A B _ D E
```

podemos dejar el hueco.

Pero entonces necesitamos saber qué posiciones están libres.

Esto podría requerir:

```text
Arreglo de lecturas
+
estructura que indique posiciones ocupadas
```

Por ejemplo:

```java
boolean[] ocupada;
```

Visualmente:

```text
Lecturas:

A B _ D E

Ocupada:

T T F T T
```

La ventaja es que eliminar puede ser más rápido.

El costo es que ahora todos los recorridos deben saber cómo manejar los huecos.

---

# 29. No existe una única respuesta correcta

Aquí aparece una idea importante de ingeniería.

Dos soluciones pueden ser válidas:

```text
Compactar
```

o:

```text
Marcar
```

La pregunta profesional no es:

> ¿Cuál me gusta más?

La pregunta es:

> **¿Cuál se ajusta mejor a las operaciones que realizará nuestro sistema?**

Por eso deberás elegir una estrategia y justificarla.

---

# 30. Matrices

Hasta ahora utilizamos un arreglo de una dimensión:

```text
[lectura]
[lectura]
[lectura]
```

Pero nuestra red tiene dos dimensiones naturales:

```text
Estación
   +
Hora
```

Por ejemplo:

```text
             HORA
          00 01 02 03 04 ...
       ┌────────────────────
EST-001│ 12 15 17 18 20 ...
EST-002│ 11 14 16 17 19 ...
EST-003│ 13 16  -  -  -  ...
EST-004│ 10 12 15 18 19 ...
  ...
```

Esta estructura puede representarse mediante una matriz.

---

# 31. Crear una matriz en Java

Una matriz puede declararse:

```java
double[][] matriz =
        new double[9][24];
```

Tenemos:

```text
9 estaciones
24 horas
```

Podemos acceder así:

```java
matriz[estacion][hora]
```

Por ejemplo:

```java
matriz[3][15] = 21.5;
```

---

# 32. El problema del cero fantasma

Java inicializa automáticamente un `double[][]` con:

```text
0.0
```

Por ejemplo:

```java
double[][] pm25 =
        new double[3][4];
```

inicialmente contiene:

```text
       00     01     02     03
    ┌──────┬──────┬──────┬──────┐
E1  │ 0.0  │ 0.0  │ 0.0  │ 0.0  │
E2  │ 0.0  │ 0.0  │ 0.0  │ 0.0  │
E3  │ 0.0  │ 0.0  │ 0.0  │ 0.0  │
    └──────┴──────┴──────┴──────┘
```

Pero existe un problema.

Supongamos que una estación realmente mide:

```text
0.0 PM2.5
```

Y otra estación:

```text
No reportó
```

En la matriz ambas podrían aparecer como:

```text
0.0
```

La estructura no permite diferenciarlas.

---

# 33. Ausencia no significa cero

Esta es una de las ideas más importantes de la semana.

Debemos diferenciar:

```text
0.0
```

de:

```text
sin dato
```

Por ejemplo:

```text
PM2.5 = 0.0
```

significa:

> El sensor reportó un valor de cero.

Mientras:

```text
PM2.5 = ausencia
```

significa:

> El sensor no reportó.

Son situaciones completamente diferentes.

---

# 34. El caso EST-003

En nuestro dataset:

```text
EST-003
```

no reporta entre las:

```text
09:00
10:00
11:00
12:00
```

Estas filas **no existen**.

No debemos interpretarlas como:

```text
PM2.5 = 0
```

El problema es que una matriz `double[][]` las puede representar inicialmente como:

```text
0.0
```

Esto produce un resultado incorrecto.

---

# 35. El promedio de una hora

Supongamos que tenemos:

```text
9 estaciones
```

Pero a las 09:00 solo reportaron:

```text
8 estaciones
```

El cálculo incorrecto sería:

```java
promedio = suma / 9;
```

Pero debemos hacer:

```java
promedio = suma / estacionesQueReportaron;
```

Por ejemplo:

```text
Hora 09

8 estaciones reportaron
1 estación no reportó
```

Entonces:

```text
Incorrecto: suma / 9
Correcto:   suma / 8
```

---

# 36. El impacto del error

Los resultados esperados muestran:

| Hora | Dividiendo entre 9 | Correcto | Estaciones que reportaron |
|---|---:|---:|---:|
| 08 | 30.99 | 30.99 | 9 |
| 09 | 9.93 | **11.18** | 8 |
| 10 | 10.80 | **12.15** | 8 |
| 11 | 10.08 | **11.34** | 8 |
| 12 | 9.20 | **11.83** | 7 |
| 13 | 12.53 | 12.53 | 9 |

Observa que las horas 08 y 13 coinciden.

¿Por qué?

Porque en esas horas:

```text
9 estaciones reportaron
```

El error solamente aparece cuando existen estaciones sin datos.

---

# 37. El caso más importante: EST-003

Aquí aparece uno de los aprendizajes centrales de la semana.

Si calculamos el promedio de EST-003 sobre las 24 horas:

```text
14.14
```

La estación parece estar aproximadamente en el promedio de la ciudad.

Pero si calculamos el promedio solamente sobre las horas en las que realmente reportó:

```text
17.86
```

Ahora descubrimos algo diferente:

> **EST-003 es la estación más contaminada de las nueve.**

¿Por qué?

Porque las horas en las que no hubo medición fueron interpretadas como:

```text
0
```

Eso hizo disminuir artificialmente su promedio.

---

# 38. ¿Por qué esto importa?

Imagina que el sistema genera un informe para la administración de la ciudad.

El informe dice:

```text
EST-003
PM2.5 promedio: 14.14
```

La administración podría concluir:

```text
La estación presenta condiciones normales.
```

Pero el dato real indica:

```text
PM2.5 promedio: 17.86
```

La estación podría requerir mayor atención.

Este ejemplo muestra que:

> **Una estructura de datos mal diseñada puede generar decisiones incorrectas incluso cuando el algoritmo matemático está bien escrito.**

---

# 39. Tres formas de representar ausencia

Tenemos diferentes alternativas.

## Alternativa 1 — Matriz paralela de `boolean`

Podemos tener:

```java
double[][] pm25;
boolean[][] tieneDato;
```

Por ejemplo:

```text
PM2.5:

12.5  13.4  0.0  15.2

TieneDato:

true  true  true  false
```

Aquí podemos diferenciar:

```text
0.0 + true
```

de:

```text
0.0 + false
```

---

## Alternativa 2 — Utilizar un valor centinela

Por ejemplo:

```text
-1
```

podría significar:

```text
sin dato
```

Pero esta estrategia tiene un problema.

¿Qué ocurre si algún día:

```text
-1
```

es un valor legítimo?

Volvemos a tener el mismo problema.

---

## Alternativa 3 — Utilizar `Double`

Podemos utilizar:

```java
Double[][] pm25;
```

Ahora una celda puede contener:

```java
25.3
```

o:

```java
null
```

Por ejemplo:

```text
25.3   31.4   null   28.7
```

Aquí:

```text
null = no existe dato
```

mientras:

```text
0.0 = existe dato y su valor es cero
```

---

# 40. `double` vs. `Double`

Esta diferencia es importante.

### `double`

Es un tipo primitivo:

```java
double temperatura = 18.5;
```

Siempre contiene un valor numérico.

### `Double`

Es un objeto:

```java
Double temperatura = 18.5;
```

Puede contener:

```java
null
```

Por ejemplo:

```java
Double temperatura = null;
```

Esto puede representar:

```text
No existe una medición.
```

Pero debemos tener cuidado.

Esto:

```java
Double temperatura = null;

System.out.println(
        temperatura + 1
);
```

puede producir:

```text
NullPointerException
```

Por eso, si utilizamos `Double`, debemos validar la presencia del valor.

---

# 41. Implementar `promedioDeHora()`

Una solución conceptual sería:

```java
public double promedioDeHora(int hora) {

    double suma = 0;
    int cantidadReportes = 0;

    for (int estacion = 0;
         estacion < numeroEstaciones;
         estacion++) {

        if (hayDato(estacion, hora)) {

            suma += pm25[estacion][hora];
            cantidadReportes++;
        }
    }

    if (cantidadReportes == 0) {
        return 0;
    }

    return suma / cantidadReportes;
}
```

Lo importante no es solamente sumar.

Debemos contar:

```text
¿Cuántas estaciones realmente reportaron?
```

---

# 42. Implementar `promedioDeEstacion()`

También debemos poder preguntar:

> ¿Cuál es el promedio de PM2.5 de una estación?

Conceptualmente:

```java
public double promedioDeEstacion(
        int estacion) {

    double suma = 0;
    int cantidadReportes = 0;

    for (int hora = 0;
         hora < 24;
         hora++) {

        if (hayDato(estacion, hora)) {

            suma += pm25[estacion][hora];
            cantidadReportes++;
        }
    }

    if (cantidadReportes == 0) {
        return 0;
    }

    return suma / cantidadReportes;
}
```

La regla fundamental es:

```text
Promedio = suma de valores existentes
           ────────────────────────────
           cantidad de valores existentes
```

No:

```text
Promedio = suma
           ─────
              24
```

cuando no existen 24 mediciones.

---

# 43. Encontrar la hora más contaminada

Otra operación que necesita nuestro sistema es:

```text
¿Cuál fue la hora con mayor contaminación?
```

Conceptualmente:

```java
public int horaMasContaminada() {

    int mejorHora = -1;
    double mayorPromedio = -1;

    for (int hora = 0;
         hora < 24;
         hora++) {

        double promedio =
                promedioDeHora(hora);

        if (promedio > mayorPromedio) {

            mayorPromedio = promedio;
            mejorHora = hora;
        }
    }

    return mejorHora;
}
```

Observa cómo reutilizamos:

```java
promedioDeHora()
```

Esto es una consecuencia directa de la modularidad estudiada en la Semana 1.

---

# 44. Integración con la ingesta

Ahora podemos visualizar cómo evoluciona nuestro sistema.

## Semana 1

```text
Archivo
   │
   ▼
Ingesta
   │
   ▼
Validación
   │
   ▼
Lectura confiable
```

## Semana 2

Agregamos:

```text
Lectura confiable
       │
       ▼
Repositorio
       │
       ▼
Arreglo
```

Y:

```text
Lecturas confiables
       │
       ▼
Matriz estación × hora
```

Nuestro sistema comienza a tener memoria.

---

# 45. Arquitectura conceptual de la semana

```text
                RED DE SENSORES IoT
                         │
                         ▼
                    DATOS CRUDOS
                         │
                         ▼
                  INGESTA SENSORES
                         │
                         ▼
                    VALIDACIÓN
                         │
                         ▼
                 LECTURAS VÁLIDAS
                         │
             ┌───────────┴───────────┐
             ▼                       ▼
      RepositorioLecturas      AnalizadorMatriz
             │                       │
             ▼                       ▼
          Arreglo             Estación × Hora
             │                       │
       ┌─────┼─────┐                 │
       ▼     ▼     ▼                 ▼
    Buscar  Act.  Eliminar       Promedios
       │     │     │                 │
       └─────┴─────┘                 │
             │                       │
             └───────────┬───────────┘
                         ▼
                  INFORMACIÓN ÚTIL
```

---

# 46. Organización del proyecto

Los archivos de trabajo de esta semana serán:

```text
proyecto/
│
├── LecturaSensor.java
│
├── IngestaSensores.java
│
├── RepositorioLecturas.java
│
├── AnalizadorMatriz.java
│
├── lecturas_ampliadas.csv
│
└── README.md
```

### `LecturaSensor.java`

Representa los datos de una lectura.

Esta clase ya fue construida durante la Semana 1.

> **No es necesario reescribirla. Estúdiala y reutilízala.**

### `IngestaSensores.java`

Se encarga de leer, validar y alimentar las estructuras.

### `RepositorioLecturas.java`

Representa nuestro TAD de almacenamiento.

En esta semana deberás completarlo.

### `AnalizadorMatriz.java`

Representa la matriz:

```text
estación × hora
```

y permite realizar análisis sobre ella.

---

# 47. Actividad práctica de la semana

La actividad estará dividida en cuatro fases.

---

## FASE 0 — El contrato antes del código

Antes de abrir el IDE, trabaja en equipo.

Define las operaciones que el repositorio necesita ofrecer.

Por ejemplo:

```text
agregar()
obtener()
buscarPorEstacion()
actualizar()
eliminar()
tamano()
```

Para cada operación responde:

> ¿Qué ocurre si la operación recibe información inválida?

Ejemplos:

```text
posición negativa
posición inexistente
repositorio vacío
estación que no existe
```

También debes identificar cuáles operaciones son parte del contrato y cuáles son detalles internos.

---

# 48. FASE 1 — Diagnóstico silencioso

Ejecuta el proyecto **sin modificarlo inicialmente**.

Obtendrás algo similar a:

```text
=== INGESTA ===
Lecturas almacenadas:      10
Descartadas por formato:    2
Descartadas por rango:      8
```

El archivo contiene:

```text
211 filas
```

Realiza la cuenta:

```text
211 - 2 - 8 = 201
```

Entonces:

> ¿Dónde están las otras 191 lecturas?

No arregles todavía el código.

Primero encuentra la causa.

---

# 49. FASE 2 — Romper el techo

Implementa:

```java
redimensionar()
```

La operación deberá:

1. Crear un arreglo más grande.
2. Copiar los elementos existentes.
3. Reemplazar el arreglo anterior.
4. Continuar agregando elementos.

Después modifica:

```java
agregar()
```

para que invoque `redimensionar()` cuando sea necesario.

Finalmente verifica:

```text
Lecturas almacenadas: 201
```

---

# 50. FASE 2.1 — Medir

No te limites a hacer que funcione.

Instrumenta el programa para registrar:

```text
Número de copias
Número de redimensionamientos
```

Compara:

```text
Crecer de uno en uno
```

contra:

```text
Duplicar capacidad
```

Registra los resultados.

> **No estimes. Mide.**

La información obtenida será utilizada posteriormente para estudiar formalmente la eficiencia de los algoritmos.

---

# 51. FASE 3 — Completar el contrato

Implementa:

```java
buscarPorEstacion()
```

Debe:

- Recorrer las lecturas.
- Comparar el identificador.
- Retornar la primera coincidencia.
- Retornar `null` si no existe.

Después implementa:

```java
actualizar()
```

Validando previamente la posición.

Finalmente corrige:

```java
eliminar()
```

y decide si utilizarás:

```text
Compactación
```

o:

```text
Marcado de posiciones
```

Debes documentar y justificar la decisión.

---

# 52. FASE 4 — La matriz y el cero mentiroso

Implementa y analiza la matriz:

```text
Estación × Hora
```

Observa específicamente:

```text
EST-003
09:00
10:00
11:00
12:00
```

Pregúntate:

> ¿Ese `0.0` significa que la calidad del aire fue perfecta o que no hubo dato?

Después:

1. Implementa una estrategia para representar ausencia.
2. Corrige `promedioDeHora()`.
3. Implementa `promedioDeEstacion()`.
4. Implementa `horaMasContaminada()`.
5. Comprueba los resultados.

---

# 53. Pruebas que debes realizar

Tu solución debe probar al menos los siguientes escenarios.

## Caso 1 — Agregar

Agregar una lectura válida:

```java
repositorio.agregar(lectura);
```

Verificar:

```java
repositorio.tamano();
```

---

## Caso 2 — Capacidad

Agregar suficientes lecturas para superar la capacidad inicial.

Verificar que:

```text
El arreglo crece
```

y que:

```text
No se pierden lecturas
```

---

## Caso 3 — Buscar

Buscar:

```text
EST-004
```

Verificar que devuelve una lectura.

Buscar una estación inexistente:

```text
EST-999
```

Verificar que devuelve:

```java
null
```

---

## Caso 4 — Actualizar

Modificar una posición válida.

Verificar que la lectura cambió.

Después probar una posición inválida:

```text
-1
```

o:

```text
999
```

---

## Caso 5 — Eliminar

Eliminar una posición válida.

Verificar:

```text
cantidad
```

y:

```text
promedio
```

Después verificar que no quede una referencia duplicada.

---

## Caso 6 — Matriz

Comprobar específicamente las horas en las que alguna estación no reportó.

Verificar que:

```text
ausencia ≠ 0.0
```

---

# 54. Preguntas para la bitácora

Responde individualmente:

### 1.

Explica qué es un TAD **sin utilizar las palabras**:

```text
abstracto
interfaz
implementación
```

Utiliza máximo cinco líneas.

---

### 2.

¿Cuántas copias realizó tu repositorio al cargar las 211 filas utilizando crecimiento de uno en uno?

¿Cuántas realizó utilizando duplicación?

¿Qué concluyes?

---

### 3.

¿Qué estrategia elegiste para `eliminar()`?

```text
Compactar
```

o:

```text
Marcar
```

Justifica tu decisión.

---

### 4.

¿Cómo resolviste el problema del cero fantasma?

Explica por qué descartaste las otras alternativas.

---

### 5.

Tu método:

```java
buscarPorEstacion()
```

recorre el arreglo completo en el peor caso.

Si mañana la ciudad tuviera:

```text
8.000 estaciones
```

¿seguiría siendo una solución adecuada?

> **Responde con un número, no únicamente con una opinión.**

---

# 55. Errores frecuentes

## Error 1 — Aumentar arbitrariamente la capacidad

Algunos estudiantes pueden hacer:

```java
new LecturaSensor[500];
```

para resolver el problema.

Esto resuelve el síntoma, no el problema.

La pregunta que debes hacerte es:

> ¿Qué ocurre mañana cuando tengamos 8.000 estaciones?

Una solución que requiere cambiar el código cada vez que aumenta la cantidad de datos no es escalable.

---

# 56. Error 2 — Utilizar `if (valor != 0)`

Puede parecer una buena solución:

```java
if (valor != 0) {
    suma += valor;
}
```

Pero piensa:

> ¿Qué ocurre si una estación realmente mide `0.0`?

Acabas de eliminar un dato válido.

El problema real no es:

```text
¿Cómo ignoro los ceros?
```

El problema es:

```text
¿Cómo represento correctamente la ausencia de datos?
```

---

# 57. Error 3 — Hacer públicos los atributos

Evita:

```java
public LecturaSensor[] lecturas;
```

y:

```java
public int cantidad;
```

Esto rompe el encapsulamiento.

Prefiere:

```java
private LecturaSensor[] lecturas;
private int cantidad;
```

y proporciona métodos para interactuar con el repositorio.

---

# 58. Error 4 — Confundir `length` con `cantidad`

Recuerda:

```java
lecturas.length
```

representa:

```text
Capacidad del arreglo
```

Mientras:

```java
cantidad
```

representa:

```text
Número real de elementos almacenados
```

Ejemplo:

```text
length  = 320
cantidad = 201
```

---

# 59. Error 5 — Eliminar sin compactar correctamente

Si utilizas compactación, debes recordar:

```java
lecturas[cantidad - 1] = null;
cantidad--;
```

De lo contrario puede quedar una referencia duplicada al final del arreglo.

---

# 60. Error 6 — Dividir siempre entre 9 o 24

Evita:

```java
promedio = suma / 9;
```

si no sabes cuántas estaciones reportaron.

Y evita:

```java
promedio = suma / 24;
```

si una estación no reportó durante todo el día.

Siempre pregunta:

```text
¿Cuántos datos existen realmente?
```

---

# 61. Principios de diseño que debes conservar

Al finalizar esta semana debes haber interiorizado estas tres ideas:

### Principio 1

> **El contrato es lo que se promete; el arreglo es un detalle reemplazable.**

### Principio 2

> **Un arreglo tiene un límite, y superar ese límite tiene un costo.**

### Principio 3

> **La ausencia de un dato no es equivalente a un valor cero.**

Estos principios aparecerán nuevamente durante el resto del semestre.

---

# 62. Conexión con las próximas semanas

Esta semana estamos preparando el terreno para las siguientes estructuras.

| Lo que construyes ahora | Lo que ocurrirá después |
|---|---|
| Búsqueda secuencial | Semana 3 — búsqueda eficiente |
| Conteo de comparaciones | Semana 3 — búsqueda lineal vs. binaria |
| Costo de mover elementos | Semana 4 — eficiencia de algoritmos |
| Arreglo con crecimiento | Semana 5 — listas enlazadas |
| Contrato del repositorio | Semana 5 — reemplazar implementación |
| Reto de genéricos | Semana 7 — `ArrayList<T>` |
| Arreglo dinámico | Semana 7 — entender qué ocurre detrás de `ArrayList` |
| Matriz estación × hora | Semana 11 — matriz de adyacencia |
| Registros duplicados | Semana 8 — `HashSet` |

---

# 63. Una conexión importante con Java

Durante esta semana construiremos manualmente un repositorio basado en un arreglo.

Más adelante utilizarás:

```java
ArrayList<LecturaSensor>
```

Es posible que parezca una estructura completamente diferente.

Pero conceptualmente:

```text
Tu Repositorio
       │
       ▼
Arreglo interno
       │
       ▼
Redimensionamiento
       │
       ▼
Copiar elementos
```

es muy cercano a lo que ocurre detrás de estructuras dinámicas como `ArrayList`.

Por eso es importante que no veas esta actividad simplemente como:

> "Construir un arreglo."

La intención real es:

> **Comprender qué problemas resuelven las estructuras de datos que posteriormente utilizarás como herramientas.**

---

# 64. Entregables de la semana

Deberás entregar:

## 1. Hoja del contrato

Documento elaborado durante la Fase 0.

Debe mostrar:

- Operaciones del TAD.
- Parámetros.
- Comportamiento esperado.
- Situaciones inválidas.
- Identificación de operaciones públicas y privadas.

---

## 2. `RepositorioLecturas.java`

Debe estar completo y funcional.

Debe permitir:

```text
Agregar
Obtener
Buscar
Actualizar
Eliminar
Consultar tamaño
Redimensionar
```

---

## 3. `AnalizadorMatriz.java`

Debe:

- Construir la matriz estación × hora.
- Representar correctamente la ausencia de datos.
- Calcular promedios correctamente.
- Identificar la hora más contaminada.

---

## 4. Evidencia de ejecución

Debe demostrar como mínimo:

```text
201 lecturas almacenadas
```

y los resultados corregidos del análisis horario.

---

## 5. Bitácora individual

Debe contener las cinco preguntas planteadas anteriormente.

La bitácora debe explicar las decisiones tomadas, no limitarse a presentar resultados.

---

# 65. Criterios de calidad del código

Antes de entregar, revisa:

### Encapsulamiento

```java
private
```

para los atributos internos.

### Responsabilidad

Cada método debe realizar una tarea clara.

### Validación

Las posiciones deben verificarse antes de acceder al arreglo.

### Reutilización

Evita copiar la misma lógica en varios métodos.

### Nombres

Prefiere:

```java
cantidadReportes
```

en lugar de:

```java
x
```

### Constantes

Evita números mágicos cuando representen reglas del sistema.

### Evidencia

Los resultados deben poder reproducirse ejecutando el programa.

---

# 66. Hito H1 del proyecto integrador

A partir de esta semana, el repositorio y la matriz dejan de ser un ejercicio aislado.

Se convierten en componentes permanentes de nuestra plataforma.

La evolución será:

```text
Semana 1
Ingesta confiable
       │
       ▼
Semana 2
Almacenamiento
       │
       ▼
Semana 3
Búsqueda
       │
       ▼
Semana 4
Ordenamiento y eficiencia
       │
       ▼
Semana 5
Listas enlazadas
       │
       ▼
...
```

Por esta razón:

> **No construyas código pensando que lo vas a desechar al terminar la actividad. Construye código que puedas mantener y mejorar durante el proyecto.**

---

# 67. Reflexión final

Durante la Semana 1 aprendimos a evitar que los datos incorrectos entraran al sistema.

Esta semana aprendemos algo diferente:

> **Los datos confiables necesitan una estructura que permita conservarlos y consultarlos.**

Pero también descubrimos algo más profundo.

Una estructura de datos no es solamente un lugar donde guardar información.

La estructura determina:

- Qué tan rápido podemos buscar.
- Qué tan costoso es insertar.
- Qué tan costoso es eliminar.
- Cuánto cuesta crecer.
- Cómo representamos la ausencia.
- Qué tan fácil será cambiar la implementación.

Por eso:

```text
Elegir una estructura de datos
            =
      tomar una decisión
         de ingeniería
```

---

# 68. La idea que debes llevarte

Nuestro sistema empezó así:

```text
Dato
 │
 ▼
Procesar
 │
 ▼
Imprimir
 │
 ▼
Perder
```

Ahora empieza a evolucionar:

```text
Dato
 │
 ▼
Validar
 │
 ▼
Almacenar
 │
 ▼
Buscar
 │
 ▼
Actualizar
 │
 ▼
Eliminar
 │
 ▼
Analizar
```

Y la pregunta que queda abierta para la siguiente semana es:

> **Si tenemos miles de estaciones, ¿podemos seguir buscando una por una hasta encontrar la que necesitamos?**

Esa pregunta nos llevará directamente al siguiente problema del proyecto:

**¿Cómo hacer que nuestras búsquedas sean más eficientes?**
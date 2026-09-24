# Bitacora individual - Semana [3]

> Copia este archivo y renombralo como `s[XX]-[tu-nombre].md`.
> Completa todas las secciones con tus propias palabras. Esta bitacora es
> individual, aunque el codigo pueda haberse construido en equipo.

## 1. Datos de la actividad

- **Estudiante:** Daniel Andrés Chacón Olaya
- **Equipo:** Grupo 3
- **Semana:** 3
- **Fecha del laboratorio:** 24/09/2026
- **Fecha del taller:** 24/09/2026
- **Tema principal:** Encontrar un dato entre un millón
- **Pregunta de la semana:** ¿Cómo encontramos una lectura específica cuando el repositorio pasa de cientos a cientos de miles o millones de registros y cuánto cuesta hacerlo?

## 2. Prediccion antes de ejecutar

Antes de abrir o ejecutar el programa, responde:

1. **Que creo que va a ocurrir?**
   Al buscar el peor caso en 1.000.000 de registros, la búsqueda lineal realizará exactamente 1.000.000 de comparaciones ($O(n)$), mientras que la búsqueda binaria tardará apenas unas 20 comparaciones ($O(\log_2 n)$) debido a que divide el espacio de búsqueda a la mitad en cada paso.
   Creo que la búsqueda binaria fallará o dará resultados incorrectos al buscar por PM2.5 porque los datos de este sensor se generan de forma aleatoria y no están ordenados.

3. **Que parte del programa o del algoritmo puede fallar?**
   El bloque de la búsqueda binaria por PM2.5 en BuscadorLecturas.java y la condición de los límites (inicio y fin) si no se actualizan con +1 o -1, lo que podría provocar un ciclo infinito.

4. **Como comprobare mi prediccion?**
   Ejecutando el BancoDePruebas con los tamaños de 1.000, 100.000 y 1.000.000 de registros y analizando la consola.

## 3. Evidencia del laboratorio

### Resultado observado

Para $n = 1.000.000$, la búsqueda lineal por timestamp arrojó 1.000.000 comparaciones, mientras que la binaria arrojó solo 20 comparaciones. Al ejecutar el experimento 4 con PM2.5, la búsqueda binaria no pudo localizar los valores existentes de manera confiable porque los datos estaban desordenados.

### Diferencia entre la prediccion y el resultado

Los resultados coincidieron exactamente con la teoría de complejidad Big-O. La búsqueda binaria demostró ser exponencialmente más eficiente, pero confirmó que las precondiciones de ordenamiento son obligatorias.

### Error o comportamiento inesperado

- **Que ocurrio?** Al implementar inicialmente la búsqueda binaria, un error en la actualización de los punteros sin el +1 provocó que el ciclo se congelara en arreglos pequeños.
- **Por que ocurrio?** El valor del medio coincidía con el inicio o fin sin avanzar, cayendo en un bucle infinito.
- **Como lo corregimos o que falta corregir?** Aseguramos que el intervalo avanzara correctamente utilizando inicio = medio + 1 y fin = medio - 1.

## 4. Explicacion en lenguaje llano

Explica el concepto principal como se lo explicarias a una persona de doce
anos. Usa entre tres y cinco lineas y evita palabras tecnicas que no expliques.

Buscar un dato entre un millón de registros sin orden es como buscar una palabra en un libro de texto sin índice, revisando página por página hasta encontrarla (búsqueda lineal). En cambio, la búsqueda binaria es como buscar en un diccionario abierto: abres exactamente a la mitad, miras si la palabra está antes o después, descartas de inmediato toda la mitad que no te sirve, y repites el proceso hasta dar con ella en muy pocos pasos.

### Ejemplo o analogia

[Relaciona el concepto con una situacion cotidiana. Explica que representa
cada parte de la analogia y donde deja de ser exacta.]

## 5. El vacio que encontre

Al intentar explicar el tema, identifica el punto que aun no comprendes bien.

- **Mi duda concreta es:** ¿Cómo manejan los sistemas de bases de datos reales la actualización de índices cuando los datos cambian constantemente de posición?
- **Lo que ya puedo explicar es:** La diferencia matemática y práctica entre la complejidad lineal $O(n)$ y la logarítmica $O(\log n)$, así como la importancia crucial de las precondiciones.
- **Para resolver la duda consulte:**La guía de la Semana 3, las discusiones de código en clase y las pruebas experimentales del banco de pruebas..
- **Ahora lo entiendo asi:** Un algoritmo brillante no sirve de nada si se ejecuta sobre datos que no cumplen las reglas que el algoritmo necesita para funcionar.

## 6. Trazado de la solucion

Escoge una ejecucion, recorrido o caso representativo y trazalo paso a paso.
Incluye los valores importantes despues de cada paso.

| Paso | Estado de los datos o estructura | Decision o resultado |
|---|---|---|
| 1 | [Estado inicial] | [Que ocurre] |
| 2 | [Siguiente estado] | [Que ocurre] |
| 3 | [Siguiente estado] | [Que ocurre] |
| 4 | [Estado final] | [Que ocurre] |

**Completa o agrega filas si es necesario.** Si trabajaste con una estructura,
dibuja su estado en cada paso o inserta aqui una imagen legible.

## 7. Decision de diseño

Relaciona lo aprendido con la Plataforma de Monitoreo Ambiental Urbano.

- **Problema que debiamos resolver:** Consultar eficientemente lecturas específicas por timestamp en un repositorio masivo de hasta 1.000.000 de registros.
- **Estructura, algoritmo o estrategia elegida:** Búsqueda binaria basada en un arreglo ordenado cronológicamente por timestamp.
- **Alternativa descartada:**Búsqueda lineal pura para todas las consultas del sistema.
- **Por que elegimos la primera:** Reduce drásticamente el número de comparaciones de un millón a tan solo 20, optimizando el rendimiento del sistema IoT.
- **Que evidencia respalda la decision:** [Prueba, medicion o comportamiento observado.]

## 8. Aporte al proyecto

- **Archivo(s) o modulo(s) trabajado(s):** [Rutas dentro del repositorio.]
- **Cambio realizado:** [Describe la funcionalidad agregada o modificada.]
- **Como se conecta con la capa anterior:** [Explica la integracion.]
- **Que queda pendiente para la siguiente semana:** [Tarea concreta.]

## 9. Commits realizados

Registra los commits que muestran tu aporte individual.

| Commit | Mensaje | Que demuestra |
|---|---|---|
| `[hash corto]` | `[mensaje del commit]` | [Cambio realizado] |
| `[hash corto]` | `[mensaje del commit]` | [Cambio realizado] |

## 10. Reexplicacion final

Despues del taller, vuelve a responder la pregunta de la semana en cinco lineas
o menos. Esta respuesta debe ser mas precisa que la de la seccion 4 y debe
incluir la razon de tu decision tecnica.

> [Escribe aqui tu reexplicacion final.]

## 11. Reflexion individual

Responde con honestidad:

1. **Lo que ahora puedo hacer y antes no podia:**
   [Respuesta.]
2. **El error o supuesto que mas me enseno:**
   [Respuesta.]
3. **La pregunta que llevaria a la proxima clase:**
   [Respuesta.]
4. **Que parte del trabajo fue realmente mia:**
   [Respuesta concreta.]

## Lista de verificacion antes de entregar

- [ ] Escribi la prediccion antes de consultar el resultado.
- [ ] Inclui evidencia concreta del laboratorio.
- [ ] Explique un concepto sin depender de jerga.
- [ ] Registre un vacio, una duda o un error real.
- [ ] Trace al menos un caso paso a paso.
- [ ] Justifique una decision del proyecto y una alternativa descartada.
- [ ] Registre mis commits y mi aporte individual.
- [ ] Deje claro que queda pendiente.
- [ ] Renombre el archivo con el formato `sXX-nombre.md`.

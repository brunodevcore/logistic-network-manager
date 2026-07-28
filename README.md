# Sistema de Gestión de Red Logística

Sistema Java para la gestión integral de una red logística: administración de mercaderías con búsquedas eficientes y modelado de una red de centros logísticos con conexiones dirigidas, incluyendo búsqueda de caminos de costo mínimo.

Proyecto académico desarrollado en la materia **Algoritmos y Estructuras de Datos 2** de la carrera Analista en Tecnologías de la Información (Universidad ORT Uruguay). Calificación obtenida: **30/30 puntos** — obligatorio exonerado.

---

## Tecnologías

- **Java 21**
- **JUnit 5** (testing)
- **IntelliJ IDEA** (desarrollo)

Sin frameworks ni dependencias externas: todas las estructuras de datos (árboles, colas, grafos) son **implementaciones propias desde cero**, sin usar las clases de `java.util`.

---

## Funcionalidades

El sistema gestiona dos dominios:

### Gestión de mercaderías
- Registro de mercaderías con validación de formato de código mediante expresiones regulares (patrón `AA-BBB-CCCCCC`)
- Búsqueda por ID y por código, retornando la cantidad de nodos recorridos
- Listado ordenado por ID (ascendente y descendente)
- Listado ordenado por código (ascendente)
- Listado filtrado por categoría

### Gestión de red logística
- Registro de centros logísticos (vértices del grafo)
- Registro de conexiones dirigidas con distancia (kilómetros) y tiempo (minutos)
- Búsqueda de centros alcanzables desde un origen con hasta N saltos (BFS)
- Cálculo del camino de menor distancia entre dos centros (Dijkstra)
- Cálculo del camino de menor tiempo entre dos centros (Dijkstra)

---

## Estructuras de datos implementadas

Todas las estructuras están implementadas desde cero, sin usar `java.util.*`:

### ABB — Árbol Binario de Búsqueda genérico
Implementación genérica con `T extends Comparable<T>`. Cada nodo mantiene la propiedad de orden (todos los elementos de la izquierda son menores, los de la derecha mayores). Operaciones implementadas: `insertar`, `buscar` (con conteo de nodos recorridos), `listarAscendente` (in-order) y `listarDescendente` (in-order inverso).

### Cola genérica (FIFO)
Implementación con nodos enlazados. Utilizada como estructura auxiliar en el algoritmo BFS.

### Grafo — Matriz de Adyacencia Dirigida
Grafo dirigido implementado con matriz de adyacencia. Las conexiones tienen dirección: si existe `A → B`, no necesariamente existe `B → A`. Incluye métodos para: agregar vértices y aristas, verificar existencia, comprobar si está lleno, ejecutar BFS y Dijkstra.

### Tupla
Clase auxiliar que agrupa la posición de un vértice y la cantidad de saltos desde el origen. Utilizada por el BFS para recordar la distancia (en saltos) al momento de encolar cada vértice.

### MercaderiaWrapper
Clase que envuelve a `Mercaderia` y sobrescribe `compareTo` para comparar por código en lugar de por ID. Permite mantener dos ABBs sobre los mismos objetos en memoria, indexados por criterios distintos. Modificar la mercadería desde cualquiera de los árboles impacta en el otro (misma referencia).

---

## Algoritmos implementados

### BFS (Breadth-First Search) con Tupla
Recorrido por niveles del grafo utilizado en `redCentrosPorCantidadDeConexiones`. La Tupla permite mantener el conteo de saltos junto con cada vértice encolado: el origen entra con 0 saltos, y cada adyacente recibe los saltos del padre + 1. Los resultados se acumulan en un ABB para retornarlos ordenados por código.

### Dijkstra — Camino de costo mínimo
Encuentra el camino de menor costo entre dos vértices en un grafo dirigido ponderado. Usa tres arrays auxiliares:
- `visitados[]` — vértices ya procesados
- `costos[]` — costo mínimo conocido hacia cada vértice (inicializado en `Integer.MAX_VALUE` salvo el origen)
- `vengo[]` — desde qué vértice se llegó a cada posición (para reconstruir el camino)

**Reutilización elegante:** el algoritmo Dijkstra está implementado una sola vez con un parámetro booleano `usarDistancia`. Cuando es `true` usa `getDistancia()`, cuando es `false` usa `getTiempo()`. Así se resuelve tanto `viajeCostoMinimoDistancia` como `viajeCostoMinimoTiempo` sin duplicar código.

---

## Complejidad algorítmica

| Operación | Complejidad | Justificación |
|-----------|-------------|---------------|
| `registrarMercaderia` | O(log n) | Inserción en ABB balanceado |
| `buscarMercaderiaPorId` | O(log n) | Búsqueda en ABB balanceado |
| `buscarMercaderiaPorCodigo` | O(log n) | Búsqueda en ABB balanceado (via wrapper) |
| `listarMercaderiasPorIdAscendente` | O(n) | Recorrido in-order del ABB |
| `listarMercaderiasPorIdDescendente` | O(n) | Recorrido in-order inverso del ABB |
| `listarMercaderiasPorCodigoAscendente` | O(n) | Recorrido in-order del ABB por código |
| `listarMercaderiasPorCategoria` | O(k) | Acceso directo al ABB de la categoría por índice + recorrido de sus k elementos |
| `redCentrosPorCantidadDeConexiones` | BFS | Recorrido por niveles del grafo |
| `viajeCostoMinimoDistancia` | Dijkstra | Camino mínimo por distancia |
| `viajeCostoMinimoTiempo` | Dijkstra | Camino mínimo por tiempo |

---

## Testing

El proyecto incluye **10 suites de pruebas unitarias** con JUnit 5, cubriendo todas las operaciones del sistema tanto en sus casos exitosos como en cada uno de los errores esperados (validaciones de parámetros null, formatos inválidos, duplicados, existencia de recursos, etc.).

Las pruebas están organizadas por operación y numeradas para reflejar el orden lógico del flujo del sistema (`Test01...`, `Test02...`, etc.).

---

## Cómo ejecutarlo

**Requisitos:** Java 21 e IntelliJ IDEA (o cualquier IDE con soporte de JUnit 5).

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/brunodevcore/logistic-network-manager.git
   ```

2. **Abrir el proyecto** en IntelliJ IDEA (`File → Open` sobre la carpeta clonada).

3. **Ejecutar los tests**
   - Click derecho sobre la carpeta `src/test/java/sistema/`
   - Seleccionar `Run 'Tests in sistema'`

Todas las pruebas deberían pasar.

---

## Documentación técnica adicional

Este proyecto incluye documentación técnica formal con análisis de complejidad detallado por cada operación, justificación de las estructuras de datos elegidas, y explicación de las decisiones de diseño. Disponible bajo pedido.

---

## Autor

**Bruno Rivero** — Desarrollador Backend Junior · Montevideo, Uruguay

- LinkedIn: [linkedin.com/in/brunorivero-dev](https://linkedin.com/in/brunorivero-dev)
- GitHub: [github.com/brunodevcore](https://github.com/brunodevcore)
- Email: bruno.erre@outlook.com

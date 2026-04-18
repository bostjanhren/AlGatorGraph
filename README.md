
# ALGator Graph Library

Java-based extension for JGraphT with unified graph generation, multi-format import. It is used as an extension to ALGator, where it provides support for algorithm testing on graphs.

---

## About

JGraphT is a Java graph library that provides various graph objects and algorithms.

**This project extends JGraphT by adding:**

* A unified interface for generating 80 graph types (classic named graphs and parameterized generators)
* Multi-format graph import capabilities (GraphML, G6/S6, Pajek, DIMACS10, TUDataset, LST, EdgeList/MTX, Stanford SNAP, MalNet, and auto-detection for adjacency matrices and edge lists from unknown file formats).
* Directed and undirected graph implementations with generic vertex and weight types
* Utility methods for transforming JGraphT graph objects into AlGatorGraph graph objects.
* Visual HTML gallery of all available graph generators

# Graph gallery and examples

Go to AlGatorGraph_graphs.html for images of graphs.

## Graphs Without Parameters *(46 graphs)*

### BIDIAKIS CUBE
- **Description:** Bidiakis cube graph - a 3-regular graph with 12 vertices and 18 edges.
- **Parameters:** None

### BLANUSA FIRST SNARK
- **Description:** Blanusa's first snark graph - a snark with 18 vertices and 27 edges.
- **Parameters:** None

### BLANUSA SECOND SNARK
- **Description:** Blanusa's second snark graph - a snark with 18 vertices and 27 edges.
- **Parameters:** None

### BRINKMANN
- **Description:** Brinkmann graph - a symmetric cubic graph with 21 vertices and 31 edges.
- **Parameters:** None

### BUCKY BALL
- **Description:** Bucky ball graph - the graph of a truncated icosahedron (C60 fullerene) with 60 vertices and 90 edges.
- **Parameters:** None

### BULL
- **Description:** Bull graph - a triangle with two pendant vertices (5 vertices, 5 edges).
- **Parameters:** None

### BUTTERFLY
- **Description:** Butterfly graph - also known as the bowtie graph (5 vertices, 6 edges).
- **Parameters:** None

### CHVATAL
- **Description:** Chvatal graph - a triangle-free 4-regular graph with 12 vertices and 24 edges.
- **Parameters:** None

### CLAW
- **Description:** Claw graph (K1,3) - a star graph with one central vertex and three leaves (4 vertices, 3 edges).
- **Parameters:** None

### CLEBSCH
- **Description:** Clebsch graph - a strongly regular graph with 16 vertices and 40 edges.
- **Parameters:** None

### COXETER
- **Description:** Coxeter graph - a non-Hamiltonian 3-regular graph with 28 vertices and 42 edges.
- **Parameters:** None

### DESARGUES
- **Description:** Desargues graph - a distance-transitive cubic graph with 20 vertices and 30 edges.
- **Parameters:** None

### DIAMOND
- **Description:** Diamond graph - a complete graph K4 with one edge removed (4 vertices, 5 edges).
- **Parameters:** None

### DODECAHEDRON
- **Description:** Dodecahedron graph - the graph of a regular dodecahedron (20 vertices, 30 edges).
- **Parameters:** None

### DOUBLE STAR SNARK
- **Description:** Double star snark graph - a snark with 30 vertices and 45 edges.
- **Parameters:** None

### DOYLE
- **Description:** Doyle graph - a symmetric cubic graph with 27 vertices and 40 edges.
- **Parameters:** None

### DURER
- **Description:** Dürer graph - a polyhedral graph with 12 vertices and 18 edges.
- **Parameters:** None

### ELLINGHAM HORTON 54
- **Description:** Ellingham-Horton 54 graph - a bipartite cubic graph with 54 vertices and 81 edges.
- **Parameters:** None

### ELLINGHAM HORTON 78
- **Description:** Ellingham-Horton 78 graph - a bipartite cubic graph with 78 vertices and 117 edges.
- **Parameters:** None

### ERRERA
- **Description:** Errera graph - a semi-symmetric cubic graph with 17 vertices and 27 edges.
- **Parameters:** None

### FOLKMAN
- **Description:** Folkman graph - a semi-symmetric regular graph with 20 vertices and 40 edges.
- **Parameters:** None

### FRANKLIN
- **Description:** Franklin graph - a 3-regular graph with 12 vertices and 18 edges.
- **Parameters:** None

### FRUCHT
- **Description:** Frucht graph - a 3-regular graph with 12 vertices and 18 edges with no non-trivial symmetries.
- **Parameters:** None

### GEM
- **Description:** Gem graph - diamond graph with an extra vertex attached.
- **Parameters:** None

### GOLDNER HARARY
- **Description:** Goldner-Harary graph - a maximal planar graph with 11 vertices and 27 edges.
- **Parameters:** None

### GOSSET
- **Description:** Gosset graph - a graph related to the E8 root system with 56 vertices and 756 edges.
- **Parameters:** None

### GROTZSCH
- **Description:** Grötzsch graph - a triangle-free graph with 11 vertices and 20 edges and chromatic number 4.
- **Parameters:** None

### HEAWOOD
- **Description:** Heawood graph - the unique (3,6)-cage graph with 14 vertices and 21 edges.
- **Parameters:** None

### HERSCHEL
- **Description:** Herschel graph - the smallest non-Hamiltonian polyhedral graph (11 vertices, 18 edges).
- **Parameters:** None

### HOFFMAN
- **Description:** Hoffman graph - a 4-regular graph with 16 vertices and 32 edges.
- **Parameters:** None

### HOUSE
- **Description:** House graph - pentagon with a triangle on top.
- **Parameters:** None

### KITTELL
- **Description:** Kittell graph - a graph with 23 vertices and 63 edges.
- **Parameters:** None

### KLEIN 3 REGULAR
- **Description:** Klein 3-regular graph - a cubic graph with 56 vertices and 84 edges.
- **Parameters:** None

### KLEIN 7 REGULAR
- **Description:** Klein 7-regular graph - a 7-regular graph with 24 vertices and 84 edges.
- **Parameters:** None

### KRACKHARDT KITE
- **Description:** Krackhardt kite graph - a social network example with 10 vertices and 18 edges.
- **Parameters:** None

### MOBIUS KANTOR
- **Description:** Möbius-Kantor graph - a symmetric bipartite cubic graph with 16 vertices and 24 edges.
- **Parameters:** None

### MOSER SPINDLE
- **Description:** Moser spindle graph - a unit distance graph with 7 vertices and 11 edges.
- **Parameters:** None

### NAURU
- **Description:** Nauru graph - a symmetric bipartite cubic graph with 24 vertices and 36 edges.
- **Parameters:** None

### PAPPUS
- **Description:** Pappus graph - a 3-regular graph with 18 vertices and 27 edges.
- **Parameters:** None

### PETERSEN
- **Description:** Petersen graph - a well-known symmetric cubic graph with 10 vertices and 15 edges.
- **Parameters:** None

### POUSSIN
- **Description:** Poussin graph - a graph with 15 vertices and 39 edges.
- **Parameters:** None

### SCHLAFLI
- **Description:** Schläfli graph - a strongly regular graph with 27 vertices and 216 edges.
- **Parameters:** None

### THOMSEN
- **Description:** Thomsen graph (K3,3) - the utility graph, a complete bipartite graph (6 vertices, 9 edges).
- **Parameters:** None

### TIETZE
- **Description:** Tietze graph - a cubic graph with 12 vertices and 18 edges.
- **Parameters:** None

### TUTTE
- **Description:** Tutte graph - the smallest cubic graph without a Hamiltonian cycle (46 vertices, 69 edges).
- **Parameters:** None

### ZACHARY KARATE CLUB
- **Description:** Zachary's karate club graph - a social network of a university karate club (34 vertices, 78 edges).
- **Parameters:** None

---

## Graphs With Parameters *(34 graphs)*

### BARABASI ALBERT FOREST
- **Description:** Barabasi-Albert forest generator.
- **Parameters:** `[numTrees: int, numVertices: int, seed: long]`
- **Example:** `BARABASI_ALBERT_FOREST, 3, 20, 42`

### BARABASI ALBERT GRAPH
- **Description:** Barabasi-Albert scale-free network model.
- **Parameters:** `[initialVertices: int, edgesPerStep: int, finalVertices: int, seed: long]`
- **Example:** `BARABASI_ALBERT_GRAPH, 10, 2, 20`

### BARBELL
- **Description:** Barbell graph - two complete graphs connected by a bridge.
- **Parameters:** `[size1: int, size2: int]`
- **Example:** `BARBELL, 4, 3`

### BROOM
- **Description:** Broom graph - path with multiple leaves at one end.
- **Parameters:** `[pathLength: int, numLeaves: int]`
- **Example:** `BROOM, 5, 3`

### COMPLETE BIPARTITE
- **Description:** Complete bipartite graph.
- **Parameters:** `[set1Size: int, set2Size: int]`
- **Example:** `COMPLETE_BIPARTITE, 5, 15`

### COMPLETE
- **Description:** Complete graph (clique).
- **Parameters:** `[numVertices: int]`
- **Example:** `COMPLETE, 20`

### CROWN
- **Description:** Crown graph - bipartite graph with a crown pattern.
- **Parameters:** `[size: int]`
- **Example:** `CROWN, 5`

### DIRECTED SCALE FREE
- **Description:** Directed scale-free graph.
- **Parameters:** `[alpha: float, gamma: float, deltaIn: float, deltaOut: float, targetEdges: int, targetNodes: int, seed: long]`
- **Example:** `DIRECTED_SCALE_FREE, 20, 30, 0.3, 0.3, 0.4, 0.1`

### EMPTY
- **Description:** Empty graph (edgeless graph).
- **Parameters:** `[numVertices: int]`
- **Example:** `EMPTY, 20`

### FAN
- **Description:** Fan graph - a single vertex joined to a path.
- **Parameters:** `[pathLength: int]`
- **Example:** `FAN, 4`

### GENERALIZED PETERSEN
- **Description:** Generalized Petersen graph.
- **Parameters:** `[n: int, k: int]`
- **Example:** `GENERALIZED_PETERSEN, 10, 3`

### GNM RANDOM BIPARTITE
- **Description:** G(n,m) random bipartite graph.
- **Parameters:** `[set1Size: int, set2Size: int, numEdges: int, seed: long]`
- **Example:** `GNM_RANDOM_BIPARTITE, 10, 10, 25, 42`

### GNM RANDOM GRAPH
- **Description:** G(n,m) random graph (Erdős–Rényi model).
- **Parameters:** `[numVertices: int, numEdges: int, seed: long]`
- **Example:** `GNM_RANDOM_GRAPH, 50, 100, 42`

### GNP RANDOM BIPARTITE
- **Description:** G(n,p) random bipartite graph.
- **Parameters:** `[set1Size: int, set2Size: int, edgeProbability: double, seed: long]`
- **Example:** `GNP_RANDOM_BIPARTITE, 25, 35, 0.3, 42`

### GNP RANDOM GRAPH
- **Description:** G(n,p) random graph (Erdős–Rényi model).
- **Parameters:** `[numVertices: int, edgeProbability: double, seed: long]`
- **Example:** `GNP_RANDOM_GRAPH, 40, 0.2, 42`

### GRID
- **Description:** Grid graph.
- **Parameters:** `[rows: int, columns: int]`
- **Example:** `GRID, 3, 4`

### HYPERCUBE
- **Description:** Hypercube graph.
- **Parameters:** `[dimensions: int]`
- **Example:** `HYPERCUBE, 5`

### KLEINBERG SMALL WORLD
- **Description:** Kleinberg small-world network.
- **Parameters:** `[gridSize: int, localEdges: int, longRangeEdges: int, clusteringExponent: int, seed: long]`
- **Example:** `KLEINBERG_SMALL_WORLD, 10, 2, 3, 42`

### LADDER
- **Description:** Ladder graph - two parallel paths connected by edges (rungs).
- **Parameters:** `[length: int]`
- **Example:** `LADDER, 5`

### LINEAR
- **Description:** Linear graph (path graph).
- **Parameters:** `[numVertices: int]`
- **Example:** `LINEAR, 20`

### LINEARIZED CHORD DIAGRAM
- **Description:** Linearized chord diagram model.
- **Parameters:** `[numVertices: int, numEdges: int, seed: long]`
- **Example:** `LINEARIZED_CHORD_DIAGRAM, 40, 60, 42`

### PLANTED PARTITION
- **Description:** Planted partition graph (stochastic block model).
- **Parameters:** `[numBlocks: int, blockSize: int, intraProbability: double, interProbability: double, seed: long]`
- **Example:** `PLANTED_PARTITION, 3, 5, 0.8, 0.1, 42`

### PRISM
- **Description:** Prism graph - two n-cycles connected by matching edges.
- **Parameters:** `[n: int]`
- **Example:** `PRISM, 4`

### PRUFER TREE
- **Description:** Random tree generator using Prüfer sequences.
- **Parameters:** `[numVertices: int, seed: long]`
- **Example:** `PRUFER_TREE, 15, 42`

### RANDOM REGULAR
- **Description:** Random regular graph.
- **Parameters:** `[numVertices: int, degree: int, seed: long]`
- **Example:** `RANDOM_REGULAR, 30, 4, 42`

### RING
- **Description:** Ring graph (cycle graph).
- **Parameters:** `[numVertices: int]`
- **Example:** `RING, 25`

### SCALE FREE
- **Description:** Scale-free graph (preferential attachment model).
- **Parameters:** `[numVertices: int, seed: long]`
- **Example:** `SCALE_FREE, 10, 423`

### SIMPLE WEIGHTED BIPARTITE MATRIX
- **Description:** Simple weighted bipartite graph from matrix.
- **Parameters:** `[weights: double[][]]`
- **Example:** `SIMPLE_WEIGHTED_BIPARTITE_MATRIX, 1,2,3, 4,5, 1.0,2.0;3.0,4.0;5.0,6.0`

### SIMPLE WEIGHTED GRAPH MATRIX
- **Description:** Simple weighted graph from matrix.
- **Parameters:** `[weights: double[][]]`
- **Example:** `SIMPLE_WEIGHTED_GRAPH_MATRIX, 1,2,3, 0.0,1.0,2.0;1.0,0.0,3.0;2.0,3.0,0.0`

### STAR
- **Description:** Star graph.
- **Parameters:** `[numVertices: int]`
- **Example:** `STAR, 15`

### SUNLET
- **Description:** Sunlet graph - cycle with pendant vertices attached to each cycle vertex.
- **Parameters:** `[cycleSize: int]`
- **Example:** `SUNLET, 5`

### WATTS STROGATZ
- **Description:** Watts-Strogatz small-world network.
- **Parameters:** `[numVertices: int, numNeighbors: int, rewiringProbability: double, seed: long]`
- **Example:** `WATTS_STROGATZ, 30, 4, 0.1, 42`

### WHEEL
- **Description:** Wheel graph.
- **Parameters:** `[numVertices: int]`
- **Example:** `WHEEL, 20`

### WINDMILL
- **Description:** Windmill graph (friendship graph).
- **Parameters:** `[numBlades: int, bladeSize: int, mode: Mode]`
- **Example:** `WINDMILL, 5, 4, WINDMILL`

---

## Creating Graphs with ALGatorGraph Library

The ALGatorGraph library provides two main graph classes: **Graph** (undirected) and **DGraph** (directed). Both support generic vertex and edge types with generic weights.

### Example 1: Creating a Simple Undirected Graph

```java
import algatorgraph.Graph;
import algatorgraph.Edge;

// Create an undirected graph with String vertices and Integer weights
Graph<String, Integer> cityGraph = new Graph<>();

// Add vertices (cities)
cityGraph.addVertex("New York");
cityGraph.addVertex("Boston");
cityGraph.addVertex("Washington DC");

// Create edges with weights
Edge<String, Integer> edge1 = new Edge<>("New York", "Boston", 215);
Edge<String, Integer> edge2 = new Edge<>("New York", "Washington DC", 225);
Edge<String, Integer> edge3 = new Edge<>("Boston", "Washington DC", 440);

// Add edges to the graph
cityGraph.addEdge("New York", "Boston", edge1);
cityGraph.addEdge("New York", "Washington DC", edge2);
cityGraph.addEdge("Boston", "Washington DC", edge3);

System.out.println(cityGraph);
```

### Example 2: Create a Directed Graph

```java
import algatorgraph.DGraph;
import algatorgraph.DEdge;

// Create a directed graph
DGraph<String, Double> webGraph = new DGraph<>();

webGraph.addVertex("Page A");
webGraph.addVertex("Page B");
webGraph.addVertex("Page C");

// Add directed edges with importance scores
webGraph.addEdge("Page A", "Page B", new DEdge<>("Page A", "Page B", 0.85));
webGraph.addEdge("Page A", "Page C", new DEdge<>("Page A", "Page C", 0.45));
webGraph.addEdge("Page B", "Page C", new DEdge<>("Page B", "Page C", 0.92));

System.out.println("Outgoing links from Page A: " + webGraph.outgoingEdgesOf("Page A"));
```

### Example 3: Build a Unweighted Graph

```java
// Create an unweighted graph
Graph<Integer, Object> myGraph = new Graph<>();

// Add vertices 0 through 4
for (int i = 0; i < 5; i++) {
    myGraph.addVertex(i);
}

// Add unweighted edges
myGraph.addEdge(0, 1);
myGraph.addEdge(0, 2);
myGraph.addEdge(1, 2);
myGraph.addEdge(1, 3);
myGraph.addEdge(2, 4);
myGraph.addEdge(3, 4);

System.out.println("Vertices: " + myGraph.vertexSet().size());  // 5
System.out.println("Edges: " + myGraph.edgeSet().size());       // 6
```

### Example 4: Generate Graphs with GraphCreator
```java
import algatorgraph.GraphCreator;

// Generate the Petersen graph
String[] petersenArgs = {"PETERSEN"};
Graph<Integer, DefaultWeightedEdge> petersen = GraphCreator.generateGraph(petersenArgs);

// Generate a complete graph K5
String[] completeArgs = {"COMPLETE", "5"};
Graph<Integer, DefaultWeightedEdge> k5 = GraphCreator.generateGraph(completeArgs);

// Generate a random graph
String[] randomArgs = {"GNP_RANDOM_GRAPH", "20", "0.3", "12345"};
Graph<Integer, DefaultWeightedEdge> randomGraph = GraphCreator.generateGraph(randomArgs);
```

### #### Example 5: Import Graphs from Files
```java
// Import a single graph
File graphFile = new File("graphs/mygraph.graphml");
Graph importedGraph = GraphCreator.importGraphFromFile(graphFile);

// Import all graphs from a folder
List<Graph> graphs = GraphCreator.importGraphsFromFolder("path/to/graphs/folder");
```


# Graph Import Format References

| Format | Link |
|--------|------|
| **GraphML** | http://graphml.graphdrawing.org/ |
| **Graph6 / Sparse6** | http://users.cecs.anu.edu.au/~bdm/data/formats.txt |
| **Pajek** | http://mrvar.fdv.uni-lj.si/pajek/ |
| **DIMACS10** | https://www.cc.gatech.edu/dimacs10/ |
| **TUDataset** | https://chrsmrrs.github.io/datasets/ |
| **Network Repository** | https://networkrepository.com/ |
| **Stanford SNAP** | https://snap.stanford.edu/ |
| **MalNet** | https://malnet.org/ |
| **LST** | (Custom format - no official link) |

# Graph Collection Sources 

The graph collections included in this repository ("graphs" folder) are sourced from the following academic and research databases:

| Source Name | Link |
|-------------|------| 
| **GD Benchmark Sets** | https://visdunneright.github.io/gd_benchmark_sets/ |
| **Pajek Datasets** | http://vlado.fmf.uni-lj.si/pub/networks/data/ |
| **Network Repository** | https://networkrepository.com |
| **Stanford SNAP** | https://snap.stanford.edu/data/ |
| **TU Dortmund** | https://chrsmrrs.github.io/datasets/docs/datasets/ |
| **DIMACS Archive** | https://sparse.tamu.edu/DIMACS10 |
| **House of Graphs** | https://houseofgraphs.org |
| **MalNet Project** | https://www.mal-net.org/ |

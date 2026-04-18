package algatorgraph;
import java.util.Set;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Utility class for converting graphs from JGraphT representations to custom graph implementations.
 * <p>
 * Provides static methods to convert JGraphT graphs to custom graph implementations with generic
 * vertex and weight types.
 * <p>
 * <b>Note:</b> This converter assumes:
 * <ul>
 *   <li>Edge weights are numeric types (Double, Integer, Float, etc.) This is because we assume the use of {@link org.jgrapht.graph.DefaultEdge} and {@link org.jgrapht.graph.DefaultWeightedEdge} </li>
 *   <li>Vertex types are preserved during conversion</li>
 *   <li>Graph directionality is preserved (directed JGraphT graphs {@literal ->} DGraph, undirected {@literal ->} Graph)</li>
 * </ul>
 */
public class GraphConverter {
    
    /**
     * Converts a JGraphT graph (directed or undirected) to a DGraph with generic weight type.
     * <p>
     * <b>Important:</b> This method always creates a {@link DGraph} regardless of whether
     * the input graph is directed or undirected in JGraphT. For undirected graphs,
     * consider using {@link #JGraphTToU(org.jgrapht.Graph, java.lang.Class)} instead.
     * 
     * <pre>
     * Usage example 1: Convert a directed JGraphT graph with Double weights
     * Graph&lt;Integer, DefaultWeightedEdge&gt; jGraph = new DefaultDirectedWeightedGraph&lt;&gt;(DefaultWeightedEdge.class);
     * jGraph.addVertex(1);
     * jGraph.addVertex(2);
     * DefaultWeightedEdge e = jGraph.addEdge(1, 2);
     * jGraph.setEdgeWeight(e, 3.5);
     * DGraph&lt;Integer, Double&gt; dGraph = GraphConverter.JGraphTToD(jGraph, Double.class);
     * System.out.println(dGraph);
     * </pre>
     * 
     * <pre>
     * Usage example 2: Convert imported graph with Integer weights
     * List&lt;Graph&gt; graphs = GraphCreator.importGraphsFromFolder("path_to_folder");
     * DGraph&lt;String, Integer&gt; dGraph = GraphConverter.JGraphTToD(graphs.get(0), Integer.class);
     * System.out.println(dGraph);
     * </pre>
     * 
     * <pre>
     * Usage example 3: Convert generated graph with Float weights
     * String[] args = {"DIRECTED_SCALE_FREE", "0.3", "0.5", "0.1", "0.1", "50", "20"};
     * Graph&lt;Integer, DefaultEdge&gt; jGraph = GraphCreator.generateGraph(args);
     * DGraph&lt;Integer, Float&gt; dGraph = GraphConverter.JGraphTToD(jGraph, Float.class);
     * System.out.println(dGraph);
     * </pre>
     * 
     * @param <V> the type of vertices in both input and output graphs
     * @param <W> the type of edge weights in the output graph (must extend Number)
     * @param jgraphtGraph the JGraphT graph to convert (can be directed or undirected)
     * @param weightClass the class object representing the weight type (e.g., Double.class, Integer.class)
     * @return a {@link DGraph} containing all vertices and edges from the input graph.
     *         Edge weights will be:
     *         <ul>
     *           <li>{@code null} for unweighted edges ({@link DefaultEdge})</li>
     *           <li>The original weight converted to type W for weighted edges ({@link DefaultWeightedEdge})</li>
     *         </ul>
     * @throws ClassCastException if vertices cannot be cast to type V or weights cannot be converted to type W
     * @throws NullPointerException if jgraphtGraph or weightClass is null
     * @throws IllegalArgumentException if weightClass is not a supported numeric type
     */
    public static <V, W extends Number> DGraph<V, W> JGraphTToD(org.jgrapht.Graph jgraphtGraph, Class<W> weightClass) {
        if (jgraphtGraph == null) {
            throw new NullPointerException("JGraphT graph cannot be null");
        }
        if (weightClass == null) {
            throw new NullPointerException("Weight class cannot be null");
        }
        
        DGraph<V, W> dGraph = new DGraph<>();

        for (Object vObj : jgraphtGraph.vertexSet()) {
            dGraph.addVertex((V) vObj);
        }

        for (Object edgeObj : jgraphtGraph.edgeSet()) {
        	V source = null;
        	V target = null;
        	try {
        	    source = (V) jgraphtGraph.getEdgeSource(edgeObj);
        	    target = (V) jgraphtGraph.getEdgeTarget(edgeObj);
        	} catch (ClassCastException e) {
        	    throw new IllegalArgumentException(
        	        "Vertex type mismatch: cannot cast vertex to expected type. " +
        	        "Edge: " + edgeObj + ". Cause: " + e.getMessage()
        	    );
        	}
            W weight = null;
            if (edgeObj instanceof DefaultWeightedEdge) {
                double originalWeight = jgraphtGraph.getEdgeWeight(edgeObj);
                weight = convertWeight(originalWeight, weightClass);
            }

            DEdge<V, W> myEdge = new DEdge<>(source, target, weight);
            dGraph.addEdge(source, target, myEdge);
        }

        return dGraph;
    }

    /**
     * Converts a JGraphT graph to a DGraph with Double weights (convenience method).
     * 
     * @param <V> the type of vertices
     * @param jgraphtGraph the JGraphT graph to convert
     * @return a DGraph with Double weights
     * @see #JGraphTToD(org.jgrapht.Graph, java.lang.Class)
     */
    public static <V> DGraph<V, Double> JGraphTToD(org.jgrapht.Graph jgraphtGraph) {
        return JGraphTToD(jgraphtGraph, Double.class);
    }
   
    /**
     * Converts a JGraphT undirected graph to an undirected Graph with generic weight type.
     * <p>
     * <b>Important:</b> This method is designed for undirected JGraphT graphs.
     * For directed graphs, use {@link #JGraphTToD(org.jgrapht.Graph, java.lang.Class)}.
     * 
     * <pre>
     * Usage example 1: Convert a simple undirected graph with Double weights
     * Graph&lt;Integer, DefaultWeightedEdge&gt; jGraph = new SimpleWeightedGraph&lt;&gt;(DefaultWeightedEdge.class);
     * jGraph.addVertex(1);
     * jGraph.addVertex(2);
     * DefaultWeightedEdge e = jGraph.addEdge(1, 2);
     * jGraph.setEdgeWeight(e, 2.5);
     * Graph&lt;Integer, Double&gt; uGraph = GraphConverter.JGraphTToU(jGraph, Double.class);
     * System.out.println(uGraph);
     * </pre>
     *
     * <pre>
     * Usage example 2: Convert generated complete graph with Integer weights
     * String[] args = {"COMPLETE", "5"};
     * Graph&lt;Integer, DefaultEdge&gt; jGraph = GraphCreator.generateGraph(args);
     * Graph&lt;Integer, Integer&gt; uGraph = GraphConverter.JGraphTToU(jGraph, Integer.class);
     * System.out.println(uGraph);
     * </pre>
     *
     * <pre>
     * Usage example 3: Convert imported graph with custom weight type
     * List&lt;Graph&gt; graphs = GraphCreator.importGraphsFromFolder("path_to_folder");
     * Graph&lt;String, Float&gt; uGraph = GraphConverter.JGraphTToU(graphs.get(0), Float.class);
     * System.out.println(uGraph);
     * </pre>
     *
     * @param <V> the type of vertices in both input and output graphs
     * @param <W> the type of edge weights in the output graph (must extend Number)
     * @param jgraphtGraph the JGraphT undirected graph to convert
     * @param weightClass the class object representing the weight type (e.g., Double.class, Integer.class)
     * @return a {@link Graph} (undirected) containing all vertices and edges from the input.
     *         Edge weights will be:
     *         <ul>
     *           <li>{@code null} for unweighted edges ({@link DefaultEdge})</li>
     *           <li>The original weight converted to type W for weighted edges ({@link DefaultWeightedEdge})</li>
     *         </ul>
     * @throws ClassCastException if vertices cannot be cast to type V or weights cannot be converted to type W
     * @throws NullPointerException if jgraphtGraph or weightClass is null
     * @throws IllegalArgumentException if weightClass is not a supported numeric type
     */
    public static <V, W extends Number> Graph<V, W> JGraphTToU(org.jgrapht.Graph jgraphtGraph, Class<W> weightClass) {
        if (jgraphtGraph == null) {
            throw new NullPointerException("JGraphT graph cannot be null");
        }
        if (weightClass == null) {
            throw new NullPointerException("Weight class cannot be null");
        }
        
        Graph<V, W> myGraph = new Graph<>();

        for (Object vObj : jgraphtGraph.vertexSet()) {
            myGraph.addVertex((V) vObj);
        }

        for (Object edgeObj : jgraphtGraph.edgeSet()) {
        	V source = null;
        	V target = null;
        	try {
        	    source = (V) jgraphtGraph.getEdgeSource(edgeObj);
        	    target = (V) jgraphtGraph.getEdgeTarget(edgeObj);
        	} catch (ClassCastException e) {
        	    throw new IllegalArgumentException(
        	        "Vertex type mismatch: cannot cast vertex to expected type. " +
        	        "Edge: " + edgeObj + ". Cause: " + e.getMessage()
        	    );
        	}

            W weight = null;
            if (edgeObj instanceof DefaultWeightedEdge) {
                double originalWeight = jgraphtGraph.getEdgeWeight(edgeObj);
                weight = convertWeight(originalWeight, weightClass);
            }

            Edge<V, W> myEdge = new Edge<>(source, target, weight);
            myGraph.addEdge(source, target, myEdge);
        }

        return myGraph;
    }

    /**
     * Converts a JGraphT undirected graph to a Graph with Double weights (convenience method).
     * 
     * @param <V> the type of vertices
     * @param jgraphtGraph the JGraphT graph to convert
     * @return a Graph with Double weights
     * @see #JGraphTToU(org.jgrapht.Graph, java.lang.Class)
     */
    public static <V> Graph<V, Double> JGraphTToU(org.jgrapht.Graph jgraphtGraph) {
        return JGraphTToU(jgraphtGraph, Double.class);
    }

    /**
     * Helper method to convert a double weight to the specified numeric type.
     * 
     * @param <W> the target weight type (must extend Number)
     * @param weight the original weight as double
     * @param weightClass the target weight class
     * @return the weight converted to type W
     * @throws IllegalArgumentException if weightClass is not a supported numeric type
     */
    @SuppressWarnings("unchecked")
    private static <W extends Number> W convertWeight(double weight, Class<W> weightClass) {
        if (weightClass == Double.class) {
            return (W) Double.valueOf(weight);
        } else if (weightClass == Integer.class) {
            return (W) Integer.valueOf((int) Math.round(weight));
        } else if (weightClass == Float.class) {
            return (W) Float.valueOf((float) weight);
        } else if (weightClass == Long.class) {
            return (W) Long.valueOf(Math.round(weight));
        } else if (weightClass == Short.class) {
            return (W) Short.valueOf((short) Math.round(weight));
        } else if (weightClass == Byte.class) {
            return (W) Byte.valueOf((byte) Math.round(weight));
        } else {
            throw new IllegalArgumentException("Unsupported weight type: " + weightClass.getName());
        }
    }
    
    /**
     * Determines whether a JGraphT graph should be converted to directed or undirected format.
     * <p>
     * This helper method can be used before conversion to decide which converter to use.
     * 
     * <pre>
     * Usage example:
     * org.jgrapht.Graph jGraph = // ... get a graph
     * if (GraphConverter.isDirected(jGraph)) {
     *     DGraph&lt;Integer, Double&gt; dGraph = GraphConverter.JGraphTToD(jGraph, Double.class);
     *     // Process as directed graph
     * } else {
     *     Graph&lt;Integer, Double&gt; uGraph = GraphConverter.JGraphTToU(jGraph, Double.class);
     *     // Process as undirected graph
     * }
     * </pre>
     * 
     * @param jgraphtGraph the JGraphT graph to check
     * @return {@code true} if the graph is directed, {@code false} if undirected
     * @throws NullPointerException if jgraphtGraph is null
     */
    public static boolean isDirected(org.jgrapht.Graph jgraphtGraph) {
        if (jgraphtGraph == null) {
            throw new NullPointerException("JGraphT graph cannot be null");
        }
        return jgraphtGraph.getType().isDirected();
    }
    
    /**
     * Unified converter that automatically detects graph directionality and converts
     * with the specified weight type.
     * 
     * <pre>
     * Usage example:
     * org.jgrapht.Graph jGraph = // ... get a graph
     * Object convertedGraph = GraphConverter.convert(jGraph, Double.class);
     * 
     * if (convertedGraph instanceof DGraph) {
     *     DGraph&lt;Integer, Double&gt; dGraph = (DGraph&lt;Integer, Double&gt;) convertedGraph;
     *     // Process as directed graph
     * } else {
     *     Graph&lt;Integer, Double&gt; uGraph = (Graph&lt;Integer, Double&gt;) convertedGraph;
     *     // Process as undirected graph
     * }
     * </pre>
     * 
     * @param <V> the type of vertices
     * @param <W> the type of edge weights
     * @param jgraphtGraph the JGraphT graph to convert
     * @param weightClass the class object representing the weight type
     * @return either a {@link DGraph} or {@link Graph} based on the input graph's directionality
     * @throws NullPointerException if jgraphtGraph or weightClass is null
     */
    @SuppressWarnings("unchecked")
    public static <V, W extends Number> Object convert(org.jgrapht.Graph jgraphtGraph, Class<W> weightClass) {
        if (isDirected(jgraphtGraph)) {
            return JGraphTToD(jgraphtGraph, weightClass);
        } else {
            return JGraphTToU(jgraphtGraph, weightClass);
        }
    }
    
    /**
     * Unified converter that automatically detects graph directionality and converts
     * with Double as the default weight type.
     * 
     * <pre>
     * Usage example:
     * org.jgrapht.Graph jGraph = // ... get a graph
     * Object convertedGraph = GraphConverter.convert(jGraph);
     * 
     * if (convertedGraph instanceof DGraph) {
     *     DGraph&lt;Integer, Double&gt; dGraph = (DGraph&lt;Integer, Double&gt;) convertedGraph;
     *     // Process as directed graph
     * } else {
     *     Graph&lt;Integer, Double&gt; uGraph = (Graph&lt;Integer, Double&gt;) convertedGraph;
     *     // Process as undirected graph
     * }
     * </pre>
     * 
     * @param <V> the type of vertices
     * @param jgraphtGraph the JGraphT graph to convert
     * @return either a {@link DGraph} or {@link Graph} based on the input graph's directionality
     * @throws NullPointerException if jgraphtGraph is null
     * @see #convert(org.jgrapht.Graph, java.lang.Class)
     */
    public static <V> Object convert(org.jgrapht.Graph jgraphtGraph) {
        return convert(jgraphtGraph, Double.class);
    }
}
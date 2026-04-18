package algatorgraph;
import java.io.Serializable;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jgrapht.GraphType;
import org.jgrapht.graph.DefaultGraphType;

/**
 * Represents an undirected graph using the adjacency list representation. The graph supports generic vertex 
 * and edge types, with edges having a generic weight type. The adjacency lists are stored in a HashMap objects.
 *
 * <p>Example usage:</p>
 * 
 * <pre>{@code
 * // Create a graph with String vertices and Integer weights
 * Graph<String, Integer> cityGraph = new Graph<>();
 * 
 * // Add vertices (cities)
 * cityGraph.addVertex("New York");
 * cityGraph.addVertex("Boston");
 * 
 * // Add a weighted edge (distance between cities)
 * cityGraph.addEdge("New York", "Boston", new Edge<>("New York", "Boston", 215));
 * 
 * // Get all edges
 * Set<Edge<String, Integer>> allRoutes = cityGraph.edgeSet();
 * }</pre>
 * 
 * @param <V> the type of vertices
 * @param <W> the type of weights on edges
 */
public class Graph<V, W> implements org.jgrapht.Graph<V, Edge<V, W>>,Serializable {
	/** Adjacency lists mapping each vertex to its incident edges */
	private Map<V, HashSet<Edge<V, W>>> adjacencyLists;
	
    /**
     * Constructor creates an empty adjacencyList
     */
    public Graph() {
        this.adjacencyLists = new HashMap<>();
    }

    /**
     * Returns the set of all edges in the graph.
     * 
     * @return the set of all edges in the graph
     * Time complexity: O(E), where E is the number of edges.
     */
    @Override
    public Set<Edge<V, W>> edgeSet() {
        Set<Edge<V, W>> allEdges = new HashSet<>();
        for (Set<Edge<V, W>> edges : adjacencyLists.values()) {
            allEdges.addAll(edges);
        }
        return allEdges;
    }

    /**
     * Returns the set of edges connected to the specified vertex.
     * 
     * @param v the vertex whose edges are to be returned
     * @return the set of edges connected to the specified vertex
     * Time complexity: O(deg(v)), where deg(v) is the degree of the vertex. We require O(deg(v)) to copy the edges to the new HashSet. Lookup happens in O(1),  
     */
    @Override
    public Set<Edge<V, W>> edgesOf(V v) {
        Objects.requireNonNull(v, "Vertex cannot be null");
        return new HashSet<>(adjacencyLists.getOrDefault(v, new HashSet<>()));
    }

    /**
     * Removes the specified edge from the graph.
     * 
     * @param e the edge to be removed
     * @return true if the edge was successfully removed, false otherwise
     * Time complexity: O(1) to lookup and remove the Edge using HashSet
     */
    @Override
    public boolean removeEdge(Edge<V, W> e) {
    	Objects.requireNonNull(e, "Edge cannot be null");
        V v1 = e.getVertex1();
        V v2 = e.getVertex2();
        
        boolean removedV1 = adjacencyLists.getOrDefault(v1, new HashSet<>()).remove(e);
        boolean removedV2 = adjacencyLists.getOrDefault(v2, new HashSet<>()).remove(e);
        
        return removedV1 || removedV2;
    }

    /**
     * Removes the specified vertex and all its edges from the graph.
     * 
     * @param v the vertex to be removed
     * @return true if the vertex was successfully removed, false otherwise
     * Time complexity: O(E), where E is the number of edges.
     */
    @Override
    public boolean removeVertex(V v) {
    	Objects.requireNonNull(v, "Vertex cannot be null");
        if (adjacencyLists.containsKey(v)) {
            adjacencyLists.remove(v);
            adjacencyLists.values().forEach(edges -> edges.removeIf(edge -> edge.getVertex1().equals(v) || edge.getVertex2().equals(v)));
            return true;
        }
        return false;
    }

    /**
     * Returns the set of all vertices in the graph.
     * 
     * @return the set of all vertices in the graph
     * Time complexity: O(V).
     */
    @Override
    public Set<V> vertexSet() {
        return new HashSet<>(adjacencyLists.keySet());
    }

    /**
     * Returns the edge connecting the specified vertices.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the edge connecting the specified vertices, or null if no such edge exists
     * Time complexity: O(deg(vertex1)), deg being the amount of edges connected to vertex1.
     */
    @Override
    public Edge<V, W> getEdge(V vertex1, V vertex2) {
    	Objects.requireNonNull(vertex1, "vertex1 cannot be null");
    	Objects.requireNonNull(vertex2, "vertex2 cannot be null");
    	for (Edge<V, W> edge : adjacencyLists.getOrDefault(vertex1, new HashSet<>())) {
            if (edge.getVertex1().equals(vertex2) || edge.getVertex2().equals(vertex2)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Returns all edges connecting the specified vertices.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return a set of all edges connecting the specified vertices
     * Time complexity: O(deg(vertex1)), deg being the amount of edges connected to vertex1.
     */
    @Override
    public Set<Edge<V, W>> getAllEdges(V vertex1, V vertex2) {
    	Objects.requireNonNull(vertex1, "vertex1 cannot be null");
    	Objects.requireNonNull(vertex2, "vertex2 cannot be null");
        return adjacencyLists.getOrDefault(vertex1, new HashSet<>())
                .stream()
                .filter(edge -> edge.getVertex1().equals(vertex2) || edge.getVertex2().equals(vertex2))
                .collect(Collectors.toSet());
    }

    /**
     * Not implemented.
     * @return a supplier for creating new edges. 
     * Time complexity: O(1).
     */
    @Override
    public Supplier<Edge<V, W>> getEdgeSupplier() {
        // Use the constructor of UEdge that takes three parameters (vertex1, vertex2, weight)
    	return () -> {
            throw new UnsupportedOperationException("Edge supplier not implemented");
        };
    }

    /**
     * Not implemented.
     * @return a supplier for creating new vertices
     * Time complexity: O(1).
     */
    @Override
    public Supplier<V> getVertexSupplier() {
        return () -> {
            throw new UnsupportedOperationException("Vertex supplier not implemented");
        };
    }

    /**
     * Adds an edge connecting the specified vertices, creating and returning a new edge.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the newly created edge, or null if the edge could not be added
     * Time complexity: O(1).
     */
    @Override
    public Edge<V, W> addEdge(V vertex1, V vertex2) {
    	Objects.requireNonNull(vertex1, "vertex1 cannot be null");
    	Objects.requireNonNull(vertex2, "vertex2 cannot be null");
        Edge<V, W> edge = getEdgeSupplier().get();
        edge.setVertex1(vertex1);
        edge.setVertex2(vertex2);
        return addEdge(vertex1, vertex2, edge) ? edge : null;
    }

    /**
     * Adds the specified edge to the graph, connecting the specified vertices.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param edge the edge to be added
     * @return true if the edge was successfully added, false otherwise
     * Time complexity: O(1).
     */
    @Override
    public boolean addEdge(V vertex1, V vertex2, Edge<V, W> edge) {
    	Objects.requireNonNull(vertex1, "vertex1 cannot be null");
    	Objects.requireNonNull(vertex2, "vertex2 cannot be null");
    	Objects.requireNonNull(edge,"edge cannot be null");
    	try {
	    	addVertex(vertex1);  
	    	addVertex(vertex2);
	    	adjacencyLists.get(vertex1).add(edge);
	    	if (!vertex1.equals(vertex2)) {
	    	    adjacencyLists.get(vertex2).add(edge);
	    	}
	    	return true;
    	}catch(Exception e) {
    		return false;
    	}
    }

    /**
     * @return the newly added vertex
     */
    @Override
    public V addVertex() {
        return getVertexSupplier().get();
    }

    /**
     * Adds the specified vertex to the graph.
     * 
     * @param vertex the vertex to be added
     * @return true if the vertex was successfully added, false otherwise
     * Time complexity: O(1).
     */
    @Override
    public boolean addVertex(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        if (!adjacencyLists.containsKey(vertex)) {
            adjacencyLists.put(vertex, new HashSet<>());
            return true;
        }
        if(adjacencyLists.containsKey(vertex)) {
        	return true;
        }
        return false;
    }

    /**
     * Checks if the graph contains the specified edge.
     * 
     * @param edge the edge to check for
     * @return true if the graph contains the specified edge, false otherwise
     * Time complexity: O(1)
     */
    @Override
    public boolean containsEdge(Edge<V, W> edge) {
    	Objects.requireNonNull(edge,"Edge cannot be null");
        V v1 = edge.getVertex1();
        V v2 = edge.getVertex2();
        return adjacencyLists.getOrDefault(v1, new HashSet<>()).contains(edge) ||
               adjacencyLists.getOrDefault(v2, new HashSet<>()).contains(edge);
    }

    /**
     * Checks if the graph contains an edge connecting the specified vertices.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if the graph contains an edge connecting the specified vertices, false otherwise
     * Time complexity: O(deg(vertex1)), deg(vertex1) being the number of edges connected to vertex1.
     */
    @Override
    public boolean containsEdge(V vertex1, V vertex2) {
        Objects.requireNonNull(vertex1, "vertex1 cannot be null");
        Objects.requireNonNull(vertex2, "vertex2 cannot be null");
        
        Set<Edge<V, W>> edges = adjacencyLists.get(vertex1);
        if (edges == null) {
            return false;
        }
        
        // Optimized search without streams
        for (Edge<V, W> edge : edges) {
            if (edge.getVertex1().equals(vertex2) || edge.getVertex2().equals(vertex2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the graph contains the specified vertex.
     * 
     * @param vertex the vertex to check for
     * @return true if the graph contains the specified vertex, false otherwise
     * Time complexity: O(1).
     */
    @Override
    public boolean containsVertex(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        return adjacencyLists.containsKey(vertex);
    }

    /**
     * Returns the degree of the specified vertex, which is the total number of edges incident to the vertex.
     * 
     * @param vertex the vertex whose degree is to be calculated
     * @return the degree of the specified vertex
     * Time complexity: O(1).
     */
    @Override
    public int degreeOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        Set<Edge<V, W>> edges = adjacencyLists.get(vertex);
        return edges != null ? edges.size() : 0;
    }

    /**
     * Returns the source vertex of the specified edge.
     * 
     * @param edge the edge whose source vertex is to be returned
     * @return the source vertex of the specified edge
     * Time complexity: O(1).
     */
    @Override
    public V getEdgeSource(Edge<V, W> edge) {
    	Objects.requireNonNull(edge, "Edge cannot be null");
        return edge.getVertex1();
    }

    /**
     * Returns the target vertex of the specified edge.
     * 
     * @param edge the edge whose target vertex is to be returned
     * @return the target vertex of the specified edge
     * Time complexity: O(1).
     */
    @Override
    public V getEdgeTarget(Edge<V, W> edge) {
    	Objects.requireNonNull(edge, "Edge cannot be null");
        return edge.getVertex2();
    }

    /**
     * Returns the weight of the specified edge. If weight is null we return 0 and 1 if the weight cannot be cast to a Number type.
     * 
     * @param edge the edge whose weight is to be returned
     * @return the weight of the specified edge
     * Time complexity: O(1).
     */
    @Override
    public double getEdgeWeight(Edge<V, W> edge) {
    	Objects.requireNonNull(edge, "Edge cannot be null");
        W weight = edge.getWeight();
        if (weight == null) {
            return 0.0;
        }
        if (weight instanceof Number) {
            return ((Number)weight).doubleValue();
        }
        return 1.0;
    }

    /**
     * Returns the type of this graph.
     * 
     * @return the type of this graph
     * Time complexity: O(1).
     */
    @Override
    public GraphType getType() {
    	 return new DefaultGraphType.Builder().build().asWeighted();
    }

    /**
     * Returns the in-degree of the specified vertex, which is the number of incoming edges to the vertex.
     * 
     * @param vertex the vertex whose in-degree is to be calculated
     * @return the in-degree of the specified vertex
     * Time complexity: O(1).
     */
    @Override
    public int inDegreeOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        return degreeOf(vertex);
    }

    /**
     * Returns the set of edges incoming to the specified vertex.
     * 
     * @param vertex the vertex whose incoming edges are to be returned
     * @return the set of edges incoming to the specified vertex
     * Time complexity: O(E), where E is the number of edges.
     */
    @Override
    public Set<Edge<V, W>> incomingEdgesOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        return edgesOf(vertex);
    }

    /**
     * Returns the out-degree of the specified vertex, which is the number of outgoing edges from the vertex.
     * 
     * @param vertex the vertex whose out-degree is to be calculated
     * @return the out-degree of the specified vertex
     * Time complexity: O(1).
     */
    @Override
    public int outDegreeOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
    	return degreeOf(vertex);
    }

    /**
     * Returns the set of edges outgoing from the specified vertex.
     * 
     * @param vertex the vertex whose outgoing edges are to be returned
     * @return the set of edges outgoing from the specified vertex
     * Time complexity: O(E), where E is the number of edges.
     */
    @Override
    public Set<Edge<V, W>> outgoingEdgesOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        return edgesOf(vertex);
    }

    /**
     * Removes all specified edges from the graph.
     * 
     * @param edges the edges to be removed
     * @return true if any edges were successfully removed, false otherwise
     * Time complexity: O(E), where E is the number of edges.
     */
    @Override
    public boolean removeAllEdges(Collection<? extends Edge<V, W>> edges) {
    	Objects.requireNonNull(edges, "Edges cannot be null");
        boolean modified = false;
        for (Set<Edge<V, W>> vertexEdges : adjacencyLists.values()) {
            modified |= vertexEdges.removeAll(edges);
        }
        return modified;
    }

    /**
     * Removes all edges connecting the specified vertices.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the set of removed edges
     * Time complexity: O(deg(vertex1)), where deg(vertex1) is the number of edges connected to vertex1.
     */
    @Override
    public Set<Edge<V, W>> removeAllEdges(V vertex1, V vertex2) {
    	Objects.requireNonNull(vertex1, "vertex1 cannot be null");
        Objects.requireNonNull(vertex2, "vertex2 cannot be null");
        Set<Edge<V, W>> edges = adjacencyLists.get(vertex1);
        if (edges != null) {
            Set<Edge<V, W>> removedEdges = edges.stream()
                    .filter(edge -> edge.getVertex1().equals(vertex2) || edge.getVertex2().equals(vertex2))
                    .collect(Collectors.toSet());
            edges.removeAll(removedEdges);
            return removedEdges;
        }
        return new HashSet<>();
    }

    /**
     * Removes all specified vertices and their incident edges from the graph.
     * 
     * @param vertices the vertices to be removed
     * @return true if any vertices were successfully removed, false otherwise
     * Time complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     */
    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices) {
    	Objects.requireNonNull(vertices, "Vertices cannot be null");
        boolean modified = false;
        for (V vertex : vertices) {
            modified |= removeVertex(vertex);
        }
        return modified;
    }

    /**
     * Removes the edge connecting the specified vertices.
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the removed edge, or null if no such edge existed
     * Time complexity: O(deg(vertex1)), where deg(vertex1) is the number of edges connected to vertex1.
     */
    @Override
    public Edge<V, W> removeEdge(V vertex1, V vertex2) {
        Objects.requireNonNull(vertex1, "Vertex1 cannot be null");
        Objects.requireNonNull(vertex2, "Vertex2 cannot be null");
        
        Set<Edge<V, W>> edges1 = adjacencyLists.get(vertex1);
        if (edges1 == null) return null;
        
        Edge<V, W> edgeToRemove = null;
        for (Edge<V, W> edge : edges1) {
            boolean matches =
                (edge.getVertex1().equals(vertex1) && edge.getVertex2().equals(vertex2)) ||
                (edge.getVertex1().equals(vertex2) && edge.getVertex2().equals(vertex1));
            if (matches) {
                edgeToRemove = edge;
                break;
            }
        }

        if (edgeToRemove != null) {
            edges1.remove(edgeToRemove);
            Set<Edge<V, W>> edges2 = adjacencyLists.get(vertex2);
            if (edges2 != null && vertex1 != vertex2) {
                edges2.remove(edgeToRemove);
            }
        }
        
        return edgeToRemove;
    }

    /**
     * Sets edge weight
     * 
     * @param edge to set weight to
     * @param weight to be assigned
     * Time complexity: O(1)
     */
    public void setEdgeWeight(Edge<V, W> edge, W weight) {
        Objects.requireNonNull(edge, "Edge cannot be null");
        Objects.requireNonNull(weight, "Weight cannot be null");
        edge.setWeight(weight);
    }
    
    /**
     Sets weight if W is of type Double, Integer, Float, Long, Short, Byte, otherwise throws exception
     */
    @Override
    public void setEdgeWeight(Edge<V, W> edge, double weight) {
    	 Objects.requireNonNull(edge, "Edge cannot be null");
    	 W newWeight;
    	 if (edge.getWeight() instanceof Double) {
 	        newWeight = (W) Double.valueOf(weight);
 	     } else if (edge.getWeight() instanceof Integer) {
 	        newWeight = (W) Integer.valueOf((int) Math.round(weight));
 	     } else if (edge.getWeight() instanceof Float) {
 	        newWeight = (W) Float.valueOf((float) weight);
 	     } else if (edge.getWeight() instanceof Long) {
 	        newWeight = (W) Long.valueOf((long) Math.round(weight));
 	     } else if (edge.getWeight() instanceof Short) {
 	        newWeight = (W) Short.valueOf((short) Math.round(weight));
 	     } else if (edge.getWeight() instanceof Byte) {
 	        newWeight = (W) Byte.valueOf((byte) Math.round(weight));
 	    } else {
 	            throw new UnsupportedOperationException("Cannot convert double to " + edge.getWeight().getClass().getSimpleName());
 	    }
 	    
 	    edge.setWeight(newWeight);	   
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph:\n");
        
        for (Map.Entry<V, HashSet<Edge<V, W>>> entry : adjacencyLists.entrySet()) {
            V vertex = entry.getKey();
            Set<Edge<V, W>> edges = entry.getValue();
            
            sb.append("Vertex ").append(vertex).append(": ");
            
            if (edges.isEmpty()) {
                sb.append("No edges");
            } else {
                for (Edge<V, W> edge : edges) {
                    sb.append(edge).append(", ");
                }
                // Remove the last comma and space
                sb.setLength(sb.length() - 2);
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Compares this undirected graph to the specified object for equality.
     * 
     * @param obj the object to compare with
     * @return true if the graphs are equal, false otherwise
     * Time complexity: O(V + E), where V is the number of vertices and E is the number of edges
     */
    @Override
    public boolean equals(Object obj) {
    	Objects.requireNonNull(obj, "Object cannot be null");
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Graph<?, ?> other = (Graph<?, ?>) obj;
 
        if (!vertexSet().equals(other.vertexSet())) {
            return false;
        }
        
        for (V vertex : adjacencyLists.keySet()) {
            if (!adjacencyLists.get(vertex).equals(other.adjacencyLists.get(vertex))) {
                return false;
            }
        }
        
        return true;
    }
}

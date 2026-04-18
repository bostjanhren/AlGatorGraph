package algatorgraph;
import java.io.Serializable;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;
import org.jgrapht.graph.DefaultGraphType;

/**
 * Represents a directed graph using an adjacency list representation. The graph supports generic vertex 
 * and edge types, with edges having a generic weight type. The adjacency lists are stored in a HashMaps.
 *
 *<p>Example usage:</p>
 * 
 * <pre>{@code
 * // Create a graph with String vertices and Integer weights
 * DGraph<String, Integer> cityGraph = new DGraph<>();
 * 
 * // Add vertices (cities)
 * cityGraph.addVertex("New York");
 * cityGraph.addVertex("Boston");
 * 
 * // Add a weighted edge (distance between cities)
 * cityGraph.addEdge("New York", "Boston", new DEdge<>("New York", "Boston", 215));
 * 
 * // Get all edges
 * Set<DEdge<String, Integer>> allRoutes = cityGraph.edgeSet();
 * }</pre>
 *
 * @param <V> the type of vertices
 * @param <W> the type of weights on edges
 */
public class DGraph<V, W> implements Graph<V, DEdge<V, W>>, Serializable {
    /** Adjacency lists mapping each vertex to its outgoing edges */
	private Map<V, Set<DEdge<V, W>>> adjacencyLists;
	
	/**
	 * Constructor creates a new empty HashMap()
	 */
    public DGraph() {
        this.adjacencyLists = new HashMap<>();
    }

    /**
     * Returns the set of all edges in the graph.
     * 
     * @return the set of all edges in the graph
     * Time complexity: O(E), where E is the number of edges.
     */
    @Override
    public Set<DEdge<V, W>> edgeSet() {
        Set<DEdge<V, W>> allEdges = new HashSet<>();
        for (Set<DEdge<V, W>> edges : adjacencyLists.values()) {
            allEdges.addAll(edges);
        }
        return allEdges;
    }


    /**
     * Returns the set of edges connected to the specified vertex.
     * 
     * @param vertex the vertex whose edges are to be returned
     * @return the set of edges connected to the specified vertex
     * Time complexity: O(1) for lookup, O(deg(v)) for copying, where deg(v) is the degree of the vertex
     */
    @Override
    public Set<DEdge<V, W>> edgesOf(V vertex) {
        Objects.requireNonNull(vertex, "Vertex cannot be null");
        return new HashSet<>(adjacencyLists.getOrDefault(vertex, new HashSet<>()));
    }

    /**
     * Removes the specified edge from the graph.
     * 
     * @param edge the edge to be removed
     * @return true if the edge was successfully removed, false otherwise
     * Time complexity: O(1) average case for HashSet removal
     */
    @Override
    public boolean removeEdge(DEdge<V, W> edge) {
        Objects.requireNonNull(edge, "Edge cannot be null");
        V source = edge.getSource();
        return adjacencyLists.containsKey(source) && adjacencyLists.get(source).remove(edge);
    }

    /**
     * Removes the specified vertex and all its edges from the graph.
     * 
     * @param vertex the vertex to be removed
     * @return true if the vertex was successfully removed, false otherwise
     * Time complexity: O(E), where E is the number of edges.
     * Explanation: This method removes the vertex and iterates through all edges to remove edges pointing to the vertex.
     */
    @Override
    public boolean removeVertex(V vertex) {
        Objects.requireNonNull(vertex, "Vertex cannot be null");
        if (adjacencyLists.containsKey(vertex)) {
            adjacencyLists.remove(vertex);
            adjacencyLists.values().forEach(edges -> 
                edges.removeIf(edge -> edge.getTarget().equals(vertex)));
            return true;
        }
        return false;
    }

    /**
     * Returns the set of all vertices in the graph.
     * 
     * @return the set of all vertices in the graph
     * Time complexity: O(V), where V is the number of vertices.
     * Explanation: This method creates a set of keys from the adjacency list map.
     */
    @Override
    public Set<V> vertexSet() {
        return new HashSet<>(adjacencyLists.keySet());
    }

    /**
     * Returns the edge connecting the specified source and target vertices.
     * 
     * @param sourceVertex the source vertex
     * @param targetVertex the target vertex
     * @return the edge connecting the specified vertices, or null if no such edge exists
     * Time complexity: O(deg(sourceVertex)), where deg is the out-degree of the source vertex
     * Explanation: This method searches through the list of edges for the source vertex.
     */
    @Override
    public DEdge<V, W> getEdge(V sourceVertex, V targetVertex) {
        Objects.requireNonNull(sourceVertex, "Source vertex cannot be null");
        Objects.requireNonNull(targetVertex, "Target vertex cannot be null");
        
        Set<DEdge<V, W>> edges = adjacencyLists.get(sourceVertex);
        if (edges != null) {
            for (DEdge<V, W> edge : edges) {
                if (edge.getTarget().equals(targetVertex)) {
                    return edge;
                }
            }
        }
        return null;
    }


    /**
     * Returns all edges connecting the specified source and target vertices.
     * 
     * @param sourceVertex the source vertex
     * @param targetVertex the target vertex
     * @return a set of all edges connecting the specified vertices
     * Time complexity: O(deg(sourceVertex)), where deg is the out-degree of the source vertex.
     * Explanation: This method filters the list of edges for the source vertex to find all edges connecting to the target vertex.
     */
    @Override
    public Set<DEdge<V, W>> getAllEdges(V sourceVertex, V targetVertex) {
        Objects.requireNonNull(sourceVertex, "Source vertex cannot be null");
        Objects.requireNonNull(targetVertex, "Target vertex cannot be null");
        
        Set<DEdge<V, W>> edges = adjacencyLists.get(sourceVertex);
        if (edges != null) {
            return edges.stream()
                    .filter(edge -> edge.getTarget().equals(targetVertex))
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }


    /**
     * Not implemented.
     * 
     * @return a supplier for creating new edges
     * Time complexity: O(1).
     * Explanation: This method returns a supplier that creates a new edge with null values.
     */
    @Override
    public Supplier<DEdge<V, W>> getEdgeSupplier() {
        return () -> {
            throw new UnsupportedOperationException("Edge supplier not implemented");
        };
    }

    /**
     * Not implemented.
     * 
     * @return a supplier for creating new vertices
     * Time complexity: O(1).
     * Explanation: This method throws an UnsupportedOperationException.
     */
    @Override
    public Supplier<V> getVertexSupplier() {
        return () -> {
            throw new UnsupportedOperationException("Vertex supplier not implemented");
        };
    }

    /**
     * Adds an edge connecting the specified source and target vertices, creating and returning a new edge.
     * 
     * @param sourceVertex the source vertex
     * @param targetVertex the target vertex
     * @return the newly created edge, or null if the edge could not be added
     * Time complexity: O(1).
     * Explanation: This method creates a new edge and adds it to the adjacency list for the source vertex.
     */
    @Override
    public DEdge<V, W> addEdge(V sourceVertex, V targetVertex) {
        DEdge<V, W> edge = new DEdge(sourceVertex,targetVertex);
        return addEdge(sourceVertex, targetVertex, edge) ? edge : null;
    }

    /**
     * Adds the specified edge to the graph, connecting the specified source and target vertices.
     * 
     * @param sourceVertex the source vertex
     * @param targetVertex the target vertex
     * @param edge the edge to be added
     * @return true if the edge was successfully added, false otherwise
     * Time complexity: O(1).
     */
    @Override
    public boolean addEdge(V sourceVertex, V targetVertex, DEdge<V, W> edge) {
        Objects.requireNonNull(sourceVertex, "Source vertex cannot be null");
        Objects.requireNonNull(targetVertex, "Target vertex cannot be null");
        Objects.requireNonNull(edge, "Edge cannot be null");
        
        if (!edge.getSource().equals(sourceVertex) || !edge.getTarget().equals(targetVertex)) {
            throw new IllegalArgumentException("Edge endpoints don't match specified vertices");
        }
        
        addVertex(sourceVertex);
        addVertex(targetVertex);
        return adjacencyLists.get(sourceVertex).add(edge);
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
     * Explanation: This method adds a new vertex to the adjacency list.
     */
    @Override
    public boolean addVertex(V vertex) {
        if (!adjacencyLists.containsKey(vertex)) {
            adjacencyLists.put(vertex, new HashSet<>());
            return true;
        }
        return false;
    }

    /**
     * Checks if the graph contains the specified edge.
     * 
     * @param edge the edge to check for
     * @return true if the graph contains the specified edge, false otherwise
     * Time complexity: O(1) average case for HashSet lookup
     */
    @Override
    public boolean containsEdge(DEdge<V, W> edge) {
    	Objects.requireNonNull(edge, "Edge cannot be null");
        V source = edge.getSource();
        return adjacencyLists.containsKey(source) && 
               adjacencyLists.get(source).contains(edge);
    }

    /**
     * Checks if the graph contains an edge connecting the specified vertices.
     * 
     * @param sourceVertex the source vertex
     * @param targetVertex the target vertex
     * @return true if the graph contains an edge connecting the specified vertices, false otherwise
     * Time complexity: O(deg(sourceVertex)), where deg is the out-degree of the source vertex
     */
    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {
        Objects.requireNonNull(sourceVertex, "Source vertex cannot be null");
        Objects.requireNonNull(targetVertex, "Target vertex cannot be null");
        return getEdge(sourceVertex, targetVertex) != null;
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
        return adjacencyLists.containsKey(vertex);
    }

    /**
     * Returns the degree of the specified vertex, which is the total number of edges incident to the vertex.
     * 
     * @param vertex the vertex whose degree is to be calculated
     * @return the degree of the specified vertex
     * Time complexity: O(E) + O(1) = O(E).
     * Explanation: This method calls and sums the numbers of incoming and outgoing edges.
     */
    @Override
    public int degreeOf(V vertex) {
        Objects.requireNonNull(vertex, "Vertex cannot be null");
        return inDegreeOf(vertex) + outDegreeOf(vertex);
    }

    /**
     * Returns the source vertex of the specified edge.
     * 
     * @param edge the edge whose source vertex is to be returned
     * @return the source vertex of the specified edge
     * Time complexity: O(1).
     */
    @Override
    public V getEdgeSource(DEdge<V, W> edge) {
    	Objects.requireNonNull(edge, "Edge cannot be null");
        return edge.getSource();
    }

    /**
     * Returns the target vertex of the specified edge.
     * 
     * @param edge the edge whose target vertex is to be returned
     * @return the target vertex of the specified edge
     * Time complexity: O(1).
     */
    @Override
    public V getEdgeTarget(DEdge<V, W> edge) {
    	Objects.requireNonNull(edge, "Edge cannot be null");
        return edge.getTarget();
    }

    /**
     * Returns the weight of the specified edge. If weight is null we return 0 and 1 if the weight cannot be cast to a Number type.
     * 
     * @param edge the edge whose weight is to be returned
     * @return the weight of the specified edge
     * Time complexity: O(1).
     */
    @Override
    public double getEdgeWeight(DEdge<V, W> edge) {
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
     * Returns the type of this graph. This implementation returns null.
     * 
     * @return the type of this graph
     * Time complexity: O(1).
     * Explanation: This method returns null as a placeholder for the graph type.
     */
    @Override
    public GraphType getType() {
    	return new DefaultGraphType.Builder().build().asWeighted().asDirected();
    }

    /**
     * Returns the in-degree of the specified vertex, which is the number of incoming edges to the vertex.
     * 
     * @param vertex the vertex whose in-degree is to be calculated
     * @return the in-degree of the specified vertex
     * Time complexity: O(E), where E is the number of edges.
     * Explanation: This method iterates through all edges to count the ones targeting the vertex.
     */
    @Override
    public int inDegreeOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        return incomingEdgesOf(vertex).size();
    }

    /**
     * Returns the set of edges incoming to the specified vertex.
     * 
     * @param vertex the vertex whose incoming edges are to be returned
     * @return the set of edges incoming to the specified vertex
     * Time complexity: O(E), where E is the number of edges.
     * Explanation: This method iterates through all edges to find those targeting the vertex.
     */
    @Override
    public Set<DEdge<V, W>> incomingEdgesOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        Set<DEdge<V, W>> incomingEdges = new HashSet<>();
        for (Set<DEdge<V, W>> edges : adjacencyLists.values()) {
            for (DEdge<V, W> edge : edges) {
                if (edge.getTarget().equals(vertex)) {
                    incomingEdges.add(edge);
                }
            }
        }
        return incomingEdges;
    }

    /**
     * Returns the out-degree of the specified vertex, which is the number of outgoing edges from the vertex.
     * 
     * @param vertex the vertex whose out-degree is to be calculated
     * @return the out-degree of the specified vertex
     * Time complexity: O(1).
     * Explanation: This method returns the size of the adjacency list for the vertex.
     */
    @Override
    public int outDegreeOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        Set<DEdge<V, W>> edges = adjacencyLists.get(vertex);
        return edges != null ? edges.size() : 0;
    }

    /**
     * Returns the set of edges outgoing from the specified vertex.
     * 
     * @param vertex the vertex whose outgoing edges are to be returned
     * @return the set of edges outgoing from the specified vertex
     * Time complexity: O(1) for lookup, O(deg(v)) for copying, where deg(v) is the out-degree of the vertex
     * Explanation: This method creates a set of edges from the adjacency list of the vertex.
     */
    @Override
    public Set<DEdge<V, W>> outgoingEdgesOf(V vertex) {
    	Objects.requireNonNull(vertex, "Vertex cannot be null");
        Set<DEdge<V, W>> edges = adjacencyLists.get(vertex);
        return edges != null ? new HashSet<>(edges) : new HashSet<>();
    }

    /**
     * Removes all specified edges from the graph.
     * 
     * @param edges the edges to be removed
     * @return true if any edges were successfully removed, false otherwise
     * Time complexity: O(E), where E is the number of edges.
     * Explanation: This method iterates through all adjacency lists to remove the specified edges.
     */
    @Override
    public boolean removeAllEdges(Collection<? extends DEdge<V, W>> edges) {
    	Objects.requireNonNull(edges, "Edges cannot be null");
        boolean modified = false;
        for (Set<DEdge<V, W>> vertexEdges : adjacencyLists.values()) {
            modified |= vertexEdges.removeAll(edges);
        }
        return modified;
    }

    /**
     * Removes all edges connecting the specified source and target vertices.
     * 
     * @param sourceVertex the source vertex
     * @param targetVertex the target vertex
     * @return the set of removed edges
     * Time complexity: O(deg(sourceVertex)), where deg(sourceVertex) is the out-degree of the source vertex.
     * Explanation: This method filters the edges of the source vertex to remove edges connecting to the target vertex.
     */
    @Override
    public Set<DEdge<V, W>> removeAllEdges(V sourceVertex, V targetVertex) {
        Objects.requireNonNull(sourceVertex, "Source vertex cannot be null");
        Objects.requireNonNull(targetVertex, "Target vertex cannot be null");
        
        Set<DEdge<V, W>> edges = adjacencyLists.get(sourceVertex);
        if (edges != null) {
            Set<DEdge<V, W>> removedEdges = edges.stream()
                    .filter(edge -> edge.getTarget().equals(targetVertex))
                    .collect(Collectors.toSet());
            edges.removeAll(removedEdges);
            return removedEdges;
        }
        return Collections.emptySet();
    }

    /**
     * Removes all specified vertices and their incident edges from the graph.
     * 
     * @param vertices the vertices to be removed
     * @return true if any vertices were successfully removed, false otherwise
     * Time complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * Explanation: This method removes each specified vertex and all edges connected to it.
     */
    @Override
    public boolean removeAllVertices(Collection<? extends V> vertices) {
        boolean modified = false;
        for (V vertex : vertices) {
            modified |= removeVertex(vertex);
        }
        return modified;
    }

    /**
     * Removes the edge connecting the specified source and target vertices.
     * 
     * @param sourceVertex the source vertex
     * @param targetVertex the target vertex
     * @return the removed edge, or null if no such edge existed
     * Time complexity: O(deg(sourceVertex)), where deg is the out-degree of the source vertex.
     * Explanation: This method searches through the list of edges for the source vertex to find and remove the edge to the target vertex.
     */
    @Override
    public DEdge<V, W> removeEdge(V sourceVertex, V targetVertex) {
        Objects.requireNonNull(sourceVertex, "Source vertex cannot be null");
        Objects.requireNonNull(targetVertex, "Target vertex cannot be null");
        
        Set<DEdge<V, W>> edges = adjacencyLists.get(sourceVertex);
        if (edges != null) {
            Iterator<DEdge<V, W>> iterator = edges.iterator();
            while (iterator.hasNext()) {
                DEdge<V, W> edge = iterator.next();
                if (edge.getTarget().equals(targetVertex)) {
                    iterator.remove();
                    return edge;
                }
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the graph.
     * 
     * @return a string representation of the graph
     * Time complexity: O(V + E), where V is the number of vertices and E is the number of edges
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DGraph:\n");
        
        for (Map.Entry<V, Set<DEdge<V, W>>> entry : adjacencyLists.entrySet()) {
            V vertex = entry.getKey();
            Set<DEdge<V, W>> edges = entry.getValue();
            
            sb.append("Vertex ").append(vertex).append(": ");
            
            if (edges.isEmpty()) {
                sb.append("No edges");
            } else {
                for (DEdge<V, W> edge : edges) {
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
     * Sets the weight of the specified edge to the given double value.
     * 
     * @param edge the edge whose weight is to be set
     * @param weight the new weight value
     * @throws UnsupportedOperationException if the weight type is not supported
     * Time complexity: O(1)
     */
    @Override
    public void setEdgeWeight(DEdge<V, W> edge, double weight) {
        Objects.requireNonNull(edge, "Edge cannot be null");
        W newWeight;

        if (edge.getWeight() instanceof Double) {
            newWeight = (W) Double.valueOf(weight);
        } else if (edge.getWeight() instanceof Integer) {
            newWeight = (W) Integer.valueOf((int) weight);
        } else if (edge.getWeight() instanceof Float) {
            newWeight = (W) Float.valueOf((float) weight);
        } else if (edge.getWeight() instanceof Long) {
            newWeight = (W) Long.valueOf((long) weight);
        } else if (edge.getWeight() instanceof Short) {
            newWeight = (W) Short.valueOf((short) weight);
        } else if (edge.getWeight() instanceof Byte) {
            newWeight = (W) Byte.valueOf((byte) weight);
        } else {
	            throw new UnsupportedOperationException("Cannot convert double to " + edge.getWeight().getClass().getSimpleName());
	    }
        edge.setWeight(newWeight);
    }
    
    /**
     * Sets edge weight
     * 
     * @param edge to set weight to
     * @param weight to be assigned
     * Time complexity: O(1)
     */
    public void setEdgeWeight(DEdge<V, W> edge, W weight) {
        Objects.requireNonNull(edge, "Edge cannot be null");
        Objects.requireNonNull(weight, "Weight cannot be null");
        edge.setWeight(weight);
    }
	
    /**
     * Compares this graph to the specified object for equality.
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
        
        DGraph<?, ?> other = (DGraph<?, ?>) obj;
        
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

package algatorgraph;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an undirected edge in a graph data structure with generic identifiers for the two vertices.
 * This class provides methods to get and set the edge's properties, including its vertices and weight.
 * The order of the vertices does not affect equality, since the edge is undirected.
 * 
 * <p>
 * The {@code equals}, {@code hashCode}, and {@code toString} methods have been overridden to ensure
 * that two edges are considered equal if they connect the same vertices, regardless of order.
 * </p>
 *
 * <pre>
 * Usage example:
 * 
 * UEdge&lt;String, Integer&gt; edge1 = new UEdge&lt;&gt;("A", "B", 5);
 * UEdge&lt;Integer, Integer&gt; edge2 = new UEdge&lt;&gt;(1, 2, 5);
 * System.out.println("Vertex 1: " + edge1.getVertex1());
 * System.out.println("Vertex 2: " + edge1.getVertex2());
 * System.out.println("Weight: " + edge1.getWeight());
 * </pre>
 */
public class Edge<V, W> implements Serializable {
	/** Vertex1 of the undirected edge */
    private V vertex1;  
    /** Vertex2 of the undirected edge */
    private V vertex2;  
    /** Weight of the undirected edge */
    private W weight;   

    /**
     * Constructs a new undirected edge with the specified vertices and weight.
     * 
     * @param vertex1  the identifier of one vertex of the edge
     * @param vertex2  the identifier of the other vertex of the edge
     * @param weight   the weight of the edge
     */
    public Edge(V vertex1, V vertex2, W weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    /**
     * Constructs a new undirected edge with the specified vertices and no weight.
     * 
     * @param vertex1  the identifier of one vertex of the edge
     * @param vertex2  the identifier of the other vertex of the edge
     */
    public Edge(V vertex1, V vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = null;
    }

    /**
     * Returns the identifier of the first vertex of this edge.
     * 
     * @return the first vertex ID
     */
    public V getVertex1() {
        return vertex1;
    }

    /**
     * Returns the identifier of the second vertex of this edge.
     * 
     * @return the second vertex ID
     */
    public V getVertex2() {
        return vertex2;
    }

    /**
     * Returns the weight of this edge.
     * 
     * @return the weight of the edge
     */
    public W getWeight() {
        return weight;
    }

    /**
     * Sets the identifier of the first vertex of this edge.
     *
     * @param v1 the new identifier for the first vertex
     */
    public void setVertex1(V v1) {
        this.vertex1 = v1;
    }

    /**
     * Sets the identifier of the second vertex of this edge.
     *
     * @param v2 the new identifier for the second vertex
     */
    public void setVertex2(V v2) {
        this.vertex2 = v2;
    }

    /**
     * Sets the weight of this edge.
     *
     * @param weight the new weight for the edge
     */
    public void setWeight(W weight) {
        this.weight = weight;
    }

    /**
     * Compares this edge to the specified object (Edge) for equality.
     * Two edges are considered equal if they connect the same vertices (in any order) 
     * and have the same weight (including null weights).
     * 
     * @param o the object to compare with this edge
     * @return {@code true} if the given object represents an edge equivalent to this one,
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) { // enak graf je če ima iste povezave in vozlišča, izločanje vozlišč
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?, ?> uEdge = (Edge<?, ?>) o;
        return ((Objects.equals(this.vertex1, uEdge.vertex1) && Objects.equals(this.vertex2, uEdge.vertex2)) ||
                (Objects.equals(this.vertex1, uEdge.vertex2) && Objects.equals(this.vertex2, uEdge.vertex1))) &&
                Objects.equals(this.weight, uEdge.weight);
    }

    /**
     * Returns a hash code value for this edge. The hash code is computed based on 
     * the sum of the hash codes of the two vertices (order-independent) and the weight.
     * This ensures consistency with the {@link #equals(Object)} method.
     * 
     * @return a hash code value for this edge
     */
    @Override
    public int hashCode() {
    	int hash1 = vertex1 != null ? vertex1.hashCode() : 0;
        int hash2 = vertex2 != null ? vertex2.hashCode() : 0;
    	int combinedHash = hash1 + hash2;

        // Combine with the hash of the weight
        return Objects.hash(combinedHash, weight);
    }
    
    /**
     * Constructs a string representing this edge
     * 
     * @return the string value for this edge
     */
    @Override
    public String toString() {
    	return this.vertex1 + "," + this.vertex2 + " w:" +this.weight; 
    }
}

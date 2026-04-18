package algatorgraph;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an edge in a graph data structure with generic identifiers for the source and target vertices.
 * This class provides methods to get and set the edge's properties, including its source vertex,
 * target vertex, and weight.
 * 
 * <p>
 * The {@code equals}, {@code hashCode}, {@code toString} methods have been overridden.
 * </p>
 *
 * <pre>
 * Usage example:
 * 
 * DEdge&lt;Integer, Double&gt; edge = new DEdge&lt;&gt;(0, 2, 5.0);
 * System.out.println("Source vertex: " + edge.getSource());
 * System.out.println("Target vertex: " + edge.getTarget());
 * System.out.println("Weight: " + edge.getWeight());
 * </pre>
 */
public class DEdge<V, W> implements Serializable {
	/** Source vertex of the edge */
    private V source;   
    /** Target vertex of the edge */
    private V target;   
    /** Weight of the edge */
    private W weight;   

    /**
     * Constructs a new edge with the specified source vertex and target vertex.
     * 
     * @param source the identifier of the source vertex of the edge 
     * @param target the identifier of the target vertex of the edge
     */
    public DEdge(V source, V target) {
        this.setSource(source);
        this.setTarget(target);
        this.weight = null;
    }

    /**
     * Constructs a new edge with the specified source vertex, target vertex, and weight.
     * 
     * @param source the identifier of the source vertex of the edge
     * @param target the identifier of the target vertex of the edge
     * @param weight the weight of the edge
     */
    public DEdge(V source, V target, W weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    /**
     * Returns the identifier of the source vertex of this edge.
     * 
     * @return the source vertex ID
     */
    public V getSource() {
        return source;
    }

    /**
     * Returns the identifier of the target vertex of this edge.
     * 
     * @return the target vertex ID
     */
    public V getTarget() {
        return target;
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
     * Sets the source vertex identifier of this edge.
     * 
     * @param source the new identifier for the source vertex
     */
    public void setSource(V source) {
        this.source = source;
    }

    /**
     * Sets the target vertex identifier of this edge.
     * 
     * @param target the new identifier for the target vertex
     */
    public void setTarget(V target) {
        this.target = target;
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
     * Compares this edge to the specified object (DEdge) for equality.
     * Two edges are considered equal if they connect the same source and target 
     * and have the same weight (including null weights).
     * 
     * @param o the object to compare with this edge
     * @return {@code true} if the given object represents an edge equivalent to this one,
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DEdge<?, ?> dEdge = (DEdge<?, ?>) o;
        return Objects.equals(source, dEdge.source) &&
               Objects.equals(target, dEdge.target) &&
               Objects.equals(weight, dEdge.weight);
    }

    /**
     * Returns a hash code value for this edge. The hash code is computed based on 
     * the two vertices and weight.
     * This ensures consistency with the {@link #equals(Object)} method.
     * 
     * @return a hash code value for this edge
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, target, weight);
    }
    
    /**
     * Constructs a string representing this edge
     * 
     * @return the string value for this edge
     */
    @Override
    public String toString() {
        return "DEdge{" +
                "source=" + source +
                ", target=" + target +
                ", weight=" + weight +
                '}';
    }
}

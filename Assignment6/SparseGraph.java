import java.util.Iterator;

/**
 * Implementation of Graph interface.
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

    private final class VertexObj<V> implements Vertex<V> {
        V data;
        SentinelList<EdgeObj<E>> out;
        SentinelList<EdgeObj<E>> in;
        Graph<V, E> owner;

        @Override
        public V get() {
            return this.data;
        }

        @Override
        public void put(V v) {
            this.data = v;
        }
    }

    private final class EdgeObj<E> implements Edge<E> {
        E data;
        VertexObj<V> from;
        VertexObj<V> to;
        Graph<V, E> owner;

        @Override
        public E get() {
            return this.data;
        }

        @Override
        public void put(E e) {
            this.data = e;
        }


    }
 

    private SentinelList<VertexObj<V>> vertices;
    private SentinelList<EdgeObj<E>> edges;

    private SparseGraph() {
        this.vertices = new SentinelList<VertexObj<V>>();
        this.edges = new SentinelList<EdgeObj<E>>();
    }

    private VertexObj<V> convert(Vertex<V> p) throws PositionException {
        try {
            VertexObj<V> v = (VertexObj<V>) p;
            if (v.owner != this) {
                throw new PositionException();
            }
            return v;
        } catch (NullPointerException | ClassCastException e) {
            throw new PositionException();
        }
    }

    @Override
    public Vertex<V> insert(V v) {
        VertexObj<V> temp = new VertexObj<V>();
        temp.data = v;
        temp.owner = this;
        this.vertices.insertBack(temp);
        return temp;
    }

    @Override
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
        throws PositionException, InsertionException {
        EdgeObj<E> temp = new EdgeObj<E>();
        temp.data = e;
        temp.from = (VertexObj) from;
        temp.to = (VertexObj) to;
        temp.owner = this;
        this.edges.insertBack(temp);

        temp.from.out.insertBack(temp);

        return temp;
    }

    @Override
    public V remove(Vertex<V> v) throws PositionException, RemovalException {

    }

    @Override
    public E remove(Edge<E> e) throws PositionException {

    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        
//        return ((SentinelList<Vertex<V>>) this.vertices).iterator();
        SentinelList<Vertex<V>>  verticesTemp = null;
        for (VertexObj<V> v: this.vertices) {
            verticesTemp.insertBack((Vertex<V>) v);
        }
        return verticesTemp.getIterable();

    }

    @Override
    public Iterable<Edge<E>> edges() {
//        return this.edges.iterator();
        SentinelList<Edge<E>>  edgesTemp = null;
        for (EdgeObj<E> e: this.edges) {
            edgesTemp.insertBack((Edge<E>) e);
        }
        return edgesTemp.getIterable();

    }

    @Override
    public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
        // somehow just return the list or iterable on the list for out from sentinel class
        VertexObj<V> temp = convert(v);

    }

    @Override
    public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
        
    }

    @Override
    public Vertex<V> from(Edge<E> e) throws PositionException {
        //VertexObj<V> temp = 
    }

    @Override
    public Vertex<V> to(Edge<E> e) throws PositionException {

    }

    @Override
    public void label(Vertex<V> v, Object l) throws PositionException {
        return;
    }

    @Override
    public void label(Edge<E> e, Object l) throws PositionException {
        return;
    }

    @Override
    public Object label(Vertex<V> v) throws PositionException {
        return true;
    }

    @Override
    public Object label(Edge<E> e) throws PositionException {
        return true;
    }

    @Override
    public void clearLabels() {
        return;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("digraph {\n");
        for (VertexObj<V> v: this.vertices) {
            s.append("  \"").append(v.data).append("\";\n");
        }
        for (EdgeObj<E> e: this.edges) {
            s.append("  \"").append(e.from).append("\" -> \"");
            s.append(e.to).append("\" [label=\"").append(e.data);
            s.append("\"];\n");
        }
        s.append("}");
        return s.toString();
    }

}

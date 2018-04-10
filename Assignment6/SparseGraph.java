/*
 * Maria Coleman
 * mcolem31
 */
import java.util.ArrayList;

/**
 * Implementation of Graph interface.
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

    private class VertexObj<V> implements Vertex<V> {
        V data;
        ArrayList<Edge<E>> out = new ArrayList<Edge<E>>();
        ArrayList<Edge<E>> in = new ArrayList<Edge<E>>();
        Graph<V, E> owner;
        Object l;

        @Override
        public V get() {
            return this.data;
        }

        @Override
        public void put(V v) {
            this.data = v;
        }

        //public void putInOut(Edge<E> e) {
        //    this.out.add(e);
        //}
    }

    private class EdgeObj<E> implements Edge<E> {
        E data;
        VertexObj<V> from;
        VertexObj<V> to;
        Graph<V, E> owner;
        Object l;

        @Override
        public E get() {
            return this.data;
        }

        @Override
        public void put(E e) {
            this.data = e;
        }
    }

    private ArrayList<Vertex<V>> vertices;
    private ArrayList<Edge<E>> edges;

    /** Constructor for checkstyle.*/
    public SparseGraph() {
        this.vertices = new ArrayList<Vertex<V>>();
        this.edges = new ArrayList<Edge<E>>();
    }

    private VertexObj<V> convert(Vertex<V> p) throws PositionException {
        try {
            VertexObj<V> v = (VertexObj<V>) p;
            if (v.owner != this) {
                throw new PositionException();
            }
            return v;
        } catch (NullPointerException | ClassCastException e) {
            //            System.out.println(e);
            throw new PositionException();
        }
    }

    private EdgeObj<E> convert(Edge<E> p) throws PositionException {
        try {
            EdgeObj<E> e = (EdgeObj<E>) p;
            //            System.out.println(e.get());
            if (e.owner != this) {
                //                System.out.println(e.get());
                throw new PositionException();
            }
            return e;
        } catch (NullPointerException | ClassCastException e) {
            //          System.out.println(e);
            throw new PositionException();
        }
    }


    @Override
    public Vertex<V> insert(V v) {
        VertexObj<V> temp = new VertexObj<V>();
        temp.data = v;
        temp.owner = this;
        this.vertices.add(temp);
        return temp;
    }

    @Override
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
        throws PositionException, InsertionException {
        EdgeObj<E> temp = new EdgeObj<E>();
        temp.data = e;
        temp.owner = this;
        //temp.from = (VertexObj) from;
        temp.from = this.convert(from);
        //temp.to = (VertexObj) to;
        temp.to = this.convert(to);
        //temp.owner = this;

        // throw InsertionException if insertion would create a self loop
        //if (from == to) {
        if (from.equals(to)) {
            throw new InsertionException();
        }

        // throw InsertionException if would create a duplicate edge

        if (temp.from.out.size() < temp.to.out.size()) {
            for (Edge<E> myEdge : temp.from.out) {
                if ((this.convert(myEdge).from.equals(temp.from))
                    && (this.convert(myEdge).to.equals(temp.to))) {
                    throw new InsertionException();
                }
            }
        } else {
            for (Edge<E> myEdge : temp.to.in) {
                if ((this.convert(myEdge).from.equals(temp.from))
                    && (this.convert(myEdge).to.equals(temp.to))) {
                    throw new InsertionException();
                }
            }
        }

        this.edges.add(temp);

        temp.from.out.add(temp);
        //temp.from.putInOut(temp);
        temp.to.in.add(temp);

        return temp;
    }

    @Override
    public V remove(Vertex<V> v) throws PositionException, RemovalException {
        // convert will throw the PositionException if necessary
        VertexObj<V> myVertex = this.convert(v);
        //V temp = v.get();

        // throw RemovalException if vertex still has incident edges
        if ((!myVertex.out.isEmpty()) || (!myVertex.in.isEmpty())) {
            throw new RemovalException();
        }

        V temp = myVertex.get();
        //this.vertices.remove(v);
        //this.vertices.remove((Position<VertexObj<V>>) convert(v));
        myVertex.owner = null;
        this.vertices.remove(v);
        return temp;
    }

    @Override
    public E remove(Edge<E> e) throws PositionException {
        EdgeObj<E> myEdge = this.convert(e);
        //E temp = e.get();
        E temp = myEdge.get();
        //this.edges.remove((Position<EdgeObj<E>>) convert(e));
        myEdge.owner = null;
        this.edges.remove(e);
        return temp;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return new ArrayList<Vertex<V>>(this.vertices);
    }

    @Override
    public Iterable<Edge<E>> edges() {
        return new ArrayList<Edge<E>>(this.edges);
    }

    @Override
    public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
        VertexObj<V> temp = this.convert(v);
        ArrayList<Edge<E>>  edgesTemp = new ArrayList<Edge<E>>();
        int ind = this.vertices.indexOf(v);
        Vertex<V> myVertex = this.vertices.get(ind);
        ArrayList<Edge<E>> mylist = this.convert(myVertex).out;

        return new ArrayList<Edge<E>>(mylist);
    }

    @Override
    public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
        VertexObj<V> temp = this.convert(v);
        ArrayList<Edge<E>>  edgesTemp = new ArrayList<Edge<E>>();
        int ind = this.vertices.indexOf(v);
        Vertex<V> myVertex = this.vertices.get(ind);
        ArrayList<Edge<E>> mylist = this.convert(myVertex).in;
        return new ArrayList<Edge<E>>(mylist);
    }

    @Override
    public Vertex<V> from(Edge<E> e) throws PositionException {
        EdgeObj<E> temp = this.convert(e);
        Vertex<V> v = (Vertex<V>) temp.from;
        return v;
    }

    @Override
    public Vertex<V> to(Edge<E> e) throws PositionException {
        // System.out.println(e.get());
        EdgeObj<E> temp = this.convert(e);
        Vertex<V> v = (Vertex<V>) temp.to;
        return v;
    }

    @Override
    public void label(Vertex<V> v, Object l) throws PositionException {
        VertexObj<V> myVertex = this.convert(v);
        myVertex.l = l;
        return;
    }

    @Override
    public void label(Edge<E> e, Object l) throws PositionException {
        EdgeObj<E> myEdge = this.convert(e);
        myEdge.l = l;
        return;
    }

    @Override
    public Object label(Vertex<V> v) throws PositionException {
        VertexObj<V> myVertex = this.convert(v);
        return myVertex.l;
    }

    @Override
    public Object label(Edge<E> e) throws PositionException {
        EdgeObj<E> myEdge = this.convert(e);
        return myEdge.l;
    }

    @Override
    public void clearLabels() {

        for (Vertex<V> v : this.vertices) {
            VertexObj<V> x = this.convert(v);
            x.l = null;
        }
        for (Edge<E> e : this.edges) {
            EdgeObj<E> x = this.convert(e);
            x.l = null;
        }

        return;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("digraph {\n");
        for (Vertex<V> v: this.vertices) {
            s.append("  \"").append(v.get()).append("\";\n");
        }
        for (Edge<E> e: this.edges) {
            EdgeObj<E> e2 = this.convert(e);
            s.append("  \"").append(e2.from.get()).append("\" -> \"");
            s.append(e2.to.get()).append("\" [label=\"").append(e2.data);

            s.append("\"];\n");
        }
        s.append("}");
        return s.toString();
    }
}

/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */

public class DirectedGraph implements Graph{
    private final int VERTICES;
    private int edges;
    private Bag<Integer>[] adj;


    public static void main(String[] args){
        DirectedGraph g = new DirectedGraph(10);
        g.addEdge(0,9);
        System.out.println(g);

    }

    DirectedGraph(int V) {

        if(V < 0) throw new IllegalArgumentException();
        this.VERTICES = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for(int i = 0; i < V; i++){
            adj[i] = new Bag<Integer>();
        }
    }

    private void validateVertex(int vertex) {
        if(0 > vertex  ||  vertex >= VERTICES) throw new IllegalArgumentException(
                "Vertex cannot be: " + vertex + " number of vertices: " + VERTICES);
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        edges++;
        adj[v].add(w);

    }

    public Iterable<Integer> adjacent(int vertex) {
        validateVertex(vertex);
        return adj[vertex];
    }

    public int degree(int vertex){
        validateVertex(vertex);
        return adj[vertex].size();
    }


    public int edges() {
        return edges;
    }


    public int vertices() {
        return VERTICES;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: " + VERTICES + " \nEdges: " + edges + "\n");
        for (int i = 0; i < VERTICES; i++){
            sb.append(i + " : ");
            for(Integer edge : adj[i]){
                sb.append(edge + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}

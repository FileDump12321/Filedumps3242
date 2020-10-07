/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EdgeWeightedDigraph {

    private final int VERTICES;
    private int edges;
    private Bag<DirectedEdge>[] adj;
    private int[] indegree;


    public EdgeWeightedDigraph(int V) {

        if (V < 0) throw new IllegalArgumentException();
        this.VERTICES = V;
        this.indegree = new int[V];
        adj = (Bag<DirectedEdge>[]) new Bag[V];

        for(int i = 0; i < V; i++){
            adj[i] = new Bag<DirectedEdge>();
        }
    }

    public EdgeWeightedDigraph(String fileName, String delimiter) throws FileNotFoundException, InputMismatchException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        try {

            VERTICES = scanner.nextInt();
            this.edges = scanner.nextInt();
            this.indegree = new int[VERTICES];
            adj = (Bag<DirectedEdge>[]) new Bag[VERTICES];
            for(int i = 0; i < VERTICES; i++){
                adj[i] = new Bag<DirectedEdge>();
            }

            while(scanner.hasNext()){

                int v = scanner.nextInt();
                int w = scanner.nextInt();
                int weight = scanner.nextInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(new DirectedEdge(v, w, weight));
            }

        }

        catch (InputMismatchException e){
            throw new InputMismatchException("File '" + fileName + "' is invalid format.");
        }
    }
    private EdgeWeightedDigraph(int vertices, int edges, Bag<DirectedEdge>[] adj, int[] indegree){
        this.VERTICES = vertices;
        this.edges = edges;
        this.adj = adj;
        this.indegree = indegree;
    }
    public EdgeWeightedDigraph copy(){

        Bag<DirectedEdge>[] adj = new Bag[this.adj.length];

        for(int i = 0; i < this.adj.length; i++){
            adj[i] = new Bag<>();
            for(DirectedEdge e : this.adj[i]){
                adj[i].add(new DirectedEdge(e));
            }
        }

        int[] indegree = new int[this.indegree.length];
        for(int i = 0; i < this.indegree.length; i++){
            indegree[i] = this.indegree[i];
        }
        return new EdgeWeightedDigraph(this.VERTICES, this.edges, adj, indegree);
    }


    public void addEdge(DirectedEdge edge) {
        int v = edge.from();
        int w = edge.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        indegree[w]++;
    }

    public Iterable<DirectedEdge> adjacent(int vertex) {
        validateVertex(vertex);
        return adj[vertex];
    }


    public int indegree(int vertex) {
        validateVertex(vertex);
        return indegree[vertex];
    }

    public int outdegree(int vertex) {
        validateVertex(vertex);
        return adj[vertex].size();
    }

    public int edges() {
        return edges;
    }


    public Iterable<DirectedEdge> allEdges() {
        Bag<DirectedEdge> edges = new Bag<>();
        for(int v = 0; v < VERTICES; v++){
            for(DirectedEdge edge : adj[v]){
                edges.add(edge);
            }
        }
        return edges;
    }


    public int vertices() {
        return VERTICES;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(VERTICES).append(" \nEdges: ").append(edges).append("\n");
        for (int i = 0; i < VERTICES; i++){
            sb.append(i).append(" : ");
            for(Edge edge : adj[i]){
                sb.append(edge.to()).append(" ");
            }
            
            sb.append("\n");
        }
        return sb.toString();
    }
    private void validateVertex(int vertex){
        if (vertex < 0  ||  VERTICES <= vertex) throw new IllegalArgumentException(
                "Vertex can't be: "
                        + vertex
                        + " it has to be between 0 and "
                        + (VERTICES -1));
    }
}

/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */


import java.io.FileNotFoundException;

public class DijkstraSearch {

    private static final int INFINITY = Integer.MAX_VALUE;
    private int[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Integer> pq;


        public static void main(String[] args) {

        try {
            int s = 1;
            int t = 7;

            EdgeWeightedDigraph digraph = new EdgeWeightedDigraph("src/NYC.txt", " ");
            System.out.println(digraph);
            DijkstraSearch search = new DijkstraSearch(digraph, s);

            System.out.println(search.hasPathTo(t));
            System.out.println("Path from " + s + " to " + t);
            Iterable<DirectedEdge> iter;
            if((iter = search.pathTo(t)) != null){
                for(DirectedEdge e : search.pathTo(t)){
                    System.out.print(e + " ");
                }
                System.out.println();
            }
            else {
                System.out.println("Doesn't exist.");
            }

        }
        catch(FileNotFoundException e){
            System.out.println("doesn't exist.");
        }
    }

    public DijkstraSearch(EdgeWeightedDigraph g, int source){

        for (Edge e : g.allEdges()){
            if (e.weight() < 0) throw new IllegalArgumentException("Edge with negative weight");
        }
        distTo = new int[g.vertices()];
        edgeTo = new DirectedEdge[g.vertices()];

        validateVertex(source);


        for(int v = 0; v < g.vertices(); v++){
            distTo[v] = INFINITY;
        }

        distTo[source] = 0;

        pq = new IndexMinPQ<>(g.vertices());
        pq.insert(source, distTo[source]);
        while(!pq.isEmpty()){
            for(DirectedEdge e : g.adjacent(pq.delMin())){
                relax(e);
            }
        }
    }
    private void relax(DirectedEdge edge){
        int v = edge.from();
        int w = edge.to();

        if(distTo[w] > distTo[v] + edge.weight()){
    
            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;
            
            if(pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else               pq.insert(w, distTo[w]);
        }
    }


    public boolean hasPathTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex] != INFINITY;
    }


    public int distTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex];
    }


    public Iterable<DirectedEdge> pathTo(int vertex){
        if (!hasPathTo(vertex)) return null;
        Bag<DirectedEdge> path = new Bag<>();
        for(DirectedEdge e = edgeTo[vertex]; e != null; e = edgeTo[e.from()]){
            path.add(e);
        }
        return path;
    }

    private void validateVertex(int vertex){
        if (vertex < 0  ||  distTo.length <= vertex) throw new IllegalArgumentException(
                "Vertex can't be: "
                        + vertex
                        + " has to be between 0 and "
                        + (distTo.length -1));
    }

}
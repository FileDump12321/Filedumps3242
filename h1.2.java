/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */

import java.io.FileNotFoundException;
public class DijkstraSearchBF {

    private static final int INFINITY = Integer.MAX_VALUE;
    private int[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Integer> pq;

    private LinkedList<DirectedEdge> halfPath;
    private boolean hasPath;


    public static void main(String[] args){
        try {
            int s = 1;
            int t = 7;
            int fd = 5;

            EdgeWeightedDigraph digraph = new EdgeWeightedDigraph("src/NYC.txt", " ");
            System.out.println(digraph);
            DijkstraSearchBF searchBF = new DijkstraSearchBF(digraph, s, t);

            for(DirectedEdge e : searchBF.pathTo()){
                System.out.println(e);
            }

            System.out.println(searchBF.hasPathTo(fd));


        }

        catch(FileNotFoundException e){
            System.out.println("File doesn't exist.");
        }
    }

    public DijkstraSearchBF(EdgeWeightedDigraph g, int source, int to){

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
                if(e.weight() == INFINITY) continue;
                relax(e);
            }
        }


        hasPath = hasPathTo(to);
        if(hasPath){
            halfPath = new LinkedList<>();
            Iterable<DirectedEdge> firstPath = pathTo(to);
            int startDist = 0;
            for(DirectedEdge e : firstPath){
                this.halfPath.append(new DirectedEdge(e.from(), e.to(), e.weight()));
                startDist += e.weight();

                e.setWeight(INFINITY);
            }

            search(g, to, startDist);
        }


    }
    private void relax(DirectedEdge edge) {

        int v = edge.from();
        int w = edge.to();

        if(distTo[w] > distTo[v] + edge.weight()){

            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;

            if(pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else               pq.insert(w, distTo[w]);
        }
    }

    private void search(EdgeWeightedDigraph g, int source, int startDistance){

        for (DirectedEdge e : g.allEdges()){
            if (e.weight() < 0) throw new IllegalArgumentException("Edge with negative weight detected.");
        }
        distTo = new int[g.vertices()];
        edgeTo = new DirectedEdge[g.vertices()];

        validateVertex(source);

        for(int v = 0; v < g.vertices(); v++){
            distTo[v] = INFINITY;
        }

        distTo[source] = startDistance;

        pq = new IndexMinPQ<>(g.vertices());
        pq.insert(source, distTo[source]);
        while(!pq.isEmpty()){
            for(DirectedEdge e : g.adjacent(pq.delMin())){
                if(e.weight() == INFINITY) continue;
                relax(e);
            }
        }
    }


    public boolean hasPathTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex] != INFINITY;
    }

    public boolean hasPath(){
        return hasPath;
    }


    public Iterable<DirectedEdge> pathTo(){
        return halfPath; // unsafe, should copy
    }


    public int distTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex];
    }


    public Iterable<DirectedEdge> pathTo(int vertex){
        if (!hasPathTo(vertex)) return null;

        LinkedList<DirectedEdge> path = new LinkedList<>();

        if(halfPath != null){
            for(DirectedEdge e : halfPath){

                path.append(e);
            }
        }

        LinkedList<DirectedEdge> tempPath = new LinkedList<>();
        for(DirectedEdge e = edgeTo[vertex]; e != null; e = edgeTo[e.from()]){

            tempPath.prepend(e);
        }

        for(DirectedEdge e : tempPath){
            path.append(e);
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

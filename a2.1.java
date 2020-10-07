/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */


public class SearchBF {
    private final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private final int source;

    public static void main(String[] args) {

        SymbolGraphUndirected sg = new SymbolGraphUndirected("src/data.txt", " ");
        Graph g = sg.graph();
        System.out.println(g);
        SearchBF searchBF = new SearchBF(g, 0);
        System.out.println(searchBF.hasPath(5));
        System.out.println("Path: ");

        for(int i : searchBF.edgeTo){
            System.out.print(i + " ");
        }
        System.out.println();

        for (int p : searchBF.pathTo(5)){
            System.out.print(p);
        }
        System.out.println();
    }

    public SearchBF(Graph graph, int sourceVertex){
        source = sourceVertex;
        marked = new boolean[graph.vertices()];
        edgeTo = new int[graph.vertices()];
        distTo = new int[graph.vertices()];
        for(int i = 0; i < distTo.length; i++){
            distTo[i] = INFINITY;
        }
        validateVertex(sourceVertex);
        markConnectedVerticesBF(graph, sourceVertex);
    }

    private void markConnectedVerticesBF(Graph graph, int sourceVertex){
        LinkedList<Integer> queue = new LinkedList<>();
        distTo[sourceVertex] = 0;
        marked[sourceVertex] = true;
        queue.append(sourceVertex);
        while(0 < queue.size()){
            int v = queue.remove(0);
            for(int w : graph.adjacent(v)){
                if(!marked[w]){
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    queue.append(w);
                }
            }
        }
    }

    public boolean hasPath(int vertex){
        validateVertex(vertex);
        return marked[vertex];
    }

    public int distTo(int vertex){
        validateVertex(vertex);
        return distTo[vertex];
    }

    public Iterable<Integer> pathTo(int vertex){
        validateVertex(vertex);
        if(!hasPath(vertex)) return null;
        LinkedList<Integer> path = new LinkedList<>();
        int x;
        for(x = vertex; x != source; x = edgeTo[x]){
            path.prepend(x);
        }
        path.prepend(x);
        return path;
    }
    
    private void validateVertex(int vertex){
        if(vertex < 0 || marked.length <= vertex) throw new IllegalArgumentException("Vertex cannot be: "
                + vertex
                + " has to be between 0 and "
                + (marked.length -1));
    }
    
}

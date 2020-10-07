/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */

public class SymbolGraphSearchBF {
    SearchBF searchBF;
    SymbolGraph symbolGraph;
    String source;

        public static void main(String[] args){
            
        SymbolGraphUndirected sg = new SymbolGraphUndirected("src/data.txt", " ");
        Graph g = sg.graph();
        System.out.println(g);
        SymbolGraphSearchBF searchBF = new SymbolGraphSearchBF(sg, "CA");

        System.out.println("CA has path to : " + searchBF.hasPath("PA"));
        for(String v : searchBF.pathTo("PA")) {
            System.out.print(v + " ");
        }

        System.out.println();
    }

    SymbolGraphSearchBF(SymbolGraph symbolGraph, String vertex) {

        try {
            this.source = vertex;
            this.symbolGraph = symbolGraph;
            searchBF = new SearchBF(symbolGraph.graph(), symbolGraph.indexOf(vertex));

        } catch (NotInGraphException e) {
            throw new IllegalArgumentException();
        }
    }

    private void validateVertex(String vertex){
        if(!symbolGraph.contains(vertex))throw new IllegalArgumentException();
    }

    public boolean hasPath(String vertex){
        try {
            return searchBF.hasPath(symbolGraph.indexOf(vertex));
        }

        catch(NotInGraphException e){
            throw new IllegalArgumentException();
        }
    }

    public Iterable<String> pathTo(String vertex){
        try {
            LinkedList<String> path = new LinkedList<>();
            for(int i : searchBF.pathTo(symbolGraph.indexOf(vertex))){
                path.append(symbolGraph.nameOf(i));
            }

            return path;
        }

        catch (NotInGraphException e){
            throw new IllegalArgumentException();
        }
    }


}
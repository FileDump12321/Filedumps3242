/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */

public class SymbolGraphSearchDF {
    SearchDF searchDF;
    SymbolGraph symbolGraph;
    String source;


        public static void main(String[] args){
            
        SymbolGraphUndirected sg = new SymbolGraphUndirected("src/data.txt", " ");
        Graph g = sg.graph();
        System.out.println(g);
        SymbolGraphSearchDF searchDF = new SymbolGraphSearchDF(sg, "CA");

        System.out.println("CA has path to : " + searchDF.hasPath("PA"));
        for(String v : searchDF.pathTo("PA")){
            System.out.print(v + " ");
        }

        System.out.println();
    }

    SymbolGraphSearchDF(SymbolGraph symbolGraph, String vertex){
        try {
            this.source = vertex;
            this.symbolGraph = symbolGraph;
            searchDF = new SearchDF(symbolGraph.graph(), symbolGraph.indexOf(vertex));

        } catch (NotInGraphException e) {
            throw new IllegalArgumentException();
        }
    }

    private void validateVertex(String vertex){
        if(!symbolGraph.contains(vertex))throw new IllegalArgumentException();
    }


    public boolean hasPath(String vertex){
        try{
            return searchDF.hasPath(symbolGraph.indexOf(vertex));
        }catch(NotInGraphException e){
            throw new IllegalArgumentException();
        }
    }
    public Iterable<String> pathTo(String vertex){
        try{
            LinkedList<String> path = new LinkedList<>();
            for(int i : searchDF.pathTo(symbolGraph.indexOf(vertex))){
                path.append(symbolGraph.nameOf(i));
            }
            return path;
        }catch (NotInGraphException e){
            throw new IllegalArgumentException();
        }
    }

}

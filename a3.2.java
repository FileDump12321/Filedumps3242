/*
 * Kabir Swain 
 * Algorithms 4th Edition Robert Sedgewick & Kevin Wayne
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SymbolGraphDirected implements SymbolGraph {

    private HashTable<String, Integer> ht = new HashTable<>();
    private String[] keys;
    private DirectedGraph graph;


        public static void main(String[] args) {

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        SymbolGraphDirected sg = new SymbolGraphDirected("src/data.txt", " ");
        System.out.println(sg.graph());
        System.out.println(sg);
        System.out.println(sg.contains("LO"));
        System.out.println(sg.nameOf(5));

        try {
            
            System.out.println(sg.indexOf("AR"));
        }

        catch (NotInGraphException e){
            e.printStackTrace();
        }
    }


    public SymbolGraphDirected(String fileName, String delimiter){
        ht = new HashTable<>();

        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                String line[] = scanner.nextLine().split(delimiter);
                for(int i = 0; i < line.length; i++){
                    if(!ht.contains(line[i])){
                        ht.put(line[i], ht.size());
                    }
                }
            }

            try{
                keys = new String[ht.size()];
                for(String key : ht.keys()){
                    keys[ht.get(key)] = key;
                }
            } 
            catch(NotInSTException e){
                e.printStackTrace(); 
            }

            graph = new DirectedGraph(ht.size());
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(delimiter);
                for(int i = 1; i < line.length; i++){
                    try {

                        graph.addEdge(ht.get(line[0]), ht.get(line[i]));
                    } 
                    catch (NotInSTException e){
                        e.printStackTrace(); 
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file path: " + fileName);
        }
    }


    public boolean contains(String key){
        return ht.contains(key);
    }

    public int indexOf(String key) throws NotInGraphException{
        try {
            return ht.get(key);
        } 
        catch (NotInSTException e) {
            throw new NotInGraphException();
        }
    }


    public String nameOf(int vertex){
        validateVertex(vertex);
        return keys[vertex];
    }

    private void validateVertex(int vertex){
        if (vertex < 0  ||  keys.length <= vertex) throw new IllegalArgumentException(
                "Vertex cannot be: "
                        + vertex
                        + " has to be between 0 and "
                        + (keys.length -1));
    }

    public Graph graph(){
        return graph;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: " + graph.vertices() + " \nEdges: " + graph.edges() + "\n");
        for (int i = 0; i < graph.vertices(); i++){
            sb.append(keys[i]).append(" : ");
            for(Integer edge : graph.adjacent(i)){
                sb.append(keys[edge]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}

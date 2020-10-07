/*
 * Kabir Swain 
 * Assignment 3
 *  
 * Assignment 3 uses the following files:
 * SymbolGraphDirected
 * SymbolGraphSearchBF (Breadth First Search) & SearchBF
 */

import java.util.Scanner;

public class a3 {

    public static void main(String[] args){

        String fileName = "src/data.txt";
        String delimiter = " ";
        SymbolGraph sg = new SymbolGraphDirected(fileName, delimiter);
        System.out.println(sg);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick vertices for path finding "
                + "(Format: 'X to Y')");

        while(scanner.hasNextLine()){
            String vertices[] = scanner.nextLine().split("\\sto\\s");
            if(vertices.length != 2){
                System.out.println("Input incorrect. Format 'X to Y'");
                continue;
            }

            String sourceVertex = vertices[0];
            String toVertex = vertices[1];

            try {
                SymbolGraphSearchBF sgSearchBF = new SymbolGraphSearchBF(sg, sourceVertex);
                System.out.print("Shortest path from " + sourceVertex + " to " + toVertex);
                if(sgSearchBF.hasPath(toVertex)) {

                    System.out.println(" is: ");
                    for(String v : sgSearchBF.pathTo(toVertex)){
                        System.out.print(v + " ");
                    }

                    System.out.println();
                }
                
                else {
                    System.out.println(" doesn't exist.");
                }
            }

            catch (IllegalArgumentException e){
                System.out.println("Source: '" + sourceVertex + "'");
                System.out.println("To: '" + toVertex + "'");
                System.out.println("Invalid vertex.");

                continue;
            }
            
            System.out.println("Pick vertices for path finding "
                    + "(Format: 'X to Y')");
        }
    }
}

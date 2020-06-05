import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class graphGene {
   public static void main(String args[]) {
        Scanner reader = new Scanner(System.in);
        GraphAL<Integer> tarGraph = new GraphAL<>(false);
        GraphAL<Integer> queGraph = new GraphAL<>(false);

        System.out.print("Enter number of Vertices of tarGraph:");
        int numVerTar = Integer.parseInt(reader.nextLine());
        System.out.print("Enter number of Edges of tarGraph:");
        int numEdgTar = Integer.parseInt(reader.nextLine());
        System.out.print("Enter number of Edges of queGraph:");
        int numEdgQue = Integer.parseInt(reader.nextLine());
        reader.close();
        String fileName = numVerTar + "Ver" + numEdgTar + "Edge" + numEdgQue + "Ver.txt";
        int sel;
        int count = 0;
        int start = (int)(Math.random() * numVerTar);
        int end = (int)(Math.random() * numVerTar);
        
        //set vertex tarGraph
        for(int i = 0; i < numVerTar; i++) 
            tarGraph.addVertex(i);
        createTar(start, end, tarGraph, numEdgTar);

        do {
            sel = (int)(Math.random() * tarGraph.getNumVertex());
        }while(tarGraph.getAdjacent(sel).size() < 1);
        createSub(sel, queGraph, tarGraph, count, numEdgQue);
       
        try {
            try(FileWriter fw = new FileWriter(new File(fileName))) {
                //printing the tarGraph
                String len = numVerTar + "";
                fw.write(len + "\n");
                for(Integer cr : tarGraph.getKeySet()) {
                    fw.write(cr + " ");
                    for(Integer crAdj : tarGraph.getAdjacent(cr)) {
                        fw.write(crAdj + " ");
                    }
                    fw.write("\n");
                }

                //print queGraph
                len = queGraph.getNumVertex() + "";
                fw.write(len + "\n");
                for(Integer cr: queGraph.getKeySet()) {
                    fw.write(cr + " ");
                    for(Integer crAdj : queGraph.getAdjacent(cr)) {
                        fw.write(crAdj + " ");
                    }
                    fw.write("\n");
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }

   public static void createTar(int start, int end, GraphAL<Integer> tarGraph, int numEdgTar) {
      boolean allDeg1 = false, temp;
      int tarCount = numEdgTar;
      int count = 0;
      int numVer = tarGraph.getNumVertex();

      while (count < tarCount) {
         if (!tarGraph.containEdge(start, end) && start != end) {
            tarGraph.addEdge(start, end);
            count++;
            start = end;
            if (!allDeg1) {
               temp = true;
               for (int i = 0; i < numVer; i++) {
                  if (tarGraph.getAdjacent(i).size() < 1)
                     temp = false;
               }
               if (temp)
                  allDeg1 = true;
            }
         }
         if (!allDeg1) {
            do {
               end = (int) (Math.random() * numVer);
            } while (tarGraph.getAdjacent(end).size() > 0);
         } else {
            end = (int) (Math.random() * numVer);
         }
      }
   }

   // recursion to find a subgraph of the graph
   public static boolean createSub(int sel, GraphAL<Integer> subGraph, GraphAL<Integer> tarGraph, int count,
         int tarCount) {
      if (count == tarCount)
         return true;
      if (!subGraph.containVertex(sel))
         subGraph.addVertex(sel);

      for (int adjSel : tarGraph.getAdjacent(sel)) {
         if (!subGraph.containEdge(sel, adjSel)) {
            if (!subGraph.containVertex(adjSel)) {
               subGraph.addVertex(adjSel);
            }
            subGraph.addEdge(sel, adjSel);
            count++;
            if (createSub(adjSel, subGraph, tarGraph, count, tarCount))
               return true;
         }
      }

      return false;
   }
}
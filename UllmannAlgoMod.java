import java.io.BufferedReader;
import java.io.FileReader;

public class UllmannAlgoMod {
   /**Represent graph for que graph */
   private GraphAL<Integer> queGraph;
   /**Represent graph for tar graph */
   private GraphAL<Integer> tarGraph;
   /**Represent graph for sub graph */
   private GraphAL<Integer> subGraph;
   /**numVerQue - number of vertex in que graph
    * numVerTar - number of vertex in target graph
    */
   private Integer numVerQue, numVerTar;
   /**record which col selected at what depth/row or in elyman what tar vertex is selected for que vertex */
   private Integer[] colSel; 
   /**representation of who is mapped or candidate into who
    * the row serves as the representaion of vertices of que graph
    * the column serves as the representaion of vertices of tar graph
    * state[x][y] = true if y is a candidate of x 
   */
   private boolean[][] state;
   /**record which columns had been assigned or mapped*/
   private boolean[] fCol;
   /**if graph is directed or undirected */
   private boolean isDir;
   /**the file directory of file for input */
   private String fileDir;
   /**record run time */
   private long runTime;

   /**
    * Constructor
    * @param fl the directory of file to read from
    * @param isDirected true if directed otherwise false
    */
   public UllmannAlgoMod(String fl, boolean isDirected) {
      this.fileDir = fl;
      this.isDir = isDirected;
      setInput();
   }

   /**start
    * this is called when the algo should start
    * @return the runtime in nanoseconds if -1 then the algo failed to find
    */
   public long start() {
      long startTime = System.nanoTime();
      filter();
      setColSel();
      if (search(0, this.state)) {
         //foundIt();
      } else {
         return -1;
      }
      long endTime = System.nanoTime() - startTime;
      this.runTime = endTime;
      return endTime;
   }

   /**SEARCH
    * the recursive backtracking method 
    * @param depth determine which row/vertex of que graph trying to map at this state
    * @param state representaiton of who is candidate of who
    */
   private boolean search(Integer depth, boolean[][] state) {
      // check if the candidate of prev vertex has the same adjacent vertices to it
      if (depth > 1) {
         Integer crPrev = this.queGraph.getVal(depth - 1);
         Integer crPrevCan = this.tarGraph.getVal(colSel[depth - 1]);
         Integer c;
         for (Integer cr : this.queGraph.getAdjacent(crPrev)) {
            c = this.queGraph.getPointer(cr);
            if (c < depth) {
               Integer crCan = this.tarGraph.getVal(colSel[c]);
               if (!this.tarGraph.containEdge(crPrevCan, crCan))
                  return false;
            }
         }
         refVer1D(crPrev, crPrevCan, state);
      }
      if (depth == this.queGraph.getNumVertex()) {
         return true;
      }
     refine(depth, state);
      for (Integer j = 0; j < state[depth].length; j++) {
         if (state[depth][j] && !fCol[j]) {
            this.fCol[j] = true;
            this.colSel[depth] = j;
            boolean[][] nxtState = copy(new boolean[this.state.length][this.state[0].length], state);
            nxtState[depth] = setTrueCol(nxtState[depth], j);
            if (search(depth + 1,  nxtState)) {
               return true;
            } else {
               this.fCol[j] = false;
               this.colSel[depth] = -1;
            }
         }
      }
      return false;
   }

   /** REFINEMENT PROCEDURE
    * @param depth which vertex is going to be refiend
    * @param state the current state
    */
   public void refine(int depth, boolean[][] state) {
      int queLen = this.queGraph.getNumVertex();
      int tarLen = this.tarGraph.getNumVertex();
      boolean theExiCan;
      for (int j = 0; j < tarLen; j++) {
         if (state[depth][j]) {
            Integer curVer = this.queGraph.getVal(depth);
            Integer canVer = this.tarGraph.getVal(j);
            for (Integer crAdj : this.queGraph.getAdjacent(curVer)) {
               theExiCan = false;
               for (Integer crCanAdj : this.tarGraph.getAdjacent(canVer)) {
                  if (state[this.queGraph.getPointer(crAdj)][this.tarGraph.getPointer(crCanAdj)])
                     theExiCan = true;
               }
               if (!theExiCan)
                  state[depth][j] = false;
            }
         }
      }
   }
   
   /** FILTERING PROCEDURE
    * find initial candidate of every vertex in que graph
   */
   private void filter() {
      for (Integer i = 0; i < this.state.length; i++) {
         for (Integer j = 0; j < this.state[i].length; j++) {
            if ((this.queGraph.getAdjacent(this.queGraph.getVal(i)))
                  .size() <= (this.tarGraph.getAdjacent(this.tarGraph.getVal(j))).size()) {
               this.state[i][j] = true;
            }
         }
      }
   }
   

   /** REFVER-1D  PROCEDURE
    * @param crPrev the vertex to check if it has a adjacent vertex having a degree of 1
    * @param crPrevCan the selected vertex of crPrev
    * @param state the current state
    */
   private void refVer1D(Integer crPrev, Integer crPrevCan, boolean[][] state) {
      for (Integer crAdj : this.queGraph.getAdjacent(crPrev)) {
         if (this.queGraph.getAdjacent(crAdj).size() == 1 && this.colSel[this.queGraph.getPointer(crAdj)] != -1) {
            int crAdjPointer = this.queGraph.getPointer(crAdj);
            for (Integer crCanAdj : this.tarGraph.getAdjacent(crPrevCan)) {
               int crCanAdjPointer = this.tarGraph.getPointer(crCanAdj);
               if (this.tarGraph.getAdjacent(crCanAdj).size() == 1 && this.fCol[crCanAdjPointer]) {
                  setTrueCol(state[crAdjPointer], crCanAdjPointer);
                  this.fCol[crCanAdjPointer] = true;
                  this.colSel[crAdjPointer] = crCanAdjPointer;
               }
            }
         }
      }
   }

   /** copy
    * @param target the matrix to copy the content onto
    * @param source the source matrix
    */
   private boolean[][] copy(boolean[][] target, boolean[][] source) {
      for (Integer i = 0; i < target.length; i++) {
         for (Integer j = 0; j < target[i].length; j++) {
            target[i][j] = source[i][j];
         }
      }
      return target;
   }


   /** 
    * @param arr the array to set all value to false except for col
    * @param col the index to remain true 
    */
   private boolean[] setTrueCol(boolean[] arr, Integer col) {
      // set every value of row to false except at col
      for (Integer i = 0; i < arr.length; i++) {
         if (i == col)
            arr[i] = true;
         else
            arr[i] = false;
      }
      return arr;
   }

   /** FOUND THE ARRAY */
   /** Create a graph to represent the matrix answer and print it*/
   private void foundIt() {
      this.subGraph = new GraphAL<>(isDir);
      for (Integer queVer : this.queGraph.getKeySet()) {
         for (Integer adjVer : this.queGraph.getAdjacent(queVer)) {
            this.subGraph.addEdge(this.tarGraph.getVal(this.colSel[this.queGraph.getPointer(queVer)]),
                  this.tarGraph.getVal(this.colSel[this.queGraph.getPointer(adjVer)]));
         }
      }
      System.out.println("Answer");
      subGraph.printGraph();
   }

   /**get run time */
   public long getRunTime() {
      return this.runTime;
   }

   /** 
    * initial method to set every colSel to -1 
   */
   private void setColSel() {
      for (Integer i = 0; i < colSel.length; i++) {
         this.colSel[i] = -1;
      }
   }

   /**
    * read input from fileDir
    */
   private void setInput() {
      this.queGraph = new GraphAL<>(this.isDir);
      this.tarGraph = new GraphAL<>(this.isDir);
      try {
         try (BufferedReader in = new BufferedReader(new FileReader(fileDir))) {
            Integer numVer = Integer.parseInt(in.readLine());
            for (Integer i = 0; i < numVer; i++) {
               String[] arrCr = in.readLine().split(" ");
               this.tarGraph.addVertex(Integer.parseInt(arrCr[0]));
               for (Integer j = 1; j < arrCr.length; j++) {
                  this.tarGraph.addEdge(Integer.parseInt(arrCr[0]), Integer.parseInt(arrCr[j]));
               }
            }
            numVer = Integer.parseInt(in.readLine());
            for (Integer i = 0; i < numVer; i++) {
               String[] arrCr = in.readLine().split(" ");
               this.queGraph.addVertex(Integer.parseInt(arrCr[0]));
               for (Integer j = 1; j < arrCr.length; j++) {
                  this.queGraph.addEdge(Integer.parseInt(arrCr[0]), Integer.parseInt(arrCr[j]));
               }
            }
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      this.numVerQue = queGraph.getNumVertex();
      this.numVerTar = tarGraph.getNumVertex();
      this.state = new boolean[numVerQue][numVerTar];
      this.fCol = new boolean[numVerTar]; 
      this.colSel = new Integer[numVerQue];
      setColSel();
   }

   /** FOR PRINTING */
   /**
    * print the matrix state which is the mapping of 
    */
   public void printArr() {
      for (Integer i = 0; i < this.state.length; i++) {
         for (Integer j = 0; j < this.state[i].length; j++) {
            System.out.print(this.state[i][j] + " ");
         }
         System.out.println();
      }
   }

   /** 
    * print the representation of que and target graph
   */
   public void print() {
      System.out.println("QueGraph:");
      this.queGraph.printGraph();
      System.out.println("\nTarGraph");
     this.tarGraph.printGraph();
      System.out.println();
   }
}
public class Main {
   public static void main(String[] args) {
      // Dense Graph File Name
      String firstD = "Generated Graph/Machine Generated Graph/Dense Graph/15Ver79Edge20Edge.txt";
      String secondD = "Generated Graph/Machine Generated Graph/Dense Graph/25Ver225Edge56Edge.txt";
      String thirdD = "Generated Graph/Machine Generated Graph/Dense Graph/35Ver446Edge111Edge.txt";
      String fourthD = "Generated Graph/Machine Generated Graph/Dense Graph/45Ver743Edge186Ege.txt";
      String fifthD = "Generated Graph/Machine Generated Graph/Dense Graph/55Ver1114Edge279Edge.txt";
      // Sparse Graph File Name
      String firstS = "Generated Graph/Machine Generated Graph/Sparse Graph/15Ver19Edge5Edge.txt";
      String secondS = "Generated Graph/Machine Generated Graph/Sparse Graph/25Ver31Edge8Edge.txt";
      String thirdS = "Generated Graph/Machine Generated Graph/Sparse Graph/35Ver44Edge11Edge.txt";
      String fourthS = "Generated Graph/Machine Generated Graph/Sparse Graph/45Ver56Edge14Edge.txt";
      String fifthS = "Generated Graph/Machine Generated Graph/Sparse Graph/55Ver69Edge17Edge.txt";
      //Dq >= Dt
      String firstM = "Generated Graph/Created Graph/DqGreaterDt/5Ver3Ver.txt";
      String secondM = "Generated Graph/Created Graph/DqGreaterDt/6Ver5Ver.txt";
      String thirdM = "Generated Graph/Created Graph/DqGreaterDt/8Ver4Ver.txt";
      String fourthM = "Generated Graph/Created Graph/DqGreaterDt/10Ver4Ver.txt";
      String fifthM = "Generated Graph/Created Graph/DqGreaterDt/12Ver6Ver.txt";
      String sixthM = "Generated Graph/Created Graph/DqGreaterDt/14Ver6Ver.txt";
      //Dq <= Dt
      String firstF = "Generated Graph/Created Graph/DqLesserDt/5Ver4Ver.txt";
      String secondF = "Generated Graph/Created Graph/DqLesserDt/6Ver4Ver.txt";
      String thirdF = "Generated Graph/Created Graph/DqLesserDt/8Ver3Ver.txt";
      String fourthF = "Generated Graph/Created Graph/DqLesserDt/10Ver6Ver.txt";
      String fifthF = "Generated Graph/Created Graph/DqLesserDt/14Ver7Ver.txt";


      String flSelected = firstS;
      /**set 
       * true if directed 
       * false if undirected*/
      boolean isDirect = false; //all experiment is executed as undirected graph

      
      Backtracking algo = new Backtracking(flSelected, isDirect);
      System.out.println(algo.start());

      // UllmannAlgo algo = new UllmannAlgo(flSelected, isDirect);
      // System.out.println(algo.start());
      
      // UllmannAlgoMod algo = new UllmannAlgoMod(flSelected, isDirect);
      // System.out.println(algo.start());
   }
}
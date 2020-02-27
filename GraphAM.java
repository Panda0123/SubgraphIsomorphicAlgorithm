import java.util.HashMap;

public class GraphAM<T> {
  private boolean isDirected;
  private int size;
  private boolean[][] matrix;
  private int pointer;
  private HashMap<T, Integer> map = new HashMap<>();


  //constructor
  public GraphAM(boolean isDirected, int size) {
    this.isDirected = isDirected;
    this.size = size;
    this.pointer = 0;
  }

  private void construct(){
    this.matrix = new boolean[size][size];
  }

  public void addVertex(T val) {
    map.put(val, pointer++);
  }

  public void addEdge(T x, T y) {
    
    if(!map.containsKey(x)) {
      map.put(x, pointer++);
    }

    if(!map.containsKey(y)) {
      map.put(y, pointer++);
    }

    matrix[map.get(x)][map.get(y)] = true;
  }

  public  boolean containVer(T val) {
    return map.containsKey(val);
  }

  public boolean containEdge(T x, T y) {
    return matrix[map.get(x)][map.get(y)];
  }

}
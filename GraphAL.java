//Graph Representation using Adjacency list
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;

public class GraphAL<T> {
  private boolean isDirected;
  private int pointer = 0;
  // to store vertex and its adjacent vertex
  private HashMap<T, List<T>> map = new HashMap<>();
  private HashMap<T, Integer> indexMap = new HashMap<>();
  private HashMap<Integer, T>  valMap = new HashMap<>();

  // Constructor
  public GraphAL(boolean directed) {
    this.isDirected = directed;
  }
  // add vertex to the graph
  public void addVertex(T k) {
    if(!map.containsKey(k)) {
      map.put(k, new LinkedList<T>());
      indexMap.put(k, pointer);
      valMap.put(pointer++, k);
    }
  }
  // add edges to the graph
  public void addEdge(T source, T destination) {
    if (!map.containsKey(source)) {
      map.put(source, new LinkedList<T>());
      indexMap.put(source, pointer);
      valMap.put(pointer++, source);
    }
    if (!map.containsKey(destination)) {
      map.put(destination, new LinkedList<T>());
      indexMap.put(destination, pointer);
      valMap.put(pointer++, destination);
    }
    if(!map.get(source).contains(destination)) 
      map.get(source).add(destination);
    if(!map.get(destination).contains(source))  
      if (!isDirected)
        map.get(destination).add(source);
  }
  //get adjacent of vertex ver
  public List<T> getAdjacent(T ver) {
    return map.get(ver);
  }

  // get number of vertices
  public int getNumVertex() {
    return map.keySet().size();
  }
  // get number of edges
  public int getNumEdges() {
    int count = 0;
    for (T k : map.keySet())
      count += map.get(k).size();
    if (!isDirected)
      count /= 2;
    return count;
  }
  // get degrees
  public int getDegree(T ver) {
    return map.get(ver).size();
  }
  //get keys
  public Set<T> getKeySet() {
    return map.keySet();
  }
  //get pointer
  public int getPointer(T ver) {
    return indexMap.get(ver);
  }
  public T getVal(int p) {
    return valMap.get(p);
  }
  // return true if there exist vertex x
  public boolean containVertex(T x) {
    return map.containsKey(x);
  }
  // return true if there exist an edge x -> y
  public boolean containEdge(T x, T y) {
    return map.get(x).contains(y);
  }
  // print graphs
  public void printGraph() {
    int index;
    for (T k : map.keySet()) {
      Iterator<T> fi = map.get(k).iterator();
      index = -1;
      System.out.print(k + ": ");
      while (fi.hasNext()) {
        System.out.print(fi.next() + " ");
      }
      index = -1;
      System.out.println();
    }
  }
}
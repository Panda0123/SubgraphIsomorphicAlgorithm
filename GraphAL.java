//Graph Representation using Adjacency list
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class GraphAL<T> {
  private boolean isDirected;
  // to store vertex and its adjacent vertex
  private HashMap<T, List<T>> map = new HashMap<>();

  // Constructor
  public GraphAL(boolean directed) {
    this.isDirected = directed;
  }

  // add vertex to the graph
  public void addVertex(T k) {
    map.put(k, new LinkedList<T>());
  }

  // add edges to the graph
  public void addEdge(T source, T destination) {
    if (!map.containsKey(source))
      map.put(source, new LinkedList<T>());

    if (!map.containsKey(destination))
      map.put(destination, new LinkedList<T>());

    map.get(source).add(destination);
    if (!isDirected)
      map.get(destination).add(source);
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

  // return true if there exist vertex x
  public boolean containVertex(T x) {
    return map.containsKey(x);
  }

  // return true if there exist an edge x -> y
  public boolean containEdge(T x, T y) {
    return map.get(x).contains(y);
  }

}
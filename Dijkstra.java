package sol;

import src.IDijkstra;
import src.IGraph;
import src.Transport;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {
    HashMap<V,Double> distances;
    HashMap<V,E> parent;

    public Dijkstra(){
        this.distances = new HashMap<>();
        this.parent = new HashMap<>();
    }

    public void checkPath(IGraph<V, E> graph, V source, Function<E, Double> edgeWeight){
        Comparator<V> weight = (edgeWeight1, edgeWeight2) -> {
            return Double.compare(this.distances.get(edgeWeight1), this.distances.get(edgeWeight2));
        };
        PriorityQueue<V> vQue = new PriorityQueue<>(weight);
        for(V vertex : graph.getVertices()){
            this.distances.put(vertex,Double.POSITIVE_INFINITY);
            this.parent.put(vertex,null);
        }
        this.distances.put(source,0.0);
        vQue.addAll(graph.getVertices());
        while(!vQue.isEmpty()){
            V currentV = vQue.poll();
            for(E out : graph.getOutgoingEdges(currentV)){
                V neighbor = graph.getEdgeTarget(out);
                double newDist = this.distances.get(currentV) + edgeWeight.apply(out);
                if(newDist < this.distances.get(neighbor)){
                    distances.put(neighbor,newDist);
                    parent.put(neighbor,out);
                    vQue.remove(neighbor);
                    vQue.add(neighbor);
                }
            }
        }
    }


    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination, Function<E, Double> edgeWeight) {
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!
        this.checkPath(graph,source,edgeWeight);
        List<E> path = new ArrayList<>();
        V currentCity = destination;
        E previous = this.parent.get(currentCity);
        while(previous != null){
            path.add(0,this.parent.get(currentCity));
            currentCity = graph.getEdgeSource(this.parent.get(currentCity));
            previous = this.parent.get(currentCity);
        }
        return path;
    }
    // TODO: feel free to add your own methods here!
}

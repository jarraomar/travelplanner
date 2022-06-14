package sol;

import src.IBFS;
import src.IGraph;
import java.util.*;

public class BFS<V, E> implements IBFS<V, E> {
    IGraph<V,E> graph;
    LinkedList<E> path;
    HashMap<V,E> trackCity;

    public BFS(){
        this.trackCity = new HashMap<>();
    }

    // TODO: implement the getPath method!
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        this.graph = graph;
        this.path = new LinkedList<E>();
            //use a link list to add the cities we're tracking back words
            //take the path from the end to it's source (or second to last end) and then add that to the link list.
            // Take the new end (or the second end) and add the path from it's source. Iterate through a loo[ until we reach the
            //start
            // parents contain a vertex and edge
        if(this.canReach(start,end)) {
            V currentCity = end;
            while (!(currentCity.equals(start))) {
                E edge = trackCity.get(currentCity);
                V source = this.graph.getEdgeSource(edge);
                path.addFirst(edge);
                currentCity = source;
//                for (E edges : this.graph.getOutgoingEdges(end)) {
//                this.graph.getEdgeSource(edges)
//                    cityTracking.add(edges);
            }
        }
        return path;
    }

    private boolean canReach(V start, V end) {
        LinkedList<E> toCheck = new LinkedList<E>(this.graph.getOutgoingEdges(start));
        HashSet<E> visited = new HashSet<E>();
        while (!toCheck.isEmpty()) {
            //E edge = toCheck.removeLast();
            E edge = toCheck.poll();
            V targetCity = this.graph.getEdgeTarget(edge);
            //possibly check if the visited contains the city rather than the edge
            //visited.add(edge);
            if(visited.contains(edge)){
                continue;
            }
            visited.add(edge);
            if (!(trackCity.containsKey(targetCity))){
                // this handles the shortest path
                trackCity.put(targetCity,edge);
            }
                if (targetCity.equals(end)){
                    //this.getPath(graph,start,end);
                    return true;
                } else {
                    //double check this
                    //put it through a loop and check if it's already been visited
                    toCheck.addAll(this.graph.getOutgoingEdges(targetCity));
                }
            }
        return false;
    }
}

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name

package sol;

import src.City;
import src.IGraph;
import src.Transport;
import src.TransportType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {
    HashMap<String,City> data;

    public TravelGraph(){
        this.data = new HashMap<>();
    }

    @Override
    public void addVertex(City vertex) {
        // TODO: implement this method!
        this.data.put(vertex.toString(),vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: implement this method!
        origin.addOut(edge);
    }

    @Override
    public Set<City> getVertices() {
        // TODO: implement this method!
        return new HashSet<>(this.data.values());
    }

    @Override
    public City getEdgeSource(Transport edge) {
        // TODO: implement this method!
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        // TODO: implement this method!
        return edge.getTarget();
    }

    public Double getPrice(Transport transport){
        return transport.getPrice();
    }

    public Double getMinutes(Transport transport){
        return transport.getMinutes();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        // TODO: implement this method!
        return fromVertex.getOutgoing();
    }

    public City getCity(String name) throws IllegalArgumentException{
        // TODO: feel free to add your own methods here!
        // hint: maybe you need to get a City by its name
        if(!this.data.containsKey(name)){
            City newCity = new City(name);
            this.addVertex(newCity);
        }
        else if(this.data.get(name).equals(null)){
            throw new RuntimeException("Name is null");
        }
        return this.data.get(name);
    }
}
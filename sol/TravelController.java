package sol;

import src.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!

    //do load first
    //how do I read the csv files for load, pass location to parse location method (call parse location and input
    //a function (watch gear up for the explanation), it will take a map of string to string (to every row in the csv )
    //and the map were given is from the column value. For each row out the header, it will call on it. That map from
    //keys (header) to the values (each characterisitics of that row)
    //map (origin) -> Boston
    //map (destination) -> Providence
    //create city objects and add them to whatever data objects

    private TravelGraph graph;
    public TravelController() {
    }

    @Override
    public String load(String citiesFile, String transportFile) {
        // TODO: instantiate a new Graph object in the graph field

        // TODO: create an instance of the TravelCSVParser

        // TODO: create a variable of type Function<Map<String, String>, Void>
        //       that will build the nodes in a graph. Keep in mind
        //       that the input to this function is a hashmap that relates the
        //       COLUMN NAMES of the csv to the VALUE IN THE COLUMN of the csv.
        //       It might be helpful to write a method in this class that takes the
        //       information from the csv needed to create an edge and uses that to
        //       build the edge on the graph.

        // TODO: create another variable of type Function<Map<String, String>, Void> which will
        //  build connections between nodes in a graph.

        // TODO: call parseLocations with the first Function variable as an argument and the right
        //  file

        // TODO: call parseTransportation with the second Function variable as an argument and
        //  the right file

        // hint: note that parseLocations and parseTransportation can
        //       throw IOExceptions. How can you handle these calls cleanly?

        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        //get the origin and destination city

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        Function<Map<String, String>, Void> addEdge = map -> {
            Transport transport = new Transport(this.graph.getCity(map.get("origin")),
                    this.graph.getCity(map.get("destination")),TransportType.fromString(map.get("type")),
                    Double.parseDouble(map.get("price")),Double.parseDouble(map.get("duration")));
            this.graph.addEdge(transport.getSource(), transport);
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseTransportation(transportFile,addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }
        return "Successfully loaded cities and transportation files.";
    }

    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        // TODO: implement this method!
        Function<Transport, Double> getTime = transportEdge -> transportEdge.getMinutes();
        Dijkstra<City,Transport> dijkstra = new Dijkstra<>();
        return dijkstra.getShortestPath(this.graph,this.graph.getCity(source),this.graph.getCity(destination),getTime);
    }

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        // TODO: implement this method!
        Function<Transport, Double> getPrice = transportEdge -> transportEdge.getPrice();
        Dijkstra<City,Transport> dijkstra = new Dijkstra<>();
        return dijkstra.getShortestPath(this.graph,this.graph.getCity(source),this.graph.getCity(destination),getPrice);
    }

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        // TODO: implement this method!
        BFS<City,Transport> bfs = new BFS<>();
        return bfs.getPath(this.graph,this.graph.getCity(source),this.graph.getCity(destination));
    }
}

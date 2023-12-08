// HW6_dyl30
import java.util.*;

public class AirportSystem {
    List<Vertex> connections;

    public AirportSystem() {
        connections = new ArrayList<>();
    }

    // Method to add an edge to the graph
    public boolean addEdge(String source, String destination, int distance) {
        if (distance < 0) return false;

        // Find the source vertex, create a new one if it doesn't exist
        Vertex sourceVertex = findVertex(source);
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            connections.add(sourceVertex);
        }

        // Check if the edge already exists to avoid duplicates
        for (Edge e : sourceVertex.edges) {
            if (e.destination.equals(destination)) return false;
        }

        // Add the new edge to the source vertex
        sourceVertex.edges.add(new Edge(source, destination, distance));
        return true;
    }

    // Utility method to find a vertex by its ID
    private Vertex findVertex(String id) {
        for (Vertex v : connections) {
            if (v.id.equals(id)) return v;
        }
        return null; // Return null if the vertex is not found
    }

    // Class representing an edge in the graph
    class Edge {
        String source;
        String destination;
        int distance;

        public Edge(String source, String destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }
    }

    // Class representing a vertex in the graph
    class Vertex {
        String id;
        List<Edge> edges;

        public Vertex(String id) {
            this.id = id;
            edges = new ArrayList<>();
        }
    }

    // Method to find the shortest distance between two cities using Dijkstra's algorithm
    public int shortestDistance(String cityA, String cityB) {
        // Return -1 if either city is not in the graph
        if (findVertex(cityA) == null || findVertex(cityB) == null) return -1;

        Map<String, Integer> distances = new HashMap<>();
        for (Vertex v : connections) {
            distances.put(v.id, Integer.MAX_VALUE);
        }
        distances.put(cityA, 0);

        // Use a list as a substitute for a priority queue
        List<String> toVisit = new ArrayList<>();
        toVisit.add(cityA);

        // Main loop of Dijkstra's algorithm
        while (!toVisit.isEmpty()) {
            String currentCity = toVisit.remove(0);
            Vertex currentVertex = findVertex(currentCity);

            for (Edge edge : currentVertex.edges) {
                int newDist = distances.get(currentCity) + edge.distance;
                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    toVisit.add(edge.destination);
                }
            }
        }
        // Return the shortest distance to the destination city or -1 if unreachable
        return distances.get(cityB) != Integer.MAX_VALUE ? distances.get(cityB) : -1;
    }

    // Method to construct the minimum spanning tree using Prim's algorithm
    public List<Edge> minimumSpanningTree() {
        // Check if the graph is empty and return an empty list in that case
        if (connections.isEmpty()) {
            return new ArrayList<>();
        }

        Set<String> inMST = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        PriorityQueue<Edge> edgesQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));

        // Start with the first vertex in connections
        Vertex startVertex = connections.get(0);
        inMST.add(startVertex.id);
        edgesQueue.addAll(startVertex.edges);

        // Main loop to construct the MST
        while (!edgesQueue.isEmpty() && inMST.size() < connections.size()) {
            Edge edge = edgesQueue.poll(); // Get the edge with the lowest distance

            // Check if the edge connects a new vertex to the MST
            if (inMST.contains(edge.source) && !inMST.contains(edge.destination)) {
                inMST.add(edge.destination);
                mstEdges.add(edge);

                // Add all edges of the new vertex to the queue, if not already in MST
                Vertex nextVertex = findVertex(edge.destination);
                if (nextVertex != null) {
                    for (Edge nextEdge : nextVertex.edges) {
                        if (!inMST.contains(nextEdge.destination)) {
                            edgesQueue.add(nextEdge);
                        }
                    }
                }
            }
        }

        // Check if all vertices are included in the MST
        if (inMST.size() != connections.size()) {
            return null;
        }
        return mstEdges;
    }

    // Method for Breadth-First Search
    public List<String> breadthFirstSearch(String startCity) {
        List<String> visited = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();

        // Find the starting vertex; return empty list if it doesn't exist
        Vertex startVertex = findVertex(startCity);
        if (startVertex == null) {
            return visited;
        }
        // Add the starting city to the queue
        queue.add(startCity);

        // Loop until the queue is empty
        while (!queue.isEmpty()) {
            // Retrieve and remove the head of the queue
            String city = queue.poll();

            // If the city has not been visited yet, process it
            if (!visited.contains(city)) {
                visited.add(city);

                // Retrieve the vertex corresponding to the city
                Vertex vertex = findVertex(city);
                if (vertex != null) {
                    // Iterate through all edges of the vertex
                    for (Edge edge : vertex.edges) {
                        // If the destination city of the edge has not been visited, add it to the queue
                        if (!visited.contains(edge.destination)) {
                            queue.add(edge.destination);
                        }
                    }
                }
            }
        }
        return visited; // Return the list of visited cities
    }

    // Method to print the graph
    public void printGraph() {
        for (Vertex vertex : connections) {
            System.out.print("Vertex: " + vertex.id + " | Edges: ");
            for (Edge edge : vertex.edges) {
                System.out.print(edge.toString() + " ");
            }
            System.out.println();
        }
    }
}
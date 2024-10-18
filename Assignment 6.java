import java.util.Scanner;

public class RoutingProtocols {

    // Dijkstra's Algorithm for Link State Routing Protocol
    public static void dijkstra(int graph[][], int src) {
        int V = graph.length; // Number of vertices
        int[] dist = new int[V]; // Array to store the shortest distance from the source to each vertex
        boolean[] sptSet = new boolean[V]; // Boolean array to track vertices included in the shortest path tree (SPT)

        // Initialize all distances as INFINITE and mark all vertices as not yet processed
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(sptSet, false);

        // Set the distance of the source vertex from itself to 0
        dist[src] = 0;

        // Find shortest paths for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the vertex with the minimum distance that has not yet been processed
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update the distance values of adjacent vertices
            for (int v = 0; v < V; v++) {
                // Update dist[v] if vertex v is not in sptSet, there is an edge from u to v,
                // and the total weight of path from src to v through u is smaller than the current value of dist[v]
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        // Print the final shortest distances from the source to each vertex
        printSolution(dist, src);
    }

    // Method to find the vertex with the minimum distance value from the set of vertices not yet processed
    private static int minDistance(int dist[], boolean sptSet[]) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        // Loop through all vertices to find the vertex with the minimum distance
        for (int v = 0; v < dist.length; v++) {
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex; // Return the index of the vertex with the minimum distance
    }

    // Method to print the shortest distances from the source to all vertices
    private static void printSolution(int dist[], int src) {
        System.out.println("Vertex \t\t Distance from Source (Node " + src + ")");
        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + " \t\t " + dist[i]);
        }
    }

    // Bellman-Ford Algorithm for Distance Vector Routing Protocol
    public static void bellmanFord(int graph[][], int src) {
        int V = graph.length; // Number of vertices
        int[] dist = new int[V]; // Array to store the shortest distances from the source to all vertices

        // Initialize distances from the source to all other vertices as INFINITE
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0; // Distance of source vertex from itself is always 0

        // Relax all edges |V| - 1 times (where V is the number of vertices)
        for (int i = 0; i < V - 1; i++) {
            // Go through all edges and update distances
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v]; // Update dist[v] if a shorter path is found
                    }
                }
            }
        }

        // Check for negative-weight cycles
        for (int u = 0; u < V; u++) {
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    System.out.println("Graph contains a negative weight cycle.");
                    return; // If a negative weight cycle is detected, return immediately
                }
            }
        }

        // Print the final shortest distances from the source to each vertex
        printSolution(dist, src);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of nodes in the network
        System.out.println("Enter the number of nodes in the network:");
        int V = scanner.nextInt();

        int[][] graph = new int[V][V]; // Create an adjacency matrix for the network

        // Input the adjacency matrix from the user
        System.out.println("Enter the adjacency matrix of the network (0 if no direct link):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = scanner.nextInt(); // Fill the matrix with the weights of the links
            }
        }

        // Choose the routing protocol to use
        System.out.println("Choose the routing protocol:\n1. Link State (Dijkstra's Algorithm)\n2. Distance Vector (Bellman-Ford Algorithm)");
        int choice = scanner.nextInt();

        // Input the source node from which to calculate the shortest paths
        System.out.println("Enter the source node:");
        int src = scanner.nextInt();

        // Execute the chosen routing protocol
        switch (choice) {
            case 1:
                dijkstra(graph, src); // Run Dijkstra's Algorithm for Link State Routing
                break;
            case 2:
                bellmanFord(graph, src); // Run Bellman-Ford Algorithm for Distance Vector Routing
                break;
            default:
                System.out.println("Invalid choice!"); // Handle invalid choices
        }

        scanner.close(); // Close the scanner
    }
}

package dns;
import java.net.*;
import java.util.Scanner;

public class DNSLookup {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Infinite loop for user to keep selecting options until they choose to exit
        while (true) {
            // Display options for the user
            System.out.println("\nDNS Lookup Options:");
            System.out.println("1. Find IP address from URL");
            System.out.println("2. Find URL from IP address");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt(); // Read user's choice
            scanner.nextLine();  // Consume newline left by nextInt()

            // Switch case to handle user's input choice
            switch (choice) {
                case 1:
                    // Option 1: Find IP address from URL
                    System.out.print("Enter URL (e.g., www.google.com): ");
                    String url = scanner.nextLine();  // Get URL from user
                    findIPAddress(url);  // Call method to find IP address of the URL
                    break;
                
                case 2:
                    // Option 2: Find URL from IP address
                    System.out.print("Enter IP address (e.g., 142.250.190.78): ");
                    String ipAddress = scanner.nextLine();  // Get IP address from user
                    findHostname(ipAddress);  // Call method to find the hostname for the IP address
                    break;
                    
                case 3:
                    // Option 3: Exit
                    System.out.println("Exiting DNS Lookup.");
                    scanner.close();  // Close the scanner to free resources
                    return;  // Exit the program
                    
                default:
                    // If user enters an invalid choice
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to find and display the IP address for a given URL
    private static void findIPAddress(String url) {
        try {
            // Use InetAddress class to get the IP address of the URL
            InetAddress inetAddress = InetAddress.getByName(url);
            System.out.println("IP Address for " + url + ": " + inetAddress.getHostAddress());  // Display the IP address
        } catch (UnknownHostException e) {
            // If the URL is invalid or cannot be found, display an error message
            System.out.println("Unknown Host: " + e.getMessage());
        }
    }

    // Method to find and display the hostname for a given IP address
    private static void findHostname(String ipAddress) {
        try {
            // Use InetAddress class to get the hostname of the IP address
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            String hostName = inetAddress.getCanonicalHostName();  // Get the canonical hostname
            System.out.println("Hostname for " + ipAddress + ": " + hostName);  // Display the hostname
        } catch (UnknownHostException e) {
            // If the IP address is invalid or cannot be found, display an error message
            System.out.println("Invalid IP Address: " + e.getMessage());
        }
    }
}

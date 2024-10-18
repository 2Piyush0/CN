package subnetting;

import java.util.Scanner;

public class Subnetting {

    // Method to convert an IP address in string format to an integer array
    public static int[] convertToIntArray(String ip) {
        String[] parts = ip.split("\\."); // Split the IP address by dots
        int[] ipAddress = new int[4]; // Array to store each octet of the IP address
        for (int i = 0; i < 4; i++) {
            ipAddress[i] = Integer.parseInt(parts[i]); // Convert each octet from string to integer
        }
        return ipAddress; // Return the integer array representing the IP address
    }

    // Method to print an IP address stored in an integer array format
    public static void printIPAddress(int[] ip) {
        // Print each octet with a dot separator
        System.out.println(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
    }

    // Method to calculate the number of bits needed for the subnet mask based on the number of subnets
    public static int calculateSubnetBits(int subnets) {
        int bits = 0; // Initialize the number of subnet bits
        // Keep increasing the number of bits until 2^bits is greater than or equal to the number of subnets
        while (Math.pow(2, bits) < subnets) {
            bits++;
        }
        return bits; // Return the calculated number of bits
    }

    // Method to calculate the subnet mask based on the number of subnet bits
    public static int[] calculateSubnetMask(int subnetBits) {
        int[] subnetMask = {255, 255, 255, 0}; // Start with the default Class C subnet mask
        int mask = 0; // Initialize the last octet of the subnet mask
        // Set bits in the last octet of the subnet mask according to the number of subnet bits
        for (int i = 0; i < subnetBits; i++) {
            mask += (1 << (7 - i)); // Set bits from left to right
        }
        subnetMask[3] = mask; // Assign the calculated mask to the last octet
        return subnetMask; // Return the subnet mask array
    }

    // Method to calculate the number of usable hosts per subnet
    public static int calculateHostsPerSubnet(int subnetBits) {
        // Calculate 2^(8 - subnetBits) and subtract 2 for network and broadcast addresses
        return (int) Math.pow(2, (8 - subnetBits)) - 2;
    }

    // Method to print the subnet addresses and number of usable hosts per subnet
    public static void printSubnets(int[] ipAddress, int[] subnetMask, int subnets, int hostsPerSubnet) {
        System.out.println("Subnet Addresses:");
        int increment = 256 / subnets; // Calculate the increment for each subnet based on the number of subnets

        // Loop to calculate and print each subnet address
        for (int i = 0; i < subnets; i++) {
            int[] subnetAddress = ipAddress.clone(); // Clone the original IP address
            subnetAddress[3] = i * increment; // Adjust the last octet for each subnet
            printIPAddress(subnetAddress); // Print the subnet address
        }

        // Print the number of usable hosts per subnet
        System.out.println("\nEach subnet can have " + hostsPerSubnet + " usable hosts.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a scanner object for user input

        // Prompt the user to input a Class C IP address
        System.out.println("Enter a Class C IP address (e.g., 192.168.1.0):");
        String ipInput = scanner.nextLine(); // Get the IP address as a string
        int[] ipAddress = convertToIntArray(ipInput); // Convert the IP address to an integer array

        // Prompt the user to input the number of subnets
        System.out.println("Enter the number of subnets you want:");
        int subnets = scanner.nextInt(); // Get the number of subnets as an integer
        int subnetBits = calculateSubnetBits(subnets); // Calculate the number of bits needed for the subnet mask
        int[] subnetMask = calculateSubnetMask(subnetBits); // Calculate the subnet mask based on the subnet bits

        // Print the calculated subnet mask
        System.out.println("Subnet Mask:");
        printIPAddress(subnetMask);

        // Calculate the number of hosts per subnet
        int hostsPerSubnet = calculateHostsPerSubnet(subnetBits);

        // Print the subnet addresses and usable hosts per subnet
        printSubnets(ipAddress, subnetMask, subnets, hostsPerSubnet);

        scanner.close(); // Close the scanner
    }
}

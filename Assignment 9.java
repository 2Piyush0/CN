UDPServer Code with Comments:
package udp;
import java.io.*;
import java.net.*;

public class UDPServer {
    // Define constants for server port and buffer size
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a DatagramSocket bound to the specified port
            socket = new DatagramSocket(PORT);
            System.out.println("UDP Server is running on port " + PORT);

            // Buffer to receive file name
            byte[] receiveBuffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            
            // Wait for a packet containing the file name from the client
            socket.receive(packet);
            String fileName = new String(packet.getData(), 0, packet.getLength()); // Extract file name
            System.out.println("Receiving file: " + fileName);
            
            // Prepare to write the received file to disk
            FileOutputStream fos = new FileOutputStream("received_" + fileName);
            boolean receiving = true;

            // Loop to receive file data from the client
            while (receiving) {
                // Reset the buffer for each packet
                receiveBuffer = new byte[BUFFER_SIZE];
                packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                // Receive packet from the client
                socket.receive(packet);

                // If packet size is zero, it means the transfer is complete
                if (packet.getLength() == 0) {
                    receiving = false;  // Stop receiving
                    System.out.println("File transfer complete.");
                } else {
                    // Write received data to the file
                    fos.write(packet.getData(), 0, packet.getLength());
                }
            }

            fos.close();  // Close the file output stream
        } catch (IOException e) {
            e.printStackTrace();  // Handle exceptions during file reception
        } finally {
            // Ensure socket is closed when the process is complete
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
UDPClient Code with Comments:
package udp;
import java.io.*;
import java.net.*;

public class UDPClient {
    // Define constants for server address, port, and buffer size
    private static final String SERVER_ADDRESS = "localhost"; // Update if server runs on a different machine
    private static final int SERVER_PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a DatagramSocket to send data
            socket = new DatagramSocket();
            
            // Read file path from user input
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the file path to send:");
            String filePath = userInput.readLine();

            // Check if the file exists
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found.");  // Print message if the file is not found
                return;  // Exit the program
            }

            // Get the file name and convert it to bytes
            String fileName = file.getName();
            byte[] fileNameBytes = fileName.getBytes();

            // Send the file name to the server
            DatagramPacket fileNamePacket = new DatagramPacket(fileNameBytes, fileNameBytes.length, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            socket.send(fileNamePacket);
            System.out.println("Sending file: " + fileName);

            // Prepare to read the file and send it in chunks
            FileInputStream fis = new FileInputStream(file);
            byte[] sendBuffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // Loop to read the file and send it as packets to the server
            while ((bytesRead = fis.read(sendBuffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(sendBuffer, bytesRead, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                socket.send(packet);  // Send each packet to the server
            }

            // Send an empty packet to signal the end of the file transfer
            DatagramPacket endPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            socket.send(endPacket);
            System.out.println("File transfer complete.");

            fis.close();  // Close the file input stream
        } catch (IOException e) {
            e.printStackTrace();  // Handle exceptions during file transmission
        } finally {
            // Ensure socket is closed when the process is complete
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

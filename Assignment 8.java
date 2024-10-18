package tcp;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) {
        try {
            // Establish a connection to the server on localhost at port 8080
            Socket socket = new Socket("localhost", 8080);
            System.out.println("Connected to server.");

            // Create input and output streams for communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Use Scanner to read input from the user
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose an option: \n1. Say Hello \n2. File Transfer \n3. Calculator");
            int choice = scanner.nextInt();  // Get the user's choice
            scanner.nextLine();  // Consume the leftover newline

            // Perform different actions based on the user's choice
            switch (choice) {
                case 1:
                    // a. Option to say hello to the server
                    out.println("HELLO");  // Send "HELLO" message to the server
                    String helloResponse = in.readLine();  // Read server's response
                    System.out.println("Server says: " + helloResponse);  // Display server's response
                    break;

                case 2:
                    // b. Option to receive a file from the server
                    out.println("FILE");  // Inform the server that the client wants a file transfer
                    receiveFile(socket, "received_example.txt");  // Call method to receive the file and save it
                    break;

                case 3:
                    // c. Option to perform a calculator operation
                    System.out.println("Enter a calculation (e.g., 12 + 5): ");  // Prompt user to enter a calculation
                    String expression = scanner.nextLine();  // Read user's input expression
                    out.println("CALCULATOR");  // Inform the server that the client wants to use the calculator
                    out.println(expression);  // Send the calculation expression to the server
                    String result = in.readLine();  // Get the calculation result from the server
                    System.out.println("Calculation result: " + result);  // Display the result
                    break;

                default:
                    System.out.println("Invalid choice.");  // Inform the user of an invalid choice
                    break;
            }

            // Close the socket connection after the operations are complete
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();  // Print any exceptions that occur during the process
        }
    }

    // Method to receive a file from the server and save it locally
    private static void receiveFile(Socket socket, String saveAs) {
        try {
            byte[] buffer = new byte[4096];  // Buffer to hold chunks of data during file transfer
            InputStream is = socket.getInputStream();  // Input stream to read data from the server
            FileOutputStream fos = new FileOutputStream(saveAs);  // File output stream to save the received file

            int bytesRead;
            // Read data from the server and write it to the local file
            while ((bytesRead = is.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);  // Write data to file
            }
            fos.close();  // Close the file output stream
            System.out.println("File received and saved as " + saveAs);  // Confirm that the file was received
        } catch (IOException e) {
            e.printStackTrace();  // Print any exceptions that occur during file reception
        }
    }
}

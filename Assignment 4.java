package Go_back_N;

import java.util.Random;
import java.util.Scanner;

public class SlidingWindowProtocol {

    static Random random = new Random(); // Random object to simulate success or failure of frame transmission

    // Method to simulate Go-Back-N sliding window protocol
    public static void goBackN(int totalFrames, int windowSize) {
        int sentFrames = 0; // Tracks how many frames have been sent successfully

        // Loop until all frames are sent
        while (sentFrames < totalFrames) {
            // Calculate the range of frames in the current window
            int windowEnd = Math.min(sentFrames + windowSize, totalFrames);
            System.out.println("\nSending frames " + sentFrames + " to " + (windowEnd - 1));

            // Try sending each frame in the window
            for (int i = sentFrames; i < windowEnd; i++) {
                boolean success = random.nextBoolean(); // Simulate whether frame is successfully sent

                if (success) {
                    // Frame successfully transmitted
                    System.out.println("Frame " + i + " successfully received.");
                } else {
                    // If a frame is lost or damaged, go back and resend the entire window starting from that frame
                    System.out.println("Frame " + i + " lost or damaged. Resending window from frame " + i);
                    sentFrames = i; // Reset the sentFrames to the damaged frame
                    break; // Stop and resend the window
                }

                // If all frames in the window are successfully sent, move the window
                if (i == windowEnd - 1) {
                    sentFrames = windowEnd;
                }
            }
        }

        // All frames sent successfully
        System.out.println("All frames successfully transmitted using Go-Back-N.\n");
    }

    // Method to simulate Selective Repeat sliding window protocol
    public static void selectiveRepeat(int totalFrames, int windowSize) {
        boolean[] received = new boolean[totalFrames]; // Track whether each frame has been successfully received
        int sentFrames = 0; // Tracks how many frames have been sent successfully

        // Loop until all frames are sent
        while (sentFrames < totalFrames) {
            // Calculate the range of frames in the current window
            int windowEnd = Math.min(sentFrames + windowSize, totalFrames);
            System.out.println("\nSending frames " + sentFrames + " to " + (windowEnd - 1));

            // Try sending each frame in the window
            for (int i = sentFrames; i < windowEnd; i++) {
                if (!received[i]) { // If the frame has not been received
                    boolean success = random.nextBoolean(); // Simulate whether the frame is successfully sent

                    if (success) {
                        // Frame successfully transmitted
                        System.out.println("Frame " + i + " successfully received.");
                        received[i] = true; // Mark the frame as received
                    } else {
                        // If a frame is lost or damaged, it will be retransmitted in the next round
                        System.out.println("Frame " + i + " lost or damaged. Will retransmit.");
                    }
                }
            }

            // Slide the window to the next unreceived frame
            while (sentFrames < totalFrames && received[sentFrames]) {
                sentFrames++;
            }
        }

        // All frames sent successfully
        System.out.println("All frames successfully transmitted using Selective Repeat.\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sliding Window Protocol Simulation in Peer-to-Peer Mode");

        // Get the total number of frames to send from the user
        System.out.println("Enter total number of frames to send:");
        int totalFrames = scanner.nextInt();

        // Get the window size from the user
        System.out.println("Enter the window size:");
        int windowSize = scanner.nextInt();

        // Allow the user to choose between Go-Back-N and Selective Repeat modes
        System.out.println("\nChoose a mode:\n1. Go-Back-N\n2. Selective Repeat");
        int mode = scanner.nextInt();

        // Execute the chosen protocol
        switch (mode) {
            case 1:
                goBackN(totalFrames, windowSize);
                break;
            case 2:
                selectiveRepeat(totalFrames, windowSize);
                break;
            default:
                System.out.println("Invalid mode selected.");
        }

        scanner.close();
    }
}

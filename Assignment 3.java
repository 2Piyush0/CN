import java.util.Scanner;

public class HammingCode {

    // Method to encode data using Hamming Code technique
    public static int[] encodeHammingCode(int[] data) {
        int m = data.length;  // Number of data bits
        int r = 0;  // Number of parity bits

        // Calculate the number of parity bits required
        while (Math.pow(2, r) < (m + r + 1)) {
            r++;
        }

        int[] encoded = new int[m + r];  // Array to hold encoded data including parity bits
        int j = 0;  // Parity bit index

        // Fill the encoded array with data and placeholders for parity bits
        for (int i = 1; i <= encoded.length; i++) {
            if (Math.pow(2, j) == i) {  // Check if the current position is a power of 2 (parity bit position)
                j++;  // Skip parity bit positions
            } else {
                encoded[i - 1] = data[m - i + j];  // Insert data bits into non-parity positions
            }
        }

        // Calculate and insert the parity bits
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;  // Calculate parity bit index
            int parityValue = 0;  // Initialize parity bit value

            // Calculate parity for the current parity bit
            for (int k = parityIndex; k < encoded.length; k += (2 * (parityIndex + 1))) {
                for (int l = k; l < k + parityIndex + 1 && l < encoded.length; l++) {
                    parityValue ^= encoded[l];  // XOR the bits to calculate parity
                }
            }

            encoded[parityIndex] = parityValue;  // Set the parity bit value
        }

        return encoded;  // Return the encoded data with parity bits
    }

    // Method to detect and correct errors in Hamming Code
    public static int[] detectAndCorrectHammingCode(int[] encoded) {
        int r = 0;  // Number of parity bits
        int n = encoded.length;  // Total number of bits in the encoded message

        // Calculate the number of parity bits
        while (Math.pow(2, r) < n + 1) {
            r++;
        }

        int errorPosition = 0;  // To store the position of the detected error (if any)

        // Check each parity bit to detect errors
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;  // Get the index of the current parity bit
            int parityValue = 0;  // Initialize parity bit value for checking

            // Calculate parity value again to check for errors
            for (int k = parityIndex; k < n; k += (2 * (parityIndex + 1))) {
                for (int l = k; l < k + parityIndex + 1 && l < n; l++) {
                    parityValue ^= encoded[l];  // XOR the bits to recalculate parity
                }
            }

            // If the calculated parity does not match, add the position to errorPosition
            if (parityValue != 0) {
                errorPosition += parityIndex + 1;  // Position is 1-based, so add 1
            }
        }

        // If error position is non-zero, correct the bit at the detected position
        if (errorPosition != 0) {
            System.out.println("Error detected at position: " + errorPosition);
            encoded[errorPosition - 1] ^= 1;  // Correct the error by flipping the bit
        } else {
            System.out.println("No errors detected.");
        }

        return encoded;  // Return the corrected encoded message
    }

    // Method to extract the original data from the corrected encoded message
    public static int[] extractData(int[] encoded) {
        int r = 0;  // Number of parity bits
        int n = encoded.length;  // Total number of bits in the encoded message

        // Calculate the number of parity bits
        while (Math.pow(2, r) < n + 1) {
            r++;
        }

        int[] data = new int[n - r];  // Array to hold the extracted data bits
        int j = 0;  // Parity bit index

        // Extract the data bits (skipping the parity bits)
        for (int i = 1; i <= n; i++) {
            if (Math.pow(2, j) == i) {  // Skip parity bit positions
                j++;
            } else {
                data[i - j - 1] = encoded[i - 1];  // Copy data bits to the data array
            }
        }

        return data;  // Return the extracted original data
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object for input

        System.out.println("Enter the number of data bits:");
        int dataBits = scanner.nextInt();  // Get the number of data bits from the user

        int[] data = new int[dataBits];  // Array to hold the data bits
        System.out.println("Enter the data bits one by one:");
        for (int i = 0; i < dataBits; i++) {
            data[i] = scanner.nextInt();  // Input data bits from the user
        }

        int[] encoded = encodeHammingCode(data);  // Encode the data using Hamming Code
        System.out.println("Encoded Hamming Code:");
        for (int bit : encoded) {
            System.out.print(bit + " ");  // Print the encoded Hamming Code
        }
        System.out.println();

        // Ask the user if they want to introduce an error
        System.out.println("Introduce error by flipping a bit? (yes/no):");
        String error = scanner.next();
        if (error.equalsIgnoreCase("yes")) {
            System.out.println("Enter the position to flip (1-based index):");
            int position = scanner.nextInt();  // Get the position to flip the bit
            encoded[position - 1] ^= 1;  // Flip the bit at the specified position
        }

        int[] correctedCode = detectAndCorrectHammingCode(encoded);  // Detect and correct any errors
        System.out.println("Corrected Hamming Code:");
        for (int bit : correctedCode) {
            System.out.print(bit + " ");  // Print the corrected Hamming Code
        }
        System.out.println();

        int[] extractedData = extractData(correctedCode);  // Extract the original data
        System.out.println("Extracted original data:");
        for (int bit : extractedData) {
            System.out.print(bit + " ");  // Print the extracted original data
        }
        System.out.println();

        scanner.close();  // Close the scanner object
    }
}

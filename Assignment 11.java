public class RemoteSoftwareInstaller {

    public static void main(String[] args) {
        // Define the target host, user credentials, and the command to be executed remotely
        String host = "192.168.1.100";  // IP address of the remote machine
        String user = "admin";  // Username for SSH login
        String password = "password123";  // Password for SSH login
        String command = "sudo apt-get install -y curl";  // Command to install software (curl) on the remote machine

        try {
            // Set up JSch session (used for SSH connection)
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);  // Establish session with SSH server on port 22
            session.setPassword(password);  // Set the user's password for authentication

            // Skip host key checking (not recommended for production for security reasons)
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();  // Establish the connection

            // Open an execution channel to run a command on the remote server
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);  // Set the command to execute (installing curl in this case)

            // Capture command output (response from the remote machine)
            InputStream in = channel.getInputStream();  // Get the input stream to capture output from the command
            channel.connect();  // Connect the channel to execute the command

            // Read the command output and print it to the console
            byte[] tmp = new byte[1024];  // Buffer to store output data
            while (in.read(tmp) != -1) {  // Continue reading until no more data is available
                System.out.print(new String(tmp));  // Print the output as a string
            }

            // Close the channel and session after command execution
            channel.disconnect();  // Disconnect the command execution channel
            session.disconnect();  // Disconnect the SSH session
            System.out.println("Software installed successfully!");  // Inform the user that the software installation was successful

        } catch (Exception e) {
            // Handle any exceptions that occur during SSH connection or command execution
            e.printStackTrace();  // Print the stack trace for debugging
        }
    }
}

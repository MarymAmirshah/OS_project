//Ali Bokaei
//Maryam Amir Shahkarami

import java.io.*;
import java.util.Arrays;

public class Main {
    private static final String HISTORY_FILE_PATH = ".history";
    private static final int MAX_LINE = 80;

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // Displaying shell prompt
            System.out.print("OSshell$ ");
            System.out.flush();

            // Getting user input
            String input = reader.readLine();

            // Checking for exit command
            if (input.equals("exit")) {
                break;
            }

            // Handling history command
            if (input.equals("history")) {
                displayHistory();
                continue;
            }

            // Handling other commands
            executeCommand(input);
        }
    }

    private static void executeCommand(String input) throws IOException, InterruptedException {
        // Creating a process builder
        ProcessBuilder processBuilder = new ProcessBuilder(input.split("\\s+"));
        processBuilder.redirectErrorStream(true);

        // Starting the process
        Process process = processBuilder.start();

        // Waiting for the process to finish
        int exitCode = process.waitFor();

        // Saving the command in history
        saveCommand(input);

        // Displaying exit code
        System.out.println("Exit code: " + exitCode);
    }

    private static void saveCommand(String command) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE_PATH, true))) {
            writer.println(command);
        }
    }

    private static void displayHistory() throws IOException {
        try (BufferedReader historyReader = new BufferedReader(new FileReader(HISTORY_FILE_PATH))) {
            String line;
            int lineNumber = 1;

            while ((line = historyReader.readLine()) != null) {
                System.out.println(lineNumber + ". " + line);
                lineNumber++;
            }
        }
    }
}

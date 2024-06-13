import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Main <input_file> <output_file>");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            Bank bank = readInputFile(inputFilePath);
            List<Integer> results = bank.processQueries();
            writeOutputFile(outputFilePath, results);
        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Bank readInputFile(String inputFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String[] initialLine = reader.readLine().split(" ");
            int initialReserve = Integer.parseInt(initialLine[0]);
            int numTransactions = Integer.parseInt(initialLine[1]);
            int numQueries = Integer.parseInt(initialLine[2]);

            Bank bank = new Bank(initialReserve);

            for (int i = 0; i < numTransactions; i++) {
                String[] transactionLine = reader.readLine().split(" ");
                int timestamp = Integer.parseInt(transactionLine[0]);
                TransactionType type = TransactionType.valueOf(transactionLine[1].toUpperCase());
                int amount = Integer.parseInt(transactionLine[2]);
                bank.addTransaction(new Transaction(timestamp, type, amount));
            }

            for (int i = 0; i < numQueries; i++) {
                String[] queryLine = reader.readLine().split(" ");
                int startTime = Integer.parseInt(queryLine[1]);
                int endTime = Integer.parseInt(queryLine[2]);
                bank.addQuery(new Query(startTime, endTime));
            }

            return bank;
        }
    }

    private static void writeOutputFile(String outputFilePath, List<Integer> results) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int result : results) {
                writer.write(result + "\n");
            }
        }
    }
}
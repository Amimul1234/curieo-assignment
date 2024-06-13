public class Transaction {

    private final int amount;
    private final String type;
    private final int timestamp;

    public Transaction(int timestamp, String type, int amount) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private final int initialReserve;
    private final List<Query> queries;
    private final List<Transaction> transactions;

    public Bank(int initialReserve) {
        this.queries = new ArrayList<>();
        this.initialReserve = initialReserve;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void addQuery(Query query) {
        queries.add(query);
    }

    public List<Integer> processQueries() {
        List<Integer> results = new ArrayList<>();
        for (Query query : queries) {
            results.add(processQuery(query));
        }
        return results;
    }

    private int processQuery(Query query) {
        int reserve = initialReserve;
        int declinedTransactions = 0;
        List<Transaction> deposits = new ArrayList<>();
        List<Transaction> withdrawals = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.timestamp() >= query.startTime() && transaction.timestamp() < query.endTime()) {
                if (transaction.type() == TransactionType.DEPOSIT) {
                    deposits.add(transaction);
                } else {
                    withdrawals.add(transaction);
                }
            }
        }

        for (Transaction deposit : deposits) {
            reserve += deposit.amount();
        }

        for (Transaction transaction : transactions) {
            if (transaction.timestamp() < query.startTime() || transaction.timestamp() >= query.endTime()) {
                if (transaction.type() == TransactionType.DEPOSIT) {
                    reserve += transaction.amount();
                } else {
                    if (reserve >= transaction.amount()) {
                        reserve -= transaction.amount();
                    }
                }
            }
        }

        for (Transaction withdrawal : withdrawals) {
            if (reserve >= withdrawal.amount()) {
                reserve -= withdrawal.amount();
                declinedTransactions++;
            }
        }

        return declinedTransactions;
    }
}

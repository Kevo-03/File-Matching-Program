import java.io.Serializable;

public class TransactionRecord implements Serializable
{
    private int accountNumber;
    private double transactionAmount;
    
    public TransactionRecord()
    {
        this(0,0.0);
    }

    public TransactionRecord(int accountNo, double transaction)
    {
        setAccNo(accountNo);
        setTransaction(transaction);
    }

    public void setAccNo(int accountNo)
    {
        accountNumber = accountNo;
    }

    public void setTransaction(double transaction)
    {
        transactionAmount = transaction;
    }

    public int getAccNo()
    {
        return accountNumber;
    }

    public double getTransaction()
    {
        return transactionAmount;
    }
}

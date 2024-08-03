import java.util.List;
import java.util.ListIterator;
import java.io.IOException;
import java.util.Formatter;

public class LogFileThread implements Runnable
{
    private List<AccountRecord> accountList;
    private List<TransactionRecord> transactionList;
    private Formatter outputLog;

    public LogFileThread(List<AccountRecord> aList, List<TransactionRecord> tList)
    {
        accountList = aList;
        transactionList = tList;
    }

    public void openLogFile()
    {
        try
        {
            outputLog = new Formatter("log.txt");
        }
        catch(IOException ioException)
        {
            System.err.println("Error opening file");
        }
    }

    public void closeLogFile()
    {
        if(outputLog != null)
            outputLog.close();
    }

    public void addToLog()
    {
       ListIterator <TransactionRecord> transactionIterator = transactionList.listIterator();

        while(transactionIterator.hasNext())
        {
            boolean match = false;
            TransactionRecord transactionRecord = transactionIterator.next();
            ListIterator <AccountRecord> accountIterator = accountList.listIterator();

            while (accountIterator.hasNext()) 
            {
                AccountRecord accountRecord = accountIterator.next();
                
                if(transactionRecord.getAccNo() == accountRecord.getAccNo())
                    match = true;
            }

            if(!match)
                outputLog.format("Unmatched transaction record for account number %d\n", transactionRecord.getAccNo());
        }
    }

    public void run()
    {
        openLogFile();
        addToLog();
        closeLogFile();
    }
}

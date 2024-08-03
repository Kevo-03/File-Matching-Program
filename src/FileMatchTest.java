import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class FileMatchTest 
{   
    public static void main(String[] args)
    {
        FileMatch app = new FileMatch();
        app.openAccountsFile();
        app.processAccountRecords();
        app.closeAccountsFile();

        app.openTransactionFile();
        app.processTransactionRecords();
        app.closeTransactionFile();

        List<AccountRecord> aListForMast = app.getAccountRecords();
        List<TransactionRecord> tListForMast = app.getTransactionRecords();
        List<AccountRecord> aListForLog = app.getAccountRecords();
        List<TransactionRecord> tListForLog = app.getTransactionRecords();

        ExecutorService application = Executors.newCachedThreadPool();

        application.execute(new NewMastThread(aListForMast, tListForMast));
        application.execute(new LogFileThread(aListForLog, tListForLog));

    }    
}

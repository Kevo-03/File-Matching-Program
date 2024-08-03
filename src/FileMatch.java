import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.util.Formatter;

public class FileMatch 
{
    private ObjectInputStream inputAcc;
    private ObjectInputStream inputTrans;
    private ObjectInputStream inputNewMast;

    private ObjectOutputStream outputNewMast;
    private Formatter outputLog;

    private List<AccountRecord> accountList = new ArrayList<AccountRecord>();
    private List<TransactionRecord> transactionList = new ArrayList<TransactionRecord>();

    public void openAccountsFile()
    {
        try
        {
            inputAcc = new ObjectInputStream( new FileInputStream("oldmast.ser"));
        }
        catch(IOException ioException)
        {
            System.err.println("Error opening file");
        }
    }

    public void openTransactionFile()
    {
        try
        {
            inputTrans = new ObjectInputStream(new FileInputStream("trans.ser"));
        }
        catch(IOException ioException)
        {
            System.err.println("Error opening file");
        }
    }

    public void openNewMastFile()
    {
        try
        {
            outputNewMast = new ObjectOutputStream(new FileOutputStream("newmast.ser"));
        }
        catch(IOException ioException)
        {
            System.err.println("Error opening file");
        }
    } 

    public void openNewMastToRead()
    {
        try
        {
            inputNewMast = new ObjectInputStream(new FileInputStream("newmast.ser"));
        }
        catch(IOException ioException)
        {
            System.err.println("Error opening file");
        }
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

    public void processAccountRecords()
    {
        AccountRecord accountRecord;
        
        try
        {
            while(true)
            {
                accountRecord = (AccountRecord) inputAcc.readObject();
                accountList.add(accountRecord);
            }
        }
        catch(EOFException endOfFilEofException)
        {
            return;
        }
        catch(ClassNotFoundException classNotFoundException)
        {
            System.err.println("Unable to create object");
        }
        catch(IOException ioException)
        {
            System.err.println("Error during reading file");
        }
    }

    public void processTransactionRecords()
    {
        TransactionRecord transRecord;

        try
        {
            while(true)
            {
                transRecord = (TransactionRecord) inputTrans.readObject();
                transactionList.add(transRecord);
            }
        }
        catch(EOFException endOfFilEofException)
        {
            return;
        }
        catch(ClassNotFoundException classNotFoundException)
        {
            System.err.println("Unable to create object");
        }
        catch(IOException ioException)
        {
            System.err.println("Error during reading file");
        }
    }

    public void addNewMast()
    {
        ListIterator <AccountRecord> accountIterator = accountList.listIterator();


        while(accountIterator.hasNext())
        {
            double balanceChange = 0.0;
            AccountRecord accountRecord = accountIterator.next();
            ListIterator <TransactionRecord> transactionIterator = transactionList.listIterator();
            while(transactionIterator.hasNext())
            {
                TransactionRecord transactionRecord = transactionIterator.next();

                if(accountRecord.getAccNo() == transactionRecord.getAccNo())
                    balanceChange += transactionRecord.getTransaction();
            }
            
            double newBalance;
            newBalance = accountRecord.getBalance() + balanceChange;
            accountRecord.setBallance(newBalance);
            try
            {
                outputNewMast.writeObject(accountRecord);
            }
            catch(IOException ioException)
            {
                System.err.println("Error writing to file");
            }
        }
    }

    public void readNewMast()
    {
        AccountRecord accountRecord;
        System.out.printf( "%-10s%-12s%-12s%10s\n", "Account","First Name", "Last Name", "Balance" );
        
        try
        {
            while(true)
            {
                accountRecord = (AccountRecord) inputNewMast.readObject();

                System.out.printf( "%-10d%-12s%-12s%10.2f\n",
                accountRecord.getAccNo(), accountRecord.getFirstName(),
                accountRecord.getLastName(), accountRecord.getBalance() );
            }
        }
        catch(EOFException endOfFilEofException)
        {
            return;
        }
        catch(ClassNotFoundException classNotFoundException)
        {
            System.err.println("Unable to create object");
        }
        catch(IOException ioException)
        {
            System.err.println("Error during reading file");
        }
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

    public void closeAccountsFile()
    {
        try
        {
            if(inputAcc != null)
                inputAcc.close();
        }
        catch(IOException ioException)
        {
            System.err.println("Error closing file");
            System.exit(1);
        }
    }

    public void closeTransactionFile()
    {
        try
        {
            if(inputTrans != null)
                inputTrans.close();
        }
        catch(IOException ioException)
        {
            System.err.println("Error closing file");
            System.exit(1);
        }
    }

    public void closeNewMastOut()
    {
        try
        {
            if(outputNewMast != null)
                outputNewMast.close();
        }
        catch(IOException ioException)
        {
            System.err.println("Error closing file");
            System.exit(1);
        }   
    }

    public void closeNewMastIn()
    {
        try
        {
            if(inputNewMast != null)
                inputNewMast.close();
        }
        catch(IOException ioException)
        {
            System.err.println("Error closing file");
            System.exit(1);
        }   
    }

    public void closeLogFile()
    {
        if(outputLog != null)
            outputLog.close();
    }

    public List<AccountRecord> getAccountRecords()
    {
        return accountList;
    }

    public List<TransactionRecord> getTransactionRecords()
    {
        return transactionList;
    }
}

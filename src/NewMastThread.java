import java.util.List;
import java.util.ListIterator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;

public class NewMastThread implements Runnable 
{
    private List<AccountRecord> accountList;
    private List<TransactionRecord> transactionList;
    
    private ObjectInputStream inputNewMast;
    private ObjectOutputStream outputNewMast;

    public NewMastThread(List<AccountRecord> aList, List<TransactionRecord> tList)
    {
        accountList = aList;
        transactionList = tList;
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

    public void run()
    {
        openNewMastFile();
        addNewMast();
        closeNewMastOut();
        openNewMastToRead();
        readNewMast();
        closeNewMastIn();
    }
}

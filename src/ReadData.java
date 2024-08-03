import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.EOFException;

public class ReadData 
{
    private ObjectInputStream inputAcc;
    private ObjectInputStream inputTrans;

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

    public void readAccountRecords()
    {
        AccountRecord accountRecord;
        System.out.printf( "%-10s%-12s%-12s%10s\n", "Account","First Name", "Last Name", "Balance" );
        
        try
        {
            while(true)
            {
                accountRecord = (AccountRecord) inputAcc.readObject();

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

    public void readTransactionRecords()
    {
        TransactionRecord transRecord;
        System.out.printf( "%-10s%10s\n", "Account", "Balance" );

        try
        {
            while(true)
            {
                transRecord = (TransactionRecord) inputTrans.readObject();

                System.out.printf( "%-10d%10.2f\n",
                transRecord.getAccNo(), 
                transRecord.getTransaction() );
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
}

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CreateData 
{
    private ObjectOutputStream outputAcc;
    private ObjectOutputStream outputTrans;

    public void openAccountsFile()
    {
        try
        {
            outputAcc = new ObjectOutputStream( new FileOutputStream("oldmast.ser"));
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
            outputTrans = new ObjectOutputStream(new FileOutputStream("trans.ser"));
        }
        catch(IOException ioException)
        {
            System.err.println("Error opening file");
        }
    }

    public void addAccountRecords()
    {
        AccountRecord accountRecord;

        int accountNumber = 0;
        String firstName;
        String lastName;
        double balance;

        Scanner input = new Scanner(System.in);

        System.out.printf( "%s\n%s\n%s\n%s\n\n",
        "To terminate input, type the end-of-file indicator ",
        "when you are prompted to enter input.",
        "On UNIX/Linux/Mac OS X type <ctrl> d then press Enter",
        "On Windows type <ctrl> z then press Enter" );

        System.out.printf( "%s\n%s",
        "Enter account number (> 0), first name, last name and balance.",
        "? " );

        while(input.hasNext())
        {
            try
            {
                accountNumber = input.nextInt();
                firstName = input.next();
                lastName = input.next();
                balance = input.nextDouble();

                if(accountNumber > 0)
                {
                    accountRecord = new AccountRecord(accountNumber,firstName,lastName,balance);
                    outputAcc.writeObject(accountRecord);
                }
                else
                {
                    System.out.println("Account number must be greater than 0");
                }
            }
            catch(IOException ioException)
            {
                System.err.println("Error writing to file");
                return;
            }
            catch(NoSuchElementException elementException)
            {
                System.err.println("Invalid input please try again");
                input.nextLine();
            }
            System.out.printf( "%s %s\n%s", "Enter account number (>0),","first name, last name and balance.", "? " );
        }
    }

    public void addTransactionRecords()
    {
        TransactionRecord transRecord;

        int accountNumber = 0;
        double transaciton;

        Scanner input = new Scanner(System.in);

        System.out.printf( "%s\n%s\n%s\n%s\n\n",
        "To terminate input, type the end-of-file indicator ",
        "when you are prompted to enter input.",
        "On UNIX/Linux/Mac OS X type <ctrl> d then press Enter",
        "On Windows type <ctrl> z then press Enter" );

        System.out.printf( "%s\n%s",
        "Enter account number (> 0) and balance.",
        "? " );

        while(input.hasNext())
        {
            try
            {
                accountNumber = input.nextInt();
                transaciton = input.nextDouble();

                if(accountNumber > 0)
                {
                    transRecord = new TransactionRecord(accountNumber,transaciton);
                    outputTrans.writeObject(transRecord);
                }
                else
                {
                    System.out.println("Account number must be greater than 0");
                }
            }
            catch(IOException ioException)
            {
                System.err.println("Error writing to file");
                return;
            }
            catch(NoSuchElementException elementException)
            {
                System.err.println("Invalid input please try again");
                input.nextLine();
            }
            System.out.printf( "%s \n%s", "Enter account number (>0), and balance.", "? " );
        }
    }


    public void closeAccountsFile()
    {
        try
        {
            if(outputAcc != null)
                outputAcc.close();
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
            if(outputTrans != null)
                outputTrans.close();
        }
        catch(IOException ioException)
        {
            System.err.println("Error closing file");
            System.exit(1);
        }
    }
}

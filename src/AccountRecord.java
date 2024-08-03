import java.io.Serializable;

public class AccountRecord implements Serializable
{
    private int accountNumber;
    private String firstName;
    private String lastName;
    private double balance;

    public AccountRecord()
    {
        this(0,"","",0.0);
    }

    public AccountRecord(int account, String name, String surname, double bal)
    {
        setAccNo(account);
        setFirstName(surname);
        setLastName(surname);
        setBallance(bal);
    }

    public void setAccNo(int account)
    {
        accountNumber = account;
    }

    public void setFirstName(String name)
    {
        firstName = name;
    }

    public void setLastName(String surname)
    {
        lastName = surname;
    }

    public void setBallance(double bal)
    {
        balance = bal;
    }

    public int getAccNo()
    {
        return accountNumber;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public double getBalance()
    {
        return balance;
    }
}

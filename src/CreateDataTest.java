public class CreateDataTest 
{
    public static void main(String[] args)
    {
        CreateData createData = new CreateData();

        createData.openAccountsFile();
        createData.addAccountRecords();
        createData.closeAccountsFile();
    }
}

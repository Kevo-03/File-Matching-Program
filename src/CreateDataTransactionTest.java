public class CreateDataTransactionTest 
{
    public static void main(String[] args)
    {
        CreateData createData = new CreateData();

        createData.openTransactionFile();
        createData.addTransactionRecords();
        createData.closeTransactionFile();
    }
}

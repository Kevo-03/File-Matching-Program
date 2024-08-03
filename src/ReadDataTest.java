public class ReadDataTest 
{
    public static void main(String[] args)
    {
        ReadData readData = new ReadData();
        readData.openAccountsFile();
        readData.readAccountRecords();
        readData.closeAccountsFile();
    }   
}

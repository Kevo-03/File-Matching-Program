public class ReadTransactionDataTest 
{
    public static void main(String[] args)
    {
        ReadData readData = new ReadData();
        readData.openTransactionFile();
        readData.readTransactionRecords();
        readData.closeTransactionFile();
    }   
}

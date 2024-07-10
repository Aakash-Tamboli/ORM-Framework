class DataManager
{
private static DataManager dataManager=null;

private DataManager()
{
// So that nobody makes multiple object of this class
}

public static DataManager getDataManager()
{
if(dataManager!=null) return dataManager;
File file=new File("config.json");

} // method braces close

private static Config loadConfigJson()
{
Config config=null;
try
{
Gson gson=new Gson();
File file=new File("/home/aakash/javaeg/orm/config.json");
if(!file.exists())
{
System.out.println("Config.json does not exists");
return null;
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
StringBuffer stringBuffer=new StringBuffer();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
stringBuffer.append((char)randomAccessFile.read());
}
String json=stringBuffer.toString();
config=gson.fromJson(json,Config.class);
randomAccessFile.close();
}catch(Exception exception)
{
System.out.println("Exception Occured in (loadConfigJson) :"+exception.getMessage());
}
return config;
}





} // class braces close

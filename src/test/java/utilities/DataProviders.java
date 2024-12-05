package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

//DataProviders is class which contains only DataProvider methods
//we need only one DataProvider method

public class DataProviders {

//Previously DataProvider method I have hard coded data in 2 dimensional array that we have return but this time we get the data from Excel file
	//DataProvider 1
	
	@DataProvider(name="LoginData")//this name should be different from one DataProvider to another DataProvider
	public String [][] getData() throws IOException
	{

//System.getProperty("user.dir") this is similar as [.] dot is representing as a current project location 
		
		String path=".\\testData\\Opencart_LoginData.xlsx";//taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		String logindata[][]=new String[totalrows][totalcols];//created for two dimension array which can store the data user and password
		
		for(int i=1;i<=totalrows;i++)  //1   //read the data from xl storing in two deminsional array
		{		
			for(int j=0;j<totalcols;j++)  //0    i is rows j is col
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  //1,0
			}
		}
	return logindata;//returning two dimension array
				
	}
	
//if we need more DataProvider methods for other test cases we can keep adding more DataProvider methods in this same class
	
	//DataProvider 2
	
	//DataProvider 3
	
	//DataProvider 4
}

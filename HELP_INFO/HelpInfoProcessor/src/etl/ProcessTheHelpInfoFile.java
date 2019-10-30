package etl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ProcessTheHelpInfoFile 
{
	public static  String strHelpFilePath = "C:\\Users\\rrose66\\Desktop\\";
	public static  String strHelpFileName = "HelpInfo-WGC102FRF32Z2";
	public static  String strHelpFileType = ".txt";
	public static String strTargetHelpInfoOutputs="C:\\PROJECTS\\DATA\\HELP_INFO\\LOCAL_HELP_INFO_TREND.TXT";
	public static String strTargetArchivedHelpInfoPath = "Y:\\ARCHIVES\\HELPINFO\\";
	public static void main(String[] args) 
	{
		String strCreatedDate=null;
		String[] aryCreatedDate=null;
		String[] aryCreatedDateWithHour=null;
		String[] aryCreatedDateYYYYMMDD=null;
		String strInputData=null;
		Boolean blnHeader1=false;
		Boolean blnHeader2=false;
		String strHeader1=null;
		String strHeader2=null;
		String strHeader3=null;
		String strVariableName=null;
		String strValue=null;
		Integer intCountOfColon=0;
		Integer intDialogButton=0;
		intDialogButton=JOptionPane.YES_NO_CANCEL_OPTION;
		String strMsg = "Click Yes to process the HelpInfo file";
		intDialogButton=JOptionPane.showConfirmDialog(null, strMsg,"Populate the HelpInfo saved file?",intDialogButton);
		if(intDialogButton == 0)
		{
		
//			File fileHelpInfo = new File("C:\\Users\\rrose66\\Desktop\\HelpInfo-WGC1AA3CQJ3M2.txt");
			File fileHelpInfo = new File(strHelpFilePath + strHelpFileName + strHelpFileType);
//			fileHelpInfo = new File("C:\\Users\\rrose66\\Desktop\\HelpInfo-WGC102FRF32Z2.txt");
			Scanner scHelpInfo = null;
			try 
			{
				scHelpInfo = new Scanner(fileHelpInfo);
			} 
			catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}
			while (scHelpInfo.hasNextLine())
			{
				strInputData=scHelpInfo.nextLine();
				blnHeader1=isThisHeader1(strInputData);
				blnHeader2=isThisHeader2(strInputData);
				if (strInputData.contains("Date Created"))
				{
					aryCreatedDate=strInputData.split(":");
					aryCreatedDateWithHour=aryCreatedDate[1].split("-");
					aryCreatedDateYYYYMMDD = aryCreatedDateWithHour[2].split(" ");
					strCreatedDate = aryCreatedDateWithHour[0].trim() + aryCreatedDateWithHour[1] + aryCreatedDateYYYYMMDD[0];
				}
				if (blnHeader1)
				{
					strHeader1 = getHeader1(strInputData);
					blnHeader1 = false;
				}
				if (blnHeader2)
				{
					strHeader2 = getHeader2(strInputData);
					blnHeader2 = false;
				}
				else
				{
					if (strHeader2 != null)
					{					
						if(strInputData.contains(":"))
						{
							intCountOfColon = countOfColons(strInputData);
							if(intCountOfColon==1)
							{
								strVariableName = getVariableName(strInputData);
								strValue = getValue(strInputData);
								try 
								{
									FileWriter fwHelpInfo = new FileWriter(strTargetHelpInfoOutputs,true);
//									FileWriter fwHelpInfo = new FileWriter("C:\\PROJECTS\\DATA\\HELP_INFO\\LOCAL_HELP_INFO_TREND.TXT",true);
	//								find the max scanned date and store it in dtLastScanned
	//								use it to search for each header1 + header2 + variable name + value
	//								determine if the value changed
	//								if the value changed record the old and new
									
									PrintWriter pwHelpInfo = new PrintWriter(fwHelpInfo);
	//								strVariableName = strVariableName.replace(",", " ");
									strVariableName = CleanData(strVariableName);
	//								strValue = strValue.replace(",", " ");
									strValue = CleanData(strValue);
									pwHelpInfo.println(strCreatedDate + "," + strHeader1 + "," + strHeader2 + ",," + strVariableName + "," + strValue );
									pwHelpInfo.close();
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
								
							}
							else if (intCountOfColon==2)
							{
								strHeader3 = getVariableName(strInputData);
								strInputData=strInputData.substring(strInputData.indexOf(":")+1,strInputData.length());
								strVariableName = getVariableName(strInputData);
								strValue = getValue(strInputData);
								try 
								{
									FileWriter fwHelpInfo = new FileWriter(strTargetHelpInfoOutputs,true);
//									FileWriter fwHelpInfo = new FileWriter("C:\\PROJECTS\\DATA\\HELP_INFO\\LOCAL_HELP_INFO_TREND.TXT",true);
	//								find the max scanned date and store it in dtLastScanned
	//								use it to search for each header1 + header2 + variable name + value
	//								determine if the value changed
	//								if the value changed record the old and new
									
									PrintWriter pwHelpInfo = new PrintWriter(fwHelpInfo);
	//								strVariableName = strVariableName.replace(",", " ");
									strVariableName = CleanData(strVariableName);
	//								strValue = strValue.replace(",", " ");
									strValue = CleanData(strValue);
									pwHelpInfo.println(strCreatedDate + "," + strHeader1 + "," + strHeader2 + "," + strHeader3 + "," + strVariableName + "," + strValue );
									pwHelpInfo.close();
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			scHelpInfo.close();
//			File fileOriginal = new File (strHelpFilePath + strHelpFileName + strHelpFileType);
//			File fileOriginal = new File ("C:\\Users\\rrose66\\Desktop\\HelpInfo-WGC102FRF32Z2.txt");
//			File fileTarget = new File (strTargetArchivedHelpInfoPath  + strHelpFileName + "_" +  getStringTodayYYYYMMDD() + strHelpFileType);
//			File fileTarget = new File ("Y:\\ARCHIVES\\HELPINFO\\" + getStringTodayYYYYMMDD() + "_HelpInfo-WGC102FRF32Z2.txt");
//			String fileSource = strHelpFilePath + strHelpFileName + strHelpFileType;
//			File fileDestination = new File (strTargetArchivedHelpInfoPath  + strHelpFileName + "_" +  getStringTodayYYYYMMDD() + strHelpFileType);
//			fileOriginal.renameTo(fileDestination);

			try 
			{
				Path path = FileSystems.getDefault().getPath(strHelpFilePath, strHelpFileName + strHelpFileType);
				Path newPath = FileSystems.getDefault().getPath(strTargetArchivedHelpInfoPath, strHelpFileName + "_" + getStringTodayYYYYMMDD() + strHelpFileType);
				Path outputPath =	Files.move(path, newPath,StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println(outputPath);
			} 
			catch (IOException e2) 
			{
			
				e2.printStackTrace();
			}
//			String fileDestination="Y:\\ARCHIVES\\HELPINFO\\" + getStringTodayYYYYMMDD() + "_HelpInfo-WGC102FRF32Z2.txt";
//			DeleteThisFile("C:\\PROJECTS\\EXECUTABLES\\Dynamic.bat");
//			File batFile = new File("C:\\PROJECTS\\EXECUTABLES\\Dynamic.bat");
//			java.lang.Process runProcess;
//			FileWriter fw = null;
//			try 
//			{
//				fw = new FileWriter(batFile);
//			} 
//			catch (IOException e1) 
//			{
//				e1.printStackTrace();
//			}
////			BufferedWriter bw = new BufferedWriter(fw);
//			try 
//			{
////				bw.write("move /Y \"" + fileSource + "\", \"" + fileDestination+"\"");
//				bw.write("copy /Y \"" + fileSource + "\", \"" + fileDestination+"\"");
//				bw.close();
//			} 
//			catch (IOException e1) 
//			{
//				e1.printStackTrace();
//			}
//			WaitUntilThisFileIsWritable("C:\\PROJECTS\\EXECUTABLES\\Dynamic.bat");
//			try 
//			{
////				runProcess = Runtime.getRuntime().exec("C:\\PROJECTS\\EXECUTABLES\\Dynamic.bat");
//				Runtime.getRuntime().exec("C:\\PROJECTS\\EXECUTABLES\\Dynamic.bat");
//			} 
//			catch (IOException e) 
//			{
//				e.printStackTrace();
//			}
		}
	}
		public static String CleanData(String strData)
	{
		String[] aryData=null;
		String strOutData=null;
		aryData=strData.split("");
		Integer intArrayPosition=0;
		for(intArrayPosition=0;intArrayPosition < strData.length();intArrayPosition++)
		{
			if(aryData[intArrayPosition].matches(","))
			{
				strOutData=strOutData + " "; 				
			}
			else
			{
				strOutData=strOutData + aryData[intArrayPosition]; 
			}
		}
		return strOutData;
	}
	public static void DeleteThisFile(String strFullPath)
	{
		Boolean blnReadyToContinue=false;
		File fileToDelete = new File(strFullPath);
		if(fileToDelete.exists())
		{
			fileToDelete.delete();
		}
	}
	public static Integer countOfColons(String strInputData)
	{
		Integer intArrayPosition=0;
		Integer intCountOfColons=0;
		String[] aryInputData=null;
		aryInputData=strInputData.split("");
		for (intArrayPosition=0;intArrayPosition < strInputData.length()-1;intArrayPosition++)
		{
			if(aryInputData[intArrayPosition].matches(":"))
			{
				intCountOfColons++;
			}
		}
		return intCountOfColons;
	}
	public static Boolean isThisHeader1(String strInputData)
	{
		if(strInputData.contains("***************"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static String getStringTodayYYYYMMDD()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date dateFile = new Date();
		return dateFormat.format(dateFile);
	}
	public static String getValue(String strInputData)
	{
		String[] aryInputData=null;
		aryInputData=strInputData.split(":");
		return aryInputData[1];
	}
	public static String getVariableName(String strInputData)
	{
		String[] aryInputData=null;
		aryInputData=strInputData.split(":");
		return aryInputData[0];
	}
	public static String getHeader1(String strInputData)
	{
		String[] aryHeader=null;
		aryHeader=strInputData.split(" ");
		return  aryHeader[2];
	}
	public static String getHeader2(String strInputData)
	{
		String[] aryHeader=null;
		aryHeader=strInputData.split(" ");
		return  aryHeader[1];
	}
	public static boolean isThisHeader2(String strInputData)
	{
		if(!strInputData.isEmpty())
		{
			if(strInputData.contains("=========="))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	public static void WaitUntilThisFileIsWritable(String strFullPath)
	{
		Boolean blnReadyToContinue=false;
		while(blnReadyToContinue==false)
		{
			File f = new File(strFullPath);
			try 
			{
				Thread.sleep(6000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			if(f.canWrite())
			{
				blnReadyToContinue=true;
			}
		}


	}

}

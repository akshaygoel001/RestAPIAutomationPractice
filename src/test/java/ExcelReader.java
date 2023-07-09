

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public static FileInputStream fs;
	public static XSSFWorkbook workbook;
	
	public static void main(String[] args) {
		
		try {
			fs=new FileInputStream("D:\\Projects\\Bench\\Data.xlsx");
			workbook=new XSSFWorkbook(fs);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int sheetCount=workbook.getNumberOfSheets();
		System.out.println(sheetCount);
		List<String> list=new ArrayList<String>();
		for(int i=0;i<sheetCount;i++) { 
			int rowCount=workbook.getSheetAt(i).getLastRowNum();
			for(int j=0;j<rowCount+1;j++) {
				int colCount=workbook.getSheetAt(i).getRow(j).getLastCellNum();
				for(int k=0;k<colCount;k++) {
					String updateTC=workbook.getSheetAt(i).getRow(j).getCell(k).getStringCellValue();
					if(updateTC.equalsIgnoreCase("Update")) {
						for(int l=1;l<colCount;l++) {
							String data=workbook.getSheetAt(i).getRow(j).getCell(l).getStringCellValue();
							list.add(data);
						}
					}
				}
			}
		}
		System.out.println(list);
		
		
		 
		
	}

}

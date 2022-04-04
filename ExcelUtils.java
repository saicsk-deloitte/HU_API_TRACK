package MAIN_ASSIGNMENT.Excel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
public class ExcelUtils {
    public static String name;
    public static String email;
    public static String password;
    public static String age;
    public static String token;
    @Test(priority =1)
    public static ArrayList readCells() throws IOException {
        DataFormatter dataFormatter = new DataFormatter();
        String excelFilePath = "C:\\Users\\Chamkumar\\Desktop\\XYZBank.xlsx";
        //reading data from the Excel using Apache POI method
        FileInputStream input = new FileInputStream(excelFilePath);
        XSSFWorkbook wb = new XSSFWorkbook(input);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum() + 1;
        for (int i = 1; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            name = row.getCell(0).getStringCellValue();
            email = dataFormatter.formatCellValue(sheet.getRow(i).getCell(1));
            password = dataFormatter.formatCellValue(sheet.getRow(i).getCell(2));
            age = dataFormatter.formatCellValue(sheet.getRow(i).getCell(3));
            token = dataFormatter.formatCellValue(sheet.getRow(i).getCell(4));
        }
        //Using ArrayList for collecting data from Excel
        ArrayList al = new ArrayList();
        //calling add method on the ArrayList (adding pointers)
        al.add(name);
        al.add(email);
        al.add(password);
        al.add(age);
        al.add(token);
        return al;
    }
    //Registering Token
    @Test(priority = 2)
    public ArrayList registerToken(String RegisterToken) throws Exception {
        String excelFilePath = "C:\\Users\\Chamkumar\\Desktop\\XYZBank.xlsx";
        FileInputStream input = new FileInputStream(excelFilePath);
        XSSFWorkbook wb = new XSSFWorkbook(input);
        XSSFSheet sheet = wb.getSheetAt(0);
        Cell cell = null;
        cell = sheet.getRow(1).getCell(4);
        cell.setCellValue(RegisterToken);
        input.close();
        //storing data to file
        FileOutputStream oS = new FileOutputStream(excelFilePath);
        //writing data into the file
        wb.write(oS);
        oS.close();
        ArrayList al = new ArrayList();
        al.add(RegisterToken);
        return al;
    }
}

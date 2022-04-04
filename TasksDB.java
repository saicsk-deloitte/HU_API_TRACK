package MAIN_ASSIGNMENT.Functionalities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
public class TasksDB {
    public static ArrayList addTasks() throws IOException {
        ArrayList al;
        al=new ArrayList();
        String excelFilePath = "C:\\Users\\Chamkumar\\Desktop\\Tasks.xlsx";
        FileInputStream input = new FileInputStream(excelFilePath);
        XSSFWorkbook wb = new XSSFWorkbook(input);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum() + 1;
        for (int i = 1; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            String Task = row.getCell(0).getStringCellValue();
            al.add(Task);
        }
        return al;
    }
    public ArrayList getOwner(String owner,int i) throws Exception {
        String excelFilePath = "C:\\Users\\Chamkumar\\Desktop\\Tasks.xlsx";
        FileInputStream input = new FileInputStream(excelFilePath);
        XSSFWorkbook wb = new XSSFWorkbook(input);
        XSSFSheet sheet = wb.getSheetAt(0);
        Cell cell;
        cell = sheet.getRow(i).getCell(1);
        cell.setCellValue(owner);
        input.close();
        FileOutputStream oS = new FileOutputStream(excelFilePath);
        wb.write(oS);
        oS.close();
        ArrayList al = new ArrayList();
        al.add(owner);
        return al;
    }
    /*public ArrayList getId(String id ) throws Exception {
        String excelFilePath = "C:\\Users\\Chamkumar\\Desktop\\XYZBank.xlsx";
        FileInputStream input = new FileInputStream(excelFilePath);
        XSSFWorkbook wb = new XSSFWorkbook(input);
        XSSFSheet sheet = wb.getSheetAt(0);
        Cell cell = null;
        cell = sheet.getRow(1).getCell(4);
        cell.setCellValue(id);
        input.close();
        FileOutputStream oS = new FileOutputStream(excelFilePath);
        wb.write(oS);
        oS.close();
        ArrayList al = new ArrayList();
        al.add(id);
        return al;*/
    }



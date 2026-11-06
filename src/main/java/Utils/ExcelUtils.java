package Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    public static void loadExcel(String filePath, String sheetName) {

        try {
            FileInputStream dataFile = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(dataFile); // XSSF for XLSX; HSSF for XLS
            sheet = workbook.getSheet(sheetName);

        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be located.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public static String getCellData(int row, int col) {
        Cell cell = sheet.getRow(row).getCell(col);
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return "";
    }

    public static int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public static void closeExcel() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
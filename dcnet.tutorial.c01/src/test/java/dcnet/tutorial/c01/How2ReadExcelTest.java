package dcnet.tutorial.c01;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Arrays;
import org.junit.Test;

public class How2ReadExcelTest {

    @Test
    public void how2ReadExcel() throws Exception {

        File file = new File("test-suites/単体テスト仕様書.xlsx");
        // 打开文件流，读取Excel(Excel学名Workbook) -> (ワークブック)
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        // 切换到T01-01的工作表(ワークシート)
        XSSFSheet sheet = workbook.getSheet("T01-01");
        // 从选定的工作表开始读取
        Iterator<Row> rowIterator = sheet.rowIterator();
        // 因为通常第一二行通常是抬头，所以我们一般跳过第一行
        int startRow = 2;
        while (rowIterator.hasNext()) {
            // 读取每一行
            Row row = rowIterator.next();
            if (row.getRowNum() < startRow) {
                // 第startRow以前都跳过
                continue;
            }
            // 这个例子中每一行我们读取10列
            String[] values = new String[7];
            for (int i = 0; i < 7; ++i) {
                Cell cell = row.getCell(i); // Cell(セル)
                String cellValue = null;
                // ExcelのCell有好多类型，我们就处理一下类型
                switch (cell.getCellType()) {
                    case BOOLEAN:
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        cellValue = String.valueOf(cell.getNumericCellValue());
                        break;
                    case STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    case BLANK:
                        cellValue = "";
                        break;
                    default:
                        throw new RuntimeException("无法处理该类型，请修改测试书");
                }
                values[i] = cellValue;
            }
            // 作为测试，我们这里直接输出值
            System.out.println(Arrays.asList(values));
        }
        // 关闭文件流
        workbook.close();

    }
}

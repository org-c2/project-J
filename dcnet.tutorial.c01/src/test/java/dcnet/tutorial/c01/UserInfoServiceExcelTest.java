package dcnet.tutorial.c01;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dcnet.tutorial.c01.business.object.Status;
import dcnet.tutorial.c01.business.object.UserRegister;
import dcnet.tutorial.c01.business.service.UserRegisterService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInfoServiceExcelTest {

    Logger log = LoggerFactory.getLogger(UserInfoServiceTest.class);

    @Autowired
    private UserRegisterService userRegisterService;

    private int testNo = 0;
    private Iterator<String[]> testSuitesIt;

    @Before
    public void beforeTest() throws Exception {
        log.info("开始测试时候，从Excel中读取该测试所有用例。");
        // 从Excel中读取每一行，结果放在List<String]\>之中
        List<String[]> testSuites = prepareTestSuites(new File("test-suites/単体テスト仕様書.xlsx"), "T01-01");
        // 为了避免链表get(x)的性能损耗，我们直接取迭代器好了
        testSuitesIt = testSuites.iterator();
        // TestNO 只是用来计数没有实际意义
        testNo = 0;
    }

    @After
    public void afterTest() {
        log.info("测试结束之后执行，做一些清理，擦屁股的工作。");
    }

    @Test
    public void test_case_from_excel_T01_01() {
        // 读取当前测试用例，这时候迭代器会自动翻到下一个
        while (testSuitesIt.hasNext()) {
            String[] cases = testSuitesIt.next();
            log.info("{}-{} 开始测试，入力值 email = {}, password = {}， 期待  status = {}, email = {}",
                    "T01-01", testNo, cases[2], cases[3], cases[4], cases[5]);
            UserRegister actuals = userRegisterService.regist(cases[2], cases[3]);
            // 和期待值比对
            Assert.assertEquals(Status.valueOf(cases[4]), actuals.getStatus());
            Assert.assertEquals(cases[5], actuals.getStatus());
            testNo++; // 这个只是出Log用
        }
    }

    public List<String[]> prepareTestSuites(File file, String sheetName) throws Exception {
        List<String[]> testSuites = new LinkedList<>();

        // 打开文件流，读取Excel(Excel学名Workbook) -> (ワークブック)
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        // 切换到T01-01的工作表(ワークシート)
        XSSFSheet sheet = workbook.getSheet(sheetName);
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
            testSuites.add(values);
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
                        log.error("#{}行的数据无法正确处理，可能你需要修改这个值，在值前面加上’可以将其转换为String。", row.getRowNum());
                        throw new RuntimeException("无法处理该类型，请修改测试书");
                }
                values[i] = cellValue;
            }
        }
        // 关闭文件流
        workbook.close();

        return testSuites;
    }

}

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class XlsReaderService {

    @Value("${xls.files.path}")
    private String xlsFilesPath;

    @Value("${xls.header.row}")
    private int headerRow;

    public List<XlsData> readXlsFiles() throws IOException, BiffException {
        List<XlsData> dataList = new ArrayList<>();

        File directory = new File(xlsFilesPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xls"));

        if (files != null) {
            for (File file : files) {
                Workbook workbook = Workbook.getWorkbook(file);
                Sheet sheet = workbook.getSheet(0);

                // Get header names
                List<String> headers = new ArrayList<>();
                for (int col = 0; col < sheet.getColumns(); col++) {
                    Cell cell = sheet.getCell(col, headerRow);
                    headers.add(cell.getContents());
                }

                // Read data rows
                for (int row = headerRow + 1; row < sheet.getRows(); row++) {
                    XlsData data = new XlsData();
                    for (int col = 0; col < sheet.getColumns(); col++) {
                        Cell cell = sheet.getCell(col, row);
                        String header = headers.get(col);
                        setField(data, header, cell.getContents());
                    }
                    dataList.add(data);
                }

                workbook.close();
            }
        }

        return dataList;
    }

    private void setField(XlsData data, String header, String value) {
        switch (header) {
            case "TEMPLATE_COMMON_NAME":
                data.setTemplateCommonName(value);
                break;
            case "Brand":
                data.setBrand(value);
                break;
            case "MAILING_REF":
                data.setMailingRef(value);
                break;
            // Add cases for other headers
        }
    }
}

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    @Value("${csv.files.path}")
    private String csvFilesPath;

    @Value("${csv.header.row}")
    private int headerRow;

    public List<XlsData> readCsvFiles() throws IOException {
        List<XlsData> dataList = new ArrayList<>();

        File directory = new File(csvFilesPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (files != null) {
            for (File file : files) {
                try (CSVReader reader = new CSVReader(new FileReader(file))) {
                    String[] headers = reader.readNext(); // Read header row

                    // Read data rows
                    String[] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        XlsData data = new XlsData();
                        for (int i = 0; i < headers.length; i++) {
                            setField(data, headers[i], nextLine[i]);
                        }
                        dataList.add(data);
                    }
                }
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

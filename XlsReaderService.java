package com.example.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class XlsReaderService {

    @Value("${xls.files.path}")
    private String xlsFilesPath;

    @Value("${xls.header.row}")
    private int headerRow;

    public List<XlsData> readXlsFiles() throws IOException {
        List<XlsData> dataList = new ArrayList<>();

        File directory = new File(xlsFilesPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xls"));

        if (files != null) {
            for (File file : files) {
                FileInputStream inputStream = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                // Find header row
                Row headerRow = sheet.getRow(this.headerRow);
                if (headerRow == null) {
                    throw new IOException("Header row not found in the Excel sheet.");
                }

                // Get header names
                List<String> headers = new ArrayList<>();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue());
                }

                Iterator<Row> rowIterator = sheet.iterator();

                // Skip header row
                rowIterator.next();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    XlsData data = new XlsData();
                    int cellIdx = 0;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        String header = headers.get(cellIdx);
                        setField(data, header, cell.getStringCellValue());
                        cellIdx++;
                    }
                    dataList.add(data);
                }
                workbook.close();
                inputStream.close();
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
            case "REF":
                data.setRef(value);
                break;
            case "Title":
                data.setTitle(value);
                break;
            case "First_Names":
                data.setFirstNames(value);
                break;
            case "Surname":
                data.setSurname(value);
                break;
            case "Address_Line_1":
                data.setAddressLine1(value);
                break;
            case "Address_Line_2":
                data.setAddressLine2(value);
                break;
            case "Address_Line_3":
                data.setAddressLine3(value);
                break;
            case "Address_Line_4":
                data.setAddressLine4(value);
                break;
            case "Address_Line_5":
                data.setAddressLine5(value);
                break;
            case "Postcode":
                data.setPostcode(value);
                break;
            case "Country_Name":
                data.setCountryName(value);
                break;
            case "CIN":
                data.setCin(value);
                break;
            case "Provider_Name":
                data.setProviderName(value);
                break;
            case "Product_Name":
                data.setProductName(value);
                break;
            case "Date_Product_Was_Sold":
                data.setDateProductWasSold(value);
                break;
            case "Date_Product_Was_Bought":
                data.setDateProductWasBought(value);
                break;
            case "Total_Payment_Value":
                data.setTotalPaymentValue(value);
                break;
            case "Payment_Lieu":
                data.setPaymentLieu(value);
                break;
            case "Tax_Deducted":
                data.setTaxDeducted(value);
                break;
            case "Account_Ending":
                data.setAccountEnding(value);
                break;
            case "Calculation_Date":
                data.setCalculationDate(value);
                break;
            case "Initial_Investment":
                data.setInitialInvestment(value);
                break;
            case "Current_Value":
                data.setCurrentValue(value);
                break;
            case "Nominal_Value":
                data.setNominalValue(value);
                break;
            case "Difference":
                data.setDifference(value);
                break;
            case "Letter_Post_Date":
                data.setLetterPostDate(value);
                break;
            case "PRODUCT_CLOSED_DATE":
                data.setProductClosedDate(value);
                break;
            case "TOTAL_PAYMENT":
                data.setTotalPayment(value);
                break;
            case "WITHDRAWAL_VALUE":
                data.setWithdrawalValue(value);
                break;
            case "RATIONALE":
                data.setRationale(value);
                break;
            case "ME_SUITABILITY_FAILURE_REASON":
                data.setMeSuitabilityFailureReason(value);
                break;
            case "BENCHMARK":
                data.setBenchmark(value);
                break;
            case "CLOSED_VALUE":
                data.setClosedValue(value);

            case "CLOSED_VALUE":
                data.setClosedValue(value);
                break;
            case "PRODUCT_PROVIDER":
                data.setProductProvider(value);
                break;
            case "ALTERNATIVE_PRODUCT_OR_FUND_COMPARISON":
                data.setAlternativeProductOrFundComparison(value);
                break;
            case "AMOUNT_ABOVE_BENCHMARK":
                data.setAmountAboveBenchmark(value);
                break;
            case "SUITABILITY":
                data.setSuitability(value);
                break;
            case "Date_Of_Letter":
                data.setDateOfLetter(value);
                break;
            case "DATE_OF_LETTER_90_DAYS":
                data.setDateOfLetter90Days(value);
                break;
            case "DATE_OF_LETTER_30_DAYS":
                data.setDateOfLetter30Days(value);
                break;
            case "SIGNATURE":
                data.setSignature(value);
                break;
            case "SIGNED_DATE":
                data.setSignedDate(value);
                break;
            case "DATE_OF_LAST_LETTER_SENT":
                data.setDateOfLastLetterSent(value);
                break;
            case "DATE_OFFER_LETTER_30_DAYS":
                data.setDateOfferLetter30Days(value);
                break;
            case "DATE_OFFER_LETTER_90_DAYS":
                data.setDateOfferLetter90Days(value);
                break;
            case "TOTAL_REFUND_VALUE":
                data.setTotalRefundValue(value);
                break;
            // Add cases for other fields
        }
    }
}

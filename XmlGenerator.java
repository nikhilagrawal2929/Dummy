package com.example.demo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class XmlGenerator {

    public static void generateXml(XlsData xlsData) {
        try {
            // Create XML document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Root element
            Element driverFile = doc.createElement("DriverFile");
            driverFile.setAttribute("xmlns:xsi", "https://natwest.com/XMLSchema-instance");
            doc.appendChild(driverFile);

            // Add elements to DriverFile
            createElement(doc, driverFile, "RecordKey", "34");
            createElement(doc, driverFile, "OrderUuid", "1");
            createElement(doc, driverFile, "EnvironmentIdentifier", "Dev");
            createElement(doc, driverFile, "PRODUCT_NAME", "2CPLTRS");
            // Add other elements

            // Create Customer element
            Element customer = doc.createElement("Customer");
            driverFile.appendChild(customer);

            // Populate Customer element with XlsData values
            createElement(doc, customer, "TEMPLATE_COMMON_NAME", xlsData.getTemplateCommonName());
            createElement(doc, customer, "Brand", xlsData.getBrand());
            createElement(doc, customer, "MAILING_REF", xlsData.getMailingRef());
            // Add other elements

            // Write the content into an XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            // Naming the XML file based on REF value
            String refValue = xlsData.getRef();
            String fileName = refValue != null ? refValue + ".xml" : "output.xml";
            StreamResult result = new StreamResult(new File(fileName));

            transformer.transform(source, result);

            System.out.println("XML file created successfully!");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void createElement(Document doc, Element parent, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value != null ? value : ""));
        parent.appendChild(element);
    }
}

/*
document-generator
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── natwest
│   │   │           └── ccpl
│   │   │               ├── DocumentGenerator.java
│   │   │               ├── DocumentType.java
│   │   │               ├── Interactive.java
│   │   │               ├── Batch.java
│   │   │               ├── OnDemand.java
│   │   │               ├── InteractiveDTO.java
│   │   │               └── DocumentMapper.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── todo
│                       └── docgeneratorServiceTests.java
└── pom.xml
*/

package com.natwest.ccpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generateDocuments")
public class DocumentGenerator {

    private final DocumentType documentType;

    public DocumentGenerator(DocumentType documentType) {
        this.documentType = documentType;
    }

    @GetMapping
    public void generateDocuments() {
        documentType.generateDocuments();
    }

    public static void main(String[] args) {
        DocumentGenerator interactive = new DocumentGenerator(new Interactive());
        interactive.generateDocuments();

        DocumentGenerator batch = new DocumentGenerator(new Batch());
        batch.generateDocuments();

        DocumentGenerator onDemand = new DocumentGenerator(new OnDemand());
        onDemand.generateDocuments();
    }
}

interface DocumentType {
    void generateDocuments();
}

class Interactive implements DocumentType {
    @Override
    public void generateDocuments() {
        System.out.println("Interactive documents generated");
    }
}

class Batch implements DocumentType {
    @Override
    public void generateDocuments() {
        System.out.println("Batch documents generated");
    }
}

class OnDemand implements DocumentType {
    @Override
    public void generateDocuments() {
        System.out.println("On-demand documents generated");
    }
}

class InteractiveDTO {
    //20-digit alphanumeric unique caller identifier
    String extSysId;

    //10 digit alphanumeric unique external inetractive document identifier
    String extDocId;

    //10 digit alphanumeric unique touchpoint identifier defined by business team for archival
    String intDocId;

    //Messagepoint Business Area
    String docBusinessArea;

    //Type of the document i.e. Interactive, Batch, OnDemand 
    String docType;
    
    //Name of the messagepoint touchpoint
    String docName;

    //Messagepoint driver file content - if documentContent is empty then empty MP order with default driver file and extDocId is created, else provided content is used
    String documentContent;

    //Is Archival Needed flag - true - archie to ECM, false - do not archive
    Boolean isArchivalNeeded;

    //is omnichannel document needed flag
    Boolean isOmnichannelDoc;

    //is only email document needed flag
    Boolean isOnlyEmailDoc;

    //is only print document needed flag
    Boolean isOnlyPrintDoc;

    //is only sms document needed flag
    Boolean isOnlySmsDoc;
}

/*
   if InteractiveDTO docType is interactive then call Interactive.generateDocuments() with InteractiveDTO object;
   if docType is batch then call Batch.generateDocuments() with provided file
   if InteractiveDTO docType is OnDemand then call Interactive.generateDocuments() with InteractiveDTO object
 */
class DocumentMapper {
    void getDocumentType(InteractiveDTO dto) {
        switch (dto.docType) {
            case "interactive":
                new Interactive().generateDocuments();
                break;
            case "batch":
                new Batch().generateDocuments();
                break;
            case "onDemand":
                new OnDemand().generateDocuments();
                break;
            default:
                throw new IllegalArgumentException("Unknown document type: " + dto.docType);
        }
    }
}
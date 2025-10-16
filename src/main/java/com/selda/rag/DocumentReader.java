package com.selda.rag;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DocumentReader {

    /**
     * PDF dosyasından metin okur
     */
    public static String readPDF(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    /**
     * Word dosyasından metin okur (.docx)
     */
    public static String readWord(String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {
            
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText()).append("\n");
            }
        }
        
        return text.toString();
    }

    /**
     * Dosya uzantısına göre uygun okuma metodunu seçer
     */
    public static String readDocument(String filePath) throws IOException {
        String fileName = new File(filePath).getName().toLowerCase();
        
        if (fileName.endsWith(".pdf")) {
            return readPDF(filePath);
        } else if (fileName.endsWith(".docx")) {
            return readWord(filePath);
        } else {
            throw new IllegalArgumentException("Desteklenmeyen dosya formatı. Sadece PDF ve DOCX dosyaları desteklenir.");
        }
    }

    /**
     * Dosyanın var olup olmadığını kontrol eder
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}

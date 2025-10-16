package com.selda.rag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    /**
     * JSON analiz sonucunu HTML rapor olarak kaydeder
     */
    public static void generateHTMLReport(JsonNode analysisResult, String outputFile, String sourceFile) throws IOException {
        StringBuilder html = new StringBuilder();
        
        // HTML başlangıcı
        html.append("<!DOCTYPE html>\n")
            .append("<html lang=\"tr\">\n")
            .append("<head>\n")
            .append("    <meta charset=\"UTF-8\">\n")
            .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
            .append("    <title>Gereksinim Analizi Raporu</title>\n")
            .append("    <style>\n")
            .append(getCSS())
            .append("    </style>\n")
            .append("</head>\n")
            .append("<body>\n");

        // Rapor başlığı
        html.append("    <header>\n")
            .append("        <h1>📋 Gereksinim Analizi Raporu</h1>\n")
            .append("        <div class=\"report-info\">\n")
            .append("            <p><strong>Kaynak Dosya:</strong> ").append(sourceFile).append("</p>\n")
            .append("            <p><strong>Analiz Tarihi:</strong> ").append(LocalDateTime.now().format(DATE_FORMAT)).append("</p>\n")
            .append("        </div>\n")
            .append("    </header>\n");

        // Ana içerik
        html.append("    <main>\n");
        
        // Fonksiyonel gereksinimler
        if (analysisResult.has("functionalRequirements")) {
            html.append(generateSection("🔧 Fonksiyonel Gereksinimler", "functionalRequirements", analysisResult));
        }

        // Fonksiyonel olmayan gereksinimler
        if (analysisResult.has("nonFunctionalRequirements")) {
            html.append(generateSection("⚡ Fonksiyonel Olmayan Gereksinimler", "nonFunctionalRequirements", analysisResult));
        }

        // Eksik bilgiler
        if (analysisResult.has("missingInformation")) {
            html.append(generateSection("❓ Eksik Bilgiler", "missingInformation", analysisResult));
        }

        // Öncelik ipuçları
        if (analysisResult.has("priorityHints")) {
            html.append(generateSection("🎯 Öncelik İpuçları", "priorityHints", analysisResult));
        }

        html.append("    </main>\n");

        // Footer
        html.append("    <footer>\n")
            .append("        <p>Bu rapor BA-LLM sistemi tarafından otomatik olarak oluşturulmuştur.</p>\n")
            .append("    </footer>\n")
            .append("</body>\n")
            .append("</html>");

        // Dosyaya yaz
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(html.toString());
        }
    }

    /**
     * Batch analiz sonucunu HTML rapor olarak kaydeder
     */
    public static void generateBatchHTMLReport(BatchAnalyzer.BatchResult batchResult, String outputFile) throws IOException {
        StringBuilder html = new StringBuilder();
        
        // HTML başlangıcı
        html.append("<!DOCTYPE html>\n")
            .append("<html lang=\"tr\">\n")
            .append("<head>\n")
            .append("    <meta charset=\"UTF-8\">\n")
            .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
            .append("    <title>Toplu Gereksinim Analizi Raporu</title>\n")
            .append("    <style>\n")
            .append(getCSS())
            .append("    </style>\n")
            .append("</head>\n")
            .append("<body>\n");

        // Rapor başlığı
        html.append("    <header>\n")
            .append("        <h1>📋 Toplu Gereksinim Analizi Raporu</h1>\n")
            .append("        <div class=\"report-info\">\n")
            .append("            <p><strong>Toplam Dosya:</strong> ").append(batchResult.getTotalFiles()).append("</p>\n")
            .append("            <p><strong>Başarılı:</strong> ").append(batchResult.getSuccessfulFiles()).append("</p>\n")
            .append("            <p><strong>Başarısız:</strong> ").append(batchResult.getFailedFiles()).append("</p>\n")
            .append("            <p><strong>Analiz Tarihi:</strong> ").append(LocalDateTime.now().format(DATE_FORMAT)).append("</p>\n")
            .append("        </div>\n")
            .append("    </header>\n");

        // Ana içerik
        html.append("    <main>\n");

        // Her dosya için analiz sonucu
        for (BatchAnalyzer.FileAnalysis analysis : batchResult.getAnalyses()) {
            html.append("        <section class=\"file-analysis\">\n")
                .append("            <h2>📄 ").append(analysis.getFileName()).append("</h2>\n");
            
            if (analysis.isSuccess()) {
                JsonNode result = analysis.getAnalysis();
                
                // Fonksiyonel gereksinimler
                if (result.has("functionalRequirements")) {
                    html.append(generateSection("🔧 Fonksiyonel Gereksinimler", "functionalRequirements", result));
                }

                // Fonksiyonel olmayan gereksinimler
                if (result.has("nonFunctionalRequirements")) {
                    html.append(generateSection("⚡ Fonksiyonel Olmayan Gereksinimler", "nonFunctionalRequirements", result));
                }

                // Eksik bilgiler
                if (result.has("missingInformation")) {
                    html.append(generateSection("❓ Eksik Bilgiler", "missingInformation", result));
                }

                // Öncelik ipuçları
                if (result.has("priorityHints")) {
                    html.append(generateSection("🎯 Öncelik İpuçları", "priorityHints", result));
                }
            } else {
                html.append("            <div class=\"error-section\">\n")
                    .append("                <h3>❌ Analiz Hatası</h3>\n")
                    .append("                <p>").append(analysis.getError()).append("</p>\n")
                    .append("            </div>\n");
            }
            
            html.append("        </section>\n");
        }

        html.append("    </main>\n");

        // Footer
        html.append("    <footer>\n")
            .append("        <p>Bu rapor BA-LLM sistemi tarafından otomatik olarak oluşturulmuştur.</p>\n")
            .append("    </footer>\n")
            .append("</body>\n")
            .append("</html>");

        // Dosyaya yaz
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(html.toString());
        }
    }

    /**
     * Bölüm oluşturur
     */
    private static String generateSection(String title, String fieldName, JsonNode data) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <section class=\"analysis-section\">\n")
               .append("            <h3>").append(title).append("</h3>\n")
               .append("            <div class=\"requirements-list\">\n");

        JsonNode items = data.get(fieldName);
        if (items.isArray() && items.size() > 0) {
            for (JsonNode item : items) {
                section.append("                <div class=\"requirement-item\">\n")
                       .append("                    <span class=\"requirement-text\">").append(item.asText()).append("</span>\n")
                       .append("                </div>\n");
            }
        } else {
            section.append("                <p class=\"no-items\">Bu bölümde öğe bulunmamaktadır.</p>\n");
        }

        section.append("            </div>\n")
               .append("        </section>\n");

        return section.toString();
    }

    /**
     * CSS stilleri
     */
    private static String getCSS() {
        return "* {\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "    box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "    line-height: 1.6;\n" +
                "    color: #333;\n" +
                "    background-color: #f5f5f5;\n" +
                "}\n" +
                "\n" +
                "header {\n" +
                "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "    color: white;\n" +
                "    padding: 2rem;\n" +
                "    text-align: center;\n" +
                "    box-shadow: 0 4px 6px rgba(0,0,0,0.1);\n" +
                "}\n" +
                "\n" +
                "header h1 {\n" +
                "    font-size: 2.5rem;\n" +
                "    margin-bottom: 1rem;\n" +
                "}\n" +
                "\n" +
                ".report-info {\n" +
                "    display: flex;\n" +
                "    justify-content: center;\n" +
                "    gap: 2rem;\n" +
                "    flex-wrap: wrap;\n" +
                "}\n" +
                "\n" +
                ".report-info p {\n" +
                "    background: rgba(255,255,255,0.2);\n" +
                "    padding: 0.5rem 1rem;\n" +
                "    border-radius: 20px;\n" +
                "    font-size: 0.9rem;\n" +
                "}\n" +
                "\n" +
                "main {\n" +
                "    max-width: 1200px;\n" +
                "    margin: 2rem auto;\n" +
                "    padding: 0 1rem;\n" +
                "}\n" +
                "\n" +
                ".file-analysis {\n" +
                "    background: white;\n" +
                "    margin: 2rem 0;\n" +
                "    border-radius: 10px;\n" +
                "    box-shadow: 0 2px 10px rgba(0,0,0,0.1);\n" +
                "    overflow: hidden;\n" +
                "}\n" +
                "\n" +
                ".file-analysis h2 {\n" +
                "    background: #4CAF50;\n" +
                "    color: white;\n" +
                "    padding: 1rem;\n" +
                "    margin: 0;\n" +
                "    font-size: 1.3rem;\n" +
                "}\n" +
                "\n" +
                ".analysis-section {\n" +
                "    padding: 1.5rem;\n" +
                "    border-bottom: 1px solid #eee;\n" +
                "}\n" +
                "\n" +
                ".analysis-section:last-child {\n" +
                "    border-bottom: none;\n" +
                "}\n" +
                "\n" +
                ".analysis-section h3 {\n" +
                "    color: #2c3e50;\n" +
                "    margin-bottom: 1rem;\n" +
                "    font-size: 1.2rem;\n" +
                "    display: flex;\n" +
                "    align-items: center;\n" +
                "    gap: 0.5rem;\n" +
                "}\n" +
                "\n" +
                ".requirements-list {\n" +
                "    display: grid;\n" +
                "    gap: 0.8rem;\n" +
                "}\n" +
                "\n" +
                ".requirement-item {\n" +
                "    background: #f8f9fa;\n" +
                "    padding: 1rem;\n" +
                "    border-left: 4px solid #3498db;\n" +
                "    border-radius: 5px;\n" +
                "    transition: transform 0.2s ease;\n" +
                "}\n" +
                "\n" +
                ".requirement-item:hover {\n" +
                "    transform: translateX(5px);\n" +
                "    box-shadow: 0 2px 8px rgba(0,0,0,0.1);\n" +
                "}\n" +
                "\n" +
                ".requirement-text {\n" +
                "    font-size: 0.95rem;\n" +
                "    line-height: 1.5;\n" +
                "}\n" +
                "\n" +
                ".no-items {\n" +
                "    color: #7f8c8d;\n" +
                "    font-style: italic;\n" +
                "    text-align: center;\n" +
                "    padding: 1rem;\n" +
                "}\n" +
                "\n" +
                ".error-section {\n" +
                "    background: #ffebee;\n" +
                "    border: 1px solid #ffcdd2;\n" +
                "    border-radius: 5px;\n" +
                "    padding: 1rem;\n" +
                "    margin: 1rem 0;\n" +
                "}\n" +
                "\n" +
                ".error-section h3 {\n" +
                "    color: #c62828;\n" +
                "    margin-bottom: 0.5rem;\n" +
                "}\n" +
                "\n" +
                "footer {\n" +
                "    background: #2c3e50;\n" +
                "    color: white;\n" +
                "    text-align: center;\n" +
                "    padding: 2rem;\n" +
                "    margin-top: 3rem;\n" +
                "}\n" +
                "\n" +
                "@media (max-width: 768px) {\n" +
                "    header h1 {\n" +
                "        font-size: 2rem;\n" +
                "    }\n" +
                "    \n" +
                "    .report-info {\n" +
                "        flex-direction: column;\n" +
                "        align-items: center;\n" +
                "        gap: 1rem;\n" +
                "    }\n" +
                "    \n" +
                "    main {\n" +
                "        margin: 1rem auto;\n" +
                "        padding: 0 0.5rem;\n" +
                "    }\n" +
                "    \n" +
                "    .analysis-section {\n" +
                "        padding: 1rem;\n" +
                "    }\n" +
                "}";
    }

    /**
     * JSON analiz sonucunu PDF rapor olarak kaydeder
     */
    public static void generatePDFReport(JsonNode analysisResult, String outputFile, String sourceFile) throws IOException {
        // Önce HTML oluştur
        String htmlFile = outputFile.replace(".pdf", ".html");
        generateHTMLReport(analysisResult, htmlFile, sourceFile);
        
        // HTML'yi PDF'e çevir
        HtmlConverter.convertToPdf(
            Files.newInputStream(Paths.get(htmlFile)),
            Files.newOutputStream(Paths.get(outputFile))
        );
        
        // Geçici HTML dosyasını sil
        Files.deleteIfExists(Paths.get(htmlFile));
    }

    /**
     * Batch analiz sonucunu PDF rapor olarak kaydeder
     */
    public static void generateBatchPDFReport(BatchAnalyzer.BatchResult batchResult, String outputFile) throws IOException {
        // Önce HTML oluştur
        String htmlFile = outputFile.replace(".pdf", ".html");
        generateBatchHTMLReport(batchResult, htmlFile);
        
        // HTML'yi PDF'e çevir
        HtmlConverter.convertToPdf(
            Files.newInputStream(Paths.get(htmlFile)),
            Files.newOutputStream(Paths.get(outputFile))
        );
        
        // Geçici HTML dosyasını sil
        Files.deleteIfExists(Paths.get(htmlFile));
    }
}

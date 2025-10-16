#!/usr/bin/env pwsh

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "BA-LLM TÜM ÖZELLİKLER TEST SCRIPTI" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Ollama servisinin çalışıp çalışmadığını kontrol et
Write-Host "[1/8] Ollama servisi kontrol ediliyor..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:11434/api/tags" -UseBasicParsing -TimeoutSec 5
    Write-Host "✅ Ollama servisi çalışıyor" -ForegroundColor Green
} catch {
    Write-Host "❌ HATA: Ollama servisi çalışmıyor!" -ForegroundColor Red
    Write-Host "Lütfen Ollama'yı başlatın: ollama serve" -ForegroundColor Red
    Read-Host "Devam etmek için Enter'a basın"
    exit 1
}
Write-Host ""

# Yardım menüsünü test et
Write-Host "[2/8] Yardım menüsü test ediliyor..." -ForegroundColor Yellow
java -jar target/ba-llm-1.0-SNAPSHOT.jar --help
Write-Host ""

# Varsayılan test metni ile analiz
Write-Host "[3/8] Varsayılan test metni ile analiz..." -ForegroundColor Yellow
java -jar target/ba-llm-1.0-SNAPSHOT.jar
Write-Host ""

# Tek dosya analizi (Word) - JSON çıktı
Write-Host "[4/8] Tek dosya analizi (Word) - JSON çıktı..." -ForegroundColor Yellow
java -jar target/ba-llm-1.0-SNAPSHOT.jar test.docx
Write-Host ""

# Tek dosya analizi (Word) - HTML rapor
Write-Host "[5/8] Tek dosya analizi (Word) - HTML rapor..." -ForegroundColor Yellow
java -jar target/ba-llm-1.0-SNAPSHOT.jar deneme.docx --report html
Write-Host ""

# Tek dosya analizi (Word) - PDF rapor
Write-Host "[6/8] Tek dosya analizi (Word) - PDF rapor..." -ForegroundColor Yellow
java -jar target/ba-llm-1.0-SNAPSHOT.jar test.docx --report pdf
Write-Host ""

# Çoklu dosya analizi - HTML rapor
Write-Host "[7/8] Çoklu dosya analizi - HTML rapor..." -ForegroundColor Yellow
java -jar target/ba-llm-1.0-SNAPSHOT.jar --files deneme.docx test.docx --report html
Write-Host ""

# Batch analizi (klasör) - PDF rapor
Write-Host "[8/8] Batch analizi (klasör) - PDF rapor..." -ForegroundColor Yellow
java -jar target/ba-llm-1.0-SNAPSHOT.jar --batch . --report pdf
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TÜM TESTLER TAMAMLANDI!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Oluşturulan dosyalar:" -ForegroundColor Yellow
Write-Host "- JSON çıktıları: *-analysis-result.json" -ForegroundColor White
Write-Host "- HTML raporları: *-report.html" -ForegroundColor White
Write-Host "- PDF raporları: *-report.pdf" -ForegroundColor White
Write-Host ""
Write-Host "Test sonuçları:" -ForegroundColor Yellow

# Oluşturulan dosyaları listele
$jsonFiles = Get-ChildItem -Path "." -Filter "*-analysis-result.json" -ErrorAction SilentlyContinue
$htmlFiles = Get-ChildItem -Path "." -Filter "*-report.html" -ErrorAction SilentlyContinue
$pdfFiles = Get-ChildItem -Path "." -Filter "*-report.pdf" -ErrorAction SilentlyContinue

if ($jsonFiles) {
    Write-Host "JSON dosyaları:" -ForegroundColor Green
    $jsonFiles | ForEach-Object { Write-Host "  - $($_.Name)" -ForegroundColor White }
}

if ($htmlFiles) {
    Write-Host "HTML dosyaları:" -ForegroundColor Green
    $htmlFiles | ForEach-Object { Write-Host "  - $($_.Name)" -ForegroundColor White }
}

if ($pdfFiles) {
    Write-Host "PDF dosyaları:" -ForegroundColor Green
    $pdfFiles | ForEach-Object { Write-Host "  - $($_.Name)" -ForegroundColor White }
}

Write-Host ""
Write-Host "Test tamamlandı! Oluşturulan raporları inceleyebilirsiniz." -ForegroundColor Green
Read-Host "Devam etmek için Enter'a basın"

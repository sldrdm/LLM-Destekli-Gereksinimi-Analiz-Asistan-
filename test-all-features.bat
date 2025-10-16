@echo off
echo ========================================
echo BA-LLM TÜM ÖZELLİKLER TEST SCRIPTI
echo ========================================
echo.

REM Ollama servisinin çalışıp çalışmadığını kontrol et
echo [1/8] Ollama servisi kontrol ediliyor...
curl -s http://localhost:11434/api/tags > nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ HATA: Ollama servisi çalışmıyor!
    echo Lütfen Ollama'yı başlatın: ollama serve
    pause
    exit /b 1
)
echo ✅ Ollama servisi çalışıyor
echo.

REM Yardım menüsünü test et
echo [2/8] Yardım menüsü test ediliyor...
java -jar target/ba-llm-1.0-SNAPSHOT.jar --help
echo.

REM Varsayılan test metni ile analiz
echo [3/8] Varsayılan test metni ile analiz...
java -jar target/ba-llm-1.0-SNAPSHOT.jar
echo.

REM Tek dosya analizi (Word)
echo [4/8] Tek dosya analizi (Word) - JSON çıktı...
java -jar target/ba-llm-1.0-SNAPSHOT.jar test.docx
echo.

REM Tek dosya analizi (Word) - HTML rapor
echo [5/8] Tek dosya analizi (Word) - HTML rapor...
java -jar target/ba-llm-1.0-SNAPSHOT.jar deneme.docx --report html
echo.

REM Tek dosya analizi (Word) - PDF rapor
echo [6/8] Tek dosya analizi (Word) - PDF rapor...
java -jar target/ba-llm-1.0-SNAPSHOT.jar test.docx --report pdf
echo.

REM Çoklu dosya analizi - HTML rapor
echo [7/8] Çoklu dosya analizi - HTML rapor...
java -jar target/ba-llm-1.0-SNAPSHOT.jar --files deneme.docx test.docx --report html
echo.

REM Batch analizi (klasör) - PDF rapor
echo [8/8] Batch analizi (klasör) - PDF rapor...
java -jar target/ba-llm-1.0-SNAPSHOT.jar --batch . --report pdf
echo.

echo ========================================
echo TÜM TESTLER TAMAMLANDI!
echo ========================================
echo.
echo Oluşturulan dosyalar:
echo - JSON çıktıları: *-analysis-result.json
echo - HTML raporları: *-report.html
echo - PDF raporları: *-report.pdf
echo.
echo Test sonuçları:
dir *.json *.html *.pdf 2>nul
echo.
pause

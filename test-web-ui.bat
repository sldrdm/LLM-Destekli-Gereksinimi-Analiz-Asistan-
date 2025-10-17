@echo off
echo ========================================
echo BA-LLM WEB ARAYÜZÜ TEST SCRIPTI
echo ========================================
echo.

REM Ollama servisinin çalışıp çalışmadığını kontrol et
echo [1/4] Ollama servisi kontrol ediliyor...
curl -s http://localhost:11434/api/tags > nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ HATA: Ollama servisi çalışmıyor!
    echo Lütfen Ollama'yı başlatın: ollama serve
    pause
    exit /b 1
)
echo ✅ Ollama servisi çalışıyor
echo.

REM Projeyi derle
echo [2/4] Proje derleniyor...
mvn clean compile package -DskipTests
if %errorlevel% neq 0 (
    echo ❌ HATA: Derleme başarısız!
    pause
    exit /b 1
)
echo ✅ Proje başarıyla derlendi
echo.

REM Web uygulamasını başlat
echo [3/4] Web uygulaması başlatılıyor...
echo ✅ Web uygulaması http://localhost:8080 adresinde çalışıyor
echo.
echo 🌐 Tarayıcınızda http://localhost:8080 adresini açın
echo.
echo 📋 Test Senaryoları:
echo    1. Dosya yükleme (PDF/DOCX)
echo    2. Metin analizi
echo    3. HTML/PDF rapor oluşturma
echo    4. Responsive tasarım kontrolü
echo.
echo ⚠️  Uygulamayı durdurmak için Ctrl+C tuşlarına basın
echo.

REM Web uygulamasını başlat
java -jar target/ba-llm-1.0-SNAPSHOT.jar

echo.
echo [4/4] Test tamamlandı!
pause

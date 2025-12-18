# BA-LLM - Yapay Zeka Destekli Gereksinim Analizi Sistemi

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen?style=for-the-badge&logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Ollama-LLM-blue?style=for-the-badge" alt="Ollama">
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="MIT License">
</p>

PDF ve Word dokümanlarından otomatik gereksinim analizi yapan, yerel LLM (Ollama) ile entegre çalışan kurumsal düzeyde bir Java web uygulamasıdır.

## 📋 İçindekiler

- [Özellikler](#-özellikler)
- [Mimari](#-mimari)
- [Gereksinimler](#-gereksinimler)
- [Kurulum](#-kurulum)
- [Kullanım](#-kullanım)
- [API Referansı](#-api-referansı)
- [Model Yönetimi](#-model-yönetimi)
- [Raporlama](#-raporlama)
- [Konfigürasyon](#️-konfigürasyon)
- [Sorun Giderme](#-sorun-giderme)

---

## 🚀 Özellikler

### ✅ Temel Özellikler
| Özellik | Açıklama |
|---------|----------|
| **Doküman Analizi** | PDF ve Word (.docx) dosyalarından otomatik gereksinim çıkarma |
| **Web Arayüzü** | Modern, responsive Bootstrap 5 tabanlı kullanıcı arayüzü |
| **Çoklu Model Desteği** | Llama3, Llama3.2:1b, Llama3.2:3b ve özel modeller |
| **Yerel İşleme** | Tüm veriler yerel sunucuda işlenir (KVKK/GDPR uyumlu) |
| **JSON Çıktı** | Yapılandırılmış analiz sonuçları |
| **Batch İşleme** | Klasör ve çoklu dosya analizi |

### 📊 Analiz Çıktıları
- **Fonksiyonel Gereksinimler**: Sistemin yapması gereken işlevler
- **Fonksiyonel Olmayan Gereksinimler**: Performans, güvenlik, kullanılabilirlik
- **Eksik Bilgiler**: Belgelerde eksik olan detaylar
- **Öncelik İpuçları**: Uygulama öncelik önerileri

### 📑 Raporlama
- **HTML Raporları**: Modern, responsive tasarım
- **PDF Raporları**: Profesyonel dokümantasyon (iText HTML2PDF)
- **Batch Raporları**: Toplu analiz sonuçları

---

## 🏗 Mimari

```
ba-llm/
├── src/main/java/com/selda/rag/
│   ├── WebApplication.java      # Spring Boot giriş noktası
│   ├── WebController.java       # REST API controller
│   ├── DocumentReader.java      # PDF/Word okuyucu
│   ├── OllamaClient.java        # LLM iletişim istemcisi
│   ├── ModelManager.java        # Model yönetimi (Singleton)
│   ├── ModelConfig.java         # Model konfigürasyon sınıfı
│   ├── ReportGenerator.java     # HTML/PDF rapor oluşturucu
│   └── BatchAnalyzer.java       # Toplu dosya analizi
├── src/main/resources/
│   ├── application.properties   # Spring Boot ayarları
│   ├── templates/index.html     # Thymeleaf ana sayfa
│   └── static/js/app.js         # Frontend JavaScript
├── model-configs.json           # Model konfigürasyonları
└── pom.xml                      # Maven bağımlılıkları
```

### Teknoloji Stack'i

| Katman | Teknoloji |
|--------|-----------|
| **Backend** | Java 17, Spring Boot 2.7.18 |
| **Frontend** | Thymeleaf, Bootstrap 5, Font Awesome |
| **LLM** | Ollama (Llama3, Mistral, Phi) |
| **Doküman İşleme** | Apache PDFBox 2.0.29, Apache POI 5.2.4 |
| **PDF Oluşturma** | iText 7, HTML2PDF 4.0.5 |
| **HTTP İstemci** | OkHttp 4.12.0 |
| **JSON** | Jackson 2.15.2 |

---

## 📋 Gereksinimler

| Gereksinim | Versiyon | Not |
|------------|----------|-----|
| **Java JDK** | 17 veya üzeri | OpenJDK önerilir |
| **Maven** | 3.6+ | Derleme için |
| **Ollama** | En son | LLM servisi |
| **RAM** | Min. 8GB | Model boyutuna göre değişir |
| **Disk** | Min. 10GB | Modeller için |

---

## 🔧 Kurulum

### 1. Ollama Kurulumu

```bash
# Windows için PowerShell (Admin)
winget install Ollama.Ollama

# Linux/MacOS
curl -fsSL https://ollama.com/install.sh | sh
```

### 2. Ollama Servisini Başlatın

```bash
# Servisi başlat
ollama serve

# Model indirin (Önerilen: llama3)
ollama pull llama3:latest

# Alternatif modeller
ollama pull llama3.2:1b    # Hızlı, az bellek
ollama pull llama3.2:3b    # Kaliteli analiz
```

### 3. Projeyi Derleyin

```bash
# Projeyi klonlayın
git clone https://github.com/selda/ba-llm.git
cd ba-llm

# Maven ile derleyin
mvn clean compile package -DskipTests
```

### 4. Uygulamayı Başlatın

```bash
# Web uygulamasını başlatın
mvn spring-boot:run

# VEYA JAR dosyasıyla
java -jar target/ba-llm-1.0-SNAPSHOT.jar
```

### 5. Tarayıcıda Açın

```
http://localhost:8080
```

---

## 🎯 Kullanım

### Web Arayüzü

1. **Dosya Yükleme**: PDF veya Word dosyası seçin
2. **Model Seçimi**: Analiz için LLM modelini seçin
3. **Analiz Başlat**: "Analiz Et" butonuna tıklayın
4. **Sonuçları Görüntüle**: Kategorize edilmiş gereksinimleri inceleyin
5. **Rapor İndir**: HTML veya PDF formatında rapor alın

### Metin Analizi

Doğrudan metin girişi yaparak da analiz yapabilirsiniz:
1. "Metin Girişi" sekmesine geçin
2. Gereksinim metnini yapıştırın
3. "Analiz Et" butonuna tıklayın

### Komut Satırı (CLI)

```bash
# Tek dosya analizi
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.pdf

# JSON çıktı ile
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.docx --output sonuc.json

# HTML rapor
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.pdf --report html

# PDF rapor
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.docx --report pdf

# Klasör analizi (Batch)
java -jar target/ba-llm-1.0-SNAPSHOT.jar --batch ./requirements/

# Çoklu dosya analizi
java -jar target/ba-llm-1.0-SNAPSHOT.jar --files dosya1.pdf dosya2.docx

# Yardım
java -jar target/ba-llm-1.0-SNAPSHOT.jar --help
```

---

## 🔌 API Referansı

### Endpoints

| Method | Endpoint | Açıklama |
|--------|----------|----------|
| `GET` | `/` | Ana sayfa (Web UI) |
| `POST` | `/analyze` | Dosya analizi |
| `POST` | `/analyze-text` | Metin analizi |
| `GET` | `/download/{fileName}` | Rapor indirme |
| `GET` | `/api/models` | Model listesi |
| `POST` | `/api/models/switch` | Model değiştirme |
| `POST` | `/api/models/parameters` | Model parametreleri güncelleme |

### Dosya Analizi (POST /analyze)

**Request:**
```http
POST /analyze
Content-Type: multipart/form-data

file: [PDF veya DOCX dosyası]
reportType: none | html | pdf
modelName: llama3:latest (opsiyonel)
```

**Response:**
```json
{
  "success": true,
  "fileName": "requirements.pdf",
  "fileSize": 125000,
  "timestamp": "18.12.2024 14:30",
  "analysisResult": {
    "functionalRequirements": [
      "Kullanıcı sisteme giriş yapabilmelidir",
      "Doküman yükleyebilmelidir"
    ],
    "nonFunctionalRequirements": [
      "Sistem 5 saniye içinde yanıt vermelidir",
      "Eş zamanlı 20 kullanıcı desteklenmelidir"
    ],
    "missingInformation": [
      "Kullanıcı rolleri tanımlanmamış",
      "Hata senaryoları eksik"
    ],
    "priorityHints": [
      "Kimlik doğrulama öncelikli",
      "Raporlama ikinci fazda"
    ]
  },
  "reportFile": "requirements_20241218_143000-report.html",
  "reportType": "html"
}
```

### Metin Analizi (POST /analyze-text)

**Request:**
```http
POST /analyze-text
Content-Type: application/x-www-form-urlencoded

text: Gereksinim metni buraya...
reportType: none | html | pdf
modelName: llama3:latest (opsiyonel)
```

---

## 🤖 Model Yönetimi

### Desteklenen Modeller

| Model | Bellek | Hız | Kalite | Kullanım |
|-------|--------|-----|--------|----------|
| `llama3.2:1b` | ~1GB | ⚡ Çok Hızlı | İyi | Hızlı tarama |
| `llama3:latest` | ~4GB | 🚀 Hızlı | Çok İyi | **Varsayılan** |
| `llama3.2:3b` | ~6GB | 🐢 Orta | Mükemmel | Detaylı analiz |

### Model Değiştirme

**Web UI:** Model seçim dropdown'undan seçin

**API:**
```bash
curl -X POST "http://localhost:8080/api/models/switch" \
  -d "modelName=llama3.2:3b"
```

### Model Konfigürasyonu (model-configs.json)

```json
{
  "models": [
    {
      "name": "llama3:latest",
      "displayName": "Llama 3 (Dengeli)",
      "description": "En iyi dengeyi sunan varsayılan model",
      "temperature": 0.7,
      "maxTokens": 1024,
      "timeoutSeconds": 60,
      "memoryUsage": "~4GB",
      "speed": "Hızlı",
      "quality": "Çok İyi",
      "enabled": true
    }
  ],
  "currentModel": "llama3:latest"
}
```

---

## 📊 Raporlama

### HTML Rapor Özellikleri
- ✅ Modern, responsive tasarım
- ✅ Renkli kategoriler
- ✅ Hover efektleri
- ✅ Mobil uyumlu
- ✅ Yazdırma dostu

### PDF Rapor Özellikleri
- ✅ HTML'den otomatik dönüşüm (iText)
- ✅ Sayfa düzeni optimizasyonu
- ✅ Profesyonel format
- ✅ Kurumsal dokümantasyon

### Rapor Çıktı Dizini
```
reports/
├── dosya1_20241218_143000-report.html
├── dosya1_20241218_143000-report.pdf
└── batch_20241218_150000-report.html
```

---

## ⚙️ Konfigürasyon

### application.properties

```properties
# Sunucu Ayarları
server.port=8080
server.servlet.context-path=/

# Dosya Yükleme
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true

# Logging
logging.level.com.selda.rag=DEBUG
```

### Ollama Bağlantı Ayarları

```java
// OllamaClient.java içinde
private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

// Timeout ayarları
.readTimeout(120, TimeUnit.SECONDS)
.writeTimeout(120, TimeUnit.SECONDS)
.connectTimeout(30, TimeUnit.SECONDS)
```

---

## 🐛 Sorun Giderme

### Ollama Bağlantı Hataları

```bash
# Servisin çalıştığını kontrol edin
curl http://localhost:11434/api/tags

# Servisi yeniden başlatın
ollama serve
```

### Model Bulunamadı Hatası

```bash
# Mevcut modelleri listeleyin
ollama list

# Model indirin
ollama pull llama3:latest
```

### JSON Parse Hatası

Model yanıtı JSON formatında değilse:
1. Farklı bir model deneyin (llama3:latest önerilir)
2. `ModelManager.java` içindeki prompt template'i kontrol edin
3. Temperature değerini düşürün (0.5-0.7)

### Bellek Hataları

```bash
# Daha küçük model kullanın
ollama pull llama3.2:1b

# Java heap size artırın
java -Xmx4g -jar target/ba-llm-1.0-SNAPSHOT.jar
```

### Dosya Yükleme Hataları

```properties
# application.properties - dosya boyutu limitini artırın
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```

---

## 📈 Performans

| İşlem | Süre | Not |
|-------|------|-----|
| Tek dosya analizi | 3-10 sn | Model ve dosya boyutuna göre |
| Batch işleme | 3-8 sn/dosya | Paralel işleme yok |
| HTML rapor oluşturma | <1 sn | |
| PDF rapor oluşturma | 1-3 sn | |
| Bellek kullanımı | 500MB-2GB | Model boyutuna göre |

---

## 🧪 Test

### Otomatik Test Scriptleri

```bash
# Windows (PowerShell)
.\test-all-features.ps1

# Windows (Command Prompt)
test-all-features.bat

# Linux/Mac
./test-all-features.sh

# Web UI Testi
test-web-ui.bat
```

### Manuel Test

```bash
# 1. Ollama kontrolü
curl http://localhost:11434/api/tags

# 2. Uygulama başlat
mvn spring-boot:run

# 3. Tarayıcıda test
# http://localhost:8080
```

---

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

---

## 🙏 Teşekkürler

- [Ollama](https://ollama.ai/) - Yerel LLM servisi
- [Spring Boot](https://spring.io/projects/spring-boot) - Web framework
- [Apache PDFBox](https://pdfbox.apache.org/) - PDF işleme
- [Apache POI](https://poi.apache.org/) - Word işleme
- [iText](https://itextpdf.com/) - PDF oluşturma
- [OkHttp](https://square.github.io/okhttp/) - HTTP istemcisi
- [Bootstrap](https://getbootstrap.com/) - UI framework

---

## 👨‍💻 Geliştirici

**Selda Erdem**

📧 İletişim: [GitHub Issues](https://github.com/selda/ba-llm/issues)

---

<p align="center">
  <i>BA-LLM - İş Analizi Süreçlerinizi Otomatikleştirin</i>
</p>

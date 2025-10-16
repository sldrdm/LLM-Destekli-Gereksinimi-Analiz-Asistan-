# BA-LLM: LLM Destekli Gereksinim Analizi Asistanı

Bu proje, PDF ve Word dosyalarından gereksinim analizi yapan, Ollama ile entegre çalışan bir Java uygulamasıdır.

## 🚀 Özellikler

### ✅ Temel Özellikler
- **PDF ve Word dosya desteği** - Apache PDFBox ve POI ile
- **Ollama entegrasyonu** - Yerel LLM desteği
- **JSON çıktı formatı** - Yapılandırılmış analiz sonuçları
- **Batch işleme** - Klasör ve çoklu dosya analizi
- **Hata yönetimi** - Güçlü hata yakalama ve raporlama

### 📊 Raporlama Sistemi
- **HTML raporları** - Modern, responsive tasarım
- **PDF raporları** - Profesyonel dokümantasyon
- **Batch raporları** - Toplu analiz sonuçları
- **Otomatik dosya kaydetme** - JSON, HTML, PDF çıktıları

## 📋 Gereksinimler

- Java 8 veya üzeri
- Maven 3.6+
- Ollama servisi çalışır durumda
- Llama3 modeli indirilmiş

## 🔧 Kurulum

1. **Ollama'yı kurun ve başlatın:**
   ```bash
   # Ollama'yı indirin ve kurun
   # Servisi başlatın
   ollama serve
   
   # Llama3 modelini indirin
   ollama pull llama3:latest
   ```

2. **Projeyi derleyin:**
   ```bash
   mvn clean compile package -DskipTests
   ```

## 🎯 Kullanım

### Temel Kullanım
```bash
# Varsayılan test metni
java -jar target/ba-llm-1.0-SNAPSHOT.jar

# Tek dosya analizi
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.pdf

# JSON çıktı ile
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.docx --output sonuc.json
```

### Rapor Oluşturma
```bash
# HTML rapor
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.pdf --report html

# PDF rapor
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.docx --report pdf

# Özel çıktı dosyası ile
java -jar target/ba-llm-1.0-SNAPSHOT.jar dosya.pdf --output analiz.json --report html
```

### Batch İşleme
```bash
# Klasör analizi
java -jar target/ba-llm-1.0-SNAPSHOT.jar --batch ./requirements/

# Klasör analizi + PDF rapor
java -jar target/ba-llm-1.0-SNAPSHOT.jar --batch ./docs/ --report pdf

# Çoklu dosya analizi
java -jar target/ba-llm-1.0-SNAPSHOT.jar --files dosya1.pdf dosya2.docx

# Çoklu dosya + HTML rapor
java -jar target/ba-llm-1.0-SNAPSHOT.jar --files req1.pdf req2.docx --report html
```

### Yardım
```bash
java -jar target/ba-llm-1.0-SNAPSHOT.jar --help
```

## 🧪 Test Etme

### Otomatik Test Scripti
```bash
# Windows (PowerShell)
.\test-all-features.ps1

# Windows (Command Prompt)
test-all-features.bat

# Linux/Mac
./test-all-features.sh
```

### Manuel Test
```bash
# 1. Ollama servisini kontrol et
curl http://localhost:11434/api/tags

# 2. Basit test
java -jar target/ba-llm-1.0-SNAPSHOT.jar

# 3. Dosya testi
java -jar target/ba-llm-1.0-SNAPSHOT.jar test.docx --report html

# 4. Batch testi
java -jar target/ba-llm-1.0-SNAPSHOT.jar --batch . --report pdf
```

## 📁 Çıktı Formatları

### JSON Analiz Sonucu
```json
{
  "functionalRequirements": [
    "Kullanıcı girişi doğrulama",
    "E-posta ile kayıt onayı"
  ],
  "nonFunctionalRequirements": [
    "Güvenlik",
    "Performans"
  ],
  "missingInformation": [
    "Veritabanı şeması detayları"
  ],
  "priorityHints": [
    "Güvenlik öncelikli",
    "Hızlı yanıt süresi"
  ]
}
```

### HTML Rapor Özellikleri
- Modern, responsive tasarım
- Renkli kategoriler
- Hover efektleri
- Mobil uyumlu
- Profesyonel görünüm

### PDF Rapor Özellikleri
- HTML'den otomatik dönüşüm
- Sayfa düzeni optimizasyonu
- Yazdırma dostu format
- Profesyonel dokümantasyon

## 🔍 Desteklenen Dosya Formatları

- **PDF**: `.pdf` (Apache PDFBox ile)
- **Word**: `.docx` (Apache POI ile)

## ⚙️ Konfigürasyon

### Ollama Model Ayarları
Kod içinde model adını değiştirebilirsiniz:
```java
.put("model", "llama3:latest") // OllamaClient.java içinde
```

### Timeout Ayarları
```java
.readTimeout(120, TimeUnit.SECONDS)  // Uzun analizler için
.writeTimeout(120, TimeUnit.SECONDS)
.connectTimeout(30, TimeUnit.SECONDS)
```

## 🐛 Sorun Giderme

### Ollama Bağlantı Hataları
```bash
# Servisi kontrol et
curl http://localhost:11434/api/tags

# Servisi yeniden başlat
ollama serve
```

### Model Bulunamadı Hatası
```bash
# Mevcut modelleri listele
ollama list

# Model indir
ollama pull llama3:latest
```

### Bellek Hataları
- Daha küçük model kullanın (llama3:1b)
- Sistem belleğini artırın
- Java heap size'ı ayarlayın: `-Xmx4g`

## 📊 Performans

- **Tek dosya**: 5-15 saniye
- **Batch işleme**: Dosya başına 3-10 saniye
- **Rapor oluşturma**: 1-3 saniye
- **Bellek kullanımı**: ~500MB-2GB

## 🤝 Katkıda Bulunma

1. Fork edin
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Commit edin (`git commit -m 'Add amazing feature'`)
4. Push edin (`git push origin feature/amazing-feature`)
5. Pull Request açın

## 📝 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 🙏 Teşekkürler

- [Ollama](https://ollama.ai/) - Yerel LLM servisi
- [Apache PDFBox](https://pdfbox.apache.org/) - PDF işleme
- [Apache POI](https://poi.apache.org/) - Word işleme
- [iText](https://itextpdf.com/) - PDF oluşturma
- [OkHttp](https://square.github.io/okhttp/) - HTTP istemcisi

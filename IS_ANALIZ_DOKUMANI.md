# İŞ ANALİZ DOKÜMANACI
## BA-LLM Destekli Gereksinim Analizi Sistemi

---

## YÖNETİCİ ÖZETİ

Projenin amacı "TechGlobal Yazılım A.Ş." firmasının iş analiz personel memnuniyetini artırarak gereksinim analiz süreçlerini herkes için kolaylaştırmaktır.

Bu amaç doğrultusunda iş analisti "Selda Erdem", 1 ay boyunca firmadaki süreçleri gözlemleyerek 28 iş analisti ile toplantılar yapmıştır. Müşterilerden istenen işlem sonrası memnuniyet anketleri de göz önüne alınarak gereksinimleri belirlemiştir ve bu gereksinimlerin çözümüne yönelik çalışmalar yapmıştır. Gözlemler sonucunda mevcut süreçlerde doküman başına 30-45 dakika manuel analiz, yılda 5,500 doküman işleme, 28 farklı analiz stili, %15-20 hata oranı ve yılda 516 iş günü israfı tespit edilmiştir.

Oluşturulan çözümler için bir yazılım firması ile anlaşılmış ve iş analistlerinin sistem üzerinden dijital belge (PDF/Word) yükleyebilecekleri, yapay zeka destekli otomatik analiz yapabileceği bir web sitesi düzenlenmiştir. Sistem, Java Spring Boot ve Ollama LLM teknolojileri kullanılarak geliştirilmiş olup, dokümanları otomatik okuyup 4 kategoriye ayıran (fonksiyonel, fonksiyonel olmayan, eksik bilgi, öncelik), HTML/PDF rapor oluşturan ve 100+ dosyayı toplu işleyebilen bir platform sunmaktadır.

Başlatılan bu çalışma ilk adımda İş Analizi Departmanı için uygulanacak olup, başarılı olması halinde diğer departmanlar (Yazılım Geliştirme, QA, Dokümantasyon) için de hazırlanacaktır.

Çözümlerin gereksinimleri karşılayıp karşılamadığını görmek için 3 aylık bir süre tanınmıştır. Bu süreç boyunca aylık raporlamalar yapılmış olup "Performans Ölçütleri Analizi" başlığında sürece yönelik grafiklerle desteklenen detaylı bir raporlama oluşturulmuştur. İlk 3 ayda 6,834 doküman analiz edilmiş, %95.8 başarı oranı elde edilmiş ve 4,218 saat (527 iş günü) tasarruf sağlanmıştır.

Tüm bu analiz sonunda hedeflenen ve süreç sonundaki mevcut durum karşılaştırması "Temel Performans Göstergeleri" başlığında gösterilmiş olup, gelinen nokta hedefin tamamının karşılandığı ve hatta aşıldığı tespit edilmiştir. Analiz süresi %91.6 azalmış (hedef: %75), günlük kapasite %550 artmış (hedef: %50), doğruluk %91.3'e ulaşmış (hedef: %85), kullanıcı memnuniyeti 4.8/5 olmuş (hedef: 4.0/5), ROI %145.6 gerçekleşmiş (hedef: %28) ve geri ödeme süresi 7.4 ay olmuştur (hedef: 8 ay).

---

## İÇİNDEKİLER

1. [Yönetici Özeti](#yönetici-özeti)
2. [Bölüm 1 - Temel Tanımlar](#bölüm-1---temel-tanimlar)
3. [Bölüm 2 - Başlangıç Durum Analizi (AS-IS)](#bölüm-2---başlangıç-durum-analizi-as-is)

---

## BÖLÜM 1 - TEMEL TANIMLAR



### 1.1 Kurumun Tanımlanması

TechGlobal Yazılım A.Ş., kurumsal yazılım projeleri geliştiren şirketlere danışmanlık ve yazılım geliştirme hizmetleri sağlamak üzere büyük ölçekli firmalar ile iş birliği içinde çalışan bir teknoloji şirketidir. Yazılım projeleri için gereksinim analizi, tasarım, geliştirme ve test süreçlerinde destek veren firma, özellikle iş analizi alanında 28 uzman personel ile hizmet vermektedir. Aynı zamanda müşterilerine dijital dönüşüm, süreç iyileştirme ve teknoloji danışmanlığı gibi çeşitli hizmetler sunar.

Firma, 2008 yılında İstanbul'da kurulmuş olup, 450+ profesyonel çalışanı ve 120+ kurumsal müşterisi ile sektörde köklü bir konuma sahiptir. İş Analizi Departmanı, yılda 5,500 gereksinim dokümanı analiz etmekte ve her doküman için ortalama 30-45 dakika manuel süreç yürütmektedir.

Zaman içerisinde teknolojinin de gelişmesiyle zamanının gerisinde kaldığını düşünen firma müdürümüz Mehmet Yılmaz, iş analizi süreçlerini ve çalışan memnuniyetini artırmak için mevcut sistemi geliştirmek, gereksinim analizi sürecinde dijital ve yapay zeka destekli bir platforma geçmek istemektedir. Manuel süreçlerin neden olduğu verimsizlik, tutarsızlık ve yüksek maliyet sorunlarını çözmek amacıyla bu proje başlatılmıştır.

---

### 1.2 Paydaşlar

| Rol | İsim | Sorumluluk |
|-----|------|------------|
| **Proje Sponsoru** | Mehmet Yılmaz (Direktör) | Bütçe onayı, stratejik kararlar |
| **Ürün Sahibi** | Dr. Ayşe Demir (Departman Müdürü) | Backlog yönetimi, kabul kriterleri |
| **Proje Yöneticisi** | Can Özkan | Sprint yönetimi, risk yönetimi |
| **Teknik Lider** | Emre Şahin | Mimari tasarım, kod kalitesi |
| **Geliştirme Ekibi** | 5 kişi | Backend/Frontend geliştirme |
| **QA Ekibi** | 2 kişi | Test ve kalite güvence |
| **Son Kullanıcılar** | 28 İş Analisti | Sistem kullanımı, geri bildirim |
| **BT Altyapı** | 3 kişi | Sunucu, monitoring, güvenlik |
| **Bilgi Güvenliği** | Zeynep Arslan | KVKK/GDPR uyumluluğu |

---

### 1.3 Riskler

| Risk | Olasılık | Etki | Önlem |
|------|----------|------|-------|
| **Veri Güvenliği** | Orta | Kritik | On-premise LLM (Ollama), AES-256 şifreleme, KVKK uyumluluğu |
| **Model Doğruluğu** | Yüksek | Yüksek | Çoklu model desteği, human-in-the-loop, sürekli iyileştirme |
| **Entegrasyon** | Orta | Orta | Aşamalı yaklaşım, POC, adaptör katmanı |
| **Kullanıcı Adaptasyonu** | Yüksek | Orta | Değişim yönetimi, eğitim, pilot kullanıcılar |
| **Performans** | Orta | Orta | GPU sunucular, asenkron işleme, caching |

---

### 1.4 Süreçler

*<Proje kapsamındaki süreçlerin listelenmesi>*

1. **Gereksinim dokümanı alma süreci** - Müşteriden PDF/Word formatında gereksinim dokümanının email veya dosya yükleme yoluyla alınması
2. **Doküman okuma ve metin çıkarma süreci** - PDF veya Word dosyasından metnin otomatik olarak okunması ve sisteme aktarılması
3. **Yapay zeka ile analiz süreci** - LLM modeli kullanarak dokümanın fonksiyonel, fonksiyonel olmayan, eksik bilgi ve öncelik kategorilerine ayrılması
4. **Sonuçları inceleme ve düzeltme süreci** - İş analistinin AI sonuçlarını gözden geçirmesi, gerekirse düzeltme yapması
5. **Rapor oluşturma ve teslimat süreci** - Analiz sonuçlarının HTML veya PDF formatında otomatik raporlanması ve müşteriye sunulması

---

### 1.5 Temel İş İhtiyacı

**Problem**
- Yılda 5,500 doküman, doküman başına 30-45 dakika analiz
- 516 iş günü/yıl düşük katma değerli işlere harcanıyor
- %15-20 hata oranı, 28 farklı analiz stili
- ₺8.2M/yıl manuel süreç maliyeti
- 2023'te ₺4.5M proje kaybı (yavaşlık nedeniyle)

**İhtiyaç**
Yapay zeka destekli, otomatik gereksinim analizi sistemi:
- **Otomasyon**: PDF/Word okuma, otomatik kategorizasyon, rapor oluşturma
- **Hızlanma**: %75-85 zaman tasarrufu hedefi
- **Standardizasyon**: Tutarlı analiz kalitesi
- **Güvenlik**: On-premise (KVKK/GDPR uyumlu)
- **Ölçeklenebilirlik**: %50-100 kapasite artışı

**Hedefler ve Faydalar**
- Kısa vade (6 ay): %70 verimlilik artışı, %80 kullanıcı adaptasyonu
- Orta vade (12 ay): %60 kapasite artışı, ₺840K tasarruf, 8 ay ROI
- Uzun vade (24 ay): SaaS ürünleştirme, sektör liderliği

---

#### 1.5.3 İş Hedefleri ve Beklenen Faydalar (DETAYLI)

**Kısa Vadeli Hedefler** (İlk 6 Ay)
1. **Operasyonel Verimlilik**
   - Analiz süresinde **%70** azalma
   - İş analistlerinin **%50 zaman** kazanması
   - İlk 6 ayda **1,500 doküman** analizi

2. **Kullanıcı Adaptasyonu**
   - **%80** kullanıcı adaptasyon oranı
   - **4/5** kullanıcı memnuniyet puanı
   - **%90** günlük aktif kullanım

3. **Kalite İyileştirmesi**
   - Analiz tutarlılığında **%50** artış
   - Hata oranında **%40** azalma
   - Müşteri şikayetlerinde **%30** düşüş

**Orta Vadeli Hedefler** (6-12 Ay)
1. **Kapasite Artışı**
   - Ek istihdam olmadan **%60** kapasite artışı
   - Yeni müşteri projeleri alabilme kapasitesi
   - Büyüme hedeflerinin desteklenmesi

2. **Finansal Kazanım**
   - **450 iş günü/yıl** tasarrufu (2 FTE eşdeğeri)
   - **₺840,000/yıl** maliyet tasarrufu
   - **8 ay** yatırım geri dönüş süresi

3. **Entegrasyon**
   - JIRA ve Confluence entegrasyonu
   - Otomatik ticket oluşturma
   - Raporların merkezi sistemde saklanması

**Uzun Vadeli Hedefler** (12-24 Ay)
1. **Stratejik Dönüşüm**
   - İş analistlerinin **%60 zamanını** stratejik işlere ayırması
   - Danışmanlık hizmetlerinde **₺2 milyon** ek gelir
   - TechGlobal'in **"AI-First"** şirket imajı

2. **Ölçeklenebilirlik**
   - **10,000 doküman/yıl** işleme kapasitesi
   - **%100** büyüme desteği
   - Yeni iş kolları (BA-as-a-Service)

3. **Ürünleştirme**
   - BA-LLM'in SaaS ürünü olarak piyasaya sürülmesi
   - Diğer şirketlere lisanslama
   - Yeni gelir kaynağı (₺5-10 milyon/yıl potansiyel)

4. **İnovasyon Liderliği**
   - Sektörde **"AI-Driven BA"** öncüsü olma
   - Konferans, makale, patent
   - Marka değerinde artış

---

#### 1.5.4 Başarı Kriterleri (KPI'lar)

**Teknik KPI'lar**
| Metrik | Mevcut | Hedef (6 Ay) | Ölçüm Yöntemi |
|--------|--------|--------------|---------------|
| Analiz Süresi | 30-45 dk | 5-10 dk | Sistem log |
| Sistem Yanıt Süresi | - | < 5 saniye | Monitoring |
| Doğruluk Oranı | - | > %85 | Kullanıcı geri bildirimi |
| Sistem Erişilebilirlik | - | > %99 | Uptime monitoring |
| Eş zamanlı kullanıcı | - | 20+ | Load testing |

**İş KPI'ları**
| Metrik | Mevcut | Hedef (6 Ay) | Ölçüm Yöntemi |
|--------|--------|--------------|---------------|
| Günlük analiz kapasitesi | 200 doküman | 1,200 doküman | İş takip sistemi |
| Analist başına verimlilik | 200 dok/yıl | 350 dok/yıl | HR raporu |
| Analiz maliyeti (doküman başına) | ₺300 | ₺90 | Finans raporu |
| Müşteri teslimat süresi | 12-14 gün | 3-5 gün | Proje yönetimi |

**Kullanıcı KPI'ları**
| Metrik | Hedef (6 Ay) | Ölçüm Yöntemi |
|--------|--------------|---------------|
| Kullanıcı memnuniyeti | > 4/5 | Aylık anket |
| Günlük aktif kullanım | > %90 | Sistem analitik |
| Eğitim tamamlama | %100 | LMS sistemi |
| Destek ticket sayısı | < 5/hafta | JIRA Service Desk |

**Finansal KPI'lar**
| Metrik | Hedef (12 Ay) | Hesaplama |
|--------|---------------|-----------|
| Maliyet tasarrufu | ₺840,000/yıl | Zaman tasarrufu × maliyet |
| ROI | %150 | (Fayda - Yatırım) / Yatırım |
| Yatırım geri dönüş | 8 ay | Kümülatif fayda analizi |
| Ek gelir potansiyeli | ₺2M/yıl | Danışmanlık + SaaS |

---

#### 1.5.5 Proje Gerekçelendirmesi (Business Case)

**Yatırım Maliyetleri** (İlk Yıl)

| Kalem | Tutar (₺) |
|-------|-----------|
| Yazılım Geliştirme (5 kişi × 6 ay) | 720,000 |
| Proje Yönetimi | 120,000 |
| Kalite Güvence | 80,000 |
| Altyapı (Sunucu, GPU) | 200,000 |
| Lisanslar (Spring Boot, iText vs.) | 50,000 |
| Eğitim ve Değişim Yönetimi | 100,000 |
| Dış Danışmanlık (AI, Güvenlik) | 150,000 |
| **Toplam Yatırım** | **1,420,000** |

**Yıllık Faydalar**

| Kalem | Tutar (₺) |
|-------|-----------|
| İş gücü tasarrufu (2 FTE) | 840,000 |
| Hata düzeltme maliyeti azalışı | 180,000 |
| Müşteri memnuniyeti artışı (kayıp azalışı) | 300,000 |
| Ek danışmanlık geliri | 500,000 |
| **Toplam Yıllık Fayda** | **1,820,000** |

**Finansal Analiz**

| Metrik | Değer |
|--------|-------|
| Net Bugünkü Değer (NPV) - 3 yıl | ₺3,680,000 |
| Yatırım Getirisi (ROI) - 1 yıl | %28 |
| Yatırım Getirisi (ROI) - 3 yıl | %359 |
| Geri Ödeme Süresi (Payback Period) | 8 ay |
| İç Verim Oranı (IRR) | %85 |

**Sonuç**: Proje, finansal olarak **oldukça kazançlı** bir yatırımdır. İlk yıl sonunda %28 ROI, 3 yıl sonunda %359 ROI beklenmektedir.

---

## SONUÇ

BA-LLM Destekli Gereksinim Analizi Sistemi, TechGlobal Yazılım A.Ş.'nin iş analizi süreçlerini dönüştürmek, verimliliği artırmak ve dijital dönüşüm vizyonunu hayata geçirmek için kritik bir projedir. 

Proje, sadece operasyonel verimlilik sağlamakla kalmayacak, aynı zamanda şirketin sektördeki **inovasyon liderliği** konumunu güçlendirecek ve yeni **gelir akışları** yaratma potansiyeli taşımaktadır.

**Ana Başarı Faktörleri**:
- ✅ Güçlü teknik mimari ve güvenlik
- ✅ Kullanıcı odaklı tasarım ve değişim yönetimi
- ✅ Etkin risk yönetimi
- ✅ Üst yönetim desteği ve kaynaklar
- ✅ Ölçülebilir hedefler ve düzenli takip

**Sonraki Adımlar**:
1. Üst yönetim onayı ve bütçe tahsisi
2. Proje ekibinin kurulması
3. Detaylı gereksinim analizi (2 hafta)
4. Teknik tasarım ve POC (2 hafta)
5. Sprint bazlı geliştirme (16 hafta)
6. Test ve kullanıcı kabul (4 hafta)
7. Eğitim ve lansman (2 hafta)

**Tahmini Proje Süresi**: 26 hafta (6 ay)  
**Proje Başlangıç**: Q1 2024  
**Go-Live**: Q3 2024

---

## BÖLÜM 2 - BAŞLANGIÇ DURUM ANALİZİ (AS-IS)

**Mevcut Durum Özeti**: Manuel, kağıt tabanlı, verimsiz süreçler

### 2.1 İş Gereksinimleri

• İş analisti ofisinde oluşabilecek iş yükü ve kalabalığı önlemek  
• Gereksinim analizi sürecini dijitalleştirerek ofiste geçirilen vakti azaltmak  
• Çalışan personelin (28 iş analisti) iş yükünün azaltılması  
• İş analistlerinin doküman okuma ve kategorizasyon sürecini kolaylaştırmak  
• Manuel süreçleri otomatikleştirerek zaman tasarrufu sağlamak  
• Analiz kalitesini standardize ederek hata oranını düşürmek

### 2.2 Organizasyon

- Geleneksel yapı, değişime direnç
- "Biz hep böyle yaptık" sendromu
- Katı onay süreçleri (2-5 gün)
- BT bütçesi düşük (%8)

### 2.3 Süreçler

*<Derste ve vakalarda verilmiş olan bilgilere uygun olarak projenin başlangıç durumundaki mevcut duarum analizinin hazırlanmsı>*

#### 2.3.1 Gereksinim Dokümanı Alma Süreci

1. Müşteri tarafından gereksinim dokümanı email eki olarak gönderilir.
2. İş analisti departman ortak email kutusuna giriş yapar ve dokümanı indirir.
   a. Daha önce müşteri ile çalışılmadıysa müşteri bilgileri (firma adı, proje adı, iletişim bilgileri) Excel'e kaydedilir ve yeni bir proje klasörü oluşturulur.
3. Doküman bilgisayara kaydedilir ve formatı kontrol edilir (PDF, Word, Excel).
4. İş analistinin bilgisayarındaki "Projeler" klasörüne manuel olarak taşınır.
5. Doküman açılır ve okunabilir olup olmadığı kontrol edilir.
6. Yazdırma butonuna tıklanır ve doküman kağıt çıktı olarak alınır.
7. Kağıt doküman, iş analistinin masasındaki "Bekleyen İşler" klasörüne konulur.
8. İş analisti, dokümanı okumaya başlamak için uygun zaman bulduğunda işleme alır.

#### 2.3.2 Gereksinim Analizi ve Kategorizasyon Süreci

1. Doküman okunduktan sonra gereksinimlerin ayrıştırılması için kağıt üzerinde işaretlemeler yapılır.
2. Renkli kalemler kullanılarak kategoriler işaretlenir:
   a. Mavi kalem ile fonksiyonel gereksinimler altı çizilir.
   b. Turuncu kalem ile fonksiyonel olmayan gereksinimler (performans, güvenlik) işaretlenir.
   c. Kırmızı kalem ile eksik veya belirsiz bilgiler vurgulanır.
   d. Yeşil kalem ile öncelikli gereksinimler işaretlenir.
3. İş analistinin kendi Excel şablonu açılır (standart şablon olmadığı için her analistin farklı şablonu vardır).
4. Kağıttaki işaretlemeler, manuel olarak Excel'e girilir ve her gereksinim satır satır yazılır.
5. Excel dosyası "Proje_[MüşteriAdı]_Analiz_v1.xlsx" adıyla kaydedilir.
6. Eksik bilgiler için müşteriye sorulacak sorular Word dokümanında listelenir.
7. Soru listesi email ile müşteriye gönderilir ve yanıt beklenir (ortalama 1-3 gün).
8. Müşteri yanıtı geldiğinde Excel dosyası açılır, güncellemeler yapılır ve "v2" olarak kaydedilir.

#### 2.3.3 Rapor Hazırlama ve Onay Süreci

1. İş analisti, firma Word rapor şablonunu açar.
2. Excel'deki veriler, manuel olarak Word'e kopyala-yapıştır ile aktarılır.
3. Rapor formatlaması yapılır:
   a. Başlıklar düzenlenir ve numaralandırılır.
   b. Gereksinim maddeleri madde işaretli liste olarak yazılır.
   c. Tablolar oluşturulur ve hizalamalar yapılır.
   d. Sayfa numaraları ve içindekiler tablosu eklenir.
4. Rapor yazdırılır (kağıt çıktı).
5. Yazdırılan rapor, Senior BA'ya elden teslim edilir ve masasına bırakılır.
6. Senior BA, kağıt üzerinde kırmızı kalemle düzeltmeler yapar ve raporu geri verir (1-2 gün).
7. Düzeltmeler Word dosyasına işlenir ve tekrar yazdırılır. Eksik düzeltme yoksa departman müdürüne onaya götürülür, varsa tekrar Senior BA'ya gönderilir.
8. Departman müdürü raporu inceler ve onaylar (2-5 gün bekleme). Onay sonrası rapor PDF'e çevrilir.
9. PDF, email ile müşteriye gönderilir ve ağ klasörüne kaydedilir.

#### 2.3.4 Arşivleme ve Raporlama Süreci

1. Tamamlanan analiz raporları, ağ klasörüne manuel olarak kaydedilir.
2. İş analisti tarafından klasör yapısına uygun konum seçilir: `\\TechGlobal\BA\Projeler\[Müşteri]\[Proje]\Analizler\`
   a. Müşteri klasörü yoksa yeni klasör oluşturulur.
   b. Proje klasörü yoksa yeni klasör oluşturulur.
3. Dosya isimlendirme standardı olmadığı için iş analisti kendi tercihine göre isim verir (Proje_ABC_Analiz.pdf, ABC_Analiz_Final.pdf vb.).
4. Geçmiş raporlara ihtiyaç duyulduğunda ağ klasöründe manuel arama yapılır (15-30 dakika).
5. Versiyon kontrolü olmadığı için en güncel dosyayı bulmak zordur ve bazen yanlış versiyon kullanılır.

### 2.4 Teknoloji
- Office 2016 (eski), ağ klasörleri (yavaş)
- JIRA/Confluence kullanımı düşük (%20)
- 5-7 yıllık laptop'lar, arızalı yazıcılar
- **Teknoloji açığı**: Sektörden 5-7 yıl geride

### 2.5 Diğer Sorunlar
- Katı onay süreçleri (2-5 gün)
- Yazılım kurulum onayı 2-4 hafta
- Rakipler %40 daha hızlı, AI kullanıyor
- %45 tükenmişlik, yetenek kaybı
- KVKK/GDPR uyumsuzluk riski

**Sonuç**: Mevcut durum sürdürülebilir değil. Dijital dönüşüm şart.

---

## BÖLÜM 3 - BAŞLICA GEREKSİNİMLER

### 3.1 Gereksinimler Listesi

*<Verilen tablo üzerinden ortaya çıkarma faaliyetleri ve diğer çalışmalar sonucunda belirlenen gereksinimlerin listelenmesi>*

| No | Gereksinim Adı | Tipi | Gereksinim Kaynağı | Öncelik | Bağlı Gereksinim | Bağlantı Açıklaması |
|----|----------------|------|-------------------|---------|------------------|---------------------|
| 1 | Doküman Yükleme ve Okuma | Fonksiyonel | İş Analisti Talebi | Yüksek | - | Sistemin temel giriş noktası |
| 2 | PDF Formatı Desteği | Fonksiyonel | İş İhtiyacı | Yüksek | 1 | Müşteri dokümanları genelde PDF formatında |
| 3 | Word Formatı Desteği | Fonksiyonel | İş İhtiyacı | Yüksek | 1 | Müşteri dokümanları Word formatında olabilir |
| 4 | Yapay Zeka ile Otomatik Analiz | Fonksiyonel | İş İhtiyacı | Yüksek | 1, 2, 3 | Manuel süreçlerin otomasyonu için kritik |
| 5 | Fonksiyonel Gereksinim Tespiti | Fonksiyonel | İş Analisti Talebi | Yüksek | 4 | Sistemin yapması gerekenlerin belirlenmesi |
| 6 | Fonksiyonel Olmayan Gereksinim Tespiti | Fonksiyonel | İş Analisti Talebi | Yüksek | 4 | Performans, güvenlik gibi gereksinimlerin belirlenmesi |
| 7 | Eksik Bilgi Tespiti | Fonksiyonel | İş İhtiyacı | Orta | 4 | Müşteriye sorulacak soruların otomatik belirlenmesi |
| 8 | Öncelik Önerisi Sunma | Fonksiyonel | İş Analisti Talebi | Orta | 4 | Gereksinimlerin önceliklendirilmesi |
| 9 | Otomatik HTML Rapor Oluşturma | Fonksiyonel | İş İhtiyacı | Yüksek | 4, 5, 6, 7, 8 | Manuel rapor hazırlama süresini ortadan kaldırma |
| 10 | Otomatik PDF Rapor Oluşturma | Fonksiyonel | İş İhtiyacı | Yüksek | 9 | Resmi sunum için PDF formatı |
| 11 | Web Tabanlı Kullanıcı Arayüzü | Arayüz | İş Analisti Talebi | Yüksek | Tüm fonksiyonlar | Kolay erişim ve kullanım |
| 12 | Dosya Sürükle-Bırak Özelliği | Arayüz | İş Analisti Talebi | Orta | 1, 11 | Kullanıcı deneyimini iyileştirme |
| 13 | Metin Girişi ile Analiz | Fonksiyonel | İş Analisti Talebi | Orta | 4 | Dosya olmadan direkt metin analizi |
| 14 | Toplu Dosya İşleme (Batch) | Fonksiyonel | İş İhtiyacı | Yüksek | 1, 4 | Büyük projelerde 100+ dosya analizi |
| 15 | Çoklu LLM Model Desteği | Fonksiyonel | Teknik Gereksinim | Orta | 4 | Farklı modeller ile kalite artışı |
| 16 | Model Değiştirme Özelliği | Fonksiyonel | İş Analisti Talebi | Orta | 15 | Kullanıcının ihtiyacına göre model seçimi |
| 17 | JSON Çıktı Formatı | Fonksiyonel | Teknik Gereksinim | Orta | 4 | Diğer sistemlerle entegrasyon için |
| 18 | Veri Güvenliği (On-premise) | Fonksiyonel Olmayan | Yasal Zorunluluk (KVKK) | Yüksek | 4 | Müşteri verilerinin dışarı çıkmaması |
| 19 | Performans (Hızlı Yanıt) | Fonksiyonel Olmayan | İş İhtiyacı | Orta | 4, 11 | 5 dakika altında analiz tamamlanması |
| 20 | Sistem Erişilebilirliği | Fonksiyonel Olmayan | İş İhtiyacı | Yüksek | 11 | %99 uptime hedefi |

---

### 3.2 Gereksinim Tanımlamaları

*<Gereksinimlerin madde madde detaylı açıklanması>*

**Gereksinim 1: Doküman Yükleme ve Okuma**
- Sistem, kullanıcıların PDF ve Word formatındaki gereksinim dokümanlarını yükleyebilmesini sağlamalıdır.
- Dosya seçici (file picker) veya sürükle-bırak (drag & drop) yöntemi ile dosya yüklenebilmelidir.
- Maksimum 10 MB boyutundaki dosyalar kabul edilmelidir.
- Desteklenmeyen format yüklendiğinde kullanıcıya anlaşılır hata mesajı gösterilmelidir.

**Gereksinim 2: PDF Formatı Desteği**
- Sistem, PDF (.pdf) formatındaki dosyaları Apache PDFBox kütüphanesi ile okuyabilmelidir.
- PDF'den metin çıkarımı otomatik olarak yapılmalıdır.
- Bozuk veya şifreli PDF dosyaları için hata yönetimi yapılmalıdır.

**Gereksinim 3: Word Formatı Desteği**
- Sistem, Microsoft Word (.docx) formatındaki dosyaları Apache POI kütüphanesi ile okuyabilmelidir.
- Word'den metin çıkarımı otomatik olarak yapılmalıdır.
- Eski Word formatları (.doc) için uyarı mesajı gösterilmelidir.

**Gereksinim 4: Yapay Zeka ile Otomatik Analiz**
- Sistem, yüklenen dokümanları Ollama LLM servisi kullanarak otomatik olarak analiz etmelidir.
- Analiz, localhost:11434 adresindeki Ollama servisi ile yapılmalıdır.
- LLM modeli, doküman içeriğini anlayarak gereksinimleri kategorize etmelidir.
- Analiz süresi 5 dakikayı geçmemelidir.

**Gereksinim 5: Fonksiyonel Gereksinim Tespiti**
- Sistem, doküman içindeki fonksiyonel gereksinimleri (sistemin yapması gerekenleri) otomatik olarak tespit etmelidir.
- Tespit edilen fonksiyonel gereksinimler, JSON formatında "functionalRequirements" dizisinde dönmelidir.
- Her gereksinim, anlaşılır ve net bir cümle olarak ifade edilmelidir.

**Gereksinim 6: Fonksiyonel Olmayan Gereksinim Tespiti**
- Sistem, performans, güvenlik, uyumluluk gibi fonksiyonel olmayan gereksinimleri tespit etmelidir.
- Tespit edilen gereksinimler, "nonFunctionalRequirements" dizisinde dönmelidir.
- Kategorizasyon tutarlı ve standardize edilmiş olmalıdır.

**Gereksinim 7: Eksik Bilgi Tespiti**
- Sistem, dokümanda eksik veya belirsiz olan bilgileri otomatik olarak tespit etmelidir.
- Eksik bilgiler, "missingInformation" dizisinde soru formatında dönmelidir.
- İş analisti, bu listeyi müşteriye gönderebilmelidir.

**Gereksinim 8: Öncelik Önerisi Sunma**
- Sistem, gereksinimlerin uygulama önceliğine dair önerilerde bulunmalıdır.
- Öncelik önerileri, "priorityHints" dizisinde dönmelidir.
- Öneriler, risk, iş değeri ve teknik karmaşıklık göz önüne alınarak yapılmalıdır.

**Gereksinim 9: Otomatik HTML Rapor Oluşturma**
- Sistem, analiz sonuçlarını modern ve profesyonel HTML formatında otomatik olarak raporlamalıdır.
- HTML raporu, responsive tasarıma sahip olmalı ve mobil cihazlarda görüntülenebilmelidir.
- Rapor, 4 ana bölüm içermelidir: Fonksiyonel, Fonksiyonel Olmayan, Eksik Bilgiler, Öncelik İpuçları.
- Rapor oluşturma süresi 2 dakikayı geçmemelidir.

**Gereksinim 10: Otomatik PDF Rapor Oluşturma**
- Sistem, HTML raporunu PDF formatına dönüştürebilmelidir.
- PDF dönüşümü, iText HTML2PDF kütüphanesi ile yapılmalıdır.
- PDF, yazdırma dostu formatta olmalıdır.

**Gereksinim 11: Web Tabanlı Kullanıcı Arayüzü**
- Sistem, web tarayıcı üzerinden erişilebilir olmalıdır.
- Arayüz, Spring Boot ve Thymeleaf ile geliştirilmelidir.
- Bootstrap 5 ile modern ve responsive tasarım sağlanmalıdır.
- Kullanıcı, sisteme giriş yaparak tüm fonksiyonlara erişebilmelidir.

**Gereksinim 12: Dosya Sürükle-Bırak Özelliği**
- Kullanıcı, dosyaları sürükleyip bırakarak (drag & drop) yükleyebilmelidir.
- Sürükleme sırasında görsel geri bildirim (hover efekti) gösterilmelidir.
- Desteklenmeyen dosya sürüklendiğinde uyarı verilmelidir.

**Gereksinim 13: Metin Girişi ile Analiz**
- Kullanıcı, dosya yüklemeden doğrudan metin girerek analiz yapabilmelidir.
- Metin girişi için çok satırlı textarea alanı bulunmalıdır.
- Minimum 10 karakter metin girilmesi zorunlu olmalıdır.

**Gereksinim 14: Toplu Dosya İşleme (Batch)**
- Sistem, birden fazla dosyayı aynı anda analiz edebilmelidir.
- Komut satırından `--batch` parametresi ile klasör bazlı toplu işleme yapılabilmelidir.
- Komut satırından `--files` parametresi ile seçili dosyalar toplu işlenebilmelidir.
- Her dosya için ayrı analiz sonucu üretilmeli ve toplu rapor oluşturulmalıdır.

**Gereksinim 15: Çoklu LLM Model Desteği**
- Sistem, en az 3 farklı LLM modelini desteklemelidir (Llama 3.2 1B, Llama 3, Llama 3.2 3B).
- Her modelin farklı hız, bellek kullanımı ve kalite özellikleri olmalıdır.
- Model bilgileri (isim, açıklama, performans) kullanıcıya gösterilmelidir.

**Gereksinim 16: Model Değiştirme Özelliği**
- Kullanıcı, web arayüzünden aktif LLM modelini değiştirebilmelidir.
- Model değişikliği, sistem yeniden başlatılmadan yapılabilmelidir.
- Seçilen model, model-configs.json dosyasında saklanmalıdır.

**Gereksinim 17: JSON Çıktı Formatı**
- Analiz sonuçları, standart JSON formatında üretilmelidir.
- JSON şeması: functionalRequirements, nonFunctionalRequirements, missingInformation, priorityHints dizilerini içermelidir.
- JSON çıktısı, otomatik olarak dosyaya kaydedilmelidir.

**Gereksinim 18: Veri Güvenliği (On-premise)**
- Sistem, Ollama kullanarak on-premise (yerinde) LLM çözümü sunmalıdır.
- Hiçbir müşteri verisi, dış servislere (cloud) gönderilmemelidir.
- KVKK ve GDPR uyumluluğu sağlanmalıdır.
- Yüklenen dosyalar, analiz sonrası otomatik olarak silinmelidir.

**Gereksinim 19: Performans (Hızlı Yanıt)**
- Tek doküman analizi, 5 dakikadan kısa sürede tamamlanmalıdır.
- Web arayüzü, 2 saniyeden kısa sürede yüklenmelidir.
- Sistem, en az 10 eş zamanlı kullanıcıyı desteklemelidir.
- LLM timeout süreleri yapılandırılabilir olmalıdır (varsayılan: 120 saniye).

**Gereksinim 20: Sistem Erişilebilirliği**
- Sistem, %99 uptime (çalışır durumda olma) hedefine sahip olmalıdır.
- Hata durumlarında kullanıcıya anlamlı mesajlar gösterilmelidir.
- Sistem logları, hata ayıklama için saklanmalıdır.

---

#### ARŞİV - DETAYLI GEREKSİNİMLER

#### GRK-001: Doküman Yükleme ve Okuma (ARŞİV)

**Gereksinim Açıklaması:**  
Sistem, kullanıcıların gereksinim dokümanlarını sisteme yükleyebilmesini ve içeriğini otomatik olarak okuyabilmesini sağlamalıdır.

**Detaylı Gereksinimler:**

1. **Dosya Formatı Desteği**
   - Sistem, **PDF (.pdf)** formatındaki dosyaları okuyabilmelidir
   - Sistem, **Microsoft Word (.docx)** formatındaki dosyaları okuyabilmelidir
   - Desteklenmeyen format yüklendiğinde, kullanıcıya anlaşılır bir hata mesajı gösterilmelidir

2. **Dosya Yükleme Yöntemleri**
   - Kullanıcı, web arayüzünden **dosya seçici (file picker)** ile dosya yükleyebilmelidir
   - Kullanıcı, **sürükle-bırak (drag & drop)** yöntemiyle dosya yükleyebilmelidir
   - Komut satırı arayüzünden dosya yolu parametre olarak verilebilmelidir

3. **Dosya Boyutu ve Doğrulama**
   - Sistem, maksimum **10 MB** boyutundaki dosyaları kabul etmelidir
   - Dosya boyutu aşıldığında, kullanıcıya uyarı verilmelidir
   - Bozuk veya okunamayan dosyalar için hata yönetimi yapılmalıdır

4. **Metin Çıkarma**
   - PDF dosyalarından metin çıkarımı **Apache PDFBox** kütüphanesi ile yapılmalıdır
   - Word dosyalarından metin çıkarımı **Apache POI** kütüphanesi ile yapılmalıdır
   - Çıkarılan metin, UTF-8 encoding ile işlenmelidir

5. **Geçici Depolama**
   - Yüklenen dosyalar, analiz süresi boyunca **geçici klasörde (uploads/)** saklanmalıdır
   - Analiz tamamlandıktan sonra, geçici dosyalar **otomatik olarak silinmelidir**
   - Dosya isimleri, çakışmayı önlemek için **timestamp** ile benzersiz hale getirilmelidir

**Kabul Kriterleri:**
- ✅ PDF ve DOCX dosyaları başarıyla yüklenebilmeli
- ✅ Dosya içeriği doğru şekilde metin olarak çıkarılabilmeli
- ✅ Hata durumlarında kullanıcı bilgilendirilmeli
- ✅ Geçici dosyalar analiz sonrası temizlenmeli

---

#### GRK-002: Yapay Zeka Destekli Analiz

**Gereksinim Açıklaması:**  
Sistem, yüklenen gereksinim dokümanlarını veya metin girişlerini yapay zeka (LLM) kullanarak otomatik olarak analiz etmeli ve kategorize etmelidir.

**Detaylı Gereksinimler:**

1. **LLM Entegrasyonu**
   - Sistem, **Ollama** servisi ile entegre çalışmalıdır
   - Ollama servisi **localhost:11434** adresinde erişilebilir olmalıdır
   - Bağlantı hatalarında kullanıcıya anlaşılır mesaj gösterilmelidir

2. **Analiz Kategorileri**
   - Sistem, gereksinimleri **Fonksiyonel Gereksinimler** olarak ayırabilmelidir
   - Sistem, gereksinimleri **Fonksiyonel Olmayan Gereksinimler** (performans, güvenlik, uyumluluk) olarak tespit edebilmelidir
   - Sistem, **Eksik veya Belirsiz Bilgileri** işaretleyebilmelidir
   - Sistem, **Öncelik İpuçları** sunabilmelidir

3. **Prompt Engineering**
   - Sistem, LLM'ye gönderilen prompt'ları **ModelManager** üzerinden yönetmelidir
   - Prompt şablonları, model tipine göre **özelleştirilebilir** olmalıdır
   - Prompt, LLM'den **JSON formatında** yapılandırılmış çıktı talep etmelidir

4. **JSON Çıktı Formatı**
   - LLM yanıtı, aşağıdaki JSON şemasına uygun olmalıdır:
   ```json
   {
     "functionalRequirements": ["string"],
     "nonFunctionalRequirements": ["string"],
     "missingInformation": ["string"],
     "priorityHints": ["string"]
   }
   ```
   - Sistem, LLM yanıtından JSON'u **otomatik olarak çıkarabilmelidir** (extractJsonFromResponse)
   - Hatalı JSON durumunda, sistem **düzeltme ve tamamlama** yapabilmelidir

5. **Hata Yönetimi**
   - LLM yanıt vermezse veya timeout olursa, kullanıcıya bilgi verilmelidir
   - JSON parse edilemezse, detaylı hata mesajı gösterilmelidir
   - Analiz başarısız olursa, kullanıcıya tekrar deneme seçeneği sunulmalıdır

6. **Performans Parametreleri**
   - Sistem, **120 saniye** okuma timeout'u ile çalışmalıdır
   - Sistem, **120 saniye** yazma timeout'u ile çalışmalıdır
   - Sistem, **30 saniye** bağlantı timeout'u ile çalışmalıdır

**Kabul Kriterleri:**
- ✅ Doküman içeriği LLM tarafından analiz edilebilmeli
- ✅ Analiz sonucu 4 kategoriye ayrılabilmeli
- ✅ JSON formatında yapılandırılmış çıktı üretilmeli
- ✅ Hata durumları düzgün yönetilmeli

---

#### GRK-003: Çoklu Model Yönetimi

**Gereksinim Açıklaması:**  
Sistem, farklı LLM modellerini desteklemeli ve kullanıcıların ihtiyaçlarına göre model seçimi yapabilmelerini sağlamalıdır.

**Detaylı Gereksinimler:**

1. **Desteklenen Modeller**
   - Sistem, **Llama 3.2 1B** (hızlı model) desteklemelidir
   - Sistem, **Llama 3** (dengeli model - varsayılan) desteklemelidir
   - Sistem, **Llama 3.2 3B** (kaliteli model) desteklemelidir
   - Sistem, kullanıcı tanımlı **özel modeller** eklenebilmelidir

2. **Model Özellikleri**
   - Her model için **temperature** parametresi ayarlanabilmelidir
   - Her model için **max_tokens** (maksimum token sayısı) belirlenebilmelidir
   - Her model için **timeout süresi** yapılandırılabilmelidir
   - Her model için **prompt şablonu** özelleştirilebilmelidir

3. **Model Seçimi**
   - Kullanıcı, web arayüzünden **aktif modeli değiştirebilmelidir**
   - Komut satırından model adı parametre olarak verilebilmelidir
   - Seçilen model, **model-configs.json** dosyasında saklanmalıdır

4. **Model Bilgileri**
   - Sistem, her model için **görünen ad (display name)** göstermelidir
   - Sistem, model **açıklaması** (description) sunmalıdır
   - Sistem, model **hız**, **bellek kullanımı** ve **kalite** bilgilerini göstermelidir
   - Aktif model bilgisi, web arayüzünde **gerçek zamanlı** gösterilmelidir

5. **Model Performans İzleme**
   - Sistem, her modelin **yanıt sürelerini** loglamalıdır
   - Sistem, model başarı/başarısızlık oranlarını takip edebilmelidir

**Kabul Kriterleri:**
- ✅ En az 3 farklı model desteklenmeli
- ✅ Kullanıcı model değiştirebilmeli
- ✅ Model bilgileri görüntülenebilmeli
- ✅ Model parametreleri yapılandırılabilmeli

---

#### GRK-004: Metin Tabanlı Analiz

**Gereksinim Açıklaması:**  
Sistem, kullanıcıların dosya yüklemeden, doğrudan metin girerek gereksinim analizi yapabilmelerini sağlamalıdır.

**Detaylı Gereksinimler:**

1. **Metin Girişi**
   - Web arayüzünde **çok satırlı metin alanı (textarea)** bulunmalıdır
   - Kullanıcı, en az **10 karakter** metin girmelidir
   - Boş metin gönderildiğinde, uyarı mesajı gösterilmelidir

2. **Analiz İşlemi**
   - Girilen metin, dosya analizi ile **aynı süreçten** geçmelidir
   - Metin, LLM'ye gönderilmeden önce **temizlenmeli** (trim) olmalıdır
   - Analiz sonucu, dosya analizi ile **aynı formatta** dönmelidir

3. **Sonuç Gösterimi**
   - Analiz sonuçları, web arayüzünde **kategorilere ayrılarak** gösterilmelidir
   - Kullanıcı, sonuçları **HTML veya PDF** olarak indirebilmelidir

**Kabul Kriterleri:**
- ✅ Metin girişi ile analiz yapılabilmeli
- ✅ Dosya analizi ile aynı kalitede sonuç üretilmeli
- ✅ Sonuçlar görselleştirilebilmeli

---

#### GRK-005: Toplu Dosya İşleme (Batch)

**Gereksinim Açıklaması:**  
Sistem, birden fazla dosyayı aynı anda analiz edebilmeli ve toplu raporlama yapabilmelidir.

**Detaylı Gereksinimler:**

1. **Batch Modları**
   - Sistem, **klasör bazlı** batch işleme desteklemelidir (`--batch ./klasor/`)
   - Sistem, **dosya listesi bazlı** batch işleme desteklemelidir (`--files dosya1.pdf dosya2.docx`)
   - Her iki modda da desteklenen formatlar (PDF, DOCX) otomatik tespit edilmelidir

2. **Sıralı İşleme**
   - Dosyalar, **sırayla** analiz edilmelidir (paralel işleme opsiyonel)
   - Her dosya için **ilerleme durumu** gösterilmelidir
   - Başarılı ve başarısız dosyalar **ayrı ayrı** raporlanmalıdır

3. **Hata Toleransı**
   - Bir dosyanın analizi başarısız olursa, **diğer dosyalar** işlenmeye devam etmelidir
   - Hatalı dosyalar için **hata mesajı** kaydedilmelidir
   - Batch sonunda **özet rapor** (toplam, başarılı, başarısız) gösterilmelidir

4. **Toplu Çıktı**
   - Tüm analizler, **tek bir JSON dosyasında** birleştirilmelidir
   - JSON formatı:
   ```json
   {
     "totalFiles": 10,
     "successfulFiles": 9,
     "failedFiles": 1,
     "files": [
       {
         "fileName": "req1.pdf",
         "success": true,
         "analysis": { ... }
       }
     ]
   }
   ```
   - Batch raporu, **HTML veya PDF** formatında oluşturulabilmelidir

5. **Performans**
   - Sistem, **100+ dosyayı** kesintisiz işleyebilmelidir
   - Her dosya için ortalama **2-5 dakika** süre hedeflenmelidir

**Kabul Kriterleri:**
- ✅ Çoklu dosya analizi yapılabilmeli
- ✅ Hata toleransı sağlanmalı
- ✅ Toplu rapor oluşturulabilmeli
- ✅ 100+ dosya işlenebilmeli

---

#### GRK-006: Otomatik Rapor Oluşturma

**Gereksinim Açıklaması:**  
Sistem, analiz sonuçlarını profesyonel ve okunabilir HTML ve PDF raporları olarak otomatik oluşturmalıdır.

**Detaylı Gereksinimler:**

1. **HTML Rapor**
   - Sistem, analiz sonucunu **modern, responsive HTML** formatında oluşturmalıdır
   - HTML raporu, **gradient header**, **kategorize edilmiş bölümler** ve **hover efektleri** içermelidir
   - Rapor, **mobil uyumlu** olmalıdır
   - Rapor başlığında **kaynak dosya adı** ve **analiz tarihi** gösterilmelidir

2. **PDF Rapor**
   - Sistem, HTML raporunu **PDF formatına** dönüştürebilmelidir
   - PDF dönüşümü, **iText HTML2PDF** kütüphanesi ile yapılmalıdır
   - PDF, **yazdırma dostu** formatta olmalıdır
   - Geçici HTML dosyası, PDF oluşturulduktan sonra **silinmelidir**

3. **Rapor İçeriği**
   - Rapor, **4 ana bölüm** içermelidir:
     - 🔧 Fonksiyonel Gereksinimler
     - ⚡ Fonksiyonel Olmayan Gereksinimler
     - ❓ Eksik Bilgiler
     - 🎯 Öncelik İpuçları
   - Her bölümde, gereksinimler **madde madde** listelenmelidir
   - Boş bölümler için **"Bu bölümde öğe bulunmamaktadır"** mesajı gösterilmelidir

4. **Batch Rapor**
   - Toplu analiz için **özel batch rapor şablonu** kullanılmalıdır
   - Batch raporda, **her dosya ayrı bölümde** gösterilmelidir
   - Batch özet bilgileri (toplam, başarılı, başarısız) **üstte** yer almalıdır

5. **Dosya İsimlendirme**
   - Rapor dosyaları, **kaynak dosya adı + timestamp** ile oluşturulmalıdır
   - Örnek: `requirements_20241217_143022-report.html`
   - Özel karakterler, **alt çizgi (_)** ile değiştirilmelidir

**Kabul Kriterleri:**
- ✅ HTML rapor oluşturulabilmeli
- ✅ PDF rapor oluşturulabilmeli
- ✅ Raporlar profesyonel görünümlü olmalı
- ✅ Batch rapor desteklenmeli

---

#### GRK-007: Web Tabanlı Kullanıcı Arayüzü

**Gereksinim Açıklaması:**  
Sistem, kullanıcı dostu, modern ve responsive bir web arayüzü sunmalıdır.

**Detaylı Gereksinimler:**

1. **Teknoloji Stack**
   - Backend: **Spring Boot** framework kullanılmalıdır
   - Frontend: **Thymeleaf** template engine kullanılmalıdır
   - Stil: **Bootstrap 5** ve özel CSS kullanılmalıdır
   - İkonlar: **Font Awesome** kullanılmalıdır

2. **Ana Sayfa Bileşenleri**
   - **Dosya Yükleme Alanı**: Drag & drop destekli
   - **Metin Girişi Alanı**: Textarea ile direkt analiz
   - **Model Seçimi**: Dropdown ile model değiştirme
   - **Rapor Formatı Seçimi**: HTML/PDF/Sadece Ekran
   - **Analiz Butonu**: Analizi başlatma

3. **Sonuç Gösterimi**
   - Analiz sonuçları, **tab (sekme) yapısında** gösterilmelidir
   - Her kategori, **ayrı tab**'de olmalıdır
   - Sonuçlar, **renkli kartlar** içinde sunulmalıdır
   - **İlerleme göstergesi** (loading spinner) gösterilmelidir

4. **Kullanıcı Deneyimi**
   - Arayüz, **sezgisel ve kolay** kullanılabilir olmalıdır
   - Hata mesajları, **anlaşılır ve yardımcı** olmalıdır
   - Başarı mesajları, **yeşil alert** ile gösterilmelidir
   - Sistem durumu (model bilgisi), **header**'da gösterilmelidir

5. **Responsive Tasarım**
   - Arayüz, **masaüstü, tablet ve mobil** cihazlarda çalışmalıdır
   - Mobilde, bileşenler **dikey** sıralanmalıdır
   - Font boyutları ve butonlar, **dokunmatik ekran** için optimize edilmelidir

6. **Performans**
   - Sayfa yükleme süresi **2 saniyeden az** olmalıdır
   - AJAX istekleri, **asenkron** olmalıdır
   - Büyük dosya yüklenirken, **progress bar** gösterilmelidir

**Kabul Kriterleri:**
- ✅ Web arayüzü erişilebilir olmalı
- ✅ Tüm fonksiyonlar arayüzden kullanılabilmeli
- ✅ Responsive tasarım çalışmalı
- ✅ Kullanıcı deneyimi akıcı olmalı

---

#### GRK-008: Veri Güvenliği ve Gizlilik

**Gereksinim Açıklaması:**  
Sistem, müşteri verilerini güvenli bir şekilde işlemeli ve KVKK/GDPR uyumlu olmalıdır.

**Detaylı Gereksinimler:**

1. **On-Premise Çözüm**
   - Sistem, **Ollama** ile yerinde (on-premise) çalışmalıdır
   - Hiçbir veri, **dış servislere (cloud LLM)** gönderilmemelidir
   - Tüm işlemler, **şirket ağı içinde** gerçekleşmelidir

2. **Veri Saklama**
   - Yüklenen dosyalar, **geçici olarak** saklanmalıdır
   - Analiz tamamlandıktan sonra, dosyalar **otomatik silinmelidir**
   - Raporlar, **kullanıcı isteğine bağlı** olarak saklanmalıdır

3. **Erişim Kontrolü**
   - Web arayüzü, **localhost** veya **şirket ağı** ile sınırlı olmalıdır
   - Gelecekte, **kullanıcı kimlik doğrulama** eklenebilmelidir
   - Dosya yükleme, **dosya tipi kontrolü** ile kısıtlanmalıdır

4. **Denetim İzi (Audit Trail)**
   - Sistem, **her analiz işlemini** loglamalıdır
   - Log'lar, **tarih, kullanıcı, dosya adı, sonuç** içermelidir
   - Log dosyaları, **güvenli** bir konumda saklanmalıdır

5. **KVKK Uyumluluğu**
   - Sistem, **kişisel veri işleme** politikasına uygun olmalıdır
   - Veri saklama süreleri, **yasal gereksinimlere** uygun olmalıdır
   - Kullanıcılar, **verilerini silme** hakkına sahip olmalıdır

**Kabul Kriterleri:**
- ✅ Veriler dışarı çıkmamalı (on-premise)
- ✅ Geçici dosyalar otomatik silinmeli
- ✅ Denetim izi tutulmalı
- ✅ KVKK uyumlu olmalı

---

#### GRK-009: Performans ve Yanıt Süresi

**Gereksinim Açıklaması:**  
Sistem, kullanıcı beklentilerini karşılayacak hızda çalışmalı ve yüksek performans sunmalıdır.

**Detaylı Gereksinimler:**

1. **Analiz Süresi**
   - Tek doküman analizi, **5 dakikadan az** sürede tamamlanmalıdır
   - Ortalama analiz süresi, **2-3 dakika** olmalıdır
   - Küçük dokümanlar (<5 sayfa), **1 dakikadan az** sürede analiz edilmelidir

2. **Sistem Yanıt Süresi**
   - Web arayüzü, **2 saniyeden az** sürede yüklenmelidir
   - AJAX istekleri, **5 saniyeden az** sürede yanıt vermelidir
   - Model değiştirme, **1 saniyeden az** sürede tamamlanmalıdır

3. **Eş Zamanlı Kullanıcı**
   - Sistem, **en az 10 eş zamanlı kullanıcıyı** desteklemelidir
   - Yüksek yük altında, **yanıt süresi %50'den fazla artmamalıdır**

4. **Kaynak Kullanımı**
   - CPU kullanımı, analiz sırasında **%80'i geçmemelidir**
   - RAM kullanımı, **4GB'ı aşmamalıdır** (LLM hariç)
   - Disk I/O, **optimize edilmiş** olmalıdır

5. **Timeout Yönetimi**
   - LLM timeout süreleri, **yapılandırılabilir** olmalıdır
   - Timeout durumunda, kullanıcıya **anlamlı mesaj** gösterilmelidir
   - Kullanıcı, timeout olan işlemi **tekrar deneyebilmelidir**

**Kabul Kriterleri:**
- ✅ Analiz süresi 5 dakikadan az olmalı
- ✅ Web arayüzü hızlı yüklenmeli
- ✅ 10 eş zamanlı kullanıcı desteklenmeli
- ✅ Kaynak kullanımı optimize edilmeli

---

#### GRK-010: JSON Çıktı Formatı

**Gereksinim Açıklaması:**  
Sistem, analiz sonuçlarını yapılandırılmış JSON formatında üretmeli ve diğer sistemlerle entegrasyon için hazır hale getirmelidir.

**Detaylı Gereksinimler:**

1. **JSON Şeması**
   - Çıktı, **standart JSON şemasına** uygun olmalıdır
   - Tüm alanlar, **string array** formatında olmalıdır
   - JSON, **pretty-print** (okunabilir) formatında kaydedilmelidir

2. **Dosya Kaydetme**
   - JSON çıktısı, **otomatik olarak dosyaya** kaydedilmelidir
   - Dosya adı, **kaynak dosya adı + "-analysis-result.json"** formatında olmalıdır
   - Batch işlemde, **tüm sonuçlar tek JSON**'da birleştirilmelidir

3. **API Entegrasyonu**
   - JSON formatı, **REST API** ile dönülebilmelidir
   - Diğer sistemler, JSON'u **parse edebilmelidir**
   - JSON, **JIRA, Confluence** gibi araçlara import edilebilmelidir

4. **Hata Durumları**
   - JSON parse hatası durumunda, **detaylı hata mesajı** dönmelidir
   - Eksik alanlar, **boş array** olarak doldurulmalıdır

**Kabul Kriterleri:**
- ✅ JSON formatı standart olmalı
- ✅ Otomatik dosyaya kaydedilmeli
- ✅ Diğer sistemlerle entegre edilebilmeli
- ✅ Hata yönetimi yapılmalı

---

**Öncelik**: 7 yüksek, 3 orta | **Temel**: GRK-001, GRK-002

---

## BÖLÜM 4 - SİSTEM TANIMI

### 4.1 İlk Durumdaki Süreç / Çalışma Yapısı / Raporlar

*<Başlangıç durumundaki süreç, organizasyonel yapı veya raporların belirtilmesi ve açıklanması>*

#### 4.1.1 Mevcut İş Akışı

BA-LLM projesi öncesinde, TechGlobal Yazılım A.Ş.'nin İş Analizi Departmanı'nda gereksinim analizi süreci tamamen manuel olarak yürütülmekteydi. 28 iş analistinin her biri, kendi çalışma tarzına göre farklı yöntemler kullanmakta ve standart bir süreç bulunmamaktaydı.

**Süreç Akışı:**
Müşteriden email ile gelen gereksinim dokümanı (PDF veya Word), iş analisti tarafından indirilir ve yazdırılırdı. Kağıt üzerinde renkli kalemlerle (mavi: fonksiyonel, turuncu: fonksiyonel olmayan, kırmızı: eksik bilgi, yeşil: öncelikli) işaretlemeler yapılır ve notlar elle deftere yazılırdı. Daha sonra bu notlar, her analistin kendi Excel şablonuna manuel olarak girilir ve kategorizasyon yapılırdı. Eksik bilgiler için müşteriye email ile sorular gönderilir ve yanıt beklenirdi (1-3 gün). Yanıt geldikten sonra Excel güncellenirdi. Son olarak, Excel'deki veriler Word şablonuna kopyala-yapıştır ile aktarılır, 2-3 saat boyunca formatlama yapılır, yazdırılarak önce Senior BA'ya sonra departman müdürüne onaya götürülürdü (2-5 gün bekleme). Onay sonrası rapor PDF'e çevrilir ve müşteriye gönderilirdi.

**Organizasyonel Yapı:**
Departman, hiyerarşik bir yapıya sahipti. 8 Senior BA, 12 Mid-level BA ve 8 Junior BA bulunmaktaydı. Her seviye, farklı karmaşıklıktaki projeleri üstlenirdi. Senior BA'lar hem analiz yapar hem de Junior ve Mid-level analistlerin raporlarını kontrol ederdi. Departman müdürü Dr. Ayşe Demir, tüm nihai raporları onaylar ve stratejik kararları alırdı. Ancak, departmanlar arası iletişim zayıftı ve iş analistleri ile yazılım geliştirme ekibi arasında bilgi akışı çoğunlukla email ve Word dosyaları üzerinden sağlanırdı.

**Raporlar:**
Analiz raporları, Word şablonu kullanılarak manuel olarak hazırlanırdı. Her analistin şablonu biraz farklı olduğu için raporlar tutarsız görünüme sahipti. Rapor içeriği: Proje özeti, fonksiyonel gereksinimler listesi, fonksiyonel olmayan gereksinimler, eksik bilgiler ve öneriler bölümlerinden oluşurdu. Raporlar PDF'e çevrildikten sonra müşteriye email ile gönderilir ve ağ klasörüne kaydedilirdi. Ancak dosya isimlendirme standardı olmadığı için ("Proje_ABC_v1.pdf", "ABC_Final.pdf", "Analiz_ABC_son.pdf" gibi) geçmiş raporlara erişim zordu. Raporların hazırlanması ortalama 2-3 saat sürerdi.

**Performans Metrikleri:**
- Doküman başına analiz süresi: 30-45 dakika
- Toplam süreç süresi (baştan sona): 10-12 gün
- Günlük analist kapasitesi: 3-4 doküman
- Hata oranı: %15-20
- Müşteri şikayeti: Yılda 47 adet
- Kağıt kullanımı: Doküman başına ~80 sayfa

---

### 4.2 Geliştirilmiş Süreç / Çalışma Yapısı / Raporlar

*<Çalışma sonunda geliştirilen süreç, organizasyonel yapı veya raporların belirtilmesi ve açıklanması>*

#### 4.2.1 Yeni İş Akışı (BA-LLM ile)

BA-LLM Gereksinim Analizi Sistemi devreye alındıktan sonra, iş analizi süreci tamamen dijitalleşmiş ve otomatikleşmiştir. İş analistleri artık web tabanlı bir platform üzerinden çalışmakta ve yapay zeka desteği almaktadır.

**Geliştirilmiş Süreç Akışı:**
İş analisti, web tarayıcısından BA-LLM sistemine giriş yapar ve ana sayfada yer alan dosya yükleme alanına gereksinim dokümanını (PDF veya Word) sürükle-bırak yöntemi ile yükler. Sistem, dosyayı otomatik olarak okur ve 5 saniye içinde metni çıkarır. Ardından, Ollama LLM servisi devreye girer ve dokümanı 2-3 dakika içinde analiz ederek 4 kategoriye ayırır: fonksiyonel gereksinimler, fonksiyonel olmayan gereksinimler, eksik bilgiler ve öncelik ipuçları. İş analisti, web arayüzünde tab yapısında sunulan sonuçları inceler (10-15 dakika), gerekirse düzeltmeler yapar ve eksik bilgiler listesini müşteriye email ile gönderir. Son olarak, "Rapor Oluştur" butonuna tıklayarak HTML veya PDF formatında profesyonel rapor otomatik olarak oluşturulur (2 dakika). Rapor, Senior BA tarafından online sistemde incelenir ve onaylanır (15-20 dakika). Onaylanan rapor, sistem tarafından otomatik olarak müşteriye email ile gönderilir. Tüm süreç, 1-2 gün içinde ve toplam 30-45 dakika net iş gücü ile tamamlanır.

**Organizasyonel Yapı:**
BA-LLM sistemi ile birlikte, departmanın çalışma yapısı daha esnek ve verimli hale gelmiştir. Hiyerarşik yapı korunmuş ancak onay süreçleri dijitalleşmiştir. Senior BA'lar artık kağıt üzerinde değil, online sistemde raporları incelemekte ve yorum ekleyebilmektedir. Departman müdürü, dashboard üzerinden tüm ekibin iş yükünü, tamamlanan analizleri ve performans metriklerini gerçek zamanlı olarak takip edebilmektedir. İş analistleri, operasyonel işlere harcadıkları zamanın %70'inden %15'e düşmesi sayesinde, stratejik danışmanlık, paydaş yönetimi ve değer katan işlere daha fazla zaman ayırabilmektedir. Ayrıca, sistemin JSON çıktısı sayesinde, yazılım geliştirme ekibi ile entegrasyon kolaylaşmış ve JIRA'ya otomatik ticket oluşturma planlanmıştır.

**Raporlar:**
BA-LLM sistemi, analiz sonuçlarını otomatik olarak HTML ve PDF formatında profesyonel raporlara dönüştürmektedir. Raporlar, standart bir şablon kullanılarak oluşturulduğu için %100 tutarlı görünüme sahiptir. HTML raporları, responsive tasarıma sahip olup masaüstü, tablet ve mobil cihazlarda görüntülenebilir. PDF raporları ise yazdırma dostu formattadır ve resmi sunumlarda kullanılabilir. Rapor içeriği: Proje bilgileri, analiz tarihi, 4 ana kategori (fonksiyonel, fonksiyonel olmayan, eksik bilgiler, öncelik ipuçları) ve her kategoride madde madde listelenmiş gereksinimlerden oluşur. Raporlar, otomatik olarak "kaynak_dosya_adı_tarih-report.html/pdf" formatında isimlendirilir ve sistem tarafından saklanır. Rapor hazırlama süresi, manuel 2-3 saatten otomatik 2 dakikaya düşmüştür.

**Performans Metrikleri:**
- Doküman başına analiz süresi: 3.8 dakika (eski: 30-45 dakika)
- Toplam süreç süresi: 1.2 gün (eski: 10-12 gün)
- Günlük analist kapasitesi: 22 doküman (eski: 3-4 doküman)
- Hata oranı: %4.2 (eski: %15-20)
- Müşteri şikayeti: 12 adet/yıl tahmini (eski: 47 adet/yıl)
- Kağıt kullanımı: 0 sayfa (eski: 80 sayfa/doküman)

#### 4.2.2 Teknoloji ve Modüller

BA-LLM sistemi, Java Spring Boot framework'ü üzerine inşa edilmiştir. Backend tarafında, `WebApplication.java` Spring Boot uygulamasının giriş noktasını oluştururken, `WebController.java` tüm HTTP endpoint'lerini yönetir. Doküman işleme için `DocumentReader.java` modülü, Apache PDFBox ve Apache POI kütüphanelerini kullanarak PDF ve Word dosyalarından otomatik metin çıkarımı yapar. Yapay zeka analizi, `OllamaClient.java` ve `ModelManager.java` modülleri tarafından gerçekleştirilir ve Ollama LLM servisi ile entegre çalışır. Toplu dosya işleme için `BatchAnalyzer.java` modülü, 100+ dosyayı sırayla veya paralel olarak işleyebilir. Rapor oluşturma ise `ReportGenerator.java` modülü tarafından yapılır ve iText HTML2PDF kütüphanesi ile HTML'den PDF'e dönüşüm sağlanır. Sistem, 3 farklı LLM modelini destekler: Llama 3.2 1B (hızlı), Llama 3 (dengeli), Llama 3.2 3B (kaliteli). Kullanıcılar, ihtiyaçlarına göre model seçimi yapabilir ve model parametrelerini (temperature, max_tokens) yapılandırabilir.

---

#### 4.2.2 Sistem Mimarisi ve Modüller

**Teknoloji Stack:**
- **Backend**: Java 8+ ile Spring Boot Framework
- **Frontend**: Thymeleaf Template Engine + Bootstrap 5
- **AI Engine**: Ollama (On-premise LLM)
- **Doküman İşleme**: Apache PDFBox (PDF), Apache POI (Word)
- **Rapor Oluşturma**: iText HTML2PDF
- **HTTP Client**: OkHttp3
- **Veri Formatı**: JSON (Jackson)

**Ana Modüller:**

```
BA-LLM Sistemi
├── Web Arayüzü (WebApplication, WebController)
├── Doküman İşleme (DocumentReader)
├── AI Analiz Motoru (OllamaClient, ModelManager)
├── Batch İşleme (BatchAnalyzer)
├── Rapor Oluşturma (ReportGenerator)
└── Model Yönetimi (ModelConfig, ModelPerformance)
```

---

#### 4.2.3 Modül Detayları ve Çözülen Sorunlar

**1. Web Tabanlı Kullanıcı Arayüzü (WebApplication + WebController)**

**Çözülen Sorunlar:**
- ❌ **Eski**: Kağıt yazdırma, Excel/Word ile manuel çalışma
- ✅ **Yeni**: Modern, tek sayfa web uygulaması

**Özellikler:**
- **Spring Boot** ile RESTful web servisi
- **Responsive tasarım**: Masaüstü, tablet, mobil uyumlu
- **Drag & Drop**: Dosya yükleme kolaylığı
- **Real-time feedback**: Anlık ilerleme göstergesi
- **Tab-based UI**: Sonuçları kategorilere ayırarak gösterme

**Teknik Detaylar:**
- `WebApplication.java`: Spring Boot uygulamasının giriş noktası
- `WebController.java`: 
  - `/` endpoint: Ana sayfa
  - `/analyze` endpoint: Dosya analizi (POST)
  - `/analyze-text` endpoint: Metin analizi (POST)
  - `/api/models` endpoint: Model bilgileri (GET)
  - `/api/models/switch` endpoint: Model değiştirme (POST)
  - `/download/{fileName}` endpoint: Rapor indirme

**İyileştirme:**
- **Erişilebilirlik**: Herhangi bir cihazdan, tarayıcı ile erişim
- **Kullanım kolaylığı**: Sezgisel arayüz, 10 dakikada öğrenilir
- **Zaman tasarrufu**: Yazdırma, kağıt işleme, manuel veri girişi **ortadan kalktı**

---

**2. Doküman İşleme Modülü (DocumentReader)**

**Çözülen Sorunlar:**
- ❌ **Eski**: Manuel okuma, elle not alma
- ✅ **Yeni**: Otomatik metin çıkarma

**Özellikler:**
- **PDF desteği**: Apache PDFBox ile tüm PDF formatları
- **Word desteği**: Apache POI ile .docx formatı
- **Otomatik encoding**: UTF-8 ile Türkçe karakter desteği
- **Hata yönetimi**: Bozuk dosyalar için anlamlı mesajlar

**Teknik Detaylar:**
- `DocumentReader.java`: 
  - `readDocument(String filePath)`: Dosya tipine göre otomatik okuma
  - `readPDF(String filePath)`: PDF'den metin çıkarma
  - `readWord(String filePath)`: Word'den metin çıkarma
  - `fileExists(String filePath)`: Dosya kontrolü

**İyileştirme:**
- **Hız**: 25 sayfalık doküman **5 saniyede** okunuyor (eski: 45 dakika)
- **Doğruluk**: %100 metin çıkarma başarısı
- **Kağıtsız**: Yazdırma ihtiyacı **tamamen ortadan kalktı**

---

**3. AI Analiz Motoru (OllamaClient + ModelManager)**

**Çözülen Sorunlar:**
- ❌ **Eski**: Manuel kategorizasyon, tutarsız analizler
- ✅ **Yeni**: AI destekli, standardize analiz

**Özellikler:**
- **Ollama Entegrasyonu**: Yerinde (on-premise) LLM kullanımı
- **Çoklu Model Desteği**: 3 farklı model (hızlı, dengeli, kaliteli)
- **Prompt Engineering**: Optimize edilmiş prompt şablonları
- **JSON Çıktı**: Yapılandırılmış, parse edilebilir sonuçlar
- **Hata Toleransı**: JSON düzeltme, timeout yönetimi

**Teknik Detaylar:**
- `OllamaClient.java`:
  - `analyzeText(String prompt, String modelName)`: LLM ile analiz
  - `extractJsonFromResponse(String responseText)`: JSON çıkarma ve düzeltme
  - `normalizeAnalysisResult(JsonNode result)`: Sonuç normalizasyonu
  - `getModelInfo()`: Model bilgilerini getirme
  - `setModel(String modelName)`: Model değiştirme

- `ModelManager.java`:
  - **Singleton pattern** ile merkezi model yönetimi
  - `buildPrompt(String content)`: Model bazlı prompt oluşturma
  - `getModelParameters()`: Temperature, max_tokens ayarları
  - 3 farklı prompt şablonu: Default, Enhanced, Detailed

**Desteklenen Modeller:**
1. **Llama 3.2 1B (Hızlı)**: ~1GB RAM, çok hızlı, temel analizler
2. **Llama 3 (Dengeli)**: ~4GB RAM, hızlı, çok iyi kalite (varsayılan)
3. **Llama 3.2 3B (Kaliteli)**: ~6GB RAM, orta hız, mükemmel kalite

**Analiz Kategorileri:**
- 🔧 **Fonksiyonel Gereksinimler**: Sistemin yapması gerekenler
- ⚡ **Fonksiyonel Olmayan Gereksinimler**: Performans, güvenlik, uyumluluk
- ❓ **Eksik Bilgiler**: Belirsiz veya eksik noktalar
- 🎯 **Öncelik İpuçları**: Uygulama önceliği önerileri

**İyileştirme:**
- **Tutarlılık**: %100 standart analiz formatı
- **Hız**: 25 sayfalık doküman **2-3 dakikada** analiz ediliyor (eski: 2-3 saat)
- **Kalite**: AI, 20+ yıl deneyimli senior analist seviyesinde analiz yapıyor
- **Güvenlik**: Veriler dışarı çıkmıyor (on-premise)

---

**4. Batch İşleme Modülü (BatchAnalyzer)**

**Çözülen Sorunlar:**
- ❌ **Eski**: Her dosya tek tek, manuel işlem
- ✅ **Yeni**: Toplu işleme, otomatik sıralama

**Özellikler:**
- **Klasör bazlı analiz**: Tüm klasördeki PDF/DOCX dosyalarını otomatik tespit
- **Dosya listesi analizi**: Belirli dosyaları seçerek analiz
- **Hata toleransı**: Bir dosya başarısız olsa bile diğerleri işlenir
- **İlerleme takibi**: Her dosya için başarı/başarısızlık raporu
- **Toplu rapor**: Tüm sonuçlar tek raporda birleştirilir

**Teknik Detaylar:**
- `BatchAnalyzer.java`:
  - `analyzeDirectory(String directoryPath)`: Klasör analizi
  - `analyzeFiles(List<String> filePaths)`: Dosya listesi analizi
  - `analyzeFile(File file)`: Tek dosya analizi
  - `findSupportedFiles(File directory)`: PDF/DOCX dosyalarını bulma
  - `toJson(BatchResult result)`: Batch sonucunu JSON'a çevirme

- **BatchResult** sınıfı:
  - `getTotalFiles()`: Toplam dosya sayısı
  - `getSuccessfulFiles()`: Başarılı analiz sayısı
  - `getFailedFiles()`: Başarısız analiz sayısı

**Kullanım Senaryoları:**
```bash
# Klasör bazlı
java -jar ba-llm.jar --batch ./requirements/ --report pdf

# Dosya listesi bazlı
java -jar ba-llm.jar --files req1.pdf req2.docx req3.pdf --report html
```

**İyileştirme:**
- **Kapasite artışı**: 100 dosya **4-6 saatte** analiz ediliyor (eski: 75 iş günü!)
- **Otomasyon**: Manuel müdahale gerektirmiyor
- **Güvenilirlik**: Hata durumunda diğer dosyalar etkilenmiyor

---

**5. Otomatik Rapor Oluşturma (ReportGenerator)**

**Çözülen Sorunlar:**
- ❌ **Eski**: Manuel Word raporu, 2-3 saat formatlama
- ✅ **Yeni**: Otomatik HTML/PDF rapor, 2 dakika

**Özellikler:**
- **HTML Rapor**: Modern, responsive, gradient tasarım
- **PDF Rapor**: Yazdırma dostu, profesyonel görünüm
- **Otomatik formatlama**: Şirket şablonu otomatik uygulanır
- **Batch rapor**: Toplu analizler için özel şablon
- **Emoji desteği**: Kategoriler görsel olarak ayırt edilebilir

**Teknik Detaylar:**
- `ReportGenerator.java`:
  - `generateHTMLReport(JsonNode analysisResult, String outputFile, String sourceFile)`: HTML oluşturma
  - `generatePDFReport(JsonNode analysisResult, String outputFile, String sourceFile)`: PDF oluşturma
  - `generateBatchHTMLReport(BatchResult batchResult, String outputFile)`: Batch HTML
  - `generateBatchPDFReport(BatchResult batchResult, String outputFile)`: Batch PDF
  - `getCSS()`: Modern CSS stilleri

**Rapor İçeriği:**
- **Header**: Gradient arka plan, proje bilgileri, tarih
- **4 Ana Bölüm**: Fonksiyonel, Fonksiyonel Olmayan, Eksik Bilgiler, Öncelik
- **Responsive**: Mobil, tablet, masaüstü uyumlu
- **Footer**: BA-LLM imzası

**İyileştirme:**
- **Hız**: Rapor oluşturma **2 dakika** (eski: 2-3 saat)
- **Kalite**: %100 tutarlı, profesyonel format
- **Esneklik**: HTML (web paylaşım) veya PDF (resmi sunum)
- **Maliyet**: Kağıt israfı **sıfır**

---

**6. Model Performans ve Karşılaştırma (ModelPerformance)**

**Çözülen Sorunlar:**
- ❌ **Eski**: Hangi analist ne kadar iyi? Bilinmiyor
- ✅ **Yeni**: Model performansı ölçülebilir, karşılaştırılabilir

**Özellikler:**
- **A/B Testing**: Farklı modelleri aynı dokümanla test etme
- **Performans metrikleri**: Yanıt süresi, doğruluk, güven skoru
- **Model karşılaştırma**: Hangi model hangi durumda daha iyi?

**Teknik Detaylar:**
- `ModelPerformance.java`:
  - `compareModels(String content, List<String> modelNames)`: Model karşılaştırma
  - `testModel(String content, String modelName)`: Tek model testi
  - `PerformanceResult`: Yanıt süresi, başarı durumu, analiz sonucu

**İyileştirme:**
- **Sürekli iyileştirme**: En iyi model tespit edilir
- **Şeffaflık**: Performans metrikleri görünür
- **Optimizasyon**: Yavaş modeller optimize edilir

---

#### 4.2.4 Yeni Süreç Akışı (End-to-End)

**Örnek Senaryo (Yeni Durum - BA-LLM ile):**

> Müşteri, 25 sayfalık bir gereksinim dokümanı (PDF) gönderir. İş analisti Ayşe:
> 1. **Dakika 0-1**: Web arayüzüne giriş yapar, dosyayı sürükle-bırak ile yükler
> 2. **Dakika 1-3**: Sistem otomatik olarak:
>    - PDF'i okur (5 saniye)
>    - LLM ile analiz eder (2 dakika)
>    - 4 kategoriye ayırır
> 3. **Dakika 3-10**: Ayşe, sonuçları ekranda inceler:
>    - Fonksiyonel gereksinimler ✅
>    - Fonksiyonel olmayan gereksinimler ✅
>    - Eksik bilgiler ⚠️ (AI'ın tespit ettiği 3 soru)
>    - Öncelik ipuçları 🎯
> 4. **Dakika 10-15**: Gerekirse düzeltme yapar, eksik soruları müşteriye email ile gönderir
> 5. **Dakika 15-17**: "PDF Rapor Oluştur" butonuna tıklar, rapor otomatik hazırlanır
> 6. **Dakika 17-20**: Senior BA, online sistemde raporu inceler, onaylar
> 7. **Dakika 20-22**: Rapor otomatik olarak müşteriye email ile gönderilir
>
> **Toplam**: 22 dakika, 0 sayfa kağıt, %100 dijital

**Karşılaştırma:**

| Metrik | Eski Durum | Yeni Durum (BA-LLM) | İyileştirme |
|--------|------------|---------------------|-------------|
| **Toplam Süre** | 12 gün | 22 dakika | **%99.8 azalma** |
| **Net İş Gücü** | 6.5 saat | 22 dakika | **%94 azalma** |
| **Kağıt Kullanımı** | 80 sayfa | 0 sayfa | **%100 azalma** |
| **Hata Oranı** | %15-20 | <%5 | **%75 azalma** |
| **Tutarlılık** | 28 farklı stil | 1 standart | **%100 iyileşme** |
| **Günlük Kapasite** | 3-4 doküman | 20-25 doküman | **%500 artış** |

---

#### 4.2.5 İş Değeri ve Etkiler

**Operasyonel İyileştirmeler:**
- ✅ **Verimlilik**: %85-90 zaman tasarrufu
- ✅ **Kapasite**: 6x artış (analist başına)
- ✅ **Kalite**: Standart, tutarlı analizler
- ✅ **Hız**: 12 günden 22 dakikaya

**Finansal Etkiler:**
- 💰 **Maliyet tasarrufu**: ₺840,000/yıl (2 FTE eşdeğeri)
- 💰 **Kağıt tasarrufu**: ₺45,000/yıl
- 💰 **Ek gelir potansiyeli**: ₺2M/yıl (danışmanlık kapasitesi artışı)

**Stratejik Etkiler:**
- 🚀 **Rekabet avantajı**: Rakiplerden %40 daha hızlı
- 🚀 **Müşteri memnuniyeti**: Hızlı teslimat, yüksek kalite
- 🚀 **İnovasyon liderliği**: Sektörde AI kullanımında öncü
- 🚀 **Çalışan memnuniyeti**: Monoton işlerden kurtulma, stratejik çalışmalara odaklanma

**Kullanıcı Geri Bildirimleri:**

> "BA-LLM sayesinde artık gerçek iş analistliği yapıyorum. Excel'e veri girmek yerine, müşterilerle stratejik konuşmalar yapıyorum."  
> — Deniz K., Senior Business Analyst

> "İlk kullandığımda inanamadım. 3 saatlik işi 5 dakikada yaptı. Şimdi günde 20 doküman analiz edebiliyorum."  
> — Mehmet Y., Mid-level Business Analyst

> "Artık her analist aynı kalitede rapor üretiyor. Müşteri şikayetleri %80 azaldı."  
> — Dr. Ayşe D., İş Analizi Departman Müdürü

---

#### 4.2.6 Teknik Üstünlükler

**1. On-Premise Güvenlik**
- Veriler **dışarı çıkmıyor** (Ollama yerinde)
- KVKK ve GDPR uyumlu
- Müşteri güveni maksimum

**2. Ölçeklenebilir Mimari**
- Spring Boot ile **mikroservis hazır**
- Horizontal scaling mümkün
- 100+ eş zamanlı kullanıcı desteklenebilir

**3. Modüler Yapı**
- Her modül **bağımsız** geliştirilebilir
- Yeni özellikler **kolayca** eklenebilir
- Test edilebilir, bakımı kolay

**4. Açık ve Esnek**
- Açık kaynak kütüphaneler
- Farklı LLM'ler entegre edilebilir
- JIRA, Confluence entegrasyonu hazır

---

## BÖLÜM 4 - ÖZET

BA-LLM Gereksinim Analizi Sistemi, **TechGlobal Yazılım A.Ş.'nin iş analizi süreçlerini kökten dönüştürmüştür**. 

**Eski Durum**: Manuel, kağıt tabanlı, yavaş, hatalı, tutarsız  
**Yeni Durum**: Otomatik, dijital, hızlı, doğru, standart

**6 Ana Modül** ile eksiksiz çözüm:
1. 🌐 Web Arayüzü - Kolay erişim
2. 📄 Doküman İşleme - Otomatik okuma
3. 🤖 AI Analiz - Akıllı kategorizasyon
4. 📦 Batch İşleme - Toplu kapasite
5. 📊 Rapor Oluşturma - Profesyonel çıktı
6. 🎛️ Model Yönetimi - Sürekli iyileştirme

**Sonuç**: %99.8 süre azalması, %500 kapasite artışı, ₺840K/yıl tasarruf

---

## BÖLÜM 5 - ÇÖZÜMÜN DEĞERLENDİRİLMESİ

*<Geliştirilen çözümün iş gereksinimini ne derecede karşıladığının açıklanması, (çeşitli varsayımlar yapılarak) sayısal veriler ile ortaya çıkarılan değerin gösterilmesi>*

### 5.1 İş Gereksinimlerinin Karşılanma Durumu

BA-LLM Gereksinim Analizi Sistemi, Bölüm 2.1'de tanımlanan iş gereksinimlerini karşılamak amacıyla geliştirilmiş ve 3 aylık test süreci sonunda aşağıdaki sonuçlar elde edilmiştir.

**Gereksinim 1: İş analisti ofisinde oluşabilecek iş yükü ve kalabalığı önlemek**
- **Hedef**: İş yükünün azaltılması ve iş akışının düzenlenmesi
- **Gerçekleşen**: Analiz süresi 45 dakikadan 3.8 dakikaya düşmüş (%91.6 azalma), günlük kapasite 3-4 doküman/analist'ten 22 dokümana çıkmıştır (%550 artış).
- **Değerlendirme**: ✅ Hedef tamamen karşılanmıştır. İş analistleri artık aynı sürede 6 kat daha fazla doküman işleyebilmekte, ofiste bekleyen iş yığılması ortadan kalkmıştır.

**Gereksinim 2: Gereksinim analizi sürecini dijitalleştirerek ofiste geçirilen vakti azaltmak**
- **Hedef**: Manuel kağıt işlemlerinin ortadan kaldırılması, dijital platform kullanımı
- **Gerçekleşen**: Kağıt kullanımı doküman başına 80 sayfadan 0 sayfaya düşmüştür (%100 azalma). Yazdırma, elle not alma, kağıt üzerinde işaretleme gibi süreçler tamamen ortadan kalkmıştır. Web tabanlı platform sayesinde iş analistleri ofiste fiziksel olarak bulunmadan da çalışabilmektedir.
- **Değerlendirme**: ✅ Hedef tamamen karşılanmıştır. Yılda 50,000 sayfa kağıt tasarrufu sağlanmış, ofiste geçirilen süre %85 azalmıştır.

**Gereksinim 3: Çalışan personelin (28 iş analisti) iş yükünün azaltılması**
- **Hedef**: İş analistlerinin operasyonel işlere harcadığı zamanın azaltılması
- **Gerçekleşen**: İş analistlerinin zamanının %70'i operasyonel işlere harcanırken, BA-LLM ile bu oran %15'e düşmüştür. Kalan %85 zaman, stratejik danışmanlık, paydaş yönetimi ve değer katan işlere ayrılmaktadır. Tükenmişlik oranı %45'ten %12'ye düşmüştür.
- **Değerlendirme**: ✅ Hedef aşılarak karşılanmıştır. İş analistleri artık daha az stresli ve daha verimli çalışmaktadır.

**Gereksinim 4: İş analistlerinin doküman okuma ve kategorizasyon sürecini kolaylaştırmak**
- **Hedef**: Manuel okuma ve kategorizasyon yerine otomatik sistem kullanımı
- **Gerçekleşen**: AI destekli otomatik analiz sayesinde, doküman okuma ve kategorizasyon süresi 2-3 saatten 2-3 dakikaya düşmüştür. Kategorizasyon tutarlılığı %98.7'ye ulaşmış, 28 farklı analiz stili yerine tek standart oluşmuştur.
- **Değerlendirme**: ✅ Hedef tamamen karşılanmıştır. Süreç hem kolaylaşmış hem de kalite artmıştır.

**Gereksinim 5: Manuel süreçleri otomatikleştirerek zaman tasarrufu sağlamak**
- **Hedef**: %75 zaman tasarrufu
- **Gerçekleşen**: %91.6 zaman tasarrufu sağlanmıştır. Yıllık 516 iş günü tasarruf edilmiş, bu da 2.3 FTE (tam zamanlı eşdeğer) çalışan kapasitesine denk gelmektedir.
- **Değerlendirme**: ✅ Hedef aşılarak karşılanmıştır (%75 hedef, %91.6 gerçekleşen).

**Gereksinim 6: Analiz kalitesini standardize ederek hata oranını düşürmek**
- **Hedef**: %60 hata azalması
- **Gerçekleşen**: Hata oranı %15-20'den %4.2'ye düşmüştür (%78.9 azalma). Müşteri şikayetleri yılda 47'den 12'ye düşmüştür (%74.5 azalma). Kategorizasyon tutarlılığı %98.7'ye ulaşmıştır.
- **Değerlendirme**: ✅ Hedef aşılarak karşılanmıştır (%60 hedef, %78.9 gerçekleşen).

**Genel Değerlendirme**: Tüm iş gereksinimleri **%100 karşılanmış** ve çoğu **hedeflerin üzerinde** gerçekleşmiştir.

---

### 5.2 Sayısal Veriler ile Değer Analizi

#### 5.2.1 Zaman Tasarrufu Analizi

**Varsayımlar:**
- Ortalama iş günü: 8 saat
- Çalışma günü/yıl: 220 gün
- İş analisti sayısı: 28 kişi
- Yıllık doküman sayısı: 5,500

**Hesaplama (Eski Durum):**
```
Doküman başına süre: 45 dakika = 0.75 saat
Yıllık toplam süre: 5,500 × 0.75 = 4,125 saat
İş günü eşdeğeri: 4,125 ÷ 8 = 516 iş günü
FTE eşdeğeri: 516 ÷ 220 = 2.3 FTE
```

**Hesaplama (Yeni Durum):**
```
Doküman başına süre: 3.8 dakika = 0.063 saat
Yıllık toplam süre: 5,500 × 0.063 = 347 saat
İş günü eşdeğeri: 347 ÷ 8 = 43 iş günü
FTE eşdeğeri: 43 ÷ 220 = 0.2 FTE
```

**Tasarruf:**
```
Zaman tasarrufu: 4,125 - 347 = 3,778 saat/yıl
İş günü tasarrufu: 516 - 43 = 473 iş günü/yıl
FTE tasarrufu: 2.3 - 0.2 = 2.1 FTE
Yüzdesel tasarruf: (3,778 ÷ 4,125) × 100 = %91.6
```

**Sonuç**: Yılda **473 iş günü** (2.1 FTE) tasarruf edilmiştir.

---

#### 5.2.2 Maliyet Tasarrufu Analizi

**Varsayımlar:**
- Ortalama iş analisti maaşı: ₺25,000/ay
- Yan haklar ve genel giderler: %40
- Toplam maliyet/analist: ₺35,000/ay = ₺420,000/yıl
- Kağıt maliyeti: ₺0.90/sayfa
- Yazdırma maliyeti: ₺0.10/sayfa

**İş Gücü Tasarrufu:**
```
2.1 FTE × ₺420,000/yıl = ₺882,000/yıl
```

**Kağıt ve Yazdırma Tasarrufu:**
```
Yıllık kağıt kullanımı (eski): 5,500 dok × 80 sayfa = 440,000 sayfa
Kağıt maliyeti: 440,000 × ₺0.90 = ₺396,000
Yazdırma maliyeti: 440,000 × ₺0.10 = ₺44,000
Toplam tasarruf: ₺440,000/yıl
```

**Hata Düzeltme Maliyeti Tasarrufu:**
```
Eski hata sayısı: 5,500 × %17.5 (ortalama) = 963 hata
Yeni hata sayısı: 5,500 × %4.2 = 231 hata
Önlenen hata: 963 - 231 = 732 hata
Hata düzeltme maliyeti: 732 × ₺5,000 = ₺3,660,000/yıl
```

**Toplam Yıllık Tasarruf:**
```
İş gücü: ₺882,000
Kağıt ve yazdırma: ₺440,000
Hata düzeltme: ₺3,660,000
TOPLAM: ₺4,982,000/yıl
```

---

#### 5.2.3 Kapasite Artışı Değeri

**Varsayımlar:**
- Ortalama proje değeri: ₺150,000
- Gereksinim analizi, proje değerinin %8'i: ₺12,000

**Eski Kapasite:**
```
28 analist × 220 gün × 3.5 doküman/gün = 21,560 doküman/yıl
Proje değeri: 21,560 × ₺12,000 = ₺258,720,000/yıl
```

**Yeni Kapasite:**
```
28 analist × 220 gün × 22 doküman/gün = 135,520 doküman/yıl
Proje değeri: 135,520 × ₺12,000 = ₺1,626,240,000/yıl
```

**Ek Kapasite Değeri:**
```
Kapasite artışı: 135,520 - 21,560 = 113,960 doküman/yıl
Ek değer: 113,960 × ₺12,000 = ₺1,367,520,000/yıl potansiyel
Gerçekçi ek gelir (%10 gerçekleşme): ₺136,752,000/yıl
```

**Sonuç**: Kapasite artışı sayesinde, ek personel istihdamı olmadan **₺136M/yıl** ek gelir potansiyeli yaratılmıştır.

---

#### 5.2.4 Yatırım Getirisi (ROI) Analizi

**Yatırım Maliyeti:**
```
Yazılım geliştirme (6 ay): ₺720,000
Proje yönetimi: ₺120,000
Kalite güvence: ₺80,000
Altyapı (sunucu, GPU): ₺200,000
Lisanslar: ₺50,000
Eğitim: ₺100,000
Dış danışmanlık: ₺150,000
TOPLAM YATIRIM: ₺1,420,000
```

**Yıllık Fayda:**
```
Maliyet tasarrufu: ₺4,982,000
Ek gelir (konservatif %5): ₺68,376,000
TOPLAM FAYDA: ₺73,358,000/yıl
```

**ROI Hesaplaması:**
```
ROI = (Fayda - Yatırım) ÷ Yatırım × 100
ROI = (₺73,358,000 - ₺1,420,000) ÷ ₺1,420,000 × 100
ROI = %5,065 (İlk yıl)

Geri Ödeme Süresi = Yatırım ÷ (Aylık Fayda)
Geri Ödeme = ₺1,420,000 ÷ (₺73,358,000 ÷ 12)
Geri Ödeme = 0.23 ay = 7 gün
```

**Sonuç**: Proje, **7 gün** içinde kendini amorti etmiştir ve yılda **%5,065 ROI** sağlamaktadır.

---

### 5.3 Performans Ölçütleri Analizi

#### 5.3.1 3 Aylık Kullanım Verileri (Ekim-Aralık 2024)

**Sistem Kullanım İstatistikleri:**

| Metrik | Değer |
|--------|-------|
| Toplam analiz sayısı | 6,834 doküman |
| Başarılı analiz | 6,547 doküman (%95.8) |
| Başarısız analiz | 287 doküman (%4.2) |
| Batch işlem | 1,248 doküman |
| HTML rapor | 3,421 adet |
| PDF rapor | 1,471 adet |
| Ortalama analiz süresi | 3.8 dakika |
| Ortalama doğruluk oranı | %91.3 |

**Aylık Performans Trendi:**

| Ay | Doküman Sayısı | Başarı Oranı | Ortalama Süre | Kullanıcı Memnuniyeti |
|----|----------------|--------------|---------------|----------------------|
| **Ekim 2024** | 1,847 | %93.2 | 4.5 dk | 4.2/5 |
| **Kasım 2024** | 2,456 | %96.1 | 3.9 dk | 4.6/5 |
| **Aralık 2024** | 2,531 | %97.5 | 3.2 dk | 4.8/5 |

**Analiz**: Sistem performansı her ay iyileşmiştir. Kullanıcılar sisteme alıştıkça, başarı oranı artmış ve analiz süresi kısalmıştır.

---

#### 5.3.2 Kullanıcı Adaptasyonu ve Memnuniyet

**Haftalık Adaptasyon Süreci:**

| Hafta | Aktif Kullanıcı | Kullanım Oranı | Günlük Ortalama Analiz |
|-------|-----------------|----------------|------------------------|
| Hafta 1 | 8 kişi | %28.6 | 12 doküman/analist |
| Hafta 2 | 15 kişi | %53.6 | 16 doküman/analist |
| Hafta 4 | 24 kişi | %85.7 | 19 doküman/analist |
| Hafta 8 | 27 kişi | %96.4 | 21 doküman/analist |
| Hafta 12 | 28 kişi | %100 | 22 doküman/analist |

**Kullanıcı Memnuniyet Anketi (28 Analist):**

| Kriter | Puan (5 üzerinden) |
|--------|-------------------|
| Kullanım kolaylığı | 4.7 |
| Analiz kalitesi | 4.6 |
| Zaman tasarrufu | 4.9 |
| Rapor kalitesi | 4.8 |
| Genel memnuniyet | 4.8 |

**Değerlendirme**: Kullanıcı adaptasyonu %100'e ulaşmış, memnuniyet 4.8/5 ile hedefin (4.0/5) üzerindedir.

---

### 5.4 Temel Performans Göstergeleri (KPI) - Hedef vs Gerçekleşen

| KPI | Hedef | Gerçekleşen | Başarı Oranı |
|-----|-------|-------------|--------------|
| **Analiz Süresi Azalması** | %75 | %91.6 | ✅ %122 |
| **Kapasite Artışı** | %50 | %550 | ✅ %1100 |
| **Hata Oranı Azalması** | %60 | %78.9 | ✅ %132 |
| **Kullanıcı Memnuniyeti** | 4.0/5 | 4.8/5 | ✅ %120 |
| **Kullanıcı Adaptasyonu** | %80 | %100 | ✅ %125 |
| **ROI** | %28 | %5,065 | ✅ %18,089 |
| **Geri Ödeme Süresi** | 8 ay | 7 gün | ✅ %9,900 |
| **Sistem Erişilebilirliği** | %99 | %99.7 | ✅ %101 |

**Sonuç**: Tüm KPI'lar **hedefin üzerinde** gerçekleşmiştir. Proje, **beklenenden çok daha başarılı** olmuştur.

---

### 5.5 Nihai Değerlendirme

BA-LLM Gereksinim Analizi Sistemi, TechGlobal Yazılım A.Ş. için **stratejik bir başarı** olmuştur. Bölüm 2.1'de tanımlanan 6 iş gereksiniminin tamamı karşılanmış, hatta çoğu hedeflerin üzerinde gerçekleşmiştir.

**Ortaya Çıkan Değer:**
- **Operasyonel**: %91.6 zaman tasarrufu, %550 kapasite artışı
- **Finansal**: ₺4.98M/yıl maliyet tasarrufu, ₺136M/yıl ek gelir potansiyeli
- **Kalite**: %78.9 hata azalması, %98.7 tutarlılık
- **İnsan**: 4.8/5 memnuniyet, %73 tükenmişlik azalması

**Proje Başarı Durumu**: ✅ **BAŞARILI - HEDEFLERİN ÜZERİNDE TAMAMLANDI**

Gelinen nokta, hedefin tamamının karşılandığı ve aşıldığı tespit edilmiştir.

#### 5.1.1 Teknik Performans KPI'ları

**Hedef vs Gerçekleşen Karşılaştırması:**

| KPI | Hedef (Bölüm 1) | Gerçekleşen (3 Ay) | Durum |
|-----|-----------------|-------------------|-------|
| **Analiz Süresi** | 5-10 dakika | **3.8 dakika** (ort.) | ✅ %62 daha iyi |
| **Sistem Yanıt Süresi** | <5 saniye | **2.1 saniye** (ort.) | ✅ %58 daha iyi |
| **Doğruluk Oranı** | >%85 | **%91.3** | ✅ %7.4 daha iyi |
| **Sistem Erişilebilirlik** | >%99 | **%99.7** | ✅ Hedef aşıldı |
| **Eş Zamanlı Kullanıcı** | 20+ | **28 kullanıcı** | ✅ %40 daha fazla |

**Detaylı Analiz Süreleri (Gerçek Veriler):**

| Doküman Boyutu | Hedef Süre | Gerçekleşen Süre | İyileştirme |
|----------------|------------|------------------|-------------|
| Küçük (<10 sayfa) | <2 dk | **1.2 dk** | ✅ %40 daha hızlı |
| Orta (10-30 sayfa) | 3-5 dk | **3.8 dk** | ✅ Hedefte |
| Büyük (30-50 sayfa) | 5-10 dk | **7.4 dk** | ✅ Hedefte |
| Çok Büyük (>50 sayfa) | 10-15 dk | **11.2 dk** | ✅ Hedefte |

**Sonuç**: Tüm teknik performans hedefleri **aşılmış** veya **karşılanmıştır**. Sistem, beklenenden **daha hızlı ve daha doğru** çalışmaktadır.

---

#### 5.1.2 İş Performans KPI'ları

**Operasyonel Verimlilik:**

| Metrik | Eski Durum | Yeni Durum | İyileştirme |
|--------|------------|------------|-------------|
| **Ortalama Analiz Süresi** | 45 dakika | **3.8 dakika** | **%91.6 azalma** ⬇️ |
| **Toplam Süreç Süresi** | 12 gün | **4.2 saat** | **%97.1 azalma** ⬇️ |
| **Günlük Analist Kapasitesi** | 3-4 doküman | **22 doküman** | **%550 artış** ⬆️ |
| **Hata Oranı** | %15-20 | **%4.2** | **%78.9 azalma** ⬇️ |
| **Müşteri Teslimat Süresi** | 12-14 gün | **1.2 gün** | **%91.4 azalma** ⬇️ |

**Kapasite ve Üretkenlik:**

| Dönem | Analiz Edilen Doküman | Analist Başına Ort. | Artış |
|-------|----------------------|---------------------|-------|
| **Q3 2024 (Öncesi)** | 1,247 doküman | 178 dok/analist | - |
| **Q4 2024 (Sonrası)** | 6,834 doküman | 976 dok/analist | **+448%** 🚀 |
| **Hedef (2025)** | 10,000+ doküman | 1,200 dok/analist | - |

**3 Aylık Kullanım İstatistikleri (Ekim-Aralık 2024):**
- **Toplam Analiz**: 6,834 doküman
- **Başarılı Analiz**: 6,547 doküman (%95.8 başarı oranı)
- **Batch İşlem**: 1,248 doküman (toplu analiz)
- **Oluşturulan Rapor**: 4,892 rapor (3,421 HTML, 1,471 PDF)
- **Toplam Zaman Tasarrufu**: **4,218 saat** (527 iş günü)

**Sonuç**: İş performans hedefleri **büyük oranda aşılmıştır**. Özellikle kapasite artışı (%550), beklentilerin **üzerindedir**.

---

#### 5.1.3 Kalite ve Tutarlılık KPI'ları

**Analiz Kalitesi:**

| Metrik | Eski Durum | Yeni Durum | İyileştirme |
|--------|------------|------------|-------------|
| **Kategorizasyon Tutarlılığı** | %45 | **%98.7** | **+119% iyileşme** ⬆️ |
| **Eksik Bilgi Tespiti** | %62 | **%89.4** | **+44% iyileşme** ⬆️ |
| **Müşteri Şikayeti** | 47 adet/yıl | **3 adet/3 ay** (12 adet/yıl tahmini) | **%74.5 azalma** ⬇️ |
| **Revizyon Oranı** | %28 | **%8.3** | **%70.4 azalma** ⬇️ |
| **Senior BA İnceleme Süresi** | 45 dk | **12 dk** | **%73.3 azalma** ⬇️ |

**Standardizasyon Başarısı:**
- ✅ **%100** raporlar aynı formatta
- ✅ **%98.7** kategorizasyon tutarlılığı
- ✅ **%95.8** ilk seferde doğru analiz
- ✅ **%91.7** müşteri onay oranı (ilk sunumda)

**Kullanıcı Geri Bildirimleri (Anket Sonuçları - 28 Analist):**

| Soru | Ortalama Puan | Hedef |
|------|---------------|-------|
| Kullanım kolaylığı | **4.7/5** | >4.0 ✅ |
| Analiz kalitesi | **4.6/5** | >4.0 ✅ |
| Zaman tasarrufu | **4.9/5** | >4.0 ✅ |
| Genel memnuniyet | **4.8/5** | >4.0 ✅ |

**Sonuç**: Kalite ve tutarlılık hedefleri **tamamıyla karşılanmıştır**. Müşteri şikayetleri **%74.5 azalmıştır**.

---

#### 5.1.4 Finansal KPI'lar

**Maliyet Tasarrufu (Yıllık Projeksiyon):**

| Kalem | Hesaplama | Tutar (₺/yıl) |
|-------|-----------|---------------|
| **İş Gücü Tasarrufu** | 4,218 saat × ₺200/saat | **₺843,600** |
| **Kağıt ve Yazdırma** | 50,000 sayfa × ₺0.90 | **₺45,000** |
| **Fazla Mesai Azalması** | 280 saat × ₺300/saat | **₺84,000** |
| **Hata Düzeltme Maliyeti** | 35 hata × ₺5,000 | **₺175,000** |
| **TOPLAM TASARRUF** | - | **₺1,147,600** |

**Yatırım ve ROI:**

| Kalem | Tutar (₺) |
|-------|-----------|
| **Toplam Yatırım** (6 ay) | ₺1,420,000 |
| **Yıllık Tasarruf** | ₺1,147,600 |
| **Ek Gelir** (kapasite artışı ile) | ₺2,340,000 |
| **Net Fayda (İlk Yıl)** | ₺2,067,600 |
| **ROI (İlk Yıl)** | **%145.6** 🎯 |
| **Geri Ödeme Süresi** | **7.4 ay** ⏱️ |

**3 Yıllık Projeksiyon:**

| Yıl | Tasarruf | Ek Gelir | Net Fayda | Kümülatif |
|-----|----------|----------|-----------|-----------|
| **2024 (Q4)** | ₺287K | ₺585K | ₺872K | ₺872K |
| **2025** | ₺1,148K | ₺2,340K | ₺3,488K | ₺4,360K |
| **2026** | ₺1,205K | ₺2,574K | ₺3,779K | ₺8,139K |
| **2027** | ₺1,265K | ₺2,832K | ₺4,097K | ₺12,236K |

**NPV (3 yıl, %10 iskonto)**: **₺9,847,000**  
**IRR (3 yıl)**: **%187.3**

**Sonuç**: Finansal hedefler **büyük oranda aşılmıştır**. ROI %145.6 (hedef: %28), geri ödeme 7.4 ay (hedef: 8 ay).

---

#### 5.1.5 Kullanıcı Adaptasyonu ve Değişim Yönetimi

**Adaptasyon Başarısı:**

| Metrik | Hedef | Gerçekleşen | Durum |
|--------|-------|-------------|-------|
| **Eğitim Tamamlama** | %100 | **%100** | ✅ |
| **Günlük Aktif Kullanım** | >%90 | **%96.4** | ✅ %7.1 daha iyi |
| **Kullanıcı Memnuniyeti** | >4/5 | **4.8/5** | ✅ %20 daha iyi |
| **Destek Ticket Sayısı** | <5/hafta | **2.3/hafta** | ✅ %54 daha az |

**Adaptasyon Süreci (Haftalık):**

| Hafta | Aktif Kullanıcı | Kullanım Oranı | Not |
|-------|-----------------|----------------|-----|
| **Hafta 1** | 8 kişi | %28.6 | Pilot kullanıcılar |
| **Hafta 2** | 15 kişi | %53.6 | İlk eğitim tamamlandı |
| **Hafta 4** | 24 kişi | %85.7 | Yaygınlaşma |
| **Hafta 8** | 27 kişi | **%96.4** | Tam adaptasyon ✅ |
| **Hafta 12** | 28 kişi | **%100** | Tüm ekip aktif 🎉 |

**Değişim Yönetimi Başarı Faktörleri:**
- ✅ **Şampiyonlar**: 3 pilot kullanıcı, ekibi motive etti
- ✅ **Eğitim**: 4 haftalık kapsamlı program
- ✅ **Destek**: İlk 3 ay yoğun destek (ortalama 2 saat yanıt)
- ✅ **İletişim**: Haftalık Q&A, başarı hikayeleri paylaşımı
- ✅ **Teşvik**: En aktif kullanıcılara tanınma ve ödül

**Kullanıcı Yorumları:**

> "İlk başta 'AI bizi işsiz bırakır' diye korkmuştum. Şimdi anlıyorum ki AI bizi daha değerli kılıyor. Artık stratejik işlere odaklanabiliyorum."  
> — Elif T., Junior Business Analyst (8 yıllık deneyim)

> "Sistemi kullanmaya başladıktan 2 hafta sonra, eski yönteme dönmeyi düşünemiyorum. Günde 20 doküman analiz edebiliyorum!"  
> — Can Ö., Mid-level Business Analyst

> "Müşterilerimiz, hızlı teslimatımızdan çok memnun. 2 büyük proje kazandık, çünkü rakiplerimizden 3 kat daha hızlıyız."  
> — Dr. Ayşe D., İş Analizi Departman Müdürü

**Sonuç**: Kullanıcı adaptasyonu **hedeflerin üzerinde** gerçekleşmiştir. %100 ekip aktif kullanıyor, memnuniyet %96.

---

### 5.2 Temel İş İhtiyacının Karşılanması

**Bölüm 1.5'te** tanımlanan **"Temel İş İhtiyacı"** şu şekilde özetlenmişti:

> "TechGlobal Yazılım A.Ş., iş analistlerinin gereksinim analizi süreçlerini **otomatikleştiren, hızlandıran ve standardize eden**, **yapay zeka destekli bir yazılım çözümüne** ihtiyaç duymaktadır."

#### 5.2.1 İhtiyaç Karşılama Matrisi

| İhtiyaç | Hedef | Gerçekleşen | Karşılanma |
|---------|-------|-------------|------------|
| **Otomasyon** | Manuel işlerin %70'i | **%91.6** | ✅ %130 |
| **Hızlanma** | %75 zaman tasarrufu | **%91.6** | ✅ %122 |
| **Standardizasyon** | %100 tutarlı format | **%98.7** | ✅ %99 |
| **Kalite** | %60 hata azalması | **%78.9** | ✅ %132 |
| **Kapasite** | %50 artış | **%550** | ✅ %1100 🚀 |
| **Güvenlik** | KVKK uyumlu | **%100 uyumlu** | ✅ %100 |

**Sonuç**: Tüm ihtiyaçlar **karşılanmış** ve çoğu **hedeflerin üzerinde** gerçekleşmiştir.

---

#### 5.2.2 Stratejik Hedeflere Ulaşma

**Kısa Vadeli Hedefler (İlk 6 Ay)** - ✅ **BAŞARILDI**

| Hedef | Durum | Gerçekleşme |
|-------|-------|-------------|
| Analiz süresinde %70 azalma | ✅ | **%91.6** (hedef aşıldı) |
| %80 kullanıcı adaptasyonu | ✅ | **%100** (hedef aşıldı) |
| 4/5 kullanıcı memnuniyeti | ✅ | **4.8/5** (hedef aşıldı) |
| %50 kalite iyileştirmesi | ✅ | **%119** (hedef aşıldı) |

**Orta Vadeli Hedefler (6-12 Ay)** - 🟢 **YOL ALINIYOR**

| Hedef | Durum | İlerleme |
|-------|-------|----------|
| %60 kapasite artışı | ✅ | **%550** (hedef aşıldı) |
| ₺840K/yıl tasarruf | ✅ | **₺1,148K** (hedef aşıldı) |
| 8 ay ROI | ✅ | **7.4 ay** (hedef aşıldı) |
| JIRA entegrasyonu | 🟡 | Q1 2025'te planlandı |

**Uzun Vadeli Hedefler (12-24 Ay)** - 🔵 **PLANLI**

| Hedef | Durum | Plan |
|-------|-------|------|
| %60 zaman stratejik işlere | 🟡 | Şu an %45, hedef yolunda |
| ₺2M ek gelir | 🟢 | ₺2.34M projeksiyon (hedef aşılacak) |
| SaaS ürünleştirme | 🔵 | 2025 Q3'te pilot |
| Sektör liderliği | 🟢 | 2 konferans konuşması, 1 makale |

**Sonuç**: Kısa vadeli hedefler **tamamen başarıldı**, orta ve uzun vadeli hedefler **yolunda**.

---

### 5.3 Beklenmeyen Faydalar ve Yan Etkiler

Proje, **planlanan faydaların** yanı sıra, **beklenmeyen olumlu etkiler** de yaratmıştır:

#### 5.3.1 Olumlu Yan Etkiler

**1. Çalışan Memnuniyeti ve Motivasyon Artışı**
- **Tükenmişlik (Burnout)**: %45 → **%12** (%73 azalma)
- **İş tatmini**: 3.2/5 → **4.5/5** (%41 artış)
- **İşten ayrılma niyeti**: %32 → **%7** (%78 azalma)

> "Artık sabahları işe gelmek için heyecanlanıyorum. Monoton işler yerine, müşterilerle stratejik konuşmalar yapıyorum."  
> — Zeynep A., Senior BA

**2. Yetenek Çekme ve Markalaşma**
- **İş başvurusu**: %60 artış (sektör ortalamasının üzerinde)
- **LinkedIn takipçi**: +2,400 (%85 artış)
- **Sektör görünürlüğü**: 2 konferans konuşması, 1 makale, 3 podcast

**3. Müşteri Memnuniyeti ve Yeni İş Fırsatları**
- **NPS (Net Promoter Score)**: 42 → **67** (%60 artış)
- **Müşteri referansı**: 8 yeni müşteri (BA-LLM sayesinde)
- **Proje kazanma oranı**: %58 → **%74** (%28 artış)

**4. Departmanlar Arası Sinerji**
- **Geliştirme ekibi**: Daha net gereksinimler, %35 daha az revizyon
- **QA ekibi**: Daha erken test başlangıcı, %28 daha az bug
- **Proje yönetimi**: Daha doğru tahminler, %42 daha az gecikme

**5. İnovasyon Kültürü**
- **Diğer departmanlar**: "Biz de AI kullanabilir miyiz?" talepleri
- **Yeni fikirler**: 12 otomasyon fikri (kod inceleme, test senaryosu üretimi)
- **Hackathon**: İlk kez düzenlendi, 45 katılımcı

---

#### 5.3.2 Yönetilen Zorluklar

Proje sürecinde bazı zorluklar yaşanmış, ancak **başarıyla yönetilmiştir**:

**1. Başlangıç Direnci (İlk 2 Hafta)**
- **Sorun**: 5 senior analist, "AI'ya güvenmiyorum" dedi
- **Çözüm**: Pilot kullanıcılar, başarı hikayeleri paylaştı
- **Sonuç**: 8. haftada %100 adaptasyon

**2. Model Doğruluğu Sorunları (İlk Ay)**
- **Sorun**: İlk modelde %78 doğruluk (hedef: %85)
- **Çözüm**: Prompt engineering, model değiştirme (Llama 3)
- **Sonuç**: %91.3 doğruluk (hedef aşıldı)

**3. Performans Sorunları (İlk 2 Ay)**
- **Sorun**: Peak saatlerde yavaşlama (10-15 saniye)
- **Çözüm**: GPU sunucu yükseltmesi, caching
- **Sonuç**: 2.1 saniye ortalama yanıt

**Sonuç**: Tüm zorluklar **zamanında tespit edildi** ve **etkili çözümlerle** yönetildi.

---

### 5.4 Sektör Karşılaştırması ve Rekabet Avantajı

#### 5.4.1 Rakip Analizi

TechGlobal'in BA-LLM sistemi, **sektörde öncü** bir konumdadır:

| Firma | AI Kullanımı | Analiz Süresi | Doğruluk | Durum |
|-------|--------------|---------------|----------|-------|
| **TechGlobal (BA-LLM)** | ✅ On-premise | **3.8 dk** | **%91.3** | 🥇 Lider |
| SoftwareTech A.Ş. | ✅ Cloud (GPT-4) | 5.2 dk | %87 | 🥈 Takipçi |
| DigiSolutions | ⚠️ Hibrit | 8.1 dk | %82 | 🥉 Geride |
| CodeMasters | ❌ Manuel | 35 dk | %75 | ❌ Çok geride |

**Rekabet Avantajları:**
1. ✅ **En hızlı**: 3.8 dakika (rakiplerden %27-53 daha hızlı)
2. ✅ **En doğru**: %91.3 (rakiplerden %5-20 daha doğru)
3. ✅ **En güvenli**: On-premise (veri dışarı çıkmıyor)
4. ✅ **En uygun maliyetli**: Açık kaynak LLM (cloud API maliyeti yok)

**Pazar Konumlandırması:**
- **2023**: Sektör ortalamasında
- **2024 Q4**: **Sektör lideri** (AI kullanımında)
- **2025 Hedef**: "AI-First BA" kategorisinde **referans firma**

---

### 5.5 Öneriler ve Gelecek Yol Haritası

#### 5.5.1 Kısa Vadeli İyileştirmeler (Q1-Q2 2025)

**1. JIRA ve Confluence Entegrasyonu**
- **Hedef**: Analizleri otomatik JIRA ticket'a dönüştürme
- **Fayda**: %30 ek zaman tasarrufu
- **Süre**: 8 hafta

**2. Kullanıcı Kimlik Doğrulama**
- **Hedef**: LDAP/Active Directory entegrasyonu
- **Fayda**: Denetim izi, güvenlik artışı
- **Süre**: 4 hafta

**3. Mobil Uygulama**
- **Hedef**: iOS/Android native app
- **Fayda**: Uzaktan erişim, esneklik
- **Süre**: 12 hafta

#### 5.5.2 Orta Vadeli Genişleme (Q3-Q4 2025)

**1. Çoklu Dil Desteği**
- **Hedef**: İngilizce, Almanca analiz
- **Fayda**: Uluslararası projeler
- **Süre**: 8 hafta

**2. Fine-tuned Model**
- **Hedef**: TechGlobal'e özel eğitilmiş model
- **Fayda**: %95+ doğruluk
- **Süre**: 16 hafta

**3. Diğer Departmanlara Yayılma**
- **Hedef**: Kod inceleme, test senaryosu üretimi
- **Fayda**: Şirket geneli otomasyon
- **Süre**: 24 hafta

#### 5.5.3 Uzun Vadeli Vizyon (2026+)

**1. SaaS Ürünleştirme**
- **Hedef**: BA-LLM'i diğer şirketlere satma
- **Potansiyel gelir**: ₺5-10M/yıl
- **Pazar**: 500+ yazılım şirketi (Türkiye)

**2. AI-Powered BA Platformu**
- **Hedef**: End-to-end BA süreçlerini otomatikleştirme
- **Özellikler**: Analiz + Tasarım + Test + Dokümantasyon
- **Vizyon**: "BA'ların GitHub Copilot'u"

---

### 5.6 Sonuç ve Genel Değerlendirme

#### 5.6.1 Proje Başarı Özeti

BA-LLM Gereksinim Analizi Sistemi, **TechGlobal Yazılım A.Ş. için dönüm noktası** olmuştur. Proje, **tüm hedeflerini aşarak** tamamlanmış ve **beklenenden daha fazla değer** yaratmıştır.

**Ana Başarılar:**

✅ **Teknik Başarı**
- %91.6 analiz süresi azalması (hedef: %75)
- %91.3 doğruluk oranı (hedef: %85)
- %99.7 sistem erişilebilirliği (hedef: %99)

✅ **İş Başarısı**
- %550 kapasite artışı (hedef: %50)
- ₺1,148K/yıl tasarruf (hedef: ₺840K)
- %145.6 ROI (hedef: %28)

✅ **İnsan Başarısı**
- %100 kullanıcı adaptasyonu (hedef: %80)
- 4.8/5 memnuniyet (hedef: 4.0)
- %73 tükenmişlik azalması

✅ **Stratejik Başarı**
- Sektör liderliği (AI kullanımında)
- %60 NPS artışı
- 8 yeni müşteri kazanımı

---

#### 5.6.2 Temel İş İhtiyacının Karşılanması - Nihai Değerlendirme

**Bölüm 1.5'te** tanımlanan temel iş ihtiyacı:

> "Gereksinim analizi süreçlerinin manuel, zaman alıcı ve hata yapma olasılığı yüksek yapısını dönüştürerek **verimlilik artışı, kalite iyileştirmesi, maliyet tasarrufu ve hata oranı düşüşü** sağlamak."

**Nihai Sonuç:**

| İhtiyaç | Hedef | Gerçekleşen | Başarı |
|---------|-------|-------------|--------|
| **Verimlilik Artışı** | %75 | **%91.6** | ✅ %122 |
| **Kalite İyileştirmesi** | Tutarlı çıktı | **%98.7 tutarlılık** | ✅ %99 |
| **Maliyet Tasarrufu** | ₺840K/yıl | **₺1,148K/yıl** | ✅ %137 |
| **Hata Oranı Düşüşü** | %60 azalma | **%78.9 azalma** | ✅ %132 |

**SONUÇ**: Temel iş ihtiyacı **%100 karşılanmış** ve **tüm hedefler aşılmıştır**. ✅

---

#### 5.6.3 Proje Değerlendirmesi - Yönetici Perspektifi

> **"BA-LLM projesi, TechGlobal'in son 5 yılda gerçekleştirdiği en başarılı dijital dönüşüm projesidir. Sadece operasyonel verimlilik sağlamakla kalmadı, şirket kültürümüzü değiştirdi ve sektörde öncü konuma getirdi. Bu proje, 'AI-First' stratejimizin ilk ve en başarılı adımıdır."**  
> — Mehmet Yılmaz, Yazılım Geliştirme Direktörü

> **"İlk başta şüpheliydim. 'AI, iş analistlerin yerini alır mı?' diye endişelendim. Şimdi görüyorum ki AI, ekibimi daha değerli kıldı. Analistlerim artık stratejik danışmanlar gibi çalışıyor. Müşterilerimiz, hızımızdan ve kalitemizden çok memnun. 2025'te 3 yeni analist almak yerine, mevcut ekiple %100 daha fazla iş yapacağız."**  
> — Dr. Ayşe Demir, İş Analizi Departman Müdürü

> **"Finansal açıdan bakıldığında, BA-LLM projesi 'no-brainer' bir yatırımdı. 7.4 ayda geri ödemesi, %145 ROI'si ve ₺12M+ 3 yıllık net faydası ile şirketimizin en karlı yatırımlarından biri. Ayrıca, SaaS ürünleştirme potansiyeli ile yeni bir gelir kaynağı yaratıyoruz."**  
> — Cem Aydın, CFO (Mali İşler Direktörü)

---

#### 5.6.4 Nihai Sonuç ve Tavsiye

**BA-LLM Gereksinim Analizi Sistemi**, TechGlobal Yazılım A.Ş. için **stratejik bir başarı** olmuştur. Proje:

✅ **Tüm teknik hedefleri aşmış**  
✅ **Tüm iş hedeflerini gerçekleştirmiş**  
✅ **Beklenmeyen olumlu etkiler yaratmış**  
✅ **Şirket kültürünü dönüştürmüş**  
✅ **Sektörde liderlik konumu sağlamış**

**Tavsiye:**

1. **Kısa Vadede (2025)**: JIRA entegrasyonu, mobil uygulama, çoklu dil desteği ile sistemi **daha da güçlendirin**
2. **Orta Vadede (2025-2026)**: Diğer departmanlara (geliştirme, QA, dokümantasyon) **AI otomasyonunu yayın**
3. **Uzun Vadede (2026+)**: BA-LLM'i **SaaS ürünü** olarak piyasaya sürün ve **yeni gelir kaynağı** yaratın

**Son Söz:**

> "BA-LLM projesi, sadece bir yazılım projesi değil, TechGlobal'in dijital dönüşüm yolculuğunun **sembol projesidir**. Bu başarı, şirketimizin inovasyon kapasitesini, ekip çalışmasını ve değişime açıklığını göstermektedir. BA-LLM, gelecekte yapacağımız onlarca AI projesinin **ilham kaynağı** olacaktır."

**Proje Durumu**: ✅ **BAŞARILI - HEDEFLERİN ÜZERİNDE TAMAMLANDI**

---

---

## EKLER

### EK-A: Proje Ekibi ve Katkıda Bulunanlar

**Proje Sponsoru**
- Mehmet Yılmaz - Yazılım Geliştirme Direktörü

**Ürün Sahibi**
- Dr. Ayşe Demir - İş Analizi Departman Müdürü

**Proje Yöneticisi**
- Can Özkan - Kıdemli Proje Yöneticisi

**Teknik Ekip**
- Emre Şahin - Senior Yazılım Mimarı (Teknik Lider)
- 5 Yazılım Geliştirici (Backend/Frontend)
- 2 QA Mühendisi

**Pilot Kullanıcılar (Şampiyonlar)**
- Deniz Kaya - Senior Business Analyst
- Elif Tuncer - Mid-level Business Analyst
- Ahmet Yıldız - Junior Business Analyst

**Destek Ekipleri**
- BT Altyapı Ekibi (3 kişi)
- Bilgi Güvenliği (Zeynep Arslan)
- İK Departmanı (Değişim Yönetimi)

**Teşekkürler:**
Tüm İş Analisti ekibine (28 kişi), sabırları ve yapıcı geri bildirimleri için teşekkür ederiz.

---

### EK-B: Teknik Özellikler Özeti

**Sistem Gereksinimleri:**
- **İşletim Sistemi**: Windows 10+, Linux, macOS
- **Java**: JDK 8 veya üzeri
- **RAM**: Minimum 8GB (Önerilen: 16GB)
- **Disk**: 10GB boş alan
- **GPU**: Önerilen (LLM performansı için)

**Kullanılan Teknolojiler:**
- Java 8+, Spring Boot 2.7+
- Thymeleaf, Bootstrap 5, Font Awesome
- Ollama (LLM), Apache PDFBox, Apache POI
- iText HTML2PDF, OkHttp3, Jackson (JSON)
- Maven 3.6+

**Desteklenen LLM Modelleri:**
- Llama 3.2 1B, Llama 3, Llama 3.2 3B
- Gelecekte: Mistral, Phi, özel modeller

---

### EK-C: Sözlük ve Kısaltmalar

| Terim | Açıklama |
|-------|----------|
| **AI** | Artificial Intelligence (Yapay Zeka) |
| **BA** | Business Analyst (İş Analisti) |
| **LLM** | Large Language Model (Büyük Dil Modeli) |
| **ROI** | Return on Investment (Yatırım Getirisi) |
| **NPV** | Net Present Value (Net Bugünkü Değer) |
| **IRR** | Internal Rate of Return (İç Verim Oranı) |
| **KPI** | Key Performance Indicator (Ana Performans Göstergesi) |
| **KVKK** | Kişisel Verilerin Korunması Kanunu |
| **GDPR** | General Data Protection Regulation |
| **SaaS** | Software as a Service |
| **API** | Application Programming Interface |
| **JSON** | JavaScript Object Notation |
| **PDF** | Portable Document Format |
| **HTML** | HyperText Markup Language |
| **NPS** | Net Promoter Score |
| **FTE** | Full Time Equivalent (Tam Zamanlı Eşdeğeri) |

---

### EK-D: Referanslar ve Kaynaklar

**Teknoloji Dokümantasyonu:**
1. Ollama Documentation - https://ollama.ai/
2. Spring Boot Reference - https://spring.io/projects/spring-boot
3. Apache PDFBox - https://pdfbox.apache.org/
4. Apache POI - https://poi.apache.org/
5. iText PDF - https://itextpdf.com/

**İş Analizi Metodolojileri:**
1. BABOK® Guide (Business Analysis Body of Knowledge)
2. Agile Requirements Modeling
3. IEEE 830 - Software Requirements Specification

**AI ve LLM Araştırmaları:**
1. "Attention Is All You Need" (Transformer Architecture)
2. "Language Models are Few-Shot Learners" (GPT-3 Paper)
3. "LLaMA: Open and Efficient Foundation Language Models"

---

## DOKÜMAN BİLGİLERİ

### Versiyon Geçmişi

| Versiyon | Tarih | Değişiklik | Hazırlayan |
|----------|-------|------------|------------|
| 0.1 | 15.10.2024 | İlk taslak | İş Analizi Departmanı |
| 0.5 | 01.11.2024 | Gereksinim detayları | Can Özkan (PM) |
| 0.8 | 20.11.2024 | Teknik detaylar | Emre Şahin (Teknik Lider) |
| 1.0 | 17.12.2024 | Final versiyon | Dr. Ayşe Demir |

### Onay Bilgileri

| Rol | İsim | İmza | Tarih |
|-----|------|------|-------|
| **Hazırlayan** | İş Analizi Departmanı | ✓ | 17.12.2024 |
| **İnceleyen** | Can Özkan (Proje Yöneticisi) | ✓ | 17.12.2024 |
| **Onaylayan** | Dr. Ayşe Demir (Departman Müdürü) | ✓ | 17.12.2024 |
| **Nihai Onay** | Mehmet Yılmaz (Direktör) | ✓ | 17.12.2024 |

### Doküman Özellikleri

- **Doküman Adı**: BA-LLM İş Analiz Dokümanı
- **Doküman Kodu**: BA-LLM-DOC-001
- **Versiyon**: 1.0
- **Sayfa Sayısı**: 2,600+ satır
- **Tarih**: 17 Aralık 2024
- **Durum**: ✅ Onaylandı
- **Gizlilik Seviyesi**: Dahili - Sadece TechGlobal Personeli
- **Dağıtım Listesi**: 
  - Üst Yönetim (CEO, CTO, CFO)
  - Yazılım Geliştirme Direktörlüğü
  - İş Analizi Departmanı
  - Proje Ekibi
  - BT Altyapı
  - Bilgi Güvenliği

### İletişim Bilgileri

**Proje ile ilgili sorularınız için:**
- **Email**: ba-llm-project@techglobal.com.tr
- **Telefon**: +90 (212) 555-1234
- **Slack**: #ba-llm-project
- **JIRA**: BA-LLM Project Board

**Teknik Destek:**
- **Email**: ba-llm-support@techglobal.com.tr
- **Telefon**: +90 (212) 555-1235 (Dahili: 4567)
- **Çalışma Saatleri**: 09:00-18:00 (Hafta içi)

---

## KAPANIŞ

BA-LLM Gereksinim Analizi Sistemi, **TechGlobal Yazılım A.Ş.'nin dijital dönüşüm yolculuğunda bir dönüm noktasıdır**. Bu doküman, projenin başlangıcından tamamlanmasına kadar olan tüm süreci, başarıları ve öğrenilenleri kapsamaktadır.

**Proje, tüm hedeflerini aşarak tamamlanmış ve TechGlobal'i sektörde AI kullanımında lider konuma getirmiştir.**

### Teşekkürler

Bu başarı, **28 iş analistinin** açık fikirliliği, **proje ekibinin** özverili çalışması, **üst yönetimin** vizyonu ve **tüm paydaşların** desteği ile mümkün olmuştur.

**"Gelecek, yapay zeka ile işbirliği yapan insanlarındır."**

---

**© 2024 TechGlobal Yazılım A.Ş. - Tüm Hakları Saklıdır**

*Bu doküman, TechGlobal Yazılım A.Ş.'nin fikri mülkiyetidir ve izinsiz çoğaltılamaz, dağıtılamaz veya yayınlanamaz.*

---

**DOKÜMAN SONU**


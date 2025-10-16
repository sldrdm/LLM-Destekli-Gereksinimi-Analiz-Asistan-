#!/bin/bash

echo "========================================"
echo "BA-LLM TÜM ÖZELLİKLER TEST SCRIPTI"
echo "========================================"
echo

# Renk kodları
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
WHITE='\033[1;37m'
NC='\033[0m' # No Color

# Ollama servisinin çalışıp çalışmadığını kontrol et
echo -e "${YELLOW}[1/8] Ollama servisi kontrol ediliyor...${NC}"
if curl -s http://localhost:11434/api/tags > /dev/null 2>&1; then
    echo -e "${GREEN}✅ Ollama servisi çalışıyor${NC}"
else
    echo -e "${RED}❌ HATA: Ollama servisi çalışmıyor!${NC}"
    echo -e "${RED}Lütfen Ollama'yı başlatın: ollama serve${NC}"
    read -p "Devam etmek için Enter'a basın..."
    exit 1
fi
echo

# Yardım menüsünü test et
echo -e "${YELLOW}[2/8] Yardım menüsü test ediliyor...${NC}"
java -jar target/ba-llm-1.0-SNAPSHOT.jar --help
echo

# Varsayılan test metni ile analiz
echo -e "${YELLOW}[3/8] Varsayılan test metni ile analiz...${NC}"
java -jar target/ba-llm-1.0-SNAPSHOT.jar
echo

# Tek dosya analizi (Word) - JSON çıktı
echo -e "${YELLOW}[4/8] Tek dosya analizi (Word) - JSON çıktı...${NC}"
java -jar target/ba-llm-1.0-SNAPSHOT.jar test.docx
echo

# Tek dosya analizi (Word) - HTML rapor
echo -e "${YELLOW}[5/8] Tek dosya analizi (Word) - HTML rapor...${NC}"
java -jar target/ba-llm-1.0-SNAPSHOT.jar deneme.docx --report html
echo

# Tek dosya analizi (Word) - PDF rapor
echo -e "${YELLOW}[6/8] Tek dosya analizi (Word) - PDF rapor...${NC}"
java -jar target/ba-llm-1.0-SNAPSHOT.jar test.docx --report pdf
echo

# Çoklu dosya analizi - HTML rapor
echo -e "${YELLOW}[7/8] Çoklu dosya analizi - HTML rapor...${NC}"
java -jar target/ba-llm-1.0-SNAPSHOT.jar --files deneme.docx test.docx --report html
echo

# Batch analizi (klasör) - PDF rapor
echo -e "${YELLOW}[8/8] Batch analizi (klasör) - PDF rapor...${NC}"
java -jar target/ba-llm-1.0-SNAPSHOT.jar --batch . --report pdf
echo

echo -e "${CYAN}========================================"
echo -e "TÜM TESTLER TAMAMLANDI!"
echo -e "========================================${NC}"
echo
echo -e "${YELLOW}Oluşturulan dosyalar:${NC}"
echo -e "${WHITE}- JSON çıktıları: *-analysis-result.json${NC}"
echo -e "${WHITE}- HTML raporları: *-report.html${NC}"
echo -e "${WHITE}- PDF raporları: *-report.pdf${NC}"
echo
echo -e "${YELLOW}Test sonuçları:${NC}"

# Oluşturulan dosyaları listele
if ls *-analysis-result.json >/dev/null 2>&1; then
    echo -e "${GREEN}JSON dosyaları:${NC}"
    ls *-analysis-result.json | sed 's/^/  - /'
fi

if ls *-report.html >/dev/null 2>&1; then
    echo -e "${GREEN}HTML dosyaları:${NC}"
    ls *-report.html | sed 's/^/  - /'
fi

if ls *-report.pdf >/dev/null 2>&1; then
    echo -e "${GREEN}PDF dosyaları:${NC}"
    ls *-report.pdf | sed 's/^/  - /'
fi

echo
echo -e "${GREEN}Test tamamlandı! Oluşturulan raporları inceleyebilirsiniz.${NC}"
read -p "Devam etmek için Enter'a basın..."

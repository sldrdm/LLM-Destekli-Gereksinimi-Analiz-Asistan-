// BA-LLM Web Arayüzü JavaScript

document.addEventListener('DOMContentLoaded', function() {
    const uploadArea = document.getElementById('uploadArea');
    const fileInput = document.getElementById('fileInput');
    const fileInfo = document.getElementById('fileInfo');
    const textInput = document.getElementById('textInput');
    const analyzeBtn = document.getElementById('analyzeBtn');
    const progressContainer = document.getElementById('progressContainer');
    const resultContainer = document.getElementById('resultContainer');
    const errorContainer = document.getElementById('errorContainer');
    const reportDownload = document.getElementById('reportDownload');
    
    let selectedFile = null;
    let isAnalyzing = false;

    // Dosya yükleme alanı event listeners
    uploadArea.addEventListener('click', () => fileInput.click());
    uploadArea.addEventListener('dragover', handleDragOver);
    uploadArea.addEventListener('dragleave', handleDragLeave);
    uploadArea.addEventListener('drop', handleDrop);
    fileInput.addEventListener('change', handleFileSelect);
    
    // Metin alanı değişiklik kontrolü
    textInput.addEventListener('input', checkAnalyzeButton);
    
    // Analiz butonu
    analyzeBtn.addEventListener('click', startAnalysis);

    function handleDragOver(e) {
        e.preventDefault();
        uploadArea.classList.add('dragover');
    }

    function handleDragLeave(e) {
        e.preventDefault();
        uploadArea.classList.remove('dragover');
    }

    function handleDrop(e) {
        e.preventDefault();
        uploadArea.classList.remove('dragover');
        
        const files = e.dataTransfer.files;
        if (files.length > 0) {
            handleFile(files[0]);
        }
    }

    function handleFileSelect(e) {
        const files = e.target.files;
        if (files.length > 0) {
            handleFile(files[0]);
        }
    }

    function handleFile(file) {
        // Dosya formatı kontrolü
        const allowedTypes = ['application/pdf', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'];
        if (!allowedTypes.includes(file.type)) {
            showError('Sadece PDF ve DOCX dosyaları desteklenir.');
            return;
        }

        // Dosya boyutu kontrolü (10MB)
        if (file.size > 10 * 1024 * 1024) {
            showError('Dosya boyutu 10MB\'dan küçük olmalıdır.');
            return;
        }

        selectedFile = file;
        showFileInfo(file);
        checkAnalyzeButton();
    }

    function showFileInfo(file) {
        document.getElementById('fileName').textContent = `📄 ${file.name}`;
        document.getElementById('fileSize').textContent = `📊 Boyut: ${formatFileSize(file.size)}`;
        fileInfo.style.display = 'block';
    }

    function formatFileSize(bytes) {
        if (bytes === 0) return '0 Bytes';
        const k = 1024;
        const sizes = ['Bytes', 'KB', 'MB', 'GB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    }

    function checkAnalyzeButton() {
        const hasFile = selectedFile !== null;
        const hasText = textInput.value.trim().length > 0;
        
        analyzeBtn.disabled = !(hasFile || hasText) || isAnalyzing;
        
        if (hasFile && hasText) {
            analyzeBtn.innerHTML = '<i class="fas fa-search"></i> Dosya ve Metin Analiz Et';
        } else if (hasFile) {
            analyzeBtn.innerHTML = '<i class="fas fa-search"></i> Dosya Analiz Et';
        } else if (hasText) {
            analyzeBtn.innerHTML = '<i class="fas fa-search"></i> Metin Analiz Et';
        } else {
            analyzeBtn.innerHTML = '<i class="fas fa-search"></i> Analizi Başlat';
        }
    }

    function startAnalysis() {
        if (isAnalyzing) return;

        const hasFile = selectedFile !== null;
        const hasText = textInput.value.trim().length > 0;
        const reportType = document.getElementById('reportType').value;

        if (!hasFile && !hasText) {
            showError('Lütfen bir dosya seçin veya metin girin.');
            return;
        }

        isAnalyzing = true;
        showProgress();
        hideResults();
        hideError();

        const formData = new FormData();
        
        if (hasFile) {
            formData.append('file', selectedFile);
        }
        
        if (hasText) {
            formData.append('text', textInput.value.trim());
        }
        
        formData.append('reportType', reportType);

        const endpoint = hasFile ? '/analyze' : '/analyze-text';

        fetch(endpoint, {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            hideProgress();
            
            if (data.success) {
                showResults(data);
                if (data.reportFile) {
                    showReportDownload(data.reportFile, data.reportType);
                }
            } else {
                showError(data.error || 'Analiz sırasında bir hata oluştu.');
            }
        })
        .catch(error => {
            hideProgress();
            showError('Sunucu ile bağlantı hatası: ' + error.message);
        })
        .finally(() => {
            isAnalyzing = false;
            checkAnalyzeButton();
        });
    }

    function showProgress() {
        progressContainer.style.display = 'block';
        analyzeBtn.disabled = true;
        analyzeBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Analiz Yapılıyor...';
    }

    function hideProgress() {
        progressContainer.style.display = 'none';
        analyzeBtn.innerHTML = '<i class="fas fa-search"></i> Analizi Başlat';
    }

    function showResults(data) {
        // Dosya detaylarını göster
        if (data.fileName) {
            const fileDetails = document.getElementById('fileDetails');
            fileDetails.innerHTML = `
                <h6><i class="fas fa-file"></i> ${data.fileName}</h6>
                <p><i class="fas fa-clock"></i> Analiz Zamanı: ${data.timestamp}</p>
                ${data.fileSize ? `<p><i class="fas fa-weight"></i> Dosya Boyutu: ${formatFileSize(data.fileSize)}</p>` : ''}
                ${data.textLength ? `<p><i class="fas fa-text-width"></i> Metin Uzunluğu: ${data.textLength} karakter</p>` : ''}
            `;
            fileDetails.style.display = 'block';
        }

        // Analiz sonuçlarını göster
        displayRequirements('functionalRequirements', data.analysisResult.functionalRequirements);
        displayRequirements('nonFunctionalRequirements', data.analysisResult.nonFunctionalRequirements);
        displayRequirements('missingInformation', data.analysisResult.missingInformation);
        displayRequirements('priorityHints', data.analysisResult.priorityHints);

        // Tab sayılarını güncelle
        updateTabCounts(data.analysisResult);

        resultContainer.style.display = 'block';
        
        // Sonuçlara kaydır
        resultContainer.scrollIntoView({ behavior: 'smooth' });
    }

    function displayRequirements(containerId, requirements) {
        const container = document.getElementById(containerId);
        
        if (!requirements || requirements.length === 0) {
            container.innerHTML = '<p class="text-muted"><i class="fas fa-info-circle"></i> Bu kategoride öğe bulunmamaktadır.</p>';
            return;
        }

        const html = requirements.map(req => 
            `<div class="requirement-item">${escapeHtml(req)}</div>`
        ).join('');
        
        container.innerHTML = html;
    }

    function updateTabCounts(analysisResult) {
        const tabs = {
            'functional-tab': analysisResult.functionalRequirements?.length || 0,
            'nonfunctional-tab': analysisResult.nonFunctionalRequirements?.length || 0,
            'missing-tab': analysisResult.missingInformation?.length || 0,
            'priority-tab': analysisResult.priorityHints?.length || 0
        };

        Object.entries(tabs).forEach(([tabId, count]) => {
            const tab = document.getElementById(tabId);
            const text = tab.textContent.trim();
            tab.innerHTML = text + ` <span class="badge bg-secondary">${count}</span>`;
        });
    }

    function showReportDownload(reportFile, reportType) {
        const downloadLink = document.getElementById('downloadLink');
        downloadLink.href = `/download/${reportFile}`;
        downloadLink.innerHTML = `<i class="fas fa-download"></i> ${reportType.toUpperCase()} Raporu İndir`;
        reportDownload.style.display = 'block';
    }

    function showError(message) {
        document.getElementById('errorMessage').textContent = message;
        errorContainer.style.display = 'block';
        errorContainer.scrollIntoView({ behavior: 'smooth' });
    }

    function hideError() {
        errorContainer.style.display = 'none';
    }

    function hideResults() {
        resultContainer.style.display = 'none';
        reportDownload.style.display = 'none';
        document.getElementById('fileDetails').style.display = 'none';
    }

    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    // Sayfa yüklendiğinde metin alanına odaklan
    textInput.focus();
    
    // Klavye kısayolları
    document.addEventListener('keydown', function(e) {
        if (e.ctrlKey && e.key === 'Enter') {
            if (!analyzeBtn.disabled) {
                startAnalysis();
            }
        }
    });
});

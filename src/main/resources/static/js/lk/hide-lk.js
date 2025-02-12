function togglePartnerSection() {
    const mainContent = document.getElementById('main');
    const partnerSection = document.getElementById('partner');

    // Если основной блок виден
    if (mainContent.style.display !== "none") {
        // Плавное исчезновение
        mainContent.classList.add("fade-out");
        setTimeout(() => {
            mainContent.style.display = "none";
            partnerSection.style.display = "flex";
            partnerSection.classList.add("fade-in");
        }, 500); // Время анимации 0.5 секунды
    } else {
        // Плавное возвращение основного блока
        partnerSection.classList.add("fade-out");
        setTimeout(() => {
            partnerSection.style.display = "none";
            mainContent.style.display = "flex";
            mainContent.classList.add("fade-in");
        }, 500);
    }
}

function togglePasswordSection() {
    const mainContent = document.getElementById('main');
    const passwordSection = document.getElementById('password-section');

    // Если основной блок виден
    if (mainContent.style.display !== "none") {
        // Плавное исчезновение основного блока
        mainContent.classList.add("fade-out");
        setTimeout(() => {
            mainContent.style.display = "none";
            passwordSection.style.display = "flex";
            passwordSection.classList.add("fade-in");
        }, 300);
    } else {
        // Плавное возвращение основного блока
        passwordSection.classList.add("fade-out");
        setTimeout(() => {
            passwordSection.style.display = "none";
            mainContent.style.display = "block";
            mainContent.classList.add("fade-in");
        }, 300);
    }
}

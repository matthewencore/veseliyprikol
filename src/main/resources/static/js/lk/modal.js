// Открытие/закрытие модального окна
function toggleModal() {
    const modal = document.getElementById('custom-modal');
    const body = document.body;
    const isActive = modal.classList.contains('active');

    if (isActive) {
        modal.classList.remove('active');
        body.style.overflow = ''; // Возвращаем прокрутку
    } else {
        modal.classList.add('active');
        body.style.overflow = 'hidden'; // Блокируем прокрутку
    }
}

// Копирование текста
function copyToClipboard(element) {
    const text = element.getAttribute('data-copy');
    navigator.clipboard.writeText(text).then(() => {
        element.textContent = 'Скопировано!';
        setTimeout(() => {
            element.textContent = text;
        }, 1000);
    });
}

// Закрытие модального окна по клику вне его
document.addEventListener('click', (event) => {
    const modal = document.getElementById('custom-modal');
    const modalContent = document.querySelector('.modal-content');

    if (modal.classList.contains('active') && !modalContent.contains(event.target) && !event.target.closest('.btn-password')) {
        toggleModal();
    }
});

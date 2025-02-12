const passwordsPerPage = 8;

// 📦 Пагинация паролей
function paginatePasswords(page) {
    const passwords = document.querySelectorAll('.password-card');
    const totalPages = Math.ceil(passwords.length / passwordsPerPage);

    passwords.forEach((card, index) => {
        card.style.display = (index >= (page - 1) * passwordsPerPage && index < page * passwordsPerPage) ? 'block' : 'none';
    });

    renderPagination('passwords-pagination', totalPages, paginatePasswords);
}

// 📄 Генерация кнопок пагинации
function renderPagination(containerId, totalPages, paginateFunc) {
    const container = document.getElementById(containerId);
    container.innerHTML = '';

    if (totalPages > 1) {
        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement('button');
            button.textContent = i;
            button.onclick = () => paginateFunc(i);
            if (i === 1) button.classList.add('active');
            container.appendChild(button);
        }
    }
}

// 📋 Копирование текста в буфер обмена
function copyToClipboard(element) {
    const textToCopy = element.getAttribute('data-copy');

    navigator.clipboard.writeText(textToCopy).then(() => {
        // ✅ Успех — показываем уведомление
        element.classList.add('copied');
        element.textContent = 'Скопировано!';

        // 🔄 Возврат к исходному значению
        setTimeout(() => {
            element.textContent = textToCopy.includes('@') ? textToCopy : '********';
            element.classList.remove('copied');
        }, 1500);
    }).catch(err => {
        console.error('Ошибка копирования:', err);
    });
}

// 👁️ Показать или скрыть пароль
function togglePassword(button) {
    const passwordSpan = button.previousElementSibling.querySelector('.password-hidden');
    const isHidden = passwordSpan.textContent === '********';
    const realPassword = passwordSpan.getAttribute('data-copy');

    passwordSpan.textContent = isHidden ? realPassword : '********';
    button.textContent = isHidden ? '🙈 Скрыть' : '👁️ Показать';
}




function filterPasswords() {
    const input = document.getElementById('password-search').value.toLowerCase();
    const cards = document.querySelectorAll('.password-card');

    cards.forEach(card => {
        const siteName = card.querySelector('h4').textContent.toLowerCase();
        card.style.display = siteName.includes(input) ? 'block' : 'none';
    });
}


// ➕ Заглушка для добавления пароля
function addPassword() {
    alert("Добавление пароля пока не реализовано! 🚀");
}


// 🚀 Инициализация
document.addEventListener('DOMContentLoaded', () => {
    paginatePasswords(1);
});
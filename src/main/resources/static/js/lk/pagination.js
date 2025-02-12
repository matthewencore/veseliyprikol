const programsPerPage = 8;
const contractsPerPage = 3;

// 📦 Пагинация программ с анимацией
function paginatePrograms(page) {
    const programs = document.querySelectorAll('.program-card');
    const programsList = document.querySelector('.programs-list');
    const totalPages = Math.ceil(programs.length / programsPerPage);

    // ✅ Проверка: нужна ли пагинация
    if (totalPages <= 1) {
        document.getElementById('programs-pagination').style.display = 'none';
    } else {
        document.getElementById('programs-pagination').style.display = 'flex';
    }

    // 🔄 Анимация исчезновения
    programsList.classList.add('fade-out');

    // 🕒 Таймер для плавного исчезновения
    setTimeout(() => {
        programs.forEach((card, index) => {
            if (index >= (page - 1) * programsPerPage && index < page * programsPerPage) {
                card.style.display = 'block';
                card.classList.add('fade-in');  // ✨ Анимация появления
            } else {
                card.style.display = 'none';
                card.classList.remove('fade-in');
            }
        });

        programsList.classList.remove('fade-out');
    }, 300);

    renderPagination('programs-pagination', totalPages, paginatePrograms);
}

// 📑 Пагинация договоров
function paginateContracts(page) {
    const contracts = document.querySelectorAll('.contract-card');
    const totalPages = Math.ceil(contracts.length / contractsPerPage);

    // ✅ Проверка: нужна ли пагинация
    if (totalPages <= 1) {
        document.getElementById('contracts-pagination').style.display = 'none';
    } else {
        document.getElementById('contracts-pagination').style.display = 'flex';
    }

    contracts.forEach((card, index) => {
        card.style.display = (index >= (page - 1) * contractsPerPage && index < page * contractsPerPage) ? 'block' : 'none';
    });

    renderPagination('contracts-pagination', totalPages, paginateContracts);
}

// 📄 Генерация кнопок пагинации
function renderPagination(containerId, totalPages, paginateFunc) {
    const container = document.getElementById(containerId);
    container.innerHTML = '';

    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.onclick = () => paginateFunc(i);
        button.classList.add('pagination-btn');

        container.appendChild(button);
    }
}

// 🚀 Запуск при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    paginatePrograms(1);
    paginateContracts(1);
});

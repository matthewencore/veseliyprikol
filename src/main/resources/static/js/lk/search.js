// 📄 Данные для подсказок
const suggestions = [
    { name: 'Иван Иванов', inn: '1234567890', director: 'Иванов И.И.' },
    { name: 'Компания "Бизнес Плюс"', inn: '9876543210', director: 'Петров П.П.' },
    { name: 'ООО "ТехноПром"', inn: '4567891230', director: 'Сидоров С.С.' }
];

// 🔎 Показать подсказки
function showSuggestions(value) {
    const suggestionsBox = document.getElementById('suggestions-box');
    suggestionsBox.innerHTML = '';

    if (value.trim() === '') {
        suggestionsBox.style.display = 'none';
        return;
    }

    const filtered = suggestions.filter(item =>
        item.name.toLowerCase().includes(value.toLowerCase())
    );

    if (filtered.length === 0) {
        suggestionsBox.innerHTML = '<div class="suggestion-item">❌ Ничего не найдено</div>';
    } else {
        filtered.forEach(item => {
            const div = document.createElement('div');
            div.className = 'suggestion-item';
            div.innerHTML = `
                ${item.name}
                <div class="suggestion-details">ИНН: ${item.inn} | Директор: ${item.director}</div>
            `;
            div.onclick = () => {
                document.getElementById('search-input').value = item.name;
                suggestionsBox.style.display = 'none';
            };
            suggestionsBox.appendChild(div);
        });
    }

    suggestionsBox.style.display = 'block';
}

// ❌ Скрыть подсказки при клике вне
document.addEventListener('click', function (e) {
    const suggestionsBox = document.getElementById('suggestions-box');
    const searchInput = document.getElementById('search-input');

    if (!suggestionsBox.contains(e.target) && e.target !== searchInput) {
        suggestionsBox.style.display = 'none';
    }
});

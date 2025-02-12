// 🚀 Добавляем обработчик на все элементы с классом "info-field"
document.addEventListener('DOMContentLoaded', () => {
    const fields = document.querySelectorAll('.info-field');

    fields.forEach((field) => {
        field.style.cursor = 'pointer'; // 🔍 Устанавливаем курсор "рука"

        field.addEventListener('click', () => {
            const textToCopy = field.textContent.trim(); // Берём текст элемента
            navigator.clipboard.writeText(textToCopy) // Копируем в буфер
                .then(() => {
                    // ✨ Показываем временное уведомление об успехе
                    field.classList.add('copied');
                    setTimeout(() => field.classList.remove('copied'), 1000);
                })
                .catch((err) => {
                    console.error('Ошибка копирования:', err);
                });
        });
    });
});

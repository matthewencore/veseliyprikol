function openTab(evt, tabName) {
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.style.display = 'none');

    const buttons = document.querySelectorAll('.tabs button');
    buttons.forEach(button => button.classList.remove('active'));

    document.getElementById(tabName).style.display = 'block';
    evt.currentTarget.classList.add('active');
}
function showLeaderInfo() {
    const infoBlock = document.getElementById('leader-info');
    infoBlock.scrollIntoView({ behavior: 'smooth' });
}

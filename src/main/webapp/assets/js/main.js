document.addEventListener('DOMContentLoaded', () => {
    // Auto-dismiss alerts
    const alerts = document.querySelectorAll('.alert-success');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Smooth scroll
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });

    // Local filtering for topic list page
    const searchInput = document.querySelector('#searchInput');
    if (searchInput) {
        searchInput.addEventListener('input', (e) => {
            const term = e.target.value.toLowerCase();
            const topics = document.querySelectorAll('.topic-row');
            topics.forEach(topic => {
                const titleElement = topic.querySelector('.topic-item-title');
                const title = titleElement ? titleElement.textContent.toLowerCase() : '';
                topic.style.display = title.includes(term) ? '' : 'none';
            });
        });
    }
});

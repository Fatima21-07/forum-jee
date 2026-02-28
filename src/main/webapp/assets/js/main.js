document.addEventListener('DOMContentLoaded', () => {
    // Add dynamic behavior to cards
    const cards = document.querySelectorAll('.card');
    cards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            // card.style.transition = 'all 0.3s ease';
        });
    });

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

    // Simple search functionality (optional but nice)
    const searchInput = document.querySelector('#searchInput');
    if (searchInput) {
        searchInput.addEventListener('keyup', (e) => {
            const term = e.target.value.toLowerCase();
            const topics = document.querySelectorAll('.list-group-item');
            topics.forEach(topic => {
                const title = topic.querySelector('h5').textContent.toLowerCase();
                if (title.includes(term)) {
                    topic.style.display = 'block';
                } else {
                    topic.style.display = 'none';
                }
            });
        });
    }
});

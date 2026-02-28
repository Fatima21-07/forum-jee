<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<div class="text-center py-5">
    <h1 class="display-1 fw-bold text-primary">404</h1>
    <h2 class="mb-4">Page non trouvablee</h2>
    <p class="text-muted mb-5">Désoler, la page que vous recherchez n'existe pas ou a autre déplacement.</p>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg px-5 fw-bold shadow-sm">
        Retour Ã  l'accueil
    </a>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

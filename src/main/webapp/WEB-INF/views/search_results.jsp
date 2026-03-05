<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="mb-4">
    <h2 class="fw-bold text-primary">Résultats de recherche pour : <span class="text-dark">"${query}"</span></h2>
    <p class="text-muted">${results.size()} sujet(s) trouvé(s)</p>
</div>

<div class="card shadow-sm border-0">
    <div class="list-group list-group-flush">
        <c:choose>
            <c:when test="${empty results}">
                <div class="p-5 text-center">
                    <i class="fas fa-search fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">Aucun résultat trouvé pour votre recherche.</h5>
                    <p class="text-muted small">Essayez avec d'autres mots-clés.</p>
                    <a href="home" class="btn btn-outline-primary mt-3">Retour à l'accueil</a>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="topic" items="${results}">
                    <a href="topic?id=${topic.id}" class="list-group-item list-group-item-action p-4 border-bottom">
                        <div class="d-flex w-100 justify-content-between align-items-center">
                            <div>
                                <span class="badge bg-secondary mb-2">${topic.categoryName}</span>
                                <h5 class="mb-2 fw-bold topic-item-title">${topic.title}</h5>
                                <div class="text-muted small">
                                    <i class="fas fa-user me-1"></i> ${topic.authorName} 
                                    <span class="mx-2">|</span>
                                    <i class="fas fa-clock me-1"></i> ${topic.created_at}
                                </div>
                            </div>
                            <div class="text-end">
                                <span class="stats-pill">
                                    <i class="fas fa-comment me-1"></i> ${topic.postCount}
                                </span>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

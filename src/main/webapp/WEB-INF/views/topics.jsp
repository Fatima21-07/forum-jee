<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="home">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">${category.name}</li>
  </ol>
</nav>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold text-primary mb-1">${category.name}</h2>
        <p class="text-muted mb-0">${category.description}</p>
    </div>
    <c:if test="${not empty sessionScope.user}">
        <a href="topic/new?categoryId=${category.id}" class="btn btn-primary fw-bold shadow-sm">
            <i class="fas fa-plus me-2"></i>Nouveau Sujet
        </a>
    </c:if>
</div>

<div class="card shadow-sm border-0">
    <div class="list-group list-group-flush">
        <c:choose>
            <c:when test="${empty topics}">
                <div class="p-5 text-center">
                    <i class="fas fa-folder-open fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">Aucun sujet dans cette catégorie pour le moment.</h5>
                    <p class="text-muted small">Soyez le premier Ã  poser une question !</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="topic" items="${topics}">
                    <a href="topic?id=${topic.id}" class="list-group-item list-group-item-action p-4 border-bottom">
                        <div class="d-flex w-100 justify-content-between align-items-center">
                            <div>
                                <h5 class="mb-1 fw-bold text-dark">${topic.title}</h5>
                                <div class="text-muted small">
                                    <i class="fas fa-user me-1"></i> ${topic.authorName} 
                                    <span class="mx-2">|</span>
                                    <i class="fas fa-clock me-1"></i> ${topic.created_at}
                                </div>
                            </div>
                            <div class="text-end">
                                <span class="badge bg-primary rounded-pill px-3 py-2">
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

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="home">Accueil</a></li>
    <li class="breadcrumb-item"><a href="topics?categoryId=${topic.category_id}">${topic.categoryName}</a></li>
    <li class="breadcrumb-item active" aria-current="page">${topic.title}</li>
  </ol>
</nav>

<!-- Main Topic -->
<div class="card shadow-sm border-0 mb-4">
    <div class="card-header bg-primary text-white p-4">
        <h2 class="fw-bold mb-0">${topic.title}</h2>
    </div>
    <div class="card-body p-4">
        <div class="d-flex mb-4">
            <div class="me-3">
                <img src="https://ui-avatars.com/api/?name=${topic.authorName}&background=0D6EFD&color=fff&size=50" class="rounded-circle" style="width: 50px; height: 50px;" alt="Avatar">
            </div>
            <div>
                <h5 class="mb-0 fw-bold">${topic.authorName}</h5>
                <small class="text-muted"><i class="fas fa-clock me-1"></i> Public le ${topic.created_at}</small>
            </div>
        </div>
        <div class="topic-content lead">
            ${topic.content}
        </div>
        <c:if test="${not empty sessionScope.user and sessionScope.user.id == topic.user_id}">
            <div class="d-flex justify-content-end gap-2 mt-4">
                <button class="btn btn-outline-primary btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#editTopicForm">
                    Modifier
                </button>
                <form action="${pageContext.request.contextPath}/topic/delete" method="post" onsubmit="return confirm('Supprimer ce sujet ?');">
                    <input type="hidden" name="topicId" value="${topic.id}">
                    <button type="submit" class="btn btn-outline-danger btn-sm">Supprimer</button>
                </form>
            </div>

            <div class="collapse mt-3" id="editTopicForm">
                <form action="${pageContext.request.contextPath}/topic/edit" method="post">
                    <input type="hidden" name="topicId" value="${topic.id}">
                    <div class="mb-2">
                        <input type="text" class="form-control" name="title" value="${topic.title}" required>
                    </div>
                    <div class="mb-2">
                        <textarea class="form-control" name="content" rows="4" required>${topic.content}</textarea>
                    </div>
                    <button type="submit" class="btn btn-primary btn-sm">Enregistrer</button>
                </form>
            </div>
        </c:if>
    </div>
</div>

<h4 class="fw-bold text-primary mb-4"><i class="fas fa-comments me-2"></i>Réponses (${posts.size()})</h4>

<!-- Posts -->
<c:forEach var="p" items="${posts}">
    <div class="card shadow-sm border-0 mb-3">
        <div class="card-body p-4">
            <div class="d-flex mb-3">
                <div class="me-3">
                    <img src="https://ui-avatars.com/api/?name=${p.authorName}&background=6C757D&color=fff&size=40" 
                         class="rounded-circle" style="width: 40px; height: 40px;" alt="Avatar">
                </div>
                <div>
                    <h6 class="mb-0 fw-bold">${p.authorName}</h6>
                    <small class="text-muted">${p.created_at}</small>
                </div>
            </div>
            <div class="message-content">
                ${p.content}
            </div>
            <c:if test="${not empty sessionScope.user and sessionScope.user.id == p.user_id}">
                <div class="d-flex justify-content-end gap-2 mt-3">
                    <button class="btn btn-outline-primary btn-sm" type="button" data-bs-toggle="collapse" data-bs-target="#editPost${p.id}">
                        Modifier
                    </button>
                    <form action="${pageContext.request.contextPath}/post/delete" method="post" onsubmit="return confirm('Supprimer cette réponse ?');">
                        <input type="hidden" name="postId" value="${p.id}">
                        <input type="hidden" name="topicId" value="${topic.id}">
                        <button type="submit" class="btn btn-outline-danger btn-sm">Supprimer</button>
                    </form>
                </div>

                <div class="collapse mt-3" id="editPost${p.id}">
                    <form action="${pageContext.request.contextPath}/post/edit" method="post">
                        <input type="hidden" name="postId" value="${p.id}">
                        <input type="hidden" name="topicId" value="${topic.id}">
                        <textarea class="form-control mb-2" name="content" rows="3" required>${p.content}</textarea>
                        <button type="submit" class="btn btn-primary btn-sm">Enregistrer</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</c:forEach>

<!-- Reply Form -->
<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <div class="card shadow-sm border-0 mt-5 mb-5">
            <div class="card-header bg-white border-bottom p-3">
                <h5 class="mb-0 fw-bold text-primary"><i class="fas fa-reply me-2"></i>Poster une réponse</h5>
            </div>
            <div class="card-body p-4">
                <form action="${pageContext.request.contextPath}/post/new" method="post">
                    <input type="hidden" name="topicId" value="${topic.id}">
                    <div class="mb-3">
                        <textarea class="form-control" name="content" rows="4" placeholder="Votre réponse..." required></textarea>
                    </div>
                    <div class="text-end">
                        <button type="submit" class="btn btn-primary fw-bold px-4 shadow-sm">Répondre</button>
                    </div>
                </form>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="alert alert-info shadow-sm p-4 mt-5 text-center" role="alert">
            <i class="fas fa-info-circle fa-2x mb-3 text-info"></i>
            <h5>Vous devez être connecté pour répondre à ce sujet.</h5>
            <div class="mt-3">
                <a href="login" class="btn btn-primary fw-bold me-2">Se connecter</a>
                <a href="register" class="btn btn-outline-primary fw-bold">S'inscrire</a>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

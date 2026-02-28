<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card shadow-sm border-0">
            <div class="card-header bg-white border-bottom p-3">
                <h4 class="mb-0 fw-bold text-primary"><i class="fas fa-plus me-2"></i>Nouveau Sujet</h4>
            </div>
            <div class="card-body p-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle me-2"></i>${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <form action="${pageContext.request.contextPath}/topic/new" method="post">
                    <input type="hidden" name="categoryId" value="${categoryId}">
                    <div class="mb-3">
                        <label for="title" class="form-label fw-bold">Titre du sujet</label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="De quoi souhaitez-vous discuter ?" required minlength="5">
                    </div>
                    <div class="mb-4">
                        <label for="content" class="form-label fw-bold">Votre question ou contenu</label>
                        <textarea class="form-control" id="content" name="content" rows="6" placeholder="Soyez aussi prÃ©cis que possible..." required minlength="10"></textarea>
                    </div>
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="${pageContext.request.contextPath}/topics?categoryId=${categoryId}" class="btn btn-outline-secondary fw-bold px-4">Annuler</a>
                        <button type="submit" class="btn btn-primary fw-bold px-4 shadow-sm">Publier le sujet</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

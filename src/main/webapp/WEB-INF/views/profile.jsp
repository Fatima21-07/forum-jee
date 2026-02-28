<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<div class="row">
    <div class="col-md-4">
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-body text-center p-4">
                <div class="mb-3">
                    <img src="https://ui-avatars.com/api/?name=${sessionScope.user.username}&background=0D6EFD&color=fff&size=150" 
                         class="rounded-circle img-thumbnail shadow-sm" style="width: 150px; height: 150px; object-fit: cover;" alt="Avatar">
                </div>
                <h4 class="fw-bold text-primary">${sessionScope.user.username}</h4>
                <p class="text-muted mb-3"><i class="fas fa-envelope me-1"></i>${sessionScope.user.email}</p>
                <div class="mb-3">
                    <p class="text-muted small">${sessionScope.user.bio}</p>
                </div>
                <p class="text-muted small">Membre depuis le : <br><strong>${sessionScope.user.created_at}</strong></p>
            </div>
        </div>
    </div>
    <div class="col-md-8">
        <div class="card shadow-sm border-0">
            <div class="card-header bg-white border-bottom p-3">
                <h4 class="mb-0 fw-bold text-primary"><i class="fas fa-user-edit me-2"></i>Modifier mes informations</h4>
            </div>
            <div class="card-body p-4">
                <c:if test="${not empty success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle me-2"></i>${success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-triangle me-2"></i>${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <form action="${pageContext.request.contextPath}/profile" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Nom d'utilisateur</label>
                        <input type="text" class="form-control" id="username" name="username" value="${sessionScope.user.username}" required>
                    </div>
                    <div class="mb-3">
                        <label for="full_name" class="form-label">Nom complet</label>
                        <input type="text" class="form-control" id="full_name" name="full_name" value="${sessionScope.user.full_name}">
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${sessionScope.user.email}" required>
                    </div>
                    <div class="mb-3">
                        <label for="bio" class="form-label">Bio</label>
                        <textarea class="form-control" id="bio" name="bio" rows="3">${sessionScope.user.bio}</textarea>
                    </div>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <button type="submit" class="btn btn-primary fw-bold px-4 shadow-sm">Enregistrer les modifications</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

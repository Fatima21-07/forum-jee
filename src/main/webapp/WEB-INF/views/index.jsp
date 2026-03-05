<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/header.jsp" %>
<div class="hero-panel p-5 mb-4 text-white">
    <div class="container-fluid py-5">
        <div class="d-flex flex-wrap gap-2 mb-3">
            <span class="badge text-bg-light text-primary fw-semibold px-3 py-2">CDL Community</span>
            <span class="badge text-bg-light text-primary fw-semibold px-3 py-2">Q&A</span>
        </div>
        <h1 class="display-5 fw-bold mb-3"><fmt:message key="welcome.title" /></h1>
        <p class="col-md-8 fs-5 hero-subtitle mb-4"><fmt:message key="welcome.subtitle" /></p>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="register" class="btn btn-light btn-lg fw-bold px-4"><fmt:message key="hero.button" /></a>
            </c:when>
            <c:otherwise>
                <a href="#categories" class="btn btn-light btn-lg fw-bold px-4"><fmt:message key="hero.explorer" /></a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div id="categories" class="my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold text-primary section-title"><i class="fas fa-th-large me-2"></i><fmt:message key="home.modules" /></h2>
    </div>
    <div class="row g-4">
        <c:forEach var="category" items="${categories}">
            <div class="col-md-6 col-lg-4">
                <div class="card h-100 shadow-sm border-0 category-card">
                    <div class="card-body p-4">
                        <div class="d-flex align-items-center mb-3">
                            <div class="category-icon bg-primary bg-opacity-10 text-primary rounded-3 p-3 me-3">
                                <i class="fas fa-code fa-lg"></i>
                            </div>
                            <h4 class="card-title mb-0 fw-bold">${category.name}</h4>
                        </div>
                        <p class="card-text text-muted">${category.description}</p>
                    </div>
                    <div class="card-footer bg-transparent border-0 p-4 pt-0">
                        <a href="topics?categoryId=${category.id}" class="btn btn-outline-primary w-100 fw-bold">
                            <fmt:message key="category.view" /> <i class="fas fa-arrow-right ms-2"></i>
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jsp" %>

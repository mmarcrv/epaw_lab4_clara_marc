<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="pc-navbar">
  <a class="menu pc-navbar-brand" href="Timeline" style="text-decoration:none">
    <div class="pc-navbar-logo">
      <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
    </div>
    PisCompany
  </a>

  <div class="pc-navbar-links" id="pc-nav-desktop">
    <a class="menu pc-nav-link" href="Timeline">
      <i data-lucide="home"></i> Inici
    </a>
    <a class="menu pc-btn-publish" href="Publish">
      <i data-lucide="plus-circle"></i> Publicar
    </a>
    <a class="menu pc-nav-link" href="FollowingTimeline">
      <i data-lucide="users"></i> Seguint
    </a>
    <a class="menu pc-nav-link" href="UserProfile">
      <span class="pc-nav-avatar">
        <c:choose>
          <c:when test="${not empty user.name && user.name.length() >= 2}">
            ${user.name.substring(0, 2).toUpperCase()}
          </c:when>
          <c:when test="${not empty user.name}">
            ${user.name.toUpperCase()}
          </c:when>
          <c:otherwise>TU</c:otherwise>
        </c:choose>
      </span> Perfil
    </a>
    <c:if test="${user.role == 'admin'}">
      <a class="menu pc-btn-admin" href="AdminPanel" style="margin-left:8px; text-decoration:none; display:inline-flex; align-items:center; gap:6px">
        <i data-lucide="shield"></i> Admin
      </a>
    </c:if>
    <a class="menu pc-nav-link" href="Logout" style="margin-left:4px">
      <i data-lucide="log-out"></i>
    </a>
  </div>
</nav>

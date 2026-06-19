<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Sol·licituds de seguiment --%>
<c:if test="${not empty requests}">
<div class="pc-card pc-right-section">
  <div class="pc-right-title">Sol·licituds de seguiment</div>
  <c:forEach var="u" items="${requests}">
    <div id="${u.id}" class="pc-request-card">
      <div class="pc-avatar" style="width:36px;height:36px;font-size:0.75rem">
        <c:choose>
          <c:when test="${not empty u.picture}"><img src="${u.picture}" alt="av"></c:when>
          <c:otherwise>${u.name.substring(0,1).toUpperCase()}</c:otherwise>
        </c:choose>
      </div>
      <div class="pc-request-info">
        <div class="pc-request-name">${u.name}</div>
        <div class="pc-request-sub">vol seguir-te</div>
      </div>
      <div class="pc-request-actions">
        <button class="acceptRequest pc-accept-btn" title="Acceptar">
          <i data-lucide="check"></i>
        </button>
        <button class="rejectRequest pc-reject-btn" title="Rebutjar">
          <i data-lucide="x"></i>
        </button>
      </div>
    </div>
  </c:forEach>
</div>
</c:if>

<%-- Perfils recomanats --%>
<c:if test="${not empty suggestions}">
<div class="pc-card pc-right-section">
  <div class="pc-right-title">Perfils recomanats</div>
  <c:forEach var="u" items="${suggestions}">
    <div id="${u.id}" class="pc-suggest-card">
      <div class="pc-avatar" style="width:40px;height:40px;font-size:0.8rem">
        <c:choose>
          <c:when test="${not empty u.picture}"><img src="${u.picture}" alt="av"></c:when>
          <c:otherwise>${u.name.substring(0,1).toUpperCase()}</c:otherwise>
        </c:choose>
      </div>
      <div class="pc-suggest-info">
        <div class="pc-suggest-name">${u.name}</div>
        <c:if test="${not empty u.comarca}">
          <div class="pc-suggest-desc">${u.comarca}</div>
        </c:if>
      </div>
      <button class="followUser pc-suggest-follow-btn">
        <i data-lucide="user-plus"></i> Seguir
      </button>
    </div>
  </c:forEach>
</div>
</c:if>

<script>lucide.createIcons();</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
.admin-stat-card {
  background: var(--white);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  padding: 14px 16px;
  margin-bottom: 10px;
  box-shadow: var(--shadow);
  display: flex; align-items: center; gap: 14px;
}
.admin-stat-icon {
  width: 40px; height: 40px; border-radius: var(--radius-sm);
  display: flex; align-items: center; justify-content: center;
  flex-shrink: 0;
}
.admin-stat-icon svg { width: 20px; height: 20px; }
.admin-stat-icon.red  { background: var(--red-bg); color: var(--red-dark); }
.admin-stat-icon.green { background: var(--green-bg2); color: #3a7d60; }
.admin-stat-label { font-size: 0.78rem; color: var(--text-muted); margin-bottom: 1px; }
.admin-stat-value { font-size: 1.3rem; font-weight: 700; color: var(--text); }

.admin-quick-btn {
  display: block; width: 100%; padding: 10px 16px;
  border-radius: var(--radius-sm); border: none;
  font-size: 0.88rem; font-weight: 600; cursor: pointer;
  margin-bottom: 8px; text-align: left;
  transition: opacity .15s;
}
.admin-quick-btn:hover { opacity: .85; }
.admin-quick-btn.red   { background: var(--red-bg); color: var(--red-dark); }
.admin-quick-btn.green { background: var(--green-bg2); color: #3a7d60; }
.admin-quick-btn.dark  { background: var(--green-1); color: white; }

.admin-activity-item {
  display: flex; align-items: flex-start; gap: 8px;
  padding: 7px 0; border-bottom: 1px solid var(--green-bg2);
  font-size: 0.82rem; color: var(--text-muted);
}
.admin-activity-item:last-child { border-bottom: none; }
.admin-activity-dot {
  width: 7px; height: 7px; border-radius: 50%;
  background: var(--green-1); flex-shrink: 0; margin-top: 5px;
}
.pc-right-section { margin-bottom: 16px; }
.pc-right-title  { font-weight: 700; font-size: 0.95rem; margin-bottom: 10px; color: var(--text); }
</style>

<!-- Estadístiques -->
<div class="pc-right-section">
  <div class="pc-right-title">
    <i data-lucide="bar-chart-2" style="width:16px;height:16px;vertical-align:-3px;margin-right:4px"></i>
    Estadístiques
  </div>

  <div class="admin-stat-card">
    <div class="admin-stat-icon red">
      <i data-lucide="alert-triangle"></i>
    </div>
    <div>
      <div class="admin-stat-label">Reports pendents</div>
      <div class="admin-stat-value">${stats.pendingReports}</div>
    </div>
  </div>

  <div class="admin-stat-card">
    <div class="admin-stat-icon green">
      <i data-lucide="users"></i>
    </div>
    <div>
      <div class="admin-stat-label">Usuaris actius</div>
      <div class="admin-stat-value">${stats.activeUsers}</div>
    </div>
  </div>

  <div class="admin-stat-card">
    <div class="admin-stat-icon green">
      <i data-lucide="file-text"></i>
    </div>
    <div>
      <div class="admin-stat-label">Posts avui</div>
      <div class="admin-stat-value">${stats.postsToday}</div>
    </div>
  </div>

  <div class="admin-stat-card">
    <div class="admin-stat-icon green">
      <i data-lucide="message-circle"></i>
    </div>
    <div>
      <div class="admin-stat-label">Comentaris avui</div>
      <div class="admin-stat-value">${stats.commentsToday}</div>
    </div>
  </div>
</div>

<!-- Accions ràpides -->
<div class="pc-right-section">
  <div class="pc-right-title">Accions ràpides</div>
  <button class="admin-quick-btn red admin-scroll-reports">
    <i data-lucide="flag" style="width:14px;height:14px;vertical-align:-2px;margin-right:6px"></i>
    Veure tots els reports
  </button>
  <button class="admin-quick-btn green">
    <i data-lucide="users" style="width:14px;height:14px;vertical-align:-2px;margin-right:6px"></i>
    Gestionar usuaris
  </button>
  <button class="admin-quick-btn dark">
    <i data-lucide="download" style="width:14px;height:14px;vertical-align:-2px;margin-right:6px"></i>
    Exportar dades
  </button>
</div>

<!-- Activitat recent -->
<div class="pc-right-section">
  <div class="pc-right-title">Activitat recent</div>
  <c:choose>
    <c:when test="${empty recentActivity}">
      <div style="color:var(--text-muted);font-size:0.85rem">Cap activitat recent.</div>
    </c:when>
    <c:otherwise>
      <c:forEach var="item" items="${recentActivity}">
        <div class="admin-activity-item">
          <span class="admin-activity-dot"></span>
          <span>${item}</span>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>
</div>

<script>
$(document).ready(function(){
  lucide.createIcons();
  $(document).on('click', '.admin-scroll-reports', function(){
    $('html, body').animate({ scrollTop: 0 }, 300);
  });
});
</script>

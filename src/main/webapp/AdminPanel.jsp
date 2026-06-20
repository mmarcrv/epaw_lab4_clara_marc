<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<script>
$(document).ready(function(){
  App.setLayout('full');
  $('#rcolumn').load('AdminRight', function(){ lucide.createIcons(); });
  lucide.createIcons();
});
</script>

<style>
/* ── Admin panel specific styles ─────────────────── */
.admin-hero {
  background: var(--white);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  padding: 20px 24px;
  margin-bottom: 20px;
  box-shadow: var(--shadow);
  display: flex; align-items: center; gap: 16px;
}
.admin-hero-icon {
  width: 48px; height: 48px; border-radius: var(--radius-sm);
  background: var(--red-bg); color: var(--red-dark);
  display: flex; align-items: center; justify-content: center;
  flex-shrink: 0;
}
.admin-hero-icon svg { width: 24px; height: 24px; }
.admin-hero h2 { font-size: 1.2rem; font-weight: 700; margin-bottom: 2px; }
.admin-hero p { color: var(--text-muted); font-size: 0.88rem; margin: 0; }

.admin-section-title {
  font-size: 1rem; font-weight: 700;
  margin-bottom: 12px; color: var(--text);
  display: flex; align-items: center; gap: 8px;
}
.admin-section-title svg { width: 18px; height: 18px; color: var(--text-muted); }

.admin-report-card {
  background: var(--white);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  padding: 14px 16px;
  margin-bottom: 10px;
  box-shadow: var(--shadow);
  animation: fadeIn .2s ease;
}
.admin-report-header { display: flex; align-items: center; gap: 10px; margin-bottom: 8px; }
.admin-report-name { font-weight: 700; font-size: 0.92rem; }
.admin-report-handle { color: var(--text-muted); font-size: 0.8rem; }
.admin-report-time { color: var(--text-muted); font-size: 0.78rem; margin-left: auto; }
.admin-report-body {
  font-size: 0.88rem; color: var(--text);
  margin-bottom: 8px; line-height: 1.5;
}
.admin-report-parent {
  font-size: 0.8rem; color: var(--text-muted);
  margin-bottom: 8px; font-style: italic;
}
.admin-report-badge {
  display: inline-flex; align-items: center; gap: 4px;
  padding: 2px 10px; border-radius: var(--radius-pill);
  background: var(--red-bg); color: var(--red-dark);
  font-size: 0.75rem; font-weight: 600;
  margin-bottom: 10px;
}
.admin-report-badge svg { width: 11px; height: 11px; }
.admin-report-actions { display: flex; gap: 8px; flex-wrap: wrap; }

.admin-btn-delete {
  display: inline-flex; align-items: center; gap: 5px;
  padding: 6px 14px; border-radius: var(--radius-pill);
  background: var(--red-bg); color: var(--red-dark);
  border: none; font-size: 0.82rem; font-weight: 600;
  cursor: pointer; transition: background .15s;
}
.admin-btn-delete:hover { background: var(--red-2); }
.admin-btn-delete svg { width: 13px; height: 13px; }

.admin-btn-ban {
  display: inline-flex; align-items: center; gap: 5px;
  padding: 6px 14px; border-radius: var(--radius-pill);
  background: #fff3e0; color: #e65100;
  border: none; font-size: 0.82rem; font-weight: 600;
  cursor: pointer; transition: background .15s;
}
.admin-btn-ban:hover { background: #ffe0b2; }
.admin-btn-ban svg { width: 13px; height: 13px; }

.admin-btn-ignore {
  display: inline-flex; align-items: center; gap: 5px;
  padding: 6px 14px; border-radius: var(--radius-pill);
  background: none; color: var(--text-muted);
  border: 1px solid var(--border); font-size: 0.82rem; font-weight: 600;
  cursor: pointer; transition: background .15s, color .15s;
}
.admin-btn-ignore:hover { background: var(--green-bg2); color: var(--green-1); }

/* Stats cards */
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
.admin-stat-icon.red { background: var(--red-bg); color: var(--red-dark); }
.admin-stat-icon.green { background: var(--green-bg2); color: #3a7d60; }
.admin-stat-label { font-size: 0.78rem; color: var(--text-muted); margin-bottom: 1px; }
.admin-stat-value { font-size: 1.3rem; font-weight: 700; color: var(--text); }

/* Quick actions */
.admin-quick-btn {
  display: block; width: 100%; padding: 10px 16px;
  border-radius: var(--radius-sm); border: none;
  font-size: 0.88rem; font-weight: 600; cursor: pointer;
  margin-bottom: 8px; text-align: left;
  transition: opacity .15s;
}
.admin-quick-btn:hover { opacity: .85; }
.admin-quick-btn.red { background: var(--red-bg); color: var(--red-dark); }
.admin-quick-btn.green { background: var(--green-bg2); color: #3a7d60; }
.admin-quick-btn.dark { background: var(--green-1); color: white; }

/* Recent activity */
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
.admin-empty {
  text-align: center; padding: 24px;
  color: var(--text-muted); font-size: 0.9rem;
}
</style>

<div id="admin-main-col">

  <!-- Header -->
  <div class="admin-hero">
    <div class="admin-hero-icon">
      <i data-lucide="shield-alert"></i>
    </div>
    <div>
      <h2>Panell d'Administració</h2>
      <p>Gestiona publicacions, comentaris i usuaris</p>
    </div>
  </div>

  <!-- Reported Posts -->
  <div class="admin-section-title">
    <i data-lucide="flag"></i> Posts Reportats
  </div>

  <c:choose>
    <c:when test="${empty reportedPosts}">
      <div class="admin-report-card admin-empty">
        <i data-lucide="check-circle" style="width:32px;height:32px;margin:0 auto 8px;display:block;color:var(--green-1)"></i>
        No hi ha posts reportats pendents.
      </div>
    </c:when>
    <c:otherwise>
      <c:forEach var="post" items="${reportedPosts}">
        <div class="admin-report-card" id="post-${post.tweetId}">
          <div class="admin-report-header">
            <div class="pc-avatar" style="width:36px;height:36px;font-size:0.78rem">
              ${fn:toUpperCase(fn:substring(post.authorName, 0, 1))}
            </div>
            <div>
              <div class="admin-report-name">${post.authorName}
                <span class="admin-report-handle">@${post.authorHandle}</span>
              </div>
            </div>
            <span class="admin-report-time">${post.time}</span>
          </div>
          <div class="admin-report-body">${post.textBody}</div>
          <div class="admin-report-badge">
            <i data-lucide="alert-triangle"></i>
            ${post.reportCount} reports: ${post.reason}
          </div>
          <div class="admin-report-actions">
            <button class="admin-btn-delete admin-action"
              data-action="deletePost"
              data-id="${post.tweetId}"
              data-author="${post.authorName}"
              data-target="post-${post.tweetId}">
              <i data-lucide="trash-2"></i> Eliminar
            </button>
            <button class="admin-btn-ban admin-action"
              data-action="banUser"
              data-id="${post.authorId}"
              data-author="${post.authorName}"
              data-target="post-${post.tweetId}">
              <i data-lucide="ban"></i> Bannejar usuari
            </button>
            <button class="admin-btn-ignore admin-action"
              data-action="ignoreReport"
              data-id="${post.tweetId}"
              data-author="${post.authorName}"
              data-target="post-${post.tweetId}">
              Ignorar
            </button>
          </div>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>

  <!-- Reported Comments -->
  <div class="admin-section-title" style="margin-top:8px">
    <i data-lucide="message-circle"></i> Comentaris Reportats
  </div>

  <c:choose>
    <c:when test="${empty reportedComments}">
      <div class="admin-report-card admin-empty">
        <i data-lucide="check-circle" style="width:32px;height:32px;margin:0 auto 8px;display:block;color:var(--green-1)"></i>
        No hi ha comentaris reportats pendents.
      </div>
    </c:when>
    <c:otherwise>
      <c:forEach var="comment" items="${reportedComments}">
        <div class="admin-report-card" id="post-${comment.tweetId}">
          <div class="admin-report-header">
            <div class="pc-avatar" style="width:36px;height:36px;font-size:0.78rem">
              ${fn:toUpperCase(fn:substring(comment.authorName, 0, 1))}
            </div>
            <div>
              <div class="admin-report-name">${comment.authorName}
                <span class="admin-report-handle">@${comment.authorHandle}</span>
              </div>
            </div>
            <span class="admin-report-time">${comment.time}</span>
          </div>
          <div class="admin-report-body">${comment.textBody}</div>
          <c:if test="${not empty comment.parentTextBodySnippet}">
            <div class="admin-report-parent">
              Al post: ${fn:substring(comment.parentTextBodySnippet, 0, 60)}...
            </div>
          </c:if>
          <div class="admin-report-badge">
            <i data-lucide="alert-triangle"></i>
            ${comment.reportCount} reports
          </div>
          <div class="admin-report-actions">
            <button class="admin-btn-delete admin-action"
              data-action="deleteComment"
              data-id="${comment.tweetId}"
              data-author="${comment.authorName}"
              data-target="post-${comment.tweetId}">
              <i data-lucide="trash-2"></i> Eliminar
            </button>
            <button class="admin-btn-ignore admin-action"
              data-action="ignoreReport"
              data-id="${comment.tweetId}"
              data-author="${comment.authorName}"
              data-target="post-${comment.tweetId}">
              Ignorar
            </button>
          </div>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>

</div>

<script>
/* Admin action handler — runs in the fragment context */
$(document).on('click', '.admin-action', function(e) {
  e.preventDefault();
  var $btn = $(this);
  var action   = $btn.data('action');
  var id       = $btn.data('id');
  var author   = $btn.data('author') || '';
  var targetId = $btn.data('target');

  $btn.prop('disabled', true).css('opacity', '.5');

  $.ajax({
    type: 'POST', url: 'AdminPanel',
    data: { action: action, id: id, authorName: author }
  }).done(function(html) {
    /* Remove the card immediately for snappy UX */
    $('#' + targetId).fadeOut(200, function() { $(this).remove(); });
    /* Refresh the right-column stats + activity */
    $('#rcolumn').load('AdminRight', function() { lucide.createIcons(); });
  }).fail(function() {
    $btn.prop('disabled', false).css('opacity', '1');
    alert('Error en executar l\'acció. Torna-ho a intentar.');
  });
});
lucide.createIcons();
</script>

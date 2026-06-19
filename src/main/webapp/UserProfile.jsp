<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<script>
$(document).ready(function(){
  App.setLayout('full');
  $('#rcolumn').load('Accessibility', function(){ lucide.createIcons(); });
  lucide.createIcons();
});
</script>

<c:if test="${profileUser != null}">

<div class="pc-profile-card">
  <div class="pc-profile-header">
    <div class="pc-avatar pc-avatar-lg">
      <c:choose>
        <c:when test="${not empty profileUser.picture}">
          <img src="${profileUser.picture}" alt="avatar">
        </c:when>
        <c:otherwise>${profileUser.name.substring(0,1).toUpperCase()}</c:otherwise>
      </c:choose>
    </div>
    <div class="pc-profile-info">
      <div class="pc-profile-name">${profileUser.firstName} ${profileUser.lastName}</div>
      <div class="pc-profile-handle">@${profileUser.name}</div>
      <div class="pc-profile-meta">
        <c:if test="${not empty profileUser.comarca}">
          <span><i data-lucide="map-pin"></i> ${profileUser.comarca}, Catalunya</span>
        </c:if>
        <c:if test="${not empty profileUser.email}">
          <span><i data-lucide="mail"></i> ${profileUser.email}</span>
        </c:if>
        <c:if test="${not empty profileUser.dateOfBirth}">
          <span><i data-lucide="calendar"></i> ${profileUser.dateOfBirth}</span>
        </c:if>
      </div>
    </div>
    <c:if test="${profileUser.id == sessionUser.id}">
      <button class="pc-btn pc-btn-ghost" style="font-size:0.85rem;padding:7px 14px">
        <i data-lucide="settings"></i> Editar perfil
      </button>
    </c:if>
  </div>

  <hr style="border:none;border-top:1px solid var(--border);margin:12px 0">

  <div class="pc-profile-stats">
    <div class="pc-stat">
      <b>${fn:length(tweets)}</b><br>
      <span>Publicacions</span>
    </div>
    <div class="pc-stat">
      <b>${followers}</b><br>
      <span>Seguidors</span>
    </div>
    <div class="pc-stat">
      <b>${following}</b><br>
      <span>Seguint</span>
    </div>
  </div>
</div>

<h3 style="font-size:1rem;font-weight:700;margin-bottom:12px">Les meves publicacions</h3>

<c:choose>
  <c:when test="${empty tweets}">
    <div class="pc-card" style="color:var(--text-muted);text-align:center;padding:32px">
      <i data-lucide="file-text" style="width:32px;height:32px;margin:0 auto 8px;display:block"></i>
      Encara no hi ha publicacions.
    </div>
  </c:when>
  <c:otherwise>
    <c:forEach var="t" items="${tweets}">
      <div id="${t.id}" class="tweet-card">
        <div class="tweet-header">
          <div class="pc-avatar">
            <c:choose>
              <c:when test="${not empty profileUser.picture}">
                <img src="${profileUser.picture}" alt="av">
              </c:when>
              <c:otherwise>${profileUser.name.substring(0,1).toUpperCase()}</c:otherwise>
            </c:choose>
          </div>
          <div class="tweet-meta">
            <div class="tweet-username">${profileUser.name}</div>
          </div>
          <span class="tweet-time">${t.time}</span>
        </div>
        <div class="tweet-badges">
          <c:if test="${not empty t.category}">
            <span class="pc-badge pc-badge-cat">${t.category}</span>
          </c:if>
          <c:if test="${not empty t.location}">
            <span class="pc-badge pc-badge-loc">
              <i data-lucide="map-pin"></i>${t.location}
            </span>
          </c:if>
        </div>
        <div class="tweet-body">${t.textBody}</div>
        <c:if test="${not empty t.picture}">
          <img src="${t.picture}" alt="imatge" class="tweet-image">
        </c:if>
        <div class="tweet-actions">
          <c:choose>
            <c:when test="${likedIds.contains(t.id)}">
              <button class="tweet-action-btn liked unlikeTweet"><i data-lucide="heart"></i> ${t.likes}</button>
            </c:when>
            <c:otherwise>
              <button class="tweet-action-btn likeTweet"><i data-lucide="heart"></i> ${t.likes}</button>
            </c:otherwise>
          </c:choose>
          <button class="tweet-action-btn toggleComments">
            <i data-lucide="message-circle"></i>
            <span class="commentCount">${t.comments}</span>
          </button>
          <c:if test="${t.userId == sessionUser.id}">
            <button class="tweet-del-btn delTweet"><i data-lucide="trash-2"></i></button>
          </c:if>
        </div>
        <div id="comments-${t.id}" style="display:none">
          <div class="comments-section">
            <div class="comment-form">
              <textarea class="pc-input commentBody" placeholder="Escriu un comentari..." rows="2" style="resize:none"></textarea>
              <button class="addComment pc-btn pc-btn-primary" style="align-self:flex-end;padding:7px 16px;font-size:0.85rem">
                <i data-lucide="send"></i> Comentar
              </button>
            </div>
            <div class="commentsList"></div>
          </div>
        </div>
      </div>
    </c:forEach>
  </c:otherwise>
</c:choose>

</c:if>

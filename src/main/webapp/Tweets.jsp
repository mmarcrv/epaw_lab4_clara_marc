<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:forEach var="t" items="${tweets}">
  <div id="${t.id}" class="tweet-card">
    <div class="tweet-header">
      <div class="pc-avatar">
        <c:choose>
          <c:when test="${not empty user.picture}">
            <img src="${user.picture}" alt="avatar">
          </c:when>
          <c:otherwise>${t.uname.substring(0,1).toUpperCase()}</c:otherwise>
        </c:choose>
      </div>
      <div class="tweet-meta">
        <div class="tweet-username">${t.uname}</div>
        <div class="tweet-handle">@${t.uname.toLowerCase().replace(' ','_')}</div>
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
          <button class="tweet-action-btn liked unlikeTweet">
            <i data-lucide="heart"></i> ${t.likes}
          </button>
        </c:when>
        <c:otherwise>
          <button class="tweet-action-btn likeTweet">
            <i data-lucide="heart"></i> ${t.likes}
          </button>
        </c:otherwise>
      </c:choose>

      <button class="tweet-action-btn toggleComments">
        <i data-lucide="message-circle"></i>
        <span class="commentCount">${t.comments}</span>
      </button>

      <c:if test="${t.userId == user.id}">
        <button class="tweet-del-btn delTweet">
          <i data-lucide="trash-2"></i>
        </button>
      </c:if>
    </div>

    <div id="comments-${t.id}" style="display:none">
      <div class="comments-section">
        <div class="comment-form">
          <textarea class="pc-input commentBody" placeholder="Escriu un comentari..." rows="2" style="resize:none"></textarea>
          <button class="addComment pc-btn pc-btn-primary" style="align-self:flex-end; padding:7px 16px; font-size:0.85rem">
            <i data-lucide="send"></i> Comentar
          </button>
        </div>
        <div class="commentsList"></div>
      </div>
    </div>
  </div>
</c:forEach>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
  <c:when test="${empty comments}">
    <p style="color:var(--text-muted);font-size:0.85rem;padding:8px 0">Encara no hi ha comentaris.</p>
  </c:when>
  <c:otherwise>
    <c:forEach var="c" items="${comments}">
      <div id="${c.id}" class="comment-card">
        <div class="pc-avatar" style="width:32px;height:32px;font-size:0.72rem">
          ${c.uname.substring(0,1).toUpperCase()}
        </div>
        <div class="comment-content">
          <div class="comment-meta">
            <span class="comment-author">${c.uname}</span>
            <span class="comment-time">${c.time}</span>
          </div>
          <div class="comment-body">${c.textBody}</div>
          <div style="display:flex;gap:8px;margin-top:6px">
            <button class="tweet-action-btn toggleReply" style="font-size:0.78rem;padding:3px 8px">
              <i data-lucide="corner-down-right"></i> Respondre
            </button>
            <c:if test="${c.userId == user.id}">
              <button class="tweet-action-btn delComment" style="font-size:0.78rem;padding:3px 8px;color:var(--text-muted)">
                <i data-lucide="trash-2"></i>
              </button>
            </c:if>
          </div>
          <div class="replyForm" data-parent="${c.id}" data-root="${parentTweetId}" style="display:none;margin-top:8px">
            <textarea class="pc-input replyBody" placeholder="Escriu una resposta..." rows="2" style="resize:none;font-size:0.85rem;margin-bottom:6px"></textarea>
            <button class="addReply pc-btn pc-btn-primary" style="font-size:0.8rem;padding:6px 14px">
              <i data-lucide="send"></i> Enviar
            </button>
          </div>
        </div>
      </div>
    </c:forEach>
  </c:otherwise>
</c:choose>

<script>lucide.createIcons();</script>

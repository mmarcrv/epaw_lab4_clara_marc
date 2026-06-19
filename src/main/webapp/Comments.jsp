<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
  <c:when test="${empty comments}">
    <p class="w3-small w3-opacity" style="margin-left:20px">No hi ha comentaris encara.</p>
  </c:when>
  <c:otherwise>
    <c:forEach var="c" items="${comments}">
      <div id="${c.id}" class="w3-container w3-pale-blue w3-round w3-margin-bottom w3-animate-opacity" style="margin-left:20px; padding:8px 12px">
        <span class="w3-small"><b>${c.uname}</b></span>
        <span class="w3-right w3-opacity w3-tiny">${c.time}</span>
        <br>
        <b class="w3-small">${c.title}</b>
        <p style="margin:4px 0; font-size:0.9em">${c.textBody}</p>

        <button type="button" class="toggleReply w3-button w3-margin-bottom" style="color:#657786; background:none; padding:2px 6px; font-size:0.8em;">
            <i class="fa fa-reply"></i> Respondre
        </button>
        <c:if test="${c.userId == user.id}">
          <button type="button" class="delComment w3-button w3-red w3-tiny w3-margin-bottom">
              <i class="fa fa-trash"></i>
          </button>
        </c:if>

        <div class="replyForm" data-parent="${c.id}" data-root="${parentTweetId}" style="display:none; margin-top:6px">
          <input class="replyTitle w3-input w3-border w3-white w3-small w3-margin-bottom" type="text" placeholder="Títol..." maxlength="255">
          <p class="replyBody w3-border w3-white w3-padding w3-small" contenteditable="true" style="min-height:30px"> </p>
          <button type="button" class="addReply w3-button w3-theme w3-tiny" style="margin-top:4px">Enviar</button>
        </div>
      </div>
    </c:forEach>
  </c:otherwise>
</c:choose>

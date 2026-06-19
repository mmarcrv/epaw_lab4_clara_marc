<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<script type="text/javascript">
$(document).ready(function(){
    $('#rcolumn').load('Accessibility');
    $('#lcolumn').load('Profile');
});
</script>

<c:if test="${profileUser != null}">

<%-- Profile header card --%>
<div class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity w3-center">
  <br>
  <img src="${profileUser.picture}" alt="Avatar" class="w3-circle" style="width:90px;height:90px;object-fit:cover">
  <h4 style="margin:8px 0">${profileUser.name}</h4>
  <c:if test="${not empty profileUser.firstName}">
    <p class="w3-opacity" style="margin:4px 0">${profileUser.firstName} ${profileUser.lastName}</p>
  </c:if>
  <c:if test="${not empty profileUser.comarca}">
    <p class="w3-small w3-opacity"><i class="fa fa-map-marker"></i> ${profileUser.comarca}</p>
  </c:if>
  <hr>
  <div class="w3-row" style="margin-bottom:12px">
    <div class="w3-half w3-padding-small">
      <b style="font-size:1.4em">${following}</b><br>
      <span class="w3-small w3-opacity" data-ca="Seguint" data-en="Following">Seguint</span>
    </div>
    <div class="w3-half w3-padding-small">
      <b style="font-size:1.4em">${followers}</b><br>
      <span class="w3-small w3-opacity" data-ca="Seguidors" data-en="Followers">Seguidors</span>
    </div>
  </div>
</div>

<%-- User's tweets --%>
<h5 class="w3-opacity" style="margin-left:4px" data-ca="Posts" data-en="Posts">Posts</h5>

<c:choose>
  <c:when test="${empty tweets}">
    <p class="w3-opacity" data-ca="Encara no hi ha posts." data-en="No posts yet.">Encara no hi ha posts.</p>
  </c:when>
  <c:otherwise>
    <c:forEach var="t" items="${tweets}">
      <div id="${t.id}" class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity">
        <br>
        <img src="${profileUser.picture}" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:50px;height:50px;object-fit:cover">
        <span class="w3-right w3-opacity w3-small">${t.time}</span>
        <h5 class="w3-margin-bottom-0">${t.uname}</h5>
        <hr class="w3-clear">
        <h6>${t.title}</h6>
        <p>${t.textBody}</p>
        <c:if test="${not empty t.picture}">
          <img src="${t.picture}" alt="Tweet image" style="max-width:100%;max-height:400px;border-radius:8px;margin-bottom:8px;display:block">
        </c:if>
        <c:choose>
          <c:when test="${likedIds.contains(t.id)}">
            <button type="button" class="unlikeTweet w3-button w3-margin-bottom" style="color:#e0245e;background:none;padding:4px 8px">
              <i class="fa fa-heart"></i> ${t.likes}
            </button>
          </c:when>
          <c:otherwise>
            <button type="button" class="likeTweet w3-button w3-margin-bottom" style="color:#657786;background:none;padding:4px 8px">
              <i class="fa fa-heart-o"></i> ${t.likes}
            </button>
          </c:otherwise>
        </c:choose>
        <button type="button" class="toggleComments w3-button w3-margin-bottom" style="color:#657786;background:none;padding:4px 8px">
          <i class="fa fa-comment-o"></i> <span class="commentCount">${t.comments}</span>
        </button>
        <c:if test="${t.userId == sessionUser.id}">
          <button type="button" class="delTweet w3-button w3-red w3-small w3-margin-bottom">
            <i class="fa fa-trash"></i>
          </button>
        </c:if>
        <div id="comments-${t.id}" style="display:none;margin-top:8px">
          <div class="w3-container w3-light-grey w3-round" style="padding:8px">
            <input class="commentTitle w3-input w3-border w3-white w3-small w3-margin-bottom" type="text" placeholder="Títol del comentari..." maxlength="255">
            <p class="commentBody w3-border w3-white w3-padding w3-small" contenteditable="true" style="min-height:36px"> </p>
            <button type="button" class="addComment w3-button w3-theme w3-small">
              <i class="fa fa-reply"></i> <span data-ca="Respondre" data-en="Reply">Respondre</span>
            </button>
          </div>
          <div class="commentsList" style="margin-top:6px"></div>
        </div>
      </div>
    </c:forEach>
  </c:otherwise>
</c:choose>

</c:if>

<script>
$(document).ready(function(){
    var lang = localStorage.getItem("lang") || "ca";
    if (typeof App !== "undefined" && App.applyLang) App.applyLang(lang);
});
</script>

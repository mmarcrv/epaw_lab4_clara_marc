<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:forEach var="t" items="${tweets}">
  <div id="${t.id}" class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity">
    <br>
    <img src="${user.picture}" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:50px">
    <span class="w3-right w3-opacity w3-small"> ${t.time} </span>
    <h5 class="w3-margin-bottom-0"> ${t.uname} </h5>
    <hr class="w3-clear">
    <h6> ${t.title} </h6>
    <p> ${t.textBody} </p>
    <button type="button" class="likeTweet w3-button w3-theme w3-margin-bottom">
        <i class="fa fa-thumbs-up"></i> &nbsp;Like (${t.likes})
    </button>
    <c:if test="${t.userId == user.id}">
      <button type="button" class="delTweet w3-button w3-red w3-margin-bottom">
          <i class="fa fa-trash"></i> &nbsp;Delete
      </button>
    </c:if>
  </div>
</c:forEach>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${not empty users}">
  <p class="w3-small w3-opacity" style="margin-bottom:4px"><b>Suggeriments</b></p>
  <c:forEach var="u" items="${users}">
    <div id="${u.id}" class="w3-container w3-card w3-round w3-white w3-section w3-animate-opacity" style="padding:10px">
      <div class="w3-row" style="align-items:center; display:flex; gap:8px">
        <img src="${u.picture}" alt="Avatar" class="w3-circle" style="width:40px; height:40px; flex-shrink:0">
        <span style="flex:1; font-weight:bold; font-size:0.9em">${u.name}</span>
      </div>
      <button type="button" class="followUser w3-button w3-theme w3-small w3-margin-top" style="width:100%; margin-top:8px">
        <i class="fa fa-user-plus"></i> &nbsp;Seguir
      </button>
    </div>
  </c:forEach>
</c:if>

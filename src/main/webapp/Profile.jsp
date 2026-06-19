<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
<c:when test="${user != null}">
<div id="${user.id}" class="w3-container w3-card w3-round w3-white w3-section w3-center">
  <h4>My Profile</h4>
  <p><img src="${user.picture}" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
  <p><b>${user.name}</b></p>
  <hr>
  <div class="w3-row">
    <div class="w3-half w3-padding">
      <b>${following}</b><br>
      <span class="w3-small w3-opacity">Seguint</span>
    </div>
    <div class="w3-half w3-padding">
      <b>${followers}</b><br>
      <span class="w3-small w3-opacity">Seguidors</span>
    </div>
  </div>
  <hr>
  <button type="button" class="editUser w3-row w3-button w3-green w3-section"><i class="fa fa-user-plus"></i> &nbsp;Edit</button>
</div>
<br>
</c:when>
<c:otherwise>
<p/>
</c:otherwise>
</c:choose>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h4 class="w3-opacity">Sol·licituds de seguiment</h4>

<c:choose>
  <c:when test="${empty requests}">
    <p class="w3-opacity">No tens sol·licituds pendents.</p>
  </c:when>
  <c:otherwise>
    <c:forEach var="u" items="${requests}">
      <div id="${u.id}" class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity"><br>
        <img src="${u.picture}" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:50px">
        <h5>${u.name}</h5>
        <p class="w3-small w3-opacity">vol seguir-te</p>
        <hr class="w3-clear">
        <button type="button" class="acceptRequest w3-button w3-green w3-margin-bottom">
          <i class="fa fa-check"></i> &nbsp;Acceptar
        </button>
        <button type="button" class="rejectRequest w3-button w3-red w3-margin-bottom">
          <i class="fa fa-times"></i> &nbsp;Rebutjar
        </button>
        <br>
      </div>
    </c:forEach>
  </c:otherwise>
</c:choose>

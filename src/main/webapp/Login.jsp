<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<script>$(document).ready(function(){ App.setLayout('simple'); lucide.createIcons(); });</script>

<div class="auth-card">

  <div class="auth-logo">
    <div class="pc-navbar-logo" style="width:48px;height:48px;border-radius:12px;margin:0 auto 12px">
      <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
    </div>
    <h2 class="auth-title">Benvingut a PisCompany</h2>
    <p class="auth-subtitle">Inicia sessió per continuar</p>
  </div>

  <form id="loginForm" action="Login" method="POST" novalidate>

    <div class="auth-field">
      <label class="auth-label" for="name">Nom d'usuari</label>
      <div class="auth-input-wrap">
        <i data-lucide="user"></i>
        <input type="text" class="pc-input auth-input" id="name" name="name"
          required minlength="5" maxlength="20"
          placeholder="El teu nom d'usuari"
          value="${user.name}"
          title="El nom d'usuari ha de tenir entre 5 i 20 caràcters.">
      </div>
    </div>

    <div class="auth-field">
      <label class="auth-label" for="password">Contrasenya</label>
      <div class="auth-input-wrap">
        <i data-lucide="lock"></i>
        <input type="password" class="pc-input auth-input" id="password" name="password"
          required
          pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$"
          placeholder="La teva contrasenya"
          value="${user.password}"
          title="Mínim 8 caràcters, majúscula, número i caràcter especial.">
      </div>
    </div>

    <button type="submit" class="pc-btn pc-btn-primary auth-submit">
      <i data-lucide="log-in"></i> Iniciar sessió
    </button>

  </form>

  <p class="auth-switch">
    Encara no tens compte?
    <a class="menu auth-link" href="Register">Registra't aquí</a>
  </p>

</div>

<script>
  App.Errors = {
    <c:forEach var="error" items="${errors}">
      "${error.key}": "${error.value}",
    </c:forEach>
  };
  App.initLoginValidation(App.Errors);
  lucide.createIcons();
</script>

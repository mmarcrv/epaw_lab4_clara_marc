<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<script>$(document).ready(function(){ App.setLayout('simple'); lucide.createIcons(); });</script>

<div class="auth-card auth-card-wide">

  <div class="auth-logo">
    <div class="pc-navbar-logo" style="width:48px;height:48px;border-radius:12px;margin:0 auto 12px">
      <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
    </div>
    <h2 class="auth-title">Crea el teu compte</h2>
    <p class="auth-subtitle">Uneix-te a la comunitat PisCompany</p>
  </div>

  <form id="registerForm" action="Register" method="POST" enctype="multipart/form-data" novalidate>

    <div class="auth-row">
      <div class="auth-field">
        <label class="auth-label" for="firstName">Nom</label>
        <div class="auth-input-wrap">
          <i data-lucide="user"></i>
          <input class="pc-input auth-input" type="text" id="firstName" name="firstName"
            required minlength="2" maxlength="50"
            placeholder="El teu nom"
            value="${user.firstName}"
            title="El nom ha de tenir entre 2 i 50 caràcters.">
        </div>
      </div>
      <div class="auth-field">
        <label class="auth-label" for="lastName">Cognom</label>
        <div class="auth-input-wrap">
          <i data-lucide="user"></i>
          <input class="pc-input auth-input" type="text" id="lastName" name="lastName"
            required minlength="2" maxlength="50"
            placeholder="El teu cognom"
            value="${user.lastName}"
            title="El cognom ha de tenir entre 2 i 50 caràcters.">
        </div>
      </div>
    </div>

    <div class="auth-row">
      <div class="auth-field">
        <label class="auth-label" for="dateOfBirth">Data de naixement</label>
        <div class="auth-input-wrap">
          <i data-lucide="calendar"></i>
          <input class="pc-input auth-input" type="date" id="dateOfBirth" name="dateOfBirth"
            required
            value="${user.dateOfBirth}"
            title="Introdueix una data vàlida.">
        </div>
      </div>
      <div class="auth-field">
        <label class="auth-label" for="comarca">Comarca d'origen</label>
        <select class="pc-select" id="comarca" name="comarca">
          <option value="">-- Selecciona comarca --</option>
          <option value="Alt Camp"             ${user.comarca == 'Alt Camp'             ? 'selected' : ''}>Alt Camp</option>
          <option value="Alt Empordà"           ${user.comarca == 'Alt Empordà'           ? 'selected' : ''}>Alt Empordà</option>
          <option value="Alt Penedès"           ${user.comarca == 'Alt Penedès'           ? 'selected' : ''}>Alt Penedès</option>
          <option value="Anoia"                 ${user.comarca == 'Anoia'                 ? 'selected' : ''}>Anoia</option>
          <option value="Bages"                 ${user.comarca == 'Bages'                 ? 'selected' : ''}>Bages</option>
          <option value="Baix Camp"             ${user.comarca == 'Baix Camp'             ? 'selected' : ''}>Baix Camp</option>
          <option value="Baix Ebre"             ${user.comarca == 'Baix Ebre'             ? 'selected' : ''}>Baix Ebre</option>
          <option value="Baix Llobregat"        ${user.comarca == 'Baix Llobregat'        ? 'selected' : ''}>Baix Llobregat</option>
          <option value="Baix Penedès"          ${user.comarca == 'Baix Penedès'          ? 'selected' : ''}>Baix Penedès</option>
          <option value="Barcelona"             ${user.comarca == 'Barcelona'             ? 'selected' : ''}>Barcelona</option>
          <option value="Berguedà"              ${user.comarca == 'Berguedà'              ? 'selected' : ''}>Berguedà</option>
          <option value="Cerdanya"              ${user.comarca == 'Cerdanya'              ? 'selected' : ''}>Cerdanya</option>
          <option value="Conca de Barberà"      ${user.comarca == 'Conca de Barberà'      ? 'selected' : ''}>Conca de Barberà</option>
          <option value="Garraf"                ${user.comarca == 'Garraf'                ? 'selected' : ''}>Garraf</option>
          <option value="Garrigues"             ${user.comarca == 'Garrigues'             ? 'selected' : ''}>Garrigues</option>
          <option value="Garrotxa"              ${user.comarca == 'Garrotxa'              ? 'selected' : ''}>Garrotxa</option>
          <option value="Gironès"               ${user.comarca == 'Gironès'               ? 'selected' : ''}>Gironès</option>
          <option value="Maresme"               ${user.comarca == 'Maresme'               ? 'selected' : ''}>Maresme</option>
          <option value="Montsià"               ${user.comarca == 'Montsià'               ? 'selected' : ''}>Montsià</option>
          <option value="Noguera"               ${user.comarca == 'Noguera'               ? 'selected' : ''}>Noguera</option>
          <option value="Osona"                 ${user.comarca == 'Osona'                 ? 'selected' : ''}>Osona</option>
          <option value="Pallars Jussà"         ${user.comarca == 'Pallars Jussà'         ? 'selected' : ''}>Pallars Jussà</option>
          <option value="Pallars Sobirà"        ${user.comarca == 'Pallars Sobirà'        ? 'selected' : ''}>Pallars Sobirà</option>
          <option value="Pla d'Urgell"          ${user.comarca == "Pla d'Urgell"          ? 'selected' : ''}>Pla d'Urgell</option>
          <option value="Pla de l'Estany"       ${user.comarca == "Pla de l'Estany"       ? 'selected' : ''}>Pla de l'Estany</option>
          <option value="Priorat"               ${user.comarca == 'Priorat'               ? 'selected' : ''}>Priorat</option>
          <option value="Ribera d'Ebre"         ${user.comarca == "Ribera d'Ebre"         ? 'selected' : ''}>Ribera d'Ebre</option>
          <option value="Ripollès"              ${user.comarca == 'Ripollès'              ? 'selected' : ''}>Ripollès</option>
          <option value="Segarra"               ${user.comarca == 'Segarra'               ? 'selected' : ''}>Segarra</option>
          <option value="Segrià"                ${user.comarca == 'Segrià'                ? 'selected' : ''}>Segrià</option>
          <option value="Selva"                 ${user.comarca == 'Selva'                 ? 'selected' : ''}>Selva</option>
          <option value="Solsonès"              ${user.comarca == 'Solsonès'              ? 'selected' : ''}>Solsonès</option>
          <option value="Tarragonès"            ${user.comarca == 'Tarragonès'            ? 'selected' : ''}>Tarragonès</option>
          <option value="Terra Alta"            ${user.comarca == 'Terra Alta'            ? 'selected' : ''}>Terra Alta</option>
          <option value="Urgell"                ${user.comarca == 'Urgell'                ? 'selected' : ''}>Urgell</option>
          <option value="Vallès Occidental"     ${user.comarca == 'Vallès Occidental'     ? 'selected' : ''}>Vallès Occidental</option>
          <option value="Vallès Oriental"       ${user.comarca == 'Vallès Oriental'       ? 'selected' : ''}>Vallès Oriental</option>
        </select>
      </div>
    </div>

    <div class="auth-field">
      <label class="auth-label" for="email">Correu electrònic</label>
      <div class="auth-input-wrap">
        <i data-lucide="mail"></i>
        <input class="pc-input auth-input" type="email" id="email" name="email"
          required
          placeholder="correu@exemple.com"
          value="${user.email}"
          title="Introdueix una adreça de correu vàlida.">
      </div>
    </div>

    <div class="auth-field">
      <label class="auth-label" for="name">Nom d'usuari</label>
      <div class="auth-input-wrap">
        <i data-lucide="at-sign"></i>
        <input class="pc-input auth-input" type="text" id="name" name="name"
          required minlength="5" maxlength="20"
          placeholder="nom_usuari"
          value="${user.name}"
          title="El nom d'usuari ha de tenir entre 5 i 20 caràcters.">
      </div>
    </div>

    <div class="auth-row">
      <div class="auth-field">
        <label class="auth-label" for="password">Contrasenya</label>
        <div class="auth-input-wrap">
          <i data-lucide="lock"></i>
          <input class="pc-input auth-input" type="password" id="password" name="password"
            required
            pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$"
            placeholder="Mínim 8 caràcters"
            value="${user.password}"
            title="Mínim 8 caràcters, majúscula, número i caràcter especial.">
        </div>
      </div>
      <div class="auth-field">
        <label class="auth-label" for="confirmPassword">Repeteix contrasenya</label>
        <div class="auth-input-wrap">
          <i data-lucide="lock"></i>
          <input class="pc-input auth-input" type="password" id="confirmPassword" name="confirmPassword"
            required
            placeholder="Repeteix la contrasenya"
            value="${user.password}"
            title="Les contrasenyes han de coincidir.">
        </div>
      </div>
    </div>

    <div class="auth-field">
      <label class="auth-label" for="picture">Foto de perfil <span style="color:var(--text-muted);font-weight:400">(opcional)</span></label>
      <div class="pc-upload-area" style="padding:14px" onclick="$('#picture').click()">
        <i data-lucide="image"></i>
        <span>Selecciona una imatge</span>
      </div>
      <input class="pc-input" type="file" id="picture" name="picture" accept="image/*" style="display:none">
    </div>

    <button type="submit" class="pc-btn pc-btn-primary auth-submit">
      <i data-lucide="user-plus"></i> Crear compte
    </button>

  </form>

  <p class="auth-switch">
    Ja tens compte?
    <a class="menu auth-link" href="Login">Inicia sessió aquí</a>
  </p>

</div>

<script>
  App.Errors = {
    <c:forEach var="error" items="${errors}">
      "${error.key}": "${error.value}",
    </c:forEach>
  };
  App.initRegisterValidation(App.Errors);
  lucide.createIcons();
</script>

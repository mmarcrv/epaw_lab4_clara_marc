<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<script>
  $(document).ready(function(){ 
    App.setLayout('simple'); 
    lucide.createIcons(); 
  });
</script>

<div class="auth-card auth-card-wide">

  <div class="auth-logo">
    <div class="pc-navbar-logo" style="width:48px;height:48px;border-radius:12px;margin:0 auto 12px">
      <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
    </div>
    <h2 class="auth-title">Edita el teu perfil</h2>
    <p class="auth-subtitle">Actualitza la teva informació personal</p>
  </div>

  <form id="editProfileForm" action="EditProfile" method="POST" enctype="multipart/form-data" novalidate>

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
          required minlength="5" maxlength="15"
          placeholder="nom_usuari"
          value="${user.name}"
          title="El nom d'usuari ha de tenir entre 5 i 15 caràcters.">
      </div>
    </div>

    <div class="auth-field" style="border: 1px dashed var(--border); padding: 14px; border-radius: 8px; margin-bottom: 20px; background: rgba(0,0,0,0.02)">
      <label class="auth-label" style="font-weight: 600; margin-bottom: 10px; display: block">Canviar contrasenya <span style="font-weight:400; color: var(--text-muted)">(deixa en blanc si no la vols canviar)</span></label>
      
      <div class="auth-field">
        <label class="auth-label" for="oldPassword">Contrasenya antiga</label>
        <div class="auth-input-wrap">
          <i data-lucide="lock"></i>
          <input class="pc-input auth-input" type="password" id="oldPassword" name="oldPassword"
            placeholder="Contrasenya actual">
        </div>
      </div>

      <div class="auth-row" style="margin-top: 12px">
        <div class="auth-field">
          <label class="auth-label" for="password">Nova contrasenya</label>
          <div class="auth-input-wrap">
            <i data-lucide="lock"></i>
            <input class="pc-input auth-input" type="password" id="password" name="password"
              placeholder="Mínim 8 caràcters">
          </div>
        </div>
        <div class="auth-field">
          <label class="auth-label" for="confirmPassword">Repeteix nova contrasenya</label>
          <div class="auth-input-wrap">
            <i data-lucide="lock"></i>
            <input class="pc-input auth-input" type="password" id="confirmPassword" name="confirmPassword"
              placeholder="Repeteix la nova contrasenya">
          </div>
        </div>
      </div>
    </div>

    <div class="auth-field">
      <label class="auth-label" for="picture">Foto de perfil <span style="color:var(--text-muted);font-weight:400">(opcional)</span></label>
      <c:if test="${not empty user.picture}">
        <div style="display: flex; align-items: center; gap: 12px; margin-bottom: 10px;">
          <img src="${user.picture}" alt="Avatar actual" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover; border: 1px solid var(--border)">
          <span style="font-size: 0.85rem; color: var(--text-muted)">Imatge actual</span>
        </div>
      </c:if>
      <div class="pc-upload-area" style="padding:14px" onclick="$('#picture').click()">
        <i data-lucide="image"></i>
        <span>Selecciona una nova imatge</span>
      </div>
      <input class="pc-input" type="file" id="picture" name="picture" accept="image/*" style="display:none">
    </div>

    <div style="display: flex; gap: 12px; margin-top: 24px">
      <a href="UserProfile" class="menu pc-btn pc-btn-ghost" style="flex: 1; text-align: center; justify-content: center; text-decoration: none; display: inline-flex; align-items: center; gap: 6px;">
        Cancel·lar
      </a>
      <button type="submit" class="pc-btn pc-btn-primary auth-submit" style="flex: 1; margin: 0">
        <i data-lucide="save"></i> Desar canvis
      </button>
    </div>

  </form>

</div>

<script>
  App.Errors = {
    <c:forEach var="error" items="${errors}">
      "${error.key}": "${error.value}",
    </c:forEach>
  };

  App.initEditProfileValidation = function (serverErrors)  {
    const form = document.getElementById('editProfileForm');
    const oldPassword = document.getElementById('oldPassword');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    const dateOfBirth = document.getElementById('dateOfBirth');
    const email = document.getElementById('email');
    const firstName = document.getElementById('firstName');
    const lastName = document.getElementById('lastName');

    function validatePasswords() {
      const hasOld = oldPassword.value.trim().length > 0;
      const hasNew = password.value.trim().length > 0;
      const hasConf = confirmPassword.value.trim().length > 0;

      if (hasOld || hasNew || hasConf) {
        if (!hasOld) {
          oldPassword.setCustomValidity("Cal introduir la contrasenya antiga per canviar-la.");
        } else {
          oldPassword.setCustomValidity("");
        }

        if (!hasNew) {
          password.setCustomValidity("Cal introduir la nova contrasenya.");
        } else {
          const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;
          if (!regex.test(password.value)) {
            password.setCustomValidity("Mínim 8 caràcters, majúscula, número i caràcter especial.");
          } else {
            password.setCustomValidity("");
          }
        }

        if (!hasConf) {
          confirmPassword.setCustomValidity("Cal repetir la nova contrasenya.");
        } else if (password.value !== confirmPassword.value) {
          confirmPassword.setCustomValidity("Les contrasenyes no coincideixen.");
        } else {
          confirmPassword.setCustomValidity("");
        }
      } else {
        oldPassword.setCustomValidity("");
        password.setCustomValidity("");
        confirmPassword.setCustomValidity("");
      }
    }

    // Fix validation logic immediately
    confirmPassword.addEventListener('input', () => {
      const hasOld = oldPassword.value.trim().length > 0;
      const hasNew = password.value.trim().length > 0;
      const hasConf = confirmPassword.value.trim().length > 0;
      if (hasOld || hasNew || hasConf) {
        confirmPassword.setCustomValidity(
          confirmPassword.value !== password.value ? "Les contrasenyes no coincideixen." : ""
        );
      } else {
        confirmPassword.setCustomValidity("");
      }
    });

    password.addEventListener('input', () => {
      validatePasswords();
    });

    oldPassword.addEventListener('input', () => {
      validatePasswords();
    });

    dateOfBirth.addEventListener('change', () => {
      if (dateOfBirth.value) {
        const birthDate = new Date(dateOfBirth.value);
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        if (birthDate > today) {
          dateOfBirth.setCustomValidity("La data de naixement no pot ser en el futur.");
        } else {
          dateOfBirth.setCustomValidity("");
        }
      }
    });

    email.addEventListener('input', () => {
      const emailRegex = /^[A-Za-z0-9+_.-]+@(.+)$/;
      if (email.value && !emailRegex.test(email.value)) {
        email.setCustomValidity("Format de correu electrònic invàlid.");
      } else {
        email.setCustomValidity("");
      }
    });

    function validateNameField(field) {
      field.addEventListener('input', () => {
        const value = field.value;
        if (value && /[^a-zA-ZàáäâèéëêìíïîòóöôùúüûñçÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛÑÇ\s'-]/.test(value)) {
          field.setCustomValidity("El nom només pot contenir lletres, espais, apòstrofs i guions.");
        } else {
          field.setCustomValidity("");
        }
      });
    }

    validateNameField(firstName);
    validateNameField(lastName);

    Object.entries(serverErrors).forEach(([field, message]) => {
      const input = document.getElementsByName(field)[0];
      if (input) {
        input.setCustomValidity(message);
        input.reportValidity();
        input.addEventListener('input', () => {
          input.setCustomValidity('');
          input.reportValidity();
        });
      }
    });

    form.addEventListener('submit', event => {
      validatePasswords();

      if (!form.checkValidity()) {
        event.preventDefault();
        form.reportValidity();
      }
    });
  };

  App.initEditProfileValidation(App.Errors);
  lucide.createIcons();
</script>

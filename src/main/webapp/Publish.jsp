<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="pc-publish-card">
  <h2 class="pc-publish-title">Crear nova publicació</h2>

  <div class="pc-field">
    <label>Categoria</label>
    <select id="tweetCategory" class="pc-select">
      <option value="">Busco company</option>
      <option value="Busco company">Busco company</option>
      <option value="Oferta habitació">Oferta habitació</option>
      <option value="Ressenya residència">Ressenya residència</option>
      <option value="Recomanació zona">Recomanació zona</option>
    </select>
  </div>

  <div class="pc-field">
    <label>Localització</label>
    <div class="pc-input-icon-wrap">
      <i data-lucide="map-pin"></i>
      <input id="tweetLocation" type="text" class="pc-input" placeholder="Ex: Barcelona - Gràcia" maxlength="100">
    </div>
  </div>

  <div class="pc-field">
    <label>Contingut</label>
    <textarea id="tweetBody" class="pc-textarea" placeholder="Comparteix la teva publicació... Explica detalls sobre l'habitació, el que busques en un company, recomanacions de zona, etc." maxlength="500" rows="6"></textarea>
    <div class="pc-char-count"><span id="charCount">0</span> / 500 caràcters</div>
  </div>

  <div class="pc-field">
    <label>Imatges (opcional)</label>
    <div class="pc-upload-area" onclick="$('#tweetPicture').click()">
      <i data-lucide="image"></i>
      <span>Afegir imatges del pis o habitació</span>
    </div>
    <input id="tweetPicture" type="file" accept="image/*" style="display:none">
  </div>

  <div class="pc-tips">
    <h6>Consells per a una bona publicació:</h6>
    <ul>
      <li>Sigues clar i concís en la descripció</li>
      <li>Inclou el preu si ofereixes una habitació</li>
      <li>Menciona serveis i transport públic proper</li>
      <li>Afegeix fotos per obtenir més respostes</li>
    </ul>
  </div>

  <div class="pc-publish-actions">
    <button class="menu pc-btn pc-btn-ghost" href="Timeline">Cancel·lar</button>
    <button id="addTweet" type="button" class="pc-btn pc-btn-primary">
      <i data-lucide="send"></i> Publicar
    </button>
  </div>
</div>

<script>
$(document).ready(function(){ App.setLayout('simple'); lucide.createIcons(); });
</script>

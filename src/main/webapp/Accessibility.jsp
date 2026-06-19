<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="pc-card">
  <div class="pc-right-title">Accessibilitat</div>

  <div class="pc-access-section">
    <div class="pc-access-title">Mode de color</div>
    <div class="pc-toggle-group">
      <button id="toggleLight" class="pc-toggle-btn active">
        <i data-lucide="sun"></i> Clar
      </button>
      <button id="toggleTheme" class="pc-toggle-btn">
        <i data-lucide="moon"></i> Fosc
      </button>
    </div>
  </div>

  <div class="pc-access-section">
    <div class="pc-access-title">Mida de lletra</div>
    <div class="pc-toggle-group">
      <button class="font-toggle-btn pc-toggle-btn" data-size="small" style="font-size:0.78rem">
        <i data-lucide="type"></i> Petita
      </button>
      <button class="font-toggle-btn pc-toggle-btn" data-size="normal">
        <i data-lucide="type"></i> Normal
      </button>
      <button class="font-toggle-btn pc-toggle-btn" data-size="large" style="font-size:1rem">
        <i data-lucide="type"></i> Gran
      </button>
    </div>
  </div>

  <div class="pc-access-section">
    <div class="pc-access-title">Idioma</div>
    <div class="pc-toggle-group">
      <button id="langCA" class="pc-toggle-btn active">
        <i data-lucide="globe"></i> Català
      </button>
      <button id="langEN" class="pc-toggle-btn">
        <i data-lucide="globe"></i> English
      </button>
    </div>
  </div>

  <div class="pc-card" style="background:var(--green-bg2);border:none;padding:12px;margin-top:4px">
    <div style="font-weight:600;font-size:0.82rem;margin-bottom:6px">Sobre les configuracions</div>
    <p style="font-size:0.78rem;color:var(--text-muted)">Personalitza la teva experiència a PisCompany amb les opcions d'accessibilitat. Aquests canvis s'apliquen a tot el lloc web.</p>
  </div>
</div>

<script>
(function(){
  var theme = localStorage.getItem("theme") || "light";
  var fontSize = localStorage.getItem("fontSize") || "normal";
  var lang = localStorage.getItem("lang") || "ca";
  if (theme === "dark") { $("#toggleTheme").addClass("active"); $("#toggleLight").removeClass("active"); }
  $(".font-toggle-btn[data-size='" + fontSize + "']").addClass("active");
  if (lang === "en") { $("#langEN").addClass("active"); $("#langCA").removeClass("active"); }
  lucide.createIcons();
})();
</script>

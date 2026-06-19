<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="w3-card w3-white w3-round w3-padding w3-section">
  <h5 class="w3-center" data-ca="Accessibilitat" data-en="Accessibility">Accessibilitat</h5>
  <hr>

  <p class="w3-small"><b data-ca="Tema" data-en="Theme">Tema</b></p>
  <button id="toggleTheme" class="w3-button w3-round w3-border w3-block w3-margin-bottom">
    <i class="fa fa-moon-o"></i>
    <span data-ca="Mode fosc" data-en="Dark mode">Mode fosc</span>
  </button>

  <hr>

  <p class="w3-small"><b data-ca="Mida de lletra" data-en="Font size">Mida de lletra</b></p>
  <div class="w3-row w3-margin-bottom">
    <div class="w3-third" style="padding:2px">
      <button id="fontSmall" class="w3-button w3-round w3-border w3-block" style="font-size:0.8em">A-</button>
    </div>
    <div class="w3-third" style="padding:2px">
      <button id="fontNormal" class="w3-button w3-round w3-border w3-block">A</button>
    </div>
    <div class="w3-third" style="padding:2px">
      <button id="fontLarge" class="w3-button w3-round w3-border w3-block" style="font-size:1.2em">A+</button>
    </div>
  </div>

  <hr>

  <p class="w3-small"><b data-ca="Idioma" data-en="Language">Idioma</b></p>
  <div class="w3-row">
    <div class="w3-half" style="padding:2px">
      <button id="langCA" class="w3-button w3-round w3-border w3-block">Català</button>
    </div>
    <div class="w3-half" style="padding:2px">
      <button id="langEN" class="w3-button w3-round w3-border w3-block">English</button>
    </div>
  </div>
</div>

<script>
(function () {
    var theme = localStorage.getItem("theme") || "light";
    var fontSize = localStorage.getItem("fontSize") || "normal";
    var lang = localStorage.getItem("lang") || "ca";

    if (theme === "dark") {
        $("#toggleTheme i").removeClass("fa-moon-o").addClass("fa-sun-o");
        $("#toggleTheme span").attr("data-ca", "Mode clar").attr("data-en", "Light mode");
    }

    $("#fontSmall, #fontNormal, #fontLarge").removeClass("w3-theme");
    if (fontSize === "small") $("#fontSmall").addClass("w3-theme");
    else if (fontSize === "large") $("#fontLarge").addClass("w3-theme");
    else $("#fontNormal").addClass("w3-theme");

    if (lang === "en") $("#langEN").addClass("w3-theme");
    else $("#langCA").addClass("w3-theme");

    if (typeof App !== "undefined" && App.applyLang) App.applyLang(lang);
})();
</script>

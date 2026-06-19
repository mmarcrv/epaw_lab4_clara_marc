<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<script type="text/javascript">
$(document).ready(function(){
  App.setLayout('full');
  $('#rcolumn').load('HomeRight', function(){ lucide.createIcons(); });
  $('#iterator').load('Tweets', function(){ lucide.createIcons(); });
});
</script>

<!-- Hero -->
<div class="pc-hero">
  <h2>Troba el teu pis perfecte a Catalunya</h2>
  <p>Connecta amb estudiants, busca companys de pis i descobreix les millors zones per viure</p>
  <div class="pc-search-wrap">
    <i data-lucide="search"></i>
    <input type="text" id="searchInput" placeholder="Cerca per zona, ciutat o categoria...">
  </div>
  <div class="pc-filters">
    <button class="pc-filter-btn active" data-cat="">Tots</button>
    <button class="pc-filter-btn" data-cat="Busco company">Busco company</button>
    <button class="pc-filter-btn" data-cat="Oferta habitació">Oferta habitació</button>
    <button class="pc-filter-btn" data-cat="Ressenya residència">Ressenyes</button>
    <button class="pc-filter-btn" data-cat="Recomanació zona">Recomanacions</button>
  </div>
</div>

<!-- Feed -->
<div id="iterator"></div>

<script>
$(document).ready(function(){
  lucide.createIcons();
  /* Client-side text search */
  $('#searchInput').on('input', function(){
    var q = $(this).val().toLowerCase();
    $('.tweet-card').each(function(){
      var text = $(this).text().toLowerCase();
      $(this).toggle(text.includes(q));
    });
  });
});
</script>

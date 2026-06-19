<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(document).ready(function(){
  App.setLayout('full');
  $('#rcolumn').load('HomeRight', function(){ lucide.createIcons(); });
  $('#iterator').load('Following', function(){ lucide.createIcons(); });
});
</script>

<div class="pc-hero">
  <h2>Publicacions de la gent que segueixes</h2>
  <p>Les últimes publicacions de les persones que estàs seguint</p>
</div>

<div id="iterator"></div>

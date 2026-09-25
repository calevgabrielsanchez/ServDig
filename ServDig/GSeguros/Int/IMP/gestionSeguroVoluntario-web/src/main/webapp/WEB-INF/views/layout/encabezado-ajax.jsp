<%@ page contentType="text/html; charset=UTF-8" %>
<div id="headerWrapper"></div>

<script>
$(document).ready(function() {
    var urlHeader = AJAX_BASE_PATH + '/html/layout/header.html';
    $('#headerWrapper').load(urlHeader, function(response, status, xhr) {
        if (status == "error") {
            console.error("Error cargando Header: " + xhr.status + " " + xhr.statusText);
            // Fallback visual si falla la carga (ej: por CORS en local)
            $('#headerWrapper').html('<nav class="navbar navbar-inverse sub-navbar navbar-fixed-top" style="background:#9d2449;height:50px;"><div class="container"><div class="navbar-header"><a class="navbar-brand" href="#">IMSS Digital (Modo Local)</a></div></div></nav><div style="height:50px"></div>');
        }
    });
});
</script>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script src="https://framework-gb.cdn.gob.mx/gobmx.js"></script>
<script >
	setTimeout ("reemplazaAcentos();", 1100); 
	function reemplazaAcentos(){
		var index = 0
		$("footer.main-footer").each(function (index) {
			$("footer.main-footer")[index].innerHTML = $("footer.main-footer")[index].innerHTML.replace(/Ã³/g, '&oacute;');
			$("footer.main-footer")[index].innerHTML = $("footer.main-footer")[index].innerHTML.replace(/Ãº/g, '&uacute;');
		});
	}
</script>

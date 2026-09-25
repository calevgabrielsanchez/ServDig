
	<button type="button" id="regresaPaginaAnterior"
		class="btn btn-default">
		<spring:message code="label.regresar" />
	</button>

<script type="text/javascript">
	document.charset = "ISO-8859-1";
	var contextPath="${contextpath}";	
	$('#regresaPaginaAnterior').click(function(e) {
		 e.preventDefault();
		 window.location.href = "${paginaAnterior}";	 
	});
</script>
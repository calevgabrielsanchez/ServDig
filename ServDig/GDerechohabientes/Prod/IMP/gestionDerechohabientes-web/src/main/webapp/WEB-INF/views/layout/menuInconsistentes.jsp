<%@ include file="../general/taglibs.jsp" %>

<script> var contextPath = "<%=request.getContextPath()%>"; </script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.fileDownload.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.cookie.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/documentosReportes/muestraDocs.js" htmlEscape="true" />"></script>
	
<div class="menu_holder" align="right">
	
	
	<div id="dgCerrarSesion" title="Cerrar Sesion" >
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>
			  &iquest;Esta Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		<br />
		 
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>
	

	<!--Inicio Menu -->
	
<div class="menu_holder">	
	<ul class="menu">
	<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario==1}">
		<li><a href="#">Reportes y documentos</a>
			<ul class="sub-menu">
				<li><a href="#" onclick="showReporte('comprobanteVD')">Comprobante de vigencia de derechos</a></li>
			</ul>
		</li>
	</c:if>
</ul>
</div>
<!-- Termino Menu -->

	
</div>

<script>
	$(document).ready(function() {
		$('.hrefTramite').click(function (event){
		    event.preventDefault(); 
		    $.blockUI();
		    var direccion = $(this).attr('href');
		    location.href = direccion;
		});
	}
	);
</script>




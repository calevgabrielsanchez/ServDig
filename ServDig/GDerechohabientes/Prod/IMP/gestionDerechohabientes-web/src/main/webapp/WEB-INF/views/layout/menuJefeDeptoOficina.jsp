 <%@ include file="../general/taglibs.jsp" %>

<script>
	var contextPath = "<%=request.getContextPath()%>";
		
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.fileDownload.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.cookie.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/documentosReportes/muestraDocs.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/generarSav011.js" htmlEscape="true" />"></script>
	
<div class="menu_holder" align="right">
	
	
	<div id="dgCerrarSesion" title="Cerrar Sesion" >
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>
			  &iquest;Esta Ud. seguro de cerrar su sesi&oacute;n?
		</p>
		</br>
		 
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>
	</div>
	

	<!--Inicio Menu -->
	
<div class="menu_holder">	
	<ul class="menu">
		<!-- se cambia la seccion para incluir los menus -->
		
			<li><a href="#">Baja</a>
				<ul class="sub-menu">
						<c:if test="${opciones.ind_baja_administrativa_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/baja/administrativa/home">Administrativa</a></li>
						</c:if>
				</ul>
			</li>
			<li><a href="#" >Pr&oacute;rroga servicios</a>
				<ul class="sub-menu">
					<c:if test="${opciones.ind_prorroga_permanente_v}">
					<li><a class = "hrefTramite" href="/${mvn.web.app.root}/prorroga/vigenciaPermanente">Vigencia permanente</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_acuerdo_v}">
					<li> <a class = "hrefTramite" href="/${mvn.web.app.root}/prorroga/acuerdos">Acuerdo</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_laudo_v}">
					<li><a class = "hrefTramite" href="/${mvn.web.app.root}/prorroga/laudo">Laudo</a></li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_temporal_v}">
					<li><a class = "hrefTramite" href="/${mvn.web.app.root}/prorroga/vigenciaTemporal">Vigencia temporal</a></li>
					</c:if>
				</ul>
			</li>
			
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




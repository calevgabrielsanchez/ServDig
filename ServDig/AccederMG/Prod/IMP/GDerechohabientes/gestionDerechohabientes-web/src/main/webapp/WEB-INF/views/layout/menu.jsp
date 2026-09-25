 <%@ include file="../general/taglibs.jsp" %>

<script>
	var contextPath = "<%=request.getContextPath()%>";
		
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.fileDownload.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.cookie.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/documentosReportes/muestraDocs.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/generarSav011.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/derechosArco.js" htmlEscape="true" />"></script>

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
		<c:choose>
			<c:when test="${usuarioObj.perfilUsuario.idPerfilUsuario==1}">
			<c:if test="${miGrupoFamiliar.estadoDerechohabiente.idEstadoDerechohabiente != 3 ||  
					miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente == 2}">
			<c:if test="${opciones.ind_registro_derechohabientes_v}">
			<li>
				 
				  <c:if test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6 ||
				  		(cabezaGrupoFamiliar.calidadParentesco.idParentesco == 5 && miGrupoFamiliar.estadoDerechohabiente.idEstadoDerechohabiente != 3)}">
					<a class = "hrefTramite" href="<%=request.getContextPath()%>/tramite/registro/iniciarTramite">Registro</a>	
				</c:if>	
			</li>
			</c:if>
			<li>
				<a href="#">Correcci&oacute;n</a>
				<ul class="sub-menu">
					<c:if test="${opciones.ind_correccion_derechohabiente_v}">
					<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/correccion/datosPersonales/">Correcci&oacute;n de datos</a></li>
					</c:if>
					<c:if test="${opciones.ind_cambio_clinica_destino || opciones.ind_cambio_clinica_origen}">
					<li><a href="#">Cambio de  cl&iacute;nica</a>
						<ul class="sub-menu">
							<c:if test="${opciones.ind_cambio_clinica_origen}">
								<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/correccion/cambioUmf/origen">UMF Origen</a></li>
							</c:if>
							<c:if test="${opciones.ind_cambio_clinica_destino}">
								<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/correccion/cambioUmf/destino">UMF Destino</a></li>
							</c:if>
						</ul>
					</li>
					</c:if>
					<c:if test="${opciones.ind_cambio_medico_turno_v}">
					<li><a href="<%=request.getContextPath()%>/derechohabiente/correccion/cambioMedico/">Cambio de turno, m&eacute;dico y consultorio</a></li>
					</c:if>
					<c:if test="${opciones.ind_autorizacion_circunscripcion_v || opciones.ind_suspencion_circunscripcion_v}">
						<li><a href="#">Circunscripci&oacute;n foranea</a>
							<ul class="sub-menu">
								<c:if test="${opciones.ind_autorizacion_circunscripcion_v}">
								<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/correccion/circunscripcion/autorizacion">Autorizaci&oacute;n</a></li>
								</c:if>
								<c:if test="${opciones.ind_suspencion_circunscripcion_v}">
								<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/correccion/circunscripcion/suspencion">Suspensi&oacute;n</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					<c:if test="${opciones.ind_asignacion_medico_v}">
					<li><a href="<%=request.getContextPath()%>/derechohabiente/correccion/asignacionMedico">Asignaci&oacute;n de turno, m&eacute;dico y consultorio </a></li>
					</c:if>
				</ul>
			</li>
			</c:if>
			<li><a href="#">Baja</a>
				<ul class="sub-menu">
					<c:if test="${opciones.ind_baja_defuncion_v}">
					<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/baja/defuncion/home">Defunci&oacute;n</a></li>
					</c:if>
					<c:if test="${miGrupoFamiliar.estadoDerechohabiente.idEstadoDerechohabiente != 3}">
						<c:if test="${opciones.ind_baja_concubinato_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/baja/concubinato/home">T&eacute;rmino de concubinato</a></li>
						</c:if>
						<c:if test="${opciones.ind_baja_divorcio_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/baja/divorcio/home">Divorcio</a></li>
						</c:if>
						<c:if test="${opciones.ind_baja_dependencia_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/baja/convivencia/home">T&eacute;rmino de dependencia</a></li>
						</c:if>
						
					</c:if>
				</ul>
			</li>
			<li><a href="#" >Pr&oacute;rroga servicios</a>
				<ul class="sub-menu">
					<c:if test="${opciones.ind_prorroga_obstetrico_v || opciones.ind_prorroga_enfermedad_v}">
						<li><a href="#" title="Incapacidad f&iacute;sica">Por incapacidad</a>
							<ul class="sub-menu">
								<c:if test="${opciones.ind_prorroga_obstetrico_v}">
								<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/prorroga/obstetricos" title="Obst&eacute;trico" >Obst&eacute;trico</a></li>
								</c:if>
								<c:if test="${opciones.ind_prorroga_enfermedad_v}">
								<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/prorroga/enfermedad" title="Incapacidad F&iacute;sica o ps&iacute;quica">F&iacute;sica o ps&iacute;quica</a></li>
								</c:if>
							</ul>
						</li>
					</c:if>
					<c:if test="${opciones.ind_prorroga_estudios_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/prorroga/estudios">Estudios</a></li>
					</c:if>
				</ul>
			</li>
			<li><a href="#">Reportes y documentos</a>
			<ul class="sub-menu">
				<c:if test="${miGrupoFamiliar.estadoDerechohabiente.idEstadoDerechohabiente != 3}">
					 
					<li><a href="#" title="Solicitud de registro o aviso de baja de beneficiario" onclick="showReporte('SAV002')">SAV002</a></li>
					<li><a href="<%=request.getContextPath()%>/reportesDocumentos/documentoSAV005" title="Solicitud de cambio de unidad m&eacute;dica de adscripci&oacute;n">SAV005</a></li>
					<!--<li><a href="/gestionDerechohabientes-web/derechohabiente/baja/defuncion/home">SAV006</a></li>-->
					<li><a href="<%=request.getContextPath()%>/reportesDocumentos/documentoSAV007" title="Aviso de pr&oacute;rroga de servicios m&eacute;dicos">SAV007</a></li>
					<!--<li><a href="#" onclick="showReporte('SAV010')">SAV010</a></li>-->
					<!-- <li><a href="#" onclick="generarSAV011();">SAV011</a></li>  -->
					<!--  <li><a href="#" title="Autorizaci&oacute;n para recibir o suspender servicios m&eacute;dicos en circunscripci&oacute;n for&aacute;nea">SAV017</a> 
						<ul class="sub-menu">
							<li><a href="/gestionDerechohabientes-web/reportesDocumentos/documentoSAV017A" title="Autorizaci&oacute;n para recibir o suspender servicios m&eacute;dicos en circunscripci&oacute;n for&aacute;nea autorizadas" >SAV017 Autorizadas</a></li>
							<li><a href="/gestionDerechohabientes-web/reportesDocumentos/documentoSAV017S" title="Autorizaci&oacute;n para recibir o suspender servicios m&eacute;dicos en circunscripci&oacute;n for&aacute;nea suspendidas">SAV017 Suspendidas</a></li>
						</ul>
					</li> -->
				</c:if>
				<li><a href="<%=request.getContextPath()%>/reportesDocumentos/cartillaNacionalSalud" >Cartilla nacional de salud</a></li>
				<c:if test="${miGrupoFamiliar.estadoDerechohabiente.idEstadoDerechohabiente != 3}">
					<!--  <li><a href="#" title="Tarjeta de adscripci&oacute;n a m&eacute;dico familiar o consultorio" onclick="showReporte('reporte4305A')">4-30-5A/2003</a></li> -->
				</c:if>
				<li><a href="#" onclick="showReporte('comprobanteVD')">Comprobante de vigencia de derechos</a></li>
			</ul>
			</li>
			</c:when>
			
			<c:when test="${usuarioObj.perfilUsuario.idPerfilUsuario==8 || usuarioObj.perfilUsuario.idPerfilUsuario==9}">
				<li>
					<a href="#">Registro</a>
					<ul class="sub-menu">
					<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/tramite/registroAcuerdo">Acuerdo del consejo consultivo</a></li>
					</ul>
				</li>
				<li>
				<a href="#">Correcci&oacute;n</a>
				<ul class="sub-menu">
					<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/tramites/admin/reactivacion">Reactivaci&oacute;n administrativa</a></li>
				</ul>
			</li>
				<li><a href="#">Baja</a>
					<ul class="sub-menu">
							<c:if test="${opciones.ind_baja_administrativa_v}">
							<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/derechohabiente/baja/administrativa/home">Administrativa</a></li>
							</c:if>
							<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/tramites/admin/baja">Baja administrativa</a></li>
							<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/tramites/admin/suspension">Suspensi&oacute;n administrativa</a></li>
					</ul>
				</li>
				<li><a href="#" >Pr&oacute;rroga servicios</a>
					<ul class="sub-menu">
						<c:if test="${opciones.ind_prorroga_permanente_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/prorroga/vigenciaPermanente">Vigencia permanente</a></li>
						</c:if>
						<c:if test="${opciones.ind_prorroga_acuerdo_v}">
						<li> <a class = "hrefTramite" href="<%=request.getContextPath()%>/prorroga/acuerdos">Acuerdo</a></li>
						</c:if>
						<c:if test="${opciones.ind_prorroga_laudo_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/prorroga/laudo">Laudo</a></li>
						</c:if>
						<c:if test="${opciones.ind_prorroga_temporal_v}">
						<li><a class = "hrefTramite" href="<%=request.getContextPath()%>/prorroga/vigenciaTemporal">Vigencia temporal</a></li>
						</c:if>
					</ul>
				</li>
				<li><a href="#" >Derechos ARCO</a>
					<ul class="sub-menu">
						<li><a href="javascript:void(0)" id="bloqDerechosArco" onclick="DerechosArco.action(true)">Bloquear</a></li>
						<li><a href="javascript:void(0)" id="desbloqDerechosArco" onclick="DerechosArco.action(false)">Desbloquear</a></li>
					</ul>
				</li>
			</c:when>
		</c:choose>
			
</ul>
</div>
<!-- Termino Menu -->
	<c:if test="${usuarioObj.perfilUsuario.idPerfilUsuario==8 || usuarioObj.perfilUsuario.idPerfilUsuario==9}">
		<div id="dialogAction"></div>
	</c:if>
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

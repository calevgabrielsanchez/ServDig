<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/reglasTabSeguimientoCorrecion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/conclusion/conclusionSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/listaTareas.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/correccion/seguimiento/seguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/devSubDelegacionSeguimientoCorreccion/devSubDelegacionSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/derivacionFiscalizacion/devFiscalizacionSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/reactivacion/reactivacionSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/devDictamenSeguimientoCorreccion/devDictamenSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/cancelacion/cancelacionSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/reqDocumentacion/reqDocumentacionSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/ofResultados/ofResultadosSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/conclusion/conclusionSeguimientoCorreccion.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/cedulaRevision/cedulaRevisionSupervisor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/cedulaValidacion/cedulaValidacion.js"></script>
																 
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/tabControler.js"></script>

<script src="/correccion-web/resources/js/delta/limpiaFormularios.js" type="text/javascript"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<link href="/correccion-web/resources/estilos/estilo.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/correcion.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css">
<link href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css" rel="stylesheet">

<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/cedulasStyle.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/estilo.css">


<div id="general"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<form:form id="formListaTareas" name="formListaTareas" method="post" modelAttribute="estudioSolicitudCorreccionVO" action="seguimiento/estudioCorreccion/consulta.do">
		<fieldset>
			<table style="width: 900px" align="center" class="tablaverde2" >
				<thead>
					<tr>
						<td align="center" colspan="4">Consulta de Folios</td>
					</tr>
				</thead>
				<tbody>
				
				<tr class="par" valign="top">
					<td width="80px">
						<label id="labelregPatron" for="inRegPatron" class="etiqueta2">
							Registro Patronal :
						</label>
					</td>
					<td width="80px">										
						<form:input id="inRegPatron" path="regPatronal" maxlength="10" onkeyup="lTrimJS(); validaCampo('noCaracteresEspeciales','inRegPatron','formListaTareas')" onkeypress="busquedaPorRegPatronalEjercicio()" 
							onmousedown="busquedaPorRegPatronalEjercicio()"/>																			
					</td>
					<td width="80px">										
						<label id="labelregPatron" for="inRegPatron" class="etiqueta2">
							Ejercicio :
						</label>															
					</td>
					<td width="80px">	
					<form:select path="ejercicioAnio"  id="ejercSolCorreccion" onchange="busquedaPorRegPatronalEjercicio()" >
		  			</form:select> 									
					</td>
				</tr>
				<tr class="par" valign="top">
					<td width="120px">
						<label id="labelFolioCor" for="inFolioCor" class="etiqueta2">
							Folio Correcci&oacute;n :
						</label>	
					</td>
					<td width="80px">				
						<form:input id="inFolioCor" path="folioCorr" maxlength="18"  onchange="recuperaEjercicios()" onkeyup="lTrimJS(); validaCampo('noCaracteresEspeciales','inFolioCor','formListaTareas')" onkeypress="busquedaPorFolioCorreccion()" onmousedown="busquedaPorFolioCorreccion()"/>																			
					</td>
					<td width="80px">										
					</td>
					<td width="80px">										
					</td>
				</tr>
				<tr class="par" valign="top">
					<td width="80px">
						<label id="labelEstado" for="inEstado" class="etiqueta2">
							Estado :
						</label>
					</td>
					<td width="80px" colspan="3">
						<%-- 	<form:select path="" id="cbxEstadoSeguimientoCorreccion"></form:select> --%>		
							<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.model.CrcStatus" 
																 idHtml="idEstadoSel"																						 
																 idHtmlContenedor="formListaTareas"  onchange="busquedaPorEstadoFechaStatus()"/>																	
					</td>
					
				</tr>
				<br>
				<tr class="par" valign="top">
					<td width="80px" colspan="2">
						<label id="labelFechaIni" for="inFechaIni" class="etiqueta2">
							Fecha de Estatus :
						</label>
						<form:input id="inFechaIni" path="fechaPresentaIni" maxlength="12" onchange="jsValidaFecFinal();jsValidaFechasLimite();busquedaPorEstadoFechaStatus();"  readonly="true"/>																			
						
						<label id="labelFechaFin" for="inFechaFin" class="etiqueta2">
							a :
						</label>									
						<form:input id="inFechaFin" path="fechaPresentaFin" maxlength="12" onchange="jsValidaFecFinal();jsValidaFechasLimite();busquedaPorEstadoFechaStatus();"  readonly="true"/>
						<div id="labelValidaFecha"></div>																			
					</td>
					
					<td width="80px">										
					</td>
					<td width="80px">										
					</td>
				</tr>
				<tr class="par">
					<td align="center" colspan="4">
						<a onclick="javascript:buscar();" href="#">
							<span class="boton">Buscar</span>
						</a>						
						<a onclick="javascript:limpiaFormTareas();" id="btnLimpiar" href="#">
							<span class="boton">Limpiar</span>
						</a>
					</td>
				</tr>
				<tr class="impar">
				<td align="center" colspan="4">
				&nbsp;
				</td>
				</tr>
				</tbody>
			</table>
		</fieldset>
	</form:form>
	
	<input type="hidden" name="accessTabs" id="accessTabs" value="">
	<input type="hidden" name="forbidenTabs" id="forbidenTabs" value="">
	
	<table id="dtListaTareas" style="width: 900px">
		<thead>
		</thead>
		<tbody>
		</tbody>
	</table>
	<br>
	<div id="btnSeguimiento" align="center" style="">
		<a onclick="javascript:seguimiento();" href="#">
			<span class="boton">Seguimiento</span>
		</a>
	</div>
	<br>
</div>

<div id="avisoFoltros">
		<jsp:include page="avisoDobleFiltro.jsp" />
</div>

<div id="detalleSeguimiento">
		<jsp:include page="/WEB-INF/views/controlGestion/seguimiento/seguimientoCorreccionMain.jsp" />
</div>
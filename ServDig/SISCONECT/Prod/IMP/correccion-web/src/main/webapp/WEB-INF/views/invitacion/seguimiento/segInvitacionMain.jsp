<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/tabControler.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/mensajeGenerico.js"></script>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/cedulasStyle.css">

<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/invitacion/seguimiento/seguimientoInvitacion.js"></script>
<script src="/correccion-web/resources/js/delta/limpiaFormularios.js" type="text/javascript"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<link href="/correccion-web/resources/estilos/estilo.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/correcion.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css">
<link href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css" rel="stylesheet">


<div id="cuerpoGeneral">
<br>
<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a>Consulta de Invitaci&oacute;n</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>

<div id="gralSegInvitacion" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<form:form id="formSegInvitacionMain" name="formSegInvitacionMain" method="post" modelAttribute="invitacionSeguimientoVO" action="seguimiento/seginvitacion/consultaInvi.do">
		<fieldset>
			<table style="width: 900px" align="center" class="tablaverde2">
				<tbody>
					<tr valign="top" class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par" valign="top">
						<td class="etiqueta2">
							<label id="lbTipoCorrInvi" class="etiquetaError">
								* 
							</label>
							Tipo de correcci&oacute;n
						</td>
						<td>
							<form:select path="idTipoCorr" id="tiposCorr" onchange="$('form#formInvitacion #lbTipoInvitacion').html('');">
								<option value="0">--Seleccione Por favor--</option>								
							</form:select>
							<div id="reqTipoCorrSITAB" style="display: none;">
									<label class="etiquetaError">El tipo de correcci&oacute;n es requerido</label>
							</div>
						</td>
					</tr>
					<tr class="par" valign="top">
						<td class="etiqueta2">
							<label class="etiquetaError">
								*  
							</label>
							Fecha de emisi&oacute;n de
						</td>
						<td class="etiqueta2">
							<form:input path="fechaEmisionIni" id="inFechaEmisionIniInv" maxlength="12" onchange="jsValidaFecFinalInv(); jsValidaFechasLimiteInv();"/>
						
							<label>
								<span class="required">*</span> a 
							</label>
						
							<form:input path="fechaEmisionFin" id="inFechaEmisionFinInv" maxlength="12" onchange="jsValidaFecFinalInv(); jsValidaFechasLimiteInv();"/>
							<div id="errorPeriodoFechasAnioSITAB" style="display: none;">													
									<label class="etiquetaError">El rango de fechas permitido es maximo un año</label>									
							</div>
							<div id="errorPeriodoFechasSITAB" style="display: none;">													
									<label class="etiquetaError">La fecha final no puede ser menor a la fecha inicio </label>									
							</div>
							<div id="reqPeriodoFechasSITAB" style="display: none;">
									<label class="etiquetaError">El periodo de fechas inicio y fin es requerido</label>
							</div>
						</td>
					</tr>
					<tr class="par" valign="top">
						<td class="etiqueta2">
							<label>
								Folio invitaci&oacute;n 
							</label>
						</td>
						<td>
							<form:input path="folioInvitacion" id="inFolioInv" onkeyup="validaCampo('noCaracteresEspeciales','inFolioInv','formSegInvitacionMain');jsLimpiaFiltrosRequeridosSITAB();" maxlength="18" size="20"/>
						</td>						
					</tr>
					<tr valign="top" class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
						<td align="center" colspan="4">
							<a onclick="javascript:validaSegInvitacion();" href="#">
								<span class="boton">Buscar</span>
							</a>
						</td>
					</tr>
					<tr valign="top" class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr valign="top" class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
	</form:form>	
	<br>
	<table id="dtListaInvitaciones" style="width: 1000px">
		<thead>
		</thead>
		<tbody>
		</tbody>
	</table>
	<br>
	<div id="divBtnSegInvi" align="center" style="">
		<a onclick="javascript:seguimientoInvitacion();" href="#">
			<span class="boton">Seguimiento</span>
		</a>
	</div>
	<br>
	<input type="hidden" name="accessTabs" id="accessTabs" value="">
	<input type="hidden" name="forbidenTabs" id="forbidenTabs" value="">
</div>
</div>

<div id="detalleSeguimientoInvitacion">
		<jsp:include page="detalleSegInvitacion.jsp" />
</div>
<div id="detalleSeguimientoInvitacion">
		<jsp:include page="../../genericos/mensajeGenerico.jsp" />
</div>


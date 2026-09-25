<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>


<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/invitacion/seguimiento/detalleSegInvitacion.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/invitacion/seguimiento/seguimientoInvitacionTAB.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/invitacion/seguimiento/cancelaSeguimientoInvitacionTAB.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/invitacion/seguimiento/derSubdelSeguimientoInvitacionTAB.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/invitacion/seguimiento/derFiscalSeguimientoInvitacionTAB.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/invitacion/seguimiento/autAviSeguimientoInvitacionTAB.js"></script>


<script src="/correccion-web/resources/js/delta/limpiaFormularios.js" type="text/javascript"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<link href="/correccion-web/resources/estilos/estilo.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/correcion.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css">
<link href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css" rel="stylesheet">

<div id="dgGenDetalleSegInvitacion"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<form:form id="formDetalleSegInvitacion" name="formDetalleSegInvitacion" method="get" modelAttribute="crtInvitacion" action="/seguimiento/seginvitacion/detalleInvitacion.do">
	<fieldset>
		<input type="hidden" id="idDetalleInvitacion">
		<input type="hidden" id="hidFechOfiDet">	
		<table style="width: 1000px" align="center" class="tablaverde2">
			<thead>
				<tr>
					<td align="center" colspan="6">Informaci&oacute;n de la Invitaci&oacute;n</td>
				</tr>
			</thead>
			<tbody>
				<tr class="par" valign="top">
					<td>
						<label id="lbFolInvi" class="etiqueta2"> 
							Folio de Correcci&oacute;n
						</label>						
					</td>
					<td>
						<label id="lbFolInviDet"> 
							
						</label>											
					</td>
					<td>
						<label id="lbFechOfi" class="etiqueta2">
							Fecha de Emisi&oacute;n del Oficio de Invitaci&oacute;n
						</label>
					</td>
					<td>
						<label id="lbFechOfiDet">
							
						</label>
					</td>
					<td>
						<label id="ldNumOfiInvi" class="etiqueta2">
							Numero de Oficio Invitaci&oacute;n
						</label>
					</td>
					<td>
						<label id="ldNumOfiInviDet">
							
						</label>
					</td>
				</tr>
				<tr class="par" valign="top">
					<td>
						<label id="lbPeriodoInvi" class="etiqueta2"> 
							Periodo de Invitaci&oacute;n  Del
						</label>
					</td>
					<td>
						<label id="lbPeriodoInviIni"> 
							
						</label>
					</td>
					<td>
						<label id="lbPeriodoInviAl" class="etiqueta2">
							Al
						</label>
					</td>
					<td>
						<label id="lbPeriodoInviFin">
							
						</label>
					</td>
					<td colspan="2">
					</td>					
				</tr>
			</tbody>			
		</table>
		<br>
		<table style="width: 1000px" align="center" class="tablaverde2">
			<thead>
				<tr>
					<td align="center" colspan="4">
						<label id="domInviVariable">
							
						</label>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr class="par" valign="top">
					<td>
						<label id="lbRegPatInvi" class="etiqueta2">
							Registro Patronal
						</label>
					</td>
					<td>
						<label id="lbRegPatInviDet">
							
						</label>
					</td>
					<td>
						<label id="lbRazSocialInvi" class="etiqueta2">
							Nombre &oacute; Raz&oacute;n Social
						</label>
					</td>
					<td>
						<label id="lbRazSocialInviDet">
							
						</label>
					</td>
				</tr>
				<tr class="par" valign="top">
					<td>
						<label id="lbDomInvi" class="etiqueta2">
							Domicilio
						</label>
					</td>
					<td colspan="3">
						<label id="lbDomInviDet">
							
						</label>
					</td>
				</tr>
				<tr class="par" valign="top">
					<td>
						<label id="lbDomObraDesc" class="etiqueta2">
							Domicilio Obra
						</label>
					</td>
					<td colspan="3">
						<label id="lbDomObra">
							
						</label>
					</td>
				</tr>
			</tbody>
		</table>
		<br>
	<div id="divTablaListaDomicilios">
		<table id="dtListaDomicilios" style="width: 1000px">
			<thead>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	</fieldset>	
	</form:form>
	<br>
	<table style="width: 1000px" align="center" >
		<tr	>
			<td colspan="4" align="left">			
				<ul class="tabs">
					<li class="etiqueta2" id="seguimientoInvitacionTAB_segInviLI"><a href="#seguimientoInvitacionTAB_segInvi" id="seguimientoInvitacionTAB_segInviLink">Seguimiento</a></li>
					<li class="etiqueta2" id="cancelaSeguimientoInvitacionTAB_segInviLI" ><a href="#cancelaSeguimientoInvitacionTAB_segInvi" id="cancelaSeguimientoInvitacionTAB_segInviLink">Cancelar</a></li>
					<li class="etiqueta4" id="derivaSubSeguimientoInvitacionTAB_segInviLI"><a href="#derivaSubSeguimientoInvitacionTAB_segInvi" id="derivaSubSeguimientoInvitacionTAB_segInviLink">Derivar a Otra Subdeleg</a></li>
					<li class="etiqueta2" id="derivaFisSeguimientoInvitacionTAB_segInviLI"><a href="#derivaFisSeguimientoInvitacionTAB_segInvi" id="derivaFisSeguimientoInvitacionTAB_segInviLink">Derivar a Fiscalizaci&oacute;n</a></li>
					<li class="etiqueta2" id="autAviSeguimientoInvitacionTAB_segInviLI"><a href="#autAviSeguimientoInvitacionTAB_segInvi" id="autAviSeguimientoInvitacionTAB_segInviLink" >Aut. Avi. Dict.</a></li>							
				</ul>				
			</td>
		</tr>
		<tr>
			<td>
				<div id="seguimientoInvitacionTAB_segInvi" class="tab_content" ><jsp:include page="seguimientoInvitacionTAB.jsp" /></div>
				<div id="cancelaSeguimientoInvitacionTAB_segInvi" class="tab_content"><jsp:include page="/WEB-INF/views/invitacion/seguimiento/cancelaSeguimientoInvitacionTAB.jsp" /></div>
				<div id="derivaSubSeguimientoInvitacionTAB_segInvi" class="tab_content" ><jsp:include page="/WEB-INF/views/invitacion/seguimiento/derSubdelSeguimientoInvitacionTAB.jsp" /></div>							
				<div id="derivaFisSeguimientoInvitacionTAB_segInvi" class="tab_content"><jsp:include page="/WEB-INF/views/invitacion/seguimiento/derFiscalSeguimientoInvitacionTAB.jsp" /></div>
				<div id="autAviSeguimientoInvitacionTAB_segInvi" class="tab_content"><jsp:include page="/WEB-INF/views/invitacion/seguimiento/autAviSeguimientoInvitacionTAB.jsp" /></div>
			</td>
		</tr>
		
	</table>
	<br>	
</div>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<div id="dgReabrirFolioSITAB"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<form:form id="formReabrirFolioSITAB" name="formReabrirFolioSITAB" method="post" modelAttribute="crtInvitacion" action="">
	<fieldset>
		<input type="hidden" id="idDetalleInvitacionReFolioSITAB">
			
		<table style="width: 900px" align="center" class="tablaverde2">
			<thead>
				<tr>
					<td align="center" colspan="4">Datos de la reapertura</td>
				</tr>
			</thead>
			<tbody>
				<tr class="par" valign="top">
					<td>
						<label id="lbFolInvi" class="etiqueta2"> 
							<span class="required">*</span> Referencia
						</label>						
					</td>
					<td>
						<form:input path="" id="referenciaReaFolioSITAB"/>	
						<div id="reqRefenciaReaFolioSITAB">
							<label class="etiquetaError">La referencia es requerida</label>
						</div>										
					</td>
					<td>
						<label id="lbFechOfi" class="etiqueta2">
							<span class="required">*</span> Fecha de reapertura
						</label>
					</td>
					<td>
						<form:input path="" id="fechaReaFolioSITAB"/>	
						<div id="reqFechaReaFolioSITAB">
							<label class="etiquetaError">La fecha de reapertura es requerida</label>
						</div>					
					</td>					
				</tr>
				<tr class="par" valign="top">
					<td align="left" colspan="4">
						<label id="lbPeriodoInvi" class="etiqueta2"> 
							Observaciones
						</label>
					</td>									
				</tr>
				<tr class="par" valign="top">
					<td align="left" colspan="4">
						<form:textarea path="" rows="5" cols="100"/>
					</td>									
				</tr>
			</tbody>			
		</table>
		<br>
		<div id="divBtnSegInvi" align="center" style="">
		<a onclick="javascript:seguimientoInvitacion();" href="#">
			<span class="boton">Confirmar</span>
		</a>
	</div>
	</fieldset>	
	</form:form>
</div>
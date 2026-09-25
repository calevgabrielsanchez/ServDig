<%@ include file="../general/taglibs.jsp"%>

<div id="dgDetalleRegistroPatronal" style="display: none;">
	<br>
	<b>Clasificaci&oacute;n</b>
	<br>
	<table style="width: 100% !important;">
		<tr class="fielsetgris" style="height: 50px !important;">
			<td style="width: 150px" valign="middle" align="center"><span class="etiqueta"><spring:message code="label.giro"/></span></td>
			<td style="width: 200px" valign="middle" align="center"><span class="etiqueta"><spring:message code="label.rp.clave.fraccion"/></span></td>
			<td style="width: 200px" valign="middle" align="center"><span class="etiqueta"><spring:message code="label.rp.division"/></span></td>
			<td style="width: 200px" valign="middle" align="center"><span class="etiqueta"><spring:message code="label.rp.grupo"/></span></td>
			<td valign="middle" align="center"><span class="etiqueta"><spring:message code="label.rp.fraccion"/></span></td>
		</tr>
		<tr>
			<td align="center">
				<span class="dato" id="desGiro">
					
				</span>
			</td>
			<td align="center">
				<span class="dato" id="idFraccionAct">
					
				</span>
			</td>
			<td align="center">
				<span class="dato" id="cvedivisionAct">
					
				</span>
			</td>
			<td align="center">
				<span class="dato" id="cvegrupoAct">
					
				</span>
			</td>
			<td align="center">
				<span class="dato" id="cvefraccionAct">
					
				</span>
			</td>
		</tr>
	</table>
	<br>
	<table>
		<tr style="height: 50px" valign="middle">
			<td class="label_patrones" style="width: 220px !important;" align="center"><spring:message code="label.domicilio.fiscal"/></td>
			<td class="label_patrones_data">
				<span id="textoDomicilioCentroTrabajo"></span>
				<!-- <span id="textoCalle"></span> #<span id="textoNumero"></span><span id="textoNumeroAlf"></span>, 
				<spring:message code="label.interior" /> <span id="textoNumeroInterior"></span><span id="textoNumeroInteriorAlf"></span>, 
				<spring:message code="label.colonia" /> <span id="textoColonia"></span>, 
				<spring:message code="label.codigo.postal.abreviado" /> <span id="textoCodigoPostal"></span>.
				 -->
			</td>
		</tr>
	</table>
	<br>
</div>

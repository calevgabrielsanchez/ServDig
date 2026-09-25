<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/cambioClinicaMas/cambioClinicaMas.js" htmlEscape="true" />"></script>	
<div>
	<br><br><br>
	<fieldset style="width: 977px" class="titulo">		
		<legend><strong><spring:message code="label.cambioClinicaMas"/></strong></legend>	
	 	<form:form  id="cambioClinicaMasForm" action="#" >
	 	 	<fieldset style="width: 950px" class="titulo">		
				<legend><strong>1. Seleccione la UMF origen y la UMF destino por favor</strong></legend>
				<table>
					<tr>
						<td>
							<strong>UMF origen:</strong>
						</td>
						<td>
							<select id="umfSelectOrigen" name="idUmfOrigen" style="width: 300px">
							</select>
						</td>
						<td align="right" width="200px">
							<strong>Umf Destino: </strong>
						</td>
						<td>
							<select id="umfSelect" name="idUmf" style="width: 300px">
							</select>
						</td>
					</tr>
				</table>
		 	</fieldset>
		
		 	<fieldset style="width: 950px; display:none;" class="titulo" id="asentamientosField">		
				<legend><strong>2. Seleccione las colonias a mover de la UMF origen a la destino</strong></legend>	
				<table id="listaAsentamientosTable" width="100%">
					<tr>
						<td>CargandoDatos...</td>
					</tr>
				</table>
			</fieldset>
			 <br>
			 <br>
		 	<div align="center" >
		 		<button type="button" id="buttonCambioClinicaMas" class="mboton"><spring:message code="boton.aceptar"/></button>	
				<button id="cancelarCambioClinicaMasivo" type="button"  class="mboton"><spring:message code="button.regresar"/></button>
			</div>	
	 	</form:form>
	</fieldset>
</div>
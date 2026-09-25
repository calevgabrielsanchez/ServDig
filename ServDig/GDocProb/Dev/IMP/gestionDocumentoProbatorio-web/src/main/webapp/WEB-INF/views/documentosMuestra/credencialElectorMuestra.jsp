<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

	var context_path= "<%=request.getContextPath()%>";
</script>
<div class="form-comment">
<form id="formdocument" >
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>Credencial elector</strong></legend>
		<center>
		<table>
		
		<tr >
				<td align="left">Folio:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.folio}
				</td>
			</tr>
			<tr >
				<td align="left">A&ntilde;o de registro:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.anioRegistro}
				</td>
			</tr>
			
			<tr >
				<td align="left">Clave de elector:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.claveElector}
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Entidad federativa :</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.localidad.municipio.entidadFederativa.nombre}
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Municipio:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.localidad.municipio.nombre}
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Localidad:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.localidad.nombre}
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			
			
		
			<tr >
				<td align="left">A&ntilde;o de Emisi&oacute;n:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.emision}
				</td>
			</tr>
			
						
			<tr >
				<td align="left">C&oacute;digo de seguridad:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.codigoSeguridad}
				</td>
			</tr>
				<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
									
		</table>
		</center>
	</fieldset>
	
</form>


</div>		
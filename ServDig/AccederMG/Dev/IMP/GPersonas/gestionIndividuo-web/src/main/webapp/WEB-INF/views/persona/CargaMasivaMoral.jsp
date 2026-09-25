<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/cargaMasivaPersonaMoral.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		
			<div class="separadorseccion">Registro Masivo de Personas Morales</div>
			<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
				
			<form:form modelAttribute="uploadFileForm" id="uploadFileForm" action="${contextpath}/persona/moral/registro-masivo/cargarArchivo" method="post" enctype="multipart/form-data"  >
				
				<fieldset>
					<legend>
						<strong>&nbsp;Ubicaci&oacute;n de Fuente de Informaci&oacute;n&nbsp;</strong>
					</legend>
					
					<form:label path="fileData" cssClass="wide">Ruta*</form:label>
					<input type="file" name="fileData" id="fileData" />
					<%-- <form:errors path="fileData" cssClass="error" /> --%>	
				</fieldset>
				
				<div style="float: right;">
					<input type="submit" value="Cargar archivo" id="_cargarArchivo" class="mboton" />
				</div>		
				<br /><br /><br /><br />
			
			</form:form>
			
			<%-- RESUMEN DEL DATA TABLE --%>
			<c:if test="${registrosArchivo > 0}" >
				<div class="separadorseccion">Resultado de la Carga Masiva</div>
				
				<c:choose>
					<c:when test="${folioSolicitud != 0}">
						<div style="color: black; font-size: 1em; font-weight: bold; width: 45%; float: left; border: 1px;">
							Se ha generado una nueva solicitud con el folio <font color="blue"><c:out value="${folioSolicitud}" /></font>
						</div>
					</c:when>
					<c:otherwise>
						<div style="color: red; font-size: 1em; font-weight: bold; width: 45%; float: left; border: 1px;">
							No se ha generado la solicitud debido a que no existe al menos un registro con estatus 'Validado por SAT, 'No validado' (este &uacute;ltimo s&oacute;lo en caso de tener DATOS B&aacute;SICOS)
						</div>
					</c:otherwise>
				</c:choose>
				
				<div style="color: #157164; font-size: 1em; font-weight: bold; width: 40%; float: left; border: 1px;">
					Registros le&iacute;dos:<br /> 
					Registros procesados:<br />
					Registros ya existentes:<br /> 
					Registros NO validados por una entidad externa:<br />
					Registros con errores:<br />
					Registros duplicados:<br />
				</div>
	
				<div style="color: black; font-size: 1em; font-weight: bold; width: 10%; float: left;">
											<c:out value="${registrosArchivo}" /><br />
					<font color="#157164">	<c:out value="${registrosAltaIMSS}" /></font><br />
											<c:out value="${registrosExistentesIMSS}" /><br />
					<font color="red">		<c:out value="${registrosNoValidados}" /></font><br />
					<font color="red">		<c:out value="${registrosErrores}" /></font><br />
					<font color="red">		<c:out value="${registrosRepetidos}" /></font><br />
				</div>
				<br /><br /><br /><br /><br /><br />
			</c:if>
			<%-- --%>
			
			<%-- IMPLEMENTACION CON DATA TABLE DE JQUERY UI PARA LEER EL ARCHIVO DE CARGA MASIVA DE PERSONAS --%>
				
			<form:form modelAttribute="personaMoral" id="personaMoral">
				<form:errors path="*" cssClass="error" />
				
				<div>
					<!-- Primera implementacion del datateibol (sin paginacion) -->
					<!-- <table style="width: 100%"  id="tablaPersonasMorales"></table> -->
					<!-- Fin primera implementacion del datateibol -->
					
					<!-- Segunda implementacion del datateibol (con paginacion) -->
					<table id="tablaPersonasMorales">
						<thead>
							<tr>
								<th>L&iacute;nea</th>
								<th>Estatus</th>
								<th>Alta en IMSS</th>
								<th>Identificador</th>
								<th>RFC</th>
								<th>NRP</th>
								<th>Raz&oacute;n Social</th>
								<th>Tipo de Sociedad</th>
								<th>Acta Constitutiva</th>
								<th>Fecha de Creaci&oacute;n</th>
							</tr>
						</thead>
						
						<tbody>
							<%-- <c:if test="${fn:length(personasMorales) gt 0}"> --%>
								
							<c:forEach items="${personasMorales}" var="personaMoral" varStatus="indice">
								<tr>
									<td>${personaMoral.numeroLineaArchivo}</td>
									<td>${personaMoral.subEstadosFormateados}</td>
									<td>${personaMoral.altaEnImss}</td>
									<td>${personaMoral.idPersona}</td>
									<td>${personaMoral.rfc}</td>
									<td>${personaMoral.nrp}</td>
									<td>${personaMoral.razonSocial}</td>
									<td>${personaMoral.tipoSociedad.descripcion}</td>
									<td>${personaMoral.actaConstitutiva}</td>
									<td>${personaMoral.fechaCreacionFormateada}</td>
								</tr>
							</c:forEach>
														
						</tbody>
					</table>
					<!-- Fin segunda implementacion del datateibol -->
				</div>
			</form:form>
	
		</div>
	</div>
</div>

<!-- DIV de query para poder desplegar un cuadro de dialogo tipo alert -->
<div id="dgError" title="Falta ingresar archivo de carga" >
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		Favor de ingresar la ruta del archivo a cargar
	</p>
</div>
<!--  -->

<script type="text/javascript">

</script>
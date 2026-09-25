<%@ include file="../general/taglibs.jsp"%>
<c:choose>
<c:when test="${empty errores}">
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/tramites.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form >
<fieldset>
<legend><strong><spring:message code="label.tituloSolicitud"/></strong></legend>
<fieldset>
<table style="border-style : solid; border-color: red">
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td style="width: 200px"></td>
		<td></td>
		<td></td>
	</tr>
			
	<tr>
		<td colspan="2" style="width: 200px">
			<spring:message code="label.folio"/>:
		</td>
		<td colspan="2">
			<input type="text" readonly="readonly" value="${solicitud.noFolioSolicitud}" style="width: 120px" />
		</td>
		<td align="right" style="width: 200px">
			<spring:message code="label.fechaAlta"/>:
		</td>
		<td align="right" >
			<input type="text" readonly="readonly"  style="width: 200px;text-align: center;" value="<fmt:formatDate  pattern="dd/MM/yyyy"  value="${solicitud.fechaSolicitud}"/>"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<spring:message code="label.solicitante"/>:
		</td>
		<td colspan="4">
			<input type="text" readonly="readonly" value="${solicitud.solicitante.usuario}" style="width: 600px" />
		</td>
	</tr>
 	<tr> 
		<td colspan="2" style="width: 200px">
			<spring:message code="label.estado"/>:
		</td> 
		<td colspan="2"> 
			<input type="text" readonly="readonly" value="${solicitud.estadoSolicitud.descripcion}" style="width: 120px" /> 
		</td> 
 		<td align="right" > 
 			<spring:message code="label.razonCancelacion"/>:
 		</td> 
 		<td> 
 			<input type="text" readonly="readonly" value="${solicitud.razonCancelacion.descripcion}" style="width: 200px;" /> 
		</td> 
	</tr> 
	<tr>
		<td  colspan="2"> 
			<spring:message code="label.observaciones"/>:
		</td>
		<td colspan="4">  
			<input type="text" readonly="readonly" value="${solicitud.observacion}" style="width: 600px"/>
		</td> 
	</tr>	
	
	
</table>
</fieldset>
<br>
<fieldset>
<legend><strong>Datos del asegurado o pensionado</strong></legend>
<table>
	<tr>
		<td style="width: 200px">
			<spring:message code="label.nombreAsegurado"/>:
		</td>
		<td colspan="4">
			<input type="text" readonly="readonly" value="${asegurado.nombre} ${asegurado.primerApellido} ${asegurado.segundoApellido}" style="width: 590px" />
		</td>
	</tr>
	<tr>
		<td style="width: 200px">
			<spring:message code="label.nss"/>:
		</td>
		<td>
			<input type="text" readonly="readonly" value="${asegurado.nssStr}" style="width: 120px" />
		</td>
		<c:if test="${AsignacionNSS.pensionado}">
			<td align="right">Tipo pension: </td>
			<td colspan="2">
				<input type="text" readonly="readonly" value="${AsignacionNSS.tipoPension}" style="width: 350px"/>
			</td>
		</c:if>
		<c:if test="${!AsignacionNSS.pensionado}">
			<td colspan="3"></td>
		</c:if>
	</tr>	
	<c:if test="${patronIMSS}">
		<tr>
			<td align="center" colspan="5">
				
				<strong>CCT 74 IMSS</strong>
				<br>
			</td>
		</tr>
	</c:if>
</table>

</fieldset>
<br>
<!--<fieldset>
	<legend><strong><spring:message code="titulo.cita"/></strong></legend>
	<center>
	<table>
		<tr>
			<th colspan="6" align="center"><spring:message code="label.datosCita"/></th>
		</tr>
		<tr>
			<td colspan="6"><br></td>
		</tr>
		<tr>
			<td ><spring:message code="label.umf.delegacion"/>:</td>
			<td>
				<input type="text" readonly="readonly" value="${solicitud.citaSolicitud.umf.subdelegacion.delegacion.descripcion}" style="width: 200px" />
			</td>
			<td ><spring:message code="label.umf"/>:</td>
			<td>
				<input type="text" readonly="readonly" value="${solicitud.citaSolicitud.umf.descripcion} - ${solicitud.citaSolicitud.umf.nombreCorto}" style="width: 200px" />
			</td>
			<td colspan="2"></td>
		</tr>
		<tr>
		
		
			<td ><spring:message code="label.fechaCita"/>:</td>
			<td>
				<input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy"  value="${solicitud.fechaCita}"/>" style="width: 200px" />
			</td>
			<td ><spring:message code="label.umf.hora"/>:</td>
			<td>
				<input type="text" readonly="readonly" value="${solicitud.citaSolicitud.turno.horaInicioTurno} -  ${solicitud.citaSolicitud.turno.horaFinTurno}" style="width: 200px" />
			</td>
			<td ><spring:message code="label.umfTurno"/>:</td>
			<td>
				<input type="text" readonly="readonly" value="${solicitud.citaSolicitud.turno.descripcion}" style="width: 200px" />
			</td>
		</tr>
	</table>
	</center>
</fieldset>
--><br>
<center>
	<strong><spring:message code="label.tramitesSolicitud"/></strong>
</center>
<br>
		<jsp:include page="../tramite/detalleTramite.jsp"></jsp:include>
</fieldset>
</form>
</div>
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<p class="ui-helper-reset ui-state-error-text">
				<div class="ui-icon ui-icon-alert"></div><spring:message code="${errores}"/>
			
		</div>
	</div>
</c:otherwise>

</c:choose>	
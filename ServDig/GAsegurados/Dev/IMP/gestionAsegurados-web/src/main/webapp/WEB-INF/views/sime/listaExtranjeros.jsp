<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoRegistroSIMEEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoNssEnum"%>

<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="mx.gob.imss.ctirss.delta.model.asegurado.Empleado" %>


<c:set var="registroExitoso" value="<%=EstadoRegistroSIMEEnum.EXITO.getClave()%>" scope="page"></c:set>
<c:set var="registroError" value="<%=EstadoRegistroSIMEEnum.ERROR.getClave()%>" scope="page"></c:set>
<c:set var="registroPendiente" value="<%=EstadoRegistroSIMEEnum.PENDIENTE.getClave()%>" scope="page"></c:set>
<c:set var="nssAsignado" value="<%=EstadoNssEnum.ASIGNADO.getClave()%>" scope="page"></c:set>
<c:set var="nssRecuperado" value="<%=EstadoNssEnum.RECUPERADO.getClave()%>" scope="page"></c:set>

<c:set var="numRegPorProcesar" value="${fn:length(EXTRANJEROS_SIME) }" scope="page" />
<c:set var="numRegProcesados" value="${fn:length(EXTRANJEROS_PROCESADOS_SIME) }" scope="page" />
<c:set var="totalRegistros" value="${numRegPorProcesar + numRegProcesados}" scope="page" />

<%
	int nssAsignados = 0;
	int nssRecuperados = 0;

	Map<Integer, Empleado> registros = (Map<Integer, Empleado>) session
			.getAttribute("EXTRANJEROS_PROCESADOS_SIME");

	if (registros != null) {
		Empleado empleado = null;

		for (Entry<Integer, Empleado> entry : registros.entrySet()) {
			empleado = entry.getValue();

			if (empleado.getEstadoNss().getClave() == EstadoNssEnum.ASIGNADO
					.getClave()) {
				nssAsignados++;
			} else if (empleado.getEstadoNss().getClave() == EstadoNssEnum.RECUPERADO
					.getClave()) {
				nssRecuperados++;
			}
		}
	}
%>

<script type="text/javascript">
	var oTable;

	$(function() {
		oTable = $('table#tblExtranjeros').dataTable({
			"sPaginationType" : "bootstrap",
			"aoColumnDefs" : [ 
				{ "bVisible" : false, "aTargets" : [0,5,6,7] },
				{ "bSortable": false, "aTargets" : [9]}
			],
			"aaSorting": [[1,'asc']]
		});
		
		$('table#tblExtranjerosProcesados').dataTable({
			"sPaginationType" : "bootstrap",
			"aoColumnDefs" : [ 
				{ "bVisible" : false, "aTargets" : [0] }
			],
			"aaSorting": [[1,'asc']]
		});
	});

	function getSelected(button) {
		var tr = $(button).closest("tr")[0];
		var data = oTable.fnGetData(tr);

		limpiarFormulario('#fisicaExtranjeroForm');

		$('#llaveRegistro').val(data[0]);
		$("#curpExtranjero").val(data[1]);
		$("#nombreExtranjero").val(data[2]);
		$("#primerApellidoExtranjero").val(data[3]);
		$("#segundoApellidoExtranjero").val(data[4]);
		$("#sexoExtranjero").val(data[5]);
		$("#fechaNacExtranjero").val(data[6]);
		$("#lugarNacExtranjero").val(data[7]);

		$('#fisicaExtranjeroForm').submit();

	}
</script>

<div class="contenedor">
	<div class="row">
		<div class="col-md-5">
			<fieldset>
				<legend>DATOS PATR&Oacute;N</legend>
				<form class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-5">N&uacute;mero de Registro Patronal:</label>
						<div class="form-control-static col-sm-7">
							<p>${PATRON_SIME.numeroRegistroPatronal }
								${PATRON_SIME.modalidad.numModalidad }
								${PATRON_SIME.digVerificador }
							</p>
						</div> 
					</div>
					<div class="form-group">
						<label class="control-label col-sm-5">Nombre Comercial:</label>
						<div class="form-control-static col-sm-7">
							<p>${PATRON_SIME.nombreComercial }</p>
						</div> 
					</div>
				</form>
			</fieldset>
		</div>
		<div class="col-md-7">
			<div class="well p-xs">
				<fieldset>
					<legend>DATOS PROCESO</legend>
					<form class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-3">Total registros:</label>
							<div class="form-control-static col-sm-3">
								<p>${totalRegistros }</p>
							</div>
							<label class="control-label col-sm-3">NSS asignados:</label>
							<div class="form-control-static col-sm-3">
								<p><%=nssAsignados%></p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3">Registros por procesar:</label>
							<div class="form-control-static col-sm-3">
								<p>${numRegPorProcesar }</p>
							</div>
							<label class="control-label col-sm-3">NSS recuperados:</label>
							<div class="form-control-static col-sm-3">
								<p><%=nssRecuperados%></p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3">Registros procesados:</label>
							<div class="form-control-static col-sm-3">
								<p>${numRegProcesados }</p>
							</div>
						</div>
					</form>
				</fieldset>
			</div>
		</div>
	</div>
	<br>
	<fieldset>
		<legend>REGISTROS POR PROCESAR</legend>
		<table id="tblExtranjeros" style="width: 100%;"
			class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>CURP</th>
					<th>Nombre</th>
					<th>Primer apellido</th>
					<th>Segundo apellido</th>
					<th>Sexo</th>
					<th>Fecha nacimiento</th>
					<th>Lugar nacimiento</th>
					<th>Estado</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${EXTRANJEROS_SIME}" var="extranjeroWrapper">
					<c:set var="extranjero" value="${extranjeroWrapper.value}" />
					<c:set var="edoRegistro" value="${extranjero.estadoRegistro.clave}" scope="page" />
					<tr>
						<td>${extranjeroWrapper.key}</td>
						<td>${extranjero.curp}</td>
						<td>${extranjero.nombre}</td>
						<td>${extranjero.apellidoPaterno}</td>
						<td>${extranjero.apellidoMaterno}</td>
						<td>${extranjero.sexo}</td>
						<td>
							<fmt:formatDate value="${extranjero.fecNacimiento}" pattern="dd/MM/yyyy"/>
						</td>
						<td>${extranjero.lugarNacimiento}</td>
						<td style="text-align: center; vertical-align: middle;">
							<c:choose>
								<c:when test="${edoRegistro eq registroPendiente }">
									<span class="label label-warning label-edo">${extranjero.estadoRegistro.desc}</span>
								</c:when>
								<c:when test="${edoRegistro eq registroError }">
									<span class="label label-danger label-edo">${extranjero.estadoRegistro.desc}</span>
								</c:when>
								<c:otherwise>
									<span class="label label-default label-edo">${extranjero.estadoRegistro.desc}</span>
								</c:otherwise>
							</c:choose>							
						</td>
						<td style="text-align: center;">
							<button type="button"
								onclick="getSelected(this)" class="btn btn-primary btn-sm">Procesar</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend>REGISTROS PROCESADOS</legend>
		<table id="tblExtranjerosProcesados" style="width: 100%;"
			class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>CURP</th>
					<th>Nombre</th>
					<th>Primer apellido</th>
					<th>Segundo apellido</th>
					<th>NSS</th>
					<th>Situaci&oacute;n NSS</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${EXTRANJEROS_PROCESADOS_SIME}" var="extranjeroProcesadoWrapper">
					<c:set var="extranjeroProcesado" value="${extranjeroProcesadoWrapper.value}" scope="page" />
					<c:set var="edoNss" value="${extranjeroProcesado.estadoNss.clave}" scope="page" />
					<tr>
						<td>${extranjeroProcesadoWrapper.key}</td>
						<td>${extranjeroProcesado.curp}</td>
						<td>${extranjeroProcesado.nombre}</td>
						<td>${extranjeroProcesado.apellidoPaterno}</td>
						<td>${extranjeroProcesado.apellidoMaterno}</td>
						<td>${extranjeroProcesado.nss}</td>
						<td style="text-align: center; vertical-align: middle;">
							<c:choose>
								<c:when test="${edoNss eq nssAsignado }">
									<span class="label label-success label-edo">${extranjeroProcesado.estadoNss.desc}</span>
								</c:when>
								<c:when test="${edoNss eq nssRecuperado }">
									<span class="label label-info label-edo">${extranjeroProcesado.estadoNss.desc}</span>
								</c:when>
							</c:choose>								
						</td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${numRegProcesados gt 0 }">
			<br><br><br>
			<div style="float: right;">
				<form action="${contextpath}/sime/generar/archivo-saiia" method="post" class="formNotBlock">
					<button type="submit" class="btn btn-primary">GENERAR ARCHIVO SAIIA</button>
				</form>
			</div>
		</c:if>
	</fieldset>
	
	<!-- Forma auxiliar para el envío de los datos para iniciar el trámite de Asignación -->
	<form:form action="${contextpath}/tramite/disMag/iniciar" method="post"
		modelAttribute="fisica" id="fisicaExtranjeroForm">
		<input type="hidden" name="llaveRegistro" id="llaveRegistro" />
		<form:hidden path="curp" id="curpExtranjero" />
		<form:hidden path="nombre" id="nombreExtranjero" />
		<form:hidden path="primerApellido" id="primerApellidoExtranjero" />
		<form:hidden path="segundoApellido" id="segundoApellidoExtranjero" />
		<form:hidden path="sexo.idSexo" id="sexoExtranjero" />
		<form:hidden path="fechaNacimiento" id="fechaNacExtranjero" />
		<form:hidden path="lugarNacimiento.clave" id="lugarNacExtranjero" />
	</form:form>
</div>
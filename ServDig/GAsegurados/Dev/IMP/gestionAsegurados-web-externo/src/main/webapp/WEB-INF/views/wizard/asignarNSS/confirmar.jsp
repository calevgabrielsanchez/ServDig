<%@ include file="../../general/taglibs.jsp"%>

<style>
#detalleDomicilio label {
	display: inline;
}

legend+.control-group {
	margin-top: 0px;
}

.nss-recuperado {
	margin: 0 auto;
	text-align: center;
	width: 50%;
}

form#forma fieldset {
	border-radius: 5px;
}

form#forma fieldset legend{
	font-weight: bold;
    margin-left: 10px;
    padding: 0 7px;
}
</style>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/asignacionNSS/confirmar.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};

	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
</script>
<div class="contenedor">

	<div class="contenido" style="width: 100%;">

		<form:form modelAttribute="fisica" id="forma" method="post">
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
			<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
			<c:if test="${not empty fisica.errorFormGeneral }">
				<c:choose>
					<c:when test="${not empty NSS_RECUPERADO }">
						<p style="font-size: medium; text-align: center;">Usted ya
							cuenta con el siguiente N&uacute;mero de Seguridad Social:</p>
						<div class="alert alert-success nss-recuperado">
							<h4 style="font-size: 35px;">
								<strong>${fisica.errorFormGeneral}</strong>
							</h4>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-danger">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<strong>Error: </strong>${fisica.errorFormGeneral}
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="${empty fisica.errorFormGeneral}">

				<p>
					La validaci&oacute;n de su informaci&oacute;n ha sido exitosa, para
					poder continuar con el tr&aacute;mite es necesario que este de
					acuerdo con la información presentada, que seleccione la UMF que
					dese&eacute; y que <strong>firme con su FIEL</strong> la solicitud que
					est&aacute; generando.
				</p>

				<fieldset>
					<legend>DATOS B&Aacute;SICOS</legend>
					<label class="control-label" for="curp"
						style="margin-right: 10px; display: inline;">CURP:</label>
					<form:input path="curp" id="curp" disabled="true" />
				</fieldset>
				<br>
				<fieldset>
					<legend>DOMICILIO PARTICULAR</legend>
					<div id="domParticularDiv">
						<c:set var="domicilio" value="${fisica.domicilios[0]}"/>
						<address>
							${domicilio.vialidadPrimaria.nombre}<br>
							${domicilio.numExteriorAlf} ${domicilio.numExterior1},
							${domicilio.numInteriorAlf} ${domicilio.numInterior}<br>
							${domicilio.asentamiento.nombre}<br>
							${domicilio.asentamiento.localidad.municipio.nombre}<br>
							${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
							C.P. ${domicilio.asentamiento.codigoPostal.codigoPostal}<br>
						</address>
					</div>
				</fieldset>
				
				<c:if test="${CON_FIEL == false }">
					<fieldset>
						<legend>MEDIOS CONTACTO</legend>
						<div id="mediosContactoDiv">
							<table id="tblRepresentantesResumen" style="width: 100%;"
								class="table table-striped table-bordered" cellpadding="0"
								cellspacing="0" border="0">
								<thead>
									<tr>
										<th>Descripci&oacute;n</th>
										<th>Tipo de Medio</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${fisica.mediosContacto}" var="medio"
										varStatus="indice">
										<tr>
											<td>${medio.desFormaContacto }</td>
											<td>${medio.tipoMedioContacto.descripcion }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</fieldset>
				</c:if>
				<br>
				<fieldset>
					<legend>UNIDAD MEDICO FAMILIAR</legend>
					<div id="umfContenedor" style="width: 100%;">
						<div style="text-align: center;">
							<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
						</div>
					</div>
				</fieldset>
			</c:if>
		</form:form>
		<br> <br>

	</div>

	<div class="pie">
		<div class="opciones">
			<c:if test="${empty fisica.errorFormGeneral}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="confirmarSolicitud"><i class="glyphicon glyphicon-ok"></i>Confirmar
								Solicitud</a></li>
					</ul>
				</div>
			</c:if>
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
		</div>

		<div class="controles"></div>
	</div>
</div>

<c:if test="${not empty asentamiento }">
	<!-- Forma auxiliar para consultar las UMF's -->
	<form:form modelAttribute="asentamiento" id="asentamientoForUmfForm">
		<form:hidden path="clave"/>
		<form:hidden path="nombre"/>
		<form:hidden path="localidad.clave"/>
		<form:hidden path="localidad.municipio.clave"/>
		<form:hidden path="localidad.municipio.entidadFederativa.clave"/>
		<form:hidden path="codigoPostal.codigoPostal"/>
		<form:hidden path="tipoAsentamiento.clave"/>
	</form:form>
</c:if>

<div id="dialog-mensajes" title="Mensaje del sistema">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

<form action="/gestionAsegurados-web-externo/wizard/nss/finalizar"
	id="finalizarNSSForm" method="post">
	<input type="hidden" name="idUMF" id="idUmfAsegurado"/>
	<input type="hidden" name="noEconomico" id="noEconomicoUmfAsegurado"/>
	
	<input type="hidden" name="subdelegacion.id" id="idSubdelegacionAsegurado"/>
	<input type="hidden" name="subdelegacion.clave" id="cveSubdelegacionAsegurado"/>
	
	<input type="hidden" name="subdelegacion.delegacion.id" id="idDelegacionAsegurado"/>
	<input type="hidden" name="subdelegacion.delegacion.clave" id="cveDelegacionAsegurado"/>
	
	<input type="hidden" name="subdelegacion.delegacion.ciz" id="cveCizAsegurado"/>
	
	
	
</form>
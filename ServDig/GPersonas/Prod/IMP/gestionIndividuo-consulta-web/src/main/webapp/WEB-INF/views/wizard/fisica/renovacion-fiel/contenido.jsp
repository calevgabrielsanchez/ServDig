<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>

<style>
	.text-center {
		text-align: center !important;
	}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/renovacion-fiel/contenido.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"  ></script>
	
<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};
	var cambioCurp = ${cambioCurp};
	var cambioRfc = ${cambioRfc};

	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />

			<div class="alert alert-info">
				<c:choose>
					<c:when test="${cambioCurp || cambioRfc}">
							Se detect&oacute; que se modificaron sus datos de registro. A continuaci&oacute;n se muestran las diferencias
						</c:when>
					<c:otherwise>
							Se detect&oacute; que el n&uacute;mero de serie de su firma digital fue modificado. A continuaci&oacute;n seleccione
							la opci&oacute;n
							"Guardar Tr&aacute;mite"
						</c:otherwise>
				</c:choose>
			</div>

			<div>
				<div class="titulo">
					<span>Informaci&oacute;n recuperada de la Persona F&iacute;sica</span>
					<hr class="red m-b-none">
				</div>
				
				<form:form modelAttribute="fisica" id="fisicaForm" method="post" cssClass="form-horizontal"
					action="${contextpath}/wizard/tramite/registro/usuario/guardar/solicitud">
					
					<form:hidden path="idPersona" />
					
					<c:if test="${cambioCurp || cambioRfc}">
						<div class="form-group m-b-xs">
							<label class="control-label col-sm-4 col-sm-offset-4 text-center">Datos encontrados</label>
							<label class="control-label col-sm-4 text-center">Diferencias</label>
						</div>
						<hr class="m-t-xs m-b-sm">
					</c:if>
										
					<div class="form-group">
						<form:label path="curp" cssClass="control-label col-sm-4">CURP</form:label>
						<div class="col-sm-4">
							<form:input path="curp" maxlength="18" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
							<c:if test="${fisicaN.curp ne null}">
								<input type="text" value="${fisicaN.curp}" class="form-control" readonly="readonly" disabled="disabled" />
							</c:if>
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="rfc" cssClass="control-label col-sm-4">RFC</form:label>
						<div class="col-sm-4">
							<form:input path="rfc" maxlength="13" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
							<c:if test="${fisicaN.rfc ne null}">
								<input type="text" value="${fisicaN.rfc}" class="form-control" readonly="readonly" disabled="disabled" />
							</c:if>
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="nombre" cssClass="control-label col-sm-4">Nombre(s)</form:label>
						<div class="col-sm-4">
							<form:input path="nombre" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="primerApellido" cssClass="control-label col-sm-4">Primer Apellido</form:label>
						<div class="col-sm-4">
							<form:input path="primerApellido" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="segundoApellido" cssClass="control-label col-sm-4">Segundo Apellido</form:label>
						<div class="col-sm-4">
							<form:input path="segundoApellido" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="sexo.idSexo" cssClass="control-label col-sm-4">Sexo</form:label>
						<div class="col-sm-4">
							<form:input path="sexo.descripcion" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="fechaNacimiento" cssClass="control-label col-sm-4">Fecha de Nacimiento</form:label>
						<div class="col-sm-4">
							<form:input path="fechaNacimientoFormateada" id="fechaNacimiento" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
						</div>
					</div>
					
					<div class="form-group">
						<form:label path="lugarNacimiento.clave" cssClass="control-label col-sm-4">Lugar de Nacimiento</form:label>
						<div class="col-sm-4">
							<form:input path="lugarNacimiento.nombre" readonly="true" cssClass="form-control" />
						</div>
						<div class="col-sm-4">
						</div>
					</div>
				</form:form>
			</div>
		</div>

		<form:form modelAttribute="solicitud" id="solicitudForm" method="post"
			action="${contextpath}/wizard/tramite/renovacion/fiel/guardar/solicitud">
			<input type="hidden" id="selloDigital" name="selloDigital" />
			<input type="hidden" id="secuenciaDeNotaria" name="secuenciaDeNotaria" />
			<input type="hidden" id="solicitudId" name="solicitudId" value="123122312" />
		</form:form>
	</div>

	<div class="pie row">
		<div class="opciones col-sm-6">
			<div class="btn-group dropup">
				<a href="#" class="btn btn-primary">Acciones</a>
				<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<li>
						<a id="guardaTramite">
							<i class="glyphicon glyphicon-download-alt"></i>
							Finalizar Tr&aacute;mite
						</a>
					</li>
					<li>
						<a id="cancelarTramite">
							<i class="glyphicon glyphicon-trash"></i>
							Cancelar Tr&aacute;mite
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right"></div>
		</div>

	</div>
	
	<span id="errorNegocioLabel" class="error hiddenElement"></span>


	<!-- Divs para dialogos de mensajes -->
	<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
		<p>
			<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
			¿Desea cancelar la solicitud?
		</p>
	</div>
	
	<div id="dialog-confirm" title="Mensaje">
		<p>
			<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
		</p>
	</div>

	<div id="dialog-acces" title="Mensaje">
		<p>
			<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogoAcces"></label>
		</p>
	</div>
	
	<div id="dialog-confirm-msg" title="Importante">
		<p>
			<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
			Al ingresar tu FIEL est&aacute;s aceptando que los datos obtenidos por el sistema son correctos y que te pertenecen,
			en caso de que no coincidan tus datos oprime la opci&oacute;n cancelar.
			<br>
			Para corregir tus datos tienes que acudir ante el SAT.
		</p>
	</div>
</div>
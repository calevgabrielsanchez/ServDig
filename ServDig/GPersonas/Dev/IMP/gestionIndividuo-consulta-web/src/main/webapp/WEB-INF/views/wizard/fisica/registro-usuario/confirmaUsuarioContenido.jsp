<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page
	import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/registro-usuario/confirmaContenido.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>

<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};
	var nombreCompletoLimpio = '${datosFirmaElectronica.nombreCompleto}'; 
	
	
	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : nombreCompletoLimpio,
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
</script>

<div class="contenedor col-sm-12">

	<div class="contenido row">
	
		<div class="col-sm-12">

			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			
			
			<div >
			<form:form modelAttribute="fisica" id="fisicaForm" method="post"
				action="${contextpath}/wizard/tramite/registro/usuario/guardar/solicitud" cssClass="form-horizontal">

				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />

				
					<fieldset>
					

						<div id="seccionPatr&oacute;n">
							<div class="separadorseccion">
								<strong><h4>
										&nbsp;Informaci&oacute;n recuperada de la Persona
										F&iacute;sica a registrar
										</h3></strong>
							</div>
						</div>

						<form:hidden path="idPersona" />

						<div class="form-group">
						<form:label path="curp" cssClass="control-label col-sm-2">CURP</form:label>
						<div class="col-sm-6">
						<form:input path="curp" maxlength="18" readonly="true"
							cssClass="form-control disabled" />
						</div>
						</div>
						
						<div class="form-group">
						<form:label path="rfc" cssClass="control-label col-sm-2">RFC</form:label>
						<div class="col-sm-6">
						<form:input path="rfc" maxlength="13" readonly="true"
							cssClass="form-control disabled" />
						</div>
						</div>
						
						<div class="form-group">
						<form:label path="nombre" cssClass="control-label col-sm-2">Nombre(s)</form:label>
						<div class="col-sm-6">
						<form:input path="nombre" maxlength="30" readonly="true"
							cssClass="form-control disabled" />
						</div>
						</div>
						
						
						<div class="form-group">
						<form:label path="primerApellido" cssClass="control-label col-sm-2">Primer Apellido</form:label>
						<div class="col-sm-6">
						<form:input path="primerApellido" maxlength="30" readonly="true"
							cssClass="form-control disabled" />
						</div>
						</div>
						
						<div class="form-group">
						<form:label path="segundoApellido" cssClass="control-label col-sm-2">Segundo Apellido</form:label>
						<div class="col-sm-6">
						<form:input path="segundoApellido" maxlength="30" readonly="true"
							cssClass="form-control disabled" />
						</div>
						</div>
						
						<div class="form-group">
						<form:label path="sexo.idSexo" class="control-label col-sm-2">Sexo</form:label>
						<div class="col-sm-6">
						<form:input path="sexo.descripcion" maxlength="30" readonly="true"
							cssClass="form-control disabled" />
						</div>
						</div>
						
						
						<div class="form-group">
						<form:label path="fechaNacimiento" class="control-label col-sm-2">Fecha de Nacimiento</form:label>
						<div class="col-sm-6">
						<form:input path="fechaNacimientoFormateada" id="fechaNacimiento"
							maxlength="10" readonly="true" cssClass="form-control disabled" />
						</div>
						</div>
						
						<div class="form-group">
						<form:label path="lugarNacimiento.clave" class="control-label col-sm-2">Lugar de Nacimiento</form:label>
						<div class="col-sm-6">
						<form:input path="lugarNacimiento.nombre" maxlength="60"
							readonly="true" cssClass="form-control disabled" />
						</div>
						</div>
					</fieldset>
				
		</form:form>
		</div>
				<br>
			</div>
		</div>
		
		<div>

			<div class="pie row">
				<div class="opciones col-sm-6">

					<div class="btn-group dropup">
						<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
							data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a id="guardaTramite"><i class="glyphicon glyphicon-download-alt"></i>
									Finalizar Tr&aacute;mite</a></li>
							<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i>
									Cancelar Tr&aacute;mite</a></li>
						</ul>
					</div>
				</div>
				<div class="controles col-sm-6">
				<div class="pull-right">
				
				</div>
		
				</div>

			</div>
		</div>
		<br /> <span id="errorNegocioLabel" class="error hiddenElement"></span>

		

	



		<form:form modelAttribute="solicitud" id="solicitudForm" method="post"
											action="${contextpath}/wizard/tramite/registro/usuario/guardar/solicitud" >
						<input type="hidden" id="selloDigital" name="selloDigital"/>
						<input type="hidden" id="secuenciaDeNotaria" name = "secuenciaDeNotaria"/>
						<input type="hidden" id="solicitudId" name="solicitudId" value="123122312"/>
				</form:form>

		<!-- Divs para dialogos de mensajes -->
		<div id="dialog-confirm-cancelar"
			title="Confirmar cancelaci&oacute;n de solicitud">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar
				la solicitud de registro de Usuario?
			</p>
		</div>
		<div id="dialog-confirm" title="Mensaje">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span> <label
					id="mensajeDialogo"></label>
			</p>
		</div>
		<div id="dialog-confirm-msg" title="Importante">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span> Al ingresar tu
				FIEL est&aacute;s aceptando que los datos obtenidos por el sistema
				son correctos y que te pertenecen, en caso de que no coincidan tus
				datos oprime la opci&oacute;n cancelar.<br> Para corregir tus
				datos tienes que acudir ante el SAT.

			</p>
		</div>



	</div>
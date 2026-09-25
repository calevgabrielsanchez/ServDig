<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/moral/modificacion/manual/modificacion-manual-datos.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var objCtrl = ModificacionManualDatosMoralCtrl;

	$(document).ready(function() {
		$('#limpiar').click(function() {
			$('#forma').clearForm();
		});

		$('#consultar').click(function(event) {
			fnMDMTest();
		});

		objCtrl.setOnCloseCallback(fnCallback);

	});

	var fnMDMTest = function() {

		objCtrl.init('mdmMoralDialog');

		objCtrl.datosEntrada.idPersona = $('#busquedaIdPersona').val();
		
		objCtrl.datosEntrada.indCapturaRFC  = $('#indCapturaRFC').is(':checked');
		objCtrl.datosEntrada.indCapturaDomicilioFiscal = $('#indCapturaDomicilioFiscal').is(':checked');
		objCtrl.datosEntrada.indCapturaMediosContactoFiscales = $('#indCapturaMediosContactoFiscales').is(':checked');
		objCtrl.datosEntrada.indCapturaRazonSocial = $('#indCapturaRazonSocial').is(':checked');
		objCtrl.datosEntrada.indCapturaFechaConstitucion = $('#indCapturaFechaConstitucion').is(':checked');
		objCtrl.datosEntrada.indCapturaTipoSociedad = $('#indCapturaTipoSociedad').is(':checked');
		
		objCtrl.datosEntrada.indCapturaActaConstitutiva = $('#indCapturaActaConstitutiva').is(':checked');
		objCtrl.datosEntrada.indCapturaRegistroSindicato = $('#indCapturaRegistroSindicato').is(':checked');
		
		objCtrl.datosEntrada.indAutorizacion = $('#indAutorizacion').is(':checked');

		objCtrl.modificacionManual();

	};

	var fnCallback = function() {
		if (objCtrl.getDatosSalida() != null) { 
			if (objCtrl.getDatosSalida().mdmDatosEntrada.personaMoral != null) {
				$('#resultadoJSON').val(objCtrl.getDatosSalida().mdmDatosEntrada.personaMoral.razonSocial);
				
				$.postJSON('/gestionIndividuo-consulta-web/persona/afectar-datos/crearSolicitudModificacion', objCtrl.getDatosSalida().mdmDatosEntrada, function(data) {
					alert("La solicitud fue creada exitosamente");
				}).error(function(data){
					alert("Error!!!!!!!!!")
				});
				
				if($('#indAfectarDatos').is(':checked')){
					
					$('#datosICA').val('test');
					
					var forma = $("form#tramiteForm").toObject();
					forma.datosModifManual = objCtrl.getDatosSalida().mdmDatosEntrada;
					forma.datosICA = null;
					
					$.postJSON('/gestionIndividuo-consulta-web/persona/afectar-datos', forma, function(data) {
						alert("MODIFICACION EXITOSA");
					}).error(function(data){
						alert("Error!!!!!!!!!")
					});
				}
			} else if (objCtrl.getDatosSalida().mdmDatosEntrada.errorFormGeneral != null) {
				$('#resultadoJSON').val(
						objCtrl.getDatosSalida().mdmDatosEntrada.errorFormGeneral);
			}
		}
	};
</script>

<div class="container">
	<div class="hero-unit">
		<div class="form-comment" style="padding-right: 20px;">

			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<form:form modelAttribute="mdmDatosEntrada" id="forma" method="post"
				action="">

				<div class="alert">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Instrucciones: </strong>Ingrese los datos para realizar la
					modificaci&oacute;n manual
				</div>

				<fieldset>

					<legend>
						<strong>&nbsp;Datos de la B&uacute;squeda&nbsp;</strong>
					</legend>

					<form:label path="personaMoral.idPersona" cssClass="wide">ID de Persona</form:label>
					<form:errors path="personaMoral.idPersona" cssClass="error"></form:errors>
					<form:input path="personaMoral.idPersona" id="busquedaIdPersona"
						cssStyle="width: 300px" maxlength="18" />
					<br /> <br /> <br />

					<fieldset>
						<legend>DATOS SAT</legend>
						<form:label path="indCapturaRazonSocial" cssClass="wide">Capturar Raz&oacute;n Social</form:label>
						<form:checkbox path="indCapturaRazonSocial" id="indCapturaRazonSocial" />
						<br /><br /><br />
						<form:label path="indCapturaFechaConstitucion" cssClass="wide">Capturar Fecha Constituci&oacute;n</form:label>
						<form:checkbox path="indCapturaFechaConstitucion" id="indCapturaFechaConstitucion" />
						<br /><br /><br />
						<form:label path="indCapturaTipoSociedad" cssClass="wide">Capturar Tipo Sociedad</form:label>
						<form:checkbox path="indCapturaTipoSociedad" id="indCapturaTipoSociedad" />
						<br /><br /><br />
						<form:label path="indCapturaRFC" cssClass="wide">Capturar RFC</form:label>
						<form:checkbox path="indCapturaRFC" id="indCapturaRFC" />
						<br /><br /><br />
						<form:label path="indCapturaDomicilioFiscal" cssClass="wide">Capturar Dom Fiscal</form:label>
						<form:checkbox path="indCapturaDomicilioFiscal"
							id="indCapturaDomicilioFiscal" />
						<br /><br /><br />
						<form:label path="indCapturaMediosContactoFiscales" cssClass="wide">Capturar Medios Fiscales</form:label>
						<form:checkbox path="indCapturaMediosContactoFiscales"
							id="indCapturaMediosContactoFiscales" />
						<br /> <br /> <br />
					</fieldset>
					
					<fieldset>
						<legend>DATOS COMPLEMENTARIOS</legend>
						<form:label path="indCapturaActaConstitutiva" cssClass="wide">Capturar Acta Constitutiva</form:label>
						<form:checkbox path="indCapturaActaConstitutiva" id="indCapturaActaConstitutiva" />
						<br /><br /><br />
						<form:label path="indCapturaRegistroSindicato" cssClass="wide">Capturar Registro de Sindicato</form:label>
						<form:checkbox path="indCapturaRegistroSindicato" id="indCapturaRegistroSindicato" />
						<br /><br /><br />
					</fieldset>

					<form:label path="indAutorizacion" cssClass="wide">Autorizaci&oacute;n</form:label>
					<form:checkbox path="indAutorizacion" id="indAutorizacion" />
					<br /> <br /> <br />
				</fieldset>
				<br />

				<div style="float: right;">
					<button type="button" class="btn btn-secondary" id="consultar">
						Consultar</button>
					<button type="button" class="btn btn-secondary" id="limpiar">
						Limpiar</button>
				</div>
				<br />

				<span id="errorNegocioLabel" class="error hiddenElement"></span>
			</form:form>

			<div id="mdmMoralDialog"></div>

			<br /> <br /> <br />
			
			<label class="wide">Invocar servicio "Afectar Datos Persona"</label> 
			<input type="checkbox" id="indAfectarDatos"/>
			
			<form:form modelAttribute="tramite" id="tramiteForm">
				<form:hidden path="datosICA"/>
				<form:hidden path="datosModifManual"/>
			</form:form>

			<textarea rows="50" cols="100" id="resultadoJSON"></textarea>

		</div>
	</div>
</div>

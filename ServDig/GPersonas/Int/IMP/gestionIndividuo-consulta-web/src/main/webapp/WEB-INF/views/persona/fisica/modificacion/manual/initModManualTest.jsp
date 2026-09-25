<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/modificacion/manual/modificacion-manual-datos.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var objCtrl = ModificacionManualDatosFisicaCtrl;

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

		objCtrl.init('mdmFisicaDialog');

		objCtrl.datosEntrada.idPersona = $('#busquedaIdPersona').val();
		objCtrl.datosEntrada.indCapturaNombre = $('#indCapturaNombre').is(':checked');
		objCtrl.datosEntrada.indCapturaCURP = $('#indCapturaCURP').is(':checked');
		objCtrl.datosEntrada.indCapturaSexo = $('#indCapturaSexo').is(':checked');
		objCtrl.datosEntrada.indCapturaFechaNacimiento = $('#indCapturaFechaNacimiento').is(':checked');
		objCtrl.datosEntrada.indCapturaLugarNacimiento = $('#indCapturaLugarNacimiento').is(':checked');
		objCtrl.datosEntrada.indCapturaDocumentoProbatorio = $('#indCapturaDocumentoProbatorio').is(':checked');

		objCtrl.datosEntrada.indCapturaRFC  = $('#indCapturaRFC').is(':checked');
		objCtrl.datosEntrada.indCapturaDomicilioFiscal = $('#indCapturaDomicilioFiscal').is(':checked');
		objCtrl.datosEntrada.indCapturaMediosContactoFiscales = $('#indCapturaMediosContactoFiscales').is(':checked');
	
		objCtrl.datosEntrada.indCapturaDomicilioParticular = $('#indCapturaDomicilioParticular').is(':checked');
		objCtrl.datosEntrada.indCapturaMediosContactoParticular = $('#indCapturaMediosContactoParticular').is(':checked');
	
		objCtrl.datosEntrada.indAutorizacion = $('#indAutorizacion').is(':checked');

		objCtrl.modificacionManual();

	};

	var fnCallback = function() {
		if (objCtrl.getDatosSalida() != null) {
			if (objCtrl.getDatosSalida().mdmDatosEntrada.personaFisica != null) {
				$('#resultadoJSON').val(objCtrl.getDatosSalida().mdmDatosEntrada.personaFisica.nombre);
				 
				delete objCtrl.getDatosSalida().mdmDatosEntrada.personaFisica.documentosProbatorios;
				delete objCtrl.getDatosSalida().mdmDatosEntrada.personaFisica.documentosProbatoriosRENAPO;
				
				/* $.postJSON('/gestionIndividuo-consulta-web/persona/afectar-datos/crearSolicitudModificacion', objCtrl.getDatosSalida().mdmDatosEntrada, function(data) {
					alert("La solicitud fue creada exitosamente");
				}).error(function(data){ 
					alert("Error!!!!!!!!!")
				}); */
				
				if($('#indAfectarDatos').is(':checked')){
					
					$('#datosICA').val('test');
					
					var forma = $("form#tramiteForm").toObject();
					forma.datosModifManual = objCtrl.getDatosSalida().mdmDatosEntrada;
					forma.datosICA = null;
					
					/* ¡ESTO SÓLO ES POR CUESTIÓN DE PRUEBAS!
					 * Se quita el campo documentos probatorios, para que
					 * al momento de que Spring parsee el objeto JSON al objeto de modelo 
					 * no truene
					 */
					delete forma.datosModifManual.personaFisica.documentosProbatorios;
					delete forma.datosModifManual.personaFisica.documentosProbatoriosRENAPO;
					
					$.blockUI();
					$.postJSON('/gestionIndividuo-consulta-web/persona/afectar-datos', forma, function(data) {
						$.unblockUI();
						alert("MODIFICACION EXITOSA");
					}).error(function(data){
						$.unblockUI();
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

					<form:label path="personaFisica.idPersona" cssClass="wide">ID de Persona</form:label>
					<form:errors path="personaFisica.idPersona" cssClass="error"></form:errors>
					<form:input path="personaFisica.idPersona" id="busquedaIdPersona"
						cssStyle="width: 300px" maxlength="18" />
					<br /> <br /> <br />

					<fieldset>
						<legend>DATOS RENAPO</legend>
						<form:label path="indCapturaNombre" cssClass="wide">Capturar Nombre</form:label>
						<form:checkbox path="indCapturaNombre" id="indCapturaNombre" />
						<br /><br /><br />
						<form:label path="indCapturaCURP" cssClass="wide">Capturar CURP</form:label>
						<form:checkbox path="indCapturaCURP" id="indCapturaCURP" />
						<br /><br /><br />
						<form:label path="indCapturaSexo" cssClass="wide">Capturar Sexo</form:label>
						<form:checkbox path="indCapturaSexo" id="indCapturaSexo" />
						<br /><br /><br />
						<form:label path="indCapturaFechaNacimiento" cssClass="wide">Capturar Fecha Nacimiento</form:label>
						<form:checkbox path="indCapturaFechaNacimiento"
							id="indCapturaFechaNacimiento" />
						<br /><br /><br />
						<form:label path="indCapturaLugarNacimiento" cssClass="wide">Capturar Lugar Nacimiento</form:label>
						<form:checkbox path="indCapturaLugarNacimiento"
							id="indCapturaLugarNacimiento" />
						<br /><br /><br />
						<form:label path="indCapturaDocumentoProbatorio" cssClass="wide">Capturar Doc Probatorio</form:label>
						<form:checkbox path="indCapturaDocumentoProbatorio"
							id="indCapturaDocumentoProbatorio" />
						<br /> <br /> <br />
					</fieldset>
					
					<fieldset>
						<legend>DATOS SAT</legend>
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
						<form:label path="indCapturaDomicilioParticular" cssClass="wide">Capturar Dom Particular</form:label>
						<form:checkbox path="indCapturaDomicilioParticular"
							id="indCapturaDomicilioParticular" />
						<br /><br /><br />
						<form:label path="indCapturaMediosContactoParticular"
							cssClass="wide">Capturar Medios Particulares</form:label>
						<form:checkbox path="indCapturaMediosContactoParticular"
							id="indCapturaMediosContactoParticular" />
						<br /> <br /> <br />
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

			<div id="mdmFisicaDialog"></div>

			<br /> <br /> <br />
			
			<label class="wide">Invocar servicio "Afectar Datos Persona"</label> 
			<input type="checkbox" id="indAfectarDatos" checked="checked"/>
			
			<form:form modelAttribute="tramite" id="tramiteForm">
				<form:hidden path="datosICA"/>
				<form:hidden path="datosModifManual"/>
			</form:form>

			<textarea rows="50" cols="100" id="resultadoJSON"></textarea>

		</div>
	</div>
</div>

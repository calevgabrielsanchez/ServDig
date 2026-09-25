<%@ include file="/WEB-INF/views/layout/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var objCtrl = identificarCambiosAutomaticosPersonaFisicaCtrl;

	$(document).ready(function() {
		$('#limpiar').click(function() {
			$('#forma').clearForm();
		});

		$('#consultar').click(function(event) {
			fnICATest();
		});
		
		objCtrl.setOnCloseCallback(fnCallback);
		
		

	});
	
	var fnICATest = function() {
		
		var isConsultaVentanilla = false;
		
		if($("input[name='tipoPantalla']:checked").val() == 'ventanilla') {
			isConsultaVentanilla = true;
		}
		
		if (isConsultaVentanilla) {
			objCtrl.init('icaFisicaDialog');
			
			objCtrl.datosEntrada.indMostrarPantalla = $('#indicadorMostrarPantalla').is(':checked');
			objCtrl.datosEntrada.indUsuarioExterno = $('#isUsuarioExterno').is(':checked');
			objCtrl.datosEntrada.idPersona = $('#busquedaIdPersona').val();
			objCtrl.datosEntrada.curp = $('#busquedaCurp').val();
			objCtrl.datosEntrada.rfc = $('#busquedaRfc').val();
			objCtrl.datosEntrada.nombrePersona = $('#busquedaNombres').val();
			objCtrl.datosEntrada.primerApellido = $('#busquedaPrimerApellido').val();
			objCtrl.datosEntrada.segundoApellido = $('#busquedaSegundoApellido').val();
			objCtrl.datosEntrada.indBusquedaRENAPO = $('#indicadorConsultaRENAPO').is(':checked');
			objCtrl.datosEntrada.indBusquedaSAT = $('#indicadorConsultaSAT').is(':checked');
			
			objCtrl.identificarCambios();	
		} else {
			var datosEntrada = new Object();
			
			datosEntrada.indicadorMostrarPantalla = $('#indicadorMostrarPantalla').is(':checked');
			datosEntrada.isUsuarioExterno = $('#isUsuarioExterno').is(':checked');
			datosEntrada.personaFisica = new Object ();
			datosEntrada.personaFisica.idPersona = $('#busquedaIdPersona').val();
			datosEntrada.personaFisica.curp = $('#busquedaCurp').val();
			datosEntrada.personaFisica.rfc = $('#busquedaRfc').val();
			datosEntrada.personaFisica.nombre = $('#busquedaNombres').val();
			datosEntrada.personaFisica.primerApellido = $('#busquedaPrimerApellido').val();
			datosEntrada.personaFisica.segundoApellido = $('#busquedaSegundoApellido').val();
			datosEntrada.indicadorConsultaRENAPO = $('#indicadorConsultaRENAPO').is(':checked');
			datosEntrada.indicadorConsultaSAT = $('#indicadorConsultaSAT').is(':checked');
			
			consultaPantallaPortal(datosEntrada);
		}
	};
	
	var fnCallback = function (){
		if(objCtrl.getDatosSalida() != null){
			if(objCtrl.getDatosSalida().personaFisicaIMSS != null){
				$('#resultadoJSON').val(objCtrl.getDatosSalida().personaFisicaIMSS.nombre);
				
				delete objCtrl.getDatosSalida().personaFisicaIMSS.nombreCompleto;
								
				$.postJSON('/gestionIndividuo-consulta-web/persona/afectar-datos/crearSolicitudICA', objCtrl.getDatosSalida(), function(data) {
					alert("La solicitud fue creada exitosamente");
				}).error(function(data){
					alert("Error!!!!!!!!!")
				});
				
				if($('#indAfectarDatos').is(':checked')){
					
					$('#datosModifManual').val('test');
					
					var forma = $("form#tramiteForm").toObject();
					forma.datosModifManual = null
					forma.datosICA = objCtrl.getDatosSalida();
					
					$.postJSON('/gestionIndividuo-consulta-web/persona/afectar-datos', forma, function(data) {
						alert("MODIFICACION EXITOSA");
					}).error(function(data){
						alert("Error!!!!!!!!!")
					});
				}
			}else if(objCtrl.getDatosSalida().traza != null){
				$('#resultadoJSON').val(objCtrl.getDatosSalida().traza);
			}else if(objCtrl.getDatosSalida().errorFormGeneral != null){
				$('#resultadoJSON').val(objCtrl.getDatosSalida().errorFormGeneral);
			}
		}
	};
		
	function consultaPantallaPortal(datosEntrada) {
		
		var url = '/gestionIndividuo-consulta-web/persona/fisica/identificar/cambios-automaticos/compararIntegrar' 
		
		$.ajax({
			type: "POST",
			url : url,
			data : datosEntrada ? JSON.stringify(datosEntrada) : null,
			contentType : "application/json",
			dataType : "json",
			converters : {
				'text json' : true
			},
			success : function(response) {
				$('#pantallaPortalDiv').html(response);
			},
			error : function(error) {
				alert('Error -> ' + error);
			}
		});
	}
</script>
	
<div class="container">
	<div class="hero-unit">
		<div class="form-comment" style="padding-right: 20px;">
		
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<form:form modelAttribute="icaDatosConsulta" id="forma" method="post" action="">
										
				<div class="alert">
				    <button type="button" class="close" data-dismiss="alert">×</button>
				    <strong>Instrucciones: </strong>Ingrese los datos para realizar la(s) consulta(s)
			    </div>
					
				<fieldset>
					
					<legend>
						<strong>&nbsp;Datos de la B&uacute;squeda&nbsp;</strong>
					</legend>

					<div>
						<form:label path="personaFisica.idPersona" cssClass="wide">ID de Persona</form:label>
						<form:errors path="personaFisica.idPersona" cssClass="error"></form:errors>
						<form:input path="personaFisica.idPersona" id="busquedaIdPersona" cssStyle="width: 300px" maxlength="18" />
					</div>
					<br /><br /><br />					
					
					<div>
						<form:label path="personaFisica.curp" cssClass="wide">CURP</form:label>
						<form:errors path="personaFisica.curp" cssClass="error"></form:errors>
						<form:input path="personaFisica.curp" id="busquedaCurp" cssStyle="width: 300px" maxlength="18" />
					</div>
					<br /><br /><br />
					
					<div>
						<form:label path="personaFisica.rfc" cssClass="wide">RFC</form:label>
						<form:errors path="personaFisica.rfc" cssClass="error"></form:errors>
						<form:input path="personaFisica.rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13" />
					</div>
					<br /><br /><br />
					<div>
						<form:label path="personaFisica.nombre" cssClass="wide">Nombre(s)</form:label>
						<form:errors path="personaFisica.nombre" cssClass="error"></form:errors>
						<form:input path="personaFisica.nombre" id="busquedaNombres" cssStyle="width: 300px" maxlength="30" />
					</div>
					<br /><br /><br />
					<div>
						<form:label path="personaFisica.primerApellido" cssClass="wide">Primer Apellido</form:label>
						<form:errors path="personaFisica.primerApellido" cssClass="error"></form:errors>
						<form:input path="personaFisica.primerApellido" id="busquedaPrimerApellido" cssStyle="width: 300px" maxlength="30" />
					</div>
					<br /><br /><br />
					<div>
						<form:label path="personaFisica.segundoApellido" cssClass="wide">Segundo Apellido</form:label>
						<form:errors path="personaFisica.segundoApellido" cssClass="error"></form:errors>
						<form:input path="personaFisica.segundoApellido" id="busquedaSegundoApellido" cssStyle="width: 300px" maxlength="30" />
					</div>
					<br /><br /><br />
					
					<form:label path="indicadorConsultaRENAPO" cssClass="wide">Consulta en RENAPO</form:label>
					<form:checkbox path="indicadorConsultaRENAPO" id="indicadorConsultaRENAPO"/>
					<br /><br /><br />
				
					<form:label path="indicadorConsultaSAT" cssClass="wide">Consulta en SAT</form:label>
					<form:checkbox path="indicadorConsultaSAT" id="indicadorConsultaSAT"/>
					<br /><br /><br />
					
					<fieldset>
						<legend>
							<strong>&nbsp;Tipo de consulta&nbsp;</strong>
						</legend>
						
						<label class="wide">Pantalla Portal</label>
						<input type="radio" name="tipoPantalla" value="portal"/>
						<br /><br /><br />
						
						<label class="wide">Pantalla Ventanilla</label>
						<input type="radio" name="tipoPantalla" value="ventanilla"/>
						<br /><br /><br />
						
						<form:label path="indicadorMostrarPantalla" cssClass="wide">Mostrar pantalla</form:label>
						<form:checkbox path="indicadorMostrarPantalla" id="indicadorMostrarPantalla"/>
					</fieldset>
					
					<br /><br /><br />
					
					<form:label path="isUsuarioExterno" cssClass="wide">Usuario Externo</form:label>
					<form:checkbox path="isUsuarioExterno" id="isUsuarioExterno"/>
					<br /><br /><br />
				</fieldset>
				<br/>
				
				<div style="float: right;">
					<button type="button" class="btn btn-secondary" id="consultar"> Consultar </button>
					<button type="button" class="btn btn-secondary" id="limpiar"> Limpiar </button>
				</div>	
				<br />
							
				<span id="errorNegocioLabel" class="error hiddenElement"></span>
			</form:form>
			
			<div id="icaFisicaDialog">
			</div>
			
			<br /><br /><br />
			
			<label class="wide">Invocar servicio "Afectar Datos Persona"</label> 
			<input type="checkbox" id="indAfectarDatos"/>
			
			<form:form modelAttribute="tramite" id="tramiteForm">
				<form:hidden path="datosICA"/>
				<form:hidden path="datosModifManual"/>
			</form:form>
			
			<textarea rows="50" cols="100" id="resultadoJSON"></textarea>
			
			
			<div id="pantallaPortalDiv"></div>
		</div>
	</div>
</div>

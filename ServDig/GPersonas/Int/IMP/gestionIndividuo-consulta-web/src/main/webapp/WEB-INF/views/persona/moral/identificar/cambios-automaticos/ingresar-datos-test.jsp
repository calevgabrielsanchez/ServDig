<%@ include file="/WEB-INF/views/layout/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/moral/identificar/cambios-automaticos/identificar-cambios-automaticos.js" htmlEscape="true" />"></script>


<script type="text/javascript">
	var objCtrl = identificarCambiosAutomaticosPersonaMoralCtrl;

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
		
		objCtrl.init('icaMoralDialog');
		
		objCtrl.datosEntrada.indMostrarPantalla = $('#indicadorMostrarPantalla').is(':checked');
		objCtrl.datosEntrada.idPersona = $('#busquedaIdPersona').val();
		objCtrl.datosEntrada.rfc = $('#busquedaRfc').val();
		
		objCtrl.identificarCambios();

	};
	
	var fnCallback = function (){ 
		if(objCtrl.getDatosSalida() != null){
			if(objCtrl.getDatosSalida().personaMoralIMSS != null){
				$('#resultadoJSON').val(objCtrl.getDatosSalida().personaMoralIMSS.razonSocial);
				
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
</script>
	
<div class="container">
	<div class="hero-unit">
		<div class="form-comment" style="padding-right: 20px;">
		
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<form:form modelAttribute="icaDatosConsulta" id="forma" method="post" action="${contextpath}/persona/moral/identificar/cambios-automaticos/consultar-comparar">
											
				<div class="alert">
				    <button type="button" class="close" data-dismiss="alert">×</button>
				    <strong>Instrucciones: </strong>Ingrese los datos para realizar la(s) consulta(s)
			    </div>
					
				<fieldset>
					
					<legend>
						<strong>&nbsp;Datos de la B&uacute;squeda&nbsp;</strong>
					</legend>

					<div>
						<form:label path="personaMoral.idPersona" cssClass="wide">ID de Persona</form:label>
						<form:errors path="personaMoral.idPersona" cssClass="error"></form:errors>
						<form:input path="personaMoral.idPersona" id="busquedaIdPersona" cssStyle="width: 300px" maxlength="18" />
					</div>
					<br /><br /><br />					
							
					<div>
						<form:label path="personaMoral.rfc" cssClass="wide">RFC</form:label>
						<form:errors path="personaMoral.rfc" cssClass="error"></form:errors>
						<form:input path="personaMoral.rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13" />
					</div>
					<br /><br /><br />
					
					<form:label path="indicadorMostrarPantalla" cssClass="wide">Mostrar pantalla</form:label>
					<form:checkbox path="indicadorMostrarPantalla" id="indicadorMostrarPantalla"/>
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
			
			<div id="icaMoralDialog">
			</div>
			
			<br /><br /><br />
			
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
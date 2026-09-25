<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.portal.web.model.TipoFiltroEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>

<c:set var="tipoFiltro" value="${filtros.tipoFiltro }" />
<c:set var="CURP" value="<%= TipoFiltroEnum.CURP.getId() %>" />
<c:set var="RFC_FISICA" value="<%= TipoFiltroEnum.RFC_FISICA.getId() %>" />
<c:set var="RFC_MORAL" value="<%= TipoFiltroEnum.RFC_MORAL.getId() %>" />
<c:set var="NSS" value="<%= TipoFiltroEnum.NSS.getId() %>" />
<c:set var="NRP" value="<%= TipoFiltroEnum.NRP.getId() %>" />

<c:choose>
	<c:when test="${ tipoFiltro eq RFC_FISICA }">
		<c:set var="path" value="rfc" />
		<c:set var="label" value="RFC" />
		<c:set var="maxLength" value="13" />
		<c:set var="placeholder" value="RFC" />
		<c:set var="cssCharValidation" value="alfanumericoEstricto" />
		<c:set var="showBusquedaAvanzada" value="false" />
		<c:set var="tipoPersona" value="<%= TipoPersonaEnum.FISICA.getId() %>" />
	</c:when>
	<c:when test="${ tipoFiltro eq RFC_MORAL}">
		<c:set var="path" value="rfc" />
		<c:set var="label" value="RFC" />
		<c:set var="maxLength" value="12" />
		<c:set var="placeholder" value="RFC" />
		<c:set var="cssCharValidation" value="alfanumericoSemiEstricto" />
		<c:set var="showBusquedaAvanzada" value="false" />
		<c:set var="tipoPersona" value="<%= TipoPersonaEnum.MORAL.getId() %>" />
	</c:when>
	<c:when test="${ tipoFiltro eq CURP }">
		<c:set var="path" value="curp" />
		<c:set var="label" value="CURP" />
		<c:set var="maxLength" value="18" />
		<c:set var="placeholder" value="CURP" />
		<c:set var="cssCharValidation" value="alfanumericoEstricto" />
		<c:set var="showBusquedaAvanzada" value="true" />
		<c:set var="tipoPersona" value="<%= TipoPersonaEnum.FISICA.getId() %>" />
	</c:when>
	<c:when test="${ tipoFiltro eq NSS }">
		<c:set var="path" value="nss" />
		<c:set var="label" value="NSS" />
		<c:set var="maxLength" value="11" />
		<c:set var="placeholder" value="NSS" />
		<c:set var="cssCharValidation" value="numericoSinPunto" />
	</c:when>
	<c:when test="${ tipoFiltro eq NRP }">
		<c:set var="path" value="nrp" />
		<c:set var="label" value="NRP" />
		<c:set var="maxLength" value="11" />
		<c:set var="placeholder" value="NRP" />
		<c:set var="cssCharValidation" value="alfanumericoEstricto" />
	</c:when>
</c:choose>

<script type="text/javascript">
	var formulario = {data : "",get tipo () {return this.data;},set tipo(value) {this.data = value;}};
	var dialogoBuscar = null;
	var dialogoWizard = null;
	
	$(function() {
		dialogoBuscar = $('div#ubicarContainer').persona({
			fnOnClose : function(persona) {
				var objPersona = JSON.parse(persona);
								
				_identidadCtrl.identidad({
					idPersona : objPersona.idPersona,
					idTipoPersona : objPersona.tipoPersona.idTipoPersona,
					personaUbicada: objPersona
				});
				_identidadCtrl.identidad('mostrar');
				_sujetoCtrl.sujeto({
		            idPersona : objPersona.idPersona,
		            idTipoPersona : objPersona.tipoPersona.idTipoPersona
				});
				_sujetoCtrl.sujeto('listaNRP');
			}
		});
			
		$("#busqAvanzada").click(function(event) {
			event.preventDefault();
			$('input#${path}Input').val('');
			_identidadCtrl.identidad('limpiar');
			_sujetoCtrl.sujeto('limpiar');
			dialogoBuscar.persona({
				valorBuscado : '',
				tipoBusqueda : 'DATOS_BASICO',
				_tipoPersona : '${tipoPersona}'
			});
			dialogoBuscar.persona('mostrar');
		});
		
		_busquedaCtrl.busqueda({
			complete : function(event, data) {
				var _datos = data.value.datosEntrada;
				
				_identidadCtrl.identidad({
					idPersona : _datos.idPersona,
					idTipoPersona : _datos.idTipoPersona,
					idTipoTramite : _tramite
				});
				_identidadCtrl.identidad('mostrar');
				
				_sujetoCtrl.sujeto({
					idTipoSujeto: _datos.idTipoSujeto,
					nrp : _datos.nrp,
		            nss : _datos.nss,
		            idAsignacionNss : _datos.idAsignacionNss,
		            idPersona : _datos.idPersona
				});
				_sujetoCtrl.sujeto('mostrar');
			}
		});

		$('button#btnBuscar').click(function(event) {
			event.preventDefault();
			_identidadCtrl.identidad('limpiar');
			_sujetoCtrl.sujeto('limpiar');
			_busquedaCtrl.busqueda('buscar');
		});
			
		$('input.clear-input').addClear({
			top: 3,
			right : 8
		});
		
		$('input#${path}Input').focus();
		$('input.clear-input + a').attr('tabindex', '5').keypress(function(event){
			event.preventDefault();
			if(event.which === 32) { 
				// this is the spacebar
				$(this).trigger('click');
		    }
		}).attr('id','_inputBusqueda').click(function(){
			_identidadCtrl.identidad('limpiar');
			_sujetoCtrl.sujeto('limpiar');
		});
	});
</script>

<form:form cssClass="form-horizontal" modelAttribute="filtros" id="buscarForm">
	<form:hidden path="tipoFiltro"/>
	<div id="errorNegocioLabel" class="hiddenElement alert alert-danger"></div>
	<div class="form-group form-group-sm">
		<form:label path="${path}" cssClass="col-sm-1 control-label">
			<span class="required">*</span>
			<b>${label}</b>
		</form:label>
		<div class="col-sm-6">
			<form:input path="${path}" id="${path}Input" maxlength="${maxLength}"
				cssClass="${cssCharValidation} form-control clear-input" 
				cssStyle="text-transform: uppercase"
				placeholder="${placeholder}" tabindex="1" />
			<span class="hiddenElement error" id="${path}Error"></span>
		</div>
		<div class="col-sm-5">
			<button type="button" id="btnBuscar" class="btn btn-primary" tabindex="2">BUSCAR</button>
			<c:if test="${showBusquedaAvanzada eq true }">
				<button type="button" id="busqAvanzada" class="btn btn-default" tabindex="3">B&Uacute;SQUEDA AVANZADA</button>
			</c:if>
		</div>
	</div>
</form:form>


<div id="ubicarContainer"></div>
<div id="wizardDatosActualizacion"></div>
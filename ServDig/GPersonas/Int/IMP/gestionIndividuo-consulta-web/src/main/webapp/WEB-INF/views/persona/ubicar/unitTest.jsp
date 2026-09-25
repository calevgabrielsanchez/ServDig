<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script src="<spring:url value="/static/resources/js/delta/personas/ubicar/UbicarPersonaWidget.js" htmlEscape="true" />"></script>
<script>
var dialogoBuscar = null;
	$(function() {
		dialogoBuscar = $('div#ubicarContainer').persona();
		$('button#testBtn').click(function(e) {
			e.preventDefault();
			//BUSCAR PERSONA MORAL POR RFC
			//dialogoBuscar.persona({valorBuscado : 'ODM950324V2A',tipoBusqueda : 'RFC', _tipoPersona: 2});
			
			//BUSCAR PERSONA FISICA POR RFC
			//dialogoBuscar.persona({valorBuscado : 'TEAG841013JU3',tipoBusqueda : 'RFC',_tipoPersona  : 1});
			
			//BUSCAR PERSONA FISICA POR CURP
			//dialogoBuscar.persona({valorBuscado : 'TEAG841013HCSRRS06',tipoBusqueda : 'CURP',_tipoPersona  : 1});
			
			//DATOS BASICOS POR PERSONA MORAL
			//dialogoBuscar.persona({valorBuscado : '',tipoBusqueda : 'DATOS_BASICO', _tipoPersona : 2});
			
			//DATOS BASICOS POR PERSONA FISICA
			dialogoBuscar.persona({valorBuscado : '',tipoBusqueda : 'DATOS_BASICO',_tipoPersona  : 1});
			
			dialogoBuscar.persona('mostrar');
		});
	});
</script>

<div style="text-align:center;" id="homecontenido" class="container">
	<div style="text-align: center; margin-bottom:2em;">
		<button type="button" id="testBtn"class="btn btn-primary">UBICAR PERSONA</button>
    </div>
	<div id="ubicarContainer" style="margin-top: 20px;"></div>
</div>
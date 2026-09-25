<%@ include file="../../../general/taglibs.jsp"%>
<div class="row">
<div class="col-sm-12">
<c:if test="${empty escritoDetalle}">
	<script type="text/JavaScript">
	$("#error").html("No se encontr&oacute; informaci&oacute;n con el folio proporcionado.");
	$("#error").show();
	</script>
</c:if>
<c:if test="${not empty escritoDetalle}">
<c:set var="patronEscrito" value="${escritoDetalle.patron}" scope="request"></c:set>
<jsp:include page="../../../common/datosPatron.jsp"></jsp:include>

<h4 style="margin-top: 30px">Datos del escrito de desacuerdo</h4>
<hr class="red" style="margin-bottom: 10px" />


<div class="row">
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">
			N&uacute;mero Folio Recepci&oacute;n:
		</label> <br> <span>${escritoDetalle.folioRecepcion}</span>
	</div>
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">
			Fecha:
		</label><br> <span><fmt:formatDate value="${escritoDetalle.fechaTramite}" pattern="dd-MM-yyyy" /></span>
	</div>
</div>

<div class="row">
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">			
			Materia:				
		</label><br> <span>${escritoDetalle.causaDesacuerdo.materiaDesacuerdo.descMateria}</span>
	</div>
	<div class="col-sm-6">

		<c:set var="tipoMateria" value=""></c:set>
		<label for="nrp" class="control-label" style="text-align: center;">			
			<c:set var="tipoMateria" value="${escritoDetalle.causaDesacuerdo.materiaDesacuerdo.descMateria}"></c:set>
			${fn:toUpperCase(fn:substring(tipoMateria, 0, 1))}${fn:toLowerCase(fn:substring(tipoMateria, 1,fn:length(tipoMateria)))}:
		</label><br> <span>${escritoDetalle.causaDesacuerdo.descCausaDes}</span>
	</div>
</div>


<div class="row">

	<c:if test="${not empty escritoDetalle.motivoDesacuerdo}">
		<div class="col-sm-12">
			<label for="nrp" class="control-label" style="text-align: center;">
				Motivo(s) del desacuerdo (agravios):
			</label> <br> <span>${escritoDetalle.motivoDesacuerdo}</span>
		</div>
	</c:if>
</div>

<div id="doctosBoveda" style="margin-top: 25px">
	Cargando documentos, espera por favor...
</div>
</c:if>
</div>
</div>
<script type="text/javascript" src="/gestionDocumentoProbatorio-web/static/resources/js/boveda/boveda.js"></script>
<script type="text/JavaScript">
	$(document).ready(function() {
		$("#doctosBoveda").boveda({tipoTramite: 155, idTramite: ${escritoDetalle.tramiteId}, tipoComponente: 2,tipoDocumental: "D:RTT:escrito_desacuerdo"})
	}) 
</script>

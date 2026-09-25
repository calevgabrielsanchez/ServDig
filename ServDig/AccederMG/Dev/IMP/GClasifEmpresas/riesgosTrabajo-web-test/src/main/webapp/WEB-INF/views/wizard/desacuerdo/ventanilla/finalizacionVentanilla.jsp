<%@ include file="../../../general/taglibs.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/finalizacionVentanilla.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
	
		<jsp:include page="pasosEscritoDesacuerdo.jsp">
			<jsp:param value="3" name="paso"/>
		</jsp:include>
		
		<div class="alert alert-success">
			La solicitud ha finalizado correctamente.<br>
		</div>
		
		<h4>
			Datos de la solicitud
		</h4>
		<hr class="red" style="margin-bottom: 10px" />
		<div class="row">
			<div class="col-sm-6">
				<label for="nrp" class="control-label" style="text-align: center;">
					Folio solicitud:
				</label> <br> <span>${solicitudEscrito.noFolioSolicitud}</span>
			</div>
			<div class="col-sm-6">
				<label for="nrp" class="control-label" style="text-align: center;">
					Folio de recepci&oacute;n:
				</label> <br> <span>${folioRecepcionDesacuerdo}</span>
			</div>
		</div>
		
		<jsp:include page="../../../common/datosPatron.jsp"></jsp:include>
		
		
		<div class="row">
			<div class="col-sm-12 text-right">
				<button type="button" id="finalizar" class="btn btn-default">Finalizar</button>
				<button type="button" id="imprimirReporte" class="btn btn-primary">Imprimir Acuse</button>
			</div>
		</div>
		
		<form action="${contextpath}/escrito/wizard/getComprobante" method="post" target="_blank" class="formNotBlock" id="formImprimirReporte">
		</form>
	
	</div>
</div>
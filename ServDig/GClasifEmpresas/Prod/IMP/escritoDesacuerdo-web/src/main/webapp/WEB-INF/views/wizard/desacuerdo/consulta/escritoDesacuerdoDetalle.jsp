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
						<spring:message code="wizard.desacuerdo.label.materia"/>:
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
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivosDesacuerdo.descMotivoDes}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.label.motivo"/>:
							</label> <br> <span>${escritoDetalle.motivosDesacuerdo.descMotivoDes}</span>
					</c:if>
				</div>

			</div>

			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo}">
						<label for="nrp" class="control-label" style="text-align: center;">
							Motivo(s):
						</label> <br> <span>${escritoDetalle.motivoDesacuerdo}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo1}">
						<span>${escritoDetalle.motivoDesacuerdo1}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo2}">
						<span>${escritoDetalle.motivoDesacuerdo2}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo3}">
						<span>${escritoDetalle.motivoDesacuerdo3}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo4}">
						<span>${escritoDetalle.motivoDesacuerdo4}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo5}">
						<span>${escritoDetalle.motivoDesacuerdo5}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo6}">
						<span>${escritoDetalle.motivoDesacuerdo6}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo7}">
						<span>${escritoDetalle.motivoDesacuerdo7}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo8}">
						<span>${escritoDetalle.motivoDesacuerdo8}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.motivoDesacuerdo9}">
						<span>${escritoDetalle.motivoDesacuerdo9}</span>
					</c:if>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.folioImpugnado}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.label.folio"/>:
							</label> <br> <span>${escritoDetalle.folioImpugnado}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.mail}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.label.correo"/>:
							</label> <br> <span>${escritoDetalle.mail}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.anVigencia}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.label.anVig"/>:
							</label> <br> <span>${escritoDetalle.anVigencia}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.trabajadorProm}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.label.trabaj"/>:
							</label> <br> <span>${escritoDetalle.trabajadorProm}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.claseAnterior}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.txt.clas"/> anterior:
							</label> <br> <span>${escritoDetalle.claseAnterior}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.fracAnterior}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.txt.frac"/> anterior:
							</label> <br> <span>${escritoDetalle.fracAnterior}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.primAnterior}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.txt.prim"/> anterior:
							</label> <br> <span>${escritoDetalle.primAnterior}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty escritoDetalle.fechNotRes}">
							<label for="nrp" class="control-label" style="text-align: center;">
								<spring:message code="wizard.desacuerdo.label.fechNotRes"/>:
							</label> <br> <span>${escritoDetalle.fechNotRes}</span>
					</c:if>
				</div>
			</div>

			<c:if test="${not empty domicilioEscrito}">
				<h4 style="margin-top: 30px">Datos del domicilio</h4>
				<hr class="red" style="margin-bottom: 10px" />
			</c:if>

			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty domicilioEscrito.desDomicilio}">
							<label for="nrp" class="control-label" style="text-align: center;">
								Calle:
							</label> <br> <span>${domicilioEscrito.desDomicilio}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty domicilioEscrito.domNumExterior}">
							<label for="nrp" class="control-label" style="text-align: center;">
								Num Exterior:
							</label> <br> <span>${domicilioEscrito.domNumExterior}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty domicilioEscrito.domNumInterior}">
							<label for="nrp" class="control-label" style="text-align: center;">
								Num Interior:
							</label> <br> <span>${domicilioEscrito.domNumInterior}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty domicilioEscrito.refCodPostal}">
							<label for="nrp" class="control-label" style="text-align: center;">
								CP:
							</label> <br> <span>${domicilioEscrito.refCodPostal}</span>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<c:if test="${not empty domicilioEscrito.desCiudad}">
							<label for="nrp" class="control-label" style="text-align: center;">
								Ciudad:
							</label> <br> <span>${domicilioEscrito.desCiudad}</span>
					</c:if>
				</div>
				<div class="col-sm-6">
					<c:if test="${not empty domicilioEscrito.desEstado}">
							<label for="nrp" class="control-label" style="text-align: center;">
								Estado:
							</label> <br> <span>${domicilioEscrito.desEstado}</span>
					</c:if>
				</div>
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

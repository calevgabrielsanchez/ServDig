<%@ include file="../general/taglibs.jsp"%>
<h4>
	<spring:message code="label.datosPat" />
</h4>
<hr class="red" style="margin-bottom: 10px" />
<div class="row">
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">
			<spring:message code="label.rp" />:
		</label> <br> <span id="spanNRP">${patronEscrito.nrp}</span>
	</div>
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">
			<spring:message code="label.nrs" />:
		</label> <br> <span>${patronEscrito.razonSocial}</span>
	</div>
</div>
<div class="row">
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">
			<spring:message code="label.delegacion" />:
		</label><br> <span>${patronEscrito.subdelegacion.delegacion.descripcion}</span>
	</div>
	<div class="col-sm-6">
		<label for="nrp" class="control-label" style="text-align: center;">
			<spring:message code="label.subdel" />:
		</label> <br> <span>${patronEscrito.subdelegacion.descripcion}</span>
	</div>
</div>
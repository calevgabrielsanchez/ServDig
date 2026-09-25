<%@ include file="../general/taglibs.jsp"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<div id="info-paso" style="margin-bottom: 50px;">

	 <div class="contenedor">
		<h3>
			<spring:message code="label.registro.asegurado.por.responsable" />
		</h3>
		<hr class="red" style="margin-bottom: 20px;"/>

		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		<form:form modelAttribute="datosAsegurado"
			id="registroAseguradoDomicilioForm"
			action="${contextpath}/correccionCURP/registroSolicitudCDAResponsable" >
			<form:errors cssClass="alert alert-danger" element="div"/>
			<div class="row">
				<div class="col-md-3">
						<spring:message code="label.curp" />
				</div>
				
					<div class="col-md-3">
						<form:input path="curp" />
					</div>
			</div>
				<br/>
				<div class="row">
					<div class="col-md-3">
							<spring:message code="label.nombre" />
					</div>
					<div class="col-md-3">
						<form:input path="nombre" maxlength="20"/>
					</div>
					
					<div class="col-md-3"> 
							<spring:message code="label.primer.apellido" />
					</div>
					<div class="col-md-3">
						<form:input path="primerApellido" maxlength="20" />
					</div>
				</div>
				<br/>
				<div class="row">
					<div class="col-md-3">
						<spring:message code="label.fecha.nacimiento" />
					</div>
					<div class="col-md-3">
						 <div class="form-group datepicker-group">
							<input class="form-control" id="calendarYear" type="text">
							<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
						</div>
					</div>
				</div>	
				</form:form>
				
			</div>
			<br/>
			<br />
			<button class="btn btn-primary pull-right" type="submit">Buscar</button>
			<button class="btn btn-default pull-right" type="submit">Cancelar</button>
</div>

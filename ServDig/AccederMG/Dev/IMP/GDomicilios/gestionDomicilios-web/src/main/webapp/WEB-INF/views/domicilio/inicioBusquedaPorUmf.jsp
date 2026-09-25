<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/domiciliosUmf.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="request" />

<script>
$(document).ready(function() {
var infoAsentamientoCP = '<p>Establecimiento de un conglomerado demogr&aacute;fico, con el conjunto de sus sistemas de convivencia, en un &aacute;rea f&iacute;sicamente localizada, considerando dentro de la misma los elementos naturales y las obras materiales que la integran.</p>';

$('#idPopoverAsentamientoCP').popover({
	animation : true,
	html : true,
	title : 'Asentamiento',
	content : infoAsentamientoCP,
	trigger : 'hover',
	placement : 'left',
	container : 'body'
});
});
</script>
<style>
	div.dom-grid div.form-group {
	    border-bottom: 1px solid #D3D3D3;
	    padding-bottom: 16px;
	}
	
	div.dom-grid div.form-group input, 
	div.dom-grid div.form-group select {
		width: 80%;
	}
	
	div.dom-grid div.form-group label {
		display: block;
	}
</style>

<script type="text/javascript">
	$(function() {
		$('div.site_position_center').css('width', '900px');
	});
</script>

			<div class="row">
				<div class="col-xs-12">
					<h2>Ubicar domicilio geogr&aacute;fico nacional</h2>
					<h3 style="font-size: .9em; color: #666666">Paso 1 / 3</h3>
				</div>
			</div>
			
			
			<div class="row dom-grid">
				<div id="codigoPostal" class="col-xs-8">
					<div>
						<form:form action="${contextpath}/domicilio/nacional/ubicar/byUmf/porCodigoPostal" 
						method="POST" modelAttribute="domicilio" id="formCodigoPostal">
						
							<fieldset>
								<legend>
									<strong>C&oacute;digo postal de su domicilio</strong>
								</legend>
								
			
								<!-- Campo del codigo postal -->
								
								<div class="form-group">
									<span id="errorNegocioLabel" class="error hiddenElement"></span>
									
									<div>
										<form:errors path="codigoPostal.codigoPostal" cssClass="error" />
										<span id="codigoPostalError" class="error hiddenElement"></span>
									</div>
									<div>
										<form:label path="codigoPostal.codigoPostal" cssClass="control-label">
											<span class="required">*</span>&nbsp;<spring:message
												code="label.codigoPostal" />
										</form:label>
										<form:input type="text" path="codigoPostal.codigoPostal"
											maxlength="5" cssClass="numerico form-control" />
									</div>
								</div>
			
								<!-- Campo de asentamiento -->
								<div class="form-group">
									<div style="float: right;">
										<a class="btn btn-default btn-xs icono-help" id="idPopoverAsentamientoCP"
											data-toggle="popover"> </a>
									</div>
									<div>
										<form:errors path="asentamiento.clave" cssClass="error" />
									</div>
									<div>
										<form:label path="asentamiento.clave" cssClass="control-label">
											<span class="required">*</span>&nbsp;<spring:message
												code="label.asentamiento" />
										</form:label>
										<select id="asentamiento.clave" name="asentamiento.clave" class="form-control" style="display: inline-block;">
											<option>--Por favor seleccione--</option>
										</select> 
										<input type="hidden" id="cveAsentamientoCPAux"
											value="${domicilio.asentamiento.clave}" />
										<img id="cveAsentamientoImgCargando"
											class="cargando-combo" style="display: none;"
											src="${staticResourcesPath}/imagenes/loading.gif"
											alt="Cargando" />
									</div>
								</div>
								
								<div>
									<button type="submit" style="float: right; margin-top: 5px;"
										class="btn btn-primary">
										<spring:message code="label.btn.ubicar" />
									</button>
								</div>
							</fieldset>
			
							<!--  Dato hidden de la localidad -->
							<form:hidden path="asentamiento.localidad.clave" />
							<form:hidden path="asentamiento.localidad.municipio.clave" />
							<form:hidden
								path="asentamiento.localidad.municipio.entidadFederativa.clave" />
						</form:form>
					</div>
				</div>
				<div id="municipio" class="dom-contenido">
				</div>
			</div>
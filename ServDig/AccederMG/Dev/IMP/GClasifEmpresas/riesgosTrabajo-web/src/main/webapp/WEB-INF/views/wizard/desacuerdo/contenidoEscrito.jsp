<%@ include file="../../general/taglibs.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<c:if test="${idOrigenPeticion == 2}">
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/componenteFielExterno.js" htmlEscape="true" />"></script>
</c:if>
<script type="text/javascript" src="/gestionDocumentoProbatorio-web/static/resources/js/boveda/boveda.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/comunesEscrito.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/contenidoDesacuerdo.js?v=1" htmlEscape="true" />"></script>


<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<div class="alert alert-info">
				El folio de la solicitud que est&aacute;s 
				<c:if test="${retomandoSolicitud}">retomando</c:if>
				<c:if test="${!retomandoSolicitud}">iniciando</c:if>
				 es: <strong>${solicitudEscrito.noFolioSolicitud}</strong>
				 <input type="hidden" id="noFolioSolicitud" value="${solicitudEscrito.noFolioSolicitud}"/>
			</div>
			<jsp:include page="../../common/datosPatron.jsp"></jsp:include>
			
			<h4 style="margin-top:20px"><spring:message code="wizard.desacuerdo.titulo"/></h4>
			<hr class="red" style="margin-bottom:10px"/>
			<form class="form form-horizontal" role="form" id="formEscrito">
			<input type="hidden" value="${cadenaOriginal}" id="contenidoFirmar"/>
			<input type="hidden" id="tramiteId" name="tramiteId" value="${idTramite}"/>
			<input type="hidden" id="retomandoSolicitud" value="${retomandoSolicitud?1:0}"/>
			<div class="form-group">
				<label for="nrp" class="control-label col-sm-6">
					<spring:message code="wizard.desacuerdo.label.materia"/> *:
				</label> 
				<div class="col-sm-6">
					<div class="radio">
						<label for="nombreRS">
							<input type="radio" class="col-xs-0 radioMateria" name="causaDesacuerdo.materiaDesacuerdo.idMateria" id="radioMateria" value="2" checked="checked"> 
							 Materia de determinaci&oacute;n de prima
						</label> 
						
						<label for="nombreRS">
							<input type="radio" class="col-xs-0 radioMateria" name="causaDesacuerdo.materiaDesacuerdo.idMateria" id="radioClasificacion" value="1"> 
							Clasificaci&oacute;n de empresas
						</label> 
					</div>
				</div>
			</div>
			<div class="form-group" id="divMateria">
				<label for="nrp" class="control-label col-sm-6">
					<spring:message code="wizard.desacuerdo.label.materiaDet"/> *:
				</label> 
				<div class="col-sm-6">
					<select class="form-control" id="materiaDeterminacion" name="causaDesacuerdo.idCausaDes">
						<option value="0">-- Selecciona por favor --</option>
						<c:forEach items="${keyCausasSession}" var="causa">
							<option value="${causa.idCausaDes}" ${causa.idCausaDes == 3 ? 'selected' : ''}>${causa.descCausaDes}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			 <div class="form-group" style="display:none" id="divClasificacion">
				<label for="nrp" class="control-label col-sm-6">
					<spring:message code="wizard.desacuerdo.label.clasificacion"/>:
				</label> 
				<div class="col-sm-6">
					<div class="radio">
						<label for="nombreRS">
							<input type="radio" class="col-xs-0" id="resolucion" value="4" checked="checked"> 
							Resoluci&oacute;n de rectificaci&oacute;n de la clasificaci&oacute;n de empresa
						</label> 
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="nrp" class="control-label col-sm-6">
					  	<spring:message code="wizard.desacuerdo.label.folio"/> *:
				 </label> 
				<div class="col-sm-6">
					 <input class="form-control" type="text" id="folioImpugnado" name="folioImpugnado" maxlength="11" placeholder="NN/NN-NNNNN">
				</div>
			</div>
			<div class="form-group">
				<label for="nrp" class="control-label col-sm-6">
					  	<spring:message code="wizard.desacuerdo.label.correo"/>:
				 </label> 
				<div class="col-sm-6">
					 <input class="form-control" type="text" id="mail" name="mail" maxlength="50" placeholder="ejemplo@correo.com">
				</div>
			</div>
			<div class="form-group">
				<label for="nrp" class="control-label col-sm-6">
					  	<spring:message code="wizard.desacuerdo.label.capturaMotivo"/> : 
					  	<a class="btn btn-xs icono-help" id="toolTipMotivo" data-toggle="tooltip" data-placement="top" title="En caso de no capturar el motivo ser&aacute; necesario que adjuntes el documento -Escrito de desacuerdo-"></a>
				 </label> 
				<div class="col-sm-6">
					<div class="radio">
						<label for="nombreRS">
							<input type="radio" class="col-xs-0 capturaMotivo" name="capturaMotivo" value="1" id="capturaMotivo1" checked="checked"> Si
						</label> 
						<label for="nombreRS">
							<input type="radio" class="col-xs-0 capturaMotivo" name="capturaMotivo" id="capturaMotivo0" value="0"> No
						</label> 
					</div>
				</div>
			</div>
			<div class="form-group" id="divMotivoDesacuerdo">
				<label for="nrp" class="control-label col-sm-6">
					  <spring:message code="wizard.desacuerdo.label.motivo"/>* :
				 </label> 
				<div class="col-sm-6">
					 <textarea class="form-control" id="motivoDesacuerdo" name="motivoDesacuerdo"></textarea>
				</div>
			</div>
			</form>
			<div id="componenteBoveda"></div>
		</div>
	</div>
	
	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposRequeridos" /></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
				<button class="btn btn-default" id="salirTramite">
				<c:if test="${idOrigenPeticion==2}">
					<spring:message code="wizard.button.cerrar"/>
				</c:if>
				<c:if test="${idOrigenPeticion!=2}">
					<spring:message code="wizard.button.salir"></spring:message>
				</c:if>
				</button>
				<c:if test="${empty error}">
					<c:if test="${idOrigenPeticion==2}">
					<div class="btn-group dropup">
						<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> 
						<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span class="caret"></span></a>
						<ul class="dropdown-menu pull-right">
							<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i><spring:message code="wizard.button.finalizarTramite" /></a></li>
							<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite" /></a></li>
							<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite" /></a></li>
						</ul>
					</div>
					</c:if>
					<c:if test="${idOrigenPeticion!=2}">
					<button type="button" class="btn btn-danger" id="cancelarTramite"><spring:message code="wizard.button.cancelarTramite" /></button>
					<button type="button" class="btn btn-primary" id="finalizarTramite"><spring:message code="wizard.button.finalizarTramite" /></button>
					</c:if>
				</c:if>
				
			</div>
			
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="idTipoTramiteIvroDomestico" value="<%=TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo()%>" />


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/common/actualizaRfc/actualizaRfc.js" htmlEscape="true" />"></script>

<style>
	.upperCase {
		text-transform: uppercase;
	}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
		
			<input id="idPersonaHidden" type="hidden" value="<c:out value="${idPersona}"/>" /> 
			<input id="rfcRequeridoHidden" type="hidden" value="<c:out value="${rfcRequerido}"/>" />

			<div class="alert alert-info">
				<label>
					<c:choose>
						<c:when test="${rfcRequerido}">Debe capturar su</c:when>
						<c:otherwise>Es opcional la captura del</c:otherwise>
					</c:choose>
					RFC para continuar con el trámite:
				</label>
				<br />
				<br />
				<label>
					<c:choose>
						<c:when test="${identificadorTipoTramite eq idTipoTramiteIvroDomestico}">
						</c:when>
						<c:otherwise>	
							El proporcionar tu RFC nos permitir&aacute; brindarte un mejor servicio, si contamos con tu informaci&oacute;n completa y 
							eres candidato a participar en el programa CREZCAMOS JUNTOS autom&aacute;ticamente se otorgar&aacute;n los beneficios de dicho programa por lo cual te invitamos a proporcionar tu informaci&oacute;n.
						</c:otherwise>
					</c:choose>
				</label>
			</div>

			<div>		
				<form:form name="formaAuxiliarActualizaRfc" modelAttribute="fisica" id="fisica" method="post"
					cssClass="form-horizontal" role="form"
					cssStyle="width: 65%; margin: 25px auto 30px;" >
		 
					<div class="form-group">
						<label for="rfc" class="col-sm-3 control-label">
							<c:choose>
								<c:when test="${rfcRequerido}">
									<span class="required">*</span>RFC:
								</c:when>
								<c:otherwise>
									RFC:
								</c:otherwise>
							</c:choose>
						</label>
						<div class="col-sm-9">
							<form:input path="rfc" maxlength="13"
								cssClass="form-control alfanumericoEstricto upperCase" />
								<span id="rfcError" class="error hiddenElement"></span>
						</div>
					</div>
					
				</form:form>
			</div>
			
		</div>	
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarActualizarRfc" class="btn btn-default">Cancelar</button>
				<button id="aceptaActualizarRfc" class="btn btn-primary">Aceptar</button>
			</div>
		</div>
	</div>
</div>

<div id="dialogoConfirmarOper">
	<p>
		<span id="dialogoMensajeOper"></span>
	</p>
</div>
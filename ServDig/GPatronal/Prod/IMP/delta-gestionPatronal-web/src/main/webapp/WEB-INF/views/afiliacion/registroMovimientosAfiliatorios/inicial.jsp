<!-- JSP Inicial de los Registros de Movimientos Afiliatorios. -->
<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script>
var idSite='${idSite}';

</script>
 <script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/registroMovimientosAfiliatorios/inicial.js" htmlEscape="true" />"></script>


<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span>Registro de Movimientos Afiliatorios.</span>
			</div>
			<div class="descripcion">
				<p>En esta opci&oacute;n podr&aacute;s realizar el registro de Movimientos Afiliatorios: Alta de trabajadores, Baja de Trabajadores y
				Modifcaciones</p>
			</div>
			<div class="opciones">
			
					<c:choose>
						<c:when test="${!solicitudCreada}">
							<button
								class="btn btn-primary btn-block"
								role="button" aria-disabled="false" id="btnInciaTramite">
								<span class="ui-button-text">Iniciar Tr&aacute;mite</span>
							</button>


							<!--<c:if test="${solicitudMismoOrigen}">
								<button
									class="ui-button-primary btn-block ui-button ui-widget ui-state-default  ui-button-text-only"
									role="button" aria-disabled="false" id="btnRetomarTramite">
									<span class="ui-button-text">Retomar Solicitud</span>
								</button>
							</c:if>
						<button
								class="btn-block ui-button ui-widget ui-state-default  ui-button-text-only"
								role="button" aria-disabled="false" id="btnCancelarTramite">
								<span class="ui-button-text">Cancelar Solicitud</span>
							</button>  -->
						</c:when>

					</c:choose>
					
					<button
						class="btn btn-default btn-block"
						role="button" id="btnInicioCancelarTramite">
						<span class="ui-button-text">Cancelar</span>
					</button>
		
				
				
			</div>
		</div>
		
		<div class="instrucciones col-sm-8">
			<h3>Instrucciones :</h3>
				<c:choose>
					<c:when test="${!existenPendientes}">
					</c:when>
					<c:otherwise>
						<div class="alert alert-info">
							Usted ya cuenta con una solicitud para <strong>EL REGISTRO DE MOVIMIENTOS AFILIATORIOS</strong> en proceso.
						</div>	
					</c:otherwise>
				</c:choose>

				<div>
					<div>
						Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.<br>
					</div>
					<div>
						1. Selecciona la opci&oacute;n iniciar solicitud.<br> 2. Los campos marcados con un asterisco (*) son datos obligatorios.
					</div>
				</div>			
		</div>
	</div>
	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<form:form id="formIniciaTramite" method="post" modelAttribute="sujetoTramite">
		
</form:form> 

  







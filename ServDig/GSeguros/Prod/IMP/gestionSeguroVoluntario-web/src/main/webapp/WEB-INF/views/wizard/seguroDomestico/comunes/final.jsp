<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/comunes/final.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	tipoOperacion = '${tipoOperacion}';
	ventanilla = ${esVentanilla};
</script>


<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	.container-fluid {
		padding-bottom: 10px;
		font-size: 13px;
	}
	
	.contenedor .pie .opciones {
	    float: left;
	}
	
	.contenedor .pie .controles {
	    float: right;
	}

</style>

<div class="contenedor container-fluid">
	<div class="contenedor row">
		<div class="col-xs-12 form-horizontal">
			<div class="titulo separadorseccion">
				<span>Seguro Registrado</span>
			</div>
			<p>
				Tu solicitud de <c:choose><c:when test="${empty tipoOperacion}">compra</c:when><c:otherwise>renovaci&oacute;n</c:otherwise></c:choose> ha sido registrada.
				El folio de la operaci&oacute;n es: <strong>${solicitud.numSolicitud}</strong>. Puedes imprimir el comprobante de tu tr&aacute;mite <c:if test="${not empty existenCuestionarios && existenCuestionarios}">y los cuestionarios de salud de tus empleados </c:if>desde la lista de acciones en la parte
				inferior de esta pantalla.
			</p>
			<p>
				Te recordamos que para activar tu seguro debes imprimir tus l&iacute;neas de captura y realizar el pago correspondiente.
			</p>
			<p>
				Puedes imprimir tus l&iacute;neas de captura en la secci&oacute;n "<strong>Seguros Dom&eacute;sticos</strong>" mediante la opci&oacute;n "<strong>Ver detalle</strong>".
			</p>
			<form:form id="imprimirComprobanteForm" action="${contextPath}/wizard/seguroDomestico/comunes/generarComprobantes" target="_blank">
			</form:form>
			<!--<form:form id="imprimirCuestionarioForm" action="${contextPath}/wizard/seguroDomestico/comunes/generarCuestionario" target="_blank">
			</form:form> -->
		</div>
	</div>
	<div class="pie">
		<div class="opciones">
			<div class="btn-group">
				<button type="button" class="btn btn-primary">Acciones</button>
				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a id="imprimirComprobante" href="#"><i class="icon-printing"></i> Imprimir Comprobante</a></li>
					<!--<c:if test="${not empty existenCuestionarios && existenCuestionarios}">
						<li><a id="imprimirCuestionario" href="#"><i class="icon-printing"></i> Imprimir Cuestionarios de salud</a></li>
					</c:if>-->
				</ul>
			</div>
		</div>
		<div class="controles">
			<button id="cerrarWizard" class="btn btn-default">Cerrar</button>
		</div>
	</div>
</div>

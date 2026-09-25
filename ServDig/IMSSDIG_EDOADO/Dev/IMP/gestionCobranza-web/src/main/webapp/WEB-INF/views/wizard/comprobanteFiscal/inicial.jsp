<!-- JSP Contenido del Widget de Comprobante Fiscal. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/comprobanteFiscal/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
label {
	display: inline;
}

.table_form table {
	margin: 15px auto;
}

.table_form table tr td {
	padding: 5px 10px;
}

textarea {
	height: 100%;
}

input,textarea,.uneditable-input {
	width: auto;
	text-transform: uppercase;
}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span> Descarga de Comprobantes Fiscales</span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					descargar los comprobantes fiscales de acuerdo al per&iacute;odo.</p>
			</div>

			<div class="opciones">
				<div style="max-width: 400px;">
					<button
						class="btn btn-primary btn-block" id="btnInciaTramite">
						<span class="ui-button-text">Iniciar Tr&aacute;mite</span>
					</button>
					<button class="btn btn-default btn-block" id="btnInicioCancelarTramite">
						<span class="ui-button-text">Cancelar</span>
					</button>
				</div>
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<ul>
				<li>
					<p>Obtenci&oacute;n de Comprobantes Fiscales</p>
					<p>
						1. Podr&aacute;s descargar los comprobantes fiscales realizados de un per&iacute;odo en formato XML.<br>
						2. Podr&aacute;s descargar la representaci&oacute;n gr&aacute;fica (PDF) del comprobante fiscal de un per&iacute;odo
					</p>
					<p>Instrucciones:</p>
					<p>
						1. Para poder continuar es necesario proporcionar el
						per&iacute;odo de pago de los comprobantes fiscales.
					</p>
				</li>
			</ul>
			<div class="well" style="background-color: white;" >
				<form:form modelAttribute="pagoFiscal" id="busquedaComprobanteFiscal" method="post"
					action="${contextpath}/wizard/comprobanteFiscal/busqueda/comprobanteFiscal">
					<div id="seccionPeriodoPago" class="table_form">

						<select id="anioPeriodo">
							<c:forEach var="anioPeriodo" items="${listAnioPeriodo}" varStatus="indice">
								<option value="${anioPeriodo.key}">${anioPeriodo.value}</option>
							</c:forEach>
						</select>
						<span> - </span>
						<select id="mesPeriodo">
							<c:forEach var="mesPeriodo" items="${listMesPeriodo}" varStatus="indice">
								<option value="${mesPeriodo.key}">${mesPeriodo.value}</option>
							</c:forEach>
						</select>

						<form:hidden path="rfc" />
						<form:hidden path="numeroRegistroPatronal" />
						<input type="hidden" id="periodo" name="periodo" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<br>
	<div class="pie row">
	</div>
</div>
<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/validatesIMSS.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/funcionesComunes.js" htmlEscape="true" />"></script>

<style>
h4.subtitulo {
	margin-top: 30px;
	margin-bottom: 30px;
}

.errorDocs {
	font-size: small;
}

.subtitulo {
	margin-top: 30px;
	margin-bottom: 30px;
}

input, textarea, .uneditable-input {
	width: auto;
	text-transform: uppercase;
}

td.dt-center {
	text-align: center;
}

.table-word-wrap-fixed {
	table-layout: fixed;
	word-wrap: break-word !important;
}

#selectable .ui-selecting {
	background: #1A79A7;
	color: white;
}

#selectable .ui-selected {
	background: #428BCA;
	color: white;
}

#selectable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 100%;
	cursor: pointer
}

#selectable li {
	margin: 3px;
	padding: 0.4em;
	color: #67666A;
}

a:active {
	outline: none;
}

a:focus {
	-moz-outline-style: none;
}

.icono-tramite {
	font-size: 2em;
}

.icono-tramite a {
	color: #545454;
	text-decoration: none;
}

.icono-tramite a:hover {
	color: black;
}

::-webkit-input-placeholder { /* Chrome/Opera/Safari */
	text-transform: none;
}

::-moz-placeholder { /* Firefox 19+ */
	text-transform: none;
}

:-ms-input-placeholder { /* IE 10+ */
	text-transform: none;
}

:-moz-placeholder { /* Firefox 18- */
	text-transform: none;
}

::-webkit-input-placeholder.form-control { /* Chrome/Opera/Safari */
	text-transform: none;
}

::-moz-placeholder.form-control { /* Firefox 19+ */
	text-transform: none;
}

:-ms-input-placeholder.form-control { /* IE 10+ */
	text-transform: none;
}

:-moz-placeholder.form-control { /* Firefox 18- */
	text-transform: none;
}
</style>

<%--breadcrumb --%>
<ol class="breadcrumb">
  <li><a url="http://www.imss.gob.mx"><i class="icon icon-home"></i></a></li>
  <li><a url="http://www.imss.gob.mx/servicios-digitales">Tr&aacute;mites</a></li>
  <li class="active"><spring:message code="label.tramite.alta.moral.titulo" /></li>
</ol>
<%--Division del titulo --%>
<h3><spring:message code="label.tramite.alta.moral.titulo" /></h3>
<h5>(<spring:message code="label.tramite.alta.moral.subtitulo"/>)</h5>
<hr class="red" style="margin-bottom: 30px;">
	
	

<ul class="wizard-steps-extensive" id="pasosTramite">
	<li class="completed">
		<h5>1</h5> <span>Registrar empresa representada</span>
	</li>
	<li>
		<h5>2</h5> <span>Revisar tus datos fiscales</span>
	</li>
	<li>
		<h5>3</h5> <span>Registrar domicilio del centro de trabajo</span>
	</li>
	<li>
		<h5>4</h5> <span>Seleccionar giro de la empresa</span>
	</li>
	<li>
		<h5>5</h5> <span>Registrar recursos materiales y humanos</span>
	</li>
	<li>
		<h5>6</h5> <span>Registrar personas autorizadas</span>
	</li>
	<li>
		<h5>7</h5> <span>Registrar escritura constitutiva o sindicato</span>
	</li>
	<li>
		<h5>8</h5> <span>Generar tu n&uacute;mero de registro patronal</span>
	</li>
</ul>

	
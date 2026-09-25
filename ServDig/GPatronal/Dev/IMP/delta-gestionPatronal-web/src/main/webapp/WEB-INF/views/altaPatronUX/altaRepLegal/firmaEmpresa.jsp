<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstatusPersona"%>
<%@ page
	import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page
	import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page
	import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<%--Script del componente de firma digital para inclustarlo en la misma pantalla --%>
<script type="text/javascript"
	src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigitalPlugin.js"></script>
<%--script de control de la pantalla de login --%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/altaPatronUX/altaRepLegal/firmaEmpresa.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12" id="leyenda1">
		<div class="row">
			<div class="col-sm-12">
				<h4>1. Registrar representante legal</h4>
				<h5>Paso 2 de 2 : Registra al representante legal de tu empresa</h5>
				<h5>Para añadir esta persona a tu registro patronal, la empresa
					representada y el representante legal deber&aacute;n firmar con FIEL.</h5>
			</div>
		</div>
	</div>
	<div class="col-sm-12" id="leyenda2">
		<div class="row">
			<div class="col-sm-12">
				<h5>Para completar el registro, ahora es necesario que el
					representante legal firme con FIEL.</h5>
			</div>
		</div>
	</div>
</div>
<div class="row" id="firmaElectronicaEmpresa" style="text-align: center">Espere
	mientras el componente de firma electr&oacute;nica es cargado...</div>
<div id="contenedorDoctosEmpresa"></div>
<div id="dialog-confirm-RepLegal" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogoRepLegal"></label>
	</p>
</div>
<div class="col-sm-12" id="divTerminosCondiciones">
	<div class="row">
		<div class="col-sm-12">
			<h5> <input type="checkbox" id="checkTermCond"> Declaramos que hemos le&iacute;do y conocemos los T&eacute;rminos y
				Condiciones, as&iacute; como las Reglas de car&aacute;cter general para el uso de
				la Firma Electr&aacute;nica Avanzada, cuyo certificado digital se ha
				emitido por el Servicio de Administraci&aacute;n Tributaria, en los actos
				que se realicen ante el Instituto Mexicano del Seguro Social, y que
				voluntariamente aceptamos los alcances legales de los mismos,
				mediante nuestras firmas electr&aacute;nicas FIEL. <a onclick="muestraCarta();">Ver Carta de T&eacute;rminos y
				Condiciones CTC. </a></h5>
		</div>
		<div class="form-group">
				<div class="col-sm-12 text-right">
				<button id="btnContinuarTermCond" onclick="sigFirmaRep();" class="btn btn-primary" style="display: inline-block;">Continuar</button>
				</div>
			</div>
	</div>
</div>
<form method="post" target="_blank" id="formTerminos">
</form>
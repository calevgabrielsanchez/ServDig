<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/asignacionNSS/asignacionNSSWizard.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>

<script type="text/javascript">
	
	$(document).ready(function (){
		WizardAsignacionNSSCtrl.init('dlgAsignarNSS');
		
		$('#initAsignarNSS').click(function(){
			WizardAsignacionNSSCtrl.asignarNSS();	
		});
		
		// Incluimos el JS de Domicilios.
		$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js", function(){
			DomicilioCtrl.init('dialogoDomicilios');
		});
		
		// Se incializa el componente de firma digital
		FirmaDigitalCtrl.init('firmaDigitalComponent', 'doctosRequeridosTramite');
	});
	
	// Función común para inicar el componente de Firma Digial
	var iniciarFirmaDigital = function (componenteFirma) {
		//Se settean los valores de entrada
		FirmaDigitalCtrl.datosEntrada.rfc = componenteFirma.rfc;
		FirmaDigitalCtrl.datosEntrada.contenido = componenteFirma.cad_original;
		FirmaDigitalCtrl.datosEntrada.firmarArchivo = componenteFirma.validarRFC;

		FirmaDigitalCtrl.datosEntrada.idTipoSolicitud = componenteFirma.idTipoSolicitud;
		FirmaDigitalCtrl.datosEntrada.descripcionTipoSolicitud = componenteFirma.descripcionTipoSolicitud;
		FirmaDigitalCtrl.datosEntrada.idTipoTramite = componenteFirma.idTipoTramite;

		FirmaDigitalCtrl.datosEntrada.folioSolicitud = componenteFirma.folioSolicitud;
		FirmaDigitalCtrl.datosEntrada.curp = componenteFirma.curp;
		FirmaDigitalCtrl.datosEntrada.registroPatronal = componenteFirma.registroPatronal;
		FirmaDigitalCtrl.datosEntrada.nombreCompleto = componenteFirma.nombreCompleto;
		FirmaDigitalCtrl.datosEntrada.fechaElectronica = componenteFirma.fechaElectronica;

		FirmaDigitalCtrl.firmaDigital();
	};
</script>

<div class="contenedor">

	<div class="contenido">
		<button class="btn btn-primary" id="initAsignarNSS">INICIAR</button>
	</div>

</div>


<div id="dlgAsignarNSS"></div>
<div id="dialogoDomicilios"></div>
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>

var l = null;
var iconFunctionsFinder = {
    patronesIconDiv: {selectorToFocus: 'listaPatronesAsociados',
            shouldBeVisible:'#listaPatronesAsociados .contenido',
            selectorClick: '#listaPatronesAsociados > .cuerpo > .titulo > .controles a.btn.btn-sm:has(i.icon-plus)'},
    datosFiscalesIconDiv   : {selectorToFocus: 'idPersonaIdentidadFiscalWidget',
            shouldBeVisible:'#idPersonaIdentidadFiscalWidget .contenido',
            selectorClick: '#idPersonaIdentidadFiscalWidget .pie > .controles a:has(i.icon-plus)'},
    datosPersonalesIconDiv : {selectorToFocus: 'idPersonaIdentidadWidget',
            shouldBeVisible:'#idPersonaIdentidadWidget .contenido',
            selectorClick: '#idPersonaIdentidadWidget .controles a.btn.btn-sm:has(i.icon-plus)'},
    representantesIconDiv  : {selectorToFocus: 'listaRepresentantesLegales',
            shouldBeVisible:'#listaRepresentantesLegales .contenido',
            selectorClick: '#listaRepresentantesLegales > .cuerpo > .titulo > .controles a.btn.btn-sm:has(i.icon-plus)'},
    empresasIconDiv        : {selectorToFocus: 'listaRepresentados',
            shouldBeVisible: '#listaRepresentados .contenido',
            selectorClick: '#listaRepresentados > .cuerpo > .titulo > .controles > a.btn.btn-sm:has(i.icon-plus)'},
    solicitudesIconDiv : {selectorToFocus: 'listaSolicitudes',
            shouldBeVisible:'#listaSolicitudes .contenido',
            selectorClick: '#listaSolicitudes > .cuerpo > .titulo > .controles > a:has(i.icon-plus)'},
    operate: function (element_key) {
        var object = this[element_key];
        if (!$(object.shouldBeVisible).is(':visible')) {
            $(object.selectorClick).click()
        }
        document.getElementById(object.selectorToFocus).scrollIntoView(false);
    }
}


/**
 * 
 */
$(document).ready(function(){
	
	$.ajaxSetup({ cache: false });
	
    l = $('body');
	$('#cerrarSesionLink').live( 'click' , function(){
		fnAbrirDialogoCerrarSesion();
	});
	
	// Se incializa el componente de firma digital
	FirmaDigitalCtrl.init('firmaDigitalComponent', 'doctosRequeridosTramite');
	
	// Se incializa el componente de domicilios
	DomicilioCtrl.init('domiciliosComponent');

	// Se inicializa el componente de Procesando Solicitud
    ProcesandoSolicitudCtrl.init('procesandoSolicitudComponent', tiempoEspera, tiempoIntervaloEspera);
    ProcesandoSolicitudCtrl.setOnCloseCallback(function() {});

    //subscripcion a canales comet
    //var urlComet = "/delta-comet-web/static/resources/js/delta/comet/CometConector.js";
    
    CometCtrl.prototype.addSubscriber = function(subscription) {
        this.listInitialSubscription.push(subscription);
    };

    
    //Los wizard "WizardDetalleSeguroCtrl y WizardDetalleDomesticoCtrl solo se cargan en home.jsp
    //para el resto de zonas no deben ser inicializados (aseguradoPortal.jsp, derechohabientePortal.jsp, 
    // patronPortal.jsp y personaRepresentadaPortal.jsp)
    if((typeof WizardDetalleSeguroCtrl) !== "undefined"){
    	//Inicializar detalle seguro persona.
    	WizardDetalleSeguroCtrl.init('wizardDetalleSeguroComponent', null);	
    }
    if((typeof WizardDetalleDomesticoCtrl) !== "undefined"){
    	//Inicializar detalle seguro domestico.
    	WizardDetalleDomesticoCtrl.init('wizardDetalleSeguroDomesticoComponent', null, null, null);
    }
	
    var closure_force_logout = function(event, ui) {
        document.location.href = '/portal-web/j_spring_security_logout';
    };

    setTimeout(function(){
        var curp_session = $("#curpPersonaCtrl").val();
        var timeout_subscriber = {
            channel: ['/channels/server/session/', curp_session].join('')
                , action: function(message) {
                    if (message.data.session_finished === true) {
                        $('#dialogEndOfSession').dialog({
                            resizable: false,
                            height:'auto',
                            modal: true,
                            close: closure_force_logout,
                            buttons: {"ACEPTAR": closure_force_logout}
                        });
                    }
                }
        };
        
        widget.init();
        portlet.init();
        var conector = new CometCtrl();
        conector.addSubscriber(widget.createSubscriber());
        conector.addSubscriber(portlet.createSubscriber());
        conector.addSubscriber(timeout_subscriber);
        conector.init(false);
    }, 1100);

    var idPersona = $("#hdnIdPersona").val();
    // peticion para obtener el detalle de la persona firmada
    var urlPersona = "/gestionIndividuo-consulta-web/servicios/internos/persona/fisica/" + idPersona; 
	$.get( urlPersona , null, function(data){
		$("#resumenpersona").html(data);
	}).complete(function() {
        $('a.dropdown-toggle + ul.dropdown-menu > li > div div[class^=span]').tooltip({placement: 'left', trigger: 'hover'})
    });

        setTimeout(function(){
            $.ajax({
                url: '/portal-web/portal/?issessionalive=',
                data: {hash: new Date().getTime(), intent: 0},
                success: function(data) {
                    result = /true/.test($.trim(data));
                    if (!result) {
                        window.location.href='/portal-web/portal/';
                    }
                },
                type: 'post',
                error: function() {
                    window.location.href='/portal-web/portal/';
                }
            });
        }, 1000);

    $('div[id$=IconDiv]').click(function() {
        iconFunctionsFinder.operate($(this).attr('id'))
    }).css('cursor', 'pointer')


    setTimeout(function(){
        var rfc = $("#hdnRfcPersonaRep").val();
        // peticion para obtener el detalle de la persona firmada
        var urlBuzon = "/portal-web/portal/consultaRfcEnBuzonTributario";
        $.postJSON(urlBuzon , rfc, function(result){
            if(result.exito){
                /**Se cambia a peticion del usuario para solo mostar una imagen no requiere datos */
				// fnAbrirDialogoBuzonTributario(result.msjBuzon,result.razonSocial, rfc);
				fnAbrirHtmlBuzonTributario();
				
            }
        });
    }, 6000);
    
    var actualizaRfcUsuario = $("#hdnActualizaRfcUsuario").val();
    if(actualizaRfcUsuario== "true"){
    	var rfcActualziar =sessionStorage.getItem('rfcCertificado');
    	var urlActualizar = "/portal-web/portal/actualizaRfcUsuario?rfc="+rfcActualziar;
        $.ajax({
	        url: urlActualizar,
	        type: 'GET',
	        error: function() {
		    },
		    success: function(data) {
		    }
	    });
    }
    
    
});

 //Funcion inicializar la firma digital
var iniciarFirmaDigital = function (componenteFirma) {
    //Se settean los valores de entrada
    FirmaDigitalCtrl.datosEntrada.rfc = componenteFirma.rfc;
    FirmaDigitalCtrl.datosEntrada.val_rfc = componenteFirma.validarRFC;
    FirmaDigitalCtrl.datosEntrada.cad_original = componenteFirma.cad_original;
    FirmaDigitalCtrl.datosEntrada.firma_archivo = componenteFirma.firma_archivo;
    FirmaDigitalCtrl.datosEntrada.min_archivos = componenteFirma.min_archivos;
    FirmaDigitalCtrl.datosEntrada.max_archivos = componenteFirma.max_archivos;
    
    FirmaDigitalCtrl.datosEntrada.origen = server_name;
    FirmaDigitalCtrl.datosEntrada.operacion = componenteFirma.tipo_operacion;//"autentica";
    FirmaDigitalCtrl.datosEntrada.aplicacion="portalimssdigital";
    FirmaDigitalCtrl.datosEntrada.salida="rfc,curp,rfc_rl,curp_rl,serie_cert,contenedores,acuse,firmas,vigencias,vigIni,vigFin";   
    FirmaDigitalCtrl.datosEntrada.acuse = 'AcuseV1.0';

    FirmaDigitalCtrl.datosEntrada.idTipoSolicitud = componenteFirma.idTipoSolicitud;
    FirmaDigitalCtrl.datosEntrada.descripcionTipoSolicitud = componenteFirma.descripcionTipoSolicitud;
    FirmaDigitalCtrl.datosEntrada.idTipoTramite = componenteFirma.idTipoTramite;

    FirmaDigitalCtrl.datosEntrada.folioSolicitud = componenteFirma.folioSolicitud;
    FirmaDigitalCtrl.datosEntrada.curp = componenteFirma.curp;
    FirmaDigitalCtrl.datosEntrada.registroPatronal = componenteFirma.registroPatronal;
    FirmaDigitalCtrl.datosEntrada.nombreCompleto = componenteFirma.nombreCompleto;
    FirmaDigitalCtrl.datosEntrada.fechaElectronica = componenteFirma.fechaElectronica;
    FirmaDigitalCtrl.datosEntrada.afectado = componenteFirma.afectado;
    FirmaDigitalCtrl.datosEntrada.representados = componenteFirma.representados;
    FirmaDigitalCtrl.datosEntrada.domicilioActualizado= componenteFirma.domicilioActualizado;
    FirmaDigitalCtrl.datosEntrada.afectadoNuevosValores= componenteFirma.afectadoNuevosValores;
    FirmaDigitalCtrl.datosEntrada.mediosContacto= componenteFirma.mediosContacto;
    FirmaDigitalCtrl.datosEntrada.tipoAcuse= componenteFirma.tipoAcuse;
    FirmaDigitalCtrl.datosEntrada.acuse= componenteFirma.acuse;
    
    if(componenteFirma.mostrarCartaTerminos){
        FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos = componenteFirma.mostrarCartaTerminos;
    } else {
        FirmaDigitalCtrl.datosEntrada.mostrarCartaTerminos = false;
    }
	
    FirmaDigitalCtrl.firmaDigital();
	
};

$.dolog = false; //cambiar a true para usar $('body').log(...)
var millis = new Date().getTime();
$.fn.log = function(text) {
    if (!$.dolog) {
        return;
    }
    if (!$("body > #jslogs").size()) {
        var style="width:100%;position:fixed;bottom:0;left:0;right:0;padding:0;font-size:8pt;text-align:right;font-weight:900;color: black;";
        $("body").append(["<div id='jslogs' style='", style, "background-color:#CCC;moz-opacity:0.6;opacity:0.5;filter: alpha(opacity=50)'>",
        , "</div>",
        "<div style='", style, "'></div>"].join(''));
    }
    if ($("#jslogs > span").size() >= 10) {
        $("#jslogs + div > span:first-child").remove();
        $("#jslogs > span:first-child").remove();
    }
    $("#jslogs + div").append(["<span style='display:block;left:0;right:0;margin:0;padding:0;'>", text, "[", new Date().getTime() - millis, "]</span>"].join(''));
    $("#jslogs").append("<span style='display:block;left:0;right:0;margin:0;padding:0;'>&nbsp;</span>");
};


// Funcion general para mostrar el detalle de una solicitud a trav�s de su folio
function ejecutarConsultaSolicitudPorFolio (folio) {
	
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		
		alert('El n\u00FAmero de folio es requerido para la realizar la b\u00FAsqueda.');
		
		return false;
	}
	
	DetalleSolicitudCtrl.init("detalleSolicitudComponent",folio);
	DetalleSolicitudCtrl.abrir();
	
}

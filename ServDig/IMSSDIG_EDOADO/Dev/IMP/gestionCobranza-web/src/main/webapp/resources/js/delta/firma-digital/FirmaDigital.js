var FirmaDigitalCtrl = {

	dialogo : {},
	init : function(idContenedor){
		this.urlFormularioFirma = 'http://${mvn.url.firmadigital}/firmaElectronicaWeb/widget/imss';

	    this.config.contenedor = idContenedor;
	    
	    var d = $('#' + idContenedor);

	    // Configuracion del dialogo
	    this.dialogo = d.dialog({
	        title : this.config.title,
	        dialogClass: "no-close",
	        closeOnEscape: false,
	        autoOpen : false,
	        width : 1000,
	        height : 800,
	        modal : true,
	        resizable : false,
	        autoResize : true,
	        overlay : {
	            opacity : 0.5,
	            background : "black"
	        }
	    });
	    
	    
	    // Configuramos el metodo onClose del dialogo
	    this.dialogo.dialog({
	    	beforeClose : function(event, ui) { 
	    		
	    		FirmaDigitalCtrl.limpiarElementosSesion();
	    		FirmaDigitalCtrl.callbacks.call();
	    }});
	},
	
	// Metodo para invocar la MDM de persona física
	firmaDigital : function() {
		var formFirmaDigital = '<form action="' + this.urlFormularioFirma
		+ '" method="post" target="formFirmaDigital" id="firmarForm" >'
		+ '<input type="hidden" id="params" name="params" readonly="readonly">'
		+ '</form>';
		$('body').append(formFirmaDigital);

		var params = new Object();
		
		params.operacion = this.datosEntrada.operacion;
		params.aplicacion = this.datosEntrada.aplicacion;
		params.acuse = this.datosEntrada.acuse;
		params.rfc = this.datosEntrada.rfc;
		params.val_rfc = this.datosEntrada.val_rfc;
		params.curp = this.datosEntrada.curp;
		params.firma_archivo = this.datosEntrada.firma_archivo;
		params.max_archivos = this.datosEntrada.max_archivos;
		params.min_archivos = this.datosEntrada.min_archivos;
		params.tipo_archivos = this.datosEntrada.tipo_archivos;
		params.cad_original = this.datosEntrada.cad_original;
		params.forma_firma_archivos = this.datosEntrada.forma_firma_archivos;
		params.contenedores = this.datosEntrada.contenedores;
		params.id_firmas = this.datosEntrada.id_firmas;
		params.salida = this.datosEntrada.salida;
		params.origen = this.datosEntrada.origen;

		// Transformar los datos a json
		var json =JSON.stringify(params);
		$('#params').val(json);

		$('#' + this.config.contenedor).html('<iframe id="formFirmaDigital" name="formFirmaDigital" width="100%" height="100%" frameborder="0"/>');
		
		this.setDatosSalida(null);
		$('#firmarForm').submit();
		
		this.dialogo.dialog('open');
	},
	
	// Datos de configuracion inicial de la ICA de persona fisica
	config : {
		url : "/gestionSolicitud-web/firma-digital/init",
//		url : "http://11.254.20.105:8080/webOPE/pages/imss.html",
		title : "Firma Digital",
		contenedor : {}
	},
	
	// Callback a invocar cuando se termine la invocacion de la consulta
	callbacks : {},

	setOnCloseCallback : function(_fnCallback){
		this.callbacks = _fnCallback;
	},
		
	//Objeto con los atributos para los parámetros de entrada
	datosEntrada : {
		operacion: '',
		aplicacion: '',
		origen: '',
		acuse: '',
		rfc: '',
		val_rfc: '',
		curp: '',
		firma_archivo: '',
		max_archivos: '',
		min_archivos: '',
		tipo_archivos: '',
		cad_original: '',
		forma_firma_archivos: '',
		contenedores: '',
		id_firmas: '',
		salida: ''
	},
	
	//Objeto de salida
	datosSalida : {},
	
	setDatosEntrada : function(objDatosEntrada){
		this.datoEntrada = objDatosEntrada;
	},
	
	getDatosSalida : function (){
		return this.datosSalida;
	},
	
	setDatosSalida : function(objDatosSalida){
		this.datosSalida = objDatosSalida;
	},
	
	// Funcion para el control de cerrado de la pagina
	cerrar : function(){
		this.dialogo.dialog('close');
	},
	
	/*
	 * Función para limpiar los elementos en sesión, se puso aquí
	 * para agregarla en el cerrar del dialogo y seguir permitiendo el
	 * setteo de la función de callback de cada gestión en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion: function (){
		
		// Se limpia la sesión de la administración de domicilios
		$.postJSON('/gestionSolicitud-web/firma-digital/limpiar-firma-digital', null, function(data) {
			
		}).error(function(data){
			
		});	
	}
};

function resultadoFirmaDigital(event) {
    
    if (event.origin == "http://${mvn.url.firmadigital}") {
    	//Los datos de salida vienen como un string por lo hace falta convertirlo a jSON
        var resultadoJSON = $.parseJSON(event.data);
        FirmaDigitalCtrl.setDatosSalida(resultadoJSON);
        FirmaDigitalCtrl.cerrar();
    } 
}

if (window.addEventListener) { 
    addEventListener("message", resultadoFirmaDigital, false); 
 } else { 
    attachEvent("onmessage", resultadoFirmaDigital); 
 }
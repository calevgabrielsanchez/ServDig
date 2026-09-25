/*
 * JS de control del Wizard de Registro de Obra.
 */
var dialogoNMPS;
var WizardNMPSCtrl = {
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _rfc, _nrp,_nssCifrado,_contx,_isPatron) {
//		alert(_contenedor+" && "+_rfc+" && "+ _nrp+ " && "+ _nssCifrado + " && "+_contx+ " && "+ _contx);
		this.config.contenedor = _contenedor;
		this.config.rfc = _rfc;
		this.config.nrp = _nrp;
		this.config.urlContexto= _contx;
		this.config.isPatron= _isPatron;
		this.config.nssCifrado= _nssCifrado;

		/*
		 * Se crea una variable para el control del dialogo que sera a traves de
		 * un iFrame
		 */
		var d = $('#' + _contenedor);

		/*
		 * Configuracion del dialogo
		 */
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 900,
			minHeight : 650,
			maxHeight : 900,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position : {
				my : "top",
				at : "top",
				of : window,
				offset : "0 10"
			}
		});
		
		 /*
         * Configuramos el metodo onClose del dialogo
         * 
         */
		this.dialogo.dialog({
			beforeClose : function(event, ui) {
				
			},
			close : function(event, ui) {
				$(this).dialog('destroy').empty();
			}
		});
	},
	/*
	 * 
	 */
	setOnCloseCallback : function(_fnCallback) {
		this.callbacks = _fnCallback;
	},
	/*
	 * Datos de configuracion inicial de la consulta de la persona moral.
	 */
	config : {
		title : "IMSS Digital",
		contenedor : {},
		nrp : "",
		rfc : "",
		isPatron:"",
		nssCifrado:"",
		urlContexto:""
	},
	/*
	 * Callback a invocar cuando se 
	 * termine la invocacion de la consulta
	 */
	callbacks : {},

	dialogo : {},
	/**
	 * 
	 */
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		
		this.init(this.config.contenedor, this.config.rfc, this.config.nrp,this.config.nssCifrado,this.config.urlContexto,this.config.isPatron);
		
		this.dialogo.dialog('open');
		var url;
		
		if(this.config.isPatron){
			url = this.config.urlContexto + "?" + "rfc=" + this.config.rfc + "&registroPatronal=" + this.config.nrp;
		}else{
			url = this.config.urlContexto + "?" + "nss=" + this.config.nssCifrado+"&rfc=" + this.config.rfc;
		}
		
		
		$('#' + this.config.contenedor).html(
						'<iframe id="nmpsFrame" src="' + url
								+ '" width="100%" height="800px" frameborder="0"/>');
	},
	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
	}
};

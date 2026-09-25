
/*
 * JS de control del Wizard de SIST.
 */
var tokenSist = $('#tokenSISTContent').data('token-sist');
var tokenParamemetro = '?tokenSIST=' + encodeURIComponent(tokenSist);
var dialogoSIST;
var WizardSISTCtrl = {
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _contx) {
//		alert(_contenedor);
		this.config.contenedor = _contenedor;
		this.config.urlContexto= _contx;
		
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
			width : 1400,
			minHeight : 750,
			maxHeight : 1200,
			modal : true,
			resizable : true,
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
		title : "Captura de datos complementarios ST-7 y ST-9",
		contenedor : {},
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
		
		this.init(this.config.contenedor, this.config.urlContexto);
		
		this.dialogo.dialog('open');
		var url = this.config.urlContexto 
		
		$('#' + this.config.contenedor).html(
						'<iframe id="SISTFrame" src="' + url
								+ '" width="100%" height="800px" frameborder="0"/>');
	},
	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
	}
};


$("#idLnkSISTAccesoDev").live('click', function() {
	WizardSISTCtrl.init('wizardSISTDiv', '/dspatev');
	WizardSISTCtrl.abrir();
});


$("#idLnkSISTAccesoQa").live('click', function() {
	WizardSISTCtrl.init('wizardSISTDiv', '/patevqa');
	WizardSISTCtrl.abrir();
});


$("#idLnkSISTAccesoUat").live('click', function() {
	WizardSISTCtrl.init('wizardSISTDiv', '/patevuat');
	WizardSISTCtrl.abrir();
});

$("#idLnkSISTAccesoProd").live('click', function() {
	WizardSISTCtrl.init('wizardSISTDiv', '/patev');
	WizardSISTCtrl.abrir();
});



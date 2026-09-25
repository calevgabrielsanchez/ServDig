var AcuseCtrl = {
	init : function(_contenedor) {
		this.config.contenedor = _contenedor;

		var d = $('#' + _contenedor);
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 1100,
			height : 600,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			}
		}).width(1100).height(600);

		this.dialogo.dialog({
			beforeClose : function(event, ui) {
				AcuseCtrl.callbacks.call(AcuseCtrl.acuse);
			}
		});
	},

	setOnCloseCallback : function(_fnCallback) {
		this.callbacks = _fnCallback;
	},

	config : {
		url : context_path + '/clasificacion/presentarAcuse',
		title : "Imprimir acuse",
		contenedor : {}
	},

	callbacks : {},

	dialogo : {},

	acuse : {},

	mostrarAcuse : function() {
		//Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor);
		this.dialogo.dialog('open');

		$('#' + this.config.contenedor).html('<iframe id="site" src="' + this.config.url
				+ '" width="100%" height="100%" frameborder="0"/>');
	},

	cerrar : function() {
		//Cerramos el dialogo
		this.dialogo.dialog('close');

		//Destruimos el dialogo
		this.dialogo.dialog('destroy');
	}
};
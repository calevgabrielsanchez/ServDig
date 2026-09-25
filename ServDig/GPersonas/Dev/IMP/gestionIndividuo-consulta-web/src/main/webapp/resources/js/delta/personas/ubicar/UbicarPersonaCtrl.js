var UbicarPersonaCtrl = {
	dialogo : {},

	init : function(_divContenedor) {
		this.config.divContenedor = _divContenedor;

		var d = $('#' + _divContenedor);

		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 1250,
			left  : '60px',
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

		this.dialogo.dialog({
			/*
			 * beforeClose : function(event, ui) {
			 * UbicarPersonaCtrl.callbacks.call(DomicilioCtrl.domicilio); },
			 */
			close : function(event, ui) {
				$(this).dialog('destroy').empty();
			}
		});
	},

	config : {
		divContenedor : '',
		url : "/gestionIndividuo-consulta-web/ubicar/persona/iniciar",
		title : "Ubicar Persona",
	},

	ubicar : function() {
		this.init( this.config.divContenedor);
		
		this.dialogo.dialog('open');

		$('#' + this.config.divContenedor)
				.html('<iframe id="ubicarPersonaFrame" src="'
					+ this.config.url
					+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'ubicarPersonaFrame\', 900)"/>');

	},

	cerrar : function() {
		this.dialogo.dialog('close');
	}
};
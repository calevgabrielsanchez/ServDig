var WizardIVRORenovacionSeguroDomesticoCtrl = {
		
	setRfc :  function(rfc){
		this.config.rfc = rfc;
	},
		
	init : function(_container, _idPersona, _rfc, _idSeguro) {
		//this.config.url = _url;
		this.config.container = _container;
		this.config.idPersona = _idPersona;
		this.config.rfc = _rfc;
		this.config.idSeguro = _idSeguro;
		var c = null;
		if($('#'+_container).lenght == 0)
			c = $('#'+ _container, parent.document);
		else
			c = $('#'+ _container);
		this.dialogo = c.dialog({
				title : this.config.title,
				autoOpen : false,
				width : 900,
				modal : true,
				resizable : false,
				autoResize : true,
				overlay : {
					opacity : 0.5,
					background : 'black'
				},
				position : {
					my : 'top',
					at : 'top',
					of : window,
					offset : '0 10'
				}
			});
		this.dialogo.dialog({
			beforeClose: function(event, ui) {
				//$('iframe#wizardRenovacionSeguroDomesticoFrame').attr("src", "");
				var windowFrame = window.frames['wizardRenovacionSeguroDomesticoFrame'].contentWindow;
				if(windowFrame === undefined || windowFrame == null)
					windowFrame = window.frames['wizardRenovacionSeguroDomesticoFrame'].frameElement.contentWindow;
				var cancel = windowFrame.cancelable;
				if(cancel !== undefined && cancel == true) {
					event.preventDefault();
					windowFrame.cancelarDesdeBoton = false;
					windowFrame.dialogoConfirmarCancelar.dialog('open');
				}
			},
			close : function(event, ui) {
				limpiarDatos(function() { $(this).dialog('destroy').empty(); });
			}
		});
	},
	open : function() {
		this.dialogo.dialog('open');
		var url = this.config.url + '/' + this.config.idPersona + '/' + this.config.rfc + '/' + this.config.idSeguro;
		$('#' + this.config.container).html('<iframe id="wizardRenovacionSeguroDomesticoFrame" src="' + url
				+ '" width="100%" height="100%" frameborder="0"'
				+ 'onload="set_size(\'wizardRenovacionSeguroDomesticoFrame\')" frameborder="0" />');
	},
	close : function() {
		if(!$.isEmptyObject(this.dialogo)) {
			this.dialogo.dialog('close');
		} else {
			$('#wizardRenovacionSeguroDomesticoFrame').parent().dialog('close');
		}
	},
	abrir : function() {
		this.open();
	},
	cerrar : function() {
		this.close();
	},
	config : {
		url : '/${mvn.web.app.root}/wizard/seguroDomestico/renovacion/init',
		title : 'Renovaci\u00F3n de Incorporaci\u00F3n Voluntaria de Trabajador Dom\u00E9stico',
		container : null,
		idPersona : null,
		rfc: null,
		idSeguro : null
	},
	dialogo : {}
};
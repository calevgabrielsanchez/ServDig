function limpiarDatos(listener) {
	$.ajax('/${mvn.web.app.root}/wizard/seguroDomestico/comunes/limpiarDatos').done(listener);
}

var WizardDetalleDomesticoCtrl = {
		
	setRfc :  function(rfc){
		this.config.rfc = rfc;
	},
	
	setDatos : function(_idPersona, _rfc, _nssCifrado) {
		this.config.idPersona = _idPersona;
		this.config.rfc = _rfc;
		this.config.nssCifrado = _nssCifrado;
	},
	
	init : function(_container, _idPersona, _rfc, _nssCifrado) {
		//this.config.url = _url;
		this.config.container = _container;
		this.config.idPersona = _idPersona;
		this.config.rfc = _rfc;
		this.config.nssCifrado = _nssCifrado;
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
				$('iframe#wizardDetalleDomesticoCtrlFrame').attr("src", "");
				var windowFrame = window.frames['wizardDetalleDomesticoCtrlFrame'].contentWindow;
				if(windowFrame === undefined || windowFrame == null)
					windowFrame = window.frames['wizardDetalleDomesticoCtrlFrame'].frameElement.contentWindow;
				var cancel = windowFrame.cancelable;
				if(cancel !== undefined && cancel == true) {
					event.preventDefault();
					windowFrame.cancelarDesdeBoton = false;
					windowFrame.dialogoConfirmarCancelar.dialog('open');
				}
			},
			close : function(event, ui) {
				limpiarDatos(function() { 
					if(!$.isEmptyObject(this.dialogo)) {
						$(this).dialog('destroy').empty();
					} else {
						$('#wizardDetalleDomesticoCtrlFrame').parent().dialog('destroy').empty();
					}
				});
			}
		});
	},
	abrir : function() {
		this.init(this.config.container, this.config.idPersona, this.config.rfc, this.config.nssCifrado);
		this.dialogo.dialog('open');
				
		var url = this.config.url + '/' + this.config.idPersona + '/' + this.config.rfc + '/' + this.config.nssCifrado+'';
		$('#' + this.config.container).html('<iframe id="wizardDetalleDomesticoCtrlFrame" src="' + url
				+ '" width="100%" height="100%" frameborder="0"'
				+ 'onload="set_size(\'wizardDetalleDomesticoCtrlFrame\')" frameborder="0" />');
	},
	cerrar : function() {
		this.close();
	},	
	close : function() {
		WizardDetalleDomesticoCtrl.setDatos(null, null, null);
		
		if(!$.isEmptyObject(this.dialogo)) {
			this.dialogo.dialog('close');
		} else {
			$('#wizardDetalleDomesticoCtrlFrame').parent().dialog('close');
		}
	},
	config : {
		url : '/${mvn.web.app.root}/wizard/seguroDomestico/ventanilla/init',
		title : 'Incorporaci\u00F3n Voluntaria de Trabajadores Dom\u00E9sticos',
		container : null,
		idPersona : null,
		rfc: null
	},
	dialogo : {}
};
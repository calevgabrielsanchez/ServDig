/*
 * JS de control del Wizard de Actualizacion de Datos
 * de Persona.
 */
var WizardActualizacionDatosCtrl = {
	init : function(_contenedor, _parametros) {
		var _this = this;
		this.config.contenedor = _contenedor;

		$.each(this.params, function(key) {
			if (_parametros[key] != null
					&& typeof _parametros[key] !== 'undefined') {
				_this.params[key] = _parametros[key];
			}
		});
	},

	config : {
		url : '/wizard/tramite/actualizar/datos',
		title : 'IMSS Digital',
		contextPath : '/${mvn.web.app.root}',
		idOrigen : '${mvn.web.app.origin.id}',
		contenedor : null,
		iframeId : 'actualizarDatosFrame',
		formId : 'actualizarDatosWizardForm'
	},

	params : {
		idPersonaSesion : null,
		idTipoPersona : null,
		idPersona : null,
		curp : null,
		rfc : null,
		idPersonaInteresadaSol : null,
		consultaRenapo : true,
		consultaSat : true
	},

	dialogo : {},

	abrir : function() {
		var _this = this;
		var _container = $('#' + this.config.contenedor);

		_container.empty();
		
		this.dialogo = _container.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 900,
			modal : true,
			resizable : false,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position : {
				my : "top",
				at : "top",
				of : window,
				offset : "0 10"
			},
			beforeClose : function(event, ui) {
				_this.limpiarElementosSesion();
			},
			close : function(event, ui) {
				$(this).dialog('destroy').empty();
			}
		});
		
		_container.append($('<iframe />', {
			id : this.config.iframeId,
			name : this.config.iframeId,
			width : '100%',
			height : '100%',
			frameborder : 0,
			onload : 'set_size(\'' + this.config.iframeId + '\')'
		}), $('<form />', {
			action : this.config.contextPath + this.config.url,
			method : 'POST',
			id : this.config.formId,
			target : this.config.iframeId
		}));

		var _form = $('form#' + this.config.formId);

		$.each(this.params, function(key, value) {
			_form.append($('<input />', {
				type : 'hidden',
				name : key,
				value : value
			}));
		});
		
		_form.submit();
		this.dialogo.dialog('open');
		_form.remove();

	},

	cerrar : function() {
		this.dialogo.dialog('close');
	},

	limpiarElementosSesion : function() {
		$.ajax(this.config.contextPath
				+ '/wizard/tramite/actualizar/datos/limpiar-sesion');
	}
};
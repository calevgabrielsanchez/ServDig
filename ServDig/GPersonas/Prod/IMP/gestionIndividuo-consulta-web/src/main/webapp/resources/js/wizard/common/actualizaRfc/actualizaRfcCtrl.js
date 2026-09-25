var WizardActualizaRfcCtrl = {
	
	wizardPrincipal : null,
	
	setWizardPrincipal : function(wizardPrincipal){
		WizardActualizaRfcCtrl.wizardPrincipal = wizardPrincipal;
	},
	
	setRfc :  function(rfc){
		this.config.rfc = rfc;
	},
	
	setSolicitarRfc :  function(solicitarRfc){
		this.config.solicitarRfc = solicitarRfc;
	},
	
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _idPersona, _rfcRequerido, _tipoTramite) {
		
		this.config.contenedor = _contenedor;
		this.config.idPersona = _idPersona;
		this.config.rfcRequerido = _rfcRequerido;
		this.config.tipoTramite = _tipoTramite;
		this.config.contextPath = '/${mvn.web.app.root}';
		this.config.idOrigen = '${mvn.web.app.origin.id}';
		this.config.rfc = "";
		
		var d = null;		
		if ($('#' + _contenedor).length == 0) {
			d = $('#' + _contenedor, parent.document);
		} else {
			d = $('#' + _contenedor);
		}

		/*
		 * Configuracion del dialogo
		 */
        this.dialogo = d.dialog({
            title: this.config.title,
	        closeOnEscape: false,
            width : 900,
            modal: true,
            resizable: false,
            autoResize : true,
            autoOpen : false,
            open: function(event) { $(".ui-dialog-titlebar-close", $(this).parent()).hide(); },
            overlay: {
                opacity: 0.5,
                background: "black"
            },
            position: { my: "top", at: "top", of: window, offset: "0 10" }
        });

		/*
		 * Configuramos el metodo onClose del dialogo
		 * 
		 */
        this.dialogo.dialog({
        	beforeClose : function(event, ui) {
        		
        		$('iframe#actualizaRfcFrame').attr("src", "");
        		
        		WizardActualizaRfcCtrl.limpiarElementosSesion();
			},
        	close: function (event, ui) {
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
		url : '/${mvn.web.app.root}/wizard/actualizaRfc/init/',
		title : "Actualizar RFC ",
		contenedor : {},		
		idOrigen : "",
		contextPath : "",
		
		idPersona : "",
		rfc : "",
		tipoTramite  : 0,
		rfcRequerido : false,	//Indicador para verificar si el RFC sera Requerido u Opcional.				
		solicitarRfc : false	//Indicador para verificar si se solcita o no, la actualizacion del RFC.
	},
	/*
	 * Callback a invocar cuando se termine la invocacion de la consulta
	 */
	callbacks : {},

	dialogo : {},

	/**
	 * 
	 */
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor, this.config.idPersona, this.config.rfcRequerido, this.config.tipoTramite);
		this.dialogo.dialog('open');
		
		var url = this.config.url + this.config.idPersona + "/" + this.config.rfcRequerido + "/" + this.config.tipoTramite;
		
		$('#' + this.config.contenedor).html(
				'<iframe id="actualizaRfcFrame" src="' + url
						+ '" width="100%" height="100%" frameborder="0"'
						+ 'onload="set_size(\'actualizaRfcFrame\')" frameborder="0" />');

	},
	
	validarSolicitarRfc : function(paramIdPersona) {
		$.blockUI();
		$.ajax({		
			url: '/${mvn.web.app.root}/wizard/actualizaRfc/validaSolicitarRfc',		
			type: 'post',
			async: false,
			dataType: 'json',
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				idPersona : paramIdPersona
			}),
			success: function(response) {
				WizardActualizaRfcCtrl.setSolicitarRfc(response.solicitarRfc);
				$.unblockUI();
				//console.log('RESPONSE-SolicitarRfc: ' + WizardActualizaRfcCtrl.config.solicitarRfc);
			},
			error: function(error) {
				console.log('Error: ' + error);
				$.unblockUI();
			}
		});
		
	},

	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
		//this.dialogo.dialog('destroy');
	},
	
	cancelarDialogo : function() {
		var _wizard = null;
		//Cerrar dialogo principal
		if(this.wizardPrincipal != null){
			_wizard = this.wizardPrincipal;
		}		
		if (_wizard != null) {
	    	_wizard.cerrar();
		}
		
		//Cerrar dialogo para actualizar RFC
		WizardActualizaRfcCtrl.cerrar();
	},
	
	concluirActualizarRfc : function() {
		var _wizard = null;
		
		//Abrir dialogo principal
		if(this.wizardPrincipal != null){
			_wizard = this.wizardPrincipal;
		}		
		if (_wizard != null) {
			if(this.config.rfc !=null){
				_wizard.setRfc(this.config.rfc);			
				if(this.config.idOrigen == 6){
					//Si es Ciudadano, actualizar el campo RFC de la pantalla "principal.jsp"
					if($('#rfc').val() != undefined )
						$('#rfc').val(this.config.rfc);
				}
			}
			
			_wizard.abrir();
		}
		//Cerrar dialogo para actualizar RFC
		//se acttualiza version 
		WizardActualizaRfcCtrl.cerrar();
	},
	
	limpiarElementosSesion : function() {
		$.postJSON(this.config.contextPath + '/wizard/actualizaRfc/limpiar-sesion', null, function(data) {
		}).error(function(data){
		});	
	}
		
};
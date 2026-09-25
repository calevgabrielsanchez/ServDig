/*
 * JS de control del Wizard
 */

var WizardCorreccionDerechohabienteCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idAsignacionNss, _nss, _tipoTramite, _idIntegrante,_portalContext, _curpFromListOfCandidates){
			

			this.config.contenedor = _contenedor;
			this.config.idAsignacionNss = _idAsignacionNss;
			this.config.nss = _nss;
			this.config.tipoTramite = _tipoTramite;
			this.config.idIntegrante = _idIntegrante;
			this.config.portalContext = _portalContext;
			this.config.curpFromListOfCandidates = _curpFromListOfCandidates != undefined ? _curpFromListOfCandidates : "";
	        
			/*
			 * Se crea una variable para el control del dialogo
			 * que sera a traves de un iFrame
			 */
	        var d = $('#' + _contenedor);
	        
	        /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = d.dialog({
	            title: this.config.title,
	            closeOnEscape: false,
	            autoOpen: false,
	            width : 900,
	            modal: true,
	            resizable: false,
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
	        	beforeClose: function(event, ui) { 
	        		WizardCorreccionDerechohabienteCtrl.limpiarElementosSesion();
	        	}, 
	        	close: function (event, ui) {
	        		$(this).dialog('destroy').empty();
	        	}
	        });
		},
		/*
		 * 
		 */
		setOnCloseCallback : function(_fnCallback){
			this.callbacks = _fnCallback;
		},
		/*
		 * Datos de configuracion inicial
		 * de la consulta de la persona moral.
		 */
		config : {
			url :  "/${mvn.web.app.root}/wizard/correccion/tramite/",
			title:"IMSS Digital",
			contenedor : {}, 
			idAsignacionNss : "",
			nss: "",
			tipoTramite: "",
			idIntegrante: "",
			curpFromListOfCandidates:"",
			portalContext: ""
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
		abrir : function(){
			
			if(this.config.curpFromListOfCandidates == "") {
				var mensajeError = "<strong>ERROR : </strong> El candidato seleccionado no cuenta con CURP, no es posible realizar el tr&aacute;mite.";
				this.mostrarMensajeError(mensajeError);
				this.dialogo.dialog('open');
				
			} else {
				
				//expresion regular para validar que el curp tenga la estructura correcta
				var characterReg = /^([a-zA-Z]{4})\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$/;
				
				if(!characterReg.test(this.config.curpFromListOfCandidates)){
					var mensajeError = "<strong>ERROR : </strong>La CURP <strong>"+ this.config.curpFromListOfCandidates + "</strong> no tiene el formato adecuado, por lo tanto " +
							"no es posible realizar el tr&aacute;mite.";
					this.mostrarMensajeError(mensajeError);
					this.dialogo.dialog('open');
				} else {
					//Para que cada vez que se abra el dialogo se cree de nuevo.
					this.init(this.config.contenedor,
					this.config.idAsignacionNss, this.config.nss,
					this.config.tipoTramite, this.config.idIntegrante, this.config.portalContext, this.config.curpFromListOfCandidates);
					
					var curpPersona = this.config.curpFromListOfCandidates;
					var rfcPersona = 'SIN_RFC';
							
					var params = {
						idPersonaSesion : this.config.idIntegrante,
						idTipoPersona : 1,
						idPersona : this.config.idIntegrante,
						curp : curpPersona,
						rfc : rfcPersona,
						idPersonaInteresadaSol : parent.AtributosPersonaCtrl.personaFirmada.idPersona,
						consultaRenapo : true,
						consultaSat : false
					};
					
					parent.WizardActualizacionDatosCtrl.init('wizardDatosActualizacion', params);
					parent.WizardActualizacionDatosCtrl.abrir();
					/*
					this.dialogo.dialog('open');
					
					var url = this.config.url 
						+ this.config.idAsignacionNss + "/"
						+ this.config.nss + "/"
						+ this.config.tipoTramite + "/" 
						+ this.config.idIntegrante + "/"
						+ this.config.curpFromListOfCandidates;
					
					$('#' + this.config.contenedor).html(
							'<iframe id="correccionDerechohabienteFrame" src="' + url
						 			+ '" width="100%" height="100%" '
						 			+ 'onload="set_size(\'correccionDerechohabienteFrame\', 900)" frameborder="0" />');*/
				}
			}
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');	
		},
		
		mostrarMensajeError : function(mensaje) {
			
			this.dialogo.dialog("option", "buttons", [ {
				text : 'ACEPTAR',
				click : function() {
					WizardCorreccionDerechohabienteCtrl.cerrar();
				}
			}]);
			
			var mensajeE = "<div class='alert alert-danger'>"+mensaje+"</div>";
			$('#' + this.config.contenedor).html(mensajeE);
		},
		
		limpiarElementosSesion: function (){
			
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.postJSON('/${mvn.web.app.root}/wizard/correccion/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};
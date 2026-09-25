
var WizardCapturaDocumentosProbatoriosCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idTipoTramite, _idTramite,_otrosParametros){
			
			
			if(_idTramite != this.config.idTipoTramite || _idTipoTramite != this.config.idTipoTramite) {
				this.limpiarIndicadores();
			}
			
			if(_otrosParametros != undefined) {
				this.config.otrosParametros = _otrosParametros;
			}
			
			this.config.contenedor = _contenedor;
			this.config.idTipoTramite = _idTipoTramite;
			this.config.idTramite = _idTramite;
			this.capturaFinalizada=false;
			
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
	            width : 950,
	            height : 700,
	            resizable: false,
	            modal: true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            },
				position : {
					my : "top",
					at : "top",
					of : window,
					offset : "0 10"
				}
	        });
	        
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		WizardCapturaDocumentosProbatoriosCtrl.limpiar_session();
	        	}, 
	        	close: function (event, ui) {
	        		if(jQuery.isFunction(WizardCapturaDocumentosProbatoriosCtrl.callbacks)) {
	        			WizardCapturaDocumentosProbatoriosCtrl.callbacks.call();
	        		}
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
			url :  "/gestionDocumentoProbatorio-web/fileupload/wizard",
			title:"Captura de documentos probatorios",
			contenedor : {}, 
			idTipoTramite: "",
			idTramite: "", 
			curp:"0",
			otrosParametros : {},
			persona: null
		},
		capturaFinalizada: false,
		primeraCaptura: true,
		numeroDocumentosRequeridos: 0,
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
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init(this.config.contenedor,
				this.config.idTipoTramite, this.config.idTramite, this.config.otrosParametros);
			var curp="0" ;
			var idUmf=0;
			var documentosNoMostrados = "0";
			if(this.config.otrosParametros.curp != undefined) {
				curp = this.config.otrosParametros.curp;
			} 
			
			if(this.config.otrosParametros.idUmf != undefined) {
				idUmf = this.config.otrosParametros.idUmf;
			}
			
			if(this.config.otrosParametros.persona != undefined) {
				this.config.persona = this.config.otrosParametros.persona;
			}
			//documentos que no queremos que se muestren en el componente
			if(this.config.otrosParametros.documentosNoMostrados != undefined) {
				documentosNoMostrados = this.config.otrosParametros.documentosNoMostrados;
			}
	        
			this.dialogo.dialog('open');

			
			var url = this.config.url  + "/" + this.config.idTramite+ "/" + this.config.idTipoTramite + "/" + curp + "/" + idUmf + "/" + documentosNoMostrados;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="doctosRequeridosFrame" src="' + url
				 			+ '" width="100%" height="100%" frameborder="0"/>');
			
		}, 
		cancelarCaptura: function() {
			WizardCapturaDocumentosProbatoriosCtrl.cerrar();
		},
		limpiar_session: function() {
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.postJSON('/gestionDocumentoProbatorio-web/fileupload/wizard/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			});	
		} ,
		getPersona : function() {
			return this.config.persona;
		},
		setPrimeraCaptura : function(_primeraCaptura) {
			this.primeraCaptura = _primeraCaptura;
		},
		isPrimeraCaptura : function () {
			return this.primeraCaptura;
		},
		setCapturaFinalizada: function(_capturaFinalizada) {
			this.capturaFinalizada = _capturaFinalizada;
		},
		isCapturaFinalizada: function() {
			return this.capturaFinalizada;
		},
		finalizarCaptura : function () {
			this.capturaFinalizada = true;
			this.primeraCaptura = false;
			this.cerrar();
		},
		limpiarIndicadores : function() {
			this.capturaFinalizada = false;
			this.primeraCaptura = true;
			this.numeroDocumentosRequeridos = 0;
			this.config.persona = null;
		},
		setNumeroDocumentosRequeridos : function(requeridos) {
			this.numeroDocumentosRequeridos = requeridos;
		},
		getNumeroDocumentosRequeridos : function() {
			return this.numeroDocumentosRequeridos;
		},
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
};
/*
 * JS de control del Wizard
 */

var WizardListadoCandidatosCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idAsignacionNss, _nss, _tipoTramite,_seleccionMultiple){
			//console.log( "Dentro del listado(init) Tipo de tramite: "  + _tipoTramite);
			this.config.contenedor = _contenedor;
			this.config.idAsignacionNss = _idAsignacionNss;
			this.config.nss = _nss;
			this.config.tipoTramite = _tipoTramite;
			this.personaSeleccionada = false;
			//Verificamos si viene el quinto parametro que indica si la lista sera de seleccion multiple
			this.config.seleccionMultiple = _seleccionMultiple != undefined ? _seleccionMultiple : false;
	        
			/*
			 * Se crea una variable para el control del dialogo
			 * que sera a traves de un iFrame
			 */
	        var d = $('#' + _contenedor);
	        
	        this.datosSalida.idAsignacionNss = _idAsignacionNss;
	        this.datosSalida.nss = _nss;
	        this.datosSalida.idTipoTramite = _tipoTramite;
	        this.datosSalida.idIntegranteSeleccionado = "";
	        this.datosSalida.seleccionMultiple = this.config.seleccionMultiple;
	        this.datosSalida.curpFromListOfCandidates = "";
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
	        		
	        		WizardListadoCandidatosCtrl.limpiarElementosSesion();
	        		if(!WizardListadoCandidatosCtrl.personaSeleccionada){
	        			WizardListadoCandidatosCtrl.callbacks.call(WizardListadoCandidatosCtrl.datosBuscados);
	        		}else{
	        			WizardListadoCandidatosCtrl.callbacks.call(WizardListadoCandidatosCtrl.getDatosSalida());
	        		}
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
		 * 
		 * Datos de configuracion inicial
		 * de la consulta de la persona moral.
		 */
		config : {
			url :  "/${mvn.web.app.root}/wizard/listado/candidatos/",
			title:"IMSS Digital",
			contenedor : {}, 
			idAsignacionNss : "",
			nss: "",
			tipoTramite: "",
			seleccionMultiple: false
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
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init(this.config.contenedor,
				this.config.idAsignacionNss, this.config.nss, 
				this.config.tipoTramite, this.config.seleccionMultiple);
			
			this.dialogo.dialog('open');
			var seleccionM = this.config.seleccionMultiple ? 1 : 0;
			
			var url = this.config.url + this.config.idAsignacionNss + "/"
			+ this.config.nss + "/"
			+ this.config.tipoTramite + "/" + seleccionM;
			//console.log( "Dentro del listado(abrir) Tipo de tramite: "  + this.config.tipoTramite);
			//alert(url);
			
			$('#' + this.config.contenedor).html(
					'<iframe id="listadoCandidatosFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'listadoCandidatosFrame\', 900)" frameborder="0" />');
			
		}, 
		datosBuscados : {},
		datosSalida: {
			nss:"",
			idAsignacionNss: "",
			idIntegranteSeleccionado: "",
			idTipoTramite: "",
			curpFromListOfCandidates: "",
			seleccionMultiple : false
		},
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');	
		},
		setIdPersona: function(idPersona) {
			this.datosSalida.idIntegranteSeleccionado = idPersona;
			this.personaSeleccionada = true;
		},
		setCurpPersona : function(curp){
			this.datosSalida.curpFromListOfCandidates = curp; 
		},
		getCurpPersona : function() {
			return this.datosSalida.curpFromListOfCandidates;
			
		},
		getDatosSalida : function() {
			return this.datosSalida;
		},
		personaSeleccionada : false,
		limpiarElementosSesion: function (){
			
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.postJSON('/${mvn.web.app.root}/wizard/listado/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			});	
		}
};
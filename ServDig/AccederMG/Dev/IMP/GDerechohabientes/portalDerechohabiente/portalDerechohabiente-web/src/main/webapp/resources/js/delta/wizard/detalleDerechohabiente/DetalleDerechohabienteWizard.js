
var WizardDetalleDerechohabienteCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _nss, _idAsignacionNss, _idIntegrante){
			
			this.config.contenedor = _contenedor;
			this.config.nss = _nss;
			this.config.idAsignacionNss = _idAsignacionNss;
			this.config.idIntegrante = _idIntegrante;
	        
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
	            width : 800,
	            modal: true,
	            resizable: false,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            },
	            buttons: {
	            	"Cerrar": function(){
	            		$(this).html("");
	            		$(this).dialog('close');
	            	}
	            },
	            position: { my: "top", at: "top", of: window, offset: "0 10" }
	        });
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		
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
			url :  "/portalDerechohabiente-ventanilla/wizard/derechohabiente/detalle",
			title:"Detalle derechohabiente",
			contenedor : {}, 
			nss:"",
			idAsignacionNss: "",
			idIntegrante: ""
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
			this.init(this.config.contenedor, this.config.nss, this.config.idAsignacionNss, this.config.idIntegrante);
			
			
			var url = this.config.url + "/" + this.config.nss + "/" + this.config.idIntegrante + "/" + this.config.idAsignacionNss;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="detalleDerechohabientesFrame" src="' + url
				 			+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'detalleDerechohabientesFrame\', 900)"/>');
			
			this.dialogo.dialog('open');
			
		}, 
	
		cerrar : function(){
			this.dialogo.html('');
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
};
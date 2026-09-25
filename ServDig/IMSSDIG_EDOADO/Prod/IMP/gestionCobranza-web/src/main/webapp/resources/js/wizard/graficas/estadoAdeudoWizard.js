/*
 * JS de control del Wizard de Registro de 
 * persona autorizada.
 */

var WizardEstadoAdeudoCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _nrp){
			
			this.config.contenedor = _contenedor;
			this.config.nrp = _nrp;
	        
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
	            height : 600,
	            modal: true,
	            resizable: false,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }
	        });
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		
	        	}	
	        });
	        
	        this.config.title = "Estado de adeudo";
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
			url :  "/gestionCobranza-web/wizard/estadoAdeudo",
			title:"",
			contenedor : {}, 
			nrp:""
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
			this.init(this.config.contenedor, this.config.nrp);
			
			this.dialogo.dialog('open');
			var url = this.config.url + "/" + this.config.nrp;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + url
				 			+ '" width="100%" height="100%" frameborder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
};
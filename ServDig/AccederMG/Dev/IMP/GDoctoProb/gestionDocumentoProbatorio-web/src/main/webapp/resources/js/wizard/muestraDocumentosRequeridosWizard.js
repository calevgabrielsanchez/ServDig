
var WizardMuestraDocumentosRequeridosCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idTipoTramite){
			
			this.config.contenedor = _contenedor;
			this.config.idTipoTramite = _idTipoTramite;
	        
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
	            width : 600,
	            height : 600,
	            position: 'center',
	            dialogClass: "no-close",
	            resizable: false,
	            modal: true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            },
	            buttons: {
	            	"Cancelar": function() {
	            		$(this).dialog('close');
	            		$(this).dialog('destroy');
	            	},
	            	"Aceptar" : function() {
	            		WizardMuestraDocumentosRequeridosCtrl.callbacks.call();
	            		$(this).dialog('close');
	            		$(this).dialog('destroy');
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
		 * Datos de configuracion inicial
		 * de la consulta de la persona moral.
		 */
		config : {
			url :  "/gestionDocumentoProbatorio-web/documentos/requeridos/clasificados",
			title:"Documentos requeridos para el tr&aacute;mite",
			contenedor : {}, 
			idTipoTramite:{}
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
				this.config.idTipoTramite);
			var tipoTramites = "";
			this.dialogo.dialog('open');
			
			for(var i=0;i<this.config.idTipoTramite.length;i++){
				tipoTramites += "" + this.config.idTipoTramite[i] + ",";
			}
			
			var url = this.config.url + "/" + tipoTramites;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="doctosRequeridosFrame" src="' + url
				 			+ '" width="100%" height="100%" frameborder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
};
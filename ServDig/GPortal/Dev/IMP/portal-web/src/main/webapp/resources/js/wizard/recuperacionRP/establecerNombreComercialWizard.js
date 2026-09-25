/*
 * JS de control del Wizard
 */

var WizardNombreComercialCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _idPatron){
			
			this.config.contenedor = _contenedor;
			this.config.idPatron = _idPatron;
	        
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
	            width : 700,
	            height : 500,
	            modal: true,
	            resizable: false,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }
	        });
	        
	        this.dialogo.dialog({
				close : function(event, ui) {
					WizardNombreComercialCtrl.callbacks.call();
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
			url :  "/portal-web/wizard/tramite/recuperacion/patron/formNombreComercial",
			title:"Nombre comercial",
			contenedor : {}, 
			idPatron: ""
		},
		/*
		 * Callback a invocar cuando se 
		 * termine la invocacion de la consulta
		 */
		callbacks : {},
		
		dialogo : {},
		
		resultado: null,
		
		/**
		 * 
		 */
		abrir : function(){
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init(this.config.contenedor,
				this.config.idPatron);
					
			var url = this.config.url + "/" + this.config.idPatron;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + url
				 			+ '" width="100%" height="100%" frameBorder="0"/>');
			
			this.dialogo.dialog('open');
			
		}, 
		
		setResultado: function (_resultado) {
			this.resultado = _resultado;
		},
		
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
};
/*
 * JS de soporte para la inclusion y llamado
 * de los componentes de domicilio, para ubicar y lozaclizar 
 * un domicilio nacional.
 */




/**
 * Objeto de control de domicilio, a traves de este objeto de JS
 * se iniciliaza, configura , invoca y regresa un objeto 
 * domicilio, el cual fue localizado.
 */
var DomicilioCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 * del domicilio.
		 */
		init : function(_contenedor,_tipoTramite,_umfs){
			
			this.config.contenedor = _contenedor;
			this.config.tipoTramite = _tipoTramite != undefined ? _tipoTramite : 0;
			this.config.umfs = _umfs;
			this.config.cambioClinica = false;
			this.config.idUmfTramite = "";
			this.config.contextPath = '/${mvn.web.app.root}';
			this.config.idOrigen = '${mvn.web.app.origin.id}';
			
			/*
			 * Inicializamos el dialogo que contiene la pantalla 
			 * de la consulta de personas morales.
			 */
			var horizontalPadding = 15;
	        var verticalPadding = 15;
	        
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
	            autoOpen: false,
	            width: 980,
	            height: 900,
	            modal: true,
	            resizable: false,
	            autoResize: true,
	            overlay: {
	                opacity: 0.5,
	                background: "black"
	            }
	        }).width(900).height(900);
	        
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		DomicilioCtrl.callbacks.call( DomicilioCtrl.domicilio);
	        	}	
	        })
	        
			
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
			url :  '/${mvn.web.app.root}'+'/domicilio/nacional/ubicar/byUmf',
			title:"Localizar domicilio nacional",
			tipoTramite: 0,
			contenedor : {},
			umfs : {},
			cambioClinica: false,
			idUmfTramite : "",
			contextPath : "",
			idOrigen:""
		},
		/*
		 * Callback a invocar cuando se 
		 * termine la invocacion de la consulta
		 */
		callbacks : {},
		
		dialogo : {},
		/*
		 * Objeto domicilio 
		 */
		domicilio : {}, 
		setIdUmfTramite : function(_idUmf) {
			this.config.idUmfTramite = _idUmf;
		},
		getIdUmfTramite : function() {
			return this.config.idUmfTramite;
		},
		setCambioClinica : function(_cambioClinica){
			this.config.cambioClinica = _cambioClinica;
		},
		isCambioClinica: function() {
			return this.config.cambioClinica;
		},
		/**
		 * 
		 */
		localizar : function(){
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init( this.config.contenedor, this.config.tipoTramite, this.config.umfs);
			
			var idUmfUsuario = this.config.umfs.umfUsuario;
			var idUmfPersona = this.config.umfs.umfPersona != undefined ? this.config.umfs.umfPersona : this.config.umfs.umfUsuario;
			var tipoTramite = this.config.tipoTramite;
			
			this.dialogo.dialog('open');
			
			$('#' + this.config.contenedor).html(
					'<iframe id="site" src="' + this.config.url + "?idUmfUsuario=" + idUmfUsuario+"&idUmfPersona="+idUmfPersona+"&tipoTramite="+tipoTramite 
				 			+ '" width="100%" height="100%" frameborder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
		
}
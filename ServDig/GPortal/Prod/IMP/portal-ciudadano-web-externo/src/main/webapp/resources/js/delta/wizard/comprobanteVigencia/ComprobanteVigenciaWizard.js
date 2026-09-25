
var WizardComprobanteVigenciaCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor,_opciones){
			
			this.config.contenedor = _contenedor;
			this.config.opciones = _opciones;
	        
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
	            heigth: 900,
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
	        		//se limpia el div, ya que si no se hace se vuelve a consultar la solicitud
	        		$('#' + WizardComprobanteVigenciaCtrl.config.contenedor).html("");
	        		WizardComprobanteVigenciaCtrl.limpiarElementosSesion();
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
			url :  "/portal-ciudadano-web-externo/wizard/comprobante/vigencia/init",
			title: "Comprobante de vigencia de derechos",
			contenedor : {},
			opciones: {}
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
			this.init(this.config.contenedor, this.config.opciones);
			
			$('#' + this.config.contenedor).html('<iframe id="reportepdf" name="reportepdf" width="100%" height="100%" '+ 
					'onload="set_size(\'reportepdf\', 900)" frameborder="0"/>');
			
			this.mostrarReporte();
			
			this.dialogo.dialog('open');
		},
		mostrarReporte : function() {
			var idDiv = '#' + this.config.contenedor;
			var datos = this.config.opciones;
			
			var form = $("<form/>", {
				id: 'asignacionNss',
				name: 'asignacionNss',
				action : this.config.url,
				method : 'POST',
				target: 'reportepdf'
			});
			
			form.append($("<input/>", {type : 'hidden',name : 'idAsignacionNSS',value : datos.idAsignacion}));
			form.append($("<input/>", {type : 'hidden',name : 'idPersona',value : datos.idPersona}));
			form.append($("<input/>", {type : 'hidden',name : 'nss',value : datos.nss}));
			form.append($("<input/>", {type : 'hidden',name : 'nssStr',value : datos.nss}));
			form.append($("<input/>", {type : 'hidden',name : 'correoElectronico.correo',value : datos.correo}));
			form.append($("<input/>", {type : 'hidden',name : 'curp',value : datos.curp}));
			form.append($("<input/>", {type : 'hidden',name : 'nombre',value : datos.nombreCompleto}));
			
			$(form).appendTo(idDiv).submit();
			$("div"+idDiv+" form#asignacionNss").remove();
			
		},
		cerrar : function(){
			$('#' + this.config.contenedor).html('');
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}, 
		limpiarElementosSesion: function() {
			// Se limpia la sesi�n de la administraci�n de domicilios
			$.blockUI();
			$.postJSON('/portal-ciudadano-web-externo/wizard/comprobante/vigencia/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			}).always(function(){
				$.unblockUI();
			});	
		}
};

var WizardComprobanteVigenciaCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor,_opciones){
			
			this.config.contenedor = _contenedor;
			this.config.opciones = _opciones;
			this.config.idOrigen = '${mvn.web.app.origin.id}';
			
			/*
			 * Se crea una variable para el control del dialogo
			 * que sera a traves de un iFrame
			 */
	        var d = $('#' + _contenedor);
	        
	        var opcionesInicio = {
	        	title: this.config.title,
	        	closeOnEscape: false,
	        	autoOpen: false,
	        	width : 900,
	        	height : 900,
	        	modal: true,
	        	resizable: false,
	        	overlay: {
	        		opacity: 0.5,
	        		background: "black"
	        	}
	        };
	        
	        if(this.config.idOrigen == '1') {
	        	opcionesInicio.buttons = {
	        		"Cerrar": function() {
	        			var $dialog = $(this);
						$dialog.dialog("close");
						if (!this.config.opciones.noDestroy) {
							$dialog.html("");
							$dialog.dialog("destroy");
						}
	        		}
	        	};
	        };
	        /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = d.dialog(opcionesInicio);
	        
	        /*
	         * Configuramos el metodo onClose del dialogo
	         * 
	         */
			if (!this.config.opciones.noDestroy) {
				this.dialogo.dialog({
					beforeClose: function (event, ui) {
						//se limpia el div, ya que si no se hace se vuelve a consultar la solicitud
						$('#' + WizardComprobanteVigenciaCtrl.config.contenedor).html("");
						WizardComprobanteVigenciaCtrl.limpiarElementosSesion();
					},
					close: function (event, ui) {
						$(this).dialog('destroy').empty();
					}
				});
			}
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
			url :  "/${mvn.web.app.root}",
			title: "Comprobante de vigencia de derechos",
			contenedor : {},
			opciones: {},
			idOrigen: null
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
		abrir : function(_mostrarReporte){
			//Para que cada vez que se abra el dialogo se cree de nuevo.
			this.init(this.config.contenedor, this.config.opciones);
			//variable donde pondremos la url
			var url = this.config.url;
			//Verificamos si el origen es portal ciudadano para que se maneje distinto 
			if(this.config.idOrigen == 6) {
				$('#' + this.config.contenedor).html('<iframe id="reportepdf" name="reportepdf" width="100%" height="100%" '+ 
				'onload="set_size(\'reportepdf\', 900)" frameborder="0"/>');
				//mostramos la pantalla inicial de impresion del reporte de vigencia, aqui se pide el nss
				this.mostrarReporte();
				this.dialogo.dialog('open');
			} else {
				//Formamos la url para imprimir el reporte de vigencia de derechos
				url += "/wizard/comprobante/vigencia/pdf" + "/" + this.config.opciones.idAsignacion + "/" + this.config.opciones.idPersona + "/" + this.config.opciones.nss + "/" + this.config.opciones.usuario;
				// Verificar si es para mostrar reporte o solo se realiza la petición y se almacena
				if (_mostrarReporte){
				    //Ya no se realiza la peticion, solo se muestra el dialogo
					this.dialogo.dialog('open');
				} else {
					//lo concatenamos al frame
					$('#' + this.config.contenedor).html('<iframe id="reportepdf" name="reportepdf" src="' + url+ '" width="100%" height="100%" onload="$.unblockUI();" frameborder="0"/>');
				}
			}

		}, 
		mostrarReporte : function() {
			var idDiv = '#' + this.config.contenedor;
			var datos = this.config.opciones;
			var urlAction = this.config.url + "/wizard/comprobante/vigencia/init";
			
			var form = $("<form/>", {
				id: 'asignacionNss',
				name: 'asignacionNss',
				action : urlAction,
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
			// Se limpia la session del comprobante de vigencia de derechos
			$.blockUI();
			$.postJSON('/${mvn.web.app.root}/wizard/comprobante/vigencia/limpiar-session', null, function(data) {
				
			}).error(function(data){
				
			}).always(function(){
				$.unblockUI();
			});	
		}
};

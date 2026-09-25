var WizardAgregarRLCtrl = {
	
	setDataTable :  function(oDataTable){
		this.config.dataTable = oDataTable;
	},
		
	setOnCancelCallback : function(_fnCallback){
		this.cancelCallbacks = _fnCallback;
	},
	
	setOnAceptCallback : function(_fnCallback){
		this.aceptCallbacks = _fnCallback;
	},

	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _parametro) {
		
		this.config.contenedor = _contenedor;
		this.config.parametro = _parametro;
		this.config.contextPath = '/${mvn.web.app.root}';
		this.config.idOrigen = '${mvn.web.app.origin.id}';
		
		var d = null;		
		if ($('#' + _contenedor).length == 0) {
			d = $('#' + _contenedor, parent.document);
		} else {
			d = $('#' + _contenedor);
		}

		/*
		 * Configuracion del dialogo
		 */
        this.dialogo = d.dialog({
            title: this.config.title,
	        closeOnEscape: false,
	        autoOpen : false,
            width : 900,
            height: 'auto',
            left  : '50px',
            modal: true,
            resizable: false,
            autoResize : true,
            open: function(event) { $(".ui-dialog-titlebar-close", $(this).parent()).hide(); },
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
        		WizardAgregarRLCtrl.limpiarElementosSesion();
				$('iframe#agergarRLFrame').attr("src", "");
				var windowFrame = window.frames['agergarRLFrame'].contentWindow;
				if(windowFrame === undefined || windowFrame == null)
					windowFrame = window.frames['agergarRLFrame'].frameElement.contentWindow;
				var cancel = windowFrame.cancelable;
				if(cancel !== undefined && cancel == true) {
					event.preventDefault();
					windowFrame.cancelarDesdeBoton = false;
					windowFrame.dialogoConfirmarCancelar.dialog('open');
				}				
			},
			close : function(event, ui) {
				limpiarDatos(function() { 
					if(!$.isEmptyObject(this.dialogo)) {
						$(this).dialog('destroy').empty();
					} else {
						$('#agergarRLFrame').parent().dialog('destroy').empty();
					}
				});
			}
        });
	},
	
	setOnCloseCallback : function(_fnCallback) {
		this.callbacks = _fnCallback;
	},
	
	setBeforeCloseCallback : function(_fnCallback) {
		if ($.isFunction(_fnCallback) || _fnCallback == null) {
			this.beforeCloseCallback =  _fnCallback;
		} else {
			$.error('No se recibio fncion para beforeCloseCallback');
		}
	},
	
	config : {
		url : "/wizard/alta/representeLegal/",
		title : "Agregar Representante Legal",
		contenedor : {},
		contextPath : "",
		idOrigen:"",
				
		parametro : "",
		dataTable : null
		
	},
	
	beforeCloseCallback : null,
	
	callbacks : {},
	
	cancelCallbacks : {},
	
	aceptCallbacks : {},
	
	dialogo : {},
	
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor, this.config.parametro);
		this.dialogo.dialog('open');
		
		var url = this.config.contextPath  + this.config.url + 'inicio';
		
		$('#' + this.config.contenedor).html(
				'<iframe id="agergarRLFrame" src="' + url
						+ '" width="100%" height="100%" frameborder="0"'
						+ 'onload="set_size(\'agergarRLFrame\')" frameborder="0" />');

	},
	
	cerrar : function() {
		if(!$.isEmptyObject(this.dialogo)) {
			this.dialogo.dialog('close');
		} else {
			$('#agergarRLFrame').parent().dialog('close');
		}
	},
	
	limpiarElementosSesion : function() {
		$.postJSON(this.config.contextPath  + this.config.url + 'limpiar-sesion', null, function(data) {
		}).error(function(data){
		});	
	}	
	
};
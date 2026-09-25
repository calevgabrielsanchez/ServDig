/*
 * JS de control del Wizard de Registro de 
 * persona autorizada.
 */

var WizardImpresionReportesCobranzaCtrl = {
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(_contenedor, _nrp, _reporte){
			
			this.config.contenedor = _contenedor;
			this.config.nrp = _nrp;
			this.config.reporte = _reporte;
	        
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
	            minHeight : 400,
	            maxHeight : 900,
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
	        		/*
					 * Workaround para evitar que el iframe se recarge al momento de
					 * cerrar el dialogo de jQuery, este comportamiento es resultado
					 * de un bug de jQuery
					 */
					$('iframe#reporteEdoAdeudo').attr("src", "");
	        	}	
	        });
	        
	        //Se pone el titulo del wizard
	        switch(this.config.reporte) {
				case 1: this.config.title = "ESTADO DE ADEUDO POR SITUACION DE COBRO";
						break;
				case 2: this.config.title = "ESTADO DE ADEUDO POR SITUACION DE COBRO RCV";
						break;
				case 3: this.config.title = "ESTADO DE ADEUDO POR MOTIVO DE COBRO";
						break;
				case 4: this.config.title = "ESTADO DE ADEUDO POR MOTIVO DE COBRO RCV";
						break;
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
			url :  "/gestionCobranza-web/reportesCobranza/edoCuenta/trabajoAdeudo/",
			title:"",
			contenedor : {}, 
			nrp:"",
			reporte:""
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
				this.config.nrp, this.config.reporte);
			
			this.dialogo.dialog('open');
			var urlReporte = "";
			
			switch(this.config.reporte) {
			case 1: urlReporte = "situacionCobro";
					break;
			case 2: urlReporte = "situacionCobroRCV";
					break;
			case 3: urlReporte = "tipoCobro";
					break;
			case 4: urlReporte = "tipoCobroRCV";
					break;
			}
			
			var url = this.config.url + urlReporte + "/" + this.config.nrp;
			
			$('#' + this.config.contenedor).html(
					'<iframe id="reporteEdoAdeudo" src="' + url
				 			+ '" width="100%" height="320px" frameborder="0"/>');
			
		}, 
	
		cerrar : function(){
			//Cerramos el dialogo
			this.dialogo.dialog('close');
			
			//Destruimos el dialogo
			this.dialogo.dialog('destroy');
			
		}
};
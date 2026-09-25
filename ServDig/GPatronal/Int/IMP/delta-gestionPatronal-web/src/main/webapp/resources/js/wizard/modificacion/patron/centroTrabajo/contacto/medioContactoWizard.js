var WizardModificacionContactoCentroTrabajoCtrl = {
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _cveIdRegistroPatronal, _idPersona, _idTipoPersona) {
		this.config.contenedor = _contenedor;
		this.config.numeroRegistroPatronal = _cveIdRegistroPatronal;
		this.config.idPersona = _idPersona;
		this.config.idTipoPersona = _idTipoPersona;
		
		/*
		 * Se crea una variable para el control del dialogo
		 * que sera a traves de un iFrame
		 */
		var d = $('#' + _contenedor);

		/*
		 * Configuracion del dialogo
		 */
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 900,
			modal : true,
			resizable : false,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position: { my: "top", at: "top", of: window, offset: "0 10" }
		});

		/*
		 * Configuramos el metodo onClose del dialogo
		 * 
		 */
		this.dialogo.dialog({
			beforeClose : function(event, ui) {
				
			},
			close: function (event, ui) {
        		$(this).dialog('destroy').empty();
        	}
		});
	},
	
	/*
	 * Datos de configuracion inicial
	 * de la consulta del registro patronal.
	 */
	config : {
		url : "/delta-gestionPatronal-web/wizard/tramite/centroTrabajo/medios/",
		title : "IMSS Digital",
		contenedor : {},
		numeroRegistroPatronal : "",
		idPersona : "",
		idTipoPersona : ""
	},
	
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor, 
			this.config.numeroRegistroPatronal, this.config.idPersona, this.config.idTipoPersona);
		this.dialogo.dialog('open');
		var url = this.config.url + this.config.numeroRegistroPatronal + "/" + this.config.idPersona + "/" + this.config.idTipoPersona;
		$('#' + this.config.contenedor).html('<iframe id="mediosPatronesFrame" src="' + url 
				+ '" width="100%" height="100%" '
				+ 'onload="set_size(\'mediosPatronesFrame\')" frameborder="0"/>');
	},
	
	cerrar : function() {
		//Cerramos el dialogo
		this.dialogo.dialog('close');
	}

};
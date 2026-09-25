/*
 * JS de control del Wizard de Modificacion de Clasificacion.
 */

var WizardModificacionPatronClasificacionCtrl = {
	/*
	 * Funcion para inicializar la configuracion
	 */
	init : function(_contenedor, _numeroRegistroPatronal, _tipoTramite, _title, _contextPath) {
		this.config.contenedor = _contenedor;
		this.config.numeroRegistroPatronal = _numeroRegistroPatronal;
		this.config.tipoTramite = _tipoTramite;
		this.config.title = _title;
		this.config.context = _contextPath;

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
			width : 950,
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
				WizardModificacionPatronClasificacionCtrl.salirLimpiaSesion();
			},
			close : function(event, ui) {
				//Destruimos el dialogo
				$(this).dialog('destroy').empty();
			}
		});
	},

	/*
	 * 
	 */
	setOnCloseCallback : function(_fnCallback) {
		this.callbacks = _fnCallback;
	},

	/*
	 * Datos de configuracion inicial
	 * de la consulta de la persona moral.
	 */
	config : {
		context: "",
		url : "/movPat/internet/wizard/tramite/clasificacion/",
		title : "Modificaciones en el seguro de riesgo de trabajo",
		contenedor : {},
		numeroRegistroPatronal : "",
		tipoTramite : ""
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
	abrir : function() {
		// Para que cada vez que se abra el dialogo se cree de nuevo.
		this.init(this.config.contenedor, this.config.numeroRegistroPatronal, this.config.tipoTramite, this.config.title, this.config.context);
		this.dialogo.dialog('open');

		var url = this.config.context + this.config.url + this.config.numeroRegistroPatronal + "/" + this.config.tipoTramite;
		$('#' + this.config.contenedor).html('<iframe id="modifClasificacionFrame" src="' + url 
				+ '" width="100%" height="100%" '
				+ 'onload="set_size(\'modifClasificacionFrame\')" frameborder="0"/>');
	},

	cerrar : function() {
		//Cerramos el dialogo
		this.dialogo.dialog('close');
	},

	/*
	 * Funci�n para limpiar los elementos en sesion, se puso aqui
	 * para agregarla en el cerrar del dialogo y seguir permitiendo el
	 * setteo de la funcion de callback de cada gestion en el momento de
	 * cerrar.
	 */
	limpiarElementosSesion : function() {
		// Se limpia la sesion de la Modificacion de Clasificacion
		$.postJSON(this.config.context+'/movPat/internet/wizard/tramite/clasificacion/limpiar-sesion', null, function(data) {
			
		}).error(function(data){
			
		});	
	},
	
	salirLimpiaSesion :  function() {
		var contexto = this.config.context;
		console.log('variable JC' + contexto);
		$.blockUI();
				$.postJSON(this.config.context +  "/movPat/internet/clean/acceso",null,
					function() {
					$.blockUI();
					location.href =  contexto+"/movPat/internet/acceso";
				});	
	}
};
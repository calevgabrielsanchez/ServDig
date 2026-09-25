
$(document).ready(function(){
	
	// -----------------------------------------------
	// Carga las razones de rechazo para el tramite
	// -----------------------------------------------
	$('#rechazarTramite').click(function(){
		RechazarTramiteCtrl.cargarRazonRechazo();
	});
	
	// ------------------------------------------------------
	// Cancela el trámite sin indicar razones de rechazo
	// ------------------------------------------------------
	$('#rechazarTramiteSinRazones').click(function(){
		RechazarTramiteCtrl.cancelarCorreccion();
	});
		
});
		



// ----------------------------------------------------------
// Controlador para rechazos de trámite
//-----------------------------------------------------------
var RechazarTramiteCtrl = {
	
	// ----------------------------------
	// Obligatorios
	// ----------------------------------
	idSolicitud 			: 	['idSolicitud','solicitudId'],
	idPersona				:	['idPersona','fisica.idPersona'],
	idTipoTramite			:	['idTipoTramite','tipoTramite.idTipoTramite','tipoTramite'],
	idTramite				:	['idTramite','tramiteId'],
	
	
	idRazonRechazo			:	['idRazonRechazo'],
	observacionesRechazo	:	['observacionesRechazo'],
		
	/**
	 * Busca en la página los campos con los id's proporcionados e
	 * intenta obtener su valor
	 * 
	 * @param ids arreglo de cadenas representando los id's 
	 * @return Valor del objeto en caso de que algún id se encuentre en la página
	 */
	obtenerValor : function(ids){
		
		var tamano = ids.length;
		
		for(var i = 0; i<tamano; i++){
			
			var $objeto = document.getElementById(ids[i]);
			
			if( $objeto ){
				return $objeto.value; 
			}
			
		}
		
		return "";
		
	},
	
	/**
	 * Obtiene los valores que el controlador necesita para cancelar el trámite
	 * 
	 */
	obtenerValoresFormaRechazo : function(){
		
		var _this = this;
		
		var campos = {
			idSolicitud 	: 	_this.obtenerValor(_this.idSolicitud),
			idPersona		:	_this.obtenerValor(_this.idPersona),
			idTipoTramite	:	_this.obtenerValor(_this.idTipoTramite),
			idTramite		:	_this.obtenerValor(_this.idTramite)
		};
		
		return campos;
		
	},	
	
	
	/**
	 * Crea una forma para redireccionar la página
	 * 
	 * @param action 
	 * @param fields campos que se incluiran en la forma
	 */
	crearForma: function(action, fields) {

		var form = $("<form/>", {
			action : action,
			method : 'POST'
		});

		$.each(fields, function(key, value) {
			form.append($("<input/>", {
				type : 'hidden',
				name : key,
				value : value
			}));
		});

		return form;
	},
	
	/**
	 * Crea un dialogo para la razones de rechazo
	 * 
	 */
	cargarRazonRechazo : function() {
		
		var _this = this;
		
		var idTipoTramite = _this.obtenerValor(_this.idTipoTramite);
		
		$razonRechazo = $('<div></div').html('Cargando Razones de rechazo...');
		$razonRechazo.dialog({
			autoOpen : false,
			title: 'Raz&oacute;n rechazo',
			//show: "blind",
			//hide: "explode",
			resizable: false,
			modal: true,
			width: 500,
			buttons:[{	
				id: "rechazoButtonSi",
	            text: "Si",
	            class: "rechazoButton",
	            disabled:true,
	            click: function() {
	            	
	            	$.blockUI();
	            	
	            	var razon = _this.obtenerValor(_this.idRazonRechazo);
					var observaciones = _this.obtenerValor(_this.observacionesRechazo);
					var campos = _this.obtenerValoresFormaRechazo();
					_this.rechazarSolicitud(campos, razon, observaciones);
					_this.cierraDialogo($(this));
	            }
	         },{
	        	id: "rechazoButtonNo",
	            text: "No",
	            class: "rechazoButton",
	            disabled:true,
	            click: function() {
	            	_this.cierraDialogo($(this));
	            }
	         }]
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		
		$razonRechazo.load(context_path + '/solicitud/cargarRazonRechazo/'+idTipoTramite,function(data){
			
			// ---------------------------------------------------
			// Límites para el textarea de observaciones
			// ---------------------------------------------------
			asignartextAreaLimites("observacionesRechazo",{styles:{}});
			
			// ---------------------------------------------------------
			// Cuando se cargen las razones, habilitamos los botones
			// ---------------------------------------------------------
			var botones = $(this).dialog('option', 'buttons');
			var tamano = botones.length;
			
			for(var i=0; i<tamano; i++){
				botones[i].disabled=false;
			}
			
			$(this).dialog('option', 'buttons', botones);
			
		});
		
		$razonRechazo.dialog('open');
	},
	
	/**
	 * Envía una solicitud post para rechazar el trámite
	 * 
	 * @param campos valores que se enviarán en la forma
	 * @param razonRechazo
	 * @param observaciones
	 */
	rechazarSolicitud: function(campos,razonRechazo, observaciones) {
		
		var _this = this;
		
		// ---------------------------------------------
		// Campos que se enviaran en la forma
		// ---------------------------------------------
		campos.idRazonRechazo	= 	razonRechazo;
		campos.observaciones	=	observaciones;
		
		var forma = _this.crearForma(context_path + "/tramite/rechazar", campos);
		$(forma).appendTo('body').submit();
		
		
	},
	
	
	/**
	 * Cancela el trámite sin especificar las razones de rechazo
	 * 
	 */
	cancelarCorreccion: function() {

		var _this = this;
		
		$decision = $('<div></div>').text('\u00BF Est\u00E1 seguro que desea salir el tr\u00E1mite de Baja?');
		$decision.dialog({
			autoOpen : false,
			resizable : false,
			width : 300,
			title : 'Advertencia',
			modal : true,
			buttons : {
				"Si" : function() {
					$.blockUI();
	            	
					var campos = _this.obtenerValoresFormaRechazo();
					_this.rechazarSolicitud(campos, "", "");
					_this.cierraDialogo($(this));
				},
				"No" : function() {
					_this.cierraDialogo($(this));
				}
			}
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

		$decision.dialog('open');
	},
	
	
	/**
	 * Cierra y destruye un dialogo
	 * 
	 * @param $dialog JQuery object
	 */
	cierraDialogo: function($dialogo){
		$dialogo.dialog('close');
		$dialogo.dialog('destroy');
		$dialogo.html('');
	}
	
	
	
};





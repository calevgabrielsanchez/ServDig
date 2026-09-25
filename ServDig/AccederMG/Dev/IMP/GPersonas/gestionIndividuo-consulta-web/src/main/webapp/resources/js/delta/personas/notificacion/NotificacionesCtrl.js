var NotificacionesCtrl = {

	/*
	 * Función para consultar las notificaciones de una persona, utiliza la
	 * bandera isMoral para saber si es una persona moral, como resultado
	 * incrusta el HTLM con el mensaje del número de notificaciones que tiene la
	 * persona
	 */
	consultarNotificaciones : function() {
		
		var url;
		var idPersona = this.datosEntrada.idPersona;
		var idModulo = this.datosEntrada.idModulo;
		var contenedor = this.datosEntrada.idContenedor;
		
		if (this.datosEntrada.isMoral){
			url = '/gestionIndividuo-consulta-web/notificaciones/persona-moral/obtener/' + idPersona + '/' + idModulo;
		} else {
			url = '/gestionIndividuo-consulta-web/notificaciones/persona/obtener/' + idPersona + '/' + idModulo;
		}
		
		$.ajaxSetup({cache:false});
		
		$.get(url, function(data) {
			$("#" + contenedor).empty();
			$("#" + contenedor).html(data);
		}).error(function(data) {
			
		});	
	},
	
	/*
	 * Función para consultar las notificaciones de una persona, utiliza la
	 * bandera isMoral para saber si es una persona moral, como resultado
	 * devuelve un objeto JSON
	 */
	consultarNotificacionesJSON : function() {
		
		var url;
		var idPersona = this.datosEntrada.idPersona;
		var idModulo = this.datosEntrada.idModulo;
		
		if (this.datosEntrada.isMoral){
			url = '/gestionIndividuo-consulta-web/notificaciones/persona-moral/obtener-detalle-json/' + idPersona + '/' + idModulo;
		} else {
			url = '/gestionIndividuo-consulta-web/notificaciones/persona/obtener-detalle-json/' + idPersona + '/' + idModulo;
		}
		
		$.ajax({
			async: false,
		    dataType: "json",
			url: url,
			type: 'post',
			success: function(data){
				NotificacionesCtrl.setDatosSalida(data);
			}
		});
	},

	//Objeto con los atributos para los parámetros de entrada
	datosEntrada : {
		idPersona : '',
		idModulo : '',
		isMoral : '',
		idContenedor : ''
	},

	//Objeto de salida
	datosSalida : {},

	setDatosEntrada : function(objDatosEntrada) {
		this.datoEntrada = objDatosEntrada;
	},

	getDatosSalida : function() {
		return this.datosSalida;
	},

	setDatosSalida : function(objDatosSalida) {
		this.datosSalida = objDatosSalida;
	}
};
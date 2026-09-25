var WizardRttCtrl = {

	
	init : function(datos) {
		this.config.contenedor = datos.contenedor;
		this.config.rfc = datos.rfc;
		this.config.rp = datos.rp;
		this.config.razonSocial= datos.razonSocial;
		this.config.contextPath = '/${mvn.web.app.root}';
		this.config.idOrigen = '${mvn.web.app.origin.id}';
		
		/*
		 * Se crea una variable para el control del dialogo que sera a traves de
		 * un iFrame
		 */
		var d = null;
		
		if ($('#' + this.config.contenedor).length == 0) {
			d = $('#' + _contenedor, parent.document);
		} else {
			d = $('#' + this.config.contenedor);
		}

		/*
		 * Configuracion del dialogo
		 */
		this.dialogo = d.dialog({
			title : this.config.title,
			autoOpen : false,
			width : 1050,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position : {
				my : "top",
				at : "top",
				of : window,
				offset : "0 10"
			}
		});

		/*
		 * Configuramos el metodo onClose del dialogo
		 * 
		 */
		this.dialogo.dialog({
			close : function(event, ui) {
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
	 * Datos de configuracion inicial de la consulta de la persona moral.
	 */
	config : {
		url : '/wizard/riesgosTrabajo/tyc/',
		url2 : '/wizard/riesgosTrabajo/tycrfc/',
		title : 'IMSS Digital',
		container : null,
		rfc : null,
		razonSocial: null,
		rp: null,
		contextPath : "",
		idOrigen:""
	},
	/*
	 * Callback a invocar cuando se termine la invocacion de la consulta
	 */
	callbacks : {},

	dialogo : {},

	/**
	 * 
	 */
	abrir : async function() {
		this.dialogo.dialog('open');

		/*var url = this.config.contextPath + this.config.url + this.config.rfc + '/'
		+ this.config.rp + '/' + this.config.razonSocial;*/

		if (await this.connec()) {
			var url = this.config.rfc == null ? this.config.contextPath + this.config.url + this.config.rfc + '/'
				+ this.config.rp + '/' + this.config.razonSocial : this.config.contextPath + this.config.url2 + this.config.rfc + '/'
				+ this.config.razonSocial;

			$('#' + this.config.contenedor).html(
				'<iframe id="solicitarRttFrame" src="' + url
				+ '" width="100%" height="100%" frameborder="0"'
				+ 'onload="set_size(\'solicitarRttFrame\')" frameborder="0" />');
		}
	},

	cerrar : function() {
		// Cerramos el dialogo
		this.dialogo.dialog('close');
	},
	connec : async function (){
		var url = this.config.contextPath + '/wizard/riesgosTrabajo/connection'
		var respuesta = false;
		var titulo = "Sesi\u00F3n";
		var mensaje = "Intermitencia en la comunicaci\u00F3n con la Base de Datos. Favor de ingresar nuevamente.";

		const connectResponse = await fetch(url).then(function (response) {
			if (response.ok) {
				return respuesta = response.ok;
			} else {
				console.log("No se estableció la conexión con Base de datos:" + error.message);
				return respuesta;
			}
		}).catch(function (error) {
			console.log("No se estableció la conexión con Base de datos:" + error.message);
			return respuesta;
		});

		if (!connectResponse)
			this.armarModal(titulo, mensaje, 400, 200);

		return respuesta;
	},
	armarModal : function (titulo, mensaje, dlgWidth, dlgHeight) {
	var newdiv = document.createElement('div');
	newdiv.setAttribute('id', 'divMsjDlgModal');
	newdiv.innerHTML = mensaje;
	var divv = document.getElementsByTagName('div')[0];
	divv.appendChild(newdiv);

	var objDialogo = $("#divMsjDlgModal").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		cache : false,
		height : dlgHeight,
		width : dlgWidth,
		title : titulo
	});
	objDialogo.dialog('open');
}
};
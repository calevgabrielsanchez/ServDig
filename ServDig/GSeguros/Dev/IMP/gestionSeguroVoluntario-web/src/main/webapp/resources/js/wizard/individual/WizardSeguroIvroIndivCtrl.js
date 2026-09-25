/**
 * Wizard para los tramites de ivro
 * En el Init debe recibir los siguientes parametros como un objeto
 * {datos : {
 * 		contenedor: 'Id del contenedor del wizard',
 *      idPersona: 'id de la persona',
 *      rfc: 'rfc de la persona',
 *      url: 'url inicial del widget'
 *      title: titulo del widget
 *      beforeOpen : funcion que se ejecuta antes de abrir el dialogo  (opcional)
 *      onClose : funcion que se ejecutara al cerrar el dialogo (opcional)
 *  } 
 **/


var WizardSeguroIvroIndivCtrl = {

		setRfc :  function(rfc){
			this.config.datos.rfc = rfc;
		},
		
		setUrlRenovacion :  function(){
			this.config.datos.url = "/${mvn.web.app.root}/wizard/individual/iniciarRenovacion/";
		},
		
		/*
		 * Funcion para inicializar la configuracion
		 */
		init : function(datos) {

            this.config.datos = datos;
            this.config.resultado = this.validarCompraRenovacion();
            this.config.renovacion = this.config.resultado.esRenovacion;
            this.config.extemporanea = this.config.resultado.esExtemporanea;
            this.config.cambioAModalidad = this.config.resultado.cambiarRenovacionACompra;
            // this.config.compra=this.config.resultado.esCompra;
            console.log("title: "+this.config.datos.title);
            console.log("renovacion: "+ this.config.renovacion);
            console.log("extemporanea: "+ this.config.extemporanea);
            console.log("modalidad: "+ this.config.cambioAModalidad);
            console.log("bandera: "+ this.config.bandera);
            // console.log("compra: "+ this.config.compra);

            // if(!this.config.renovacion&&this.config.compra!==undefined){
            //     this.config.datos.title = "Renovaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio";
            // } else
            if(!this.config.renovacion){
                this.config.datos.title = "Incorporaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio";
            } else if (this.config.renovacion && this.config.bandera === undefined){
                this.config.datos.title = "Renovaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio";
            } else if (this.config.renovacion && this.config.bandera && (this.config.extemporanea || this.config.cambioAModalidad)){
                this.config.datos.title = "Incorporaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio";
            } else {
                this.config.datos.title = "Renovaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio";
            }

            if(!(this.config.datos.url)) {
				this.config.datos.url = "/${mvn.web.app.root}/wizard/individual/";
			}
			var c = null;
			if($('#' + this.config.datos.contenedor).lenght == 0)
				c = $('#'+ this.config.datos.contenedor, parent.document);
			else
				c = $('#'+ this.config.datos.contenedor);	        
	        
	        /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = c.dialog({
	            title: this.config.datos.title,
	            autoOpen : false,
		        closeOnEscape: false,
	            width : 950,
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
	        		//$('iframe#ivroPersonalFrame').attr("src", "");
	        	},
	        	close: function (event, ui) {
//	        		$(this).dialog('destroy').empty();
//	        		WizardSeguroIvroIndivCtrl.limpiarElementosSesion(event);
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

		    datos : {}
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
            this.config.bandera;
			this.init(this.config.datos);			
			this.dialogo.dialog('open');
			var url = this.config.datos.url + this.config.datos.idPersona  + "/" + this.config.datos.correo + "/" +  this.config.datos.rfc;
			$('#' + this.config.datos.contenedor).html(
					'<iframe id="ivroPersonalFrame" src="' + url
				 			+ '" width="100%" height="100%" '
				 			+ 'onload="set_size(\'ivroPersonalFrame\')" frameBorder="0"/>');

		}, 
	
		cerrar : function(){
			if (!$.isEmptyObject(this.dialogo)) {
				this.dialogo.dialog('close');
			} else {
				$('#ivroPersonalFrame').parent().dialog('close');
			}
			WizardSeguroIvroIndivCtrl.limpiarElementosSesion();
		},

		limpiarElementosSesion: function () {
			$.postJSON('/${mvn.web.app.root}/wizard/individual/limpiaSesion');
		},
		
		dialogoRenovacionExtemporanea : function (divId, titulo, mensaje) {
			$("#textoMensaje").html(mensaje);
			$("#textoMensaje").removeAttr("style");	
			$("#textoMensaje").attr("style", "color: blue;");
			var height=300;		
			var width=600;				
			var objDialogo = $(divId).dialog({
				autoOpen : false,
				resizable : false,
				modal : true,
				height : height,
				width : width,
				title : titulo,
				buttons : [
					{
						text : "Cancelar",
						class : "btn btn-default",
						click : function() {
							$(this).dialog("close");
						}
					},
					{
		                text: "Siguiente" ,
						class: "btn btn-primary",
		                click: function () {

		                	var idPersona;
		                    var sinDomicilio=false;
		                    var mensajeSinDomicilio="";
                            parent.WizardSeguroIvroIndivCtrl.config.datos.title = undefined;
                            parent.WizardSeguroIvroIndivCtrl.config.bandera = true;
		                    $.ajax({
		                        url : '/${mvn.web.app.root}/wizard/individual/validaDomicilio?'+Math.random(),
		                        dataType : 'json',
		                        success : function(response) {

		                            sinDomicilio = response.sinDomicilio;
		                            mensajeSinDomicilio=response.ErrorFormGeneral;
		                            idPersona = response.idPersona;

		                            console.log("clave idPersona: "+idPersona);
		                            console.log(sinDomicilio)

		                            if(sinDomicilio==true&&mensajeSinDomicilio!=""){

		                                console.log("Entrando al servicio de Domicilio");
		                                $divError = $('<div></div>');
		                                $divError.dialog({
		                                    autoOpen : false,
		                                    resizable : false,
		                                    width: 400,
		                                    height : 'auto',
		                                    title : 'Mensaje del sistema',
		                                    modal : true,
		                                    close: function(){
		                                        console.log("entrando a la llamada del servicio");
		                                        parent.callbackActualizarDomicilio();
		                                    },
		                                    buttons : {
		                                        "Aceptar" : function() {
		                                            $(this).dialog('close');
		                                        }
		                                    }
		                                }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

		                                var htmlError ='<div class="alert alert-info">'+
		                                    ''+
		                                    '<strong>Importante: </strong>'+ mensajeSinDomicilio + '</div>';
		                                $divError.html(htmlError);
		                                $divError.dialog('open');

		                            }else{
		                                console.log(sinDomicilio)
		                                iniciaRenovacionEx();
		                            }

		                        },
		                        error : function(error) {
		                            $.unblockUI();
		                            var msgError = error.msgError;
		                            if (typeof msgError === 'undefined') {
		                                msgError = 'Ocurri\u00f3 un error inesperado al consultar el domicilio.';
		                            }
		                            parent.construirDialogo("#dialogoMensajes",
		                                "Mensaje de sistema", msgError, true,
		                                undefined, undefined, 250, 400);
		                        }
		                    });
		                  $( this ).dialog( "close" );
		                }
		            }
				]
			});
			objDialogo.dialog('open');
		},

		validarCompraRenovacion: function(){
			var esRenovacion = false;
			var esExtemporanea = false;
			var cambiarRenovacionACompra = false;
			// var esCompra = undefined;

			var mapa;
			$.ajax({
				dataType: "json",
				url: '/${mvn.web.app.root}/wizard/individual/validarSeguroComprado/' + this.config.datos.idPersona,
				async: false,
				success: function(resultado){
					esRenovacion = resultado.esRenovacion;
					esExtemporanea = resultado.esExtemporanea;
                    cambiarRenovacionACompra = resultado.cambiarRenovacionACompra;
					mapa= resultado;
				}
			});

			return mapa;
		}

};

function iniciaRenovacionEx(){
	parent.WizardSeguroIvroIndivCtrl.setUrlRenovacion();
    parent.WizardSeguroIvroIndivCtrl.config.datos.title = undefined;
    parent.WizardSeguroIvroIndivCtrl.config.bandera = true;

    var _wizard = null;
    _wizard = parent.WizardSeguroIvroIndivCtrl;
    _wizard.config.bandera = true;
    _wizard.config.datos.title = undefined;
    _wizard.abrir();

}

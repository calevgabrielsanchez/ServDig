/*
 * JS de control del Wizard de Carta de No Adeudo.
 * 
 */

var urlCartaNoAdeudo = '/${mvn.web.app.root}'+'/wizard/cartaNoAdeudo/';

var WizardCartaNoAdeudoCtrl = {		
		
		init : function(_contenedor, _idPersona, _idTipoPersona, _rfc, _curp){
						
			this.config.contenedor = _contenedor;
			this.config.idPersona = _idPersona;
			this.config.idTipoPersona = _idTipoPersona;
			this.config.rfc = _rfc;
			this.config.curp = (_curp == undefined || _curp == null || _curp == "") ? "000" : _curp;
			
	        var d = $('#' + _contenedor);
	        
	        /*
		     * Configuracion del dialogo
		     */
	        this.dialogo = d.dialog({
	            title: this.config.title,
	            autoOpen: false,
	            closeOnEscape: false,
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
	        
	        this.dialogo.dialog({
	        	beforeClose: function(event, ui) { 
	        		/*
					 * Workaround para evitar que el iframe se recarge al momento de
					 * cerrar el dialogo de jQuery, este comportamiento es resultado
					 * de un bug de jQuery
					 */
					$('iframe#cartaNoAdeudoFrame').attr("src", "");
	        	},
	        	close: function (event, ui) {
	        		$(this).dialog('destroy').empty();
	        	}
	        });
		},
				
		setOnCloseCallback : function(_fnCallback){
			this.callbacks = _fnCallback;
		},
		
		config : {
			url :  urlCartaNoAdeudo,
			title:"IMSS Digital",
			contenedor : {}, 
			idPersona:"",
			idTipoPersona:"",
			rfc:"",
			curp:""
		},
		
		callbacks : {},
		
		dialogo : {},	
		
		abrir : function(){
            console.log("imprimiendo RFC: "+this.config.rfc);
			var str = this.config.rfc;
			var resp = str.indexOf("#");
			if(resp>=0){
			var strFin = str.replace("#", "Ñ");
			console.log("imprimiendo RFC sin Ñ: "+strFin);
			this.init(this.config.contenedor, this.config.idPersona, this.config.idTipoPersona, strFin, this.config.curp);
			this.dialogo.dialog('open');
			var url = this.config.url + this.config.idPersona + "/" + this.config.idTipoPersona + "/" + strFin + "/" + this.config.curp;
			}else{
				this.init(this.config.contenedor, this.config.idPersona, this.config.idTipoPersona, this.config.rfc, this.config.curp);
				this.dialogo.dialog('open');
				var url = this.config.url + this.config.idPersona + "/" + this.config.idTipoPersona + "/" + this.config.rfc + "/" + this.config.curp;
			}
									
			$('#' + this.config.contenedor).html('<iframe id="cartaNoAdeudoFrame" src="' + url
				+ '" width="100%" height="320px" frameborder="0" frameborder="0" />');
						
		},
		
		cerrar : function(){
			this.dialogo.dialog('close');
			this.dialogo.dialog('destroy');
		}
};

function construirDialogoCartaNoAdeudo(divId, titulo, mensaje, error, callback, callbackForXButton, height, width) {
	$("#textoMensajeGeneral").html(mensaje);
	$("#textoMensajeGeneral").removeAttr("style");
	if (error) {
		$("#textoMensajeGeneral").attr("style", "color: red;");
	} else {
		$("#textoMensajeGeneral").attr("style", "color: blue;");
	}
	
	if(height == undefined){
		height=150;
	}
	if(width == undefined){
		width=400;
	}
	
	var objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		close: function(event, ui) {
				    if ( event.originalEvent && $(event.originalEvent.target).closest(".ui-dialog-titlebar-close").length ) {
				    	if ( callbackForXButton != undefined && jQuery.isFunction(callbackForXButton)) {
				    		callbackForXButton();
				    	}
				    }
		  		},
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	objDialogo.dialog('open');
}

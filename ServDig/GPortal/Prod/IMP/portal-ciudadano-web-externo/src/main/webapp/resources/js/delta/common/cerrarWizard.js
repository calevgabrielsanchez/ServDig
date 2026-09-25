/**
 * 
 */
var alertCierreWizard = {
	idDivContenedor: 'divConfirmCerrarWizard',
	abrir : function (_wizard) {
		//verificamos si la bandera de mostrar mensaje existe
		if(_wizard.getMostrarAlerta != undefined) {
			//
			if(_wizard.getMostrarAlerta()) {
				this.construirDialog(_wizard);
				return false;
			} else {
				alertCierreWizard.cierraWizard(_wizard);
				return true;
			}
		} else {
			this.construirDialog(_wizard);
			return false;
		}
		
	}, 
	construirDialog: function(_wizard) {
		//creamos el dialogo
		var _divCerrar = $('#'+this.idDivContenedor);
		//preguntamos si esta seguro que desea salir del tramite
		$('#divConfirmCerrarWizard').html("Est&aacute; seguro que desea salir del tr&aacute;mite? Se perderan los datos capturados");
		//creamos el dialogo
        this.dialogoCerrar = _divCerrar.dialog({
            title: "Confirmaci&oacute;n requerida",
            closeOnEscape: false,
            autoOpen: false,
            width : 'auto',
            heigth: 'auto',
            modal: true,
            resizable: false,
            overlay: {
                opacity: 0.5,
                background: "black"
            },
            buttons: {
    			"Si" : function() {
    				//si presiona que si
    				alertCierreWizard.cierraWizard(_wizard);
    				$(this).dialog("close");
    	        },
    	        "No" : function() {
      	          $(this).dialog("close");
      	        }
    		}
        });
	}, 
	cierraWizard: function(_wizard) {
		//verificamos si el metodo de cerrar wizard existe
		if(_wizard.cerrarWizard != undefined) {
			//de existir llamamos al metodo
			_wizard.cerrarWizard();
		} else {
			if(_wizard.limpiarElementosSesion != undefined) {
				_wizard.limpiarElementosSesion();
			}
			if(_wizard.cerrar != undefined) {
				_wizard.cerrar();
			}
		}
	},
	dialogoCerrar :{}
}
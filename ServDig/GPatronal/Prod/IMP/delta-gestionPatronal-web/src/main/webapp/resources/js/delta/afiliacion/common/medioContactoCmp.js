var oDialogoMedioContacto;
var dtMediosContacto;

var columnasMedioContactoCmp = [ 
{
	mDataProp : "tipoMedioContacto.idTipoMedioContacto",
	bVisible : false
},{
	"sTitle" : "Tipo de medio de contacto",
	"mDataProp" : "tipoMedioContacto.descripcion",
	sWidth : "200px"
}, {
	"sTitle" : "detalle",
	"mDataProp" : "desFormaContacto",
	sWidth : "200px"
} ];


var fnAbrirDialogoAgregarMedioContacto = function(){
	accionSobreMediosContacto = "agregar";
	ocultarFormulariosMediosContactoAlta();
	$('#tipoMedioContacto\\.idTipoMedioContacto').removeAttr("disabled");
	$('#tipoMedioContacto\\.idTipoMedioContacto').val("-1");
	oDialogoMedioContacto.dialog('open');
}


function fnAbrirDialogoEliminarMedioContacto(view){
	if (view == 'fromRLAltaAgregar'){
		if(fnValidaRegistroSeleccionado( dtMediosContacto )){
			showPromptDialog("\u00BFDesea eliminar el elemento seleccionado de la lista?", fnEliminarMedioContacto);
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder eliminarlo.");
		}
	} else if (view == 'fromRLAltaModificar'){
		if(fnValidaRegistroSeleccionado( dtMediosContacto )){
			showPromptDialog("\u00BFDesea eliminar el elemento seleccionado de la lista?", fnEliminarMedioContacto);
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder eliminarlo.");
		}
	}
}

function fnAbrirDialogoModificarMedioContacto(view){
	accionSobreMediosContacto = "modificar";
	if (view == 'fromRLAltaAgregar'){
		if(fnValidaRegistroSeleccionado( dtMediosContacto )){
			mostrarFormModificacionMedioContacto(fnGetRowSelected(dtMediosContacto));
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder modificarlo.");
		}
	} else if (view == 'fromRLAltaModificar'){
		if(fnValidaRegistroSeleccionado( dtMediosContacto )){
			mostrarFormModificacionMedioContacto(fnGetRowSelected(dtMediosContacto));
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder modificarlo.");
		}
	}
}


function fnEliminarMedioContacto(){
	// el elemento se remueve de los dos datatables
	// removemos del datatable de la pantalla de agregar RL
	$(dtMediosContacto.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
        	dtMediosContacto.fnDeleteRow(this.nTr.sectionRowIndex);
        }
    });
	
	// removemos del datatable de la pantalla de modificar RL
	$(dtMediosContacto.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
        	dtMediosContacto.fnDeleteRow(this.nTr.sectionRowIndex);
        }
    });
}


/*
* Reutilizamos el dialogo definido en este form para mostrar la modificación
*/
function mostrarFormModificacionMedioContacto(medioContactoToModify){
	ocultarFormulariosMediosContactoAlta();
	fnOcultaErrores($("#errorMedioContacto"));
	$('#tipoMedioContacto\\.idTipoMedioContacto').val(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto);
	$('#tipoMedioContacto\\.descripcion').val(medioContactoToModify.tipoMedioContacto.descripcion);
	
	if (medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 1){
		$('#correoElectronico').show();	
		$('#correoElectronico\\.correo').val(medioContactoToModify.desFormaContacto);
		$('#correoElectronico\\.correo').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 2){
		$('#telefonoFijo').show();
		$('#telefonoFijo\\.numero').val(medioContactoToModify.telefonoFijo.numero);
		$('#telefonoFijo\\.extension').val(medioContactoToModify.telefonoFijo.extension);
		$('#telefonoFijo\\.claveLada').val(medioContactoToModify.telefonoFijo.claveLada);
		$('#telefonoFijo\\.numero').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 3){
		$('#telefonoMovil').show();
		$('#telefonoMovil\\.numero').val(medioContactoToModify.telefonoMovil.numero);
		$('#telefonoMovil\\.numero').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 4){
		$('#facebook').show();
		$('#facebook\\.cuenta').val(medioContactoToModify.facebook.cuenta);
		$('#facebook\\.cuenta').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 5){
		$('#twitter').show();
		$('#twitter\\.cuenta').val(medioContactoToModify.twitter.cuenta);
		$('#twitter\\.cuenta').focus();
	}
	
	// para evitar modificar el tipo de medio de contacto, limitamos a que se modifique el detalle.
	$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", true);
	
	oDialogoMedioContacto.dialog('open');
}


function ejecutarAccionMedioContacto(){
	fnHideErrores("form#agregarMedioContactoFormAlta");
	var oForm = $("form#agregarMedioContactoFormAlta").toObject();
	var array_trim = [idTipoContactoTwitter, idTipoContactoFacebook];
    $.each(array_trim, function(a_index, a_val) {
        if (a_val == $('#tipoMedioContacto\\.idTipoMedioContacto').val()) {
            var _tmpval = $('#tipoMedioContacto\\.descripcion').val()
            $('#tipoMedioContacto\\.descripcion').val(_tmpval.replace(/^\s+|\s+$/g));
        }
    });
	if (accionSobreMediosContacto == "agregar"){
		var desFormaContacto;
		
		if (oForm != undefined){
			
			desFormaContacto = fnGetDesFormaMedioContacto(oForm);
			
			if (desFormaContacto != undefined){
				oForm.desFormaContacto = desFormaContacto;
			} else {
				showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
				return;
			}
			if(fnValidaMedioContacto(oForm)){
				fnAgregarNuevaFilaMedioContacto(oForm);
				resetMedioContactoOptions();
			}
		} else {
			showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
		}
	} else if (accionSobreMediosContacto == "modificar"){
//		showMessageDialog("accionSobreMediosContacto: modificar");
		var idTipoContacto=$('#tipoMedioContacto\\.idTipoMedioContacto').val();
		var desTipoContacto=$('#tipoMedioContacto\\.descripcion').val();
		
		oForm.tipoMedioContacto.idTipoMedioContacto=idTipoContacto;
		oForm.tipoMedioContacto.descripcion=desTipoContacto;
		
		var desFormaContacto = fnGetDesFormaMedioContacto(oForm);
		oForm.desFormaContacto=desFormaContacto;
		
		// Row
		if(fnValidaMedioContacto(oForm))
			fnActualizarFilaMedioContacto(oForm, aPosMediosContacto);
	}
}

function fnGetDesFormaMedioContacto(oForm){
	var desFormaContacto;
	
	if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoCorreo){
		if (oForm.correoElectronico!=undefined && oForm.correoElectronico.correo != undefined && oForm.correoElectronico.correo != ""){
			desFormaContacto = oForm.correoElectronico.correo;
		}
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoFacebook){
		if (oForm.facebook!=undefined && oForm.facebook.cuenta!= undefined && oForm.facebook.cuenta!= ""){
			desFormaContacto = oForm.facebook.cuenta;
		}
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoFijo){
		
		if(oForm.telefonoFijo!=undefined 
				&& oForm.telefonoFijo.claveLada != undefined 
				&& oForm.telefonoFijo.extension != undefined
				&& oForm.telefonoFijo.numero != undefined){
			var claveLada;
			var extension;
			var numero;
			
			claveLada = oForm.telefonoFijo.claveLada != undefined ? oForm.telefonoFijo.claveLada : " ";
			extension = oForm.telefonoFijo.extension != undefined ? oForm.telefonoFijo.extension : " ";
			numero = oForm.telefonoFijo.numero != undefined ? oForm.telefonoFijo.numero : " ";
			
			desFormaContacto = claveLada + '-' + numero + '-' + extension;
		}
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoMovil){
		if (oForm.telefonoMovil!=undefined && oForm.telefonoMovil.numero!= undefined && oForm.telefonoMovil.numero!= ""){
			desFormaContacto = oForm.telefonoMovil.numero;
		}
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoTwitter){
		if (oForm.twitter!=undefined && oForm.twitter.cuenta!= undefined && oForm.twitter.cuenta!= ""){
			desFormaContacto = oForm.twitter.cuenta;
		}
	}
	return desFormaContacto;
}

function fnValidaMedioContacto(oForm){
	var desFormaContacto;
	
	if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoCorreo){
		if (oForm.correoElectronico.correo != undefined && oForm.correoElectronico.correo != ""){
			desFormaContacto = oForm.correoElectronico.correo;
		}
		var valCorreo = fnValidaCorreo(desFormaContacto);
		if (!valCorreo) {						
			this.fnDespliegaError($("#errorMedioContacto"), "La descripci&oacute;n no cuenta con un formato correcto de correo<br>");
			return false;
		}
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoFacebook){
		if (oForm.facebook.cuenta!= undefined && oForm.facebook.cuenta!= ""){
			desFormaContacto = oForm.facebook.cuenta;
		}
		var valFacebook = fnValidaFacebook(desFormaContacto);
		if (!valFacebook) {						
			this.fnDespliegaError($("#errorMedioContacto"), "La direcci&oacute;n de facebook no cuenta con un formato correcto.<br> " +
					"Ejemplo: http://www.facebook.com/cuenta, www.facebook.com/cuenta, facebook.com/cuenta");
			return false;
		}
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoFijo){
		var claveLada;
		var extension;
		var numero;
		
		claveLada = oForm.telefonoFijo.claveLada != undefined ? oForm.telefonoFijo.claveLada : " ";
		extension = oForm.telefonoFijo.extension != undefined ? oForm.telefonoFijo.extension : " ";
		numero = oForm.telefonoFijo.numero != undefined ? oForm.telefonoFijo.numero : " ";
		
		desFormaContacto = claveLada + '-' + numero + '-' + extension;
		
		return true;
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoMovil){
		if (oForm.telefonoMovil.numero!= undefined && oForm.telefonoMovil.numero!= ""){
			desFormaContacto = oForm.telefonoMovil.numero;
		}
		return true;
	} else if (oForm.tipoMedioContacto.idTipoMedioContacto == idTipoContactoTwitter){
		if (oForm.twitter.cuenta!= undefined && oForm.twitter.cuenta!= ""){
			desFormaContacto = oForm.twitter.cuenta;
		}
		var valTwitter = fnValidaTwitter(desFormaContacto);							
		if (!valTwitter){
			this.fnDespliegaError($("#errorMedioContacto"), " La descripci&oacute;n debe cumplir con un formato correcto de twitter: @nombre_usuario (hasta 15 car\u00E1cteres, letras, n\u00FAmeros y _ )");
			return false;
		}
	}
	return true;
}


function construirGridMediosContacto(idGridMediosContacto){
	var jqDataTableId='#'+idGridMediosContacto;
	dtMediosContacto = $(jqDataTableId).dataTable({
		bJQueryUI: false,
		bPaginate: true,
		bLengthChange: false,
		iDisplayLength: 5,
		bFilter: false,
		bSort: false,
		bInfo: false,
		bAutoWidth: false,
		//bServerSide : true,			
		aoColumns : columnasMedioContactoCmp,
		sPaginationType: "full_numbers",
		bProcessing : true,
		//sAjaxSource : context_path + '/afiliacion/alta/visualizarRepresentantes',
		//fnServerData : enviarParametrosRepresentante
	});
	
	inicializaEstiloGridParaMediosContacto($(jqDataTableId+" tbody"), dtMediosContacto);
	
	return dtMediosContacto;
}


function fnAgregarNuevaFilaMedioContacto(medioContacto,idDataTable){
	// pasamos los datos de contacto de un dtatable a otro para modificacion
	//dtMediosContactoRLAltaEnModificar.fnClearTable();
	dtMediosContacto.fnAddData(medioContacto);
//	dtMediosContactoRLAltaEnAgregar.fnAddData(medioContacto);
	oDialogoMedioContacto.dialog('close');
}

function fnActualizarFilaMedioContacto(medioContacto, rowNumber){
	dtMediosContacto.fnUpdate( medioContacto, rowNumber ); 
	oDialogoMedioContacto.dialog('close');
}

function validateTipoContactoOptionSelection(){
	if($("#agregarMedioContactoFormAlta #tipoMedioContacto\\.idTipoMedioContacto").val()==-1){
		return "* Seleccione el tipo de medio de contacto";
	}
}

function validateDatoContacto(){
	if($("#agregarMedioContactoFormAlta #tipoMedioContacto\\.idTipoMedioContacto").val()!=-1){
		var oForm = $("form#agregarMedioContactoFormAlta").toObject();
		var idTipoMContacto = oForm.tipoMedioContacto.idTipoMedioContacto;
		var desFormaContacto;
		var mensaje;
		if(idTipoMContacto == idTipoContactoMovil){
			desFormaContacto = oForm.telefonoMovil.numero;
			mensaje="* Proporcione el n\u00FAmero de tel\u00E9fono m\u00F3vil";
		}else if(idTipoMContacto == idTipoContactoCorreo){
			desFormaContacto = oForm.correoElectronico.correo;
			mensaje="* Proporcione una cuenta de correo v\u00E1lida";
		}else if(idTipoMContacto == idTipoContactoFacebook){
			desFormaContacto = oForm.facebook.cuenta;
			mensaje="* Proporcione una cuenta de facebook";
		}else if(idTipoMContacto == idTipoContactoTwitter){
			mensaje="* Proporcione una cuenta de twitter";
		}
		
		
		if(desFormaContacto == undefined || desFormaContacto == ""){
			return mensaje;
		}
		
	}
}

function validaLada(){
	if($("#agregarMedioContactoFormAlta #tipoMedioContacto\\.idTipoMedioContacto").val()!=-1){
		var oForm = $("form#agregarMedioContactoFormAlta").toObject();
		var idTipoMContacto = oForm.tipoMedioContacto.idTipoMedioContacto;
	
		if(idTipoMContacto == idTipoContactoFijo){
			var claveLada = oForm.telefonoFijo.claveLada;
			if(claveLada==undefined || claveLada=="")
				return "* Este dato es requerido";
		}
	}
}

function validaNumeroFijo(){
	if($("#agregarMedioContactoFormAlta #tipoMedioContacto\\.idTipoMedioContacto").val()!=-1){
		var oForm = $("form#agregarMedioContactoFormAlta").toObject();
		var idTipoMContacto = oForm.tipoMedioContacto.idTipoMedioContacto;
	
		if(idTipoMContacto == idTipoContactoFijo){
			var numeroFijo = oForm.telefonoFijo.numero;
			if(numeroFijo==undefined || numeroFijo=="")
				return "* Este dato es requerido";
		}
	}
}

function validaExtension(){
	if($("#agregarMedioContactoFormAlta #tipoMedioContacto\\.idTipoMedioContacto").val()!=-1){
		var oForm = $("form#agregarMedioContactoFormAlta").toObject();
		var idTipoMContacto = oForm.tipoMedioContacto.idTipoMedioContacto;
	
		if(idTipoMContacto == idTipoContactoFijo){
			var extension = extension = oForm.telefonoFijo.extension;
			if(extension==undefined || extension=="")
				return "* Este dato es requerido";
		}
	}
}


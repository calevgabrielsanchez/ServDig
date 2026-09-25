/**
 * File : medioContacto.js
 */

var uniqueMedioContactoIdentifier = 100001;
var instanciasMedioContacto = [];
datosPrueba = [
              	{ idVista: 1, clave:11, tipoMedioContacto:{idTipoMedioContacto:1, descripcion:"Telefono Fijo"}, 		desFormaContacto:"5555332211"},
              	{ idVista: 2, clave:22, tipoMedioContacto:{idTipoMedioContacto:1, descripcion:"Telefono Fijo"}, 		desFormaContacto:"5555332212"},
              	{ idVista: 3, clave:33, tipoMedioContacto:{idTipoMedioContacto:2, descripcion:"Telefono Movil"},		desFormaContacto:"5544332211"},
              	{ idVista: 4, clave:44, tipoMedioContacto:{idTipoMedioContacto:3, descripcion:"Correo Electronico"},	desFormaContacto:"mail_1@mail.com"},
              	{ idVista: 5, clave:55, tipoMedioContacto:{idTipoMedioContacto:3, descripcion:"Correo Electronico"},	desFormaContacto:"mail_2@mail.com"}
             ];

function inhabilitaEnterKey(event){
	var key;      
    if(window.event)
          key = window.event.keyCode; //IE
     else
          key = e.which; //firefox      
        
    return (key != 13);
}

function aplicarCaracteristicas(campoTipo, campoDescripcion){
	if ($(campoTipo).val()== 1 )
		ajustarMaxLenght(campoDescripcion,60);
	else if($(campoTipo).val()== 2 )
		ajustarMaxLenght(campoDescripcion,12);
	else if($(campoTipo).val()== 3 )
		ajustarMaxLenght(campoDescripcion,13);
	else if($(campoTipo).val()== 4 ){
		ajustarMaxLenght(campoDescripcion,100);
		if($(campoDescripcion).val()=='')
			$(campoDescripcion).val("facebook.com/");
	}else if($(campoTipo).val()== 5 )
		ajustarMaxLenght(campoDescripcion,100);
}


function ajustarMaxLenght(nombreElemento,size){
	$(nombreElemento).attr("maxlength",size);
}


function MedioContacto(idContenedor, despliegue, tpPropietario, idPropietario, idSolicitud, idSujetoObligado, mostrarTituloInfoActual){
	this.uniqueId = uniqueMedioContactoIdentifier++;
	instanciasMedioContacto[this.uniqueId] = this;
	this.idContenedor = idContenedor;
	this.idSolicitud = idSolicitud;
	this.propietario = tpPropietario;
	this.idPropietario = idPropietario;
	this.idSujetoObligado = idSujetoObligado;
	this.despliegue = despliegue;
	this.idVistaActual = null;
	this.cargar = true;
	
	this.acciones = {grid : "../../gestionMediosContacto-web/medioContacto/paginarMedioContacto", 
					combo : "../../gestionMediosContacto-web/medioContacto/cargarComboTipoMedioContacto"};
	
	this.columnasMC=[
	                 	 {bVisible: false, sTitle: "id", 				 mDataProp: "idVista"}, 
						 {bVisible: false, sTitle: "calve", 			 mDataProp: "clave"}, 
						 {bVisible: false, sTitle: "idTipo", 			 mDataProp: "tipoMedioContacto.idTipoMedioContacto"},
						 {bVisible: true,  sTitle: "Medio de Contacto",  mDataProp: "tipoMedioContacto.descripcion", },
						 {bVisible: true,  sTitle: "Descripci&oacute;n", mDataProp: "desFormaContacto"}
                     ];
							
	this.jqContenedor = "#"+this.idContenedor;
	this.jqSelectTipo = "#tipoMedioContacto"+this.uniqueId;
	this.jqTxtFldDesc = "#descMedioContacto"+this.uniqueId;
	this.jqTxtFldDesc = "#descMedioContacto"+this.uniqueId;
	this.jqClaveMedio = "#clveMedioContacto"+this.uniqueId;
	this.jqidVistaMCt = "#idVistaMedioCto"+this.uniqueId;
	this.jqGridBaseDD = "#mcgBase"+this.uniqueId;
	this.jqGridSolici = "#mcgTram"+this.uniqueId;
	this.jqDivDialogo = "#mcDialogForm"+this.uniqueId;
	this.jqDivDialogoConfirmacionEliminarMedioContacto = "#mcDialogConfirmacionEliminarMedioContacto"+this.uniqueId;
	this.jqFormaMedio = "#mcforma"+this.uniqueId;
	this.jqDlgSelectn = "#dgErrorSinSeleccionMC"+this.uniqueId; 

	
	this.jqTabla = "mcLayoutTable"+this.uniqueId;
	this.jqTitleBD = "#mcTituloBaseDatos"+this.uniqueId;
	this.jqTitleTT = "#mcTituloTramite"+this.uniqueId;
	this.jqCeldaBD = "#mcCeldaBaseDatos"+this.uniqueId;
	this.jqCeldaTT = "#mcCeldaTramite"+this.uniqueId;
	this.jqFilaBtn = "#mcLayoutBotonesRow"+this.uniqueId;
	
	this.accion = "";
	this.seleccion = undefined;
	this.indexMod = 0;
	
	this.gridBase = null;
	this.gridTramite = null;
	this.dlgMedioContacto = null;
	this.dlgConfirmacionEliminarMedioContacto = null;
	this.dlgSelecRegistro = null;

	this.init = function(){
		this.agregarInterfazMC(this.jqContenedor);
		var source = this.acciones["grid"];
		var params = "";
		if(this.idSolicitud != undefined && this.idSolicitud != null && this.idSolicitud > 0 ){
			params = "?";
			params += "tpPropietario="+this.propietario;
			params += "&idPropietario="+(this.idPropietario || 0);
			params += "&idSolicitud="+(this.idSolicitud || 0);
			params += "&idSujetoObligado="+(this.idSujetoObligado || 0);
			this.gridTramite = this.crearGrid(this.jqGridSolici, this.columnasMC, source+params);
		}else{
			this.gridTramite = this.crearGrid(this.jqGridSolici, this.columnasMC);
		}
		
		if(this.propietario != undefined && this.propietario != null 
		&& this.idPropietario != undefined && this.idPropietario != null){
			params = "?";
			params += "tpPropietario="+this.propietario;
			params += "&idPropietario="+(this.idPropietario || 0);
			params += "&idSolicitud=0";
			params += "&idSujetoObligado="+(this.idSujetoObligado || 0);
			
			this.gridBase = this.crearGrid(this.jqGridBaseDD, this.columnasMC, source+params);
		}else{
			this.gridBase = this.crearGrid(this.jqGridBaseDD, this.columnasMC);
		}
				
		this.initDlgMedioContacto();
		this.initDlgConfirmacionEliminarMedioContacto();
		cargarCombo(this.propietario, this.acciones, this.jqSelectTipo, this.uniqueId);
		this.modoDespliegue(despliegue);
		
	};
	
	/**
	 * Esta función se debe cambiar para que use templates
	 */
	this.agregarInterfazMC = function (jqContenedor){
		var idTabla = this.jqTabla;
		var html ="";
		html += "<div class='page_holder' style='margin:0px;width: 100% !important;'>";
		html += "<div class='contenedor' style='width: 100% !important;'><div class='row'>";
		html += "	<div class='cell' >";
		html += "		<div class='row' id='medioContacto"+this.uniqueId+"'></div>";
		html += "	</div>";
		html += "</div></div>";
		html += "</div>";
		$(jqContenedor).append(html);
		html += "<div id='mcDialogConfirmacionEliminarMedioContacto"+this.uniqueId+"' style='width: 100%; display:none;' title='&iquest;Eliminar elemento?'>";
		html += "Este dato de contacto ser&aacute; eliminado, &iquest;Est&aacute; Ud. seguro?";
		html += "</div>";
		$(jqContenedor).append(html);
		$("#medioContacto"+this.uniqueId).append("<TABLE id='"+idTabla+"' style='width: 100%;height: 260px !important;'></TABLE");
		var idFila="mcHeaderRow"+this.uniqueId;
		$("#"+idTabla).append("<tr id="+idFila+"></tr>");
		html  = "<td id=\"mcTituloBaseDatos"+this.uniqueId+"\">";
		if(mostrarTituloInfoActual==undefined || (mostrarTituloInfoActual!=undefined && mostrarTituloInfoActual==true))
			html += "	<h3>Informaci&oacute;n Actual</h3>";
		html += "</td>";
		html += "<td id=\"mcTituloTramite"+this.uniqueId+"\">";
		html += "	<h3>Informaci&oacute;n a modificar</h3>";
		html += "</td>";
		$("#"+idFila).append(html);
		idFila="mcGridsRow"+this.uniqueId;
		$("#"+idTabla).append("<tr id="+idFila+"></tr>");
		html  = "<td id=\"mcCeldaBaseDatos"+this.uniqueId+"\">";
		html += "	<table id='mcgBase"+this.uniqueId+"' style='width: 100%; vertical-align: top;'>";
		html += "		<thead></thead>";
		html += "		<tbody style='width: 100%;'></tbody>";
		html += "	</table>";
		html += "</td>";
		$("#"+idFila).append(html);
		html  = "<td id='mcCeldaTramite"+this.uniqueId+"'>";
		html += "	<table id='mcgTram"+this.uniqueId+"' style='width: 100%; vertical-align: top;'>";
		html += "		<thead></thead>";
		html += "		<tbody style='width: 100%;'></tbody>";
		html += "	</table>";
		html += "</td>";
		$("#"+idFila).append(html);
		var idFila="mcLayoutBotonesRow"+this.uniqueId;
		$("#"+idTabla).append("<tr id="+idFila+"></tr>");
		html  = "<td colspan='2' align='left'>";
		html += "<div class='opciones'>";
		html += "	<form>";
		html += "		<div class='opcion'>";
		html += "			<input type='button' onclick='instanciasMedioContacto["+this.uniqueId+"].dialogoMedioContacto(\"Agregar\");' class='mboton' value='Agregar' style='font-size: .8em !important;'>";
		html += "		</div>";
		html += "		<div class='opcion'>";
		html += "			<input type='button' onclick='instanciasMedioContacto["+this.uniqueId+"].dialogoMedioContacto(\"Modificar\");' class='mboton' value='Modificar' style='font-size: .8em !important;'>";
		html += "		</div>";
		html += "		<div class='opcion'>";
		html += "			<input type='button' onclick='instanciasMedioContacto["+this.uniqueId+"].dialogoMedioContacto(\"Eliminar\");' class='mboton' value='Eliminar' style='font-size: .8em !important;'>";
		html += "		</div>";
		html += "	</form>";
		html += "</div></td>";
		$("#"+idFila).append(html);
		html = "<DIV id='mcDialogForm"+this.uniqueId+"' style='width: 100%;'>";
		html += "<div class='page_holder' style='width:100% !important; margin: 0 0 0 0;'>";
		html += "<div class='row'>";
		html += "<div class='cell form-comment'>";
		html += "<form id='mcforma"+this.uniqueId+"'>";
		html += "<input type='hidden' id='idMedioContacto"+this.uniqueId+"'>";
		html += "<input type='hidden' id='idVistaMedioCto"+this.uniqueId+"'>";
		html += "<fieldset>";
		html += "	<span id='tipoMedioContactoErr"+this.uniqueId+"' class=\"error hiddenElement\"></span>";
		html += "	<label>Medio de Contacto</label>";
		html += "	<select id='tipoMedioContacto"+this.uniqueId+"'>Medio de Contacto";
		html += "		<option>== Seleccione una opci&oacute;n ==</option>";
		html += "	</select>";
		html += "</fieldset>";
		html += "<fieldset>";
		html += "	<span id='descMedioContactoErr"+this.uniqueId+"' class=\"error hiddenElement\"></span>";
		html += "	<label>Descripci&oacute;n:</label>";
		html += "	<input type='text' id='descMedioContacto"+this.uniqueId+"' onfocus=\"aplicarCaracteristicas('#tipoMedioContacto"+this.uniqueId+"','#descMedioContacto"+this.uniqueId+"')\"  onkeypress='return inhabilitaEnterKey(event)' style='text-transform: none !important;'>";
		html += "</fieldset>";
		html += "</form>";
		html += "</div>";
		html += "</div>";
		html += "</div>";
		html += "</DIV>";
		$(jqContenedor).append(html);
	};
	
	this.initDlgMedioContacto = function(){
		if(this.dlgMedioContacto != undefined && this.dlgSelecRegistro != null){
			this.dlgMedioContacto.destroy();
		}
		var dialogo = this;
		this.dlgMedioContacto =  $(this.jqDivDialogo).dialog(
				{
					autoOpen : false,
					resizable : false,
					modal : true,
					height : 250,
					width : 500,
					title : "Medio de Contacto",
					buttons : {
						"Aceptar" : function() {
							var cerrar = true;
							switch(dialogo.accion){
							case "Agregar":
								cerrar = dialogo.agregarMedioContacto();
								break;
							case "Modificar":
								cerrar = dialogo.modificarMedioContaco();
								break;
							}
							if(cerrar){
								$(this).dialog("close");
							}
						},
						"Cancelar" : function() {
							dialogo.ocultarErrores("tipoMedioContacto");
							dialogo.ocultarErrores("descMedioContacto");
							$(this).dialog("close");
						}
					}
				});
	};
	
	this.initDlgConfirmacionEliminarMedioContacto = function(){
		if(this.dlgConfirmacionEliminarMedioContacto != undefined && this.dlgSelecRegistro != null){
			this.dlgConfirmacionEliminarMedioContacto.destroy();
		}
		var dialogo = this;
		this.dlgConfirmacionEliminarMedioContacto =  $(this.jqDivDialogoConfirmacionEliminarMedioContacto).dialog(
			{
				autoOpen:false,
				resizable: false,
				modal: true,
				height:200,
				modal: true,
				buttons: {
					"Eliminar": function(){
						dialogo.eliminarMedioContacto();
						$(this).dialog("close"); // mover a un colbac
					},
					'Cancelar': function (){
						$( this ).dialog( "close" );
					}
				}
			}
		);
	};
	
	this.modoDespliegue = function(modo){
		$(this.jqTitleBD).removeAttr("style");
		$(this.jqCeldaBD).removeAttr("style");
		$(this.jqTitleTT).removeAttr("style");
		$(this.jqCeldaTT).removeAttr("style");
		$(this.jqFilaBtn).removeAttr("style");

		var ocultar = "display:none;";
		var mostrar = "width:100%; vertical-align:top; display:block";
		
		switch(modo){
			case 1:
				$(this.jqTitleBD).attr("style", mostrar);
				$(this.jqCeldaBD).attr("style", mostrar);
				$(this.jqTitleTT).attr("style", ocultar);
				$(this.jqCeldaTT).attr("style", ocultar);
				$(this.jqFilaBtn).attr("style", ocultar);
				break;
			case 2:
				$(this.jqTitleBD).attr("style", ocultar);
				$(this.jqCeldaBD).attr("style", ocultar);
				$(this.jqTitleTT).attr("style", mostrar);
				$(this.jqCeldaTT).attr("style", mostrar);
				$(this.jqFilaBtn).attr("style", mostrar);
				break;
			default:
				$(this.jqTitleBD).attr("style", mostrar);
				$(this.jqCeldaBD).attr("style", mostrar);
				$(this.jqTitleTT).attr("style", mostrar);
				$(this.jqCeldaTT).attr("style", mostrar);
				$(this.jqFilaBtn).attr("style", mostrar);
				break;
		}
		this.cancelarLlamadasAlServidorParaGrid(this.gridBase);
		this.gridBase.fnDraw();
		this.cancelarLlamadasAlServidorParaGrid(this.gridTramite);
		this.gridTramite.fnDraw();
	};
	
	function obtenerDatosGrid(gridTramite){
		return gridTramite.fnSettings().aoData;
	};
	
	this.dialogoMedioContacto = function(accion){
		this.accion = accion;
		var datos;
		var registroSeleccionado = false;
		if (this.accion == "Modificar" || this.accion == "Eliminar") {
			var fila = undefined, index = undefined;
			var data = obtenerDatosGrid(this.gridTramite);
			for(var i = 0; i<data.length; i++){
				datos = data[i];
		        if( $(datos.nTr).hasClass('row_selected')){
		        	fila = datos._aData;
		        	index = i;
		        }
		    };
		    this.indexMod = index;
		    this.seleccion = fila;
		    registroSeleccionado = this.seleccion != undefined;
		}
		if(registroSeleccionado || this.accion == "Agregar"){
			$(this.jqFormaMedio).clearForm();
			switch(this.accion){
				case "Modificar":
					this.cargarDatosEnForma();
					break;
				case "Eliminar":
					this.dlgConfirmacionEliminarMedioContacto.dialog("open");
					//this.eliminarMedioContacto();
					return;
				case "Agregar":
					$("#tipoMedioContacto"+this.uniqueId).removeAttr("disabled");
				break;
			}
			this.dlgMedioContacto.dialog("open");
		}else{
			alert("Debe de seleccionar un registro para realizar esta accion");
		}
	};
	
	this.cargarDatosEnForma = function(){
		$(this.jqidVistaMCt).val(this.seleccion["idVista"]);
		$(this.jqClaveMedio).val(this.seleccion["clave"]);
		$(this.jqTxtFldDesc).val(this.seleccion["desFormaContacto"]);
		var select = document.getElementById(this.jqSelectTipo.replace("#", ""));
		opcion = select.namedItem("tpMC_"+this.uniqueId+"_"+this.seleccion["tipoMedioContacto"]["idTipoMedioContacto"]);
		opcion.selected = true;
		$("#tipoMedioContacto"+this.uniqueId).attr("disabled", "true");
	};
	
	this.eliminarMedioContacto = function(){
		this.cancelarLlamadasAlServidorParaGrid(this.gridTramite);
		this.gridTramite.fnDeleteRow(this.indexMod, true);
	};
	
	this.cancelarLlamadasAlServidorParaGrid = function(grid){
		var settings = grid.dataTableSettings;
		for ( var i=0 ; i<settings.length ; i++ ){
			if ( settings[i].nTable == $(grid)[0]){
				settings[i].oFeatures.bServerSide = false;
			}
		}
	};

	this.agregarMedioContacto = function(){
		var newRow = this.obtenerDatos();
		if(newRow){
			this.cancelarLlamadasAlServidorParaGrid(this.gridTramite);
			this.gridTramite.fnAddData( newRow );
			this.gridTramite.fnPageChange("last");
		}
		return newRow;
	};
	
	this.modificarMedioContaco = function(){
		var modRow = this.obtenerDatos();
		if(modRow){
			this.cancelarLlamadasAlServidorParaGrid(this.gridTramite);
			this.gridTramite.fnUpdate( modRow, this.indexMod, undefined, true, true );
		}
		return modRow;
	};
	
	this.obtenerDatos = function(){
		var select = document.getElementById(this.jqSelectTipo.replace("#", ""));
		var index = select.selectedIndex;
		var opciones = select.options;
		var cveTipoMedioContacto = select.value;
		var dscTipoMedioContacto = opciones[index].text;
		var desFormaContacto = $(this.jqTxtFldDesc).val();
		var modRow = { 	
				idVista: $(this.jqidVistaMCt).val(),
				clave:	$(this.jqClaveMedio).val() || "", 
				tipoMedioContacto:{ idTipoMedioContacto : cveTipoMedioContacto,  descripcion: dscTipoMedioContacto},
				desFormaContacto:desFormaContacto};
		if(this.validarDatos(cveTipoMedioContacto, desFormaContacto)){
			return modRow;
		}		
		return false;
	};
	
	this.validarDatos = function(cveTipoMedioContacto, desFormaContacto){
		this.ocultarErrores("tipoMedioContacto");
		this.ocultarErrores("descMedioContacto");
		
		if ((cveTipoMedioContacto != undefined && cveTipoMedioContacto != null && cveTipoMedioContacto != "" && cveTipoMedioContacto > 0) || 
			(desFormaContacto != undefined && desFormaContacto != null && desFormaContacto != "")){
			if(cveTipoMedioContacto != undefined && cveTipoMedioContacto != null 
			&& cveTipoMedioContacto != "" && cveTipoMedioContacto > 0){
				if(desFormaContacto != undefined && desFormaContacto != null 
				&& desFormaContacto != ""){
					if(desFormaContacto.length <= 255){
						var expReg = "";									
						switch(cveTipoMedioContacto){
							case "1":							
								var valCorreo = checaMail(desFormaContacto);							
								if (!valCorreo) {						
									this.fnShowError("descMedioContacto", "La descripci&oacute;n no cuenta con un formato correcto de correo");
									return false;
								}
								if (desFormaContacto.length > 150) {
									this.fnShowError("descMedioContacto", "La longitud m&aacute;xima del campo descripci&oacute;n es de 150 caracteres");
									return false;
								}
								break;
							case "2":
								var valTelefono = PermiteSoloNumeros(desFormaContacto);
								if (!valTelefono) {
									this.fnShowError("descMedioContacto", "La descripci&oacute;n no cuenta con un formato correcto de tel&eacute;fono(Solo permite n&uacute;meros)");
									return false;
								}
								if (desFormaContacto.length > 12) {
									this.fnShowError("descMedioContacto", "La longitud m&aacute;xima del campo descripci&oacute;n es de 12 caracteres");
									return false;
								}
								break;
							case "3":
								var valTelefono = PermiteSoloNumeros(desFormaContacto);
								if (!valTelefono) {
									this.fnShowError("descMedioContacto", "La descripci&oacute;n no cuenta con un formato correcto de tel&eacute;fono(Solo permite n&uacute;meros)");
									return false;
								}
								if (desFormaContacto.length > 12) {
									this.fnShowError("descMedioContacto", "La longitud m&aacute;xima del campo descripci&oacute;n es de 12 caracteres");
									return false;
								}
								break;
							case "4":			
								var valFacebook = checaFacebook(desFormaContacto);
								if (!valFacebook) {
									this.fnShowError("descMedioContacto", "La direcci&oacute;n de facebook no cuenta con un formato correcto.");
									return false;
								}
								
								if (desFormaContacto.length > 50) {
									this.fnShowError("descMedioContacto", "La longitud m&aacute;xima del campo descripci&oacute;n es de 50 caracteres");
									return false;
								}
								break;
							case "5":
								var valTwitter = checaTwitter(desFormaContacto);							
								if (!valTwitter){
									this.fnShowError("descMedioContacto", "La descripci&oacute;n debe cumplir con un formato correcto de twitter: @nombre_usuario (hasta 15 caracteres)");
									return false;
								}
								if (desFormaContacto.length > 50) {
									this.fnShowError("descMedioContacto", "La longitud m&aacute;xima del campo descripci&oacute;n es de 50 caracteres");
									return false;
								}
								break;
							default:
								expReg = "/\w{1,255}/";
								break;
						}

					}else{
						this.fnShowError("descMedioContacto", "La longitud m&aacute;xima del campo descripci&oacute;n es de 255 caracteres");
						return false;
					}
				}else{
					this.fnShowError("descMedioContacto", "Debe agregar un valor en la descripci&oacute;n del medio de contacto");
					return false;
				}
			}else{
				this.fnShowError("tipoMedioContacto", "Debe seleccionar un tipo de medio de contacto");
				return false;
			}
		} else {
			this.fnShowError("descMedioContacto", "Debe agregar un valor en la descripci&oacute;n del medio de contacto");
			this.fnShowError("tipoMedioContacto", "Debe seleccionar un tipo de medio de contacto");
			return false;
		}
		
		return true;
	};
	
	function checaMail(campo){	
	
		var result = false;
		var exr = /^\w+([\.-]?\w+)*@[0-9a-z\-\.]+\.[a-z]{1,4}$/i;

		if(exr.test(campo)){
			result = true;
		  }
		  
		 return result;
	   
	};
	
		
	function checaTwitter(campo){		
		
		var expresion =  /^(\@)[A-Za-z0-9_]{1,15}$/;
		
		if(expresion.test(campo)){
			return true;
		}else{
			return false;
		}
	    
	};
	
	function checaFacebook(campo){		
		var result = false;
		//var exr = /^(http\:\/\/|https\:\/\/)?(?:www\.)?facebook\.com\/(?:(?:\w\.)*#!\/)?(?:pages\/)?(?:[\w\-\.]*\/)*([\w\-\.]*)/;
		  var exr = /^(http\:\/\/|https\:\/\/)?(?:www\.)?facebook\.com\/(?:(?:\w\.)*\/)?(?:pages\/)?(?:[\w\-\.]*\/)*([\w\-\.]+)+$/;
		if(exr.test(campo)){
			result = true;
		  }
		 return result;
	};

	
	function PermiteSoloNumeros(campo) {		
	    var valida = campo.replace(/[^0-9()]*/gi,"");
	    if (campo.length==valida.length) {
	    	return true
	    }
	    return false;
	};
	
	this.ocultarErrores = function(nombre){
		var nClassShow = 'showElement';
		var nClassHidden ='hiddenElement';
		var campo = "#"+nombre+"Err"+this.uniqueId;
		$(campo).removeClass(nClassShow);
		$(campo).addClass(nClassHidden);
		$(campo).html();
	};
	
	this.fnShowError = function(nombre , mensajeError){
		var nClassShow = 'showElement';
		var nClassHidden ='hiddenElement';
		var campo = "#"+nombre+"Err"+this.uniqueId;
		$(campo).removeClass(nClassHidden);
		$(campo).addClass(nClassShow);
		$(campo).html(mensajeError);
	};
		
	this.buscarDatoEnGrid = function(data){
		return false;
	};
	
	this.replicarDatosBase = function(dataServer){
		if(this.idSolicitud != undefined && this.idSolicitud != null && this.idSolicitud > 0){
			return;
		}
		var data;
		var vista = 0;
		if(this.cargar){
			for(registro in dataServer.aaData){
				data = dataServer.aaData[registro];
				var newRow = { 	
					idVista: data["idVista"],
					clave:	 data["clave"], 
					tipoMedioContacto:{
						idTipoMedioContacto : data["tipoMedioContacto"]["idTipoMedioContacto"], 
						descripcion : data["tipoMedioContacto"]["descripcion"]
									  },
					desFormaContacto:data["desFormaContacto"]
				};
				
				this.gridTramite.fnAddData( newRow );
				
				if(data["idVista"] > vista){
					vista = data["idVista"];
				}
				this.idVistaActual = vista;
			}
			this.cargar = false;
		}
	};
	
	this.crearGrid = function(idGrid, columModel, source, data) {
		var grid;
		var mc = this;
		if(source != undefined && source != ""){
			grid = $(idGrid).dataTable({
				bJQueryUI : false,
				bFilter : false,
				bInfo : false,
				bSort : false,
				bPaginate : true,
				bAutoWidth : false,
				bProcessing : true,
				sPaginationType : "full_numbers",
				aoColumns : columModel,
				iDisplayLength: 4,
				iLength: 4,
//TODO Confirmar si funciona de lo contrario quitar el comentario				bServerSide : true,
				sAjaxSource : source,
				fnServerData :	function(sSource, aoData, fnCallback) {
									aoData.push({
										"name" : "sSearch",
										"value" : ''
									});
									var mc2 = mc;
									var wrapper = new Object();
									wrapper.aoData = aoData;
									$.postJSON(sSource, wrapper, function(data) {
										fnCallback(data);
										var mc3 = mc2;
										if(data.aaData.length > 0){
											mc3.replicarDatosBase(data);
										}
									});
									
								},
				parentMCObject : this
			});
		}else{
			grid = $(idGrid).dataTable({
				bJQueryUI : false,
				bFilter : false,
				bInfo : false,
				bSort : false,
				bPaginate : true,
				//TODO Eliminar si no funciona -Se agrego bProcessing-
				//bProcessing : true,
				sPaginationType : "full_numbers",
				bAutoWidth : false,
				bProcessing : true,
				aoColumns : columModel,
				aaData: data,
				iDisplayLength: 4,
				iLength: 4,
				parentMCObject : this
			});	
		}

		$(idGrid + " tbody").click(function(event) {
			var seleccionar = !$(event.target.parentNode).hasClass('row_selected');
			$(grid.fnSettings().aoData).each(function() {
				$(this.nTr).removeClass('row_selected');
			});
			if (seleccionar){
				$(event.target.parentNode).addClass('row_selected');
			}
		});
		return grid;
	};
	
	function cargarCombo(propietario, acciones, jqSelectTipo, uniqueId) {
		if(propietario != undefined && propietario != null){
		var sSource = acciones["combo"];
		var request = $.ajax({
			url : sSource,
			async : false,
			type : "POST",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
		});
		request.done(function(response) {
			if (response.errors != undefined) {
				alert(response.errors);
			} else {
				var data = response.catalogo;
				var campo;
				for (index in data) {
					campo = data[index];
					$(jqSelectTipo).append("<option value='"+campo.cveIdTipoContacto+"' id='tpMC_"+uniqueId+"_"+campo.cveIdTipoContacto+"'>"+campo.desTipoContacto+"</option>");
				}
			}
		});
		}else{
			$(jqSelectTipo).append("<option value='1' id='tpMC_"+uniqueId+"_1'>Telefono Fijo</option>");
			$(jqSelectTipo).append("<option value='2' id='tpMC_"+uniqueId+"_2'>Telefono Movil</option>");
			$(jqSelectTipo).append("<option value='3' id='tpMC_"+uniqueId+"_3'>Correo Electronico</option>");
		}
	};

	this.obtenerListaMediosContacto = function(){
		var lista = [];
		var medioContacto;
		var dataOrigen = obtenerDatosGrid(this.gridTramite);
		for(registro in dataOrigen){
			data = dataOrigen[registro]._aData;
			medioContacto = new Object();
			medioContacto.idVista = data["idVista"];
			medioContacto.clave = data["clave"];
			medioContacto.desFormaContacto = data["desFormaContacto"];
				tipoMedioContacto = new Object();
				tipoMedioContacto.idTipoMedioContacto = data["tipoMedioContacto"]["idTipoMedioContacto"];
				tipoMedioContacto.descripcion = data["tipoMedioContacto"]["descripcion"];
			medioContacto.tipoMedioContacto = tipoMedioContacto;
			lista.push(medioContacto);
		}
		return lista;
	};
	
	this.destroy = function(){
		var tabla = document.getElementById(this.jqTabla);
		documen.removeElement(tabla);
		var dialogo = document.getElementById(this.jqDivDialogo);
		documen.removeElement(dialogo);
		var dialogo1 = document.getElementById(this.jqDivDialogoConfirmacionEliminarMedioContacto);
		document.removeElement(dialogo1);
	};
	
}

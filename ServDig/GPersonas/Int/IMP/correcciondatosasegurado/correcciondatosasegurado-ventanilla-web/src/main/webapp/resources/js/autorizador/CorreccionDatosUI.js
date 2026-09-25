 function CorreccionDatosUI(module) {
  this.module = module;
  this.init();
}

CorreccionDatosUI.prototype.colorCampos = function () {
	var origenes = this.metadata.ui.components[0].components[4].components;
	var destinos = this.metadata.ui.components[0].components[5].components;
	var orig=this.module.controller.model[this.metadata.ui.components[0].model].informacionBDTU.idOrigen;
		for (var i = 0; i < destinos.length; i++) {
			$("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).css("background-color", "").css("color", "");
			if ($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val() === "") {
			  $("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).css("background-color", "#545454");
			} else {
				if ($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val() !==
				  $("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val()){
				  if(this.reemplazarEspecialesSindo($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val()) !==
				      this.reemplazarEspecialesSindo($("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val())) {
					  $("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).css("background-color", "#D0021B").css("color", "#FFF");
				  }
			  }
			}
			if ($("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val() === "") {
				$("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).css("background-color", "#545454");
			}
		}
  $("#" + this.module.render.replaceAll(destinos[destinos.length-1].field, ".", "_")).css("background-color", "#545454");
  this.mostrarDiferentes();  
  this.comparaLugarN();
  //reglas qeu solo aplican para fuentes distintas de BDTU 6
  if(orig !="6"){
    this.comparaMesSINDOyCANASE();
    this.ocultarCamposDefault();
  }
	this.ocultarDocumentoProbatorio();
	this.mostrarSinCambios();
};

CorreccionDatosUI.prototype.reemplazarEspecialesSindo = function (val) {
	if(val != undefined){
		val = val.replace(/[^\w\s]/gi, '');
	}
    return val;
};

CorreccionDatosUI.prototype.comparaMesSINDOyCANASE = function () {
  var origenes = this.metadata.ui.components[0].components[4].components;
  var destinos = this.metadata.ui.components[0].components[5].components;
  //COMPARA MES PARA SINDO Y CANASE
  if($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val() != ""){
    fechaOrig=(($("#" + this.module.render.replaceAll(origenes[5].field, ".", "_")).val()));
    fechaDest=(($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val()));
    this.comparaMes(fechaOrig,fechaDest);
  }
};

CorreccionDatosUI.prototype.mostrarDiferentes = function () {
	var origenes = this.metadata.ui.components[0].components[4].components;
	var destinos = this.metadata.ui.components[0].components[5].components;
	var orig=this.module.controller.model[this.metadata.ui.components[0].model].informacionBDTU.idOrigen;
	var fecha=this.module.controller.model[this.metadata.ui.components[0].model].informacionBDTU.fechaNacimiento;
	for (var i = 0; i < destinos.length; i++) {
	    $("label[for='"+ destinos[i].field +"']").hide();
	    $("label[for='"+ origenes[i].field +"']").hide();
	    $("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).hide();
	    $("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).hide();
	    if(($("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val())!=($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val())){	 
	      if(this.reemplazarEspecialesSindo($("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val()) !== 
		this.reemplazarEspecialesSindo($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val())){
		      $("label[for='"+ destinos[i].field +"']").show();
		      $("label[for='"+ origenes[i].field +"']").show();
		      $("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).show();
		      $("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).show();
			}
	     }
	}	
};

CorreccionDatosUI.prototype.ocultarCamposDefault = function (){
	var origenes = this.metadata.ui.components[0].components[4].components;
	var destinos = this.metadata.ui.components[0].components[5].components;
	$("label[for='informacionBDTU.nacionalidad']").hide();
	$("label[for='informacionBDTU.datosDocumentoProbatorio']").hide();
	$("#" + this.module.render.replaceAll(destinos[7].field, ".", "_")).hide();
	$("#" + this.module.render.replaceAll(destinos[8].field, ".", "_")).hide();
	$("label[for='informacionRENAPO.nacionalidad']").hide();
	$("label[for='informacionRENAPO.datosDocumentoProbatorio']").hide();
	$("#" + this.module.render.replaceAll(origenes[7].field, ".", "_")).hide();
	$("#" + this.module.render.replaceAll(origenes[8].field, ".", "_")).hide();
};

CorreccionDatosUI.prototype.comparaMes = function(fechaOrig,fechaDest){
	var origenes = this.metadata.ui.components[0].components[4].components;
	var destinos = this.metadata.ui.components[0].components[5].components;
	if(fechaOrig !== undefined && fechaDest !== undefined){
	    fOrig = fechaOrig.split("/");
	    fDest = fechaDest.split("/");
	    //Compara mes
	    if(fOrig[1]==fDest[1]){
		  $("label[for='informacionBDTU.fechaNacimiento']").hide();
		  $("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).hide();
		  $("label[for='informacionRENAPO.fechaNacimiento']").hide();
		  $("#" + this.module.render.replaceAll(origenes[5].field, ".", "_")).hide();
	    }
	    if((fOrig[1] != fDest[1]) && fDest[1]!= "00"){
		  ($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val(fDest[1]));
	    }
	    if(fDest[1] == "00"){
		    ($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val(""));
		    $("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).css("background-color", "#545454");
	    }  
	 }
};

CorreccionDatosUI.prototype.comparaLugarN = function(){
	var origenes = this.metadata.ui.components[0].components[4].components;
	var destinos = this.metadata.ui.components[0].components[5].components;
	lugOrig=($("#" + this.module.render.replaceAll(origenes[6].field, ".", "_")).val());
	lugDest=($("#" + this.module.render.replaceAll(destinos[6].field, ".", "_")).val());
	if(lugOrig != undefined && lugDest != undefined){
		if(lugDest.indexOf(lugOrig) != -1){
		$("label[for='informacionRENAPO.lugarNacimiento']").hide();
		$("label[for='informacionBDTU.lugarNacimiento']").hide();
		$("#" + this.module.render.replaceAll(destinos[6].field, ".", "_")).hide();
		$("#" + this.module.render.replaceAll(origenes[6].field, ".", "_")).hide();
		}
	}
};

CorreccionDatosUI.prototype.mostrarMensaje = function(){
	var origenes = this.metadata.ui.components[0].components[4].components;
	var destinos = this.metadata.ui.components[0].components[5].components;
	  $('#cambios').show();
	  $('#cambiosB').show();
	  $("label[for='informacionRENAPO.datosDocumentoProbatorio']").hide();
	  $("label[for='informacionBDTU.datosDocumentoProbatorio']").hide();
	  $("#" + this.module.render.replaceAll(origenes[origenes.length-1].field, ".", "_")).hide();
	  $("#" + this.module.render.replaceAll(destinos[destinos.length-1].field, ".", "_")).hide();
};

CorreccionDatosUI.prototype.mostrarSinCambios = function(){
	if($("#panelDatosBDTU label:hidden").length == this.metadata.ui.components[0].components[5].components.length){
	    //todo oculto mostrar banner sin cambios
	    $("#cambios").show();
	    $("#cambiosB").show();
	}
};

CorreccionDatosUI.prototype.ocultarDocumentoProbatorio = function(){
	if($("#panelDatosBDTU label:hidden").length == this.metadata.ui.components[0].components[5].components.length-1){
		var origenes = this.metadata.ui.components[0].components[4].components;
		var destinos = this.metadata.ui.components[0].components[5].components;
	    $("label[for='informacionRENAPO.datosDocumentoProbatorio']").hide();
		$("label[for='informacionBDTU.datosDocumentoProbatorio']").hide();
		$("#" + this.module.render.replaceAll(origenes[origenes.length-1].field, ".", "_")).hide();
		$("#" + this.module.render.replaceAll(destinos[destinos.length-1].field, ".", "_")).hide();
	}
};

CorreccionDatosUI.prototype.init = function () {
  this.metadata = {
    ui: {
      components: [
        {
          type: "FormPanelComponent",
          id: "panelCorreccionDatos",
          name: "correccionDatos",
          model: "detalle",
          postFetch: "colorCampos",
          label: "Correcci\u00F3n de datos",
          level: 3,
          components: [
            {type: "TextFieldComponent", field: "nss", label: "NSS", disabled: true, labelStyle:true},
            {
              type: "SelectFieldComponent",
              label: "Tipo de NSS",
              id: "tipoNSSSelectField",
              field: "tipoNSS.idTipoNSSCorreccion",
              data: "tiposNSS",
              disabled: true
            },
            
            {
                  type: "PanelComponent",
                  label: "Tipo de regularizaci\u00F3n",
                  level: 4,
                  components: [
                    {type: "CheckBoxFieldComponent", field: "grupoCorreccion.nombre", label: "Correcci\u00F3n de nombre", disabled: true},
                    {type: "CheckBoxFieldComponent", field: "grupoCorreccion.datosEstadisticos", label: "Correcci\u00F3n de datos estad\u00EDsticos", disabled: true},                    
                  ],
                  layout: [[{span: 4}, {span: 4}]]
            },
            {
            	type: "NavegacionPersonaNSSComponent",
				postFetch: "mostrarSinCambios",
            	id: "navegacionPersonaNSS",
            	model:"detalle"
            },
            {
              type: "PanelComponent",
              id: "panelDatosRenapo",
              components: [
                {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true},
                {type: "TextFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true},
                {type: "TextFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true},
                {type: "TextFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true},
                {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo", disabled: true},
                {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true},
                {type: "TextFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
                {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true},
                {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8}
              ],
              layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
            },                        
            {
              type: "PanelComponent",
              id: "panelDatosBDTU",
              components: [
                {type: "TextFieldComponent", field: "informacionBDTU.curp", label: "CURP", disabled: true},
                {type: "TextFieldComponent", field: "informacionBDTU.apellidoPaterno", label: "Primer apellido", disabled: true},
                {type: "TextFieldComponent", field: "informacionBDTU.apellidoMaterno", label: "Segundo apellido", disabled: true},
                {type: "TextFieldComponent", field: "informacionBDTU.nombre", label: "Nombre(s)", disabled: true},
                {type: "TextFieldComponent", field: "informacionBDTU.sexo", label: "Sexo",disabled: true},
                {type: "TextFieldComponent", field: "informacionBDTU.fechaNacimiento", label: "Fecha de nacimiento", disabled: true},
                {type: "TextFieldComponent", field: "informacionBDTU.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
                {type: "TextFieldComponent", field: "informacionBDTU.nacionalidad", label: "Nacionalidad", disabled: true},
                {type: "TextAreaFieldComponent", field: "informacionBDTU.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8}
              ],
              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
            },
            {
	            type:"CardLayoutComponent",
	            id:"correccionCardLayout",
	            components:[
	              {
		              type: "ButtonGroupComponent",              
		              components: [                
		                {
		                  type: "ButtonComponent",
		                  id: "btnRegresarCorreccion",
		                  label: "Regresar",
		                  command: "regresarCorreccion",
		                  className: "btn-default"
		                },
		                {
		                  type: "ButtonComponent",
		                  label: "Siguiente",
		                  command: "cambiosAutorizarController.init",
		                  className: "btn-primary",
		                  href:"top",
		                  id: "btnSiguiente"
		                }
		              ]
		          },
		          //en caso de requerir mas cambios en los botones utilizar un elemento mas en el cardlayout actual
		          {
		              type: "ButtonGroupComponent",              
		              components: [                
		                {
		                  type: "ButtonComponent",
		                  id: "btnRegresarCorreccion",
		                  label: "Regresar",
		                  command: "regresarCorreccion",
		                  className: "btn-default"
		                },
		                {
		                  type: "ButtonComponent",
		                  label: "Siguiente",
		                  command: "siguienteCorreccion",
		                  className: "btn-primary",
		                  href:"top",
		                  id: "btnSiguiente"
		                }
		              ]
		          }
	            ]
	        },
            {
              type: "ModalComponent",
              title: "Confirmar operaci\u00F3n",
              id: "confirmarModal",
              size: "modal-lg",
              body: {
                type: "PanelComponent",
                components: [
                  {type: "ResumenCorreccionComponent", id:"resumenCorreccion",model:"resumenCorreccion"}
                ],
                layout: [[{span: 12}]]
              },
              footer: {
                type: "PanelComponent",
                components: [
                  {type: "LabelComponent"}, 
                  {type: "ButtonComponent", className: "btn-primary", label: "Aceptar", command: "confirmar"}
                ],
                layout: [[{span: 8}, {span: 4}]]
              }
            }
          ],
          layout: [[{span: 4},{span: 8}], [{span: 12}], [{span: 12}], [{span: 6}, {span: 6}], [{span: 12}],[{span: 12}]]
        }
      ],
      layout: [[{span: 12}]]
    }
  };
};
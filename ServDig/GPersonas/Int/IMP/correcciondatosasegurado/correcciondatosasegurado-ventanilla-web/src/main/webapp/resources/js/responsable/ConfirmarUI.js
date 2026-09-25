function ConfirmarUI(module) {
  this.module = module;
  this.init();
  this.TAMANIO_TEXTAREA = "2em";
  this.TAMANIO_TEXTAREA_CADENA = 20;
}

ConfirmarUI.prototype.tamanioGrid = function () {
	for(var i = 2;i< 5;i++){
		if($("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).val().length<this.TAMANIO_TEXTAREA_CADENA){
			$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("height", this.TAMANIO_TEXTAREA);
		}else{
			$("#" + this.module.render.replaceAll(this.panel.components[i].field, ".", "_")).css("word-wrap", "break-word");
		}
	}
	if($("#" + this.module.render.replaceAll(this.panel.components[9].field, ".", "_")).val() === ""){
		$("#" + this.module.render.replaceAll(this.panel.components[9].field, ".", "_")).parent().parent().parent().parent().parent().remove();
	}
};


ConfirmarUI.prototype.init = function () {
  this.panel={
          type: "FormPanelComponent",
          id: "panelCorreccionDatos",
          name: "correccionDatos",
          model: "detalle",
          postFetch: "tamanioGrid",
          label: "Confirmar operaci\u00F3n",
          level: 3,
          components: [
            {type: "TextFieldComponent", field: "informacionRENAPO.folio", label: "Folio", disabled: true, labelStyle:true},
            {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true, labelStyle:true},
            {type: "TextAreaFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true, labelStyle:true},
            {type: "TextAreaFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true, labelStyle:true},
            {type: "TextAreaFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true, labelStyle:true},
            {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo", disabled: true, labelStyle:true},
            {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true, labelStyle:true},
            {type: "TextFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true, labelStyle:true},
            {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true, labelStyle:true},
            {type:"TextAreaFieldComponent", field:"informacionRENAPO.curpsHistoricas",label:"CURPS Hist\u00F3ricas",disabled:true,rows:3},
            {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8},            
            {type: "ResumenCorreccionComponent", id:"resumenCorreccion",model:"resumenCorreccion"},
            {
            	type : "CardLayoutComponent",
				id : "responsableConfirmarButtonsCardLayout",
				components : [
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
			                  command: "confirmar",
			                  className: "btn-primary"
			                }
			              ]
				    }, 
				    {
				    	type: "ButtonGroupComponent",
				    	components: [                
			                {
			                  type: "ButtonComponent",
			                  id: "btnRegresarCorreccion",
			                  label: "Regresar",
			                  command: "regresarCorreccion",
			                  className: "btn-default"
			                }
			            ]
				    }
				]
            }
          ],
          layout: [[{span:3}],[{span:3},{span:3},{span:3},{span:3}],[{span:3},{span:3},{span:3},{span:3}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
        };
};
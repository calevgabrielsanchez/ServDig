function FormaCorreccionDatosComponent(render) {
  this.render = render;
}
FormaCorreccionDatosComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="formCorreccionDatos_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
FormaCorreccionDatosComponent.prototype.getClassName = function ( source, model) {
  // console.log("fullDate: " + this.component.fullDate + " source:" + source + " model: " + model);
  if(source === 'DISTRITO FEDERAL' && model === 'CIUDAD DE MÉXICO')
  {
	  model ='DISTRITO FEDERAL';
  }
  if(model === 'NaN')
	 {
	  	model ='';
	 }
  if( this.render.isEmpty( model ) ){
    return "bg-dark";
  }else if(model==='Hombre' || model==='Mujer' || model==='No Binario'){
    model = model.toUpperCase();
  }
  // Cambiar los # por Ñ
  
  if( source !== model.replace(/#/g, '\u00D1') ){
    return "bg-danger";
  }
  return "";
};
FormaCorreccionDatosComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);

  if (model === undefined) {
    model = {};
  }
  
  var source = this.render.getModel(this.component.source);
  if (source === undefined) {
    source = {};
  }

  if(this.component.fullDate !== true){
    if(!this.render.isEmpty(model.fechaNacimiento) && model.fechaNacimiento.length > 8){
        model.fechaNacimiento = model.fechaNacimiento.substring(3,5);
    }
  }
  
  var metadata = {type: "FormPanelComponent", label: this.component.label, name: this.component.model, model:this.component.model, entity:"correccionDatos",
    layout: [ [{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}] ],
    components: [
      { type: "TextFieldComponent", readOnly:"true",  field:"curp", label:"CURP", className: this.getClassName( source.curp, model.curp )  },
      { type: "TextFieldComponent", readOnly:"true", field:"apellidoPaterno", className: this.getClassName( source.apellidoPaterno, model.apellidoPaterno ), label:"Primer apellido"},
      { type: "TextFieldComponent", readOnly:"true", field:"apellidoMaterno", className: this.getClassName( source.apellidoMaterno, model.apellidoMaterno ), label:"Segundo apellido"},
      { type: "TextFieldComponent", readOnly:"true", field:"nombre", className: this.getClassName( source.nombre, model.nombre ), label:"Nombre(s)"},
      { type: "TextFieldComponent", readOnly:"true", field:"sexo", className: this.getClassName( source.sexo, model.sexo ), label:"Sexo"},
      { type: "TextFieldComponent", readOnly:"true", field:"fechaNacimiento", className: this.component.fullDate === true ? this.getClassName( source.fechaNacimiento, model.fechaNacimiento ) : this.getClassName( source.fechaNacimiento.substring(3,5), model.fechaNacimiento ) , label:"Fecha de nacimiento"},
      { type: "TextFieldComponent", readOnly:"true", field:"lugarNacimiento", className: this.getClassName( ""+parseInt(source.idlugarNacimiento), ""+parseInt(model.idlugarNacimiento) ), label:"Lugar de nacimiento"},
      { type: "TextFieldComponent", readOnly:"true", field:"nacionalidad", className: this.getClassName( source.nacionalidad, model.nacionalidad ), label:"Nacionalidad"},
      { type: "TextAreaFieldComponent", readOnly:"true", field:"datosDocumentoProbatorio", className: this.getClassName( source.datosDocumentoProbatorio, model.datosDocumentoProbatorio ), label:"Datos del documento probatorio", rows: 8 }      
    ]
  };
  
  var html = "";
  var panel = new FormPanelComponent(this.render);
  html += panel.draw(metadata);  
  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  return html;
};

FormaCorreccionDatosComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#formCorreccionDatos_" + this.render.toId(this.component.id)).html(html);
};

FormaCorreccionDatosComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "edit":
      this.edit();
      break;
    case "save":
      this.save();
      break;
  }
};


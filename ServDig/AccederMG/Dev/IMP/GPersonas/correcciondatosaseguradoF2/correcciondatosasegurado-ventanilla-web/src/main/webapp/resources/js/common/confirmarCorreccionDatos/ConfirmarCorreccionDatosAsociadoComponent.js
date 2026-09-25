function ConfirmarCorreccionDatosAsociadoEntryComponent(render) {
  this.render = render;
}
ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.draw = function (component) {
  this.component = component;

  
  var html = '<div id="correccionDatosAsociadoEntry_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  
  var parentModel = this.render.getModel(this.component.parentModel);
  
  var i;
  var tabla = "";
  
  tabla += '<table class="table table-striped">';
  tabla += '<thead><tr>';
  tabla += '<th>Origen de informaci\u00f3n</th>';
  tabla += '<th>CURP</th>';
  tabla += '<th>Nombre</th>';
  tabla += '<th>Lugar de nacimiento</th>';
  tabla += '<th>Fecha de nacimiento</th>';
  tabla += '<th>Sexo</th>';
  tabla += '<th>Estatus del cambio</th>';
  tabla += '<th>Fecha de proceso</th>';
  tabla += '</tr></thead>';
  tabla += '<tbody>';
  if(model.bdtu!= undefined)
  {
	  if(model.bdtu.apellidoMaterno === null
      && model.bdtu.apellidoPaterno === null
	  && model.bdtu.curp === null
	  && model.bdtu.curpsHistoricas === null
	  && model.bdtu.datosDocumentoProbatorio === null
	  && model.bdtu.fechaNacimiento === null
	  && model.bdtu.lugarNacimiento === null
	  && model.bdtu.nacionalidad === null
	  && model.bdtu.nombre === null
	  && model.bdtu.sexo === null ){
		model.bdtu="";		  
	  }
  }
   if(model.cizUno!= undefined)
  {
	   if(model.cizUno.apellidoMaterno === null
      && model.cizUno.apellidoPaterno === null
	  && model.cizUno.curp === null
	  && model.cizUno.curpsHistoricas === null
	  && model.cizUno.datosDocumentoProbatorio === null
	  && model.cizUno.fechaNacimiento === null
	  && model.cizUno.lugarNacimiento === null
	  && model.cizUno.nacionalidad === null
	  && model.cizUno.nombre === null
	  && model.cizUno.sexo === null ){		  
	  model.cizUno ="";}
  }
   if(model.cizDos!= undefined)
  {
	  if(model.cizDos.apellidoMaterno === null
      && model.cizDos.apellidoPaterno === null
	  && model.cizDos.curp === null
	  && model.cizDos.curpsHistoricas === null
	  && model.cizDos.datosDocumentoProbatorio === null
	  && model.cizDos.fechaNacimiento === null
	  && model.cizDos.lugarNacimiento === null
	  && model.cizDos.nacionalidad === null
	  && model.cizDos.nombre === null
	  && model.cizDos.sexo === null ){		  
	  model.cizDos ="";}
  }

	  if(model.cizTres){
	    if(model.cizTres.apellidoMaterno === null
      && model.cizTres.apellidoPaterno === null
	  && model.cizTres.curp === null
	  && model.cizTres.curpsHistoricas === null
	  && model.cizTres.datosDocumentoProbatorio === null
	  && model.cizTres.fechaNacimiento === null
	  && model.cizTres.lugarNacimiento === null
	  && model.cizTres.nacionalidad === null
	  && model.cizTres.nombre === null
	  && model.cizTres.sexo === null ){		  
	  model.cizTres = ""; }
	  }
  if(model.canase!= undefined)
  {
	   if(model.canase.apellidoMaterno === null
      && model.canase.apellidoPaterno === null
	  && model.canase.curp === null
	  && model.canase.curpsHistoricas === null
	  && model.canase.datosDocumentoProbatorio === null
	  && model.canase.fechaNacimiento === null
	  && model.canase.lugarNacimiento === null
	  && model.canase.nacionalidad === null
	  && model.canase.nombre === null
	  && model.canase.sexo === null ){		  
	   model.canase ="";}
  }
  if(model.historico!= undefined)
  {
	    if(model.historico.apellidoMaterno === null
      && model.historico.apellidoPaterno === null
	  && model.historico.curp === null
	  && model.historico.curpsHistoricas === null
	  && model.historico.datosDocumentoProbatorio === null
	  && model.historico.fechaNacimiento === null
	  && model.historico.lugarNacimiento === null
	  && model.historico.nacionalidad === null
	  && model.historico.nombre === null
	  && model.historico.sexo === null ){		  
	   model.historico = "";} 
  }
	  
	
	   tabla += this.drawRow( parentModel.renapo, model.cizDos, "CIZ2");
	   tabla += this.drawRow( parentModel.renapo, model.canase, "CANASE");
	   tabla += this.drawRow( parentModel.renapo, model.historico, "HISTORICO");
	   tabla += this.drawRow( parentModel.renapo, model.bdtu, "BDTU");
	   tabla += this.drawRow( parentModel.renapo, model.cizTres, "CIZ3");
	   tabla += this.drawRow( parentModel.renapo, model.cizUno, "CIZ1");
  
  tabla += '</tbody>';
  tabla += '</table>';

  var metadata = {type: "PanelComponent",
    layout: [[{span: 3}, {span: 3}, {span: 3}, {span: 3}], [{span: 12}]],
    components: [

      {type: "LabelComponent",className:"pull-right text-right", tag:"label", label: "NSS"},
      {type: "LabelComponent", label: model.nss},
      {type: "LabelComponent",className:"pull-right text-right", tag:"label", label: "Detalle"},
      {type: "LabelComponent", label: model.detalle},
      {type: "HTMLComponent", html: tabla}
    ]
  };

  var html = "";
  var panel = new PanelComponent(this.render);
  html += panel.draw(metadata);
//  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  return html;
};

ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.drawRow = function (renapo, model, name) {
  var row = "";
  var className = "";
  
  if( model !== undefined &&  model !== ""){
  
    row += '<tr>';
    row += '<td>'+ name +'</td>';
	if( this.customCompare( renapo.curp , model.curp ))
	{
		row += '<td>'+ this.render.htmlEncode(model.curp)+'</td>';
	}
	else
	{
		row += '<td class="danger">'+ this.render.htmlEncode(model.curp)+'</td>';
	}
    
    className = "bg-danger";
    if( this.customCompare( renapo.nombre , model.nombre ) && 
        this.customCompare( renapo.apellidoPaterno , model.apellidoPaterno ) && 
        this.customCompare( renapo.apellidoMaterno , model.apellidoMaterno ) ) {        
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(this.render.nvl(model.apellidoPaterno,"") + " " + this.render.nvl(model.apellidoMaterno,"") + " " + this.render.nvl(model.nombre,""))+'</td>';

    className = "bg-danger";
    if( this.customCompare( ""+ parseInt(renapo.idlugarNacimiento) , ""+parseInt(model.idlugarNacimiento )) ) {        
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(model.lugarNacimiento)+'</td>';
    className = "bg-danger";
    if( this.customCompare( renapo.fechaNacimiento , model.fechaNacimiento ) ) {        
      className = "";
    }
	if(name === "BDTU" && renapo.fechaNacimiento.substring(3,5) === model.fechaNacimiento) {        
      className = "";
    }
	if(name === "CANASE" && renapo.fechaNacimiento.substring(3,5) === model.fechaNacimiento) {        
      className = "";
    }
	if(name === "CIZ1" && renapo.fechaNacimiento.substring(3,5) === model.fechaNacimiento) {        
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(model.fechaNacimiento)+'</td>';
    className = "bg-danger";
    if( this.customCompare( renapo.sexo , model.sexo.toUpperCase() ) ) {
      className = "";
    }
    row += '<td class="'+className+'">'+ this.render.htmlEncode(model.sexo)+'</td>';
    row += '<td>'+ this.render.htmlEncode(model.estatus)+'</td>';
    row += '<td>'+ this.render.htmlEncode(model.fechaProceso)+'</td>';
    row += '</tr>';
  }
  return row;
};


ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#correccionDatosAsociadoEntry_" + this.render.toId(this.component.id)).html(html);
};

//valida
// Comparar con los #
ConfirmarCorreccionDatosAsociadoEntryComponent.prototype.customCompare = function (renapo, model) {
  return !this.render.isEmpty( model ) && renapo === model.replace(/#/g, '\u00D1');
};

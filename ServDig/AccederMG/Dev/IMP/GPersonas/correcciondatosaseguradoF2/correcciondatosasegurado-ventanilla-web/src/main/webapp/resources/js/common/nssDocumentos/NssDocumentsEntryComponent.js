function NssDocumentsEntryComponent(render) {
  this.render = render;
}
NssDocumentsEntryComponent.prototype.draw = function (component) {
  this.component = component;

  
  var html = '<div id="nssDocumentsEntry_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
/**
 * model = { nss: "XXX", documentosProbatorios:[ { nombreArchivo:"docto.jpg", idDocBoveda:"XXXXX" } ]}
 * 
 * @returns {String}
 */

NssDocumentsEntryComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var i;
  var tabla1 = "";
  var tabla2 = "";
  
  
  tabla1 += '<table class="table table-striped">';
  tabla1 += '<thead><tr>';
  tabla1 += '<th>NSS Involucrados</th>';  
  if(this.component.agregado !== "undefined" && this.component.agregado === "true"){
	  tabla1 += '<th>Agregado por</th>';  
  }
  tabla1 += '</tr></thead>';
  tabla1 += '<tbody>';
  tabla1 += '<tr><td>';
  
  if(this.component.editable === "true"){
    tabla1 += '<a href="#" onclick="return module.controller.agregarNssDocumentoController.editarNss(';
    tabla1 += this.component.elemento;
	tabla1 += ',\'';
	tabla1 += this.component.idGrid;
	tabla1 += '\',\'';
	tabla1 += this.component.eliminar;
    tabla1 += '\');">';
    tabla1 += model.nss;
    tabla1 += '</a>';
  } else {
    tabla1 += '<label>';
    tabla1 += model.nss;
    tabla1 += '</label>';      
  }
  

  tabla1 += '</td>';
   if(this.component.agregado !== "undefined" && this.component.agregado === "true"){
	  tabla1 += '<td>';
	  tabla1 += model.origen == "2" ? "Responsable" : "Sistema";
	  tabla1 += '</td>';
  }
  tabla1 += '</tr>';
  tabla1 += '</tbody>';
  tabla1 += '</table>';
  
  tabla2 += '<table class="table table-striped">';
  tabla2 += '<thead><tr>';
  
  if(this.component.numerarDcotos !== "undefined" && this.component.numerarDcotos === "true"){
	   tabla2 += '<th>No.</th>';
  }
  
  tabla2 += '<th>Documentos probatorios del NSS</th>';  
  if(this.component.observaciones !== "undefined" && this.component.observaciones === "true"){
	  tabla2 += '<th>Observaciones</th>';  
  }
  tabla2 += '</tr></thead>';
  tabla2 += '<tbody>';
   for( i=0; i<model.documentosProbatorios.length; i++ ){
    tabla2 += '<tr>';
	
	if(this.component.numerarDcotos !== "undefined" && this.component.numerarDcotos === "true"){
	   tabla2 += '<td>';
	   tabla2 += i+1;
       tabla2 += '</td>';
	}
	
	
    tabla2 += '<td>';
	tabla2 += '<a href="#" onclick="return module.controller.mostrarDocumentonss(\'';
	tabla2 += model.documentosProbatorios[i].nombreArchivo + '\',\'';
	tabla2 += model.documentosProbatorios[i].idDocBoveda ;
	tabla2 += '\');">';
	tabla2 += model.documentosProbatorios[i].desDocumento;
	tabla2 += '</a>';    
    tabla2 += '</td>';
	
	if(this.component.observaciones !== "undefined" && this.component.observaciones === "true"){
	   tabla2 += '<td>';
	   tabla2 += model.observaciones;
       tabla2 += '</td>';
	}

    if(this.component.eliminar !== "undefined" && this.component.eliminar === "true"){
            tabla2 += '<td>';
			tabla2 += '<a href="#" onclick="return module.controller.agregarNssDocumentoController.eliminarDocumento(\''; 
			tabla2 += model.nss;
                        tabla2 += '\',\'';
                        tabla2 += model.documentosProbatorios[i].idDocBoveda;
			tabla2 += '\');">Eliminar</a>';		
          
          tabla2 += '</td>';
    }
    tabla2 += '</tr>';
  }
  tabla2 += '</tbody>';
  tabla2 += '</tbody>';
  tabla2 += '</table>';    
  
  var metadata = {type: "PanelComponent",
    layout: [[{span: 6}, {span: 6}]],
    components: [      
      {type: "HTMLComponent", html: tabla1},
      {type: "HTMLComponent", html: tabla2}
    ]
  };

  var html = "";
  var panel = new PanelComponent(this.render);
  html += panel.draw(metadata);

  return html;
};


NssDocumentsEntryComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#nssDocumentsEntry_" + this.render.toId(this.component.id)).html(html);
};

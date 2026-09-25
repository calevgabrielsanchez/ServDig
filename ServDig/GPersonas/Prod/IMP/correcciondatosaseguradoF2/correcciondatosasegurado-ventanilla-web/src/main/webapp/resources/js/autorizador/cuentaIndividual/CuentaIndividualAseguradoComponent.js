function CuentaIndividualAseguradoComponent(render) {
  this.render = render;
}
CuentaIndividualAseguradoComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="cuentaIndividualAsegurado_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CuentaIndividualAseguradoComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var j;
  if (model === undefined) {
    model = {};
  }
  if( this.component.state === undefined ){
    this.component.state = "CuentaIndividualNssComponent";
  }
  
  var metadata = {
    type: "PanelComponent",
    
    components: [          
      {type:"PanelComponent", id:"encabezadoNss" , 
	        	    components:[  
							{type:"LabelComponent" ,label: "NSS:"},
							{type:"LabelComponent", label: model.nss},
							{type:"LabelComponent", label: "Tipo de Regularización:"},
							{type:"LabelComponent",label: model.tipoRegularizacion }	
	        	      ],
	        	      layout:[[{span:3},{span:3},{span:3},{span:3}],[{span:12}]]
	  },
      {
        type: "IteratorComponent", indexName: "index",
        id: this.component.id + ".listaCuentaIndividual",
        model: this.component.model + ".listaCuentaIndividual",
        entry: {
          type: this.component.state,
          parentId: this.component.id, element: "index",
          id: this.component.id + ".listaCuentaIndividual[index]",
          model: this.component.model + ".listaCuentaIndividual[index]"
        }
      }
    ],
    layout: [ [{span: 12}],[{span: 12}] ]
  };

  var html = "";  
  var panel = new PanelComponent(this.render);
  html += panel.draw( metadata );
  return html;

};

CuentaIndividualAseguradoComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#cuentaIndividualAsegurado_" + this.render.toId(this.component.id)).html(html);
};

CuentaIndividualAseguradoComponent.prototype.edit = function () {
  var model = this.render.getModel(this.component.model);
  this.component.state = "CuentaIndividualEditComponent";
  // Indicar en el modelo que se esta en modo de edicion.
  model.state = "edit";
  var parameters = { type: 'CuentaIndividualComponent', event: 'edit', key: "cuentaIndividualAsegurado", model:"" };
  this.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );  
  this.render.notify("CuentaIndividualAseguradoComponent", this.component.id);
};

CuentaIndividualAseguradoComponent.prototype.nss = function () {  
  var model = this.render.getModel(this.component.model);
  this.component.state = "CuentaIndividualNssComponent";
  // Indicar en el modelo que se esta en modo de nss.
  model.state = "nss";
  this.render.notify("CuentaIndividualAseguradoComponent", this.component.id);
};


CuentaIndividualAseguradoComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "edit":
      this.edit(parameters);
      break;
    case "nss":
      this.nss(parameters);
      break;
  }
};


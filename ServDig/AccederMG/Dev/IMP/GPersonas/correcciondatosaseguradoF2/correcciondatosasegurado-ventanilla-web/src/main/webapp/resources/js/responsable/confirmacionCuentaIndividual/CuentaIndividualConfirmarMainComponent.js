function CuentaIndividualConfirmarMainComponent(render) {
  this.render = render;
}

CuentaIndividualConfirmarMainComponent.prototype.draw = function (component) {
  this.component = component;

  //Configuracion de los botones iniciales
  var disabled = [false, false];
  this.render.module.controller.model.cuentaIndividualConfirmarButtonGroup = disabled;
  //Tab inicial
  this.render.module.controller.model.tabs_cuentaIndividual = 0;

  var html = '<div id="mainCuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};

CuentaIndividualConfirmarMainComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);

  if (model === undefined) {
    model = {};
  }

  var metadata = {type: "PanelComponent", layout: [[{span: 12}], [{span: 12}],[{span: 12}]],
    components: [
                 
	{type: "PanelComponent", label: "Informaci\u00f3n de la solicitud", level: 4,
	    components: [
	      {type: "LabelComponent", tag:"label", label: "Folio"},
	      {type: "LabelComponent", tag:"label", label: "Tipo de tramite de la solicitud"},
	      {type: "LabelComponent", label: model.solicitud.folio},
	      {type: "LabelComponent", label: model.solicitud.tipoTramite}
	    ],
	    layout: [[{span: 6}, {span: 6}], [{span: 6}, {span: 6}]]
	  },
	  
	  {type: "PanelComponent", label: "Confirmar cambios de Cuenta Individual", level: 3,layout: [[{span: 12}]],
	      components: [  
	                   
	                   {type: "TabPanelComponent", id: "cuentaIndividualConfirmar",
	                       tabs: [
	                         {title: "CERTIFICADOR",
	                           panel: {type: "PanelComponent",
	                             components: [
	                                 {type:"LabelComponent" , id: "infoCertificador", label:"Informaci\u00F3n del n\u00FAmero de seguridad social con el que se certificar\u00E1 el tr\u00E1mite"},         
	                                 {type: "IteratorComponent", indexName: "index",
	                                 id: "cuentaIndividualAsegurado.registrosPatronalesCertificador",
	                                 model: "cuentaIndividualAsegurado.registrosPatronalesCertificador",
	                                 entry: {
	                                   type: "CuentaIndividualAseguradoComponent",
	                                   parentId: "cuentaIndividualAsegurado.registrosPatronalesCertificador", element: "index",
	                                   id: "cuentaIndividualAsegurado.registrosPatronalesCertificador[index]",
	                                   model: "cuentaIndividualAsegurado.registrosPatronalesCertificador[index]"
	                                 }
	                               }], layout: [[{span: 12}],[{span: 12}]]}
	                         },
	                         {title: "ASOCIADO AL ASEGURADO",
	                           panel: {type: "PanelComponent",
	                             components: [
	               				  {type:"LabelComponent" , id: "infoAsociado"}, 
	                                 {type: "IteratorComponent", indexName: "index",
	                                 id: "cuentaIndividualAsegurado.registrosPatronalesAsociados",
	                                 model: "cuentaIndividualAsegurado.registrosPatronalesAsociados",
	                                 entry: {
	                                   type: "CuentaIndividualAseguradoComponent",
	                                   parentId: "cuentaIndividualAsegurado.registrosPatronalesAsociados", element: "index",
	                                   id: "cuentaIndividualAsegurado.registrosPatronalesAsociados[index]",
	                                   model: "cuentaIndividualAsegurado.registrosPatronalesAsociados[index]"
	                                 }
	                               }], layout: [[{span: 12}],[{span: 12}]]}
	                         },
	                         {title: "NO CORRESPONDEN AL ASEGURADO", panel: {type: "PanelComponent",
	                             components: [
	                                 {type:"LabelComponent" ,id: "infoNoCorresponde"},         
	                                 {type: "IteratorComponent", indexName: "index",
	                                 id: "cuentaIndividualAsegurado.registrosPatronalesNoPertenece",
	                                 model: "cuentaIndividualAsegurado.registrosPatronalesNoPertenece",
	                                 entry: {
	                                   type: "CuentaIndividualAseguradoComponent",
	                                   parentId: "cuentaIndividualAsegurado.registrosPatronalesNoPertenece", element: "index",
	                                   id: "cuentaIndividualAsegurado.registrosPatronalesNoPertenece[index]",
	                                   model: "cuentaIndividualAsegurado.registrosPatronalesNoPertenece[index]"
	                                 }
	                             }], layout: [[{span: 12}],[{span: 12}]]}
	                         }
	                       ]
	                     }
	                   
	                   
	                 ]                                                                                       
	  },

      {type: "ButtonGroupComponent", id: "cuentaIndividualConfirmarButtonGroup",
        components: [
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonPreviuos"),
	         label: "Regresar", icon: "", className: "btn-default",
	         trigger: {type: "CuentaIndividualConfirmarMainComponent", event: "previuos", key: this.component.id, model: ""}
	      },
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonNext"),
            label: "Siguiente", icon: "", className: "btn-primary",
            trigger: {type: "CuentaIndividualConfirmarMainComponent", event: "next", key: this.component.id, model: ""}
          }
          
        ]
      }
    ]
  };

  var html = "";
  var panel = new PanelComponent(this.render);
  html += panel.draw(metadata);
  this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  return html;
};


CuentaIndividualConfirmarMainComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#mainCuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};


CuentaIndividualConfirmarMainComponent.prototype.next = function () {
	$("#modal").modal('show');
    //Termina por crear los tramites de cuenta individual 
	//avanzar tarea 
	//por autorizar - Envía los cambios al autorizador.
	 this.render.module.service.confirmar();

	/*regresa a bandeja*/ 
	 this.render.module.controller.model.filtrosBusqueda = {};
	 this.render.module.validator.formToModel("FormPanelComponent-filter", this.render.module.controller.model.filtrosBusqueda);
//	 this.render.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	 this.render.module.controller.transition("bandejaSolicitudesUI");
	 
	 this.render.module.controller.model.paginaTramitesActualTramites = this.module.controller.model.gridTramites.currentPage;
	 this.render.module.controller.model.paginaTramitesActualHistorico = this.module.controller.model.gridHistorico.currentPage;
	
};


CuentaIndividualConfirmarMainComponent.prototype.previuos = function () {
	$("#modal").modal('show');
    this.render.module.service.fetchCuentaIndividual();
    $("#modal").modal('hide');
};

CuentaIndividualConfirmarMainComponent.prototype.save = function () {

  $.validator.addMethod("9Sep", function (value, element) {
    return  /09\/09\/2018/.test(value);
  });

  $.validator.addMethod("custom3", function (value, element) {

    return value === "";

  });


  var i, j, k;
  var periodo,forma;

  // Identificar el tab actual.
  var tabIndex = this.render.module.controller.model.tabs_cuentaIndividual;

  var modelNames = [
    "cuentaIndividualAsegurado.registrosPatronalesCertificador",
    "cuentaIndividualAsegurado.registrosPatronalesAsociados",
    "cuentaIndividualAsegurado.registrosPatronalesNoPertenece"
  ];

  // Obtener la lista de registros a validar ( Alguno de los tres anteriores )
  var model = this.render.getModel(modelNames[tabIndex]);

  // Revisar los grupos que estan en edicion
  var valid = true;
  var row;
  for (i = 0; i < model.length; i++) {
    if (model[i].state === "edit") {
      // Validar las formas
      for (j = 0; j < model[i].listaCuentaIndividual.length; j++) {
        // Periodos modificados
        for (k = 0; k < model[i].listaCuentaIndividual[j].periodos.length; k++) {
          forma = this.render.toId(modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodos[" + k + "]");
          row = this.render.getModel( modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodos[" + k + "]" );
            this.render.module.model.periodoCuentaIndividual.prepareValidator( row );
          valid = valid & this.render.module.validator.validForm({entity: "periodo", name: forma});
        }
        // Periodos nuevos
        
        if ( !this.render.isEmpty(model[i].listaCuentaIndividual[j].periodosNuevos ) ) {
          for (k = 0; k < model[i].listaCuentaIndividual[j].periodosNuevos.length; k++) {
            forma = this.render.toId(modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodosNuevos[" + k + "]");
            row = this.render.getModel( modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodosNuevos[" + k + "]" );
            this.render.module.model.periodoCuentaIndividual.prepareValidator( row );
            valid = valid & this.render.module.validator.validForm({entity: "periodo", name: forma});
          }
        }
      }
    }
  }

  if (!valid) {
    // Mostrar Modal
  } else {
    
    // Actualizar los modelos
    
    
    for (i = 0; i < model.length; i++) {
      if (model[i].state === "edit") {
        for (j = 0; j < model[i].listaCuentaIndividual.length; j++) {
          // Periodos modificados
          for (k = 0; k < model[i].listaCuentaIndividual[j].periodos.length; k++) {
            periodo = this.render.getModel( modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodos[" + k + "]" );
            forma = this.render.toId(modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodos[" + k + "]");
            this.render.module.validator.formToModel("FormPanelComponent-"+forma, periodo );
          }
          // Periodos nuevos
          if ( !this.render.isEmpty(model[i].listaCuentaIndividual[j].periodosNuevos ) ) {
            for (k = 0; k < model[i].listaCuentaIndividual[j].periodosNuevos.length; k++) {
              periodo = this.render.getModel( modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodosNuevos[" + k + "]" );
              forma = this.render.toId(modelNames[tabIndex] + "[" + i + "].listaCuentaIndividual[" + j + "].periodosNuevos[" + k + "]");
              this.render.module.validator.formToModel("FormPanelComponent-"+forma, periodo );
            }
          }
        }
      }
    }
    
    for (i = 0; i < model.length; i++) {
      var myparameters = { type: 'CuentaIndividualAseguradoComponent', event: 'nss', key: modelNames[tabIndex] + "[" + i + "]", model:"" };
      this.render.triggerEvent( window.btoa( JSON.stringify( myparameters )) );        
    $("#li-tabs-cuentaIndividual-0").removeClass('disabled');
    $("#li-tabs-cuentaIndividual-1").removeClass('disabled');
    $("#li-tabs-cuentaIndividual-2").removeClass('disabled');
    $('.nav-tabs a').click(function(){
      $(this).tab('show');
    });
    
    var disabled = [true, false];
    this.render.module.updateModel("ButtonGroupComponent", "cuentaIndividualConfirmarButtonGroup", disabled);
    
  }
  }

};


CuentaIndividualConfirmarMainComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "next":
      this.next();
      break;
    case "previuos":
      this.previuos();
      break;
  }
};


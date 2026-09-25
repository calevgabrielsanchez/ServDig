function EditCorreccionDatosComponent(render) {
  this.render = render;  
  this.BUSCAR_CI ="atencionResponsable/readMovimientosCuentaIndividualNss.do";
}
EditCorreccionDatosComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="editCorreccionDatos_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
EditCorreccionDatosComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var html = "";
  var currentNss = this.render.getModel("currentNss");
  if (currentNss === undefined) {
    currentNss = 0;
  }
  currentNss = parseInt(currentNss);

  if (model !== undefined) {
    var tipoNssDisabled = { certificador: true, asociado: true, corresOtraPersona:true, noExisteCanase:true};
    // console.log("ReadOnly " + this.component.readOnly );
    if( this.component.readOnly !== true ){
      tipoNssDisabled = this.render.module.model.correccionDatos.getDisabledTipoNss(model, currentNss);
    }
    // console.log("Disabled:" + JSON.stringify( tipoNssDisabled ) );
    
    var metadata = {
      type: "PanelComponent",
      components: [
        {type: "FormPanelComponent",
          name: this.component.model + ".listaNss[" + currentNss + "].tipoNss", model: this.component.model + ".listaNss[" + currentNss + "].tipoNss", entity: "tipoNss",
          label: " ",
          components: [
            {type: "RadioGroupFieldComponent",
              field: "tipo",
              elements: [
                {value: "certificador", label: "Certificador", disabled: tipoNssDisabled.certificador,
                  onChangeEvent: {
                    type: "EditCorreccionDatosComponent",
                    event: "changeCertificador",
                    key: this.component.id,
                    model: ""
                  }

                },
                {value: "asociado", label: "Asociado al Certificador", disabled: tipoNssDisabled.asociado,
                  onChangeEvent: {
                    type: "EditCorreccionDatosComponent",
                    event: "changeCertificador",
                    key: this.component.id,
                    model: ""
                  }

                },
                {value: "corresOtraPersona", label: "Corresponde a otra persona", disabled: ((module.controller.model.consultaSolicitud.estatus.indexOf("EN ESPERA DE AUTORIZACI"))===0?true:module.controller.model.consultaSolicitud.bloqueoCorrespondeOtroAsegurado?false:true),
                onChangeEvent: {
                    type: "EditCorreccionDatosComponent",
                    event: "changeCertificador",
                    key: this.component.id,
                    model: ""
                  }
                },
                {value: "noExisteCanase", label: "No existe en CANASE", disabled: tipoNssDisabled.noExisteCanase,
                onChangeEvent: {
                    type: "EditCorreccionDatosComponent",
                    event: "changeCertificador",
                    key: this.component.id,
                    model: ""
                  }
                }
              ]
            }
            
          ], layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]}
        ,
        {
          type: "EditTipoRegularizacionComponent", parentModel: this.component.model,
          readOnly: this.component.readOnly,
          id: this.component.model + ".listaNss[" + currentNss + "].tipoAclaracion",
          model: this.component.model + ".listaNss[" + currentNss + "].tipoAclaracion"
        }




      ],
      layout: [[{span: 12}], [{span: 12}]]
    };

    var panel = new PanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
  return html;
};

EditCorreccionDatosComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#editCorreccionDatos_" + this.render.toId(this.component.id)).html(html);
};


EditCorreccionDatosComponent.prototype.changeCertificador = function () {
  var index = this.render.module.controller.model.currentNss;
  if (index === undefined) {
    index = 0;
  }
  index = parseInt(index);

  var model = this.render.getModel(this.component.model);
  if (model.listaNss[index].tipoNss === undefined) {
    model.listaNss[index].tipoNss = {};
  }
  var row = model.listaNss[index].tipoNss;

  var forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoNss");
  this.render.module.validator.formToModel("FormPanelComponent-" + forma, row);

  
    model.listaNss[index].tipoNss.certificador = false;
    model.listaNss[index].tipoNss.asociado = false;
    model.listaNss[index].tipoNss.corresOtraPersona = false;
    model.listaNss[index].tipoNss.noExisteCanase = false;
  

  model.listaNss[index].tipoNss[row.tipo] = true;
  model.listaNss[index].tipoNss.tipo = row.tipo;
  this.render.module.updateModel("EditCorreccionDatosComponent", "editCorreccionDatos", row);

};

EditCorreccionDatosComponent.prototype.triggerEvent = function (event, parameters) {		
	  switch (event) {
	    case "changeCertificador":
		  this.changeCertificador();
	      this.buscarCI( this.render.module, null);		  
	      break;    
	  }	
};

var dialogoConfirmarMsgCI = $('<div class="modal fade" id="modalConfirmarSesion" tabindex="-1" role="dialog">  <div class="modal-dialog ">    <div class="modal-content">      <div class="modal-header">        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>        <h4 class="modal-title">Mensaje de sistema</h4>      </div><div class="modal-body"><div><div class=""><div class="row"><div class="col-md-12"><p class="container-fluid"><span>Existen movimientos de cuenta individual, si realiza algún cambio serán eliminados</span></p></div></div></div><div class=""><div class="row"><div class="col-md-12"><div class="pull-right"><button onclick="cancelarEliminarCI()" type="button" class="btn btn-default "> <span class="glyphicon glyphicon-undefined"> </span> Cancelar </button> <span> </span><button onclick="aceptarEliminarCI()" type="button" class="btn btn-primary "> <span class="glyphicon glyphicon-undefined"> </span> Aceptar </button><span> </span></div></div></div></div></div></div></div></div></div>');

EditCorreccionDatosComponent.prototype.buscarCI = function(module, componentId) {
	var url = this.BUSCAR_CI;
	var params = module.controller.model.consultaSolicitud.folio;	
	$("#modal").modal();
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			mode : "abort",
			port : "uniqueport",
			success : function(data) {								
				$("#modal").modal('hide');	
				if (data > 0) {
					$("#borradoCI").modal('show');					
				}
			},
			error : function(errMsg) {
				if (errMsg === null) {
					errMsg = "create.error";
				}
				$("#modal").modal('hide');
				this.module.updateModel("AlertComponent", "alert2", {
					level : "danger",
					message : errMsg
				});
			}
		});
	}
};

EditCorreccionDatosComponent.prototype.aceptarEliminarCI = function(){
	this.changeCertificador();
	dialogoConfirmarMsgCI.modal('hide');
}

EditCorreccionDatosComponent.prototype.cancelarEliminarCI = function(){
	dialogoConfirmarMsgCI.modal('hide');
}

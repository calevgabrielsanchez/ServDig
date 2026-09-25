function CorreccionDatosComponent(render) {
  this.render = render;
}

CorreccionDatosComponent.prototype.draw = function (component) {
  this.component = component;

  //Configuracion de los botones iniciales
  var disabled = [false, false, false, true];
  if (this.component.readOnly !== true) {
    disabled = [true, false, false, false];
  }

  this.render.module.controller.model.correccionDatosButtonGroup = disabled;
  //Tab inicial
  this.render.module.controller.model.tabs_correccionDatos = 0;

  var html = '<div id="mainCorreccionDatos_' + this.component.id + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};

CorreccionDatosComponent.prototype.drawBody = function () {
                
  var html = "";
  var model = this.render.getModel(this.component.model);
  
  var currentNss = this.render.getModel("currentNss");
  if (currentNss === undefined) {
    currentNss = 0;

    currentNss = parseInt(currentNss);
  }
  if (model !== undefined) {

    // Aplicar reglas de negocio
    //this.render.module.model.correccionDatos.RNFUE16( currentNss );
                  
    // console.log("noExisteInformacion");
                  var noExisteInformacion = this.render.module.model.correccionDatos.noExisteInformacion(model,currentNss);
                                                                  var tipoMensajeNSS = this.render.module.model.correccionDatos.mostrasMensaje(model,currentNss);
                  
                  
    if( this.component.readOnly !== true ){
      // console.log("RNFUE17");
      this.render.module.model.correccionDatos.RNFUE17(model);
      // console.log("RNFUE15");
      this.render.module.model.correccionDatos.RNFUE15(model,currentNss);
      // console.log("RNFUE16");
      this.render.module.model.correccionDatos.RNFUE16(model,currentNss);
                    // console.log("RN028");
      this.render.module.model.correccionDatos.RN028(model,currentNss);
      // console.log("noExisteCanase");
      this.render.module.model.correccionDatos.noExisteCanase(model,currentNss);       
                  
                  

                  
                  
    }
  



    var metadata = {type: "PanelComponent", label: "Correcci\u00f3n de datos",
      layout:
              [
[{span:12}],
[{span: 4}, {span: 5}, {span: 3}],
                [{span: 3}, {span: 9}],
                [{span: 12}]
              ],
      components: [
                                {type:"AlertComponent", id:"alert2"},
        {type: "TabPanelComponent", id: "renapoTab",
          tabs: [
            {title: "RENAPO", panel: {type: "PanelComponent", components: [{type: "FormaCorreccionDatosComponent", fullDate: true,  id: "renapo", model: this.component.model + ".renapo", source: this.component.model + ".renapo", label: "Informaci\u00f3n en RENAPO"}], layout: [[{span: 12}]]}}
          ]
        },
        {type: "TabPanelComponent", id: "correccionDatos",
          tabs: [
            {title: "CANASE", panel: {type: "PanelComponent", components: [{type: "FormaCorreccionDatosComponent", id: "canase", source: this.component.model + ".renapo", model: this.component.model + ".listaNss[" + currentNss + "].canase", label: noExisteInformacion.canase ? "No existe informaci\u00f3n":"Informaci\u00f3n en el IMSS - CANASE"}], layout: [[{span: 12}]]}},
           {title: "CIZ1", panel: {type: "PanelComponent", components: [{type: "FormaCorreccionDatosComponent", id: "cizUno", source: this.component.model + ".renapo", model: this.component.model + ".listaNss[" + currentNss + "].cizUno", label: noExisteInformacion.cizUno ? "No existe informaci\u00f3n": "Informaci\u00f3n en el IMSS - CIZ1"}], layout: [[{span: 12}]]}},
            {title: "CIZ2", panel: {type: "PanelComponent", components: [{type: "FormaCorreccionDatosComponent", id: "cizDos", source: this.component.model + ".renapo", model: this.component.model + ".listaNss[" + currentNss + "].cizDos", label: noExisteInformacion.cizDos ? "No existe informaci\u00f3n": "Informaci\u00f3n en el IMSS - CIZ2"}], layout: [[{span: 12}]]}},
            {title: "CIZ3", panel: {type: "PanelComponent", components: [{type: "FormaCorreccionDatosComponent", id: "cizTres", source: this.component.model + ".renapo", model: this.component.model + ".listaNss[" + currentNss + "].cizTres", label: noExisteInformacion.cizTres ? "No existe informaci\u00f3n": "Informaci\u00f3n en el IMSS - CIZ3"}], layout: [[{span: 12}]]}},
            {title: "HIST\u00d3RICO", panel: {type: "PanelComponent", components: [{type: "FormaCorreccionDatosComponent", source: this.component.model + ".renapo", id: "historico", model: this.component.model + ".listaNss[" + currentNss + "].historico", label: noExisteInformacion.historico ? "No existe informaci\u00f3n":  "Informaci\u00f3n en el IMSS - Hist\u00f3rico"}], layout: [[{span: 12}]]}},
            {title: "BDTU", panel: {type: "PanelComponent", components: [{type: "FormaCorreccionDatosComponent", fullDate: true, id: "bdtu", source: this.component.model + ".renapo", model: this.component.model + ".listaNss[" + currentNss + "].bdtu", label:  noExisteInformacion.bdtu ? "No existe informaci\u00f3n": "Informaci\u00f3n en el IMSS - BDTU"}], layout: [[{span: 12}]]}}
          ]
        },
        {type: "PanelComponent",
          components: [
            {type: "LabelComponent", label: "N&uacutemero de Seguridad Social registrado en solicitud", className: "text-center"},
            {type: "LabelComponent", className: "text-center", tag: "h3", label: this.render.getModel(this.component.model + ".listaNss[" + currentNss + "].nss")},
            {type: "LabelComponent", tag: "h3", label: tipoMensajeNSS, className: "text-center"},
            {type: "EditCorreccionDatosComponent", readOnly: this.component.readOnly, id: "editCorreccionDatos", model: this.component.model}],
          layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]},
        {type: "LabelComponent", label: ""},
        {type: "NssPagerComponent", componentName:"CorreccionDatosComponent",  parentId: this.component.id, id: "currentNss", numberOfElements: 5, model: this.component.model + ".listaNss"},
        {type: "ButtonGroupComponent", id: "correccionDatosButtonGroup",
          components: [
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonExit"),
              label: "Salir", icon: "", className: "btn-danger",
              trigger: {type: "CorreccionDatosComponent", event: "exit", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonNext"),
              label: "Siguiente", icon: "", className: "btn-primary",
              trigger: {type: "CorreccionDatosComponent", event: "save", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonBack"),
              label: "Regresar", icon: "", className: "btn-default",
              trigger: {type: "CorreccionDatosComponent", event: "back", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonTray"),
              label: "Bandeja de Solicitudes", icon: "", className: "btn-default",
              trigger: {type: "CorreccionDatosComponent", event: "tray", key: this.component.id, model: ""}
            }
          ]
        }

      ]
    };
    var panel = new PanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
                this.render
                
  }

  return html;
};


CorreccionDatosComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();  
  $("#mainCorreccionDatos_" + this.component.id ).html(html);
};

CorreccionDatosComponent.prototype.page = function (page) {
  var index = this.render.module.controller.model.currentNss;
  if (index === undefined) {
    index = 0;
  }
  index = parseInt(index);
  this.persist(index);
  this.render.module.controller.model.currentNss = parseInt(page);
  this.render.notify("CorreccionDatosComponent", this.component.id);
};

CorreccionDatosComponent.prototype.persist = function (index) {

  if( this.component.readOnly !== true ){

    var model = this.render.getModel(this.component.model);
    if (model.listaNss[index].tipoNss === undefined) {
      model.listaNss[index].tipoNss = {};
    }
    if (model.listaNss[index].tipoAclaracion === undefined) {
      model.listaNss[index].tipoAclaracion = {};
    }
    var row = model.listaNss[index].tipoNss;
    row.certificador=false; 
    row.asociado=false; 
    row.corresOtraPersona=false; 
    row.noExisteCanase=false;
    var forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoNss");
    this.render.module.validator.formToModel("FormPanelComponent-" + forma, row);
    model.listaNss[index].tipoNss = row;
    model.listaNss[index].tipoNss[row.tipo] = "true";

    var tipoAclaracion = {};
    forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoAclaracion");
    this.render.module.validator.formToModel("FormPanelComponent-" + forma, tipoAclaracion);
    model.listaNss[index].tipoAclaracion = tipoAclaracion;
                
  }

};


CorreccionDatosComponent.prototype.save = function (page) {
  
  // console.log("save");

  if (this.component.readOnly === true) {
    this.render.module.controller.next(this.component.screen);
  } else {

    var i;
    var model = this.render.getModel(this.component.model);
    
    

    var index = this.render.module.controller.model.currentNss;
    
    if (index === undefined) {
      index = 0;
    }
    index = parseInt(index);
    //this.persist(index);
    //var model = this.render.getModel(this.component.model);
    
    // console.log("RNFUE19");

    if( !this.render.module.model.correccionDatos.RNFUE19(model, index) ){
      return;
    }
    // console.log("existCertificador");
                  if( !this.render.module.model.correccionDatos.existCertificador(model, index) ){
      return;
    }
    
    this.render.module.model.correccionDatos.prepareValidator(model.listaNss[index].tipoNss);

    var forma = this.render.toId(this.component.model + ".listaNss[" + index + "].tipoNss");
    //var valid = this.render.module.validator.validForm({entity: "tipoNss", name: forma});
    
    // Validar que todos los NSS tengan un tipoNss seleccionado y al menos un tipo de aclaracion
    
    var valid = true;
                var noCanase=0;
    for( i=0; i<model.listaNss.length; i++){
      var row = model.listaNss[i];
                  var contadorCertificador =0;
      
                  
      // console.log("RNFUE19-All");
      if( !this.render.module.model.correccionDatos.RNFUE19( model, i) ){
        return;
      }
      
      // console.log(JSON.stringify(row));
      if( this.render.isEmpty(row.tipoNss.tipo) ){
        // Tipo invalido, no seguir validando
        valid = false;
        break;
      }
      // console.log("TipoNSS valido" + i);
                  if(model.listaNss.length === 1){
                   if (model.listaNss[i].canase.apellidoMaterno === null &&
            model.listaNss[i].canase.apellidoPaterno === null &&
            model.listaNss[i].canase.curp === null &&
            model.listaNss[i].canase.curpsHistoricas === null &&
            model.listaNss[i].canase.datosDocumentoProbatorio === null &&
            model.listaNss[i].canase.fechaNacimiento === null &&
            model.listaNss[i].canase.lugarNacimiento === null &&
            model.listaNss[i].canase.nacionalidad === null &&
            model.listaNss[i].canase.nombre === null &&
            model.listaNss[i].canase.sexo === null) {
                                                $("#noCANSE").modal('show');  
                                                break;
                  }}
                  
                  
      // Validar tipo de aclaracion
     
                  if((model.listaNss[i].tipoNss.certificador=== true || model.listaNss[i].tipoNss.certificador=== 'true') && model.listaNss.length > 1)
                  {
                                  if( row.tipoAclaracion.canceladoDup      === "false" &&
          row.tipoAclaracion.correccionEstadis === "false" &&
          row.tipoAclaracion.correccionNombre  === "false" &&
          row.tipoAclaracion.homonimio         === "false" &&
          row.tipoAclaracion.noExisteCanase    === "false" &&
          row.tipoAclaracion.otroAsegurado     === "false" &&
          row.tipoAclaracion.cuentaIlogica     === "false" &&
                                  row.tipoAclaracion.cuentaIndividual  === "false" ){
          valid = true;
                                  noCanase = noCanase + 1;
          //break;
      }
                  }
                  else if ((model.listaNss[i].tipoNss.noExisteCanase=== true || model.listaNss[i].tipoNss.noExisteCanase=== 'true') && model.listaNss.length > 1)
                  {
                                  if( (row.tipoAclaracion.canceladoDup      === "false" || row.tipoAclaracion.canceladoDup      === false  )&&
          (row.tipoAclaracion.correccionEstadis === "false" || row.tipoAclaracion.correccionEstadis === false )&&
          (row.tipoAclaracion.correccionNombre  === "false" || row.tipoAclaracion.correccionNombre  === false )&&
          (row.tipoAclaracion.homonimio         === "false" || row.tipoAclaracion.homonimio         === false )&&
          (row.tipoAclaracion.noExisteCanase   === "true"   || row.tipoAclaracion.noExisteCanase === true )&&
          (row.tipoAclaracion.otroAsegurado     === "false" || row.tipoAclaracion.otroAsegurado  === false ) &&
          (row.tipoAclaracion.cuentaIlogica     === "false" || row.tipoAclaracion.cuentaIlogica  === false ) 
                                 ){
                                                  noCanase = noCanase + 1;
                                                  if(noCanase === model.listaNss.length)
                                                  {
                                                                  valid = false;
                                                  }         
          //break;
      }
                                  
                  }
                  else{
                                  
                                  if( (row.tipoAclaracion.canceladoDup      === "false" || row.tipoAclaracion.canceladoDup === false) &&
          (row.tipoAclaracion.correccionEstadis === "false" || row.tipoAclaracion.correccionEstadis === false) &&
          (row.tipoAclaracion.correccionNombre  === "false" || row.tipoAclaracion.correccionNombre  === false)&&
          (row.tipoAclaracion.homonimio         === "false" || row.tipoAclaracion.homonimio === false) &&
          (row.tipoAclaracion.noExisteCanase    === "false" ||  row.tipoAclaracion.noExisteCanase === false )&&
          (row.tipoAclaracion.otroAsegurado     === "false" || row.tipoAclaracion.otroAsegurado === false)&&
          (row.tipoAclaracion.cuentaIlogica     === "false" || row.tipoAclaracion.cuentaIlogica === false) &&
                                  (row.tipoAclaracion.cuentaIndividual  === "false" || row.tipoAclaracion.cuentaIndividual  === false )){
          valid = false;
          //break;
                  }
                  }
                  
      // console.log("TipoAsignacion valido" + i);
    }
                var certificadorCURP="";
                var otroAseguradoCURP="";
                for( i=0; i<model.listaNss.length; i++){
                    if(model.listaNss[i].tipoNss.asociado === true || model.listaNss[i].tipoNss.asociado === 'true' )
                      {
                          this.render.module.controller.model.correccionDatos.listaNss[i].tipoAclaracion.canceladoDup = true;
                      }                         
                }
                
                
    
    // console.log("Resultado de validacion:" + valid);
    if( valid === true ){      
                var contador = 0;              
                for( i=0; i<model.listaNss.length; i++){
                                  
                                var row = model.listaNss[i];
                                var correccionNombre;
                                var correccionEstadisticos;
                                if(((row.tipoAclaracion.correccionEstadis === "true" || row.tipoAclaracion.correccionEstadis === true) ||
									(row.tipoAclaracion.correccionNombre  === "true" || row.tipoAclaracion.correccionNombre  === true) ||
									(row.tipoNss.noExisteCanase  === "true" || row.tipoNss.noExisteCanase  === true) ||
                                    (row.tipoAclaracion.canceladoDup  === "true" || row.tipoAclaracion.canceladoDup  === true))&&(
                                    (row.tipoAclaracion.cuentaIlogica  === "false" || row.tipoAclaracion.cuentaIlogica  === false) ||
                                    (row.tipoAclaracion.cuentaIndividual  === "false" || row.tipoAclaracion.cuentaIndividual  === false))){
                                                                contador = contador + 1;
                                                                
                                                                if(contador === model.listaNss.length)
                                                                {
																	var cuenta = 0;
																	for(h=0;h<model.listaNss.length; h++)
																	{
																		
																		var row1 = model.listaNss[h];
																		if((row1.tipoAclaracion.cuentaIlogica  === "true" || row.tipoAclaracion.cuentaIlogica  === true)||
																		(row.tipoAclaracion.cuentaIndividual  === "true" || row.tipoAclaracion.cuentaIndividual  === true))
																		{
																				cuenta = cuenta + 1;
																		}																																	
																	}
																	if(cuenta === 0)
																		{
																			  this.render.module.service.correccionDatosService.save( this.render.module ); 
                                                                                //this.render.module.controller.next(this.component.screen);                                                                                
                                                                                                $("#modalMov5").modal('show');  
																		}
																		else{
																			 this.render.module.service.correccionDatosService.save( this.render.module ); 
																	}
                                                                         
                                                                }
																
                                                                
                                                }
                                                else {
                                                                //this.render.module.model.correccionDatos.RNFUE19( model, i)
                                                                correccionNombre = this.render.module.model.correccionDatos.correccionNombre( model, i);
                                                                correccionEstadisticos = this.render.module.model.correccionDatos.correccionEstadistico( model, i);
                                                                
                                                                contador = contador + 1;
                                                                if(contador === model.listaNss.length){
                                                                                this.render.module.service.correccionDatosService.save( this.render.module ); 
                                                                }
                                                                if(((correccionNombre === true || correccionNombre === 'true' )&& 
                                                                (model.listaNss[i].tipoAclaracion.correccionNombre=== false || model.listaNss[i].tipoAclaracion.correccionNombre=== 'false')&&
                                                                ((model.listaNss[i].tipoNss.asociado === true || model.listaNss[i].tipoNss.asociado === 'true') || (model.listaNss[i].tipoNss.certificador === true ||  model.listaNss[i].tipoNss.certificador === 'true' )))
                                                                ||((correccionEstadisticos === true || correccionEstadisticos === 'true' )&& 
                                                                (model.listaNss[i].tipoAclaracion.correccionEstadis=== false || model.listaNss[i].tipoAclaracion.correccionEstadis=== 'false')&&
                                                                ((model.listaNss[i].tipoNss.asociado === true || model.listaNss[i].tipoNss.asociado === 'true') || (model.listaNss[i].tipoNss.certificador === true ||  model.listaNss[i].tipoNss.certificador === 'true' )))
                                                                )                                                              
                                                                                {
                                                                                                $("#modalCorreccionNo").modal('show');  
                                                                                }
                                                //this.render.module.controller.next(this.component.screen);
                                                }
                                
                 }
                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '';
                    var canceladoDup = false;
                                  var correccionEstadis = false;
                                  var correccionNombre = false;
                                  var homonimio = false;
                                  var noExisteCanase = false;
                                  var otroAsegurado = false;
                                  var blanqueoCurp = false;
                                  var cuentaIlogica = false;
                  for( i=0; i<model.listaNss.length; i++)
                  {
                                
                                  
                                  if(( model.listaNss[i].tipoAclaracion.canceladoDup === "true" ||  model.listaNss[i].tipoAclaracion.canceladoDup === true) &&!canceladoDup){
                                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[2] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                  canceladoDup = true;
                                  }
                                    if(( model.listaNss[i].tipoAclaracion.correccionEstadis === "true" ||  model.listaNss[i].tipoAclaracion.correccionEstadis === true) &&!correccionEstadis){
                                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[1] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                  correccionEstadis = true;
                                  }
                                    if(( model.listaNss[i].tipoAclaracion.correccionNombre === "true" ||  model.listaNss[i].tipoAclaracion.correccionNombre === true) &&!correccionNombre){
                                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[0] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                  correccionNombre = true;
                                  }
                                    if(( model.listaNss[i].tipoAclaracion.homonimio === "true" ||  model.listaNss[i].tipoAclaracion.homonimio === true) &&!homonimio){
                                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[3] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                  homonimio = true;
                                  }
                                  if(( model.listaNss[i].tipoNss.noExisteCanase === "true" ||  model.listaNss[i].tipoNss.noExisteCanase === true) &&!noExisteCanase){
                                                this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[5] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                                noExisteCanase = true;
                                  }
//                                if(( model.listaNss[i].tipoAclaracion.noExisteCanase === "true" ||  model.listaNss[i].tipoAclaracion.noExisteCanase === true) &&!noExisteCanase){
//                              this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[5] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
//                              noExisteCanase = true;
//                              }
                                    if(( model.listaNss[i].tipoAclaracion.otroAsegurado === "true" ||  model.listaNss[i].tipoAclaracion.otroAsegurado === true) &&!otroAsegurado){
                                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+  definicion[4] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                  otroAsegurado = true;
                                  }
                                    if(( model.listaNss[i].tipoAclaracion.blanqueoCurp === "true" ||  model.listaNss[i].tipoAclaracion.blanqueoCurp === true) &&!blanqueoCurp){
                                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[6] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                  blanqueoCurp = true;
                                  }
                                    if(( model.listaNss[i].tipoAclaracion.cuentaIlogica === "true" ||  model.listaNss[i].tipoAclaracion.cuentaIlogica === true) &&!cuentaIlogica){
                                  this.render.module.controller.model.consultaSolicitud.tipoTramite = '<br>'+ definicion[7] + ' ' + this.render.module.controller.model.consultaSolicitud.tipoTramite;
                                  cuentaIndividual = true;
                                  }
                                  
                                
                  }
                
    }else{
      $("#modalCorreccionDatos").modal('show');    
    }    
  }
};




CorreccionDatosComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "exit":
      document.location.reload();
      break;
    case "tray":
      document.location.reload();
      break;
    case "page":
      this.page(parameters);
      break;
    case "save":
      this.save();
      break;
    case "back":
      this.render.module.controller.back(this.component.screen);
      break;
  }
};

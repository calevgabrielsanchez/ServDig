function CuentaIndividualConsultaComponent(render) {
  this.render = render;
}
CuentaIndividualConsultaComponent.prototype.draw = function (component) {
  this.component = component;
  
  //Tab inicial
  this.render.module.controller.model.tabs_cuentaIndividual = 0;
  
  //Configuracion de los botones iniciales
  var disabled = [false,false,false, true,true,true];
  
  if( this.component.autorizador === true){
    disabled = [true, false, true ,false,false,false];
  }
  if( this.component.autorizador === false){
      disabled = [false, false, true, true,true,true];
    }
  this.render.module.controller.model.cuentaIndividualConsultaButtonGroup = disabled;
  
  

  var html = '<div id="mainCuentaIndividual_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
CuentaIndividualConsultaComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var html = "";
  var i;
  var htmlAux = "";
  var mensajeSinPeriodos = "";
  if (model !== undefined) {
    this.prepareModel();
    
    if( model.listaTipoTramites !== undefined ){
      htmlAux += "<ul>";
      for(i=0; i<model.listaTipoTramites.length;i++){
        htmlAux +="<li>"+model.listaTipoTramites[i]+"</li>";        
      }
      htmlAux += "</ul>";
    }
    if( model.tipoTramite !== undefined ){
      htmlAux += model.tipoTramite;
    }

    if (!this.render.isEmpty(model.listaCuentaIndividualNssCertificador) && !this.render.isEmpty(model.listaCuentaIndividualNssNoPertenece)) {
      if (model.listaCuentaIndividualNssCertificador[0] !== undefined && model.listaCuentaIndividualNssNoPertenece[0] !== undefined) {
        if (model.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal.length === 0 && model.listaCuentaIndividualNssNoPertenece[0].listaPeriodosRegistroPatronal.length === 0) {
          mensajeSinPeriodos = "Para esta solicitud no se ha seleccionado ning\u00FAn movimiento afiliatorio para que se mueva de un NSS a otro, por lo cual no se impactar\u00E1 ning\u00FAn periodo en SINDO, si deseas mover alg\u00FAn periodo puedes regresar antes de solicitar la autorizaci\u00F3n o rechazar la solicitud si est\u00E1 ya se encuentra en espera de ser autorizada";
        }
      }
    } else if (!this.render.isEmpty(model.listaCuentaIndividualNssCertificador) && !this.render.isEmpty(model.listaCuentaIndividualNssAsociado)) {
      if (model.listaCuentaIndividualNssCertificador[0] !== undefined && model.listaCuentaIndividualNssAsociado[0] !== undefined) {
        if (model.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal.length === 0 && model.listaCuentaIndividualNssAsociado[0].listaPeriodosRegistroPatronal.length === 0) {
          mensajeSinPeriodos = "Para esta solicitud no se ha seleccionado ning\u00FAn movimiento afiliatorio para que se mueva de un NSS a otro, por lo cual no se impactar\u00E1 ning\u00FAn periodo en SINDO, si deseas mover alg\u00FAn periodo puedes regresar antes de solicitar la autorizaci\u00F3n o rechazar la solicitud si est\u00E1 ya se encuentra en espera de ser autorizada";
        }
      }
    }
    this.render.module.controller.model["alert_sinperiodos"] = { level:"warning", message:mensajeSinPeriodos};
    var metadata = {type: "PanelComponent", id:"panelConsultaCuentaIndividual",  layout: [[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}], [{span: 12}]],
      components: [
        { type:"PanelComponent", label:"Informaci\u00f3n de la solicitud", layout:[[{span:6},{span:6}],[{span:6},{span:6}]], 
          components:[
            { type:"LabelComponent", className:"label-cda", label:"Folio"}, { type:"LabelComponent", className:"label-cda", label:"Tipo de tr\u00e1mite de la solicitud"},
            { type:"LabelComponent", label: model.folioSolicitud }, { type:"HTMLComponent", html: htmlAux }
          ]},
        
        { type:"LabelComponent", tag:"h3", label:"Confirmar cambios de Cuenta Individual"},
        { type:"AlertComponent", id:"alert_sinperiodos"},
        {type: "TabPanelComponent", id: "cuentaIndividual",
          tabs: [
            {title: "CERTIFICADOR",
              panel: {type: "PanelComponent", id: "tab-certificadorPagerConsulta" , className:"scrolled",
                components: [
                  {
                    type: "CuentaIndividualConsultaAseguradoComponent", indexName: "certificadorPagerConsulta",
                    parentId: this.component.model,
                    id: this.component.model + ".listaCuentaIndividualNssCertificador",
                    model: this.component.model + ".listaCuentaIndividualNssCertificador"
                  },
                  {type: "NssPagerComponent", componentName: "CuentaIndividualConsultaAseguradoComponent",
                    parentId: this.component.model + ".listaCuentaIndividualNssCertificador",
                    id: "certificadorPagerConsulta", model: this.component.model + ".listaCuentaIndividualNssCertificador", numberOfElements: 5}
                ], layout: [[{span: 12}], [{span: 12}]]}},
            {title: "ASOCIADO AL ASEGURADO",
              panel: {type: "PanelComponent", id: "tab-asociadoPagerConsulta", className:"scrolled",
                components: [
                  {
                    type: "CuentaIndividualConsultaAseguradoComponent", indexName: "asociadoPagerConsulta",
                    parentId: this.component.model,
                    id: this.component.model + ".listaCuentaIndividualNssAsociado",
                    model: this.component.model + ".listaCuentaIndividualNssAsociado"
                  },
                  {type: "NssPagerComponent", componentName: "CuentaIndividualConsultaAseguradoComponent",
                    parentId: this.component.model + ".listaCuentaIndividualNssAsociado",
                    id: "asociadoPagerConsulta", model: this.component.model + ".listaCuentaIndividualNssAsociado", numberOfElements: 5}
                ], layout: [[{span: 12}], [{span: 12}]]}},
            {title: "NO CORRESPONDEN AL ASEGURADO", panel: {type: "PanelComponent", id: "tab-noPertenecePagerConsulta", className:"scrolled",
                components: [
                  {
                      type: "CuentaIndividualConsultaAseguradoComponent", indexName: "noPertenecePagerConsulta",
                      parentId: this.component.model,
                      id: this.component.model + ".listaCuentaIndividualNssNoPertenece",
                      model: this.component.model + ".listaCuentaIndividualNssNoPertenece"
                    },
                    {type: "NssPagerComponent", componentName: "CuentaIndividualConsultaAseguradoComponent",
                    parentId: this.component.model + ".listaCuentaIndividualNssNoPertenece",
                    id: "noPertenecePagerConsulta", model: this.component.model + ".listaCuentaIndividualNssNoPertenece", numberOfElements: 5}
                ], layout: [[{span: 12}], [{span: 12}]]}}
          ]
        },
        {type: "ButtonGroupComponent", id: "cuentaIndividualConsultaButtonGroup",
          components: [
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonBack"),
              label: "Regresar", icon: "", className: "btn-default",
              trigger: {type: "CuentaIndividualConsultaComponent", event: "back", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".informacion"),
              label: "Informaci\u00f3n de la solicitud", icon: "", className: "btn-default",
              command: "transition", argument:"'informacionSolicitud'"
            },
            {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonNext"),
              label: "Siguiente", icon: "", className: "btn-primary",
              trigger: {type: "CuentaIndividualConsultaComponent", event: "next", key: this.component.id, model: ""}
            },
            {type: "ButtonComponent", 
                label: "Regresar", icon: "", className: "btn-primary", command:"regresarInicioCorreccion"
                
            },  
            {type: "ButtonComponent", 
              label: "Autorizar", icon: "", className: "btn-primary", command:"autorizarSindo"
              
            },            
            {type: "ButtonComponent", 
              label: "Rechazar", icon: "", className: "btn-danger", command:"rechazarController.mostrarRechazar"
              
            }
          ]
        }

      ]
    };


    var panel = new PanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
  return html;
};


CuentaIndividualConsultaComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;  
  var html = this.drawBody();
  $("#mainCuentaIndividual_" + this.render.toId(this.component.id)).html(html);
};


CuentaIndividualConsultaComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "back":
      this.render.module.controller.back( this.component.screen );
      break;
    case "next":
      this.render.module.controller.next( this.component.screen );
      break;
  }
};

/* 
 * Los periodos incluidos seran intercambiados por los periodos originales
 * para mantener la funcionalidad del marcado al detectar el cambio en NSS  
 */
CuentaIndividualConsultaComponent.prototype.prepareModel = function () {
  var i;
  var model = this.render.getModel( this.component.model );
  
  if( this.render.isEmpty(model) ){
    return;
  }
  
  if( !this.render.isEmpty(model.listaCuentaIndividualNssCertificador) ){
    for(i=0;i<model.listaCuentaIndividualNssCertificador.length;i++){
      this.prepareCuentaIndividualNss( model.listaCuentaIndividualNssCertificador[i] );      
    }
  }
  if( !this.render.isEmpty(model.listaCuentaIndividualNssAsociado) ){
    for(i=0;i<model.listaCuentaIndividualNssAsociado.length;i++){
      this.prepareCuentaIndividualNss( model.listaCuentaIndividualNssAsociado[i] );      
    }
  }
  if( !this.render.isEmpty(model.listaCuentaIndividualNssNoPertenece) ){
    for(i=0;i<model.listaCuentaIndividualNssNoPertenece.length;i++){
      this.prepareCuentaIndividualNss( model.listaCuentaIndividualNssNoPertenece[i] );      
    }
  }
};

CuentaIndividualConsultaComponent.prototype.prepareCuentaIndividualNss = function ( cuentaIndividualNss ) {
  var j,k,l;
  if( !this.render.isEmpty( cuentaIndividualNss.listaPeriodosRegistroPatronal ) ){        
    for(j=0; j<cuentaIndividualNss.listaPeriodosRegistroPatronal.length; j++){
      
      var periodosRegistroPatronal = cuentaIndividualNss.listaPeriodosRegistroPatronal[j];
      
      var movimientos = 0;
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosIncluidos ) ){
        movimientos += periodosRegistroPatronal.periodosIncluidos.length;
      }
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosEliminados ) ){
        movimientos += periodosRegistroPatronal.periodosEliminados.length;
      }
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosModificados ) ){
        movimientos += periodosRegistroPatronal.periodosModificados.length;
      }
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosNuevos ) ){
        movimientos += periodosRegistroPatronal.periodosNuevos.length;
      }
      
      if( movimientos === 0 ){
        cuentaIndividualNss.listaPeriodosRegistroPatronal.splice(j,1);
        j-=1;
        continue;
      }
      
      
      var periodos = periodosRegistroPatronal.periodos;
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosIncluidos ) ){
        for(k=0; k<periodosRegistroPatronal.periodosIncluidos.length;k++ ){              
          var periodoIncluido = periodosRegistroPatronal.periodosIncluidos[k];
          for(l=0; l<periodos.length; l++){
            if(periodoIncluido.cveIdPeriodoAnterior === periodos[l].cveIdPeriodoCuentaIndividual
               &&  periodoIncluido.nss !== periodos[l].nss
               ){
              periodoIncluido.cveIdPeriodoAnterior = periodos[l].cveIdPeriodoCuentaIndividual;
              periodoIncluido.cveIdPeriodoCuentaIndividual = periodos[l].cveIdPeriodoCuentaIndividual;
              periodoIncluido.state="nss";
              periodoIncluido.nss = ""+periodoIncluido.nss;
              console.log(  JSON.stringify(periodoIncluido) );
              console.log( periodoIncluido.nss );
              periodoIncluido.tipoRegularizacionPeriodo = "I";
              periodoIncluido.classNames = this.getClassName( periodoIncluido, periodos[l]);
            }
          }
        }
      }
      
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosEliminados ) ){
        for(k=0; k<periodosRegistroPatronal.periodosEliminados.length;k++ ){              
          var periodoEiminado = periodosRegistroPatronal.periodosEliminados[k];
          for(l=0; l<periodos.length; l++){
            if(periodoEiminado.cveIdPeriodoAnterior === periodos[l].cveIdPeriodoCuentaIndividual){
              periodoEiminado.cveIdPeriodoCuentaIndividual = periodos[l].cveIdPeriodoCuentaIndividual;
              periodoEiminado.state="delete";
              periodoEiminado.tipoRegularizacionPeriodo = "E";
              periodoEiminado.classNames = { tipoRegularizacionPeriodo :"bg-danger label-cda" };
            }
          }
        }
      }
      
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosModificados ) ){
        for(k=0; k<periodosRegistroPatronal.periodosModificados.length;k++ ){              
          var periodoModificado = periodosRegistroPatronal.periodosModificados[k];
          for(l=0; l<periodos.length; l++){
            if(periodoModificado.cveIdPeriodoAnterior === periodos[l].cveIdPeriodoCuentaIndividual){
              periodoModificado.cveIdPeriodoCuentaIndividual = periodos[l].cveIdPeriodoCuentaIndividual;
              periodoModificado.state="edit";
              periodoModificado.tipoRegularizacionPeriodo = "M";
              periodoModificado.classNames = this.getClassName( periodoModificado, periodos[l]);
              
            }
          }
        }
      }
      
      // Falta hacer merge entre Incluidos y Modificados para que no se repitan
      periodosRegistroPatronal.periodos = [];
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosIncluidos ) ){
        for(k=0; k<periodosRegistroPatronal.periodosIncluidos.length;k++ ){
          periodosRegistroPatronal.periodos[periodosRegistroPatronal.periodos.length] = periodosRegistroPatronal.periodosIncluidos[k];
        }
      }
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosEliminados ) ){
        for(k=0; k<periodosRegistroPatronal.periodosEliminados.length;k++ ){
          periodosRegistroPatronal.periodos[periodosRegistroPatronal.periodos.length] = periodosRegistroPatronal.periodosEliminados[k];
        }
      }
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosModificados ) ){
        for(k=0; k<periodosRegistroPatronal.periodosModificados.length;k++ ){
          periodosRegistroPatronal.periodos[periodosRegistroPatronal.periodos.length] = periodosRegistroPatronal.periodosModificados[k];
        }
      }
      
      
      
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosNuevos ) ){
        for(k=0; k<periodosRegistroPatronal.periodosNuevos.length;k++ ){              
          var periodoNuevo = periodosRegistroPatronal.periodosNuevos[k];
          periodoNuevo.state="add";
          periodoNuevo.tipoRegularizacionPeriodo = "A";
          periodoNuevo.classNames = { tipoRegularizacionPeriodo :"bg-success label-cda" };
        }
      }
      
    }
  }
};

CuentaIndividualConsultaComponent.prototype.getClassName = function (periodoA, periodoB) {
  var classNames = {};
  $.each( periodoB,function( key,value){
    classNames[key] = periodoA[key] === value ? "" :"bg-warning label-cda";
  });
  return classNames;
};

function ConfrontaCuentaIndividualComponent(render) {
  this.render = render;
}
ConfrontaCuentaIndividualComponent.prototype.draw = function (component) {
  this.component = component;

  //Tab inicial
  this.render.module.controller.model.tabs_cuentaIndividual = 0;

  var html = '<div id="mainCuentaIndividual_' + this.component.id + '" >';
  if( !this.render.isEmpty( this.render.getModel( this.component.model ) ) ){
    html += this.drawBody();
  }
  html += '</div>';
  return html;
};
ConfrontaCuentaIndividualComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var html = "";
  var i;
  var htmlAux = "";
  
  if (model !== undefined) {
  
    if( model.listaTipoTramites !== undefined ){
      htmlAux += "<ul>";
      for(i=0; i<model.listaTipoTramites.length;i++){
        htmlAux +="<li>"+model.listaTipoTramites[i]+"</li>";        
      }
      htmlAux += "</ul>";
    }
  
  this.prepareModel();
  
  var metadata = {type: "PanelComponent", id:"panelConfrontaCuentaIndividual", layout: [[{span: 12}],[{span: 12}], [{span: 12}]],
    components: [
      { type:"PanelComponent", label:"Informaci\u00f3n de la solicitud", layout:[[{span:6},{span:6}],[{span:6},{span:6}]], 
          components:[
            { type:"LabelComponent", className:"label-cda", label:"Folio"}, { type:"LabelComponent", className:"label-cda", label:"Tipo de tr\u00e1mite de la solicitud"},
            { type:"LabelComponent", label: model.folioSolicitud }, { type:"HTMLComponent", html: htmlAux }
          ]},
      {type: "TabPanelComponent", id: "cuentaIndividual",
        tabs: [
          {title: "CERTIFICADOR",
            panel: {type: "PanelComponent", id:"tab-certificadorPagerConfronta",
              components: [                
                { type:"LabelComponent", label:"Informaci\u00f3n regularizada en el IMSS", tag:"h4"},
                { type: "PanelComponent", className:"scrolled",
                  layout:[[{span:12}]], 
                  components:[                                                            
                    {
                        type: "ConfrontaCuentaIndividualAseguradoComponent", indexName:"certificadorPagerConfronta",
                      parentId: this.component.model+".ultima.listaCuentaIndividualNssCertificador",
                      id: this.component.model+".ultima.listaCuentaIndividualNssCertificador",
                      model: this.component.model+".ultima.listaCuentaIndividualNssCertificador"
                    }
                  ]
                },
                { type:"LabelComponent", label:"Informaci\u00f3n previa en el IMSS", tag:"h4"},
                { type: "PanelComponent", className:"scrolled", layout:[[{span:12}]], 
                  components:[
                    {                      
                        type: "ConfrontaCuentaIndividualAseguradoComponent", indexName:"certificadorPagerConfronta",
                        parentId: this.component.model+".inicial.listaCuentaIndividualNssCertificador", 
                        id: this.component.model+".inicial.listaCuentaIndividualNssCertificador",
                        model: this.component.model+".inicial.listaCuentaIndividualNssCertificador"
                     
                    }
                  ]
                },
                { type:"NssPagerComponent", componentName: "ConfrontaCuentaIndividualAseguradoComponent",
                  parentId: this.component.model+".ultima.listaCuentaIndividualNssCertificador",
                  id:"certificadorPagerConfronta", model: this.component.model+".ultima.listaCuentaIndividualNssCertificador", numberOfElements: 5}
              ], layout: [[{span:12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}]]}}
          ,
          {title: "ASOCIADO AL ASEGURADO",
          panel: {type: "PanelComponent", id:"tab-asociadoPagerConfronta",
              components: [                
                { type:"LabelComponent", label:"Informaci\u00f3n regularizada en el IMSS", tag:"h4"},
                { type: "PanelComponent", className:"scrolled",
                  layout:[[{span:12}]], 
                  components:[
                    {
                        type: "ConfrontaCuentaIndividualAseguradoComponent", indexName:"asociadoPagerConfronta",
                        parentId: this.component.model+".ultima.listaCuentaIndividualNssAsociado",
                        id: this.component.model+".ultima.listaCuentaIndividualNssAsociado",
                        model: this.component.model+".ultima.listaCuentaIndividualNssAsociado"
                    }
                    
                  ]
                },
                { type:"LabelComponent", label:"Informaci\u00f3n previa en el IMSS", tag:"h4"},
                { type: "PanelComponent", className:"scrolled", layout:[[{span:12}]], 
                  components:[
                    {
                        type: "ConfrontaCuentaIndividualAseguradoComponent", indexName:"asociadoPagerConfronta",
                        parentId: this.component.model+".inicial.listaCuentaIndividualNssAsociado", 
                        id: this.component.model+".inicial.listaCuentaIndividualNssAsociado",
                        model: this.component.model+".inicial.listaCuentaIndividualNssAsociado"
                      }
                    
                  ]
                },
                { type:"NssPagerComponent", componentName: "ConfrontaCuentaIndividualAseguradoComponent",
                  parentId: this.component.model+".ultima.listaCuentaIndividualNssAsociado",
                  id:"asociadoPagerConfronta", model: this.component.model+".ultima.listaCuentaIndividualNssAsociado", numberOfElements: 5}
              ], layout: [[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}]]}},
          {title: "NO CORRESPONDEN AL ASEGURADO", 
            panel: {type: "PanelComponent", id:"tab-noPertenecePagerConfronta",
              components: [                
                { type:"LabelComponent", label:"Informaci\u00f3n regularizada en el IMSS", tag:"h4"},
                { type: "PanelComponent", className:"scrolled",
                  layout:[[{span:12}]], 
                  components:[
                    {
                        type: "ConfrontaCuentaIndividualAseguradoComponent", indexName:"noPertenecePagerConfronta",
                        parentId: this.component.model+".ultima.listaCuentaIndividualNssNoPertenece",
                        id: this.component.model+".ultima.listaCuentaIndividualNssNoPertenece",
                        model: this.component.model+".ultima.listaCuentaIndividualNssNoPertenece"                   
                    }
                  ]
                },
                { type:"LabelComponent", label:"Informaci\u00f3n previa en el IMSS", tag:"h4"},
                { type: "PanelComponent", className:"scrolled", layout:[[{span:12}]], 
                  components:[
                    {
                        type: "ConfrontaCuentaIndividualAseguradoComponent", indexName:"noPertenecePagerConfronta",
                        parentId: this.component.model+".inicial.listaCuentaIndividualNssNoPertenece",
                        id: this.component.model+".inicial.listaCuentaIndividualNssNoPertenece",
                        model: this.component.model+".inicial.listaCuentaIndividualNssNoPertenece"
                     
                    }
                  ]
                },
                { type:"NssPagerComponent", componentName: "ConfrontaCuentaIndividualAseguradoComponent",
                  parentId:this.component.model+".ultima.listaCuentaIndividualNssNoPertenece",
                  id:"noPertenecePagerConfronta", model: this.component.model+".ultima.listaCuentaIndividualNssNoPertenece", numberOfElements: 5}
              ], layout: [[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}]]}}
        ]
      },
      {type: "ButtonGroupComponent", id: "cuentaIndividualButtonGroup",
        components: [          
          
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonBack"),
            label: "Regresar", icon: "", className: "btn-default",
            trigger: {type: "ConfrontaCuentaIndividualComponent", event: "back", key: this.component.id, model: ""}
          },
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".informacion"),
              label: "Informaci\u00f3n de la solicitud", icon: "", className: "btn-default",
              command: "transition", argument:"'informacionSolicitud'"
          },
          {type: "ButtonComponent", id: this.render.toId(this.component.id + ".buttonNext"),
            label: "Siguiente", icon: "", className: "btn-primary",
            trigger: {type: "ConfrontaCuentaIndividualComponent", event: "next", key: this.component.id, model: ""}
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


ConfrontaCuentaIndividualComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#mainCuentaIndividual_" + this.component.id).html(html);
};



ConfrontaCuentaIndividualComponent.prototype.triggerEvent = function (event, parameters) {
  switch (event) {
    case "next":
      this.render.module.controller.next(this.component.screen);
      break;
    case "back":
      this.render.module.controller.back(this.component.screen);
      break;
  }
};

/* 
 * Los periodos incluidos seran intercambiados por los periodos originales
 * para mantener la funcionalidad del marcado al detectar el cambio en NSS  
 */
ConfrontaCuentaIndividualComponent.prototype.prepareModel = function () {
  var i;
  var model = this.render.getModel( this.component.model );
  
  if( this.render.isEmpty(model) ){
    return;
  }
  
  var ultima = JSON.parse( JSON.stringify(model) );
  var inicial = JSON.parse( JSON.stringify(model) );
  
  model.ultima = ultima;
  model.inicial = inicial;
  
  
  if( !this.render.isEmpty(model.ultima.listaCuentaIndividualNssCertificador) ){
    for(i=0;i<model.ultima.listaCuentaIndividualNssCertificador.length;i++){
      this.prepareCuentaIndividualNss( model.ultima.listaCuentaIndividualNssCertificador[i] );      
    }
  }
  if( !this.render.isEmpty(model.ultima.listaCuentaIndividualNssAsociado) ){
    for(i=0;i<model.ultima.listaCuentaIndividualNssAsociado.length;i++){
      this.prepareCuentaIndividualNss( model.ultima.listaCuentaIndividualNssAsociado[i] );      
    }
  }
  if( !this.render.isEmpty(model.ultima.listaCuentaIndividualNssNoPertenece) ){
    for(i=0;i<model.ultima.listaCuentaIndividualNssNoPertenece.length;i++){
      this.prepareCuentaIndividualNss( model.ultima.listaCuentaIndividualNssNoPertenece[i] );      
    }
  }
  
  // De la version inicial eliminar los periodos modificados
  if( !this.render.isEmpty(model.inicial.listaCuentaIndividualNssCertificador) ){
    for(i=0;i<model.inicial.listaCuentaIndividualNssCertificador.length;i++){
      model.inicial.listaCuentaIndividualNssCertificador[i].periodosNuevos = [];      
    }
  }
  if( !this.render.isEmpty(model.inicial.listaCuentaIndividualNssAsociado) ){
    for(i=0;i<model.inicial.listaCuentaIndividualNssAsociado.length;i++){            
      model.inicial.listaCuentaIndividualNssAsociado[i].periodosNuevos = [];
    }
  }
  if( !this.render.isEmpty(model.inicial.listaCuentaIndividualNssNoPertenece) ){
    for(i=0;i<model.inicial.listaCuentaIndividualNssNoPertenece.length;i++){      
      model.inicial.listaCuentaIndividualNssNoPertenece[i].periodosNuevos = [];
    }
  }
  
  
  
  // console.log( model );
  
};

ConfrontaCuentaIndividualComponent.prototype.prepareCuentaIndividualNss = function ( cuentaIndividualNss ) {
  var j,k,l;
  if( !this.render.isEmpty( cuentaIndividualNss.listaPeriodosRegistroPatronal ) ){        
    for(j=0; j<cuentaIndividualNss.listaPeriodosRegistroPatronal.length; j++){
      
      var periodosRegistroPatronal = cuentaIndividualNss.listaPeriodosRegistroPatronal[j];
      
     
      
      
      var periodos = periodosRegistroPatronal.periodos;
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosIncluidos ) ){
        for(k=0; k<periodosRegistroPatronal.periodosIncluidos.length;k++ ){              
          var periodoIncluido = periodosRegistroPatronal.periodosIncluidos[k];
          for(l=0; l<periodos.length; l++){
            if(periodoIncluido.cveIdPeriodoAnterior === periodos[l].cveIdPeriodoCuentaIndividual){
              periodoIncluido.cveIdPeriodoAnterior = periodos[l].cveIdPeriodoCuentaIndividual;
              periodoIncluido.cveIdPeriodoCuentaIndividual = periodos[l].cveIdPeriodoCuentaIndividual;
              periodoIncluido.state="nss";
              periodoIncluido.nss = ""+periodoIncluido.nss;
              // console.log(  JSON.stringify(periodoIncluido) );
              // console.log( periodoIncluido.nss );
              periodoIncluido.tipoRegularizacionPeriodo = "I";
              periodoIncluido.classNames = this.getClassName( periodoIncluido, periodos[l]);
              periodos[l] = periodoIncluido;
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
              periodos[l] = periodoEiminado;
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
              periodos[l] = periodoModificado;
              
            }
          }
        }
      }
      
      // Falta hacer merge entre Incluidos y Modificados para que no se repitan
      
      
      
      
      if( !this.render.isEmpty( periodosRegistroPatronal.periodosNuevos ) ){
        for(k=0; k<periodosRegistroPatronal.periodosNuevos.length;k++ ){              
          var periodoNuevo = periodosRegistroPatronal.periodosNuevos[k];
          periodoNuevo.state="add";
          periodoNuevo.tipoRegularizacionPeriodo = "A";
          periodoNuevo.classNames = { tipoRegularizacionPeriodo :"bg-success label-cda" };
          periodos[periodos.length] = periodoNuevo;
        }
      }
      
    }
  }
};

ConfrontaCuentaIndividualComponent.prototype.getClassName = function (periodoA, periodoB) {
  var classNames = {};
  $.each( periodoB,function( key,value){
    classNames[key] = periodoA[key] === value ? "" :"bg-warning label-cda";
  });
  return classNames;
};

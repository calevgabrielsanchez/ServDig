/* global folioTramite */

function Model(module) {
  this.module = module;
  this.periodoCuentaIndividual = new PeriodoCuentaIndividualModel(module);
  this.correccionDatos = new CorreccionDatosModel(module);
  this.consultaSolicitud = new ConsultaSolicitudModel(module);
  this.init();
}

Model.prototype.init = function () {
  var i;
  this.metadata = {    
    model: {
      entities: [],
      domains: []
    }
  };
  
  for(i=0;i<this.periodoCuentaIndividual.entities.length; i++){
    this.metadata.model.entities[this.metadata.model.entities.length] = this.periodoCuentaIndividual.entities[i];
  }
  for(i=0;i<this.periodoCuentaIndividual.domains.length; i++){
    this.metadata.model.domains[this.metadata.model.domains.length] = this.periodoCuentaIndividual.domains[i];
  }
  
  for(i=0;i<this.correccionDatos.entities.length; i++){
    this.metadata.model.entities[this.metadata.model.entities.length] = this.correccionDatos.entities[i];
  }
  for(i=0;i<this.correccionDatos.domains.length; i++){
    this.metadata.model.domains[this.metadata.model.domains.length] = this.correccionDatos.domains[i];
  }
  
}; 

function ResponsableModule(instanceName, container) {
    this.container = container;
    this.instanceName = instanceName;
    this.view = new ResponsableUI(this);
    this.model = new Model(this);
    this.metadata = this.view.metadata;
    this.metadata.model = this.model.metadata.model;
    this.service = new ResponsableServices(this);
    this.controller = new ResponsableController(this);
    this.render = new Render(this);
    this.validator = new Validator(this);
    
    this.render.componentTemplates.ConsultaSolicitudButtonGroupComponent = new ConsultaSolicitudButtonGroupComponent(this.render);
    
    
    this.render.componentTemplates.ConfirmarCorreccionDatosNoCorrespondeComponent = new ConfirmarCorreccionDatosNoCorrespondeComponent( this.render );
    this.render.componentTemplates.ConfirmarCorreccionDatosAsociadoEntryComponent = new ConfirmarCorreccionDatosAsociadoEntryComponent(this.render);
    this.render.componentTemplates.ConfirmarCorreccionDatosComponent = new ConfirmarCorreccionDatosComponent(this.render);
    this.render.componentTemplates.ConfirmarCorreccionDatosEntryComponent = new ConfirmarCorreccionDatosEntryComponent(this.render);
  
    this.render.componentTemplates.EditTipoRegularizacionComponent = new EditTipoRegularizacionComponent(this.render);
    this.render.componentTemplates.EditCorreccionDatosComponent = new EditCorreccionDatosComponent(this.render);
    this.render.componentTemplates.CorreccionDatosComponent = new CorreccionDatosComponent(this.render);
    this.render.componentTemplates.FormaCorreccionDatosComponent = new FormaCorreccionDatosComponent(this.render);
    this.render.componentTemplates.NssPagerComponent = new NssPagerComponent(this.render);
    
    this.render.componentTemplates.ConfrontaPeriodoCuentaIndividualComponent = new ConfrontaPeriodoCuentaIndividualComponent(this.render);
    this.render.componentTemplates.ConfrontaCuentaIndividualNssComponent = new ConfrontaCuentaIndividualNssComponent(this.render);
    this.render.componentTemplates.ConfrontaCuentaIndividualComponent = new ConfrontaCuentaIndividualComponent(this.render);
    this.render.componentTemplates.ConfrontaCuentaIndividualAseguradoComponent = new ConfrontaCuentaIndividualAseguradoComponent(this.render);
    
	
    this.render.componentTemplates.CuentaIndividualConsultaComponent = new CuentaIndividualConsultaComponent(this.render);
    this.render.componentTemplates.CuentaIndividualConsultaAseguradoComponent = new CuentaIndividualConsultaAseguradoComponent(this.render);
    this.render.componentTemplates.PeriodoCuentaIndividualConsultaComponent = new PeriodoCuentaIndividualConsultaComponent(this.render);        
    this.render.componentTemplates.CuentaIndividualConsultaNssComponent = new CuentaIndividualConsultaNssComponent(this.render);
    
    
    this.render.componentTemplates.CuentaIndividualComponent = new CuentaIndividualComponent(this.render);
    this.render.componentTemplates.CuentaIndividualNssComponent = new CuentaIndividualNssComponent(this.render);
    this.render.componentTemplates.PeriodoCuentaIndividualNssComponent = new PeriodoCuentaIndividualNssComponent(this.render);
    this.render.componentTemplates.RegistroPatronalComponent = new RegistroPatronalComponent(this.render);
    
    //this.render.componentTemplates.CuentaIndividualComponent = new CuentaIndividualComponent(this.render);
    //this.render.componentTemplates.CuentaIndividualAseguradoComponent = new CuentaIndividualAseguradoComponent(this.render);
    //this.render.componentTemplates.PeriodoCuentaIndividualEditComponent = new PeriodoCuentaIndividualEditComponent(this.render);
    //this.render.componentTemplates.PeriodoCuentaIndividualNssComponent = new PeriodoCuentaIndividualNssComponent(this.render);
    //this.render.componentTemplates.CuentaIndividualEditComponent = new CuentaIndividualEditComponent(this.render);
    //this.render.componentTemplates.CuentaIndividualNssComponent = new CuentaIndividualNssComponent(this.render);
    
    //this.render.componentTemplates.CuentaIndividualConfirmarMainComponent = new CuentaIndividualConfirmarMainComponent(this.render);
    //this.render.componentTemplates.CuentaIndividualConfirmarComponent = new CuentaIndividualConfirmarComponent(this.render);
    //this.render.componentTemplates.PeriodoCuentaIndividualConfirmarComponent = new PeriodoCuentaIndividualConfirmarComponent(this.render);
    
    this.render.componentTemplates['ConsultaSolicitudComponent'] = new ConsultaSolicitudComponent(this.render);
    this.render.componentTemplates['ResumenCorreccionComponent'] = new ResumenCorreccionComponent(this.render);
//    this.render.componentTemplates['NavegacionPersonaNSSComponent'] = new NavegacionPersonaNSSComponent(this.render);

    this.render.componentTemplates.NssDocumentsEntryComponent = new NssDocumentsEntryComponent(this.render);
    this.render.componentTemplates.NssDocumentsComponent = new NssDocumentsComponent(this.render);
  
};

ResponsableModule.prototype.updateModel = function (component, key, value) {
  // console.log("UpdateModel, model:" + key +", value: " + JSON.stringify(value) );  
  this.controller.model[key] = value;
  this.render.notify(component, key, value);
};

ResponsableModule.prototype.updateModelGroupGrids = function (component, key, value, model) {
    this.controller.model[model] = value;
    this.render.notify(component, key, value);
};

var module;

$( document ).ready(function () {
  module = new ResponsableModule( 'module', 'workingArea' );
  
  if (folioTramite !== "") {
    module.render.module.controller.model.responsableCardLayout = 2;
    module.service.fetchSolicitudSeguimiento(folioTramite);
    module.controller.model.consultaSolicitud={idEstadoSolicitud:0, informacionRENAPO:{curpsHistoricas:""}};
    module.controller.init();        
  }else{
    module.controller.init();
  }
});

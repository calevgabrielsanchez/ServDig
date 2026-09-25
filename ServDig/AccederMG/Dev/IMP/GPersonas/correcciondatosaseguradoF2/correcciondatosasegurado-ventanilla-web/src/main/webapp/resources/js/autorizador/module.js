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



function AutorizadorModule(instanceName, container) {
    this.container = container;
    this.instanceName = instanceName;
    this.view = new ResponsableUI(this);
    this.model = new Model(this);
    this.metadata = this.view.metadata;
    this.metadata.model = this.model.metadata.model;
    this.service = new AutorizadorServices(this);
    this.controller = new AutorizadorController(this);
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
    
    
    //  this.render.componentTemplates.ConfirmarCorreccionDatosLecturaNoCorrespondeComponent = new ConfirmarCorreccionDatosLecturaNoCorrespondeComponent( this.render );
    //  this.render.componentTemplates.ConfirmarCorreccionDatosLecturaAsociadoEntryComponent = new ConfirmarCorreccionDatosLecturaAsociadoEntryComponent(this.render);
    //  this.render.componentTemplates.ConfirmarCorreccionDatosLecturaComponent = new ConfirmarCorreccionDatosLecturaComponent(this.render);
    //  this.render.componentTemplates.ConfirmarCorreccionDatosLecturaEntryComponent = new ConfirmarCorreccionDatosLecturaEntryComponent(this.render);
      
    //  this.render.componentTemplates.EditTipoRegularizacionLecturaComponent = new EditTipoRegularizacionLecturaComponent(this.render);
    // this.render.componentTemplates.EditCorreccionDatosLecturaComponent = new EditCorreccionDatosLecturaComponent(this.render);
    //  this.render.componentTemplates.CorreccionDatosLecturaComponent = new CorreccionDatosLecturaComponent(this.render);
    //  this.render.componentTemplates.FormaCorreccionDatosLecturaComponent = new FormaCorreccionDatosLecturaComponent(this.render);
    //  this.render.componentTemplates.NssPagerLecturaComponent = new NssPagerLecturaComponent(this.render);
    
    /*
    this.render.componentTemplates.CuentaIndividualComponent = new CuentaIndividualComponent(this.render);
    this.render.componentTemplates.CuentaIndividualAseguradoComponent = new CuentaIndividualAseguradoComponent(this.render);
    this.render.componentTemplates.PeriodoCuentaIndividualEditComponent = new PeriodoCuentaIndividualEditComponent(this.render);
    this.render.componentTemplates.PeriodoCuentaIndividualNssComponent = new PeriodoCuentaIndividualNssComponent(this.render);
    this.render.componentTemplates.CuentaIndividualEditComponent = new CuentaIndividualEditComponent(this.render);
    this.render.componentTemplates.CuentaIndividualNssComponent = new CuentaIndividualNssComponent(this.render);
    */
    
    this.render.componentTemplates['ConsultaSolicitudComponent'] = new ConsultaSolicitudComponent(this.render);
    this.render.componentTemplates['ResumenCorreccionComponent'] = new ResumenCorreccionComponent(this.render);
//    this.render.componentTemplates['NavegacionPersonaNSSComponent'] = new NavegacionPersonaNSSComponent(this.render);

    this.render.componentTemplates.NssDocumentsEntryComponent = new NssDocumentsEntryComponent(this.render);
    this.render.componentTemplates.NssDocumentsComponent = new NssDocumentsComponent(this.render);
};

AutorizadorModule.prototype.updateModel = function (component, key, value) {
    this.controller.model[key] = value;
    this.render.notify(component, key, value);
};

AutorizadorModule.prototype.updateModelGroupGrids = function (component, key, value, model) {
    this.controller.model[model] = value;
    this.render.notify(component, key, value);
};

var module;

$( document ).ready(function () {
  module = new AutorizadorModule( 'module', 'workingArea' );
  module.controller.init();
  if (folioTramiteReportes!= "") {
    module.service.fetchSolicitudReporte("consultaSolicitud",module, folioTramiteReportes);
  }
});

function ResponsableModule(instanceName, container) {
    this.container = container;
    this.instanceName = instanceName;
    this.view = new ResponsableUI(this);
    this.metadata = this.view.metadata;
    this.service = new ResponsableServices(this);
    this.controller = new ResponsableController(this);
    this.render = new Render(this);
    this.validator = new Validator(this);
    this.render.componentTemplates.PeriodoCuentaIndividualComponent = new PeriodoCuentaIndividualComponent(this.render);
    this.render.componentTemplates.CuentaIndividualComponent = new CuentaIndividualComponent(this.render);
    this.render.componentTemplates.CuentaIndividualConfirmarMainComponent = new CuentaIndividualConfirmarMainComponent(this.render);
    this.render.componentTemplates.CuentaIndividualConfirmarComponent = new CuentaIndividualConfirmarComponent(this.render);
    this.render.componentTemplates.PeriodoCuentaIndividualConfirmarComponent = new PeriodoCuentaIndividualConfirmarComponent(this.render);
    
    this.render.componentTemplates['ConsultaSolicitudComponent'] = new ConsultaSolicitudComponent(this.render);
    this.render.componentTemplates['ResumenCorreccionComponent'] = new ResumenCorreccionComponent(this.render);
    this.render.componentTemplates['NavegacionPersonaNSSComponent'] = new NavegacionPersonaNSSComponent(this.render);
};

ResponsableModule.prototype.updateModel = function (component, key, value) {
    this.controller.model[key] = value;
    this.render.notify(component, key, value);
};

var module;

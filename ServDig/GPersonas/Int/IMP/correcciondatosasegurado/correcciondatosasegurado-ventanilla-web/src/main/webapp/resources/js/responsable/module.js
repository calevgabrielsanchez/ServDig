function ResponsableModule(instanceName, container) {
    this.container = container;
    this.instanceName = instanceName;
    this.view = new ResponsableUI(this);
    this.metadata = this.view.metadata;
    this.service = new ResponsableServices(this);
    this.controller = new ResponsableController(this);
    this.render = new Render(this);
    this.validator = new Validator(this);
    this.render.componentTemplates['ConsultaSolicitudComponent'] = new ConsultaSolicitudComponent(this.render);
    this.render.componentTemplates['ResumenCorreccionComponent'] = new ResumenCorreccionComponent(this.render);
    this.render.componentTemplates['NavegacionPersonaNSSComponent'] = new NavegacionPersonaNSSComponent(this.render);
};

ResponsableModule.prototype.updateModel = function (component, key, value) {
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
  module.controller.init();
});

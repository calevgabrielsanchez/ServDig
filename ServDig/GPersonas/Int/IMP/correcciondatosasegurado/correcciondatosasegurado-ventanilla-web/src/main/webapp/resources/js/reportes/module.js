function ReportesModule(instanceName, container) {
    this.container = container;
    this.instanceName = instanceName;
    this.view = new ReportesUI(this);
    this.metadata = this.view.metadata;
    this.service = new ReportesServices(this);
    this.controller = new ReportesController(this);
    this.render = new Render(this);
    this.validator = new Validator(this);
    //this.render.componentTemplates['ConsultaSolicitudComponent'] = new ConsultaSolicitudComponent(this.render);
    //this.render.componentTemplates['ResumenCorreccionComponent'] = new ResumenCorreccionComponent(this.render);
    //this.render.componentTemplates['NavegacionPersonaNSSComponent'] = new NavegacionPersonaNSSComponent(this.render);
};

ReportesModule.prototype.updateModel = function (component, key, value) {
    this.controller.model[key] = value;
    this.render.notify(component, key, value);
};

var module;

$( document ).ready(function () {
  module = new ReportesModule( 'module', 'workingArea' );
  module.controller.init();
});

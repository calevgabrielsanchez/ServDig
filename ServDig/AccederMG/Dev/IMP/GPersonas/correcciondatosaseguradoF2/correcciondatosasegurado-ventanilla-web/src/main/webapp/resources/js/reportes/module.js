function ReportesModule(instanceName, container) {
    this.container = container;
    this.instanceName = instanceName;
    this.view = new ReportesUI(this);
    this.metadata = this.view.metadata;
    this.model = new Model (this);
    this.metadata.model = this.model.metadata.model;
    this.service = new ReportesServices(this);
    this.controller = new ReportesController(this);
    this.render = new Render(this);
    this.validator = new Validator(this);
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
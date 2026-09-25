function BandejaSolicitudesModule(metadata, instanceName, container) {
    this.container = container;
    this.instanceName = instanceName;
    this.metadata = metadata;
    this.service = new BandejaSolicitudesServices(this);
    this.controller = new BandejaSolicitudesController(this);
    this.render = new Render(this);
    this.validator = new Validator(this);
};

BandejaSolicitudesModule.prototype.updateModel = function (component, key, value) {
    this.controller.model[key] = value;
    this.render.notify(component, key, value);
};

var module;
$( document ).ready(function () {
  module = new BandejaSolicitudesModule( metadata, 'module', 'workingArea' );
  module.controller.init();
});

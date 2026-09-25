function ConsultaSolicitudButtonGroupComponent( render ){
    this.render = render;
}
ConsultaSolicitudButtonGroupComponent.prototype.draw = function (component) {
  this.component = component;  
  var html = '<div id="consultaSolicitudButtonGroup_' + component.id + '" >';
  html += this.drawBody();  
  html += '</div>';
  return html;
};

ConsultaSolicitudButtonGroupComponent.prototype.drawBody = function () {
  var html = "";
  var botones = [];
  var i;
  var model = this.render.getModel( this.component.model );
  // console.log( model );
  if( !this.render.isEmpty( model ) ){
    model = parseInt( model );
    if( this.component.position === "top"){
      botones = this.render.module.model.consultaSolicitud.getBotonesSuperiores( model );
    }else{
      botones = this.render.module.model.consultaSolicitud.getBotonesInferiores( model );
    }
    
    var components = [];
    for( i=0; i<botones.length;i++ ){
      components[components.length] = { type:"ButtonComponent", 
        label: botones[i].label, className: botones[i].className,
        command: botones[i].command };
    }
    
    var metadata = {
      type : "ButtonGroupComponent",
      components : components
    };
    
    var buttonGroupComponent = new ButtonGroupComponent( this.render );
    html += buttonGroupComponent.draw( metadata );
    
  }  
  return html;
};

ConsultaSolicitudButtonGroupComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata;        
    var html = this.drawBody();
    $("#consultaSolicitudButtonGroup_" + this.component.id).html( html );
};



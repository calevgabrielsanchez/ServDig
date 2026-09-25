function ResumenCorreccionComponent( render ){
    this.render = render;
    this.model = this.render.module.controller.model.resumenCorreccion;
}
ResumenCorreccionComponent.prototype.draw = function (component) {
    var html = '<div class="row-fluid"';
    if( !this.render.isEmpty( component.id ) ){
        html += 'id="resumen_' + component.id + '" ';
    }    
    html += " >";
    if( !this.render.isEmpty( this.model ) ){
      html += "<table class='table'>";
      html += "<tr><th>NSS</th><th>Tipo</th><th>Detalles</th></tr>";
      for( var i = 0; i< this.model.length; i++){
        html += "<tr> ";
        html += "<td>" + this.model[i].nss +"</td>";
        html += "<td>Certificador</td>";
        html += "<td>";
        var opciones = this.model[i].grupoCorreccion;
          var _this = this;
          if( opciones !== null && typeof opciones === 'object'){
            html += "<ul>";
            $.each( opciones, function (key, value) {
              if( value === true || value === "true"){
                html += "<li>";
                html += _this.render.nvl(_this.render.module.controller.model.gruposCorreccionLabels[key]);
                html += "</li>";
              }
            });
            html += "</ul>";
          }
        
        html += "</td>";
        html += "</tr> ";
      }
      html += "</table>";    
    }
    
    html += '</div>';
    return html;
};

ResumenCorreccionComponent.prototype.notify = function( watch, model ){
    this.watch = watch;
    this.model = this.render.module.controller.model[watch.model];
    var html = this.draw(watch);
    $("#resumen_" + watch.id).html( html );
};
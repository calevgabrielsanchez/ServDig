function NssPagerComponent(render){
 this.render = render; 
}
NssPagerComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div style="text-align:center" id="nssPager_' + this.render.toId(this.component.id) + '" >';
  html += this.drawBody();
  html += '</div>';
  return html;
};
NssPagerComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var currentIndex = this.render.getModel( this.component.id );
  if (model === undefined) {
    model = [];
  }
  if( currentIndex === undefined ){
    currentIndex = 0;
  }
  // console.log( currentIndex );
  
  var total = model.length;
  var elements = this.component.numberOfElements;
  if( elements === undefined ){
    elements = 2;
  }
  var initialIndex = currentIndex - elements;
  if( initialIndex < 0 ){
    initialIndex = 0;
  }
  var finalIndex = currentIndex + elements;
  if( finalIndex > total ){
    finalIndex = total;
  }
    

  var html = "";
  html += '<nav aria-label="Page navigation">';
  html += '<ul class="pagination">';
  html += '  <li ';
  if( initialIndex === 0){
    html += 'class="disabled" ><a>&raquo;</a>';
  }else{
    html += '><a href="#" onclick="' + this.render.drawTriggerEvent( this.component.componentName, "page", this.component.parentId , "0" ) + '" >&laquo;</a>';
  }
  html += '</li>';
  for (var index = initialIndex; index < finalIndex; index++) {
    html += '<li ';
    if (index === currentIndex) {
      html += ' class="active" ';
    }
    html += '><a href="#" onclick="' + this.render.drawTriggerEvent( this.component.componentName, "page", this.component.parentId , ""+index ) + ' " >' + model[index].nss;
    if (index === currentIndex) {
      html += '<span class="sr-only">(current)</span>';
    }
    html += '</a></li>';
  }
  html += '<li ';
  if (finalIndex === total) {
    html += ' class="disabled" ><a>&raquo;</a>';
  }else{
    html += '><a href="#" onclick="' + this.render.drawTriggerEvent( this.component.componentName, "page", this.component.parentId , ""+finalIndex ) + ' " >&raquo;</a>';
  }
  html += '</li></ul></nav>';
        
  return html;
};

NssPagerComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var html = this.drawBody();
  $("#nssPager_" + this.render.toId(this.component.id)).html(html);
};



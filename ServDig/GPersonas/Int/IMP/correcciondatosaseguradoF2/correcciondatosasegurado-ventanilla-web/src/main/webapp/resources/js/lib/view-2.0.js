/*jshint -W061 */
function IteratorComponent( render ){
    this.render = render;
}
IteratorComponent.prototype.draw = function (component) {
  this.component = component;  
  var html = '<div id="iterator_' + this.render.toId(component.id) + '" >';
  html += this.drawBody();  
  html += '</div>';
  return html;
};

IteratorComponent.prototype.drawBody = function () {
  var html = "";
  var panel = new PanelComponent(this.render);
  this.model = this.render.getModel( this.component.model );
  var metadata = { type: "PanelComponent", components:[], layout:[] };
  if(!this.render.isEmpty(this.component) && !this.render.isEmpty( this.model )){
    var entry = JSON.stringify( this.component.entry );
    if( !this.render.isEmpty( this.component.indexName ) ){      
      for( var index = 0; index < this.model.length; index++){        
        var replaced = entry.replace( new RegExp(this.component.indexName, "g"), index );
        metadata.components[index] = JSON.parse(replaced);
        
        if(!this.render.isEmpty(this.component.expression)){
          eval( "metadata.components[index]" + this.component.expression );
        }
        
        metadata.layout[index] = [{span:12}];        
      }      
    }
    
    html = panel.draw( metadata );
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
  return html;
};

IteratorComponent.prototype.notify = function (metadata, model) {
    this.component = metadata;
    var id = this.component.id;
    this.model = model;
    var html = this.drawBody();
    $("#iterator_" + this.render.toId(id)).html( html );
};

/**
 * Incluye o sustituye un componente al arreglo de componentes volatiles
 * @param {type} volatile
 * @returns {undefined}
 */
Render.prototype.addVolatileComponent = function (volatile) {
  var i;
  for(i=0; i< this.volatileComponents.length; i++){
    if( this.volatileComponents[i].type === volatile.type &&
        this.volatileComponents[i].id === volatile.id  && volatile.id !== undefined  ){
          this.volatileComponents[i] = volatile;
          return;
        }
  }
  this.volatileComponents[this.volatileComponents.length] = volatile;
};
/**
 * M�todo que valida si 'value' est� vacio
 * @param {*} value
 * @returns {Boolean}
 */
Render.prototype.isEmpty = function (value) {
  return (value === null || value === undefined || value === '');
};
/**
 * M�todo que genera el html de un comando
 * @param {String} component
 * @returns {String}
 */
Render.prototype.drawCommand = function (component) {
  var html = "";
  html += this.module.instanceName + ".controller." + component + "()";
  return html;
};
/**
 * M�todo que genera el html de un comando
 * @param {String} component
 * @param {String} args
 * @returns {String}
 */
Render.prototype.drawCommandArguments = function (component, args) {
  var html = "";
  html += this.module.instanceName + ".controller." + component + "(" + args + ")";
  return html;
};
/**
 * M�todo que genera el html de un evento
 * @param {String} component
 * @returns {String}
 */
Render.prototype.drawTriggerEvent = function (type, event, key, model) {
  var html = "";
  var parameters = { type: type, event: event, key: key, model: model };
  var base64 = window.btoa( JSON.stringify( parameters ) );
    
  if( model.indexOf("currentValue") > -1 ){
    html += this.module.instanceName + ".render.triggerEventValue('" + base64 + "', this.value )";
  }
  else{    
    html += this.module.instanceName + ".render.triggerEvent('" + base64 + "' )";
  }
  
  return html;
};
/**
 * 
 * @param {String} component
 * @returns {String}
 */
/**
 * M�todo que obtine un watch de la lista de Watch
 * @param {String} key
 * @param {String} type : Tipo de componente (ver componentTemplates)
 * @returns {Object}
 * e.g.
 * module.render.getWatch('campo_21', 'TextFieldComponent');
 */
Render.prototype.getWatch = function (key, type) {
  for (var i = 0; i < this.watchers.length; i++) {
    if (this.watchers[i].id === key && this.watchers[i].type === type) {
      var w = this.watchers[i];
      return w.field;
    }
  }
  return {};
};
/**
 * Metodo que procesa los eventos en cada uno de los componentes
 * @param {String} type                : Tipo de componente (ver componentTemplates)
 * @param {String} event               : Tipo de evento, definido por c/u de los componentes
 * @param {String} key                 : Identificador del campo
 * @param {Object|String|Number} model : Par�metros
 * @returns {void}
 * e.g.
 * module.render.triggerEvent('CardLayoutComponent','card','card', 0);
 * module.render.triggerEvent('TextFieldComponent','validate','CAMPO_09', {isValid:true, message:'campo obligatorio'});
 */
Render.prototype.triggerEvent = function ( base64 ) {
  var parameters = JSON.parse( window.atob( base64 ) );  
  var component = this.componentTemplates[parameters.type];
  component.component = this.getComponentById(parameters.key, parameters.type);
  if (!this.isEmpty(component)) {
    component.triggerEvent(parameters.event, parameters.model);
  }else{
    // // // console.log("No se encuentra el componente:" + type + " :"  + key);
  }
};
/**
 * Metodo que procesa los eventos en cada uno de los componentes
 * @param {String} type                : Tipo de componente (ver componentTemplates)
 * @param {String} event               : Tipo de evento, definido por c/u de los componentes
 * @param {String} key                 : Identificador del campo
 * @param {Object|String|Number} model : Par�metros
 * @param {String|Number} value : Valor actual del componente
 * @returns {void}
 * e.g.
 * module.render.triggerEvent('CardLayoutComponent','card','card', 0);
 * module.render.triggerEvent('TextFieldComponent','validate','CAMPO_09', {isValid:true, message:'campo obligatorio'});
 */
Render.prototype.triggerEventValue = function (base64, value) {
  var parameters = JSON.parse( window.atob( base64 ) );
  var component = this.componentTemplates[parameters.type];
  component.component = this.getComponentById(parameters.key, parameters.type);
  if (!this.isEmpty(component)) {
    var replaced = parameters.model.replace("currentValue", value);
    component.triggerEvent(parameters.event, JSON.parse(replaced));
  }else{
    // // // console.log("No se encuentra el componente:" + type + " :"  + key);
  }
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos y avisara al componente al que corresponda la notificaci�n
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
Render.prototype.notify = function (type, key, value) {

  var component = this.componentTemplates[type];
  if (!this.isEmpty(component)) {
    var metadata = this.getComponentById(key, type);        
    component.notify(metadata, value);
  }  
  this.digest();
};
/**
 * M�todo que procesa cada uno de los componentes agregados en la lista linker
 * @param {Object} link : metadata
 * @returns {void}
 */
Render.prototype.digest = function ( ) {
  var localLinks = this.linker;
  this.linker = [];
  for (var i = 0; i < localLinks.length; i++) {
    var component = this.componentTemplates[localLinks[i].type];
    if (component !== null && component !== undefined) {      
      component.digest(localLinks[i]);
    }
  }
};
/**
 * M�todo que dibuja todos los componentes
 * @param {String} fluid
 * @returns {void}
 */
Render.prototype.draw = function (fluid) {
  var html = this.render(fluid); // Con margenes
  var div = document.getElementById(this.container);
  if (div !== null) {
    div.innerHTML = html;
  }
  var _this = this;
  var timeTimer = 200;
  setTimeout(function () {
    _this.digest();
    if (_this.dispatchEvent) {
      _this.dispatchEvent = false;
      $.event.trigger({
        type: "onDrawReady"
      });
      setTimeout(function () {
        _this.dispatchEvent = true;
      }, timeTimer * 2);
    }
  }, timeTimer);
};
/**
 * M�todo
 * @param {String} fluid
 * @returns {String}
 */
Render.prototype.render = function (fluid) {
  if (fluid === undefined || fluid === null) {
    fluid = "";
  }
  var html = "";
  var rows = this.layout;
  var components = this.components;

  var componentIndex = 0;

  for (var i = 0; i < rows.length; i++) {
    html += "<div class='" + fluid + "'>";
    html += "<div class='row' >";
    var row = rows[i];
    var ocupado = 0;
    for (var j = 0; j < row.length; j++) {
      ocupado += (row[j].span) * 100 / 12;
      if (components.length > componentIndex) {
        var component = this.componentTemplates[components[componentIndex].type];
        if (component !== null && component !== undefined) {
          switch (true) {
            case (components[componentIndex].type === "HiddenComponent"):
              component.draw(components[componentIndex]);
              j--;
              break;
            case (components[componentIndex].type === "ModalComponent"):
              var modal = component.draw(components[componentIndex]);
              this.addModal(modal);
              j--;
              break;
            default:
              html += "<div class='col-md-" + (row[j].span) + "'>";
              html += component.draw(components[componentIndex]);
              html += "</div>";
              break;
          }
        }
        componentIndex++;
      }
    }
    html += "</div>";
    html += "</div>";
  }
  return html;
};
/**
 * M�todo que agrega una ventana modal
 * @param {String} html
 * @returns {void}
 */
Render.prototype.addModal = function (html) {
  $("#modals").append(html);
};
/**
 * M�todo que muestra los datos del modelo en la vista(GUI)
 * @param {String} formId : {formId:'FormPanelComponent-' + component.field}
 * @param {Object} model  : Objeto donde se descargaran los datos de la forma
 * @param {String} prefix
 * @returns {void}
 * e.g.
 * module.render.modelToForm('FormPanelComponent-' + componet.field, {});
 * module.render.modelToForm('FormPanelComponent-INSUMOS', module.controller.model);
 */
Render.prototype.modelToForm = function (formId, model, prefix, subfix) {
  var _this = this;
  if (prefix === undefined) {
    prefix = "";
  }
  if (subfix === undefined) {
    subfix = "";
  }
  if (model !== null && typeof model === 'object') {
    if( Array.isArray( model ) ){
      prefix = prefix.substr(0, prefix.length-1 );
      for( var index = 0; index < model.length; index ++ ){
        _this.modelToForm(formId, model[index],prefix + "["+index+"]." + "" );
      }
    }else{
      $.each(model, function (key, value) {
      if (value !== null && typeof value === 'object') {
        _this.modelToForm(formId, value, key + ".", "");
      } else {
        
        // HOOK: no intentar setear metadata 
        if( key !== "metadata" ){
          var matches = $.find("#" + formId + " :input[name='" + prefix + key + subfix+ "']");
          if (matches.length === 1) {        
            if (matches[0].type === "checkbox") {
              if (value === "on" || value === true || value === "true") {
                $("#" + matches[0].id).prop('checked', true);
              } else {
                $("#" + matches[0].id).prop('checked', false);
              }
              $("#" + matches[0].id).change();
            } else {
              $("#" + formId + " :input[name='" + prefix + key + subfix +"']").val( value );
              $("#" + formId + " :input[name='" + prefix + key + subfix + "']").change();
            }
          } else {
            if (matches.length > 1){
              $("#" + formId + " :input[name='" + prefix + key + subfix + "'][value='" + value + "']").prop('checked', true);
              $("#" + formId + " :input[name='" + prefix + key + subfix + "'][value='" + value + "']").change();
            }
          } 
        }
        
        
      }
    });
    }
  }
};
/**
 * M�todo que realiza el encode de una cadena en un div
 * @param {String} value
 * @returns {jQuery}
 */
Render.prototype.htmlEncode = function (value) {
  //create a in-memory div, set it's inner text(which jQuery automatically encodes)
  //then grab the encoded contents back out.  The div never exists on the page.
  if (!this.isEmpty(value)) {
    return $('<div/>').text(value).html();
  }
  return "";
};
/**
 * M�todo que realiza el decode de una cadena en un div
 * @param {type} value
 * @returns {jQuery}
 */
Render.prototype.htmlDecode = function (value) {
  return $('<div/>').html(value).text();
};

Render.prototype.toId = function (value) {
  return value.replace(/\./g, '_').replace(/\[/g,'L').replace(/\]/g,'J');
};
/**
 * M�todo para acceder al modelo desde la vista, el modelo ya debe existir
 * @param {type} value
 * @returns {jQuery}
 */
Render.prototype.getModel = function (value) {
  var modelBase = this.module.controller.model;
  var model;
  eval( "model = modelBase." + value );
  return model;
};
/**
 * M�todo que regresa el mesaje data del archivo de lenguaje
 * @param {String} data
 * @param {String} value
 * @returns {Render.prototype.nvl.text}
 */
Render.prototype.nvl = function (data, value) {
  var text = data; //$.i18n.prop(data);
  return (text !== null && text !== undefined) ? text : value;
};
/**
 * M�todo que agrega un link a la lista 'linker'
 * @param {Object} link : metadata
 * @returns {void}
 */
Render.prototype.addLinker = function (link) {
  // Cambiar la definicion a sets
  var i;
  for (i = 0; i < this.linker.length; i++) {
    if (this.linker[i].id === link.id) {
      this.linker[i] = link;
      return;
    }
  }
  // Ordenar los linker por prioridad
  if (this.linker.length === 0) {
    this.linker[this.linker.length] = link;
  } else {
    for (i = 0; i < this.linker.length; i++) {
      if (this.linker[i].priority >= link.priority) {
        break;
      }
    }
    this.linker.splice(i, 0, link);
  }
};
/**
 * M�todo que agrega un lista de link's a la lista 'linker'
 * @param {Object} link : metadata
 * @returns {void}
 */
Render.prototype.addLinkers = function (links) {
  for (var i = 0; i < links.length; i++) {
    this.addLinker(links[i]);
  }
};
Render.prototype.getComponentById = function (idComponente, type) {
  var component;
  var i;
  for (i = 0; i < this.components.length; i++) {
    if (this.components[i].id === idComponente && this.components[i].type === type) {
      return this.components[i];
    }
    if (!this.isEmpty(this.components[i].components)) {
      component = this.getComponentByIdContainer(idComponente, this.components[i], type);
      if (!this.isEmpty(component.id) && component.id === idComponente && component.type === type) {
        return component;
      }
    }
    if (!this.isEmpty(this.components[i].body) && !this.isEmpty(this.components[i].body.components)) {
      component = this.getComponentByIdContainer(idComponente, this.components[i].body, type);
      if (!this.isEmpty(component.id) && component.id === idComponente && component.type === type) {
        return component;
      }
    }
  }
  for (i = 0; i < this.volatileComponents.length; i++) {
    if (this.volatileComponents[i].id === idComponente && this.volatileComponents[i].type === type) {
      return this.volatileComponents[i];
    }
    if (!this.isEmpty(this.volatileComponents[i].components)) {
      component = this.getComponentByIdContainer(idComponente, this.volatileComponents[i], type);
      if (!this.isEmpty(component.id) && component.id === idComponente && component.type === type ) {
        return component;
      }
    }
    if (!this.isEmpty(this.volatileComponents[i].body) && !this.isEmpty(this.volatileComponents[i].body.components)) {
      component = this.getComponentByIdContainer(idComponente, this.volatileComponents[i].body, type);
      if (!this.isEmpty(component.id) && component.id === idComponente && component.type === type) {
        return component;
      }
    }
  }
  return {};
};
Render.prototype.getComponentByIdContainer = function (idComponente, container, type) {
  
  // Mientras se corrije CDA
  if(container.id == "sliderNSS" && (container.type == "cargarValores"||container.type == "cargarValoresLectura"))
    return {};
  
  var component;
  for (var i = 0; i < container.components.length; i++) {
    if (container.components[i].id === idComponente && container.components[i].type === type) {
      return container.components[i];
    }
    if (!this.isEmpty(container.components[i].components)) {
      component = this.getComponentByIdContainer(idComponente, container.components[i], type);
      if (!this.isEmpty(component.id) && component.id === idComponente && component.type === type) {
        return component;
      }
    }
    if (!this.isEmpty(container.components[i].body) && !this.isEmpty(container.components[i].body.components)) {
      component = this.getComponentByIdContainer(idComponente, container.components[i].body, type);
      if (!this.isEmpty(component.id) && component.id === idComponente && component.type === type) {
        return component;
      }
    }
  }
  return {};
};
/**
 * M�todo que agrega un watch a la lista 'watchers'
 * @param {type} watch
 * @returns {void}
 */
Render.prototype.addWatch = function (watch) {
  for (var i = 0; i < this.watchers.length; i++) {
    if (this.watchers[i].id === watch.id) {
      this.watchers[i] = watch;
      return;
    }
  }
  this.watchers[this.watchers.length] = watch;
};
/**
 * M�todo que agrega un lista de watch's a la lista 'watchers'
 * @param {type} watch
 * @returns {void}
 */
Render.prototype.addWatchers = function (watchers) {
  for (var i = 0; i < watchers.length; i++) {
    this.addWatch(watchers[i]);
  }
};
/**
 * M�todo que cierra la ventana modal
 * @param {String} $idModal
 * @returns {void}
 */
Render.prototype.closeModal = function ($idModal) {
  this.componentTemplates.ModalComponent.close($idModal);
};
/**
 * M�todo que muesta u oculta los componentes de un formulario, true los muestra
 * false los oculta
 * @param {String} $formId
 * @param {Array} $fieldList : ['campo_01', 'campo_02']
 * @param {Boolean} $enabled : [ true | false ]
 * @returns {void}
 */
Render.prototype.showFieldFormPanelComponent = function ($formId, $fieldList, $enabled) {
  var cardMetadata = this.getWatch('card', "CardLayoutComponent");
  var metadata = this.getWatch($formId, "FormPanelComponent");
  var currentCardId = cardMetadata.current;
  var currentCardModel = cardMetadata.model;

  if ($enabled === false) {
    var remove = this.removeMetadataFormField(metadata, $fieldList);
    if (remove) {
      this.componentTemplates.CardLayoutComponent.triggerEvent("card", currentCardModel, currentCardId);
    }
  } else {
    var completeMetadata = this.componentTemplates.CardLayoutComponent.getMetadata().cards[cardMetadata.current];
    var add = this.addMetadataFormField(completeMetadata, metadata, $fieldList, currentCardId);
    if (add) {
      this.componentTemplates.CardLayoutComponent.triggerEvent("card", currentCardModel, currentCardId);
    }
  }
};
/**
 * M�todo que quita del metadata los campos que no son requeridos en un formulario
 * regresa un booleano si lo remueve(true) o no(false)
 * @param {Object} $metadata
 * @param {Array} $fieldList : ['campo_01', 'campo_02']
 * @returns {Boolean}
 */
Render.prototype.removeMetadataFormField = function ($metadata, $fieldList) {
  var remove = false;
  var components = [];
  for (var index = 0; index < $metadata.components.length; index++) {
    var include = true;
    for (var element = 0; element < $fieldList.length; element++) {
      if (!this.isEmpty($fieldList[element])) {
        if ($fieldList[element] === $metadata.components[index].field) {
          delete this.module.controller[ $metadata.model ][$fieldList[element]];
          delete $fieldList[element];
          remove = true;
          include = false;
          break;
        }
      }
    }
    if (include) {
      components.push($metadata.components[index]);
    }
  }
  if (remove) {
    delete $metadata.components;
    $metadata.components = components;
  }
  return remove;
};
/**
 * M�todo que agrega el metadata los campos que son requeridos en un formulario
 * regresa un booleano si lo a�ade(true) o no(false)
 * @param {Object} $completeMetadata
 * @param {Object} $metadata
 * @param {Array} $fieldList : ['campo_01', 'campo_02']
 * @param {Number} $currentCardId
 * @returns {Boolean}
 */
Render.prototype.addMetadataFormField = function ($completeMetadata, $metadata, $fieldList, $currentCardId) {
  var complete = this.componentTemplates.CardLayoutComponent.getMetadata();
  var realElementsLength = $metadata.components.length;
  var metadataLength = $metadata.components.length;
  var hasToolbar = false;
  var add = false;
  if (!this.isEmpty(complete.cards[$currentCardId].managed)) {
    if (complete.cards[$currentCardId].managed === true) {
      hasToolbar = true;
      realElementsLength -= 2;
      metadataLength -= 2;
    }
  }
  if ($completeMetadata.components.length > realElementsLength) {
    var components = [];
    var elementCounter = 0;
    var fieldCounter = 0;
    for (var index = 0; index < $completeMetadata.components.length; index++) {
      if (elementCounter < metadataLength && $completeMetadata.components[index].type === "LabelComponent") {
        elementCounter++;
        components.push($completeMetadata.components[index]);
        add = true;
      } else if (elementCounter < metadataLength && $completeMetadata.components[index].field === $metadata.components[elementCounter].field) {
        elementCounter++;
        components.push($completeMetadata.components[index]);
        add = true;
      } else if (fieldCounter < $fieldList.length && $completeMetadata.components[index].field === $fieldList[fieldCounter]) {
        fieldCounter++;
        components.push($completeMetadata.components[index]);
        add = true;
      }
    }
    if (add) {
      if (hasToolbar) {
        components.push($metadata.components[metadataLength + 0]);
        components.push($metadata.components[metadataLength + 1]);
      }
      delete $metadata.components;
      $metadata.components = components;
    }
  }
  return add;
};
/**
 * M�todo que habilita o no un TextFieldComponent
 * @param {String} $key        : Identificador del componente
 * @param {Boolean} $enabled   : Bandera que indica si se habilita o no el componente
 * @param {Boolean} $emptyForm : Bandera que indica si se limpiara o no el valor del modelo
 * @returns {void}
 */
Render.prototype.enabledTextFieldComponent = function ($key, $enabled, $emptyForm) {
  var select = $("#" + $key);
  if (!this.isEmpty($enabled)) {
    if ($enabled === true) {
      select.removeAttr("disabled");
    } else {
      select.attr("disabled", "disabled");
      if (!this.isEmpty($emptyForm)) {
        this.componentTemplates.FormPanelComponent.updateField($emptyForm, $key);
      }
    }
  }
};
/**
 * M�todo que habilita o no un SelectFieldComponent
 * @param {String} $key        : Identificador del componente
 * @param {Boolean} $enabled   : Bandera que indica si se habilita o no el componente
 * @param {Boolean} $emptyForm : Bandera que indica si se limpiara o no el valor del modelo
 * @returns {void}
 */
Render.prototype.enabledSelectFieldComponent = function ($key, $enabled, $emptyForm) {
  var metadata = this.getWatch($key, "SelectFieldComponent");
  var select = $("#" + metadata.field.replace(/\./g, '_'));
  if (!this.isEmpty($enabled)) {
    if ($enabled === true) {
      select.removeAttr("disabled");
    } else {
      select.attr("disabled", "disabled");
      if (!this.isEmpty($emptyForm)) {
        this.componentTemplates.FormPanelComponent.updateField($emptyForm, $key);
      }
    }
  }
};
/**
 * M�todo que habilita o no un DatePickerFieldComponent
 * @param {String} $key        : Identificador del componente
 * @param {Boolean} $enabled   : Bandera que indica si se habilita o no el componente
 * @param {Boolean} $emptyForm : Bandera que indica si se limpiara o no el valor del modelo
 * @returns {void}
 */
Render.prototype.enabledDatePickerFieldComponent = function ($key, $enabled, $emptyForm) {
  var metadata = this.getWatch($key, "DatePickerFieldComponent");
  var component = $("#" + metadata.field.replace(/\./g, '_'));
  if (!this.isEmpty($enabled)) {
    if ($enabled === true) {
      component.removeProp("disabled");
    } else {
      component.prop("disabled", "disabled");
      if (!this.isEmpty($emptyForm)) {
        this.componentTemplates.FormPanelComponent.updateField($emptyForm, $key);
      }
    }
  }
};
/**
 * M�todo que habilita o no un DetailComponent
 * @param {String} $key        : Identificador del componente
 * @param {Boolean} $enabled   : Bandera que indica si se habilita o no el componente
 * @param {Boolean} $emptyForm : Bandera que indica si se limpiara o no el valor del modelo
 * @returns {void}
 */
Render.prototype.enabledDetailComponent = function ($key, $enabled, $emptyForm) {
  var component = $("#" + $key + "_link");
  if (!this.isEmpty($enabled)) {
    if ($enabled === true) {
      component.removeClass("disabled");
    } else {
      component.addClass("disabled");
      if (!this.isEmpty($emptyForm)) {
        this.componentTemplates.FormPanelComponent.updateField($emptyForm, $key);
      }
    }
  }
};
/**
 * M�todo que obtiene el valor de un elemento contenido en el modelo de datos, si no lo
 * encuentra regresa null
 * @param {String} $fieldName
 * @returns {String}
 */
Render.prototype.getValue = function ($fieldName) {
  if (this.module.controller.model) {
    if (this.module.controller.model[ $fieldName ]) {
      var value = this.module.controller.model[$fieldName];
      switch (true) {
        case isNaN(value):
          return this.module.controller.model[$fieldName];
        case (value.indexOf('.') > - 1):
          return parseFloat(this.module.controller.model[$fieldName]);
        default:
          return parseInt(this.module.controller.model[$fieldName]);
      }
    }
  }
  return null;
};
/**
 * M�todo que ingresa o actualiza un valor en el modelo de datos
 * @param {String} $fieldName
 * @param {Array:String|Number} $value
 * @returns {void}
 */
Render.prototype.setValue = function ($fieldName, $value) {
  if (this.module.controller.model) {
    this.module.controller.model[ $fieldName ] = $value;
    var component = $('#' + $fieldName);
    if (!this.isEmpty(component)) {
      switch (true) {
        case component.is('select'):
          this.notify("SelectFieldComponent", $fieldName, $value);
          break;
        case component.is('input'):
          var currentClass = component.attr('class');
          if (currentClass.indexOf('hasDatepicker') > -1) {
            this.notify("DatePickerFieldComponent", $fieldName, $value);
          } else {
            this.notify("TextFieldComponent", $fieldName, $value);
          }
          break;
        default:
          // -
          break;
      }
    } else {
      this.notify("HiddenComponent", $fieldName, $value);
    }
  }
};
/**
 * M�todo que agrega un listener a un campo, detonandose cuando el campo suscrito pierde
 * el foco
 * @param {String} $fieldName
 * @param {Function} $callback
 * @returns {void}
 */
Render.prototype.addEventListener = function ($metadata, $callback) {
  if (this.module.controller.model) {
    var _this = this;
    var component = this.componentTemplates[ $metadata.type ];
    if (component !== null) {
      if ($callback !== null) {
        this.triggerEvent($metadata.type, 'bind', $metadata.field, $callback);
      }
    }
  }
};
/**
 * M�todo que muestra una ventana de alerta
 * @param {String} $title
 * @param {String} $message
 * @returns {void}
 */
Render.prototype.showAlert = function ($title, $message) {
  showAlert($title, $message, null, null, "Aceptar");
};
/**
 * M�todo que se encarga de obtener el metadata del CardLayoutComponent actual
 * @returns {Object}
 */
Render.prototype.getCurrentMetadata = function () {
  var metadata = this.getWatch('card', "CardLayoutComponent");
  var currentCard = metadata.current;
  return metadata.cards[currentCard];
};
/**
 * M�todo que se encargar� de colocar un field como v�lido o no de acuerdo a
 * la bandera $isValid, de no ser v�lido pondr� el marco en rojo y el mensaje
 * de error.
 * 
 * @param {Object} $fieldMetadata
 * @param {Boolean} $isValid
 * @param {String} $message
 * @returns {void}
 */
Render.prototype.setValid = function ($fieldMetadata, $isValid, $message) {
  if ($isValid) {
    this.module.controller.model[$fieldMetadata.field] = $('#' + $fieldMetadata.field).val();
  }
  this.notify($fieldMetadata.type, $fieldMetadata.field, this.module.controller.model[$fieldMetadata.field]);
  this.triggerEvent($fieldMetadata.type, "validate", $fieldMetadata.field, {isValid: $isValid, message: $message});
};
/**
 * M�todo que realiza el cambio de rules para el momento de cambiar de pantalla
 * @param {String} $field
 * @param {Boolean} $required
 * @returns {void}
 */
Render.prototype.setRequired = function ($field, $required) {
  var metadata = this.getCurrentMetadata();
  //Pendiente implementar multiples formas
  var formName = metadata.cards[ metadata.current].name;
  var entities = this.metadata.model.entities;
  var i = 0;
  var entityAux = null;
  //Se busca la entidad
  for (i = 0; i < entities.length; i++) {
    if (entities[i].name === formName) {
      entityAux = entities[i];
      break;
    }
  }
  //Si no existe se crea la entidad
  if (entityAux === null) {
    entityAux = {name: formName, fields: []};
  }
  if ($required) {
    //si no existe la regla se agrega
    if (entityAux.fields.indexOf({name: $field, domain: "required"}) === -1) {
      entityAux.fields.push({name: $field, domain: "required"});
    }
  } else {
    //si existe la regla se elimina del arreglo
    var posicion = entityAux.fields.indexOf({name: $field, domain: "required"});
    if (posicion !== -1) {
      entityAux.fields.slice(posicion, 1);
    }
  }
  this.metadata.model.entities[i] = entityAux;
};
/**
 * M�todo que obtiene todas las formas
 * (nombre de la forma y de su modelo) 
 * existentes por componente
 * @param {componentTemplates} $element
 * @returns {formas[{nombre,modelo}]}
 */
Render.prototype.getForms = function ($element) {
  var typeComponent = $element.type;
  var forms = [];
  var i = 0;
  switch (typeComponent) {
    case 'FormPanelComponent':
      if ($element.managed === undefined || $element.managed === false) {
        aux = {name: $element.name, model: $element.model};
        forms = forms.concat(aux);
        for (i = 0; i < $element.components.length; i++) {
          aux = this.getForms($element.components[i]);
          if (aux !== null) {
            forms = forms.concat(aux);
          }
        }
        return forms;
      }
      return null;
    case 'PanelComponent':
      for (i = 0; i < $element.components.length; i++) {
        aux = this.getForms($element.components[i]);
        if (aux !== null) {
          forms = forms.concat(aux);
        }
      }
      return forms;
    case 'DetailComponent':
      if ($element.modal !== undefined && $element.modal !== null) {
        for (i = 0; i < $element.modal.body.components.length; i++) {
          aux = this.getForms($element.modal.body.components[i]);
          if (aux !== null) {
            forms = forms.concat(aux);
          }
        }
        return forms;
      }
      return null;
    case 'ModalComponent':
      for (i = 0; i < $element.components.length; i++) {
        aux = this.getForms($element.components[i]);
        if (aux !== null) {
          forms = forms.concat(aux);
        }
      }
      return forms;
    case 'HandlerCardLayoutComponent':
    case 'CardLayoutComponent':
      //En este momento se trabaja sobre el current card del handler y del card
      //De ser necesario hacerlo iterativo por cada card
      aux = this.getForms($element.cards[ $element.current ]);
      if (aux !== null) {
        forms = forms.concat(aux);
      }
      return forms;
    default:
      return null;
  }
};
/**
 * M�todo que realiza el cambio de un token por otro dentro de una cadena
 * @param {String} string
 * @param {String} token
 * @param {String} newtoken
 * @returns {void}
 */
Render.prototype.replaceAll = function (string, token, newtoken) {
  if (token != newtoken)
    while (string.indexOf(token) > -1) {
      string = string.replace(token, newtoken);
    }
  return string;
};

/*jshint -W061 */
function SelectFieldComponent( render ){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
SelectFieldComponent.prototype.draw = function (component) {
  var html = "";
  if( component.id === undefined){
    component.id = component.field;
  }
  if( component.attribute === "hide"){
   html += "<input type='hidden' name='"+ component.field +"'/>"; 
  }else{
    if( component.attribute === "readOnly"){
      component.readOnly = "true";
      component.disabled = true;
    }
    html = '<div class="form-group">';
    if( !this.render.isEmpty(component.label) ){
      html +='<label for="' + component.id + '" class="">' + this.render.nvl(component.label, "") + '</label>';
    }
    html +='<div class="input-group col-md-12">';
    html += '<select id="' + this.render.toId(component.id);
    html += '" class="form-control select-style ';
    if( !this.render.isEmpty(component.className) ){
      html += component.className;
    }
    html +='"  name="' + component.field + '"   ';
    if( !this.render.isEmpty(component.disabled) ){
        if( component.disabled === "true" || component.disabled === true ){
            html += ' disabled ';
        }
    }
    if( !this.render.isEmpty(component.style) ){
        html += ' style="' + component.style + '" ';
    }
    if( !this.render.isEmpty(component.onchange) ){
        html += ' onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    if( !this.render.isEmpty(component.onChangeEvent) ){
        html += ' onchange="' + this.render.drawTriggerEvent(component.onChangeEvent.type,component.onChangeEvent.event, component.onChangeEvent.key, component.onChangeEvent.model ) + '" ';
    }
    html += ((component.readOnly !== null && component.readOnly === "true") ? ' onclick="return false;" readonly="readonly" ' : '');
    html += '>';
    if( !this.render.isEmpty( component.elements) ){
      for( var i=0; i<component.elements.length; i++ ){
        html += '<option value="'+component.elements[i].value+'">'+component.elements[i].label+'</option>';
      }
    }
    html += '</select>';
    if (!this.render.isEmpty(component.popover)) {
        html += '<div class="input-group-addon"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';

        
    }
    html += '</div></div>';
    if( this.render.isEmpty( component.elements) ){
      this.render.addLinker(new Link(component.field, "SelectFieldComponent", component, 1));
    }
    //this.render.module.controller.model[component.id] = component.elements;
  }  
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
SelectFieldComponent.prototype.digest = function (link) {
    eval(this.render.module.instanceName + ".service.combos('" + link.metadata.data + "','" + link.metadata.id + "', " + this.render.module.instanceName + ")");
    if (!this.render.isEmpty(link.metadata.onchange)) {
        var select = $("#" + link.metadata.field.replace(/\./g, '_'));
        var _this = this;
        select.change(function () {
            var js = _this.render.drawCommand(link.metadata.onchange);
            eval(js.replace('()', '("' + this.value + '", "' + select.closest('form').attr('id') + '")'));
        });
    }
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
SelectFieldComponent.prototype.notify = function (metadata, model) {
    if( !this.render.isEmpty( metadata.field ) ){
        var select = $("#" + this.render.toId(metadata.id));
        var options = null;
        var that = this;
        if (select.prop) {
                options = select.prop('options');
        } else {
                options = select.attr('options');
        }
        if (select !== null && select.length === 1) {
            var newOptions = model;
            //var newOptions = this.render.module.controller.model[model];
            $('option', select).remove();
            if (newOptions !== null) {              
                //options[options.length] = new Option("--Seleccione--",0);
                $.each(newOptions, function (index, option) {
                  if( !that.render.isEmpty(option.key)){
                    options[options.length] = new Option(option.value, option.key);
                  }else{
                    options[options.length] = new Option(option.label, option.value);
                  }
                });
                select[0].selectedIndex = 0;
            }
            select.val();
            select.change();
            
        }
    }
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
SelectFieldComponent.prototype.getValue = function( $key ){
    return $('#' + $key).val();
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
SelectFieldComponent.prototype.setValue = function( $key, $value ){
    var component = $("#" + $key);
    if( component ){
        var options = null;
        if ( component.prop ) {
            options = component.prop('options');
        } else {
            options = component.attr('options');
        }
        if (options !== null) {
            var selectedIndex = 0;
            $.each(options, function (index, option) {
                if(option.value === $value){
                    component[0].selectedIndex = selectedIndex;
                }
                selectedIndex++;
            });
        }
        this.format($key);
    }
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
SelectFieldComponent.prototype.format = function( $key ){
    //-
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
SelectFieldComponent.prototype.triggerEvent = function ($event, $key, $model) {
    switch( $event ){
        case 'validate':
            this.refresh( $key, $model );
            break;
        case 'bind':
            this.bind( $key, $model );
            break;    
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'validate'
 * @param {Object} link : metadata
 * @returns {void}
 */
SelectFieldComponent.prototype.refresh = function( $key, $data ){
    var textField = $('#' + $key);
    if ( textField ) {
        if( $data.isValid === true ){
            textField.parent().parent().removeClass('has-error');
        } else {
            textField.parent().parent().addClass('has-error');
            if( $data.message.length > 0 ){
                this.render.showAlert('', $data.message);
            }
        }
    }
};
/**
 * M�todo que suscribe el evento focusout e invoca el callback suscrito
 * @param {String} $key
 * @param {Function} $callback
 * @returns {void}
 */
SelectFieldComponent.prototype.bind = function( $key, $callback ){
    var _this = this;
    var component = $('#' + $key);
    component.focusout( function( $event ) {
        $callback( _this.getValue( $key ), _this.render.module.controller.model );
    });
};

function ButtonGroupComponent( render ){
    this.render = render;
}
ButtonGroupComponent.prototype.draw = function (component) {
  this.component = component;  
  var html = '<div id="buttongroup_' + component.id + '" >';
  html += this.drawBody(this.component);  
  html += '</div>';
  return html;
};
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
ButtonGroupComponent.prototype.drawBody = function (component) {
    var html = "<div ";
    
    if( this.render.isEmpty(component.className) ){
      html +=" class='pull-right' ";
    }else{
      html +=" class='"+component.className+"' ";
    }
    
    if( !this.render.isEmpty(component.style) ){
      html +=" style='"+component.style+"' ";
    }
    
    html +=">";
    
    var enabled = this.render.module.controller.model[this.component.id];
    var separator = "<span> </span>";
    if( !this.render.isEmpty(component.orientation) &&  component.orientation === "vertical"){
      separator = "<p> </p>";
    }
    
    for( var i=0; i< component.components.length; i++){
      var element = this.render.componentTemplates[component.components[i].type];
      if( element !== undefined ){
        if( !this.render.isEmpty( enabled ) &&  !this.render.isEmpty( enabled[i] ) ){
          component.components[i].disabled = enabled[i];
        }
        if( component.components[i].disabled !== true ){
          html += element.draw(component.components[i]);        
          html += separator;
        }
      }
    }
    html += "</div>";
    return html;
};
ButtonGroupComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata;        
    var html = this.drawBody(this.component);
    $("#buttongroup_" + this.component.id).html( html );
};


/*jshint -W061 */
function FormPanelComponent( render ){
    this.render = render;
}

FormPanelComponent.prototype.notify = function (metadata, modelName) {
  
  if(!this.render.isEmpty( metadata ) ){
    var formId = this.render.toId("FormPanelComponent-"+metadata.name);
    if(!this.render.isEmpty( document.forms[formId] )){
      document.forms[formId].reset();
      this.render.modelToForm(formId,
        this.render.getModel(metadata.model) );
    }
    if( !this.render.isEmpty(metadata.postFetch ) ){
      eval( this.render.module.instanceName + ".controller." + metadata.postFetch + "()" );
    }
  }
};
/**
 * M�todo que adjunta las reglas de la forma
 * @param {Object} metadata
 * @returns {Object} config
 */
FormPanelComponent.prototype.rules = function (metadata) {
    var disabled = {
        add:    metadata.state != "browse" ,
        update: metadata.state != "browse" || metadata.size === 0,
        save:   metadata.state != "add" && metadata.state != "edit",
        remove: metadata.state != "browse" || metadata.size === 0,
        cancel: metadata.state != "add" && metadata.state != "edit",
        first:  metadata.current=== 0 || metadata.state != "browse",
        back:   metadata.current=== 0 || metadata.state != "browse",
        next:   metadata.size -1 <= metadata.current || metadata.state != "browse",
        last:   metadata.size -1 <= metadata.current || metadata.state != "browse"
    };
    return disabled;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
FormPanelComponent.prototype.digest = function (link) {
  var id = this.render.toId("FormPanelComponent-"+link.metadata.name);    
    this.render.modelToForm( id ,    
    this.render.getModel(link.metadata.model));
  if( !this.render.isEmpty(link.metadata.postFetch ) ){
    eval( this.render.module.instanceName + ".controller." + link.metadata.postFetch + "()" );
  }
};
/**
 * M�todo que buscar en el modelo si hay datos que mostar en la forma
 * @param {Object} metadata
 * @returns {void}
 */
FormPanelComponent.prototype.fetch = function (metadata) {
    var isManagerForm = this.render.isEmpty( metadata.managed ) ? false : metadata.managed;
    if ( this.render.module.controller[ metadata.model ] === null ){
        this.render.module.controller[ metadata.model ] = {};
        if( isManagerForm ){
            this.render.module.controller[ metadata.model ][ metadata.name ] = [];
        }
    }
    var model = isManagerForm ? this.render.module.controller[ metadata.model ][ metadata.name ] : this.render.module.controller[ metadata.model ];
    if( !this.render.isEmpty( model ) ){
        if( model.length !== undefined ){
            if( model.length > metadata.current ){
                this.render.modelToForm( metadata.formId, model[ metadata.current ] );
            }
            metadata.size = model.length;
        } else {
            this.render.modelToForm( metadata.formId, model );
        }
        this.render.addWatch(new Watch( metadata.formId, "FormPanelComponent", metadata ) );
        this.repaintNavigationPanel( metadata );
    }
};
/**
 * M�todo que redibuja la barra de navegacion superior
 * @param {Object} metadata
 * @returns {void}
 */
FormPanelComponent.prototype.repaintNavigationPanel = function (metadata) {
    var panel = new PanelComponent(this.render);
    var html = panel.draw( this.getNavigationButton( metadata) );  
    $("#nbFormPanel-"+metadata.formId).html(html);
};
/**
 * M�todo que redibuja la barra de control
 * @param {Object} metadata
 * @returns {void}
 */
FormPanelComponent.prototype.repaintToolBarButton = function (metadata) {
    var panel = new PanelComponent(this.render);
    var html = panel.draw( this.getToolBarButton( metadata ) );
    $( "#tbbFormPanel-" + metadata.formId ).html( html );
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.triggerEvent = function (event, key, model) {
    switch( event ){
        case 'first'      : this.first(key,model);  break;
        case 'last'       : this.last(key,model);   break;
        case 'next'       : this.next(key,model);   break;
        case 'back'       : this.back(key,model);   break;
        case 'update'     : this.update(key,model); break;
        case 'updateField': this.updateField(key, model); break;
        case 'add'        : this.add(key,model);    break;
        case 'cancel'     : this.cancel(key,model); break;
        case 'save'       : this.save(key,model);   break;
        case 'remove'     : this.remove(key,model); break;
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'first'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.first = function (key,modelo) { 
    var metadata = this.render.getWatch( key , "FormPanelComponent" );
    metadata.current=0;
    this.fetch(metadata);
};
/**
 * M�todo que procesa el evento capturado de tipo 'last'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.last = function (key,modelo) { 
    var metadata = this.render.getWatch( key , "FormPanelComponent" );  
    metadata.current=metadata.size-1;
    this.fetch(metadata);
};
/**
 * M�todo que procesa el evento capturado de tipo 'next'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.next = function (key,modelo) { 
    var metadata = this.render.getWatch( key , "FormPanelComponent" );  
    if( metadata.current < metadata.size ){
        metadata.current++;
        this.fetch(metadata);
    }  
};
/**
 * M�todo que procesa el evento capturado de tipo 'back'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.back = function (key,modelo) { 
    var metadata = this.render.getWatch( key , "FormPanelComponent" );  
    if( metadata.current > 0 ){
        metadata.current--;
        this.fetch(metadata);
    }  
};
/**
 * M�todo que procesa el evento capturado de tipo 'update'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.update = function (key,model) {
    var metadata = this.render.getWatch( key , "FormPanelComponent" );
    metadata.state = "edit";
    this.repaintToolBarButton(metadata);     
    this.repaintNavigationPanel(metadata);
    this.render.addWatch(new Watch(metadata.formId, "FormPanelComponent", metadata));
};
/**
 * M�todo que modifica el valor de un field contenido en la forma
 * @param {String} formId : FormPanelComponent-fieldName
 * @param {String} field
 * @param {String|Number} value
 * @returns {void}
 */
FormPanelComponent.prototype.modelToFormField = function (formId, field, value) {
    var matches = $.find("#" + formId + " :input[name='" + field + "']");
    if (matches.length == 1) {
        $("#" + formId + " :input[name='" + field + "']").val(value);
        $("#" + formId + " :input[name='" + field + "']").change();
        if (matches[0].type == "select-one") {
            /* if (matches[0].options[matches[0].selectedIndex] !== null) {
             $("#" + matches[0].id + "_input_cb").val(matches[0].options[matches[0].selectedIndex].text);
             }*/
        } else if ( matches[0].type == "checkbox" ) {
            if (value == "on") {
                $("#" + matches[0].id).prop('checked', true);
            } else if (value === true) {
                $("#" + matches[0].id).prop('checked', true);
            }
        }
    } else {
        // Puede ser radio
        $("#" + formId + " :input[name='" + field + "'][value='" + value + "']").prop('checked', true);
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'updateField'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.updateField = function (key, field) {
    var metadata = this.render.getWatch("FormPanelComponent-" + key, "FormPanelComponent");
    var model = this.render.module.controller[ metadata.model ][ metadata.name ];
    if (!this.render.isEmpty(model)) {
        if (model.length > metadata.current) {
            this.modelToFormField(metadata.formId, field, model[metadata.current][field]);
        } else {
            this.modelToFormField(metadata.formId, field, model[field]);
        }
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'add'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.add = function (key,model) {
    $("#"+key)[0].reset();
    var metadata = this.render.getWatch( key , "FormPanelComponent" );
    metadata.state = "add";
    this.repaintToolBarButton(metadata);
    this.repaintNavigationPanel(metadata);
    this.render.addWatch(new Watch(metadata.formId, "FormPanelComponent", metadata));
};
/**
 * M�todo que procesa el evento capturado de tipo 'cancel'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.cancel = function (key,model) {  
    var metadata = this.render.getWatch( key , "FormPanelComponent" );
    metadata.state = "browse";
    this.repaintToolBarButton(metadata);
    this.fetch(metadata);
};
/**
 * M�todo que procesa el evento capturado de tipo 'save'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.save = function (key, modelo) {
    var metadata = this.render.getWatch(key, "FormPanelComponent");
    var index = metadata.current;
    if (this.render.module.validator.validForm(metadata)) {
        if (metadata.state === "add") {
            index = metadata.size;
        }
        var row = {};
        this.render.module.validator.formToModel(metadata, row);
        if ( this.render.module.controller[ metadata.model ][ metadata.name ] === null || this.render.module.controller[ metadata.model ][ metadata.name ] === undefined ){
            this.render.module.controller[ metadata.model ][ metadata.name ] = [];
        }
        var model = this.render.module.controller[ metadata.model ][ metadata.name ];
        model[ index ] = row;
        metadata.state = "browse";
        this.repaintToolBarButton(metadata);
        this.fetch(metadata);
        if (!this.render.isEmpty(metadata.afterSaveTrigger)) {
            var jsEvent = this.render.drawTriggerEvent(metadata.afterSaveTrigger.type, metadata.afterSaveTrigger.event, key, metadata.afterSaveTrigger.model);
            eval(jsEvent);
        }
        if (!this.render.isEmpty(metadata.afterSaveCommand)) {
            var jsCommand = this.render.drawCommand(metadata.afterSaveCommand);
            eval(jsCommand);
        }
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'remove'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
FormPanelComponent.prototype.remove = function (key, modelo) {
    var metadata = this.render.getWatch(key, "FormPanelComponent");
    var model = this.render.module.controller[ metadata.model ][ metadata.name ];
    model.splice(metadata.current, 1);
    $("#" + key)[0].reset();
    metadata.state = "browse";
    metadata.size = model.length;
    metadata.current = 0;
    this.repaintToolBarButton( metadata );
    this.fetch( metadata );
    if ( !this.render.isEmpty( metadata.afterSaveTrigger ) ) {
        var jsEvent = this.render.drawTriggerEvent(metadata.afterSaveTrigger.type, metadata.afterSaveTrigger.event, key, metadata.afterSaveTrigger.model);
        eval(jsEvent);
    }
    if ( !this.render.isEmpty( metadata.afterSaveCommand ) ) {
        var jsCommand = this.render.drawCommand(metadata.afterSaveCommand);
        eval(jsCommand);
    }
};
/**
 * M�todo que se encarga de generar el metadata de la barra de botones que
 * modifican el comportamiento de la forma administrada
 * @param {Object} component
 * @returns {Object} metadata
 */
FormPanelComponent.prototype.getToolBarButton = function (component) {
    var disabled = this.rules(component);
    var toolBar = {
        type: "PanelComponent",
        id: "tbbFormPanel-"+component.formId,
        components: [ 
            {type:"ButtonComponent", label:"Agregar", icon:"plus", trigger:{ type:"FormPanelComponent", event:"add", key: component.formId, model: component.model }, disabled: disabled.add   },
            {type:"ButtonComponent", label:"Modificar",icon:"pencil" , trigger:{ type:"FormPanelComponent", event:"update", key: component.formId, model: component.model }, disabled: disabled.update },
            {type:"ButtonComponent", label:"Guardar",icon:"download-alt", trigger:{ type:"FormPanelComponent", event:"save", key: component.formId, model: component.model }, disabled: disabled.save  },
            {type:"ButtonComponent", label:"Eliminar",icon:"minus", trigger:{ type:"FormPanelComponent", event:"remove", key: component.formId, model: component.model }, disabled: disabled.remove },
            {type:"ButtonComponent", label:"Cancelar",icon:"ban-circle", trigger:{ type:"FormPanelComponent", event:"cancel", key: component.formId, model: component.model }, disabled: disabled.cancel}
        ],
        layout: [ [{span:2},{span:2},{span:2},{span:2},{span:2} ] ]
    };  
    return toolBar;
};
/**
 * M�todo que se encarga de generar la barra de navegaci�n entre los registos
 * de la forma administrada
 * @param {Object} component
 * @returns {Object} metadata
 */
FormPanelComponent.prototype.getNavigationButton = function (component) {
    var disabled = this.rules(component);
    var toolBar = {
        type: "PanelComponent",
        id: "nbFormPanel-"+component.formId,
        components: [
            {type:"ButtonComponent", disabled: disabled.first, label:"",icon:"fast-backward", trigger:{ type:"FormPanelComponent", event:"first", key: component.formId, model: component.model }},
            {type:"ButtonComponent", disabled: disabled.back, label:"",icon:"step-backward", trigger:{ type:"FormPanelComponent", event:"back", key: component.formId, model: component.model }},
            {type:"LabelComponent",  className: "text-center",label: "Registros "+ (component.current+1)%(component.size+1) +" de " + component.size },
            {type:"ButtonComponent", disabled: disabled.next, label:"",icon:"step-forward", trigger:{ type:"FormPanelComponent", event:"next", key: component.formId, model: component.model }},
            {type:"ButtonComponent", disabled: disabled.last, label:"",icon:"fast-forward", trigger:{ type:"FormPanelComponent", event:"last", key: component.formId, model: component.model }}
        ],
        layout: [ [{span:1},{span:1},{span:2},{span:1},{span:1} ] ]
    };
    return toolBar;
};
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
FormPanelComponent.prototype.draw = function (component) {
    var id = this.render.toId( "FormPanelComponent-" + component.name );
    if( component.className === undefined ){
      component.className = "form";
    }
    if( !this.render.isEmpty(component.horizontal) ){
      component.className = "form-horizontal";
      this.render.horizontal = component.horizontal;
    }
    if( !this.render.isEmpty(component.entity) ){      
      this.render.entity = component.entity;
    }
    
    var html = "<div ><form id='" + id + "' name='" + component.name + "' class='"+component.className+"'";
    if (!this.render.isEmpty(component.style)) {
      html+= " style='"+component.style+"' ";
    }
    html += "><fieldset>";
    component.formId = id;
    if (this.render.isEmpty(component.state)) {
        component.state = "browse";
    }
    var panel = new PanelComponent(this.render);
    // FIX
    if (component.managed === true) {
        component.size = 0;
        component.current = 0;
        if (this.render.isEmpty(component.rendered)) {
            //component.components.splice(0,0, this.getToolBarButton( component ) );      
            component.components[component.components.length] = this.getToolBarButton(component);
            component.components[component.components.length] = this.getNavigationButton(component);
            //component.layout.splice(0,0, [ {span:12} ] );
            component.layout[component.layout.length] = [{span: 12}];
            component.layout[component.layout.length] = [{span: 12}];
            component.rendered = true;
        }
    }
    html += panel.draw(component);
    html += "</fieldset></form>";
    html += "</div>";
    this.render.horizontal = undefined;
    this.render.entity = undefined;
    this.render.addWatch(new Watch(id, "FormPanelComponent", component));
    this.render.addLinker(new Link(id, "FormPanelComponent", component, 10));
    // Registrar la forma al contenedor si existe
    if ( !this.render.isEmpty( this.render.idToolBarPanel ) ) {
        this.render.addWatch(new Watch(this.idToolBarPanel + "-" + (this.render.counter++), "formValidator", component));
    }
    return html;
};

function TabPanelComponent(render){
  this.render = render;
}
TabPanelComponent.prototype.draw = function( component ){
  this.component = component; 
  var html = "";
  html += "<div id='tabpanel_" + this.component.id + "'>";
  html += this.drawBody(); 
  html +="</div>";   
  return html;  
};

TabPanelComponent.prototype.notify = function (metadata,value) {
  this.component = metadata;        
  var html = this.drawBody();
  $("#tabpanel_" + this.component.id).html( html );
};


TabPanelComponent.prototype.drawBody = function (){
  var i=0;
  var html = "";
  var id = this.render.toId( this.component.id);
  html +='<ul class="nav nav-tabs nav-justified" id="tabs_'+ id +'" >';
  this.component.components = this.component.tabs;    
  for(i=0; i<this.component.tabs.length; i++){
    html += "<li  class='"+(i===0?" active ": "")+" '   id='li-tabs-"+id+"-"+i+"' ><a id='a-tabs-"+id+"-"+i+"' data-toggle='tab' onclick='";
    html += this.render.module.instanceName + ".controller.model.tabs_" +id + "=" + i+ ";' ";    
    html += "   href='#tabs-"+id+"-"+i+"'>"+this.render.nvl(this.component.tabs[i].title, "")+"</a></li>";
  }      
  html +="  </ul>";
  html += "<div class='tab-content'>";
  for(i=0; i<this.component.tabs.length; i++){
    html += "<div class='tab-pane fade "+(i===0?" in active ": "")+"' id='tabs-"+id+"-"+i+"'>";
    var panel = new PanelComponent( this.render );
    html += panel.draw( this.component.tabs[i].panel );
    this.component.tabs[i].components = [ this.component.tabs[i].panel ];
    this.component.tabs[i].layout =[[{span:12}]];
    html +='</div>';
  }
  html += "</div>";
  return html;
};




/*jshint -W061 */
function Validator( module){
    this.module = module;
}

Validator.prototype.upload = function( idFileElement ){
  
  if( $("#" + idFileElement).val() !== '' ){
  
  
  var formData = new FormData();
      //formData.set("encoding","multipart/form-data");
      var fileInputElement = $("#" + idFileElement);
      var index;
      
      var fileSize = 0;
      var maxFileSize = this.module.controller.model.system.fileSize;
      if( maxFileSize === undefined ){
        maxFileSize = 1048576;  // 1M
      }
      // Validar tamanio maximo
      for( index=0; index<fileInputElement[0].files.length; index++ ){
        fileSize += fileInputElement[0].file[index].size;        
      }
      
      if( fileSize > maxFileSize ){
        var message = "El tama\u00F1o m\u00E1ximo es de carga es de " + maxFileSize + " bytes y selecciono archivos de " + fileSize + " bytes.";        
        $("#" + idFileElement).parent().addClass("has-error");
        $("#" + idFileElement).next().html("<span for='"+idFileElement+"' class='label label-danger' >"+message+"</span>");
        //$("#" + idFileElement).val('-');
        return;
      }
      
      for( index=0; index<fileInputElement[0].files.length; index++ ){
        formData.append("files", fileInputElement[0].files[index] );
      }
    
    var validator = this;
    // Ajax call for file uploaling
    var ajaxReq = $.ajax({
      url : '/banorte/fileUpload/save',
      type : 'POST',
      data : formData,
      cache : false,
      contentType : false,
      processData : false,
      xhr: function(){
        //Get XmlHttpRequest object
         var xhr = $.ajaxSettings.xhr() ;
        
        //Set onprogress event handler 
         xhr.upload.onprogress = function(event){
          	var perc = Math.round((event.loaded / event.total) * 100);
          	$('#progressBar-'+idFileElement).text(perc + '%');
          	$('#progressBar-'+idFileElement).css('width',perc + '%');
         };
         return xhr ;
    	},
    	beforeSend: function( xhr ) {
    		//Reset alert message and progress bar
    		//$('#alertMsg').text('');
    		$('#progressBar-'+idFileElement).text('');
    		$('#progressBar-'+idFileElement).css('width','0%');
              }
    });
  
    // Called on success of file upload
    ajaxReq.done(function(msg) {
      var document = msg;
      //// // // console.log(document);
      //$('#alertMsg').text(msg);
      //$("#" + idFileElement).val('-');
      $('#progressBar-'+idFileElement).text('');
      $('#progressBar-'+idFileElement).css('width','0%');
      var actual = validator.module.controller.model[idFileElement];      
      if( Array.isArray( actual ) ){
        for(var index=0; index < document.length ; index ++){
          actual[actual.length] = document[index];
        }
        if( validator.module.controller.model.data !== undefined ){
          validator.module.controller.model.data[idFileElement] = actual;
        }
        validator.module.updateModel("FileFieldComponent", idFileElement, actual );
      }else{
        if( validator.module.controller.model.data !== undefined ){
          validator.module.controller.model.data[idFileElement] = document;
        }
        validator.module.updateModel("FileFieldComponent", idFileElement, document );
      }       
      
    });
    
    // Called on failure of file upload
    ajaxReq.fail(function(jqXHR) {
      //$('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+' - '+jqXHR.statusText+')');
      //$('button[type=submit]').prop('disabled',false);
    });
    
  }
};

/**
 * M�todo que valida si los campos de la forma son v�lidos
 * @param {String} formName
 * @param {Object|String|Number} model
 * @returns {Boolean}
 */
Validator.prototype.validFormPanel = function( formName, model){
    var valid = true;
    // Encontrar el id del formulario
    var idFormPanel = null;
    for( var i=0; i< this.module.render.watchers.length; i++ ){
        if( this.module.render.watchers[i].field == formName && this.module.render.watchers[i].type == "FormPanelComponent" ){
            idFormPanel = this.module.render.watchers[i].id;
            var component = {};
            component.formId = idFormPanel;
            model[ formName ] = this.formToModel( component, model[ formName ] );
            //break;
        }
    }
    return valid;
};
/**
 * M�todo que valida un toolbarpanel
 * @param {type} toolBarPanelName
 * @param {type} model
 * @param {type} onlyData
 * @returns {Boolean}
 */
Validator.prototype.validToolBarPanel = function( toolBarPanelName, model, onlyData ){
    if( onlyData === undefined || onlyData === null ){
        onlyData = false;
    }
    // Obtener todas los FormPanels dentro del ToolBar
    // ? GlobalName in the render?
    var valid = true;
    var i;
    // Encontrar el id del toolbarpanel
    var idToolBarPanel = null;
    for( i=0; i< this.module.render.watchers.length; i++ ){
        if( this.module.render.watchers[i].field.title == toolBarPanelName && this.module.render.watchers[i].type == "toolBarPanel" ){
            idToolBarPanel = this.module.render.watchers[i].id;
            break;
        }
    }
    // Buscar las formas asociadas a este ID.
    if( idToolBarPanel !== null ){
        for( i=0; i< this.module.render.watchers.length; i++ ){
            if( ( this.module.render.watchers[i].id.lastIndexOf(idToolBarPanel, 0) === 0  ) && this.module.render.watchers[i].type == "formValidator" ){
                if( onlyData || this.validForm( this.module.render.watchers[i].field ) ){
                    model[ this.module.render.watchers[i].field.name ] = this.formToModel( this.module.render.watchers[i].field, model[ this.module.render.watchers[i].field.name ] );
                }else{
                    valid = false;
                }
            }
        }
    }
    return valid;
};
/**
 * M�todo que pasa los datos de la forma al modelo de datos que se pase en el 
 * argumento
 * @param {Object} component
 * @param {Object|String|Number} model
 * @returns {void}
 */
Validator.prototype.formToModel = function( idForma, model ){
    if (model === null || model === undefined) {
        model = {};
    }
    var data = $("#" + idForma ).serializeArray();
    // Esta funcion no regresa los checkbox en falso, por lo que
    // hay que identificar cuantos checks tiene la forma y no estan
    // seleccionados para marcarlos en el model.
    data = data.concat(
            jQuery('#'+idForma+' input[type=checkbox]:not(:checked)').map(
                    function() {
                        return {"name": this.name, "value": 'false'};
                    }).get()
    );
    
    // Incluimos el soporte para datos deshabilitados
    data = data.concat(
            jQuery('#'+idForma+' :disabled').map(
                    function() {
                      if( this.type !== "radio" && ( this.type!=="checkbox"  && this.checked === false ) ){
                        return {"name": this.name, "value": this.value};
                      }else{
                        return {"name":"", "value":""};
                      }
                    }).get()
    );
    // // // console.log( "Forma: " + JSON.stringify( data ) );
    var _this = this;
    if( data !== null && data.length > 0 ){
        $.each( data, function(){ 
            //model[ this.name] = this.value; 
              if( this.name !== ""){
                _this.addAtributeToObject( model, this.name, this.value );
              }
            }
        );
    }
    //// // // console.log("FormToModel: ");
    //// // // console.log(model);
    return model;
};
/**
 * M�todo que agrega un atibuto a un objeto, crear la estructura si no existe
 * @param {Object} obj
 * @param {String} attribute
 * @param {?} value
 * @returns {void}
 */
Validator.prototype.addAtributeToObject = function( obj, attribute, value ){
    ////// // // console.log("FormToModel, " );
    ////// // // console.log(obj );
    ////// // // console.log( attribute );
    var names = attribute.split(".");
    var reference = obj;
    for(var i=0; i< names.length; i++){ 
      ////// // // console.log("reference");
      ////// // // console.log(reference);
      //Validamos si es un arreglo
      var arreglo = names[i].split("[");
      if( arreglo.length > 1 ){
        if( !Array.isArray( reference[arreglo[0]] ) ){ 
          reference[arreglo[0]] = [];
        }
        var aux;
        eval( "aux = reference." + names[i] );
        if( aux === undefined ){
          eval( "reference." + names[i] + "={}" );
        }
        eval( "reference = reference." + names[i] );
      }else{      
        // Crear la estructura de objeto
        if( reference[names[i]] === null || reference[names[i]] === undefined){
            reference[names[i]] = {};
        }
        reference = reference[names[i]];
      }
      
    }
    ////// // // console.log("FormToModel, " + "obj."+attribute+"=value");
    //Asignar el valor, attribute puede ser una estructura
    eval( "obj."+attribute+"=value");
};
/**
 * M�todo que valida la forma
 * @param {Object} component
 * @returns {Boolean}
 */
Validator.prototype.validForm = function( component ){
    var entityName = component.entity;
    var entity = this.getEntity( entityName );
    var validatorAux = new Validator( this.module );
    validatorAux.prepare ( entity );    
    var data = $.find("#FormPanelComponent-" + component.name );    
    if( data.length ===1 ){  
        $( "#FormPanelComponent-" + component.name ).validate({
            errorElement: 'span',
            errorClass: "label label-danger",
            highlight: function(element) {
                $(element).parent().addClass("has-error");
            },
            unhighlight: function(element) {
                $(element).parent().removeClass("has-error");
            },
            rules: validatorAux.rules,
            messages: validatorAux.messages
        });
        return $("#FormPanelComponent-" + component.name ).valid();
    } 
    return true;
};
/**
 * Metodo que obtiene la entidad definida en el metadata
 * @param {String} entityName
 * @returns {Object}
 */
Validator.prototype.getEntity = function( entityName ){
    if( !this.module.render.isEmpty( this.module.metadata.model ) && !this.module.render.isEmpty( this.module.metadata.model.entities )  ){
        var entities = this.module.metadata.model.entities;
        for(var i=0; i<entities.length; i++){
            if( entities[i].name === entityName ){
                return entities[i];
            }
        }
    }
    return {};
};
/**
 * 
 * @param {Object} entity
 * @returns {void}
 */
Validator.prototype.prepare = function( entity ){
    this.rules = {};
    this.messages = {};
    if( !this.module.render.isEmpty( entity.fields )  ){
        for(var i=0; i<entity.fields.length; i++ ){
            if( entity.fields[i].domain !== null ){
                var ruleName = entity.fields[i].name;
                this.rules[ ruleName ] = this.getRulesFromDomain( entity.fields[i].domain );
                this.messages[ ruleName ] = this.getMessagesFromDomain( entity.fields[i].domain );
            }
        }
    }
    //// // // console.log( this.rules );
};
/**
 * M�todo que obtiene los mensages que se tienen definidos en el metadata de
 * cada uno de los componentes que integran la forman
 * @param {Array} domain
 * @returns {Object}
 */
Validator.prototype.getMessagesFromDomain = function( domain ){
    var domains = this.module.metadata.model.domains;
    for(var i=0; i<domains.length; i++){
        if( domains[i].name == domain ){
            var messages = {};
            for(var j=0; j<domains[i].rules.length; j++){
                this.prepareMessage( messages, domains[i].rules[j] );
            }
            return messages;
        }
    }
    return {};
};
/**
 * M�todo que prepara el mensaje a desplegar
 * @param {Object} messages
 * @param {String} rule
 * @returns {void}
 */
Validator.prototype.prepareMessage = function( messages, rule ){
    switch(rule.type){
        case "Required":
            if( rule.message !== null ){
                //messages.required = $.i18n.prop(rule.message);
                messages.required = rule.message;
            }else{
                //messages.required = $.i18n.prop("required");
                messages.required = "Campo requerido";
            }
            break;
        case "Number":
            if( rule.message !== null ){
                //messages.number = $.i18n.prop(rule.message);
                messages.number = rule.message;
            }else{
                //messages.number = $.i18n.prop("number");
                messages.number = "Numero requerido";
            }
        break;
        case "Regex":
            if( rule.message !== null ){                
                messages.regex = rule.message;
            }else{                
                messages.regex = "Formato incorrecto";
            }
        break;
        case "Length":
            if( rule.message !== null ){                
                messages.lengthRule = rule.message;
            }else{                
                messages.lengthRule = "Longuitud incorrecta";
            }
        break;
        case "Custom":
            if( rule.message !== null ){                
              messages[rule.name] = rule.message;
            }else{                
              messages[rule.name] = "Mensaje no definido";
            }
        break;
        case "LessThanToday":
            if( rule.message !== null ){                
              messages.lessThanToday = rule.message;
            }else{                
              messages.lessThanToday = "Mensaje no definido";
            }
        break;
    }
};
/**
 * M�todo que obtiene la definicion de las reglas definidas en el metadata(Domain)
 * @param {String} domain
 * @returns {Array}
 */
Validator.prototype.getDefinitionRulesFromDomain = function (domain){
    var domains = this.module.metadata.model.domains;
    for(var i=0; i<domains.length; i++){
        if( domains[i].name == domain ){
            return domains[i].rules;
        }
    }
    return [];
};
/**
 * M�todo que obtiene las reglas definidas en el metadata(Domain)
 * @param {String} domain
 * @returns {Object}
 */
Validator.prototype.getRulesFromDomain = function( domain ){
    var domains = this.module.metadata.model.domains;
    for(var i=0; i<domains.length; i++){
        if( domains[i].name === domain ){
            var rules = {};
            for(var j=0; j<domains[i].rules.length; j++){
                this.prepareRule( rules, domains[i].rules[j] );
            }
            return rules;
        }
    }
    return {};
};
/**
 * M�todo que prepara las reglas
 * @param {Object} rules
 * @param {Object} rule
 * @returns {void}
 */
Validator.prototype.prepareRule = function( rules, rule ){
    switch(rule.type){
        case "Required":
            rules.required = true;
            break;
        case "Number":
            rules.number = true;
            break;
        case "Regex":
            rules.regex = this.getParamValue(rule.params, "regex");
            break;
        case "Length":
            rules.lengthRule = { 
                min: this.getParamValue(rule.params, "min"),
                max:this.getParamValue(rule.params, "max")
              };
            break;
        case "Custom":
          rules[rule.name] = { params: rule.params };
          break;
        case "LessThanToday":
          rules.lessThanToday = true;
          break;
        
    }
};
/**
 * M�todo que obtiene el valor del par�metro
 * @param {Array} params
 * @param {String} name
 * @returns {void}
 */
Validator.prototype.getParamValue = function( params, name ){
    var i=0;
    for( i=0; i<params.length; i++){
        if( params[i].name == name ){
            return params[i].value;
        }
    }
    return null;
};
/**
 * M�todo que obtine las reglas definidas para un campo
 * @param {Object} component
 * @returns {String}
 */
Validator.prototype.textFieldRules = function( component ){
    var html = "";
    if(component.entity !==null && component.entity !== undefined ){
        var entity = this.getEntity(component.entity);
        if( entity !== null && entity !== undefined &&
            entity.fields !== null && entity.fields !== undefined ){
            for(var i=0; i<entity.fields.length; i++ ){
                if( entity.fields[i].name === component.field && entity.fields[i].domain !== null && entity.fields[i].domain !== undefined ){
                    var rules = this.getDefinitionRulesFromDomain( entity.fields[i].domain );
                    for( var j=0; j<rules.length; j++){        
                        if( rules[j].type === "Default" ){
                            html += ' value="'+rules[j].value+'" ';
                            break;
                        }
                        if( rules[j].type === "MaxLength" ){
                            html += ' maxlength="'+ this.getParamValue( rules[j].params, "maxlength" ) +'" ';
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
    return html;
};

// Solo funciona para dd/mm/yyyy
Validator.prototype.stringToDate = function( fecha ){
  var parts = fecha.split("/");
  return new Date(parseInt(parts[2], 10), parseInt(parts[1], 10) - 1, parseInt(parts[0], 10));
};

// maxlength para TextArea
$(document).ready(function() {  
    $("textarea[maxlength]").bind("keyup input paste", function() {
        var limit = parseInt($(this).attr('maxlength'));  
        var text = $(this).val();  
        var chars = text.length;  
  
        if(chars > limit){  
            var new_text = text.substr(0, limit);   
            $(this).val(new_text);  
        }  
    });
   
  $.validator.addMethod(
        "regex",
        function(value, element, regexp) {
            var re = new RegExp(regexp);
            return this.optional(element) || re.test(value);
        }); 
  
  Date.prototype.sameDay = function(d) {
    return this.getFullYear() === d.getFullYear() && this.getDate() === d.getDate() && this.getMonth() === d.getMonth();
  };
  
  $.validator.addMethod(
    "lessThanToday",
    function(value, element ) {
      var parts = value.split("/");
      var dt = new Date(parseInt(parts[2], 10),
                  parseInt(parts[1], 10) - 1,
                  parseInt(parts[0], 10));
      var now = new Date();
      var today = new Date(Date.UTC(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate() ));
      return value == "" ||  today.getTime() > dt.getTime();
    });
        
        
    $.validator.addMethod(
        "lengthRule",
        function(value, element, params) {            
            if( params.min === undefined || params.min === null ){
              params.min = 0;
            }
            if( params.max === undefined || params.max === null ){
              params.max = 100000;
            }
            return ( value.length ===0 ||  (value.length >= parseInt(params.min) && value.length <= parseInt(params.max)) );
        }); 
});
/*jshint -W061 */
function DatePickerFieldComponent( render ){
  this.render = render;
}
/**
 * M굯do que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
DatePickerFieldComponent.prototype.draw = function (component) {
  var html = "";
  if( component.id === undefined){
    component.id = component.field;
  }
  
  if( component.attribute === "hide"){
   html += "<input type='hidden' name='"+ component.field +"'/>"; 
  }else{
     html = '<div class="form-group has-feedback">';
     if( !this.render.isEmpty(component.label) ){
       html += ' <label for="' + component.id + '" class="control-label">' + this.render.nvl(component.label, "") + '</label>';
     }
     html += '<div class="">';
    if (!this.render.isEmpty(component.popover)) {
        html += '<div class="input-group-addon" style="right:-10px;"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';
        // Registrar el linker
        this.render.addLinker(new Link(component.id, "TextFieldComponent", component, 5));
    }
    
    html += "<input class='form-control' style='width:100%' type='text' id='" + component.id + "' name='" + component.field + "' ";
    if( !this.render.isEmpty(component.disabled) ){
        if( component.disabled == "true" || component.disabled === true ){
            html += " disabled ";
        }
    }
    
    if (!this.render.isEmpty(component.onChangeEvent)) {
        html += ' onchange="' + this.render.drawTriggerEvent(component.onChangeEvent.type,component.onChangeEvent.event,component.onChangeEvent.key,component.onChangeEvent.model) + '" ';
    }
    
    if( component.attribute === "readOnly"){
      component.readOnly = "true";
    }
    html += ((component.readOnly !== null && component.readOnly === "true") ? ' readonly="readonly" ' : '');
    html += ' tabindex="' + (this.render.index++) + '" >';
    if( !(component.readOnly !== null && component.readOnly === "true") ){
      html += "<a  onclick='$(\"#" + component.id + "\").datepicker({";
      if( !this.render.isEmpty(component.min)  ){
        html += "minDate: new Date(\"" + this.formatDate( component.min ) + "\" ) ";
        if( !this.render.isEmpty(component.max) ){
          html += " , ";
        }
      }
      if( !this.render.isEmpty(component.max) ){
          html += "maxDate: new Date(\"" + this.formatDate( component.max ) + "\" ) ";
      }
      html +="}).focus()' ><span class='glyphicon glyphicon-calendar form-control-feedback' aria-hidden='true'></span></a>";
      this.render.addLinker(new Link(component.id, "DatePickerFieldComponent", component));
      this.render.addWatch(new Watch(component.id, 'DatePickerFieldComponent', component, 5));
    }
    html += "</div></div>";
    
  }
    return html;
};

DatePickerFieldComponent.prototype.formatDate = function(str){
  
  return str.substring(3, 5) + "-" +str.substring(0, 2) + "-"+str.substring(6, 10);
  
};

/**
 * M굯do que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
DatePickerFieldComponent.prototype.digest = function (link) {
    var _this = this;
    var component = link.metadata;
    
    
    $("#" + link.metadata.id).datepicker({
        buttonText: '<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>',
        showOptions: {direction: "up"},
        showButtonPanel: true,
        dateFormat:'dd/mm/yy',
        beforeShow: function (input) {
            setTimeout(function () {
                var buttonPane = $(input)
                        .datepicker("widget")
                        .find(".ui-datepicker-buttonpane");
                buttonPane.html("");
                $("<button>", {
                    text: "Borrar Fecha",
                    click: function () {
                        //Code to clear your date field (text box, read only field etc.) I had to remove the line below and add custom code here
                        $.datepicker._clearDate(input);
                    }
                }).appendTo(buttonPane).addClass("btn-calendar");
            }, 1);
        },
        onChangeMonthYear: function (year, month, instance) {
            setTimeout(function () {
                var buttonPane = $(instance)
                        .datepicker("widget")
                        .find(".ui-datepicker-buttonpane");
                buttonPane.html("");
                $("<button>", {
                    text: "Borrar Fecha",
                    click: function () {
                        //Code to clear your date field (text box, read only field etc.) I had to remove the line below and add custom code here
                        $.datepicker._clearDate(instance.input);
                    }
                }).appendTo(buttonPane).addClass("btn-calendar");
            }, 1);
        }
        // Se remueve para suscribirlo despues de que el componente es dibujado
        /*,
        onSelect: function(dateText, inst) {
            if( !_this.render.isEmpty( link.metadata.onchange ) ){
                var js = _this.render.drawCommand(link.metadata.onchange);
                eval(js.replace("()", "('" + $(this).val() + "')"));
            }
        }*/
    });
    
    
     if( !this.render.isEmpty(component.min)  ){
       $("#" + component.id).datepicker("option", "minDate", new Date( this.formatDate( component.min ) ) );     
      }
      if( !this.render.isEmpty(component.max) ){
       $("#" + component.id).datepicker("option", "maxDate", new Date( this.formatDate( component.max ) ) );
      }
    
    
    
};
/**
 * M굯do que se procesarᡣuando hay una notificaci󮠤e cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par⮥tro(s)
 * @returns {void}
 */
DatePickerFieldComponent.prototype.notify = function (key, model) {
    var metadata = this.render.getWatch(key, "DatePickerFieldComponent");
    if(typeof model === 'object'){
        if (model.rango === "max") {
            $("#" + metadata.id).datepicker("option", "maxDate", model.valor);
        } else if (model.rango === "min") {
            $("#" + metadata.id).datepicker("option", "minDate", model.valor);
        }
    } else {
        this.setValue( key, model );//$("#" + metadata.field).datepicker('setDate', model );
    }
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
DatePickerFieldComponent.prototype.getValue = function( $key ){
    return $( '#' + $key ) === null ? null : $( '#' + $key ).val();
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
DatePickerFieldComponent.prototype.setValue = function( $key, $value ){
    $( "#" + $key ).datepicker( 'setDate', $value );
    this.format( $key );
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
DatePickerFieldComponent.prototype.format = function( $key ){
    var component = $( '#' + $key );
    var value = component.val();
    component.datepicker( 'setDate', value );
};
/**
 * M굯d que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par⮥tro(s) que recibirᡥl evento
 * @returns {void}
 */
DatePickerFieldComponent.prototype.triggerEvent = function ($event, $key, $model) {
    switch( $event ){
        case 'validate':
            this.validate( $key, $model );
            break;
        case 'bind':
            this.bind( $key, $model );
            break;
    }
};
/**
 * M굯do que procesa el evento capturado de tipo 'validate'
 * @param {Object} link : metadata
 * @returns {void}
 */
DatePickerFieldComponent.prototype.validate = function( $key, $data ){
    var textField = $('#' + $key);
    if ( textField ) {
        if( $data.isValid === true ){
            textField.parent().parent().removeClass('has-error');
        } else {
            textField.parent().parent().addClass('has-error');
            if( $data.message.length > 0 ){
                this.render.showAlert('', $data.message);
            }
        }
    }
};
/**
 * M굯do que suscribe el evento focusout e invoca el callback suscrito
 * @param {String} $key
 * @param {Function} $callback
 * @returns {void}
 */
DatePickerFieldComponent.prototype.bind = function( $key, $callback ){
    var _this = this;
    var component = $( '#' + $key );
    component.datepicker().on( "change", function() {
        $callback( _this.getValue( $key ), _this.render.module.controller.model );
    });
};

function TextFieldComponent(render){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
TextFieldComponent.prototype.draw = function (component) {
  
  var html = "";
  if( component.id === undefined ){
    component.id = component.field;
  }
  if( component.attribute === "hide"){
   html += "<input type='hidden' name='"+ component.field +"'/>"; 
  }else{
    component.entity = this.render.entity;
    html = '<div class="form-group" ';
	
	if(!this.render.isEmpty( component.id )){
		html += 'id="divTF-'+component.id+'"';
	}
	html += ' >';
    
    if (!this.render.isEmpty(component.label)) {          
      html += '<label for="';
      html += component.id + '" class="control-label ';
      if( !this.render.isEmpty( this.render.horizontal )){
        html += 'col-sm-' + this.render.horizontal.label;
      }
      html += ' ">';
      html += this.render.nvl(component.label, "") + '</label>';      
    }
    html += '<div '; //input-group
    
    if (!this.render.isEmpty(component.popover) || !this.render.isEmpty(component.button)) {
      html += ' class="input-group" ';
    }
    if( !this.render.isEmpty( this.render.horizontal )){
        html += 'class="col-sm-' + this.render.horizontal.control + ' "';
    }
    
    html +='>';
    
    if( component.attribute === "readOnly"){
      component.readOnly = "true";
    }
    
    html += '<input id="' + component.id + '" ';
    html += ((component.disabled !== null && ( component.disabled === "true" || component.disabled === true ) ) ? ' disabled ' : '');
    html += ((component.readOnly !== null && ( component.readOnly === "true" || component.readOnly === true )) ? ' readonly="readonly" ' : '');
    html += ' class="form-control ';
    if (!this.render.isEmpty(component.className)) {
        html +=  component.className;
    }
    html +='"   name="' + component.field + '"  ';
    if (!this.render.isEmpty(component.onchange)) {
        html += ' onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    if (!this.render.isEmpty(component.onEventChange)) {
        html += ' onchange="' + this.render.drawTriggerEvent(component.onEventChange.type,component.onEventChange.event,component.onEventChange.key,component.onEventChange.model) + '" ';
    }
    if (!this.render.isEmpty(component.onblur)) {
        html += ' onblur="' + this.render.drawCommand(component.onblur) + '" ';
    }
    if (!this.render.isEmpty(component.tooltip)) {
        html += 'data-toggle="tooltip" data-placement="bottom" title="' + this.render.nvl(component.tooltip) + '" ';
    }
    html += this.render.module.validator.textFieldRules(component);
    html += '  />';
    
    if (!this.render.isEmpty(component.button)) {
      html += ' <span class="input-group-btn">';
      html += '  <button class="btn '+component.button.className+'"';
      if ( !this.render.isEmpty( component.button.command ) ) {
          html +=' onclick="';
          if ( !this.render.isEmpty( component.button.argument ) ) {
            html += this.render.drawCommandArguments( component.button.command, component.button.argument );
          }else{
            html += this.render.drawCommand( component.button.command );
          }
          html +='" ';
        }
      html += ' type="button">';
      html += '<span class="glyphicon glyphicon-' + component.button.icon + '"> </span>'+component.button.label+'</button>';
      html += '</span> ';
    }
    
    if (!this.render.isEmpty(component.popover)) {
        html += '<div class="input-group-addon"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';
        // Registrar el linker
        this.render.addLinker(new Link(component.field, "TextFieldComponent", component, 5));
    }
    html += '</div>';
    html += '</div>';
  }
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
TextFieldComponent.prototype.digest = function (link) {
    $('[data-toggle="popover"]').popover();
    /*$("#" + link.metadata.field).onEnter( function( event ) {
        var elements = this.form.elements,
            validTabs = 0;
        for (var i = 0 ; i < elements.length ; i++) {
            if (elements[i].tabIndex > 0 &&
                !elements[i].disabled && 
                !elements[i].hidden && 
                !elements[i].readOnly &&
                elements[i].type !== 'hidden') {
                validTabs++;
            }
        }
        var tabindex = $(this).attr('tabindex');
        tabindex++;
        if(tabindex > validTabs){
            tabindex = 1;
        }
        $("[TabIndex='"+tabindex+"']").focus();
    });*/
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
TextFieldComponent.prototype.notify = function( $key, $value ){
    this.setValue( $key, $value );
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
TextFieldComponent.prototype.getValue = function( $key ){
    return $( '#' + $key ) === null ? null : $( '#' + $key ).val();
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
TextFieldComponent.prototype.setValue = function( $key, $value ){
    $('#' + $key).val($value);
    this.format($key);
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
TextFieldComponent.prototype.format = function( $key ){
    var component = $('#' + $key);
    var value = component.val();
    component.val(value);
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
TextFieldComponent.prototype.triggerEvent = function ($event, $key, $model) {
    switch( $event ){
        case 'validate':
            this.validate( $key, $model );
            break;
        case 'bind':
            this.bind( $key, $model );
            break;
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'validate'
 * @param {Object} link : metadata
 * @returns {void}
 */
TextFieldComponent.prototype.validate = function( $key, $data ){
    var textField = $('#' + $key);
    if ( textField ) {
        if( $data.isValid === true ){
            textField.parent().parent().removeClass('has-error');
        } else {
            textField.parent().parent().addClass('has-error');
            if( $data.message.length > 0 ){
                this.render.showAlert('', $data.message);
            }
        }
    }
};
/**
 * M�todo que suscribe el evento focusout e invoca el callback suscrito
 * @param {String} $key
 * @param {Function} $callback
 * @returns {void}
 */
TextFieldComponent.prototype.bind = function( $key, $callback ){
    var _this = this;
    var component = $('#' + $key);
    component.focusout( function( $event ) {
        $callback( _this.getValue( $key ), _this.render.module.controller.model );
    });
};


function LabelComponent(render) {
  this.render = render;
}
LabelComponent.prototype.draw = function (component) {
  this.component = component;  
  var html = '<div id="label_' + component.id + '" >';
  // // console.log( this.component.id );
  // // console.log( this.render.getModel(this.component.id) );
  if( this.render.getModel(this.component.id) !== undefined ){
    this.component.label = this.render.getModel(this.component.id);
  }
  
  
  html += this.drawBody();  
  html += '</div>';
  return html;
};

LabelComponent.prototype.drawBody = function () {
  var component = this.component;
  var html = "";
  if (this.render.isEmpty(component.tag)) {
    html += '<p class="' + (this.render.isEmpty(component.className) ? 'container-fluid' : component.className) + '">';

    if (this.render.isEmpty(component.field)) {
      html += '<span>';
    } else {
      html += '<span id="' + component.field + '">';
    }
    if (!this.render.isEmpty(component.popover)) {
      html += '<a href="#" id="mytooltip" data-toggle="tooltip" data-placement="left" data-placement="top" title="' + this.render.nvl(component.popover.title, "") + '" data-original-title="' + this.render.nvl(component.popover.title, "") + '" style="nounderline:link; text-decoration:none;color:black ">';
    }

    html += this.render.nvl(component.label, "");
    if (!this.render.isEmpty(component.popover)) {
      html += '</a>';
    }
    html += '</span>';
    html += '</p>';




  } else {
    html += "<" + component.tag + '  class="' + (this.render.isEmpty(component.className) ? 'container-fluid' : component.className) + '">';
    html += this.render.nvl(component.label, "");
    html += "</" + component.tag + ">";
  }
  return html;
};

LabelComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata; 
    this.component.label = this.render.getModel(this.component.id);
    // // console.log( this.component.label );
    var html = this.drawBody();
    $("#label_" + this.component.id).html( html );
};

function CheckBoxFieldComponent(render){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
CheckBoxFieldComponent.prototype.draw = function (component) {
  var html = "";
  if( component.attribute === "hide"){
   html += "<input type='hidden' name='"+ component.field +"'/>"; 
  }else{
    if( component.attribute === "readOnly"){
      component.readOnly = "true";
    }
  html = '<div class="checkbox '+((component.disabled !== null && component.disabled === true) ? ' disabled ' : '');
  if (!this.render.isEmpty(component.className)) {
        html +=  component.className;
    }
    
   html +=' "> <label><input value="true"  type="checkbox" name="'+component.field+'" id="' + this.render.replaceAll(component.field,'.','_') + '" '+((component.disabled !== null && component.disabled === true) ? ' disabled ' : '');
    
    
    
    
    if (!this.render.isEmpty(component.onchange)) {
        html += '  onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    if (!this.render.isEmpty(component.onChangeEvent)) {
        html += ' onchange="' + this.render.drawTriggerEvent(component.onChangeEvent.type,component.onChangeEvent.event, component.onChangeEvent.key, component.onChangeEvent.model ) + '"';
    }
    html += ((component.readOnly !== null && component.readOnly === "true") ? ' onclick="return false;" ' : '');
    html += ' />';
    
    html += '<span> </span>';
    html += this.render.nvl(component.label, "") + '</label></div>';
  }
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
CheckBoxFieldComponent.prototype.digest = function (link) {
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
CheckBoxFieldComponent.prototype.notify = function( $key, $value ){
    
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
CheckBoxFieldComponent.prototype.getValue = function( $key ){
    return $( '#' + $key ) === null ? null : $( '#' + $key ).val();
};

/**
 * M�todo que procesa el evento capturado de tipo 'validate'
 * @param {Object} link : metadata
 * @returns {void}
 */
CheckBoxFieldComponent.prototype.validate = function( $key, $data ){
    var textField = $('#' + $key);
    if ( textField ) {
        if( $data.isValid === true ){
            textField.parent().parent().removeClass('has-error');
        } else {
            textField.parent().parent().addClass('has-error');
            if( $data.message.length > 0 ){
                this.render.showAlert('', $data.message);
            }
        }
    }
};

/*jshint -W061 */
function Validator( module){
    this.module = module;
}

Validator.prototype.upload = function( idFileElement ){
  
  if( $("#" + idFileElement).val() !== '' ){
  
  
  var formData = new FormData();
      //formData.set("encoding","multipart/form-data");
      var fileInputElement = $("#" + idFileElement);
      var index;
      
      var fileSize = 0;
      var maxFileSize = this.module.controller.model.system.fileSize;
      if( maxFileSize === undefined ){
        maxFileSize = 1048576;  // 1M
      }
      // Validar tamanio maximo
      for( index=0; index<fileInputElement[0].files.length; index++ ){
        fileSize += fileInputElement[0].file[index].size;        
      }
      
      if( fileSize > maxFileSize ){
        var message = "El tama\u00F1o m\u00E1ximo es de carga es de " + maxFileSize + " bytes y selecciono archivos de " + fileSize + " bytes.";        
        $("#" + idFileElement).parent().addClass("has-error");
        $("#" + idFileElement).next().html("<span for='"+idFileElement+"' class='label label-danger' >"+message+"</span>");
        //$("#" + idFileElement).val('-');
        return;
      }
      
      for( index=0; index<fileInputElement[0].files.length; index++ ){
        formData.append("files", fileInputElement[0].files[index] );
      }
    
    var validator = this;
    // Ajax call for file uploaling
    var ajaxReq = $.ajax({
      url : '/banorte/fileUpload/save',
      type : 'POST',
      data : formData,
      cache : false,
      contentType : false,
      processData : false,
      xhr: function(){
        //Get XmlHttpRequest object
         var xhr = $.ajaxSettings.xhr() ;
        
        //Set onprogress event handler 
         xhr.upload.onprogress = function(event){
          	var perc = Math.round((event.loaded / event.total) * 100);
          	$('#progressBar-'+idFileElement).text(perc + '%');
          	$('#progressBar-'+idFileElement).css('width',perc + '%');
         };
         return xhr ;
    	},
    	beforeSend: function( xhr ) {
    		//Reset alert message and progress bar
    		//$('#alertMsg').text('');
    		$('#progressBar-'+idFileElement).text('');
    		$('#progressBar-'+idFileElement).css('width','0%');
              }
    });
  
    // Called on success of file upload
    ajaxReq.done(function(msg) {
      var document = msg;
      //// // // console.log(document);
      //$('#alertMsg').text(msg);
      //$("#" + idFileElement).val('-');
      $('#progressBar-'+idFileElement).text('');
      $('#progressBar-'+idFileElement).css('width','0%');
      var actual = validator.module.controller.model[idFileElement];      
      if( Array.isArray( actual ) ){
        for(var index=0; index < document.length ; index ++){
          actual[actual.length] = document[index];
        }
        if( validator.module.controller.model.data !== undefined ){
          validator.module.controller.model.data[idFileElement] = actual;
        }
        validator.module.updateModel("FileFieldComponent", idFileElement, actual );
      }else{
        if( validator.module.controller.model.data !== undefined ){
          validator.module.controller.model.data[idFileElement] = document;
        }
        validator.module.updateModel("FileFieldComponent", idFileElement, document );
      }       
      
    });
    
    // Called on failure of file upload
    ajaxReq.fail(function(jqXHR) {
      //$('#alertMsg').text(jqXHR.responseText+'('+jqXHR.status+' - '+jqXHR.statusText+')');
      //$('button[type=submit]').prop('disabled',false);
    });
    
  }
};

/**
 * M�todo que valida si los campos de la forma son v�lidos
 * @param {String} formName
 * @param {Object|String|Number} model
 * @returns {Boolean}
 */
Validator.prototype.validFormPanel = function( formName, model){
    var valid = true;
    // Encontrar el id del formulario
    var idFormPanel = null;
    for( var i=0; i< this.module.render.watchers.length; i++ ){
        if( this.module.render.watchers[i].field == formName && this.module.render.watchers[i].type == "FormPanelComponent" ){
            idFormPanel = this.module.render.watchers[i].id;
            var component = {};
            component.formId = idFormPanel;
            model[ formName ] = this.formToModel( component, model[ formName ] );
            //break;
        }
    }
    return valid;
};
/**
 * M�todo que valida un toolbarpanel
 * @param {type} toolBarPanelName
 * @param {type} model
 * @param {type} onlyData
 * @returns {Boolean}
 */
Validator.prototype.validToolBarPanel = function( toolBarPanelName, model, onlyData ){
    if( onlyData === undefined || onlyData === null ){
        onlyData = false;
    }
    // Obtener todas los FormPanels dentro del ToolBar
    // ? GlobalName in the render?
    var valid = true;
    var i;
    // Encontrar el id del toolbarpanel
    var idToolBarPanel = null;
    for( i=0; i< this.module.render.watchers.length; i++ ){
        if( this.module.render.watchers[i].field.title == toolBarPanelName && this.module.render.watchers[i].type == "toolBarPanel" ){
            idToolBarPanel = this.module.render.watchers[i].id;
            break;
        }
    }
    // Buscar las formas asociadas a este ID.
    if( idToolBarPanel !== null ){
        for( i=0; i< this.module.render.watchers.length; i++ ){
            if( ( this.module.render.watchers[i].id.lastIndexOf(idToolBarPanel, 0) === 0  ) && this.module.render.watchers[i].type == "formValidator" ){
                if( onlyData || this.validForm( this.module.render.watchers[i].field ) ){
                    model[ this.module.render.watchers[i].field.name ] = this.formToModel( this.module.render.watchers[i].field, model[ this.module.render.watchers[i].field.name ] );
                }else{
                    valid = false;
                }
            }
        }
    }
    return valid;
};
/**
 * M�todo que pasa los datos de la forma al modelo de datos que se pase en el 
 * argumento
 * @param {Object} component
 * @param {Object|String|Number} model
 * @returns {void}
 */
Validator.prototype.formToModel = function( idForma, model ){
    if (model === null || model === undefined) {
        model = {};
    }
    var data = $("#" + idForma ).serializeArray();
    // Esta funcion no regresa los checkbox en falso, por lo que
    // hay que identificar cuantos checks tiene la forma y no estan
    // seleccionados para marcarlos en el model.
    data = data.concat(
            jQuery('#'+idForma+' input[type=checkbox]:not(:checked)').map(
                    function() {
                        return {"name": this.name, "value": 'false'};
                    }).get()
    );
    
    // Incluimos el soporte para datos deshabilitados
    data = data.concat(
            jQuery('#'+idForma+' :disabled').map(
                    function() {
                      if( this.type !== "radio" && ( this.type!=="checkbox"  && this.checked === false ) ){
                        return {"name": this.name, "value": this.value};
                      }else{
                        return {"name":"", "value":""};
                      }
                    }).get()
    );
    // // // console.log( "Forma: " + JSON.stringify( data ) );
    var _this = this;
    if( data !== null && data.length > 0 ){
        $.each( data, function(){ 
            //model[ this.name] = this.value; 
              if( this.name !== ""){
                _this.addAtributeToObject( model, this.name, this.value );
              }
            }
        );
    }
    //// // // console.log("FormToModel: ");
    //// // // console.log(model);
    return model;
};
/**
 * M�todo que agrega un atibuto a un objeto, crear la estructura si no existe
 * @param {Object} obj
 * @param {String} attribute
 * @param {?} value
 * @returns {void}
 */
Validator.prototype.addAtributeToObject = function( obj, attribute, value ){
    ////// // // console.log("FormToModel, " );
    ////// // // console.log(obj );
    ////// // // console.log( attribute );
    var names = attribute.split(".");
    var reference = obj;
    for(var i=0; i< names.length; i++){ 
      ////// // // console.log("reference");
      ////// // // console.log(reference);
      //Validamos si es un arreglo
      var arreglo = names[i].split("[");
      if( arreglo.length > 1 ){
        if( !Array.isArray( reference[arreglo[0]] ) ){ 
          reference[arreglo[0]] = [];
        }
        var aux;
        eval( "aux = reference." + names[i] );
        if( aux === undefined ){
          eval( "reference." + names[i] + "={}" );
        }
        eval( "reference = reference." + names[i] );
      }else{      
        // Crear la estructura de objeto
        if( reference[names[i]] === null || reference[names[i]] === undefined){
            reference[names[i]] = {};
        }
        reference = reference[names[i]];
      }
      
    }
    ////// // // console.log("FormToModel, " + "obj."+attribute+"=value");
    //Asignar el valor, attribute puede ser una estructura
    eval( "obj."+attribute+"=value");
};
/**
 * M�todo que valida la forma
 * @param {Object} component
 * @returns {Boolean}
 */
Validator.prototype.validForm = function( component ){
    var entityName = component.entity;
    var entity = this.getEntity( entityName );
    var validatorAux = new Validator( this.module );
    validatorAux.prepare ( entity );
    ////// // // console.log(validatorAux.rules);
    var data = $.find("#FormPanelComponent-" + component.name );    
    if( data.length ===1 ){  
        $( "#FormPanelComponent-" + component.name ).validate({
            errorElement: 'span',
            errorClass: "label label-danger",
            highlight: function(element) {
                $(element).parent().addClass("has-error");
            },
            unhighlight: function(element) {
                $(element).parent().removeClass("has-error");
            },
            rules: validatorAux.rules,
            messages: validatorAux.messages
        });
        return $("#FormPanelComponent-" + component.name ).valid();
    } 
    return true;
};
/**
 * Metodo que obtiene la entidad definida en el metadata
 * @param {String} entityName
 * @returns {Object}
 */
Validator.prototype.getEntity = function( entityName ){
    if( !this.module.render.isEmpty( this.module.metadata.model ) && !this.module.render.isEmpty( this.module.metadata.model.entities )  ){
        var entities = this.module.metadata.model.entities;
        for(var i=0; i<entities.length; i++){
            if( entities[i].name === entityName ){
                return entities[i];
            }
        }
    }
    return {};
};
/**
 * 
 * @param {Object} entity
 * @returns {void}
 */
Validator.prototype.prepare = function( entity ){
    this.rules = {};
    this.messages = {};
    if( !this.module.render.isEmpty( entity.fields )  ){
        for(var i=0; i<entity.fields.length; i++ ){
            if( entity.fields[i].domain !== null ){
                var ruleName = entity.fields[i].name;
                this.rules[ ruleName ] = this.getRulesFromDomain( entity.fields[i].domain );
                this.messages[ ruleName ] = this.getMessagesFromDomain( entity.fields[i].domain );
            }
        }
    }
    //// // // console.log( this.rules );
};
/**
 * M�todo que obtiene los mensages que se tienen definidos en el metadata de
 * cada uno de los componentes que integran la forman
 * @param {Array} domain
 * @returns {Object}
 */
Validator.prototype.getMessagesFromDomain = function( domain ){
    var domains = this.module.metadata.model.domains;
    for(var i=0; i<domains.length; i++){
        if( domains[i].name == domain ){
            var messages = {};
            for(var j=0; j<domains[i].rules.length; j++){
                this.prepareMessage( messages, domains[i].rules[j] );
            }
            return messages;
        }
    }
    return {};
};
/**
 * M�todo que prepara el mensaje a desplegar
 * @param {Object} messages
 * @param {String} rule
 * @returns {void}
 */
Validator.prototype.prepareMessage = function( messages, rule ){
    switch(rule.type){
        case "Required":
            if( rule.message !== null ){
                //messages.required = $.i18n.prop(rule.message);
                messages.required = rule.message;
            }else{
                //messages.required = $.i18n.prop("required");
                messages.required = "Campo requerido";
            }
            break;
        case "Number":
            if( rule.message !== null ){
                //messages.number = $.i18n.prop(rule.message);
                messages.number = rule.message;
            }else{
                //messages.number = $.i18n.prop("number");
                messages.number = "Numero requerido";
            }
        break;
        case "Regex":
            if( rule.message !== null ){                
                messages.regex = rule.message;
            }else{                
                messages.regex = "Formato incorrecto";
            }
        break;
        case "Length":
            if( rule.message !== null ){                
                messages.lengthRule = rule.message;
            }else{                
                messages.lengthRule = "Longuitud incorrecta";
            }
        break;
        case "Custom":
            if( rule.message !== null ){                
              messages[rule.name] = rule.message;
            }else{                
              messages[rule.name] = "Mensaje no definido";
            }
        break;
        case "LessThanToday":
            if( rule.message !== null ){                
              messages.lessThanToday = rule.message;
            }else{                
              messages.lessThanToday = "Mensaje no definido";
            }
        break;
    }
};
/**
 * M�todo que obtiene la definicion de las reglas definidas en el metadata(Domain)
 * @param {String} domain
 * @returns {Array}
 */
Validator.prototype.getDefinitionRulesFromDomain = function (domain){
    var domains = this.module.metadata.model.domains;
    for(var i=0; i<domains.length; i++){
        if( domains[i].name == domain ){
            return domains[i].rules;
        }
    }
    return [];
};
/**
 * M�todo que obtiene las reglas definidas en el metadata(Domain)
 * @param {String} domain
 * @returns {Object}
 */
Validator.prototype.getRulesFromDomain = function( domain ){
    var domains = this.module.metadata.model.domains;
    for(var i=0; i<domains.length; i++){
        if( domains[i].name === domain ){
            var rules = {};
            for(var j=0; j<domains[i].rules.length; j++){
                this.prepareRule( rules, domains[i].rules[j] );
            }
            return rules;
        }
    }
    return {};
};
/**
 * M�todo que prepara las reglas
 * @param {Object} rules
 * @param {Object} rule
 * @returns {void}
 */
Validator.prototype.prepareRule = function( rules, rule ){
    switch(rule.type){
        case "Required":
            rules.required = true;
            break;
        case "Number":
            rules.number = true;
            break;
        case "Regex":
            rules.regex = this.getParamValue(rule.params, "regex");
            break;
        case "Length":
            rules.lengthRule = { 
                min: this.getParamValue(rule.params, "min"),
                max:this.getParamValue(rule.params, "max")
              };
            break;
        case "Custom":
          rules[rule.name] = { params: rule.params };
          break;
        case "LessThanToday":
          rules.lessThanToday = true;
          break;
        
    }
};
/**
 * M�todo que obtiene el valor del par�metro
 * @param {Array} params
 * @param {String} name
 * @returns {void}
 */
Validator.prototype.getParamValue = function( params, name ){
    var i=0;
    for( i=0; i<params.length; i++){
        if( params[i].name == name ){
            return params[i].value;
        }
    }
    return null;
};
/**
 * M�todo que obtine las reglas definidas para un campo
 * @param {Object} component
 * @returns {String}
 */
Validator.prototype.textFieldRules = function( component ){
    var html = "";
    if(component.entity !==null && component.entity !== undefined ){
        var entity = this.getEntity(component.entity);
        if( entity !== null && entity !== undefined &&
            entity.fields !== null && entity.fields !== undefined ){
            for(var i=0; i<entity.fields.length; i++ ){
                if( entity.fields[i].name === component.field && entity.fields[i].domain !== null && entity.fields[i].domain !== undefined ){
                    var rules = this.getDefinitionRulesFromDomain( entity.fields[i].domain );
                    for( var j=0; j<rules.length; j++){        
                        if( rules[j].type === "Default" ){
                            html += ' value="'+rules[j].value+'" ';
                            break;
                        }
                        if( rules[j].type === "MaxLength" ){
                            html += ' maxlength="'+ this.getParamValue( rules[j].params, "maxlength" ) +'" ';
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
    return html;
};

// Solo funciona para dd/mm/yyyy
Validator.prototype.stringToDate = function( fecha ){
  var parts = fecha.split("/");
  return new Date(parseInt(parts[2], 10), parseInt(parts[1], 10) - 1, parseInt(parts[0], 10));
};

// maxlength para TextArea
$(document).ready(function() {  
  
  $(function () {
    $('[data-toggle="tooltip"]').tooltip();
  });
  
  
    $("textarea[maxlength]").bind("keyup input paste", function() {
        var limit = parseInt($(this).attr('maxlength'));  
        var text = $(this).val();  
        var chars = text.length;  
  
        if(chars > limit){  
            var new_text = text.substr(0, limit);   
            $(this).val(new_text);  
        }  
    });
   
  $.validator.addMethod(
        "regex",
        function(value, element, regexp) {
            var re = new RegExp(regexp);
            return this.optional(element) || re.test(value);
        }); 
  
  Date.prototype.sameDay = function(d) {
    return this.getFullYear() === d.getFullYear() && this.getDate() === d.getDate() && this.getMonth() === d.getMonth();
  };
  
  $.validator.addMethod(
    "lessThanToday",
    function(value, element ) {
      var parts = value.split("/");
      var dt = new Date(parseInt(parts[2], 10),
                  parseInt(parts[1], 10) - 1,
                  parseInt(parts[0], 10));
      var now = new Date();
      var today = new Date(Date.UTC(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate() ));
      return value == "" ||  today.getTime() > dt.getTime();
    });
        
        
    $.validator.addMethod(
        "lengthRule",
        function(value, element, params) {            
            if( params.min === undefined || params.min === null ){
              params.min = 0;
            }
            if( params.max === undefined || params.max === null ){
              params.max = 100000;
            }
            return ( value.length ===0 ||  (value.length >= parseInt(params.min) && value.length <= parseInt(params.max)) );
        }); 
});

function AlertComponent( render ){
    this.render = render;
}
AlertComponent.prototype.draw = function (component) {
  this.component = component;  
  var html = '<div id="alert_' + component.id + '" >';
  html += this.drawBody();  
  html += '</div>';
  return html;
};

AlertComponent.prototype.drawBody = function () {
  var html = "";
  if(!this.render.isEmpty(this.component) && !this.render.isEmpty( this.render.module.controller.model[this.component.id] ) && !this.render.isEmpty( this.render.module.controller.model[this.component.id].message ) ){
    
    html += "<div class='alert alert-"+this.render.module.controller.model[this.component.id].level+"'>";
    html += '<a href="#" class="close" data-dismiss="alert">&times;</a>';
    html +=  this.render.module.controller.model[this.component.id].message;
    html += "</div>";
  }
  return html;
};

AlertComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata;        
    var html = this.drawBody();
    $("#alert_" + this.component.id).html( html );
};

function HTMLComponent( render ){
    this.render = render;
}
HTMLComponent.prototype.draw = function (component) {
  this.component = component;  
  var html = '<div id="html_' + component.id + '" >';
  html += this.drawBody();  
  html += '</div>';
  return html;
};

HTMLComponent.prototype.drawBody = function () {
  var html = "";
  if(!this.render.isEmpty(this.component) && !this.render.isEmpty( this.component.html ) ){
    
    html += this.component.html;
  }
  return html;
};

HTMLComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata;        
    var html = this.drawBody();
    $("#html_" + this.component.id).html( html );
};

function ButtonComponent(render) {
    this.render = render;
}
/**
 * M�todo que hace el dibujado y configuraci�n del bot�n
 * @param {Object} component : Metadata
 * @returns {String}         : html
 */
ButtonComponent.prototype.draw = function (component) {
    var html ="<";
    if (!this.render.isEmpty(component.href)) {
      html += 'a';
    }else{
      html += 'button';
    }
    
    html += ' onclick="';
    if ( !this.render.isEmpty( component.command ) ) {
        if( !this.render.isEmpty( component.argument ) ){
            html += this.render.drawCommandArguments( component.command, component.argument );            
        } else {
            html += this.render.drawCommand( component.command );
        }
    }
    if ( !this.render.isEmpty( component.trigger ) ) {
        if (!this.render.isEmpty(component.href)) {
          html +='" href="javascript:';
        }
        html += this.render.drawTriggerEvent(component.trigger.type, component.trigger.event, component.trigger.key, JSON.stringify( component.trigger.model ) );
    }
    if (!this.render.isEmpty(component.className)) {
        if(component.className !== 'btnLink'){
            html += '" type="button" class="btn ' + component.className + ' "';
        } else {
            html += '" type="button" class="' + component.className + '"';
        }
    } else {
        html += '" type="button" class="btn"';
    }
    if( !this.render.isEmpty( component.datadismiss ) ) {
        if(component.datadismiss === true) {
            html += ' data-dismiss="modal" ';
        }
    }
    if (!this.render.isEmpty(component.href) && this.render.isEmpty( component.trigger ) ) {
        html += 'href="#'+component.href+'" ';
    }
    if (!this.render.isEmpty(component.tooltip)) {
        html += 'data-toggle="tooltip" data-placement="bottom" title="' + this.render.nvl(component.tooltip) + '" ';
    }
    if (!this.render.isEmpty(component.disabled) && component.disabled === true) {
        html += 'disabled="disabled"  ';
    }
    if (!this.render.isEmpty(component.id)) {
        html += 'id="' + this.render.nvl(component.id) + '" ';
    }
    html += '>';
    html += '<span class="oi oi-' + component.icon + ' glyphicon glyphicon-'+ component.icon +'"></span>' + this.render.nvl(component.label, "");
    html += '</';
    if (!this.render.isEmpty(component.href)) {
      html += 'a';
    }else{
      html += 'button';
    }
    html +='>';
    return html;
};


function TextAreaFieldComponent(render){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
TextAreaFieldComponent.prototype.draw = function (component) {
  var html = "";
  if( component.attribute === "hide"){
   html += "<input type='hidden' name='"+ component.field +"'/>"; 
  }else{
  
    html = '<div class="form-group"> ';
    if (!this.render.isEmpty(component.label)) {          
      html += '<label for="';
      html += component.field + '" class="control-label ';
      if( !this.render.isEmpty( this.render.horizontal )){
        html += 'col-sm-' + this.render.horizontal.label;
      }
      html += ' ">';
      html += this.render.nvl(component.label, "") + '</label>';      
    }
    
    
    if( component.attribute === "readOnly"){
      component.readOnly = "true";
    }
    html += '<div '; //input-group
    
    if( !this.render.isEmpty( this.render.horizontal )){
        html += 'class="col-sm-' + this.render.horizontal.control + ' "';
    }
    
    html +='>';
    
    
    html += '<textarea id="' + this.render.replaceAll(component.field,'.','_') + '" ';
    html += ((component.disabled !== null && component.disabled === true) ? ' disabled ' : '');
    html += ((component.readOnly !== null && component.readOnly === "true") ? ' readonly="readonly" ' : '');
    html += ' class="form-control ';
    if (!this.render.isEmpty(component.className)) {
        html +=  component.className;
    }
    html +='"   name="' + component.field + '"  ';
    if (!this.render.isEmpty(component.rows)) {
        html += ' rows="' + component.rows + '" ';
    }
    if (!this.render.isEmpty(component.maxlength)) {
        html += ' maxlength="' + component.maxlength + '" ';
    }
    if (!this.render.isEmpty(component.onchange)) {
        html += ' onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    if (!this.render.isEmpty(component.onblur)) {
        html += ' onblur="' + this.render.drawCommand(component.onblur) + '" ';
    }
    if (!this.render.isEmpty(component.tooltip)) {
        html += 'data-toggle="tooltip" data-placement="bottom" title="' + this.render.nvl(component.tooltip) + '" ';
    }
    if (!this.render.isEmpty(component.oncopy)) {
        html += ' oncopy="' + component.oncopy + '" ';
    }
    if (!this.render.isEmpty(component.oncut)) {
        html += ' oncut="' + component.oncut + '" ';
    }
    if (!this.render.isEmpty(component.onpaste)) {
        html += ' onpaste="' + component.onpaste + '" ';
    }
    html += this.render.module.validator.textFieldRules(component);
    html += ' tabindex="' + (this.render.index++) + '" ></textarea>';
    
    html += '</div></div>';
  }
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
TextAreaFieldComponent.prototype.digest = function (link) {
    $('[data-toggle="popover"]').popover();    
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
TextAreaFieldComponent.prototype.notify = function( $key, $value ){
    this.setValue( $key, $value );
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
TextAreaFieldComponent.prototype.getValue = function( $key ){
    return $( '#' + $key ) === null ? null : $( '#' + $key ).val();
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
TextAreaFieldComponent.prototype.setValue = function( $key, $value ){
    $('#' + $key).val($value);
    this.format($key);
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
TextAreaFieldComponent.prototype.format = function( $key ){
    var component = $('#' + $key);
    var value = component.val();
    component.val(value);
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
TextAreaFieldComponent.prototype.triggerEvent = function ($event, $key, $model) {
    switch( $event ){
        case 'validate':
            this.validate( $key, $model );
            break;
        case 'bind':
            this.bind( $key, $model );
            break;
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'validate'
 * @param {Object} link : metadata
 * @returns {void}
 */
TextAreaFieldComponent.prototype.validate = function( $key, $data ){
    var textField = $('#' + $key);
    if ( textField ) {
        if( $data.isValid === true ){
            textField.parent().parent().removeClass('has-error');
        } else {
            textField.parent().parent().addClass('has-error');
            if( $data.message.length > 0 ){
                this.render.showAlert('', $data.message);
            }
        }
    }
};
/**
 * M�todo que suscribe el evento focusout e invoca el callback suscrito
 * @param {String} $key
 * @param {Function} $callback
 * @returns {void}
 */
TextAreaFieldComponent.prototype.bind = function( $key, $callback ){
    var _this = this;
    var component = $('#' + $key);
    component.focusout( function( $event ) {
        $callback( _this.getValue( $key ), _this.render.module.controller.model );
    });
};

function RadioGroupFieldComponent( render ){
    this.render = render;
}
RadioGroupFieldComponent.prototype.draw = function (component) {
  //this.render.module.controller.model[component.field] = component;
  var html = '<div id="radioGroup_' + component.field + '" >';
  html += this.drawBody(component);  
  html += '</div>';
  return html;
};

RadioGroupFieldComponent.prototype.drawBody = function (component) {
  var html = "";
  this.component = component;
  
  if( component.attribute === "hide"){
   html += "<input type='hidden' name='"+ component.field +"'/>"; 
  }else{
    if( component.attribute === "readOnly"){
      component.readOnly = "true";
    }
  
    if( !this.render.isEmpty(this.component)   ){
      html += '<span>';
      html += this.render.htmlEncode(this.component.label);
      html += '</span>';
      for(var index=0;index< this.component.elements.length;index++){
          if( this.render.isEmpty(this.component.inline)){

              html += '<div class=" radio ">';
          }else{
              html += '<div class="radio-inline ">';
          }        
          html += "<label ";
          if (!this.render.isEmpty(component.className)) {
            html += 'class=" ' + component.className + '" ';
          }
          html += ' for="';
          html += this.component.field+index+'" ';
          html += ">";
  //        html += '<div class=" radio"><label>';
          html += '<input type="radio" name="'+this.component.field+'" id="';
          html += this.component.field+index+'" value="';
          html += this.component.elements[index].value;        
          html += '" ';          
          html += ((component.readOnly !== null && component.readOnly === "true") ? ' disabled ' : '');
          html += ((component.elements[index].disabled !== null && component.elements[index].disabled === true ) ? ' disabled ' : '');
          if( !this.render.isEmpty(this.component.elements[index].onclick) ){
            html += ' onclick="';
            html += this.component.elements[index].onclick;
            html += '"';
          }
          if( this.component.elements[index].checked === true ){
            html += ' checked="';
            html += this.component.elements[index].checked;
            html += '"';
          }
          if (!this.render.isEmpty(component.onchange)) {
          html += '  onchange="' + this.render.drawCommand(component.onchange) + '" ';
          }
          if (!this.render.isEmpty(component.elements[index].onChangeEvent)) {
            html += ' onclick="' + this.render.drawTriggerEvent(component.elements[index].onChangeEvent.type,component.elements[index].onChangeEvent.event, component.elements[index].onChangeEvent.key, component.elements[index].onChangeEvent.model ) + '"';
          }
          html += '/>';
          html += '<span> </span>';
          html += this.render.htmlEncode(this.component.elements[index].label);
          
          html += '</label></div>';        
      }

    }
  }
  return html;
};

RadioGroupFieldComponent.prototype.notify = function (metadata, model) {
    this.component = metadata;
    // El model sera el nuevo conjunto de elementos
    this.component.elements = model.elements;
    var html = this.drawBody(this.component);
    $("#radioGroup_" + this.component.field).html( html );
};




function PanelComponent( render ){
    this.render = render;
}
PanelComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="panel_' + component.id + '" >';  
    html += this.drawBody();  
  html += '</div>';
  return html;
};
/**
 * M�todo que dibuja el componente
* 
 * @returns {String}         : html
 */
PanelComponent.prototype.drawBody = function () {
  var component = this.component;
  var html = "";
    html += "<div ";
        if( !this.render.isEmpty( component.className ) ){
        html += 'class="' + component.className + '" ';
    }
    
    if( !this.render.isEmpty( component.id ) ){
        html += 'id="' + component.id + '" ';
    }

    if( !this.render.isEmpty( component.hidden )){
      html += ' hidden ';
    }
    
    html += " >";
    if (component.label !== null && component.label !== undefined) {
        //html += "<fieldset class='fieldset'><legend>" + this.render.nvl(component.label, "") + "</legend>";
        if (component.level === null || component.level === undefined) {
          component.level = 3;
        }
        if (component.classNameHR === null || component.classNameHR === undefined) {
          component.classNameHR = "red";
        }
        html +="<h"+component.level+">" + this.render.nvl(component.label, "") + "</h"+component.level+"><hr class='"+component.classNameHR+"'></hr>" ;
    }
    //var renderAux = new Render(this.render.module);
    
    var components = this.render.components;
    var layout = this.render.layout;
    
    
    //If formPanel, pass entity to fields for validtor rules
    if (component.entity !== null && component.entity !== undefined) {
        for (var i = 0; i < component.components.length; i++) {
            component.components[i].entity = component.entity;
        }
    }
    this.render.components = component.components;
    this.render.layout = component.layout;
    html += this.render.render();
    
    this.render.components = components;
    this.render.layout = layout;
    /*this.render.addLinkers(renderAux.linker);
    this.render.addWatchers(renderAux.watchers);
    this.render.counter = renderAux.counter + 2;*/
    
    html += "</div>";
    return html;
};

PanelComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  var component = this.component;
  var html = this.drawBody();
  $("#panel_" + component.id).html(html);
};

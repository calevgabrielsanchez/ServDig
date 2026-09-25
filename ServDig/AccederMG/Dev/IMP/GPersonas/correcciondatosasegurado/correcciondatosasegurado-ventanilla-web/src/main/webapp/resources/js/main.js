function AlertComponent( render ){
    this.render = render;
}
AlertComponent.prototype.draw = function (component) {
  this.compoment = component;  
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
    html += this.render.htmlEncode( this.render.module.controller.model[this.component.id].message );
    html += "</div>";
  }
  return html;
};

AlertComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata;        
    var html = this.drawBody();
    $("#alert_" + this.component.id).html( html );
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
	var html = "";
    if((component.inLineWithLabel !== null && component.inLineWithLabel === true)){
        html += '<div class="form-group"><label for="" class="control-label"></label><div class=""> ';
    }
	
	
	
    html +="<";
    if (!this.render.isEmpty(component.href)) {
      html += 'a';
    }else{
      html += 'button';
    }
    
    html += ' onclick="';
    if ( !this.render.isEmpty( component.command ) ) {
        if( !this.render.isEmpty( component.argument ) ){
            html += this.render.drawCommand( component.command ).replace('()', '(' + component.argument + ')');
        } else {
            html += this.render.drawCommand( component.command );
        }
    }
    if ( !this.render.isEmpty( component.trigger ) ) {
        html += this.render.drawTriggerEvent(component.trigger.type, component.trigger.event, component.trigger.key, component.trigger.model);
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
    if (!this.render.isEmpty(component.href)) {
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
    html += '<span class="glyphicon glyphicon-' + component.icon + '"></span>' + this.render.nvl(component.label, "");
    html += '</';
    if (!this.render.isEmpty(component.href)) {
      html += 'a';
    }else{
      html += 'button';
    }
    html +='>';
    if((component.inLineWithLabel !== null && component.inLineWithLabel === true)){
    	html+='</div></div>'
    }
    return html;
};

function ButtonGroupComponent( render ){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
ButtonGroupComponent.prototype.draw = function (component) {
    var html = "<div class='pull-right'>";    
    for( var i=0; i< component.components.length; i++){
      var element = this.render.componentTemplates[component.components[i].type];
      if( element !== undefined ){
        html += element.draw(component.components[i]);
        html += "<span> </span>";
      }
    }
    html += "</div>";
    return html;
};


/*jshint -W061 */
function CardLayoutComponent(render){
  this.render = render;
}
CardLayoutComponent.prototype.draw = function( component ){
  var html = "";
  html += "<div id='cardLayout_" + component.id + "'>";  
  html +="</div>";   
  this.render.addLinker(new Link(component.id, "CardLayoutComponent", component, 0));
  return html;  
};
CardLayoutComponent.prototype.digest = function (link){
  if( this.render.isEmpty(link.metadata.current) ){
    link.metadata.current = 0;
  }
  this.showCard(link.metadata);      
};


CardLayoutComponent.prototype.notify = function (metadata,value) {
  window.location.hash = "";
  metadata.current = value;
  this.showCard(metadata);
  window.location.hash = "top";  
};


CardLayoutComponent.prototype.showCard = function (metadata){
  if( !this.render.isEmpty( metadata.components ) ){
    var panelData = metadata.components[ metadata.current % metadata.components.length ];
    var componentsBack = this.render.components;
    var layoutBack = this.render.layout;
    var containerBack = this.render.container;
    /*var renderAux = new Render(this.render.module);
    renderAux.counter = this.render.counter++;
    renderAux.componentTemplates = this.render.componentTemplates;
    */
    this.render.container = "cardLayout_" + metadata.id;    
    this.render.components = [ panelData ];
    this.render.layout = [ [{span: "12"}]  ];
    this.render.draw();
    
    this.render.components = componentsBack;
    this.render.layout = layoutBack;
    this.render.container = containerBack;
  }
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
	var html="";
	if((component.inLineWithLabel !== null && component.inLineWithLabel === true)){
	    html += '<div class="form-group"><label for="" class="control-label"></label><div class=""> ';
	}
	html += '<div class="checkbox '+((component.disabled !== null && component.disabled === true) ? ' disabled ' : '')+'"> <label><input value="true" type="checkbox"  name="'+component.field+'" id="' + this.render.replaceAll(component.field,'.','_') + '" '+((component.disabled !== null && component.disabled === true) ? ' disabled ' : '') + ((component.checked !== null && component.checked === true) ? ' checked ' : '');
    if (!this.render.isEmpty(component.onchange)) {
        html += '  onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    html += ' />';
    html += this.render.nvl(component.label, "") + '</label></div>';
    if((component.inLineWithLabel !== null && component.inLineWithLabel === true)){
    	html+='</div></div>'
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


function CurrencyFieldComponent(render){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
CurrencyFieldComponent.prototype.draw = function (component) {
    var html = '<div class="form-group"> <label for="';
    html += component.field + '" class="col-md-4 control-label">';
    html += this.render.nvl(component.label, "") + '</label>';
    html += '<div class="input-group col-md-8">';
    html += '<div class="input-group-addon">$</div>';
    html += '<input id="' + component.field + '" ';
    html += ((component.disabled !== null && component.disabled === 'true') ? ' disabled ' : '');
    html += ' class="form-control "  name="' + component.field + '"  ';
    if (!this.render.isEmpty(component.onchange)) {
        html += ' onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    if (!this.render.isEmpty(component.onblur)) {
        html += ' onblur="' + this.render.drawCommand(component.onblur) + '" ';
    }
    if (!this.render.isEmpty(component.tooltip)) {
        html += 'data-toggle="tooltip" data-placement="bottom" title="' + this.render.nvl(component.tooltip) + '" ';
    }
    html += this.render.module.validator.textFieldRules(component);
    html += ' tabindex="' + (this.render.index++) + '" />';
    if (!this.render.isEmpty(component.popover)) {
        html += '<div class="input-group-addon"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';
        // Registrar el linker
        this.render.addLinker(new Link(component.field, "CurrencyFieldComponent", component, 5));
    }
    html += '</div></div>';
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
CurrencyFieldComponent.prototype.digest = function (link) {
    $('[data-toggle="popover"]').popover();
    var __this = this;
    $("#" + link.metadata.field).on("keypress", 
       function( event ){
         __this.render.triggerEvent( 'CurrencyFieldComponent', 'keypress', link.metadata.field, event );
       }
      );
    $("#" + link.metadata.field).on("keyup", 
        function( event ){
            __this.render.triggerEvent( 'CurrencyFieldComponent', 'keyup', link.metadata.field, event );
        }
      );
    $("#" + link.metadata.field).on("paste", 
        function( event ){
            __this.render.triggerEvent( 'CurrencyFieldComponent', 'paste', link.metadata.field, event );
        }
      );
 /*   $("#" + link.metadata.field).on("input", 
       function( event ){
         __this.render.triggerEvent( 'CurrencyFieldComponent', 'paste', link.metadata.field, event );
       }
      );*/
    
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
CurrencyFieldComponent.prototype.notify = function( $key, $value ){
    var textField = $('#' + $key);
    if ( textField ) {
        this.setValue( $value );
    }
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
CurrencyFieldComponent.prototype.triggerEvent = function ($event, $key, $model) {
    switch( $event ){
        case 'validate':
            this.validate( $key, $model );
            break;
        case 'keypress':
            this.keypress( $key, $model );
          break;
        case 'keyup':
            this.keyup( $key, $model );
          break;
        case 'paste':
            this.paste( $key, $model );
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
CurrencyFieldComponent.prototype.validate = function( $key, $data ){
    var component = $('#' + $key);
    if ( component ) {
        if( $data.isValid === true ){
            component.parent().parent().removeClass('has-error');
        } else {
            component.parent().parent().addClass('has-error');
            if( $data.message.length > 0 ){
                this.render.showAlert('', $data.message);
            }
        }
    }
};
/**
 * M�todo que procesa el evento keypress
 * @param {String} $key
 * @param {Object} $event
 * @returns {void}
 */
CurrencyFieldComponent.prototype.keypress = function( $key, $event ){
    var textField = $('#' + $key);
    var key = $event.which || $event.keyCode;
    // Allow: backspace, delete, tab, enter, left arrow, right arrow
    var controlKeys = [8, 9, 13, 37, 39];

    // Ctrl+ anything or one of the conttrolKeys is valid
    var isControlKey = $event.ctrlKey || controlKeys.join(",").match(new RegExp(key));

    if (isControlKey) {return;}
        
    if( key ===  46 ){
        if( textField.val().indexOf(".") !== -1 ){
            $event.preventDefault();
            return;
        } 
    } else {
        // stop current key press if it's not a number
        if (!(48 <= key && key <= 57) ) {
            $event.preventDefault();
            return;
        }
    }
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
CurrencyFieldComponent.prototype.getValue = function( $key ){
    var value = $('#' + $key).val().replace(/,/g,'');
    return value;
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
CurrencyFieldComponent.prototype.setValue = function( $key, $value ){
    $('#' + $key).val($value);
    this.format($key);
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
CurrencyFieldComponent.prototype.format = function( $key ){
    var textField = $('#' + $key);
    var partes = textField.val().split(".");
    if( partes !== null && partes.length > 0 ){
        var entera = partes[0].replace(/,/g,'');
        var actual;
        if( this.render.isEmpty( entera ) ){
            actual = "";
        } else { 
            actual = ""+parseInt( entera , 10 );
            if( isNaN(actual) ){
                actual = "0";
            }
        }
        var part = "";
        if( !this.render.isEmpty(actual) ){      
            var i;
            for( i = 0; i < actual.length % 3; i++ ){
                part =  part + actual[i];                        
            }
            for( i; i < actual.length; i+=3 ){
                if( !this.render.isEmpty(part) ){
                    part =  part + ",";
                }
                part =  part + actual[i] + actual[i+1] + actual[i+2];                        
            }          
        }
        if( partes.length === 2 ){
            if( partes[1].length > 0 ){
                part = part + "." + partes[1];
            } else {
                part = part + ".";
            }
        }    
        textField.val(part);
    }
};

CurrencyFieldComponent.prototype.keyup = function( $key, $event ){
    var start = $event.target.selectionStart,
          end = $event.target.selectionEnd;
    var length = $( $event.target ).val().length;
    this.format($key);
    var length2 = $( $event.target ).val().length;
    var increment = length2 > length ? 1 : 0;
    $event.target.setSelectionRange(start + increment, end + increment );
};

CurrencyFieldComponent.prototype.paste = function( $key, $event ){    
    var input = this.getValue($key);
    var parsed = parseFloat( input, 10 );
    if ( !isNaN( parsed ) ){
        this.setValue( $key , parsed );
    } else {
        this.setValue( $key , "" );
    }
};
/**
 * M�todo que suscribe el evento focusout e invoca el callback suscrito
 * @param {String} $key
 * @param {Function} $callback
 * @returns {void}
 */
CurrencyFieldComponent.prototype.bind = function( $key, $callback ){
    var _this = this;
    var component = $( '#' + $key );
    component.focusout( function( $event ) {
        $callback( _this.getValue( $key ), _this.render.module.controller.model );
    });
};


/*jshint -W061 */
function DatePickerFieldComponent( render ){
  this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
DatePickerFieldComponent.prototype.draw = function (component) {
    var html = '<div class="form-group datepicker-group has-feedback" > ';
    
    if (!this.render.isEmpty(component.label)) {
        html += '<label for="' + component.field + '" class="col-md-4 control-label">' + this.render.nvl(component.label, "") + '</label>';
    }
    	
    html += '<div class="input-group" style="width:100%;">';
    
    if (!this.render.isEmpty(component.popover)) {
        html += '<div class="input-group-addon" style="right:-10px;"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';
        // Registrar el linker
        this.render.addLinker(new Link(component.field, "TextFieldComponent", component, 5));
    }
    html += "<input class='form-control' style='width:100%;' type='text' id='" + component.field + "' name='" + component.field + "' ";
    if( !this.render.isEmpty(component.disabled) ){
        if( component.disabled == "true" || component.disabled === true ){
            html += " disabled ";
        }
    }
    html += ' tabindex="' + (this.render.index++) + '" >';
    html += "<a  onclick='$(\"#" + component.field + "\").datepicker().focus()' ><span class='glyphicon glyphicon-calendar form-control-feedback' aria-hidden='true'></span></a>";
    html += "</div></div>";
    this.render.addLinker(new Link(component.field, "DatePickerFieldComponent", component));
    this.render.addWatch(new Watch(component.field, 'DatePickerFieldComponent', component, 5));
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
DatePickerFieldComponent.prototype.digest = function (link) {
    var _this = this;
    $("#" + link.metadata.field).datepicker({
        buttonText: '<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>',
        showOptions: {direction: "up"},
        showButtonPanel: true,
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
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
DatePickerFieldComponent.prototype.notify = function (key, model) {
    var metadata = this.render.getWatch(key, "DatePickerFieldComponent");
    if(typeof model === 'object'){
        if (model.rango === "max") {
            $("#" + metadata.field).datepicker("option", "maxDate", model.valor);
        } else if (model.rango === "min") {
            $("#" + metadata.field).datepicker("option", "minDate", model.valor);
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
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
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
 * M�todo que procesa el evento capturado de tipo 'validate'
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
 * M�todo que suscribe el evento focusout e invoca el callback suscrito
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

function DetailComponent(render){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
DetailComponent.prototype.draw = function (component) {
    var html = '<div class="form-group has-feedback"> <label for="' + component.field + '" class="col-md-4 control-label">' + this.render.nvl(component.label, "") + '</label>' + '<div class="input-group">';
    if( !this.render.isEmpty( component.disabled ) ){
        if( component.disabled === "true" || component.disabled === true ){
            html += "<a id='" + component.field + "_link' class='disabled' data-toggle='modal' data-target='#" + component.modal.name + "' ><span class='glyphicon glyphicon-new-window form-control-feedback' aria-hidden='true'></span></a>";
        } else {
            html += "<a id='" + component.field + "_link' data-toggle='modal' data-target='#" + component.modal.name + "' ><span class='glyphicon glyphicon-new-window form-control-feedback' aria-hidden='true'></span></a>";
        }
    } else {
        html += "<a id='" + component.field + "_link' data-toggle='modal' data-target='#" + component.modal.name + "' ><span class='glyphicon glyphicon-new-window form-control-feedback' aria-hidden='true'></span></a>";
    }
    if ( !this.render.isEmpty( component.popover ) ) {
        html += '<div class="input-group-addon" style="right:-10px;"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';
        // Registrar el linker
        this.render.addLinker( new Link( component.field, "TextFieldComponent", component, 5 ) );
    }
    //html += "<input disabled class='form-control' style='width:100%'  type='text' id='detailComponent_" + component.field + "' name='" + component.field + "' ";
    html += "<input disabled class='form-control' style='width:100%'  type='text' id='" + component.field + "' name='" + component.field + "' ";
    html += "tabindex='" + (this.render.index++) + "' >";
    html += "</div></div>";
    var modal = new ModalComponent( this.render );
    //component.modal.body.components[0].afterSaveTrigger = { type: "DetailComponent", event: "update", model: "detailComponent_"+component.field }; 
    this.render.addModal( modal.draw( component.modal ) );
    return html;
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
DetailComponent.prototype.notify = function( $key, $value ){
    this.setValue( $key, $value );
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
DetailComponent.prototype.getValue = function( $key ){
    return $( '#' + $key ) === null ? null : $( '#' + $key ).val();
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
DetailComponent.prototype.setValue = function( $key, $value ){
    $( '#' + $key ).val( $value );
    this.format( $key );
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
DetailComponent.prototype.format = function( $key ){
    var component = $( '#' + $key );
    var value = component.val();
    component.val( value );
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
DetailComponent.prototype.triggerEvent = function (event, key, model) {
    switch( event ){
        case 'update':
            this.update(key,model);
            break;
        case 'validate':
            this.validate( key, model );
            break;
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'update'
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
DetailComponent.prototype.update = function ( key, model) {
    var metadata = this.render.getWatch( key , "DetailComponent" );  
    $("#"+model).val( metadata.size );
};
/**
 * M�todo que procesa el evento capturado de tipo 'validate'
 * @param {Object} link : metadata
 * @returns {void}
 */
DetailComponent.prototype.validate = function( $key, $data ){
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
function FormPanelComponent( render ){
    this.render = render;
}

FormPanelComponent.prototype.notify = function (metadata, modelName) {
  document.forms["FormPanelComponent-"+metadata.name].reset();
  this.render.modelToForm("FormPanelComponent-"+metadata.name,
    this.render.module.controller.model[modelName]);
  if( !this.render.isEmpty(metadata.postFetch ) ){
    eval( this.render.module.instanceName + ".controller." + metadata.postFetch + "()" );
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
    this.render.modelToForm("FormPanelComponent-"+link.metadata.name,
    this.render.module.controller.model[link.metadata.model]);
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
    var id = "FormPanelComponent-" + component.name;
    var html = "<div class='form'><form id='" + id + "' name='" + component.name + "' class='form";
    html += "'>";
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
    html += "</form>";
    html += "</div>";
    this.render.addWatch(new Watch(id, "FormPanelComponent", component));
    this.render.addLinker(new Link(id, "FormPanelComponent", component, 10));
    // Registrar la forma al contenedor si existe
    if ( !this.render.isEmpty( this.render.idToolBarPanel ) ) {
        this.render.addWatch(new Watch(this.idToolBarPanel + "-" + (this.render.counter++), "formValidator", component));
    }
    return html;
};

function GridGroupComponent( render ){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
GridGroupComponent.prototype.draw = function (component) {   
    var html = "";
    this.component = component;
    this.model = component.model;
    html += '<div id="'+component.id+'">'; 
    html += this.drawBody();
    html += '</div>';

    this.render.addLinker(new Link(component.id, "GridGroupComponent", component));
    return html;
};
/**
 * M�todo que dibuja cuerpo del componente
 * @returns {String}         : html
 */
GridGroupComponent.prototype.drawBody = function () {
    var component = this.component;    
	var html = "";  
	 if(component.grids !== undefined){
		 for( var i=0; i< component.grids ; i++){
	      var element = this.render.componentTemplates[component.components[i].type];
	      if( element !== undefined ){
	        html += element.draw(component.components[i]);
	        html += "<span> </span>";
	      }
	    }
  }
	    
    return html;
};

GridGroupComponent.prototype.digest = function (linker) {
	  eval(this.render.module.instanceName + ".service." + linker.metadata.size + "('" + linker.metadata.id + "', " + this.render.module.instanceName + ",1)");
  
	  if( !this.render.isEmpty(linker.metadata.data ) ){
		    for (var i= 0; i< linker.metadata.grids; i++){
		    	console.log(linker.metadata.components[i].id);
		    	eval(this.render.module.instanceName + ".service." + linker.metadata.data + "('" + linker.metadata.components[i].id + "', " + this.render.module.instanceName + ","+i+")");
		    }
		    
		 }
};
	
GridGroupComponent.prototype.notify = function (metadata, noGrids) {
	    this.component = metadata;  
	    this.component.grids = noGrids;
	    
	    var components = [];
	    
	    var componente = this.component.components[0];
	    var name = this.component.components[0].id;
	    /*Random grids are created inside the gridgroupcomponent*/
	    for(var index = 0 ; index < noGrids ; index++){
	    	    var id = name;
	    	    var grid = new GridComponent();
		    	grid.id = id + index;
		    	grid.type= componente.type;
		    	grid.model = componente.model;  
		    	grid.index = componente.index; 
		    	grid.columns = componente.columns; 
		    	components.push(grid);
	    }

	    delete this.component.components;
	    this.component.components = components;
	    
	    var html = this.drawBody();
	    $("#" + this.component.id).html( html );
};

/*jhint -W061 */
function GridComponent(render) {
    this.render = render;    
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
GridComponent.prototype.draw = function (component) {  
    var html = "";
    this.component = component;
    this.model = component.model;
    html += '<div  id="grid_' + component.id + '" >';
    html += this.drawBody();
    html += '</div>';

    this.render.addLinker(new Link(component.id, "GridComponent", component));
    return html;
};
/**
 * M�todo que dibuja cuerpo del componente
 * @returns {String}         : html
 */
GridComponent.prototype.drawBody = function () {
    var component = this.component;    
    var model = component.model;
    
    var i;
    var html = "";
    if (component !== null && model !== null && !this.render.isEmpty( component.columns ) ) {
        
    	if(!this.render.isEmpty( component.title)){
    		html += "<h4>" + this.render.nvl(component.title) + "</h4>";
    	}
        
        html +=	"<table class='table table-hover table-bordered table-responsive' ";
        
        if((component.styled !== null && component.styled === true)){
        	html +=	" style=' ";
        	
        	 if(!this.render.isEmpty(component.fontSize)){
             	
             	html +=	" font-size:"+component.fontSize+"; ";//16px;
             }
        	 
        	 if((component.breakWord !== null && component.breakWord === true)){
                 html +=" table-layout: fixed; width: 100%; ";
             }
        	
        	html +=	" '";
        }
       
        html+=">";        
        html += "<tr>";
        var headers = "";
        if((component.index !== null && component.index === true)){
                  headers += "<th width='10%'>No.";
                  headers += "</th>";
                  }
        for (i = 0; i < component.columns.length; i++) {
            headers += "<th ";            
            if(component.columns[i].width !== null && component.columns[i].width >= 0 && component.columns[i].width <=100){
                headers += " width= '"+component.columns[i].width+"' %";
            }            
            headers+= " >";
            headers += this.render.nvl(component.columns[i].label);
            headers += "</th>";
        }
		if((component.index !== null && component.index === true) && 
			(this.render.module.controller.model[model] !== undefined &&
			this.render.module.controller.model[model].data.length ===0)){
			headers = "";
		}
		html += headers;
        html += "</tr>";
        html += "<tbody>";
        if( !this.render.isEmpty( this.render.module.controller.model[model] ) ){
          var data = this.render.module.controller.model[model].data;
          if ( !this.render.isEmpty( data ) ) {
              for (i = 0; i < data.length; i++) {
                  html += "<tr";
                  if ( !this.render.isEmpty(component.onclick) ) {
                      html += ' onclick="' + this.render.module.instanceName + '.controller.' + component.onclick + '(' + i + ')" ';
                  }
                  html += ">";
                  if((component.index !== null && component.index === true)){
                  html += "<td>";
                  html += (i+1);
                  html += "</td>";
                  }
                  for (var j = 0; j < component.columns.length; j++) {
                      html += "<td ";
                      if((component.columns[j].breakWord !== null && component.columns[j].breakWord === true)){
                          html+="style='word-wrap: break-word'";
                      }
                      html+=" > ";
                      if ( !this.render.isEmpty(component.columns[j].href) ) {
                        html += '<a href="#" ';
                        html += ' onclick="' + this.render.module.instanceName + '.controller.' + component.columns[j].href + '(' + i + ')" >';
                      }
                      var _aux = eval("data[i]." + component.columns[j].name);
                      html += _aux === null ? "" : this.render.htmlEncode(_aux);
                      if ( !this.render.isEmpty(component.columns[j].href) ) {
                        html += '</a>';
                      }
                      html += "</td>";
                  }
                  html += "</tr>";
              }
          }
        }
        html += "</tbody>";
        html += "</table>";
        if( !this.render.isEmpty( this.render.module.controller.model[model] )  ){
          var currentPage = this.render.module.controller.model[model].currentPage;
          var pageSize = this.render.module.controller.model[model].pageSize;
          var totalOfRecords = this.render.module.controller.model[model].totalOfRecords;
          var totalOfPages = Math.ceil(totalOfRecords / pageSize);
          var minPage = currentPage - 2;
          if (minPage < 1) {
              minPage = 1;
          }
          var maxPage = currentPage + 2;
          if (maxPage > totalOfPages) {
              maxPage = totalOfPages;
          }
          if( totalOfRecords > pageSize){
            html += '<div style="text-align:center"><ul class="pagination middle">';
            html += '<li ';
            if (minPage === 1) {
            	html += ' style="pointer-events: none;cursor: default;"';
            	html += ' class="disabled" ';  
            }
            html += '><a href="#" onclick="' + this.render.module.instanceName + '.render.triggerEvent(\'GridComponent\', \'page\', \'' + this.component.id + '\', \'1\' )" >&laquo;</a></li>';
            for (var page = minPage; page <= maxPage; page++) {
                html += '<li ';
                if (page === currentPage) {
                    html += ' class="active" ';
                }
                html += '><a href="#" onclick="' + this.render.module.instanceName + '.render.triggerEvent(\'GridComponent\', \'page\', \'' + this.component.id + '\', \'' + page + '\' )" >' + page;
                if (page === currentPage) {
                    html += '<span class="sr-only">(current)</span>';
                }
                html += '</a></li>';
            }
            html += '<li ';
            if (maxPage === totalOfPages) {
                html += ' class="disabled" ';
                html += ' style="pointer-events: none;cursor: default;"';
            }
            html += '><a href="#" onclick="' + this.render.module.instanceName + '.render.triggerEvent(\'GridComponent\', \'page\', \'' + this.component.id + '\', \'' + totalOfPages + '\' )" >&raquo;</a></li></ul></div>';
          }
        }
    }
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} linker : metadata
 * @returns {void}
 */
GridComponent.prototype.digest = function (linker) {
	if( !this.render.isEmpty(linker.metadata.data ) ){
		eval(this.render.module.instanceName + ".service." + linker.metadata.data + "('" + linker.metadata.id + "', " + this.render.module.instanceName + ",1)");
	}
};

/**
 * M�todo que procesa los eventos del componente
 * @param {String} event
 * @param {Object|String|Number} parameters
 * @returns {void}
 */
GridComponent.prototype.triggerEvent = function (event, parameters) {
    switch (event) {
        case "page":
            eval(this.render.module.instanceName + ".service." + this.component.data + "('" + this.component.id + "', " + this.render.module.instanceName + " , " + parameters + ")");
            break;
    }
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {Object} watch               : metadata
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
GridComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata;    
    this.model = modelName;
    var html = this.drawBody();
    $("#grid_" + this.component.id).html( html );
};


function HRComponent( render ){
    this.render = render;
}
HRComponent.prototype.draw = function (component) {
  this.compoment = component;
  var html = '<hr class="' + component.className + '" ></hr>';
  return html;
};



/*jshint -W061 */
function HandlerCardLayoutComponent(render){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
HandlerCardLayoutComponent.prototype.draw = function (component) {
    var html = "";
    html += "<div id='handlerCardLayout_" + component.model + "'>";
    html += "</div>";
    this.render.addWatch(new Watch(component.model, "HandlerCardLayoutComponent", component));
    this.render.addLinker(new Link(component.model, "HandlerCardLayoutComponent", component, 20));
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
HandlerCardLayoutComponent.prototype.digest = function (link) {
    if (this.render.isEmpty(link.metadata.current)) {
        link.metadata.currentCard = 0;
        this.render.addWatch(new Watch(link.metadata.model, "HandlerCardLayoutComponent", link.metadata, 1));
    }
    this.showCard(link.metadata);
};
/**
 * M�todo que visualiza el componente
 * @param {Object} metadata
 * @returns {void}
 */
HandlerCardLayoutComponent.prototype.showCard = function (metadata) {
    if ( !this.render.isEmpty( metadata.cards ) ) {
        var panelData = metadata.cards[ metadata.current % metadata.cards.length ];
        panelData.current = 0;
        this.render.container = "handlerCardLayout_" + metadata.model;
        this.render.components = [panelData];
        this.render.layout = [[{span: "12"}]];
        this.render.draw();
        if( !this.render.isEmpty(metadata.onchange) ){
            this.render.module.controller[metadata.onchange](metadata.model, metadata.current);
        }
    }
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
HandlerCardLayoutComponent.prototype.triggerEvent = function (event, key, model) {
    switch( event ){
        case 'changeCard':
            this.card(key,model);    
            break;
    }
};
/**
 * M�todo que procesa el evento capturado de tipo 'card'
 * @param {String} key                  : Identificador del elemento
 * @param {Object|String|Number} modelo : Parametro(s) que recibir� el evento
 * @returns {void}
 */
HandlerCardLayoutComponent.prototype.card = function (key, modelo) {
    var metadata = this.render.getWatch(key, "HandlerCardLayoutComponent");
    if (!this.render.isEmpty(metadata.cards[metadata.current].lostFocus)) {
        var js = this.render.drawCommand(metadata.cards[ metadata.current].lostFocus);
        var result = false;
        eval('result = ' + js);
        if (result === true) {
            metadata.current = +modelo;
            this.render.addWatch(new Watch(metadata.model, "HandlerCardLayoutComponent", metadata));
            this.showCard(metadata);
        }
    } else {
        metadata.current = +modelo;
        this.render.addWatch(new Watch(metadata.model, "HandlerCardLayoutComponent", metadata));
        this.showCard(metadata);
    }
};

/**
 * Este componente genera un elemento en el modelo de datos definido en la etiqueta
 * 'field', es necesario generar un span a&uacute;nque no se visualize para ser
 * considerado.
 * 
 * @param {Render} $render
 * @returns {HiddenComponent}
 * @see Render
 * 
 * metadata:
 * {
 *     type: "HiddenComponent",
 *     field: "field_05_01"
 * }
 */
function HiddenComponent( $render ) {
    this.render = $render;
}

/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
HiddenComponent.prototype.draw = function ( $component ) {
    this.render.module.controller.model[$component.field] = this.render.isEmpty( $component.value ) ? '' : $component.value;
    return '';
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
HiddenComponent.prototype.digest = function ( $link ) {
    
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
HiddenComponent.prototype.notify = function( $key, $value ){
    this.setValue( $key, $value );
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
HiddenComponent.prototype.getValue = function( $key ){
    return this.render.module.controller.model[$key];
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
HiddenComponent.prototype.setValue = function( $key, $value ){
    if( this.render.module.controller.model[$key] !== null ){
        this.render.module.controller.model[$key] = $value;
    }
    this.format($key);
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
HiddenComponent.prototype.format = function( $key ){
    
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
HiddenComponent.prototype.triggerEvent = function ($event, $key, $model) {
    switch( $event ){}
};

function LabelComponent( render ){
    this.render = render;
}
LabelComponent.prototype.draw = function (component) {
	var html = '<div ';
	
	if( this.render.isEmpty( component.field ) ){
		html += 'id="'+component.id+'"';
	}
	html += ' >';
	
    html += '<p class="' + (this.render.isEmpty(component.className) ? 'container-fluid' : component.className) + '" ';
    
    var auxHtml ='';
    if (!this.render.isEmpty(component.popover)) {
    	html += ' style="float: left;" ';
    	
    	auxHtml += '<div style="float: left;"><a tabindex="-1" class="" role="button" data-toggle="popover tooltip" data-placement="left" data-trigger="hover" ';
    	auxHtml += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
    	auxHtml += '><span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="color:#545454"></span></a></div>';
        // Registrar el linker
        this.render.addLinker(new Link(component.field, "LabelComponent", component, 5));
    }
    
    html += ' >';
    
    if( this.render.isEmpty( component.field ) ){
        html += '<span>'; 
    } else {
        html += '<span id="' + component.field + '">';
    }

    html += this.render.nvl(component.label, "");
    html += '</span>';
    html += '</p>';
    html += auxHtml;   
    html += '</div>';
    return html;
};

function ModalComponent(render){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
ModalComponent.prototype.draw = function(component){
    var html ='';
    html += '<div class="modal fade" id="'+component.id+'" tabindex="-1" role="dialog">';
    html += '  <div class="modal-dialog ';
    if( !this.render.isEmpty( component.size) ){
        html += component.size;
    }
    html += '">';
    html += '    <div class="modal-content">';
    html += '      <div class="modal-header">';
    html += '        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    html += '        <h4 class="modal-title">'+ this.render.nvl(component.title)+'</h4>';
    html += '      </div>';
    html += '<div class="modal-body">';
    if( !this.render.isEmpty( component.body) ){
        var body = new PanelComponent( this.render );
        html += body.draw( component.body );
    }      
    html += '</div>';
    if( !this.render.isEmpty( component.footer) ){
        html += '<div class="modal-footer">';
        var footer = new PanelComponent( this.render );
        html += footer.draw( component.footer );
        html += '</div>';
    }
    html += '</div>';
    html += '</div>';
    html += '</div>';
    return html;
};
/**
 * M�todo que cierra la ventana modal
 * @param {String} $idModal
 * @returns {void}
 */
ModalComponent.prototype.close = function( $idModal ){
    var $modal = $('#'+$idModal);
    $modal.modal('hide');
    var $modalback = $('div.modal-backdrop.fade');
    $modalback.remove();
};

function SimpleCollapsableComponent( render ){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
SimpleCollapsableComponent.prototype.draw = function (component) {
    
    var html = "";
    html += '<div ';
    if( !this.render.isEmpty( component.id ) ){
        html += 'id="' + component.id + '" ';
    }
    
    if( !this.render.isEmpty( component.className ) ){
    	 html += 'class="' + component.className + '" ';
     }else{
         html += 'class="container" '; //Default class Bootstrap
     }
        
     html += ' >'; 
     
     var show= "", icon = "";
     
     if (!this.render.isEmpty(component.collapsed)) {
         show= ' in ';
         icon= 'up';
     }else{
    	 icon= 'down';
     }
         
     html += '<div  style="width:100%;height:auto;display:flex;">';  
     
     html += '<div ';
     
     var direction= "";
     if( !this.render.isEmpty( component.direction)){
    	 if (component.direction== "right"){
    		 html += ' style="width:100%"';
    		 direction=component.direction;
    	 }
     }   
    	 
     html += ' ></div> <div';
     
     if(direction!=="right"){
    	 html += ' style="width:100%" ';	
     }
     
     html += ' > ';	 
       
     html += ' <a data-toggle="collapse" data-target="#divCollapse"';
     
     if( !this.render.isEmpty( component.command)){
    	 html += ' onclick="'+this.render.drawCommand( component.command )+'" ';
     }
     
     html += ' >';
     
     html += '<span id="span' + component.id + '" class="glyphicon glyphicon-menu-'+icon+'"></span>'; 
     html += '</a></div> </div>';
     html += '<div id="divCollapse" class="collapse '+show+'">'; 
   
     // Draw components
     for( var i=0; i< component.components.length; i++){
         var element = this.render.componentTemplates[component.components[i].type];
         if( element !== undefined ){
           html += element.draw(component.components[i]);
         }
     }
     
     html += '</div>';
     html += '</div>';
   
    return html;
};

function PanelCollapsableComponent( render ){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
PanelCollapsableComponent.prototype.draw = function (component) {
    var html = "";
    html += '<div class="panel-group ficha-collapse" id="accordion' + component.id + '" ';
    html += " >";
    html +='<div class="panel panel-default">';
    html +='<div class="panel-heading">';
    html +='<h4 class="panel-title">';
    html +='<a data-parent="#accordion' + component.id + '" data-toggle="collapse" href="#' + component.id + 'panel-01" aria-expanded="true" aria-controls="' + component.id + 'panel-01">';
    if (component.label !== null && component.label !== undefined) {
    	if (component.fontSize !== null && component.fontSize !== undefined) {
    		html+="<span style='font-size:"+component.fontSize+"px'>"
    		html +=  this.render.nvl(component.label, "");
    		html+="</span>";
    	}else{
    		html +=  this.render.nvl(component.label, "");
    	}
        
    }
    if (component.collapsed === null || component.collapsed === undefined) {
        component.collapsed = "collapsed";
    }
    html +='</a>';
    html +='</h4>';
    html +='<button type="button" class="collpase-button '+component.collapsed+'" data-parent="#accordion" data-toggle="collapse" href="#' + component.id + 'panel-01"></button>';
    html +='</div>';
    html +=' <div class="panel-collapse collapse ';
    if( component.collapsed !== "collapsed"){
      html += 'in';
    }
    html +='" id="' + component.id + 'panel-01">';
    html +='<div class="panel-body">';
    var renderAux = new Render(this.render.module);
    renderAux.counter = this.render.counter++;
    // FIX
    if (this.render.idToolBarPanelCollapsable !== null && this.render.idToolBarPanelCollapsable !== undefined) {
        renderAux.idToolBarPanelCollapsable = this.render.idToolBarPanelCollapsable;
    }
    //If formPanelCollapsable, pass entity to fields for validtor rules
    if (component.entity !== null && component.entity !== undefined) {
        for (var i = 0; i < component.components.length; i++) {
            component.components[i].entity = component.entity;
        }
    }
    renderAux.components = component.components;
    renderAux.layout = component.layout;
    html += renderAux.render();
    this.render.addLinkers(renderAux.linker);
    this.render.addWatchers(renderAux.watchers);
    this.render.counter = renderAux.counter + 2;    
    html += "</div></div></div></div>";
    return html;
};


function PanelComponent( render ){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
PanelComponent.prototype.draw = function (component) {
    var html = "";
    html += "<div ";
    if( !this.render.isEmpty( component.id ) ){
        html += 'id="' + component.id + '" ';
    }
    if( !this.render.isEmpty( component.className ) ){
        html += 'class="' + component.className + '" ';
    }
    
    if( !this.render.isEmpty( component.hidden )){
    	html += 'style="display: none;" ';
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
        html +="<h"+component.level+">" + this.render.nvl(component.label, "") + "</h"+component.level+"><hr style='margin:10px 0 20px' class='"+component.classNameHR+"'></hr>" ;
    }
    var renderAux = new Render(this.render.module);
    renderAux.counter = this.render.counter++;
    // FIX
    if (this.render.idToolBarPanel !== null && this.render.idToolBarPanel !== undefined) {
        renderAux.idToolBarPanel = this.render.idToolBarPanel;
    }
    //If formPanel, pass entity to fields for validtor rules
    if (component.entity !== null && component.entity !== undefined) {
        for (var i = 0; i < component.components.length; i++) {
            component.components[i].entity = component.entity;
        }
    }
    renderAux.componentTemplates = this.render.componentTemplates;
    renderAux.components = component.components;
    renderAux.layout = component.layout;
    html += renderAux.render();
    this.render.addLinkers(renderAux.linker);
    this.render.addWatchers(renderAux.watchers);
    this.render.counter = renderAux.counter + 2;
    /*if (component.label !== null && component.label !== undefined) {
        html += "</fieldset>";
    }*/
    html += "</div>";
    return html;
};


function PanelTabComponent( render ){
    this.render = render;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
PanelTabComponent.prototype.draw = function (component) {
		
    var html = "";
    html += '<div '; //Componente que embebe todos los componentes
    if( !this.render.isEmpty( component.id ) ){
        html += 'id="' + component.id + '" ';
    }
    
    if( !this.render.isEmpty( component.className ) ){
    	 html += 'class="' + component.className + '" ';
     }else{
         html += 'class="col-md-12" '; //Default class Bootstrap
     }
        
     html += ' ><div id="headerTabs" style="width:100%">'; //Componente que los botones como tabs
    
    	if (!this.render.isEmpty(component.tabs) && !this.render.isEmpty(component.components)) {
    		var sizeTabs= component.tabs.length;
    		var sizeComponents= component.components.length;
    		if( sizeTabs == sizeComponents ){
    			
    			var sizeOfTabs = 100/sizeTabs;
	    		   			    		
	    		var htmlAux ="";
	    		
	    		html += '<ul class="nav nav-tabs" style="display: flex;">';
	    		
	    		for (var i = 0; i < sizeComponents; i++) {
	    		   //Crea cada boton para tab 
	    		   var idTab= component.tabs[i];
	    		   	               
	    		   html += '<li style="display: flex;flex: 1;white-space:normal;width:'+sizeOfTabs+'%;"';
	    		   
	               if(i == 0){
	            	   //Tab by Default active class
	            	   html += 'class="active"';
	               }
	               
	               html += '><a data-toggle="tab" href="#panel'+idTab+'" style="flex: 1">';
	               
	               if (component.labels!== undefined && component.labels!==null){
	                    html += component.labels[i];
	    		   }else{
	    			    html += 'TAB'+i; 
	    		   }
           	   
	               html += '</a></li>';
	               
	               var element = this.render.componentTemplates[component.components[i].type];
	               
	               var panels = "";
	               if( element !== undefined ){
	            	  //Dibuja cada contenido de los tabs
	            	  panels+= '<div id="panel'+idTab+'" class="tab-pane fade '; 
	            	   
	            	  if(i == 0){
	            		  //Tab Content by Default active class
	            		  panels += 'in active'; 
	            	  }
	            	  
	            	  panels += ' ">';
	            	  panels += element.draw(component.components[i]);
	            	  panels += '</div>';
	               }
	               
	               htmlAux += panels;
	               
	            }
	    		
	    		html += '</ul> ';

    		}
    		
    	 }
    
    html += '</div> '; //cierra div de tabs
    html += '<div class="tab-content"> '+ htmlAux + '</div> </div>';
    
    return html;
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
	var html = '<div class="form-group"> <label for="' + component.field + '" class="">' + this.render.nvl(component.label, "") + '</label>';
	if(!this.render.isEmpty(component.tamanoAnchoSelect)&& component.tamanoAnchoSelect > 1 && component.tamanoAnchoSelect <=12){
		html += '<div class="input-group col-md-' + component.tamanoAnchoSelect + ' pull-left">';
	} else {
		html += '<div class="input-group col-md-8 pull-left">';
	}
	
    html += '<select id="' + component.field.replace(/\./g, '_') + '" class="form-control select-style"  name="' + component.field + '"   ';
    if( !this.render.isEmpty(component.disabled) ){
        if( component.disabled === "true" || component.disabled === true ){
            html += ' disabled ';
        }
    }
    if( !this.render.isEmpty(component.onchange) ){
        html += ' onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    html += '></select>';
    if (!this.render.isEmpty(component.popover)) {
        html += '<div class="input-group-addon"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';

        
    }
    html += '</div></div>';    
    this.render.addLinker(new Link(component.field, "SelectFieldComponent", component, 1));
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
        var select = $("#" + metadata.field.replace(/\./g, '_'));
        var options = null;
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
                    options[options.length] = new Option(option.value, option.key);
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
    var html = '<div class="form-group"> ';
    if (!this.render.isEmpty(component.label)) {          
      html += '<label for="';
      html += component.field + '" class="control-label '+(this.render.isEmpty(component.className) ? 'container-fluid' : component.className)+'">';
      html += this.render.nvl(component.label, "") + '</label>';      
    }
    html += '<div class="">';
    html += '<textarea id="' + this.render.replaceAll(component.field,'.','_') + '" ';
    html += ((component.disabled !== null && component.disabled === true) ? ' disabled ' : '');
    html += ' class="form-control "  name="' + component.field + '"  ';
    if (!this.render.isEmpty(component.rows)) {
        html += ' rows="' + component.rows + '" ';
    }
    if (!this.render.isEmpty(component.onchange)) {
        html += ' onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    if (!this.render.isEmpty(component.onblur)) {
        html += ' onblur="' + this.render.drawCommand(component.onblur) + '" ';
    }

    if((component.labelStyle !== null && component.labelStyle === true) || (component.upperCase !== null && component.upperCase === true )){
    	html+='style= "';
    	if((component.labelStyle !== null && component.labelStyle === true )){
    	html += ' border: 0px solid; outline: none; background: transparent;  -webkit-box-shadow: none;-moz-box-shadow: none;box-shadow: none; resize: none; ';
        }
        if((component.upperCase !== null && component.upperCase === true)) {  
        html+= 'text-transform: uppercase; ';
        }
        html+='"';
    }                    
    
    if (!this.render.isEmpty(component.tooltip)) {
        html += 'data-toggle="tooltip" data-placement="bottom" title="' + this.render.nvl(component.tooltip) + '" ';
    }
    html += this.render.module.validator.textFieldRules(component);
    html += ' tabindex="' + (this.render.index++) + '" ></textarea>';
    
    html += '</div></div>';
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
    var html = '<div class="form-group">';
    
    if (!this.render.isEmpty(component.label)) {          
      html += '<label for="';
      html += component.field + '" class="control-label '+(this.render.isEmpty(component.className) ? 'container-fluid' : component.className)+'">';       
      html += this.render.nvl(component.label, "") + '</label>'; 
    }
    html += '<div class="">'; //input-group
    
    html += '<input id="' + this.render.replaceAll(component.field,'.','_') + '" ';
    html += ((component.disabled !== null && component.disabled === true) ? ' disabled ' : '');
    html += ' class="form-control"   name="' + component.field + '"  ';
    if (!this.render.isEmpty(component.onchange)) {
        html += ' onchange="' + this.render.drawCommand(component.onchange) + '" ';
    }
    if (!this.render.isEmpty(component.onblur)) {
        html += ' onblur="' + this.render.drawCommand(component.onblur) + '" ';
    }
    
    if (!this.render.isEmpty(component.tipo)) {		
		html += ' onkeypress="' + this.render.drawTriggerEventTextField(component.tipo,'event') + '" ';
    }
    
    if((component.labelStyle !== null && component.labelStyle === true) || (component.upperCase !== null && component.upperCase === true )){
    	html+='style= "';
    	if((component.labelStyle !== null && component.labelStyle === true )){
    	html += ' border: 0px solid; outline: none; background: transparent;  -webkit-box-shadow: none;-moz-box-shadow: none;box-shadow: none;';
        }
        if((component.upperCase !== null && component.upperCase === true)) {  
        html+= 'text-transform: uppercase;';
        }
        html+='"';
    }
    
    if(!this.render.isEmpty(component.maxlength)){
		 html += ' maxlength="' + this.render.nvl(component.maxlength) + '" ';
	}
    
    if (!this.render.isEmpty(component.tooltip)) {
        html += 'data-toggle="tooltip" data-placement="bottom" title="' + this.render.nvl(component.tooltip) + '" ';
    }
    html += this.render.module.validator.textFieldRules(component);
    html += ' tabindex="' + (this.render.index++) + '" />';
    if (!this.render.isEmpty(component.popover)) {
        html += '<div class="input-group-addon"><a tabindex="-1" class="" role="button" data-toggle="popover" data-placement="left" data-trigger="hover" ';
        html += 'title="' + this.render.nvl(component.popover.title) + '" data-content="' + this.render.nvl(component.popover.content) + '"';
        html += '><span class="' + this.defaultIcon + '" aria-hidden="true"></span></a></div>';
        // Registrar el linker
        this.render.addLinker(new Link(component.field, "TextFieldComponent", component, 5));
    }
    html += '</div>';
    html += '</div>';
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

function TwoListFieldComponent(component) {
    this.label = component.label;
    this.field = component.field;
    this.data = component.data;
    this.model = null;
    this.render = null;
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
TwoListFieldComponent.prototype.draw = function () {
    var component = this;
    var html = "";
    html += '<div  id="twoListFieldComponent_' + component.field + '" >';
    html += this.drawBody();
    html += '</div>';
    this.render.addWatch(new Watch(component.field, "TwoListFieldComponent", component));
    this.render.addLinker(new Link(component.field, "twoList", component));
    return html;
};
/**
 * M�todo que dibuja cuerpo del componente
 * @returns {String}         : html
 */
TwoListFieldComponent.prototype.drawBody = function () {
    var component = this;
    var html = "";
    var i;
    
    html += '<h4>';
    html += this.render.nvl(this.label, "");
    html += '</h4>';

    html += '<div class="panel-body" >';
    html += '<div class="container-fluid"><div class="row">';

    html += '<div class="col-md-5">';
    html += '<select   id="twoListFieldComponent_' + component.field + '_source" multiple class="form-control" size=9>';
    if (this.model !== null && this.model.source !== null) {
        for ( i = 0; i < this.model.source.length; i++) {
            html += '<option value=' + i + '>' + this.render.htmlEncode(this.model.source[i].value) + '</option>';
        }
    }
    html += '</select>';
    html += '</div>';
    html += '<div class="col-md-2">';
    html += '<p><button type="button" class="btn btn-primary btn-block" onclick="' + this.render.module.instanceName + '.render.triggerEvent(\'TwoListFieldComponent\', \'addAll\', \'' + this.field + '\')"><span class="glyphicon glyphicon-forward"></span></button></p>';
    html += '<p><button type="button" class="btn btn-primary btn-block" onclick="' + this.render.module.instanceName + '.render.triggerEvent(\'TwoListFieldComponent\', \'add\', \'' + this.field + '\')"><span class="glyphicon glyphicon-chevron-right"></span></button></p>';
    html += '<p><button type="button" class="btn btn-primary btn-block" onclick="' + this.render.module.instanceName + '.render.triggerEvent(\'TwoListFieldComponent\', \'remove\', \'' + this.field + '\')"><span class="glyphicon glyphicon-chevron-left"></span></button></p>';
    html += '<p><button type="button" class="btn btn-primary btn-block" onclick="' + this.render.module.instanceName + '.render.triggerEvent(\'TwoListFieldComponent\', \'removeAll\', \'' + this.field + '\')"><span class="glyphicon glyphicon-backward"></span></button></p>';
    html += '</div>';
    html += '<div class="col-md-5">';
    html += '<select  id="twoListFieldComponent_' + component.field + '_target" multiple class="form-control" size=9>';
    if (this.model !== null && this.model.target !== null) {
        for (i = 0; i < this.model.target.length; i++) {
            html += '<option value=' + i + '>' + this.render.htmlEncode(this.model.target[i].value) + '</option>';
        }
    }
    html += '</select>';
    html += '</div>';
    html += '</div></div></div>';
    return html;
};
/**
 * M�todo que procesa el componente
 * @param {Object} link : metadata
 * @returns {void}
 */
TwoListFieldComponent.prototype.digest = function ( ) {
    
};
/**
 * M�todo que procesa el evento de 'addAll'
 * @returns {void}
 */
TwoListFieldComponent.prototype.addAll = function ( ) {
    for (var i = 0; i < this.model.source.length; i++) {
        this.model.target.push(this.model.source[i]);
    }
    this.model.source = [];
    var html = this.drawBody();
    $("#twoListFieldComponent_" + this.field).html(html);
};
/**
 * M�todo que procesa el evento de 'removeAll'
 * @returns {void}
 */
TwoListFieldComponent.prototype.removeAll = function ( ) {
    for (var i = 0; i < this.model.target.length; i++) {
        this.model.source.push(this.model.target[i]);
    }
    this.model.target = [];
    var html = this.drawBody();
    $("#twoListFieldComponent_" + this.field).html(html);
};
/**
 * M�todo que procesa el evento de 'add'
 * @returns {void}
 */
TwoListFieldComponent.prototype.add = function ( ) {
    var id = "twoListFieldComponent_" + this.field + "_source";
    var _this = this;
    var _toRemove = [];
    $("#" + id + " option:selected").each( function () {
        var index = $(this).val();
        _this.model.target.push(_this.model.source[index]);
        _toRemove[_toRemove.length] = index;
    });
    for (var i = _toRemove.length - 1; i >= 0; i--) {
        this.model.source.splice(_toRemove[i], 1);
    }
    var html = this.drawBody();
    $("#twoListFieldComponent_" + this.field).html(html);
};
/**
 * M�todo que procesa el evento de 'remove'
 * @returns {void}
 */
TwoListFieldComponent.prototype.remove = function ( ) {
    var id = "twoListFieldComponent_" + this.field + "_target";
    var _this = this;
    var _toRemove = [];
    $("#" + id + " option:selected").each(function () {
        var index = $(this).val();
        _this.model.source.push(_this.model.target[index]);
        _toRemove[_toRemove.length] = index;
    });
    for (var i = _toRemove.length - 1; i >= 0; i--) {
        this.model.target.splice(_toRemove[i], 1);
    }
    var html = this.drawBody();
    $("#twoListFieldComponent_" + this.field).html(html);
};
/**
 * M�todo que procesa los eventos del componente
 * @param {String} event
 * @param {Object|String|Number} parameters
 * @returns {void}
 */
TwoListFieldComponent.prototype.digestEvent = function (event) {
    switch (event) {
        case "addAll":
            this.addAll();
            break;
        case "add":
            this.add();
            break;
        case "remove":
            this.remove();
            break;
        case "removeAll":
            this.removeAll();
            break;
    }
};
/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {Object} watch               : metadata
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
TwoListFieldComponent.prototype.notify = function (watch, data) {
    this.size = data.length;
    this.model = data;
    var html = this.drawBody();
    $("#twoListFieldComponent_" + watch.id).html(html);
};
/**
 * Metodo que obtiene el valor del campo proporcionado($key)
 * @param {String} $key
 * @returns {String}
 */
TwoListFieldComponent.prototype.getValue = function( $key ){
    return this.model;
};
/**
 * Metodo que cambia el valor del campo proporcionado($key)
 * @param {String} $key
 * @param {String|Number|Boolean} $value
 * @returns {void}
 */
TwoListFieldComponent.prototype.setValue = function( $key, $value ){
    this.notify({id:$key}, $value);
    this.format($key);
};
/**
 * Metodo que asigna un formato al valor del campo
 * @param {String} $key
 * @returns {void}
 */
TwoListFieldComponent.prototype.format = function( $key ){
    //-
};
/**
 * M�tod que procesa los eventos destinados para el componente
 * @param {String} event               : Identificador del evento
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s) que recibir� el evento
 * @returns {void}
 */
TwoListFieldComponent.prototype.triggerEvent = function ($event, $key, $model) {
    switch( $event ){}
};

function UserProfileComponent( render ){
    this.render = render;
    this.model = this.render.module.controller.model.userProfile;
}
UserProfileComponent.prototype.draw = function (component) {
	var html = '<div class="list-group"';
    if( !this.render.isEmpty( component.id ) ){
        html += 'id="userProfile_' + component.id + '" ';
    }    
    html += " > <li class='list-group-item col-sm-6' style='background-color: #c6c6c6; padding-top: 0px; padding-bottom: 0px'>";
    html += '<span class="glyphicon glyphicon-user" aria-hidden="true"> </span>';
    html += '<span class="glyphicon-class" style="padding-left:6px">';
    if( !this.render.isEmpty( this.model ) ){
      html += this.render.nvl(this.model.nombreCompleto, "");
    }
    html += "</span>";
    html += "</li>";
    
    html += "<li class='list-group-item col-sm-6' style='background-color: #c6c6c6; padding-top: 0px; padding-bottom: 0px'><span class='glyphicon glyphicon-map-marker' aria-hidden='true'> </span>";
    html += '<span class="glyphicon-class" style="padding-left:6px">';    
    if( !this.render.isEmpty( this.model ) ){
      html += this.render.nvl(this.model.subdelegacion, "");
    }
    html += "</span></li>";
    
    html += "<li class='list-group-item col-sm-6' style='background-color: #c6c6c6; padding-top: 0px; padding-bottom: 0px'><span class='glyphicon glyphicon-credit-card' aria-hidden='true'> </span>";
    html += '<span class="glyphicon-class" style="padding-left:6px">';    
    if( !this.render.isEmpty( this.model ) ){
      html += this.render.nvl(this.model.perfilDescripcion, "");
    }
    html += "</span></li>";
    
    html += "<li class='list-group-item col-sm-6' style='background-color: #c6c6c6; padding-top: 0px; padding-bottom: 0px'><span class='glyphicon glyphicon-calendar' aria-hidden='true'> </span>";
    html += '<span class="glyphicon-class" style="padding-left:6px">';
    html += this.dateFormat(new Date());
    html += "</span></li>";
    
    html += '</div>';
    return html;
};

String.prototype.paddingLeft = function (paddingValue) {
	   return String(paddingValue + this).slice(-paddingValue.length);
};

String.prototype.format = function () {
 var args = arguments;
 return this.replace(/{(\d+)}/g, function (match, number) {
     return typeof args[number] != 'undefined' ? args[number] : match;
 });
};

UserProfileComponent.prototype.dateFormat = function(date){
	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	day = day.toString().paddingLeft("00");
	month = month.toString().paddingLeft("00");

	  return "{0}/{1}/{2}".format(day, month,year);
};

UserProfileComponent.prototype.notify = function( watch, model ){
    this.watch = watch;
    this.model = this.render.module.controller.model[watch.model];
    var html = this.draw(watch);
    $("#userProfile_" + watch.id).html( html );
};

/*jshint -W061 */
function Validator( module){
    this.module = module;
}
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
    var _this = this;
    if( data !== null && data.length > 0 ){
        $.each( data, function(){ 
            //model[ this.name] = this.value; 
            _this.addAtributeToObject( model, this.name, this.value );
            }
        );
    }
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
    var names = attribute.split(".");
    var reference = obj;
    for(var i=0; i< names.length; i++){
        if( reference[names[i]] === null || reference[names[i]] === undefined){
            reference[names[i]] = {};
        }
        reference = reference[names[i]];
    }
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
    console.log(validatorAux.rules);
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
});

function Link(id, type, metadata, priority) {
    this.id = id;
    this.type = type;
    this.metadata = metadata;
    this.priority = priority;
}

function CheckBoxComponent( render ){
    this.render = render;
}

CheckBoxComponent.prototype.draw = function (component) {
    var html = "<table> <tr><td style=\"width:200px;height:10px;text-align:left;\"><label style=\"text-align:left;\">";
    html += this.render.nvl(component.label, "") + '</label></td>';
    html +=" <td><div class=\""+this.render.nvl(component.id) +"\"><input type=\"checkbox\" value=\"None\" id=\""+this.render.nvl(component.id) +"\" name=\"check\" />";
    html +="<label id=\"label"+this.render.nvl(component.id) +"\" for=\""+this.render.nvl(component.id) +"\"></label>";   
    html += "</div></td></tr></table><br>";
   
    return html;
}

function Render(module) {
    if (module !== null) {
        this.module = module;
        this.container = module.container;
        this.components = module.metadata.ui.components;
        this.layout = module.metadata.ui.layout;
    }
    this.counter = 0;
    this.index = 1;
    this.linker = [];
    this.watchers = [];
    this.dispatchEvent = true;
    this.componentTemplates = {
        'PanelComponent'            : new PanelComponent(this),
        'FormPanelComponent'        : new FormPanelComponent(this),
        'TextFieldComponent'        : new TextFieldComponent(this),
        'ButtonComponent'           : new ButtonComponent(this),
        'LabelComponent'            : new LabelComponent(this),
        'SelectFieldComponent'      : new SelectFieldComponent(this),
        'DatePickerFieldComponent'  : new DatePickerFieldComponent(this),
        'ModalComponent'            : new ModalComponent(this),
        'DetailComponent'           : new DetailComponent(this),
        'CardLayoutComponent'       : new CardLayoutComponent(this),
        'HandlerCardLayoutComponent': new HandlerCardLayoutComponent(this),
        'HiddenComponent'           : new HiddenComponent(this),
        'CurrencyFieldComponent'    : new CurrencyFieldComponent(this),
        'UserProfileComponent'    : new UserProfileComponent(this),
        'GridComponent'    : new GridComponent(this),
        'GridGroupComponent' : new GridGroupComponent(this),
        'AlertComponent': new AlertComponent(this),
        'SimpleCollapsableComponent' : new SimpleCollapsableComponent(this),
        'PanelCollapsableComponent': new PanelCollapsableComponent(this),
        'PanelTabComponent' :  new PanelTabComponent(this),
        'TextAreaFieldComponent': new TextAreaFieldComponent(this),
        'CheckBoxFieldComponent': new CheckBoxFieldComponent(this),
        'HRComponent': new HRComponent(this),
        'ButtonGroupComponent': new ButtonGroupComponent(this),
  		'CheckBoxComponent': new CheckBoxComponent(this),
  		'HeaderComponent': new HeaderComponent(this),
		'PanelCheckBoxComponent': new PanelCheckBoxComponent(this),
  		'linea': new linea(this),
  		'slaiderr': new slaiderr(this),
  		'cargarValores': new cargarValores(this)
    };
}
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
 * M�todo que genera el html de un evento
 * @param {String} component
 * @returns {String}
 */
Render.prototype.drawTriggerEvent = function (type, event, key, model) {
    var html = "";    
    html += this.module.instanceName + ".render.triggerEvent('" + type + "','"+event+"','"+ key+"','"+model+"' )";
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
Render.prototype.getWatch = function( key, type ){
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
 * module.render.triggerEvent('TextFieldComponent','validate','CAMPO_09', {isValid:true, message :'campo obligatorio'});
 */
Render.prototype.triggerEvent = function (type, event, key, model) {
    var component = this.componentTemplates[type];
    component.component = this.getComponentById(key);
    if( !this.isEmpty(component) ){
        component.triggerEvent(event, model);
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
  if( !this.isEmpty(component) ){
    var metadata = this.getComponentById(key);
    component.notify(metadata, value);
  }
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
        if( component !== null && component !== undefined ){
            component.digest( localLinks[i] );
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
        if( _this.dispatchEvent ){
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
                if( component !== null && component !== undefined ){ 
                    switch( true ){
                        case ( components[componentIndex].type === "HiddenComponent" ):
                            component.draw( components[componentIndex] );
                            j--;
                            break;
                        case ( components[componentIndex].type === "ModalComponent" ):
                            var modal = component.draw( components[componentIndex] );
                            this.addModal( modal );
                            j--;
                            break;
                        default:
                            html += "<div class='col-md-" + (row[j].span) + "'>";
                            html += component.draw( components[componentIndex] );
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
    $("#modals").append( html );
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
Render.prototype.modelToForm = function (formId, model, prefix) {
    var _this = this;
    if (prefix === undefined) {
        prefix = "";
    }
    if( model !== null && typeof model === 'object'){
    $.each( model, function (key, value) {
        if (value !== null && typeof value === 'object') {
            _this.modelToForm(formId, value, key + ".");
        } else {
            var matches = $.find("#" + formId + " :input[name='" + prefix + key + "']");
            if (matches.length === 1) {
              if (matches[0].type === "checkbox") {
                if (value === "on" || value === true || value === "true" ) {
                  $("#" + matches[0].id).prop('checked', true);                        
                }else {
                  $("#" + matches[0].id).prop('checked', false);
                }
                $("#" + matches[0].id).change();
              }
              else{
                $("#" + formId + " :input[name='" + prefix + key + "']").val(value);
                $("#" + formId + " :input[name='" + prefix + key + "']").change();
              }
            } else {
                // Puede ser radio
                $("#" + formId + " :input[name='" + prefix + key + "'][value='" + value + "']").prop('checked', true);
            }
        }
    });
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
    if( !this.isEmpty(value)){
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
    if( this.linker.length === 0){
        this.linker[this.linker.length] = link;
    }else{
        for (i = 0; i < this.linker.length; i++) {
            if( this.linker[i].priority >= link.priority ){
                break;
            }
        }
        this.linker.splice( i, 0, link );
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
Render.prototype.getComponentById = function (idComponente) {
    var component;
    for (var i = 0; i < this.components.length; i++) {
        if (this.components[i].id === idComponente) {
            return this.components[i];
        }
        if( !this.isEmpty( this.components[i].components ) ){
          component =  this.getComponentByIdContainer( idComponente, this.components[i] );
          if( !this.isEmpty(component.id) && component.id === idComponente ){
            return component;
          }
        }
        if( !this.isEmpty( this.components[i].body ) && !this.isEmpty( this.components[i].body.components ) ){
          component =  this.getComponentByIdContainer( idComponente, this.components[i].body );
          if( !this.isEmpty(component.id) && component.id === idComponente ){
            return component;
          }
        }
    }
    return {};
};
Render.prototype.getComponentByIdContainer = function (idComponente, container) {
    var component;
    for (var i = 0; i < container.components.length; i++) {
        if (container.components[i].id === idComponente) {
            return container.components[i];
        }
        if( !this.isEmpty( container.components[i].components ) ){
          component = this.getComponentByIdContainer( idComponente, container.components[i] );
          if( !this.isEmpty(component.id) && component.id === idComponente ){
            return component;
          }
        }
        if( !this.isEmpty( container.components[i].body ) && !this.isEmpty( container.components[i].body.components ) ){
          component =  this.getComponentByIdContainer( idComponente, container.components[i].body );
          if( !this.isEmpty(component.id) && component.id === idComponente ){
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
Render.prototype.closeModal = function ( $idModal ) {
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
Render.prototype.showFieldFormPanelComponent = function ( $formId, $fieldList, $enabled ) {
    var cardMetadata = this.getWatch('card', "CardLayoutComponent");
    var metadata = this.getWatch($formId, "FormPanelComponent");
    var currentCardId = cardMetadata.current;
    var currentCardModel = cardMetadata.model;
    
    if($enabled === false){
        var remove = this.removeMetadataFormField(metadata, $fieldList);
        if(remove){
            this.componentTemplates.CardLayoutComponent.triggerEvent("card", currentCardModel, currentCardId );
        }
    } else {
        var completeMetadata = this.componentTemplates.CardLayoutComponent.getMetadata().cards[cardMetadata.current];
        var add = this.addMetadataFormField(completeMetadata, metadata, $fieldList, currentCardId);
        if(add){
            this.componentTemplates.CardLayoutComponent.triggerEvent("card", currentCardModel, currentCardId );
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
Render.prototype.removeMetadataFormField = function ( $metadata, $fieldList ) {
    var remove = false;
    var components = [];
    for(var index=0 ; index<$metadata.components.length ; index++){
        var include = true;
        for(var element=0 ; element<$fieldList.length  ; element++){
            if( !this.isEmpty($fieldList[element]) ){
                if( $fieldList[element] === $metadata.components[index].field ){
                    delete this.module.controller[ $metadata.model ][$fieldList[element]];
                    delete $fieldList[element];
                    remove = true;
                    include = false;
                    break;
                }
            }
        }
        if( include ){
            components.push($metadata.components[index]);
        }
    }
    if( remove ){
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
Render.prototype.addMetadataFormField = function ( $completeMetadata, $metadata, $fieldList, $currentCardId ) {
    var complete = this.componentTemplates.CardLayoutComponent.getMetadata();
    var realElementsLength = $metadata.components.length;
    var metadataLength = $metadata.components.length;
    var hasToolbar = false;
    var add = false;
    if( !this.isEmpty( complete.cards[$currentCardId].managed ) ){
        if( complete.cards[$currentCardId].managed === true ){
            hasToolbar = true;
            realElementsLength -= 2;
            metadataLength -= 2;
        }
    }
    if($completeMetadata.components.length > realElementsLength ){
        var components = [];
        var elementCounter = 0;
        var fieldCounter = 0;
        for(var index = 0 ; index<$completeMetadata.components.length ; index++){
            if( elementCounter < metadataLength && $completeMetadata.components[index].type === "LabelComponent"){
                elementCounter++;
                components.push($completeMetadata.components[index]);
                add = true;
            } else if( elementCounter < metadataLength && $completeMetadata.components[index].field === $metadata.components[elementCounter].field ){
                elementCounter++;
                components.push($completeMetadata.components[index]);
                add = true;
            } else if( fieldCounter < $fieldList.length && $completeMetadata.components[index].field === $fieldList[fieldCounter]){
                fieldCounter++;
                components.push($completeMetadata.components[index]);
                add = true;
            }
        }
        if(add){
            if(hasToolbar){
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
    var select = $("#" + $key );
    if (!this.isEmpty($enabled)) {
        if ($enabled === true) {
            select.removeAttr("disabled");
        } else {
            select.attr("disabled", "disabled");
            if( !this.isEmpty( $emptyForm ) ){
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
            if( !this.isEmpty( $emptyForm ) ){
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
            if( !this.isEmpty( $emptyForm ) ){
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
            if( !this.isEmpty( $emptyForm ) ){
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
Render.prototype.getValue = function( $fieldName ){
    if( this.module.controller.model ){
        if( this.module.controller.model[ $fieldName ] ){
            var value = this.module.controller.model[$fieldName];
            switch( true ){
                case isNaN(value):
                    return this.module.controller.model[$fieldName];
                case (value.indexOf('.') > -1):
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
Render.prototype.setValue = function( $fieldName, $value ){
    if( this.module.controller.model ){
        this.module.controller.model[ $fieldName ] = $value;
        var component = $('#' + $fieldName);
        if( !this.isEmpty( component ) ){
            switch( true ){
                case component.is('select'):
                    this.notify("SelectFieldComponent", $fieldName, $value ); break;
                case component.is('input'):
                    var currentClass = component.attr('class');
                    if( currentClass.indexOf('hasDatepicker') > -1 ){
                        this.notify( "DatePickerFieldComponent", $fieldName, $value );
                    } else {
                        this.notify( "TextFieldComponent", $fieldName, $value );
                    }
                    break;
                default:
                    // -
                    break;
            }
        } else {
            this.notify( "HiddenComponent", $fieldName, $value );
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
Render.prototype.addEventListener = function( $metadata, $callback ){
    if( this.module.controller.model ){
        var _this = this;
        var component = this.componentTemplates[ $metadata.type ];
        if( component !== null ){
            if( $callback !== null ){
                this.triggerEvent( $metadata.type , 'bind', $metadata.field, $callback );
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
Render.prototype.showAlert = function( $title, $message ){
    showAlert($title, $message, null, null, "Aceptar");
};
/**
 * M�todo que se encarga de obtener el metadata del CardLayoutComponent actual
 * @returns {Object}
 */
Render.prototype.getCurrentMetadata = function(){
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
Render.prototype.setValid = function($fieldMetadata, $isValid, $message){
    if( $isValid ){
        this.module.controller.model[$fieldMetadata.field] = $( '#' + $fieldMetadata.field ).val();
    }
    this.notify( $fieldMetadata.type, $fieldMetadata.field, this.module.controller.model[$fieldMetadata.field] );
    this.triggerEvent($fieldMetadata.type, "validate", $fieldMetadata.field, {isValid:$isValid, message:$message});
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
            aux = this.getForms( $element.cards[ $element.current ] );
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
    if(token!=newtoken)
    while(string.indexOf(token) > -1) {
        string = string.replace(token, newtoken);
    }
    return string;
};

function Watch(id, type, field) {
    this.id = id;
    this.type = type;
    this.field = field;
}


Render.prototype.drawTriggerEventTextField = function (type, event) {
    var html = " return ";    
    html += this.module.instanceName + ".render.setKey('" + type + "',"+event+" )";
    return html;
};

Render.prototype.setKey = function (typeInput, event) {	
    switch (typeInput) {
        case 'number':
            return this.inputKey(event, /\d/);
        case 'text':
        	return this.inputKey(event, /[A-Za-z]/);
        default:
            return null;
    }
}

Render.prototype.inputKey = function (event, patron){
	tecla = (document.all) ? event.keyCode : event.which;
	if (tecla==8) return true;
	te = String.fromCharCode(tecla);
	return patron.test(te);
}


function HeaderComponent(render) {
    this.render = render;    
}
/**
 * M�todo que dibuja el componente
 * @param {Object} component : metadata
 * @returns {String}         : html
 */
HeaderComponent.prototype.draw = function (component) {    
    var html = "";
    this.component = component;
	this.model = component.model;
    html += '<div  id="grid_' + component.id + '" " >';
    html += this.drawBody();
    html += '</div>';

	this.render.addLinker(new Link(component.id, "HeaderComponent", component));
    return html;
};
/**
 * M�todo que dibuja cuerpo del componente
 * @returns {String}         : html
 */
HeaderComponent.prototype.drawBody = function () {
    var component = this.component;    
    var model = component.model;
    
    var i;
    var html = "";       
	
	if( !this.render.isEmpty( this.render.module.controller.model[model] ) ){
          var data = this.render.module.controller.model[model].data;
		  html +="<table class='table table-hover table-bordered table-responsive' ";
		  var rp = '';
          if ( !this.render.isEmpty( data ) ) {
			  for (i = 0; i < data.length; i++) {
				  rp = data[i].registroPatronal;
                  html += "<tr>";
				  html += "<th> image </th>";
                  for (var j = 0; j < component.columns.length; j++) {					
					html += "<th>";
					html += this.render.nvl(component.columns[j].label);
					html += "</th>";	
					html += "<td ";
					html+=" > ";
					var _aux = eval("data[i]." + component.columns[j].name);
					html += _aux === null ? "" : this.render.htmlEncode(_aux);
					html += "</td>";
                  }
                  html += "</tr>";
				  html += "<tr>";
				  html += "<td colspan='9'>";
				  
//--------------------------------Grid interno ----------------------------------				  
				  html += "<table class='table table-hover table-bordered table-responsive' ";
        
					if((component.styled !== null && component.styled === true)){
						html +=	" style=' ";
						
						 if(!this.render.isEmpty(component.fontSize)){
							
							html +=	" font-size:"+component.fontSize+"; ";//16px;
						 }
						 
						 if((component.breakWord !== null && component.breakWord === true)){
							 html +=" table-layout: fixed; width: 100%; ";
						 }
						
						html +=	" '";
					}					
					html+=">"; 			
					html += "<tr>";
					var headers = "";
					var k = 0;
					for (k = 0; k < component.subcolumns.length; k++) {
						headers += "<th ";            
						if(component.subcolumns[k].width !== null && component.subcolumns[k].width >= 0 && component.subcolumns[k].width <=100){
							headers += " width= '"+component.subcolumns[k].width+"' %";
						}            
						headers+= " >";
						headers += this.render.nvl(component.subcolumns[k].label);
						headers += "</th>";
					}
					html += headers;
					html += "</tr>";
					html += "<tbody>";
					if( !this.render.isEmpty( this.render.module.controller.model[model] ) ){
					  var data = this.render.module.controller.model[model].data;
					  var l = 0;					  
					  if ( !this.render.isEmpty( data ) ) {
						  for (l = 0; l < data.length; l++) {
							  if (data[l].registroPatronal === rp) {
								  var r = 0;
								  var periodos = data[l].periodos;
								  for (r = 0; r < periodos.length; r++) {
									  html += "<tr>";
									  var t = 0;
									  for (var t = 0; t < component.subcolumns.length; t++) {
										  html += "<td ";
										  if((component.subcolumns[t].breakWord !== null && component.subcolumns[t].breakWord === true)){
											  html+="style='word-wrap: break-word'";
										  }
										  html+=" > ";								  
										  var _aux = eval("periodos[r]." + component.subcolumns[t].name);
										  html += _aux === null ? "" : this.render.htmlEncode(_aux);
										  html += "</td>";
									  }
									  html += "</tr>";
								  }
								  break;
							  }
						  }
					  }
					}
					html += "</tbody>";
				    html += "</table>";		
//--------------------------------Grid interno ----------------------------------					
										
				  html += "</td>";
				  html += "</tr>";
				}
		  }
		  html +="</table>";
	}    	
    return html;
};

HeaderComponent.prototype.digest = function (linker) {
  eval(this.render.module.instanceName + ".service." + linker.metadata.data + "('" + linker.metadata.id + "', " + this.render.module.instanceName + ",1)");
};

/**
 * M�todo que procesa los eventos del componente
 * @param {String} event
 * @param {Object|String|Number} parameters
 * @returns {void}
 */
HeaderComponent.prototype.triggerEvent = function (event, parameters) {
    switch (event) {
        case "page":
            eval(this.render.module.instanceName + ".service." + this.component.data + "('" + this.component.id + "', " + this.render.module.instanceName + " , " + parameters + ")");
            break;
    }
};

/**
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos
 * @param {Object} watch               : metadata
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
HeaderComponent.prototype.notify = function (metadata, modelName) {
    this.component = metadata;    
    this.model = modelName;
    var html = this.drawBody();
    $("#grid_" + this.component.id).html( html );
};

function linea(render){
    this.render = render;
};

linea.prototype.draw = function (component) {
	  var html = '';	   	  
	  html+='<div><hr style=\"height: 1px; background-color: #E6E6E6; top:0px;\"></div>'; 
	    return html;
	    };


function slaiderr(render){
    this.render = render;
};

slaiderr.prototype.draw = function (component) {
	  var html = '';	   	  
	debugger
	 html+= "<div id=\""+ component.id +">";
	 html+=	"<table>";
	 html+=		"<tr>";
	 html+=		"	<td>";
	 html+=		"		<button style=\"color:#A4A4A4;width:20px;height:32px;background-color:#E6E6E6; border:none;padding:0.2em 0;outline:none;\", onclick=\"anteriorNSS()\"><</button>";
	 html+=		"	</td>";
	 html+=		"	<td>";
	 html+=		"	  <div style=\"width:403px;overflow:hidden;\">";
	 html+=		"		<div id=\"contBoton\">";
	 var conteo = component.components.length;
		for( var i=0; i< component.components.length; i++)
		{
  	      var element = this.render.componentTemplates[component.components[i].type];
  	              html += element.draw(component.components[i]); 	    	 
  	    }    
	 html+=		"		</div>";
	 html+=		"		</div>";
	 html+=		"	</td>";
	 html+=		"	<td style=\" left: 424px;top:0px;position:absolute;overflow: hidden;\">";
	 html+=		"		<button style=\"color:#A4A4A4;width:20px;height:32px;background-color:#E6E6E6; border:none;padding:0.2em 0;outline:none;\", onclick=\"module.controller.siguienteNSS()\">></button>";
	 html+=		"	</td>";
	 html+=		"</tr>";
	 html+=	"</table>";
	 html+=	"</div>";
		
	    return html;
	    };


function cargarValores(render){
    this.render = render;
};



cargarValores.prototype.draw = function (component) 
	{
		debugger
	var nss =['34454554545','56780987657','91019876543','91019876543','019283745','019283745'];
	var html='';
	var i=0;
	for(i; i < nss.length; i++)
	{
		if(i===0)
		{
			html+="<span id=\"spanestilo\"><button id=\"btonselect\">"+ nss[i] +"</button></span>";
		}
		else
		{
			if(nss[i]==null)
			{
				html+=""
			}
			else
			html+="<span id=\"spanestilo\"><button id=\"btonselect\">"+ nss[i] +"</button></span>";
		}			
	}
	return html;	
};
function PanelCheckBoxComponent( render ){
    this.render = render;
};

PanelCheckBoxComponent.prototype.draw = function (component) {
	  var html = "";

	    	for( var i=0; i< component.components.length; i++){
	    	      var element = this.render.componentTemplates[component.components[i].type];
	    	      var ids = 0;
	    	      if(i==0)
	    	    	  {
	    	    	  	html +="<div class=\"col-md-3\"> ";
	    	    	  }
	    	      else if(i==1)
	    	    	  {
	    	    	  	html +="<div class=\"col-md-6\"> ";
	    	    	  }
	    	    	  else if(i==2)
		    	      {
		    	    	  	html +="<div class=\"col-md-3\"> ";
		    	      }

	    	          html += element.draw(component.components[i]);
	    	    	  html += "</div>";
	    	    }    
	    return html;
	    };


/*
function ButtonComponent(render) {
    this.render = render;
}

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
    if (!this.render.isEmpty(component.estadoAtendidaOperada) &&  component.estadoAtendidaOperada  === true &&($("#estatus").val() != "Atendida"|| $("#estatus").val() !="Operada")) {
        html += 'disabled="disabled"  ';
    }    if (!this.render.isEmpty(component.id)) {
        html += 'id="' + this.render.nvl(component.id) + '" ';
    }
    html += '>';
    
    html += '<span class="glyphicon glyphicon-' + component.icon + '"></span>' ;
    
    html += "<span ";
    if (!this.render.isEmpty(component.breakWord) && component.breakWord === true) {
        html += " style='white-space: normal' ";
    }
    
    html += " >" + this.render.nvl(component.label, "") + "</span>";
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
*/
/*
function ButtonGroupComponent( render ){
    this.render = render;
}

ButtonGroupComponent.prototype.draw = function (component) {
	var html = "";
	if(!this.render.isEmpty( component.direction)){
		if(component.direction === "right"){
		 html += "<div class='pull-right'>";   
		}else {
		 html += "<div class='pull-left'>";   
		}
	
	}else{
		html += "<div class='pull-right'>";   
	}
	
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
*/


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
	html += '<div class="checkbox '+((component.disabled !== null && component.disabled === true) ? ' disabled ' : '')+'"> <label ><input value="true" type="checkbox"  name="'+component.field+'" id="' + this.render.replaceAll(component.field,'.','_') + '" '+((component.disabled !== null && component.disabled === true) ? ' disabled ' : '') + ((component.checked !== null && component.checked === true) ? ' checked ' : '');
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
		    	// // console.log(linker.metadata.components[i].id);
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
    html += '<div  id="grid_' + component.id + '"';
    
    if(!this.render.isEmpty(component.fontSize)){
     	html +=	' style="font-size:'+component.fontSize+';" ';
     }
    
    html += ' >';
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
    	
    	if(component.desfasado){
    		html += "<div style='overflow-x: auto'>";
    	}
        
        html +=	"<table class='table table-hover table-bordered table-responsive' ";
        
        if((component.styled !== null && component.styled === true)){
        	html +=	" style=' ";
        	 
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
                        this.render.module.controller.model[model].data !== null &&
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
                      if(!this.render.isEmpty(component.columns[j].list)){
                    	  if(!this.render.isEmpty(component.columns[j].listName)){
                    		  var listName= eval("data[i]." + component.columns[j].listName);
                    		  for (var m =0; m <listName.length; m++){
                    			  if ( !this.render.isEmpty(component.columns[j].href) ) {
                                      html += '<a href="#" ';
                                      html += ' onclick="' + this.render.module.instanceName + '.controller.' + component.columns[j].href + '(' + i + ')" >';
                                    }
                    			      
                    			  var _auxList = "";
                    			  if(!this.render.isEmpty(component.columns[j].name)){
	                    			  _auxList = eval("data[i]." + component.columns[j].listName+"["+m+"]."+component.columns[j].name);
	                                  html += _auxList === null ? "" : this.render.htmlEncode(_auxList);
                    			  }else{
                                      _auxList = eval("data[i]." + component.columns[j].listName+"["+m+"]");
	                                  html += _auxList === null ? "" : this.render.htmlEncode(_auxList);
                    				  
                    			  }
	                               
                                  if ( !this.render.isEmpty(component.columns[j].href) ) {
                                      html += '</a>';
                                    }
                                    
                                  if(!this.render.isEmpty(component.columns[j].name)){
	                                  if(m != listName.length -1){
	                               	   html += '<hr>';
	                                  }
                                  }else{
                                	  html += '</br>';
                                  }
                    			  
                    		  }
                    	  }
                    	 
                      }else if (!this.render.isEmpty(component.columns[j].text)){ 
                    	  
                    	  if ( !this.render.isEmpty(component.columns[j].href) ) {
                              html += '<a href="#" ';
                              html += ' onclick="' + this.render.module.instanceName + '.controller.' + component.columns[j].href + '(' + i + ')" >';
                            }
            			  
                              html += component.columns[j].text;
                          
                          if ( !this.render.isEmpty(component.columns[j].href) ) {
                              html += '</a>';
                            }
                    	  
                      }else{
                    	  
                    	  if ( !this.render.isEmpty(component.columns[j].href) ) {
                              html += '<a href="#" ';
                              html += ' onclick="' + this.render.module.instanceName + '.controller.' + component.columns[j].href + '(' + i + ')" >';
                            }
                    	  
	                    	  var _aux = eval("data[i]." + component.columns[j].name);
	                          html += _aux === null ? "" : this.render.htmlEncode(_aux);
                          
                          if ( !this.render.isEmpty(component.columns[j].href) ) {
                              html += '</a>';
                            }
                    	  
                      }
                      html += "</td>";
                  }
                  html += "</tr>";
              }
          }
        }
        html += "</tbody>";
        html += "</table>";
        if(component.desfasado){
    		html += "</div>";
    	}
        if( !this.render.isEmpty( this.render.module.controller.model[model] )  ){
          var currentPage = this.render.module.controller.model[model].currentPage;
          var pageSize = this.render.module.controller.model[model].pageSize;
          var totalOfRecords = this.render.module.controller.model[model].totalOfRecords;
          var totalOfPages = Math.ceil(totalOfRecords / pageSize);
          
          var min= 2;
          
          var minPage = currentPage - min; 
          if (minPage < 1) { 
              minPage = 1; 
          }
          
          var max= 2;
          if( !this.render.isEmpty( component.maxPage )){
        	  max = component.maxPage -1;  
          }
          
          if( !this.render.isEmpty( component.maxPage ) && currentPage > 1){
        	  max = component.maxPage - 2;  
          }
          
          if( !this.render.isEmpty( component.maxPage ) && currentPage > 2){
        	  max = component.maxPage - 3;  
          }
          
          var maxPage = currentPage + max; 
          if (maxPage > totalOfPages) {
              maxPage = totalOfPages;
          }
          
          var minPage = currentPage - 5;
      if (minPage < 1) {
        minPage = 1;
      }
      var maxPage = currentPage + 5;
      if (maxPage > totalOfPages) {
        maxPage = totalOfPages;
      }
      if( (maxPage - minPage)<10 && maxPage < totalOfPages ){
        var faltantes = 10-(maxPage - minPage);
        if( totalOfPages >= (maxPage+faltantes) ){
          maxPage += faltantes;
        }else{
          maxPage = totalOfPages;
        }
      }
      
          
          if( totalOfRecords > pageSize){
              html += '<div style="text-align:center"><ul class="pagination middle">';
              
              html += '<li ';
              if (minPage === 1) {
              	html += ' style="pointer-events: none;cursor: default;"';
              	html += ' class="disabled" ';  
              }
              html += '><a href="#" onclick="' + this.render.drawTriggerEvent( "GridComponent", "page", this.component.id , "1" ) + '" >&#10092;&#10092;</a></li>';
              
              //Simple Previous Cursor
              var previousPage = currentPage - 1;
              html += '<li ';
              if (currentPage === 1) {
                  html += ' class="disabled" ';
                  html += ' style="pointer-events: none;cursor: default;"';
              }
              html += '><a href="#" onclick="' + this.render.drawTriggerEvent( "GridComponent", "page", this.component.id , ""+previousPage ) + '" >&#10092;</a></li>';
              
              //List of Number of Pages
              for (var page = minPage; page <= maxPage; page++) {
                  html += '<li ';
                  if (page === currentPage) {
                      html += ' class="active" ';
                  }
                  html += '><a href="#" onclick="' + this.render.drawTriggerEvent( "GridComponent", "page", this.component.id , ""+page )  + '" >' + page;
                  if (page === currentPage) {
                      html += '<span class="sr-only">(current)</span>';
                  }
                  html += '</a></li>';
              }
              
              //Simple Next Cursor
              var nextPage = currentPage + 1;
              html += '<li ';
              if (nextPage > totalOfPages) {
                  html += ' class="disabled" ';
                  html += ' style="pointer-events: none;cursor: default;"';
              }
              html += '><a href="#" onclick="' + this.render.drawTriggerEvent( "GridComponent", "page", this.component.id , ""+nextPage )  + '" >&#10093;</a></li>';
              
              html += '<li ';
              if (maxPage === totalOfPages) {
                  html += ' class="disabled" ';
                  html += ' style="pointer-events: none;cursor: default;"';
              }
              html += '><a href="#" onclick="' + this.render.drawTriggerEvent( "GridComponent", "page", this.component.id , ""+totalOfPages )  + '" >&#10093;&#10093;</a></li></ul></div>';
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
    html += '<div class="modal fade" id="'+component.id+'" tabindex="-1" role="dialog"'+ ((component.id === 'modal')? 'style="pointer-events:none"': + ' ') +  '>';
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
     
     var show= "", icon = "", opositeIcon = "";;
     
     if (!this.render.isEmpty(component.collapsed)) {
         show= ' in ';
         icon= 'down';
         opositeIcon = "up";
     }else{
    	 icon= 'up';
    	 opositeIcon = "down";
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
     
     html += '<span id="span' + component.id + '" class="glyphicon glyphicon-chevron-'+icon+'" style="color:#545454;" ></span>'; 
     html += '<span id="span' + component.id + 'Oposite" class="glyphicon glyphicon-chevron-'+opositeIcon+'" style="color:#545454;" ></span>';
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

/*
function PanelComponent( render ){
    this.render = render;
}

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
    html += "</div>";
    return html;
};
*/

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
	    		   	               
	    		   html += '<li id="tabButton' +idTab +'" style="display: flex;flex: 1;white-space:normal;width:'+sizeOfTabs+'%;"';
	    		   
	               if(i == 0){
	            	   //Tab by Default active class
	            	   html += 'class="active"';
	               }
	               if(idTab ==='canase' ||idTab ==='ciz1' ||idTab ==='ciz2' ||idTab ==='ciz3' ||idTab ==='historico' ||idTab=== 'bdtu')
	            	   {
	            	   
	            	   html += '><a data-toggle="tab" href="#panel'+idTab+'" style="flex: 1;';
	            	   if(idTab ==='historico' )
            		   {
	            		   html += 'text-indent: -14px;"';
            		   }
	            	   
	            	    html +='" onclick="module.controller.informaciontempCheck('+idTab+')">';
	            	    }
	               else
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
/*
function SelectFieldComponent( render ){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}
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
SelectFieldComponent.prototype.getValue = function( $key ){
    return $('#' + $key).val();
};
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
SelectFieldComponent.prototype.format = function( $key ){
    //-
};
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
SelectFieldComponent.prototype.bind = function( $key, $callback ){
    var _this = this;
    var component = $('#' + $key);
    component.focusout( function( $event ) {
        $callback( _this.getValue( $key ), _this.render.module.controller.model );
    });
};
*/
/*
function TextAreaFieldComponent(render){
    this.render = render;
    this.defaultIcon = "glyphicon glyphicon-question-sign";
}

TextAreaFieldComponent.prototype.draw = function (component) {
    var html = '<div class="form-group" ';
    if (!this.render.isEmpty(component.id)) {     
    	html += 'id="' +component.id+'"';
    }
    html += ' >';
    
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
    
    if(!this.render.isEmpty(component.maxlength)){
		 html += ' maxlength="' + this.render.nvl(component.maxlength) + '" ';
	}
    
    if (!this.render.isEmpty(component.tooltip)) {
        html += 'data-toggle="tooltip" data-placement="bottom" title="' + this.render.nvl(component.tooltip) + '" ';
    }
    html += this.render.module.validator.textFieldRules(component);
    html += ' tabindex="' + (this.render.index++) + '" ></textarea>';
    
    html += '</div></div>';
    return html;
};

TextAreaFieldComponent.prototype.digest = function (link) {
    $('[data-toggle="popover"]').popover();    
};

TextAreaFieldComponent.prototype.notify = function( $key, $value ){
    this.setValue( $key, $value );
};

TextAreaFieldComponent.prototype.getValue = function( $key ){
    return $( '#' + $key ) === null ? null : $( '#' + $key ).val();
};

TextAreaFieldComponent.prototype.setValue = function( $key, $value ){
    $('#' + $key).val($value);
    this.format($key);
};

TextAreaFieldComponent.prototype.format = function( $key ){
    var component = $('#' + $key);
    var value = component.val();
    component.val(value);
};

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

TextAreaFieldComponent.prototype.bind = function( $key, $callback ){
    var _this = this;
    var component = $('#' + $key);
    component.focusout( function( $event ) {
        $callback( _this.getValue( $key ), _this.render.module.controller.model );
    });
};
*/
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
	
    var html = '<div class="form-group" ';
	
	if(!this.render.isEmpty( component.id )){
		html += 'id="'+component.id+'"';
	}
	html += ' >';
	
    if (!this.render.isEmpty(component.label)) {          
      html += '<label for="';
      html += component.field + '" class="control-label '+(this.render.isEmpty(component.className) ? 'container-fluid' : component.className)+'">';       
      html += this.render.nvl(component.label, "") + '</label>'; 
    }
    html += '<div class="">'; //input-group
    
    html += '<table style="width: 100%;"><tr>'
    html += '<td><input id="' + this.render.replaceAll(component.field,'.','_') + '" ';
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
    
    if((component.labelStyle !== null && component.labelStyle === true) || (component.upperCase !== null && component.upperCase === true )
    		||(component.aling !== null && component===true )){
    	html+='style= "';
    	if((component.labelStyle !== null && component.labelStyle === true )){
    	html += ' border: 0px solid; outline: none; background: transparent;  -webkit-box-shadow: none;-moz-box-shadow: none;box-shadow: none;';
        }
        if((component.upperCase !== null && component.upperCase === true)) {  
        html+= 'text-transform: uppercase;';
        }
        if((component.aling !== null && component===true)) {  
        html+=' text-align:center;';
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
    html += ' tabindex="' + (this.render.index++) + '" /></td>';
    if (this.render.isEmpty(component.sinA))
    	{
    		html += '<td><p id="diferenteValor'+ this.render.replaceAll(component.field,'.','_') +'" style="color:red; visibility: hidden;font-size: 20px;">*</p></td></tr></table>';
    	}
    else
    html += '<td></td></tr></table>';
    
    
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
            	if($data.modal===true){
            	 this.printMessage($key,$data.message);	
            	}else{
                 this.render.showAlert('', $data.message);
            	}
                
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

TextFieldComponent.prototype.printMessage = function( $key, $message ){
	$( "#message" ).remove();
    var html = "<div  id='message' class='form-group' style='color:#a94442;font-size: small;'>"+$message+"</div>";
    $( html ).insertAfter( ".has-error" );
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
    html +=" <td><div class=\""+this.render.nvl(component.id) +"\"><input type=\"checkbox\" value=\"activo\"  id=\""+this.render.nvl(component.id) +"\" name=\""+this.render.nvl(component.id) +"\" />";
   
    if (!this.render.isEmpty(component.disabled) || component.disabled === true) {
       html +="<label  id='label"+this.render.nvl(component.id) +"' for='"+this.render.nvl(component.id)+"'></label>";
    }
	else{
	html +="<label onclick='ResponsableController.prototype.accionCheck("+this.render.nvl(component.id)+")' id='label"+this.render.nvl(component.id) +"' for='"+this.render.nvl(component.id)+"'></label>";
	}
     
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
    this.volatileComponents = [];
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
        'UserProfileComponent' 		: new UserProfileComponent(this),
        'GridComponent'			    : new GridComponent(this),
        'GridGroupComponent'        : new GridGroupComponent(this),
        'AlertComponent'            : new AlertComponent(this),
        'SimpleCollapsableComponent': new SimpleCollapsableComponent(this),
        'PanelCollapsableComponent' : new PanelCollapsableComponent(this),
        'PanelTabComponent' 		:  new PanelTabComponent(this),
        'TextAreaFieldComponent'    : new TextAreaFieldComponent(this),
        'CheckBoxFieldComponent'    : new CheckBoxFieldComponent(this),
        'HRComponent'				: new HRComponent(this),
        'ButtonGroupComponent'		: new ButtonGroupComponent(this),
        'IteratorComponent'		: new IteratorComponent(this),
  		'CheckBoxComponent'			: new CheckBoxComponent(this),
  		'HeaderComponent'			: new HeaderComponent(this),
		'PanelCheckBoxComponent'	: new PanelCheckBoxComponent(this),
  		'linea'						: new linea(this),
  		'SliderComponent'			: new SliderComponent(this),
  		'cargarValores'				: new cargarValores(this),
  		'cargarValoresLectura'				: new cargarValoresLectura(this),
  		'textlater'					: new textlater(this),
  		'textArealater' 			: new textArealater(this),
		'EspacioComponent' 			: new EspacioComponent(this),
		'TextoenlineaComponent' 	: new TextoenlineaComponent(this),
		'tablaResumenComponentAsegurado' : new tablaResumenComponentAsegurado(this),
		'tablaResumenDetalleComponent' : new tablaResumenDetalleComponent(this),
		'tablaDetalleCuentaIndComponent' : new tablaDetalleCuentaIndComponent(this),
		'tablaResumenComponentAsociadoAsegurado' : new tablaResumenComponentAsociadoAsegurado(this),
    	'ProcesandoComponent': new ProcesandoComponent(this),
      'TabPanelComponent': new TabPanelComponent(this),
      'HTMLComponent': new HTMLComponent(this),
      'RadioGroupFieldComponent': new RadioGroupFieldComponent(this)
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
 * M�todo que se procesar� cuando hay una notificaci�n de cambio en el modelo de
 * datos y avisara al componente al que corresponda la notificaci�n
 * @param {String} key                 : Identificador del elemento
 * @param {Object|String|Number} model : Par�metro(s)
 * @returns {void}
 */
/*
Render.prototype.notify = function (type, key, value) {
  var component = this.componentTemplates[type];
  if( !this.isEmpty(component) ){
    var metadata = this.getComponentById(key);
    component.notify(metadata, value);
  }
};
                                            */
/**
 * M�todo que procesa cada uno de los componentes agregados en la lista linker
 * @param {Object} link : metadata
 * @returns {void}
 */
/*Render.prototype.digest = function ( ) {

    var localLinks = this.linker;
    this.linker = [];    
    for (var i = 0; i < localLinks.length; i++) {      
        var component = this.componentTemplates[localLinks[i].type];
        if( component !== null && component !== undefined ){
            component.digest( localLinks[i] );
        }        
    }
};*/
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
Render.prototype.modelToForm = function (formId, model, prefix, subfix) {
  //// // console.log("ModelToForm, " + formId +", mode: " + JSON.stringify( model ) + ", prefix:" + prefix);
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
          //// // console.log("Buscando... " + "#" + formId + " :input[name='" + prefix + key + subfix+ "']");
          //// // console.log(matches.length);
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
              //// // console.log("#" + formId + " :input[name='" + prefix + key + subfix + "'][value='" + value + "']");
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
/*
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
	
	if(container.id == "sliderNSS" && (container.type == "cargarValores"||container.type == "cargarValoresLectura"))
	{
		delete container;
	}
	else{
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
		
	}
	
       
    return {};
}; 
                                                  */
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
};


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
		  html += "<div style='width:100%; height:100%; overflow:auto'>";
		  html +="<table class='table table-hover table-bordered table-responsive' ";
		  var rp = '';
          if ( !this.render.isEmpty( data ) ) {
			  for (i = 0; i < data.length; i++) {
				  var listaNss = data[i].listaNss;	
				  rp = data[i].registroPatronal;
                  html += "<tr>";
				  html += "<th align='center'><a href='#'"				                                       
                  html += "onclick='" + this.render.module.instanceName + ".controller." + component.iconaction + "(" + i + ")' >";                                    				  
				  html += "<span class='";
				  html += component.icon;
				  html += "'></a></span></th>";
                  for (var j = 0; j < component.columns.length; j++) {					
					html += "<th style='background-color:LightGray'>";
					html += this.render.nvl(component.columns[j].label);
					html += "</th>";	
					html += "<td> ";
					var _aux = eval("data[i]." + component.columns[j].name);
					_aux = _aux === null ? "" : this.render.htmlEncode(_aux);
					if (component.columns[j].editable === undefined ||
						component.columns[j].editable === "false") {
						html += _aux;
					}
					else {
						var id = '' + i; 
						html+="<input type='hidden' id='";
						html+= id;
						html+="Anterior' value='"																									
						html+= _aux;
						html+="' maxlength='";
						html+=_aux.length;
						html+="' size='";
						html+=_aux.length;
						html+="'>";
						switch(component.columns[j].type) {							
							case "select":
								var listaNss = data[j].listaNss;								
								html+=" <select onchange='";
								html+=this.render.module.instanceName + ".controller.registrarCambiosTodos(" + id + ")' ";                                    				  													
								html+=" id='";
								html+=id;
								html+="Actual'> ";
								var x = 0;
								for (x = 0; x < listaNss.length; x++) {
									var _selected = _aux.trim() === listaNss[x].toString() ? "selected" : "";														
									html+="  <option value='";
									html+=listaNss[x];
									html+="'"; 
									html+=_selected;
									html+=">";
									html+=listaNss[x];
									html+="</option>";
								}													
								html+=" </select> ";								
								break;							
							default:
								break;
						}
							  
					}
					html += "</td>";
                  }
                  html += "</tr>";
				  html += "<tr>";
				  html += "<td colspan='9'>";
				  
//--------------------------------Grid interno ----------------------------------				  				  
				  html += "<table id='"; 
				  html += i
				  html += "TablaPeriodos' class='table table-hover table-bordered table-responsive' ";
        
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
					html += "<tr style='background-color:LightGray'>";
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
									  var existeId = false;
									  html += "<tr>";
									  var t = 0;
									  for (var t = 0; t < component.subcolumns.length; t++) {
										  var index ='' + i + r;
										  var idSub ='' + i + r + component.subcolumns[t].name; 
										  html += "<td ";
										  if((component.subcolumns[t].breakWord !== null && component.subcolumns[t].breakWord === true)){
											  html+="style='word-wrap: break-word'";
										  }
										  html+=" id='";
										  html+=idSub;
										  html+="Celda' > ";				
									      var _aux = eval("periodos[r]." + component.subcolumns[t].name);
										  _aux = _aux === null ? "" : this.render.htmlEncode(_aux);										  
										  if (component.subcolumns[t].editable === undefined ||
										  	component.subcolumns[t].editable === "false") {
											html += _aux;
										  }
										  else {											  
											  if (!existeId) {
												html+="<input type='hidden' id='";
												html+= idSub;
												html+="Id' value='"																									
												html+= periodos[r].cveIdPeriodo;
												html+="' >";
												existeId = true;
											  }											  											  
											  html+="<input type='hidden' id='";
													html+= idSub;
													html+="Anterior' value='"																									
													html+= _aux;
													html+="' maxlength='";
													html+=_aux.length;
													html+="' size='";
													html+=_aux.length;
													html+="'>";
											  switch(component.subcolumns[t].type) {
												case "text":
													html+="<input type='text' id='";
													html+= idSub;
													html+="Actual' value='"																									
													html+= _aux;
													html+="' maxlength='";
													html+=_aux.length;
													html+="' size='";
													html+=_aux.length;
													html+="'>";
													break;
												case "select":													
													///**
													html+=" <select id='";
													html+= idSub;
													html+="Actual' ";
													html+=" onchange='";
													html+=this.render.module.instanceName + ".controller." + component.subcolumns[t].evento +"(\"" + index + "\",\"" + i + "\",\"" + r + "\");'> ";
													var opciones = component.subcolumns[t].valores;
													var x = 0;													
													if (opciones === undefined) {														
														for (x = 0; x < listaNss.length; x++) {
															var _selected = _aux.trim() === listaNss[x].toString() ? "selected" : "";														
															html+="  <option value='";
															html+=listaNss[x];
															html+="'"; 
															html+=_selected;
															html+=">";
															html+=listaNss[x];
															html+="</option>";
														}				
													}
													else {
														for (x = 0; x < opciones.length; x++) {
															var _selected = _aux.trim() === opciones[x].valor.toString() ? "selected" : "";														
															html+="  <option value='";
															html+=opciones[x].clave;
															html+="'"; 
															html+=_selected;
															html+=">";
															html+=opciones[x].valor;
															html+="</option>";
														}	
													}														
													html+=" </select> ";
													break;
												case "link":
													var links = component.subcolumns[t].links;
													var x = 0;
													for (x = 0; x < links.length; x++) {
														html += "<a href='#'"				                                       
														html += "onclick='" + this.render.module.instanceName + ".controller." +
														        links[x].accion	+"(" + t + ")' >";  
														html += links[x].nombre;
														html += "</a><br/>";
													}
													break;
												default:
													break;
											  }
												  
										  }
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
		  html += "</div>";
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
	this.component = component;  
	var html = '';	   	  
	  html+='<div><hr style=\"height: 1px;';
	  if(component.margen!=undefined||component.margen!="")
		  {
		  	html+='margin-top:'+component.margen+'px';
		  }
      html +=';background-color: #E6E6E6; top:0px;\"></div>'; 
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
	    	    	  	html +="<div class='col-md-3'> ";
	    	    	  }
	    	      else if(i==1)
	    	    	  {
	    	    	  	html +="<div class='col-md-6'> ";
	    	    	  }
	    	    	  else if(i==2)
		    	      {
		    	    	  	html +="<div class='col-md-3'> ";
		    	      }

	    	          html += element.draw(component.components[i]);
	    	    	  html += "</div>";
	    	    }    
	    return html;
	    };
	    
	    
//															SliderComponent	    
	    function SliderComponent( render ){
	        this.render = render;
	    }

	    SliderComponent.prototype.draw = function (component) {   
	    	
	        var html = "";
	
	        this.component = component;
	        this.model = component.model;
	        html += '<div id="'+component.id+'">'; 
	        html += this.drawBody();
	        html += '</div>';

	      //  this.render.addLinker(new Link(component.id, "SliderComponent", component));
	        return html;
	    };

	    
	    SliderComponent.prototype.drawBody = function () {
	    	
	        var component = this.component;    
//	    	   var element = this.render.componentTemplates[component.components[i].type];
	        var html = "";  
	         if(component !== null){  
	        	 var tabs = 1;
	         }
	             for( var i=0; i< tabs ; i++){
	             
	            	  var html = '';	   	  
	            		 html+= "<div id='"+ component.id +"'>";
	            		 html+=	"<table align='right'>";
	            		 html+=		"<tr>";
	            		 html+=		"<td>";
	            		 html+=	"<div id='hola'>";
	            		 html+=		"<button type='button' style='color:#A4A4A4;width:20px;height:32px;background-color:#E6E6E6;border:none;padding:0.2em 0;outline:none;' onclick='ResponsableController.prototype.anteriorNSS()'>&lt;</button>";
	            		 html+=		"</div>";
	            		 html+=	"</td>";
	            		 html+=	"<td>";
	            		 html+=		"<div style='width:783px;overflow:hidden;'>";
	            		 html+=		"<div id='contBoton'>";
	            		 
//	            		 var conteo = component.components.length;
	            		 var i=0;
	            			while( i< component.components.length )
	            			{
	            				i++
	            		      var element = this.render.componentTemplates[component.components[0].type];
							
	            		              html += element.draw(component.components[0]); 	    	 
	            		    }    
	            		 html+=		"</div>";
	            		 html+=		"</div>";
	            		 html+=		"</td>";
	            		 html+=		 "<td>";
	            		 html+=		 "<button type='button' style='color:#A4A4A4;width:20px;height:32px;background-color:#E6E6E6; border:none;padding:0.2em 0;outline:none;' onclick='ResponsableController.prototype.siguienteNSS()'>&gt;</button>";
	            		 html+=		 "</td>";
	            		 html+=		"</tr>";
	            		 html+=	"</table>";
	            		 html+=	"</div>";
	            		 html+= "<br><br><br>";
	            			

	                html += "<span> </span>";
	             
	      }
	            
	        return html;
	    };
	    
	    //SliderComponent.prototype.digest = function (linker) {
	    	
	        //eval(this.render.module.instanceName + ".service." + linker.metadata.size + "('" + linker.metadata.id + "', " + this.render.module.instanceName + ",1)");
	    
	        //if( !this.render.isEmpty(linker.metadata.data ) ){
	          //    for (var i= 0; i< linker.metadata.components.length; i++){
	            //      // // console.log(linker.metadata.components[i].id);
	              //    eval(this.render.module.instanceName + ".service." + linker.metadata.data + "('" + linker.metadata.components[i].id + "', " + this.render.module.instanceName + ","+i+")");
	              //}
	              
	           //}
	  //};
	      
	  SliderComponent.prototype.notify = function (metadata, noTabs) {
		  
	          this.component = metadata;  
	          this.component.tabs= noTabs;
	          
	          var components = [];
	          
	          var componente = this.component.components[0];
	          var name = this.component.components[0].id;
	          /*Random grids are created inside the gridgroupcomponent*/
	          for(var index = 0 ; index < noTabs ; index++){
	                 
                   components.push(grid);
	          }
	          delete this.component.components;
	          this.component.components = components;
	          
	          var html = this.drawBody();
	          $("#" + this.component.id).html( html );

	  };


	  function cargarValores(render){
		  
		    this.render = render;
		};

		cargarValores.prototype.draw = function (component) {   
		
		        var html = "";
		        this.component = component;
		        this.model = component.model;
		        html += '<div id="'+component.id+'">'; 
		        html += this.drawBody();
		        html += '</div>';

		        this.render.addLinker(new Link(component.id, "cargarValores", component));
		        return html;
		    };
		    

		var numNSS;
		cargarValores.prototype.drawBody = function (component) 
			{
			
		    var component = this.component;    
			var html='';
		    if(component.datosNSS!== undefined)
				{
				
				var nss =[];
				for(var i = 0 ; i < component.datosNSS.gridsNSS.length ; i++)
				{
					nss.push(component.datosNSS.gridsNSS[i].data[0].nss);
				}
				if(component.tabs.gridsNSS != undefined)
				{
					$("#numeroNSS").text(component.tabs.gridsNSS[0].data[0].nss);
					numNSS=component.tabs.gridsNSS[0].data[0].nss;
				}
				else
				{$("#numeroNSS").text(numNSS);}
				
				for(var i=0; i < nss.length; i++)
				{					
							html+="<span id='spanestilo'><button type='button' class='btonselect' id='btonselect"+i+"' onclick='module.controller.obtenerDatosNSSinicio(this)' value='"+nss[i]+"'>"+ nss[i] +"</button></span>";							
				}
				return html;			
				}	
		};
		
		 cargarValores.prototype.digest = function (linker) {
		    	
		        eval(this.render.module.instanceName + ".service." + linker.metadata.size + "('" + linker.metadata.id + "', " + this.render.module.instanceName + ",1)");
		    
		        if( !this.render.isEmpty(linker.metadata.data ) ){
		              for (var i= 0; i< linker.metadata.components.length; i++){
		                  // // console.log(linker.metadata.components[i].id);
		                  eval(this.render.module.instanceName + ".service." + linker.metadata.data + "('" + linker.metadata.components[i].id + "', " + this.render.module.instanceName + ","+i+")");
		              }
		              
		           }
		  };
		  
		  var bandera=0;
		  var idTramite;
		  cargarValores.prototype.notify = function (metadata, noTabs) {
			  
			  bandera = bandera +1;
			  var llenadodatos=0;
			  this.component = metadata;  
	          this.component.tabs= noTabs;
	          if(bandera == 2 )
	        	  {
	        	  this.component.datosNSS= noTabs;
	        	  	bandera =0;
	        	  	llenadodatos = 1;
	        	  }
	          else
	        	  {
	        	  	idTramite = noTabs;
	        	  }
	          
	          var components = [metadata];
	          delete this.component.components;
	          this.component.components = components;
	          
	          var html = this.drawBody();
	          $("#sliderNSS").html( html );
	          if(llenadodatos == 1)
	        	  {
	        	  
	        	  var renapoo=0;
				  for(var reanaponum = 0 ; reanaponum< noTabs.gridsNSS.length; reanaponum++)
				  {
					  if (noTabs.gridsNSS[reanaponum].data["0"].informacionRENAPO != null)
					  {
						  renapoo = reanaponum;
					  }
				  }
				 
        	  
        	  $("#informacionRENAPO_curp").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.curp);
        	  $("#informacionRENAPO_apellidoPaterno").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.apellidoPaterno);	        		
        	  $("#informacionRENAPO_apellidoMaterno").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.apellidoMaterno);	        		
        	  $("#informacionRENAPO_nombre").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.nombre);	        		
        	  $("#informacionRENAPO_sexo").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.sexo);	        		
        	  $("#informacionRENAPO_fechaNacimiento").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.fechaNacimiento);	        		
        	  $("#informacionRENAPO_lugarNacimiento").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.lugarNacimiento);	        		
        	  $("#informacionRENAPO_nacionalidad").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.nacionalidad);	        		
              $("#informacionRENAPO_datosDocumentoProbatorio").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.datosDocumentoProbatorio);
        	  		        	  
//	              if(noTabs.gridsNSS["0"].data.informacionBDTU.length != 0)
//      			{
//      				var fuenteDorigen;
//      				for(var contador = 0 ; contador < noTabs.gridsNSS["0"].data.informacionBDTU.length; contador++ )
//      					{	
//      						fuenteDorigen = noTabs.gridsNSS["0"].data.informacionBDTU[contador].origen;
//      						if(fuenteDorigen ==="SINDO CIZ 3"){fuenteDorigen = "CIZ3"}
//      						else if(fuenteDorigen ==="SINDO CIZ 2"){fuenteDorigen = "CIZ2"}
//      						else if(fuenteDorigen ==="SINDO CIZ 1"){fuenteDorigen = "CIZ1"}
//      						else if(fuenteDorigen ==="HIST&Oacute;RICO CENTRAL"||fuenteDorigen ==="HIST&Oacute;RICO"){fuenteDorigen = "HISTORICO"}
//      					
//      					 	$("#informacion"+fuenteDorigen+"_curp").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].curp);
//      		        		$("#informacion"+fuenteDorigen+"_apellidoPaterno").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].apellidoPaterno);	        		
//      		        		$("#informacion"+fuenteDorigen+"_apellidoMaterno").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].apellidoMaterno);	        		
//      		        		$("#informacion"+fuenteDorigen+"_nombre").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].nombre);	        		
//      		        		$("#informacion"+fuenteDorigen+"_sexo").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].sexo);	        		
//      		        		$("#informacion"+fuenteDorigen+"_fechaNacimiento").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].fechaNacimiento);	        		
//      		        		$("#informacion"+fuenteDorigen+"_lugarNacimiento").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].lugarNacimiento);	        		
//      		        		$("#informacion"+fuenteDorigen+"_nacionalidad").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].nacionalidad);	        		
//      		        		$("#informacion"+fuenteDorigen+"_datosDocumentoProbatorio").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].datosDocumentoProbatorio);
//      					}
//      				
//      			}
	        		
	        		llenadodatos = 0;
	        		
	        	  	ResponsableController.prototype.obtenerDatosNSSinicio(noTabs.gridsNSS["0"].data["0"].nss);
	        	  	ResponsableController.prototype.validarNSS();
					ResponsableController.prototype.validarCheckboxMenu("canase",noTabs.gridsNSS.length);
	        	  }

		  };
	    
	    
		  cargarValores.prototype.idTramite = function () {
			  
			  return idTramite;
		  };
		  
		  
		  function cargarValoresLectura(render){
			  

			    this.render = render;
			};

			cargarValoresLectura.prototype.draw = function (component) {   
		

			        var html = "";
			        this.component = component;
			        this.model = component.model;
			        html += '<div id="'+component.id+'">'; 
			        html += this.drawBody();
			        html += '</div>';

			        this.render.addLinker(new Link(component.id, "cargarValoresLectura", this.component,5));
			        return html;
					
					
			    };
	
			cargarValoresLectura.prototype.drawBody = function (component) 
				{
				
			    var component = this.component;    
				var html='';
			    if(component.datosNSS!== undefined)
					{
					
					var nss =[];
					for(var i = 0 ; i < component.datosNSS.gridsNSS.length ; i++)
					{
						nss.push(component.datosNSS.gridsNSS[i].data[0].nss);
					}
					if(component.tabs.gridsNSS != undefined)
					{
						$("#numeroNSS").text(component.tabs.gridsNSS[0].data[0].nss);
						numNSS=component.tabs.gridsNSS[0].data[0].nss;
					}
					else
					{$("#numeroNSS").text(numNSS);}
		
					for(var i=0; i < nss.length; i++)
					{					

								html+="<span id='spanestilo'><button type='button' class='btonselect' id='btonselect"+i+"' onclick='module.controller.obtenerDatosNSSinicio(this)' value='"+nss[i]+"'>"+ nss[i] +"</button></span>";							
					}

					return html;			
					}	

			};
			
			
		

			 cargarValoresLectura.prototype.digest = function (linker) {
			    	

			        eval(this.render.module.instanceName + ".service." + linker.metadata.size + "('" + linker.metadata.id + "', " + this.render.module.instanceName + ",1)");
			    

			        if( !this.render.isEmpty(linker.metadata.data ) ){
			              for (var i= 0; i< linker.metadata.components.length; i++){
			                  // // console.log(linker.metadata.components[i].id);
			                   eval(this.render.module.instanceName + ".service." + linker.metadata.data + "('" + linker.metadata.components[i].id + "', " + this.render.module.instanceName + ","+i+")");
			              }
			              
			           }
			  };
			  
			
			  cargarValoresLectura.prototype.notify = function (metadata, noTabs) {

 



				  bandera = bandera +1;
				  var llenadodatos=0;
				  this.component = metadata;  
		          this.component.tabs= noTabs;
		          if(bandera == 2 )
		        	  {

		        	  this.component.datosNSS= noTabs;
		        	  	bandera =0;
		        	  	llenadodatos = 1;
		        	  }
		          else
		        	  {



		        	  	idTramite = noTabs;
		        	  }
		          


		          var components = [metadata];
		          delete this.component.components;
		          this.component.components = components;
		          

		          var html = this.drawBody();
		          $("#sliderLecturaNSS").html( html );
		          if(llenadodatos == 1)
		        	  {
		        	  


		        	  var renapoo=0;
					  for(var reanaponum = 0 ; reanaponum< noTabs.gridsNSS.length; reanaponum++)
					  {
						  if (noTabs.gridsNSS[reanaponum].data["0"].informacionRENAPO != null)
						  {


							  renapoo = reanaponum;

						  }
					  }
					 
	        	  


	        	  $("#informacionRENAPO_curp").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.curp);
	        	  $("#informacionRENAPO_apellidoPaterno").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.apellidoPaterno);	        		
	        	  $("#informacionRENAPO_apellidoMaterno").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.apellidoMaterno);	        		
	        	  $("#informacionRENAPO_nombre").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.nombre);	        		
	        	  $("#informacionRENAPO_sexo").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.sexo);	        		
	        	  $("#informacionRENAPO_fechaNacimiento").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.fechaNacimiento);	        		
	        	  $("#informacionRENAPO_lugarNacimiento").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.lugarNacimiento);	        		
	        	  $("#informacionRENAPO_nacionalidad").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.nacionalidad);	        		
	              $("#informacionRENAPO_datosDocumentoProbatorio").val(noTabs.gridsNSS[renapoo].data["0"].informacionRENAPO.datosDocumentoProbatorio);
	        	  		        	  

		              if(noTabs.gridsNSS["0"].data.informacionBDTU.length != 0)
	      			{

	      				var fuenteDorigen;
	      				for(var contador = 0 ; contador < noTabs.gridsNSS["0"].data.informacionBDTU.length; contador++ )
	      					{	

	      						fuenteDorigen = noTabs.gridsNSS["0"].data.informacionBDTU[contador].origen;
	      						if(fuenteDorigen ==="SINDO CIZ 3"){fuenteDorigen = "CIZ3"}
	      						else if(fuenteDorigen ==="SINDO CIZ 2"){fuenteDorigen = "CIZ2"}
	      						else if(fuenteDorigen ==="SINDO CIZ 1"){fuenteDorigen = "CIZ1"}
	      						else if(fuenteDorigen ==="HIST&Oacute;RICO CENTRAL"||fuenteDorigen ==="HIST&Oacute;RICO"){fuenteDorigen = "HISTORICO"}
	      					

	      					 	$("#informacion"+fuenteDorigen+"_curp").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].curp);
	      		        		$("#informacion"+fuenteDorigen+"_apellidoPaterno").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].apellidoPaterno);	        		
	      		        		$("#informacion"+fuenteDorigen+"_apellidoMaterno").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].apellidoMaterno);	        		
	      		        		$("#informacion"+fuenteDorigen+"_nombre").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].nombre);	        		
	      		        		$("#informacion"+fuenteDorigen+"_sexo").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].sexo);	        		
	      		        		$("#informacion"+fuenteDorigen+"_fechaNacimiento").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].fechaNacimiento);	        		
	      		        		$("#informacion"+fuenteDorigen+"_lugarNacimiento").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].lugarNacimiento);	        		
	      		        		$("#informacion"+fuenteDorigen+"_nacionalidad").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].nacionalidad);	        		
	      		        		$("#informacion"+fuenteDorigen+"_datosDocumentoProbatorio").val(noTabs.gridsNSS["0"].data.informacionBDTU[contador].datosDocumentoProbatorio);
	      					}
	      				
	      			}
		        		




		        		llenadodatos = 0;
		        	  	ResponsableController.prototype.obtenerDatosNSSinicio(noTabs.gridsNSS["0"].data["0"].nss);
		        	  	ResponsableController.prototype.validarNSSLectura();
		        	  	ResponsableController.prototype.validarCheckboxMenu("canase",noTabs.gridsNSS["0"].data.length);
		        	  }


			  };
		    
		    



			  cargarValoresLectura.prototype.idTramite = function () {
				  

				  return idTramite;
			  };

		  
/*
 * Caja de texto con leyenda lateral
 * 
*/

function textlater(render){
    this.render = render;
};

textlater.prototype.draw = function (component) {
	  var html = '';	   	  
	  html+="<div class='form-group'><table><tr><th><label style='width:170px;font-size:15px'>"+this.render.nvl(component.label, "")+"</label></th><th><input type='text' id='" + component.field + "' name='" + component.field + "' style='background-color:#FFF;border:#FFF 1px solid;padding-top:20px;text-align:center;' disabled/></th></tr></table></div>";
      return html;
	   };
	   
/*
 * Text area con leyenda lateral
 * 
 */
function textArealater(render){
     this.render = render;
	};

textArealater.prototype.draw = function (component) {
	  var html = '';	   	  
	  html+="<div class='form-group'><table align='center'><tr><th><label style='width:110px;font-size:15px'>"+this.render.nvl(component.label, "")+"</label></th><th><textarea rows='3' style='background-color:#E6E6E6;border:#FFF 1px solid;padding-top:0px;width: 236px;height: 109px' disabled></textarea></th></tr></table></div>";
      return html;
   };
   
/*
 * 2 renglones espacio 
 * 
 */
function EspacioComponent(render){
     this.render = render;
	};

EspacioComponent.prototype.draw = function (component) {
	
	  var html = '';	 
	  this.component = component;
	  var numeroEspacio=parseInt(component.numero);
	  html+="<br><br>";
	  if(component.numero != undefined ||component.numero != "" )
		  {
		     for(var i=0 ;i< numeroEspacio; i++)
		    	 {
		    	 html += "<br>";
		    	 }
		  }		  			  
      return html;
   };
   
/*
 * 2 Cajas de texto
 * 
 */
function TextoenlineaComponent(render){
     this.render = render;
	};

TextoenlineaComponent.prototype.draw = function (component) {
	  var html = '';	   	  
	  html+="<div class='form-group'><table align='left'><tr><th><label style='width:110px;font-size:15px;padding-left: 15px;'>"+this.render.nvl(component.labelNSS, "")+"</label></th><th><input type='text' id='" + component.fielduno + "' name='" + component.fielduno + "' value='"+curp+"' style='font-size: 13px;background-color:#FFF;border:#FFF 1px solid;padding-top:10px;text-align:center' disabled/></th>"+
			"<th><label style='width:110px;font-size:15px; padding-left: 40px;'>"+this.render.nvl(component.labelDETALLE, "")+"</label></th><th><input type='text' id='" + component.fielddos + "' value='"+responsable+"' name='" + component.fielddos + "' style='font-size:13px;background-color:#FFF;border:#FFF 1px solid; padding-top:10px; width:600px;' disabled/></th></tr></table></div>";
      return html;
   };
   
   /*
    * Tabla certificador
    */
   

   function tablaResumenComponentAsegurado(render){
	     this.render = render;
		};
   
   tablaResumenComponentAsegurado.prototype.draw = function (component) {
   	  var elemento= ["curp","apellidoPaterno","apellidoMaterno","nombre","sexo","fechaNacimiento","lugarNacimiento","nacionalidad","pertenecebd","documentos"];
   	  var html = '';	 
   	  html = "<table align='center' border=1 cellspacing=0 cellpadding=2 bordercolor='#D8D8D8' style='font-size:90%;color:#585858;'>"+
   	  			"<tr>"+
      "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Origen de<br> informacion</th>"+
      "<th style='width:200px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Dato</th>"+
      "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Informacion del IMSS</th>"+ 
      "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Informacion de actualizacion</th>"+
      "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Estatus del cambio</th>"+
      "<th style='width:70px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Fecha de proceso</th>"+
      "</tr>";
for(var datosdCDA=0; datosdCDA<datosCDA.length;datosdCDA++ )
	{
	if(datosCDAModificado[datosdCDA].tipoNSS==="certificador")
		{
		var poselemento1;
		for(poselemento = 0 ; poselemento < 1 ; poselemento++)
	  		{
	  	 //si fue modificacion
	  						$("#informacionNSScertificador_NSS").val(datosCDA[datosdCDA].NSS);	
	  				
						if(datosCDA[datosdCDA].nombre != datosCDAModificado[datosdCDA].nombre )
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[3]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].nombre+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].nombre;
						
						html =html +"<td>"+ datosCDA[datosdCDA].nombre+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}
						if (datosCDA[datosdCDA].curp != datosCDAModificado[datosdCDA].curp)
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[0]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].curp+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].curp;
						
						html =html +"<td>"+ datosCDA[datosdCDA].curp+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}
						if (datosCDA[datosdCDA].documentos != datosCDAModificado[datosdCDA].documentos)
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[9]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].documentos+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].documentos;
						
						html =html +"<td>"+ datosCDA[datosdCDA].documentos+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}
						if (datosCDA[datosdCDA].apellidoPaterno != datosCDAModificado[datosdCDA].apellidoPaterno)
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[1]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].apellidoPaterno+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].apellidoPaterno;
						
						html =html +"<td>"+ datosCDA[datosdCDA0].apellidoPaterno+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}
						if (datosCDA[datosdCDA].apellidoMaterno != datosCDAModificado[datosdCDA].apellidoMaterno)
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[2]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].apellidoMaterno+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].apellidoMaterno;
						
						html =html +"<td>"+ datosCDA[datosdCDA].apellidoMaterno+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}
						if (datosCDA[datosdCDA].sexo != datosCDAModificado[datosdCDA].sexo)
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[4]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].sexo+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].sexo;
						
						html =html +"<td>"+ datosCDA[datosdCDA].sexo+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}      	  					
					if (datosCDA[datosdCDA].fechaNacimiento != datosCDAModificado[datosdCDA].fechaNacimiento)
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[5]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].fechaNacimiento+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].fechaNacimiento;
						
						html =html +"<td>"+ datosCDAModificado[datosdCDA].fechaNacimiento+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}     
					
					if (datosCDA[datosdCDA].lugarNacimiento != datosCDAModificado[datosdCDA].lugarNacimiento)
						{
						html = html + "<tr align='center'>"+
	  					"<td>"+datosCDA[datosdCDA].pertenecebd+"</td>"+
	  					"<td>"+elemento[5]+"</td>";
						
							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[datosdCDA].lugarNacimiento+"</td>";
							datomodificadoo = datosCDAModificado[datosdCDA].lugarNacimiento;
						
						html =html +"<td>"+ datosCDA[datosdCDA].lugarNacimiento+"</td>"+
	  					"<td> </td>"+
	  					"<td> </td>"+   	  	
	  					"</tr>";
						}
					poselemento1=poselemento;
		}
		}
	
   	  					
//   	  			}
					
   	  	   //Si no existen datos
       	  	if((datosCDA[datosdCDA].nombre != datosCDAModificado[datosdCDA].nombre && (datosCDAModificado[datosdCDA].nombre == '' || datosCDAModificado[datosdCDA].nombre == null ))||
				   (datosCDA[datosdCDA].curp != datosCDAModificado[datosdCDA].curp && (datosCDAModificado[datosdCDA].curp == '' || datosCDAModificado[datosdCDA].curp == null ))||
				   (datosCDA[datosdCDA].documentos != datosCDAModificado[datosdCDA].documentos && (datosCDAModificado[datosdCDA].documentos == '' || datosCDAModificado[datosdCDA].documentos == null ))||
				   (datosCDA[datosdCDA].apellidoPaterno != datosCDAModificado[datosdCDA].apellidoPaterno && (datosCDAModificado[datosdCDA].apellidoPaterno == '' || datosCDAModificado[datosdCDA].apellidoPaterno == null ))||
				   (datosCDA[datosdCDA].apellidoMaterno != datosCDAModificado[datosdCDA].apellidoMaterno && (datosCDAModificado[datosdCDA].apellidoMaterno == '' || datosCDAModificado[datosdCDA].apellidoMaterno == null ))||
				   (datosCDA[datosdCDA].sexo != datosCDAModificado[datosdCDA].sexo && (datosCDAModificado[datosdCDA].sexo == '' || datosCDAModificado[datosdCDA].sexo == null )))
				   
       	  		{
       	  	poselemento
       	  	html = html + "<tr align='center'>"+
       	  		"<td>"+datosCDA.pertenecebd+"</td>"+
					"<td>"+elemento[poselemento]+"</td>"+
					"<td style='background-color:#6E6E6E;color:#6E6E6E'>"+datosCDA.elemento[poselemento1]+"</td>"+
//					"<td>"+datosCDAModificado.elemento[poselemento]+"</td>"+
					"<td> </td>"+
					"<td> </td>"+   	
   	  		"</tr>";
  
       	  		}
   	  		

		}
	
   	  	html = html + "</table>";
   	  
         return html;
      };
      
 //__________________________Asociado al asegurador     
         
         /*
          * Tabla resumen Asociado al asegurador
          * 
          */
         function tablaResumenComponentAsociadoAsegurado(render){
              this.render = render;
         	};
         	
         	tablaResumenComponentAsociadoAsegurado.prototype.draw = function (component) {
         		
         	  var elemento= ["curp","apellidoPaterno","apellidoMaterno","nombre","sexo","fechaNacimiento","lugarNacimiento","nacionalidad","pertenecebd",];
         	  var html = '';	 
         	  html = "<table align='center' border=1 cellspacing=0 cellpadding=2 bordercolor='#D8D8D8' style='font-size:90%;color:#585858;'>"+
         	  			"<tr>"+
            "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Origen de<br> informacion</th>"+
            "<th style='width:200px;background-color:#E6E6E6;font-size:80%;color:#585858;'>CURP</th>"+
            "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>NOMBRE</th>"+ 
            "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Lugar de<br> nacimiento </th>"+
            "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Fecha de<br> nacimiento</th>"+
            "<th style='width:70px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Sexo</th>"+
            "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Estatus del<br> cambio</th>"+
            "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Estatus del<br>Fecha de<br> proceso</th>"+
            "</tr>";
         	 for(var datosdCDA=0; datosdCDA<datosCDA.length;datosdCDA++ )
         	{
         	if(datosCDAModificado.tipoNSS=="asociadoCertificador")
         	  {
         	  	for(var poselemento = 0 ; poselemento < elemento.length ; poselemento++)
         	  		{
         	  	 //si fue modificacion
         	  		if((datosCDA[0].nombre != datosCDAModificado[0].nombre && (datosCDAModificado[0].nombre != '' || datosCDAModificado[0].nombre != null ))||
         				   (datosCDA[0].curp != datosCDAModificado[0].curp && (datosCDAModificado[0].curp != '' || datosCDAModificado[0].curp != null ))||
         				   (datosCDA[0].documentos != datosCDAModificado[0].documentos && (datosCDAModificado[0].documentos != '' || datosCDAModificado[0].documentos != null ))||
         				   (datosCDA[0].apellidoPaterno != datosCDAModificado[0].apellidoPaterno && (datosCDAModificado[0].apellidoPaterno != '' || datosCDAModificado[0].apellidoPaterno != null ))||
         				   (datosCDA[0].apellidoMaterno != datosCDAModificado[0].apellidoMaterno && (datosCDAModificado[0].apellidoMaterno != '' || datosCDAModificado[0].apellidoMaterno != null ))||
         				   (datosCDA[0].sexo != datosCDAModificado[0].sexo && (datosCDAModificado[0].sexo == '' || datosCDAModificado[0].sexo != null )))
         	  			{
         	  			html = html + "<tr align='center'>"+
         	  					"<td>"+datosCDA.pertenecebd+"</td>"+
         	  					"<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDAModificado.curp+"</td>";
         	  					if(datosCDAModificado[0].nombre != datosCDA[0].nombre || datosCDAModificado[0].apellidoPaterno != datosCDA[0].apellidoPaterno || datosCDAModificado[0].apellidoMaterno != datosCDA[0].apellidoMaterno)
         	  						{
         	  						html = html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDAModificado[0].nombre+" "+datosCDAModificado[0].apellidoPaterno+" "+datosCDAModificado[0].apellidoMaterno+"</td>";
         	  						}
         	  					else{
         	  						html = html + "<td>"+datosCDAModificado[0].nombre+" "+datosCDAModificado[0].apellidoPaterno+" "+datosCDAModificado[0].apellidoMaterno+"</td>";
         	  					}
         	  					
         	  					html = html +"<td>"+datosCDAModificado[0].lugarNacimiento+"</td>"+
         	  					"<td>"+datosCDAModificado[0].fechaNacimiento+"</td>"+
         	  					"<td>"+datosCDAModificado[0].sexo+"</td>"+   	
         	  					"<td> </td>"+
         	  					"<td> </td>"+ 
         	  					"</tr>";
         	  			}         	  	  
         	  		}
         	  }
         	}
         	  	
         	  	html = html + "</table>";
         	  
               return html;
            };
 
function tablaResumenDetalleComponent(render){
	this.render = render;
};
          
tablaResumenDetalleComponent.prototype.draw = function (component) {
         		
          	  var elemento= ["curp","apellidoPaterno","apellidoMaterno","nombre","sexo","fechaNacimiento","lugarNacimiento","nacionalidad","pertenecebd","documentos"];
          	  var html = '';	 
          	  html = "<table align='center' border=1 cellspacing=0 cellpadding=2 bordercolor='#D8D8D8' style='font-size:90%;color:#585858;'>"+
          	  			"<tr>"+
             "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Origen de<br> informacion</th>"+
             "<th style='width:200px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Dato</th>"+
             "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Informacion previa en el IMSS</th>"+ 
             "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Informacion de RENAPO actualizada en el IMSS</th>"+
             "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Estatus del cambio</th>"+
             "<th style='width:70px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Fecha de proceso</th>"+
             "</tr>";
       for(var datosdCDA=0; datosdCDA<datosCDA.length;datosdCDA++ )
       	{
       	if(datosCDAModificado.tipoNSS=="certificador")
       		{
       		for(var poselemento = 0 ; poselemento < 1 ; poselemento++)
       	  		{
       	  	 //si fue modificacion
       	  						$("#informacionNSScertificador_NSS").val(datosCDA[0].NSS);	
       	  				
       						if(datosCDA[0].nombre != datosCDAModificado[0].nombre )
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[3]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].nombre+"</td>";
       							datomodificadoo = datosCDAModificado[0].nombre;
       						
       						html =html +"<td>"+ datosCDA[0].nombre+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}
       						if (datosCDA[0].curp != datosCDAModificado[0].curp)
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[0]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].curp+"</td>";
       							datomodificadoo = datosCDAModificado[0].curp;
       						
       						html =html +"<td>"+ datosCDA[0].curp+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}
       						if (datosCDA[0].documentos != datosCDAModificado[0].documentos)
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[9]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].documentos+"</td>";
       							datomodificadoo = datosCDAModificado[0].documentos;
       						
       						html =html +"<td>"+ datosCDA[0].documentos+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}
       						if (datosCDA[0].apellidoPaterno != datosCDAModificado[0].apellidoPaterno)
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[1]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].apellidoPaterno+"</td>";
       							datomodificadoo = datosCDAModificado[0].apellidoPaterno;
       						
       						html =html +"<td>"+ datosCDA[0].apellidoPaterno+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}
       						if (datosCDA[0].apellidoMaterno != datosCDAModificado[0].apellidoMaterno)
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[2]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].apellidoMaterno+"</td>";
       							datomodificadoo = datosCDAModificado[0].apellidoMaterno;
       						
       						html =html +"<td>"+ datosCDA[0].apellidoMaterno+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}
       						if (datosCDA[0].sexo != datosCDAModificado[0].sexo)
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[4]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].sexo+"</td>";
       							datomodificadoo = datosCDAModificado[0].sexo;
       						
       						html =html +"<td>"+ datosCDA[0].sexo+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}      	  					
       					if (datosCDA[0].fechaNacimiento != datosCDAModificado[0].fechaNacimiento)
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[5]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].fechaNacimiento+"</td>";
       							datomodificadoo = datosCDAModificado[0].fechaNacimiento;
       						
       						html =html +"<td>"+ datosCDA[0].fechaNacimiento+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}     
       					
       					if (datosCDA[0].lugarNacimiento != datosCDAModificado[0].lugarNacimiento)
       						{
       						html = html + "<tr align='center'>"+
       	  					"<td>"+datosCDA[0].pertenecebd+"</td>"+
       	  					"<td>"+elemento[5]+"</td>";
       						
       							html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+datosCDA[0].lugarNacimiento+"</td>";
       							datomodificadoo = datosCDAModificado[0].lugarNacimiento;
       						
       						html =html +"<td>"+ datosCDA[0].lugarNacimiento+"</td>"+
       	  					"<td> </td>"+
       	  					"<td> </td>"+   	  	
       	  					"</tr>";
       						}
       		}
       		}
       	
          	  					
//          	  			}
       					
          	  	   //Si no existen datos
              	  	if((datosCDA[0].nombre != datosCDAModificado[0].nombre && (datosCDAModificado[0].nombre == '' || datosCDAModificado[0].nombre == null ))||
       				   (datosCDA[0].curp != datosCDAModificado[0].curp && (datosCDAModificado[0].curp == '' || datosCDAModificado[0].curp == null ))||
       				   (datosCDA[0].documentos != datosCDAModificado[0].documentos && (datosCDAModificado[0].documentos == '' || datosCDAModificado[0].documentos == null ))||
       				   (datosCDA[0].apellidoPaterno != datosCDAModificado[0].apellidoPaterno && (datosCDAModificado[0].apellidoPaterno == '' || datosCDAModificado[0].apellidoPaterno == null ))||
       				   (datosCDA[0].apellidoMaterno != datosCDAModificado[0].apellidoMaterno && (datosCDAModificado[0].apellidoMaterno == '' || datosCDAModificado[0].apellidoMaterno == null ))||
       				   (datosCDA[0].sexo != datosCDAModificado[0].sexo && (datosCDAModificado[0].sexo == '' || datosCDAModificado[0].sexo == null )))
       				   
              	  		{
              	  	html = html + "<tr align='center'>"+
              	  		"<td>"+datosCDA.pertenecebd+"</td>"+
       					"<td>"+elemento[poselemento]+"</td>"+
       					"<td style='background-color:#6E6E6E;color:#6E6E6E'>"+datosCDA.elemento[poselemento]+"</td>"+
//       					"<td>"+datosCDAModificado.elemento[poselemento]+"</td>"+
       					"<td> </td>"+
       					"<td> </td>"+   	
          	  		"</tr>";
         
              	  		}
          	  		

       		}
       	
          	  	html = html + "</table>";
          	  
                return html;
             };
		 
             function tablaDetalleCuentaIndComponent(render){
            	 this.render = render;
             };
             
             tablaDetalleCuentaIndComponent.prototype.draw = function (component) {


            	 var html = '';	 
            	 html = "<table align='center' border=1 cellspacing=0 cellpadding=2 bordercolor='#D8D8D8' style='font-size:90%;color:#585858;'>"+
            	 "<tr>"+
            	 "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Origen de<br> informacion</th>"+
            	 "<th style='width:200px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Dato</th>"+
            	 "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Informacion previa del IMSS</th>"+ 
            	 "<th style='width:250px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Informacion regularizada en el IMSS</th>"+
            	 "<th style='width:100px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Estatus del cambio</th>"+
            	 "<th style='width:70px;background-color:#E6E6E6;font-size:80%;color:#585858;'>Fecha de proceso</th>"+
            	 "</tr>";
            	 for(var dato=0; dato<cuentaIndividualDetalle.length;dato++ )
            	 {

            		 
            			 //si fue modificacion
            			 //	$("#informacionNSScertificador_NSS").val(cuentaIndividualDetalle[0].NSS);	

if(detalleCuentaIndividual !=null &&
		(detalleCuentaIndividual.origen != null && detalleCuentaIndividual.origen !="")||
	   		(detalleCuentaIndividual.fechaProceso != null && detalleCuentaIndividual.fechaProceso!="")||
			(detalleCuentaIndividual.dato  != null && detalleCuentaIndividual.dato!="")||
			(detalleCuentaIndividual.infoPrevia != null && detalleCuentaIndividual.infoPrevia!="")||
			(detalleCuentaIndividual.infoRegularizada != null && detalleCuentaIndividual.infoRegularizada!=""))
	{
            			 html = html + "<tr align='center'>"+
            			 "<td>"+cuentaIndividualDetalle[0].origen+"</td>"+
            			 "<td>"+cuentaIndividualDetalle[0].dato+"</td>";

            			 html=html + "<td style='background-color:#B40404;color:#FAFAFA'>"+cuentaIndividualDetalle[0].infoPrevia+"</td>";
            			 datomodificadoo = cuentaIndividualDetalle[0].infoPrevia;

            			 html =html +"<td>"+ cuentaIndividualDetalle[0].infoRegularizada+"</td>"+
            			 "<td> </td>"+
            			 "<td> </td>"+   	  	
            			 "</tr>";

            	 }		 


            	 }  					


            	 html = html + "</table>";

            	 return html;
             };

             function ProcesandoComponent( render ){
            		
            		this.render = render;
            	}

            	ProcesandoComponent.prototype.draw = function (component) {

            		this.compoment = component;  

            		var model = component.model;
            		this.render;




            		var html ='';
            		html += '<div class="modal fade" id="'+component.id+'" tabindex="-1" role="dialog">';
            		html += '  <div class="modal-dialog ';

            		html += 'procesar';

            		html += '">';
            		html += '    <div class="modal-content">';
            		html += '      <div class="modal-header2">';
            		html += '        <button type="button" class="close2" data-dismiss="modal" ><span aria-hidden="true">&times;</span></button>';
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



            		this.render.addLinker(new Link(component.id, "ProcesandoComponent", component));

            		return html;
            	};

            	ProcesandoComponent.prototype.digest = function (linker) {
            		
            		if( !this.render.isEmpty(linker.metadata.data ) ){
            			eval(this.render.module.instanceName + ".service." + linker.metadata.data + "('" + linker.metadata.id + "', " + this.render.module.instanceName + ",1)");
            		}
            	};
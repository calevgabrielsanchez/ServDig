/**
	Objeto para mandar llamar las validaciones generales.
**/
var validateForm=new validationsFormsClass();
/**
	Objeto que encapsula las expresiones regulares comunes.
**/
var regularExpression={
	///Expresiones regulares
	decimal:      /^\d+(\.\d+)?$/,
	decimal_2_6:  /(^\d{0,2}\.?$)|(^\d{0,2}\.\d{0,6}$)|(^\.\d{0,6}$)/,
	decimal_6_6:  /(^\d{0,6}\.?$)|(^\d{0,6}\.\d{0,6}$)|(^\.\d{0,6}$)/,
	decimal_14_6: /(^\d{0,14}\.?$)|(^\d{0,14}\.\d{0,6}$)|(^\.\d{0,6}$)/,
	decimal_4_3:  /(^[1-9][0-9]{0,3}\.?$)|(^[1-9][0-9]{0,3}\.\d{0,3}$)|(^\.\d{0,3}$)/,
	entero:    /^\d*$/,	
	porcenteaje_entero_3:  /(^100$)|(^\d{0,5}$)/,
	entero_4:  /^\d{0,4}$/,
	entero_3:  /^\d{0,3}$/,
	entero_8: /(^[0-9][0-9]{0,7}$)/,
	entero_14: /(^[0-9][0-9]{0,13}$)/,
	entero_10: /(^([0-9]([0-9]{0,9}))?$)/,
	entero_20: /(^([0-9]([0-9]{0,19}))?$)/,
	entero_11: /(^([0-9]([0-9]{0,10}))?$)/,
	entero_15: /(^([0-9]([0-9]{0,14}))?$)/,
	entero_30: /(^([0-9]([0-9]{0,29}))?$)/,
	porcentaje_2_3: /(^100$)|(^\.\d{0,3}$)|(^[1-9][0-9]?\.?$)|(^[1-9][0-9]?\.\d{0,3}$)/,
	porcentaje_0_2: /^([0]+)|([0]+\.\d{0,2})|([0]?\.\d{1,2})$/,
	decimal_3_2: /(^\d{0,3}\.?$)|(^\d{0,3}\.\d{0,2}$)|(^\.\d{0,2}$)/,	
	alfanumerico: /(^[a-zA-Z0-9]+$)/,
	alfanumerico_15: /(^[a-zA-Z0-9]+$)/,
	mayusculasnumerico_2: /^[A-Z0-9]{0,2}$/,
	mayusculasnumerico_5: /^[A-Z0-9]{0,5}$/,
	mayusculasnumerico_7: /^[A-Z0-9]{0,7}$/,
	dateGD: /^([0][1-9]|[12][0-9]|3[01])(\/|-)([0][1-9]|[1][0-2])\2(\d{4})$/,
	hora: /^(([0-1]$)|([0-1][0-9]$)|([0-1][0-9]:$)|([0-1][0-9]:[0-5]$)|([0-1][0-9]:[0-5][0-9]$)|([2]$)|([2][0-3]$)|([2][0-3]:$)|([2][0-3]:[0-5]$)|([2][0-3]:[0-5][0-9]$))/,
	horaFinal:/^[0-1][0-9]:[0-5][0-9]$|^[2][0-3]:[0-5][0-9]$/,
	alfanumerico_espacios: /^[\s-\w ÁáÉéÍíÓóÚúÑñ]*$/,
	alfabetico_espacios: /^[a-zA-Z\s ÁáÉéÍíÓóÚúÑñ]*$/
};
//|(^[1-9]\d{0,1}\.?\d{0,3}$)/
//porcentaje: /(^100$)|(^\d{0,2}\.?$)|(^\d{0,2}\.\d{0,3}$)|(^\.\d{0,3}$)/

/**
	Clase que encapsula validaciones generales para diferentes elementos de formularios.
	Los métodos publicos para utilizar de esta clase son:
	1.- validateForm.allowOnlyRegularExpression( input,regExp); //impide que el input acepte valores que no cumplan con la expresion regular.
	@see regularExpression //Objeto que encapsula las expresiones regulares comunes
**/
function validationsFormsClass(){
	var This=this;
	///Método que impide que un input text o textarea admitan valores que no cumplan con una expresionregular dada. 
	///No muestra mensaje y solo impide la entrada de valores que no cumplan con la expresion regular
	///La forma de incluirlo para un input debe ser:
	///
	/// validateForm.allowOnlyRegularExpression( $("#idInput"),regularExpression.decimal_14_6 );
	///
	///El valor de la expresion regular varia segun lo que se pretenda limitar.
	///@param input El objeto jquery con los input text o textarea que se limitaran los valores aceptados.
	///@param regExp Expresion regular que indica los unicos valores permitidos para un determinado input
	this.allowOnlyRegularExpression=function(input,regExp){
	     //le asignamos la expresion regular que sera validada por los manejadores de eventos
	     input.one('keyup', function(){this.regExp=regExp;this.beforeValue=this.value;});
	     input.one('keypress', function(){this.regExp=regExp;this.beforeValue=this.value;});
	     //le asignamos el manejador del evento keypress
	     input.keypress(validateKeypressRegularExpression);
	     //le asignamos el manejador del evento keyup
	     input.keyup(validateKeyupRegularExpression);
	     
	};
	//Manejador del evento keyup que valida la expresion regular introducida
	function validateKeyupRegularExpression(evento){
	    //Verificamos si no cumple con su expresion regular
   		if(!this.regExp.test(this.value) && this.value !== ''){
   		   //si no cumple se retorna su valor anterior para "impedir la modificacion a su valor"
   		   this.value=this.beforeValue;
   		}
   		//Almacenamos el valor actual como valor anterior
   		this.beforeValue=this.value;
   		return true;
	}
	//Manejador de evento keypress que valida la expresion regular introducida
	function validateKeypressRegularExpression(evento){
	    //Obtenemos el caracter pulsado
	    var character=String.fromCharCode(evento.which);
	    //Validamos que no sea un caracter (teclas epeciales) o que cumpla con su expression regular
	    if(!isCharacter(evento)||this.regExp.test(this.value+character)){
   		   return true;
   		}
   		return false;
	}
	///Para los eventos onkeyup, onkeydown y onkeypress determina si la tecla presionada genera un caracter
	///Retorna falso para cualquier tecla de tipo especial como alt,control y shift
	///@param evento Es el objeto event generado al ejecutarse alguno de los eventos del teclado.
	///@return true Si y solo si la tecla pulsada genera algun caracter. 
	function isCharacter(evento){
	  var validation;
	  switch(evento.keyCode){
	     case 8: //borrar
	     case 9: //tabulador
	     case 13://enter
	     case 16://shift
	     case 17://control
	     case 18://Alt
	     case 19://pause
	     case 20://mayusculas
	     case 27://escape
	     case 33://pageUp
	     case 34://pageDown
	     case 35://fin
	     case 36://inicio
	     case 37://flecha izquierda
	     case 38://flecha ariba
	     case 39://flecha derecha
	     case 40://flecha abajo
	     case 45://insertar
	     case 46://delete
	     case 91://windows
	     case 93://acceso rapido del ouse por el teclado
	     /*case 112://f1  //lo identamos por que como siempre el iexplorer nos crea conflictos
	     case 113://f2
	     case 114://f3
	     case 115://f4
	     case 116://f5
	     case 117://f6
	     case 118://f7
	     case 119://f8
	     case 120://f9
	     case 121://f10
	     case 122://f11*/
	          validation=false;
   		 break;
   		 default:
   		      validation=true;
   		 break;
	  }
	  return validation;
	}
	/**
	  * Para un conjunto de datos checa si todos estan vacios o si todos estan llenos, si se cumple que todos los campos estan vacios o que todos
	  * los campos estan llenos, si alguna de estas 2 condiciones es cierta retorna falso, de lo contrario retorna el primer campo vacio o invalido.
	  * @see existEmptyValue
	  * @see checkAllEmpty
	**/
	this.checkAllEmptyOrAllFull=function(especifications){
	     if(This.checkAllEmpty(especifications.elements)){
	       return false;
	     }
	     return This.existEmptyValue(especifications);
	}
	/**
	  * Retorna true si todos los elementos indicados estan vacios, falso en caso contrario.
	  * @param Objeto Query con todos los elementos que se requiere validar.
	**/
	this.checkAllEmpty=function (elements){
		 var allEmpty=true;
		 var element;
		 elements.each(function(){
		   element=$(this);
		   value=element.val();
		   allEmpty=!(value&&value.length>0);
		   if(!allEmpty){
		   	  return false;
		   }
	     });
	     return allEmpty;
	}
	/**
	  * Valida un conjunto de campos para determinar si existe un elemento vacio y retorna el elemento vacio, de lo contrario retorna false.
	  * @param especifications Objeto con las especificasiones para las validaciones.
	  * @especifications.elements Atributo obligatorio, es un objeto JQuery haciendo referencia a los campos que se quiere validar.
	  * @especifications.invalidValues Atributo opcional, es un arreglo de valores que son considerados invalidos para los campos, si se encuetra uno es como si el campo estubiera vacio y se retorna el primer campo encontrado.
	  * @especifications.showInvalidMessage Atributo boolean que indica si se debe o no mostrar un mensaje al encontrar un elemento invalido. Por default es falso.
	  * @especifications.invalidMessage Atributo opcional con el mensaje a mostrar si se encuentra un atributo invalido. Por defecto es "El campo 'title' es requerido.", donde "title" es el atributo title del campo.
	  * @especifications.focusInvalidFile Atributo opcional, indica si se enforacra el campo invalido encontrado. Por defaul es verdadero.
	  * @return El primer elemento invalido encotrado o false si no se encontro ninguno.
	**/
	this.existEmptyValue=function(especifications){
	  var invalid=checkNonEmptyValue(especifications);
	  var e=especifications;
	  if(!invalid){
	  	return false;
	  }
	  if(e.showInvalidMessage){
	  	if(!e.invalidMessage){
	  	   e.invalidMessage='El campo "'+invalid.attr("title")+'"  es requerido.';
	  	}
	  	if(alerta && alerta.warning){
	  	   alerta.warning(e.invalidMessage);
	  	}
	  }
	  if(e.focusInvalidFile!==false){
	     invalid.focus();
	  }
	  return invalid;
	}
	
	function checkNonEmptyValue(especifications){
	  var e=especifications;
	  var invalidElement=null;
	  var element,index,value,valid;
	  if(e.invalidValues&&!(e.invalidValues instanceof Array)){
	  	 e.invalidValues=new Array(e.invalidValues);
	  }
	  valid=true;
	  e.elements.each(function(){
		  	 element=$(this);
		  	 value=element.val();
		  	 valid=!((!value)||(value.length==0));
		  	 if(valid && e.invalidValues){
		  	   for(index=0;index<invalidValues.length;index+=1){
		  	   	  if(value==invalidValues[index]){
		  	   	    valid=false;
		  	   	    break;
		  	   	  }
		  	   }
		  	 }
		  	 if(!valid){
		  	 	invalidElement=element;
		  	 	return invalidElement;
		  	 }
	  });
	  return invalidElement; 
	}
}
/**
 * indica el tab seleccionado
 */
activeTab = '#cedulaA';

/**
 * Indica la forma seleccionada
 */
FORMA_ACTUAL ='ECEForm';

/**Variables indicativas de las secciones
 *disponibles. 
 */
var CEDULA_A=1;
var CEDULA_G=2;
var CEDULA_H=3;
var CEDULA_I=4;
var CEDULA_O=5;
var CEDULA_Q=6;
var COPS=7;


$(document).ready(function() {

$.postJSON(getAppContextParaJS()+"/consultaEstudioCorreccion/obtenerUsuarioSession.do", null,function(data) {
	
	 if(data!=null && data.registroPatronal != null){
		 $('#registroPatronal').val(data.registroPatronal.substring(0,10));
		 $('#registroPatronal').prop("readonly", "readonly");
	 }
	 
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		

		
	});


	triggerPatronInternet('','folioCorreccion','folioCorreccion','onblur',undefined,true);
});

/**
 * Controla el valor del hidden el cual indica
 * en que sección se encuentra el usuario.
 * Esta variable es utilizada por JAVA.
 */
function setToFormSeccionActual(){

	/*NO APLICA SE REQUIERE MENCIONAR ESTA FUNCION
	 * NO BORRAR
	 **/
	
	
}

/**
 * Nos permite validar si la búsqueda del usuario
 * cuenta con todos los parámetros obligatorios.
 * 
 * Se manejan una variable de control
 * 
 *  - Response: Resultado final de las validaciones,
 *              en caso de que sea false en alguna ocasión
 *              este se quedará así hasta validar de nuevo.
 *              
 *  
 * @returns {Boolean} true en caso de exito, 
 * 
 */
function validaFormaCEC(){

	
	
	var formAction = getAppContextParaJS() + "/consultaEstudioCorreccion/consultar.do";
	var response = true;
	
	response =  errorLabel('cecForm','folioCorreccion','labelFolioCorreccion',response) ;
	response =  errorLabel('cecForm','periodo','periodoLabel',response);
	
	$("form#cecForm").attr('action',formAction);
	
	if(response) bloquear();
	
	
	return response;
}

/**
 * Función generica para validar a través de labels
 * campos de la forma.
 * 
 * @param formName Nombre de la forma
 * @param attribute atributo que se va a validar
 * @param labelName label en donde va a aparecer el error
 * @param response resultado de la validación final
 * @returns true si todos los campso son correctos,
 *          false en cualquier otro caso
 */
function errorLabel(formName,attribute,labelName,response){
	
	var formAttribute = "form#"+formName+" #"+attribute;
	var formLabel = "form#"+formName+" #"+labelName;

	if($(formAttribute).val() == '' || $(formAttribute).val() == '0'){
		 $(formLabel).html('<label style="color: red;"> Campo Requerido </label>');		
		  return false;
	}else{
		 $(formLabel).html('');
	}
	
	return response;
	
}

function obtenerPeriodos(){
	
	var folioCorreccion = document.getElementById("folioCorreccion").value;
	
	if(folioCorreccion!=""){
		var variable = '{"folioCorreccion":'+'"'+folioCorreccion+'"}';
		
		var variableJson = jQuery.parseJSON(variable);
					
		bloquear();
		
		$.postJSON(getAppContextParaJS()+"/consultaEstudioCorreccion/getPeriodosCorreccion.do",variableJson,function(data) { 
			if(data == null && data.length>0){
		
				alert('El aviso no tiene ejercicio asignado');
				desbloquear();
			}else{
		
				var myselect=document.getElementById("periodo");
				myselect.options.length = 1;
				for(var i = 0 ; i < data.length ; i++){
					myselect.add(new Option(data[i], data[i]));
				}	
				desbloquear();
				
				try{
					var valorPeriodoSelect = document.getElementById("periodoSeleccionado").value;
					
					document.getElementById("periodo").value = valorPeriodoSelect!="" ? valorPeriodoSelect : "0";
					
				}catch(error){}
				
			}
			
		});

	}
	
}

/**
 * Re carga los periodos despues de consultar el folio
 */
function reloadPeriodos(){
	
	if($('#folioCorreccion').val()!=""){
		obtenerPeriodos();
		 
	}
}

function alertError(){
	if($('#error').val()!=""){
		alert($('#error').val());
	}
}
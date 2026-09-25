

var arrayFIeldRequiredsEnAnterior = [  
{ nombre : 'Fracci\u00F3n' , id: '#txtNumEnAnt', tipo : 'input'},
{nombre:'Palabra(s) clave', id: '#txtPalabraEnAnt', tipo:'input'}
];




/**
 * Objeto del data table de la pantalla de resultados anteriores.
 */
var dtResultadosEnAnt;


var bSearchByPalabra = true;


/*Creamos el objeto para mostrar o ocultar el mensaje de error*/
var ctrlMsgEnAnterior = new  msgObject();
ctrlMsgEnAnterior.sDivWrapperMsg = '#wrapperMsgResultsEnAnterior';
//ctrlMsgEnAnterior.sSpanDisplayResult = '#msgResultadosEnAnt';
//ctrlMsgEnAnterior.sSpanTotalResult = '#msgTotalEnAnt';
ctrlMsgEnAnterior.sDivWrapperTooMsg ='#wrapperMsgResultsEnAnteriorToo';


/**
 * Iniciamos el data table de la cuarta pantalla
 */



handlePalabraEnAnterior = function(e){

		//Se modifica la bandera para buscar por palabra.
			bSearchByPalabra = true;
			/*Si esta checado debemos deshabilitar el campo de por numero*/
			$('#txtNumEnAnt').val('');
			$('#txtNumEnAnt').attr({'disabled': false});
			/*Y habilitar el de Palabra */
			$('#txtPalabraEnAnt').val('');
			$('#txtPalabraEnAnt').attr({'disabled': false});
			
			resetDisplayStart(dtResultadosEnAnt);
			
}


handleNumeroEnAnterior = function(e){
	/*Inicializacion de los butones*/
			//Se modifica la bandera para buscar por numero.
			bSearchByPalabra = false;
			/*Si esta checado debemos deshabilitar el campo de por palabra*/
			$('#txtPalabraEnAnt').val('');
			$('#txtPalabraEnAnt').attr({'disabled': false});
			/*Y habilitar el de Numero */
			$('#txtNumEnAnt').val('');
			$('#txtNumEnAnt').attr({'disabled': false});
			
			
			resetDisplayStart(dtResultadosEnAnt);
}


/*Funcion para validar que se haya capturado al menos un filtro*/
function hasFIltroCapturadoEnAnterior(){
		
	var num = $('#txtNumEnAnt').val();
	var pal = $('#txtPalabraEnAnt').val();

	if(num == '' &&  pal == '')
		return 	ctrlValidaDatosCatalogos.checkCapturaCompleta(arrayFIeldRequiredsEnAnterior, true);	
	
	var oCampo;
	var ctrlValidaDatosEnAnterior= new ctrlValidaDatos();
	
var sFilter;
			if(bSearchByPalabra){
						/*recuperamos el txto de la palabra clave.*/
						sFilter = $('#txtPalabraEnAnt').val();
						oCampo = arrayFIeldRequiredsEnAnterior[1];
					}else{
						/*recuperamos el txt del numero*/
						sFilter = $('#txtNumEnAnt').val();
						oCampo = arrayFIeldRequiredsEnAnterior[0];
					}
					
			
			return ctrlValidaDatosEnAnterior.checkFiltroCapturado(oCampo);					
					
}
	



$(document).ready(function() {
	
	
	
	
	
	
	var txtPalabraEnAnt = $('#txtPalabraEnAnt').button().bind('keypress', handleEnter).bind('focus', handlePalabraEnAnterior).bind('click', handlePalabraAnterior).addClass('cajaTexto');
	txtPalabraEnAnt.attr({'disabled':false});
	var txtNumEnAnt = $('#txtNumEnAnt').button().attr({'disabled':false}).bind('keypress', handleEnter).bind('focus', handleNumeroEnAnterior).bind('click', handleNumeroAnterior).addClass('cajaTexto');;
	
	
	

	
	/*
	 * Configuracion del datatable de resultados anteriores
	 * 
	 * */
	dtResultadosEnAnt = $('#dtResultadosEnAnterior').dataTable({
			bJQueryUI : true,
			bFilter : false,
			bInfo:true,
			bSort: false,
			"bPaginate": true,
			"bAutoWidth" : false,
			"bServerSide" : true,
			"sPaginationType": "full_numbers",
			"aoColumns" : [ 
		                
						{
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' + oObj.aData['desFraccion'] +'" id="radio" class="radioFraccion" name="radio" onclick="fraccionCtrl.getFraccion(this.value);"/> ';
				return retVal;
				
			}, 
			aTargets: [0]
			},{
					"sTitle" : "Fracci&oacute;n",
					"mDataProp" : "desFraccion",
					"sClass": "dtFraccionClassColumn"
				}, {
					"sTitle" : "Actividad",
					"mDataProp" : "nomActividad",
					"sClass": "dtCenterClassColumn"
					
				}, {
					"sTitle" : "Descripci&oacute;n",
					"mDataProp" : "desActividad",
					"sClass":"dtJustifyClassHLColumn"
				}, {
					"sTitle" : "Clase",
					"mDataProp" : "cveClase",
					"sClass": "dtCenterClassColumn",
								"bVisible" : showClase
				}
				
				
				],

				"bProcessing" : true,
				"sAjaxSource" : 'fraccion/buscarFraccionEnCatalogoAnterior.do',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					
					
				
					
					var vSearch ='';
					
					var bSource = 'dpPalabraClave';
					
					if(bSearchByPalabra){
						//Buscamos por palabra
						bSource = 'dpPalabraClave';
						/*recuperamos el txto de la palabra clave.*/
						vSearch = $('#txtPalabraEnAnt').val().toUpperCase();
					}else{
						//Buscamos por numero
						bSource = 'dpNumero';
						/*recuperamos el txt del numero*/
						vSearch = $('#txtNumEnAnt').val();
						
						
						
					}
					
					/*Ocultamos el texto remarcado*/
					$('#resultadosEnAnterior td').removeHighlight();
					
					
					/*Ocultamos el mensaje*/
//					hiddeMsgResultsAnt();
					ctrlMsgEnAnterior.hidde();
					
					
					if(hasFIltroCapturadoEnAnterior()){
					
							aoData.push({
								"name" : "sSearch",
								"value" : vSearch

							}, {
								"name" : "time",
								"value" : new Date()
							}, {
								"name" : "dispatch",
								"value" : bSource
							});
						
							
					$.postJSON(sSource, aoData, function(data) {
						
						var sustantivos = new Array(); 
						sustantivos = getSentences(data);
						
						fnCallback(data);
						/*Mostramos el mensaje de resultados*/
//					 	showMsgResultsAnt( data.iTotalDisplayRecords, data.iTotalRecords  );
						ctrlMsgEnAnterior.show( data.iTotalRecords  , data.iTotalDisplayRecords);
					 
					 
					 /*resaltamos el texto que coincida con la busqueda*/
					 
						/*
						 * LUDS: Se resolvio el problema con el subrayado, el cual 
						 * fue un problema de arreglos de arreglos.
						 */
							var c = 0;
						  $('#resultadosEnAnterior td.dtJustifyClassHLColumn').each(function(){
							  var sentence = sustantivos[c];
							 innerHighlightSingle(this, sentence);
							  c++;
						  });
					  
//						  for (var count=0; count<sustantivos.length;count++) {
//					  
//						  var sentence = sustantivos[count];
//						  
//						  $('#resultadosEnAnterior td.dtJustifyClassHLColumn').highlightClasif(  sentence  );
//					  
//					  }
					  
						  
						 /*Se debe de validar si la bsuqueda fue 
						  * por palabra,para poder habilitar 
						  * las palabras concretas
						  * LUDS 29/11/2012
						  * 
						  * 
						  * */ 
						  if(bSearchByPalabra){
							  /*Marcamos las palabras concretas encontradas*/
								 for (var i = 0 ; i < data.palabrasConcretas.length ; i++){
									 $('#resultadosEnAnterior td').highlight(  data.palabrasConcretas[i] );
								 }
						  }
					  
						 
					 
						
					});
					}else{
						fnCallback(dataEmpty);
					
					}
				}
			});

	
	
	
	
	
});



function limpiarFormularioEnAnterior(){
	limpiarFormulario('#wrapperFiltersEnAnterior');
	dtResultadosEnAnt.fnDraw();
	
}








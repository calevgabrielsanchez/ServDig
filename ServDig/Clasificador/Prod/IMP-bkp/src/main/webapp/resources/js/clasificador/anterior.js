

var arrayFIeldRequiredsAnterior = [  
{ nombre : 'Fracci\u00F3n' , id: '#txtNumAnt', tipo : 'input'},
{nombre:'Palabra(s) clave', id: '#txtPalabraAnt', tipo:'input'}
];




/**
 * Objeto del data table de la pantalla de resultados anteriores.
 */
var dtResultadosAnt;


var bSearchByPalabra = true;


/*Creamos el objeto para mostrar o ocultar el mensaje de error*/


var ctrlMsgAnterior = new  msgObject();
ctrlMsgAnterior.sDivWrapperMsg = '#wrapperMsgResultsAnterior';
//ctrlMsgAnterior.sSpanDisplayResult = '#msgResultadosAnt';
//ctrlMsgAnterior.sSpanTotalResult = '#msgTotalAnt';
ctrlMsgAnterior.sDivWrapperTooMsg = '#wrapperMsgResultsAnteriorToo';

/**
 * Iniciamos el data table de la segunda pantalla
 */



handlePalabraAnterior = function(e){

		//Se modifica la bandera para buscar por palabra.
			bSearchByPalabra = true;
			/*Si esta checado debemos deshabilitar el campo de por numero*/
			$('#txtNumAnt').val('');
			$('#txtNumAnt').attr({'disabled': false});
			/*Y habilitar el de Palabra */
			$('#txtPalabraAnt').val('');
			$('#txtPalabraAnt').attr({'disabled': false});
			
			resetDisplayStart(dtResultadosAnt);
			
}


handleNumeroAnterior = function(e){
	/*Inicializacion de los butones*/
			//Se modifica la bandera para buscar por numero.
			bSearchByPalabra = false;
			/*Si esta checado debemos deshabilitar el campo de por palabra*/
			$('#txtPalabraAnt').val('');
			$('#txtPalabraAnt').attr({'disabled': false});
			/*Y habilitar el de Numero */
			$('#txtNumAnt').val('');
			$('#txtNumAnt').attr({'disabled': false});
			
			
			resetDisplayStart(dtResultadosAnt);
}


/*Funcion para validar que se haya capturado al menos un filtro*/
function hasFIltroCapturadoAnterior(){
		
	var num = $('#txtNumAnt').val();
	var pal = $('#txtPalabraAnt').val();

	if(num == '' &&  pal == '')
		return 	ctrlValidaDatosCatalogos.checkCapturaCompleta(arrayFIeldRequiredsAnterior, true);	
	
	var oCampo;
	var ctrlValidaDatosAnterior= new ctrlValidaDatos();
	
var sFilter;
			if(bSearchByPalabra){
						/*recuperamos el txto de la palabra clave.*/
						sFilter = $('#txtPalabraAnt').val();
						oCampo = arrayFIeldRequiredsAnterior[1];
					}else{
						/*recuperamos el txt del numero*/
						sFilter = $('#txtNumAnt').val();
						oCampo = arrayFIeldRequiredsAnterior[0];
					}
					
			
			return ctrlValidaDatosAnterior.checkFiltroCapturado(oCampo);					
					
}
	



$(document).ready(function() {
	
	
	
	
	
	
	var txtPalabraAnt = $('#txtPalabraAnt').button().bind('keypress', handleEnter).bind('focus', handlePalabraAnterior).bind('click', handlePalabraAnterior).addClass('cajaTexto');
	txtPalabraAnt.attr({'disabled':false});
	var txtNumAnt = $('#txtNumAnt').button().attr({'disabled':false}).bind('keypress', handleEnter).bind('focus', handleNumeroAnterior).bind('click', handleNumeroAnterior).addClass('cajaTexto');;
	
	
	

	
	/*
	 * Configuracion del datatable de resultados anteriores
	 * 
	 * */
	dtResultadosAnt = $('#dtResultadosAnterior').dataTable({
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
					"sTitle" : "Fraccion",
					"mDataProp" : "desFraccion",
					"sClass": "dtFraccionClassColumn"
				}, {
					"sTitle" : "Actividad",
					"mDataProp" : "nomActividad",
					"sClass": "dtCenterClassColumn"
					
				}, {
					"sTitle" : "Descripcion",
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
				"sAjaxSource" : 'fraccion/buscarFraccionPorCatalogoAnterior.do',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					
					
				
					
					var vSearch ='';
					
					var bSource = 'dpPalabraClave';
					
					if(bSearchByPalabra){
						//Buscamos por palabra
						bSource = 'dpPalabraClave';
						/*recuperamos el txto de la palabra clave.*/
						vSearch = $('#txtPalabraAnt').val().toUpperCase();
					}else{
						//Buscamos por numero
						bSource = 'dpNumero';
						/*recuperamos el txt del numero*/
						vSearch = $('#txtNumAnt').val();
						
						
						
					}
					
					/*Ocultamos el texto remarcado*/
					$('#resultadosAnterior td').removeHighlight();
					
					
					/*Ocultamos el mensaje*/
//					hiddeMsgResultsAnt();
ctrlMsgAnterior.hidde();
					
					if(hasFIltroCapturadoAnterior()){
					
					
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
					 ctrlMsgAnterior.show( data.iTotalRecords  , data.iTotalDisplayRecords);
					 
					 
					 /*resaltamos el texto que coincida con la busqueda*/
					 
					 for (var count=0; count<sustantivos.length;count++) {
						  
						  var sentence = sustantivos[count];
						  
						  $('#resultadosAnterior td.dtJustifyClassHLColumn').highlightClasif(  sentence  );
					  
					  }
					 
					 
					 /*Marcamos las palabras concretas encontradas*/
					 for (var i = 0 ; i < data.palabrasConcretas.length ; i++){
						 $('#resultadosAnterior td').highlight(  data.palabrasConcretas[i] );
					 }
					 
					  
					  
					 
					 	/**
					 	 * Para ocultar los mensajes depues de X tiempo
					 	 */
					 //	setTimeout( hiddeMsgResultsAnt , timeOutOffMsgs );
						
					});
					}else{
						fnCallback(dataEmpty);
					
					}
				}
			});

	
});



function limpiarFormularioAnterior(){
	limpiarFormulario('#wrapperFiltersAnterior');
	dtResultadosAnt.fnDraw();
	

	
}







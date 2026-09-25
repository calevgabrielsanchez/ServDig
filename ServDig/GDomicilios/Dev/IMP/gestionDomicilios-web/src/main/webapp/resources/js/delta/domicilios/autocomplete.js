


$(document).ready(function() {
	
	
	//Inicializamos los componentes de autocompletar
	
	window.query_cache = {};
	
	 typeaheadBuilder("vialidadPrimaria");
	 typeaheadBuilder("vialidadReferenciaPrimaria");
	 typeaheadBuilder("vialidadReferenciaSecundaria");
	 typeaheadBuilder("vialidadReferenciaPosterior");
});


/**
 * Builder de los elementos input text para hacerlos autocompletar.
 * @param idElemento
 */
function typeaheadBuilder(idElemento) {
	var isVialidadPrimaria = idElemento == "vialidadPrimaria";
	var numItems = isVialidadPrimaria ? 11 : 10;
	
	$('input#' + idElemento + 'Input').typeahead({
		minLength: 1,
		items: numItems,
		source: function (query, process) {
			 // if in cache use cached value, if don't wanto use cache remove this if statement
			if(query_cache[query]){
				process(query_cache[query]);
				return;
			}
			
			if( typeof searching != "undefined") {
				clearTimeout(searching);
				process([]);
			}
			
			searching = setTimeout(function() {
				
				$('label#' + idElemento + 'Error').hide();
				if(isVialidadPrimaria) {
					$("#vialidadPrimaria\\.clave\\.errors").hide();
					verificarErroresRegreso();
				}
				// ----------------------------------------------------------------------------------------
				// Cuando se valida el formulario se pueden agregan errores en caso de datos invalidos.
				// ----------------------------------------------------------------------------------------
				fnHideErrores("form#formComplemento #"+idElemento);
				
				if(request != null) {
					request.abort();
				}
				
		        request = $.ajax({
		        	url : '/${mvn.web.app.root}'+'/domicilio/nacional/ubicar/get/vialidades/por-nombre',
		            type: 'post',
		            data : {
						cveEnt : $('input#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(),
						cveMun : $('input#asentamiento\\.localidad\\.municipio\\.clave').val(),
						cveLoc : $('input#asentamiento\\.localidad\\.clave').val(),
						nomVialidad : query,
						periodo: 4,
						isVialidadP: isVialidadPrimaria
					},
		            dataType: 'json',
		            beforeSend : function() {
		            	$('#' + idElemento + ' img').show();
		            	limpiarElementosVialidades(idElemento);
		            },
		            success: function (result) {
		            	var resultList = $.map( result.vialidades, function( item ) {
							 var aItem = {
								 nomVialidad: item.nombre,
								 idTipoVialidad: item.tipoVialidad.clave,
								 descTipoVialidad: item.tipoVialidad.descripcion
							 };
							 return JSON.stringify(aItem);
						 });
	
		                return process(resultList);
		            },
		            complete : function() {
		            	$('#' + idElemento + ' img').hide();
		            },
		            error : function (error) {
		            	if (error.statusText != 'abort') {
			            	$('label#' + idElemento + 'Error').text('Sin resultados en la b\u00fasqueda');
			            	$('label#' + idElemento + 'Error').show();
			            	
			            	limpiarElementosVialidades(idElemento);
		            	}
		            }
		        });
		        
		        return request;
		        
			 }, 600); // 300 ms
	    },
	    matcher: function (obj) {
	        var item = JSON.parse(obj);
	        return isVialidadPrimaria ? true : ~item.nomVialidad.toLowerCase().indexOf(this.query.toLowerCase());
	    },
	    sorter: function (items) {          
	       var beginswith = [], caseSensitive = [], caseInsensitive = [];
	        while (aItem = items.shift()) {
	            var item = JSON.parse(aItem);
	            if (!item.nomVialidad.toLowerCase().indexOf(this.query.toLowerCase())){
	            	beginswith.push(JSON.stringify(item));
	            } else if (~item.nomVialidad.indexOf(this.query)) {
	            	caseSensitive.push(JSON.stringify(item));
	            } else {
	            	caseInsensitive.push(JSON.stringify(item));
	            }
	        }
	        return beginswith.concat(caseSensitive, caseInsensitive);

	    },
	    highlighter: function (obj) {
	        var item = JSON.parse(obj);
	        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');
	        
	        if(item.idTipoVialidad != 0) {
	        return '<div class=\'row-fluid\'><div class=\'row-fluid\'><div class=\'span8 nomVialidad\'>' + item.nomVialidad.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	            return '<strong>' + match + '</strong>';
	        }) + '</div><div class=\'span4 descElementos\'>Vialidad</div></div>' 
	        + '<div class=\'row-fluid\'><div class=\'span8\'><strong class=\'descTipoVialidad\'>' + item.descTipoVialidad + '</strong></div>'
	        + '<div class=\'span4 descElementos\'>Tipo Vialidad</div></div></div>';
	        } else {
	        	return '<div><i class="glyphicon glyphicon-exclamation-sign" style="margin-right: 7px; font-size: 13px;"></i><label style="cursor: pointer; display: inline; '
	        		+ 'vertical-align: top;">&iquest;No encuentras tu vialidad? Da clic aqu&iacute;</label></div>';
	        }
	       
	    },
	    updater: function (obj) {
	        var item = JSON.parse(obj);
	        
	        if(item.idTipoVialidad != 0) {
	        	$.blockUI();
		        $.ajax({
		        	url : '/${mvn.web.app.root}'+'/domicilio/nacional/ubicar/get/vialidad/elegida',
		            type: 'post',
		            data : {
						cveEnt : $('input#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(),
						cveMun : $('input#asentamiento\\.localidad\\.municipio\\.clave').val(),
						cveLoc : $('input#asentamiento\\.localidad\\.clave').val(),
						nomVialidad : item.nomVialidad,
						periodo: 4,
						cveTipoVialidad : item.idTipoVialidad
					},
		            dataType: 'json',
		            success: function (result) {
		            	$('input#' + idElemento + '\\.clave\\.hidden').val(result.vialidadElegida.clave);
		            	$('input#' + idElemento + '\\.tipoVialidad\\.descripcion\\.hidden').val(result.vialidadElegida.tipoVialidad.descripcion);
		            	$('input#' + idElemento + '\\.tipoVialidad\\.clave\\.hidden').val(result.vialidadElegida.tipoVialidad.clave);
		            	
		            	if (idElemento == 'vialidadPrimaria') {
		            		$('#asentamiento\\.localidad\\.clave').val(result.localidadVialidadPrimaria.clave);
		            		$('#calle').val("");
		            		setDescripcionComboLocalidad('asentamiento\\.localidad\\.clave','asentamiento\\.localidad\\.nombre');
		            	}
		            	
		            	$.unblockUI();
		            }
		        });
		        
		        $('input#' + idElemento + 'Lbl').val(item.descTipoVialidad);
		        return item.nomVialidad;
	        } else {
	        	$('input#' + idElemento + '\\.clave\\.hidden').val("");
            	$('input#' + idElemento + '\\.tipoVialidad\\.descripcion\\.hidden').val("");
            	$('input#' + idElemento + '\\.tipoVialidad\\.clave\\.hidden').val("");
            	
            	if (idElemento == 'vialidadPrimaria') {
            		$('#asentamiento\\.localidad\\.clave').val("");
            	}
            	$('input#' + idElemento + 'Lbl').val("");
            	
            	$("div#divVialidadPrimaria").hide();
            	$("div#vialidadNoEncontrada").show();
            	return "";
	        }
	    }
	});
}



/**
 * 
 * @param idCombo
 * @param idDescripcion
 */
function setDescripcionComboLocalidad(idCombo,idDescripcion) {
	var texto = $("select#"+idCombo+" option:selected").html();
	$("#"+idDescripcion).val(texto);
}


/**
 * 
 * @param idElemento
 */
function limpiarElementosVialidades(idElemento) {
	$('input#' + idElemento + 'Lbl').val('');
	$('input#' + idElemento + '\\.clave\\.hidden').val('');
	$('input#' + idElemento + '\\.tipoVialidad\\.descripcion\\.hidden').val('');
	$('input#' + idElemento + '\\.tipoVialidad\\.clave\\.hidden').val('');
}
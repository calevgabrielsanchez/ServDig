var _charts = new Object();
var _baseColorsArray = ['#89bbd8','#4da944','#FFA500','#999966','#2E8B57',
                        '#FFC0CB','#d9ce00','#754c24','#2eb9b4','#0e2e42',
                        '#6F00DD','#DD006D','#0000CC','#D2691E','#778899',
                        '#AA5500','#00FFFF','#3C7700','#FF8000','#CCCCCC',
                        '#C71585','#FF4040','#CCFF66','#3333CC','#00CC00'];

$(document).on("pagecreate", "#demo-page", function() {
	$(document).on("swipeleft swiperight", "#demo-page", function(e) {
		if ($(".ui-page-active").jqmData("panel") !== "open") {
			if (e.type === "swipeleft") {
				$("#right-panel").panel("open");
			} else if (e.type === "swiperight") {
				$("#left-panel").panel("open");
			}
		}
	});
	
	$('#lstGraficas').on('filterablefilter', function( event, ui ) {
		ui.items.each(function( index ) {
			if ($(this).hasClass('ui-collapsible') 
					&& !$(this).hasClass('ui-screen-hidden')
					&& $('input[data-type=search]').val() != '') {
				$(this).collapsible('expand');
				
				$('ul.ui-listview li a', $(this)).each(function(idx, elemento) {
					if ($(this).text().toLowerCase().indexOf($('input[data-type=search]').val()) == -1){
						$(this).addClass('ui-screen-hidden');
					}
				});
				
			} else if ($('input[data-type=search]').val() == '') {
				$('.ui-screen-hidden','#lstGraficas').removeClass('ui-screen-hidden');
				$('.ui-collapsible','#lstGraficas').collapsible('collapse');
			}
		});
	});
		
	$('.graficaLink').click(function(e){
		e.preventDefault();
		
		$('div#errorGeneral').hide();
		$('div#graficasWrapper').empty();
		showLoader();
					
		$.each(_charts, function(idx){
			delete _charts[idx];
		});
				
		$( "#left-panel" ).panel( "close" );
		
		var _graficaUrl = $(this).attr('grafica-url');
				
		setTimeout(function() {
			$.ajax({
				url : _graficaUrl,
				dataType : 'json',
				type : 'get',
				success : function(_lstWrapper) {
					
					var _baseChartId = "cifrasChart";
						
					setTimeout(function() {
						$.each(_lstWrapper, function(_idx, _wrapper){
							
							$('div#graficasWrapper').append(generarGraficaContainer(_wrapper));
							
							var _chart = null;
							var _chartId = _baseChartId + _wrapper.identificador;
																					
							if ($('#' + _chartId).length <= 0) {
								$('div#grafica' + _wrapper.identificador).append('<div id="' + _chartId + '"></div>');
							}
							
							if (_wrapper.errorFormGeneral == null || _wrapper.errorFormGeneral == '' 
								|| _wrapper.errorFormGeneral == 'null') {

								$('div#' + _chartId).css("height","300px");
								
								
								if (_wrapper.tipo == 1) {
									
									var _options = {
										element : _chartId,
										data : _wrapper.data,
										xkey : _wrapper.xKey,
										ykeys : _wrapper.yKeys,
										labels : _wrapper.labels,
										stacked : true,
										resize : true,
										barColors: function (row, series, type) {
											var _baseColor = _baseColorsArray[row.x];
											if (type === 'bar') {
												if (series.index == 0) {
													return _baseColor;
												} else {
													return '#' + shadeColor(_baseColor, series.index * 10);
												}
											} else {
												return '#000';
											}
										 },
										 xLabelFormat : function(x){
											return (x.label.split("#"))[0];
										}
									};
									
									if (_wrapper.mostrarDetalleDia == false) {
										_options.hoverCallback = function (index, options, content, row) {
											generarDetalleDia(_wrapper, index, options, content, row);
										};
									}
									
									_chart = new Morris.Bar(_options);									
								} else if (_wrapper.tipo == 2) {
									var _fnToFormatDate = null;
									
									if (_wrapper.tipoFormatoEjeX == 1) {
										_fnToFormatDate = formatFechaDia;
									} else if (_wrapper.tipoFormatoEjeX == 2) {
										_fnToFormatDate = formatFechaMes;
									}
									
									var _options = {
										element : _chartId,
										data : _wrapper.data,
										xkey: _wrapper.xKey,
										ykeys: _wrapper.yKeys,
										labels: _wrapper.labels,
										resize : true,
										smooth : false,
										lineColors : _baseColorsArray,
										dateFormat : function(dateTmp){
											return _fnToFormatDate(dateTmp); 
										},
										xLabelFormat : function(dateTmp){
											return _fnToFormatDate(dateTmp);
										}
									};
									
									if (_wrapper.mostrarDetalleDia == false) {
										_options.hoverCallback = function (index, options, content, row) {
											generarDetalleDia(_wrapper, index, options, content, row);
										};
									}
									
									_chart = new Morris.Line(_options);
								} else if (this.tipo == 3) {
									_chart = new Morris.Donut({
										element : _chartId,
										data : _wrapper.data,
										resize : true,
										colors : _baseColorsArray
									});
								}
								
								generarCifrasTotales(_wrapper);
								_charts[_wrapper.identificador] = _chart;
								_chartId = null;
							} else {
								$('div#' + _chartId).css("height","auto");
								$('div#'+ _chartId).html('<div style="text-align: center; width: 80%; margin: 0px auto;" class="alert alert-warning"><strong>'
									+ _wrapper.errorFormGeneral + '</strong></div>');
							}
						});
					}, 300);
				},
				error : function() {
					$('div#graficasWrapper').html('<div style="text-align: center; width: 80%; margin: 0px auto;" class="alert alert-danger"><strong>Error inesperado al generar la gráfica</strong></div>');
				},
				complete : function() {
					hideLoader();
				}
			});
		}, 500);
	});
	
	$(document).on('click', '.icon-refresh', function() {
		var _url = $(this).attr('url');
		var _id = $(this).attr('ident');
		
		$('div#errorGeneral').hide();
		
		$.ajax({
			url : _url,
			dataType : 'json',
			type : 'get',
			beforeSend : function(){
				$('div#contenidoGrafica' + _id).hide();
				showLoader();
			},
			success : function(_wrapper) {
				if (_wrapper.errorFormGeneral == null || _wrapper.errorFormGeneral == '' 
					|| _wrapper.errorFormGeneral == 'null') {
					$('div#contenidoGrafica' + _id).show();
					generarCifrasTotales(_wrapper);
					_charts[_wrapper.identificador].setData(_wrapper.data);
				} else {
					$('div#errorGeneral').html('<strong>Error inesperado al actualizar la gráfica</strong>').show();
				}
			},
			error : function(){
				$('div#errorGeneral').html('<strong>Error inesperado al actualizar la gráfica</strong>').show();
			}, complete : function() {
				hideLoader();	
			}
		});
	});
	
	$('ul#lstGraficas').children().first().children().trigger('click');
});

function generarGraficaContainer(_wrapper) {
	var _container = '<div id="graficaContainer'
			+ _wrapper.identificador
			+ '" class="panel panel-default">'
			+ '<div id="tituloGrafica'
			+ _wrapper.identificador
			+ '" class="panel-heading">'
			+ '<div class="row"><div class="col-xs-10 vcenter">'
			+ '<h1 class="panel-title" style="font-weight: bold; text-transform: uppercase;">'
			+ _wrapper.nombre
			+ '</h1>'
			+ '<div id="descripcionGrafica'
			+ _wrapper.identificador
			+ '"><h5 style="font-style: italic;">'
			+ _wrapper.descripcion
			+ '</h5></div>'
			+ '</div><div class="col-xs-1 col-xs-offset-1 vcenter"><i class="icon-refresh" '
			+ 'url="' + _wrapper.url + '" '
			+ 'ident="' + _wrapper.identificador + '"'
			+ 'style="float: right;"></i>'
			+ '</div></div></div>'
			+ '<div id="contenidoGrafica'
			+ _wrapper.identificador
			+ '" class="panel-body">'
			+ '<div class="row"><div id="granTotal' + _wrapper.identificador
			+ '"></div>';
	
			if (_wrapper.tipo != 3) {
				_container += '<div class="col-md-12" id="grafica'
					+ _wrapper.identificador
					+ '"></div></div>'
					+ '<div class="row">';
					
				if (_wrapper.mostrarDetalleDia == false) {
					_container += '<div class="col-md-6" id="totalDiaGrafica'
						+ _wrapper.identificador + '">'
						+ '<div id="detalleTotalDia' + _wrapper.identificador
						+ '"></div></div>';
					
					_container += '<div class="col-md-6" id="totalesGrafica'
						+ _wrapper.identificador + '">'
						+ '<div id="detalleTotales' + _wrapper.identificador
						+ '"></div></div>';
				} else {
					_container += '<div class="col-md-6 col-md-offset-3" id="totalesGrafica'
						+ _wrapper.identificador + '">'
						+ '<div id="detalleTotales' + _wrapper.identificador
						+ '"></div></div>';
				}
			} else {
				_container += '<div class="col-md-6" id="grafica'
					+ _wrapper.identificador
					+ '"></div>'
					+ '<div class="col-md-6" id="totalesGrafica'
					+ _wrapper.identificador + '">'
					+ '<div id="detalleTotales' + _wrapper.identificador
					+ '"></div></div>';
			}
			
			_container += '</div></div></div>';
	
	return _container;
}

function generarCifrasTotales(_wrapper) {
	
	var totalMov = 0;
	var totalParcial = 0;
	var _totalsArray = new Array();
	var _labelsArray = null;
	
	if (_wrapper.tipo == 1 || _wrapper.tipo == 3 ) {
		_labelsArray = new Array();
		
		$.each(_wrapper.data, function(idx, elemento) {
			totalParcial = 0;
			$.each(_wrapper.yKeys, function(idx, key) {
				totalParcial += elemento[key];
			});
			_totalsArray.push(totalParcial);
			_labelsArray.push(elemento[_wrapper.xKey]);
			totalMov += totalParcial;
		});
	} else if (_wrapper.tipo == 2) {
		_labelsArray = _wrapper.labels;
		
		var _totalsTmp = new Object(); 
		$.each( _wrapper.data, function( idx, elemento ) {
			$.each( _wrapper.yKeys, function( idx2, yKey ) {
				totalMov += elemento[yKey];
				
				if (_totalsTmp[yKey] == null) {
					_totalsTmp[yKey] = 0;
				}
				
				_totalsTmp[yKey] += elemento[yKey];
			});
		});
		
		$.each(_totalsTmp, function(idx, elemento){
			$.each( _wrapper.yKeys, function( idx2, yKey ) {
				_totalsArray.push(_totalsTmp[yKey]);
			});
		});
	}
	
	generarLabels(_labelsArray, _baseColorsArray, _totalsArray, _wrapper.identificador);
	setTotalMovimientos(totalMov, _wrapper.identificador, _wrapper.graficaDiaActual);
}

function generarLabels(labelsArray, colorsArray, totalsArray, identificador) {

	$('div#detalleTotales' + identificador).empty();
	
	var tblLables = '<div style=\'margin-top: 10px; text-align: center;\'><span class=\'chartLabels\'>';
	tblLables += '<table class=\'chartLabels\'><tbody>';

	for ( var i = 0; i < labelsArray.length; i++) {
		tblLables += '<tr>';
		tblLables += '<td style=\'background-color:' + colorsArray[i];
		tblLables += '; width: 15px;\'></td>';
		tblLables += '<td style=\'width: 5px;\'></td>';
		tblLables += '<td>' + labelsArray[i].replace('#', ' - ') + '</td>';
		tblLables += '<td style=\'width: 10px;\'></td>';
		tblLables += '<td class="cifraTotal">' + formatNumber(totalsArray[i]) + '</td>';
		tblLables += '</tr>';

		if (i < labelsArray.length - 1) {
			tblLables += '<tr style=\'height: 6px;\'><td></td></tr>';
		}
	}

	tblLables += '</tbody></table></span></div>';

	$('div#detalleTotales' + identificador).html(tblLables);
}

function generarLabelsDia(_wrapper, colorsArray, data) {
	
	var labelsArray = _wrapper.labels;
	var yKeys = _wrapper.yKeys; 
	var identificador = _wrapper.identificador;
	
	$('div#detalleTotalDia' + identificador).empty();
	
	if (_wrapper.tipoFormatoEjeX == 1) {
		_fnToFormatDate = formatFechaDia;
	} else if (_wrapper.tipoFormatoEjeX == 2) {
		_fnToFormatDate = formatFechaMes;
	}
	
	var tblLables = '<div style=\'margin-top: 10px; text-align: center;\'><span class=\'chartLabels\'>';
	tblLables += '<table class=\'chartLabels\'><tbody>';
	
	tblLables += '<tr><td colspan="5" style="text-align: center;"><strong>' 
		+ _fnToFormatDate(data.fecha) 
		+ '</strong></td></tr>';
	
	for ( var i = 0; i < labelsArray.length; i++) {
		tblLables += '<tr>';
		tblLables += '<td style=\'background-color:' + colorsArray[i];
		tblLables += '; width: 15px;\'></td>';
		tblLables += '<td style=\'width: 5px;\'></td>';
		tblLables += '<td>' + labelsArray[i].replace('|', ' - ') + '</td>';
		tblLables += '<td style=\'width: 10px;\'></td>';
		tblLables += '<td class="cifraTotal">' + formatNumber(data[yKeys[i]]) + '</td>';
		tblLables += '</tr>';

		if (i < labelsArray.length - 1) {
			tblLables += '<tr style=\'height: 6px;\'><td></td></tr>';
		}
	}

	tblLables += '</tbody></table></span></div>';

	$('div#detalleTotalDia' + identificador).html(tblLables);
}

function setTotalMovimientos(totalMov, identificador, graficaDiaActual) {
	
	$('div#granTotal' + identificador).empty();
	
	var totalContainer = '<div style="text-align: center;">';
	totalContainer += '<h2><strong>Total de Movimientos: ';
	totalContainer += formatNumber(totalMov);
	totalContainer += '</strong></h2>';
	
	if (graficaDiaActual == true) {
		var date = new Date();
		
		totalContainer += '<h4>Fecha: ';
		totalContainer += pad(date.getDate(), 2)  + '/' + pad((date.getMonth() + 1),2) + '/' +  date.getFullYear();
		totalContainer += '</h4>';
	}
		
	totalContainer += '</div>';

	$('div#granTotal' + identificador).html(totalContainer);
	
}

function formatNumber(valor) {
	return valor.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

var formatFechaDia = function (fecha) {
	var date = getDate(fecha);	
	return pad(date.getDate(), 2) + '/' + pad((date.getMonth() + 1), 2) + '/'
			+ date.getFullYear();
};

var formatFechaMes = function (fecha) {
	var date = getDate(fecha);
	var meses = [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
	              "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ];												
	return meses[date.getMonth()] + ' ' + date.getFullYear();
};

function showLoader() {
    window.setTimeout(function(){
        $.mobile.loading('show');
    }, 1);
};

function getDate(fecha) {
	var date = null;
	
	if (/(\d{4})-(\d{2})-(\d{2})/.test(fecha)) {
		date = new Date(fecha.replace( /(\d{4})-(\d{2})-(\d{2})/, "$2/$3/$1"));
	} else {
		date = new Date(fecha);
	}
	
	return date;
}

function hideLoader() {
    window.setTimeout(function(){
        $.mobile.loading('hide');
    }, 1);
};

function pad (str, max) {
	str = str.toString();
	return str.length < max ? pad("0" + str, max) : str;
}

function ajustarGraficas() {
	 $.each(_charts, function(idx){
		 this.redraw();
	 });
}

function shadeColor(color, percent) {
	color = color.replace('#','');
    var num = parseInt(color,16),
    amt = Math.round(2.55 * percent),
    R = (num >> 16) + amt,
    G = (num >> 8 & 0x00FF) + amt,
    B = (num & 0x0000FF) + amt;
    return (0x1000000 + (R<255?R<1?0:R:255)*0x10000 + (G<255?G<1?0:G:255)*0x100 + (B<255?B<1?0:B:255)).toString(16).slice(1);
}

var generarDetalleDia = function (_wrapper, index, options, content, row) {
	generarLabelsDia(_wrapper, _baseColorsArray, row);
};
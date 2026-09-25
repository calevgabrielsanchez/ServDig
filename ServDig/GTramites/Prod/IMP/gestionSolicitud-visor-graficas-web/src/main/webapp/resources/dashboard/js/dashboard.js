var origenChart;
var origenHoyChart;
var rankingChart;
var estadosChart;
var tramitesDT;

$(document).ready(function() {
	dashboardTramite.init();
	
	$('#data_5 .input-daterange').datepicker({
		format : 'dd/mm/yyyy',
        keyboardNavigation : false,
        forceParse : false,
        autoclose : true,
        language : 'es',
        disableTouchKeyboard : true,
        //endDate : new Date(),
        todayHighlight : true
    });
});

var dashboardTramite = {
	init : function() {
				
		$('div.panel-dashboard').bind('load', function(event, periodo){
			var _self = this;
			var fechaInicio = null;
			var fechaFin = null;
			
			$(_self).attr('periodo',periodo);
			
			if (periodo == 5) {
				fechaInicio = $('input#fechaInicio').val();
				fechaFin = $('input#fechaFin').val();
			}
			
			if ($(this).hasClass('panel-gran-total')) {
				loadGranTotal(_self);
			} else if ($(this).hasClass('panel-contadores')) {
				loadPanelContadores(_self, periodo, fechaInicio, fechaFin);
			} else if ($(this).hasClass('panel-cifras-origen')) {
				loadContadorPorOrigen(_self, periodo, fechaInicio, fechaFin);
			} else if ($(this).hasClass('panel-ranking-tramites')) {
				loadRankingTramites(_self, periodo, fechaInicio, fechaFin);
			} else if ($(this).hasClass('panel-porcentajes-estado')) {
				loadContadorPorEstado(_self, periodo, fechaInicio, fechaFin);
			} else if ($(this).hasClass('panel-cifras-atendidas-periodos')) {
				loadContadorAtendidasPeriodos(_self, periodo, fechaInicio, fechaFin);
			} else if ($(this).hasClass('panel-cifras-tramites-origen')) {
				loadContadorTipoTramiteOrigen(_self, periodo, fechaInicio, fechaFin);
			}
			
			// Se pone la legenda del periodo
			var descPeriodo = null;
			if (periodo == 1) {
				descPeriodo = 'HOY';
			} else if (periodo == 2) {
				descPeriodo = 'SEMANAL';
			} else if (periodo == 3) {
				descPeriodo = 'MENSUAL';
			} else if (periodo == 4){
				descPeriodo = 'ANUAL';
			} else if (periodo == 5){
				descPeriodo = fechaInicio + ' a ' + fechaFin;
			} 
			$('.panel-body span.label-periodo', _self).text(descPeriodo);
			
			// Para ocultar el porcentaje de atendidas
			$('.stat-percent').hide();
			
		}).trigger('load', 1);
		
		$('ul.dropdown-periodo li a').on('click', function(event){
			event.preventDefault();
			var periodo = $(this).attr('periodo');
			$(this).parents('.panel-dashboard').trigger('load', periodo);
		});
		
		$('a.refresh-contadores-tramite').on('click', function(event){
			event.preventDefault();
			var _parent = $(this).parents('.panel-dashboard');
			var periodo = $(_parent).attr('periodo');
			$(_parent).trigger('load', periodo);
		});
		
		$('ul.dropdown-periodo-global li a').on('click', function(event){
			event.preventDefault();
			var periodo = $(this).attr('periodo');
			
			// Se pone la legenda del periodo
			var descPeriodo = null;
			if (periodo == 1) {
				descPeriodo = 'HOY';
				$('div#rangoFechasContainer').hide();
			} else if (periodo == 2) {
				descPeriodo = 'SEMANAL';
				$('div#rangoFechasContainer').hide();
			} else if (periodo == 3) {
				descPeriodo = 'MENSUAL';
				$('div#rangoFechasContainer').hide();
			} else if (periodo == 4){
				descPeriodo = 'ANUAL';
				$('div#rangoFechasContainer').hide();
			} else if (periodo == 5){
				descPeriodo = '-';
				$('div#rangoFechasContainer').show();
				$('span#periodo-global').text(descPeriodo);
				
				return true;
			}
			$('span#periodo-global').text(descPeriodo);
			
			
			$('div.panel-dashboard:not(.panel-gran-total)').each(function(){
				$(this).trigger('load', periodo);
			});
		});
		
		$('a.refresh-global').on('click', function(event){
			event.preventDefault();
						
			$('div.panel-dashboard').each(function(){
				var periodo = $(this).attr('periodo');
				$(this).trigger('load', periodo);
			});			
		});
		
		$('a#btnTablaRanking').on('click', function(event){
			event.preventDefault();
			$('table#tblCifrasRanking').show();
			$('div.grafica-ranking').hide();
		});
		
		$('a#btnGraficaRanking').on('click', function(event){
			event.preventDefault();
			$('table#tblCifrasRanking').hide();
			$('div.grafica-ranking').show();
		});
		
		$('button#btnRangoFechas').on('click', function(event){
			event.preventDefault();
			
			var periodo = $(this).attr('periodo');
			
			$('div.panel-dashboard:not(.panel-gran-total)').each(function(){
				$(this).trigger('load', periodo);
			});			
		});
	}
};

function loadGranTotal(_self) {
	
	var colors = ['progress-bar-success', 'progress-bar-warning', 'progress-bar-info'];
	var colorIdx = 0;
	
	var lblColors = ['label-success', 'label-info', 'label-primary', 'label-default'];
	var lblColorIdx = 0;
	
	sendRequest(_self, context_path + '/dashboard/getGranTotal', null, function(response){
		
		$('h1#granTotal', _self).text(formatNumber(response.total));
		
		$('div#granTotalOrigenes div.progress', _self).empty();
		$('ul#top5TramitesGlobal', _self).empty();
		
		$.each(response.data, function(idx, elemento){
			$('div#granTotalOrigenes div.progress', _self).append(
				$('<div/>', {
					'class' : 'progress-bar ' + colors[colorIdx],
					'data-original-title' : elemento.descOrigen + ' - ' + elemento.porcentaje + '%',
					'data-toggle' : 'tooltip',
					'data-placement' : 'top',
					'pTmp' : elemento.porcentajeCerrado
				}).css('width', elemento.porcentajeCerrado + '%')
			);
			
			colorIdx ++;
			
			if(colorIdx == colors.length) {
				colorIdx = 0;
			}
		});
		
		$('div#granTotalOrigenes div.progress div.progress-bar', _self).each(function(){
			if($(this).css('width') == '0px') {
				$(this).prev().css('width', ($(this).prev().attr('pTmp') - 2) + '%');
				$(this).css('width', '2%');
			}
		});
		
		$('[data-toggle="tooltip"]').tooltip();
		
		$.each(response.dataTop5, function(idx, elemento){
			$('ul#top5TramitesGlobal', _self).append(
				$('<li/>',{
					'class' : 'list-group-item' + (idx == 0 ? ' first-item' : '')
				}).append(
					$('<span/>',{
						'class' : 'pull-right',
						'text' : formatNumber(elemento.total) 
					}),
					$('<span/>',{
						'class' : 'label ' + lblColors[lblColorIdx],
						'text' : (idx + 1) 
					}),
					elemento.descTipoTramite
				)				
			);
						
			lblColorIdx ++;
			
			if(lblColorIdx == lblColors.length) {
				lblColorIdx = 0;
			}
		});
		
		// Se pone la fecha de actualización
		$('label.updatedOn', _self).text(response.hora);
	});
}

function loadPanelContadores(_self, periodo, fechaInicio, fechaFin) {
	var idsTramite = $(_self).attr('idTramite').split('|');
	var idGrafica = $(_self).attr('id');
	
	var data = new Object();
	data.idGrafica = idGrafica;
	data.periodo = periodo;
	data.tramites = idsTramite;
	
	if (periodo == 5) {
		data.fechaInicio = fechaInicio;
		data.fechaFin = fechaFin;
	}
	
	sendRequest(_self, context_path + '/dashboard/getTotalPorcentaje', data, function(response){
		
		if (!$.isEmptyObject(response)) {
			// Se pone el total de trámites
			$('.panel-body h1.cifra', _self).text(formatNumber(response[idGrafica].atendidas));
			
			// Se pone el porcentaje de atentidas
			var porcentaje = parseFloat(response[idGrafica].porcentajeAtendidas);
			var porcentajeCss = null;
			var iconoPorcentaje = null;
			$('.panel-body div.stat-percent > label', _self).text(response[idGrafica].porcentajeAtendidas + '%');
			if (porcentaje >= 80) {
				porcentajeCss = 'text-success';
				iconoPorcentaje = 'fa-check-square-o';
			} else if (porcentaje < 80 && porcentaje >= 60) {
				porcentajeCss = 'text-warning';
				iconoPorcentaje = 'fa-warning';
			} else {
				porcentajeCss = 'text-danger';
				iconoPorcentaje = 'fa-warning';
			}
			$('.panel-body div.stat-percent', _self).removeClass(function (index, css) {
				return (css.match (/(^|\s)text-\S+/g) || []).join(' ');
			}).addClass(porcentajeCss);
			$('.panel-body div.stat-percent > i.fa', _self).removeClass(function (index, css) {
				return (css.match (/(^|\s)fa-\S+/g) || []).join(' ');
			}).addClass(iconoPorcentaje);
			
			$('div#detalle-' + idGrafica + '-container', _self).empty();
			
			/* 
			 * Se checa si la consulta fue por más de un trámite, si es así
			 * se genera sección para el detalle
			 */
			if (idsTramite.length > 1 && !$(_self).hasClass('no-detail')) {
				if ($('div#detalle-' + idGrafica + '-container', _self).length == 0) {
					$('.panel-body .row:last-child', _self).after($('<div/>', {
						'class' : 'row',
						'id' : 'detalle-' + idGrafica + '-container'
					}).css('margin-top', '5px'));
				}
				
				$('div#detalle-' + idGrafica + '-container', _self).append($('<div/>', {
					'class' : 'col-sm-12'
				}).append($('<div/>', {
					'class' : 'row'
				}).append($('<div/>', {
					'class' : 'col-sm-12'
				}).append($('<a/>', {
					'class' : 'pull-right',
					'data-toggle' : 'collapse',
					'href' : '#detalle-' + idGrafica 
				}).text('Ver detalle'))), $('<div/>', {
					'class' : 'detalle m-b-none m-t-sm collapse',
					'id' : 'detalle-' + idGrafica
				}).append($('<table/>', {
					'class' : 'table m-b-none',
					'id' : 'tbl-' + idGrafica
				}).append($('<thead/>').append($('<tr/>').append($('<td/>'), $('<td/>'), $('<td/>'))), $('<tbody/>')))));
				
				$.each(idsTramite, function(idx){
					if (typeof response[this] !== 'undefined') {
						$('table#tbl-' + idGrafica + ' tbody' , _self).append($('<tr/>').append(
							$('<td/>'),
							$('<td/>').append(
								$('<small/>', {
									'class' : 'stats-label'
								}).text(response[this].descTramite),
								$('<h4/>').text(formatNumber(response[this].atendidas))
							),
							$('<td/>').append(
								$('<small/>', {
									'class' : 'stats-label'
								}),
								$('<h4/>').text(response[this].porcentajeVsGranTotal + '%')
							)
						));
					}
				});
				
				$('table#tbl-' + idGrafica).dataTable({
					"searching": false,
					"paging": false,
					"info": false,
					"order": [[ 2, "desc" ]],
					"fnDrawCallback": function ( oSettings ) {
					    $(oSettings.nTHead).hide();
					}
				});
			}
			
			// Se pone la fecha de actualización
			$('.panel-footer label.updatedOn', _self).text(response[idGrafica].hora);
		}
	});
}

function loadContadorPorOrigen(_self, periodo, fechaInicio, fechaFin) {
	
	var data = new Object();
	data.periodo = periodo;
	
	if (periodo == 5) {
		data.fechaInicio = fechaInicio;
		data.fechaFin = fechaFin;
	}
	
	AmCharts.shortMonthNames = [ 'Ene', 'Feb', 'Mar', 'Abr', 'May',
		'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dec' ];
	
	if (typeof origenChart !== 'undefined') {
		origenChart.clear();
	}
	
	sendRequest(_self, context_path + '/dashboard/getCifrasOrigen', data, function(response) {
		var origenDataProvider = null;
		var agrupado = response.agrupado;
		
		if (agrupado != 'dia') {
			$('div#amChartPorOrigenWrapper').show();
			$('div#amChartPorOrigenHoyWrapper').hide();			
			
			var graphs = new Array(); 
			$.each(response.origenes, function (idx, origen){
				var graph = new Object();
				graph.bullet = "round";
				graph.fillAlphas = 0.7;
				graph.id = "origenGraph" + idx;
				graph.lineAlpha = 0;
				graph.title = origen;
				graph.type = "smoothedLine";
				graph.valueField = origen;
				
				graphs.push(graph);
			});
			
			var dataProvider = new Array();
			$.each(response.labels, function (idx, label){
				var data = new Object();
				data.date = label;
				
				$.each(response.origenes, function (idx, origen){
					data[origen] = response[label][origen];
				});
				
				dataProvider.push(data);
			});
			
			var dataDateFormat = null;
			var categoryAxis = null;
			var equalSpacing = false;
			var categoryBalloonDateFormat = null;
			
			if (response.labels.length > 1) {
				equalSpacing = true;
			}

			if (agrupado == 'anios') {
				categoryAxis = {
					"minPeriod" : "YYYY",
					"equalSpacing" : equalSpacing,
					"markPeriodChange" : false,
					"parseDates" : true,
					"startOnAxis" : true,
					"twoLineMode" : true
				};
				dataDateFormat = "YYYY";
				categoryBalloonDateFormat = "YYYY";
			} else if (agrupado == 'meses') {
				categoryAxis = {
					"minPeriod" : "MM",
					"equalSpacing" : equalSpacing,
					"markPeriodChange" : false,
					"parseDates" : true,
					"startOnAxis" : true,
					"twoLineMode" : true
				};
				dataDateFormat = "YYYY-MM";
				categoryBalloonDateFormat = "MMM YYYY";
			} else {
				categoryAxis = {
					"equalSpacing" : equalSpacing,
					"firstDayOfWeek" : 1,
					"markPeriodChange" : false,
					"parseDates" : true,
					"startOnAxis" : true,
					"twoLineMode" : true
				};
				dataDateFormat = "YYYY-MM-DD";
				categoryBalloonDateFormat = "DD/MM/YYYY";
			}
			
			origenChart = AmCharts.makeChart("amChartPorOrigen", {
				"type" : "serial",
				"pathToImages" : pathToImages,
				"categoryField" : "date",
				"dataDateFormat" : dataDateFormat,
				"theme" : "light",
				"sequencedAnimation": false,
				"startDuration": 1,
				"responsive": {
					"enabled": true
				},
				"startEffect": "easeOutSine",
				"categoryAxis": categoryAxis,
				"valueAxes" : [{
					"id": "ValueAxis-1",
					"usePrefixes": true
				}],
				"chartCursor": {
					"pan": true,
					"categoryBalloonDateFormat": categoryBalloonDateFormat
				},
				"legend" : {
					"align" : "center",
					"backgroundAlpha" : 0.8,
					"backgroundColor" : "#EFEFEF",
					"borderAlpha" : 0.4,
					"borderColor" : "#888888",
					"horizontalGap" : 8,
					"marginLeft" : 0,
					"marginRight" : 0,
					"markerType" : "circle",
					"maxColumns" : 1,
					"periodValueText": "Total: [[value.sum]]",
					"valueWidth": 100
				},
				"chartScrollbar" : {},
				"trendLines" : [],
				"graphs" : graphs,
				"guides" : [],
				"allLabels" : [],
				"balloon" : {},
				"dataProvider" : dataProvider
			});
		} else {
			$('div#amChartPorOrigenWrapper').hide();
			$('div#amChartPorOrigenHoyWrapper').show();
			
			origenDataProvider = new Array();			
			
			$.each(response.origenes, function(idx, origen){
				var data = new Object();
				data.origen = origen;
				data.total = response[origen][origen];
				
				origenDataProvider.push(data);
			});
			
			if (typeof origenHoyChart === 'undefined') {
				origenHoyChart = AmCharts.makeChart("amChartPorOrigenHoy", {
					"type" : "pie",
					"pathToImages" : pathToImages,
					"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
					"innerRadius" : "55%",
					"titleField" : "origen",
					"valueField" : "total",
					"theme" : "light",
					"allLabels" : [],
					"labelRadius" : 10,
					"labelText": "",
					"responsive": {
						"enabled": true
					},
					"pullOutRadius" : "8%",
					"startEffect" : "easeOutSine",
					"dataProvider" : origenDataProvider
				});
				
				origenHoyChart.addListener('rendered', function(event) {
					buildLegend(origenHoyChart , 'origenHoyChart', 'amChartPorOrigenHoyLegend');
				});
				
				origenHoyChart.validateNow();
			} else {
				origenHoyChart.dataProvider = [];
				origenHoyChart.dataProvider = origenDataProvider;
			    
				origenHoyChart.validateData();
				origenHoyChart.validateNow();
			}
		}
		
		// Se pone la fecha de actualización
		$('.panel-footer label.updatedOn', _self).text(response.hora);
	});
}

function loadRankingTramites(_self, periodo, fechaInicio, fechaFin) {
	var data = new Object();
	data.periodo = periodo;
	
	if (periodo == 5) {
		data.fechaInicio = fechaInicio;
		data.fechaFin = fechaFin;
	}
	
	sendRequest(_self, context_path + '/dashboard/getRankingTramites', data, function(response) {
		$('table.tbl-cifras-ranking tbody', _self).empty();
		
		var data = response.data;
		
		$.each(response.labels, function(idx, label) {
			$('table.tbl-cifras-ranking tbody', _self).append(
				$('<tr/>').append(
					$('<td/>'),
					$('<td/>').append(
						$('<small/>', {
							'class' : 'stats-label'
						}).text(label),
						$('<h4/>').text(formatNumber(data[label].total))
					),
					$('<td/>').append(
						$('<small/>', {
							'class' : 'stats-label'
						}),
						$('<h4/>').text(data[label].porcentaje + '%')
					),
					$('<td/>').append(
						$('<span/>', {
							'class' : 'pie'
						}).text(data[label].total + '/' + response.total)
					)
				)
			);
		});
		
		var rankingDataProvider = new Array();			
		
		$.each(response.labels, function(idx, estado){
			var data = new Object();
			data.estado = estado;
			data.total = response.data[estado].total;
			
			rankingDataProvider.push(data);
		});
		
		if (typeof rankingChart === 'undefined') {
			rankingChart = AmCharts.makeChart("amChartRanking", {
				"type" : "pie",
				"pathToImages" : pathToImages,
				"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
				"innerRadius" : "55%",
				"titleField" : "estado",
				"valueField" : "total",
				"theme" : "light",
				"allLabels" : [],
				"labelRadius" : 10,
				"labelText": "",
				"startEffect" : "easeOutSine",
				"pullOutRadius" : "8%",
				"responsive": {
					"enabled": true
				},
				"dataProvider" : rankingDataProvider
			});
			
			rankingChart.addListener('rendered', function(event) {
				buildLegend(rankingChart , 'rankingChart', 'amChartRankingLegend');
			});
			
			rankingChart.validateNow();
		} else {
			rankingChart.dataProvider = [];
			rankingChart.dataProvider = rankingDataProvider;
		    
		    rankingChart.validateData();
		    rankingChart.validateNow();
		}
				
		// Se pone la fecha de actualización
		$('.panel-footer label.updatedOn', _self).text(response.hora);
		
		$("span.pie").peity("pie", {
			fill: ['#1ab394', '#d7d7d7', '#ffffff']
	    });
	});
}

function loadContadorPorEstado(_self, periodo, fechaInicio, fechaFin) {
	var data = new Object();
	data.periodo = periodo;
	
	if (periodo == 5) {
		data.fechaInicio = fechaInicio;
		data.fechaFin = fechaFin;
	}
	
	sendRequest(_self, context_path + '/dashboard/getCifrasPorEstado', data, function(response) {
		$('table.tbl-cifras-por-estado tbody', _self).empty();
		
		if (typeof estadosChart !== 'undefined') {
			estadosChart.clear();
		}
		
		var data = response.data;
		
		$.each(response.labels, function(idx, label) {
			$('table.tbl-cifras-por-estado tbody', _self).append(
				$('<tr/>').append(
					$('<td/>'),
					$('<td/>').append(
						$('<small/>', {
							'class' : 'stats-label'
						}).text(label),
						$('<h4/>').text(formatNumber(data[label].total))
					),
					$('<td/>').append(
						$('<small/>', {
							'class' : 'stats-label'
						}),
						$('<h4/>').text(data[label].porcentaje + '%')
					),
					$('<td/>').append(
						$('<span/>', {
							'class' : 'pie'
						}).text(data[label].total + '/' + response.total)
					)
				)
			);
			
			var edoDataProvider = new Array();			
			
			$.each(response.labels, function(idx, estado){
				var data = new Object();
				data.estado = estado;
				data.total = response.data[estado].total;
				
				edoDataProvider.push(data);
			});
				
			estadosChart = AmCharts.makeChart("amChartPorEstado", {
				"type" : "pie",
				"pathToImages" : pathToImages,
				"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
				"innerRadius" : "55%",
				"titleField" : "estado",
				"valueField" : "total",
				"theme" : "light",
				"allLabels" : [],
				"labelRadius" : 10,
				"labelText": "",
				"pullOutRadius" : "8%",
				"startEffect" : "easeOutSine",
				"balloon" : {},
				"legend" : {
					"align" : "center",
					"backgroundAlpha" : 0.8,
					"backgroundColor" : "#EFEFEF",
					"borderAlpha" : 0.4,
					"borderColor" : "#888888",
					"horizontalGap" : 8,
					"marginLeft" : 0,
					"marginRight" : 0,
					"markerType" : "circle",
					"maxColumns" : 1,
					"valueText": "[[value]] - [[percents]] %",
					"valueWidth": 150						
				},
				"titles" : [],
				"dataProvider" : edoDataProvider
			});
		});
		
		// Se pone la fecha de actualización
		$('.panel-footer label.updatedOn', _self).text(response.hora);
		
		$("span.pie").peity("pie", {
			fill: ['#1ab394', '#d7d7d7', '#ffffff']
	    });
	});
}

function loadContadorAtendidasPeriodos(_self, periodo, fechaInicio, fechaFin) {
	var data = new Object();
	data.periodo = periodo;
	
	if (periodo == 5) {
		data.fechaInicio = fechaInicio;
		data.fechaFin = fechaFin;
	}
	
	sendRequest(_self, context_path + '/dashboard/getCifrasAtendidasPeriodos', data, function(response){
		$('li#periodoActual > h2', _self).text(formatNumber(response.ACTUAL));
		var descPeriodo = null;
		if (periodo == 1) {
			descPeriodo = 'del día de hoy';
		} else if (periodo == 2) {
			descPeriodo = 'en la semana';
		} else if (periodo == 3) {
			descPeriodo = 'en el mes';
		} else if (periodo == 4) {
			descPeriodo = 'en el año';
		} else if (periodo == 5) {
			descPeriodo = 'del día ' + fechaInicio + ' al ' + fechaFin;
		}
		$('li#periodoActual > small > span', _self).text(descPeriodo);
		$('li#periodoActual > div.progress > div.progress-bar', _self).css('width' , response.porcentaje + '%');
				
		$('li#periodoAnterior > h2', _self).text(formatNumber(response.ANTERIOR));
		var descPeriodo = null;
		if (periodo == 1) {
			descPeriodo = 'del día de ayer';
		} else if (periodo == 2) {
			descPeriodo = 'en la semana anterior';
		} else if (periodo == 3) {
			descPeriodo = 'en el mes anterior';
		} else if (periodo == 4) {
			descPeriodo = 'en el año anterior';
		} else if (periodo == 5) {
			descPeriodo = 'del día ' + response.fechaInicioAnterior + ' al ' + response.fechaFinAnterior;
		}
		$('li#periodoAnterior > small > span', _self).text(descPeriodo);
		$('li#periodoAnterior > div.progress > div.progress-bar', _self).css('width' , response.ANTERIOR == 0 ? '0%' : '100%');
		
		
		// Se pone la fecha de actualización
		$('.panel-footer label.updatedOn', _self).text(response.hora);
	});
} 

function loadContadorTipoTramiteOrigen(_self, periodo, fechaInicio, fechaFin) {
	var data = new Object();
	data.periodo = periodo;
	
	if (periodo == 5) {
		data.fechaInicio = fechaInicio;
		data.fechaFin = fechaFin;
	}
	
	sendRequest(_self, context_path + '/dashboard/getCifrasTipoTramiteOrigen', data, function(response){
		
		if (typeof tramitesDT  !== 'undefined') {
			tramitesDT.destroy();
		}
		
		$('table#tblTramitesOrigen tbody', _self).empty();
		
		$.each(response.data, function(idx, contador){
			$('table#tblTramitesOrigen tbody', _self).append($('<tr/>')
					.append($('<td/>').text(idx + 1))
				.append($('<td/>').text(contador.descTipoTramite))
				.append($('<td/>').text(contador.descOrigen))
				.append($('<td/>').text(formatNumber(contador.total)))
				.append($('<td/>').text(contador.porcentaje + '%'))
			);
		});
		

		tramitesDT = $('table#tblTramitesOrigen', _self).DataTable({
			responsive: true,
			'language' : {
				"sProcessing" : "Procesando...",
				"sLengthMenu" : "Mostrar _MENU_ registros",
				"sZeroRecords" : "No se encontraron resultados",
				"sEmptyTable" : "Ningún dato disponible en esta tabla",
				"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
				"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
				"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
				"sInfoPostFix" : "",
				"sSearch" : "Buscar:",
				"sUrl" : "",
				"sInfoThousands" : ",",
				"sLoadingRecords" : "Cargando...",
				"oPaginate" : {
					"sFirst" : "Primero",
					"sLast" : "Último",
					"sNext" : "Siguiente",
					"sPrevious" : "Anterior"
				},
				"oAria" : {
					"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
					"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
				}
			}
		});
	
		
		// Se pone la fecha de actualización
		$('.panel-footer label.updatedOn', _self).text(response.hora);
	});
}

function formatNumber(valor) {
	return valor.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function sendRequest(contenedor, url, jsonData, success) {
	
	$.ajax({
		url: url,
        type: "POST",
        data: jsonData ? JSON.stringify(jsonData) : null,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: success,
        beforeSend : function() {
        	$('img.loading', contenedor).show();
        },
        complete : function() {
        	$('img.loading', contenedor).hide();
        }
	});
	
}

function buildLegend(chart, chartVarName, legendContainer) {
	
	$('div#' + legendContainer).empty();
	
	chart.customLegend = document.getElementById(legendContainer);
		
	$('div#' + legendContainer).append($('<div/>', {
		'class' : 'well m-b-none p-xs'
	}).append($('<table/>', {
		'class' : 'table m-b-none'
	})));
	
	for ( var i in chart.chartData) {
		var row = chart.chartData[i];
		var color = chart.colors[i];
		var percent = Math.round(row.percents * 100) / 100;
		var value = row.value;
		
		$('div#' + legendContainer + ' div.well table').append(
			$('<tr/>',{
				'onclick' : 'toggleHide(' + chartVarName + ', ' + i + ', "' + legendContainer + '")',
				'style' : 'cursor: pointer'
			}).append(
					$('<td/>').append($('<i/>', {
						'class' : 'fa fa-circle'
					}).css({
						'font-size' : '12px'
					})).css({
						'color' : color,
						'width' : '10%'
					}),
					$('<td/>').css({
						'width' : '50%'
					}).text(row.title),
					$('<td/>').css({
						'width' : '40%'
					}).append($('<strong/>').text(formatNumber(value)),
						$('<span/>').text(' - (' + percent + '%)')
			)).addClass('p-b-sm')
		);	
	}
}

function toggleHide(chart, item, legendContainer) {
	if (chart.chartData[ item ].hidden) {
		chart.showSlice(item);
		$('div#' + legendContainer + ' div.well table tr:eq(' + item + ') td:first-child i').removeClass('fa-circle-o').addClass('fa-circle');
		$('div#' + legendContainer + ' div.well table tr:eq(' + item + ')').removeClass('disabled');
	} else {
		chart.hideSlice(item);
		$('div#' + legendContainer + ' div.well table tr:eq(' + item + ') td:first-child i').removeClass('fa-circle').addClass('fa-circle-o');
		$('div#' + legendContainer + ' div.well table tr:eq(' + item + ')').addClass('disabled');
	}
}
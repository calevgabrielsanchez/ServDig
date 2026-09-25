(function($) {
	$.widget("delta.menu", {
		options : {
			'opcionesSrc' : null
		},
		_initMenu : function() {
			jsonMenu = {
				tramite : null,
				filtro : null
			};

			$('li.opc').click(function() {
				var _tramite = $(this).attr('tram');
				var _filtro = $(this).attr('filtro');

				$('input#tramite', 'form#formTramite').val(_tramite);
				$('input#filtro', 'form#formTramite').val(_filtro);

				$('form#formTramite').submit();
			});
			
			$('ul div[class*=col]:first-child li:first-child').focus();
			
			$('li.opc').focus(function() {
				jsonMenu.tramite = $(this).attr('tram');
				jsonMenu.filtro = $(this).attr('filtro');
			});
			
			$(document).keypress(function(e) {
				if (e.which == 13) {
					if (jsonMenu.tramite != null
							&& jsonMenu.filtro != null) {
						$('input#tramite', 'form#formTramite').val(
								jsonMenu.tramite);
						$('input#filtro', 'form#formTramite').val(
								jsonMenu.filtro);
						$('form#formTramite').submit();
					} else {
						jsonMenu.tramite = $('ul').find(
								'div > li:first-child').attr('tram');
						jsonMenu.filtro = $('ul').find(
								'div > li:first-child').attr('filtro');
						if (jsonMenu.tramite != null
								&& jsonMenu.filtro != null) {
							$('input#tramite', 'form#formTramite').val(
									jsonMenu.tramite);
							$('input#filtro', 'form#formTramite').val(
									jsonMenu.filtro);
							$('form#formTramite').submit();
						}
					}
				}
			});
			
			$(document).keyup(function(e) {
				if (e.keyCode == 9) {
					//TAB
					var _focused = $(':focus');
					
					if ($.isEmptyObject(_focused) || !_focused.is('li.tile')) {
						$('ul div[class*=col]:first-child li:first-child').focus();
					}
				}
			});
		},
		_create : function() {
			var _this = this;

			this._options = {
				numColumnas : 3
			};
			
			this.element.append($('<div/>', {
				'class' : 'demo-wrapper'
			}).append($('<div/>', {
				'class' : 'dashboard clearfix'
			}).append($('<ul/>', {
				'class' : 'tiles'
			}))));

			for ( var i = 1; i <= this._options.numColumnas; i++) {
				$('ul.tiles', this.element).append($('<div/>', {
					class : 'col' + i + ' clearfix'
				}));
			}
			
			$('head').append($('<style/>', {
				'id' : 'iconos-menu'
			}));
			
			var _idx = 1;
			$('ul#' + this.options.opcionesSrc + ' li').each(function(idx) {
				$('div.col' + _idx, _this.element).append($('<li/>', {
					'tabindex' : idx + 1,
					'class' : 'tile tile-big tile-5 opc',
					'data-page-type' : 'r-page',
					'data-page-name' : 'random-r-page',
					'tram' : $(this).attr('tram'),
					'filtro' : $(this).attr('filtro'),
					'id' : $(this).attr('id')
				}).append($('<div/>', {
					'class' : 'container-fluid'
				}).append($('<div/>', {
					'class' : 'row'
				}).append($('<div/>', {
					'class' : 'col-xs-12'
				}).append($('<div/>', {
					'class' : 'titulo'
				}).text($(this).attr('titulo'))), $('<div/>', {
					'class' : 'col-xs-12 contenedor-texto'
				}).text($(this).attr('desc'))))));
				
				if (_idx < _this._options.numColumnas) {
					_idx++;
				} else {
					_idx = 1;
				}
				
				$('style#iconos-menu').append('#'+ $(this).attr('id') + ':before {' 
					+ 'content : "\\' + $(this).attr('icono') + '";'
					+ '}'
				);
			});
			
			$('ul#' + this.options.opcionesSrc).remove();
			
			this._initMenu();
		},
		_setOption : function(key, value) {
			this._super(key, value);
		},
		_setOptions : function(options) {
			this._super(options);
		},
		_destroy : function() {
			console.log("DESTRUYENDO");
		}
	});
})(jQuery);
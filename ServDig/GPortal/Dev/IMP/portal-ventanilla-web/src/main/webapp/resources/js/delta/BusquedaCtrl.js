(function($) {
	$.widget("delta.busqueda", {
		options : {
			ttc : null,
			tf : null
		},
		_create : function() {
			if (this.options.ttc === null) {
				$.error('Datos insuficientes para mostrar los filtros de búsqueda');
			} else {
				var _container = $(this.element);
				
				$.ajax({
					url: '/portal-ventanilla-web/busqueda/filtro',
					type: 'post',
					dataType: 'html',
					data: this.options,
					beforeSend : function() {
						_container.html('<div class="loading well" style="text-align: center;"><img class="loading"></div>');
					},
					success: function(contenido) {
						_container.html(contenido);
						inicializarValidacionesCaracteresEspeciales();
						$('div#busquedaContainer input:first-child').focus();
					},
					error: function(error) {
						_container.html(error.responseText);
					}
				});
			}
		},
		_setOption : function(key, value) {
			this._super(key, value);
		},
		_setOptions : function(options) {
			this._super(options);
		},
		_destroy : function() {
			console.log("DESTRUYENDO");
		},
		buscar : function() {
			
			fnHideErrores('form#buscarForm');
			$('div#errorNegocioLabel').addClass('hiddenElement').empty();
			
			var _tf = this.options.tf;
			var _this = this;
			var _url = null;
			
			if (_tf === '1' || _tf === '2' || _tf === '3') {
				_url = '/portal-ventanilla-web/busqueda/validar';
			} else if (_tf === '4' || _tf === '5') {
				_url = '/portal-ventanilla-web/busqueda/sujeto';
			}
			
			$.ajax({
				url: _url,
				type: 'post',
				dataType: 'json',
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify($('form#buscarForm').toObject()),
				beforeSend: function() {
					$.blockUI();
				},
				success: function(data) {
					if (_tf === '1' || _tf === '2' || _tf === '3') {
						dialogoBuscar.persona({
							valorBuscado : data.vB,
							tipoBusqueda : data.tB, 
							_tipoPersona : data.tP
						});
						
						dialogoBuscar.persona('mostrar');
					} else if (_tf === '4' || _tf === '5') {
						_this._trigger( "complete", null, {value : data} );
					}
					
					$.unblockUI();
				},
				error: function(error) {
					if($('input#rfcInput').length>0)
						$('input#rfcInput').focus();
					else if($('input#nssInput').length>0)
						$('input#nssInput').focus();
					else if($('input#curpInput').length>0)
						$('input#curpInput').focus();
					else if($('input#nrpInput').length>0)
						$('input#nrpInput').focus();
					$.unblockUI();
					try {
						fnProcesarErrores(error, 'form#buscarForm');
					} catch (error) {
						$('div#errorNegocioLabel','form#buscarForm')
		                    .text("Ocurrió un error inesperado al consultar")
		                    .removeClass('hiddenElement');
					}
				}
			});
		}
	});
})(jQuery);
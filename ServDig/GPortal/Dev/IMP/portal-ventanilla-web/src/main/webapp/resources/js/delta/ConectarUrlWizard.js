(function($) {
	var dialogo = null;
	var cargar = function(_widget){
		init(_widget);
		dialogo.dialog('open');
	};
	
	var init = function(_widget){
		var d = _widget.element;
		dialogo = d.dialog({
			title : _widget.options.titulo,
			autoOpen : false,
			width : 900,
			modal : true,
			resizable : false,
			autoResize : true,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			position : {
				my : "top+20",
				at : "top+20",
				of : window
			}
		});

		dialogo.dialog({
			close : function(event, ui) {
				$(this).dialog('destroy').empty();
			}
		});
	};
	var iframe = function (_div, url){
    	 $.ajax({
    		 beforeSend: function() {
    			 _div.html('<div class="loading" style="text-align: center;"><img class="loading"></div>');
             },
             success: function() {
            	 _div.html('<iframe id="ubicarPersonaFrame" src="'
         				+ url
         				+ '" width="100%" height="100%" frameborder="0" onload="set_size(\'ubicarPersonaFrame\', 900)"/>');
             },
         });
	};
	
	$.widget("delta.wizard", {
		options : {
			url: null,
			div: null,
			titulo: null,
		},
		mostrar : function() {
					var div = this.element;
					var _url = this.options.url;
						console.log(" url: "+_url);
						iframe(div,_url);
						cargar(this);
			},
		cerrar : function(){
			dialogo.dialog('close');
		}
	});
})(jQuery);
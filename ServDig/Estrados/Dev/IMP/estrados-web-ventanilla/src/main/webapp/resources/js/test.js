$(document).ready(function(){
	
	(function ($) {
	    $.extend({
	        postJSON: function (url, jsonData, success, options) {
	            var config = {
	                url: url,
	                type: "POST",
	                data: jsonData ? JSON.stringify(jsonData) : null,
	                dataType: "json",
	                contentType: "application/json; charset=utf-8",
	                success: success
	            };
	            $.ajax($.extend(options, config));
	        }
	    });
	})(jQuery);
	
	
	
	
	
	
});
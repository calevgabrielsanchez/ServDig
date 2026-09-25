/*
 * Name: jquery.validator()
 * Version: 1.0
 * Author: Jose M. Carbonell jose.axtro@gmail.com
 * Link: http://theproc.es/2010/7/30/26167/crea-un-sencillo-plugin-para-validar-tus-formularios-con-jquery
 * Demo: http://theproc.es/page/demo/demo-jquery-validator?layout=false
 *
 * Usage:
 *   $(document).ready(function(){
 *     $('form').validator();
 *   });
 * 
 */
(function($){
  $.fn.validator = function(opts){
    $(this).find('.notFilled').live('keyup', function(){
      if($(this).val()!=""){
          $(this).removeClass('notFilled');
      }
    });
	$(this).find('.notFilled').live('change', function(){
      if($(this).val()!=""){
          $(this).removeClass('notFilled');
      }
    });
    return $(this).submit(function(evt){
      $(this).find('.required').each(function(){
        if ($(this).attr('value') == ''){
          $(this).addClass('notFilled');
          evt.preventDefault();
        }
      });
      $(this).find('.notFilled').first().focus();
    });
  };
})(jQuery);
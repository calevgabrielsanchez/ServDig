/*
 * Function: fnDisplayStart
 * Purpose:  Set the display start point
 * Returns:  void
 * Inputs:   object:oSettings - DataTables settings object - NOTE - added automatically
 *           int:iStart - New display start point
 *           bool:bRedraw - Redraw the display based on new start point - optional - default true
 */
$.fn.dataTableExt.oApi.fnDisplayStart = function ( oSettings, iStart, bRedraw )
{
	if ( typeof bRedraw == 'undefined' )
	{
		bRedraw = true;
	}
	
	oSettings._iDisplayStart = iStart;
	oSettings.oApi._fnCalculateEnd( oSettings );
	
	if ( bRedraw )
	{
		oSettings.oApi._fnDraw( oSettings );
	}
}
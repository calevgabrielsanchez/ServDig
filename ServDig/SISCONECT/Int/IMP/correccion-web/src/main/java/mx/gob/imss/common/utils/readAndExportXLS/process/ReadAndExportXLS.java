/**
 * RBGSoftware clean service
 * 
 * 2013.07.23
 */

package mx.gob.imss.common.utils.readAndExportXLS.process;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.FormulaCell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.formula.FormulaException;
import jxl.format.CellFormat;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Colour;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import mx.gob.imss.common.utils.readAndExportXLS.vo.DataBlock;
import mx.gob.imss.common.utils.readAndExportXLS.vo.ResourceDataXLS;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;

@SuppressWarnings({ "unused", "deprecation" })
public class ReadAndExportXLS {
	private WritableSheet sheet;
	private WritableCellFormat xlsStyleCombos;
	private WritableCellFormat xlsStyleContent;
	private WritableCellFormat xlsStyleCurrency;
	private String templateXLS;
	private List<ResourceDataXLS> resourceData;
	private List<DataBlock> resourceDataBlock;

	public ReadAndExportXLS(String templateXLS) {
		this.templateXLS = templateXLS;
		resourceData = new ArrayList<ResourceDataXLS>();
		resourceDataBlock = new ArrayList<DataBlock>();
		initStyleCombos();
		initStyleContent();
		initStyleCurrency();
	}

	public List<ResourceDataXLS> getResourceData() {
		return resourceData;
	}

	public List<DataBlock> getResourceDataBlock() {
		return resourceDataBlock;
	}

	public void initStyleCurrency() {
		try {
			NumberFormat nf = new NumberFormat("$-#,##0.00");
			WritableCellFormat wcf = new WritableCellFormat(nf);
			wcf.setLocked(true);
			WritableFont font = new WritableFont(WritableFont.ARIAL, 10);
			font.setColour(Colour.BLACK);
			wcf.setFont(font);

			xlsStyleCurrency = new WritableCellFormat(wcf);
			xlsStyleCurrency.setAlignment(Alignment.CENTRE);
			xlsStyleCurrency
					.setVerticalAlignment(VerticalAlignment.CENTRE);
			xlsStyleCurrency.setBorder(Border.ALL, BorderLineStyle.THIN);
			xlsStyleCurrency.setWrap(true);
		} catch (WriteException localWriteException) {
		}
	}

	public void initStyleCombos() {
		try {
			WritableCellFormat wcf = new WritableCellFormat();
			wcf.setLocked(true);

			WritableFont font = new WritableFont(WritableFont.ARIAL, 8);
			font.setColour(Colour.GREY_25_PERCENT);
			wcf.setFont(font);

			xlsStyleCombos = new WritableCellFormat(wcf);
			xlsStyleCombos.setAlignment(Alignment.CENTRE);
			xlsStyleCombos.setVerticalAlignment(VerticalAlignment.CENTRE);
			xlsStyleCombos.setBorder(Border.NONE, BorderLineStyle.NONE);
		} catch (WriteException localWriteException) {
		}
	}

	public void initStyleContent() {
		try {
			WritableCellFormat wcf = new WritableCellFormat();
			wcf.setLocked(true);
			WritableFont font = new WritableFont(WritableFont.ARIAL, 10);
			font.setColour(Colour.BLACK);
			wcf.setFont(font);

			xlsStyleContent = new WritableCellFormat(wcf);
			xlsStyleContent.setAlignment(Alignment.CENTRE);
			xlsStyleContent.setVerticalAlignment(VerticalAlignment.CENTRE);
			xlsStyleContent.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			xlsStyleContent.setWrap(true);
		} catch (WriteException localWriteException) {
		}
	}

	private CellView hideElement() {
		CellView cv = new CellView();
		cv.setAutosize(true);
		cv.setHidden(true);

		return cv;
	}

	public Boolean execute(HttpServletResponse response,
			HttpServletRequest request, String nombreArchivo) {
		try {
			System.out.println("ReadAndExport LIB [execute]->Inicio");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.out
					.println("ReadAndExport LIB [execute]->Obteniendo archivo XLS");

			WorkbookSettings ws = new WorkbookSettings();

			ws.setSuppressWarnings(true);
			ws.setEncoding("CP1250");

			Workbook source = Workbook.getWorkbook(new File(templateXLS),
					ws);
			System.out.println("ReadAndExport LIB [execute]->Abriendo Libro");
			WritableWorkbook sourceWritable = Workbook.createWorkbook(baos,
					source);
			System.out.println("ReadAndExport LIB [execute]->Libro Abierto");
			Label label = null;
			Iterator<ResourceDataXLS> iterRS = resourceData.iterator();
			ResourceDataXLS rsd = null;
			System.out
					.println("ReadAndExport LIB [execute]->Iniciando ciclo de lectura 1");

			while (iterRS.hasNext()) {
				rsd = (ResourceDataXLS) iterRS.next();

				sheet = sourceWritable.getSheet(rsd.getSheetName());

				label = new Label(rsd.getLocation().getColumn().intValue(), rsd
						.getLocation().getRow().intValue(), rsd.getData(), rsd
						.getIsCatalogo().booleanValue() ? xlsStyleCombos
						: xlsStyleContent);

				sheet.addCell(label);

				if (rsd.getIsCatalogo().booleanValue()) {
					sheet.setColumnView(rsd.getLocation().getColumn()
							.intValue(), hideElement());
				}
			}

			Iterator<DataBlock> iterDataBlock = resourceDataBlock.iterator();
			DataBlock dataBlock = null;
			System.out
					.println("ReadAndExport LIB [execute]->Iniciando ciclo de lectura 2");
			while (iterDataBlock.hasNext()) {
				dataBlock = (DataBlock) iterDataBlock.next();
				iterRS = dataBlock.getElementos().iterator();

				while (iterRS.hasNext()) {
					rsd = (ResourceDataXLS) iterRS.next();
					sheet = sourceWritable.getSheet(rsd.getSheetName());
					label = new Label(
							rsd.getLocation().getColumn().intValue(),
							rsd.getLocation().getRow().intValue(),
							rsd.getData(),
							rsd.getIsCatalogo().booleanValue() ? xlsStyleCombos
									: xlsStyleContent);

					if (DataBlock.ROW == dataBlock.getBockType()) {
						if ((rsd.getLocation().getRow().intValue() > dataBlock
								.getBottomLimitBlock().intValue())
								&& (dataBlock.getInitialLocation().getColumn() == rsd
										.getLocation().getColumn())) {
							sheet.insertRow(rsd.getLocation().getRow()
									.intValue());

							Formula f = new Formula(dataBlock
									.getInitialLocation().getColumn()
									.intValue(), rsd.getLocation().getRow()
									.intValue() - 1, "");

							sheet.addCell(f);
						}

					} else if (rsd.getLocation().getColumn().intValue() > dataBlock
							.getBottomLimitBlock().intValue()) {
						sheet.insertRow(rsd.getLocation().getColumn()
								.intValue());
					}

					Number number3 = new Number(rsd.getLocation().getColumn()
							.intValue(), rsd.getLocation().getRow().intValue(),
							new Double(rsd.getData()).doubleValue(),
							xlsStyleCurrency);

					sheet.addCell(number3);
				}
			}

			System.out.println("ReadAndExport LIB [execute]->Escribiendo XLS");
			sourceWritable.write();
			sourceWritable.close();

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-disposition", "attachment; filename="
					+ nombreArchivo);

			response.setContentType("application/vnd.ms-excel");

			response.setContentLength(baos.size());

			ServletOutputStream out = response.getOutputStream();
			System.out.println("ReadAndExport LIB [execute]->XLS en Pantalla");
			baos.writeTo(out);
			out.flush();

			return Boolean.valueOf(true);
		} catch (IOException e) {
			System.out.println("Error en el logo " + e);
			return Boolean.valueOf(false);
		} catch (WriteException er) {
			System.out.println("Error a la hora de generar el archivo xls "
					+ er);
			return Boolean.valueOf(false);
		} catch (Exception e) {
			System.out
					.println("Error a la hora de generar el archivo xls " + e);
		}
		return Boolean.valueOf(false);
	}

	public static String getStringPosition(Integer colum) throws Exception {
		String position = "";

		if (colum.intValue() <= 25) {
			position = "" + (colum.intValue() + 65);
		} else {
			Integer control = Integer.valueOf(0);

			control = Integer.valueOf(colum.intValue() / 26);

			position = "" + (control.intValue() - 1 + 65);
			position = position
					+ (char) (colum.intValue() - 26 * control.intValue() + 65);
		}

		return position;
	}

	public Boolean executePOI(HttpServletResponse response,
			HttpServletRequest request, String nombreArchivo) {

		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(templateXLS));			
			HSSFWorkbook workbook = new HSSFWorkbook(fs);

			HSSFSheet sheet;
			HSSFRow row = null;
			HSSFCell celda;
			Iterator<ResourceDataXLS> iterRS = resourceData.iterator();
			ResourceDataXLS rsd = null;
			System.out
					.println("ReadAndExport LIB [execute]->Iniciando ciclo de lectura 1");

			while (iterRS.hasNext()) {
				rsd = (ResourceDataXLS) iterRS.next();
				sheet = workbook.getSheet(rsd.getSheetName());
				row = sheet.getRow(rsd.getLocation().getRow().intValue());
				if (row == null) {
					row = sheet
							.createRow(rsd.getLocation().getRow().intValue());
				}

				celda = row
						.createCell(rsd.getLocation().getColumn().intValue());
				celda.setCellStyle(rsd.getIsCatalogo() ? getStyleCombos(workbook)
						: getStyleContent(workbook));
				celda.setCellValue(rsd.getData());

				if (rsd.getIsCatalogo().booleanValue()) {
					celda.getCellStyle().setHidden(true);
				}
			}

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-disposition", "attachment; filename="
					+ nombreArchivo);
			response.setContentType("application/vnd.ms-excel");

			ServletOutputStream out = response.getOutputStream();
			System.out.println("ReadAndExport LIB [execute]->XLS en Pantalla");
			workbook.write(out);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public HSSFCellStyle getStyleContent(HSSFWorkbook workbook) {

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setLocked(true);

		HSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 10);
		font.setColor(HSSFColor.BLACK.index);

		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);

		return cellStyle;
	}

	public HSSFCellStyle getStyleCombos(HSSFWorkbook workbook) {

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setLocked(true);

		HSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 8);
		font.setColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(CellStyle.BORDER_NONE);

		return cellStyle;
	}

	public WritableWorkbook copySheet(WritableWorkbook sourceDocument,
			WritableWorkbook newWorkbook, String sheetName) {
		try {
			WritableSheet sourceSheet = sourceDocument.getSheet(sheetName);
			WritableSheet targetSheet = newWorkbook.createSheet(sheetName, 0);

			for (int row = 0; row < sourceSheet.getRows(); row++)
				for (int col = 0; col < sourceSheet.getColumns(); col++) {
					WritableCell readCell = sourceSheet.getWritableCell(col,
							row);
					WritableCell newCell = readCell.copyTo(col, row);
					CellFormat readFormat = readCell.getCellFormat();

					if (readFormat != null) {
						WritableCellFormat newFormat = new WritableCellFormat(
								readFormat);
						newCell.setCellFormat(newFormat);
					}

					targetSheet.addCell(newCell);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newWorkbook;
	}

	public static void main(String[] args) {
		try {
			getStringPosition(Integer.valueOf(202));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] read(String file) throws IOException {

		File f = new File(file);
		FileInputStream fin = null;
		FileChannel ch = null;
		byte[] bytes = null;
		try {
			fin = new FileInputStream(f);
			ch = fin.getChannel();
			int size = (int) ch.size();
			MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
			bytes = new byte[size];
			buf.get(bytes);

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (ch != null) {
					ch.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	public final static byte[] load(String fileName) {
		try {
			FileInputStream fin = new FileInputStream(fileName);
			return load(fin);
		} catch (Exception e) {

			return new byte[0];
		}
	}

	public final static byte[] load(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			return load(fin);
		} catch (Exception e) {

			return new byte[0];
		}
	}

	public final static byte[] load(FileInputStream fin) {
		byte readBuf[] = new byte[512 * 1024];

		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			int readCnt = fin.read(readBuf);
			while (0 < readCnt) {
				bout.write(readBuf, 0, readCnt);
				readCnt = fin.read(readBuf);
			}

			fin.close();

			return bout.toByteArray();
		} catch (Exception e) {

			return new byte[0];
		}
	}
}

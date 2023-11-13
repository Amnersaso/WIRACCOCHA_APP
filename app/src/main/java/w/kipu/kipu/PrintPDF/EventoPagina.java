package w.kipu.kipu.PrintPDF;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

/**
 *
 * @author david
 */
public class EventoPagina implements IEventHandler {

    private final Document documento;
    private final byte[] bitmapdata;
    private final String txtQuienEntrega,txtUsuario,txtTipoProductor;

    public EventoPagina(Document doc,byte[] imagen,String QuienEntrega,String tipoProductor,String Usuario) {
        documento = doc;
        bitmapdata = imagen;
        txtQuienEntrega=QuienEntrega;
        txtUsuario= Usuario;
        txtTipoProductor=tipoProductor;
    }

    /**
     * Crea el rectangulo donde pondremos el encabezado
     * @param docEvent Evento de documento
     * @return Area donde colocaremos el encabezado
     */
    private Rectangle crearRectanguloEncabezado(PdfDocumentEvent docEvent) {
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();

        float xEncabezado = pdfDoc.getDefaultPageSize().getX() + documento.getLeftMargin();
        float yEncabezado = pdfDoc.getDefaultPageSize().getTop() - documento.getTopMargin();
        float anchoEncabezado = page.getPageSize().getWidth() - 72;
        float altoEncabezado = 50F;

        Rectangle rectanguloEncabezado = new Rectangle(xEncabezado, yEncabezado-4, anchoEncabezado, 20);

        return rectanguloEncabezado;
    }
    private Rectangle crearRectanguloPie(PdfDocumentEvent docEvent) {
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();

        float xPie = pdfDoc.getDefaultPageSize().getX() + documento.getLeftMargin();
        float yPie = pdfDoc.getDefaultPageSize().getBottom() ;
        float anchoPie = page.getPageSize().getWidth() - 72;
        float altoPie = 50F;
        //Rectangle rectanguloPie = new Rectangle(xPie, yPie, anchoPie, altoPie);
        Rectangle rectanguloPie = new Rectangle(0, 0, 800, 50);

        return rectanguloPie;
    }

    private Table crearTablaEncabezado(PdfDocumentEvent docEvent) {
        PdfPage page = docEvent.getPage();
        float  col_hoja1[] = {480,40,480};
        Table tb_col1 = new Table(col_hoja1);
        tb_col1.setWidth(840);

        float[] anchos = {230,40,230};
        Table tablaPie = new Table(anchos);
        //tablaPie.setWidth(300);
        String pageNum = (docEvent.getDocument().getPageNumber(page))+"/"+(docEvent.getDocument().getNumberOfPages());
        Integer pageTotal = docEvent.getDocument().getNumberOfPages();

        tablaPie.addCell(new Cell(1,1).add( new Paragraph().setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        tablaPie.addCell(new Cell(1,1).add( new Paragraph(pageNum).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        tablaPie.addCell(new Cell(1,1).add( new Paragraph().setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));

        tb_col1.addCell(new Cell(1,1).add(tablaPie).setBorder(Border.NO_BORDER));
        tb_col1.addCell(new Cell(1,1).add(new Paragraph("").setMargin(5)).setBorder(Border.NO_BORDER));
        tb_col1.addCell(new Cell(1,1).add(tablaPie).setBorder(Border.NO_BORDER));

        return tb_col1;
    }

    /**
     * Crea la tabla de pie de pagina, con el numero de pagina
     * @param docEvent Evento del documento
     * @return Pie de pagina con el numero de pagina
     */
    private Table crearTablaPie(PdfDocumentEvent docEvent) {
        PdfPage page = docEvent.getPage();
        float  col_hoja1[] = {250,250,250,250};
        Table tb_col1 = new Table(col_hoja1);
        tb_col1.setWidth(900);

        float[] anchos = {250,250,250,250};
        Table tablaPie = new Table(anchos);
        //tablaPie.setWidth(300);

         Text txtruc = new Text("_________________________ \n Registrado por: "+txtUsuario).setFontSize(7);
        Text txtruc1 = new Text("_________________________ \n "+ txtTipoProductor +txtQuienEntrega).setFontSize(7);
        Text txtruc2 = new Text("_________________________ \n Almacén").setFontSize(7);
        Text txtruc3 = new Text("_________________________ \n V.B.").setFontSize(7);
        tablaPie.addCell(new Cell(1,1).add( new Paragraph(txtruc).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        tablaPie.addCell(new Cell(1,1).add( new Paragraph(txtruc1).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        tablaPie.addCell(new Cell(1,1).add( new Paragraph(txtruc2).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        tablaPie.addCell(new Cell(1,1).add( new Paragraph(txtruc3).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));

        tb_col1.addCell(new Cell(1,1).add(tablaPie).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        tb_col1.addCell(new Cell(1,1).add(new Paragraph("")).setBorder(Border.NO_BORDER));
        tb_col1.addCell(new Cell(1,1).add(tablaPie).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));

        return tb_col1;
    }


    /**
     * Manejador del evento de cambio de pagina, agrega el encabezado y pie de pagina
     * @param event Evento de pagina
     */
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

        canvas.addImage(ImageDataFactory.create(bitmapdata), PageSize.A4.rotate(), false);

        Table tablaEncabezado = this.crearTablaEncabezado(docEvent);
        Rectangle rectanguloEncabezado = this.crearRectanguloEncabezado(docEvent);
        Canvas canvasEncabezado = new Canvas(canvas, pdfDoc, rectanguloEncabezado);
        canvasEncabezado.add(tablaEncabezado);

        Table tablaNumeracion = this.crearTablaPie(docEvent);
        Rectangle rectanguloPie = this.crearRectanguloPie(docEvent);
        Canvas canvasPie = new Canvas(canvas, pdfDoc, rectanguloPie);
        canvasPie.add(tablaNumeracion);
    }
}
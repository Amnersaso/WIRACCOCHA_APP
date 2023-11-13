package w.kipu.kipu.PrintPDF;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
//import android.graphics.Color;
import android.graphics.drawable.Drawable;
//import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemListaSerieNumPeso;
import w.kipu.kipu.ITEM.ItemPesoPrintPDF_One;
import w.kipu.kipu.ITEM.Item_EncabProductCompraPrint;
import w.kipu.kipu.ITEM.Item_Product_Peso_Print;
import w.kipu.kipu.R;

//import print
import android.graphics.drawable.BitmapDrawable;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

public class PrintDocumentPDF extends AppCompatActivity {
    Button btn_print;
    String file_name = "WiraPDF.pdf";
    List<Item_Product_Peso_Print> ListaProducto = new ArrayList<>();
    List<Item_Product_Peso_Print> ListaProductoPDF = new ArrayList<>();
    ArrayList<ItemPesoPrintPDF_One>  dataListaPesoModifica= new ArrayList<>();
    ArrayList<ItemPesoPrintPDF_One>  dataListaPesoReal= new ArrayList<>();
    List<ItemListaSerieNumPeso> ListaSerieNumPeso = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterProductPesoPrint adapterAprobado;
    CONEXION conexionLocal = new CONEXION();
    Connection conPublica,conAll=null;
    AdapterSQLite myDB;
    String idCompra,YearCapania,idCompraNCompra = "",idProvNCompra = "",idProv,idAllCompra, PROD,path;
    TextView numSer,txt_dni,productor,comunidad,distrito,/*parcela,dni,tipo*/placa,fecha,nomProdFirma,dniProdFirma,nomUserFirma,dniUserFirma,txtSoliAdelanto,txtTotal,txtnombrGAC;
    TextView txtEstiva,txtTitulo,txtAlmacenGac,txtCampaniaYear,txtnCompraGAC,txtAlmacen,txt_loteProveedor,txt_guiaRemision;
    LinearLayout linearEncabRegPeso,linearEncabNotaCompra;
    String registroPesoValidar,gacRegisPesoValidar,id_usuario,tituloSerieNum,nube,tipoRegisNCompra,estado,tipoNota,tipoRegistroCompra,quienCobra,telefono,obsCompraGeneral,pruebaTipo,loteProveedor,guiaRemision;
//    private static final int external = 1;
//    private static String[] permisos={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
////    public static void verificarPermiso(Activity activity){
////        int permiso = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
////
////        if(permiso!= PackageManager.PERMISSION_GRANTED){
////            ActivityCompat.requestPermissions(activity,permisos,external);
////        }
////    }
////    public void perimoso_r(){
////        if (Build.VERSION.SDK_INT >= 30){
////            if (!Environment.isExternalStorageManager()){
//////                Intent getpermission = new Intent();
//////                getpermission.setAction(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//////                startActivity(getpermission);
////
////                Intent intent = new Intent();
////                intent.setAction(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
////                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
////                intent.setData(uri);
////                startActivity(intent);
////            }
////        }
////    }
    public void form(){
        numSer = findViewById(R.id.txt_numSerieProductCompraPrint);
        txt_dni = findViewById(R.id.TXT_DNI);
        productor = findViewById(R.id.txt_nombreProductCompraPrint);
        comunidad = findViewById(R.id.txt_comunidadProductCompraPrint);
        distrito = findViewById(R.id.txt_distritoProductCompraPrint);
        //parcela = findViewById(R.id.txt_parcelaProductCompraPrint);
        //tipo = findViewById(R.id.txt_tipProductorProductCompraPrint);
        placa = findViewById(R.id.txt_placaProductCompraPrint);
        //txt_loteProveedor = findViewById(R.id.txt_dniProductCompraPrint);
        txt_guiaRemision = findViewById(R.id.txt_tipProductorProductCompraPrint);
        fecha = findViewById(R.id.txt_fechaProductCompraPrint);
        btn_print = findViewById(R.id.btnProbar);
        nomProdFirma = findViewById(R.id.txt_nombreProdFirma);
        dniProdFirma = findViewById(R.id.txt_dniProdFirma);
        nomUserFirma = findViewById(R.id.txt_nombreUserFirma);
        dniUserFirma = findViewById(R.id.txt_dniUserFirma);
        txtSoliAdelanto = findViewById(R.id.txt_soliAdelantoNCompra);
        txtTotal = findViewById(R.id.txt_totalNCompra);
        txtnombrGAC = findViewById(R.id.txt_nombreUsuarioPrintDocPDF);
        txtnCompraGAC = findViewById(R.id.txt_nCompraSeriePrintDocPDF);
        linearEncabRegPeso = findViewById(R.id.LinearEncabRegPeso);
        linearEncabNotaCompra = findViewById(R.id.LinearEncabNotaCompra);
        txtAlmacen = findViewById(R.id.txtAlmacenCompraPrint);
        txtEstiva = findViewById(R.id.txt_estivaNombre);
        txtTitulo = findViewById(R.id.txt_tituloPrintNCRP);
        txtAlmacenGac = findViewById(R.id.txt_ubicacionGacPrint);
        txtCampaniaYear=findViewById(R.id.txt_campaniaYear);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_document_p_d_f);
        form();
        //verificarPermiso(this);
        //ActivityCompat.requestPermissions(this, new String [] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);
        //perimoso_r();
        recyclerView = findViewById(R.id.rvProduct);
        myDB = new AdapterSQLite(this);
        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            idCompraNCompra =(String) b.get("idCompraNCompra");
            idProvNCompra =(String) b.get("idProvNCompra");
            tipoRegisNCompra =(String) b.get("tipoRegistroNCompra");
            estado = (String) b.get("estadoCompraRegistroNCompra");
            tipoNota = (String) b.get("tipo_Nota");

            idCompra=(String) b.get("idCompraMostrarListProdRegis_Update");
            registroPesoValidar =(String) b.get("registroPesoMostListProducRegis");
            gacRegisPesoValidar =(String) b.get("gacUsusarioMostListProducRegis");
            id_usuario=(String) b.get("idusuarioMostListProducRegis");
            tituloSerieNum=(String) b.get("tituloSerieNumeroMostListProducRegis");
            pruebaTipo=(String) b.get("tipo_User");
            nube=(String) b.get("nubeNCompraNube");
        }
        if(idCompra != null)
            idAllCompra = idCompra;
        else
            idAllCompra = idCompraNCompra;

        //Toast.makeText(this, idAllCompra +" / "+idProvNCompra+" / "+tipoRegisNCompra+ " / "+registroPesoValidar+ " / "+pruebaTipo, Toast.LENGTH_SHORT).show();
        if(nube != null){
            Toast.makeText(this, "NUBE", Toast.LENGTH_SHORT).show();
            conPublica = conexionLocal.ConnectionDB_Local();
            if(conPublica != null)
                conAll = conPublica;
            else
                conAll = conexionLocal.ConnectionDB_Local();
        }
        if(idCompra != null){
            try {
                SEIDPROV(idAllCompra);
                SEIDPROVPRINT(idProv,idAllCompra);
                if (registroPesoValidar != null) {
                    listaSerieNumPesoPDF(idAllCompra);
                    selectSerieNum(idAllCompra);
                    txtTitulo.setText("REGISTRO DE PESO");
                    ViewGroup.LayoutParams params = linearEncabRegPeso.getLayoutParams();
                    params.height = 90;
                    params.width = 1000;
                    linearEncabRegPeso.setLayoutParams(params);
                    ViewGroup.LayoutParams params1 = linearEncabNotaCompra.getLayoutParams();
                    params1.height = 0;
                    params1.width = 1000;
                    linearEncabNotaCompra.setLayoutParams(params1);
                } else if (tipoRegisNCompra.equals("NC")){
                    txtTitulo.setText("NOTA DE COMPRA");
                    ViewGroup.LayoutParams params = linearEncabRegPeso.getLayoutParams();
                    params.height = 0;
                    params.width = 1000;
                    linearEncabRegPeso.setLayoutParams(params);
                    ViewGroup.LayoutParams params1 = linearEncabNotaCompra.getLayoutParams();
                    params1.height = 87;
                    params1.width = 1000;
                    linearEncabNotaCompra.setLayoutParams(params1);
                }else if (tipoRegisNCompra.equals("NS")){
                    txtTitulo.setText("NOTA DE SERVICIO");
                    ViewGroup.LayoutParams params = linearEncabRegPeso.getLayoutParams();
                    params.height = 0;
                    params.width = 1000;
                    linearEncabRegPeso.setLayoutParams(params);
                    ViewGroup.LayoutParams params1 = linearEncabNotaCompra.getLayoutParams();
                    params1.height = 87;
                    params1.width = 1000;
                    linearEncabNotaCompra.setLayoutParams(params1);
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();}
        }else{
            //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            SEIDPROVPRINT(idProvNCompra,idAllCompra); //encabezado --
            if (tipoRegisNCompra.equals("RP")){
                txtTitulo.setText("REGISTRO DE PESO");
                listaSerieNumPesoPDF(idAllCompra);
                selectSerieNum(idAllCompra);
                ViewGroup.LayoutParams params = linearEncabRegPeso.getLayoutParams();
                params.height = 90;
                params.width = 1000;
                linearEncabRegPeso.setLayoutParams(params);
                ViewGroup.LayoutParams params1 = linearEncabNotaCompra.getLayoutParams();
                params1.height = 0;
                params1.width = 1000;
                linearEncabNotaCompra.setLayoutParams(params1);
            } else if (tipoRegisNCompra.equals("NC")){
                txtTitulo.setText("NOTA DE COMPRA");
                ViewGroup.LayoutParams params = linearEncabRegPeso.getLayoutParams();
                params.height = 0;
                params.width = 1000;
                linearEncabRegPeso.setLayoutParams(params);
                ViewGroup.LayoutParams params1 = linearEncabNotaCompra.getLayoutParams();
                params1.height = 87;
                params1.width = 1000;
                linearEncabNotaCompra.setLayoutParams(params1);
            }else if (tipoRegisNCompra.equals("NS")){
                txtTitulo.setText("NOTA DE SERVICIO");
                ViewGroup.LayoutParams params = linearEncabRegPeso.getLayoutParams();
                params.height = 0;
                params.width = 1000;
                linearEncabRegPeso.setLayoutParams(params);
                ViewGroup.LayoutParams params1 = linearEncabNotaCompra.getLayoutParams();
                params1.height = 87;
                params1.width = 1000;
                linearEncabNotaCompra.setLayoutParams(params1);
            }
        }
        listaPeso();
        selectEstiva(idAllCompra);
        ListarUbicacion(idAllCompra);
        listaPesoPDF();
        Toast.makeText(this, tipoNota + " / "+ tipoRegistroCompra, Toast.LENGTH_SHORT).show();

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //createpdf();
                    createpdf_sinPermiso();
                    //finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void listaPeso(){
        try {
            ListaProducto.clear();
            ArrayList<String> dataPeso = new ArrayList<>();
            String prod;
            Cursor rs = myDB.selectListarEncabezadoPesoPDF(idAllCompra);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    prod = rs.getString(0);
                    Cursor rsPeso = myDB.ListaPrevistaPesos(idAllCompra,prod);
                    while (rsPeso.moveToNext()){
                        dataPeso.add(rsPeso.getString(0));
                    }
                    ListaProducto.add(new Item_Product_Peso_Print(rs.getString(0),"SAC "+ rs.getString(1)+" | ","PESO "+ rs.getString(2)+" | ",dataPeso.toString(),
                            "",rs.getString(5),"S/ "+ rs.getString(4)+" | ","PU " +rs.getString(3)+" | ",rs.getString(7),"PESO "));
                    dataPeso.clear();
                }
            }else{
                //Toast.makeText(getApplication(),"1 ",Toast.LENGTH_LONG).show();
                PreparedStatement pst1 = conAll.prepareStatement("{ call SPAPP_SEIDPROD_COM_PDF (?)}");
                pst1.setString(1,idAllCompra);
                ResultSet result = pst1.executeQuery();
                while (result.next()) {
                    ListaProducto.add(new Item_Product_Peso_Print(result.getString("PRODUCTO"), result.getString("SACOS"), result.getString("TOTAL"),result.getString("PESOS")
                            ,result.getString("LOTE"),result.getString("OBSERVACION_GENERAL"),result.getString("SUBTOTAL"),result.getString("PRECIO_UNIT"),result.getString("LOTE_PROVEEDOR"),result.getString("PESOS")));
                    PROD= result.getString("PRODUCTO");
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
            adapterAprobado = new AdapterProductPesoPrint(getApplication(), ListaProducto);
            recyclerView.setAdapter(adapterAprobado);
            recyclerView.getAdapter().notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(getApplication(),"ERROR LISTA PESO /"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void listaPesoPDF(){
        try {
            ListaProductoPDF.clear();
            Cursor rs = myDB.selectListarEncabezadoPesoPDF(idAllCompra);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    //Toast.makeText(getApplication(),rs.getString(7),Toast.LENGTH_LONG).show();
                    ListaProductoPDF.add(new Item_Product_Peso_Print(rs.getString(0), rs.getString(1), rs.getString(2),"",rs.getString(6),rs.getString(5),
                            rs.getString(4),rs.getString(3),rs.getString(7),rs.getString(8)));
                }
            }else{
                //Toast.makeText(getApplication(),"1 ",Toast.LENGTH_LONG).show();
                PreparedStatement pst1 = conAll.prepareStatement("{ call SPAPP_SEIDPROD_COM_PDF (?)}");
                pst1.setString(1,idAllCompra);
                ResultSet result = pst1.executeQuery();
                while (result.next()) {
                    ListaProductoPDF.add(new Item_Product_Peso_Print(result.getString("PRODUCTO"), result.getString("SACOS"), result.getString("TOTAL"),result.getString("PESOS")
                            ,result.getString("LOTE"),result.getString("OBSERVACION_GENERAL"),result.getString("SUBTOTAL"),result.getString("PRECIO_UNIT"),result.getString("LOTE_PROVEEDOR"),result.getString("TOTAL_REAL")));
                    PROD= result.getString("PRODUCTO");
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),"ERROR LISTA PESO PDF / "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void listaSerieNumPesoPDF(String id){
        try {
            ListaSerieNumPeso.clear();
            Cursor rs = myDB.listaSerieNumNC_by_RP(id);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToFirst()){
                    Toast.makeText(getApplication(),rs.getString(4) + " / ",Toast.LENGTH_LONG).show();
                    ListaSerieNumPeso.add(new ItemListaSerieNumPeso(rs.getString(0) + rs.getString(1) , rs.getString(3), rs.getString(4), rs.getString(2)));
                }
            }else{
                //Toast.makeText(getApplication(),"2 ",Toast.LENGTH_LONG).show();
                PreparedStatement pst1 = conAll.prepareStatement("{ call SPAPP_SELISTDSERNUM_TBSERNUM_RP (?)}");
                pst1.setString(1,id);
                ResultSet result = pst1.executeQuery();
                while (result.next()) {
                    ListaSerieNumPeso.add(new ItemListaSerieNumPeso(result.getString("SERIE"), result.getString("SACOS"), result.getString("TOTAL"), result.getString("PRODUCTO")));
                }
            }
        }catch (Exception e){
            //Toast.makeText(getApplication(),"ERROR SERIE NUM / "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        if(idProvNCompra != null){
            super.onBackPressed();
        }else{
            ShowDialog();
        }
    }
    public void ShowDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SALIR");
        builder.setMessage("¿ SEGURO QUE DESEA SALIR SIN IMPRIMIR ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }
    private void createpdf_sinPermiso() throws FileNotFoundException {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream= openFileOutput(file_name,MODE_PRIVATE);
            PdfWriter writer = new PdfWriter(fileOutputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);

            Document document = new Document(pdfDocument,PageSize.A4.rotate());
            //document.setMargins(100,0,100,0);
            document.setMargins(15, 0, 80, 0);

            Drawable drawable = getDrawable(R.drawable.fondo_pdf);
            Bitmap bitmap1 = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream1 = new com.itextpdf.io.source.ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG,100,stream1);
            byte[] fondo =stream1.toByteArray();

            //imagen
            Drawable d = getDrawable(R.drawable.logo_pdf);
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            ByteArrayOutputStream stream = new com.itextpdf.io.source.ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] bitmapdata =stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapdata);

            SolidLine line = new SolidLine(1f);
            Color black = new DeviceRgb(0,0,0);
            line.setColor(black);
            LineSeparator ls = new LineSeparator(line);
            ls.getStrokeWidth(); ls.setMarginTop(5); ls.setMarginBottom(5);
            Integer countPeso=0;
            double pe=0; double preUNIT=0; double pesoArray=0;
            Integer filaRestante=0;
            String tipoProductor;
            ArrayList<Double> dataPesoResult = new ArrayList<>();

            Text CAMPAÑA = new Text("CAMPAÑA "+ YearCapania +" \n").setBold().setFontSize(8);
            Text NOTA_COMPRA = new Text(txtTitulo.getText().toString()+"\n").setBold().setFontSize(12);
            Text Serie_Numero = new Text(numSer.getText().toString()).setFontSize(10);
            Text txtruc = new Text("R.U.C. Nº 20494626897").setBold().setFontSize(7);
            Text txtdireccion = new Text("Jr. Próceres Nº 201 - San Juan Bautista Ayacucho").setBold().setFontSize(7);
            Text txtTelefonoCel = new Text("Central Telf.: (066) 314815 / 966013140 / 966013141").setBold().setFontSize(7);
            Text txtFech = new Text(fecha.getText().toString()).setBold().setFontSize(7);

            // tabla encabezado LOGO Y NUMERO DE DOC
            float  col_hoja1[] = {480,40,480};
            Table tb_col1 = new Table(col_hoja1);
            float  col_en[] = {150,150,150};
            Table tb_encabezado = new Table(col_en);

            // encabesado informacion2
            float  rowf1[] = {40,40,40,50,50,50,50,50,50};
            Table tb_enf1 = new Table(rowf1);

            //detalle nota de compra
            float  rowdetalle[] = {50,50,50,50,50,50,50,50,50,50,50};
            Table tb_detalle = new Table(rowdetalle);

            Text txtDNI = new Text(txt_dni.getText().toString()).setBold().setFontSize(7);
            Text txtProductor = new Text(productor.getText().toString()).setBold().setFontSize(7);
            Text txtComunidad = new Text(comunidad.getText().toString()).setBold().setFontSize(7);
            Text txtDistrito = new Text(distrito.getText().toString()).setBold().setFontSize(7);
            //Text txtTipo = new Text(tipo.getText().toString()).setBold().setFontSize(7);
            Text txtPlaca = new Text(placa.getText().toString()).setBold().setFontSize(7);
            //Text txtLoteProveedor = new Text(txt_loteProveedor.getText().toString()).setBold().setFontSize(7);
            Text txtGuiaRemision = new Text(txt_guiaRemision.getText().toString()).setBold().setFontSize(7);
            Text txtTelefono= new Text(String.valueOf(telefono)).setBold().setFontSize(7);
            //Text txtParcela = new Text(parcela.getText().toString()).setBold().setFontSize(7);
            Text txtalmace = new Text(txtAlmacen.getText().toString()).setBold().setFontSize(7);
            Text txtGacPDF = new Text(txtnombrGAC.getText().toString()).setBold().setFontSize(7);
            Text txtSeriePDF = new Text(txtnCompraGAC.getText().toString()).setBold().setFontSize(7);
            Text txtEntrega = new Text(nomProdFirma.getText().toString()).setBold().setFontSize(7);
            String user=nomUserFirma.getText().toString();
            String entrega=nomProdFirma.getText().toString();
            String subTotalKG,totalSole,totalKG;
            DecimalFormat df = new DecimalFormat("###,###.#");
            String subTotalKGDecimal,pesoTexto,pesoTotalSerie;
            double subTotalD,pesoDouble,pesoTotalSerieDouble;

            if(tipoRegistroCompra.equals("RP")){
                tb_encabezado.addCell(new Cell(1,1).add( new Image(imageData).setHeight(50).setWidth(100)).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("\t")).setBorder(Border.NO_BORDER).setMarginLeft(0));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph(CAMPAÑA).add(NOTA_COMPRA).add(Serie_Numero).add(new Text("\n LC:______________").setFontSize(10)).setPaddingBottom(0)
                        .setTextAlignment(TextAlignment.CENTER)).setPadding(0).setVerticalAlignment(VerticalAlignment.MIDDLE));

                // Encabesado informacion
                tb_encabezado.addCell(new Cell(1,3).add( new Paragraph(txtruc)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,3).add( new Paragraph(txtdireccion)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,2).add( new Paragraph(txtTelefonoCel)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("Fecha: ").setFontSize(8).add(txtFech)).setPadding(0).setBorder(Border.NO_BORDER));

                tb_col1.addCell(new Cell(1,1).add(tb_encabezado).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ").setMargin(20)).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_encabezado).setBorder(Border.NO_BORDER));

                tipoProductor = "GAC: ";
                tb_enf1.addCell(new Cell(1,5).add( new Paragraph("NOMBRE: ").add(txtGacPDF).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,2).add( new Paragraph("ALMACÉN: ").add(txtalmace).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,2).add( new Paragraph("PLACA: ").add(txtPlaca).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,9).add( new Paragraph("N. COMPRA: ").add(txtSeriePDF).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));

                tb_col1.addCell(new Cell(1,1).add(tb_enf1).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ")).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_enf1).setBorder(Border.NO_BORDER));

                tb_detalle.addCell(new Cell(1,11).add(new Paragraph("")).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,7).add(new Paragraph("")).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("COD. PROV")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setPaddingLeft(-2).setPaddingRight(-2).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("CANTIDAD: ")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));

                for (Item_Product_Peso_Print DET : ListaProductoPDF){
                    tb_detalle.addCell(new Cell(1,7).add( new Paragraph(DET.getProducto()).add(" | ").add(DET.getLote())
                            .setTextAlignment(TextAlignment.LEFT).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph(DET.getLoteProveedor())
                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setPaddingLeft(-2).setPaddingRight(-2).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph(DET.getPeso())
                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    //tb_detalle.addCell(new Cell(1,5).add( new Paragraph(DET.getTotal()+DET.getPeso()+DET.getpUnit()+DET.getSubtotal())
                    //      .setTextAlignment(TextAlignment.RIGHT).setFontSize(8)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
//                   tb_detalle.addCell(new Cell(1,1).add( new Paragraph("KG")
//                           .setTextAlignment(TextAlignment.CENTER).setFontSize(8)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    ListarPesos(DET.getProducto());
                    //listaSacos(idAllCompra,DET.getProducto());

                    for (ItemPesoPrintPDF_One Pesos : dataListaPesoModifica){
                        countPeso = countPeso + 1;
                        pesoDouble = Double.parseDouble(Pesos.getPeso());
                        pesoTexto = df.format(pesoDouble);
                        if(countPeso == 11){
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).
                                    setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph(pesoTexto).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                            pe = pesoDouble;
                            countPeso = 1;
                            //Toast.makeText(getApplication(),String.valueOf(countPeso)+" / es 11",Toast.LENGTH_SHORT).show();
                        }else {
                            pe = pe + pesoDouble;
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph(pesoTexto).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        }
                    }
                    filaRestante = (countPeso % 10);
                    if(filaRestante<10 && filaRestante>0){
                        tb_detalle.addCell(new Cell(1,10 - filaRestante).add(ls));
                        tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).
                                setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        pe = 0;
                        countPeso = 0;
                    }else {
                        tb_detalle.addCell(new Cell(1, 1).add(new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0, 0, 0), 0.1f).
                                setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        pe = 0;
                        countPeso = 0;
                    }

                    if (DET.getObservacion().isEmpty())
                        tb_detalle.addCell(new Cell(1,8).add( new Paragraph()).setBorder(Border.NO_BORDER));
                    else
                        tb_detalle.addCell(new Cell(1,8).add( new Paragraph("OBS: ").add(DET.getObservacion()).setFontSize(7)));

                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph("SUB TOTAL (KG) ").setTextAlignment(TextAlignment.RIGHT).setFontSize(7).setBold()));

                    subTotalD = Double.parseDouble(DET.getTotal());
                    subTotalKGDecimal = df.format(subTotalD);

                    tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(subTotalKGDecimal).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).
                            setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));

                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));
                    dataPesoResult.add(Double.parseDouble(DET.getTotal()));

                }

                tb_detalle.addCell(new Cell(1,7).add( new Paragraph()).setBorder(Border.NO_BORDER));

                for (double i : dataPesoResult)
                    pesoArray +=i;
                totalKG = df.format(pesoArray);
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("TOTAL (KG) ").setTextAlignment(TextAlignment.RIGHT)).setBold().setFontSize(7));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph(totalKG).setPaddingRight(7).setTextAlignment(TextAlignment.RIGHT)).setBold().setFontSize(7));

                tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));

                //
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("FORMATO").setTextAlignment(TextAlignment.CENTER).setFontSize(7))
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,4).add( new Paragraph()));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("VºBº").setTextAlignment(TextAlignment.CENTER).setFontSize(7))
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,4).add( new Paragraph()).setBorder(Border.NO_BORDER));

                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("FLETE")).setTextAlignment(TextAlignment.CENTER).setFontSize(7)
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,4).add( new Paragraph()).setBorder(Border.NO_BORDER));

                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("ESTIBA")).setTextAlignment(TextAlignment.CENTER).setFontSize(7)
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,4).add( new Paragraph()).setBorder(Border.NO_BORDER));

                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("COMBUSTIBLE")).setTextAlignment(TextAlignment.CENTER).setFontSize(7)
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,4).add( new Paragraph()).setBorder(Border.NO_BORDER));

                tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));

                float  rowSerie[] = {50,50,50,50,50,50,50,50,50,50,50,50};
                Table tb_serie = new Table(rowSerie);

                //tb_serie.setKeepTogether(true);

                tb_serie.addCell(new Cell(1,2).add( new Paragraph("SERIE").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).
                        setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_serie.addCell(new Cell(1,3).add( new Paragraph("SACO | PRODUCTO").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).
                        setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_serie.addCell(new Cell(1,2).add( new Paragraph("PESOS").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).
                        setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_serie.addCell(new Cell(1,1).add( new Paragraph()).setBorder(Border.NO_BORDER));
                tb_serie.addCell(new Cell(1,2).add( new Paragraph("PESOS NC").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).
                        setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_serie.addCell(new Cell(1,2).add( new Paragraph("PESOS RP").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).
                        setBackgroundColor(new DeviceRgb(0,0,0),0.1f));


                for (ItemListaSerieNumPeso SER : ListaSerieNumPeso){
                    pe = pe + Double.parseDouble(SER.getPeso());
                }
                int a = 0;
                double x = pesoArray - pe;
                for (ItemListaSerieNumPeso SER : ListaSerieNumPeso){

                    pesoTotalSerieDouble = Double.parseDouble(SER.getPeso());
                    pesoTotalSerie = df.format(pesoTotalSerieDouble);

                    tb_serie.addCell(new Cell(1,2).add( new Paragraph(SER.getSerie()).setTextAlignment(TextAlignment.CENTER).setFontSize(7)));
                    tb_serie.addCell(new Cell(1,3).add( new Paragraph(SER.getNum()).add(" | ").add(SER.getProd()).setTextAlignment(TextAlignment.LEFT).setFontSize(7)));
                    tb_serie.addCell(new Cell(1,2).add( new Paragraph(pesoTotalSerie).setTextAlignment(TextAlignment.CENTER).setFontSize(7)));
                    tb_serie.addCell(new Cell(1,1).add( new Paragraph()).setBorder(Border.NO_BORDER));

                    if (a == 0){
                        tb_serie.addCell(new Cell(1,2).add( new Paragraph(String.valueOf(pe)).setTextAlignment(TextAlignment.CENTER).setFontSize(7)));
                        tb_serie.addCell(new Cell(1,2).add( new Paragraph(totalKG).setTextAlignment(TextAlignment.CENTER).setFontSize(7)));
                    }else if(a == 1)
                        tb_serie.addCell(new Cell(1,4).add( new Paragraph("DIFERENCIA DE: ").add(String.valueOf(x)).setTextAlignment(TextAlignment.CENTER).setFontSize(7)));
                    else
                        tb_serie.addCell(new Cell(1,4).add( new Paragraph()).setBorder(Border.NO_BORDER));
                    a++;
                }
                if(a==1){
                    tb_serie.addCell(new Cell(1,8).add( new Paragraph()).setBorder(Border.NO_BORDER));
                    tb_serie.addCell(new Cell(1,4).add( new Paragraph("DIFERENCIA DE: ").add(String.valueOf(x))
                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)));
                }

                tb_serie.addCell(new Cell(1,12).add( new Paragraph("")).setBorder(Border.NO_BORDER));

                if(obsCompraGeneral!=null){
                    tb_serie.addCell(new Cell(1,12).add( new Paragraph("OBS: ").add(obsCompraGeneral).setFontSize(7))
                            .setTextAlignment(TextAlignment.LEFT).setPaddingTop(10).setBorder(Border.NO_BORDER));
                }else{
                    tb_serie.addCell(new Cell(1,12).add( new Paragraph("OBS: ________________________________________________________________________________________").setFontSize(7))
                            .setTextAlignment(TextAlignment.CENTER).setPaddingTop(10).setBorder(Border.NO_BORDER));
                }

                Text txtEsti = new Text(txtEstiva.getText().toString()).setFontSize(7);
                //Toast.makeText(getApplication(),txtEsti.getText().toString(),Toast.LENGTH_SHORT).show();
                if(txtEsti.getText().equals("")){
                }else{
                    tb_serie.addCell(new Cell(1,12).add( new Paragraph("ESTIBA / DESESTIBA: ").setFontSize(7).setBold())
                            .setTextAlignment(TextAlignment.LEFT).setPaddingTop(10).setBorder(Border.NO_BORDER));
                    tb_serie.addCell(new Cell(1,12).add( new Paragraph().add(txtEsti)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                }

                tb_col1.addCell(new Cell(1,1).add(tb_detalle).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ")).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_detalle).setBorder(Border.NO_BORDER));

                tb_col1.addCell(new Cell(1,1).add(tb_serie).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ")).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_serie).setBorder(Border.NO_BORDER));
            }
            else if(tipoRegistroCompra.equals("NC")){
                tb_encabezado.addCell(new Cell(1,1).add( new Image(imageData).setHeight(50).setWidth(100)).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("\t")).setBorder(Border.NO_BORDER).setMarginLeft(0));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph(CAMPAÑA).add(NOTA_COMPRA).add(Serie_Numero).add(new Text("\n LC:______________").setFontSize(10)).setPaddingBottom(0)
                        .setTextAlignment(TextAlignment.CENTER)).setPadding(0).setVerticalAlignment(VerticalAlignment.MIDDLE));

                // Encabesado informacion
                tb_encabezado.addCell(new Cell(1,3).add( new Paragraph(txtruc)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,3).add( new Paragraph(txtdireccion)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,2).add( new Paragraph(txtTelefonoCel)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("Fecha: ").setFontSize(8).add(txtFech)).setPadding(0).setBorder(Border.NO_BORDER));

                tb_col1.addCell(new Cell(1,1).add(tb_encabezado).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ").setMargin(20)).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_encabezado).setBorder(Border.NO_BORDER));

                tipoProductor = "Productor: ";
                tb_enf1.addCell(new Cell(1,6).add( new Paragraph("EMPRESA/PROVEEDOR: ").add(txtProductor).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,3).add( new Paragraph("DNI/RUC: ").add(txtDNI).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,4).add( new Paragraph("COMUNIDAD: ").add(txtComunidad).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,5).add( new Paragraph("DISTRITO: ").add(txtDistrito).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,3).add( new Paragraph("GUÍA REMISIÓN: ").add(txtGuiaRemision).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,3).add( new Paragraph("PLACA: ").add(txtPlaca).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
//                tb_enf1.addCell(new Cell(1,2).add( new Paragraph("PARCELA: ").add("txtParcela").setMargin(0)
//                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,3).add( new Paragraph("ALMACÉN: ").add(txtalmace).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,7).add( new Paragraph("ENTREGADO POR: ").add(txtEntrega).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,2).add( new Paragraph("CELULAR: ").add(txtTelefono).setMargin(0)
                        .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));

                tb_col1.addCell(new Cell(1,1).add(tb_enf1).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ")).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_enf1).setBorder(Border.NO_BORDER));
                // Toast.makeText(getApplication(),"3",Toast.LENGTH_SHORT).show();
                //ENCABEZADO COLUMNA
                Text txtquienCobra = new Text(quienCobra).setBold().setFontSize(7);
                if(quienCobra != null){
                    tb_detalle.addCell(new Cell(1,5).add( new Paragraph("COBRA: ").add(txtquienCobra).setMargin(0)
                            .setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                }else {
                    tb_detalle.addCell(new Cell(1, 5).add(new Paragraph()).setBorder(Border.NO_BORDER));
                }
                //Toast.makeText(getApplication(),"4",Toast.LENGTH_SHORT).show();
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("COD. PROV")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setPaddingLeft(-2).setPaddingRight(-2).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,1).add( new Paragraph("SACOS")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setPaddingLeft(-2).setPaddingRight(-2).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,1).add( new Paragraph("P.U.")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("TOTAL S/")
                        .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                // tb_detalle.addCell(new Cell(1,1).add( new Paragraph()).setBorder(Border.NO_BORDER));

                //Toast.makeText(getApplication(),"5",Toast.LENGTH_SHORT).show();
                for (Item_Product_Peso_Print DET : ListaProductoPDF){
                    tb_detalle.addCell(new Cell(1,5).add( new Paragraph(DET.getProducto()).add(" | ").add(DET.getLote())
                            .setTextAlignment(TextAlignment.LEFT).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph(DET.getLoteProveedor())
                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,1).add( new Paragraph(DET.getPeso())
                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    preUNIT = Double.parseDouble(DET.getpUnit());

                    if (preUNIT == 0){
                        tb_detalle.addCell(new Cell(1,1).add( new Paragraph("")
                                .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                        tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")
                                .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    }else{
                        tb_detalle.addCell(new Cell(1,1).add( new Paragraph(DET.getpUnit())
                                .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                        pesoDouble = Double.parseDouble(DET.getSubtotal());
                        subTotalKG = df.format(pesoDouble);
                        tb_detalle.addCell(new Cell(1,2).add( new Paragraph(subTotalKG)
                                .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                        pesoDouble=0;
                        subTotalKG="";
                    }
                    //tb_detalle.addCell(new Cell(1,5).add( new Paragraph(DET.getTotal()+DET.getPeso()+DET.getpUnit()+DET.getSubtotal())
                    //      .setTextAlignment(TextAlignment.RIGHT).setFontSize(8)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
//                    tb_detalle.addCell(new Cell(1,1).add( new Paragraph("KG")
//                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    ListarPesos(DET.getProducto());

                    for (ItemPesoPrintPDF_One Pesos : dataListaPesoModifica){
                        countPeso = countPeso + 1;
                        pesoDouble = Double.parseDouble(Pesos.getPeso());
                        pesoTexto = df.format(pesoDouble);
                        if(countPeso == 11){
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).
                                    setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                            //Toast.makeText(getApplication(),String.valueOf(pe),Toast.LENGTH_SHORT).show();
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph(pesoTexto).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                            pe = pesoDouble;
                            countPeso = 1;
                        }else {
                            pe = pe + pesoDouble;
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph(pesoTexto).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        }
                    }
                    filaRestante = (countPeso % 10);
                    if(filaRestante<10 && filaRestante>0){
                        tb_detalle.addCell(new Cell(1,10 - filaRestante).add(ls));
                        tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).
                                setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        pe = 0;
                        countPeso = 0;
                    }else {
                        tb_detalle.addCell(new Cell(1, 1).add(new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0, 0, 0), 0.1f).
                                setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        pe = 0;
                        countPeso = 0;
                    }

                    if (DET.getObservacion().isEmpty())
                        tb_detalle.addCell(new Cell(1,8).add( new Paragraph()).setBorder(Border.NO_BORDER));
                    else
                        tb_detalle.addCell(new Cell(1,8).add( new Paragraph("OBS: ").add(DET.getObservacion()).setFontSize(7)));

                    double subTotal = Double.parseDouble(DET.getTotal());
                    subTotalKG = df.format(subTotal);
                    //Toast.makeText(getApplication(),subTotalKG,Toast.LENGTH_SHORT).show();
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph("SUB TOTAL (KG) ").setTextAlignment(TextAlignment.RIGHT).setFontSize(7).setBold()));
                    tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(subTotalKG).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).
                            setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph("")).setBorder(Border.NO_BORDER));
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph("")).setBorder(Border.NO_BORDER));
                    dataPesoResult.add(subTotal);
                }

                Text txtTot = new Text(txtTotal.getText().toString()).setBold();
                double pTotal = Double.parseDouble(txtTot.getText());
                if(pTotal == 0)
                    totalSole = "";
                else
                    totalSole = df.format(pTotal);
                //Toast.makeText(getApplication(),totalSole,Toast.LENGTH_SHORT).show();
                tb_detalle.addCell(new Cell(1,5).add( new Paragraph()).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,3).add( new Paragraph("TOTAL S/ ").add(totalSole).setTextAlignment(TextAlignment.LEFT).setPaddingLeft(7)).setPadding(0).setBold().setFontSize(8));
                for (double i : dataPesoResult)
                    pesoArray +=i;
                totalKG = df.format(pesoArray);
                //Toast.makeText(getApplication(),totalKG,Toast.LENGTH_SHORT).show();
                tb_detalle.addCell(new Cell(1,3).add( new Paragraph("TOTAL KG ").add(totalKG).setTextAlignment(TextAlignment.LEFT).setPaddingLeft(7)).setPadding(0).setBold().setFontSize(8));

                tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,11).add( new Paragraph()).setBorder(Border.NO_BORDER));

                tb_detalle.addCell(new Cell(2,2).add( new Paragraph("SOLICITUD DE ADELANTO").setTextAlignment(TextAlignment.CENTER).setFontSize(7))
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("FECHA").setTextAlignment(TextAlignment.CENTER).setFontSize(7))
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("S/").setTextAlignment(TextAlignment.CENTER).setFontSize(7))
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("SALDO").setTextAlignment(TextAlignment.CENTER).setFontSize(7))
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("VºBº").setTextAlignment(TextAlignment.CENTER).setFontSize(7))
                        .setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,1).add( new Paragraph()).setBorder(Border.NO_BORDER));

                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")).setPadding(8));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")));
                tb_detalle.addCell(new Cell(1,1).add( new Paragraph()).setBorder(Border.NO_BORDER));

                String txtAdelant = txtSoliAdelanto.getText().toString();
                //Toast.makeText(getApplication(),txtSoliAdelanto.getText().toString(),Toast.LENGTH_SHORT).show();
                if(txtAdelant != null)
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph("S/ ").add(txtAdelant).setTextAlignment(TextAlignment.CENTER)).setFontSize(7));
                else
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph("S/S").setTextAlignment(TextAlignment.CENTER)).setFontSize(7));

                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")).setFontSize(7));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")).setFontSize(7));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")).setFontSize(7));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("")).setFontSize(7));
                tb_detalle.addCell(new Cell(1,1).add( new Paragraph()).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,11).add( new Paragraph("")).setBorder(Border.NO_BORDER));

                if(obsCompraGeneral!=null){
                    tb_detalle.addCell(new Cell(1,12).add( new Paragraph("OBS: ").add(obsCompraGeneral).setFontSize(7))
                            .setTextAlignment(TextAlignment.LEFT).setPaddingTop(10).setBorder(Border.NO_BORDER));
                }else{
                    tb_detalle.addCell(new Cell(1,12).add( new Paragraph("OBS: ________________________________________________________________________________________").setFontSize(7))
                            .setTextAlignment(TextAlignment.CENTER).setPaddingTop(10).setBorder(Border.NO_BORDER));
                }

                Text txtEsti = new Text(txtEstiva.getText().toString()).setFontSize(7);
                //Toast.makeText(getApplication(),txtEsti.getText().toString(),Toast.LENGTH_SHORT).show();
                if(txtEsti.getText().equals("")){
                }else{
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph("ESTIBA / DESESTIBA: ").setFontSize(7))
                            .setTextAlignment(TextAlignment.LEFT).setPaddingTop(10).setBorder(Border.NO_BORDER));
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph().add(txtEsti).setFontSize(7))
                            .setTextAlignment(TextAlignment.LEFT).setPadding(0).setBorder(Border.NO_BORDER));
                }

                tb_col1.addCell(new Cell(1,1).add(tb_detalle).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ")).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_detalle).setBorder(Border.NO_BORDER));

            }
            else{
                tb_encabezado.addCell(new Cell(1,1).add( new Image(imageData).setHeight(50).setWidth(120)).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("\t")).setBorder(Border.NO_BORDER).setMarginLeft(0));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("").add(NOTA_COMPRA).add(Serie_Numero).setPaddingBottom(0).setTextAlignment(TextAlignment.CENTER)).setPadding(0).setVerticalAlignment(VerticalAlignment.MIDDLE));
                tb_encabezado.addCell(new Cell(1,2).add( new Paragraph("")).setBorder(Border.NO_BORDER).setMarginLeft(0));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("Impreso desde la App").setFontSize(8).setTextAlignment(TextAlignment.CENTER)).setPadding(0).setBorder(Border.NO_BORDER));
                // Encabesado informacion
                tb_encabezado.addCell(new Cell(1,3).add( new Paragraph(txtruc)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,3).add( new Paragraph(txtdireccion)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,2).add( new Paragraph(txtTelefonoCel)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_encabezado.addCell(new Cell(1,1).add( new Paragraph("Fecha: ").setFontSize(8).add(txtFech)).setPadding(0).setBorder(Border.NO_BORDER));

                tb_col1.addCell(new Cell(1,1).add(tb_encabezado).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ").setMargin(20)).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_encabezado).setBorder(Border.NO_BORDER));

                tipoProductor = "Empresa: ";
                tb_enf1.addCell(new Cell(1,6).add( new Paragraph("CLIENTE: ").add(txtProductor).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,3).add( new Paragraph("DNI/RUC: ").add(txtDNI).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,4).add( new Paragraph("PROCEDENCIA: ").add(txtComunidad).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,5).add( new Paragraph("ENTREGADO POR: ").add(txtEntrega).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                //tb_enf1.addCell(new Cell(1,5).add( new Paragraph("DISTRITO: ").add(txtDistrito).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,3).add( new Paragraph("GUÍA REMISIÓN: ").add(txtGuiaRemision).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,2).add( new Paragraph("ALMACÉN: ").add(txtalmace).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,2).add( new Paragraph("CELULAR: ").add(txtTelefono).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));
                tb_enf1.addCell(new Cell(1,2).add( new Paragraph("PLACA: ").add(txtPlaca).setMargin(0).setTextAlignment(TextAlignment.LEFT).setFontSize(8)).setPadding(0).setBorder(Border.NO_BORDER));

                tb_col1.addCell(new Cell(1,1).add(tb_enf1).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ")).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_enf1).setBorder(Border.NO_BORDER));

                //Toast.makeText(getApplication(),"4",Toast.LENGTH_SHORT).show();
                tb_detalle.addCell(new Cell(1, 6).add(new Paragraph()).setBorder(Border.NO_BORDER));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("LOTE PROV").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setPaddingLeft(-2).setPaddingRight(-2).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("SACOS / PALETA").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setPaddingLeft(-2).setPaddingRight(-2).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                tb_detalle.addCell(new Cell(1,1).add( new Paragraph()).setBorder(Border.NO_BORDER));

                //DecimalFormat decimalFormat = new DecimalFormat("#.#");

                for (Item_Product_Peso_Print DET : ListaProductoPDF){
                    tb_detalle.addCell(new Cell(1,6).add( new Paragraph(DET.getProducto()).add(" | ").add(DET.getLote())
                            .setTextAlignment(TextAlignment.LEFT).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph(DET.getLoteProveedor())
                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph(DET.getPeso())
                            .setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                   tb_detalle.addCell(new Cell(1,1).add( new Paragraph("KG").setTextAlignment(TextAlignment.CENTER).setFontSize(7)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    ListarPesoReal(DET.getProducto());

                    for (ItemPesoPrintPDF_One Pesos : dataListaPesoReal){
                        countPeso = countPeso + 1;
                        pesoDouble = Double.parseDouble(Pesos.getPeso());
                        pesoTexto = df.format(pesoDouble);
                        if(countPeso == 11){
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(String.valueOf(df.format(pe))).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                            //Toast.makeText(getApplication(),String.valueOf(pe),Toast.LENGTH_SHORT).show();
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph(pesoTexto).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                            pe = pesoDouble;
                            countPeso = 1;
                        }else {
                            pe = pe + pesoDouble;
                            tb_detalle.addCell(new Cell(1,1).add( new Paragraph(pesoTexto).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        }
                    }
                    filaRestante = (countPeso % 10);
                    if(filaRestante<10 && filaRestante>0){
                        tb_detalle.addCell(new Cell(1,10 - filaRestante).add(ls));
                        tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).
                                setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        pe = 0;
                        countPeso = 0;
                    }else {
                        tb_detalle.addCell(new Cell(1, 1).add(new Paragraph().add(String.valueOf(pe)).setBackgroundColor(new DeviceRgb(0, 0, 0), 0.1f).
                                setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()));
                        pe = 0;
                        countPeso = 0;
                    }
                    if (DET.getObservacion().isEmpty())
                        tb_detalle.addCell(new Cell(1,8).add( new Paragraph()).setBorder(Border.NO_BORDER));
                    else
                        tb_detalle.addCell(new Cell(1,8).add( new Paragraph("OBS: ").add(DET.getObservacion()).setFontSize(7)));

                    //double subTotal = Double.parseDouble(DET.getTotal());
                    double subTotal = Double.parseDouble(DET.getPesoReal());
                    subTotalKG = df.format(subTotal);
                    //Toast.makeText(getApplication(),subTotalKG,Toast.LENGTH_SHORT).show();
                    tb_detalle.addCell(new Cell(1,2).add( new Paragraph("SUB TOTAL (KG) ").setTextAlignment(TextAlignment.RIGHT).setFontSize(7).setBold()).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,1).add( new Paragraph().add(subTotalKG).setTextAlignment(TextAlignment.CENTER).setFontSize(7).setBold()).setBackgroundColor(new DeviceRgb(0,0,0),0.1f));
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph("")).setBorder(Border.NO_BORDER));
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph("")).setBorder(Border.NO_BORDER));
                    dataPesoResult.add(subTotal);
                }
//                Text txtTot = new Text(txtTotal.getText().toString()).setBold();
//                double pTotal = Double.parseDouble(txtTot.getText());
//                if(pTotal == 0)
//                    totalSole = "";
//                else
//                    totalSole = df.format(pTotal);
                //Toast.makeText(getApplication(),totalSole,Toast.LENGTH_SHORT).show();
                tb_detalle.addCell(new Cell(1,8).add( new Paragraph()).setBorder(Border.NO_BORDER));
                //tb_detalle.addCell(new Cell(1,3).add( new Paragraph("TOTAL S/ ").add(totalSole).setTextAlignment(TextAlignment.LEFT).setPaddingLeft(7)).setPadding(0).setBold().setFontSize(8));
                for (double i : dataPesoResult)
                    pesoArray +=i;
                totalKG = df.format(pesoArray);
                tb_detalle.addCell(new Cell(1,2).add( new Paragraph("TOTAL KG ").setTextAlignment(TextAlignment.RIGHT)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).setPaddingRight(2).setBold().setFontSize(8));
                tb_detalle.addCell(new Cell(1,1).add( new Paragraph(totalKG).setTextAlignment(TextAlignment.CENTER)).setBackgroundColor(new DeviceRgb(0,0,0),0.1f).setBold().setFontSize(8));

                if(obsCompraGeneral!=null){
                    tb_detalle.addCell(new Cell(1,12).add( new Paragraph("OBS: ").add(obsCompraGeneral).setFontSize(7))
                            .setTextAlignment(TextAlignment.LEFT).setPaddingTop(10).setBorder(Border.NO_BORDER));
                }else{
                    tb_detalle.addCell(new Cell(1,12).add( new Paragraph("OBS: ________________________________________________________________________________________").setFontSize(7))
                            .setTextAlignment(TextAlignment.CENTER).setPaddingTop(10).setBorder(Border.NO_BORDER));
                }

                Text txtEsti = new Text(txtEstiva.getText().toString()).setFontSize(7);
                //Toast.makeText(getApplication(),txtEsti.getText().toString(),Toast.LENGTH_SHORT).show();
                if(txtEsti.getText().equals("")){
                }else{
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph("ESTIBA / DESESTIBA: ").setFontSize(7))
                            .setTextAlignment(TextAlignment.LEFT).setPaddingTop(10).setBorder(Border.NO_BORDER));
                    tb_detalle.addCell(new Cell(1,11).add( new Paragraph().add(txtEsti).setFontSize(7))
                            .setTextAlignment(TextAlignment.LEFT).setPadding(0).setBorder(Border.NO_BORDER));
                }

                tb_col1.addCell(new Cell(1,1).add(tb_detalle).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(new Paragraph("     ")).setBorder(Border.NO_BORDER));
                tb_col1.addCell(new Cell(1,1).add(tb_detalle).setBorder(Border.NO_BORDER));
            }

            EventoPagina evento = new EventoPagina(document,fondo,entrega,tipoProductor,user);
            //pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE,evento);
            //pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE,evento);
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, evento);
            document.add(tb_col1);
            Paragraph salto =  new Paragraph("\n");
            document.add(salto);

            document.close();
            PrintPDF(path);
            Toast.makeText(this,"DOCUMENTO GENERADO", Toast.LENGTH_LONG).show();
    }catch (Exception e){
        Toast.makeText(this," CREAR PDF | " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
    }
    public void selectSerieNum(String id){
        try {
            String s = null;
            ArrayList<String> data=new ArrayList<>();
            data.clear();
            Cursor rs = myDB.listaSerieNumEncabezado_RP(id);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()) {
                    data.add(rs.getString(1) + " - " + rs.getString(2));
                }
                txtnCompraGAC.setText(data.toString());
            }else{
                PreparedStatement pst = conAll.prepareStatement("{CALL SPAPP_SEIDCOM_TBSERNUM (?)}");
                pst.setString(1,idAllCompra);
                ResultSet result = pst.executeQuery();
                while (result.next()){
                     s= result.getString("SERIE");
                }
                txtnCompraGAC.setText(s);
            }
        }catch (Exception e){
            Toast.makeText(this,"ERROR "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void ListarPesos(String PO){
        try {
            String valor=null;
            dataListaPesoModifica.clear();
            Cursor rs = myDB.selectListarPesoPDF(idAllCompra,PO);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    valor = rs.getString(0);
                    dataListaPesoModifica.add(new ItemPesoPrintPDF_One(rs.getString(0)));
                }
            }else{
                PreparedStatement pst3 = conAll.prepareStatement("{call SPAPP_SEALLPESO_DETCOM_OBT (?,?)}");
                pst3.setString(1, idAllCompra);
                pst3.setString(2, PO);
                ResultSet rs3 = pst3.executeQuery();
                while (rs3.next()) {
                    dataListaPesoModifica.add(new ItemPesoPrintPDF_One(rs3.getString("PESO_MODIFICADO")));
                }
            }
        }catch (Exception e){
        }
    }
    public void ListarPesoReal(String PO){
        try {
            String valor=null;
            dataListaPesoReal.clear();
            Cursor rs = myDB.ListaPrevistaPesos(idAllCompra,PO);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    valor = rs.getString(0);
                    dataListaPesoReal.add(new ItemPesoPrintPDF_One(rs.getString(0)));
                }
            }else{
                PreparedStatement pst3 = conAll.prepareStatement("{call SPAPP_SEALLPESO_DETCOM_OBT_PESO_REAL (?,?)}");
                pst3.setString(1, idAllCompra);
                pst3.setString(2, PO);
                ResultSet rs3 = pst3.executeQuery();
                while (rs3.next()) {
                    dataListaPesoReal.add(new ItemPesoPrintPDF_One(rs3.getString("PESO_REAL")));
                }
            }
        }catch (Exception e){
        }
    }
    public void ListarUbicacion(String id){
        try {
            Cursor rs = myDB.selectMostrarAlmacen(id);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()) {
                    txtAlmacen.setText(rs.getString(0));
                    txtAlmacenGac.setText(rs.getString(0));
                }
            }else{
                PreparedStatement pst3 = conAll.prepareStatement("{call SPAPP_SEBYIDCOM_TBALMA (?)}");
                pst3.setString(1, id);
                ResultSet rs3 = pst3.executeQuery();
                if(rs3.next()) {
                    txtAlmacen.setText(rs3.getString("DESCRIPCION"));
                    txtAlmacenGac.setText(rs3.getString("DESCRIPCION"));
                }
            }
        }catch (Exception e){
        }
    }
    public void PrintPDF(String ruta){
        /*
        File file = new File(ruta);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = Intent.createChooser(target,"Abrir Archivo");
        try{
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(this, "No tiene aplicación para leer PDF!", Toast.LENGTH_SHORT).show();
        }

         */
        FileInputStream fileInputStream =null;
        PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
        try{
            //String path = getClass().getClassLoader().toString();
            //String ru = new File(path +"/files/" + file_name).getAbsolutePath();
            //fileInputStream = openFileInput(file_name);
            //InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            File pa = getApplication().getFileStreamPath(file_name);
            //Toast.makeText(getApplication(),pa.getAbsolutePath(),Toast.LENGTH_LONG).show();
            PrintDocumentAdapter pdfDocumentAdapter = new PdfDocumentAdapter(PrintDocumentPDF.this,pa.getAbsolutePath());
            PrintAttributes attrib = new PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE)
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    . build();
            printManager.print("Documento Wiraccocha",pdfDocumentAdapter,attrib);
        }catch (Exception e){
            Toast.makeText(getApplication(),"no se pudo imprimir "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void SEIDPROV(String idCompra){
        try {
            Cursor rs = myDB.selectIdProveedorCompra(idCompra);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    idProv = rs.getString(3);
                    //Toast.makeText(getApplication(),idProv,Toast.LENGTH_SHORT).show();
                }
            }else
                Toast.makeText(getApplication(),"ERROR AL MOSTRAR PROVEEDOR",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"ID PROVEEDOR NO EXISTE!!! "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void SEIDPROVPRINT(String idProveedorI, String idCompraI){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
            String valor = null;
            Cursor rs = myDB.selectMostrarEncabezadoPDF(idCompraI,idProveedorI);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    numSer.setText(rs.getString(1) +"-"+ rs.getString(2));
                    txt_dni.setText(rs.getString(3));
                    valor = rs.getString(3);
                    productor.setText(rs.getString(4));
                    txtnombrGAC.setText(rs.getString(4));
                    comunidad.setText(rs.getString(9));
                    distrito.setText(rs.getString(10));
                    //parcela.setText(rs.getString(11));
                    //tipo.setText(rs.getString(7));
                    placa.setText(rs.getString(8));
                    fecha.setText(rs.getString(12));
                    String nco = rs.getString(5);
                    quienCobra = rs.getString(6);
                    tipoRegistroCompra = rs.getString(20);
                    telefono = rs.getString(21);
                    obsCompraGeneral = rs.getString(22);
                    if(nco != null){
                        nomProdFirma.setText(nco);
                    }else{
                        nomProdFirma.setText(rs.getString(4));
                        dniProdFirma.setText(rs.getString(3));
                    }
                    nomUserFirma.setText(rs.getString(13));
                    dniUserFirma.setText(rs.getString(14));
                    txtTotal.setText((rs.getString(15)));
                    txtSoliAdelanto.setText((rs.getString(16)));
                    //txt_loteProveedor.setText((rs.getString(23)));
                    txt_guiaRemision.setText((rs.getString(24)));
                    if(rs.getString(12) != null){
                        Date date = dateFormat.parse(String.valueOf(rs.getString(12)));
                        YearCapania = dateFormatYear.format(date);
                        txtCampaniaYear.setText("CAMPAÑA " +YearCapania);
                    }
                }
                if(valor == null){
                    PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEIDPROVPRINT (?,?)}");
                    pst1.setString(1,idProveedorI);
                    pst1.setString(2,idCompraI);
                    ResultSet result = pst1.executeQuery();
                    if (result.next()){
                        numSer.setText(result.getString("SERIENUM"));
                        txt_dni.setText(result.getString("DNI_RUC"));
                        productor.setText(result.getString("NOMBRE_COMPLETO"));
                        txtnombrGAC.setText(result.getString("NOMBRE_COMPLETO"));
                        comunidad.setText(result.getString("COMUNIDAD"));
                        distrito.setText(result.getString("DISTRITO"));
                        //parcela.setText(result.getString("PARCELA"));
                        //tipo.setText(result.getString("TIPO"));
                        placa.setText(result.getString("PLACA"));
                        fecha.setText(result.getString("FECHA"));
                        String nco = result.getString("PERSONA_QUIENENTREGA");
                        quienCobra = result.getString("PERSONA_ACOBRAR");
                        tipoRegistroCompra = result.getString("TIPOREGISTRO");
                        telefono= result.getString("TELEFONO");
                        obsCompraGeneral=result.getString("OBSERVACION_COMPRA");
                        YearCapania=result.getString("YEAR");
                        txtCampaniaYear.setText("CAMPAÑA " + YearCapania);
                        //txt_loteProveedor.setText(result.getString("LOTE_PROVEEDOR"));
                        txt_guiaRemision.setText(result.getString("GUIA_REMISION"));
                        if(nco != null){
                            nomProdFirma.setText(nco);
                        }else{
                            nomProdFirma.setText(result.getString("NOMBRE_COMPLETO"));
                            dniProdFirma.setText(result.getString("DNI_RUC"));
                        }
                        nomUserFirma.setText(result.getString("NOMBREUSER"));
                        dniUserFirma.setText(result.getString("DNIUSER"));
                        txtTotal.setText((result.getString("MONTO_TOTAL")));
                        txtSoliAdelanto.setText((result.getString("ADELANTO")));
                        String co = comunidad.getText().toString();

                        if(co.isEmpty()){
                            comunidad.setText(result.getString("COMU"));
                            distrito.setText(result.getString("DISTRI"));
                            //parcela.setText(result.getString("PARCE"));
                        }
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"ERROR / "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void selectEstiva(String id){
        try {
            StringBuffer stringBuffer = new StringBuffer();
            Cursor rs = myDB.selectMostrarEstivaPDF(id);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    stringBuffer.append("- "+rs.getString(0)+"\n");
                }
                txtEstiva.setText(stringBuffer.toString());
            }else{
                PreparedStatement pst = conAll.prepareStatement("{CALL SPAPP_SEIDCOM_TBESTIVA (?)}");
                pst.setString(1,id);
                ResultSet rsCON = pst.executeQuery();
                while (rsCON.next()){
                    stringBuffer.append("- "+rsCON.getString("NOMBRE")+"\n");
                }
                txtEstiva.setText(stringBuffer.toString());
            }
        }catch (Exception e) {
        }
    }
}
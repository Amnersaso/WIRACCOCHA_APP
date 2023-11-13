package w.kipu.kipu.formtocompra.ConvertFile_Byte;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class EnviarJsonAPI {
    public String enviarJSON(byte[] archivo,String NombreArchivo){
        String SOAP_ACTION="http://tempuri.org/DataJson";
        String METODO_NAME="DataJson";
        String NAMESPACE="http://tempuri.org/";
        String URL="http://190.117.59.216:1234/Inicio_app.asmx";
        SoapPrimitive returnPrimitive;
        try{
            SoapObject request = new SoapObject(NAMESPACE,METODO_NAME);

            request.addProperty("nombreArchivo",NombreArchivo);
            //String encoded = Base64.encode(archivo);
            PropertyInfo info = new PropertyInfo();
            info.setName("archivo");
            info.setValue(archivo);
            info.setType(archivo.getClass());
            request.addProperty(info);
            MarshalBase64 marshalBase64 = new MarshalBase64();
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);
            envelope.dotNet=true;
            envelope.implicitTypes=true;
            envelope.setAddAdornments(false);
            envelope.setOutputSoapObject(request);
            marshalBase64.register(envelope);

            HttpTransportSE transportSE = new HttpTransportSE(URL);
            transportSE.call(SOAP_ACTION,envelope);
            returnPrimitive =(SoapPrimitive) envelope.getResponse();
            String a = (String) returnPrimitive.getValue();
            return a;
        }catch (Exception e){
            return "falla de envio: "+ e.getMessage();
        }
    }
}

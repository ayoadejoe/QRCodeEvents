package qrcode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import interfaces.CreateCodeInterface;

public class CreateCode {

	private CreateCodeInterface createCodeInterface;
	private int z=0;
	public void createTheCode(String server, String name){

		// The data that the QR code will contain
		String data = server;
		
		// The path where the image will get saved
		String path = name;
		
		// Encoding charset
		String charset = "UTF-8";
		
		Map<EncodeHintType, ErrorCorrectionLevel> hashMap
		 = new HashMap<EncodeHintType,
		               ErrorCorrectionLevel>();
		
		hashMap.put(EncodeHintType.ERROR_CORRECTION,
		         ErrorCorrectionLevel.L);
		
		try {
			createQR(data, path, charset, hashMap, 200, 200);
			createCodeInterface.codeGenerationCompleted(true);
			System.out.println("QR Code Generated!!! ");
			z=0;
		}catch( NotFoundException | IOException e) {
			e.printStackTrace();
			createCodeInterface.codeGenerationCompleted(false);
		}catch(WriterException q) {
			System.out.println(q.getMessage()+" z="+z);
			
			if(z>1) {	//means already passed through loop once, tried to rectify write error but not working
			path = path.replace('"', '_');
			path = path.replace('~', '_');
			path = path.replace('|', '_');
			path = path.replace('*', '_');
			
			path = path.replace('&', '_');
			path = path.replace('$', '_');
			path = path.replace('%', '_');
			path = path.replace('{', '_');
			path = path.replace('}', '_');
			
			path = path.replace('?', '_');
			path = path.replace('>', '_');
			path = path.replace('<', '_');
			path = path.replace('/', '_');
			
			createTheCode(server, path);
			}else createCodeInterface.codeGenerationCompleted(false);	//register error and continue
		}
		}
	

	// Function to create the QR code
    public void createQR(String data, String path,
                                String charset, Map hashMap,
                                int height, int width)
        throws WriterException, IOException, NotFoundException {
    	
    	BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);
     
            MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));
    }


	public void setCodeGenerationCompleted(CreateCodeInterface createCodeInterface) {
		this.createCodeInterface = createCodeInterface;
	}
   
}

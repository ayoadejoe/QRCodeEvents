package qrcode;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import interfaces.CreateCodeInterface;

public class CreateCodeLogo {
	private final String DIR = "qrcodes/";
    private final String ext = ".png";
    private final String LOGO = "chipcode_underlay2"
    		+ ".png";
    private final String CONTENT = "www.ariseplay.com";
    private final int WIDTH = 300;
    private final int HEIGHT = 300;
    private CreateCodeInterface createCodeInterface;
	private int z=0;
    
	public void generate(String linkaddress, String name) {
        // Create new configuration that specifies the error correction
		
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
           
        	initDirectory(DIR);
        	
            // Create a qr code with the url as content and a size of WxH px
            bitMatrix = writer.encode(linkaddress, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

            // Load QR image
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, getMatrixConfig());

            // Load logo image
            BufferedImage overly = getOverly(LOGO);

            // Calculate the delta height and width between QR code and logo
            int deltaHeight = qrImage.getHeight() - overly.getHeight();
            int deltaWidth = qrImage.getWidth() - overly.getWidth();

            // Initialize combined image
            BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();

            // Write QR code to new image at position 0/0
            g.drawImage(qrImage, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            // Write logo into combine image at position (deltaWidth / 2) and
            // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
            // the same space for the logo to be centered
            g.drawImage(overly, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

            // Write combined image as PNG to OutputStream
            ImageIO.write(combined, "png", os);
            // Store Image
            Files.copy( new ByteArrayInputStream(os.toByteArray()), Paths.get(DIR+name +ext), StandardCopyOption.REPLACE_EXISTING);
            createCodeInterface.codeGenerationCompleted(true);
			z=0;
        } catch (WriterException q) {
        	System.out.println(q.getMessage()+" z="+z);
			
			if(z<=1) {	//means already passed through loop once, tried to rectify write error but not working
			name = name.replace('"', '_');
			name = name.replace('~', '_');
			name = name.replace('|', '_');
			name = name.replace('*', '_');
			
			name = name.replace('&', '_');
			name = name.replace('$', '_');
			name = name.replace('%', '_');
			name = name.replace('{', '_');
			name = name.replace('}', '_');
			
			name = name.replace('?', '_');
			name = name.replace('>', '_');
			name = name.replace('<', '_');
			name = name.replace('/', '_');
			System.out.println("new name:"+name);
			generate(linkaddress, name); ++z;
			}else createCodeInterface.codeGenerationCompleted(false);	//register error and continue
        } catch (IOException e) {
        	createCodeInterface.codeGenerationCompleted(false);
            e.printStackTrace();
        }catch(InvalidPathException l) {
        	System.out.println(l.getMessage()+" z="+z);
			
			if(z<=1) {	//means already passed through loop once, tried to rectify write error but not working
			name = name.replace('"', '_');
			name = name.replace('~', '_');
			name = name.replace('|', '_');
			name = name.replace('*', '_');
			
			name = name.replace('&', '_');
			name = name.replace('$', '_');
			name = name.replace('%', '_');
			name = name.replace('{', '_');
			name = name.replace('}', '_');
			
			name = name.replace('?', '_');
			name = name.replace('>', '_');
			name = name.replace('<', '_');
			name = name.replace('/', '_');
			System.out.println("new name:"+name);
			generate(linkaddress, name); ++z;
			}else createCodeInterface.codeGenerationCompleted(false);	//register error and continue
        }
    }
	
	private BufferedImage getOverly(String LOGO) throws IOException {
        return ImageIO.read(new File(LOGO));
    }

	private MatrixToImageConfig getMatrixConfig() {
        // ARGB Colors
        // Check Colors ENUM
        return new MatrixToImageConfig(CreateCodeLogo.Colors.BLACK.getArgb(), CreateCodeLogo.Colors.WHITE.getArgb());
    }

    private String generateRandoTitle(Random random, int length) {
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public enum Colors {

        BLUE(0xFF40BAD0),
        RED(0xFFE91C43),
        PURPLE(0xFF8A4F9E),
        ORANGE(0xFFF4B13D),
        WHITE(0xFFFFFFFF),
        BLACK(0xFF000000),
        GREEN(0xFF008000),
    	AFC(0xFF0C2C6C);
    	

        private final int argb;

        Colors(final int argb){
            this.argb = argb;
        }

        public int getArgb(){
            return argb;
        }
    }
    
    private void initDirectory(String DIR) throws IOException {
        Files.createDirectories(Paths.get(DIR));
    }

    public void setCodeGenerationCompleted(CreateCodeInterface createCodeInterface) {
		this.createCodeInterface = createCodeInterface;
	}

}

package gui;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.json.JSONException;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import database.GetAddressList;
import database.JSonConverter;
import qrcode.Settings;

public class Foundation {
	private static GetAddressList getAddressList = new GetAddressList();
	private static Settings settings = new Settings();
	private static JSonConverter jSonConverter = new JSonConverter();
	private static Map<String, Object> addressesKey = new TreeMap<>();
	private static List<String> unallocatedIDs = new ArrayList<>();
	private static String ID;
	
    public static void main(String[] args) throws NotFoundException, WriterException, IOException {
        SwingUtilities.invokeLater(()-> {
        	try {
    	        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	            if ("Nimbus".equals(info.getName())) {
    	                UIManager.setLookAndFeel(info.getClassName());
    	                break;
    	            }
    	        }
    	    } catch (Exception e) {
    	        try {
    	            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    	        } catch (Exception ex) {
    	        }
    	    }
        	
        	getAddressList.setAddressInterface(a->{
    			addressesKey = jSonConverter.iDs(a);
    			unallocatedIDs = jSonConverter.getUnallocatedIDs();
    			//System.out.println(unallocatedIDs);
    			if(addressesKey.size()>0) {
	    			ID = unallocatedIDs.get(0);
	    			MainGui mainGui = new MainGui(jSonConverter, ID, addressesKey);
    			}
    			//
    			//
    		});
    		
    		try {
    			getAddressList.readJsonFromUrl(settings.getServerAddress());
    		} catch (JSONException | IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			if(e.getClass() == ConnectException.class) {
    				JOptionPane.showMessageDialog(null, "Connection timed out: Check your network");
    			}
    		}
        });
    }
}

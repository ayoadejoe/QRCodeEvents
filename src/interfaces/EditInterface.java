package interfaces;

import javax.swing.JComponent;

public interface EditInterface {
	public int k=0;
	public String m="t";
	public void editParticipant(JComponent c);
	
	default String araldite() {
		System.out.println(k);
		return m;
	}
}

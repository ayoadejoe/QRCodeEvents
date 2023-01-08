package gui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.JOptionPane;

import java.awt.Color;

public class EditRegistration extends RegistrationPanel{

	public EditRegistration(String ID, Object arrayList) {
		super(ID);
		List<Object> params = (List<Object>) arrayList;
		
		txtFirstnameLastname.setText(params.get(1)+" "+params.get(2));
		txtCompanyEmail.setText(params.get(3)+"");
		txtMobileNo.setText(params.get(4)+"");
		txtJobTitle.setText(params.get(5)+"");
		txtrCompanyName.setText(params.get(6)+"");
		comboIndustry.setSelectedItem(params.get(7));
		txtOtherIndustry.setText(params.get(8)+"");
		comboCountry.setSelectedItem(params.get(9));
		comboAttendance.setSelectedItem(params.get(10));
		comboPassType.setSelectedItem(params.get(11));
		
		txtFirstnameLastname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtFirstnameLastname.setBackground(Color.ORANGE);
				txtFirstnameLastname.setText(params.get(1)+" "+params.get(2));
			}
		});
		
		txtCompanyEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtCompanyEmail.setBackground(Color.ORANGE);
				txtCompanyEmail.setText(params.get(3)+"");
			}
		});
		
		txtMobileNo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtMobileNo.setBackground(Color.ORANGE);
				txtMobileNo.setText(params.get(4)+"");
			}
		});
		
		txtJobTitle.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtJobTitle.setBackground(Color.ORANGE);
				txtJobTitle.setText(params.get(5)+"");
			}
		});
		
		txtrCompanyName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtrCompanyName.setBackground(Color.ORANGE);
				txtrCompanyName.setText(params.get(6)+"");
			}
		});
		
		txtOtherIndustry.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtOtherIndustry.setBackground(Color.ORANGE);
				txtOtherIndustry.setText(params.get(8)+"");
			}
		});
	}
}

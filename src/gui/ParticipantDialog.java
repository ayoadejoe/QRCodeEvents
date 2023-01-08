package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;
import java.awt.Component;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ParticipantDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	protected final String DIR = "qrcodes/";
	private String title;
	public ParticipantDialog(Object object) {
			List<Object> parts = (List<Object>) object;
			title = parts.get(1) + " "+parts.get(2);
			String name = parts.get(0)+"_"+parts.get(1) + "_"+parts.get(2);
			String filename = DIR+name+".png";
			System.out.println("File:"+filename);
		
			setIconImage(Toolkit.getDefaultToolkit().getImage("255617_Adetunji_Ayoade.png"));
			setTitle(title.toUpperCase());
			setAlwaysOnTop(true);
			setBounds(100, 100, 400, 550);
			setLocationRelativeTo(getParent());
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBackground(new Color(204, 255, 204));
			contentPanel.setBorder(new LineBorder(new Color(102, 153, 102), 2));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout(0, 0));
			{
				JLabel qrCode = new JLabel("");
				qrCode.setIcon(new ImageIcon(filename));
				qrCode.setHorizontalAlignment(SwingConstants.CENTER);
				contentPanel.add(qrCode, BorderLayout.CENTER);
			}
			{
				JPanel detailsPanel = new JPanel();
				detailsPanel.setBackground(new Color(204, 255, 204));
				contentPanel.add(detailsPanel, BorderLayout.SOUTH);
				detailsPanel.setLayout(new GridLayout(6, 1, 0, 0));
				{
					JLabel lblNewLabel_2 = new JLabel("");
					detailsPanel.add(lblNewLabel_2);
				}
				{
					JLabel nameLabel = new JLabel(title.toUpperCase());
					nameLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
					nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
					detailsPanel.add(nameLabel);
				}
				{
					JLabel jobTitleLabel = new JLabel(parts.get(5)+"");
					jobTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
					jobTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
					detailsPanel.add(jobTitleLabel);
				}
				{
					JLabel companyLabel = new JLabel(parts.get(6)+"");
					companyLabel.setFont(new Font("Arial", Font.BOLD, 13));
					companyLabel.setHorizontalAlignment(SwingConstants.CENTER);
					detailsPanel.add(companyLabel);
				}
				{
					Object i = parts.get(7);
					String industry=i+"";
					if(i==null)industry = parts.get(8)+"";
					JLabel industryLabel = new JLabel(industry);
					industryLabel.setForeground(new Color(0, 102, 51));
					industryLabel.setFont(new Font("Arial", Font.BOLD, 13));
					industryLabel.setHorizontalAlignment(SwingConstants.CENTER);
					detailsPanel.add(industryLabel);
				}
				{
					JLabel lblNewLabel_1 = new JLabel("\u00A9chipcode [2022]");
					lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
					lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
					detailsPanel.add(lblNewLabel_1);
				}
			}
			{
				JPanel panel = new JPanel();
				panel.setBackground(new Color(204, 255, 204));
				contentPanel.add(panel, BorderLayout.NORTH);
				panel.setLayout(new GridLayout(1, 1, 0, 0));
				{
					JLabel lblNewLabel = new JLabel((parts.get(11)+"").toUpperCase());
					panel.add(lblNewLabel);
					lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
					lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				}
			}
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setBackground(new Color(204, 255, 204));
				buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("Print");
					okButton.addActionListener(e->{
						okButton.setVisible(false);
						getContentPane().remove(buttonPane);
						printComponenent(getContentPane());
						
					});
					okButton.setForeground(new Color(0, 153, 51));
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
			}
		
	}
	
	public void printComponenent(Component component){
		  PrinterJob pj = PrinterJob.getPrinterJob();
		  pj.setJobName(" Print Component ");

		  pj.setPrintable (new Printable() {    
		    public int print(Graphics pg, PageFormat pf, int pageNum){
		      if (pageNum > 0){
		      return Printable.NO_SUCH_PAGE;
		      }

		      Graphics2D g2 = (Graphics2D) pg;
		      g2.translate(pf.getImageableX() + pf.getImageableWidth() / 2 - component.getWidth() / 2, pf.getImageableY() + pf.getImageableHeight() / 2 - component.getHeight() / 2);
		      component.paint(g2);
		      return Printable.PAGE_EXISTS;
		    }
		  });
		  if (pj.printDialog() == false)
		  return;

		  try {
		        pj.print();
		  } catch (PrinterException ex) {
		        // handle exception
		  }
		}
	
}

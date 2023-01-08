package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import database.JSonConverter;
import interfaces.EditInterface;

public class EditParticipant{
  protected final String[] columnNames = { "ID", "FirstName", "LastName", "Email", "MobileNumber", "JobTitle", 
		  "Company", "Industry", "OtherIndustry", "Country", "AttendingInPerson", "PassType"};
  protected Object[][] data ;

  protected DefaultTableModel model;
  protected JTable table;
  protected TableRowSorter<TableModel> rowSorter;
  protected final JTextField searchFilter = new JTextField();
  protected final JButton open = new JButton("open");
  protected JSonConverter jSonConverter;
  protected Map<String, Object> allocated;
  protected EditInterface editInterface;
  protected boolean sortUsed = false;
  protected JPopupMenu popupMenu = new JPopupMenu();
  
  JMenuItem mntmView = new JMenuItem("View Participant");
  JMenuItem mntmNewMenuItem = new JMenuItem("Edit");
  JMenuItem mntmNewMenuSearch = new JMenuItem("Edit Searched");
  
  public EditParticipant(JSonConverter jSonConverter) {
	  this.jSonConverter = jSonConverter;
	  this.data = jSonConverter.getTableAllocated();
	  this.allocated = jSonConverter.getAllocated();
	  System.out.println("allocated:"+allocated.size());
  }


public JComponent makeUI() {
	model = new DefaultTableModel(data, columnNames) {
	    @Override public Class<?> getColumnClass(int column) {
	    	Object val = getValueAt(0, column);
	    	if(val == null) val=new String();
	      return val.getClass();
	    }
	  };
	
	table = new JTable(model);
	rowSorter = new TableRowSorter<>(model);
    table.setRowSorter(rowSorter);
    table.setRowHeight(30);
    
	mntmNewMenuItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println(table.getSelectedRow()+","+ table.getSelectedColumn());
			Object clickedID = table.getModel().getValueAt(table.getSelectedRow(), 0);
			if(clickedID !=null) {
				System.out.println("allocated:"+allocated.get(clickedID));
				EditRegistration  editRegistration = 
						new EditRegistration(clickedID.toString(), allocated.get(clickedID));
				editInterface.editParticipant(editRegistration);
			}
		}
	});
	popupMenu.add(mntmNewMenuItem);
	
	
	table.addMouseListener(new MouseAdapter() {
		@Override
		 public void mouseClicked(MouseEvent evt) {
		    int row = table.rowAtPoint(evt.getPoint());
		    int col = table.columnAtPoint(evt.getPoint());
		    System.out.println(sortUsed+"> "+row+","+col);
		    if(!sortUsed) {
			    if (row >= 0 && col >= 0) {
			    	Object clicked1 = table.getModel().getValueAt(row, 1);
			    	Object clicked2 = table.getModel().getValueAt(row, 2);
			    	if(clicked1 != null && clicked2 != null) {
			    		mntmView.setText("View participant-> "+clicked1+" "+clicked2);
			    		mntmNewMenuItem.setText("Edit participant-> "+clicked1+" "+clicked2);
			    	}
			    	showMenu(evt);
			    }
		    }else {
		    	int newrow = rowSorter.convertRowIndexToModel(row);
		    	System.out.println(model.getValueAt(newrow, col));
		    	if (newrow >= 0 && col >= 0) {
			    	Object clicked1 = table.getModel().getValueAt(newrow, 1);
			    	Object clicked2 = table.getModel().getValueAt(newrow, 2);
			    	if(clicked1 != null && clicked2 != null) {
			    		mntmNewMenuSearch.setText("Edit participant-> "+clicked1+" "+clicked2);
			    		mntmView.setText("View participant-> "+clicked1+" "+clicked2);
			    	}
			    	showMenu(evt);
			    }
		    }
		 }
		
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {
				showMenu(e);
			}
		}
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				showMenu(e);
			}
		}
		private void showMenu(MouseEvent e) {
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
		});
	
	

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel("Search:"), BorderLayout.WEST);
    panel.add(searchFilter, BorderLayout.CENTER);

    searchFilter.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        String text = searchFilter.getText();
        System.out.println(text);
        if (text.trim().length() == 0) {
          rowSorter.setRowFilter(null);
          sortUsed = false;
        } else {
          rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
          sortUsed = true;
        }
        System.out.println("sort:"+sortUsed);
        table.repaint();
        table.revalidate();
        
        mntmNewMenuSearch.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			int newrow = rowSorter.convertRowIndexToModel(table.getSelectedRow());
    			Object clickedID = table.getModel().getValueAt(newrow, 0);
    			if(clickedID !=null) {
    				System.out.println("allocated:"+allocated.get(clickedID));
    				EditRegistration  editRegistration = 
    						new EditRegistration(clickedID.toString(), allocated.get(clickedID));
    				editInterface.editParticipant(editRegistration);
    			}
    		}
    	});
    	popupMenu.add(mntmNewMenuSearch);
      }
      @Override
      public void removeUpdate(DocumentEvent e) {
        String text = searchFilter.getText();
        if (text.trim().length() == 0) {
          rowSorter.setRowFilter(null);
        } else {
          rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
      }
      @Override
      public void changedUpdate(DocumentEvent e) {
        //not needed: throw new UnsupportedOperationException("Not supported yet.");
      }
    });

    JPanel p = new JPanel(new BorderLayout());
    p.add(new JScrollPane(table));
    p.add(panel, BorderLayout.SOUTH);
    p.setVisible(true);
    return p;
  }


	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	public void setEditParticipant(EditInterface editInterface) {
		this.editInterface = editInterface;
	}
}
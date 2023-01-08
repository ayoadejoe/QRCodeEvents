package gui;

import database.JSonConverter;

public class ViewParticipant extends EditParticipant{

	
	public ViewParticipant(JSonConverter jSonConverter) {
		super(jSonConverter);
		mntmNewMenuItem.setVisible(false);
		mntmNewMenuSearch.setVisible(false);
		popupMenu.remove(mntmNewMenuItem);
		popupMenu.remove(mntmNewMenuSearch);
		popupMenu.add(mntmView);
		popupMenu.revalidate();
		
	
		mntmView.addActionListener(d->{
			System.out.println(table.getSelectedRow()+","+ table.getSelectedColumn());
			Object clickedID = table.getModel().getValueAt(table.getSelectedRow(), 0);
			if(clickedID !=null) {
				System.out.println("allocated:"+allocated.get(clickedID));
				ParticipantDialog participant = new ParticipantDialog(allocated.get(clickedID));
				participant.setVisible(true);
			}
		});

	}


}

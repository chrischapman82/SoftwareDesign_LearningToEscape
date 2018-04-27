package strategies;

import java.util.ArrayList;

import automail.Clock;
import automail.MailItem;
import automail.StorageTube;
import exceptions.TubeFullException;

public class UpperPool implements IMailPool {

	private ArrayList<MailItem> mailItems; 
	
	public UpperPool() {
		mailItems = new ArrayList<MailItem>();
	}
	
	@Override
	public void addToPool(MailItem mailItem) {
		System.out.printf("T: %3d > new addToPool [%s]%n", Clock.Time(), mailItem.toString());
		mailItems.add(mailItem);
	}

	@Override
	public void fillStorageTube(StorageTube tube, boolean strong) {
		while(!tube.isEmpty()) {
			addToPool(tube.pop());
		}
		while(!tube.isFull() && !mailItems.isEmpty()) {
			try {
				tube.addItem(mailItems.remove(0));
			} catch (TubeFullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

package strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import automail.Clock;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.StorageTube;
import exceptions.TubeFullException;

public class UpperPool implements IMailPool {

	private PriorityQueue<MailItem> mailItems; 
	
	public UpperPool() {
		mailItems = new PriorityQueue<MailItem>(100, new Comparator<MailItem>() {
			
			public int compare(MailItem item1, MailItem item2) {	
				
				return priority(item2) - priority(item1);
					
			}
		});
	}
	
	private int priority(MailItem m) {
		return (m instanceof PriorityMailItem) ? ((PriorityMailItem) m).getPriorityLevel() : 0;
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
				tube.addItem(mailItems.poll());
			} catch (TubeFullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

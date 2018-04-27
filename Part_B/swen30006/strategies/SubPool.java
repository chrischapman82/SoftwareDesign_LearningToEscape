package strategies;

import java.util.*;

import automail.Clock;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.StorageTube;
import exceptions.TubeFullException;

public class SubPool implements IMailPool {
	
	private LinkedList<MailItem> mailItems; 
	
	public SubPool() {
		mailItems = new LinkedList<MailItem>();
	}
	
	private int priority(MailItem m) {
		return (m instanceof PriorityMailItem) ? ((PriorityMailItem) m).getPriorityLevel() : 0;
	}
	
	@Override
	public void addToPool(MailItem mailItem) {
		System.out.printf("T: %3d > new addToPool [%s]%n", Clock.Time(), mailItem.toString());
		if (mailItem instanceof PriorityMailItem) {  // Add in priority order
			int priority = ((PriorityMailItem) mailItem).getPriorityLevel();
			ListIterator<MailItem> i = mailItems.listIterator();
			while (i.hasNext()) {
				if (priority(i.next()) < priority) {
					i.previous();
					i.add(mailItem);
					return; // Added it - done
				}
			}
		}
		mailItems.addLast(mailItem);
	}

	@Override
	public void fillStorageTube(StorageTube tube, boolean strong) {
		Queue<MailItem> q = mailItems;
		try{
			while(!tube.isFull() && !q.isEmpty()) {
				tube.addItem(q.remove());  // Could group/order by floor taking priority into account - but already better than simple
			}
		}
		catch(TubeFullException e){
			e.printStackTrace();
		}
	}

}

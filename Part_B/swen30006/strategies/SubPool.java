package strategies;

import java.util.*;

import automail.Clock;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.StorageTube;
import exceptions.TubeFullException;

public class SubPool implements IMailPool {
	
	private LinkedList<MailItem> mailItems;		// A list of the mail in the pool
	
	public SubPool() {
		mailItems = new LinkedList<MailItem>();	// Init mailItems
	}
	
	
	/* Helper function.
	 * Takes a mailItem and translates its priority into an int
	 * 
	 * @return If a priority item, returns the priority value. If not, returns 0. 
	 */
	private int priority(MailItem m) {
		return (m instanceof PriorityMailItem) ? ((PriorityMailItem) m).getPriorityLevel() : 0;
	}
	
	
	/*
	 * Adds a mailItem to the pool
	 * Utilises the approach used by the provided version.  
	 */
	@Override
	public void addToPool(MailItem mailItem) {
		
		// This doesn't attempt to put the re-add items back in time order but there will be relatively few of them,
		// from the strong robot only, and only when it is recalled with undelivered items.
		// Check whether mailItem is for strong robot
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

	
	/*
	 * Fills the storage tube, tube, with mail items from the current pool
	 * @param tube - The tube that is to be filled
	 */
	@Override
	public void fillStorageTube(StorageTube tube) {
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

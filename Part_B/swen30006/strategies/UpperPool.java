package strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import automail.Clock;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.StorageTube;
import exceptions.TubeFullException;

public class UpperPool implements IMailPool {

private LinkedList<MailItem> mailItems; 
	
	public UpperPool() {
			
		mailItems = new LinkedList<MailItem>();
	}
	
	@Override
	public void addToPool(MailItem mailItem) {
		System.out.printf("T: %3d > new addToPool [%s]%n", Clock.Time(), mailItem.toString());
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

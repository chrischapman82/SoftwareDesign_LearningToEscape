package strategies;

import java.util.*;

import automail.Clock;
import automail.MailItem;
import automail.PriorityMailItem;
import automail.StorageTube;
import exceptions.TubeFullException;

public class LowerPool implements IMailPool {
	
	private PriorityQueue<MailItem> mailItems; 
	
	public LowerPool() {
			
		mailItems = new PriorityQueue<MailItem>(100, new Comparator<MailItem>() {
			
			public int compare(MailItem item1, MailItem item2) {	
				
				// we must explicitly test different type cases due to casting
				// if one is a priority and the other is a normal mail item, order accordingly 
				if((item2 instanceof PriorityMailItem) && !(item1 instanceof PriorityMailItem)){
					return 1;
				}
				else if(!(item2 instanceof PriorityMailItem) && (item1 instanceof PriorityMailItem)){
					return -1;
				}
				
				// if both are priority items, compare priority levels and then arrival time
				else if((item2 instanceof PriorityMailItem) && (item1 instanceof PriorityMailItem)) {
					if(((PriorityMailItem)item2).getPriorityLevel() == ((PriorityMailItem)item1).getPriorityLevel()) {
						return item1.getArrivalTime() - item2.getArrivalTime();
					}
					else {
						return ((PriorityMailItem)item2).getPriorityLevel() - ((PriorityMailItem)item1).getPriorityLevel();
					}
				}
				else {
					// else if both are normal items, compare by arrival time
					return item1.getArrivalTime() - item2.getArrivalTime();	
				}
					
			}
		});
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

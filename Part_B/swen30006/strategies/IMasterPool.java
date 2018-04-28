package strategies;

import automail.*;


public interface IMasterPool {
	
	// passes off mail to a mail pool
	public void distributeMail(MailItem mailItem);
	
}

package strategies;

import automail.*;

/* IMasterPool controls and regulates the MailPool.
 * When new mail is generated, distributes mail to the different IMailPools.
 * For a robot, delegates them a mailPool that a robot will be accessing and putting mail into.
 */
public interface IMasterPool {
	
<<<<<<< HEAD
	/* Distributes mailItem to one of its mail pools based on mail attributes.
	 */
	void distributeMail(MailItem m);
=======
	// passes off mail to a mail pool
	public void distributeMail(MailItem mailItem);
>>>>>>> raymond
	
}

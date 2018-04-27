package strategies;
import java.util.ArrayList;

import automail.*;


public interface IMasterPool {
	
	// passes off mail to a mail pool
	void distributeMail(MailItem m);
	
}

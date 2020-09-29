package come.lyes.controllers;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

public class Controller {

	
	public boolean handleChanges(DocumentEvent e) throws BadLocationException {

		if(e.getDocument().getText(0, 1).equals(Integer.toString(5))) {
			return true;
		}else {
			return false;
		}
	}
}

package helper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

public class ViewHelper extends JFrame {

	static final long serialVersionUID = 1L;

	private ViewHelper() {}
	
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
	public static LocalTime extrairHoraDo(JFormattedTextField campo) {
		try {
			return LocalTime.from(dtf.parse(campo.getText()));
		}catch (DateTimeParseException e) {
			return null;
		}
	}	
}



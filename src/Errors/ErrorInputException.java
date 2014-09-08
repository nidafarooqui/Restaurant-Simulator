package Errors;

import javax.swing.JOptionPane;

public class ErrorInputException extends Exception{
	public ErrorInputException(String s){
		JOptionPane.showMessageDialog(null,s,"Error",JOptionPane.WARNING_MESSAGE);
	}
}

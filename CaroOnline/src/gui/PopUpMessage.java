package gui;

import javax.swing.JOptionPane;

public class PopUpMessage {
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Th�ng b�o: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}

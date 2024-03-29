package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import dto.Room;
 
/**
 * @version 1.0 11/09/98
 */
public class ButtonEditor extends DefaultCellEditor {
  protected JButton button;
  private String    label;
  private boolean   isPushed;
  Menu menu;
 
  public ButtonEditor(JCheckBox checkBox, Menu _menu) {
    super(checkBox);
    menu = _menu;
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }
 
  public Component getTableCellEditorComponent(JTable table,Object value,
                   boolean isSelected, int row, int column) {
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else{
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
    label = (value ==null) ? "" : value.toString();
    button.setText( label );
    isPushed = true;
    return button;
  }
 
  public Object getCellEditorValue() {
    if (isPushed)  {
    	//JOptionPane.showMessageDialog(button ,label.substring(9) + ": Ouch!");
    	//System.out.println(Integer.parseInt(label.trim().substring(9))+"");
    	menu.goToRoom_Player(Integer.parseInt(label.trim().substring(9)));
    }
    isPushed = false;
    return new String( label ) ;
  }
   
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }
 
  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}
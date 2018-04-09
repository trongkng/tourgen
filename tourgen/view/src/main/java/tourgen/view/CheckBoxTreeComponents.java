package tourgen.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import tourgen.controller.CheckBoxTreeCustomCheckBoxListener;
import tourgen.model.Meet;

public class CheckBoxTreeComponents {
  static class CheckRenderer extends JPanel implements TreeCellRenderer {
    protected CheckBoxTreeCustomCheckBox checkBox;

    protected TreeLabel label;

    public CheckRenderer(CheckBoxTreeCustomCheckBoxListener listener) {
      setLayout(null);
      checkBox = new CheckBoxTreeCustomCheckBox();
      // checkBox.addActionListener(listener);
      System.out.println("actionListener added");
      add(checkBox);
      add(label = new TreeLabel());
      checkBox.setBackground(UIManager.getColor("Tree.textBackground"));
      label.setForeground(UIManager.getColor("Tree.textForeground"));
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
        boolean leaf, int row, boolean hasFocus) {
      String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus);
      setEnabled(tree.isEnabled());
      checkBox.setSelected(((CheckNode) value).isSelected());
      label.setFont(tree.getFont());
      label.setText(stringValue);
      label.setSelected(isSelected);
      label.setFocus(hasFocus);
      if (leaf) {
        label.setIcon(UIManager.getIcon("Tree.leafIcon"));
      } else if (expanded) {
        label.setIcon(UIManager.getIcon("Tree.openIcon"));
      } else {
        label.setIcon(UIManager.getIcon("Tree.closedIcon"));
      }
      checkBox.setCheckNode(value);
      return this;
    }

    public Dimension getPreferredSize() {
      Dimension d_check = checkBox.getPreferredSize();
      Dimension d_label = label.getPreferredSize();
      return new Dimension(d_check.width + d_label.width,
          (d_check.height < d_label.height ? d_label.height : d_check.height));
    }

    public void doLayout() {
      Dimension d_check = checkBox.getPreferredSize();
      Dimension d_label = label.getPreferredSize();
      int y_check = 0;
      int y_label = 0;
      if (d_check.height < d_label.height) {
        y_check = (d_label.height - d_check.height) / 2;
      } else {
        y_label = (d_check.height - d_label.height) / 2;
      }
      checkBox.setLocation(0, y_check);
      checkBox.setBounds(0, y_check, d_check.width, d_check.height);
      label.setLocation(d_check.width, y_label);
      label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
    }

    public void setBackground(Color color) {
      if (color instanceof ColorUIResource)
        color = null;
      super.setBackground(color);
    }

    public class TreeLabel extends JLabel {
      boolean isSelected;

      boolean hasFocus;

      public TreeLabel() {
      }

      public void setBackground(Color color) {
        if (color instanceof ColorUIResource)
          color = null;
        super.setBackground(color);
      }

      public void paint(Graphics g) {
        String str;
        if ((str = getText()) != null) {
          if (0 < str.length()) {
            if (isSelected) {
              g.setColor(UIManager.getColor("Tree.selectionBackground"));
            } else {
              g.setColor(UIManager.getColor("Tree.textBackground"));
            }
            Dimension d = getPreferredSize();
            int imageOffset = 0;
            Icon currentI = getIcon();
            if (currentI != null) {
              imageOffset = currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
            }
            g.fillRect(imageOffset, 0, d.width - 1 - imageOffset, d.height);
            if (hasFocus) {
              g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
              g.drawRect(imageOffset, 0, d.width - 1 - imageOffset, d.height - 1);
            }
          }
        }
        super.paint(g);
      }

      public Dimension getPreferredSize() {
        Dimension retDimension = super.getPreferredSize();
        if (retDimension != null) {
          retDimension = new Dimension(retDimension.width + 3, retDimension.height);
        }
        return retDimension;
      }

      public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
      }

      public void setFocus(boolean hasFocus) {
        this.hasFocus = hasFocus;
      }
    }
  }

  static class CheckNode extends DefaultMutableTreeNode implements tourgen.util.ICheckNode {

    public final static int SINGLE_SELECTION = 0;

    public final static int DIG_IN_SELECTION = 4;

    protected int selectionMode;

    protected boolean isSelected;

    private Object value;

    public CheckNode() {
      this(null);
    }

    public CheckNode(Object userObject) {
      this(userObject, true, false);
    }

    public CheckNode(Object userObject, boolean allowsChildren, boolean isSelected) {
      super(userObject, allowsChildren);
      this.value = userObject;
      this.isSelected = isSelected;
      setSelectionMode(DIG_IN_SELECTION);
    }

    public void setSelectionMode(int mode) {
      selectionMode = mode;
    }

    public int getSelectionMode() {
      return selectionMode;
    }

    public void setSelected(boolean isSelected) {
      this.isSelected = isSelected;

      if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
        Enumeration e = children.elements();
        while (e.hasMoreElements()) {
          CheckNode node = (CheckNode) e.nextElement();
          node.setSelected(isSelected);
        }
      }
    }

    public boolean isSelected() {
      return isSelected;
    }

    /*
     * public Object getMeet() { return value; }
     */

    public Object getValue() {
      return value;
    }

    // If you want to change "isSelected" by CellEditor,
    /*
     * public void setUserObject(Object obj) { if (obj instanceof Boolean) {
     * setSelected(((Boolean)obj).booleanValue()); } else {
     * super.setUserObject(obj); } }
     */

  }
}
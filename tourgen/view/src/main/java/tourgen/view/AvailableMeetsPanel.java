package tourgen.view;

import javax.swing.JList;

import tourgen.controller.NewMainViewController;
import tourgen.model.Repository;
import tourgen.util.IAvailableMeetsPanel;

public class AvailableMeetsPanel extends javax.swing.JPanel 
implements IAvailableMeetsPanel {
  
  private Object selectedSchool;
  private Object newMeet;
  private Object oldMeet;
  
  private tourgen.controller.NewMainViewController newMainViewController;
  private  javax.swing.DefaultListModel availableMeetListModel;
  private javax.swing.JList<tourgen.model.Meet> availableMeetGraphicalList;
  private tourgen.controller.ChangeCompetitionSiteListener changeCompetitionSiteListener;
  private javax.swing.JButton changeCompetitionSiteButton;
  public AvailableMeetsPanel() {
    
    availableMeetListModel = new javax.swing.DefaultListModel();
    
    this.setLayout(new java.awt.BorderLayout());
    availableMeetGraphicalList = 
        new JList<tourgen.model.Meet>(availableMeetListModel);
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(availableMeetGraphicalList);
    add(scrollPane, java.awt.BorderLayout.CENTER);
    
    javax.swing.JPanel panel = new javax.swing.JPanel();
    
    changeCompetitionSiteButton = new javax.swing.JButton("Change");
    panel.add(changeCompetitionSiteButton);
    changeCompetitionSiteButton.addActionListener(changeCompetitionSiteListener);
    
    
    add(panel, java.awt.BorderLayout.SOUTH);
  }
  void setNewMainViewController(
      tourgen.controller.NewMainViewController newMainViewControllerArg) {
    newMainViewController = newMainViewControllerArg;
  }
  
  public void showAvailableMeets(Object meet, Object school) {
   selectedSchool = school;
   oldMeet = meet;
   java.util.List<tourgen.model.Meet> sectionalMeetSuggestionList = 
       newMainViewController.getAvailableSectionalMeets(meet, school);
   availableMeetListModel.removeAllElements();
   for (tourgen.model.Meet sectionalMeet : sectionalMeetSuggestionList) {
     availableMeetListModel.addElement(sectionalMeet);
   }
  }
  
  void createListeners() {
    changeCompetitionSiteButton.addActionListener(
        new tourgen.controller.ChangeCompetitionSiteListener(
            newMainViewController,this));
    
  }
  @Override
  public Object getSelectedSchool() {
    return selectedSchool;
  }
  
  @Override
  public Object getNewMeet() {
    return availableMeetGraphicalList.getSelectedValue();
  }
  
  @Override
  public Object getOldMeet() {
    return oldMeet;
  }
}
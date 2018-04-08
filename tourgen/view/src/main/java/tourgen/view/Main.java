package tourgen.view;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuListener;
import javax.swing.plaf.FontUIResource;

import tourgen.controller.AddSchoolFormListeners;
import tourgen.controller.AddSchoolUseCaseController;
import tourgen.controller.EditSchoolFormListeners;
import tourgen.controller.EditSchoolUseCaseController;
import tourgen.controller.IController;
import tourgen.controller.RemoveSchoolUseCaseController;
import tourgen.controller.ReportViewListeners;
import tourgen.controller.ReportViewUseCaseController;
import tourgen.controller.SchoolListListeners;
import tourgen.controller.ViewSchoolListUseCaseController;
import tourgen.model.Repository;
import tourgen.model.RepositoryInitialization;
import tourgen.model.SchoolManager;
import tourgen.util.IAddSchoolForm;
import tourgen.util.IEditSchoolForm;
import tourgen.util.IReportTableFrame;
import tourgen.util.IReportTableView;
import tourgen.util.IRepositoryView;
import tourgen.util.ISchoolListView;

public class Main {
	public static void main(String[] args) {
		FontUIResource resource = new FontUIResource(new Font("Tahoma", Font.PLAIN, 24));
		setUIFont(resource);
		Repository repo = new Repository();
		SchoolManager manager = new SchoolManager(repo);
		
		
		AddSchoolFormListeners addSchoolFormListeners = new AddSchoolFormListeners();
		ActionListener addListener = addSchoolFormListeners.new AddSchoolListener();
		
		EditSchoolFormListeners editSchoolFormListeners = new EditSchoolFormListeners();
		ActionListener editListener = editSchoolFormListeners.new EditSchoolListener();
		
		SchoolListListeners listListeners = new SchoolListListeners();
		ActionListener listAddButtonListener = listListeners.new AddSchoolListener();
		ActionListener listEditButtonListener = listListeners.new EditSchoolListener();
		ActionListener listRemoveButtonListener = listListeners.new RemoveSchoolListener();
		MenuListener listShowSchoolListActions = listListeners.new ShowSchoolListActionsListener();
		
		IAddSchoolForm addForm = new AddSchoolForm(addListener);
		IEditSchoolForm editForm = new EditSchoolForm(editListener);
		ISchoolListView schoolList =  new SchoolListView(listAddButtonListener, listEditButtonListener, listRemoveButtonListener, listShowSchoolListActions, manager);
		
		AddSchoolUseCaseController addUseCaseController = new AddSchoolUseCaseController(manager,schoolList,addForm,addSchoolFormListeners);
		EditSchoolUseCaseController editUseCaseController = new EditSchoolUseCaseController(manager,schoolList,editForm,editSchoolFormListeners);
		RemoveSchoolUseCaseController removeUseCaseController = new RemoveSchoolUseCaseController(schoolList, listListeners, manager);
		listListeners.setAddController(addUseCaseController);
		listListeners.setEditController(editUseCaseController);
		listListeners.setRemoveController(removeUseCaseController);
		schoolList.setRemoveUseCaseController(removeUseCaseController);
		
		ReportViewListeners reportViewListeners = new ReportViewListeners();
		ActionListener reportViewManageSchoolButtonListener = reportViewListeners.new ManageSchoolButtonListener();
		ListSelectionListener tournamentSelectionListener = reportViewListeners.new TournamentSelectionListener();
		
		IReportTableFrame reportFrame = new ReportTableFrame(reportViewManageSchoolButtonListener,tournamentSelectionListener,repo);
		IReportTableView reportTableView = reportFrame.returnReportTableView();
		IRepositoryView repositoryView = reportFrame.returnRepositoryView();
		
		
		
		ReportViewUseCaseController  reportViewUseCaseController = new ReportViewUseCaseController(reportTableView, repositoryView);
		reportViewListeners.setCoordinator(reportViewUseCaseController);
		ViewSchoolListUseCaseController viewSchoolListUseCaseController = new ViewSchoolListUseCaseController(schoolList);
		listListeners.setViewSchoolListController(viewSchoolListUseCaseController);
		reportViewListeners.setViewSchoolListUseCaseController(viewSchoolListUseCaseController);
		
		manager.initSchools();
		schoolList.populate();
		
		//RepositoryInitialization init = new RepositoryInitialization();
		RepositoryInitialization.init(repo, manager);
		repositoryView.populate();
		
		reportFrame.showView();
		
		manager.addObserver(editForm);
		manager.addObserver(addForm);
		manager.addObserver(schoolList);
		
	}
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f) {
		java.util.Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
		UIDefaults uiDef = UIManager.getLookAndFeelDefaults();
		uiDef.put("Menu.font", f);
		UIManager.put("Menu.font", f);
		uiDef.put("MenuItem.font", f);
		UIManager.put("MenuItem.font", f);
		uiDef.put("TextPane.font", f);
		UIManager.put("TextPane.font", f);
		uiDef.put("defaultFont", 20);
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				uiDef.put(key, f);
		}	
	}
}

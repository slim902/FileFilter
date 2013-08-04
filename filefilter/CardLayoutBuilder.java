package ca.softwarebyslim.filefilter;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;

/**
 * 
 * @author slim902
 * 
 * Class to build a gui using card layout. This allows the app to have "pages"
 *and naveigate through these pages
 *
 */
public class CardLayoutBuilder {
	/** TEST PATH: C:\Users\slim\Desktop\Test  **/

	static boolean DEBUG_MODE = false;
	static FilterRuleManager mgr;
	
	static CardLayout cardLayout;		
	static JPanel card;// = new JPanel();
	static JButton viewRules;// = new JButton("View Rules");  // Button to navigate to ViewRules Page
	static JButton viewRulesFromAddRulesCard;// = new JButton("View Rules");
	static JButton home;// = new JButton("Home");  		   // Button to navigate back to Home Page 
	static JButton addRule;// = new JButton("Add Rule");	   // Button to navigate to the add rule Page
	static JButton addRuleCardAddRule;// = new JButton("Add Rule");
	static JTextArea fileFilterInfo;
	static JTable rulesTable;
	
	String[] tempRuleInfo;// = new String[2];		// Store info
	
	public CardLayoutBuilder(FilterRuleManager ruleManger){
		mgr = ruleManger;
		card = new JPanel();
		viewRules = new JButton("View Rules");
		viewRulesFromAddRulesCard = new JButton("View Rules");
		home = new JButton("Home");
		addRule = new JButton("Add Rule");
		addRuleCardAddRule = new JButton("Add Rule");
		tempRuleInfo = new String[2];
	}

	public static void main(String[] args) {
		CardLayoutBuilder myCardLayout = new CardLayoutBuilder(mgr);
		myCardLayout.buildGUI();
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
/**        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		CardLayoutBuilder myCardLayout = new CardLayoutBuilder(mgr);
        		 //mgr = new FilterRuleManager();
        		 //SyncFolder syncFolder = new SyncFolder();
        		myCardLayout.buildGUI();
            	//buildGUI();
            }
        });
        */		
	}

	public void buildGUI() {
		
		JFrame frame = new JFrame();  // main frame
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel contentPane = (JPanel) frame.getContentPane();  
		card.setLayout(cardLayout = new CardLayout());

		Border loweredEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		
		
		// Set up Home Card
		JPanel homeCard = new JPanel(); 			// JPanel that will act as our "home page"
		homeCard.setLayout(new GridBagLayout());
		JPanel homeCardLabelPanel = new JPanel();   // JPanel to hold label
		JLabel homeCardLabel = new JLabel("Welcome To FileFilter!");
		homeCardLabelPanel.add(homeCardLabel);		

		// Home Card GridBagLayout Constraints
		GridBagConstraints homeCardLabelPanelConstr = new GridBagConstraints();
		homeCardLabelPanelConstr.anchor = GridBagConstraints.PAGE_START;
		homeCardLabelPanelConstr.fill = GridBagConstraints.HORIZONTAL;		// set up constraints for grid bag layout
		homeCardLabelPanelConstr.gridx = 0;									// we want the label to take up the top row, all three columns
		homeCardLabelPanelConstr.gridy = 0;
		homeCardLabelPanelConstr.gridwidth = 3;
		homeCardLabelPanelConstr.weightx = 0.5;
		homeCardLabelPanelConstr.weighty = 1.0;
		//homeCardLabelPanel.setBorder(loweredEtched);
		homeCard.add(homeCardLabelPanel, homeCardLabelPanelConstr);
		
		// Home Card Text Area
		fileFilterInfo = new JTextArea(8, 40);  // config text area to display app info
		fileFilterInfo.setLineWrap(true);
		fileFilterInfo.setWrapStyleWord(true);
		fileFilterInfo.setEditable(false);
		fileFilterInfo.setBorder(loweredEtched);
		Color BGColor = homeCard.getBackground();
		fileFilterInfo.setBackground(BGColor);
		JPanel infoPanel = new JPanel();
		infoPanel.add(fileFilterInfo);
		
		// Set Constraints for infoPanel 
		GridBagConstraints homeCardInfoPanelConstr = new GridBagConstraints();
		homeCardInfoPanelConstr.anchor = GridBagConstraints.NORTH;
		homeCardInfoPanelConstr.fill = GridBagConstraints.HORIZONTAL;
		homeCardInfoPanelConstr.gridx = 0;
		homeCardInfoPanelConstr.gridy = 1;
		homeCardInfoPanelConstr.gridwidth = 3;
		//homeCardInfoPanelConstr.weightx = 0.5;
		//homeCardInfoPanelConstr.weighty = 1;
		homeCard.add(infoPanel, homeCardInfoPanelConstr);
		
		
		
		// Home Card Buttons
		JPanel homeCardButtonPanel = new JPanel(); //JPanel to hold button for homeCard
		homeCardButtonPanel.add(viewRules);
		viewRules.addActionListener(new ViewRulesButtonListener());
		//homeCard.add(homeCardButtonPanel);

		// Set Constraints for buttonPanel 
		GridBagConstraints homeCardbuttonPanelConstr = new GridBagConstraints();
		homeCardbuttonPanelConstr.fill = GridBagConstraints.NONE;
		homeCardbuttonPanelConstr.gridx = 1;
		homeCardbuttonPanelConstr.gridy = 2;
		homeCardbuttonPanelConstr.gridwidth = 1;
		homeCardbuttonPanelConstr.weightx = 0.5;
		homeCardbuttonPanelConstr.weighty = 1;
		//homeCardButtonPanel.setBorder(loweredEtched);
		homeCard.add(homeCardButtonPanel, homeCardbuttonPanelConstr);
		
		// Set up Rules Card
		JPanel rulesCard = new JPanel();
		rulesCard.setLayout(new GridBagLayout());
		JPanel rulesCardLabelPanel = new JPanel();
		JLabel rulesCardLabel = new JLabel("Set Up Rules");
		rulesCardLabelPanel.add(rulesCardLabel);
		//rulesCard.add(rulesCardLabelPanel);
		
		// Set up rulesCard labelPanel Gridbag Layout Constraints
		GridBagConstraints rulesCardLabelPanelConstr = new GridBagConstraints();
		rulesCardLabelPanelConstr.anchor = GridBagConstraints.PAGE_START;
		rulesCardLabelPanelConstr.fill = GridBagConstraints.HORIZONTAL;		// set up constraints for grid bag layout
		rulesCardLabelPanelConstr.gridx = 0;									// we want the label to take up the top row, all three columns
		rulesCardLabelPanelConstr.gridy = 0;
		rulesCardLabelPanelConstr.gridwidth = 3;
		rulesCardLabelPanelConstr.weightx = 0.5;
		rulesCardLabelPanelConstr.weighty = 1.0;
		//rulesCardLabelPanelConstr.setBorder(loweredEtched);
		rulesCard.add(rulesCardLabelPanel, rulesCardLabelPanelConstr);
		
		
		String[] colNames = { "File Type", "Save Path"};
		Object[][] data = mgr.getRules();
		
		if(DEBUG_MODE){
			System.out.println("Data Length: " + data.length);
		}

		
		rulesTable = new JTable(new DefaultTableModel(data, colNames));
		rulesTable.setEnabled(false);
		rulesTable.setBackground(BGColor);
		JScrollPane rulesScrollPane = new JScrollPane(rulesTable);

		// Set up rulesCard rulesScrollPanel Gridbag Layout Constraints
		GridBagConstraints rulesScrollPanelConstr = new GridBagConstraints();
		rulesScrollPanelConstr.anchor = GridBagConstraints.PAGE_START;
		rulesScrollPanelConstr.fill = GridBagConstraints.NONE;		// set up constraints for grid bag layout
		rulesScrollPanelConstr.gridx = 0;									// we want the label to take up the top row, all three columns
		rulesScrollPanelConstr.gridy = 1;
		rulesScrollPanelConstr.gridwidth = 3;
		rulesScrollPanelConstr.weightx = 0.5;
		rulesScrollPanelConstr.weighty = 1.0;
		//rulesCardLabelPanelConstr.setBorder(loweredEtched);
		rulesCard.add(rulesScrollPane, rulesScrollPanelConstr);
		
		
		JPanel rulesCardButtonPanel = new JPanel();
		rulesCardButtonPanel.add(home);
		home.addActionListener(new HomeButtonListener());
		//rulesCard.add(rulesCardButtonPanel);

		// Set GridBagLayout Constraints for rulesCard buttonPanel 
		GridBagConstraints rulesCardHomeButtonPanelConstr = new GridBagConstraints();
		rulesCardHomeButtonPanelConstr.fill = GridBagConstraints.NONE;
		rulesCardHomeButtonPanelConstr.gridx = 2;
		rulesCardHomeButtonPanelConstr.gridy = 2;
		rulesCardHomeButtonPanelConstr.gridwidth = 2;
		rulesCardHomeButtonPanelConstr.weightx = 0.5;
		rulesCardHomeButtonPanelConstr.weighty = 1;
		//homeCardButtonPanel.setBorder(loweredEtched);
		rulesCard.add(rulesCardButtonPanel, rulesCardHomeButtonPanelConstr);
		
		rulesCardButtonPanel.add(addRule);
		addRule.addActionListener(new AddRuleButtonListener());

		// Set GridBagLayout Constraints for rulesCard buttonPanel 
		GridBagConstraints rulesCardAddRuleButtonPanelConstr = new GridBagConstraints();
		rulesCardAddRuleButtonPanelConstr.fill = GridBagConstraints.NONE;
		rulesCardAddRuleButtonPanelConstr.gridx = 0;
		rulesCardAddRuleButtonPanelConstr.gridy = 2;
		rulesCardAddRuleButtonPanelConstr.gridwidth = 2;
		rulesCardAddRuleButtonPanelConstr.weightx = 0.5;
		rulesCardAddRuleButtonPanelConstr.weighty = 1;
		//homeCardButtonPanel.setBorder(loweredEtched);
		rulesCard.add(rulesCardButtonPanel, rulesCardAddRuleButtonPanelConstr);
		
		// Set up AddRule Card
		JPanel addRuleCard = new JPanel();
		addRuleCard.setLayout(new GridBagLayout());
		JPanel addRuleCardLabelPanel = new JPanel();
		JLabel addRuleCardLabel = new JLabel("Add A Rule");
		addRuleCardLabelPanel.add(addRuleCardLabel);
		
		//addRuleCardLabelPanel.setBorder(loweredEtched);
		
		// Set up addRuleCard labelPanel Gridbag Layout Constraints
		GridBagConstraints addRuleCardLabelPanelConstr = new GridBagConstraints();
		addRuleCardLabelPanelConstr.anchor = GridBagConstraints.PAGE_START;
		addRuleCardLabelPanelConstr.fill = GridBagConstraints.HORIZONTAL;		// set up constraints for grid bag layout
		addRuleCardLabelPanelConstr.gridx = 0;									// we want the label to take up the top row, all three columns
		addRuleCardLabelPanelConstr.gridy = 0;
		addRuleCardLabelPanelConstr.gridwidth = 4;
		addRuleCardLabelPanelConstr.weightx = 0.5;
		addRuleCardLabelPanelConstr.weighty = .2;
		//rulesCardLabelPanelConstr.setBorder(loweredEtched);
		addRuleCard.add(addRuleCardLabelPanel, addRuleCardLabelPanelConstr);
		
		// Add JComboBox and its GridBag Layout Constraints
		String[] comboBoxOptions = {"Choose a file type", "Music", "Video", "Pictures", "Office", "Folders"};
		JPanel addRuleCardJComboBoxPanel = new JPanel();
		JComboBox<String> addRuleCardJComboBox = new JComboBox<String>(comboBoxOptions);
		addRuleCardJComboBox.setSelectedIndex(0);
		addRuleCardJComboBox.addActionListener(new ComboBoxListener(addRuleCardJComboBox));
		addRuleCardJComboBoxPanel.add(addRuleCardJComboBox);
		
		//addRuleCardJComboBoxPanel.setBorder(loweredEtched);

		// Set up addRuleCard JComboBox Gridbag Layout Constraints
		GridBagConstraints addRuleCardJComboBoxpanelConstr = new GridBagConstraints();
		addRuleCardJComboBoxpanelConstr.anchor = GridBagConstraints.FIRST_LINE_START;
		addRuleCardJComboBoxpanelConstr.fill = GridBagConstraints.HORIZONTAL;		// set up constraints for grid bag layout
		addRuleCardJComboBoxpanelConstr.gridx = 0;									// we want the label to take up the top row, all three columns
		addRuleCardJComboBoxpanelConstr.gridy = 1;
		addRuleCardJComboBoxpanelConstr.gridwidth = 2;
		addRuleCardJComboBoxpanelConstr.weightx = 0.5;
		addRuleCardJComboBoxpanelConstr.weighty = 0;
		addRuleCard.add(addRuleCardJComboBoxPanel, addRuleCardJComboBoxpanelConstr);
		
		JPanel addRuleCardPathTextFieldPanel = new JPanel();
		JTextField addRuleCardPathTextField = new JTextField(22);
		addRuleCardPathTextField.setMargin(new Insets(3,3,3,3));
		//addRuleCardPathTextField.setPreferredSize(29);
		//addRuleCardPathTextField.getDocument().addDocumentListener(new AddRuleCardPathTextFieldListener());
		addRuleCardPathTextField.addFocusListener(new AddRuleCardPathTextFieldFocusListener());
		addRuleCardPathTextFieldPanel.add(addRuleCardPathTextField);

		//addRuleCardPathTextFieldPanel.setBorder(loweredEtched);

		// Set up addRuleCard JComboBox Gridbag Layout Constraints
		GridBagConstraints addRuleCardPathTextFieldPanelConstr = new GridBagConstraints();
		addRuleCardPathTextFieldPanelConstr.anchor = GridBagConstraints.FIRST_LINE_END;
		addRuleCardPathTextFieldPanelConstr.fill = GridBagConstraints.HORIZONTAL;		// set up constraints for grid bag layout
		addRuleCardPathTextFieldPanelConstr.gridx = 2;									// we want the label to take up the top row, all three columns
		addRuleCardPathTextFieldPanelConstr.gridy = 1;
		addRuleCardPathTextFieldPanelConstr.gridwidth = 2;
		addRuleCardPathTextFieldPanelConstr.weightx = 0.5;
		addRuleCardPathTextFieldPanelConstr.weighty = 0;
		addRuleCard.add(addRuleCardPathTextFieldPanel, addRuleCardPathTextFieldPanelConstr);
		
		
		// Set up addRulesCard viewRules button
		JPanel viewRulesFromAddRulesCardButtonPanel = new JPanel(); 
		viewRulesFromAddRulesCardButtonPanel.add(viewRulesFromAddRulesCard);
		viewRulesFromAddRulesCard.addActionListener(new ViewRulesButtonListener());

		//viewRulesFromAddRulesCardButtonPanel.setBorder(loweredEtched);

		// Set Constraints for buttonPanel 
		GridBagConstraints viewRulesFromAddRulesCardButtonPanelConstr = new GridBagConstraints();
		viewRulesFromAddRulesCardButtonPanelConstr.fill = GridBagConstraints.HORIZONTAL;
		viewRulesFromAddRulesCardButtonPanelConstr.gridx = 0;
		viewRulesFromAddRulesCardButtonPanelConstr.gridy = 3;
		viewRulesFromAddRulesCardButtonPanelConstr.gridwidth = 4;
		viewRulesFromAddRulesCardButtonPanelConstr.weightx = 0.5;
		viewRulesFromAddRulesCardButtonPanelConstr.weighty = 0.5;
		addRuleCard.add(viewRulesFromAddRulesCardButtonPanel, viewRulesFromAddRulesCardButtonPanelConstr);

		// Set up addRuleCard addRule button
		JPanel addRuleFromAddRulesCardButtonPanel = new JPanel();
		addRuleFromAddRulesCardButtonPanel.add(addRuleCardAddRule);
		addRuleCardAddRule.addActionListener(new AddRuleFromAddRulesCardListener());
		
		//addRuleFromAddRulesCardButtonPanel.setBorder(loweredEtched);

		// Set GridBagLayout Constraints for addRule button
		GridBagConstraints addRuleFromAddRulesCardButtonPanelConstr = new GridBagConstraints();
		addRuleFromAddRulesCardButtonPanelConstr.anchor = GridBagConstraints.LINE_END;
		addRuleFromAddRulesCardButtonPanelConstr.fill = GridBagConstraints.HORIZONTAL;
		addRuleFromAddRulesCardButtonPanelConstr.gridx = 0;
		addRuleFromAddRulesCardButtonPanelConstr.gridy = 2;
		addRuleFromAddRulesCardButtonPanelConstr.gridwidth = 4;
		addRuleFromAddRulesCardButtonPanelConstr.weightx = 0.5;
		addRuleFromAddRulesCardButtonPanelConstr.weighty = 0;
		addRuleCard.add(addRuleFromAddRulesCardButtonPanel, addRuleFromAddRulesCardButtonPanelConstr);
		
		
		// Add Cards to static panel
		card.add("homeCard", homeCard);
		card.add("rulesCard", rulesCard);
		card.add("addRuleCard", addRuleCard);
		cardLayout.show(card, "homeCard");
		contentPane.add(card);
		
		// Set mainFrame parameters
		frame.setVisible(true);
		frame.setSize(600, 400);
		frame.pack();
		frame.setLocation(350, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	// Inner Classes to Listen for events on our various buttons
	public class ViewRulesButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cardLayout.show(card, "rulesCard");
			DefaultTableModel model = (DefaultTableModel) rulesTable.getModel();
			for(String[] rules : mgr.getRules()){
				Object[] temp = {rules[0], rules[1]};
				model.addRow(temp);
			}
		}
	}
	
	public class HomeButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			cardLayout.show(card, "homeCard");
		}
	}
	
	public class AddRuleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			tempRuleInfo[0] = "";
			tempRuleInfo[1] = "";
			cardLayout.show(card, "addRuleCard");
		}
	}
	
	public class ComboBoxListener implements ActionListener {
		private JComboBox<String> comboBox;
		public ComboBoxListener(JComboBox<String> comboBox){
			this.comboBox = comboBox;
		}
		@Override
		public void actionPerformed(ActionEvent event) {
			 ComboBoxModel<String> model = comboBox.getModel();
			 int index = comboBox.getSelectedIndex();
			 String fileType = model.getElementAt(index);
			 tempRuleInfo[0] = fileType;
			 
			 if(DEBUG_MODE){
				 System.out.println("FileType :" + fileType);
			 }
		}
	}
	/*
	public class AddRuleCardPathTextFieldListener implements DocumentListener {
		//public void actionPerformed(ActionEvent event) {
			//JTextField tf = (JTextField) event.getSource();
			 //String newRulePath = tf.getText();
			 //tempRuleInfo[1] = newRulePath;
			 //System.out.println("FileType: " + tempRuleInfo[0]);
			 //System.out.println("Destination Path " + tempRuleInfo[1]);
		 //}
		public void insertUpdate(DocumentEvent event) {
			Document newRulePath = (Document)event.getDocument();
			System.out.println(newRulePath);
		}
		
		public void removeUpdate(DocumentEvent event) {
			
		}
		
		public void changedUpdate(DocumentEvent event) {
			
		}
	}
	
	public class AddRuleCardPathTextFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JTextField tf = (JTextField) event.getSource();
			String newRulePath = tf.getText();
			tempRuleInfo[1] = newRulePath;
			System.out.println("FileType: " + tempRuleInfo[0]);
			System.out.println("Destination Path " + tempRuleInfo[1]);
			
		}
	}
	*/
	public class AddRuleCardPathTextFieldFocusListener implements FocusListener {
		public void focusGained(FocusEvent event) {
		}
		
		public void focusLost(FocusEvent event) {
			JTextField field = (JTextField)event.getSource();
			tempRuleInfo[1] =(String)field.getText();
			
			if(DEBUG_MODE){
				System.out.println("FileType: " + tempRuleInfo[0]);
				System.out.println("Destination Path " + tempRuleInfo[1]);
			}
		}
	}
	
	public class AddRuleFromAddRulesCardListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(tempRuleInfo.length == 2 && tempRuleInfo[0] != "" && tempRuleInfo[1] != ""){
				mgr.addRule(new FilterRule(tempRuleInfo[0], tempRuleInfo[1]));
				mgr.listRules();
			}else{
				if(tempRuleInfo[0] == "") {
					System.out.println("Please Select A File Type");
				}
				else if(tempRuleInfo[1] == "") {
					System.out.println("Please Enter A Destination Path");
				}
			}
		}
	}
	
}

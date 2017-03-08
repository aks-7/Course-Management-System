package main.admin.adminpanel;

import javax.swing.JPanel;

import main.admin.admin.Admin;
import main.admin.adminpanel.addAdmin.addAdmin;
import main.admin.adminpanel.admintablemodel.AdminTableModel;
import main.admin.adminpanel.proftablemodel.ProfTableModel;
import main.admin.adminpaneldao.AdminPanelDAO;
import main.professor.professor.Professor;
import main.professor.professorDAO.ProfessorDAO;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTextField;

import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

public class ProfessorSearchPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXTextField textField;
	private JTable table;
    private ProfTableModel profTableModel;
    List<Professor> prof = null;
	/**
	 * Create the panel.
	 */
	public ProfessorSearchPanel()throws Exception {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 1320, 38);
		add(panel);
		panel.setLayout(null);
		table=new JTable();
		textField = new JXTextField();
		textField.setBounds(10, 11, 1090, 20);
		textField.setPrompt("Faculty search panel. Please enter the string to search");
		panel.add(textField);
		textField.setColumns(10);
		ProfessorDAO profPanelDAO = new ProfessorDAO();
		prof = profPanelDAO.getAllProfessor(); 
		profTableModel = new ProfTableModel(prof);
		table.setModel(profTableModel);
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				String toSearch = textField.getText();
				prof = profPanelDAO.searchProfessor(toSearch);
				profTableModel = new ProfTableModel(prof);
				table.setModel(profTableModel);
				}catch(Exception e1){
					e1.printStackTrace();
				}
				}
		});
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSearch.setBounds(1120, 10, 89, 23);
		panel.add(btnSearch);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				try {
					prof = profPanelDAO.getAllProfessor();
					profTableModel = new ProfTableModel(prof);
					table.setModel(profTableModel);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRefresh.setBounds(1230, 10, 89, 23);
		panel.add(btnRefresh);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 530, 1320, 42);
		add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			addAdmin add = new addAdmin();
			add.setVisible(true);
			}
		});
		JButton btnUpdate = new JButton("Update");
		JToggleButton tglbtnSelectMultiple = new JToggleButton("Select Multiple");
		tglbtnSelectMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglbtnSelectMultiple.isSelected()){
					table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					btnUpdate.setEnabled(false);
				}
				else
				{
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					btnUpdate.setEnabled(true);
				}
			}
		});
		tglbtnSelectMultiple.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(tglbtnSelectMultiple);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(btnAdd);
		
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_2.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 1320, 450);
		add(scrollPane);
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

	}
}
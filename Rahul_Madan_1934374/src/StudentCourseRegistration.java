import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentCourseRegistration {
	
	private Connection connection;
	private JFrame frame;
	private JTextField textFieldID;
	private JTextField textFieldName;
	private JTextField textFieldContact;
	private JTextField textFieldCourse;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentCourseRegistration window = new StudentCourseRegistration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	/**
	 * Create the application.
	 */
	
	public void refreshTable() {
		try {
			String query = "select * from StudentInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			textFieldID.setText(null);
			textFieldName.setText(null);
			textFieldContact.setText(null);
			textFieldCourse.setText(null);
			pst.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	
	public StudentCourseRegistration() {
		initialize();
		connection = SQLLiteConnection.dbConnector();
		refreshTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
            try {
					
					int row = table.getSelectedRow();
					String ID = table.getModel().getValueAt(row, 0).toString();
					String query = "select * from StudentInfo where ID ='" + ID + "'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs =  pst.executeQuery();
					
					while(rs.next()) {
						
						textFieldID.setText(rs.getString("ID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldContact.setText(rs.getString("Contact"));
						textFieldCourse.setText(rs.getString("Course"));
					}
					
					pst.close();
					rs.close();
					
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
					
				}
				
			}
		});
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 707, 477);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Lasalle College");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(116, 21, 192, 42);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Montreal");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(126, 67, 62, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("ID:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(40, 123, 46, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Name:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2_1.setBounds(40, 158, 46, 23);
		frame.getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Contact:");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2_2.setBounds(40, 192, 62, 23);
		frame.getContentPane().add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("Course:");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2_3.setBounds(40, 227, 62, 23);
		frame.getContentPane().add(lblNewLabel_2_3);
		
		textFieldID = new JTextField();
		textFieldID.setBounds(118, 126, 148, 20);
		frame.getContentPane().add(textFieldID);
		textFieldID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(118, 161, 148, 20);
		frame.getContentPane().add(textFieldName);
		
		textFieldContact = new JTextField();
		textFieldContact.setColumns(10);
		textFieldContact.setBounds(118, 195, 148, 20);
		frame.getContentPane().add(textFieldContact);
		
		textFieldCourse = new JTextField();
		textFieldCourse.setColumns(10);
		textFieldCourse.setBounds(118, 230, 148, 20);
		frame.getContentPane().add(textFieldCourse);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					String query = "insert into StudentInfo (ID, Name, Contact, Course) values (?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, Integer.parseInt(textFieldID.getText()));
					pst.setString(2, textFieldName.getText());
					pst.setString(3, textFieldContact.getText());
					pst.setString(4, textFieldCourse.getText());
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Saved successfully");
					pst.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				refreshTable();
				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnAdd.setForeground(new Color(0, 0, 0));
		btnAdd.setBackground(Color.YELLOW);
		btnAdd.setBounds(25, 294, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					int action = JOptionPane.showConfirmDialog(null, "Are You Sure. You want to DELETE?", "Delete", JOptionPane.YES_NO_OPTION);
					if(action == 0) {
						
						try {
							
								String query = "delete from StudentInfo where ID='" + textFieldID.getText() + "'";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.execute();
								JOptionPane.showMessageDialog(null, "Removed Successfully!");
								pst.close();
					
					
						} catch (Exception e3) {
							// TODO: handle exception
								JOptionPane.showMessageDialog(null, e3);
						}
						
						refreshTable();
				}
				
			}
		});
		btnDelete.setBackground(Color.YELLOW);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnDelete.setBounds(25, 339, 89, 23);
		frame.getContentPane().add(btnDelete);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
try {
					
					String query = "update StudentInfo set ID='" + textFieldID.getText()+ "', Name='" + textFieldName.getText() + 
							"', Contact='" + textFieldContact.getText() + "', Course='" +textFieldCourse.getText() + "' where ID='" + textFieldID.getText() + "'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Updated Successfully!");
					pst.close();
			
			
			} catch (Exception e2) {
				// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2);
			}
			
			refreshTable();
				
			}
		});
		btnUpdate.setBackground(Color.YELLOW);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnUpdate.setBounds(158, 294, 89, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		btnClear.setForeground(Color.BLACK);
		btnClear.setBackground(Color.YELLOW);
		btnClear.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnClear.setBounds(158, 339, 89, 23);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(299, 105, 382, 307);
		frame.getContentPane().add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\madan\\eclipse-workspace\\Rahul_Madan_1934374\\new_LaSalle_college.png"));
		lblNewLabel_3.setBounds(25, 21, 91, 82);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Sreach by name : ");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2_1_1.setBounds(314, 73, 128, 23);
		frame.getContentPane().add(lblNewLabel_2_1_1);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					if (textField.getText().isEmpty()) {
						refreshTable();
						return;
					}
					String query = "select * from StudentInfo where Name = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		textField.setText((String) null);
		textField.setColumns(10);
		textField.setBounds(452, 76, 148, 20);
		frame.getContentPane().add(textField);
	}
}

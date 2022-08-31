package Management_System;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Student_Management {

	private JFrame frame;
	private JTextField txtStudName;
	private JTextField txtYearLevel;
	private JTextField txtCourse;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student_Management window = new Student_Management();
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
	public Student_Management() {
		initialize();
		connect();
		load_table();
	}
	
	Connection con;
	PreparedStatement prdst;
	ResultSet rs;
	private JTextField txtStudId;
	public void connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/student_management_crud","root","");
		}
		catch(ClassNotFoundException ex)
		{
			ex.getException();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	void load_table()
	{
		try
		{
			prdst = con.prepareStatement("select * from tbl_student");
			rs = prdst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 0, 51));
		frame.setBounds(100, 100, 762, 546);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Management");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(258, 11, 234, 63);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Operations Panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(23, 108, 317, 240);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Student ID");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(10, 41, 73, 29);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Student Name");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(10, 97, 88, 29);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Year level");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1_1.setBounds(10, 142, 88, 29);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Course");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1_1_1.setBounds(10, 189, 88, 29);
		panel.add(lblNewLabel_1_1_1_1);
		
		txtStudName = new JTextField();
		txtStudName.setBounds(108, 97, 181, 29);
		panel.add(txtStudName);
		txtStudName.setColumns(10);
		
		txtYearLevel = new JTextField();
		txtYearLevel.setColumns(10);
		txtYearLevel.setBounds(108, 142, 181, 29);
		panel.add(txtYearLevel);
		
		txtCourse = new JTextField();
		txtCourse.setColumns(10);
		txtCourse.setBounds(108, 189, 181, 29);
		panel.add(txtCourse);
		
		txtStudId = new JTextField();
		txtStudId.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
				try
				{
					String id = txtStudId.getText();
					prdst = con.prepareStatement("select student_name,year_level,course from tbl_student where student_id = ?");
					prdst.setString(1,id);
					ResultSet rs = prdst.executeQuery();
					
					if(rs.next()==true)
					{
						String name = rs.getString(1);
						String year = rs.getString(2);
						String course = rs.getString(3);
						
						txtStudName.setText(name);
						txtYearLevel.setText(year);
						txtCourse.setText(course);
					}
					else
					{
						txtStudName.setText("");
						txtYearLevel.setText("");
						txtCourse.setText("");
					}
				}
				catch(SQLException ex)
				{
					
				}
			
			
			
		}
		});
		txtStudId.setColumns(10);
		txtStudId.setBounds(108, 41, 181, 29);
		panel.add(txtStudId);
		
		JButton saveBtn = new JButton("SAVE");
		saveBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String studname, yrlevel, course;
			studname = txtStudName.getText();
			yrlevel = txtYearLevel.getText();
			course = txtCourse.getText();
			
			try {
				prdst = con.prepareStatement("insert into tbl_student(student_name,year_level,course)values(?,?,?)");
				prdst.setString(1, studname);
				prdst.setString(2, yrlevel);
				prdst.setString(3, course);
				prdst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Added");
				txtStudName.setText("");
				txtYearLevel.setText("");
				txtCourse.setText("");
				txtStudName.requestFocus();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		
		
		
		}
		});
		saveBtn.setForeground(Color.WHITE);
		saveBtn.setBackground(new Color(0, 255, 0));
		saveBtn.setFont(new Font("Arial Black", Font.PLAIN, 12));
		saveBtn.setBounds(23, 359, 99, 49);
		frame.getContentPane().add(saveBtn);
		
		JButton exitBtn = new JButton("EXIT");
		exitBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		});
		exitBtn.setForeground(Color.WHITE);
		exitBtn.setBackground(new Color(255, 69, 0));
		exitBtn.setFont(new Font("Arial Black", Font.PLAIN, 12));
		exitBtn.setBounds(132, 359, 99, 49);
		frame.getContentPane().add(exitBtn);
		
		JButton clearBtn = new JButton("CLEAR");
		clearBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtStudName.setText("");
			txtYearLevel.setText("");
			txtCourse.setText("");
			txtStudId.setText("");
			txtStudName.requestFocus();
		}
		});
		clearBtn.setForeground(Color.WHITE);
		clearBtn.setBackground(new Color(255, 215, 0));
		clearBtn.setFont(new Font("Arial Black", Font.PLAIN, 12));
		clearBtn.setBounds(241, 359, 99, 49);
		frame.getContentPane().add(clearBtn);
		
		JScrollPane data_table = new JScrollPane();
		data_table.setBounds(350, 108, 362, 300);
		frame.getContentPane().add(data_table);
		
		table = new JTable();
		data_table.setViewportView(table);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton updateBtn = new JButton("UPDATE");
		updateBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String studname, yrlevel, course, studid;
			studname = txtStudName.getText();
			yrlevel = txtYearLevel.getText();
			course = txtCourse.getText();
			studid = txtStudId.getText();
			
			try {
				prdst = con.prepareStatement("update tbl_student set student_name=?,year_level=?,course=? where student_id=?");
				prdst.setString(1, studname);
				prdst.setString(2, yrlevel);
				prdst.setString(3, course);
				prdst.setString(4, studid);
				prdst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Updated");
				txtStudName.setText("");
				txtYearLevel.setText("");
				txtCourse.setText("");
				txtStudName.requestFocus();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		
		
		
		
		
		}
		});
		updateBtn.setForeground(Color.WHITE);
		updateBtn.setBackground(new Color(30, 144, 255));
		updateBtn.setFont(new Font("Arial Black", Font.PLAIN, 12));
		updateBtn.setBounds(428, 419, 99, 49);
		frame.getContentPane().add(updateBtn);
		
		JButton deleteBtn = new JButton("DELETE");
		deleteBtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String studid;
			studid = txtStudId.getText();
			
			try {
				prdst = con.prepareStatement("delete from tbl_student where student_id=?");
				prdst.setString(1, studid);
				prdst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Deleted");
				txtStudName.setText("");
				txtYearLevel.setText("");
				txtCourse.setText("");
				txtStudName.requestFocus();
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
		
		
		
		}
		});
		deleteBtn.setForeground(Color.WHITE);
		deleteBtn.setBackground(Color.RED);
		deleteBtn.setFont(new Font("Arial Black", Font.PLAIN, 12));
		deleteBtn.setBounds(552, 419, 99, 49);
		frame.getContentPane().add(deleteBtn);
	}
}

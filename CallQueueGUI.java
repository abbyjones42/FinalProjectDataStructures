package departments;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CallQueueGUI { 
	private PriorityQueue pq;
	private JTextArea queueDisplay;
	
	public CallQueueGUI() {
		pq = new PriorityQueue();
		setupGUI();
		}
	
	private void setupGUI() {
		//Creating the main frame using JFrame
		JFrame frame = new JFrame("Lucky Leap Customer Call Queue");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400); 
		frame.setLayout(new BorderLayout());
		
		//Creating a Panel that Displays a title
		JPanel topPanel = new JPanel(new GridLayout(4, 2));
		topPanel.setBorder(BorderFactory.createTitledBorder("Add Caller to Queue"));
		
		//Creating fields to enter caller information
		JTextField nameField = new JTextField();
		JComboBox<String> conditionBox = new JComboBox<>(new String[]{"CAT Assistance Line", "Billing and Payments", "Claims and Processing"});
		JTextField priorityField = new JTextField(); 
		
		//Creating labels for the different text and selection boxes
		topPanel.add(new JLabel("Caller Name:"));
		topPanel.add(nameField); 
		
		topPanel.add(new JLabel("Department:")); 
		topPanel.add(conditionBox); 
		
		topPanel.add(new JLabel("Priority (1=CAT, 2=Billing, 3=Claims):")); 
		topPanel.add(priorityField);
		
		JButton addButton = new JButton("Add Caller"); 
		topPanel.add(new JLabel());
		
		topPanel.add(addButton);
		
		//Creating the Center Panel to Display the Current Call Queue
		queueDisplay = new JTextArea();
		queueDisplay.setEditable(false); 
		
		JScrollPane scrollPane = new JScrollPane(queueDisplay);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Current Callers in Queue")); 
		
		//Creating Bottom Panel Buttons to redirect the page
		JPanel bottomPanel = new JPanel(); 
		JButton dequeueButton = new JButton("Assist the next Customer."); 
		JButton refreshButton = new JButton("Refresh Call Queue Results");
		bottomPanel.add(dequeueButton); 
		bottomPanel.add(refreshButton);
		
		//Creating panels for the Frame
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH); 
		
		//Creating Action Listeners
		addButton.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String department = (String) conditionBox.getSelectedItem();
				String priorityText = priorityField.getText();
				
				if (name.isEmpty() || priorityText.isEmpty()) { 
					JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
					}
				
				try { 
					int priority = Integer.parseInt(priorityText); 
					pq.enqueue(new Caller(name, department, priority));
					nameField.setText(""); 
					priorityField.setText("");
					JOptionPane.showMessageDialog(frame, "Caller added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
					}
				//Error Exceptions to ensure integer is entered for priority status
				catch (NumberFormatException ex) { 
					JOptionPane.showMessageDialog(frame, "A valid integer must be entered to place priority.", "Error", JOptionPane.ERROR_MESSAGE); 
					}
				}
		});
		
	 dequeueButton.addActionListener(new ActionListener() {
		 @Override
		 public void actionPerformed(ActionEvent e) { 
			 Caller nextCaller = pq.dequeue();
			 
			 if (nextCaller == null) {
				 JOptionPane.showMessageDialog(frame, "There are currently no callers in the queue..", "Status: ", JOptionPane.INFORMATION_MESSAGE); 
				 } else { 
					 JOptionPane.showMessageDialog(frame, "Assisting Customer: " + nextCaller, "Next Customer: ", JOptionPane.INFORMATION_MESSAGE);
				 } 
			 refreshQueueDisplay(); 
			}
	 });
	 
	 refreshButton.addActionListener(new ActionListener() { 
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 refreshQueueDisplay();
			 }
		 });
	 
	 frame.setVisible(true);
	 }

	private void refreshQueueDisplay() {
		StringBuilder displayText = new StringBuilder(); 
		for (String department : new String[]{"CAT Assistance Line", "Billing and Payments", "Claims and Processing"}) {
			displayText.append(department).append(" Callers:\n");
			DepartmentLinkedList list = pq.getDepartmentLinkedList(department); 
			if (list != null) {
				Node current = list.getHead();
				while (current != null) { 
					displayText.append(current.caller).append("\n"); current = current.next;
					}
				}
			displayText.append("\n");
			}
		queueDisplay.setText(displayText.toString());
		}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(CallQueueGUI::new);
	}
}
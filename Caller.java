package departments;
import java.util.HashMap; 

class Caller {
	String name;
	String department;
	int priorityStatus; //Lowest number is HIGHEST PRIORITY
	
	public Caller(String name, String department, int priorityStatus) { 
	this.name = name;
	this.department = department;
	this.priorityStatus = priorityStatus;
	}

	@Override public String toString() {
		return "[Caller: " + name + ", Department: " + department + ", Priority: " + priorityStatus + "]"; }
	}

//Creating Node Class
	class Node {
	Caller caller;
	Node next;
	public Node(Caller caller) { 
	this.caller = caller;
	this.next = null; 
		}
}
	
	//Creating Department LinkedList Class with relevant methods for GUI and testing
	class DepartmentLinkedList { 
	private Node head;
	public boolean isEmpty() { 
		return head == null; 
	}
	
	public Node getHead() {
		return head;
	}
	
	public void enqueue(Caller caller) { 
	Node newNode = new Node(caller); 
	 
	if (isEmpty() || head.caller.priorityStatus > caller.priorityStatus) {
		newNode.next = head;
		head = newNode;
	} else { 
		Node current = head;
	while (current.next != null && current.next.caller.priorityStatus <= caller.priorityStatus) { 
		current = current.next;
	}
	newNode.next = current.next; 
	current.next = newNode;
		}
	}
	public Caller dequeue() {
	if (isEmpty()) {
		return null;
	} 
	
	Caller caller = head.caller;
	head = head.next; // Removing the head node
	 return caller;
	 }

	 public void display() { 
	 if (isEmpty()) {
		 System.out.println("There are no callers for this Department.");
		 return;
	 }

	 Node current = head;
	 while (current != null) {
		 System.out.println(current.caller);	
		 current = current.next;
	 		}
	 	}
	 }

	class PriorityQueue { 
	private HashMap<String, DepartmentLinkedList> departmentMap;

	 public PriorityQueue() { 

	departmentMap = new HashMap<>(); // Initialize linked lists for each department

	departmentMap.put("CAT Assistance Line", new DepartmentLinkedList()); 

	departmentMap.put("Billing and Payments", new DepartmentLinkedList());

	departmentMap.put("Claims and Processing", new DepartmentLinkedList());

	 }
	 
	 public DepartmentLinkedList getDepartmentLinkedList(String department) {
		 return departmentMap.get(department);
	 }

	 public void enqueue(Caller caller) { 
	 if (!departmentMap.containsKey(caller.department)) { 
		 System.out.println("That is an unknown department: " + caller.department);
	 return; 
	} 

	departmentMap.get(caller.department).enqueue(caller);
	 }

	 public Caller dequeue() { 
	 for (String department : new String[]{"CAT Assistance Line", "Billing and Payments", "Claims and Processing"}) {

	 DepartmentLinkedList list = departmentMap.get(department);

	 if (!list.isEmpty()) { 
		 return list.dequeue();
	 	}
	 }
	 return null; 
	 }

	 public void display() {
	 for (String department : new String[]{"CAT Assistance Line", "Billing and Payments", "Claims and Processing"}) { 
		 System.out.println(department + " Callers:"); 

	departmentMap.get(department).display();
	 		}
	 	}
	 }

	class Departments {
	 public static void main(String[] args) { 
	 PriorityQueue pq = new PriorityQueue();

	 //Adding Callers to the Queue

	 pq.enqueue(new Caller("Abigail Jones", "CAT Assistance Line", 1));

	 pq.enqueue(new Caller("Bryan Burkhardt", "Claims and Processing", 3)); 

	pq.enqueue(new Caller("Ahmei Jewell", "Billing and Payments", 2));

	 pq.enqueue(new Caller("Ella Mae", "CAT Assistance Line", 1));

	 pq.enqueue(new Caller("Thomas the Train", "Claims and Processing", 3));

	 System.out.println("Lucky Leap's Current Call Queue:");

	 pq.display(); 
	 System.out.println("Current Customers Being Helped:");
	 while (true) {
		 Caller caller = pq.dequeue();
		 if(caller == null) break;
		 System.out.println("Assisting Customer: " + caller);
	 }
	 }
}

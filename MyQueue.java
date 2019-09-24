/*
Ermiyas Liyeh
30711570
CSC 172 Online- Summer 2019
I did not collaborate with anyone on this assignment.
*/

public class MyQueue<AnyType> implements Queue<AnyType> {

	MyDoubleNode<AnyType> head;
	MyDoubleNode<AnyType> tail;
	
	MyQueue(){
		
		head = null;
		tail = null;
		
	}

	
	public boolean isEmpty() {
		
		return (head == null);
		
	}

	
	public void enqueue(AnyType x) {
		
		if(head == null) {
			head = new MyDoubleNode<AnyType>(null, x, null);
			return;
		}
		

		MyDoubleNode<AnyType> current = head;

		while(current.next!=null) {
			current = current.next;
		}
			
		MyDoubleNode<AnyType> newLink = new MyDoubleNode<AnyType> (current,x,null); 
			
		current.next = newLink;
		tail = newLink;
		
		
	}

	
	public AnyType dequeue() {
		
		
		if(isEmpty()) {
			return null;
		}
		
		MyDoubleNode<AnyType> newhead = new MyDoubleNode<AnyType> (null, head.data, head.next);

		
		newhead.next = head.next;
		head = newhead.next;
		
		
		return newhead.data;
	}

	
	public AnyType peek() {
		
		if(head == null) {
			return null;
		}
		
		return head.data;
	}
	
	
	
	///*     //Optional print statement for the list of elements
	public void printList() {
		
		MyDoubleNode<AnyType> current = head;
		while(current != null) {
			System.out.println(current.data);
			current = current.next;
		}
		
		
	}
	//*/
	
}


class MyDoubleNode<AnyType> {

	public AnyType data;
	public MyDoubleNode<AnyType> next;
	public MyDoubleNode<AnyType> prev;
	
	
	public MyDoubleNode(AnyType data) {
		prev = null;
		this.data = data;
		next = null;
	}
	
	public MyDoubleNode(MyDoubleNode<AnyType> prev, AnyType data, MyDoubleNode<AnyType> next) {
		this.prev = prev;
		this.data = data;
		this.next = next;
	}
	
}
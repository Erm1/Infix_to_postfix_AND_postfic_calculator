/*
Ermiyas Liyeh
30711570
CSC 172 Online- Summer 2019
I did not collaborate with anyone on this assignment.
*/


public class MyStack<AnyType> implements Stack<AnyType> {

	
	MyNode<AnyType> head;
	
	MyStack() {
		head = null;
	}
	
	
	
	public boolean isEmpty() {
		
		return (head == null);
		
	}

	
	public void push(AnyType x) {
		
		if(head == null) {
			head = new MyNode<AnyType> (x);
			return;
		}
		
		MyNode<AnyType> newHead = new MyNode<AnyType> (x);
		newHead.next = head;
		head = newHead;
		
	}

	
	public AnyType pop() {
		
		if(isEmpty()) {
			return null;
		}
		
		MyNode<AnyType> newHead = head;
		head = head.next;
		
		
		return newHead.data;
		
		
	}

	
	public AnyType peek() {
		
		if(head == null) {
			return null;
			
		}
		
		return head.data;
	}
	
	
	
	///*     //Optional print statement for the list of elements
	public void printList() {
		
		MyNode<AnyType> current = head;
		while (current != null) {
			System.out.println(current.data);
			current = current.next;
		}
		
	}
	//*/
}



class MyNode<AnyType> {

	AnyType data;
	MyNode<AnyType> next;
	
	MyNode(AnyType Data){
		this.data = Data;
	}
	 
}
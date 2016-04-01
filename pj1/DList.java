
public class DList {

	protected DListNode head;
	protected DListNode tail;
	protected long size;

	public DList() {
		head = null;
		tail = null;
		size = 0;
	}

	public void insertFront(int r, int g, int b, int num) {
		if(size == 0) {
			head = new DListNode(r, g, b, num);
			tail = head;
			size++;
		}else {
			DListNode newNode = new DListNode(r, g, b, num);
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
			size++;
		}
	}

	public void insertBack(int r, int g, int b, int num) {
		if(size == 0) {
			head = new DListNode(r, g, b, num);
			tail = head;
			size++;
		}else {
			DListNode newNode = new DListNode(r, g, b, num);
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
			size++;
		}
	}

	public void insertBetween(int r, int g, int b, int num, DListNode pre){
		if(size > 1) {
			DListNode newNode = new DListNode(r, g, b, num);
			pre.next.prev = newNode;
			newNode.next = pre.next;
			newNode.prev = pre;
			pre.next = newNode;
			size++;
		}
	}

	public void removeFront() {
		if(size > 1) {
			head.next.prev = null;
			head = head.next;
			size--;
		}else if (size ==1) {
			head = null;
			tail = null;
			size--;
		}
	}

	public void removeBack() {
		if(size > 1) {
			tail.prev.next = null;
			tail = tail.prev;
			size--;
		}else if (size == 1) {
			head = null;
			tail = null;
			size--;
		}
	}

	public void removeBetween(DListNode pre){
		if(size > 2) {
			pre.next = pre.next.next;
			pre.next.prev = pre;
			size--;
		}
	}
}

public class DListNode {
	
	public int r;
	public int g;
	public int b;
	public int num;
	public DListNode prev;
	public DListNode next;

	DListNode() {
		r = 0;
		g = 0;
		b = 0;
		num = 0;
		prev = null;
		next = null;
	}

	DListNode(int red, int green, int blue, int length) {
		r = red;
		g = green;
		b = blue;
		num = length;
		prev = null;
		next = null;
	}

}
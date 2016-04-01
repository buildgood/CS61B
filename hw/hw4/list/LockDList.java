package list;

public class LockDList extends DList {

	protected LockDListNode newNode(Object item, DListNode prev, DListNode next){
		return new LockDListNode(item, prev, next);
	}

	public void lockNode(DListNode node){
		LockDListNode temp = (LockDListNode)node;
		temp.lock = true;
	}

	public LockDListNode front(){
		return (LockDListNode)(super.front());
	}

	public LockDListNode back(){
		return (LockDListNode)(super.back());
	}

	public LockDListNode next(DListNode node){
		return (LockDListNode)(super.next(node));
	}

	public LockDListNode prev(DListNode node){
		return (LockDListNode)(super.prev(node));
	}

	public void remove(DListNode node){
		if(node !=null){
		  if(((LockDListNode)node).lock !=true){
      		node.prev.next = node.next;
      		node.next.prev = node.prev;
      		size--;
    }
		}
	}

}
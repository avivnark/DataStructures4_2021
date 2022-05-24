import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;


public class BacktrackingAVL extends AVLTree {

	// You are to implement the function Backtrack.
	public void Backtrack() {
		IntegrityStatement.signature(); // Do not remove this line
		if (!backtrack.isEmpty()) {// if it empty we dont do nothing
			ArrayDeque<Integer> step = backtrack.poll();
			int val = step.poll();// the val of the node we insert
			if (!step.isEmpty()) { // if it empty we just need to delete the node
				int rotate=step.poll();//the node rotate
				int type=step.poll();//the type
				Node curr=search(rotate,root);//find the node
				Node parent=curr.parent;
				Node change=Backtrack(curr,type);//the part we backtrack
				if(parent==null)//put in the right place
					root = change;
				else if(change.value<parent.value)
					parent.left=change;
				else
					parent.right=change;
			}
			if(!delete(val,root))
				root=null;
		}
	}

	private Node Backtrack(Node node,int type) {
		// Left Cases
		Node curr;
		if (type == 1 || type == 3) {
			curr = leftRotate(node);
			if (type == 3)
				curr.left = rightRotate(node);
		}
		// Right Cases
		else {
			curr = rightRotate(node);
			if (type == 4)
				curr.right = leftRotate(node);
		}
		return curr;
	}

	private Node search(int value, Node node) {// search value that exists
		if (node.value == value)
			return node;
		else if (value < node.value) {// left side
			return search(value, node.left);
		} else {// right side
			return search(value, node.right);
		}
	}

	private boolean delete(int value, Node curr) {// delete value that exist
		boolean delete = false;
		if (curr.value == value) {
			if (curr.parent != null) {
				if (curr.parent.left != null && curr.parent.left.value == value)
					curr.parent.left = null;
				else// if (curr.parent.right != null && curr.parent.right.value == value)
					curr.parent.right = null;
				delete = true;
			}
		} else if (value < curr.value) {// left side
			delete = delete(value, curr.left);
			curr.height = Math.max(getNodeHeight(curr.left), getNodeHeight(curr.right)) + 1;
		} else {// right side
			delete = delete(value, curr.right);
			curr.height = Math.max(getNodeHeight(curr.left), getNodeHeight(curr.right)) + 1;
		}
		return delete;// its the root
	}

	// Change the list returned to a list of integers answering the requirements
	public static List<Integer> AVLTreeBacktrackingCounterExample() {
		IntegrityStatement.signature(); // Do not remove this line
		List<Integer> l = new LinkedList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		return l;
	}

}

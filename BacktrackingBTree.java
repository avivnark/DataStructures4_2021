import java.util.LinkedList;
import java.util.List;

public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {

	// You are to implement the function Backtrack.
	public void Backtrack() {
		IntegrityStatement.signature(); // Reminder!
		if (!backtrack.isEmpty()) {
			LinkedList<T> step = backtrack.removeFirst();
			T toRemove = step.remove();// the node we added
			Node<T> leafnode = this.getNode(toRemove);
			leafnode.removeKey(toRemove);// remove it regularly
			size=size-1;//updating the size
			while (!step.isEmpty()) {// returns all the nodes we splitted to their positions
				Merge(step.remove());
			}
			if (size() == 0)//tree is empty
				root = null;
		}
	}

	// function that merges nodes by middle value
	private Node<T> createAllNode(Node<T> left, T medianValue, Node<T> right) {
		Node<T> all = new Node<T>(null, left.numOfKeys + right.numOfKeys + 1);
		for (int i = 0; i < left.numOfKeys; i++) {
			all.addKey(left.getKey(i));
		}
		all.addKey(medianValue);
		for (int i = 0; i < right.numOfKeys; i++) {
			all.addKey(right.getKey(i));
		}
		return all;
	}

	// the opposite action of split
	private void Merge(T value) {
		Node<T> medianValue = this.getNode(value);
		Node<T> left = medianValue.getChild(getValuePosition(value, medianValue));
		Node<T> right = medianValue.getChild(getValuePosition(value, medianValue) + 1);
		Node<T> all = createAllNode(left, value, right);
		// add to all node the children
		for (int i = 0; i < left.getNumberOfChildren(); i++)
			all.addChild(left.getChild(i));
		for (int i = 0; i < right.getNumberOfChildren(); i++)
			all.addChild(right.getChild(i));
		// if the node doesn't have neighbors
		if (medianValue.getNumberOfKeys() == 1) {
			medianValue = all;
		} else {
			medianValue.removeChild(left);
			medianValue.removeChild(right);
			medianValue.removeKey(value);
			medianValue.addChild(all);
		}
		if (medianValue.parent == null)
			root = medianValue;
	}

	private int getValuePosition(T value, Node<T> node) {
		boolean found = false;
		int i = 0;
		while (i < node.numOfKeys && !found) {
			if (value.compareTo(node.getKey(i)) <= 0) {
				found = true;
			} else {
				i++;
			}
		}
		return i;
	}

	// Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample() {
		IntegrityStatement.signature(); // Reminder!
		List<Integer> l = new LinkedList<Integer>();
		l.add(3);
		l.add(2);
		l.add(4);
		l.add(1);
		return l;
	}

}

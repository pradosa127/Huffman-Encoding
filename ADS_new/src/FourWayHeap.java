import java.util.ArrayList;

public class FourWayHeap {
	public ArrayList<HuffmanNode> heapArray;
	public int numElements;

	public FourWayHeap() {
		numElements = 3;
		heapArray = new ArrayList<HuffmanNode>();
		HuffmanNode dummy = new HuffmanNode(Integer.MIN_VALUE, -1, null, null);
		heapArray.add(dummy);
		heapArray.add(dummy);
		heapArray.add(dummy);
	}

	public boolean isEmpty() {
		return (numElements) == 4;
	}

	public void add(HuffmanNode priority_level) {
		heapArray.add(priority_level);
		heapUp(numElements);
		numElements++;
	}

	public void heapUp(int index) {
		HuffmanNode lastElement = heapArray.get(index);
		int parentIndex = (index + 8) / 4;
		while ((index > 3) && (lastElement.frequency < heapArray.get(parentIndex).frequency)) {
			heapArray.set(index, heapArray.get(parentIndex));
			index = parentIndex;
			parentIndex = (parentIndex + 8) / 4;
		}
		heapArray.set(index, lastElement);
	}

	public HuffmanNode delete_min() {
		HuffmanNode min = heapArray.get(3);
		numElements--;
		heapArray.set(3, heapArray.get(numElements));
		heapDown(3);
		heapArray.remove(numElements);
		return min;
	}

	public HuffmanNode peek() {
		return heapArray.get(3);
	}

	public void heapDown(int index) {
		int maxChild = findMin((index * 4) - 8, (index * 4) - 5);
		HuffmanNode tempRoot = heapArray.get(index);
		while ((maxChild < numElements) && (heapArray.get(maxChild).frequency < tempRoot.frequency)) {
			heapArray.set(index, heapArray.get(maxChild));
			index = maxChild;
			maxChild = findMin(maxChild * 4 - 8, maxChild * 4 - 5);
		}
		heapArray.set(index, tempRoot);
	}

	public int findMin(int from, int to) {
		int maxChild = from;
		for (int i = from + 1; (i <= to && i < numElements); i++) {
			if (heapArray.get(maxChild).frequency > heapArray.get(i).frequency)
				maxChild = i;
		}
		return maxChild;
	}

	public static void main(String args[]) {
		FourWayHeap h = new FourWayHeap();

		// for (int i = 100; i > 0; i--)
		h.add(new HuffmanNode(0, 4, null, null));
		h.add(new HuffmanNode(999, 2, null, null));
		h.add(new HuffmanNode(34, 3, null, null));
		h.add(new HuffmanNode(2, 1, null, null));
		h.add(new HuffmanNode(2245, 4, null, null));
		h.add(new HuffmanNode(446, 2, null, null));

		// for (int i = 0; i < 10; i++)
		System.out.println(h.delete_min().frequency);
		System.out.println(h.delete_min().frequency);
		System.out.println(h.delete_min().frequency);
		System.out.println(h.delete_min().frequency);
		System.out.println(h.delete_min().frequency);
		System.out.println(h.delete_min().frequency);
	}
}
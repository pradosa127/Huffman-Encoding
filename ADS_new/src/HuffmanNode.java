public class HuffmanNode {
	        int data;
	        int frequency;
	        HuffmanNode left;
	        HuffmanNode right;

	        HuffmanNode(int data, int frequency,  HuffmanNode left,  HuffmanNode right) {
	            this.data = data;
	            this.frequency = frequency;
	            this.left = left;
	            this.right = right;
	        }
	        
	        HuffmanNode() {
	            this.data = -1;
	            this.frequency = -1;
	            this.left = null;
	            this.right = null;
	        }
	        
	        HuffmanNode(int data) {
	            this.data = data;
	            this.frequency = -1;
	            this.left = null;
	            this.right = null;
	        }
	        
	    }

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class encoder {
	
	

	      
	    private  class HuffManComparator implements Comparator<HuffmanNode> {
	        @Override
	        public int compare(HuffmanNode node1, HuffmanNode node2) {
	            return node1.frequency - node2.frequency;
	        }
	    }

	    /**
	     * Compresses the string using huffman algorithm.
	     * The huffman tree and the huffman code are serialized to disk
	     * 
	     * @param sentence                  The sentence to be serialized
	     * @throws FileNotFoundException    If file is not found
	     * @throws IOException              If IO exception occurs.
	     */ 
	    
	    public static void compress(String filename) throws FileNotFoundException, IOException {
	        if (filename == null) {
	            throw new NullPointerException("Input sentence cannot be null.");
	        }
	        if (filename.length() == 0) {
	            throw new IllegalArgumentException("The string should atleast have 1 character.");
	        }

	        ArrayList<Integer> list =new ArrayList<Integer>();
	        final Map<Integer, Integer> charFreq = getCharFrequency(filename,list);
	        /*for(Integer key : charFreq.keySet() )
			{
				System.out.println(key+"  "+charFreq.get(key));
			}	*/
	        
	        
	       /*long startTime1 = System.currentTimeMillis();
			 		     
		   for(int i = 0; i < 10; i++){ 
	       final HuffmanNode root1 = buildTreeUsingBinaryTree(charFreq);
	       }
		   long endTime1   = System.currentTimeMillis();			
		   long totalTime1 = (endTime1 - startTime1)/10;
		   System.out.println("time for binary tree:"+totalTime1);*/
	        final HuffmanNode root2 = buildTreeUsingBinaryTree(charFreq);
	      // final HuffmanNode root2 = buildTreeUsingFourwayHeap(charFreq);
	        
		  /*long startTime2 = System.currentTimeMillis();
			for(int i = 0; i < 10; i++){ 
			       final HuffmanNode root2 = buildTreeUsingFourwayHeap(charFreq);
			       }
				   long endTime2   = System.currentTimeMillis();			
				   long totalTime2 = (endTime2 - startTime2)/10;
					System.out.println("time for fourway tree:"+totalTime2);
					
		  long startTime3 = System.currentTimeMillis();
					for(int i = 0; i < 10; i++){ 
					       final HuffmanNode root3 = buildTreeUsingParingHeap(charFreq);
					       }
			long endTime3   = System.currentTimeMillis();			
			long totalTime3 = (endTime3 - startTime3)/10;
							System.out.println("time for pairing tree:"+totalTime3);	*/
	      
	        //printInorder(root);
	       // final HuffmanNode root3 = buildTreeUsingParingHeap(charFreq);
	        
	        
	       final Map<Integer, String> IntCode = generateCodes(charFreq.keySet(), root2);
	        /*for(Integer key : IntCode.keySet() )
			{
				System.out.println(key+"  "+IntCode.get(key));
			}	*/
	        
	      // codeTable(IntCode);
	        
	        //final String encodedMessage = encodeMessage(IntCode, filename);
	       // System.out.print(encodedMessage);
	        
	      // serializeMessage(encodedMessage);
	       
	       createEncodedFile(list,filename,IntCode);
	       
	    }
	    
	   
	    
	    private static Map<Integer, Integer> getCharFrequency(String filename,ArrayList<Integer> list) {
			Map<Integer, Integer> map=new HashMap<Integer,Integer>();
	       
	        try{
	    		FileInputStream fSteream=new FileInputStream(filename);
	    		DataInputStream in= new DataInputStream(fSteream);
	    		BufferedReader br =new BufferedReader(new InputStreamReader(in));
	    		String strLine;
	    		
	    		while((strLine=br.readLine())!=null)
	    		{
	    			if(strLine.trim().equals("")){
	    				continue;
	    			}
	    			
	    			int inp = Integer.parseInt(strLine);
	    			list.add(inp);
	    			//System.out.println(strLine);
	    			
	                if (!map.containsKey(inp)) {
	                    map.put(inp, 1);
	                } else {
	                    int val = map.get(inp);
	                    map.put(inp, ++val);
	                }
	    			    			
	    		}
	    		    		
	    	}catch(Exception e)
	    		{
	    			System.err.println("Error: " + e.getMessage());
	    		}
	        
	        return map; 
	            
	    }
	/***********************************************************************************************************/
	    /*private static HuffmanNode buildTree(Map<Integer, Integer> map) {
	        final Queue<HuffmanNode> nodeQueue = createNodeQueue(map);

	        while (nodeQueue.size() > 1) {
	            final HuffmanNode node1 = nodeQueue.remove();
	            final HuffmanNode node2 = nodeQueue.remove();
	            HuffmanNode node = new HuffmanNode(-1, node1.frequency + node2.frequency, node1, node2);
	            nodeQueue.add(node);
	        }

	        // remove it to prevent object leak.
	        return nodeQueue.remove();
	    }
	    
	    private static Queue<HuffmanNode> createNodeQueue(Map<Integer, Integer> map) {
	        final Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>(11, new HuffManComparator());
	        for (Entry<Integer, Integer> entry : map.entrySet()) {
	            pq.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
	        }
	        return pq;
	    }*/
	 /*******************************************************************************************************/   
	   public static HuffmanNode buildTreeUsingBinaryTree(Map<Integer, Integer> map) {
	        final BinaryHeap nodeQueue = createNodeQueue(map);
	        while (nodeQueue.size() > 1) {
	            final HuffmanNode node1 = nodeQueue.removeMin();
	            final HuffmanNode node2 = nodeQueue.removeMin();
	            HuffmanNode node = new HuffmanNode(0, node1.frequency + node2.frequency, node1, node2);
	            nodeQueue.add(node);
	        }
	        return nodeQueue.removeMin();
	    }
	    
	    private static BinaryHeap createNodeQueue(Map<Integer, Integer> map) {
	        final BinaryHeap pq = new BinaryHeap();
	        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
	            pq.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
	        }
	        return pq;
	    }
	    
	    
	  /*******************************************************************************************/
	    
	    public static HuffmanNode buildTreeUsingParingHeap(Map<Integer, Integer> map) {
	        final PairHeap nodeQueue = createNodeParingQueue(map);
	       //nodeQueue.inorder();
	       // System.out.println(nodeQueue.root.element.data);
	      while (nodeQueue.size>1) 
	       // for(int i=0;i<1;i++)
	        {
	            final HuffmanNode node1 = nodeQueue.removeMin();
	            //System.out.println(nodeQueue.root.element.data);
	            
	            final HuffmanNode node2 = nodeQueue.removeMin();
	            //System.out.println(nodeQueue.root.element.data);
	            HuffmanNode node = new HuffmanNode(0, node1.frequency + node2.frequency, node1, node2);
	            //printInorder(node);
	            nodeQueue.add(node);
	            //System.out.println(nodeQueue.root.element.data);
	        }   
	        
	      
	      return nodeQueue.removeMin();
	    }
	    
	    private static PairHeap createNodeParingQueue(Map<Integer, Integer> map) {
	        final PairHeap pq = new PairHeap();
	        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
	            pq.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
	        }
	      //  System.out.println(pq.size);
	        return pq;
	    }
	    
	    /*******************************************************************************************/ 
	    
	    public static HuffmanNode buildTreeUsingFourwayHeap(Map<Integer, Integer> map) {
	        final FourWayHeap nodeQueue = createNodeFourwayQueue(map);
	       //nodeQueue.inorder();
	       // System.out.println(nodeQueue.root.element.data);
	      while (nodeQueue.numElements>4) 
	      	        {
	            final HuffmanNode node1 = nodeQueue.delete_min();
	            //System.out.println(nodeQueue.root.element.data);
	            
	            final HuffmanNode node2 = nodeQueue.delete_min();
	            //System.out.println(nodeQueue.root.element.data);
	            HuffmanNode node = new HuffmanNode(0, node1.frequency + node2.frequency, node1, node2);
	            //printInorder(node);
	            nodeQueue.add(node);
	            
	        }   
	        
	      
	      return nodeQueue.delete_min();
	    }
	    
	    private static  FourWayHeap createNodeFourwayQueue(Map<Integer, Integer> map) {
	    	/*ArrayList<HuffmanNode> list=new ArrayList<HuffmanNode>();
	        
	        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
	            list.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
	        }
	       // final FourWayHeap pq = new FourWayHeap(list);
	       
	        return pq;*/
	    	
	    	
	    	final FourWayHeap pq = new FourWayHeap();
	        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
	            pq.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
	        }
	      //  System.out.println(pq.size);
	        return pq;
	    }
	    
	    
	    private static void writeBinaryFile(ArrayList<Integer> list ,Map<Integer, String> map) throws IOException {
	    	try{
	    	BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("encoded.bin"));
	    	ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
	    	StringBuilder number = new StringBuilder();
	    	int i=0;
	    	while (i<list.size()) {
	    	int index = list.get(i++);
	    	if (map.containsKey(index)) {
	    	String code = map.get(index);
	    	number.append(code);
	    	if(number.length()>=8){
	    	while(number.length()>=8){
	    	byte[] byteArray=getByteByString(number.substring(0, 8).toString());
	    	outputStream.write(byteArray);
	    	number=number.delete(0, 8);
	    	}
	    	}
	    	} else {
	    	System.out.println("not found in table");
	    	}
	    	}
	    	if (number.length() != 0) {
	    	byte[] byteArray = getByteByString(number.substring(0,8).toString());
	    	outputStream.write(byteArray);
	    	}
	    	byte[] byteArray = outputStream.toByteArray();
	    	bos.write(byteArray);
	    	bos.close();
	    	} catch (Exception e) {
	    	System.out.println(e);
	    	}
	    	}

	    public static byte[] getByteByString(String binaryString) {
	    	int splitSize = 8;
	    	if (binaryString.length() % splitSize == 0) {
	    	int index = 0;
	    	int position = 0;
	    	byte[] resultByteArray = new byte[binaryString.length() / splitSize];
	    	StringBuilder text = new StringBuilder(binaryString);
	    	while (index < text.length()) {
	    	String binaryStringChunk = text.substring(index, Math.min(index+splitSize, text.length()));
	    	Integer byteAsInt = Integer.parseInt(binaryStringChunk, 2);
	    	resultByteArray[position] = byteAsInt.byteValue();
	    	index += splitSize;
	    	position++;
	    	}
	    	return resultByteArray;
	    	} else {
	    	System.out.println("Invalid string length");
	    	return null;
	    	}
	    	}

	    	private static void createEncodedFile(ArrayList<Integer> iList, String path,Map<Integer, String> map) {
	    	try {
	    	writeBinaryFile(iList,map);

	    	} catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    	} catch (IOException e) {
	    	e.printStackTrace();
	    	}
	    	}


	    /**************************************************************************************/
	    private static  Map<Integer, String> generateCodes(Set<Integer> keys, HuffmanNode node) {
	        final Map<Integer, String> map = new HashMap<Integer, String>();
	        doGenerateCode(node, map, "");
	        return map;
	     }
	    
	    private  static void doGenerateCode(HuffmanNode node, Map<Integer, String> map, String s) {
	        if (node.left == null && node.right == null) {
	            map.put(node.data, s);
	            return;
	        		}
	        doGenerateCode(node.left, map, s + '0');
	        doGenerateCode(node.right, map, s + '1');
	        
	        }    
	    
	    
	    
	   private static void codeTable(Map<Integer, String> map) 
	   {
		   try{
			    File fileOne=new File("code_table.txt");
			    FileWriter fw=new FileWriter(fileOne);
			    PrintWriter pw=new PrintWriter(fw);

			    
			    for(Map.Entry<Integer,String> m :map.entrySet()){
		            pw.print(m.getKey()+" "+m.getValue()+"\n");
		        }

		        pw.flush();	        
		        pw.close();
		        fw.close();
			        
			    }catch(Exception e){}
	   }
	    
	   
	 
	    private static String encodeMessage(Map<Integer, String> IntCode, String filename) {
	        final StringBuilder stringBuilder = new StringBuilder();

	        try{
	    		FileInputStream fSteream=new FileInputStream(filename);
	    		DataInputStream in= new DataInputStream(fSteream);
	    		BufferedReader br =new BufferedReader(new InputStreamReader(in));
	    		String strLine;
	    		
	    		while((strLine=br.readLine())!=null)
	    		{
	    			if(strLine.trim().equals("")){
	    				continue;
	    			}
	    			
	    			int inp = Integer.parseInt(strLine);
	    			//System.out.println(strLine);
	    			 
	    		            stringBuilder.append(IntCode.get(inp));
	    		}
	    		    		
	    	}catch(Exception e)
	    		{
	    			System.err.println("Error: " + e.getMessage());
	    		}
	        
	           
	        return stringBuilder.toString();
	    }
	    
	 /********************************************************************************************************/   
	    private static void serializeMessage(String message) throws IOException {
	        final BitSet bitSet = getBitSet(message);
	        System.out.print(bitSet.size());
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("encoded.bin"))){

	            oos.writeObject(bitSet);
	        } 
	    }

	    private static BitSet getBitSet(String message) {
	        final BitSet bitSet = new BitSet();
	        int i = 0;
	        for (i = 0; i < message.length(); i++) {
	            if (message.charAt(i) == '0') {
	                bitSet.set(i, false);
	            } else {
	                bitSet.set(i, true);
	            }
	        }
	        bitSet.set(i, true); // dummy bit set to know the length 
	        
	        /*for (i = 0; i < message.length(); i++) {
	            if (bitSet.get(i) == true) {
	                System.out.print(1);
	            } else {
	            	System.out.print(0);
	            }
	        }*/
	        return bitSet;
	    }

	    /**********************************************************************************************************/
	    
	    
	  private  void printInorder(HuffmanNode node) 
		{
			if (node == null)
				return;
			printInorder(node.left);
			System.out.print(node.data + " ");
			printInorder(node.right);
		}
	    
	   
	
	 public static void main(String [] args)throws FileNotFoundException, IOException, ClassNotFoundException 
		{
		 
		// long startTime = System.currentTimeMillis();

		 String file=args[0];
		 encoder.compress(file);
	     System.out.print("success");
	   //  long endTime   = System.currentTimeMillis();			
	   //  long totalTime = endTime - startTime;
		// System.out.println(totalTime);
         		
	    	
		 
		}
}
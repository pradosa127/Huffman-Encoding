import java.io.BufferedReader;
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



public class decoder{
	
	      
	    public static void expand(String encodedFile,String code_table) throws FileNotFoundException, IOException,ClassNotFoundException {
	    	
	    	Map<Integer, String> map=readCodeTable(code_table);
	    	//print All data in MAP
	        /*for(Map.Entry<Integer,String> m :map.entrySet()){
	            System.out.println(m.getKey()+" : "+m.getValue());
	        }*/
	       HuffmanNode root= generateHuffmanTree(map);
	       //printInorder(root);
	       decodeMessage(root,encodedFile);
	    	
	     //  System.out.println(message);
	    }
	    
	   
	
	   
	  private static Map<Integer,String> readCodeTable(String filename)
	  {
		  try{
		        File toRead=new File(filename);
		        FileInputStream fis=new FileInputStream(toRead);
		        Scanner sc=new Scanner(fis);
		        Map<Integer,String> mapInFile=new HashMap<Integer,String>();
		     	//read data from file line by line:
		        String currentLine;
		        while(sc.hasNextLine()){
		            currentLine=sc.nextLine();
		            StringTokenizer st=new StringTokenizer(currentLine," ");
		            //put tokens ot currentLine in map
		            mapInFile.put(Integer.parseInt(st.nextToken()),st.nextToken());
		        }
		        fis.close();
		                     
		        return mapInFile;
		    }catch(Exception e){}
		  
		  return null;
	  }
	 
	   
	    private static HuffmanNode generateHuffmanTree(Map<Integer, String> map) {
	        HuffmanNode root=new HuffmanNode();
	            for(Map.Entry<Integer,String> entry :map.entrySet()){
	        	int key=entry.getKey();
	        	String value=entry.getValue();
	        	
	        	HuffmanNode node=root;
	        	for(int i=0;i<value.length();i++)
	        	{
	        		if(i<value.length()-1)
	        		{
	        			if(value.charAt(i)=='0' && node.left==null)
	        			{
	        				HuffmanNode node1=new HuffmanNode();
	        				node.left=node1;
	        				node=node.left;
	        			}
	        			else if(value.charAt(i)=='0' && node.left!=null)
	        			{
	        				node=node.left;
	        			}
	        			else if(value.charAt(i)=='1' && node.right==null)
	        			{
	        		
		        			HuffmanNode node1=new HuffmanNode();
		        			node.right=node1;
		        			node=node.right;
		        		}
		        		else
		        		{
		        			node=node.right;
		        		}
	        		}	
		        	else
		        	{
		        		if(value.charAt(i)=='0' && node.left==null)
		        		{
		        			HuffmanNode node1=new HuffmanNode(key);
		        			node.left=node1;
		            	}
		        		else if(value.charAt(i)=='0' && node.left!=null)
		        		{
		        			node=node.left;
		        		}
		        		else if(value.charAt(i)=='1' && node.right==null)
		        		{
		        		
		        			HuffmanNode node1=new HuffmanNode(key);
		        			node.right=node1;
		        			
		        		}
		        		
		        	}
	        		
	        	}
	      
	        }
	        
	         return root;   
	   }    
	    
	  private  void printInorder(HuffmanNode node) 
		{
			if (node == null)
				return;
			printInorder(node.left);
			System.out.print(node.data + " ");
			printInorder(node.right);
		}
	  
	  private static void decodeMessage(HuffmanNode node, String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
	      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
	    	    
	          final BitSet bitSet = (BitSet) ois.readObject();
	          File fileOne=new File("decoded.txt");
			  FileWriter fw=new FileWriter(fileOne);
			  PrintWriter pw=new PrintWriter(fw);
	         // final StringBuilder stringBuilder = new StringBuilder();
	          for (int i = 0; i < (bitSet.length() - 1);) {
	              HuffmanNode temp = node;
	              // since huffman code generates full binary tree, temp.right is certainly null if temp.left is null.
	              while (temp.left != null) {
	                  if (!bitSet.get(i)) {
	                      temp = temp.left;
	                  } else {
	                      temp = temp.right;
	                  }
	                  i = i + 1;
	             }
	                                     		    
	  		        pw.print(temp.data+"\n");
	          }

	  	        pw.flush();	        
	  	        pw.close();
	  	        fw.close();
	              
	          }
	         // return stringBuilder.toString();
	      }
	
	 public static void main(String [] args)throws FileNotFoundException, IOException, ClassNotFoundException 
		{
	    	 long startTime = System.currentTimeMillis();
					
	    		String encoded=args[0];
	    		String codetable=args[1];
	    	    expand(encoded,codetable);
	    		System.out.print("success");
	    		
	    		long endTime   = System.currentTimeMillis();			
	   	     long totalTime = endTime - startTime;
	   		 System.out.println(totalTime);
					  		
		}
	     
		
}
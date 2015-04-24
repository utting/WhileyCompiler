package wyopcl.util;

import java.util.HashMap;

import wyil.lang.CodeBlock;
import wyil.lang.Codes;

/**
 * Stores the symbol information where each label relates to its line number in a block.
 * It also can map a block and a label name to the corresponding line number. The table is
 * implemented with a nested HashMap. 
 * @author Min-Hsien Weng
 * 
 */
public class SymbolTable{
	
	private HashMap<CodeBlock, HashMap<String, Integer>> labelLocMap;		
	private String catchlabel = "";

	public SymbolTable(){
		labelLocMap = new HashMap<CodeBlock, HashMap<String, Integer>>();
	}	
	/**
	 * Adds the label name in a block associated with its line number.	
	 * @param blk the block
	 * @param label the name of label
	 * @param line the line number
	 */
	public void addLabelLoc(CodeBlock blk, String label, int line){
		HashMap<String, Integer> map;
		if(labelLocMap.containsKey(blk)){
			map = labelLocMap.get(blk);
		}else{
			map = new HashMap<String, Integer>();
		}
		
		map.put(label, line);
		labelLocMap.put(blk, map);
	}
	
	public int getCodeBlockPosByLabel(CodeBlock blk, String label){
		if(labelLocMap.containsKey(blk)){
			HashMap<String, Integer> map = labelLocMap.get(blk);
			if(map.containsKey(label)){
				return map.get(label);
			}	
		}
		
		return -1;
	}
	
	public int getCatchPos(CodeBlock blk){
		return getCodeBlockPosByLabel(blk, catchlabel);
	}
	
}
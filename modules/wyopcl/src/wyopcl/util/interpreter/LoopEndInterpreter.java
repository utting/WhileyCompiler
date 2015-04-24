package wyopcl.util.interpreter;
import wyil.lang.Code;
import wyil.lang.Codes;
import wyopcl.util.Interpreter;
import wyopcl.util.StackFrame;

/**
 * Interprets <code>Codes.LoopEnd</code> bytecode.
 * @author Min-Hsien Weng
 * @see wyil.lang.Codes.LoopEnd
 *
 */
public class LoopEndInterpreter extends Interpreter {
	private static LoopEndInterpreter instance;	
	private LoopEndInterpreter(){}
	
	/*Implement the Singleton pattern to ensure this class has one instance.*/
	public static LoopEndInterpreter getInstance(){
		if (instance == null){
			instance = new LoopEndInterpreter();
		}
		return instance;
	}
	
}

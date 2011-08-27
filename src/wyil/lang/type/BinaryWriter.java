package wyil.lang.type;

import java.io.IOException;

import wyil.lang.NameID;
import wyil.lang.Type;
import wyil.util.Pair;
import wyjvm.io.BinaryOutputStream;

/**
 * The BinaryBuilder converts a type into a short binary string.
 * 
 * @author djp
 * 
 */
public class BinaryWriter implements Builder {		
	private final BinaryOutputStream writer;	
	
	public BinaryWriter(BinaryOutputStream writer) {
		this.writer = writer;			
	}
	
	public void initialise(int numNodes) {
		try {				
			writeLength(numNodes);
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildPrimitive(int index, Type.Leaf t) {
		try {
			if(t == Type.T_ANY) {
				writeKind(Node.K_ANY );
			} else if(t == Type.T_VOID) {
				writeKind(Node.K_VOID);
			} else if(t == Type.T_NULL) {
				writeKind(Node.K_NULL);
			} else if(t == Type.T_BOOL) {
				writeKind(Node.K_BOOL);			
			} else if(t == Type.T_BYTE) {			
				writeKind(Node.K_BYTE);		
			} else if(t == Type.T_CHAR) {			
				writeKind(Node.K_CHAR);		
			} else if(t == Type.T_INT) {			
				writeKind(Node.K_INT);		
			} else if(t == Type.T_REAL) {
				writeKind(Node.K_RATIONAL);			
			} else if(t == Type.T_STRING) {
				writeKind(Node.K_STRING);			
			} else {
				throw new RuntimeException("unknown type encountered: " + t);		
			}
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}			
	}

	public void buildExistential(int index, NameID name) {
		try {
			writeKind(Node.K_EXISTENTIAL);				
			writeIdentifier(name.module().toString());
			writeIdentifier(name.name());				
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildSet(int index, int element) {
		try {
			writeKind(Node.K_SET);			
			writeNode(element);
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildList(int index, int element) {
		try {
			writeKind(Node.K_LIST);
			writeNode(element);
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildProcess(int index, int element) {
		try {
			writeKind(Node.K_PROCESS);	
			writeNode(element);				
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildDictionary(int index, int key, int value) {
		try {
			writeKind(Node.K_DICTIONARY);
			writeNode(key);
			writeNode(value);
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildTuple(int index, int... elements) {
		try {
			writeKind(Node.K_TUPLE);
			writeLength(elements.length);
			for(int e : elements) {					
				writeNode(e);					
			}	
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildRecord(int index, Pair<String, Integer>... fields) {
		try {				
			writeKind(Node.K_RECORD);
			// FIXME: bug here if number of entries > 64K
			writeLength(fields.length);
			for(Pair<String,Integer> p : fields) {
				writeIdentifier(p.first());										
				writeNode(p.second());					
			}			
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}

	public void buildFunction(int index, int ret,
			int... parameters) {
		try {				
			writeKind(Node.K_FUNCTION);				
			writeNode(ret);
			writeLength(parameters.length);
			for (int p : parameters) {
				writeNode(p);
			}
		} catch (IOException e) {
			throw new RuntimeException("internal failure", e);
		}
	}

	public void buildMethod(int index, int receiver, int ret,
			int... parameters) {
		try {				
			if(receiver == -1) {
				// headless method
				writeKind(HEADLESS_METH_TYPE);					
			} else {
				writeKind(Node.K_METHOD);
				writeNode(receiver);
			}
			writeNode(ret);
			writeLength(parameters.length);
			for (int p : parameters) {
				writeNode(p);
			}
		} catch (IOException e) {
			throw new RuntimeException("internal failure", e);
		}
	}
	
	public void buildUnion(int index, int... bounds) {
		try {				
			writeKind(Node.K_UNION);			
			writeLength(bounds.length);
			for(int b : bounds) {
				writeNode(b);
			}	
		} catch(IOException e) {
			throw new RuntimeException("internal failure",e);
		}
	}
	
	protected void writeKind(int kind) throws IOException {
		writer.write_un(kind,5);
	}
	
	protected void writeLength(int len) throws IOException {
		writer.write_uv(len);
	}
	
	protected void writeNode(int node) throws IOException {
		writer.write_uv(node);			
	}
	
	/**
	 * An identifier is a string made up of characters from
	 * [A-Za-z_][A-Za-z0-9_]*
	 * 
	 * @param identifier
	 * @throws IOException
	 */
	protected void writeIdentifier(String id) throws IOException {			
		writeLength(id.length());
		for(int i=0;i!=id.length();++i) {
			writer.write_un(id.charAt(i),7);
		}
	}		
}	

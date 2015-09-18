// Copyright (c) 2012, David J. Pearce (djp@ecs.vuw.ac.nz)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//    * Redistributions of source code must retain the above copyright
//      notice, this list of conditions and the following disclaimer.
//    * Redistributions in binary form must reproduce the above copyright
//      notice, this list of conditions and the following disclaimer in the
//      documentation and/or other materials provided with the distribution.
//    * Neither the name of the <organization> nor the
//      names of its contributors may be used to endorse or promote products
//      derived from this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL DAVID J. PEARCE BE LIABLE FOR ANY
// DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package wyil.builders;

import java.util.HashMap;

import wycc.util.Pair;
import wycs.syntax.Expr;
import wyil.lang.Type;

/**
 * Provides a global environment which is shared across all branches. This
 * provides a single authority for allocating variable versions and their types.
 * 
 * @author David J. Pearce
 *
 */
public class VcEnvironment {
	/**
	 * A fixed list of variable "prefixes". These are the names given to each
	 * register slot, and which are then appended with their SSA number to form
	 * an actual register name. Note that the prefixes are fixed for the entire
	 * branch graph of a function / method.
	 */
	private final String[] prefixes;
	
	/**
	 * For each variable we maintain the current "version". This is an integer
	 * value which is used to determine the appropriate SSA number for a given
	 * variable.
	 */
	final int[] versions;
	
	/**
	 * Maps variable names to their internal index.
	 */
	private final HashMap<String,Pair<Type, Expr>> variables;
	
	/**
	 * Construct the master verification branch for a given attributed code
	 * block. The pc for the master branch of a block is the root index (i.e.
	 * the branch begins at the entry of the block).
	 *
	 * @param numInputs
	 *            --- the minimum number of register slots required
	 * @param prefixes
	 *            --- Variable names to use as prefixes when generating register
	 *            names. If null, the default names are used instead.
	 */
	public VcEnvironment(int numInputs, String[] prefixes) {
		int numSlots = numInputs;
		this.variables = new HashMap<String,Pair<Type, Expr>>();
		this.versions = new int[numSlots];
		
		if (prefixes == null) {
			// Construct default variable prefixes if none are given.
			this.prefixes = new String[numSlots];
			for (int i = 0; i != numSlots; ++i) {
				this.prefixes[i] = "r" + i;
			}
		} else {
			this.prefixes = prefixes;
		}
	}

	public int numVariables() {
		return versions.length;
	}
	
	public String write(int register, Type type, Expr expr) {
		versions[register] = versions[register] + 1;
		String name = prefixes[register] + "$" + register;
		variables.put(name, new Pair<Type, Expr>(type, expr));
		return name;
	}
	
	public String havoc(int register, Type type) {
		versions[register] = versions[register] + 1;
		String name = prefixes[register] + "$" + register;
		Expr.Variable var = new Expr.Variable(name);
		variables.put(name, new Pair<Type, Expr>(type, var));
		return name;
	}
	
	public Pair<Type,Expr> read(String name) {
		return variables.get(name);
	}
	
	
}

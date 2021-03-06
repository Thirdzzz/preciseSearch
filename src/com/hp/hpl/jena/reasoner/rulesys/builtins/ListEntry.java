/******************************************************************
 * File:        ListEntry.java
 * Created by:  Dave Reynolds
 * Created on:  13-May-2005
 * 
 * (c) Copyright 2005, Hewlett-Packard Development Company, LP
 * [See end of file]
 * $Id: ListEntry.java,v 1.4 2008/01/02 12:06:20 andy_seaborne Exp $
 *****************************************************************/

package com.hp.hpl.jena.reasoner.rulesys.builtins;

import com.hp.hpl.jena.reasoner.rulesys.*;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.graph.*;

/**
 * listEntry(?list, ?index, ?val) will bind ?val to the ?index'th entry
 * in the RDF list ?list. If there is no such entry the variable will be unbound
 * and the call will fail. Only useable in rule bodies.
 * 
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.4 $ on $Date: 2008/01/02 12:06:20 $
 */
public class ListEntry extends BaseBuiltin {

    /**
     * Return a name for this builtin, normally this will be the name of the 
     * functor that will be used to invoke it.
     */
    public String getName() {
        return "listEntry";
    }
    
    /**
     * Return the expected number of arguments for this functor or 0 if the number is flexible.
     */
    public int getArgLength() {
        return 3;
    }

    /**
     * This method is invoked when the builtin is called in a rule body.
     * @param args the array of argument values for the builtin, this is an array 
     * of Nodes, some of which may be Node_RuleVariables.
     * @param length the length of the argument list, may be less than the length of the args array
     * for some rule engines
     * @param context an execution context giving access to other relevant data
     * @return return true if the buildin predicate is deemed to have succeeded in
     * the current environment
     */
    public boolean bodyCall(Node[] args, int length, RuleContext context) {
        checkArgs(length, context);
        BindingEnvironment env = context.getEnv();
        Node list = getArg(0, args, context);
        Node index = getArg(1, args, context);
        if ( ! Util.isNumeric(index) )  return false;
        Node elt = getEntry(list, Util.getIntValue(index), context);
        if (elt == null) {
            return false;
        } else {
            env.bind(args[2], elt);
            return true;
        }
    }
    
    /**
     * Return the i'th element of the list, starting from 0
     * @param list the start of the list
     * @param i the element to return
     * @param context the context through which the data values can be found
     * @return the entry or null if the there isn't such an entry
     */
    protected static Node getEntry(Node list, int i, RuleContext context ) {
        int count = 0;
        Node node = list;
        while (node != null && !node.equals(RDF.Nodes.nil)) {
            if (count == i) {
                return Util.getPropValue(node, RDF.Nodes.first, context);
            } else {
                node = Util.getPropValue(node, RDF.Nodes.rest, context);
                count++;
            }
        }
        return null;
    }
    
}

/*
    (c) Copyright 2005, 2006, 2007, 2008 Hewlett-Packard Development Company, LP
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

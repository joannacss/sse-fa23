import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.ipa.callgraph.*;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.config.FileOfClasses;
import viz.CFGVisualizer;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarFile;

/**
 * Example coded in class - Lecture 14.
 * Walked through WALA's main data structures and how to create call graphs.
 *
 * @author Joanna C. S. Santos
 */
public class LiveExampleL14 {

    private static void printCallGraph(CallGraph cg, String callgraphName) {
        System.out.println("================ " + callgraphName + " call graph ===================");
        System.out.println(CallGraphStats.getStats(cg));
        System.out.println("Call Graph (application scope only): ");
        for (CGNode node : cg) {
            // only prints the nodes & edges in the application scope
            if (node.getMethod().getDeclaringClass().getClassLoader().getReference().equals(ClassLoaderReference.Application)) {

                if (cg.getSuccNodeCount(node) > 0)
                    System.out.println(node.getMethod().getSignature());

                cg.getSuccNodes(node).forEachRemaining(succ -> {
                    System.out.println("  -> " + succ.getMethod().getSignature());
                });
            }
        }
        System.out.println("===================================================");
    }

    public static void main(String[] args) throws Exception {

        // TODO: create the analysis scope


        // TODO: create the class hierarchy


        // TODO: print the number of classes in the class hierarchy



        // TODO: create the CHA call graph



        // TODO: create the RTA call graph


        // TODO: create the 1-CFA call graph


        // TODO: print the IR of the main method

    }
}
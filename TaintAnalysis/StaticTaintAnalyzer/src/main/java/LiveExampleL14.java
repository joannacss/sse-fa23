import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.ipa.callgraph.*;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.config.FileOfClasses;
import viz.CFGVisualizer;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
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
        AnalysisScope scope = AnalysisScope.createJavaAnalysisScope();
        String appPath = LiveExampleL14.class.getResource("Example1.jar").getPath();
        scope.addToScope(ClassLoaderReference.Application, new JarFile(appPath));
        String primordialPath = LiveExampleL14.class.getResource("jdk-17.0.1/rt.jar").getPath();
        scope.addToScope(ClassLoaderReference.Primordial, new JarFile(primordialPath));
        String exclusionsPath = LiveExampleL14.class.getResource("Java60RegressionExclusions.txt").getPath();
        scope.setExclusions(new FileOfClasses( new FileInputStream(exclusionsPath)));
        System.out.println("Analysis scope: " + scope);

        // TODO: create the class hierarchy
        ClassHierarchy classHierarchy = ClassHierarchyFactory.make(scope);


        // TODO: print the number of classes in the class hierarchy
        System.out.println("Class hierarchy has " + classHierarchy.getNumberOfClasses() + " classes");



        // TODO: create the CHA call graph
        CHACallGraph chaCG = new CHACallGraph(classHierarchy);
        Iterable<Entrypoint> entrypoints = Util.makeMainEntrypoints(scope, classHierarchy);
        chaCG.init(entrypoints);
        printCallGraph(chaCG, "CHA");

        // TODO: create the RTA call graph
        AnalysisOptions options = new AnalysisOptions();
        options.setEntrypoints(entrypoints);
        CallGraphBuilder<InstanceKey> rtaBuilder = Util.makeRTABuilder(options, new AnalysisCacheImpl(), classHierarchy, scope);
        CallGraph rtaCG = rtaBuilder.makeCallGraph(options, null);
        printCallGraph(rtaCG, "RTA");

        // TODO: create the 1-CFA call graph
        SSAPropagationCallGraphBuilder nCfaBuilder = Util.makeNCFABuilder(1, options, new AnalysisCacheImpl(), classHierarchy, scope);
        CallGraph nCfaCG = nCfaBuilder.makeCallGraph(options, null);
        printCallGraph(nCfaCG, "1-CFA");


        // TODO: print the IR of the main method
        System.out.println("IR of the main method: ");
        CGNode mainNode = nCfaCG.getEntrypointNodes().iterator().next();
        IR ir = mainNode.getIR();

        System.out.println(ir);


    }
}

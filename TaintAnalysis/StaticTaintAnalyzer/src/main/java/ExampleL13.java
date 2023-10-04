import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.types.TypeName;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.config.FileOfClasses;

import java.io.FileInputStream;
import java.util.jar.JarFile;

/**
 * Example coded in class - Lecture 13.
 * Walked through WALA's main data structures and how to create a CHA call graph.
 *
 * @author Joanna C. S. Santos
 */
public class ExampleL13 {
    public static void main(String[] args) throws Exception {
        // TODO: get paths for jar file, exclusions file, and JRE
        String jarFilePath = LiveExampleL13.class.getResource("Example1.jar").getPath();
        String exFilePath = LiveExampleL13.class.getResource("Java60RegressionExclusions.txt").getPath();
        String jrePath = LiveExampleL13.class.getResource("jdk-17.0.1/rt.jar").getPath();

        // TODO: create the analysis scope
        AnalysisScope scope = AnalysisScope.createJavaAnalysisScope();
        scope.addToScope(ClassLoaderReference.Application, new JarFile(jarFilePath));
        scope.addToScope(ClassLoaderReference.Primordial, new JarFile(jrePath));
        scope.setExclusions(new FileOfClasses(new FileInputStream(exFilePath)));

        // TODO: create the class hierarchy
        IClassHierarchy classHierarchy = ClassHierarchyFactory.make(scope);

        // TODO: print the number of classes in the class hierarchy
        System.out.println("Number of classes: " + classHierarchy.getNumberOfClasses());
        for (IClass iClass : classHierarchy) {
            if (iClass.getClassLoader().getReference().equals(ClassLoaderReference.Application))
                System.out.println(iClass.getName());
        }
        TypeReference rectangle = TypeReference.findOrCreate(ClassLoaderReference.Application, TypeName.findOrCreate("LRectangle"));
        IClass k = classHierarchy.lookupClass(rectangle);

        // TODO: create the CHA call graph
        CHACallGraph callGraph = new CHACallGraph(classHierarchy, true);
        Iterable<Entrypoint> entrypoints = Util.makeMainEntrypoints(scope, classHierarchy);
        callGraph.init(entrypoints);
        System.out.println(callGraph);

    }
}

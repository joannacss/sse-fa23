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
public class LiveExampleL13 {
    public static void main(String[] args) throws Exception {
        // TODO: get paths for jar file, exclusions file, and JRE


        // TODO: create the analysis scope


        // TODO: create the class hierarchy


        // TODO: print the number of classes in the class hierarchy


        // TODO: create the CHA call graph


    }
}

package edu.cmu.deiis.types;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceProcessException;


public class MyCASConsumer extends CasConsumer_ImplBase {

  BufferedWriter bout = null;
  HashSet<String> output = null;
  /**
   * initialize bufferedwriter to write to outputfile
   */
  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    String name = (String)getConfigParameterValue("OutputPath");
    File file = new File(name);
    try {
      bout = new BufferedWriter(new FileWriter(file));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      throw new UIMARuntimeException(e);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      throw new UIMARuntimeException(e);
    }
    output = new HashSet<String>();
  }
  
  /**
   * @param arg0 CAS object
   * write output to file using bufferedwriter 
   */
  @Override
  public void processCas(CAS arg0) throws ResourceProcessException {
    JCas jcas = null;
    try {
      jcas = arg0.getJCas();
    } catch (CASException e) {
      throw new UIMARuntimeException(e);
    }
    try {
      FSIterator iter = jcas.getJFSIndexRepository().getAllIndexedFS(SentenceAnnotation.type);
      String sentId = ((SentenceAnnotation) iter.next()).getSentID();
      
      iter = jcas.getJFSIndexRepository().getAllIndexedFS(NEAnnotation.type);
      while(iter.hasNext()) {
        NEAnnotation ne = (NEAnnotation)iter.next();
        String str = sentId + "|" + ne.getBegin() + " " + ne.getEnd() + "|" + ne.getNamedEntity() + " " + "\n";
        if(!output.contains(str)) {
          bout.write(str);
          output.add(str);
        }
      }
      bout.flush();
    } catch(Exception e) {
      throw new UIMARuntimeException(e);
    }
  }
}

package edu.cmu.deiis.types;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;


public class MyCollectionReader extends CollectionReader_ImplBase {

  BufferedReader br = null;
  /**
   * initialize bufferedreader to read from file
   */
  public void initialize() {
    File file = new File( this.getClass().getClassLoader().getResource( (String)getConfigParameterValue("InputPath")).getPath());
    try {
      br = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      throw new UIMARuntimeException(e);
    }
  }
  
  /**
   * @param aCas CAS object
   * get next sentence from file and prepare a cas for it
   */
  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    // TODO Auto-generated method stub
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    try {
      String line = br.readLine();
      String comp[] = line.split(" ", 2);
      SentenceAnnotation s = new SentenceAnnotation(jcas);
      s.setSentID(comp[0]);
      s.setSentence(comp[1]);
      s.addToIndexes();
    } catch(Exception e) {
      throw new UIMARuntimeException(e);
    }
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * return true if bufferedreader is not empty
   */
  @Override
  public boolean hasNext() throws IOException, CollectionException {
    // TODO Auto-generated method stub
    return br.ready();
  }
}

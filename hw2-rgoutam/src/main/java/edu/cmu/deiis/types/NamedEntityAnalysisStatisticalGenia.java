package edu.cmu.deiis.types;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.ConfidenceChunker;
import com.aliasi.util.AbstractExternalizable;


public class NamedEntityAnalysisStatisticalGenia extends JCasAnnotator_ImplBase {
  ConfidenceChunker model;
  
  /**
   * read the NER model file
   */
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    // TODO Auto-generated method stub
    try {
      File f = new File(this.getClass().getClassLoader().getResource((String)aContext.getConfigParameterValue("ModelName")).getFile());
      model = (ConfidenceChunker) AbstractExternalizable.readObject(f);
    } catch(Exception e) {
      throw new UIMARuntimeException(e);
    }
  }
  
  /**
   * @param arg0 CAS Object
   * process sentences to get named entities
   */
  @Override
  public void process(JCas arg0) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    int MAX_N_BEST = 10;
    FSIterator iter = arg0.getJFSIndexRepository().getAllIndexedFS(SentenceAnnotation.type);
    SentenceAnnotation sent = (SentenceAnnotation) iter.next();
    Iterator<Chunk> it = model.nBestChunks(sent.getSentence().toCharArray(), 0, sent.getSentence().length(), MAX_N_BEST);
    
    while(it.hasNext()) {
      Chunk c = it.next();
      NEAnnotation ne = new NEAnnotation(arg0);
      ne.setBegin(countChar((String)sent.getSentence(), c.start()));
      ne.setEnd(countChar((String)sent.getSentence(), c.end()) - 1);
      ne.setNamedEntity((String)sent.getSentence().substring(c.start(), c.end()));
      ne.setConfidence(Math.pow(2.0, c.score()));
      if(ne.getConfidence() >= 0.5)
        ne.addToIndexes();
    }
  }
  
  /**
   * count the number of non-space characters in text till index
   * @param text 
   * @param index
   * @return
   */
  private static int countChar(String text, int index) {
    int ret = 0;
    for(int i = 0; i < index; i++) {
      if(text.charAt(i) != ' ')
        ret++;
    }
    return ret;
  }

}

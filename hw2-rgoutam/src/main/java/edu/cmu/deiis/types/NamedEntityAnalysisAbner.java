package edu.cmu.deiis.types;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UIMARuntimeException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import abner.Tagger;

/**
 * Analysis engine that uses ABNER NER library
 * @author rgoutam
 *
 */
public class NamedEntityAnalysisAbner extends JCasAnnotator_ImplBase {
  Tagger model;
  String AnalysisID = "Abner";
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    // TODO Auto-generated method stub
    try {
      model = new Tagger();
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
    FSIterator iter = arg0.getJFSIndexRepository().getAllIndexedFS(SentenceAnnotation.type);
    SentenceAnnotation sent = (SentenceAnnotation) iter.next();
    String sentText = sent.getSentence();
    String[][] entities = model.getEntities(sentText);
    //System.out.println(sentText);
    
    for(String ner : entities[0]) {
      //System.out.println("ner before : " + ner);
      ner = ner.replaceAll(" \\( ", "\\(");
      ner = ner.replaceAll(" \\) ", "\\)");
      ner = ner.replaceAll(" \\. ", "\\.");
      ner = ner.replaceAll(" : ", ":");
      ner = ner.replaceAll(" , ", ",");
      //System.out.println("ner : " + ner);
      Pattern p = Pattern.compile(Pattern.quote(ner));
      Matcher m = p.matcher(sentText);
      
      while(m.find()) {
        int beginIndex = m.start();
        int endIndex = beginIndex + ner.length();
        NEAnnotation ne = new NEAnnotation(arg0);
        ne.setBegin(countChar((String)sent.getSentence(), beginIndex));
        ne.setEnd(countChar((String)sent.getSentence(), endIndex) - 1);
        ne.setNamedEntity((String)sent.getSentence().substring(beginIndex, endIndex));
        ne.setCasProcessorId(AnalysisID);
        String str = ne.getNamedEntity();
        if(str.length() > 8 && str.split(" ").length < 3 && str.indexOf('(') == -1 && str.indexOf(')') == -1)
          ne.addToIndexes();
      }
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasisentimentandlexicalwithstopword;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentModel;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 *
 * @author
 */
public class Extraction {

    public List<String> getNgrams(int n, String sentence) {
        List<String> ngrams = new ArrayList<String>();
        
        String[] tokenz = getTokenz(sentence);
        String[] words  = new String[tokenz.length];
            
        int j = 0;
            for(String tok : tokenz){
                String[] tos = tok.split("_");
                String word = tos[0].trim();
                
                words[j] = word;
                j++;
            }

        for (int i = 0; i < words.length - n + 1; i++) {
            ngrams.add(concat(words, i, i + n));
        }
        return ngrams;
    }

    
    private String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append((i > start ? " " : "") + words[i]);
        }
        return sb.toString();
    }
    
        //mendapatkan elemen token dari sebuah kalimat / kata
    private String[] getTokenz(String sentence){
        StringTokenizer st1 = new StringTokenizer(sentence);
           int numberOfElements = st1.countTokens();
           String[] tokenz = new String[numberOfElements];
           int index = 0;
           
            while (st1.hasMoreTokens()) {
                    String element = st1.nextToken();
                    tokenz[index] = element;
                    index++;
                }
            
        return tokenz;
    }
    
//    public String getSentenceWithOutTag(String sentence){
//        String[] tokenz = getTokenz(sentence);
//            
//            String sentenceWithOutTag = MainPage.BLANK;
//            
//            for(String tok : tokenz){
//                String[] tos = tok.split("_");
//                String word = tos[0].trim();
//                sentenceWithOutTag += word + MainPage.SPASI;
//            }
//            
//            sentenceWithOutTag= sentenceWithOutTag.trim();
//            
//            return sentenceWithOutTag;
//    }
    
    public List<String> getAdverbs(String sentence){
        List<String> listAdverbs = new ArrayList<String>();
        
        String[] tokenz = getTokenz(sentence);
            
            for(String tok : tokenz){
                String[] tos = tok.split("_");
                String word = tos[0].trim();
                String tag = tos[1].trim();
                
                if(tag.toUpperCase().equals("RB") ||
                        tag.toUpperCase().equals("RBR") ||
                            tag.toUpperCase().equals("RBS")) {
                    listAdverbs.add(word);
                }
            }
            
            return listAdverbs;
    }
    
    
    public List<String> getVerbs(String sentence){
        List<String> listVerbs = new ArrayList<String>();
        
        String[] tokenz = getTokenz(sentence);
            
            for(String tok : tokenz){
                String[] tos = tok.split("_");
                String word = tos[0].trim();
                String tag = tos[1].trim();
                
                if(tag.toUpperCase().equals("VB") ||
                        tag.toUpperCase().equals("VBD") ||
                            tag.toUpperCase().equals("VBG") ||
                                tag.toUpperCase().equals("VBN") ||
                                    tag.toUpperCase().equals("VBP") ||
                                        tag.toUpperCase().equals("VBZ")) {
                    listVerbs.add(word);
                }
            }
            
            return listVerbs;
    }
    
    public List<String> getModalAuxiliary(String sentence){
        List<String> listModalAuxs = new ArrayList<String>();
        
        String[] tokenz = getTokenz(sentence);
        
        int i = 0;
            for(String tok : tokenz){
                if(i >= tokenz.length - 1){
                    return listModalAuxs;
                }
                
                String[] tos = tok.split("_");
                String word = tos[0].trim();
                String tag = tos[1].trim();
                
                if(tag.toUpperCase().equals("VB") ||
                        tag.toUpperCase().equals("VBD") ||
                            tag.toUpperCase().equals("VBG") ||
                                tag.toUpperCase().equals("VBN") ||
                                    tag.toUpperCase().equals("VBP") ||
                                        tag.toUpperCase().equals("VBZ")) {
                    String[] tos1 = tokenz[i+1].split("_");
                    String word1 = tos1[0].trim();
                    String tag1 = tos1[1].trim();
                    
                    if(tag1.toUpperCase().equals("VB") ||
                        tag1.toUpperCase().equals("VBD") ||
                            tag1.toUpperCase().equals("VBG") ||
                                tag1.toUpperCase().equals("VBN") ||
                                    tag1.toUpperCase().equals("VBP") ||
                                        tag1.toUpperCase().equals("VBZ")) {
                        listModalAuxs.add(word);
                    }
                }
                
                i++;
            }
            
            return listModalAuxs;
    }
    
    
    public List<String> getNonNouns(String sentence){
        List<String> output = new ArrayList<String>();
        
        String[] sp = sentence.split(" ");
            for(String s : sp){
                
                String[] ss = s.split("_");
                if(ss.length == 2){
                    String tag = ss[1].substring(0, 2);
                    
                    if(!tag.equals("NN")){
                        output.add(ss[0].trim());
                    }
                }
            }
        return output;
    }
    
    
    public List<String> getWordCouples(List<String> list){    
        List<String> listWordCouples = new ArrayList<String>();
        
        int i = 1;
        for(String str : list){
                SentimentModel model = SentimentModel.loadSerialized("edu/stanford/nlp/models/sentiment/sentiment.ser.gz");
                boolean knownWord = model.wordVectors.containsKey(str);
                
                if(knownWord){
                    listWordCouples.add(str);
                }
                
             String info = "(" + String.valueOf(i) + " of " + String.valueOf(list.size()) + 
                     ") check word couples - " + str + " - " + String.valueOf(knownWord);
                System.out.println(info);
             i++;
        }
        
        return listWordCouples;
    }
    
    
    public int getStanfordSentimentRate(String sentimentText) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        //StanfordCoreNLP
        int totalRate = 0;
        String[] linesArr = sentimentText.split("\\.");
        for (int i = 0; i < linesArr.length; i++) {
            if (linesArr[i] != null) {
                Annotation annotation = pipeline.process(linesArr[i]);
               for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                    int score = RNNCoreAnnotations.getPredictedClass(tree);
                    totalRate = totalRate + (score - 2);
                }
            }
        }
        return totalRate;
    }
    
    
    public String getInfoSentiment(int value){
        if(value == 1){
            return "support";
        }
        
        if(value == -1){
            return "attack";
        }
        
        return "netral";
    }
}

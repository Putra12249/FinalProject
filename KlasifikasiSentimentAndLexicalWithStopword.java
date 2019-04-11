/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasisentimentandlexicalwithstopword;

/**
 *
 * @author Komang
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;

public class KlasifikasiSentimentAndLexicalWithStopword {

    /**
     * @param args the command line arguments
     */
    
    private static KlasifikasiSentimentAndLexicalWithStopword support;
    private static KlasifikasiSentimentAndLexicalWithStopword attack;

    File file; // mengakses datta set
    File file_attack; // mengakses datta set
    StringBuilder contents; // nyimoen kontrn dari data
    BufferedReader reader; // membaca file
    BufferedReader reader_attack; // membaca file
    String stringContents;
    String stringContents_attack;
    String[] sentences; // memtong per kalimat 
    String[] sentencesClean;
    String[] sentences_attack; // memtong per kalimat 
    String[] sentencesClean_attack;
    static List<String> claimCleaned_ = new ArrayList<String>(); 
    static List<String> premiseCleaned_ = new ArrayList<String>();

    List<String> listFeature;
    List<String> listFeatureWithLabel;
    String productName;
    private List<String> listEJFeatureOrientation;
    private List<Integer> listEJFeatureOrientationSentenceIndex;
    //private List<Arguments> listArguments;
    List<String> claim_ = new ArrayList<String>();
    List<String> premise_ = new ArrayList<String>();
    public String claim;
    public String premise;
    List<String> attack_claim_ = new ArrayList<String>();
    List<String> attack_premise_ = new ArrayList<String>();
    static List<String> claimed_unigram = new ArrayList<String>();
    static List<String> premised_unigram = new ArrayList<String>();
    static List<String> att_claimed_unigram = new ArrayList<String>();
    static List<String> att_premised_unigram = new ArrayList<String>();
    public String attack_claim;
    public String attack_premise;
    static List<String> feature_claim_string_ = new ArrayList<String>();
    static List<String> feature_premise_string_ = new ArrayList<String>();
    static List<String> claim_sentimentRate_ = new  ArrayList<String>();
    static List<String> premise_sentimentRate_ = new  ArrayList<String>();
    static List<String> attackfeature_claim_string_ = new  ArrayList<String>();
    static List<String> attack_claim_sentimentRate_ = new  ArrayList<String>();
    static List<String> attack_feature_premise_string_ = new  ArrayList<String>();
    static List<String> attack_premise_sentimentRate_ = new  ArrayList<String>();
    
    static List<String> attack_claimCleaned_ = new ArrayList<String>();
    static List<String> attack_premiseCleaned_ = new ArrayList<String>();
    StringBuilder contents_attack; // nyimoen kontrn dari data
    
    

    KlasifikasiSentimentAndLexicalWithStopword() {
    } 
    KlasifikasiSentimentAndLexicalWithStopword(String path) {
        file = new File(path);
        contents = new StringBuilder();
        reader = null;
        listFeature = new ArrayList<>();
        listFeatureWithLabel = new ArrayList<>();
    }
    
    public KlasifikasiSentimentAndLexicalWithStopword(String productName, String path) {
        file = new File(path);
        file_attack = new File(path);
        contents = new StringBuilder();
        contents_attack = new StringBuilder();
        reader = null;
        reader_attack = null;
        listFeature = new ArrayList<>();
        listFeatureWithLabel = new ArrayList<>();
        this.productName = productName;
    }
    
    public String getStringContents() {
        return stringContents;
    }

    public String[] getListSentences() {
        return sentencesClean;
    }

    public List<String> getListEJFeatureOrientation() {
        return listEJFeatureOrientation;
    }

    public List<Integer> getListEJFeatureOrientationSentenceIndex() {
        return listEJFeatureOrientationSentenceIndex;
    }

    public String[] getSentences() {
        return sentences;
    }

    public String[] getSentencesClean() {
        return sentencesClean;
    }
    

    
    public void preprocessing() {
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text)
                        .append(System.getProperty(
                        "line.separator"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stringContents = contents.toString();
        //System.out.println(stringContents);
        
        String[] tempContents;
        tempContents = stringContents.split("\\n");

        


        
        for(String temp : tempContents){
            String[] sp = temp.split("--");
//            System.out.println(sp[0]);
//            System.out.println("-----------");
//            System.out.println(sp[1]);
            if(sp.length == 2){
//                tempSentences.add(sp[0].trim());
                
                claim = sp[0].trim();
                premise = sp[1].trim();
//                System.out.println("Claim:" + claim);
//                System.out.println("Premise:" + premise);
                claim_.add(claim); // arrray ahir 
                premise_.add(premise);


            }
            else {
                claim = sp[0].trim();
                claim_.add(claim);
                premise = "Null"; 
                premise_.add(premise);
            }
            
        }
        for (int i = 0; i < claim_.size(); i++) {
            String[] claimCleaned = claim_.toArray(new String[0]);
            String[] premiseCleaned = premise_.toArray(new String[0]);
            claimCleaned[i] = claim_.get(i);
            premiseCleaned[i] = premise_.get(i);
            claimCleaned[i]= claimCleaned[i].replaceAll("\\.", " ");
            claimCleaned[i] = claimCleaned[i].replaceAll("(.*)##", "");
            claimCleaned[i] = claimCleaned[i].replaceAll("##", "");
            claimCleaned[i] = claimCleaned[i].replaceAll("\\[t\\]", "");
            claimCleaned[i] = claimCleaned[i].replaceAll("[^a-zA-Z0-9\\s\']*", "");
            claimCleaned[i] = claimCleaned[i].replaceAll("[\\s][\\s]*", " ");
            claimCleaned[i] = claimCleaned[i].replaceAll("^\\s", "");
            
            
            premiseCleaned[i] = premiseCleaned[i].replaceAll("\\.", " ");
            premiseCleaned[i] = premiseCleaned[i].replaceAll("(.*)##", "");
            premiseCleaned[i] = premiseCleaned[i].replaceAll("##", "");
            premiseCleaned[i] = premiseCleaned[i].replaceAll("\\[t\\]", "");
            premiseCleaned[i] = premiseCleaned[i].replaceAll("[^a-zA-Z0-9\\s\']*", "");
            premiseCleaned[i] = premiseCleaned[i].replaceAll("[\\s][\\s]*", " ");
            premiseCleaned[i] = premiseCleaned[i].replaceAll("^\\s", "");
            StopwordRemoval stopword = new StopwordRemoval();
            stopword.getListStopword();
            claimCleaned[i] = stopword.removeStopword(claimCleaned[i]);
            premiseCleaned[i] = stopword.removeStopword(premiseCleaned[i]);

            StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
            claimCleaned[i] = lemmatizer.lemmatizeSentence(claimCleaned[i]);
            premiseCleaned[i] = lemmatizer.lemmatizeSentence(premiseCleaned[i]);
            
            
            
            
            
            claimCleaned_.add(claimCleaned[i]);
            premiseCleaned_.add(premiseCleaned[i]);
        }

//        sentences = tempSentences.toArray(new String[tempSentences.size()]);
        
        System.out.println("Before: " + claim_);
        System.out.println("Claim Cleaned (Stopword + Lemmi): " + claimCleaned_);
        System.out.println("Before: " + premise_);
        System.out.println("Premiese Cleaned (Stopword + Lemmi): " + premiseCleaned_);        
    }
    
    public void preprocessingAttack() {
        try {
            reader_attack = new BufferedReader(new FileReader(file_attack));
            String text = null;

            // repeat until all lines is read
            while ((text = reader_attack.readLine()) != null) {
                contents_attack.append(text)
                        .append(System.getProperty(
                        "line.separator"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader_attack != null) {
                    reader_attack.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stringContents_attack = contents_attack.toString();
        //System.out.println(stringContents);
        
        String[] tempContents_attack;
        tempContents_attack = stringContents_attack.split("\\n");

        


        
        for(String temp : tempContents_attack){
            String[] sp = temp.split("--");
//            System.out.println(sp[0]);
//            System.out.println("-----------");
//            System.out.println(sp[1]);
            if(sp.length == 2){
//                tempSentences.add(sp[0].trim());
                
                attack_claim = sp[0].trim();
                attack_premise = sp[1].trim();
//                System.out.println("Claim:" + claim);
//                System.out.println("Premise:" + premise);
                attack_claim_.add(attack_claim);
                attack_premise_.add(attack_premise);


            }
            else{
                attack_claim = sp[0].trim();
                attack_claim_.add(attack_claim);
                attack_premise = "Null";
                attack_premise_.add(attack_premise);
            }
        }
        for (int i = 0; i < attack_claim_.size(); i++) {
            String[] attack_claimCleaned = attack_claim_.toArray(new String[0]);
            String[] attack_premiseCleaned = attack_premise_.toArray(new String[0]);
            attack_claimCleaned[i] = attack_claim_.get(i);
            attack_premiseCleaned[i] = attack_premise_.get(i);
            attack_claimCleaned[i]= attack_claimCleaned[i].replaceAll("\\.", " ");
            attack_claimCleaned[i] = attack_claimCleaned[i].replaceAll("(.*)##", "");
            attack_claimCleaned[i] = attack_claimCleaned[i].replaceAll("##", "");
            attack_claimCleaned[i] = attack_claimCleaned[i].replaceAll("\\[t\\]", "");
            attack_claimCleaned[i] = attack_claimCleaned[i].replaceAll("[^a-zA-Z0-9\\s\']*", "");
            attack_claimCleaned[i] = attack_claimCleaned[i].replaceAll("[\\s][\\s]*", " ");
            attack_claimCleaned[i] = attack_claimCleaned[i].replaceAll("^\\s", "");
            
            
            attack_premiseCleaned[i] = attack_premiseCleaned[i].replaceAll("\\.", " ");
            attack_premiseCleaned[i] = attack_premiseCleaned[i].replaceAll("(.*)##", "");
            attack_premiseCleaned[i] = attack_premiseCleaned[i].replaceAll("##", "");
            attack_premiseCleaned[i] = attack_premiseCleaned[i].replaceAll("\\[t\\]", "");
            attack_premiseCleaned[i] = attack_premiseCleaned[i].replaceAll("[^a-zA-Z0-9\\s\']*", "");
            attack_premiseCleaned[i] = attack_premiseCleaned[i].replaceAll("[\\s][\\s]*", " ");
            attack_premiseCleaned[i] = attack_premiseCleaned[i].replaceAll("^\\s", "");
            StopwordRemoval stopword = new StopwordRemoval();
            stopword.getListStopword();
            attack_claimCleaned[i] = stopword.removeStopword(attack_claimCleaned[i]);
            attack_premiseCleaned[i] = stopword.removeStopword(attack_premiseCleaned[i]);

            StanfordLemmatizer lemmatizer = new StanfordLemmatizer();
            attack_claimCleaned[i] = lemmatizer.lemmatizeSentence(attack_claimCleaned[i]);
            attack_premiseCleaned[i] = lemmatizer.lemmatizeSentence(attack_premiseCleaned[i]);
            
            
            
            
            
            attack_claimCleaned_.add(attack_claimCleaned[i]);
            attack_premiseCleaned_.add(attack_premiseCleaned[i]);
        }

//        sentences = tempSentences.toArray(new String[tempSentences.size()]);
        
//        System.out.println("Before: " + attack_claim_);
//        System.out.println("Claim Cleaned (Stopword + Lemmi): " + attack_claimCleaned_);
//        System.out.println("Before: " + attack_premise_);
//        System.out.println("Premiese Cleaned (Stopword + Lemmi): " + attack_premiseCleaned_);        
    }

    
    
    private static void saveFeaturesToFile_support() {
        PrintWriter out = null;
        try {
            File file = new File("C:\\Users\\Komang\\Desktop\\Cosingan Baru\\KlasifikasiSentimentAndLexicalWithStopword\\src\\klasifikasisentimentandlexicalwithstopword\\Train_SupportSentimentAndLexicalWithStopword.csv");
            out = new PrintWriter(new FileWriter(file));
            for (int i = 0; i < feature_claim_string_.size(); i++) {
                out.println(feature_claim_string_.get(i) + "," + feature_premise_string_.get(i) + "," + claim_sentimentRate_.get(i) +","+ premise_sentimentRate_.get(i)+"," + claimed_unigram.get(i) + "," + premised_unigram.get(i) + "," +"support");
            }
        } catch (IOException ex) { 
            System.out.println(ex);
        } finally {
            out.close();
        }
    }
    
    private static void saveFeaturesToFile_attack() {
        PrintWriter out = null;
        try {
            File file = new File("C:\\Users\\Komang\\Desktop\\Cosingan Baru\\KlasifikasiSentimentAndLexicalWithStopword\\src\\klasifikasisentimentandlexicalwithstopword\\Train_AttackSentimentAndLexicalWithStopword.csv");
            out = new PrintWriter(new FileWriter(file));
            for (int i = 0; i < attackfeature_claim_string_.size(); i++) {
                out.println(attackfeature_claim_string_.get(i) + "," + attack_feature_premise_string_.get(i) + "," + attack_claim_sentimentRate_.get(i) +","+ attack_premise_sentimentRate_.get(i)+","+att_claimed_unigram.get(i)+","+att_premised_unigram.get(i)+","+ "attack");
            }
        } catch (IOException ex) { 
            System.out.println(ex);
        } finally {
            out.close();
        }
    }
    
    

    public static void main(String[] args) {
        // TODO code application logic here
        support = new KlasifikasiSentimentAndLexicalWithStopword("Support", "C:\\Users\\Komang\\Desktop\\Cosingan Baru\\KlasifikasiSentimentAndLexicalWithStopword\\src\\klasifikasisentimentandlexicalwithstopword\\Data_Training_Support_Full.txt");
        support.preprocessing();
        attack = new KlasifikasiSentimentAndLexicalWithStopword ("Attack", "C:\\Users\\Komang\\Desktop\\Cosingan Baru\\KlasifikasiSentimentAndLexicalWithStopword\\src\\klasifikasisentimentandlexicalwithstopword\\Data_Training_Attack_Full.txt");
        attack.preprocessingAttack();
        PoSTagging postagging = new PoSTagging();
        PoSTagging postaggingAttack = new PoSTagging();
        
        String[] claim_tag =  claimCleaned_.toArray(new String[0]);
        String[] attack_claim_tag =  attack_claimCleaned_.toArray(new String[0]);
        claim_tag = postagging.tagPOS(claim_tag);
        attack_claim_tag = postaggingAttack.tagPOS(attack_claim_tag);
        String[] premise_tag =  premiseCleaned_.toArray(new String[0]);
        String[] attack_premise_tag =  attack_premiseCleaned_.toArray(new String[0]);
        premise_tag = postagging.tagPOS(premise_tag);
        attack_premise_tag = postaggingAttack.tagPOS(attack_premise_tag);
        
        //Support
        Lexical lex_claimed = new Lexical();
        lex_claimed.read();
        Extraction ex = new Extraction();
        for (int j = 0; j < claim_tag.length; j++) {
            String sentence = claim_tag[j];
            //System.out.println("Sentence : " + sentence);
           // List<String> feature_claim = ex.getNonNouns(sentence);
            String feature_claim_string = String.join(" ", sentence).trim();
            feature_claim_string_.add(feature_claim_string);
            int claim_sentimentRate = ex.getStanfordSentimentRate(feature_claim_string);
            claim_sentimentRate_.add(String.valueOf(claim_sentimentRate));
            //System.out.println("Claim Feature Extraction : " + feature_claim_string + claim_sentimentRate );

            String claimed_unigram_ = lex_claimed.addUnigram(feature_claim_string);
            claimed_unigram.add(claimed_unigram_);

        }
        Lexical lex_premised = new Lexical();
        lex_premised.read();
        Extraction ex2 = new Extraction();
            for (int  k= 0; k < premise_tag.length; k++) {
            String premisesentence = premise_tag[k];
            //System.out.println("Sentence : " + sentence);
            //List<String> feature_premise = ex2.getNonNouns(premisesentence);
            String feature_premise_string = String.join(" ", premisesentence).trim();
            int premise_sentimentRate = ex.getStanfordSentimentRate(feature_premise_string);
            feature_premise_string_.add(feature_premise_string);
            premise_sentimentRate_.add(String.valueOf(premise_sentimentRate));
            String premised_unigram_ = lex_claimed.addUnigram(feature_premise_string);
            premised_unigram.add(premised_unigram_);            
            }
             //String claimed_unigram = lex_claimed.addUnigram(attackpremisesentence);

            //System.out.println("Premise Feature Extraction : " + feature_premise_string + premise_sentimentRate);
        //Attack
        //Mas coba tambahin lagi lexical di bagian attack caranya pakai yang ada di bagian support
        Lexical lex_claimed_att = new Lexical();
        lex_claimed_att.read();
        Extraction Attack_ex = new Extraction();
        for (int z = 0; z < attack_claim_tag.length; z++) {
            String sentence = attack_claim_tag[z];
            //System.out.println("Sentence : " + sentence);
          //  List<String> attackfeature_claim = Attack_ex.getNonNouns(sentence);
            String attackfeature_claim_string = String.join(" ", sentence).trim();
            attackfeature_claim_string_.add(attackfeature_claim_string);
            int attack_claim_sentimentRate = Attack_ex.getStanfordSentimentRate(attackfeature_claim_string);
            attack_claim_sentimentRate_.add(String.valueOf(attack_claim_sentimentRate));
            //System.out.println("Claim Feature Extraction : " + feature_claim_string + claim_sentimentRate );
            String att_claimed_unigram_ = lex_claimed.addUnigram(attackfeature_claim_string);
            att_claimed_unigram.add(att_claimed_unigram_);

            }
        
        Lexical lex_premised_att = new Lexical();
        lex_premised_att.read();        
        Extraction Attackex2 = new Extraction();
            for (int  kj= 0; kj < attack_premise_tag.length; kj++) {
            String attackpremisesentence = attack_premise_tag[kj];
            //System.out.println("Sentence : " + sentence);
           // List<String> attack_feature_premise = Attackex2.getNonNouns(attackpremisesentence);
            String attack_feature_premise_string = String.join(" ", attackpremisesentence).trim();
            int attackpremise_sentimentRate = Attackex2.getStanfordSentimentRate(attack_feature_premise_string);
            attack_feature_premise_string_.add(attack_feature_premise_string);
            attack_premise_sentimentRate_.add(String.valueOf(attackpremise_sentimentRate));        
            //String claimed_unigram = lex_claimed.addUnigram(attackpremisesentence);
            String att_premised_unigram_ = lex_claimed.addUnigram(attack_feature_premise_string);
            att_premised_unigram.add(att_premised_unigram_);
            }

        

       // System.out.println("Attack - Claim Feature Extraction:" + attackfeature_claim_string_);
        //System.out.println("Attack - Premise Feature Extraction:" + attack_feature_premise_string_);

        saveFeaturesToFile_support();
        saveFeaturesToFile_attack();
        
              
        

    }
}
        
//        
//        removeNoise();
//        saveSentencesToFile(productName);
//    }  
//    
//}

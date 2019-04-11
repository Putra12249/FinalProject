/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasisentimentandlexicalwithstopword;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Komang
 */
public class Lexical {
    private List<String> Listlexical;

    public Lexical() {
        Listlexical = new ArrayList<>();
        read();
    }

    public List<String> Listlexical() {
        return Listlexical;
    }

    public final void read() {
        String line;
        try {
            File inputFile = new File("C:\\Users\\Komang\\Desktop\\Cosingan Baru\\KlasifikasiSentimentAndLexicalWithStopword\\src\\klasifikasisentimentandlexicalwithstopword\\lexicon.txt");
            InputStream inStream = new FileInputStream(inputFile);
            InputStreamReader inreader = new InputStreamReader(inStream, "8859_1");
            BufferedReader reader = new BufferedReader(inreader);

            while ((line = reader.readLine()) != null) {
                Listlexical.add(line);
            }
            reader.close();
        } catch (Exception e) {
        }

    }
    
    public String addUnigram(String before) {
        String result = "";
        String hasil_unigram = "";
        String[] token = before.split(" ");
        for (int i = 0; i < token.length; i++) {
            for (int j = 0; j < Listlexical.size(); j++) {
//                System.out.println("Token: " + token[i]);
//                System.out.println("Unigram: " + Listlexical.get(j));
                if (!token[i].equals(Listlexical.get(j))) {
                    //token[i] = Listlexical.get(j);
                    result = "FALSE";  
                }
                else{
                    System.out.println("Token: " + token[i]);
                    System.out.println("Unigram: " + Listlexical.get(j));
                    result = "TRUE";
                    break;
                }
            }
            if (result == "TRUE"){
                hasil_unigram = "1";
                break;
            }
            else{
                hasil_unigram = "0";
            }
//            if (!token[i].equals("")) {
//                //result = result + token[i] + " ";
//                result = result + "0";
//                
//            }
//            else {
//                result = result + "1";
//            }
        }
        result = result.replaceAll("\\s[\\s]*$", "");
        return hasil_unigram;
}
}

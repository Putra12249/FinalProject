/*
 * To change this template, choose Tools | Templates
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

public class StopwordRemoval {

    private List<String> listStopword;

    public StopwordRemoval() {
        listStopword = new ArrayList<>();
        read();
    }

    public List<String> getListStopword() {
        return listStopword;
    }

    public final void read() {
        String line;
        try {
            File inputFile = new File("C:\\Users\\Komang\\Desktop\\Cosingan Baru\\KlasifikasiSentimentAndLexicalWithStopword\\src\\klasifikasisentimentandlexicalwithstopword\\stopwords.txt");
            InputStream inStream = new FileInputStream(inputFile);
            InputStreamReader inreader = new InputStreamReader(inStream, "8859_1");
            BufferedReader reader = new BufferedReader(inreader);

            while ((line = reader.readLine()) != null) {
                listStopword.add(line);
            }
            reader.close();
        } catch (Exception e) {
        }

    }
    
    public String removeStopword(String before) {
        String result = "";
        String[] token = before.split(" ");
        for (int i = 0; i < token.length; i++) {
            for (int j = 0; j < listStopword.size(); j++) {
                if (token[i].equals(listStopword.get(j))) {
                    token[i] = "";
                }
            }
            if (!token[i].equals("")) {
                result = result + token[i] + " ";
            }
        }
        result = result.replaceAll("\\s[\\s]*$", "");
        return result;
    }
}

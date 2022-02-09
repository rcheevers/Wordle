import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class transTable {
    HashMap table1;
    static String[] error = new String[] {"error","",""};

    public transTable(String current1,String current2,String current3,ArrayList<String> possibleWords){
        table1 = new HashMap();
    }

    public String[] updateCurrent(String current1,String current2,String current3,String word,int[] result){
        current1 = current1.substring(0,5)+(current1.charAt(5)-47);
        for(int i = 0;i<5;i++){
            if(result[i]==0){
                int letter = (int) word.charAt(i)-97;
                current3 = current3.substring(0,letter) + "1" + current3.substring(letter+1);
            }else if(result[i]==1){
                //if part not in parts
                //  add part
                //else
                //  if place not in part
                //      if three places in part already
                //          promote to current1
                //      else
                //          add place to part
                ArrayList<Integer> letters = new ArrayList<>(); //get letters
                for(int j = 0;j<current2.length();j++){
                    if(Character.isLetter(current2.charAt(j))){
                        letters.add(j);
                    }
                }
                if(letters.size()>5){
                    return error;
                }
                ArrayList<String> parts = new ArrayList<>(); //split current2 into parts based on letters
                boolean inParts = false;
                for(int j = 0;j<letters.size();j++){
                    String part;
                    if(j+1 < letters.size()){
                        part = current2.substring(letters.get(j),letters.get(j+1));
                    }else{
                        part = current2.substring(letters.get(j));
                    }
                    if(part.charAt(0)==word.charAt(i)){// if a part is the part we want
                        inParts = true;
                        ArrayList<Integer> places = new ArrayList<>();//get places for the part
                        for(int k = 0;k<part.length();k++){
                            places.add(i);
                        }
                        places.add(i);// add place
                        if(places.size()>4){
                            return error;
                        }
                        places.sort(Comparator.naturalOrder());//sort
                        part = part.substring(0,1);
                        for(int k = 0;k<places.size();k++){//reconstruct part
                            part+= String.valueOf(places.get(k));
                        }
                    }
                    parts.add(part);
                }
                if(inParts==false){
                    parts.add(word.charAt(i)+String.valueOf(i));
                }
                parts.sort(Comparator.naturalOrder());//sort
                current2 = "";
                for(int k = 0;k<parts.size();k++){//reconstruct current2
                    current2 += parts.get(k);
                }
            }else{
                char letter = word.charAt(i);
                if(current1.charAt(i) != '_' && current1.charAt(i) != word.charAt(i)){
                    return error;
                }
                current1 = current1.substring(0,i)+letter+current1.substring(i+1);
            }
        }
        return new String[] {current1,current2,current3};
    }

    public boolean contains(String current1,String current2,String current3){
        if(table1.containsKey(current1)){
            HashMap table2 = (HashMap) table1.get(current1);
            if(table2.containsKey(current2)) {
                HashMap table3 = (HashMap) table2.get(current2);
                if (table3.containsKey(current3)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int get(String current1,String current2,String current3){
        if(this.contains(current1,current2,current3)){
            HashMap table2 = (HashMap) table1.get(current1);
            HashMap table3 = (HashMap) table2.get(current2);
            int stored = (int) table3.get(current3);
            return stored;
        }
        return -1;
    }

    public void put(String current1,String current2,String current3,int value){
        if(table1.containsKey(current1)){
            HashMap table2 = (HashMap) table1.get(current1);
            if(table2.containsKey(current2)){
                HashMap table3 = (HashMap) table2.get(current2);
                table3.put(current3,value);
            }else{
                HashMap table3 = new HashMap();
                table2.put(current2,table3);
                table3.put(current3,value);
            }
        }else{
            HashMap table2 = new HashMap();
            HashMap table3 = new HashMap();
            table1.put(current1,table2);
            table2.put(current2,table3);
            table3.put(current3,value);
        }
    }
}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class board {
    ArrayList<String> guesses;
    ArrayList<String> possibleWords;
    HashMap transTable;
    String currentTrans;

    public board(ArrayList<String> words,ArrayList<String> tempWords) {
        guesses = words;
        possibleWords = tempWords;
        currentTrans = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000009";
        transTable = new HashMap();
        transTable.put(currentTrans.substring(0,15),new HashMap<>());
        ((HashMap) transTable.get(currentTrans.substring(0,15))).put(currentTrans.substring(15),possibleWords.clone());
    }

    public void list(){
        for(int i = 0;i<possibleWords.size();i++){
            System.out.println(possibleWords.get(i));
        }
    }

    private void removePos(char letter, int place){
        for(int i = 0;i< possibleWords.size();i++){
            if(possibleWords.get(i).charAt(place)==letter){
                possibleWords.remove(i);
                i--;
            }
        }
    }

    private void remove(char letter){
        for(int i = 0;i<5;i++){
            removePos(letter,i);
        }
    }

    private void addPos(char letter, int place){
        for(int i = 0;i< possibleWords.size();i++){
            if(possibleWords.get(i).charAt(place)!=letter){
                possibleWords.remove(i);
                i--;
            }
        }
    }

    private void add(char letter){
        for(int i = 0;i< possibleWords.size();i++){
            String word = possibleWords.get(i);
            if(word.charAt(0)!=letter && word.charAt(1)!=letter && word.charAt(2)!=letter && word.charAt(3)!=letter && word.charAt(4)!=letter){
                possibleWords.remove(i);
                i--;
            }
        }
    }

    public boolean check2s(int letter){// false is error, true is all good.
        int place = 0;
        int sum = 0;
        for(int j = 0; j < 5; j++){
            if(currentTrans.charAt(j*26+letter) == '2'){
                sum += 1;
                place = j;
            }
        }
        if(sum == 0){
            return false;
        }else if(sum == 1){
            //currentTrans = currentTrans.substring(0, (place) * 26 + letter) + "3" + currentTrans.substring((place) * 26 + letter + 1);
            for (int j = 0; j < 26; j++) {
                if (j != letter) {
                    if (currentTrans.charAt(place * 26 + j) == '3') {
                        return false;
                    } else if (currentTrans.charAt(place * 26 + j) == '2') {
                        currentTrans = currentTrans.substring(0, (place) * 26 + j) + "1" + currentTrans.substring((place) * 26 + j + 1);
                        if(!check2s(j)){
                            return false;
                        }
                    } else {
                        currentTrans = currentTrans.substring(0, (place) * 26 + j) + "1" + currentTrans.substring((place) * 26 + j + 1);
                    }
                }
            }
        }
        return true;
    }

    public void words(String word,int[] result){
        for(int i = 0;i<5;i++){
            if(result[i]==0){
                for(int j = 0;j<5;j++) {
                    if(currentTrans.charAt(j*26+(int)word.charAt(i)-97) == '3' || currentTrans.charAt(j*26+(int)word.charAt(i)-97) == '2'){
                        currentTrans = "";
                        possibleWords = new ArrayList<>();
                        return;
                    }else {
                        currentTrans = currentTrans.substring(0, (j) * 26 + (int) word.charAt(i) - 97) + "1" + currentTrans.substring((j) * 26 + ((int) word.charAt(i) - 97) + 1);
                    }
                }
            }else if(result[i]==1){
                if(currentTrans.charAt(0*26+(int)word.charAt(i)-97) == '1' && currentTrans.charAt(1*26+(int)word.charAt(i)-97) == '1' && currentTrans.charAt(2*26+(int)word.charAt(i)-97) == '1' && currentTrans.charAt(3*26+(int)word.charAt(i)-97) == '1' && currentTrans.charAt(4*26+(int)word.charAt(i)-97) == '1'){
                    currentTrans = "";
                    possibleWords = new ArrayList<>();
                    return;
                }
                for(int j = 0;j<5;j++){
                    if(currentTrans.charAt(j*26+(int)word.charAt(i)-97) == '0')
                        currentTrans = currentTrans.substring(0,(j)*26+(int)word.charAt(i)-97)+"2"+currentTrans.substring((j)*26+((int)word.charAt(i)-97)+1);
                }
                if(!check2s((int)word.charAt(i)-97)){
                    currentTrans = "";
                    possibleWords = new ArrayList<>();
                    return;
                }
                if(currentTrans.charAt(i*26+(int)word.charAt(i)-97) == '3'){
                    currentTrans = "";
                    possibleWords = new ArrayList<>();
                    return;
                }else {
                    currentTrans = currentTrans.substring(0, (i) * 26 + (int) word.charAt(i) - 97) + "1" + currentTrans.substring((i) * 26 + ((int) word.charAt(i) - 97) + 1);
                }
            }else{
                if(currentTrans.charAt(i*26+(int)word.charAt(i)-97) == '1') {
                    currentTrans = "";
                    possibleWords = new ArrayList<>();
                    return;
                } else {
                    for (int j = 0; j < 26; j++) {
                        if (j != word.charAt(i) - 97) {
                            if (currentTrans.charAt(i * 26 + j) == '3') {
                                currentTrans = "";
                                possibleWords = new ArrayList<>();
                                return;
                            } else if(currentTrans.charAt(i * 26 + j) == '2') {
                                if (!check2s(j)){
                                    currentTrans = "";
                                    possibleWords = new ArrayList<>();
                                    return;
                                }
                            } else {
                                currentTrans = currentTrans.substring(0, (i) * 26 + j) + "1" + currentTrans.substring((i) * 26 + j + 1);
                            }
                        } else {
                            if(currentTrans.charAt(i*26+(int)word.charAt(i)-97) == '2') {
                                currentTrans = currentTrans.substring(0, (i) * 26 + j) + "1" + currentTrans.substring((i) * 26 + j + 1);
                                if(!check2s((int)word.charAt(i)-97)){
                                    currentTrans = "";
                                    possibleWords = new ArrayList<>();
                                    return;
                                }                            }
                            currentTrans = currentTrans.substring(0, (i) * 26 + (int) word.charAt(i) - 97) + "3" + currentTrans.substring((i) * 26 + ((int) word.charAt(i) - 97) + 1);
                        }
                    }
                }
            }
        }
        currentTrans = currentTrans.substring(0,130)+((int) currentTrans.charAt(130)-49);
        if(transTable.containsKey(currentTrans.substring(0,15)) && ((HashMap)transTable.get(currentTrans.substring(0,15))).containsKey(currentTrans.substring(15))){
            possibleWords = (ArrayList<String>) ((HashMap)transTable.get(currentTrans.substring(0,15))).get(currentTrans.substring(15));
        }else {
            for (int i = 0; i < 5; i++) {
                if (result[i] == 0) {
                    remove(word.charAt(i));
                } else if (result[i] == 1) {
                    add(word.charAt(i));
                    removePos(word.charAt(i), i);
                } else {
                    addPos(word.charAt(i), i);
                }
            }
            if(transTable.containsKey(currentTrans.substring(0,15))){
                ((HashMap) transTable.get(currentTrans.substring(0,15))).put(currentTrans.substring(15),possibleWords.clone());
            }else{
                transTable.put(currentTrans.substring(0,15),new HashMap<>());
                ((HashMap) transTable.get(currentTrans.substring(0,15))).put(currentTrans.substring(15),possibleWords.clone());
            }
        }
    }

    public int maximum(String word,int depth,int minimum){
        String tempTrans = currentTrans;
        //System.out.println(word + ": " + currentTrans);
        //System.out.println(possibleWords.size());
        //int sum = 0;
        int maximum = 0;
        for(int a = 0;a<3;a++){
            for(int b = 0;b<3;b++){
                for(int c = 0;c<3;c++){
                    for(int d = 0;d<3;d++){
                        for(int e = 0;e<3;e++){
                            int countTemp;
                            int[] result = {a,b,c,d,e};
                            //System.out.println(currentTrans);
                            words(word,result);
                            if(depth > 1){
                                if(possibleWords.size() <= maximum){//change back to maximum
                                    currentTrans = tempTrans;
                                    possibleWords = (ArrayList<String>) ((ArrayList<String>) ((HashMap) transTable.get(tempTrans.substring(0,15))).get(tempTrans.substring(15))).clone();
                                    continue;
                                }
                                String s = miniMax(depth - 1, maximum)[1];
                                countTemp = Integer.parseInt(s);
                            }else{
                                countTemp = possibleWords.size();
                            }
                            //System.out.println(currentTrans);
                            //sum += countTemp;
                            //System.out.println("" + a + b + c + d + e + " " + word + ": " + countTemp + " " + currentTrans);
                            currentTrans = tempTrans;
                            possibleWords = (ArrayList<String>) ((ArrayList<String>) ((HashMap) transTable.get(tempTrans.substring(0,15))).get(tempTrans.substring(15))).clone();
                            if(countTemp >= minimum){
                                return countTemp;
                            } else if(countTemp>maximum){
                                maximum = countTemp;
                            }
                        }
                    }
                }
            }
        }
        /*if(sum != possibleWords.size()){
            //System.out.println("ERROR PLEASE FIX!!!!!!!!!");//changed back to maximum
            return maximum;
        }*/
        //System.out.println(sum);
        return maximum;
    }

    public String[] miniMax(int depth, int maximum){
        int minimum = possibleWords.size();
        int place = 0;
        for(int i = 0;i<guesses.size();i++){
            int tempMin = maximum(guesses.get(i),depth,minimum);
            if(tempMin == 0){
                return new String[] {guesses.get(i),"0"};
            }
            if(depth>1) {
                String print = "";
                for (int j = 0; j < 4 - depth; j++) {
                    print += "\t";
                }
                System.out.println(print + depth + ": " + guesses.get(i)+" "+tempMin+"/"+minimum);
            }
            if(tempMin <= maximum) {
                String[] result = {guesses.get(i), String.valueOf(tempMin)};
                //System.out.println("2: "+guesses.get(place)+": "+tempMin +" <= "+ maximum+" depth: "+ depth);
                return result;
            }else if(tempMin<minimum){
                minimum = tempMin;
                place = i;
            }
        }
        String[] result = {guesses.get(place), String.valueOf(minimum)};
        //System.out.println("1: "+guesses.get(place)+": "+minimum+" depth: "+ depth);
        return result;
    }

    public ArrayList<String> sort(){
        ArrayList<String> stuff = new ArrayList<>();
        for(int i = 0;i<guesses.size();i++){
            int tempMin = maximum(guesses.get(i),1,10000);
            String count = String.valueOf(tempMin);
            stuff.add(count.length()+" "+count+"     "+guesses.get(i));
            System.out.println(guesses.get(i)+": "+count);
        }
        stuff.sort(Comparator.naturalOrder());
        return stuff;
    }
}

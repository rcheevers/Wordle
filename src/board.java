import java.util.ArrayList;
import java.util.Comparator;

public class board {
    ArrayList<String> guesses;
    ArrayList<String> possibleWords;
    transTable transTable;
    String currentTrans1;
    String currentTrans2;
    String currentTrans3;

    public board(ArrayList<String> words,ArrayList<String> tempWords) {
        guesses = words;
        possibleWords = tempWords;
        currentTrans1 = "_____";
        currentTrans2 = "";
        currentTrans3 = "00000000000000000000000000";
        transTable = new transTable(currentTrans1,currentTrans2,currentTrans3,possibleWords);
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

    public void words(String word,int[] result){
        String[] trans = transTable.updateCurrent(currentTrans1,currentTrans2,currentTrans3,word,result);
        currentTrans1 = trans[0];
        currentTrans2 = trans[1];
        currentTrans3 = trans[2];
        if(transTable.contains(currentTrans1,currentTrans2,currentTrans3)){
            possibleWords = transTable.get(currentTrans1,currentTrans2,currentTrans3);
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
            transTable.put(currentTrans1,currentTrans2,currentTrans3,possibleWords);
        }
    }

    public int maximum(String word,int depth,int minimum){
        String tempTrans1 = currentTrans1;
        String tempTrans2 = currentTrans2;
        String tempTrans3 = currentTrans3;
        //System.out.println(word + ": " + currentTrans);
        //System.out.println(possibleWords.size());
        //int sum = 0;
        //String worst = "";
        int maximum = 0;
        for(int a = 0;a<3;a++){
            for(int b = 0;b<3;b++){
                for(int c = 0;c<3;c++){
                    for(int d = 0;d<3;d++){
                        for(int e = 0;e<3;e++) {
                            int countTemp;
                            int[] result = {a, b, c, d, e};
                            words(word, result);
                            if (depth > 1) {
                                if (possibleWords.size() <= maximum) {//change back to maximum
                                    currentTrans1 = tempTrans1;
                                    currentTrans2 = tempTrans2;
                                    currentTrans3 = tempTrans3;
                                    possibleWords = transTable.get(currentTrans1,currentTrans2,currentTrans3);
                                    continue;
                                }
                                String s = miniMax(depth - 1, maximum)[1];
                                countTemp = Integer.parseInt(s);
                            } else {
                                countTemp = possibleWords.size();
                            }
                            //System.out.println("" + a + b + c + d + e + " " + word + ": " +countTemp+ " " + currentTrans1+"|"+currentTrans2+"|"+currentTrans3);
                            //System.out.println(possibleWords);
                            //System.out.println(currentTrans);
                            //sum += countTemp;
                            currentTrans1 = tempTrans1;
                            currentTrans2 = tempTrans2;
                            currentTrans3 = tempTrans3;
                            possibleWords = transTable.get(currentTrans1,currentTrans2,currentTrans3);
                            if(countTemp >= minimum){
                                return countTemp;
                            } else if(countTemp>maximum){
                                maximum = countTemp;
                                //worst = ""+a+b+c+d+e;
                            }
                        }
                    }
                }
            }
        }
        /*
        if(sum != possibleWords.size()){
            //System.out.println("ERROR PLEASE FIX!!!!!!!!!");//changed back to maximum
            return maximum;
        }
        */
        //System.out.println(sum);
        //System.out.println(worst);
        return maximum;
    }

    public String[] miniMax(int depth, int maximum){
        int minimum = possibleWords.size();
        int place = 0;
        for(int i = 0;i<guesses.size();i++){
            int tempMin = maximum(guesses.get(i),depth,minimum);
            if(tempMin == 0){
                System.out.println("error: min returned 0");
                breakpoint();
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

    public void breakpoint(){
        breakpoint();
    }

    public ArrayList<String> sort(){
        ArrayList<String> stuff = new ArrayList<>();
        for(int i = 0;i<guesses.size();i++){
            int tempMin = maximum(guesses.get(i),1,10000);
            String count = String.valueOf(tempMin);
            stuff.add(count.length()+" "+count+"     "+guesses.get(i));
        }
        for(int i = 0;i<possibleWords.size();i++){
            int tempMin = maximum(possibleWords.get(i),1,10000);
            String count = String.valueOf(tempMin);
            stuff.add(count.length()+" "+count+"     "+possibleWords.get(i));
        }
        stuff.sort(Comparator.naturalOrder());
        return stuff;
    }
}
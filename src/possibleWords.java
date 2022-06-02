import java.util.ArrayList;
import java.util.Comparator;

public class possibleWords {
    ArrayList<String> words;

    public possibleWords(ArrayList<String> possibleWords){
        words = (ArrayList<String>) possibleWords.clone();
    }

    public int size(){
        return words.size();
    }

    public possibleWords clone(){
        possibleWords newWords = new possibleWords(words);
        return newWords;
    }

    private void removePos(char letter, int place){
        for(int i = 0;i<words.size();i++){
            if(words.get(i).charAt(place)==letter){
                words.remove(i);
                i--;
            }
        }
    }

    private void remove(char letter, String word, int[] result){
        for (int i = 0; i < 5; i++) {
            removePos(letter, i);
        }
    }

    private void addPos(char letter, int place){
        for(int i = 0;i<words.size();i++){
            if(words.get(i).charAt(place)!=letter){
                words.remove(i);
                i--;
            }
        }
    }

    private void add(char letter){
        for(int i = 0;i< words.size();i++){
            String word = words.get(i);
            if(word.charAt(0)!=letter && word.charAt(1)!=letter && word.charAt(2)!=letter && word.charAt(3)!=letter && word.charAt(4)!=letter){
                words.remove(i);
                i--;
            }
        }
    }

    public void words(String word,int[] result){
        for (int i = 0; i < 5; i++) {
            if (result[i] == 0) {
                boolean flag = true;
                for(int j = 0;j<5;j++){
                    if(word.charAt(j) == word.charAt(i) && result[j] != 0){
                        flag = false;
                    }
                }
                if (flag) {
                    remove(word.charAt(i), word, result);
                }
            } else if (result[i] == 1) {
                add(word.charAt(i));
                removePos(word.charAt(i), i);
            } else {
                addPos(word.charAt(i), i);
            }
        }
    }

    public void word(String words,String nums){
        for (int i = 0; i < words.length(); i+=5) {
            String letters = words.substring(i,i+5);
            int a = nums.charAt(i)-48;
            int b = nums.charAt(i+1)-48;
            int c = nums.charAt(i+2)-48;
            int d = nums.charAt(i+3)-48;
            int e = nums.charAt(i+4)-48;
            words(letters,new int[] {a,b,c,d,e});
        }
    }

    public void list(){
        for(int i = 0;i<words.size();i++){
            System.out.println(words.get(i));
        }
    }

    public ArrayList<String> sortMax(ArrayList<String> guesses, int depth){
        ArrayList<String> stuff = new ArrayList<>();
        for(int i = 0;i<guesses.size();i++){
            int tempMin = board.maximum(guesses.get(i),depth,10000,1,this.clone(),guesses);
            System.out.println(guesses.get(i)+" "+tempMin);
            String count = String.valueOf(tempMin);
            stuff.add(count.length()+" "+count+"     "+guesses.get(i));
        }
        stuff.sort(Comparator.naturalOrder());
        return stuff;
    }

    public ArrayList<String> sortExpected(ArrayList<String> guesses,int depth){
        ArrayList<String> stuff = new ArrayList<>();
        for(int i = 0;i<guesses.size();i++){
            float tempMin = board.expected(guesses.get(i),depth,10000,1,this.clone(),guesses);
            String count = String.valueOf(tempMin);
            stuff.add(String.valueOf(Math.round(tempMin)).length()+" "+count+"     "+guesses.get(i));
        }
        stuff.sort(Comparator.naturalOrder());
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0;i<stuff.size();i++){
            result.add(stuff.get(i).substring(stuff.get(i).length()-5));
        }
        return stuff;
    }
}

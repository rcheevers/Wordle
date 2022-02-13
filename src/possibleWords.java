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

    private void remove(char letter){
        for(int i = 0;i<5;i++){
            removePos(letter,i);
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
                remove(word.charAt(i));
            } else if (result[i] == 1) {
                add(word.charAt(i));
                removePos(word.charAt(i), i);
            } else {
                addPos(word.charAt(i), i);
            }
        }
    }

    public void list(){
        for(int i = 0;i<words.size();i++){
            System.out.println(words.get(i));
        }
    }

    public ArrayList<String> sort(ArrayList<String> guesses){
        ArrayList<String> stuff = new ArrayList<>();
        for(int i = 0;i<guesses.size();i++){
            int tempMin = board.maximum(guesses.get(i),1,1,10000,this.clone(),guesses);
            String count = String.valueOf(tempMin);
            if(tempMin != 0){
                stuff.add(count.length()+" "+count+"     "+guesses.get(i));
            }
        }
        stuff.sort(Comparator.naturalOrder());
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0;i<stuff.size();i++){
            //System.out.println(stuff.get(i));
            result.add(stuff.get(i).substring(stuff.get(i).length()-5));
        }
        return result;
    }
}

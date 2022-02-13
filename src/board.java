import java.util.ArrayList;

public class board {

    public static int maximum(String word,int depth,int minimum, possibleWords possibleWords, ArrayList<String> guesses){
        int maximum = 0;
        for(int a = 0;a<3;a++){
            for(int b = 0;b<3;b++){
                for(int c = 0;c<3;c++){
                    for(int d = 0;d<3;d++){
                        for(int e = 0;e<3;e++) {
                            int countTemp;
                            int[] result = {a, b, c, d, e};
                                possibleWords tempWords = possibleWords.clone();
                                tempWords.words(word, result);
                                if (depth > 1) {
                                    if (tempWords.size() <= maximum) {
                                        continue;
                                    }
                                    String s = miniMax(depth - 1, maximum,tempWords,guesses)[1];
                                    countTemp = Integer.parseInt(s);
                                } else {
                                    countTemp = tempWords.size();
                                }
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
        return maximum;
    }

    public static String[] miniMax(int depth, int maximum, possibleWords possibleWords, ArrayList<String> guesses){
        int minimum = possibleWords.size();
        int place = 0;
        for(int i = 0;i<guesses.size();i++){
            int tempMin = maximum(guesses.get(i),depth,minimum,possibleWords,guesses);
            if(tempMin == 0){
                continue;
            }
            if(depth>1) {
                System.out.println("depth of " + depth + ": " + guesses.get(i)+" "+tempMin+"/"+minimum);
            }
            if(tempMin <= maximum) {
                String[] result = {guesses.get(i), String.valueOf(tempMin)};
                return result;
            }else if(tempMin<minimum){
                minimum = tempMin;
                place = i;
            }
        }
        String[] result = {guesses.get(place), String.valueOf(minimum)};
        return result;
    }
}
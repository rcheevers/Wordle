import java.util.ArrayList;

public class board {

    public static int maximum(String word,int depth,int minimum,int maximum, possibleWords possibleWords, ArrayList<String> guesses){
        int value = 0;
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
                                    if (tempWords.size() <= value) {
                                        continue;
                                    }
                                    String s = miniMax(depth - 1, minimum,maximum,tempWords,guesses)[1];
                                    countTemp = Integer.parseInt(s);
                                } else {
                                    countTemp = tempWords.size();
                                }
                            if(countTemp>value){
                                value = countTemp;
                            }
                            if(countTemp >= minimum){
                                return countTemp;
                            }
                            if(value>maximum){
                                maximum=value;
                            }
                        }
                    }
                }
            }
        }
        return maximum;
    }

    public static float expected(String word,int depth,float minimum,float maximum, possibleWords possibleWords, ArrayList<String> guesses){
        float value = 0;
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
                                String s = expectiMin(depth - 1, minimum,maximum,tempWords,guesses)[1];
                                countTemp = Integer.parseInt(s);
                            } else {
                                countTemp = tempWords.size();
                            }
                            value += countTemp*countTemp;
                            if(countTemp >= minimum){
                                return countTemp;
                            }
                            if(value>maximum){
                                maximum=value;
                            }
                        }
                    }
                }
            }
        }
        return (value/possibleWords.size());
    }

    public static String[] miniMax(int depth,int minimum, int maximum, possibleWords possibleWords, ArrayList<String> guesses){
        int value = possibleWords.size();
        int place = 0;
        for(int i = 0;i<guesses.size();i++){
            int tempMin = maximum(guesses.get(i),depth,minimum,maximum,possibleWords,guesses);
            if(tempMin == 0){
                continue;
            }
            if(depth>1) {
                System.out.println("depth of " + depth + ": " + guesses.get(i)+" "+tempMin+"/"+minimum);
            }
            if(tempMin<value){
                value = tempMin;
                place = i;
            }
            if(value <= maximum) {
                String[] result = {guesses.get(i), String.valueOf(tempMin)};
                return result;
            }
            if(value<minimum){
                minimum = value;
            }
        }
        String[] result = {guesses.get(place), String.valueOf(minimum)};
        return result;
    }

    public static String[] expectiMin(int depth,float minimum, float maximum, possibleWords possibleWords, ArrayList<String> guesses){
        float value = possibleWords.size();
        int place = 0;
        for(int i = 0;i<guesses.size();i++){
            float tempMin = expected(guesses.get(i),depth,minimum,maximum,possibleWords,guesses);
            if(tempMin == 0){
                continue;
            }
            if(depth>1) {
                System.out.println("depth of " + depth + ": " + guesses.get(i)+" "+tempMin+"/"+minimum);
            }
            if(tempMin<value){
                value = tempMin;
                place = i;
            }
            if(value <= maximum) {
                String[] result = {guesses.get(i), String.valueOf(tempMin)};
                return result;
            }
            if(value<minimum){
                minimum = value;
            }
        }
        String[] result = {guesses.get(place), String.valueOf(minimum)};
        return result;
    }
}
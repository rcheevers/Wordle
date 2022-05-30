import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        ArrayList<String> guesses = createGuesses();
        ArrayList<String> create = createWords();
        possibleWords wordList;
        ArrayList<String> options = createStuff();

        for(int i = 0;i<options.size();i++) {
            double sum = 1.0;
            int count = 1;
            for (int a = 0; a < 3; a++) {
                for (int b = 0; b < 3; b++) {
                    for (int c = 0; c < 3; c++) {
                        for (int d = 0; d < 3; d++) {
                            for (int e = 0; e < 3; e++) {
                                int[] result = {a, b, c, d, e};
                                wordList = new possibleWords(create);
                                wordList.words(options.get(i), result);
                                board.expectiMin(1, 10000, 1, wordList, guesses);
                                if (wordList.size() > 0) {
                                    count += wordList.size();
                                }
                                if (wordList.size() > 1) {
                                    sum++;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(options.get(i));
            System.out.println(sum);
            System.out.println(count);
            System.out.println(sum/count+"\n");
        }

        //wordList.word("craneicing", "0002010220");
        // growl drowl glory prowl
        // sunny skunk swung spunk


        //wordList.list();
        //guesses = wordList.sortMax(guesses,1);
        //String[] result = board.miniMax(1,10000,1,wordList,guesses);
        //System.out.println(result[0]+": "+result[1]);
        //result = board.expectiMin(1,10000,1,wordList,guesses);
        //System.out.println(result[0]+": "+result[1]);
        //System.out.println("size: "+wordList.size());
        //guesses = wordList.sortMax(guesses,2);
        //save(guesses,"src/sortedOptions3.txt");
    }

    public static void save(ArrayList<String> results,String file){
        try {
            File myObj = new File(file);
            FileOutputStream outputStream = new FileOutputStream(myObj);
            for(int i = 0;i< results.size();i++){
                String result = results.get(i)+"\n";
                byte[] strToBytes = result.getBytes();
                outputStream.write(strToBytes);
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> createGuesses(){
        String line = "";
        ArrayList<String> guesses = new ArrayList<>();
        try {
            File myObj = new File("src/sortedOptions.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                line = myReader.nextLine();
                guesses.add(line.substring(line.length()-5));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return guesses;
    }

    public static ArrayList<String> createWords(){
        String line = "";
        ArrayList<String> possibleWords = new ArrayList<>();
        try {
            File myObj = new File("src/tempWords.txt");
            Scanner myReader = new Scanner(myObj);
            line = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < line.length() + 1; i += 9) {
            possibleWords.add(line.substring(i + 1, i + 6));
        }
        return possibleWords;
    }

    public static ArrayList<String> createStuff(){
        String line = "";
        ArrayList<String> guesses = new ArrayList<>();
        try {
            File myObj = new File("src/sortedOptions6.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                line = myReader.nextLine();
                guesses.add(line.substring(line.length()-5));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return guesses;
    }
}

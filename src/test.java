import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        ArrayList<String> guesses = createGuesses();
        possibleWords wordList = new possibleWords(createWords());

        //wordList.words("aloes",new int[] {0,0,0,1,0});
        //wordList.words("prink",new int[] {0,1,0,0,0});
        //wordList.words("aurei",new int[] {1,0,0,0,0});
        //wordList.words("ymolt",new int[] {0,0,0,1,0});
        //wordList.list();
        //guesses = wordList.sort(guesses);

        long start = System.nanoTime();
        String[] result = board.miniMax(2,1,10000,wordList,guesses);
        System.out.println(result[0]+": "+result[1]);
        long end = System.nanoTime();
        System.out.println(end-start);
        //System.out.println(possibleWords.size());
    }

    public static void save(ArrayList<String> results){
        try {
            File myObj = new File("src/sortedOptions.txt");
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
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        board obj = createBoard();

        //obj.words("aloes",new int[] {0,2,0,2,0});
        //obj.words("aesir",new int[] {0,0,0,0,0});
        //obj.words("duply",new int[] {0,1,0,0,0});
        //obj.words("sengi",new int[] {0,1,0,0,1});
        //obj.words("rabis",new int[] {1,0,0,1,0});
        //obj.maximum("nitry",1,10000);

        //System.out.println(obj.transpositionTable(obj.currentTrans));
        //System.out.println(obj.currentTrans);
        //System.out.println(obj.transTable.get("_____","","00000000000000000000000000").size());
        //obj.list();

        //long start = System.nanoTime();

        String[] result = obj.miniMax(2,1);
        System.out.println(result[0]+": "+result[1]);
        //long end = System.nanoTime();
        //System.out.println(end-start);
        System.out.println(obj.possibleWords.size());
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

    public static board createBoard(){
        String line = "";
        ArrayList<String> guesses = new ArrayList<>();
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

        board obj = new board(guesses, possibleWords);
        return obj;
    }
}

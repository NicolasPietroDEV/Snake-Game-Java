import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
abstract class ArrayTools {
    public static String[][] deepCopy(String[][] array) {
        String[][] data = new String[array.length][];
        for (int i = 0; i < data.length; i++) {
            data[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return data;
    }
}
class Snake {
    public Boolean alive = true;
    public String previousDirection;
    public int points = 0;
    private String[][] table = {{" "," "," ", " ", " ", " ", " "}, {" ","■"," ", " ", " ", " ", " "}, {" "," "," ", " ", " ", " ", " "}, {" "," "," ", " ", " ", " ", " "}, {" "," "," ", " ", " ", " ", " "}, {" "," "," ", " ", " ", " ", " "}, {" "," "," ", " ", " ", " ", " "}};
    private final String[][] emptyTable = {{" "," "," ", " "," "," ", " "}, {" "," "," ", " "," "," ", " "}, {" "," "," ", " "," "," ", " "}, {" "," ","🍎", " "," "," ", " "}, {" "," "," ", " ", " ", " ", " "}, {" "," "," ", " ", " ", " ", " "}, {" "," "," ", " ", " ", " ", " "}};
    private ArrayList<int[]> snake = new ArrayList<>();
    private int[] pointer = {1, 1};
    
    public Snake(){
        snake.add((new int[]{1, 1}).clone());
    }


    private void reloadTable(){
        table =  ArrayTools.deepCopy(emptyTable);
        for (int[] xy : snake) {
            if (snake.indexOf(xy) == snake.size() - 1){
                table[xy[0]][xy[1]] = "□";
            }else{
                table[xy[0]][xy[1]] = "■";
            }

        }
    }
    public void showTable() {
        if (alive){
        this.reloadTable();
        System.out.println(String.format("Pontos: %s", this.points));
        for (String[] line : table){
            System.out.println(Arrays.toString(line));
        }
        System.out.println("----------------");} else {
            System.out.println("Você perdeu");
        }
    }
    void move(String dir){
        this.reloadTable();
        boolean executeMove = true;
        switch (dir) {
            case "w" -> {
                if(previousDirection == "s" && snake.size() != 1){
                    executeMove = false;
                } else {
                    pointer[0] -= 1;
                    previousDirection = "w";
                }
            }
            case "s" -> {
                if(previousDirection == "w" && snake.size() != 1){
                    executeMove = false;
                } else {
                    pointer[0] += 1;
                    previousDirection = "s";
                }
            }
            case "a" -> {
                if(previousDirection == "d" && snake.size() != 1){
                    executeMove = false;
                } else {
                    pointer[1] -= 1;
                    previousDirection = "a";
                }
            }
            case "d" -> {
                if(previousDirection == "a" && snake.size() != 1){
                    executeMove = false;
                } else {
                    pointer[1] += 1;
                    previousDirection = "d";
                }
            }
            default -> {
                executeMove = false;
            }
        }
        if(executeMove){
            boolean generateAnApple = false;
                if(pointer[0] != -1 && pointer[0] != 7 && pointer[1] != -1 && pointer[1] != 7  ){
                    if (table[pointer[0]][pointer[1]] != "🍎") {
                        snake.remove(0);
                        this.reloadTable();
                    } else {
                        emptyTable[pointer[0]][pointer[1]] = " ";
                        this.points++;
                        generateAnApple = true;
                    }
                    if(table[pointer[0]][pointer[1]] != "■") {
                        snake.add(pointer.clone());
                    } else {
                    alive = false;}
                } else {
                    alive = false;
                }
            this.reloadTable();
                if (generateAnApple){
                    generateApple();
                }}}
    private void generateApple(){
        this.reloadTable();
        ArrayList<int[]> emptySpaces = new ArrayList<int[]>();
        for (int line = 0; line<table.length;line++){
            for (int cell = 0; cell<table[line].length;cell++){
                if (table[line][cell] == " "){
                    emptySpaces.add(new int[]{line, cell});
                }
            }
        }
        if (emptySpaces.size() == 0){
            System.out.println("Você venceu!!");
        } else {
            int randomChoice = (int) Math.floor(Math.random() * emptySpaces.size());
            emptyTable[((int[]) emptySpaces.get(randomChoice))[0]][((int[]) emptySpaces.get(randomChoice))[1]] = "🍎";
        }

    }
}
public class App {
    private static final Scanner console = new Scanner(System.in);
    public static Snake snk = new Snake();
    public static void main(String[] args){
        String in;
        while (true){
            snk = new Snake();
        while (snk.alive){
            in = console.next();
            snk.move(in);
            snk.showTable();
        }}
    } 
}
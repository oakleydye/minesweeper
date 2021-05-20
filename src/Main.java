import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Let's play minesweeper!");
        System.out.println("Enter the number of columns (1-26)");
        int cols = in.nextInt();
        System.out.println("Enter the number of rows");
        int rows = in.nextInt();
        System.out.println("Enter the number of mines");
        int mines = in.nextInt();

        Board board = new Board(cols, rows, mines);
        while (true){
            System.out.println();
            System.out.println("Would you like to [F]lag or [U]ncover a square?");
            String action = in.next();
            System.out.println("Enter the coordinates of the square (ex. [A1]");
            String square = in.next();

            if (action.equalsIgnoreCase("F")){
                if (board.checkValidSquare(square)){
                    board.flagSquare(square);
                } else{
                    System.out.println("Invalid square! Please enter valid coordinates (ex. [A1])");
                    continue;
                }
            } else if (action.equalsIgnoreCase("U")){
                if (board.checkValidSquare(square)){
                    if (!board.uncoverSquare(square)){
                        System.out.println("You lose!");
                        System.out.println("Would you like to play again? (Y/N)");
                        if (in.next().equalsIgnoreCase("N")){
                            break;
                        } else {
                            board = new Board(cols, rows, mines);
                            continue;
                        }
                    }
                } else {
                    System.out.println("Invalid square! Please enter valid coordinates (ex. [A1])");
                    continue;
                }
            } else {
                System.out.println("Invalid action! Please enter either [U] or [F]");
                continue;
            }

            if (board.checkWin()){
                System.out.println("You won!");
                System.out.println("Would you like to play again? (Y/N)");
                if (in.next().equalsIgnoreCase("N"))
                    break;
                else
                    board = new Board(cols, rows, mines);
            }
        }
    }
}

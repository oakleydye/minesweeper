import java.util.*;

public class Board {

    //region class members
    //Defaults
    private int rows = 10;
    private int cols = 10;
    private int mines = 10;
    private final char[] alpha = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private List<List<String>> boardMatrix;
    private List<List<String>> userBoardMatrix;
    //endregion

    //region Constructors
    public Board(int cols, int rows, int mines){
        if (cols <= 26 &&  cols >= 1) {
            this.cols = cols;
        }
        if (rows > 0){
            this.rows = rows;
        }
        if (mines > 0){
            this.mines = mines;
        }

        createBoardMatrix(false);
        placeMines();
        createBoardMatrix(true);
        printBoard(userBoardMatrix);
    }
    //endregion

    //region Public Methods
    public boolean checkWin(){
        int mineCount = 0;
        int flagCount = 0;
        int unknownSquares = 0;
        for (int i = 0; i < userBoardMatrix.size(); i++){
            for (int j = 0; j < userBoardMatrix.get(i).size(); j++){
                if (userBoardMatrix.get(i).get(j).equals("F") && boardMatrix.get(i).get(j).equals("*"))
                    mineCount++;
                if (userBoardMatrix.get(i).get(j).equals("F"))
                    flagCount++;
                if (userBoardMatrix.get(i).get(j).equals("?"))
                    unknownSquares++;
            }
        }
        return (mineCount == flagCount && mineCount == mines) || unknownSquares == mines || (mineCount == flagCount && unknownSquares + mineCount == mines);
    }

    public boolean uncoverSquare(String square){
        int rowIndex = Integer.parseInt(square.substring(1)) - 1;
        int colIndex = getIndexOfCharInAlphaArray(square.toUpperCase().charAt(0));

        //Uncover a mine
        if (boardMatrix.get(rowIndex).get(colIndex).equals("*")){
            userBoardMatrix.get(rowIndex).set(colIndex, "*");
            printBoard(userBoardMatrix);
            return false;
        }

        //Empty square
        if (boardMatrix.get(rowIndex).get(colIndex).equals(" ")){
            userBoardMatrix.get(rowIndex).set(colIndex, " ");
            uncoverAdjacentSquares(rowIndex, colIndex);
        }

        printBoard(userBoardMatrix);
        return true;
    }

    public void flagSquare(String square){
        int rowIndex = Integer.parseInt(square.substring(1)) - 1;
        int colIndex = getIndexOfCharInAlphaArray(square.toUpperCase().charAt(0));
        userBoardMatrix.get(rowIndex).set(colIndex, "F");
        printBoard(userBoardMatrix);
    }

    public boolean checkValidSquare(String square){
        int colIndex = getIndexOfCharInAlphaArray(square.toUpperCase().charAt(0));
        int rowIndex = Integer.parseInt(square.substring(1));
        return colIndex != -1 && colIndex < cols && rowIndex < rows && rowIndex > -1;
    }
    //endregion

    //region Private Helper Methods
    private int getIndexOfCharInAlphaArray(char colName){
        for (int i = 0; i < alpha.length; i++) {
            char c = alpha[i];
            if (c == colName)
                return i;
        }
        return -1;
    }

    private void uncoverAdjacentSquares(int rowIndex, int colIndex){
        if (userBoardMatrix.get(rowIndex).get(colIndex).equals(" ")){
            boolean bCont = false;
            //region Check all adjacent squares
            //Check left
            if (colIndex > 0){
                int tempColIndex = colIndex - 1;
                if (tempColIndex >= 0) {
                    boolean bContTemp = uncoverSquare(rowIndex, tempColIndex);
                    if (bContTemp)
                        bCont = true;
                }
            }

            //Check right
            if (colIndex < cols){
                int tempColIndex = colIndex + 1;
                if (tempColIndex < cols){
                    boolean bContTemp = uncoverSquare(rowIndex, tempColIndex);
                    if (!bCont && bContTemp)
                        bCont = true;
                }
            }

            //Check up
            if (rowIndex > 0){
                int tempRowIndex = rowIndex - 1;
                if (tempRowIndex >= 0) {
                    boolean bContTemp = uncoverSquare(tempRowIndex, colIndex);
                    if (!bCont && bContTemp)
                        bCont = true;
                }
            }

            //Check down
            if (rowIndex < rows){
                int tempRowIndex = rowIndex + 1;
                if (tempRowIndex < rows){
                    boolean bContTemp = uncoverSquare(tempRowIndex, colIndex);
                    if (!bCont && bContTemp)
                        bCont = true;
                }
            }

            //Check up-left
            if (rowIndex > 0 && colIndex > 0){
                int tempRowIndex = rowIndex - 1;
                int tempColIndex = colIndex - 1;
                if (tempRowIndex >= 0 && tempColIndex >= 0){
                    boolean bContTemp = uncoverSquare(tempRowIndex, tempColIndex);
                    if (!bCont && bContTemp)
                        bCont = true;
                }
            }

            //Check up-right
            if (rowIndex > 0 && colIndex < cols){
                int tempRowIndex = rowIndex - 1;
                int tempColIndex = colIndex + 1;
                if (tempRowIndex >= 0 && tempColIndex < cols){
                    boolean bContTemp = uncoverSquare(tempRowIndex, tempColIndex);
                    if (!bCont && bContTemp)
                        bCont = true;
                }
            }

            //Check down-left
            if (rowIndex < rows && colIndex > 0){
                int tempRowIndex = rowIndex + 1;
                int tempColIndex = colIndex - 1;
                if (tempRowIndex < rows && tempColIndex >= 0){
                    boolean bContTemp = uncoverSquare(tempRowIndex, tempColIndex);
                    if (!bCont && bContTemp)
                        bCont = true;
                }
            }

            //Check down-right
            if (rowIndex < rows && colIndex < cols){
                int tempRowIndex = rowIndex + 1;
                int tempColIndex = colIndex + 1;
                if (tempRowIndex < rows && tempColIndex < cols){
                    boolean bContTemp = uncoverSquare(tempRowIndex, tempColIndex);
                    if (!bCont && bContTemp)
                        bCont = true;
                }
            }
            //endregion
        }
    }

    private boolean uncoverSquare(int rowIndex, int colIndex){
        if (!boardMatrix.get(rowIndex).get(colIndex).equals(" ")){
            if (boardMatrix.get(rowIndex).get(colIndex).equals("*"))
                return false;

            userBoardMatrix.get(rowIndex).set(colIndex, boardMatrix.get(rowIndex).get(colIndex));
            return false;
        }
        userBoardMatrix.get(rowIndex).set(colIndex, boardMatrix.get(rowIndex).get(colIndex));
        return true;
    }

    private void createBoardMatrix(boolean isUserBoard){
        List<List<String>> tempBoard = new ArrayList<>();
        for (int i = 0; i < rows; i++){
            tempBoard.add(new ArrayList<>());
            for (int j = 0; j < cols; j++){
                tempBoard.get(i).add("?");
            }
        }
        if (isUserBoard)
            userBoardMatrix = tempBoard;
        else
            boardMatrix = tempBoard;
    }

    private void placeMines(){
        Random random = new Random();
        for (int i = 0; i < mines; i++){
            int colSpace = random.nextInt(cols);
            int rowSpace = random.nextInt(rows);

            //Make sure that we don't place mines in the same spot twice
            if (!boardMatrix.get(rowSpace).get(colSpace).equals("*"))
                boardMatrix.get(rowSpace).set(colSpace, "*");
            else
                i--;
        }

        for (int j = 0; j < boardMatrix.size(); j++)
            for (int k = 0; k < boardMatrix.get(j).size(); k++)
                if (!boardMatrix.get(j).get(k).equals("*"))
                    boardMatrix.get(j).set(k, " ");
    }

    private void printBoard(List<List<String>> board){
        System.out.println();
        StringBuilder colHeaders = new StringBuilder();
        int maxRowIndicatorSize = String.valueOf(rows).length();
        if (maxRowIndicatorSize > 1){
            colHeaders.append(" ".repeat(maxRowIndicatorSize - 1));
        }
        colHeaders.append("  ");
        for (int i = 0; i < cols; i++){
            colHeaders.append(" ");
            colHeaders.append(alpha[i]);
        }
        System.out.println(colHeaders);

        int j = 1;
        for (List<String> row : board){
            StringBuilder rowString = new StringBuilder();
            if (maxRowIndicatorSize > 1 && String.valueOf(j).length() != maxRowIndicatorSize){
                rowString.append(" ".repeat(Math.max(0, maxRowIndicatorSize - String.valueOf(j).length())));
            }
            rowString.append(j);
            rowString.append(" ");
            for (String colValue : row){
                rowString.append(" ");
                rowString.append(colValue);
            }
            System.out.println(rowString);
            j++;
        }
    }
    //endregion
}

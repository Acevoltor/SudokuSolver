

public class App {


    public static void main(String[] args) throws Exception {
        int[][] sudoku = new int[9][9];
        // Initialise the board to zero everywhere
        for(int i = 0; i < sudoku.length; i++){
            for(int j = 0; j < sudoku.length; j++){
                sudoku[i][j] = 0;
            }
        }
        sudoku[0][0] = 1;
        sudoku[0][1] = 2;
        sudoku[0][2] = 3;
        sudoku[0][8] = 5;

        sudoku[1][4] = 6;
        sudoku[1][8] = 4;
        
        sudoku[2][5] = 7;
        sudoku[2][8] = 3;
        
        sudoku[3][2] = 8;
        sudoku[3][5] = 6;
        
        sudoku[4][1] = 9;
        sudoku[4][7] = 1;

        sudoku[5][3] = 4;
        sudoku[5][6] = 2;

        sudoku[6][0] = 7;
        sudoku[6][3] = 3;
        
        sudoku[7][0] = 6;
        sudoku[7][4] = 4;

        sudoku[8][0] = 5;
        sudoku[8][6] = 7;
        sudoku[8][7] = 8;
        sudoku[8][8] = 9;
        //22
        int[][] hint = new int[22][2];

        hint[0] = new int[]{0,0};
        hint[1] = new int[]{0,1};
        hint[2] = new int[]{0,2};
        hint[3] = new int[]{0,8};
        hint[4] = new int[]{1,4};
        hint[5] = new int[]{1,8};
        hint[6] = new int[]{2,5};
        hint[7] = new int[]{2,8};
        hint[8] = new int[]{3,2};
        hint[9] = new int[]{3,5};
        hint[10] = new int[]{4,1};
        hint[11] = new int[]{4,7};
        hint[12] = new int[]{5,3};
        hint[13] = new int[]{5,6};
        hint[14] = new int[]{6,0};
        hint[15] = new int[]{6,3};
        hint[16] = new int[]{7,0};
        hint[17] = new int[]{7,4};
        hint[18] = new int[]{8,0};
        hint[19] = new int[]{8,6};
        hint[20] = new int[]{8,7};
        hint[21] = new int[]{8,8};
        

        printBoard(sudoku);
        System.out.println("------Solved------");
        int [][] solved = solver(sudoku,hint);
        printBoard(solved);

    }

    static boolean isHint(int[][] hint ,int i,int j) {
        for (int k = 0; k < hint.length ; k++){
            if ((hint[k][0] == i) && (hint[k][1] == j) ){
                return true;
            }
        }
        return false;
    }

    static boolean checkRow(int[][] board , int i, int j , int value ){
        
        for(int y = 0; y < board[i].length; y++){
            if (value == board[i][y]) {
                return false;
           }
        }
        return true;
    } 

    static boolean checkColumn(int[][] board , int i, int j , int value ){
        
        for(int x = 0; x < board[i].length; x++){
            if (value == board[x][j]) {
                return false;
           }
        }
        return true;
    } 

   
    static boolean checkSquare(int[][] board , int i, int j , int value ){
        //linge
        int l = 3 * (i / 3) ;
        //colonne
        int k = 3 * (j / 3) ;
        
        for(int x = 0; x < 3; x++ ){
            for(int y = 0; y < 3; y++ ){
                if (value == board[l+x][k+y]){
                    return false;
                }
            }
        }

        return true;
    }
    
    static boolean isLegal(int[][] board , int i, int j , int value ){
        boolean legalite = (checkColumn(board, i, j, value) && checkRow(board, i, j, value) && checkSquare(board, i, j, value));
        return legalite;
    }

    static int[] findNext(int [][] board,int [][] hint, int i , int j){
        for(int x = i  ; x < board.length; x++){
            //x == i (cond) ? (walrus question mark operator) j + 1 (if fulfilled) : 0 (else)
            for(int y = x == i ? j + 1 : 0 ; y < board.length; y++){
                if (!isHint(hint,x,y)){
                    return new int[] {x,y};
                }
            }
        }
        return new int[]{-1,-1};
    }

    static boolean hasNext(int [][] board,int [][] hint, int i , int j){
        int [] next = findNext( board, hint, i , j);
        return (next[0] != -1 ) && (next[1] != -1);
    }

    static int[] findPrev(int [][] board,int [][] hint, int i , int j){
        for(int x = i  ; x >= 0; x--){
            //8 (max length array)
            for(int y = x == i ? j - 1 : 8 ; y >= 0; y--){
                if (!isHint(hint,x,y)){
                    return new int[] {x,y};
                }
            }
        }
        return new int[]{0,-1};
    }

    static int[][] solver(int[][] board , int [][] hint){
        int i = 0;
        int j = -1;
        while (hasNext(board,hint,i,j)) {
            int [] next = findNext(board,hint,i,j);
            int l = next[0];
            int k = next[1];
            int value = board[l][k] + 1;
            boolean found = false;
            // check for a next
            while (!found && (value < 10)) {
                if (isLegal(board,l,k,value)){
                    found = true;
                    board[l][k] = value;
                }
                    value ++;
            }
            // backtrack
            if (!found){
                int [] prev = findPrev(board,hint,i,j);
                
                board[l][k] = 0;
                i = prev[0];
                j = prev[1];
            }else{
                i = l;
                j = k;
            }

        }

        return board;
    }

    static void printBoard(int [][] board){
        for (int i = 0 ; i < 9 ; i ++){
            for (int j = 0 ; j < 9 ; j ++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

}





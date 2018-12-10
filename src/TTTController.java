public class TTTController
{

    // Current player's turn, either X or O
    private char whoseturn;
    // 2D array to store pieces placed on the board, ' ', 'X', 'O'
    private char[][] board;
    // Indicates if the game is over or not, if over do nothing on button click
    private boolean gameOver;
    // Label to display messages to the players
    public static final int BOARDSIZE = 3;

    public TTTController()
    {
        gameOver = false;
        board = new char[BOARDSIZE][BOARDSIZE];
        for (int y=0; y<BOARDSIZE; y++)
        {
            for (int x=0; x<BOARDSIZE; x++)
            {
                board[x][y]=' ';	// Initially no piece on each board location
            }
        }
        whoseturn = 'X';  // X goes first
    }

    public boolean isDone()
    {
        return gameOver;
    }
    public boolean isOccupied(int x, int y)
    {
        return(getBoard(x,y)!=' ');
    }
    public String playTurn(int x, int y)
    {
        String whomoved = new Character (getTurn()).toString();
        setBoard(x,y,getTurn());
        playerMoved();
        return whomoved;
    }
    public char getTurn()
    {
        return whoseturn;
    }

    /**
     * @param x The x coordinate of the board location we want to set
     * @param y The y coordinate of the board location we want to set
     * @param newValue the char to store at board[x][y]
     */
    public void setBoard(int x, int y, char newValue)
    {
        board[x][y] = newValue;
    }

    /**
     * @return char The character (' ', 'X', or 'Y') stored at board[x][y]
     */
    public char getBoard(int x, int y)
    {
        return (board[x][y]);
    }

    /**
     * Notifies the game that a player moved, so the current player should be toggled
     * to the other player.
     */
    public void playerMoved()
    {
        if (whoseturn=='X') whoseturn='O';
        else whoseturn = 'X';
    }

    /**
     * Checks if a player won the game or if there was a draw.  If the game is not over then the
     * method returns a null string.  If the game is over the method returns a string indicating the
     * winning player.
     */
    public String checkGameOver()
    {
        boolean win;
        char first;
        String message = null;

        // Check all rows across.  There is a winner if all
        // entries are the same character, but not ' '
        for (int y=0; y<BOARDSIZE; y++)
        {
            win = true;
            first = board[0][y];
            if (first != ' ')
            {
                for (int x=1; x<BOARDSIZE; x++)
                {
                    if (board[x][y]!=first) win=false;
                }
                if (win) {
                    message = first + " is the winner!";
                    gameOver = true;
                }
            }
        }
        // Check all rows down. There is a winner if all entries
        // are the same character, but not ' '
        for (int x=0; x<BOARDSIZE; x++)
        {
            win = true;
            first = board[x][0];
            if (first != ' ')
            {
                for (int y=1; y<BOARDSIZE; y++)
                {
                    if (board[x][y]!=first) win=false;
                }
                if (win) {
                    message = first + " is the winner!";
                    gameOver = true;
                }
            }
        }
        // Check diagonal top left to bottom right
        first = board[0][0];
        win = true;
        if (first != ' ')
        {
            for (int x=1; x<BOARDSIZE; x++)
            {
                if (board[x][x]!=first) win=false;
            }
            if (win) {
                message = first + " is the winner!";
                gameOver = true;
            }
        }
        // Check diagonal bottom left to top right
        first = board[0][BOARDSIZE-1];
        win = true;
        if (first != ' ')
        {
            for (int x=1; x<BOARDSIZE; x++)
            {
                if (board[x][BOARDSIZE-1-x]!=first) win=false;
            }
            if (win) {
                message = first + " is the winner!";
                gameOver = true;
            }
        }

        // Check if board is full.  If so, there is a draw.
        int numpieces = 0;
        for (int x=0; x<BOARDSIZE; x++)
            for (int y=0; y<BOARDSIZE; y++)
            {
                if (board[x][y]!=' ') numpieces++;
            }
        if (numpieces==BOARDSIZE*BOARDSIZE)
        {
            message = "The game is a draw!";
            gameOver = true;
        }
        return(message);
    }
}

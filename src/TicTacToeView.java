/**
 * TicTacToe.java
 *
 * This program uses Swing to play a simple game of Tic Tac Toe
 * (noughts and crosses).  It uses a GridLayout of JButton's to
 * play the game.  Each JButton has its own ButtonListener class
 * that updates an internal 2D game board and checks to see if the
 * game is over.
 * The GridLayout is embedded in the CENTER of a BorderLayout so
 * we can put a JLabel at the top of the window to display
 * any messages.
 *
 * Created: Sat Apr 16 2005
 *
 * @author Hadi Aamir
 * @version 1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * TicTacToe GUI to play the game
 */
public class TicTacToeView extends JFrame
{

    //	 Dimensions of the game board, should be square
    //  use a static variable for this
    private static int xGrid = 500;
    private static int yGrid = 500;
    JPanel boardPanel, buttonPanel;


    // the game controller (separate class for MVC pattern
    // the model is actually implicit in the controller for this game
    // because its such a simple game
    private TTTController controller;

    // Label to display messages to the players
    private JLabel messageLabel;

        /**
         * The TicTacToeView constructor creates the JFrame and
         * adds 9 JButtons to the grid layout.
         * The action listener is created using the ButtonListener
         * class described above.
         */
        public TicTacToeView()
        {
            // call the superclass constructor
            super();
            // instantiate the controller
            controller = new TTTController();

            gameView();
        }

    public void gameView()
    {
        // set the size, title and default close of the JFrame
        setTitle("Tic Tac Toe");
        setSize(500, 530);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        // make a new label to store messages and assign it to the instance variable
        messageLabel = new JLabel();

        // Layout for JFrame is a border layout
        setLayout(new BorderLayout());
        add(messageLabel, BorderLayout.NORTH);	// Messages go on top

        // Make a panel to store the game board buttons
        boardPanel = new JPanel();

        buttonPanel = new JPanel();
        getContentPane().add(boardPanel);
        // put the button Panel in the center of the main window using the border layout
        boardPanel.add(buttonPanel, BorderLayout.CENTER);
        // Organize the panel using a grid layout sized the same as the board size
        buttonPanel.setLayout(new GridBagLayout());
        //Set up components preferred size
        buttonPanel.setPreferredSize(new Dimension(xGrid, yGrid));




       for (int y=0; y<TTTController.BOARDSIZE; y++)
        {
            for (int x=0; x<TTTController.BOARDSIZE; x++)
            {
                //Create buttons and link each button back to a coordinate on the grid
                // add listeners to the buttons in the same loop
                GridBagConstraints gbc = new GridBagConstraints();
                JButton boardButton = new JButton();
                gbc.gridx = x;
                gbc.gridy = y;
                gbc.ipadx = 90;
                gbc.ipady = 125;
                boardButton.addActionListener(new ButtonListener(x, y, boardButton));
                buttonPanel.add(boardButton, gbc);

            }
        }

        updateMove();
    }
    
    private void updateMove()
    {
        messageLabel.setText("Player: " + controller.getTurn() + " Turn");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }



    /**
     * This inner class is the ActionListener for one of the button clicks.
     * The constructor takes the X and Y coordinate for the
     * entry in the game board
     * that corresponds to where this button is located.
     */
    private class ButtonListener implements ActionListener
    {

        // integer Coordinates on the game board for this button
        private int buttonX;
        private int buttonY;

        // instance variable reference to the JButton object for each ActionListener object
        JButton boardButton;

        /**
         * The constructor stores references to the coordinates and the button
         * @param x  X coordinate on the game board for this button
         * @param y  Y coordinate on the game board for this button
         * @param but  The actual button object so we can change the text
         *
         */
        ButtonListener(int x, int y, JButton but)
        {
            this.buttonX = x;
            this.buttonY = y;
            this.boardButton = but;
        }

        /**
         * The actionPerformed event is invoked when a button is clicked.
         * Check to see if the cell that corresponds to this button is clear;
         * if not it is an invalid move.  If clear, put a piece there,
         * update the button text with the piece label,
         * update the game board, and see if someone won.
         */
         @Override
        public void actionPerformed(ActionEvent e)
        {
            // Check to see if the game is over
            if(controller.isDone())// game is done
            {
                // change this part of the code so that it pops up a JOptionPane
                // as well as setting the label text

                // check to see  if the game is over and alert the players about the result
                messageLabel.setText(controller.checkGameOver());

                //default icon, custom title
                JFrame dialogFrame = new JFrame();
                String dialogMsg = "Would you like to play again?";
                String dialogTitle = "Game Over";
                int reply = JOptionPane.showConfirmDialog(dialogFrame, dialogMsg, dialogTitle, JOptionPane.YES_NO_OPTION);

                if(reply == JOptionPane.YES_OPTION)
                {
                    /*
                    UNFINISHED
                     */
                }
                else
                {
                    new TicTacToeView().dispatchEvent(new WindowEvent(new TicTacToeView(), WindowEvent.WINDOW_CLOSING));
                }

                return;
            }
            // find out if the cell is occupied on the game board and display
            // appropriate message
            if(controller.isOccupied(buttonX, buttonY))// cell is occupied
            {
                messageLabel.setText("Cell is occupied");
                messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }

			else // cell is not occupied
            {
                //if it isn't,  let the controller know that the player is marking that location
                // get the returned string from the controller to set the button label
                boardButton.setText(controller.playTurn(buttonX, buttonY));
                updateMove();
            }
            controller.checkGameOver();
        }


    }
    /*End of Inner Class Definition */


    // The main method does nothing but create a new GUI object and set it visible
        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    new TicTacToeView().setVisible(true);
                }
            });

        }

}

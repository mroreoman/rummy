import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Rummy {
    private static Queue<Card> drawPile;
    private static Stack<Card> discardPile;
    private static Player player;
    private static ComputerPlayer computer;

    public static void main(String[] args) {
        do {
            start(10);
            while (turn());
        } while (finish());
    }

    /**
     * start a game of rummy
     * @param handSize - player hand size
     */
    private static void start(int handSize) {
        ArrayList<Card> deck = new ArrayList<Card>(Arrays.asList(Card.fullDeck()));
        Collections.shuffle(deck);
        drawPile = new LinkedList<Card>(deck);
        discardPile = new Stack<Card>();
        player = new Player(handSize);
        computer = new ComputerPlayer(handSize);
        for (int i = 0; i < handSize; i++) {
            player.draw(drawPile.remove());
            computer.draw(drawPile.remove());
        }
        
    }

    /**
     * play a turn of rummy
     * @return if the game should continue
     */
    private static boolean turn() {
        return false; //TODO
    }

    /**
     * finishes a game of rummy
     * @return if they want to play a new game
     */
    private static boolean finish() {
        return false; //TODO
    }
}

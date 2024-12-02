import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Rummy {
    private Queue<Card> drawPile;
    private Stack<Card> discardPile;
    private Player player;
    private ComputerPlayer computer;
    private int handSize;
    private Scanner scan;

    /**
     * initalizes all objects and deals starting hands
     * @param handSize - player hand size
     */
    public Rummy(int handSize) {
        System.out.println("Starting game of rummy with hand size " + handSize + ".");
        drawPile = new LinkedList<Card>();
        discardPile = new Stack<Card>();
        player = new Player(handSize);
        computer = new ComputerPlayer(handSize);
        this.handSize = handSize;
    }

    public void start() {
        System.out.println("Shuffling deck.");
        List<Card> deck = Arrays.asList(Card.DECK);
        Collections.shuffle(deck);
        drawPile.addAll(deck);
        // System.out.println(drawPile);

        System.out.println("Dealing cards.");
        for (int i = 0; i < handSize; i++) {
            player.draw(drawPile.remove());
            computer.draw(drawPile.remove());
        }
        // System.out.println("player: " + player);
        // System.out.println("computer: " + computer);

        System.out.println("Starting game.");
        scan = new Scanner(System.in);
        while (turn());
    }

    /**
     * play a turn of rummy
     * @return if the game should continue
     */
    private boolean turn() {
        System.out.println("=============");
        System.out.println("Your hand: " + player);
        if (discardPile.empty()) {
            System.out.println("1 - Draw from draw pile");
            System.out.println("2 - (n/a)");
            System.out.println("3 - Rearrange hand");
        } else {
            System.out.println("Top of discard: " + discardPile.peek());
            System.out.println("1 - Draw from draw pile");
            System.out.println("2 - Draw from discard pile");
            System.out.println("3 - Rearrange hand");
        }
        System.out.print("enter your choice: ");
        switch (scan.next()) {
            case "1":
                drawCard(drawPile.remove());
                break;
            case "2":
                if (discardPile.empty()) {
                    System.out.println("invalid choice");
                    return true;
                }
                drawCard(discardPile.pop());
                break;
            case "3":
                rearrangeHand();
                return true;
            default:
                System.out.println("invalid choice");
                return true;
        }
        
        if (player.won() || computer.won()) {
            finish();
            return false;
        }

        return true;
    }

    private void drawCard(Card newCard) { //TODO: finish method
        System.out.println("Drawing card.");
        System.out.println("Your hand: " + player + "    " + newCard);
        System.out.print("Enter card to discard (J♠/Js are valid formats): ");
        String cardStr = scan.next().toLowerCase();
        Card card = new Card(cardStr); // throws exception if invalid card
        // if card is new card
            // return?
        // else if card is in hand
            // do things
        // else
            // print invalid card
            // ask for input again
    }

    private void rearrangeHand() { //TODO: add manual swapping
        player.sortHand();
    }

    private void finish() {
        //TODO: make this method
    }
}

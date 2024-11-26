import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Rummy {
    private Queue<Card> drawPile;
    private Stack<Card> discardPile;
    private Player player;
    private ComputerPlayer computer;
    private Scanner scan;

    /**
     * initalizes all objects and deals starting hands
     * @param handSize - player hand size
     */
    public Rummy(int handSize) {
        ArrayList<Card> deck = new ArrayList<Card>(Arrays.asList(Card.fullDeck()));
        Collections.shuffle(deck);
        drawPile = new LinkedList<Card>(deck);
        discardPile = new Stack<Card>();
        player = new Player(handSize);
        computer = new ComputerPlayer(handSize);
        scan = new Scanner(System.in);
        for (int i = 0; i < handSize; i++) {
            player.draw(drawPile.remove());
            computer.draw(drawPile.remove());
        }
    }

    /**
     * play a turn of rummy
     * @return if the game should continue
     */
    public boolean turn() { //TODO: handle invalid choices, maybe need to do other stuff too
        System.out.println("Your hand: " + player);
        System.out.println("Top of discard: " + (String)(discardPile.empty() ? "empty" : discardPile.peek()));
        System.out.println("1 - Draw from draw pile");
        System.out.println("2 - Draw from discard pile");
        System.out.println("3 - Rearrange hand");
        System.out.print("enter your choice: ");
        Card newCard;
        switch (scan.next()) {
            case "1":
                newCard = drawPile.remove();
                break;
            case "2":
                if (discardPile.empty())
                    return true;
                newCard = discardPile.pop();
                break;
            case "3":
                rearrangeHand();
                return true;
            default:
                System.out.println("invalid choice");
                return true;
        }

        System.out.println("new card: " + newCard);
        System.out.println("Enter \"new\" to discard new card");
        System.out.println("Enter number to discard card from hand");
        String choice = scan.next().toLowerCase();
        if (choice.equals("new")) {
            discardPile.add(newCard);
        } else {
            try {
                int num = Integer.parseInt(choice);
                if (num >= 1 && num <= player.getHandSize()) {
                    discardPile.add(player.discard(num));
                    player.draw(newCard);
                } else {
                    System.out.println("invalid number choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid choice");
            }
        }
        
        if (player.won() || computer.won()) {
            finish();
            return false;
        }

        return true;
    }

    private void rearrangeHand() {
        //TODO: make this method
    }

    /** finishes a game of rummy */
    private void finish() {
        //TODO: make this method
    }
}

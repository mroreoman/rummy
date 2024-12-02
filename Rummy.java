import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Rummy {
    private Queue<Card> stock;
    private Stack<Card> discardPile;
    private Player player;
    private ComputerPlayer computer;
    private Scanner scan;
    private int handSize = 10;

    /**
     * initalizes all objects and deals starting hands
     */
    public Rummy() {
        System.out.println("Starting game of rummy with hand size " + handSize + ".");
        stock = new LinkedList<Card>();
        discardPile = new Stack<Card>();
        player = new Player(handSize);
        computer = new ComputerPlayer(handSize);
    }

    public void start() {
        System.out.println("Shuffling deck.");
        List<Card> deck = Arrays.asList(Card.DECK);
        Collections.shuffle(deck);
        stock.addAll(deck);
        // System.out.println(stock);

        System.out.println("Dealing cards.");
        for (int i = 0; i < handSize; i++) {
            player.draw(stock.remove());
            computer.draw(stock.remove());
        }
        player.sortHand();
        discardPile.add(stock.remove());
        // System.out.println("player: " + player);
        // System.out.println("computer: " + computer);
        // System.out.println("discard pile: " + discardPile);

        System.out.println("Starting game.");
        scan = new Scanner(System.in);
        while (turn());
    }

    /**
     * play a turn of rummy
     * @return if the game should continue
     */
    private boolean turn() {
        System.out.println("\nYour hand: " + player);
        System.out.println("Top of discard: " + discardPile.peek());
        //TODO: display melds
        System.out.println("1 - Draw from stock");
        System.out.println("2 - Draw from discard pile");
        System.out.println("3 - Rearrange hand");
        System.out.print("Enter your choice: ");
        switch (scan.next()) {
            case "1":
                System.out.println("\n\tDrawing card from stock.");
                drawCard(stock.remove(), false);
                break;
            case "2":
                System.out.println("\n\tDrawing card from discard pile.");
                drawCard(discardPile.pop(), true);
                break;
            case "3":
                System.out.println("\n\tRearranging hand.");
                rearrangeHand();
                return true;
            default:
                System.out.println(TextColoring.error("Invalid choice!"));
                return true;
        }
        
        if (player.won()) {
            System.out.println("You won!!!");
            return false;
        } else if (computer.won()) {
            System.out.println("The computer won :(");
            return false;
        } else {
            return true;
        }
    }

    private void drawCard(Card newCard, boolean fromDiscard) {
        System.out.println("\tYour hand: " + player);
        System.out.println("\tNew card: " + newCard);

        //TODO: place down meld or add to melds

        while (true) {
            System.out.print("\tEnter card (like A\u0005/2\u0006/Js) to discard, or r to return new card: ");
            String cardStr = scan.next();
            Card card;
            if (cardStr.equalsIgnoreCase("r")) {
                card = newCard;
            } else {
                try {
                    card = new Card(cardStr);
                } catch (IllegalArgumentException e) {
                    System.out.println(TextColoring.error("\tInvalid card!"));
                    continue;
                }
            }

            if (card.equals(newCard)) {
                if (fromDiscard) {
                    System.out.println(TextColoring.error("\tYou can't return after drawing from the discard pile!"));
                    continue;
                } else {
                    System.out.println("\tReturning card.");
                    discardPile.add(newCard);
                    return;
                }
            } else if (player.handContains(card)) {
                System.out.println("\tDiscarding " + card + ".");
                player.discard(card);
                discardPile.add(card);
                player.draw(newCard);
                return;
            } else {
                System.out.println(TextColoring.error("\tInvalid card!"));
                continue;
            }
        }
    }

    private void rearrangeHand() {
        player.sortHand();
    }
}

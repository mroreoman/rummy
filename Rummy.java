import java.util.ArrayList;
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
    private List<List<Card>> melds;
    private Player player;
    private ComputerPlayer computer;
    private Scanner scan;
    private Output out;
    private int handSize = 10;

    public Rummy() {
        out = new Output();
        out.println("Created game of rummy with hand size " + handSize + ".");
    }

    public void start() {
        out.println("Shuffling deck.");
        List<Card> deck = Arrays.asList(Card.DECK);
        Collections.shuffle(deck);
        stock = new LinkedList<>(deck);

        out.println("Dealing cards.");
        List<Card> playerHand = new ArrayList<>(handSize);
        List<Card> computerHand = new ArrayList<>(handSize);
        for (int i = 0; i < handSize; i++) {
            playerHand.add(stock.remove());
            computerHand.add(stock.remove());
        }
        player = new Player(playerHand);
        computer = new ComputerPlayer(computerHand);
        
        discardPile = new Stack<>();
        melds = new ArrayList<>();
        discardPile.push(stock.remove());
        // out.println("player: " + player);
        // out.println("computer: " + computer);
        // out.println("discard pile: " + discardPile);
        // out.println("stock: " + stock);

        out.println("Starting game.");
        scan = new Scanner(System.in);
        while (turn());
    }

    /**
     * play a turn of rummy
     * @return if the game should continue
     */
    private boolean turn() {
        out.println("======================================");
        out.println("Your hand: " + player);
        out.println("Top of discard: " + discardPile.peek());
        out.print("Melds: ");
        for (List<Card> meld : melds) {
            out.print(meld);
        }
        out.println();

        boolean repeat = true;
        while (repeat) {
            out.println("1 - Draw from stock");
            out.println("2 - Draw from discard pile");
            out.println("3 - Rearrange hand");
            out.print("Enter your choice: ");
            String choice = scan.next();
            out.indent();
            switch (choice) {
                case "1":
                    drawCard(stock.remove(), false);
                    repeat = false;
                    break;
                case "2":
                    drawCard(discardPile.pop(), true);
                    repeat = false;
                    break;
                case "3":
                    rearrangeHand();
                    repeat = false;
                    break;
                default:
                    out.println(Output.error("Invalid choice!"));
                    break;
            }
            out.outdent();
        }
        
        if (player.won()) {
            out.println("You won!!!");
            return false;
        } else if (computer.won()) {
            out.println("The computer won :(");
            return false;
        } else {
            return true;
        }
    }

    private void drawCard(Card newCard, boolean fromDiscard) {
        player.draw(newCard);
        if (fromDiscard) {
            out.println("Drew " + newCard + " from discard pile.");
        } else {
            out.println("Drew " + newCard + " from stock.");
        }
        out.println("Your hand: " + player);

        boolean repeat = true;
        while (repeat) {
            out.println("1. Lay down meld");
            out.println("2. Add to meld");
            out.println("3. Rearrange hand");
            out.println("4. Discard card (ends turn)");
            out.print("Enter your choice: ");
            String choice = scan.next();
            out.indent();
            switch (choice) {
                case "1":
                    out.println("Laying down meld.");
                    // input 3-10 cards
                    // check if valid set/sequence
                    break;
                case "2":
                    out.println("Adding to meld.");
                    // ask which meld they want to add to
                    // ask which card they want to add (with option to go back)
                    // check if it would create a valid set/sequence
                    break;
                case "3":
                    rearrangeHand();
                    break;
                case "4":
                    discardCard(fromDiscard);
                    repeat = false;
                    break;
                default:
                    out.println(Output.error("Invalid choice!"));
                    break;
            }
            out.outdent();
        }
    }

    private void discardCard(boolean fromDiscard) {
        out.println("Discarding card.");
        while (true) {
            out.print("Enter card (like A\u0005/2\u0006/Js) to discard: ");
            String cardStr = scan.next();
            Card card;
            try {
                card = new Card(cardStr);
            } catch (IllegalArgumentException e) {
                out.println(Output.error("Invalid card!"));
                continue;
            }

            if (player.isNewCard(card) && fromDiscard) {
                out.println(Output.error("You can't return a card after drawing it from the discard pile!"));
                continue;
            } else if (player.handContains(card)) {
                out.println("Discarding " + card + ".");
                player.discard(card);
                discardPile.push(card);
                return;
            } else {
                out.println(Output.error("Invalid card!"));
                continue;
            }
        }
    }

    private void rearrangeHand() {
        out.println("Rearranging hand.");
        player.sortHand();
        out.println("Auto sorted hand.");
    }
}

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
    private List<Meld> melds;
    private Player player;
    private ComputerPlayer computer;
    private Scanner scan;
    private Output out;
    private int handSize = 10;

    private int turn = 1;

    public Rummy() {
        out = new Output();
        out.println("Creating game of rummy with hand size " + handSize + ".");

        out.println("Shuffling deck.");
        List<Card> deck = Arrays.asList(Card.DECK);
        Collections.shuffle(deck);
        stock = new LinkedList<>(deck);

        out.println("Dealing cards.");
        List<Card> playerHand = new ArrayList<>(handSize);
        List<Card> computerHand = new ArrayList<>(handSize);
        for (int i = 0; i < handSize; i++) {
            playerHand.add(stock.remove());
            // computerHand.add(stock.remove()); TODO: put back when implementing computer player
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
    }

    public static void printInstructions() {
        System.out.println("Welcome to Rummy!");
        System.out.println("The goal of the game is to get rid of all of your cards.");
        System.out.println("You get rid of cards by making melds and placing them on the table, or adding to the melds on the table.");
        System.out.println("A meld is a group of cards of 3 or more cards that either...");
        System.out.println("  - all have the same rank (like a 3 or 4 of a kind), or");
        System.out.println("  - are a sequence within one suit (like a straight flush, except you can wrap around like Q K A 2).");
        System.out.println("You start each turn by drawing a card from either the stock or the discard pile.");
        System.out.println("Then you will take any actions you wish to take, such as laying down or adding to a meld.");
        System.out.println("Finally, you will discard a card to end your turn.");
        System.out.println("Have fun!");
        System.out.println();
    }

    public void play() {
        out.println("Starting game.");
        scan = new Scanner(System.in);

        while (turn());

        scan.close();

        out.println("================= Game Done =================");
        if (player.won()) {
            out.println("you won!!!");
        } else if (computer.won()) {
            out.println("the computer won :(");
        }
        out.println("Melds: " + melds);
        out.println("Your hand: " + player);
        out.println("Computer's hand: " + computer);
        out.println("Top of discard: " + discardPile.peek());
    }

    /**
     * play a turn of rummy
     * @return {@code true} if the game should continue
     */
    private boolean turn() {
        out.println();
        out.println("================== " + "Turn " + turn++ + " ==================");
        
        Card drawn;
        do {
            drawn = drawMenu();
        } while (drawn == null);

        out.indent();
        while (!discardMenu(drawn));
        out.outdent();
        
        if (player.won() || computer.won()) {
            return false;
        } else {
            return true;
        }
    }

    // returns null if player did not draw a card
    private Card drawMenu() {
        out.println();
        out.println("Your hand: " + player);
        out.println("Melds: " + melds);
        out.println("Top of discard: " + discardPile.peek());
        out.println();
        out.println("1 - Draw from stock");
        out.println("2 - Draw from discard pile");
        out.println("3 - Sort hand by rank");
        out.println("4 - Sort hand by suit");
        out.print("Enter your choice: ");
        String choice = scan.next();
        scan.nextLine();
        out.indent();
        Card drawnCard = null;
        switch (choice) {
            case "1":
                drawnCard = stock.remove();
                player.draw(drawnCard);
                out.println();
                out.println("Drew " + drawnCard + " from stock.");
                if (stock.isEmpty()) {
                    stock.addAll(discardPile);
                    discardPile.clear();
                   out.println("Recycled discard pile");
                }
                break;
            case "2":
                drawnCard = discardPile.pop();
                player.draw(drawnCard);
                out.println();
                out.println("Drew " + drawnCard + " from discard pile.");
                break;
            case "3":
                player.sortByRank();
                out.println("\n\tSorted hand by rank.");
                break;
            case "4":
                player.sortBySuit();
                out.println("\n\tSorted hand by suit.");
                break;
            default:
                out.println();
                out.println(Output.error("Invalid choice!"));
                break;
        }
        out.outdent();
        return drawnCard;
    }

    // returns true if player discarded a card and turn should end
    private boolean discardMenu(Card drawnCard) {
        out.println();
        out.println("Your hand: " + player);
        out.println("Melds: " + melds);
        out.println("Drawn card: " + drawnCard);
        out.println();
        out.println("1 - Lay down meld");
        out.println("2 - Add to meld");
        out.println("3 - Sort hand by rank");
        out.println("4 - Sort hand by suit");
        out.println("5 - Choose card to discard (ends turn)");
        out.println("6 - Discard drawn card (ends turn)");
        out.print("Enter your choice: ");
        String choice = scan.next();
        scan.nextLine();
        out.indent();
        Card toDiscard = null;
        switch (choice) {
            case "1":
                layMeld();
                break;
            case "2":
                addToMeld();
                break;
            case "3":
                player.sortByRank();
                break;
            case "4":
                player.sortBySuit();
                break;
            case "5":
                out.println();
                out.print("Enter card (like A\u0005 or As) to discard, or x to cancel: ");
                String input = scan.next();
                scan.nextLine();
                if (input.equalsIgnoreCase("x")) {
                    out.println("Cancelling discard.");
                    break;
                }
                try {
                    toDiscard = selectCardFromHand(input);
                } catch (IllegalArgumentException e) {
                    out.println(Output.error(e.getMessage()));
                } catch (IllegalStateException e) {
                    out.println(Output.error(e.getMessage()));
                }
                break;
            case "6":
                if (player.handContains(drawnCard)) {
                    toDiscard = drawnCard;
                } else {
                    out.println();
                    out.println(Output.error("You can't discard a card after laying it down!"));
                }
                break;
            default:
                out.println(Output.error("Invalid choice!"));
                break;
        }
        out.outdent();
        if (toDiscard != null) {
            discardPile.push(player.discard(toDiscard));
            out.println();
            out.println("Discarded " + toDiscard + ".");
            return true;
        }
        return false;
    }

    private void layMeld() {
        out.println();
        out.println("Selecting cards for meld.");
        Meld meld = new Meld();
        while (true) {
            out.print("Enter card (like A\u0005/2\u0006/Js), f to finish the meld, or x to cancel: ");
            String input = scan.next();
            scan.nextLine();
            if (input.equalsIgnoreCase("x")) {
                out.println("Cancelling meld.");
                return;
            } else if (input.equalsIgnoreCase("f")) {
                if (meld.isComplete()) {
                    out.println("Laying " + meld + " on the table.");
                    player.layCards(meld);
                    melds.add(meld);
                    return;
                } else {
                    out.println(Output.error("Meld is incomplete!"));
                    continue;
                }
            }

            Card card;
            try {
                card = new Card(input);
            } catch (IllegalArgumentException e) {
                out.println(Output.error("Invalid card!"));
                continue;
            }
            
            if (!player.handContains(card)) {
                out.println(Output.error("Card not in hand!"));
                continue;
            }

            if (meld.addCard(card)) {
                out.println("Meld: " + meld);
            }
        }
    }

    //TODO: make this nicer
    private void addToMeld() {
        out.println();
        out.println("Selecting meld to add to.");
        Meld meld = null;
        while (meld == null) {
            out.println("Melds: " + melds);
            out.print("Enter a card that is in the meld you want to select, or x to cancel: ");
            String input = scan.next();
            scan.nextLine();
            if (input.equalsIgnoreCase("x")) {
                out.println("Cancelling addToMeld.");
                return;
            }

            Card card;
            try {
                card = new Card(input);
            } catch (IllegalArgumentException e) {
                out.println(Output.error("Invalid card!"));
                continue;
            }

            meld = melds.stream().filter(m -> m.contains(card)).findAny().orElse(null);
            if (meld == null) {
                out.println(Output.error("No meld contains that card!"));
                continue;
            } else {
                out.println("Selected meld " + meld);
            }
        }
        
        out.println("Selecting card to add to meld");
        while (true) {
            out.print("Enter card (like A\u0005/2\u0006/Js), or x to cancel: ");
            String input = scan.next();
            scan.nextLine();
            if (input.equalsIgnoreCase("x")) {
                out.println("Cancelling addToMeld.");
                return;
            }

            Card card;
            try {
                card = new Card(input);
            } catch (IllegalArgumentException e) {
                out.println(Output.error("Invalid card!"));
                continue;
            }
            
            if (!player.handContains(card)) {
                out.println(Output.error("Card not in hand!"));
                continue;
            }

            if (meld.addCard(card)) {
                out.println("Meld: " + meld);
                player.layCard(card);
                return;
            }
        }
    }

    private Card selectCardFromHand(String cardStr) {
        Card card;
        try {
            card = new Card(cardStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid card!");
        }
        if (player.handContains(card)) {
            out.println("Selected " + card);
            return card;
        } else {
            throw new IllegalStateException("Card not in hand!");
        }
    }
}

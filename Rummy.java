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
    private int handSize = 10;

    private int turn = 1;

    public Rummy() {
        System.out.println("Creating game of rummy with hand size " + handSize + ".");

        System.out.println("Shuffling deck.");
        List<Card> deck = Arrays.asList(Card.DECK);
        Collections.shuffle(deck);
        stock = new LinkedList<>(deck);

        System.out.println("Dealing cards.");
        List<Card> playerHand = new ArrayList<>(handSize);
        List<Card> computerHand = new ArrayList<>(handSize);
        for (int i = 0; i < handSize; i++) {
            playerHand.add(stock.remove());
            // computerHand.add(stock.remove()); // TODO: put back when implementing computer player
        }
        player = new Player(playerHand);
        computer = new ComputerPlayer(computerHand);
        
        discardPile = new Stack<>();
        melds = new ArrayList<>();
        discardPile.push(stock.remove());
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
        System.out.println("Have fun!\n");
    }

    public void play() {
        System.out.println("Starting game.");
        scan = new Scanner(System.in);

        while (turn());

        scan.close();

        System.out.println("================= Game Done =================");
        if (player.won()) {
            System.out.println("you won!!!");
        } else if (computer.won()) {
            System.out.println("the computer won :(");
        }
        System.out.println("Your hand: " + player);
        System.out.println("Computer's hand: " + computer);
        System.out.println("Melds: " + melds);
        System.out.println("Top of discard: " + discardPile.peek());
    }

    /**
     * play a turn of rummy
     * @return {@code true} if the game should continue
     */
    private boolean turn() {
        System.out.println();
        System.out.print("================== " + "Turn " + turn++ + " ==================");
        Card drawn;
        do {
            drawn = drawMenu();
        } while (drawn == null);

        Card discarded;
        do {
            discarded = discardMenu(drawn);
        } while (discarded == null);
        
        if (player.won() || computer.won()) {
            return false;
        } else {
            return true;
        }
    }

    private Card drawMenu() {
        System.out.println();
        System.out.println("Your hand: " + player);
        System.out.println("Melds: " + melds);
        System.out.println("Top of discard: " + discardPile.peek());
        System.out.println();
        System.out.println("1 - Draw from stock");
        System.out.println("2 - Draw from discard pile");
        System.out.println("3 - Sort hand by suit");
        System.out.println("4 - Sort hand by rank");
        System.out.print("Enter your choice: ");
        String choice = scan.next();
        scan.nextLine();
        switch (choice) {
            case "1": {
                Card card = stock.remove();
                player.draw(card);
                System.out.println("\n\tDrew " + card + " from stock.");
                if (stock.isEmpty()) {
                    stock.addAll(discardPile);
                    discardPile.clear();
                    System.out.println("\tRecycled discard pile");
                }
                return card;
            } case "2":
                Card card = discardPile.pop();
                player.draw(card);
                System.out.println("\n\tDrew " + card + " from discard pile.");
                return card;
            case "3":
                player.sortBySuit();
                System.out.println("\n\tSorted hand by suit.");
                return null;
            case "4":
                player.sortByRank();
                System.out.println("\n\tSorted hand by rank.");
                return null;
            default:
                System.out.println();
                System.out.println(Colors.error("Invalid choice!"));
                return null;
        }
    }

    private Card discardMenu(Card drawnCard) {
        System.out.println();
        System.out.println("Your hand: " + player);
        System.out.println("Melds: " + melds);
        System.out.println("Drawn card: " + drawnCard);
        System.out.println();
        System.out.println("1 - Lay down meld");
        System.out.println("2 - Add to meld");
        System.out.println("3 - Sort hand by suit");
        System.out.println("4 - Sort hand by rank");
        System.out.println("5 - Choose card to discard (ends turn)");
        System.out.println("6 - Discard drawn card (ends turn)");
        System.out.print("Enter your choice: ");
        String choice = scan.next();
        scan.nextLine();
        switch (choice) {
            case "1":
                layMeld();
                return null;
            case "2":
                addToMeld();
                return null;
            case "3":
                player.sortBySuit();
                return null;
            case "4":
                player.sortByRank();
                return null;
            case "5":
                discardCard();
            case "6":
                if (player.handContains(drawnCard)) {
                    discardPile.push(player.discard(drawnCard));
                    System.out.println("\n\tDiscarded " + drawnCard + ".");
                } else {
                    System.out.println(Colors.error("\n\tYou can't return a card after laying it down!"));
                }
                return drawnCard;
            default:
                System.out.println(Colors.error("\n\tInvalid choice!"));
                return null;
        }
    }

    private void layMeld() {
        System.out.println();
        System.out.println("Selecting cards for meld.");
        Meld meld = new Meld();
        while (true) {
            System.out.print("Enter card (like A\u0005/2\u0006/Js), f to finish the meld, or x to cancel: ");
            String input = scan.next();
            scan.nextLine();
            if (input.equalsIgnoreCase("x")) {
                System.out.println("Cancelling meld.");
                return;
            } else if (input.equalsIgnoreCase("f")) {
                if (meld.isComplete()) {
                    System.out.println("Laying " + meld + " on the table.");
                    player.layCards(meld);
                    melds.add(meld);
                    return;
                } else {
                    System.out.println(Colors.error("Meld is incomplete!"));
                    continue;
                }
            }

            Card toAdd = selectCardFromHand(input);
            if (toAdd == null) {
                continue;
            } else if (meld.addCard(toAdd)) {
                System.out.println("Meld: " + meld);
            } else {
                System.out.println(Colors.error("Card doesn't fit in meld!"));
            }
        }
    }

    private void addToMeld() {
        System.out.println();
        System.out.println("Selecting meld to add to.");
        Meld meld = null;
        while (meld == null) {
            System.out.println("Melds: " + melds);
            System.out.print("Enter a card that is in the meld you want to select, or x to cancel: ");
            String input = scan.next();
            scan.nextLine();
            if (input.equalsIgnoreCase("x")) {
                System.out.println("Cancelled addToMeld.");
                return;
            }

            Card card;
            try {
                card = new Card(input);
            } catch (IllegalArgumentException e) {
                System.out.println(Colors.error("Invalid card!"));
                continue;
            }

            meld = melds.stream().filter(m -> m.contains(card)).findAny().orElse(null);
            if (meld == null) {
                System.out.println(Colors.error("No meld contains that card!"));
                continue;
            } else {
                System.out.println("Selected meld " + meld);
            }
        }
        
        System.out.println("Selecting card to add to meld");
        while (true) {
            System.out.print("Enter card (like A\u0005/2\u0006/Js), or x to cancel: ");
            String input = scan.next();
            scan.nextLine();
            if (input.equalsIgnoreCase("x")) {
                System.out.println("Cancelling addToMeld.");
                return;
            }

            Card toAdd = selectCardFromHand(input);
            if (toAdd == null) {
                continue;
            } else if (meld.addCard(toAdd)) {
                System.out.println("Meld: " + meld);
                player.layCard(toAdd);
                return;
            } else {
                System.out.println(Colors.error("Card doesn't fit in meld!"));
            }
        }
    }

    private Card discardCard() {
        System.out.print("\n\tEnter card (like A\u0005/2\u0006/Js), or x to cancel: ");
        String input = scan.next();
        scan.nextLine();
        if (input.equalsIgnoreCase("x")) {
            System.out.println("Cancelled selection.");
            return null;
        }

        Card toDiscard = selectCardFromHand(input);
        if (toDiscard == null) {
            return null;
        } else {
            discardPile.push(player.discard(toDiscard));
            System.out.println("\n\tDiscarded " + toDiscard + ".");
            return toDiscard;
        }
    }

    private Card selectCardFromHand(String cardStr) {
        Card card;
        try {
            card = new Card(cardStr);
        } catch (IllegalArgumentException e) {
            System.out.println(Colors.error("Invalid card!"));
            return null;
        }

        if (player.handContains(card)) {
            System.out.println("Selected " + card);
            return card;
        } else {
            System.out.println(Colors.error("Card not in hand!"));
            return null;
        }
    }
}

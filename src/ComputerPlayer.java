import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**Keeps track of the computer player's hand and chooses actions for the computer */
public class ComputerPlayer {
    private List<Card> hand;
    private Random rand;

    /**
     * Creates a ComputerPlayer object with the given starting hand
     * @param hand - the ComputerPlayer's starting hand
     */
    public ComputerPlayer(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
        rand = new Random();
    }

    /**
     * Draws a card
     * @param c - card to draw
     */
    public void draw(Card c) {
        hand.add(c);
    }

    /**
     * Discards a card
     * @param c - card to discard
     * @return the discarded card, or {@code null} if the card was not in hand
     */
    public Card discard(Card c) {
        if (hand.remove(c)) {
            return c;
        } else {
            return null;
        }
    }
    
    /**
     * Chooses whether to discard from discard pile or draw pile randomly
     * @param discardCard - top card of discard pile
     * @return {@code true} if it should draw from the discard pile
     */
    public boolean drawFromDiscard(Card discardCard) {
        return rand.nextBoolean();
    }

    /**
     * Finds all melds that can be made and removes the corresponding cards from hand
     * @return the melds to lay down
     */
    public List<Meld> layMelds() {
        List<Meld> melds = new ArrayList<>();

        // look for sets
        List<Meld> sets = new ArrayList<>();
        hand.sort(new Card.RankComparator());
        sets.add(new Meld());
        for (Card c : hand) {
            if (!sets.getLast().addCard(c)) {
                sets.add(new Meld());
            }
        }

        for (Meld set : sets) {
            if (set.isComplete()) {
                hand.removeAll(set.getCards());
                melds.add(set);
            }
        }

        // look for runs
        List<Meld> runs = new ArrayList<>();
        hand.sort(new Card.RankComparator());
        runs.add(new Meld());
        for (Card c : hand) {
            if (!runs.getLast().addCard(c)) {
                runs.add(new Meld());
            }
        }

        for (Meld run : runs) {
            if (run.isComplete()) {
                hand.removeAll(run.getCards());
                melds.add(run);
            }
        }

        return melds;
    }

    /**
     * Finds which cards can be added to a meld and removes them from hand
     * @param cards - cards in the meld to add to
     * @return the cards to add to the meld
     */
    public List<Card> addToMeld(List<Card> cards) {
        Meld meld = new Meld(cards);
        List<Card> toAdd = new ArrayList<>();
        for (Card c : hand) {
            if (meld.addCard(c)) {
                toAdd.add(c);
            }
        }
        hand.removeAll(toAdd);
        return toAdd;
    }

    /**
     * Picks a random card to discard
     * @return card to discard
     */
    public Card cardToDiscard() {
        return hand.get(rand.nextInt(hand.size()));
    }

    /**
     * Checks if the computer has won
     * @return {@code true} if the computer has won
     */
    public boolean won() {
        return hand.isEmpty();
    }

    /**
     * Finds how many cards are in hand
     * @return number of cards in hand
     */
    public int numCards() {
        return hand.size();
    }

    /**
     * @return string representation of hand
     */
    @Override
    public String toString() {
        if (won()) {
            return "computer has won.";
        }

        hand.sort(new Card.SuitComparator());
        String s = "";
        boolean first = true;
        for (Card c : hand) {
            s += (first ? "" : " ") + c;
            first = false;
        }
        return s;
    }
}

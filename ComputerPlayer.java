import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer {
    private List<Card> hand;
    private Random rand;

    public ComputerPlayer(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
        rand = new Random();
    }

    public void draw(Card c) {
        hand.add(c);
    }

    public Card discard(Card c) {
        if (hand.remove(c)) {
            return c;
        } else {
            return null;
        }
    }
    
    /**
     * calculates whether to draw from stock or draw pile
     * @param discardCard - top card of discard pile
     * @return {@code true} if it should draw from the discard pile
     */
    public boolean drawFromDiscard(Card discardCard) {
        return rand.nextBoolean();
    }

    public List<Meld> layMelds() {
        List<Meld> melds = new ArrayList<>();

        // look for sets
        List<Meld> sets = new ArrayList<>();
        hand.sort(new Card.RankComparator());
        sets.add(new Meld());
        for (Card c : hand) {
            if (!sets.get(sets.size()-1).addCard(c)) {
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
            if (!runs.get(runs.size()-1).addCard(c)) {
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

    public Card cardToDiscard() {
        return hand.get(rand.nextInt(hand.size()));
    }

    /**
     * checks if a player has won
     * @return {@code true} if the player has won
     */
    public boolean won() {
        return hand.isEmpty();
    }

    public int numCards() {
        return hand.size();
    }

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

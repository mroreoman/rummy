import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;

    public Player(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
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

    public Card discard() {
        if (hand.size() != 1) {
            throw new IllegalStateException("This method should only be used for the last card.");
        }
        return hand.removeFirst();
    }

    public void layCard(Card c) {
        if (hand.size() <= 1) {
            throw new IllegalStateException("Player attempting to lay their last card.");
        }
        if (!hand.remove(c)) {
            throw new IllegalStateException("Player attempting to lay a card they don't have.");
        }
        hand.remove(c);
    }

    public void layCards(Meld meld) {
        if (!hand.containsAll(meld.getCards())) {
            throw new IllegalStateException("Player attempting to lay cards they don't have.");
        }
        if (hand.size() <= meld.getCards().size()) {
            throw new IllegalStateException("Player attempting to lay their last card.");
        }
        hand.removeAll(meld.getCards());
    }

    public void sortBySuit() {
        hand.sort(new Card.SuitComparator());
    }

    public void sortByRank() {
        hand.sort(new Card.RankComparator());
    }

    public boolean handContains(Card c) {
        return hand.contains(c);
    }

    public int handSize() {
        return hand.size();
    }

    /**
     * checks if a player has won
     * @return {@code true} if the player has won
     */
    public boolean won() {
        return hand.isEmpty();
    }

    @Override
    public String toString() {
        if (won()) {
            return "player has won.";
        }

        String s = "";
        boolean first = true;
        for (Card c : hand) {
            s += (first ? "" : " ") + c;
            first = false;
        }
        return s;
    }
}

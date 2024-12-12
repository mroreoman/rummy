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

    public boolean layCard(Card c) { // TODO: players can maybe lay their last card, they need to discard that
        return hand.remove(c);
    }

    public boolean layCards(Meld meld) { // TODO: make sure players can't lay all their cards
        if (hand.containsAll(meld.getCards())) {
            hand.removeAll(meld.getCards());
            return true;
        }
        return false;
    }

    public void swap(int index1, int index2) {
        Card c = hand.remove(index1);
        hand.add(index1, hand.remove(index2));
        hand.add(index2, c);
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

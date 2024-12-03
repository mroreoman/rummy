import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private List<Card> hand;
    private Card newCard;

    public Player(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
        sortHand();
    }

    public void draw(Card c) {
        hand.add(c);
        newCard = c;
    }

    public Card discard(Card c) {
        if (hand.remove(c)) {
            newCard = null;
            return c;
        } else {
            return null;
        }
    }

    public boolean layCard(Card c) { // TODO: players can maybe lay their last card, they need to discard that
        return hand.remove(c);
    }

    public boolean layCards(Meld meld) {
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

    public void sortHand() {
        Collections.sort(hand);
    }

    public boolean handContains(Card c) {
        return hand.contains(c);
    }

    public boolean isNewCard(Card c) {
        return c.equals(newCard);
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
            if (isNewCard(c)) {
                s += (first ? "" : " ") + "(" + c + ")";
            } else {
                s += (first ? "" : " ") + c;
            }
            first = false;
        }
        return s;
    }
}

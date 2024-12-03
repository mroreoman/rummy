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

    public boolean layCards(List<Card> cards) {
        if (hand.containsAll(cards)) {
            hand.removeAll(cards);
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
     * @return whether they won
     */
    public boolean won() {
        return hand.isEmpty();
    }

    @Override
    public String toString() {
        String s = "";
        for (Card c : hand) {
            if (isNewCard(c)) {
                s += "(" + c + ") ";
            } else {
                s += c + " ";
            }
        }
        return s.substring(0, s.length() - 1);
    }
}

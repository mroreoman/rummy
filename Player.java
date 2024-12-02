import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private List<Card> hand;
    private boolean autoSort = true;

    public Player(int handSize) {
        hand = new ArrayList<>(handSize);
    }

    public void draw(Card c) {
        hand.add(c);
        if (autoSort) {
            sortHand();
        }
    }

    public Card discard(Card c) {
        if (hand.remove(c)) {
            return c;
        } else {
            return null;
        }
    }

    public void swap(int index1, int index2) {
        Card c = hand.remove(index1);
        hand.add(index1, hand.remove(index2));
        hand.add(index2, c);
    }

    public void sortHand() {
        Collections.sort(hand);
    }

    public void setAutoSort(boolean autoSort) {
        this.autoSort = autoSort;
    }

    public boolean handContains(Card c) {
        return hand.contains(c);
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
            s += c + " ";
        }
        return s;
    }
}

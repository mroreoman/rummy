import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private List<Card> hand;

    public Player(int handSize) {
        hand = new ArrayList<>(handSize);
    }

    public void draw(Card c) {
        hand.add(c);
    }

    public Card discard(int index) {
        return hand.remove(index);
    }

    public void swap(int index1, int index2) {
        Card c = hand.remove(index1);
        hand.add(index1, hand.remove(index2));
        hand.add(index2, c);
    }

    /**
     * checks if a player has won
     * @return whether they won
     */
    public boolean won() {
        return false; //TODO: make this method
    }

    public void sortHand() {
        Collections.sort(hand);
    }

    public String toString() {
        String s = "";
        for (Card c : hand) {
            s += c + " ";
        }
        return s;
    }
}

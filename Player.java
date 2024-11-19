import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private ArrayList<Card> hand;

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

    public void sortByRank() {
        //TODO
    }

    public void sortBySuit() {
        //TODO
    }

    public String toString() {
        String s = "";
        for (Card c : hand) {
            s += c + " ";
        }
        return s;
    }
}

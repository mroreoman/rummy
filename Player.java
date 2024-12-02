import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private int handSize;

    public Player(int handSize) {
        hand = new ArrayList<>(handSize);
        this.handSize = handSize; //TODO: maybe want to check hand size at the end of each turn?
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

    public int getHandSize() {
        return handSize;
    }

    public void sortByRank() {
        //TODO: make this method
    }

    public void sortBySuit() {
        //TODO: make this method
    }

    public String toString() {
        String s = "";
        for (Card c : hand) {
            s += c + " ";
        }
        return s;
    }
}

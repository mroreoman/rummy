import java.util.ArrayList;
import java.util.List;

/**Keeps track of the player's hand and validates actions taken by the user  */
public class Player {
    private List<Card> hand;

    /**
     * Creates a Player object with the given hand
     * @param hand - starting hand
     */
    public Player(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
    }

    /**
     * Adds a card to the player's hand
     * @param c - card to add
     */
    public void draw(Card c) {
        hand.add(c);
    }


    /**
     * Removes a card from the player's hand
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
     * Gets the last remaining card in the player's hand (meant to be used when the player has one card left).
     * @return player's last card
     */
    public Card getLastCard() {
        if (hand.size() != 1) {
            throw new IllegalStateException("This method should only be used for the last card.");
        }
        return hand.getFirst();
    }

    /**
     * Removes the card from the player's hand if it can be laid down
     * @param c - card to add to meld
     */
    public void layCard(Card c) {
        if (hand.size() <= 1) {
            throw new IllegalStateException("Player attempting to lay their last card.");
        }
        if (!hand.remove(c)) {
            throw new IllegalStateException("Player attempting to lay a card they don't have.");
        }
        hand.remove(c);
    }

    /**
     * Removes the cards in the meld from the player's hand if they can be laid down
     * @param meld - the meld the player wants to lay down
     */
    public void layCards(Meld meld) {
        if (!hand.containsAll(meld.getCards())) {
            throw new IllegalStateException("Player attempting to lay cards they don't have.");
        }
        if (hand.size() <= meld.getCards().size()) {
            throw new IllegalStateException("Player attempting to lay their last card.");
        }
        hand.removeAll(meld.getCards());
    }

    /**Sorts player's hand by suit */
    public void sortBySuit() {
        hand.sort(new Card.SuitComparator());
    }

    /**Sorts player's hand by rank */
    public void sortByRank() {
        hand.sort(new Card.RankComparator());
    }

    /**
     * Checks if hand contains a card
     * @param c - card to check
     * @return {@code true} if hand contains the card
     */
    public boolean handContains(Card c) {
        return hand.contains(c);
    }

    /**
     * Finds how many cards are in hand
     * @return number of cards in hand
     */
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

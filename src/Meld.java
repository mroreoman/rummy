import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**Represents a meld */
public class Meld {
    public final static Meld SET = new Meld(new Card("6s"), new Card("6d"), new Card("6h"));
    public final static Meld RUN = new Meld(new Card("Ks"), new Card("As"), new Card("2s"), new Card("3s"));

    private List<Card> cards;

    /**Creates an empty meld */
    public Meld() {
        cards = new ArrayList<>();
    }

    /**
     * Creates a meld with the given cards.
     * This does NOT check if it is a valid meld, it should only be used for testing and creating example melds.
     * @param cards - cards to add to the meld
     */
    public Meld(List<Card> cards) {
        this.cards = new ArrayList<Card>(cards);
    }
    
    /**
     * Creates a meld with the given cards.
     * This does NOT check if it is a valid meld, it should only be used for testing and creating example melds.
     * @param cards - cards to add to the meld
     */
    public Meld(Card... cards) {
        this(Arrays.asList(cards));
    }

    /**
     * Checks if the meld would create a valid set or run with the card, and adds it to the meld if so.
     * @param newCard - card to be added
     * @return {@code true} if card was added to meld
     */
    public boolean addCard(Card newCard) {
        if (cards.isEmpty()) {
            cards.add(newCard);
            return true;
        }

        if (cards.contains(newCard)) {
            return false;
        }

        // check for valid set
        if (cards.stream().allMatch(c -> (c.getRank() == newCard.getRank()))) {
            cards.add(newCard);
            return true;
        }

        // check for valid run
        int index = -1;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getSuit() != newCard.getSuit()) {
                return false;
            } else {
                int diff = cards.get(i).getRank().compareTo(newCard.getRank());
                if (diff == 1 || diff == -12) {
                    index = i;
                } else if (diff == -1 || diff == 12) {
                    index = i + 1;
                }
            }
        }
        if (index != -1) {
            cards.add(index, newCard);
            return true;
        }

        return false;
    }

    /**
     * Checks if the meld is complete (has 3 or more cards)
     * @return {@code true} if meld is complete
     */
    public boolean isComplete() {
        return cards.size() >= 3;
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * @return string representation of the cards in the meld
     */
    @Override
    public String toString() {
        return cards.toString();
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meld {
    public final static Meld SET = new Meld(new Card("6s"), new Card("6d"), new Card("6h"));
    public final static Meld RUN = new Meld(new Card("Ks"), new Card("As"), new Card("2s"), new Card("3s"));

    private List<Card> cards;

    public Meld() {
        cards = new ArrayList<>();
    }

    public Meld(List<Card> cards) {
        this.cards = new ArrayList<Card>(cards);
    }

    public Meld(Card... cards) {
        this(Arrays.asList(cards));
    }

    /**
     * tries to add a card to this meld
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

    public boolean isComplete() {
        return cards.size() >= 3;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean contains(Card c) {
        return cards.contains(c);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}

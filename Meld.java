import java.util.ArrayList;
import java.util.List;

public class Meld {
    private List<Card> cards;

    public Meld() {
        cards = new ArrayList<>();
    }

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

        // check for valid sequence
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
        } else {
            return false;
        }
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

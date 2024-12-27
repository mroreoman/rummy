import java.util.Arrays;
import java.util.Comparator;

/**Represents a playing card */
public class Card {
    public enum Suit {
        SPADES("\u0006", Output.Colors.BLUE), //♠
        CLUBS("\u0005", Output.Colors.GREEN), //♣
        HEARTS("\u0003", Output.Colors.RED), //♥
        DIAMONDS("\u0004", Output.Colors.YELLOW); //♦

        private String symbol;
        private String color;

        Suit(String symbol, String color) {
            this.symbol = symbol;
            this.color = color;
        }

        @Override
        public String toString() {
            return color + symbol + Output.Colors.RESET;
        }

        public static Suit getEnum(String value) {
            if (value.equals(SPADES.symbol) || value.equalsIgnoreCase("S")) {
                return SPADES;
            } else if (value.equals(CLUBS.symbol) || value.equalsIgnoreCase("C")) {
                return CLUBS;
            } else if (value.equals(HEARTS.symbol) || value.equalsIgnoreCase("H")) {
                return HEARTS;
            } else if (value.equals(DIAMONDS.symbol) || value.equalsIgnoreCase("D")) {
                return DIAMONDS;
            } else {
                return null;
            }
        }
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
        
        private int rank;

        Rank() {
            rank = ordinal() + 1;
        }

        @Override
        public String toString() {
            if (rank == 1) {
                return "A";
            } else if (rank == 11) {
                return "J";
            } else if (rank == 12) {
                return "Q";
            } else if (rank == 13) {
                return "K";
            } else {
                return Integer.toString(rank);
            }
        }

        public static Rank getEnum(String value) {
            return Arrays.stream(values()).filter(rank -> rank.toString().equalsIgnoreCase(value)).findAny().orElse(null);
        }
    }
    
    /**Comparator implementation to sort cards by rank */
    public static final class RankComparator implements Comparator<Card> {
        @Override
        public int compare(Card c1, Card c2) {
            if (c1.rank.compareTo(c2.rank) != 0) {
                return c1.rank.compareTo(c2.rank);
            } else {
                return c1.suit.compareTo(c2.suit);
            }
        }
    }
    
    /**Comparator implementation to sort cards by suit */
    public static final class SuitComparator implements Comparator<Card> {
        @Override
        public int compare(Card c1, Card c2) {
            if (c1.suit.compareTo(c2.suit) != 0) {
                return c1.suit.compareTo(c2.suit);
            } else {
                return c1.rank.compareTo(c2.rank);
            }
        }
    }

    private Rank rank;
    private Suit suit;

    /**
     * Creates a Card object with the specified rank and suit
     * @param rank - card rank (the number)
     * @param suit - card suit (the symbol)
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Creates a Card object from a string, meant for input from the terminal
     * @param cardStr - card represented as a String in the form A♠ or As (suit can be symbol or letter)
     * @throws IllegalArgumentException if the string isn't a valid card
     */
    public Card(String cardStr) throws IllegalArgumentException {
        String rankStr = cardStr.substring(0, cardStr.length() - 1);
        Rank rank = Rank.getEnum(rankStr);
        if (rank == null) {
            throw new IllegalArgumentException("invalid rank '" + rankStr + "'");
        }
        this.rank = rank;

        String suitStr = cardStr.substring(cardStr.length() - 1);
        Suit suit = Suit.getEnum(suitStr);
        if (suit == null) {
            throw new IllegalArgumentException("invalid suit '" + suitStr + "'");
        }
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    /**
     * @return card in the form A♠
     */
    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card) {
            Card c = (Card)o;
            return this.rank == c.rank && this.suit == c.suit;
        } else {
            return false;
        }
    }

    /**
     * creates a full deck of cards
     * @return sorted deck of cards
     */
    public static Card[] fullDeck() {
        Card[] deck = new Card[52];
        for (int i = 0; i < deck.length; i++) {
            deck[i] = new Card(Rank.values()[i % 13], Suit.values()[i / 13]);
        }
        return deck;
    }
}

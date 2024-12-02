import java.util.Arrays;

public class Card implements Comparable<Card> {
    public enum Suit {
        SPADES("\u0006"), //♠
        CLUBS("\u0005"), //♣
        HEARTS("\u0003"), //♥
        DIAMONDS("\u0004"); //♦

        private String symbol;

        Suit(String symbol) {
            this.symbol = symbol;
        }

        public String toString() {
            return symbol;
        }

        public static Suit getEnum(String value) {
            value = value.toUpperCase();
            if (value.equals(SPADES.symbol) || value.equals("S")) {
                return SPADES;
            } else if (value.equals(CLUBS.symbol) || value.equals("C")) {
                return CLUBS;
            } else if (value.equals(HEARTS.symbol) || value.equals("H")) {
                return HEARTS;
            } else if (value.equals(DIAMONDS.symbol) || value.equals("D")) {
                return DIAMONDS;
            } else {
                return null;
            }
        }
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
        
        public String toString() {
            int val = rank();
            if (val == 1) {
                return "A";
            } else if (val == 11) {
                return "J";
            } else if (val == 12) {
                return "Q";
            } else if (val == 13) {
                return "K";
            } else {
                return Integer.toString(val);
            }
        }

        public int rank() {
            return ordinal() + 1;
        }

        public static Rank getEnum(String value) {
            String val = value.toUpperCase();
            return Arrays.stream(values()).filter(rank -> rank.toString().equals(val)).findAny().orElse(null);
        }
    }

    public static final Card[] DECK = new Card[52];
    
    static {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = new Card(Rank.values()[i % 13], Suit.values()[i / 13]);
        }
    }

    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

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

    public String toString() {
        return rank.toString() + suit.toString();
    }

    public int compareTo(Card c) {
        if (suit.compareTo(c.suit) == 0) {
            return rank.compareTo(c.rank);
        } else {
            return suit.compareTo(c.suit);
        }
    }
}

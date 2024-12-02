import java.util.Arrays;

public class Card {
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
            value = value.toLowerCase();
            if (value == SPADES.symbol || value == "s") {
                return SPADES;
            } else if (value == CLUBS.symbol || value == "c") {
                return CLUBS;
            } else if (value == HEARTS.symbol || value == "h") {
                return HEARTS;
            } else if (value == DIAMONDS.symbol || value == "d") {
                return DIAMONDS;
            } else {
                return null;
            }
        }
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
        
        public String toString() {
            int val = ordinal() + 1;
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

        public static Rank getEnum(String val) {
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

    public Card(String cardStr) {
        this(
            Rank.getEnum(cardStr.substring(0, cardStr.length() - 1)),
            Suit.getEnum(cardStr.substring(cardStr.length() - 1))
        );
    }

    public String toString() {
        // String out = String.valueOf(rank);

        // if (rank == 1) {
        //     out = "A";
        // } else if (rank == 11) {
        //     out = "J";
        // } else if (rank == 12) {
        //     out = "Q";
        // } else if (rank == 13) {
        //     out = "K";
        // }

        // out += suit.toString();
        // return out;
        return rank.toString() + suit.toString();
    }
}

import java.util.Arrays;

public class Card implements Comparable<Card> {
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

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

    @Override
    public int compareTo(Card c) {
        if (suit.compareTo(c.suit) == 0) {
            return rank.compareTo(c.rank);
        } else {
            return suit.compareTo(c.suit);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card) {
            return compareTo((Card)o) == 0;
        } else {
            return false;
        }
    }
}

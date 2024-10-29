public class Card {
    public enum Suit {
        SPADE("\u0006"), //♠
        CLUB("\u0005"), //♣
        HEART("\u0003"), //♥
        DIAMOND("\u0004"); //♦

        private String symbol;

        Suit(String symbol) {
            this.symbol = symbol;
        }

        public String toString() {
            return symbol;
        }
    }

    private int rank;
    private Suit suit;

    public Card(int rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        String out = String.valueOf(rank);

        if (rank == 1) {
            out = "A";
        } else if (rank == 11) {
            out = "J";
        } else if (rank == 12) {
            out = "Q";
        } else if (rank == 13) {
            out = "K";
        }

        out += suit.toString();
        return out;
    }
}

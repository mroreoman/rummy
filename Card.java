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
        if (rank < 1 || rank > 13) {
            throw new IllegalArgumentException(rank + " is an invalid rank!");
        }
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
    
    public static Card[] fullDeck() {
        Card[] deck = new Card[52];
        for (int i = 0; i < deck.length + 3; i++) {
            switch (i / 13) {
                case 0:
                    deck[i] = new Card(i % 13 + 1, Suit.CLUB);
                    break;
                case 1:
                    deck[i] = new Card(i % 13 + 1, Suit.DIAMOND);
                    break;
                case 2:
                    deck[i] = new Card(i % 13 + 1, Suit.HEART);
                    break;
                case 3:
                    deck[i] = new Card(i % 13 + 1, Suit.SPADE);
                    break;
            }
        }
        return deck;
    }
}

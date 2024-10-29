class Main {
    public static void main(String[] args) {
        Card c1 = new Card(1, Card.Suit.SPADE);
        Card c2 = new Card(7, Card.Suit.HEART);
        Card c3 = new Card(11, Card.Suit.DIAMOND);
        Card c4 = new Card(13, Card.Suit.CLUB);

        Card[] cArr = {c1, c2, c3, c4};
        for (Card c : cArr) {
            System.out.println(c);
        }
    }
}

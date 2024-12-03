import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Client {
    public static void main(String[] args) {
        Rummy.printInstructions();
        Rummy game = new Rummy();
        game.play();
    }

    public static void test() {
        // Card.toString()
        System.out.print("full deck: ");
        for (Card c : Card.DECK) {
            System.out.print(c + " ");
        }
        System.out.println();

        // new Card(String cardStr)
        System.out.print("jacks of spades: ");
        Card c = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        System.out.print(c + " ");
        c = new Card("J\u0006");
        System.out.print(c + " ");
        c = new Card("Js");
        System.out.print(c + " ");
        c = new Card("jS");
        System.out.println(c);
        try {
            c = new Card("ju");
        } catch (IllegalArgumentException e) {
            System.out.println("invalid suit: " + e);
        }
        try {
            c = new Card("uS");
        } catch (IllegalArgumentException e) {
            System.out.println("invalid rank: " + e);
        }

        // card.compareTo()
        List<Card> deck = Arrays.asList(Card.DECK);
        Collections.shuffle(deck);
        Collections.sort(deck);
        if (deck.toString().equals(Arrays.toString(Card.DECK))) {
            System.out.println("sorted succesfully!");
        } else {
            System.out.println("sort didn't work. deck: " + deck);
        }
        
        // Card.equals()
        Card cc = new Card("Js");
        Card c1 = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        Card c2 = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        Card c3 = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
        Card c4 = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
        System.out.print(!cc.equals(null) + " ");
        System.out.print(cc.equals(c1) + " ");
        System.out.print(c1.equals(c2) + " ");
        System.out.print(!c2.equals(c3) + " ");
        System.out.print(!c3.equals(c4) + " ");
        System.out.print(!c4.equals(c1) + "\n");

        // melds
        Meld m = new Meld();
        String[] cards = new String[]{"As", "Ac", "Ah", "Ad"};
        for (String s : cards) {
            m.addCard(new Card(s));
        }
        System.out.println("four aces: " + m);

        m = new Meld();
        cards = new String[]{"As", "2s", "3h", "3s", "Ks", "Js", "Qs", "Js"};
        for (String s : cards) {
            m.addCard(new Card(s));
        }
        System.out.println("J-3 of spades: " + m);

        m = new Meld();
        cards = new String[]{"Kd", "Qd", "Ad", "5d"};
        for (String s : cards) {
            m.addCard(new Card(s));
        }
        System.out.println("Q-A of diamonds: " + m);
    }
}

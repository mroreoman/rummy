import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Client {
    public static void main(String[] args) {
        Rummy game = new Rummy();
        game.start();
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
        System.out.println(!cc.equals(null));
        System.out.println(cc.equals(c1));
        System.out.println(c1.equals(c2)); 
        System.out.println(!c2.equals(c3));
        System.out.println(!c3.equals(c4));
        System.out.println(!c4.equals(c1));
    }
}

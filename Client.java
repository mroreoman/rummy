import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Client {
    public static void main(String[] args) {
        // Rummy game = new Rummy(10);
        // game.start();

        test();
    }

    public static void test() {
        for (Card c : Card.DECK) {
            System.out.print(c + " ");
        }
        System.out.println();

        Card c = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        System.out.println(c);
        c = new Card("J\u0006");
        System.out.println(c);
        c = new Card("Js");
        System.out.println(c);
        c = new Card("jS");
        System.out.println(c);
        c = new Card("ju");
        System.out.println(c);

        List<Card> deck = Arrays.asList(Card.DECK);
        Collections.shuffle(deck);
        Collections.sort(deck);
        if (deck.toString().equals(Arrays.toString(Card.DECK))) {
            System.out.println("sorted succesfully!");
        } else {
            System.out.println("sort didn't work. deck: " + deck);
        }
    }
}

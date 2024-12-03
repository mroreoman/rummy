import java.util.List;

public class ComputerPlayer extends Player {
    public ComputerPlayer(List<Card> hand) {
        super(hand);
    }
    
    /**
     * calculates whether to draw from stock or draw pile
     * @param discardCard - top card of discard pile
     * @return {@code true} if it should draw from the stock
     */
    // public boolean drawLocation(Card discardCard) { }

    // public int cardToDiscard() { }

    @Override
    public boolean won() {
        return false; //TODO: remove when implementing computer player
    }
}

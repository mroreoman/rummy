public class ComputerPlayer extends Player {
    public ComputerPlayer(int handSize) {
        super(handSize);
    }

    /**
     * calculates whether to draw from discrad pile or draw pile
     * @param discardCard - top card of discard pile
     * @return true if it should draw from the draw pile
     */
    public boolean drawLocation(Card discardCard) {
        return true; //TODO
    }

    public int cardToDiscard() {
        return 0; //TODO
    }
}

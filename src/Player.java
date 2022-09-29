public class Player {
   
    private Hand hand;

    public Player() {
        hand = new Hand();
    }

    public void addCardToHand(Card card) {
        this.hand.insert(card);
    }

    public void displayHand() {
        System.out.println(this.hand);
    }
}

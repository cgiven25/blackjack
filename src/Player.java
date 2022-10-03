import java.lang.Math;
public class Player {
    
    private static final int STARTING_MONEY = 100;

    private Hand hand;
    private String name;
    private int money = STARTING_MONEY;
    private int betAmount = 0;
    
    public Player(String name) {
        this.hand = new Hand();
        this.name = name;
    }
    
    public Hand getHand() {
        return hand;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getBetAmount() {
        return betAmount;
    }
    
    public void setBetAmount(int amount) {
        betAmount = amount;
    }
    
    public int getMoney() {
        return money;
    }
    
    public int getCount() {
        return hand.getCount();
    }
    
    public void addCardToHand(Card card) {
        hand.insert(card);
    }

    public void resolveBet(Result res) {

        // Using a switch case here causes an empty anonymous class Player$1 to be generated.
        // I don't know why and I'm having trouble finding the answer on google
        if (res == Result.BLACKJACK) {
            money +=  (int) Math.floor(betAmount*1.5);
        } else if (res == Result.WIN) {
            money += betAmount;
        } else if (res == Result.LOSE) {
            money -= betAmount;
        } else if (res == Result.LOSE) {
            money -= 0;
        }

        betAmount = 0;

        // The switch statement in question
        // Wouldn't let me use Result.BLACKJACK because switch(case) intuits the type of the case variable
        // switch(res) {
        //     case BLACKJACK:
        //         this.money +=  (int) Math.floor(betAmount*1.5);
        //         break;
        //     case WIN:
        //         this.money += betAmount;
        //         break;
        //     case LOSE:
        //         this.money -= betAmount;
        //         break;
        //     case PUSH:
        //         this.money -= 0;
        //         break;
        //     default:
        //         break;
        // }

    }

    public void displayHand() {
        StringBuilder handStr = new StringBuilder();
        handStr.append(String.format("%s's hand: ", name));
        handStr.append(hand.toString() + "\n");
        handStr.append(String.format("Count: %d\n", getCount()));

        System.out.println(handStr.toString());
    }
}

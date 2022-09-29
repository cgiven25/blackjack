# Author: Chris Given
# Blackjack
# Rules: https://bicyclecards.com/how-to-play/blackjack/

#################### Rules I Need to Keep Track Of ####################
# 6 decks will be used, like in a casino.
#   - One addition may be to add a stopper so that the bottom 60-75 cards don't get used.
#       - This makes card counting harder
# 
# If both the dealer and player have blackjack, it's a tie (I thought that the player won before the dealer's hand was checked)
#######################################################################
import random
from time import sleep

class Deck:
    def __init__(self, sampleCards, numRepeats=1):
        self.cards = []
        
        for _ in range(numRepeats):
            for card in sampleCards:
                self.cards.append(Card(card.rank, card.suit))

    def deal(self, receivingHand, numCards=1, faceDown=False):
        for _ in range(numCards):
            dealtCard = self.cards.pop()
            if faceDown:
                dealtCard.faceDown = True
                receivingHand.hiddenCard = dealtCard
            receivingHand.cards.append(dealtCard)

    def shuffle(self):
        random.shuffle(self.cards)

    # Used for testing
    def sortInOrder(self):
        self.cards.sort(key = lambda c: (c.pointValue, c.rank, c.suit))

    def __str__(self):
        return "\n".join(map(str, self.cards))

class Card:
    specialRankToString = {"A": "Ace", "J": "Jack",
                              "Q": "Queen", "K": "King"}

    suitToString = {"H": "Hearts", "S": "Spades",
                     "C": "Clubs", "D": "Diamonds"}

    specialPointValues = {"A": 11, "K": 10, "Q": 10, "J": 10}

    def __init__(self, rank, suit, faceDown=False):
        self.rank = rank
        self.suit = suit
        self.faceDown = faceDown

        if rank in self.specialPointValues:
            self.pointValue = self.specialPointValues[rank]
        else:
            self.pointValue = int(rank)

    def __str__(self):
        if self.faceDown:
            return "?"

        rankRepr = self.rank
        if self.rank in self.specialRankToString:
            rankRepr = self.specialRankToString[self.rank]
        
        suitRepr = self.suitToString[self.suit]
        return f"{rankRepr} of {suitRepr}"

class Hand:
    # Apparently if you have an empty list as a default parameter, that empty list is actually
    #   a single list passed by reference to every object created thereafter.
    #   Googled it, this is because default arguments are evaluated only once, when the interpreter defines them.
    # Should consider if it should even be possible to define a hand from a list of cards rather than an empty hand.
    def __init__(self, cards=None):
        if cards is None:
            self.cards = []
        else:
            self.cards = cards
            
    def calculateScore(self):
        score = 0
        aces = 0
        for card in self.cards:
            if card.faceDown:
                continue
            if card.rank == "A":
                aces += 1
            score += card.pointValue

        # Adjust score to account for aces when value exceeds 21
        while aces > 0 and score > 21:
            aces -= 1
            score -= 10

        return score
    
    # In blackjack, only one card will ever be face-down.
    # However, in games like poker, several cards may be hidden or facedown to the player
    def revealAll(self):
        for card in self.cards:
            if card.faceDown:
                card.faceDown = False

    def isBlackjack(self):
        return len(self.cards) == 2 and self.calculateScore() == 21

    def discardAll(self):
        self.cards = []

    def __str__(self):
        return "\n".join(map(str, self.cards))

class Player:
    def __init__(self):
        pass

class Dealer:
    hand = Hand()

    def __init__(self):
        pass

class Blackjack:
    dealer = Dealer()
    players = []
    
    def __init__(self, numPlayers=1):
        pass


    def displayDealerHand(self):
        pass

    def displayPlayerHand(self, playerID):
        pass

    def displayAllPlayerHands(self):
        for player in self.players:
            self.displayPlayerHand(self, player.ID)

    def play(self):
        pass

# Generate a standard, 52-card deck of Ace-King
# The value of an ace is temporarily one, I will figure out a better way to represent 1/11
def generateStandardDeck():
    ranks = ["2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"]
    suits = ["H", "S", "C", "D"]

    cards = []
    for rank in ranks:
        for suit in suits:
            cards.append(Card(rank, suit))

    return cards

def awaitInput():
    ans = input().lower()
    while True:
        if ans == "h" or ans == "hit":
            return "hit"
        elif ans == "s" or ans == "stay":
            return "stay"
        ans = input().lower()


# Pause in seconds between each text display event
def testGame():
    standardDeck = generateStandardDeck()
    deck = Deck(standardDeck, numRepeats=6)
    deck.shuffle()

    while True:
        playerHand = Hand()
        dealerHand = Hand()

        # Player gets first card, then dealer
        print("Dealing to player...")
        deck.deal(playerHand)
        print(playerHand)

        print()

        print("Dealing to dealer...")
        deck.deal(dealerHand)
        print(dealerHand)

        print()

        print("Dealing second card to player...")
        deck.deal(playerHand)
        print(playerHand)

        print()

        print("Dealing second card to dealer (face-down)...")
        deck.deal(dealerHand, faceDown=True)
        print(dealerHand)

        print("\n------------------------------------------------------\n")

        print(f"Your hand: \n{playerHand}\nYour score: {playerHand.calculateScore()}\n")
        print(f"Dealer's hand: \n{dealerHand}\nDealer's score: {dealerHand.calculateScore()}\n")

        while playerHand.calculateScore() < 21:
            print("Would you like to (H)it or (S)tay?")
            ans = awaitInput()
            if ans == "hit":
                print("You selected 'hit'. Dealing another card...")
                deck.deal(playerHand)
                print(f"Your hand: \n{playerHand}\nYour score: {playerHand.calculateScore()}\n")
            else:
                print("You selected 'stay'.")
                dealerHand.revealAll()
                print(F"Dealer's hand: \n{dealerHand}\nDealer's score: {dealerHand.calculateScore()}\n")
                break

        # Bring the dealer up to 17
        while dealerHand.calculateScore() < 17:
            print("Dealing another card to the dealer...")
            deck.deal(dealerHand)
            print(f"Dealer received: {dealerHand.cards[-1]}\nDealer Score: {dealerHand.calculateScore()}\n")

        if playerHand.calculateScore() > 21:
            print("Bust! The dealer wins.")
        else:
            playerScore = playerHand.calculateScore()
            dealerScore = dealerHand.calculateScore()
            
            print(f"Your hand: \n{playerHand}\nYour score: {playerScore}\n")
            print(f"Dealer's hand: \n{dealerHand}\nDealer's score: {dealerScore}\n")

            if playerScore > dealerScore or dealerScore > 21:
                print("You win!")
            elif dealerScore > playerScore:
                print("The dealer wins!")
            else:
                print("Push!")

        print("Playing again...")
        playerHand.discardAll()
        dealerHand.discardAll()
        

if __name__ == "__main__":
    testGame()

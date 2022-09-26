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

class Deck:
    def __init__(self, sample_cards, num_repeats=1):
        self.cards = []
        
        for _ in range(num_repeats):
            for card in sample_cards:
                self.cards.append(Card(card.rank, card.suit, card.point_value))

    # Used for testing
    def sort_in_order(self):
        self.cards.sort(key = lambda c: (c.point_value, c.rank, c.suit))

    def __str__(self):
        return "\n".join(map(str, self.cards))

class Card:
    special_rank_to_string = {"A": "Ace", "J": "Jack",
                              "Q": "Queen", "K": "King"}

    suit_to_string = {"H": "Hearts", "S": "Spades",
                     "C": "Clubs", "D": "Diamonds"}

    def __init__(self, rank, suit, point_value):
        self.rank = rank
        self.suit = suit
        self.point_value = point_value

    def __str__(self):
        rank_repr = self.rank
        if self.rank in self.special_rank_to_string:
            rank_repr = self.special_rank_to_string[self.rank]
        
        suit_repr = self.suit_to_string[self.suit]
        return f"{rank_repr} of {suit_repr}"

class Hand:
    pass

# Generate a standard, 52-card deck of Ace-King
# The value of an ace is temporarily one, I will figure out a better way to represent 1/11
def generate_standard_deck():
    ranks = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"]
    points = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10]
    suits = ["H", "S", "C", "D"]

    cards = []
    for card in enumerate(ranks):
        for suit in suits:
            point_val = points[card[0]]
            rank = card[1]
            cards.append(Card(rank, suit, point_val))

    return cards

if __name__ == "__main__":
    standard_deck = generate_standard_deck()
    deck = Deck(standard_deck, num_repeats=6)
    deck.sort_by_rank()
    print(deck)
package player;

import java.util.ArrayList;
import java.util.List;
import cards.*;

public class Player {

    private int health;
    private List<Card> hand;
    private List<Card> deck;
    private Card lastPlayedCard;
    private static int initialNumberOfCards = 6;
    private boolean attackingStatus;
    private boolean isProtected;
    private int damage;
    private int damageToTake;

    public Player(int health, List<Card> deck) {
        this.health = health;
        this.deck = deck;
        this.hand = new ArrayList<>();
        lastPlayedCard = null;
        isProtected = false;
        attackingStatus = false;
        damage = 0;
        shuffleDeck();
    }

    public void takeDamage(int amountOfDamage){
        health = health - amountOfDamage;
    }

    public boolean getAttackingStatus(){
        return attackingStatus;
    }

    public void resetAttackingStatus(){
        attackingStatus = false;
    }
    public void setIsProtected(Boolean isProtected){this.isProtected = isProtected;}

    public int getDamage(){
        return damage;
    }

    public void resetDamage(){
        damage = 0;
    }

    public int getHealth() {
        return health;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDeck() {
        return deck;
    }

      public Card getLastPlayedCard() {
        return lastPlayedCard;
    }

      public int getInitialNumberOfCards() {
        return initialNumberOfCards;
    }

    public void shuffleDeck() {
        return;
    }

    public boolean getIsProtected() {return isProtected;}

    public void populateDeck(List<Card> cardList) {
        deck.addAll(cardList);
    }

    public int getNumberOfCardsInDeck(){
        return deck.size();
    }

    public int getNumberOfCardsInHand(){
        return hand.size();
    }

    public void drawCard() {
        if (deck.size() > 0) {
            Card drawnCard = deck.remove(deck.size() - 1);
            hand.add(drawnCard);
        }
    }

    public void drawInitialCards() {
        for (int i = 0; i < initialNumberOfCards; i++) {
            drawCard();
        }
    }

    public void playCard(int cardNumber) {
        Card cardToPlay = null;
        for (Card card : hand) {
            if (card.getNumber() == cardNumber) {
                cardToPlay = card;
                break;
            }
        }
        if (cardToPlay == null) {
            System.out.println("Invalid input. Please enter a valid card number or 'end'.");
        }else if (isProtected && cardNumber == 1) {
            System.out.println("You are already protected!");
        }
        else {
            hand.remove(cardToPlay);
            cardToPlay.effect();
            lastPlayedCard = cardToPlay;

            if (cardToPlay instanceof AttackCard) {
                attackingStatus = true;
                damage += cardToPlay.getNumber();
            }
            if (cardToPlay instanceof BoostAttackCard) {
                attackingStatus = false;
                damage += ((BoostAttackCard) cardToPlay).getBoost();
            }
            if (cardToPlay instanceof ProtectCard) {
                setIsProtected(true);
            }
        }
    }

    public void setDamageToTake(int damage){
        damageToTake = damage;
    }

    public void playCardInDefense(int cardNumber){
         Card cardToPlay = null;
        for (Card card : hand) {
            if (card.getNumber() == cardNumber) {
                cardToPlay = card;
                break;
            }
        }

        if(cardToPlay != null){
            lastPlayedCard = cardToPlay;
            attackingStatus = true;

            System.out.println(String.format("You've defended yourself! You've been attacked for %d damage and you've used special ability of Attacking card %d to deflect the attack\r\n", cardNumber, cardNumber));
        }
        else {
            System.out.println("You don't have this card in your hand...\r\n");
        }
    }

    public boolean checkForProtectionPossibilitiesInHand(){
        for (Card card : hand) {
            if (card instanceof ProtectCard || card.getNumber() == damageToTake) {

                return true;
            }
        }
        return isProtected;
    }

    public boolean findNumberInHand(int number){
        for(Card card : hand){
            if(card.getNumber() == number){
                return false;
            }
        }
        System.out.println(String.format("There is no card in hand with number %d\r\n", number));
        return true;
    }

    public void printHand() {
        System.out.println("Player's Hand:");
        for (Card card : hand) {
            System.out.println(card.getNumber() + "(" + card.description()+ ")\r");
            // You can add additional details about the card if needed
        }
        System.out.println();
    }
}


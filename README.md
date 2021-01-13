# Evil Hangman
### Evil Hangman Game Coded in Java

The game is played in the console. The program chooses a random word from the dictionary file and the user must guess the word, given a certain 
number of wrong letters (in this case 10) before they lose.

This code base includes a **"Normal"** and **"Evil"** version of the *gameState* class.
- The **normal** version picks a random word from the dictionary file and plays the typical hangman game.
- The **evil** version doesn't pick a word, rather it picks a word *length* and then depending on the user guess(es), identifies all possible word *"families"* ( words which have a given set of letters in the same positions) and chooses the word family that has the largest number of words.
This continues as the user enters more and more letters. If the user doesn't run out of guesses, then the the last set of possible word families will each have 1 option (i.e. one word) at which point the final word is chosen at random.

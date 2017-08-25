# spooky-spider-smash

**2D Android Game Loop (Bug Smasher Game)**

Android game programming using SurfaceView and Thread.

#### Assignment Requirements:
1. A Title Screen showing the name of the application and 2 options:
Play Game
High Score
2. Clicking High Score should activate a Preferences Screen with one option – the
display of the best high score obtained so far. Clicking the back button from this screen
should return to the Title Screen.
3. Clicking Play Game from the Title Screen should go to the Game Screen.
4. On the Game Screen, implement the following functionality:
   1. Clicking the back button at any time will return to the Title Screen.
   2. Upon entering the Game Screen, the app will audibly say “Get Ready”. After 3
seconds the game will begin. Music will begin to play and should continue playing as
long as the user is playing the game on the Game Screen.
   3. The player has 3 “lives”. These are displayed as small icons in the upper part
of the screen (on the score bar).
   4. The player score will appear in the upper left part of the screen (on the score
bar)
   5. A food bar will appear in the lower bottom portion of the screen.
   6. Bugs will randomly move from the top of the screen to the bottom of the
screen. For each bug that reaches the bottom of the screen, one “life” will be lost.
   7. The user can click on bugs to kill them. Use the DOWN event for these user
clicks.
   8. Bugs will take 1 click to kill and are worth 1 point. There should be a sound
effect when killing a bug. For this sound you should have at least 3 sounds and randomly
choose one to play (3 different squishy sounds).
   9. The score should be constantly updated.
   10. Bugs should move down the screen at different speeds (chosen randomly) since
some bugs will be faster than others.
   11. The “kill zone” of a bug should be as large as the bug sprite on screen.
   12. The legs of your bugs should appear to move.
   13. At the end of the game, if the score is higher than the current high score stored
in preferences then store the new high score in preferences. Also, display a message to
the user indicating a new high score has been reached. This can be done several ways:
         - a graphical image with the words “New High Score”
         - a popup Android dialog
         - a Toast
In addition, play a special sound effect for this event.
   14. For clicks on the screen that do not touch bugs, play a short sound effect that
gives the user feedback for the click.
   15. There must be situations in which multiple bugs can appear on screen at the
same time.

My special thanks to [Dr. Timothy Roden](http://cs.lamar.edu/faculty/troden/troden.htm) who provided his guidence and support in completion of this project.

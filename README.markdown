Catacomb Snatch Reloaded
========================
A modified and fixed version of Mojang's mojam game Catacomb Snatch.

Major Features:
Dual Stick Ccontrols
Chat System
Pause Screen
Level Selection
C418 and Ansou's Title and Ending music

It also contains bug fixes and underlying code improvement.

A good place to find custom levels for download is [here](http://codyshepp.com/cs/levels.php)

If you find any bugs, have any suggestions at all, or just want to talk email me at 
	rekh127 [at] gmail [dot] com

A major bug that is still present is the bug with sound on linux. To work around this for now install the Oracle 7 JDK or JRE. To do this on Ubuntu you can use the ppa:webupd8team/java. 
	sudo add-apt-repository ppa:webupd8team/java
	sudo apt-get update
	sudo apt-get install oracle-jdk7-installer
If you don't wish to do this or you are on another distribution you can manually install the download from oracle.com


Current controls:
----------------
	W       - move up
	A       - move left
	S       - move down
	D       - move right

	Up      - shoot up
	Left    - shoot left
	Down    - shoot down
	Right   - shoot right

	(hold up/down with left/right to move/shoot diagonally)

	R/X/Space - build rail (place car if in base)
	E/Z       - buy/pickup
	 

	Esc     - pause
	Enter/T - chat
	(in chat Enter to send Esc to cancel)






Cumulative Change log:
----------------------
	Improvements:
	*Pause Menu
	*Chat System
	*Hide/Show FPS option on Pause Menu
	*Dual Stick controls
	*C418's Title and Anosou's Ending songs
	*Level select
	Fixes:
	*Railcars pickup/drop off locations
	*Harvester crash bug
	*Turrets shooting at neutral entities
	*improved MP latency
	*Mobs spawning outside the map
	*fixed improper scoring
	Known Issues:
	*Sound bugs on linux (missing sounds with sun 6, unstable openjdk6/7)
	-workaround install Oracle 7 jdk/jre
	*Open Folder on linux
	

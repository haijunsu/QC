New codes are in default package, server package, client package and common 
package. If server and client are running on different machines, the common
package needs be included on both sides (Server/Client).
Usage:
java Server [-port <num>] [-rqvpgc <num>] [-log <num>]
		-port	Server port. Default is 8803

		-r	Number of rounds in the game
		-q	Number of questions per round
		-v	Question value
		-p	Right percent (0-100)
		-g	Room capacity (group size)
		-c	Number of Contestants

		-log	log level. Default level (warn). Debug levels: 0-Debug, 1-Info, 
				2-Warn, 3-Error
		

	java Client [-host <hostname/address>] [-port <num>] [-log <num>]
		-host	Server name or IP address. Default value is 'localhost'
		-port	Server port. Default value is 8803

		-log	log level. Default level (warn). Debug levels: 0-Debug, 1-Info, 
				2-Warn, 3-Error
		

Project 1 codes are in package server.impl. Only added some methods to support 
running on server. It still can be run independently by following command.
	java server.impl.GuessWhatWho [-rqvpgc <num>] [-log <num>]
		-r	Number of rounds in the game
		-q	Number of questions per round
		-v	Question value
		-p	Right percent (0-100)
		-g	Room capacity (group size)
		-c	Number of Contestants

		-log	log level

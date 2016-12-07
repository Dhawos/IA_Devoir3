tileRemaining(X,Y) :-
	safe(X,Y),
	not(visited(X,Y)).

shouldThrowRock :-
	not(tileRemaining(X,Y)).

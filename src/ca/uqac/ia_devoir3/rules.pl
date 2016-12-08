tileRemaining(X,Y) :-
	safe(X,Y),
	not(visited(X,Y)).

potentialMonster(X,Y) :-
    not(safe(X,Y)),
    neighborSmell(X,Y).

potentialCliff(X,Y) :-
    not(safe(X,Y)),
    neighborWind(X,Y).

shouldThrowRock(X,Y) :-
    potentialMonster(X,Y),
    not(potentialCliff(X,Y)).

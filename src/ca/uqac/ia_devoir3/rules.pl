:- dynamic
        safe/2,
        visited/2,
        neighborWind/2,
        neighborSmell/2.

count(P,Count) :-
        findall(1,P,L),
        length(L,Count).

tileRemaining(X,Y) :-
	safe(X,Y),
	not(visited(X,Y)).

riskyTile(X,Y) :-
    potentialMonster(X,Y).

riskyTile(X,Y) :-
    potentialCliff(X,Y).

potentialMonster(X,Y) :-
    neighborSmell(X,Y),
    not(safe(X,Y)).

potentialCliff(X,Y) :-
    neighborWind(X,Y),
    not(safe(X,Y)).

shouldThrowRock(X,Y) :-
    potentialMonster(X,Y).


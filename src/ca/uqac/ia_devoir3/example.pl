smell(currentTile).
mustFallBack :-
	dangerous(currentTile).
exit(X) :-
	light(X).
safe(X) :-
        empty(X).
dangerous(X) :-
	smell(X).
dangerous(X) :-
	wind(X).
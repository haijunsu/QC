; Haijun Su CS316 assignment3
; 1. Define a LISP function MIN-2 with the following properties. MIN-2 takes 
; two arguments. If those arguments have values A and B, and A and B are 
; numbers, then MIN-2 returns A if A<=B and return B if A>B; but if A or B
; is not a number MIN-2 returns the symbol ERROR.
; Examples:
; 	(MIN-2 21.3 7/2) => 7/2
; 	(MIN-2 17.5 29)  => 17.5
; 	(MIN-2 5 'APPLE) => ERROR
; 	(MIN-2 '(31) '(54)) => ERROR
;
; Solution to Problem 1
(defun min-2 (A B)
	(if (and (numberp A) (numberp B))	
		(if (> A B) B A)
		'ERROR))

; 2. Write a LIST function SAFE-AVG that takes 2 arguments and returns the 
; average of those arguments if they are numbers. If one or both of the 
; arguments is not a number, the function should return NIL.
; Examples:
; 	(SAFE-AVG 23 47.4) => 35.2
; 	(SAFE-AVG 3 8) => 11/2
; 	(SAFE-AVG '(23.1) 47.3) => NIL
; 	(SAFE-AVG 'ORANGE 'PLUM) => NIL
;
; Solution to Problem 2
(defun safe-avg (A B)
	( if (and (numberp A) (numberp B))
		(/ (+ A B) 2)
		NIL))

; 3. Write a LISP predicate ODD-GT-MILLION that takes one argument, and which
; returns T if its argument is an odd integer greater that a million, but 
; returns NIL otherwise. Hint: Make use of the predicate INTEGERP. 
; Examples:
; 	(ODD-GT-MILLION 2010231) => T
; 	(ODD-GT-MILLION 171) => NIL
; 	(ODD-GT-MILLION 2010232) => NIL
; 	(ODD-GT-MILLION 21/5) => NIL
; 	(ODD-GT-MILLION 1718671.24) => NIL
; 	(ODD-GT-MILLION '(2010231)) => NIL
; 	(ODD-GT-MILLION 'APPLE) => NIL
;
; Solution to Problem 3
(defun odd-gt-million (A)
	(if (integerp A)
		(if (and (oddp A) (> A 1000000))
			T
			NIL)
		NIL))

; 4. Read the discussion of the predicate MEMBER on page 51 of Winston and Horn
; or pages 59-60 of Wilensky. Then write a LISP predicate MULTIPLE-MEMBER that
; takes two arguments and behaves as follows: If the first argument is a symbol
; or number and the second is a list, then MULTIPLE-MEMBER return a true value
; if the first argument occurs at least twice in the second argument, and 
; returns NIL otherwise.
; Examples:
; 	(MULTIPLE-MEMBER 'A '(B A B B A C A D)) => (A C A D)
; 	(MULTIPLE-MEMBER 'A '(B A B B C C A D)) => (A D)
; 	(MULTIPLE-MEMBER 'A '(B A B B C D)) => NIL
; [Notice that the behavior of MULTIPLE-MEMBER is unspecified in cases where
; the first argument is not a symbol or number, and in cases where the second
; argument is not a list. In other words, you definition may return any value
; or produce an evaluation error in such cases.]
;
; Solution to Problem 4
(defun multiple-member (A B)
	(member A (cdr (member A B))))

; 5. Define a LIST function MONTH->INTEGER which takes as argument a symbol that
; should be the name of a month, and which returns the number of the month. For
; example, (MONTH->INTEGER 'MARCH) => 3 and (MONTH->INTEGER 'JUNE) => 6. If the
; argument is not a symbol that is the name of a month, the function should
; return the symbol ERROR. For example, (MONTH->INTEGER 'CAT) => ERROR and 
; (MONTH->INTEGER '(MARCH)) => ERROR
;
; Solution to Problem 5
(defun month->integer (A)
	(if (symbolp A)
		(if (member A '(January February March April May June July August September October November December))
			(- 13 (length (member A '(January February March April May June July August September October November December))))
			'ERROR)
		'ERROR))

; 6. Define a LIST function SCORE->GRADE which takes a single argument, s, and
; returns a symbol according to the following scheme:
; 	s >= 90		A
; 	87 <= s < 90	A-
; 	83 <= s < 87	B+
; 	80 <= s < 83 	B
; 	77 <= s < 80	B-
; 	73 <= s < 77	C+
; 	70 <= s < 73	C
; 	60 <= s < 70	D
; 	s < 60		F
; If the argument s is not a number then the function should return NIL.
; Examples:
; 	(SCORE->GRADE 86.3) => B+
; 	(SCORE->GRADE 106) => A
; 	(SCORE->GRADE -10.1) => F
; 	(SCORE->GRADE 59.9) => F
; 	(SCORE->GRADE 83) => B+
; 	(SCORE->GRADE 74) => C+
; 	(SCORE->GRADE 67) => D
; 	(SCORE->GRADE 87.0) => A-
; 	(SCORE->GRADE '(86.3)) => NIL
; 	(SCORE->GRADE 'DOG) => NIL
;
; Solution to Problem 6
(defun score->grade (s)
	(if (numberp s)
		(cond ((> s 90) 'A)
		      ((and (>= s 87) (< s 90)) 'A-)
		      ((and (>= s 83) (< s 87)) 'B+)
		      ((and (>= s 80) (< s 83)) 'B)
		      ((and (>= s 77) (< s 80)) 'B-)
		      ((and (>= s 73) (< s 77)) 'C+)
		      ((and (>= s 70) (< s 73)) 'C)
		      ((and (>= s 60) (< s 70)) 'D)
		      ((< s 60) 'F))
		NIL))
	

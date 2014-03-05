; CS316 Assignment 4
; Haijun Su
;
; SECTION 1
; A. Re-write SUM
(defun my-sum (L)
	(let ((X (sum (cdr L))))
		(+ (car L) X)))
; B. Re-write NEG-NUMS
; (NEG-NUMS '(-1 0 -8 2 0 8 -1 -8 2 8 4 -3)) => (-1 -8 -1 -8 -3)
(defun my-neg-nums (L)
	(let ((X (neg-nums (cdr L))))
		(if (>= (car L) 0) X (cons (car L) X))))
; C. Re-write INC-LIST-2
; if L is any list of numbers and N is a number then (INC-LIST-2 L N) returns 
; a list of the same length as L in which each element is eaual to ( N + the 
; corresponding element of L) For example, 
; 	(INC-LIST-2 () 5) => NIL
; 	(INC-LIST-2 '(3 2.1 1 7.9) 5) => (8 7.1 6 12.9)
(defun my-inc-list-2 (L N)
	(let ((X (inc-list-2 (cdr L) N)))
		(cons (+ (car L) N) X))) 

; D. Re-write INSERT
; If N is any real number and L is any list of real numbers in ascending order 
; the (INSERT N L) return a list of number in ascending order obtained by 
; inserting N in an appropriate position in L. Examples:
; 	(INSERT 8 ()) => (8)
; 	(INSERT 4 '(0 0 1 2 4)) => (0 0 1 2 4 4)
; 	(INSERT 4 '(0 0 1 3 3 7 8 8 )) => (0 0 1 3 3 4 7 8 8)
(defun my-insert (N L)
	(let ((X (insert N (cdr L))))
		(if (< (car L) N) 
		    (cons (car L) X)
		    (cons N (cons (car L) (cdr X))))))

; E. Re-write ISORT
; If L is any list of real numbers the (ISORT L) is a list consisting of the 
; elements of L in ascending order.
(defun my-isort (L)
	(let ((X (isort (cdr L))))
		(insert (car L) X)))

; F. Re-write SPLIT-LIST
; If L is any list then (SPLIT-LIST L) returns a list of two lists, in which the first list consists of the 1st, 3rd, 5th, ... elements of L and the second list consists of the 2nd, 4th, 6th, ... elements of L.
; Examples:
; 	(SPLIT-LIST ()) => (NIL NIL)
; 	(SPLIT-LIST '(A B C D 1 2 3 4 5)) => ((A C 1 3 5) (B D 2 4))
; 	(SPLIT-LIST '(B C D 1 2 3 4 5)) => ((B D 2 4) (C 1 3 5))
; 	(SPLIT-LIST '(A)) => ((A) NIL)
(defun my-split-list (L)
	(let ((X (split-list (cdr L))))
		(list 
			(cons (car L) (car (cdr X))) 
			(car X))))
; G. PARTITION is a function that is already defined on venus and euclid; if L
; is a list of real numbers and P is a realnumber then (PARTITION L P) returns 
; a list whose CAR is a list of the elements of L that are strictly less than 
; P, and whose CADR is a list of the other elements of L. Each element of L 
; must appear in the CAR or CADR of (PARTITION L P), and should appear there
; just as many times as in L.
; Example:
; 	(PARTITION '(7 5 3 2 1 5) 1) => (NIL (7 5 3 2 1 5))
; 	(PARTITION '(4 0 5 3 1 2 4 1 4) 4) => ((0 3 1 2 1) (4 5 4 4))
; 	(PARTITION () 9) => (NIL NIL)
(defun my-partition (L P)
	(let ((X (partition (cdr L) P)))
		(if (< (car L) P)
			(list 
				(cons (car L) (car X))
				(car (cdr X)))
			(list 
				(car X)
				(cons (car L) (car (cdr X)))))))

; Section 2
; 1. Define a recursive function SUM with the properties stated in problem A. 
; Note that whereas NIL is not a valid argument of MY-SUM, NIL is a valid 
; argument of SUM
;
; Solution to Problem 1
(defun sum (L)
	(if (null L)
		0
		(let ((X (sum (cdr L))))
			(+ (car L) X))))

; 2. Define a recursive function NEG-NUMS with the properties stated in problem
; B. Note that NIL is a valid argument of NEG-NUMS.
;
; Solution to Problem 2
(defun neg-nums (L)
	(if (null L)
		NIL
		(let ((X (neg-nums (cdr L))))
			(if (>= (car L) 0) 
				X 
				(cons (car L) X)))))

; 3. Define a recursive function INC-LIST-2 with the properties stated in
; problem C. Note that the first argument of INC-LIST-2 maybe NIL.
;
; Solution to Problem 3
(defun inc-list-2 (L N)
	(if (null L)
		NIL	
		(let ((X (inc-list-2 (cdr L) N)))
			(cons (+ (car L) N) X)))) 

; 4. Define a recursive function INSERT with the properties stated in problem
; D. Not that the second argument of INSERT may be NIL.
;
; Solution to Problem 4
(defun insert (N L)
	(if (null L)
		(cons N ())
		(let ((X (insert N (cdr L))))
			(if (< (car L) N) 
		    		(cons (car L) X)
		    		(cons N (cons (car L) (cdr X)))))))

; 5. Define a recursive function ISORT with the properties stated in problem E.
; Hint: In your definition of ISORT you should not have to call any function
; other than ISORT itself, INSERT, CAR, CDR, and ENDP. (A special from such as
; IF or COND is not considered to be a function, and will be needed.)
;
; Solution to Problem 5
(defun isort (L)
	(if (null L)
		NIL
		(let ((X (isort (cdr L))))
			(insert (car L) X))))

; 6 Define a recursive function SPLIT-LIST with the properties stated in problem
; F.
;
; Solution to Problem 6
(defun split-list (L)
	(if (null L)
		'(NIL NIL)
		(let ((X (split-list (cdr L))))
			(list 
				(cons (car L) (car (cdr X))) 
				(car X)))))

; 7. Define a recursive function PARTITION with the properties stated in problem
; G.
;
; Solution to Problem 7
(defun partition (L P)
	(if (null L)
		'(NIL NIL)	
		(let ((X (partition (cdr L) P)))
			(if (< (car L) P)
				(list 
					(cons (car L) (car X))
					(car (cdr X)))
				(list 
					(car X)
					(cons (car L) (car (cdr X))))))))

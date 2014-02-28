; A Re-write SUM
(defun my-sum (L)
	(let ((X (sum (cdr L))))
		(+ (car L) X)))
; B Re-write NEG-NUMS
; (NEG-NUMS '(-1 0 -8 2 0 8 -1 -8 2 8 4 -3)) => (-1 -8 -1 -8 -3)
(defun my-neg-nums (L)
	(let ((X (neg-nums (cdr L))))
		(if (> (car L) 0) X (cons (car L) X))))
; C Re-write INC-LIST-2
; if L is any list of numbers and N is a number then (INC-LIST-2 L N) returns 
; a list of the same length as L in which each element is eaual to ( N + the 
; corresponding element of L) For example, 
; 	(INC-LIST-2 () 5) => NIL
; 	(INC-LIST-2 '(3 2.1 1 7.9) 5) => (8 7.1 6 12.9)
(defun my-inc-list-2 (L N)
	(let ((X (inc-list-2 (cdr L) N)))
		(cons (+ (car L) N) X))) 

; D Re-write INSERT
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

; E Re-write ISORT
; If L is any list of real numbers the (ISORT L) is a list consisting of the 
; elements of L in ascending order.
(defun my-isort (L)
	(let ((X (isort (cdr L))))
		(insert (car L) X)))



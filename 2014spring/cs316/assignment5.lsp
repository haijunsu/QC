; CS316 assignment 5
; Haijun Su

; A. INDEX is a function that is already defined on euclid and venus. If N is 
; any positive integer and L is any list, then (INDEX N L) return the Nth 
; element of L if N does not exceed the length of L; if N exceeds the length
; of the list L, then (INDEX NL) returns the symbol ERROR. For example, 
; (INDEX 3 '(A S (A S) (A) D)) => (A S)
; (INDEX 6 '(A S (A S) (A) D)) => ERROR
; Complete the following definition of a function MY-INDEX without making
; further calls of INDEX and without calling MY-INDEX recursively, in such
; a way that if N is any integer greater than 1 and L is any nonempty list 
; then (MY-INDEX N L) is equal to (INDEX N L).
;
(defun my-index (N L)
	(let ((X (index (- N 1) (cdr L))))
		(if (endp L) 
			'ERROR
			(if (eql n 1)
				(car L)
				X)))) 
; B. MIN-FIRST is a function that is already defined on euclid and venus; if L
; is any nonempty list of real number then (MIN-FIRST L) is a list whose CAR 
; is the minimum of the numbers in L and whose CDR is the list obtained when
; the first occurrence of that value is removed from L. (Thus (MIN_first L) 
; is "L with the first occurrence of its minimum value moved to the front")
; For Example,
; 	(min-first '(0 3 1 1 0 3 5)) => (0 3 1 1 0 3 5)
; 	(min-first '(4 3 1 1 0 3 5 0 3 2)) => (0 4 3 1 1 3 5 0 3 0 2)
; Complete the followint definition of a function MY-MIN-FIRST without making
; further calls of MIN-FIRST and without calling MY_MIN and without calling 
; MY-MIN-FIRST recursively, in such a way that if L is any list of at leat
; two real number then (MY-LIST-First L) is equal to (MIN-FIRST L).
;
(defun my-min-first (L)
	(let ((X (min-first (CDR L))))
		(if (<= (car L) (car X))
			L
			(append (cons (car X) (cons (car L) nil)) (CDR X)))))
 
; C. SSORT is a function that is already defined on euclid and venus; if L is
; any list of real numbers then (SSORT L) is a list of those numbers in 
; ascending order. Complete the following definition of function MY-SSORT
; without making further calls of SSORT and without calling MY-SSORT 
; recursively, in such a way that if L is any nonempty list of real numbers 
; then (MY-SSORT L) is equal to (SSORT L).
;
(defun my-ssort (L)
	(let* (( L1 (min-first L))
		(X (ssort (cdr L1))))
		(cons (car L1) X)))


; PART 1
; 1. Define a recursive function INDEX with the properties stated in problem
; A. Note that the first argument of INDEX my be 1, and that the second 
; argument my be NIL.
;
; Solution to Problem 1
; 
(defun index (N L)
	(if (endp L)
            'ERROR
            (if (eql n 1)
                (car L)
                (index (- N 1) (cdr L)))))
; 2. Define a recursive function MIN-FIRST with the properties stated in problem
; B. Not that the argument of MIN-FIRST may be a list of length 1.
;
; Solution to Problem 2
;
(defun min-first (L)
	(if (= 1 (length L))
		L
		(let ((X (min-first (CDR L))))
			(if (<= (car L) (car X))
                        	L
                        	(append 
					(cons (car X) (cons (car L) nil)) 
					(CDR X))))))

; 3. Define a recursive function SSORT with the properties stated in problem
; C. In the definition of SSORT you may call SSORT itself, MIN-FIRST, CONS,
; CAR, CDR and ENDP, but you should not call any other function.
;
; Solution to Problem 3
;
(defun ssort (L)
	(if (endp L)
		NIL
		(let* (( L1 (min-first L))
                	(X (ssort (cdr L1))))
                	(cons (car L1) X))))

; 4. Use the function PARTITION from List Assignment 4 to complete the 
; following definition of a recursive function QSORT such that if L is a list 
; of real numbers then (QSORT L) is a list of those numbers in ascending order.
; 
; Solution to Problem 4
;
(defun qsort (L)
	(cond ((endp L) ())
		(T (let ((PL (partition L (car L))))
			(cond 
				((endp (car PL))
					(cons (car L) (qsort (cdr L))))
				((endp (cdr PL))
					(cons (qsort (cdr L)) (car L)))
				(T (append 
					(qsort (car PL)) 
					(qsort (car (cdr PL))))))))))

; 5. Define a List function MERGE-LISTS such that if each of L1 and L2 is a
; list of real numbers in ascending order then (MERGE-LISTS L1 L2) returns
; the list of numbers in ascending order that is obtained by merging L1 and L2.
; Your definition must not call any sort function.
; Examples:
; 	(MERGE-LISTS '(2 4 5 5 7 8 9) '(3 4 6 9 9))
; 		=> (2 3 4 4 5 5 6 7 8 9 9 9)
; 	(MERGE-LISTS '(1 2 3) '(4 5 6 7)) => (1 2 3 4 5 6 7)
; 	(MERGE-LISTS '(3 4 5 6 7) '(0 1 2 3)) => (0 1 2 3 3 4 5 6 7)
;
; Hint: Consider the 4 cases L1=(), L2=(), (< (car L1) (car L2)), and
; (>= (car L1) (car L2)).
;
; Solution to Problem 5
;
(defun merge-lists (L1 L2)
	(cond ((endp L1) L2)
		((endp L2) L1)
		((< (car L1) (car L2)) 
			(cons (car L1) (merge-lists (cdr L1) L2)))
		((>= (car L1) (car L2))
			(cons (car L2) (merge-lists L1 (cdr L2))))))

; 6. User the function SPLIT-LIST from Lisp Assignment 4 and MERGE-LISTS to
; define a recursive List function MSORT such that if L is a list of real
; numbers then (MSORT L) is a list consisting of the elements of L in 
; ascending order. In the definition of MSORT you may call SPLIT-LIST, 
; MSORT itself, MERGE-LISTS, CAR, CDR, CADR and ENDP, but you should not call
; any other function. Be sure to use LET or LET*, so that MSORT only calls 
; SPLIT-LIST once.
; Hint: Does a list of length 1 satify condition BC-3 for one of your 
; function's recursive calls?
;
; Solution to Problem 6
;
(defun msort (L)
	(cond ((endp L) ())
		((endp (cdr L)) L);length = 1
		(T (let ((X (split-list L)))
			(cond ((endp (car X)) (msort (cadr X)))
				((endp (cadr X)) (msort (car X)))
				((and (endp (cdr (car X))) 
					(endp (cdr (cadr X)))) 
						;L length is 2, order L
						(if (< (car (car X)) (car (cadr X)))
							(append (car X) (cadr X))
							(append (cadr X) (car X)))) 
				(T (merge-lists 
					(msort (car X))
					(msort (cadr X))))))))) 

; In problems 7-10, assume the argument is a list
; (a b a a a c c)
; 7. 10.4(a) on page 418 of Sethi (a b a c)
;
; Solution to Problem 7
;
(defun remove-adj-dupl (L)
	(if (endp L) 
		()
		(if (eq (car L) (car (remove-adj-dupl (cdr L))))
			(remove-adj-dupl (cdr L))
			(cons (car L) (remove-adj-dupl (cdr L))))))

; 8. 10.4(b) on same page
; (a b)
; Solution to Problem 8 
;
(defun unrepeated-elts (L)
	(cond ((endp L) ())
		((or (endp (cdr L)) (not (equal (car L) (cadr L))))
			(cons (car L) (unrepeated-elts (cdr L))))
		((or (endp (cddr L)) (not (equal (car L) (caddr L))))
			(unrepeated-elts (cddr L)))
		(t (unrepeated-elts (cdr L)))))

; 9. 10.4(c) on same page
; (a c)
; Solution to Problem 9 
;
(defun repeated-elts (L)
	(cond ((endp L) ())
		((or (endp (cdr L)) (not (equal (car L) (cadr L))))
			(repeated-elts (cdr L)))
		((or (endp (cddr L)) (not (equal (car L) (caddr L))))
			(cons (car L) (repeated-elts (cdr L))))
		(t (cons (car L) (repeated-elts (cddr L))))))

; 10. 10.4 (d) on same page
; ((1 a) (1 b) (3 a) (2 c))
; Solution to Problem 10
;
(defun count-repetitions (L)
	(if (endp L) 
		()
		(let ((X (count-repetitions (cdr L))))
			(cond ((endp X) (cons (cons 1 (cons (car L) ()))()))
				((eq (car L) (cadar X)) 
					(append (cons (cons (+ 1 (caar X)) (cons (cadar X) ())) ()) (cdr X)))
				((not (eq (car L) (cadar X)))
					(append (cons (cons 1 (cons (car L) ())) ()) X))))))

; 11. [Exercise 8 on p.141 of Wilesky]
;
; Solution to Problem 11
;
(defun subset (f L)
	(if (endp L)
		()
		(if (funcall f (car L))
			(cons (car L) (subset f (cdr L)))
			(subset f (cdr L))))) 

; 12. [Exercise 7 on P.141 of Wilensky]
;
; Solution to Problem 12
;
(defun our-some (f L)
	(if (endp L)
		NIL
		(if (funcall f (car L))
			L
			(our-some f (cdr L)))))
(defun our-every (f L)
	(if (endp L)
		NIL
		(if (funcall f (car L))
			(if (null (cadr L)); last element
				T
				(our-every f (cdr L)))
			NIL)))

; 13. QSORT1
;
; Solution to Problem 13
;
(defun mypp (f L P)
	(if (endp L)
		'(NIL NIL)
		(let ((X (mypp f (cdr L) P)))
			(if (funcall f (car L) P)
				(list
					(cons (car L) (car X))
					(car (cdr X)))
				(list
					(car X)
					(cons (car L) (car (cdr X))))))))	
(defun qsort1 (f L)
	(if (endp L)
		()
		(let ((PL (mypp f L (car L))))
			(cond
				((endp (car PL))
					(cons (car L) (qsort1 f (cdr L))))
				((endp (cdr PL))
					(cons (qsort1 f (cdr L)) (car L)))
				(T (append 
					(qsort1 f (car PL))
					(qsort1 f (cadr PL))))))))

; 14. 10.12a on P420 of Sethi
; Example:
; 	(FOO #'- '(1 2 3 4 5)) => ((-1 2 3 4 5) (1 -2 3 4 5) (1 2 -3 4 5) 
; 					(1 2 3 -4 5) (1 2 3 4 -5)
;
; Solution to Problem 14
;
(defun foo (f L)
	(if (endp L)
		()
		(if (null (cadr L)) ;last element
			(cons (funcall f (car L)) ())
			(list 
				(cons (funcall f (car L)) (cdr L))
				(mapcar (lambda (L1) (car (cons (car L) (cons L1() ))
				(foo f (cdr L)))))))

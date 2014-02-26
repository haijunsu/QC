;3 Use SETF or SETQ to assign X the value (A B C) 
;and then use X to produce the list (A B C A B C).
(setq X '(A B C))
(setq Y (append X X))
(defun ex3 ()
	(setq Y (append (setq X '(A B C)) X )))

;4 Write the expression ''(A) using QUOTE rather than '. What is the data type of the expression 'A?
; '(A) is a symbol
(defun ex4 ()
	(quote '(A)))

;5 (a) Use SETF or SETQ go give Y the value (A B). But, instead of writing '(A B), 
;you must use a list function call to create the list (A B)
(defun ex5a ()
	(setq Y (cons 'a '(B))))
; (b) Write code that makes the list (D A). However, you must get A from the variable Y, 
; which has the value (A B) from part(a).
(defun ex5b()
	(setq Y2 (list 'd (car Y))))
;6 Define a function called SQR that returns a list of the perimeter and the area of a square, 
;given the length of one side.
(defun sqr (x) 
	(list (* 4 x) (* x x)))
;7 Define QUADRATIC, a function with three paramenters A, B and C that returns a list of the two roots of the
;equation Ax^2 + Bx + C = 0. You should use the buint-in function SQRT. Recall that the two roots are given by
;the following formula:(-B+_SQRT(B^2-4AC)/2A
(defun quadratic (A B C)
	(list 
		(/ (+ (- 0 B) (sqrt (- (* B B) (* 4 A C)))) (* 2 A)) 
		(/ (- (- 0 B) (sqrt (- (* B B) (* 4 A C)))) (* 2 A))))
;8 Write a List function that computes the area of a circle given its radius. Use the predefined constant PI.
(defun radius (A) 
	(SQRT (/ A PI)))
;9 Define a function called FTOC which takes as its argument a degree reading in Fahrenheit and returns its
; Celsius equivalent. (The Celsius equivalent of a Fahrenheit temperature is obtained by substracting 32 and 
; multiplying by 5/9.)
(defun ftoc (c)
	(* (- c 32.0) (/ 5 9)))
; 10. Define a function ROTATE-LEFT which takes a list as argument and returns a new list in which the former
; first element has became the last element. Thus (ROTATE-LEFT '(A B C D)) should return (B C D A)
(defun rotate-left (L)
	(append (cdr L) (list (car L))))
; 11. A point (x, y) in the plane can be represented as a two-element list (x y). Write a List function that takes
; two such lists as arguments and returns the distance between the corresponding points. Recall that the distance 
; between two points (x1, y1) and (x2, y2) is given by sqrt((x1-x2)^2 + (y1-y2)^2)
(defun ex11 (P1 P2)
	(sqrt (+ (expt (- (car P1) (car P2)) 2) (expt (- (nth 1 P1) (nth 1 P2)) 2))))
; 12. Define Lisp function HEAD and TAIL that behave just like CAR and CDR, respectively.
(defun head (L)
	(car L))
(defun tail (L)
	(cdr L))
; 13. Define a Lisp function SWITCH that takes as its argument a two-element list and returns a list consisting
; of the same two elements, but in the opposite order. Example: (switch '(A B)) => (B A)
(defun switch (L)
	(list (nth 1 L) (car L)))
(defun switch2 (L)
	(reverse L))


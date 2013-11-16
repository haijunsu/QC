package ch5;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Exercise c5.7
 * 
 * This Calculator can parser a regular expression as a Postfix notation with a
 * stack. It can calculate the expression value while it transfer the regular
 * expression to a postfix notation expression. There is another method to
 * calculate the postfix notation expression with linked list and stack.
 * 
 * <pre>
 * Methods:
 * 	1. parserExpression(String expression)
 * 	2. calculatePostfixNotationExpression(String pfnttEx)
 * 
 * Outputs:
 * Example: 
 * ((3 + 4.050 + (5.89 + 2) * 5 - (0.5+0.3) * (8 + 3))) / 4 = 9.425
 * Postfix Notation expression: 3 4.05 5.89 2 + 5 * 0.5 0.3 + 8 3 + * - + + 4 /
 * Result of Postfix Notation expression: 9.425
 * 
 * Please input your expression: (((4+5)*4 *3 + (6 +8)) -3) *8/3
 * (((4+5)*4 *3 + (6 +8)) -3) *8/3 = 317.33334
 * Postfix Notation expression: 4 5 + 4 * 3 * 6 8 + + 3 - 8 * 3 /
 * Result of Postfix Notation expression: 317.33334
 * 
 * </pre>
 * 
 * @author Haijun Su Created date: 2013-10-4
 */
public class ExpressionCalculator {

	/**
	 * Use a stack to store previous operations, previous expressions and left
	 * parentheses
	 */
	private Stack<String> expStack = null;

	private Stack<Float> calStack = null;

	private List<String> calList = null;

	/**
	 * <pre>
	 * 1. Analysis the expression and generation a postfix notation 
	 * 2. Calculate the expression value
	 * </pre>
	 * 
	 * @param expression
	 * @return
	 * @throws Exception
	 */
	public String parserExpression(String expression) throws Exception {
		String solution = null; // postfix notation expression
		char[] expChars = expression.toCharArray();
		String tmpPreExp = null;
		char tmpOperation = '\0';
		boolean isAddedtoRight = false;
		int numOffset = -1;
		// init stack
		expStack = new Stack<String>();
		calStack = new Stack<Float>();
		float currentNum = 0;
		float calResult = 0;
		for (int i = 0; i < expChars.length; i++) {
			if (isDigit(expChars[i]) || expChars[i] == '.') {
				if (numOffset == -1) {
					numOffset = i;
				}
				continue; // check the next char
			} else {
				// build number and push it into stack
				if (numOffset != -1) {
					currentNum = buildNumber(expChars, numOffset, i);
					tmpPreExp = formatNumber(currentNum);
					// if previous operation is priority operation, combine it
					// as expression.
					if (tmpOperation != '\0') {
						// needn't check the expression because it has been
						// checked before assigned value to tmpOperation.
						tmpPreExp = expStack.pop() + " " + tmpPreExp + " "
								+ Character.toString(tmpOperation);
						currentNum = calculate(calStack.pop(), currentNum,
								tmpOperation);
						tmpOperation = '\0';
					}
					calStack.push(currentNum);
					expStack.push(tmpPreExp);
					numOffset = -1;
					currentNum = 0;
					tmpPreExp = null;
				}
				if (isOperator(expChars[i])) {
					if (expStack.isEmpty()) {
						throw new Exception(
								"Operation can't be at first of expression.");
					}
					// if the operation follows the left parenthesis, throw
					// exception
					tmpPreExp = expStack.pop();
					if (tmpPreExp.equals("(")) {
						throw new Exception(
								"Operation can't follows the left parenthesis");
					}
					expStack.push(tmpPreExp);
					tmpPreExp = null;

					tmpOperation = expChars[i];
					if (!isPriorityOperator(expChars[i])) {
						// put addition or subtraction operator to stack
						expStack.push(Character.toString(tmpOperation));
						tmpOperation = '\0';
					}
				} else if (isLeftParentheses(expChars[i])) {
					if (tmpOperation != '\0') {
						expStack.push(Character.toString(tmpOperation));
						tmpOperation = '\0';
					}
					if (!expStack.isEmpty()) {
						tmpPreExp = expStack.pop();
						if (!tmpPreExp.equals("(")
								&& !isOperator(tmpPreExp.toCharArray()[0])) {
							throw new Exception(
									"Left parenthese must follow an operation.");
						}
						expStack.push(tmpPreExp);
					}
					expStack.push(Character.toString(expChars[i]));
				} else if (isRightParentheses(expChars[i])) {
					if (expStack.isEmpty() && tmpPreExp == null) {
						throw new Exception(
								"Right parenthesis can't be at first of expresisson.");
					}
					tmpPreExp = expStack.pop(); // should be a number or an
												// expression
					calResult = calStack.pop();
					if (tmpOperation != '\0'
							|| isOperator(tmpPreExp.toCharArray()[0])) {
						throw new Exception(
								"Right parenthesis can't be after an operator.");
					}
					if (tmpPreExp.equals("(")) {
						throw new Exception("Parenthesis block is empty.");
					}
					// combine the expression between parentheses
					boolean isFoundLeftParenthesis = false;
					String tmpPop = null;
					String tmpPopOp = null;
					while (!expStack.isEmpty() && !isFoundLeftParenthesis) {
						tmpPop = expStack.pop();
						if (tmpPop.equals("(")) {
							isFoundLeftParenthesis = true;
							tmpPop = null;
						} else {
							if (isAddedtoRight) {
								tmpPreExp = tmpPop + " " + tmpPreExp;
								calResult = calculate(calStack.pop(),
										calResult, tmpPopOp.toCharArray()[0]);
								isAddedtoRight = false;
							} else {
								tmpPreExp = tmpPreExp + " " + tmpPop; // tmpPop
																		// is an
																		// operator
								tmpPopOp = tmpPop;
								isAddedtoRight = true;
							}
							tmpPop = null;
						}
					}
					if (!expStack.isEmpty()) {
						// check if the previous is priority operator.
						tmpPop = expStack.pop();
						if (isPriorityOperator(tmpPop.toCharArray()[0])) {
							// combine the priority operation
							tmpPreExp = expStack.pop() + " " + tmpPreExp + " "
									+ tmpPop;
							calResult = calculate(calStack.pop(), calResult,
									tmpPop.toCharArray()[0]);// calculate
						} else {
							expStack.push(tmpPop);
						}
					}
					if (!isFoundLeftParenthesis) {
						throw new Exception("Can't find left parenthesis.");
					}
					expStack.push(tmpPreExp);
					calStack.push(calResult);
					calResult = 0;
					tmpPreExp = null;
					isAddedtoRight = false;
				}
			}
		}
		if (numOffset != -1) {
			// handle last number
			currentNum = buildNumber(expChars, numOffset, expChars.length);
			tmpPreExp = formatNumber(currentNum);
			// if previous operation is priority operation, combine it
			// as expression.
			if (tmpOperation != '\0') {
				// needn't check the expression because it has been
				// checked before assigned value to tmpOperation.
				tmpPreExp = expStack.pop() + " " + tmpPreExp + " "
						+ Character.toString(tmpOperation);
				currentNum = calculate(calStack.pop(), currentNum, tmpOperation);
				tmpOperation = '\0';
			}
			calStack.push(currentNum);
			expStack.push(tmpPreExp);
			numOffset = -1;
			currentNum = 0;
			tmpPreExp = null;
		}
		if (tmpOperation != '\0') {
			throw new Exception("Operation can't be at the end of expersission");
		}
		solution = expStack.pop();
		calResult = calStack.pop();
		if (isOperator(solution.toCharArray()[0])) {
			throw new Exception("Operation can't be at the end of expersission");
		}
		// build the postfix notation expression
		String calOp = null;
		while (!expStack.isEmpty()) {
			if (isAddedtoRight) {
				solution = expStack.pop() + " " + solution;
				calResult = calculate(calStack.pop(), calResult,
						calOp.toCharArray()[0]);// calculate
				isAddedtoRight = false;
			} else {
				calOp = expStack.pop();
				solution = solution + " " + calOp;
				isAddedtoRight = true;
			}
		}
		System.out.println(expression + " = " + formatNumber(calResult));
		expStack = null;
		calStack = null;
		return solution;
	}

	/**
	 * Calculate Postfix Notation expression.
	 * 
	 * @param pfnttEx
	 * @return
	 */
	public float calculatePostfixNotationExpression(String pfnttEx) {
		float calResult = 0;
		String[] items = pfnttEx.split(" ");
		// We can calculate value with array directly. In order to show how to
		// use list, I need transfer arrays to a list.

		calList = new LinkedList<String>(); // list is FIFO
		calStack = new Stack<Float>();
		for (int i = 0; i < items.length; i++) {
			calList.add(items[i]);
		}

		for (Iterator<String> iterator = calList.iterator(); iterator.hasNext();) {
			String item = iterator.next();
			if (isOperator(item.toCharArray()[0])) {
				calResult = calStack.pop();
				calResult = calculate(calStack.pop(), calResult,
						item.toCharArray()[0]);
				calStack.push(calResult);
			} else {
				calStack.push(Float.valueOf(item));
			}
		}
		calStack = null;
		calList = null;
		return calResult;
	}

	/**
	 * Calculate the result of operator
	 * 
	 * @param src1
	 * @param src2
	 * @param operator
	 * @return
	 */
	private float calculate(float src1, float src2, char operator) {
		float result = 0f;
		switch (operator) {
		case '*':
			result = src1 * src2;
			break;
		case '+':
			result = src1 + src2;
			break;
		case '-':
			result = src1 - src2;
			break;
		case '/':
			result = src1 / src2;
			break;
		case '%':
			result = src1 % src2;
			break;
		default:
			System.out.println("Unknow operation: "
					+ Arrays.toString(Character.toChars(operator)));
			break;
		}
		return result;
	}

	/**
	 * Format output of float number. Remove the ".0" at end of the number.
	 * 
	 * @param num
	 * @return
	 */
	private static String formatNumber(float num) {
		if (num % 1 == 0) {
			return Integer.toString((int) num);
		}
		return Float.toString(num);
	}

	/**
	 * Find the number values in a char in a rang and build them to a float.
	 * 
	 * @param input
	 * @param start
	 * @param end
	 * @return
	 */
	private float buildNumber(char[] input, int start, int end)
			throws Exception {
		float num = 0f;
		int pointPos = 0;
		for (int i = start; i < end; i++) {
			if (input[i] == '.') {
				if (pointPos > 0)
					throw new Exception("More than one dot in a number.");
				pointPos = i + 1;
			} else {
				num = num * 10.0f + (input[i] - '0');
			}
		}
		if (pointPos > 0) {
			int scale = end - pointPos;
			for (int i = 0; i < scale; i++) {
				num = num / 10.0f;
			}
			// Note: there are some different values with real float. For
			// example, 5.89 will be 5.8900003 in the codes.It is precision
			// problem. We use BigDecimal and scale to fix it.
			BigDecimal bigD = new BigDecimal(Float.toString(num));
			BigDecimal one = new BigDecimal("1");
			num = bigD.divide(one, end - pointPos, BigDecimal.ROUND_HALF_UP)
					.floatValue();
		}
		return num;

	}

	/**
	 * Check if the char is between '0' and '9'
	 * 
	 * @param c0
	 * @return
	 */
	private boolean isDigit(char c0) {
		int num = c0 - '0';
		return num >= 0 && num < 10;
	}

	/**
	 * Check whether the character is an operator
	 * 
	 * @param c
	 * @return
	 */
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
	}

	/**
	 * Check whether the operator is multiplication or division?
	 * 
	 * @param c
	 * @return
	 */
	private boolean isPriorityOperator(char c) {
		return c == '*' || c == '/' || c== '%';
	}

	/**
	 * Check whether the character is a left parentheses?
	 * 
	 * @param c
	 * @return
	 */
	private boolean isLeftParentheses(char c) {
		return c == '(';
	}

	/**
	 * Check whether the character is right parentheses
	 * 
	 * @param c
	 * @return
	 */
	private boolean isRightParentheses(char c) {
		return c == ')';
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Example: ");
		String expression = "((3 + 4.050 + (5.89 + 2) * 5 - (0.5+0.3) * (8 + 3))) / 4";
		ExpressionCalculator proj = new ExpressionCalculator();
		try {
			String pfnttEx = proj.parserExpression(expression);
			System.out.println("Postfix Notation expression: " + pfnttEx);
			System.out.println("Result of Postfix Notation expression: "
					+ proj.calculatePostfixNotationExpression(pfnttEx));
			while (true) {
			System.out.println();
			System.out.print("Please input your expression: ");
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine();
			pfnttEx = proj.parserExpression(line);
			System.out.println("Postfix Notation expression: " + pfnttEx);
			System.out.println("Result of Postfix Notation expression: "
					+ formatNumber(proj
							.calculatePostfixNotationExpression(pfnttEx)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

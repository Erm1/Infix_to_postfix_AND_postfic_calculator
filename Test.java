/*
Ermiyas Liyeh
30711570
CSC 172 Online- Summer 2019
I did not collaborate with anyone on this assignment.
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Test {
	
	public static void main(String [] args) throws IOException {
		
		//creating buffered reader that reads from the file whose name is the first command line argument
		File file = new File("infix_input.txt");
		FileReader input = new FileReader(file);
		BufferedReader reader = new BufferedReader(input);
		
		File outputFile = new File("OUTPUT.txt");
		 
		// if the output file doesn't exist then it is created
		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}

		//creating buffered writer that writes the output to file
		FileWriter fileWriter = new FileWriter(outputFile.getAbsoluteFile());
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		//the current line from the input file
		String currentLine;
		
		//keeps processing lines from the file until the line is null (i.e. the end of the file is reached)
		while ((currentLine = reader.readLine()) != null) {
		
			//calls method that formats the string for appropriate conversion
			//the method inserts spaces a specified points so the string can be split at the spaces
			currentLine = formatString(currentLine);
			
			//calls the method which fills the queue with the postfix expression based on the formatted infix expression
			MyQueue<String> postfixExpression = infixToPostfix(currentLine);
			//postfixExpression.print();
			
			//calls the method which evaluates the postfix expression
			String finalResult = postfixEval(postfixExpression);
			
			//writes the result of the postfix evaluation to the output file
			writer.write(finalResult);
			writer.flush();
			writer.newLine();
			
			//System.out.println("\nFinal output: " + finalResult);
		}
		//closing reader and writer
		reader.close();
		writer.close();
	}
	
	//method that converts an infix expression to a postfix expression
	public static MyQueue<String> infixToPostfix(String currentLine) {
		
		MyQueue<String> outputQueue = new MyQueue<String>();
		MyStack<String> pendingOperators = new MyStack<String>();
		
		//splits the formatted line at spaces
		String [] infixExpression = currentLine.split(" ");
		
		//used to check if a character is an operand of an operator
		String operators = "()!+-/%*<>=&|";
		
		//for each character in the array of characters
		for(int i = 0; i < infixExpression.length; i++) {
			//if the character is NOT an operator (i.e. it is an operand) it is enqueued
			if(operators.indexOf(infixExpression[i]) == -1) {
				outputQueue.enqueue(infixExpression[i]);
			}
			else {
				//if the operand is a close parenthesis, keep popping and enqueue-ing characters from the stack
				//until you reach the end of the stack or an open parenthesis
				if(infixExpression[i].equals(")")) {
					while(!pendingOperators.isEmpty() && pendingOperators.peek().equals("(") == false) {
					
						outputQueue.enqueue(pendingOperators.pop());
					}
					//to get rid of the open parenthesis
					pendingOperators.pop();
				}
				else {
					//if the character is an operand but is not a close parenthesis
					//keep popping off the stack until you reach an operator of lower precedence
					while(!pendingOperators.isEmpty() && pendingOperators.peek().equals("(") == false) {
						//precedence method returns an integer representation of the operators precedence in terms of order of operations
						if(precedence(pendingOperators.peek()) >= precedence(infixExpression[i])) {
							outputQueue.enqueue(pendingOperators.pop());
						}
						else {
							break;
						}
					}
					//push the current character onto the stack
					pendingOperators.push(infixExpression[i]);
				}
			}
		}
		
		//if the stack still contains characters, enqueue them onto the output queue
		while(pendingOperators.isEmpty() == false) {
			outputQueue.enqueue(pendingOperators.pop());
			
		}
		
		//To print the postfix form of each infix mathematical expressions
		outputQueue.printList();
		System.out.println("----------------");
		return outputQueue;
	}
	
	//method that evaluates postfix expressions
	public static String postfixEval(MyQueue<String> postfixExp) {
		
		//used to check if a character is an operator
		String operators = "+-*/<>=&|!%";
		
		MyStack<String> evaluation = new MyStack<String>();
		String currentToken;//current token that is being processed
		int operatorIndex;//used for switch statement
		String operandA, operandB;//operands as Strings
		double opA, opB;//operands as doubles
		String result;//result as a string
		boolean opBooleanA, opBooleanB;//operands as booleans
		
		//decimal format to include exactly two decimal places
		DecimalFormat twoDecimals = new DecimalFormat("0.00");
		
		//keeps processing until the queue containing the postfix expression is empty
		while(postfixExp.isEmpty() == false) {
			//get the current token
			currentToken = postfixExp.dequeue();
			
			//if the token is an operand, push it onto the stack
			if(operators.indexOf(currentToken) == -1) {
				evaluation.push(currentToken);
			}
			else{
				//resetting all the operands just in case
				operandA = operandB = result = "";
				opA = opB = 0;
				opBooleanA = opBooleanB = false;
				
				//gets the index of the operator from above string of operators
				operatorIndex = operators.indexOf(currentToken);
				
				//switch statement for evaluation
				switch(operatorIndex) {
				
				// ADDITION
				case 0: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
							opA = Double.parseDouble(operandA);
							opB = Double.parseDouble(operandB);
							}
							catch(NumberFormatException e) {
								System.out.println("Number Format Exception: " + e.getMessage());
								return "You didn't enter a valid expression";
							}
						result = Double.toString((opA + opB));
						evaluation.push(result);
						break;
						
				// SUBTRACTION		
				case 1: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
							opA = Double.parseDouble(operandA);
							opB = Double.parseDouble(operandB);
							}
							catch(NumberFormatException e) {
								System.out.println("Number Format Exception: " + e.getMessage());
								return "You didn't enter a valid expression";
							}
						result = Double.toString((opB - opA));
						evaluation.push(result);
						break;
						
				// MULTIPLICATION
				case 2: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
						opA = Double.parseDouble(operandA);
						opB = Double.parseDouble(operandB);
						}
						catch(NumberFormatException e) {
							System.out.println("Number Format Exception: " + e.getMessage());
							return "You didn't enter a valid expression";
						}
						result = Double.toString((opA * opB));
						evaluation.push(result);
						break;
				
				// DIVISION
				case 3: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
							opA = Double.parseDouble(operandA);
							opB = Double.parseDouble(operandB);
							}
							catch(NumberFormatException e) {
								System.out.println("Number Format Exception: " + e.getMessage());
								return "You didn't enter a valid expression";
							}
						if(opA != 0) {
							result = Double.toString((opB / opA));
						}
						else {
							return "You tried to divide by zero";
						}
						evaluation.push(result);
						break;
				
				// LESS THAN
				case 4: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
							opA = Double.parseDouble(operandA);
							opB = Double.parseDouble(operandB);
							}
							catch(NumberFormatException e) {
								System.out.println("Number Format Exception: " + e.getMessage());
								return "You didn't enter a valid expression";
							}
						
						if(opB < opA) {
							result = "1";
						}
						else {
							result = "0";
						}
						
						evaluation.push(result);
						break;
				
				// GREATER THAN
				case 5: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
							opA = Double.parseDouble(operandA);
							opB = Double.parseDouble(operandB);
							}
							catch(NumberFormatException e) {
								System.out.println("Number Format Exception: " + e.getMessage());
								return "You didn't enter a valid expression";
							}
						
						if(opB > opA) {
							result = "1";
						}
						else {
							result = "0";
						}
						
						evaluation.push(result);
						break;
				
				// EQUAL TO
				case 6: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
							opA = Double.parseDouble(operandA);
							opB = Double.parseDouble(operandB);
							}
							catch(NumberFormatException e) {
								System.out.println("Number Format Exception: " + e.getMessage());
								return "You didn't enter a valid expression";
							}
						
						if(opA == opB) {
							result = "1";
						}
						else {
							result = "0";
						}
						
						evaluation.push(result);
						break;	
					
									
				// LOGICAL AND
				case 7: operandA = evaluation.pop();
						operandB = evaluation.pop();
						
						if(operandA.equals("1")) {
							opBooleanA = true;
						}
						else {
							opBooleanA = false;
						}
						
						if(operandB.equals("1")) {
							opBooleanB = true;
						}
						else {
							opBooleanB = false;
						}
						
						if(opBooleanA && opBooleanB) {
							result = "1";
						}
						else {
							result = "0";
						}
						
						evaluation.push(result);
						break;	
					
				// LOGICAL OR
				case 8: operandA = evaluation.pop();
						operandB = evaluation.pop();
						
						if(operandA.equals("1")) {
							opBooleanA = true;
						}
						else {
							opBooleanA = false;
						}
						
						if(operandB.equals("1")) {
							opBooleanB = true;
						}
						else {
							opBooleanB = false;
						}
						
						if(opBooleanA || opBooleanB) {
							result = "1";
						}
						else {
							result = "0";
						}
						
						evaluation.push(result);
						break;	
						
				// LOGICAL NOT
				case 9: operandA = evaluation.pop();
						
					if(operandA.equals("1")) {
						opBooleanA = true;
					}
					else {
						opBooleanA = false;
					}
						
					if(!opBooleanA == true) {
						result = "1";
					}
					else {
						result = "0";
					}
						
					evaluation.push(result);
					break;
					
				// MODULUS
				case 10: operandA = evaluation.pop();
						operandB = evaluation.pop();
						try {
							opA = Double.parseDouble(operandA);
							opB = Double.parseDouble(operandB);
							}
							catch(NumberFormatException e) {
								System.out.println("Number Format Exception: " + e.getMessage());
								return "You didn't enter a valid expression";
							}
						
						result = Double.toString((opB%opA));
						evaluation.push(result);
						break;
						
				}
			}
		}
		
		//the final result is the last number of the stack
		String finalResult = evaluation.pop();
		
		//error handling for a final answer that is not a valid number
		try{
		//format the final result with two decimal places
		Double finalResultDouble = Double.parseDouble(finalResult);
		finalResult = twoDecimals.format(finalResultDouble);
		}
		catch(NumberFormatException e) {
			System.out.println("Number Format Exception: " + e.getMessage());
			return "You didn't enter a valid expression";
		}
		
		//if the stack is now empty, the processing was successful
		if(evaluation.isEmpty() == true) {
			return finalResult;
		}
		else {
			//if the stack is not empty, there was a problem
			return "Error : Final Stack contained more than one answer";
		}
		
	}
	
	//method that formats the current line from the input file
	public static String formatString(String currentLine) {
		
		//first get rid of all spacing in the line
		currentLine = currentLine.replaceAll(" ","");
		
		//used to check if the character is an operator or not
		String operators = "()/*%+-<>=&|!";
		
		//using a string builder to append spaces where necessary
		StringBuilder result = new StringBuilder();
		
		//for the entire length of the current line
		for (int i = 0; i < currentLine.length(); i++) {
			
			//if the character is a minus sign that is not at start of the line
			if(operators.indexOf(currentLine.charAt(i)) == 6 && i != 0) {
				//if there is no operand before or after the minus sign, it is truly a minus sign and spaces are appended before and after it
				if(operators.indexOf(currentLine.charAt(i - 1)) == -1 && operators.indexOf(currentLine.charAt(i + 1)) == -1) {
					result.append(" ");
				    result.append(currentLine.charAt(i));
				    result.append(" ");
				}
				else {
					//if the previous character is a number
					if(operators.indexOf(currentLine.charAt(i - 1)) == -1) {
						result.append(" ");
					    result.append(currentLine.charAt(i));
					}
					else {
						//if the next character is a number it is a negative sign
						if(operators.indexOf(currentLine.charAt(i + 1)) == -1){
						result.append(" ");
					    result.append(currentLine.charAt(i));
						}
						else {
							
							if(operators.indexOf(currentLine.charAt(i - 1)) != -1 && operators.indexOf(currentLine.charAt(i + 1)) != -1) {
								result.append(" ");
							    result.append(currentLine.charAt(i));
							    result.append(" ");
							}
						}
					}
				}
			}
			else {
				//if the minus sign is at the beginning of the line (in which case it is a negative sign)
				if(operators.indexOf(currentLine.charAt(i)) == 6 && i == 0) {
					result.append(currentLine.charAt(i));
				}
				else {
					//if the character is an operator that is not at the beginning of the line
				  if (operators.indexOf(currentLine.charAt(i)) != -1 && i != 0) {
				      result.append(" ");
				      result.append(currentLine.charAt(i));
				      result.append(" ");
				  }
				  else {
					  //if the character is an operator at the beginning of the line
					  if(operators.indexOf(currentLine.charAt(i)) != -1){
						  result.append(currentLine.charAt(i));
					      result.append(" ");
					  }
					  else {
						  //otherwise the character is an operand and no space is needed
						  result.append(currentLine.charAt(i));
					  }
				  }
				   
				}
			}
		}
		
		//current line is updated from string builder
		currentLine = result.toString();
		
		//eliminates any double spaces
		currentLine = currentLine.replaceAll("  ", " ");
		//System.out.println(currentLine);
		
		//return the procesed string
		return currentLine;
	}
	
	
	//method that determines the precedence of operators
	public static int precedence(String operator) {
		
		//operators in order of precedence
		String operatorList = "!|&=<>+-*/%()";
		
		//because the operators come in doubles (* and / have the same precedence) divide the index by 2
		return (int) Math.floor(0.5 * operatorList.indexOf(operator));
		
		
	}

}

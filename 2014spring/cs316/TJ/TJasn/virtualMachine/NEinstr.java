package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;

public class NEinstr extends ZeroOperandInstruction {

  void execute ()
  {
     /* ???????? */
	  /* Haijun Su */
	  EXPRSTACK[--ESP-1] = (EXPRSTACK[ESP-1] != EXPRSTACK[ESP]) ? 1:0;
  }

  public NEinstr ()
  {
    super("NE");
  }
}

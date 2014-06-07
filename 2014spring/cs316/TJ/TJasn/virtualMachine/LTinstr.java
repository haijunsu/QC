package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;

public class LTinstr extends ZeroOperandInstruction {

  void execute ()
  {
    /* ???????? */
	  /* Haijun Su */
	  EXPRSTACK[--ESP-1] = (EXPRSTACK[ESP-1] < EXPRSTACK[ESP]) ? 1:0;
  }

  public LTinstr ()
  {
    super("LT");
  }
}

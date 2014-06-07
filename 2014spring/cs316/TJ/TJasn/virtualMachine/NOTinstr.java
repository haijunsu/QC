package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;

public class NOTinstr extends ZeroOperandInstruction {

  void execute ()
  {
     /* ???????? */
	  /* Haijun Su */
	  EXPRSTACK[ESP-1] ^= 1;
  }

  public NOTinstr ()
  {
    super("NOT");
  }
}

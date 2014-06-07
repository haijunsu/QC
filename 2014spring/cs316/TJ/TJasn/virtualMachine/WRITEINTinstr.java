package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;

public class WRITEINTinstr extends ZeroOperandInstruction {

  void execute ()
  {
    /* ???????? */
	  /* Haijun Su */
	  System.out.println(EXPRSTACK[--ESP]);
  }

  public WRITEINTinstr ()
  {
    super("WRITEINT");
  }
}


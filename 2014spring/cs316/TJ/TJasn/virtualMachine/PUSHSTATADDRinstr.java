package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;
import TJasn.TJ;

public class PUSHSTATADDRinstr extends OneOperandInstruction {

  void execute ()
  {
    /* ???????? */
	  /* Haijun Su */
	  EXPRSTACK[ESP++] = operand + POINTERTAG;
  }

  public PUSHSTATADDRinstr (int offset)
  {
    super(offset, "PUSHSTATADDR");
  }
}


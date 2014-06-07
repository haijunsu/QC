package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;

public class JUMPONFALSEinstr extends OneOperandInstruction {

  void execute ()
  {
    /* ???????? */
	  /* Haijun Su */
	  if (EXPRSTACK[--ESP] == 0) {
		  PC = operand;
	  }
  }

  public JUMPONFALSEinstr (int address)
  {
    super(address, "JUMPONFALSE");
  }
}


package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;
import TJasn.TJ;
import TJasn.virtualMachine.VirtualMachineHaltException;

public class SAVETOADDRinstr extends ZeroOperandInstruction {

  void execute () throws VirtualMachineHaltException
  {
     /* ???????? */
	  /* Haijun Su */
	  // pop v
	  int v = EXPRSTACK[--ESP];
	  // pop p
	  int p = EXPRSTACK[--ESP];
	  // store v
	  TJ.data[p - POINTERTAG] = v;
  }

  public SAVETOADDRinstr ()
  {
    super("SAVETOADDR");
  }
}


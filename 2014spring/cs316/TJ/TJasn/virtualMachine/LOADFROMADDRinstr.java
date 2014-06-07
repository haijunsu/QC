package TJasn.virtualMachine;

import static TJasn.virtualMachine.CodeInterpreter.*;
import TJasn.TJ;
import TJasn.virtualMachine.VirtualMachineHaltException;

public class LOADFROMADDRinstr extends ZeroOperandInstruction {

  void execute () throws VirtualMachineHaltException
  {
       /* ???????? */
	  /* Haijun Su */
	  int p = EXPRSTACK[ESP - 1];
	  EXPRSTACK[ESP - 1] = TJ.data[p - POINTERTAG];
  }

  public LOADFROMADDRinstr ()
  {
    super("LOADFROMADDR");
  }
}


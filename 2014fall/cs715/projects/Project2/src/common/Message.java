package common;

import java.io.Serializable;

/**
 * Used to exchange message between clients and server
 * 
 * @author Haijun Su 
 * Date Dec 18, 2014
 *
 */
public class Message implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 3635384963538773512L;

	/**
	 * All commands which server can handle.
	 * 
	 * @author Haijun Su
	 * Date Dec 19, 2014
	 *
	 */
	public enum Command {
		joinGrp,
		askForSeat,
		takingExam,
		submitExam,
		readyForGame,
		playGame,
		playFinalQuestion,
		givePermissionEnterClassroom,
		startExam,
		gradeExam,
		prepareHost,
		introduceWinners,
		startGame,
		waitGameStartSignal,
		runRoundQuestions,
		runFianlQuestion,
		configuration,
		next,
		EXIT
	}

	/**
	 * Client role
	 * 
	 * @author Haijun Su
	 * Date Dec 19, 2014
	 *
	 */
	public enum Role {
		Contestant,
		Announcer,
		Host,
		Service,
		Other
	}

	/**
	 * Message type
	 * 
	 * @author Haijun Su
	 * Date Dec 19, 2014
	 *
	 */
	public enum Type {
		text,
		command
	}

	/**
	 * Identify the message owner
	 */
	private String sender;

	/**
	 * Sender's role
	 */
	private Role role;

	/**
	 * Text content
	 */
	private String txtMsg;

	/**
	 * Integer content
	 */
	private int intValue;

	/**
	 * Command
	 */
	private Command cmd;

	/**
	 * Message type
	 */
	private Type type = Type.text;

	/**
	 * Construct with sender and sender's role
	 * @param sender
	 * @param role
	 */
	public Message(String sender, Role role) {
		this.sender = sender;
		this.role = role;
	}

	/**
	 * Construct with sender, sender's role, and text content
	 * @param sender
	 * @param role
	 * @param txtMsg
	 */
	public Message(String sender, Role role, String txtMsg) {
		this.sender = sender;
		this.role = role;
		this.txtMsg = txtMsg;
		type = Type.text;
	}

	/**
	 * Construct with sender, sender's role, and command
	 * @param sender
	 * @param role
	 * @param command
	 */
	public Message(String sender, Role role, Command command) {
		this.sender = sender;
		this.role = role;
		this.cmd = command;
		type = Type.command;
	}

	/**
	 * Construct with sender, sender's role, command, and text content
	 * @param sender
	 * @param role
	 * @param command
	 * @param txtMsg
	 */
	public Message(String sender, Role role, Command command, String txtMsg) {
		this.sender = sender;
		this.role = role;
		this.txtMsg = txtMsg;
		this.cmd = command;
		type = Type.command;
	}

	/**
	 * Construct with sender, sender's role, command, and integer content
	 * @param sender
	 * @param role
	 * @param command
	 * @param intMsg
	 */
	public Message(String sender, Role role, Command command, int intMsg) {
		this.sender = sender;
		this.role = role;
		this.intValue = intMsg;
		this.cmd = command;
		type = Type.command;
	}

	public String getSender() {
		return sender;
	}

	public void setOwner(String sender) {
		this.sender = sender;
	}

	public String getTxtMsg() {
		return txtMsg;
	}

	public void setTxtMsg(String txtMsg) {
		this.txtMsg = txtMsg;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Command getCommad() {
		return cmd;
	}

	public void setCommand(Command cmd) {
		this.cmd = cmd;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmd == null) ? 0 : cmd.hashCode());
		result = prime * result + intValue;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((txtMsg == null) ? 0 : txtMsg.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (cmd != other.cmd)
			return false;
		if (intValue != other.intValue)
			return false;
		if (role != other.role)
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (txtMsg == null) {
			if (other.txtMsg != null)
				return false;
		} else if (!txtMsg.equals(other.txtMsg))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", role=" + role + ", txtMsg="
				+ txtMsg + ", intValue=" + intValue + ", cmd=" + cmd
				+ ", type=" + type + "]";
	}

}
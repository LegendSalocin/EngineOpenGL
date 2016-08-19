package de.salocin.engine.util.input;

import org.lwjgl.glfw.GLFW;

public enum Key {
	
	UNKNOWN(-1),
	
	/** Printable keys. */
	KEY_SPACE(32, true),
	KEY_APOSTROPHE(39, true),
	KEY_COMMA(44, true),
	KEY_MINUS(45, true),
	KEY_PERIOD(46, true),
	KEY_SLASH(47, true),
	KEY_0(48, true),
	KEY_1(49, true),
	KEY_2(50, true),
	KEY_3(51, true),
	KEY_4(52, true),
	KEY_5(53, true),
	KEY_6(54, true),
	KEY_7(55, true),
	KEY_8(56, true),
	KEY_9(57, true),
	KEY_SEMICOLON(59, true),
	KEY_EQUAL(61, true),
	KEY_A(65, true),
	KEY_B(66, true),
	KEY_C(67, true),
	KEY_D(68, true),
	KEY_E(69, true),
	KEY_F(70, true),
	KEY_G(71, true),
	KEY_H(72, true),
	KEY_I(73, true),
	KEY_J(74, true),
	KEY_K(75, true),
	KEY_L(76, true),
	KEY_M(77, true),
	KEY_N(78, true),
	KEY_O(79, true),
	KEY_P(80, true),
	KEY_Q(81, true),
	KEY_R(82, true),
	KEY_S(83, true),
	KEY_T(84, true),
	KEY_U(85, true),
	KEY_V(86, true),
	KEY_W(87, true),
	KEY_X(88, true),
	KEY_Y(89, true),
	KEY_Z(90, true),
	KEY_LEFT_BRACKET(91, true),
	KEY_BACKSLASH(92, true),
	KEY_RIGHT_BRACKET(93, true),
	KEY_GRAVE_ACCENT(96, true),
	KEY_WORLD_1(161, true),
	KEY_WORLD_2(162, true),
	
	/** Function keys. */
	KEY_ESCAPE(256),
	KEY_ENTER(257),
	KEY_TAB(258),
	KEY_BACKSPACE(259),
	KEY_INSERT(260),
	KEY_DELETE(261),
	KEY_RIGHT(262),
	KEY_LEFT(263),
	KEY_DOWN(264),
	KEY_UP(265),
	KEY_PAGE_UP(266),
	KEY_PAGE_DOWN(267),
	KEY_HOME(268),
	KEY_END(269),
	KEY_CAPS_LOCK(280),
	KEY_SCROLL_LOCK(281),
	KEY_NUM_LOCK(282),
	KEY_PRINT_SCREEN(283),
	KEY_PAUSE(284),
	KEY_F1(290),
	KEY_F2(291),
	KEY_F3(292),
	KEY_F4(293),
	KEY_F5(294),
	KEY_F6(295),
	KEY_F7(296),
	KEY_F8(297),
	KEY_F9(298),
	KEY_F10(299),
	KEY_F11(300),
	KEY_F12(301),
	KEY_F13(302),
	KEY_F14(303),
	KEY_F15(304),
	KEY_F16(305),
	KEY_F17(306),
	KEY_F18(307),
	KEY_F19(308),
	KEY_F20(309),
	KEY_F21(310),
	KEY_F22(311),
	KEY_F23(312),
	KEY_F24(313),
	KEY_F25(314),
	KEY_NUMPAD_0(320),
	KEY_NUMPAD_1(321),
	KEY_NUMPAD_2(322),
	KEY_NUMPAD_3(323),
	KEY_NUMPAD_4(324),
	KEY_NUMPAD_5(325),
	KEY_NUMPAD_6(326),
	KEY_NUMPAD_7(327),
	KEY_NUMPAD_8(328),
	KEY_NUMPAD_9(329),
	KEY_NUMPAD_DECIMAL(330),
	KEY_NUMPAD_DIVIDE(331),
	KEY_NUMPAD_MULTIPLY(332),
	KEY_NUMPAD_SUBTRACT(333),
	KEY_NUMPAD_ADD(334),
	KEY_NUMPAD_ENTER(335),
	KEY_NUMPAD_EQUAL(336),
	KEY_LEFT_SHIFT(340),
	KEY_LEFT_CONTROL(341),
	KEY_LEFT_ALT(342),
	KEY_LEFT_SUPER(343),
	KEY_RIGHT_SHIFT(344),
	KEY_RIGHT_CONTROL(345),
	KEY_RIGHT_ALT(346),
	KEY_RIGHT_SUPER(347),
	KEY_MENU(348);
	
	private int id;
	private boolean printable;
	
	private Key(int id) {
		this(id, false);
	}
	
	private Key(int id, boolean printable) {
		this.id = id;
		this.printable = printable;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isPrintable() {
		return printable;
	}
	
	public String getLocalizedName() {
		if (!isPrintable()) {
			throw new RuntimeException("This key is not printable.");
		}
		
		return GLFW.glfwGetKeyName(id, UNKNOWN.id);
	}
	
	public static Key fromId(int id) {
		for (Key k : values()) {
			if (k.id == id) {
				return k;
			}
		}
		
		return Key.UNKNOWN;
	}
}

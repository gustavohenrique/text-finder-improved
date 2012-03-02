package wdev.enums;

public enum CategoryEnum {

	BSAD2("BSAD2"), BSAD3("BSAD3"), OTHER("OTHER");

	private String code;

	private CategoryEnum(String c) {
		code = c;
	}

	public String getName() {
		return code;
	}

	public static CategoryEnum find(String code) {
		for (CategoryEnum me : CategoryEnum.values()) {
			if (me.code.equals(code)) {
				return me;
			}
		}
		throw new IllegalArgumentException("Nao foi encontrada opcao para " + code);
	}
}

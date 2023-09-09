package reflexivity.model;

public class A {

	private Integer a;
	private String b;
	public Long c;

	public A(Integer a, String b, Long c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public A(Integer a) {
		super();
		this.a = a;
	}

	public A() {
		super();
	}

	public A(Integer a, String b) {
		super();
		this.a = a;
		this.b = b;
	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public Long getC() {
		return c;
	}

	public void setC(Long c) {
		this.c = c;
	}
}
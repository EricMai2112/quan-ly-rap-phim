package MODEL;

public enum VaiTro {
    QUAN_LY("Quản lý"),
    THU_NGAN("Thu ngân");

    private final String dbValue;
    VaiTro(String dbValue) { this.dbValue = dbValue; }
    public String getDbValue() { return dbValue; }

    @Override
    public String toString() {
        return dbValue;
    }
	 public String getDbValue() {
	        return this.name(); // trả về đúng giá trị THU_NGAN, QUAN_LY cho database
	    }
}

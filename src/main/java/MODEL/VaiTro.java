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
}

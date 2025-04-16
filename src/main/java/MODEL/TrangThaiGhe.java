
package MODEL;

public enum TrangThaiGhe {
	GHE_TRONG("GHE_TRONG", "Ghế trống"),
    GHE_DAT("GHE_DAT", "Ghế đã được đặt");

    private final String dbValue; // Giá trị lưu vào cơ sở dữ liệu
    private final String displayName; // Giá trị hiển thị cho người dùng

    TrangThaiGhe(String dbValue, String displayName) {
        this.dbValue = dbValue;
        this.displayName = displayName;
    }

    // Trả về giá trị để lưu vào cơ sở dữ liệu
    public String getDbValue() {
        return dbValue;
    }

    // Trả về giá trị hiển thị
    @Override
    public String toString() {
        return displayName;
    }

    // Phương thức để lấy enum từ giá trị cơ sở dữ liệu
    public static TrangThaiGhe fromDbValue(String dbValue) {
        for (TrangThaiGhe trangThai : TrangThaiGhe.values()) {
            if (trangThai.getDbValue().equals(dbValue)) {
                return trangThai;
            }
        }
        throw new IllegalArgumentException("Invalid trangThaiGhe value: " + dbValue);
    }
}

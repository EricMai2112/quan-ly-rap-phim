package MODEL;

public enum TrangThaiPhim {

    DANG_CHIEU("Đang chiếu"), SAP_CHIEU("Sắp chiếu"), HET_CHIEU("Hết chiếu");
    
    private String trangThaiPhim;
    

	private TrangThaiPhim(String trangThaiPhim) {
		this.trangThaiPhim = trangThaiPhim;
	}

	@Override
    public String toString() {
        return trangThaiPhim;
	}
	public static TrangThaiPhim fromString(String text) {
        for (TrangThaiPhim t : TrangThaiPhim.values()) {
            if (t.trangThaiPhim.equalsIgnoreCase(text)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy trạng thái phim: " + text);
    }
}

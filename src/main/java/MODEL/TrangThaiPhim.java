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
}

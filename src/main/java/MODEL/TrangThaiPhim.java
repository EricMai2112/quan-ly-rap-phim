package MODEL;

public enum TrangThaiPhim {

    DANG_CHIEU("Phim dang duoc chieu"), SAP_CHIEU("Phim sap chieu"), HET_CHIEU("Phim da het chieu");
    
    private String trangThaiPhim;
    

	private TrangThaiPhim(String trangThaiPhim) {
		this.trangThaiPhim = trangThaiPhim;
	}

	@Override
    public String toString() {
        return trangThaiPhim;
	}
}

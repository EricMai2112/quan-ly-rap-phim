package MODEL;

public enum TrangThaiGhe {
    GHE_TRONG("Ghe trong"), GHE_DAT("Ghe da duoc dat");
    
    private String trangThaiGhe;
    

	private TrangThaiGhe(String trangThaiGhe) {
		this.trangThaiGhe = trangThaiGhe;
	}

	@Override
    public String toString() {
        return trangThaiGhe;
    }
}

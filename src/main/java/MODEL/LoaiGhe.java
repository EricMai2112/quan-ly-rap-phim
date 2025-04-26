package MODEL;

public enum LoaiGhe {
    THUONG("Ghe thuong"), VIP("Ghe vip");
    
    private String loaiGhe;
    

    private LoaiGhe(String loaiGhe) {
		this.loaiGhe = loaiGhe;
	}


	@Override
    public String toString() {
        return loaiGhe;
    }
}

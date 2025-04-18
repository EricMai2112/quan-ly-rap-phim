
package MODEL;

public enum VaiTro {
    THU_NGAN("Thu ngan"), QUAN_LY("Quan ly");
    
    private String vaiTro;
    

	private VaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}

	@Override
    public String toString() {
        return vaiTro;
    }
	 public String getDbValue() {
	        return this.name(); // trả về đúng giá trị THU_NGAN, QUAN_LY cho database
	    }
}

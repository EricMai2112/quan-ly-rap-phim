
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
}
